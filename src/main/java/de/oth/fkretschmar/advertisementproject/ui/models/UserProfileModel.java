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
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Admin
 */
@Named
@ViewScoped
public class UserProfileModel extends AbstractModel {

    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;

    // --------------- Public getters and setters ---------------
    
    public Collection<Account> getAccounts() {
        return this.applicationModel.processCurrentUser(
                user -> user.getAccounts());
    }
    
    public String getAreaCode() {
        return this.applicationModel.processCurrentUser(
                user -> user.getAddress().getAreaCode());
    }
    
    public String getCity() {
        return this.applicationModel.processCurrentUser(
                user -> user.getAddress().getCity());
    }
    
    public String getCompany() {
        return this.applicationModel.processCurrentUser(
                user -> user.getCompany());
    }
    
    public String getCountry() {
        return this.applicationModel.processCurrentUser(
                user -> user.getAddress().getCountry());
    }
    
    public String getFirstName() {
        return this.applicationModel.processCurrentUser(
                user -> user.getFirstName());
    }
    
    public String getLastName() {
        return this.applicationModel.processCurrentUser(
                user -> user.getLastName());
    }
    
    public String getStreet() {
        return this.applicationModel.processCurrentUser(
                user -> user.getAddress().getStreet());
    }
}
