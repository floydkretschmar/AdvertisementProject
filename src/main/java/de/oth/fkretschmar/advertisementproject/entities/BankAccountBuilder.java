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
public class BankAccountBuilder  extends AbstractEntityBuilder<String, BankAccount> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link BankAccountBuilder}.
     * 
     * @param   iban    the IBAN that uniquely identifies a bank account that is 
     *                  being built.
     * @param   bic     the BIC identifying the banking institution where the 
     *                  account that is being built is registered.
     */
    private BankAccountBuilder(String iban, String bic) {
        super(new BankAccount(iban, bic));
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link BankAccountBuilder}.
     * 
     * @param   iban    the IBAN that uniquely identifies a bank account that is 
     *                  being built.
     * @param   bic     the BIC identifying the banking institution where the 
     *                  account that is being built is registered.
     * @return 
     */
    public static BankAccountBuilder create(String iban, String bic) {
        return new BankAccountBuilder(iban, bic);
    }
    
    
    // --------------- Protected methods ---------------

    
    /**
     * Validates the {@link BankAccount} and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the address that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(BankAccount entity) 
            throws EntityBuilderValidationException {
        if(entity.getIban() == null || entity.getIban().isEmpty())
            throw new EntityBuilderValidationException(
                    BankAccountBuilder.class,
                    "The IBAN can not be null or empty.");
        
        if(entity.getBic()== null || entity.getBic().isEmpty())
            throw new EntityBuilderValidationException(
                    BankAccountBuilder.class,
                    "The BIC can not be null or empty.");
    }
}
