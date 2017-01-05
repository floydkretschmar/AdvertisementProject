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
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.Collection;
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
public class UserProfileModel extends AbstractModel {

    // --------------- Private fields ---------------
    
    @Getter
    @Setter
    private Collection<Account> accounts;
    
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
    
    @Getter
    @Setter
    private Account newAccount;
    
    /**
     * Stores the repository used to manage {@link User} entities.
     */
    @Inject
    private IUserService userService;

    
    public void addNewAccount() {
        this.currentUserCopy.addAccount(this.newAccount);
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
    
    public String saveChanges() {
        this.applicationModel.processAndChangeCurrentUser(user -> 
        {
            return this.userService.changeUserBasicInformation(user, this.currentUserCopy);
        });
        this.editing = false;
        return "userProfile";
    }
}
