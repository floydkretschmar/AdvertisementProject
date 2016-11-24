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

/**
 *
 * @author fkre
 */
public class PaymentBuilder extends AbstractEntityBuilder<Long, Payment> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link PaymentBuilder}.
     * 
     * @param payment the payment that is being build.
     */
    protected PaymentBuilder(Payment payment) {
        super(payment);
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link PaymentBuilder}.
     * 
     * @return 
     */
    public static PaymentBuilder create() {
        return new PaymentBuilder(new Payment());
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Creates the {@link Payment} using the specified amount.
     * 
     * @param   amount      the payment amount in the smallest possible unit of the 
     *                      currency.
     * @return  the builder used to build the {@link Payment}.
     */
    public PaymentBuilder withAmount(Long amount) {
        this.getEntity().setAmount(amount);
        return this;
    }
    
    
    /**
     * Creates the {@link Payment} using the specified currency.
     * 
     * @param   currency  the currency in which the payment will be payed.
     * @return  the builder used to build the {@link Payment}.
     */
    public PaymentBuilder withCurrency(PaymentCurrencyType currency) {
        this.getEntity().setCurrency(currency);
        return this;
    }
    
    
    /**
     * Creates the {@link Payment} using the specified reason.
     * 
     * @param   reason  the text that describes the reason for the payment.
     * @return  the builder used to build the {@link Payment}.
     */
    public PaymentBuilder withReason(String reason) {
        this.getEntity().setReason(reason);
        return this;
    }
    
    
    /**
     * Creates the {@link Payment} using the specified recipient.
     * 
     * @param   recipientAccount    the account of the recipient of the payment.
     * @return  the builder used to build the {@link Payment}.
     */
    public PaymentBuilder withRecipientAccount(Account recipientAccount) {
        this.getEntity().setRecipientAccount(recipientAccount);
        return this;
    }
    
    
    /**
     * Creates the {@link Payment} using the specified sender.
     * 
     * @param   senderAccount   the account of the sender of the payment.
     * @return  the builder used to build the {@link Payment}.
     */
    public PaymentBuilder withSenderAccount(Account senderAccount) {
        this.getEntity().setSenderAccount(senderAccount);
        return this;
    }
    
    // --------------- Protected methods ---------------

    
    /**
     * Validates the {@link Payment} and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the address that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(Payment entity) 
            throws EntityBuilderValidationException {
        if(entity.getCurrency() == null)
            throw new EntityBuilderValidationException(
                    PaymentBuilder.class,
                    "The currency type can not be null.");
        
        if(entity.getReason() == null || entity.getReason().isEmpty())
            throw new EntityBuilderValidationException(
                    PaymentBuilder.class,
                    "The reason can not be null or empty.");
        
        if(entity.getRecipientAccount() == null)
            throw new EntityBuilderValidationException(
                    PaymentBuilder.class,
                    "The recipient account can not be null.");
        
        if(entity.getSenderAccount() == null)
            throw new EntityBuilderValidationException(
                    PaymentBuilder.class,
                    "The sender account can not be null.");
    }
}
