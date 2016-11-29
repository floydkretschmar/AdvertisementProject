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

import java.util.Date;

/**
 *
 * @author fkre
 */
public class RegularPaymentBuilder extends PaymentStatementBuilder {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link RegularPaymentBuilder}.
     */
    private RegularPaymentBuilder() {
        super(new RegularPayment());
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link RegularPaymentBuilder}.
     * 
     * @return 
     */
    public static RegularPaymentBuilder create() {
        return new RegularPaymentBuilder();
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Creates the {@link RegularPayment} using the specified end date.
     * 
     * @param   endDate     the end date of the regular payment.
     * @return  the builder used to build the {@link RegularPayment}.
     */
    public RegularPaymentBuilder withEndDate(Date endDate) {
        ((RegularPayment)this.getObject()).setEndDate(endDate);
        return this;
    }
    
    
    /**
     * Creates the {@link RegularPayment} using the specified interval.
     * 
     * @param   interval  the interval in which the payment will be made.
     * @return  the builder used to build the {@link RegularPayment}.
     */
    public RegularPaymentBuilder withPaymentInterval(PaymentInterval interval) {
        ((RegularPayment)this.getObject()).setInterval(interval);
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
        RegularPayment regularPayment = (RegularPayment)entity;
        
        if(regularPayment.getStartDate() == null)
            throw new EntityBuilderValidationException(
                    RegularPaymentBuilder.class,
                    "The start date can not be null.");
        
        if(regularPayment.getStartDate().compareTo(new Date()) < 0)
            throw new EntityBuilderValidationException(
                    RegularPaymentBuilder.class,
                    "The start date can not be earlier than the current day.");
            
        if(regularPayment.getEndDate() != null 
                && regularPayment.getEndDate().compareTo(new Date()) < 0)
            throw new EntityBuilderValidationException(
                    RegularPaymentBuilder.class,
                    "The end date can not be earlier than the current day.");
    }
}
