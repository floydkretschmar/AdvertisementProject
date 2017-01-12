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
import de.oth.fkretschmar.advertisementproject.entities.user.Address;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import de.oth.fkretschmar.advertisementproject.ui.AccountType;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
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
public class UserProfileModel implements Serializable {

    // --------------- Private fields ---------------
        
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;
    
    /**
     * Stores the copy of the current user that is used to store and potentially
     * revert changes.
     */
    @Getter
    @Setter
    private User currentUserCopy;
    
    /**
     * Stores the value indicating whether or not the user profile is in editing
     * mode.
     */
    @Getter
    @Setter
    private boolean editing;
    
    /**
     * Stores the repository used to manage {@link User} entities.
     */
    @Inject
    private IUserService userService;

    // --------------- Public getters and setters ---------------

    
    /**
     * Gets all account types that the application can handel.
     * 
     * @return  the list of valid account types. 
     */
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
    
    
    /**
     * Gets all the account of the user in an ordered manner.
     * 
     * @return  the collection of accounts.
     */
    public Collection<Account> getAccounts() {
        return this.currentUserCopy.getAccounts()
                .stream()
                .sorted((acc1, acc2) -> 
                {
                   if(acc1 instanceof BankAccount) {
                       return ((BankAccount)acc1).getIban().compareTo(((BankAccount)acc2).getIban());
                   }
                   else {
                       return ((PayPalAccount)acc1).getName().compareTo(((PayPalAccount)acc2).getName());
                   }
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the birthday of the user as a date.
     * 
     * @return 
     */
    public Date getBirthdate() {
        return Date.from(this.currentUserCopy.getBirthdate().atStartOfDay(
                ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * Sets the birthday of the user as a date.
     * @param date 
     */
    public void setBirthdate(Date date) {
        this.currentUserCopy.setBirthdate(date.toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate());
    }

    // --------------- Private fields ---------------
    
    
    /**
     * Adds a newly added account to the list of accounts of the copied user.
     * 
     * @param   entity  the account that will be added.
     */
    public void addNewAccount(Object entity) {
        this.currentUserCopy.addAccount((Account)entity);
    }
    
    
    /**
     * Initialize the model by creating an exact copy of the current user and
     * setting the editing mode to false.
     */
    @PostConstruct
    public void initialize() {
        this.currentUserCopy = this.applicationModel.processCurrentUser(user -> 
        {
            User copy = User.createUser()
                    .address(Address.createAddress()
                                .areaCode(user.getAddress().getAreaCode())
                                .city(user.getAddress().getCity())
                                .country(user.getAddress().getCountry())
                                .houseNumber(user.getAddress().getHouseNumber())
                                .street(user.getAddress().getStreet()).build())
                    .birthdate(user.getBirthdate())
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
    
    
    /**
     * Removes a newly created account from the copy of the user.
     * 
     * @param element   the account that will be deleted.
     */
    public void removeAccount(Object element) {
        this.currentUserCopy.removeAccount((Account)element);
    }
    
    
    /**
     * Applies the changes stored in the copied user to the actual current user.
     * 
     * @return the next navigation point.
     */
    public String saveChanges() {
        this.applicationModel.processAndChangeCurrentUser(user -> 
        {
            return this.userService.changeUserBasicInformation(user, this.currentUserCopy);
        });
        this.editing = false;
        return "userProfile";
    }
}
