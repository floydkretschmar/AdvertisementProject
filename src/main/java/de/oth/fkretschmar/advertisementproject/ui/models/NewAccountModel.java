/*
 * Copyright (C) 2016 Admin
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

import de.oth.fkretschmar.advertisementproject.ui.resources.AccountType;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import java.io.Serializable;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Admin
 */
@Named
@ViewScoped
public class NewAccountModel implements Serializable  {

    // --------------- Private fields ---------------

    /**
     * Stores the BIC of the account that will be created.
     */
    @Getter
    @Setter
    private String bic;

    /**
     * Stores the IBAN of the account that will be created.
     */
    @Getter
    @Setter
    private String iban;

    /**
     * Stores the PayPal name of the account that will be created.
     */
    @Getter
    @Setter
    private String payPalName;

    /**
     * Stores the type of account that will be created.
     */
    @Getter
    @Setter
    private String selectedAccountType = AccountType.BANK_ACCOUNT;

    // --------------- Public getters ---------------
    
    
    /**
     * Gets the account that is being build.
     * 
     * @return  the fully created bank account or pay pal account.
     */
    public Account getAccount() {
        Account account = null;
        
        if(this.selectedAccountType.equals(AccountType.BANK_ACCOUNT)) {
            account = BankAccount.createBankAccount()
                    .bic(this.bic)
                    .iban(this.iban).build();
        }
        else {
            account = PayPalAccount.createPayPalAccount()
                    .name(this.payPalName).build();
        }
        
        this.bic = "";
        this.iban = "";
        this.payPalName = "";
        this.selectedAccountType = AccountType.BANK_ACCOUNT;
        
        return account;
    }
}
