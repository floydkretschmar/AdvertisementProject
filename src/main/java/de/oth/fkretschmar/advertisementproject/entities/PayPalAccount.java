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

import javax.persistence.Entity;

/**
 * Represents a specific paypal account.
 * 
 * @author fkre
 */
@Entity(name = "T_PAYPAL_ACCOUNT")
public class PayPalAccount extends Account {
    
    // --------------- Protected constructors ---------------

    
    /**
     * Creates a new instance of {@link PayPalAccount} using the e-mail address.
     * 
     * @param   eMailAddress    that is used to identify an paypal account.
     */
    protected PayPalAccount(String eMailAddress) {
        super(eMailAddress);
    }
    
    // --------------- Public getters and setters ---------------

    /**
     * Gets the the text used to identify an account when interacting with
     * the external system.
     * 
     * @return  the text that identifies an account.
     */
    public String getEMailAddress() {
        return this.getId();
    }
    
    // --------------- Public static methods ---------------

    
    /**
     * Creates a new instance of {@link PayPalAccount} using the specified 
     * {@link PayPalAccountBuilder}.
     * 
     * @param   eMailAddress    the email address that uniquely identifies the
     *                          paypal account that is being built.
     * @return  the address builder to create the {@link PayPalAccount} with.
     */
    public static PayPalAccountBuilder create(String eMailAddress) {
        return PayPalAccountBuilder.create(eMailAddress);
    }
}