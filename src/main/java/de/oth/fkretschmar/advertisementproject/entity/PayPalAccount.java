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
package de.oth.fkretschmar.advertisementproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Represents a specific paypal account.
 * 
 * @author fkre
 */
@Entity(name = "T_PAYPAL_ACCOUNT")
public class PayPalAccount extends Account {

    // --------------- Private fields ---------------
    

    /**
     * Stores the e-mail identifying the account.
     */
    @NotNull
    @Column(name = "EMAIL", unique = true)
    private String eMailAddress;
    
    // --------------- Package-private constructors ---------------
    
    /**
     * Creates a new instance of {@link PayPalAccount}.
     */
    protected PayPalAccount() {
        super();
    }
    
    // --------------- Public constructors ---------------

    
    /**
     * Creates a new instance of {@link PayPalAccount} using the e-mail address.
     * 
     * @param   eMailAddress    that is used to identify an paypal account.
     */
    public PayPalAccount(String eMailAddress) {
        this();
        this.eMailAddress = eMailAddress;
    }
    
    // --------------- Public getters and setters ---------------

    /**
     * Gets the the text used to identify an account when interacting with
     * the external system.
     * 
     * @return  the text that identifies an account.
     */
    public String getEMailAddress() {
        return this.eMailAddress;
    }
}
