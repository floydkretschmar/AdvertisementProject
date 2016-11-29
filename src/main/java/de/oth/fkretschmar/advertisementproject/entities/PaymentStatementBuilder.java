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

import javax.money.MonetaryAmount;

/**
 *
 * @author fkre
 */
public class PaymentStatementBuilder extends AbstractEntityBuilder<Long, PaymentStatement> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link PaymentStatementBuilder}.
     * 
     * @param payment the payment that is being build.
     */
    protected PaymentStatementBuilder(PaymentStatement payment) {
        super(payment);
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link PaymentStatementBuilder}.
     * 
     * @param   money   the amount of money that is being paid with the payment.
     * @return 
     */
    public static PaymentStatementBuilder create(MonetaryAmount money) {
        return new PaymentStatementBuilder(new PaymentStatement(money));
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Creates the {@link PaymentStatement} using the specified reason.
     * 
     * @param   reason  the text that describes the reason for the payment.
     * @return  the builder used to build the {@link PaymentStatement}.
     */
    public PaymentStatementBuilder withReason(String reason) {
        this.getObject().setReason(reason);
        return this;
    }
    
    
    /**
     * Creates the {@link PaymentStatement} using the specified recipient.
     * 
     * @param   recipientAccount    the account of the recipient of the payment.
     * @return  the builder used to build the {@link PaymentStatement}.
     */
    public PaymentStatementBuilder withRecipientAccount(Account recipientAccount) {
        this.getObject().setRecipientAccount(recipientAccount);
        return this;
    }
    
    
    /**
     * Creates the {@link PaymentStatement} using the specified sender.
     * 
     * @param   senderAccount   the account of the sender of the payment.
     * @return  the builder used to build the {@link PaymentStatement}.
     */
    public PaymentStatementBuilder withSenderAccount(Account senderAccount) {
        this.getObject().setSenderAccount(senderAccount);
        return this;
    }
    
    // --------------- Protected methods ---------------

    
    /**
     * Validates the {@link PaymentStatement} and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the address that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(PaymentStatement entity) 
            throws EntityBuilderValidationException {
        if(entity.getMonetaryAmount() == null)
            throw new EntityBuilderValidationException(
                    PaymentStatementBuilder.class,
                    "The monetary amount can not be null.");
        
        if(entity.getReason() == null || entity.getReason().isEmpty())
            throw new EntityBuilderValidationException(
                    PaymentStatementBuilder.class,
                    "The reason can not be null or empty.");
        
        if(entity.getRecipientAccount() == null)
            throw new EntityBuilderValidationException(
                    PaymentStatementBuilder.class,
                    "The recipient account can not be null.");
        
        if(entity.getSenderAccount() == null)
            throw new EntityBuilderValidationException(
                    PaymentStatementBuilder.class,
                    "The sender account can not be null.");
    }
}
