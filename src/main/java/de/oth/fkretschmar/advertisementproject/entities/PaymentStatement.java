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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Represents a payment used to pay for an advertisement order.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@Entity(name = "T_PAYMENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class PaymentStatement extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the monetary value of the payment that consists of the amount and
     * the currency type. 
     */
    private Money moneyAmount;
    
    /**
     * Stores the reason for the payment.
     */
    @NotNull
    @Column(name = "REASON")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String reason;
    
    /**
     * Stores the account of the recipient of the payment.
     */
    @NotNull
    @OneToOne
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Account recipientAccount;
    
    /**
     * Stores the account of the sender of the payment.
     */
    @NotNull
    @OneToOne
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Account senderAccount;
    
    // --------------- Private constructors ---------------
    
    /**
     * Creates a new instance of {@link PaymentStatement} using the specified
     * money amount, reason, recipient and sender.
     * 
     * @param   moneyAmount         the monetary value of the payment that 
     *                              consists of the amount and the currency type.
     * @param   reason              the reason for the payment.
     * @param   recipientAccount    the account of the recipient of the payment.
     * @param   senderAccount       the account of the sender of the payment.
     */
    protected PaymentStatement(
            Money moneyAmount, 
            String reason, 
            Account recipientAccount, 
            Account senderAccount) {
        super();
        this.moneyAmount = moneyAmount;
        this.reason = reason;
        this.recipientAccount = recipientAccount;
        this.senderAccount = senderAccount;
    }

    
    // --------------- Public getters and setters ---------------

    /**
     * Gets the monetary value of the payment that consists of the amount and
     * the currency type. 
     * 
     * @return  the {@link MonetaryAmount} object that represents the monetary 
     *          value.
     */
    public MonetaryAmount getMonetaryAmount() {
        return this.moneyAmount.getValue();
    }

    
    /**
     * Sets the monetary value of the payment that consists of the amount and
     * the currency type. 
     * 
     * @param   monetaryAmount   the amount of money that is being paid with the 
     *                           payment.
     */
    public void setMonetaryAmount(MonetaryAmount monetaryAmount) {
        this.moneyAmount.setValue(monetaryAmount);
    }
    
    
    // --------------- Protected static methods ---------------
    
    
    /**
     * Validates the input data.
     *     
     * @param   monetaryAmount      the monetary value of the payment that 
     *                              consists of the amount and the currency type.
     * @param   reason              the reason for the payment.
     * @param   recipientAccount    the account of the recipient of the payment.
     * @param   senderAccount       the account of the sender of the payment.
     */
    @Builder(
            builderMethodName = "create", 
            builderClassName = "PaymentStatementBuilder",
            buildMethodName = "build")
    static final Money validateStatementInputData(
            MonetaryAmount monetaryAmount, 
            String reason, 
            Account recipientAccount, 
            Account senderAccount) {
        if(monetaryAmount == null)
            throw new BuilderValidationException(
                    PaymentStatement.class,
                    "The monetary amount can not be null.");
        
        if(reason == null || reason.isEmpty())
            throw new BuilderValidationException(
                    PaymentStatement.class,
                    "The reason can not be null or empty.");
        
        if(recipientAccount == null)
            throw new BuilderValidationException(
                    PaymentStatement.class,
                    "The recipient account can not be null.");
        
        if(senderAccount == null)
            throw new BuilderValidationException(
                    PaymentStatement.class,
                    "The sender account can not be null.");
        
        return Money.createMoney()
                .monetaryAmount(monetaryAmount).build();
    }
    
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link PaymentStatement}.
     *     
     * @param   monetaryAmount      the monetary value of the payment that 
     *                              consists of the amount and the currency type.
     * @param   reason              the reason for the payment.
     * @param   recipientAccount    the account of the recipient of the payment.
     * @param   senderAccount       the account of the sender of the payment.
     * @return  the built {@link PaymentStatement}.
     */
    @Builder(
            builderMethodName = "createPaymentStatement", 
            builderClassName = "PaymentStatementBuilder",
            buildMethodName = "build")
    private static PaymentStatement validateAndCreatePaymentStatement(
            MonetaryAmount monetaryAmount, 
            String reason, 
            Account recipientAccount, 
            Account senderAccount) {
        Money moneyAmount = PaymentStatement.validateStatementInputData(
                monetaryAmount, 
                reason, 
                recipientAccount, 
                senderAccount);
        
        return new PaymentStatement(
                moneyAmount, reason, recipientAccount, senderAccount);
    }
}
