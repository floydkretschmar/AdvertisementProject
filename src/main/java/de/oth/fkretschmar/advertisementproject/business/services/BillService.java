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

import de.oth.fkretschmar.advertisementproject.business.annotation.PayPalTransaction;
import de.oth.fkretschmar.advertisementproject.business.annotation.BankTransaction;
import de.oth.fkretschmar.advertisementproject.business.services.base.ITransactionService;
import de.oth.fkretschmar.advertisementproject.business.repositories.BillItemRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.BillRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.CampaignRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRequestRepository;
import de.oth.fkretschmar.advertisementproject.business.services.base.IBillService;
import de.oth.fkretschmar.advertisementproject.business.services.base.ICampaignService;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.billing.BillItem;
import de.oth.fkretschmar.advertisementproject.entities.billing.ContentRequest;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.PaymentInterval;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * The service that offers functionality relatetd to the generation and
 * management of {@link Bill} instances.
 *
 * @author fkre Floyd Kretschmar
 */
@Singleton
public class BillService implements Serializable, IBillService {

    // --------------- Private static constants ---------------
    
    /**
     * Defines the initial overdue charge if the account which is supposed to pay
     * a bill has not enough money on it.
     */
    private static final Money INITIAL_OVERDUE_CHARGE 
            = Money.of(CurrencyUnit.EUR, 10);
    
    /**
     * Defines the own account that is the target for transactions.
     * 
     * NOTE: Should not be hard coded ofcourse but I am spending way to much 
     * time on irrelevant shit already.
     */
    public static final BankAccount OWN_BANK_ACCOUNT 
            = BankAccount.createBankAccount()
                    .bic("BYLADEM1DQE")
                    .iban("DE72772300000000000052")
                    .build();
    
    /**
     * Defines the own account that is the target for transactions.
     */
    public static final PayPalAccount OWN_PAYPAL_ACCOUNT 
            = PayPalAccount.createPayPalAccount()
                    .name("testuser@gmail.com")
                    .build();
    /**
     * Defines the subsequent overdue charge for every attempt to pay the 
     * overdue bill, that fails.
     */
    private static final Money SUBSEQUENT_OVERDUE_CHARGE 
            = Money.of(CurrencyUnit.EUR, 15);

    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to manage {@link BankAccount} entities.
     */
    @Inject
    @BankTransaction
    private ITransactionService bankTransactionService;

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
    private ICampaignService campaignService;

    /**
     * Stores the repositorz used to manage {@link ContentRequest} entities.
     */
    @Inject
    private ContentRequestRepository contentRequestRepository;

    /**
     * Stores the service used to manage {@link PayPalAccount} entities.
     */
    @Inject
    @PayPalTransaction
    private ITransactionService payPalTransactionService;

    // --------------- Public methods ---------------
    /**
     * Performs the work of billing the latest set of content requests and
     * setting up the payment job for campaigns that are payed monthly.
     */
    @Schedule(hour = "*", minute = "*/3", second = "0")
    @Transactional
    public void billMonthlyContentRequests() {
        this.billContentRequests(PaymentInterval.MONTHLY);
    }

    /**
     * Performs the work of billing the latest set of content requests and
     * setting up the payment job for campaigns that are payed quaterly.
     */
    @Schedule(hour = "*", minute = "*/9", second = "0")
    @Transactional
    public void billQuaterlyContentRequests() {
        this.billContentRequests(PaymentInterval.QUATERLY);
    }

    /**
     * Performs the work of billing the latest set of content requests and
     * setting up the payment job for campaigns that are payed yearly.
     */
    @Schedule(hour = "*", minute = "*/36", second = "0")
    @Transactional
    public void billYearlyContentRequests() {
        this.billContentRequests(PaymentInterval.YEARLY);
    }

    /**
     * Creates a new {@link Bill} and links it to the already existing specified
     * {@link Campaign}.
     *
     * @param campaign to which the bill will be linked.
     * @param bill that will be created.
     * @return the changed campaign.
     */
    @Transactional
    @Override
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
     * @param bill that will be deleted.
     */
    @Transactional
    @Override
    public void deleteBill(Bill bill) {
        Object[] items = bill.getItems().toArray();

        for (int i = 0; i < items.length; i++) {
            if (items[i] instanceof BillItem) {
                // only call remove because the bill and bill items are 
                // undeletable so still keep their connection
                this.billItemRepository.remove((BillItem) items[i]);
            }
        }

        this.billRepository.remove(bill);
    }

    // --------------- Private methods ---------------
    /**
     * Performs the work of billing the latest set of content requests and
     * setting up the payment job for the specified payment interval.
     *
     * @param interval the interval.
     */
    @Transactional
    private void billContentRequests(PaymentInterval interval) {
        // finds all requests that have been made since the last interval and 
        // that have the specified pazment interval
        Collection<ContentRequest> requests
                = this.contentRequestRepository.findForPaymentInterval(interval);

        Map<Long, Bill> bills = new TreeMap<Long, Bill>();
        Map<Long, Campaign> campaigns = new TreeMap<Long, Campaign>();
        Map<Long, Map<String, BillItem>> billItemMaps = new TreeMap<Long, Map<String, BillItem>>();

        if (requests != null && requests.size() > 0) {
            for (ContentRequest request : requests) {
                Content content = request.getContent();
                Campaign campaign = content.getCampaign();

                // 1.   find out whether or not a bill has already been created for 
                //      the campaign that this request is bound to...
                Bill bill = null;
                if (bills.containsKey(campaign.getId())) {
                    //... and get the bill as well as the corresponding bill items
                    // if the bill already exists
                    bill = bills.get(campaign.getId());
                    Map<String, BillItem> billItems = billItemMaps.get(campaign.getId());

                    // 2.   if a bill already exists, find out whether or not there
                    //      is already a corresponding bill item to the content of
                    //      this request
                    BillItem billItem = null;
                    if (billItems.containsKey(content.getId())) {
                        // A corresponding bill item already exists so get it...
                        billItem = billItems.get(content.getId());

                        // ... add the additional request ...
                        billItem = BillItem.createBillItem()
                                .content(content)
                                .contentRequests(billItem.getContentRequests() + 1)
                                .build();

                        // ... and replace the old item.
                        billItems.replace(content.getId(), billItem);
                    } else {
                        // No corresponding bill item exists so just create a new
                        // one and add it to the bill.
                        billItem = BillItem.createBillItem()
                                .content(content)
                                .contentRequests(1)
                                .build();
                        billItems.put(content.getId(), billItem);
                    }

                    // replace the found bill with the changed bill
                    bills.replace(campaign.getId(), bill);
                } else {
                    // 3.   If a bill does not exist already, just create a new one,
                    //      create a map for the bill items, add a bill item for 
                    //      this change request and store it in the map.
                    Map<String, BillItem> billItems = new HashMap<>();
                    BillItem billItem = BillItem.createBillItem()
                            .content(content)
                            .contentRequests(1)
                            .build();
                    billItems.put(content.getId(), billItem);
                    billItemMaps.put(campaign.getId(), billItems);

                    bill = Bill.createBill().build();
                    bills.put(campaign.getId(), bill);

                    campaigns.put(campaign.getId(), campaign);
                }
            }

            bills.forEach((campaignId, bill)
                    -> {
                // Add all corresponding bill items to the bill...
                Map<String, BillItem> billItems = billItemMaps.get(campaignId);
                billItems.values().forEach(billItem -> bill.addItem(billItem));

                // ... create each bill for its existing campaign...
                Campaign campaign = this.createBillForCampaign(
                        campaigns.get(campaignId), bill);
                // ... inform the current user, that the campaign has changed...
                this.campaignService.changeCampaignForUser(
                        campaign.getComissioner(),
                        campaign);

                try {
                    // ... and actually pay for the bill.
                    if (campaign.getPaymentAccount() instanceof PayPalAccount) {
                        this.payPalTransactionService.transfer(
                                bill.getTotalPrice(),
                                campaign.getPaymentAccount(),
                                BillService.OWN_PAYPAL_ACCOUNT,
                                String.format(
                                        "Payment for bill %s of campaign %s",
                                        bill.getId(),
                                        campaign.getId()));
                    } else if (campaign.getPaymentAccount() instanceof BankAccount) {
                        this.bankTransactionService.transfer(
                                bill.getTotalPrice(),
                                campaign.getPaymentAccount(),
                                BillService.OWN_BANK_ACCOUNT,
                                String.format(
                                        "Payment for bill %s of campaign %s",
                                        bill.getId(),
                                        campaign.getId()));
                    }
                } catch (TransactionFailedException ex) {
                    if (ex.getReason() == TransactionFailureReason.SENDER_OUT_OF_MONEY) {
                        bill.setOverdue(true);
                        bill.setOverdueCharge(BillService.INITIAL_OVERDUE_CHARGE);
                    }
                }
            });

            // Set the corresponding bill on the requests so next time around the
            // paid requests are not loaded.
            requests.forEach(request
                    -> {
                ContentRequest mergedRequest
                        = this.contentRequestRepository.merge(request);

                long campaignId = mergedRequest.getContent().getCampaign().getId();
                mergedRequest.setBill(bills.get(campaignId));
            });
        }
    }
}
