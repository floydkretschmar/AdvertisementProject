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
import de.oth.fkretschmar.advertisementproject.entities.base.MonetaryAmountAttributeConverter;
import javax.money.MonetaryAmount;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import javax.persistence.Column;
import javax.persistence.Convert;
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
    private Content content;
    
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
    @Column(name = "ITEM_PRICE", nullable = false)
    @Getter
    @Convert(converter = MonetaryAmountAttributeConverter.class)
    private MonetaryAmount itemPrice;
    
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link BillItem}.
     * 
     * @param   content             the content that is being billed.
     * @param   contentRequests     the number of times the content was requested.
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
            Content content, 
            long contentRequests) throws BuilderValidationException {
        
        if (content == null) {
            throw new BuilderValidationException(
                    BillItem.class,
                    "The content can not be null.");
        }
        
        if (contentRequests < 0) {
            throw new BuilderValidationException(
                    BillItem.class,
                    "The amount can not be smaller than 0.");
        }
        
        MonetaryAmount itemPrice 
                = content.getPricePerRequest().multiply(contentRequests);
        
        return new BillItem(content, contentRequests, itemPrice);
    }
}
