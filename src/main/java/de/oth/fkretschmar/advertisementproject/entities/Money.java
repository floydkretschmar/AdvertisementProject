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
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fkre
 */
@Embeddable
class Money implements Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the payment amount in the smallest possible unit of the currency.
     */
    @NotNull
    @Column(name = "AMOUNT")
    private long amount;
    
    /**
     * Stores the currency type of the payment amount.
     */
    @NotNull
    @Column(name = "CURRENCY")
    private String currencyCode;
    
        
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link Money}.
     */
    protected Money() {
        super();
    }
    
    
    // --------------- Public getters and setters ---------------
    
    /**
     * Gets the value that consists of the amount and the currency type. 
     * 
     * @return  the {@link MonetaryAmount} object that represents the complex 
     *          money representation.
     */
    public MonetaryAmount getValue() {
        return Monetary.getDefaultAmountFactory()
                    .setCurrency(Monetary.getCurrency(this.currencyCode))
                    .setNumber(this.amount)
                    .create();
    }
    
    
    /**
     * Sets the value that consists of the amount and the currency type. 
     * 
     * @param   monetaryAmount   the complex money representation.
     */
    public void setValue(MonetaryAmount monetaryAmount) {
        this.amount = monetaryAmount.getNumber().longValueExact();
        this.currencyCode = monetaryAmount.getCurrency().getCurrencyCode();
    }
    
    
    // --------------- Public static methods ---------------
    
    
    /**
     * Creates a new instance of {@link Money} using the specified 
     * {@link MoneyBuilder}.
     * 
     * @return  the money builder to create the {@link Money} with.
     */
    public static MoneyBuilder create() {
        return MoneyBuilder.create();
    }
}
