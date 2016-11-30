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
public class PaymentPlanBuilder extends AbstractEntityBuilder<Long, PaymentPlan> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link PaymentPlanBuilder}.
     * 
     * @param payment the payment plan that is being build.
     */
    protected PaymentPlanBuilder(PaymentPlan payment) {
        super(payment);
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link PaymentPlanBuilder}.
     * 
     * @return  the builder used to build the {@link PaymentPlan}.
     */
    public static PaymentPlanBuilder create() {
        return new PaymentPlanBuilder(new PaymentPlan());
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Creates the {@link PaymentPlan} using the specified monetary amount.
     * 
     * @param   money   the amount of money that is being paid with the payment.
     * @return  the builder used to build the {@link PaymentPlan}.
     */
    public PaymentPlanBuilder withMonetaryAmount(MonetaryAmount money) {
        this.getObject().setMonetaryAmount(money);
        return this;
    }
    
    /**
     * Creates the {@link PaymentPlan} using the specified payment plan type.
     * 
     * @param   type  defines what type of payment plan is being used.
     * @return  the builder used to build the {@link PaymentPlan}.
     */
    public PaymentPlanBuilder withType(PaymentPlanType type) {
        this.getObject().setType(type);
        return this;
    }
    
    
    // --------------- Protected methods ---------------

    
    /**
     * Validates the {@link PaymentPlan} and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the address that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(PaymentPlan entity) 
            throws EntityBuilderValidationException {
        if(entity.getMonetaryAmount() == null)
            throw new EntityBuilderValidationException(
                    PaymentPlanBuilder.class,
                    "The monetary amount can not be null.");
        
        if(entity.getType() == PaymentPlanType.UNDEFINED) {
            throw new EntityBuilderValidationException(
                    PaymentPlanBuilder.class, 
                    "The type of the payment plan has to be defined.");
        }
    }
}
