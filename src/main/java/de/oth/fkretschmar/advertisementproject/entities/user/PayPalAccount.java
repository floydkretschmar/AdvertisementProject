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
package de.oth.fkretschmar.advertisementproject.entities.user;

import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a specific paypal account.
 * 
 * @author fkre
 */
@Entity(name = "T_PAYPAL_ACCOUNT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class PayPalAccount extends Account {
    
    // --------------- Private constructors ---------------

    
    /**
     * Creates a new instance of {@link PayPalAccount} using the e-mail address.
     * 
     * @param   eMailAddress    that is used to identify an paypal account.
     */
    private PayPalAccount(String eMailAddress) {
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
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link PayPalAccount}.
     * 
     * @param   eMailAddress    that is used to identify an paypal account.
     * @return  the built {@link PayPalAccount}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createPayPalAccount", 
            builderClassName = "PayPalAccountBuilder",
            buildMethodName = "build")
    private static PayPalAccount validateAndCreatePayPalAccount(
            String eMailAddress) throws BuilderValidationException {
        if(eMailAddress == null || eMailAddress.isEmpty())
            throw new BuilderValidationException(
                    PayPalAccount.class,
                    "The e-mail address can not be null or empty.");
        
        return new PayPalAccount(eMailAddress);
    }
}
