/*
 * Copyright (C) 2016 Floyd
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
package de.oth.fkretschmar.advertisementproject.ui.models;

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The abstract base implementation of an ui model.
 * 
 * @author Floyd
 */
@Named
@RequestScoped
public class AccountModel {
    
    // --------------- Public getters and setters ---------------
    
    /**
     * Gets the label for the specified account.
     * 
     * @param   account the account for which the label will be returned.
     * @return  the account label.
     */
    public String getAccountLabel(Account account) {
        if(account instanceof BankAccount)
            return ((BankAccount)account).getIban();
        else
            return ((PayPalAccount)account).getName();
    }
    
    
    /**
     * Gets the value indicating whether or not the specified account is a
     * bank account.
     * 
     * @param   account the account that will be tested.
     * @return  {@code true} if the account is a bank account, otherwise
     *          {@code false}
     */
    public boolean isBankAccount(Account account) {
        return account instanceof BankAccount;
    }
    
    
    /**
     * Gets the value indicating whether or not the specified account is a
     * PayPal account.
     * 
     * @param   account the account that will be tested.
     * @return  {@code true} if the account is a bank account, otherwise
     *          {@code false}
     */
    public boolean isPayPalAccount(Account account) {
        return account instanceof PayPalAccount;
    }
}
