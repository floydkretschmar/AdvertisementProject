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
@Entity(name = "T_ORDER_ROW")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class OrderRow extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the advertisment for which is managed by the order row.
     */
    @NotNull
    @OneToOne
    @Getter(AccessLevel.PUBLIC)
    private Advertisement advertisement;
    
    /**
     * Stores the number of advertisements that have been ordered for the target 
     * context.
     */
    @NotNull
    @Column(name = "AMOUNT")
    @Getter(AccessLevel.PUBLIC)
    private long amount;
    
    /**
     * Stores the target context of the specified order row that influinces the
     * price.
     */
    @NotNull
    @OneToOne
    @Getter(AccessLevel.PUBLIC)
    private TargetContext context;
    
    /**
     * Stores the monetary value of the payment plan that consists of the amount 
     * and the currency type. 
     */
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private Money moneyAmount;
    
    /**
     * Stores the kind of payment that this plan describes.
     */
    @NotNull
    @Column(name = "TYPE", updatable = false)
    @Enumerated(EnumType.STRING)
    @Getter(AccessLevel.PUBLIC)
    private OrderRowPaymentType type;
    
    
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
     * Validates the input and creates the corresponding {@link OrderRow}.
     * 
     * @param   advertisement   the advertisment for which is managed by the 
     *                          order row.
     * @param   amount          the number of advertisements that have been 
     *                          ordered for the target context.
     * @param   context         the target context of the specified order row 
     *                          that influinces the price.
     * @param   moneyAmount     the money value of the payment plan that 
     *                          consists of the amount and the currency type. 
     * @param   type            the kind of payment that this plan describes.
     * @return  the built {@link OrderRow}.
     */
    @Builder(
            builderMethodName = "createOrderRow", 
            builderClassName = "OrderRowBuilder",
            buildMethodName = "build")
    private static OrderRow validateAndCreateOrderRow(
            Advertisement advertisement,
            long amount,
            TargetContext context,
            MonetaryAmount monetaryAmount, 
            OrderRowPaymentType type) {
        if (advertisement == null) {
            throw new BuilderValidationException(
                    OrderRow.class,
                    "The advertisement can not be null.");
        }
        
        if (amount == 0) {
            throw new BuilderValidationException(
                    OrderRow.class,
                    "The amount can not be 0.");
        }
        
        if (context == null) {
            throw new BuilderValidationException(
                    OrderRow.class,
                    "The target context can not be null.");
        }
        
        Money moneyAmount = Money.createMoney()
                .monetaryAmount(monetaryAmount).build();
        
        if (type == OrderRowPaymentType.UNDEFINED) {
            throw new BuilderValidationException(
                    OrderRow.class,
                    "The payment type has to be defined.");
        }
        
        return new OrderRow(advertisement, amount, context, moneyAmount, type);
    }
}
