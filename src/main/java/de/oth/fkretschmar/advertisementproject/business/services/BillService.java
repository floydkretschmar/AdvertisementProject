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
import de.oth.fkretschmar.advertisementproject.entities.Bill;
import de.oth.fkretschmar.advertisementproject.entities.BillItem;
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
    

    // --------------- Public methods ---------------
    
    
    /**
     * Creates the specified {@link Bill}.
     * 
     * @param   bill   the bill that will be saved.
     * @return         the saved bill.
     */
    @Transactional
    public Bill create(Bill bill) {
        this.billItemRepository.persist(bill.getItems());
        return this.billRepository.persist(bill);
    }
    
    
    /**
     * Deletes the specified {@link Bill} from the database.
     * 
     * @param   bill    that will be deleted.
     */
    @Transactional
    public void delete(Bill bill) {
        // remove all accounts
        Object[] items = bill.getItems().toArray();
        
        for (int i = 0; i < items.length; i++) {
            if(items[i] instanceof BillItem){
                bill.removeItem((BillItem)items[i]);
                this.billItemRepository.remove((BillItem)items[i]);
            }
        }
        
        this.billRepository.remove(bill);
    }
}
