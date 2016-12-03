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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the specific implementation of {@link Account} to interact with 
 * banks.
 *
 * @author fkre
 */
@Entity(name = "T_BANK_ACCOUNT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class BankAccount extends Account {

    // --------------- Private fields ---------------

    /**
     * Stores the BIC identifying the banking institution where the account is
     * registered.
     */
    @NotNull
    @Column(name = "BIC")
    @Getter
    private String bic;

    // --------------- Private constructors ---------------
    
    /**
     * Creates a new instance of {@link BankAccount} using the specified IBAN
     * and BIC.
     * 
     * @param   iban    that uniquely identifies a bank account.
     * @param   bic     that identifies a banking institution.
     */
    private BankAccount(String iban, String bic) {
        super(iban);
        this.bic = bic;
    }

    // --------------- Public getters and setters ---------------

    
    /**
     * Gets the IBAN identifying the account of the transaction.
     *
     * @return  the IBAN of the account.
     */
    public String getIban() {
        return this.getId();
    }
    
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link BankAccount}.
     * 
     * @param   iban    the IBAN that uniquely identifies a bank account that is 
     *                  being built.
     * @param   bic     the BIC identifying the banking institution where the 
     *                  account that is being built is registered.
     * @return  the built {@link BankAccount}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createBankAccount", 
            builderClassName = "BankAccountBuilder",
            buildMethodName = "build") 
    private static BankAccount validateAndCreateBankAccount(
            String iban, 
            String bic) throws BuilderValidationException {
        if(iban == null || iban.isEmpty())
            throw new BuilderValidationException(
                    BankAccount.class,
                    "The IBAN can not be null or empty.");
        
        if(bic == null || bic.isEmpty())
            throw new BuilderValidationException(
                    BankAccount.class,
                    "The BIC can not be null or empty.");
        
        return new BankAccount(iban, bic);
    }
}
