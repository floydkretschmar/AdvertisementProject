/*
 * Copyright (C) 2016 fkre
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.oth.fkretschmar.advertisementproject.business.services;

import de.oth.fkretschmar.advertisementproject.business.repositories.BillItemRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.BillRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.CampaignRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRequestRepository;
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.billing.BillItem;
import de.oth.fkretschmar.advertisementproject.entities.billing.ContentRequest;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.PaymentInterval;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Schedule;
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Bill} instances.
 *
 * @author  fkre    Floyd Kretschmar
 */
@ApplicationScoped
public class BillService implements Serializable {

    // --------------- Private fields ---------------

    /**
     * Stores the repository used to manage {@link Bill} entites.
     */
    @Inject
    private BillRepository billRepository;

    /**
     * Stores the repository used to manage {@link BillItem} entites.
     */
    @Inject
    private BillItemRepository billItemRepository;
    
    /**
     * Stores the repository used to manage {@link Campaign} entites.
     */
    @Inject
    private CampaignRepository campaignRepository;
    
    /**
     * Stores the service used to manage {@link Campaign} entites.
     */
    @Inject
    private CampaignService campaignService;

    /**
     * Stores the repositorz used to manage {@link ContentRequest} entities.
     */
    @Inject
    private ContentRequestRepository contentRequestRepository;

    // --------------- Public methods ---------------
    
    
    /**
     * Performs the work of billing the latest set of content requests and 
     * setting up the payment job for campaigns that are payed monthly.
     */
    @Schedule(minute = "*/1")
    @Transactional
    public void billMonthlyContentRequests() {
        this.billContentRequests(PaymentInterval.MONTHLY);
    }
    
    
    /**
     * Performs the work of billing the latest set of content requests and 
     * setting up the payment job for campaigns that are payed quaterly.
     */
    @Schedule(minute = "*/2")
    @Transactional
    public void billQuaterlyContentRequests() {
        this.billContentRequests(PaymentInterval.QUATERLY);
    }
    
    
    /**
     * Performs the work of billing the latest set of content requests and 
     * setting up the payment job for campaigns that are payed yearly.
     */
    @Schedule(minute = "*/4")
    @Transactional
    public void billYearlyContentRequests() {
        this.billContentRequests(PaymentInterval.YEARLY);
    }
    
    /**
     * Creates a new {@link Bill} and links it to the already existing 
     * specified {@link Campaign}.
     * 
     * @param   campaign    to which the bill will be linked.
     * @param   bill        that will be created.
     * @return              the changed campaign.
     */
    @Transactional
    public Campaign createBillForCampaign(Campaign campaign, Bill bill) {
        
        // 1. persist the bill items
        this.billItemRepository.persist(bill.getItems());
        
        // 2. set the campaign on the bill
        campaign = this.campaignRepository.merge(campaign);
        bill.setCampaign(campaign);
        
        // 3. persist the bill
        this.billRepository.persist(bill);
        
        // 4. set the bill on the campaign
        campaign.addBill(bill);
        
        return campaign;
    }
    
    
    /**
     * Deletes the specified {@link Bill} from the database.
     * 
     * @param   bill    that will be deleted.
     */
    @Transactional
    public void delete(Bill bill) {
        Object[] items = bill.getItems().toArray();
        
        for (int i = 0; i < items.length; i++) {
            if(items[i] instanceof BillItem){
                // only call remove because the bill and bill items are 
                // undeletable so still keep their connection
                this.billItemRepository.remove((BillItem)items[i]);
            }
        }
        
        this.billRepository.remove(bill);
    }
    
    // --------------- Private methods ---------------
    
    
    /**
     * Performs the work of billing the latest set of content requests and 
     * setting up the payment job for the specified payment interval.
     * @param interval  the interval.
     */
    @Transactional
    private void billContentRequests(PaymentInterval interval) {
        // finds all requests that have been made since the last interval and 
        // that have the specified pazment interval
        Collection<ContentRequest> requests 
                = this.contentRequestRepository.findForPaymentInterval(interval);
        
        Map<Long, Bill> bills = new TreeMap<Long, Bill>();
        Map<Long, Campaign> campaigns = new TreeMap<Long, Campaign>();
                
        
        requests.forEach(request -> 
        {
            Content content = request.getContent();
            Campaign campaign = content.getCampaign();

            // 1.   find out whether or not a bill has already been created for 
            //      the campaign that this request is bound to
            Bill bill = null;
            if(bills.containsKey(campaign.getId())) {
                bill = bills.get(campaign.getId());

                // 2.   if a bill already exists, find out whether or not there
                //      is already a corresponding bill item to the content of
                //      this request
                BillItem billItem = null;
                if (bill.getItemMap().containsKey(content.getId())) {
                    // A corresponding bill item already exists so get it...
                    billItem = bill.getItemMap().get(
                                            content.getId());

                    // ... add the additional request ...
                    billItem = BillItem.createBillItem()
                            .content(content)
                            .contentRequests(billItem.getContentRequests() + 1)
                            .build();
                    
                    // ... and replace the old item.
                    bill.replaceItem(billItem);
                }
                else {
                    // No corresponding bill item exists so just create a new
                    // one and add it to the bill.
                    billItem = BillItem.createBillItem()
                            .content(content)
                            .contentRequests(1)
                            .build();
                    bill.addItem(billItem);
                }
                
                // replace the found bill with the changed bill
                bills.replace(campaign.getId(), bill);
            }
            else {
                // 3.   If a bill does not exist already, just create a new one,
                //      add a bill item for this change request and store it.
                BillItem billItem = BillItem.createBillItem()
                            .content(content)
                            .contentRequests(1)
                            .build();
                BillItem[] items = {billItem};
                bill = Bill.createBill().items(items).build();
                bills.put(campaign.getId(), bill);
                campaigns.put(campaign.getId(), campaign);
            }
        });
        
        bills.forEach((campaignId, bill) -> 
        {
            // Create each bill for its existing campaign...
            Campaign campaign = this.createBillForCampaign(
                    campaigns.get(campaignId), bill);
            // ... and inform the current user, that the campaign has changed.
            this.campaignService.changeCampaignForUser(
                            campaign.getComissioner(), 
                            campaign);
        });
        
        // Set the corresponding bill on the requests so next time around the
        // paid requests are not loaded.
        requests.forEach(request -> 
        {
            ContentRequest mergedRequest 
                    = this.contentRequestRepository.merge(request);
            
            long campaignId = mergedRequest.getContent().getCampaign().getId();
            mergedRequest.setBill(bills.get(campaignId));
        });
    }
}
