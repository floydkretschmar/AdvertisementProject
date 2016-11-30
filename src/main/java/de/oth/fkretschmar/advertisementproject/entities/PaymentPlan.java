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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author fkre
 */
@Entity(name = "T_PAYMENT_PLAN")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
    @Getter
    @Setter
    private PaymentPlanType type;
    
    
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link PaymentPlan} using the amount and type.
     * 
     * @param   moneyAmount     the money value of the payment plan that 
     *                          consists of the amount and the currency type. 
     * @param   type            the kind of payment that this plan describes.
     */
    private PaymentPlan(Money moneyAmount, PaymentPlanType type) {
        super();
        this.moneyAmount = moneyAmount;
        this.type = type;
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
     * Sets the monetary value of the payment plan that consists of the amount 
     * and the currency type. 
     * 
     * @param   monetaryAmount   the amount of money that is being paid with the 
     *                           payment plan.
     */
    public void setMonetaryAmount(MonetaryAmount monetaryAmount) {
        this.moneyAmount.setValue(monetaryAmount);
    }
    
    
    // --------------- Public static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link PaymentPlan}.
     * 
     * @param   eMailAddress    that is used to identify an paypal account.
     * @return  the built {@link PaymentPlan}.
     */
    @Builder(
            builderMethodName = "create", 
            builderClassName = "PaymentPlanBuilder",
            buildMethodName = "build")
    private static PaymentPlan validateAndCreatePaymentPlan(
            MonetaryAmount monetaryAmount, 
            PaymentPlanType type) {
        Money moneyAmount = Money.create()
                .monetaryAmount(monetaryAmount).build();
        
        if(type == PaymentPlanType.UNDEFINED)
            throw new BuilderValidationException(
                    PaymentPlan.class,
                    "The payment plan type has to be defined.");
        
        return new PaymentPlan(moneyAmount, type);
    }
}
