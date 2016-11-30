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
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author fkre
 */
public class PaymentPlan extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the monetary value of the payment plan that consists of the amount 
     * and the currency type. 
     */
    private Money moneyAmount;
    
    
    /**
     * Stores the kind of payment that this plan describes.
     */
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private PaymentPlanType type;
    
    
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link PaymentPlan}.
     */
    protected PaymentPlan() {
        super();
    }
    
    
    // --------------- Public getters and setters ---------------

    /**
     * Gets the monetary value of the payment plan that consists of the amount 
     * and the currency type. 
     * 
     * @return  the {@link MonetaryAmount} object that represents the monetary 
     *          value.
     */
    public MonetaryAmount getMonetaryAmount() {
        return this.moneyAmount.getValue();
    }

    
    /**
     * Gets the kind of payment that this plan describes.
     * 
     * @return  the {@link PaymentPlanType} that describes the type of the 
     *          payment.
     */
    public PaymentPlanType getType() {
        return this.type;
    }
    
    
    /**
     * Sets the monetary value of the payment plan that consists of the amount 
     * and the currency type. 
     * 
     * @param   monetaryAmount   the amount of money that is being paid with the 
     *                           payment plan.
     */
    public void setMonetaryAmount(MonetaryAmount monetaryAmount) {
        this.moneyAmount.setValue(monetaryAmount);
    }

    
    /**
     * Sets the kind of payment that this plan describes.
     * 
     * @param   type    that describes the type of the payment.
     */
    public void setType(PaymentPlanType type) {
        this.type = type;
    }
    
    
    // --------------- Public static methods ---------------
    
    
    /**
     * Creates a new instance of {@link PaymentPlan} using the specified 
     * {@link PaymentPlanBuilder}.
     * 
     * @return  the payment plan builder to create the 
     *          {@link PaymentPlan} with.
     */
    public static PaymentPlanBuilder create() {
        return PaymentPlanBuilder.create();
    }
}
