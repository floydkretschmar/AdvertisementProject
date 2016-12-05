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

import java.io.Serializable;
import java.util.Locale;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author fkre
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
class Money implements Serializable {
    
    // --------------- Private fields ---------------
    
//    /**
//     * Stores the payment amount in the smallest possible unit of the currency.
//     */
//    @NotNull
//    @Column(name = "MONEY_AMOUNT", nullable = false)
//    private long amount;
//    
//    /**
//     * Stores the currency type of the payment amount.
//     */
//    @NotNull
//    @Column(name = "MONEY_CURRENCY", nullable = false)
//    private String currencyCode;
    
    /**
     * Stores the monetary amount as a formatted string.
     */
    @NotNull
    @Column(name = "MONEY_VALUE", nullable = false)
    private String formattedValue;
        
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link Money} using the monetary amount.
     * 
     * @param   monetaryAmount   the complex money representation.
     */
    private Money(MonetaryAmount monetaryAmount) {
        super();
        this.setValue(monetaryAmount);
    }
    
    
    // --------------- Public getters and setters ---------------
    
    /**
     * Gets the value that consists of the amount and the currency type. 
     * 
     * @return  the {@link MonetaryAmount} object that represents the complex 
     *          money representation.
     */
    public MonetaryAmount getValue() {
        MonetaryAmountFormat germanFormat 
                = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        
        return germanFormat.parse(this.formattedValue);
//        return Monetary.getDefaultAmountFactory()
//                    .setCurrency(Monetary.getCurrency(this.currencyCode))
//                    .setNumber(this.amount)
//                    .create();
    }
    
    
    /**
     * Sets the value that consists of the amount and the currency type. 
     * 
     * @param   monetaryAmount   the complex money representation.
     */
    public void setValue(MonetaryAmount monetaryAmount) {
        MonetaryAmountFormat germanFormat 
                = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        this.formattedValue = germanFormat.format(monetaryAmount);
//        this.amount = monetaryAmount.getNumber().longValueExact();
//        this.currencyCode = monetaryAmount.getCurrency().getCurrencyCode();
    }
    
    
    // --------------- Public static methods ---------------
    
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Money}.
     * 
     * @param   monetaryAmount   the complex money representation.
     * @return the built {@link Money}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createMoney", 
            builderClassName = "MoneyBuilder",
            buildMethodName = "build")
    static Money validateAndCreateMoney(MonetaryAmount monetaryAmount) 
            throws BuilderValidationException {
        if(monetaryAmount == null)
            throw new BuilderValidationException(
                    Money.class, 
                    "The monetary amount can not be null or empty.");
        
        return new Money(monetaryAmount);
    }
}
