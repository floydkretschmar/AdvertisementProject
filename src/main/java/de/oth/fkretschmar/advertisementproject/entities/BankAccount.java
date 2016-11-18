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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fkre
 */
@Entity(name = "T_BANK_ACCOUNT")
public class BankAccount extends Account {

    
    // --------------- Private fields ---------------

    /**
     * Stores the BIC identifying the banking institution where the account is
     * registered.
     */
    @NotNull
    @Column(name = "BIC")
    private String bic;

    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link BankAccount}.
     */
    protected BankAccount() {
        super();
    }

    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link BankAccount} using the specified IBAN
     * and BIC.
     * 
     * @param   iban    that uniquely identifies a bank account.
     * @param   bic     that identifies a banking institution.
     */
    public BankAccount(String iban, String bic) {
        super(iban);
        this.bic = bic;
    }

    // --------------- Public getters and setters ---------------

    
    /**
     * Gets the BIC identifying the banking institution where the account is
     * registered.
     *
     * @return  the BIC of the account.
     */
    public String getBic() {
        return this.bic;
    }

    
    /**
     * Gets the IBAN identifying the account of the transaction.
     *
     * @return  the IBAN of the account.
     */
    public String getIban() {
        return this.getId();
    }
    
    // --------------- Static methods ---------------
    
    
    /**
     * Creates a new instance of {@link BankAccount} using the specified 
     * {@link BankAccountBuilder}.
     * 
     * @param   iban    the IBAN that uniquely identifies a bank account that is 
     *                  being built.
     * @param   bic     the BIC identifying the banking institution where the 
     *                  account that is being built is registered.
     * @return  the address builder to create the {@link BankAccount} with.
     */
    public static BankAccountBuilder create(String iban, String bic) {
        return BankAccountBuilder.create(iban, bic);
    }
}
