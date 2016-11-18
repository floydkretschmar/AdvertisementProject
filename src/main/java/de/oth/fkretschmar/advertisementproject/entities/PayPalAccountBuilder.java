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
public class PayPalAccountBuilder  extends AbstractEntityBuilder<String, PayPalAccount> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link PayPalAccountBuilder} using the 
     * specified email address.
     * 
     * @param   eMailAddress    the email address that uniquely identifies the
     *                          paypal account that is being built.
     */
    private PayPalAccountBuilder(String eMailAddress) {
        super(new PayPalAccount(eMailAddress));
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link PayPalAccountBuilder} using the 
     * specified email address.
     * 
     * @param   eMailAddress    the email address that uniquely identifies the
     *                          paypal account that is being built.
     * @return 
     */
    public static PayPalAccountBuilder create(String eMailAddress) {
        return new PayPalAccountBuilder(eMailAddress);
    }
    
    // --------------- Protected methods ---------------

    
    /**
     * Validates the {@link PayPalAccount} and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the address that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(PayPalAccount entity) 
            throws EntityBuilderValidationException {
        if(entity.getEMailAddress() == null || entity.getEMailAddress().isEmpty())
            throw new EntityBuilderValidationException(
                    PayPalAccountBuilder.class,
                    "The email address can not be null or empty.");
    }
}
