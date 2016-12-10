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
import de.oth.fkretschmar.advertisementproject.entities.Bill;
import de.oth.fkretschmar.advertisementproject.entities.BillItem;
import de.oth.fkretschmar.advertisementproject.entities.Campaign;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Bill} instances.
 *
 * @author fkre
 */
@RequestScoped
public class BillService implements Serializable {

    // --------------- Private fields ---------------

    /**
     * Stores the repository used to manage {@link Bill} entites.
     */
    @Inject
    BillRepository billRepository;

    /**
     * Stores the repository used to manage {@link BillItem} entites.
     */
    @Inject
    BillItemRepository billItemRepository;
    
    /**
     * Stores the repository used to manage {@link Campaign} entites.
     */
    @Inject
    CampaignRepository campaignRepository;
    

    // --------------- Public methods ---------------
    
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
}
