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
package de.oth.fkretschmar.advertisementproject.entities;

import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import de.oth.fkretschmar.advertisementproject.entities.base.IDeletable;

/**
 * Represents all billing information for one particular campaign payment.
 *
 * @author fkre
 */
@Entity(name = "T_BILL")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class Bill extends AbstractAutoGenerateKeyedEntity {
    
    /**
     * Stores the campaign for which this bill applies.
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "CAMPAIGN_ID", nullable = false)
    private Campaign campaign;
    
    
    /**
     * Stores the items that make up the bill.
     */
    @NotNull
    @OneToMany
    @JoinColumn(name="BILL_ID", referencedColumnName="ID", nullable = false)
    private final Collection<BillItem> items = new ArrayList<BillItem>();
    
    /**
     * Stores the total price of the bill.
     */
    @Transient
    private MonetaryAmount totalPrice;
    
    // --------------- Private constructors ---------------
    
    /**
     * 
     * @param campaign 
     */
    private Bill(Campaign campaign) {
        super();
        this.campaign = campaign;
    }
    
    // --------------- Public getters and setters ---------------
 
    
    /**
     * Gets the items that make up the bill.
     * 
     * @return  the bank items of the bill as an unmodifiable colleciton.
     */
    public Collection<BillItem> getItems() {
        return Collections.unmodifiableCollection(this.items);
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Adds an addtional item to the bill.
     * 
     * @param   item    the item that describes a single billing position.
     * @return  {@code true} if the item was added, otherwise {@code false}
     */
    public boolean addItem(BillItem item) {
        if(this.items.add(item)) {
            this.addToTotalPrice(item);
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Removes an existing item from the bill.
     * 
     * @param item  the item that will be removed.
     * @return {@code true} if the item was removed, otherwise {@code false}
     */
    public boolean removeItem(BillItem item) {
        if(this.items.remove(item)) {
            this.totalPrice.subtract(item.getItemPrice());
            return true;
        }
        
        return false;
    }
    
    // --------------- Protected methods ---------------
    
    /**
     * Performs the work of initializing the total price when loading the bill
     * from the database.
     */
    @Override
    protected void postLoad() {
        for (BillItem item : this.items) {
            this.addToTotalPrice(item);
        }
    }
    
    // --------------- Private methods ---------------
    
    
    /**
     * Adds the price of the specified item to the total price of the bill. If
     * the total price has not been set, the total price will be initialized
     * with the price of the specified item.
     * 
     * @param item  the item whose price will be added.
     */
    private void addToTotalPrice(BillItem item) {
        MonetaryAmount itemPrice = item.getItemPrice();

        if(this.totalPrice == null) {
            this.totalPrice = Monetary.getDefaultAmountFactory()
                        .setAmount(itemPrice).create();
        }
        else {
            this.totalPrice.add(itemPrice);
        }
    }
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Bill}.
     * 
     * @param   campaign 
     * @return  the built {@link Bill}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createBill", 
            builderClassName = "BillBuilder",
            buildMethodName = "build")
    private static Bill validateAndCreateBill(
            Campaign campaign) throws BuilderValidationException {
        
        if (campaign == null) {
            throw new BuilderValidationException(
                    Bill.class,
                    "The campaign can not be null.");
        }
        
        return new Bill(campaign);
    }
}
