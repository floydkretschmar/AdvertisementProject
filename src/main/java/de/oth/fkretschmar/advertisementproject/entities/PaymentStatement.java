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


/**
 * Represents a payment used to pay for an advertisement order.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@Entity(name = "T_PAYMENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
    private String reason;
    
    /**
     * Stores the account of the recipient of the payment.
     */
    @NotNull
    @OneToOne
    private Account recipientAccount;
    
    /**
     * Stores the account of the sender of the payment.
     */
    @NotNull
    @OneToOne
    private Account senderAccount;
    
    // --------------- Protected constructors ---------------
    
    /**
     * Creates a new instance of {@link PaymentStatement}.
     */
    protected PaymentStatement() {
        super();
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
     * Gets the reason for the payment.
     * 
     * @return  the reason for which the payment was authorized.
     */
    public String getReason() {
        return this.reason;
    }

    
    /**
     * Gets the account of the recipient of the payment.
     * 
     * @return  the account of the recipient.
     */
    public Account getRecipientAccount() {
        return this.recipientAccount;
    }
    

    /**
     * Gets the account of the sender of the payment.
     * 
     * @return the account of the sender.
     */
    public Account getSenderAccount() {
        return this.senderAccount;
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
    
    
    /**
     * Gets the reason for the payment.
     * 
     * @param   reason  for which the payment was authorized. 
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Sets the account of the recipient of the payment.
     * 
     * @param recipientAccount that will be set on the payment.
     */
    public void setRecipientAccount(Account recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    
    /**
     * Sets the account of the sender of the payment.
     * 
     * @param senderAccount that will be set on the payment.
     */
    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }
    
    
    // --------------- Public static methods ---------------
    
    
    /**
     * Creates a new instance of {@link PaymentStatement} using the specified 
     * {@link PaymentStatementBuilder}.
     * 
     * @return  the payment statement builder to create the 
     *          {@link PaymentStatement} with.
     */
    public static PaymentStatementBuilder create() {
        return PaymentStatementBuilder.create();
    }
}
