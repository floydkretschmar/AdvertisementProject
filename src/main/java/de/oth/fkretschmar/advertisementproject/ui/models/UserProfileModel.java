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

import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import de.oth.fkretschmar.advertisementproject.ui.AccountType;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Admin
 */
@Named
@ViewScoped
public class UserProfileModel extends AccountModel {

    // --------------- Private fields ---------------
        
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;

    @Getter
    @Setter
    private User currentUserCopy;
    
    @Getter
    @Setter
    private boolean editing;
    
    /**
     * Stores the repository used to manage {@link User} entities.
     */
    @Inject
    private IUserService userService;

    
    public Collection<String> getAccountTypes() {
        ArrayList<String> accounts = new ArrayList<String>();
        
        for (Account account : this.currentUserCopy.getAccounts()) {
            if  (account instanceof BankAccount && !accounts.contains(AccountType.BANK_ACCOUNT))
                accounts.add(AccountType.BANK_ACCOUNT);
            else if (account instanceof PayPalAccount && !accounts.contains(AccountType.PAYPAL_ACCOUNT))
                accounts.add(AccountType.PAYPAL_ACCOUNT);
        }
        
        return accounts.stream().sorted().collect(Collectors.toList());
    }
    
    public void addNewAccount(Object entity) {
        this.currentUserCopy.addAccount((Account)entity);
    }
    
    @PostConstruct
    public void initialize() {
        this.currentUserCopy = this.applicationModel.processCurrentUser(user -> 
        {
            User copy = User.createUser()
                    .address(user.getAddress())
                    .company(user.getCompany())
                    .eMailAddress(user.geteMailAddress())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .password(user.getPassword())
                    .build();
            user.getAccounts().forEach(account -> copy.addAccount(account));
            user.getCampaigns().forEach(campaign -> copy.addCampaign(campaign));
            return copy;
        });
        this.editing = false;
    }
    
    public void removeAccount(Object element) {
        this.currentUserCopy.removeAccount((Account)element);
    }
    
    public String saveChanges() {
        this.applicationModel.processAndChangeCurrentUser(user -> 
        {
            return this.userService.changeUserBasicInformation(user, this.currentUserCopy);
        });
        this.editing = false;
        return "userProfile";
    }
}
