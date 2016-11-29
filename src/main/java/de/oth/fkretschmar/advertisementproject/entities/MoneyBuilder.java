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
class MoneyBuilder extends AbstractBuilder<Money> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link MoneyBuilder}.
     * 
     * @param payment the payment that is being build.
     */
    MoneyBuilder(Money payment) {
        super(payment);
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Creates the {@link Money} using the specified reason.
     * 
     * @param   monetaryAmount   the amount of money that is being paid with the
     *                           payment.
     * @return  the builder used to build the {@link Money}.
     */
    public MoneyBuilder withMonetaryAmount(MonetaryAmount monetaryAmount) {
        this.getObject().setValue(monetaryAmount);
        return this;
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link MoneyBuilder}.
     * @return 
     */
    public static MoneyBuilder create() {
        return new MoneyBuilder(new Money());
    }
}
