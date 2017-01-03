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

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Admin
 */
@Named
@ViewScoped
public class NewAccountModel extends AbstractModel {

    // --------------- Private static constants ---------------
    /**
     * Defines the display name of the bank account type.
     */
    private static final String BANK_ACCOUNT = "Bank acccount";

    /**
     * Defines the display name of the paypal account type.
     */
    private static final String PAYPAL_ACCOUNT = "PayPal acccount";

    // --------------- Private fields ---------------
    /**
     * Stores the account that is being build or being edited.
     */
    @Getter
    @Setter
    private Account account;

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
    private String selectedAccountType;

    // --------------- Public getters and setters ---------------
    /**
     * Gets the list of types the account can have.
     *
     * @return the account types as a list of strings.
     */
    public List<String> getAccountTypes() {
        ArrayList<String> types = new ArrayList<String>();

        types.add(NewAccountModel.BANK_ACCOUNT);
        types.add(NewAccountModel.PAYPAL_ACCOUNT);

        return types;
    }

    /**
     * Gets the value indicating if the selected account type is a bank account.
     *
     * @return {@code true} if the selected account is a bank account, otherwise
     * {@code false}.
     */
    public boolean isBankAccount() {
        return this.selectedAccountType.equals(NewAccountModel.BANK_ACCOUNT);
    }

    /**
     * Gets the value indicating if the selected account type is a paypal
     * account.
     *
     * @return {@code true} if the selected account is a paypal account,
     * otherwise {@code false}.
     */
    public boolean isPayPalAccount() {
        return this.selectedAccountType.equals(NewAccountModel.PAYPAL_ACCOUNT);
    }

    // --------------- Public getters ---------------
    
    
    /**
     * Applies all the changes defined in the account dialog and stores the
     * created account.
     *
     * @param event the arguments of the event.
     */
    public void applyChanges(ActionEvent event) {
        if(this.isBankAccount()) {
            this.account = BankAccount.createBankAccount()
                    .bic(this.bic)
                    .iban(this.iban).build();
        }
        else {
            this.account = PayPalAccount.createPayPalAccount()
                    .eMailAddress(this.payPalName).build();
        }
    }
}
