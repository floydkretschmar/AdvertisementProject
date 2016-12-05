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
import javax.money.MonetaryAmount;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author fkre
 */
@Entity(name = "T_BILL_ITEM")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class BillItem extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the content that is being billed with this item.
     */
    @OneToOne(optional = false)
    @NotNull
    @Getter
    private CampaignContent campaignContent;
    
    /**
     * Stores the number of time the content was requestet since the last bill.
     */
    @NotNull
    @Column(name = "REQUESTS", nullable = false)
    @Getter
    private long contentRequests;
    
    /**
     * Stores the total price for this bill item.
     */
    @NotNull
    @AttributeOverrides({
        @AttributeOverride(name="formattedValue",
                           column=@Column(name="ITEM_PRICE"))
//        @AttributeOverride(name="amount",
//                           column=@Column(name="PRICE_PER_REQUEST_AMOUNT")),
//        @AttributeOverride(name="currencyCode",
//                           column=@Column(name="PRICE_PER_REQUEST_CURRENCY"))
    })
    private Money itemPrice;
    
    // --------------- Public methods ---------------
    
    
    /**
     * Gets the total price for this bill item.
     * 
     * @return the item price.
     */
    public MonetaryAmount getItemPrice() {
        return this.itemPrice.getValue();
    }
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link BillItem}.
     * 
     * @param campaignContent 
     * @param contentRequests 
     * @return  the built {@link BillItem}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createBillItem", 
            builderClassName = "BillItemBuilder",
            buildMethodName = "build")
    private static BillItem validateAndCreateBillItem(
            CampaignContent campaignContent, 
            long contentRequests) throws BuilderValidationException {
        
        if (campaignContent == null) {
            throw new BuilderValidationException(
                    BillItem.class,
                    "The content can not be null.");
        }
        
        if (contentRequests < 0) {
            throw new BuilderValidationException(
                    BillItem.class,
                    "The amount can not be smaller than 0.");
        }
        
        Money itemPrice = Money.createMoney()
                .monetaryAmount(
                        campaignContent.getPricePerRequest().multiply(contentRequests))
                .build();
        
        return new BillItem(campaignContent, contentRequests, itemPrice);
    }
}
