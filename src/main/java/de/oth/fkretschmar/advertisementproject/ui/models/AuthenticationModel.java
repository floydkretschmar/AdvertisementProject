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

import de.oth.fkretschmar.advertisementproject.business.services.ApplicationService;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordService;
import de.oth.fkretschmar.advertisementproject.business.services.UserService;
import de.oth.fkretschmar.advertisementproject.entities.user.Address;
import de.oth.fkretschmar.advertisementproject.entities.user.Password;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import de.oth.fkretschmar.advertisementproject.ui.models.base.NavigationPoint;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Floyd
 */
@Named
@ManagedBean
@ConversationScoped
public class AuthenticationModel extends AbstractModel {

    // --------------- Private static fields ---------------
    
    private static final String AUTHENTICATION_FAILED
            = "The given e-mail address and/or password are incorrect.";
    
    /**
     * Stores the error message for unexpected errors during login.
     */
    private static final String UNEXPECTED_ERROR 
            = "An unexpected error occurred. Please try again.";

    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationService applicationService;
    
    /**
     * Stores the area code of the region the user lives in.
     */
    @Getter
    @Setter
    private String areaCode;
    
    /**
     * Stores the city the user lives in.
     */
    @Getter
    @Setter
    private String city;
    
    /**
     * Stores the company the user works for.
     */
    @Getter
    @Setter
    private String company;
    
    /**
     * Stores the country the user lives in.
     */
    @Getter
    @Setter
    private String country;
    
    /**
     * Stores the e-mail address of the user, used to authenticate with the 
     * system.
     */
    @Getter
    @Setter
    private String eMailAddress;
    
    /**
     * Stores the value indicating if the login has failed.
     */
    @Getter
    @Setter
    private boolean error;
    
    /**
     * Stores the error message if the login has failed.
     */
    @Getter
    @Setter
    private String errorMessage;
    
    /**
     * Stores the first name of the user.
     */
    @Getter
    @Setter
    private String firstName;
    
    /**
     * Stores the house number of the user.
     */
    @Getter
    @Setter
    private String houseNumber;
    
    /**
     * Stores the last name of the user.
     */
    @Getter
    @Setter
    private String lastName;
    
    /**
     * Stores the user password.
     */
    @Getter
    @Setter
    private String password;
    
    /**
     * Stores the street the user lives in.
     */
    @Getter
    @Setter
    private String street;
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private UserService userService;
    
    // --------------- Public methods ---------------
    
    
    /**
     * Performs the login for the specified e-mail address and password.
     * 
     * @param   e   the action event.
     */
    public void onLoginPageLogin(ActionEvent e) {
        try {
            boolean authenticated = this.applicationService.authenticateUser(
                    this.eMailAddress, this.password.toCharArray());
            
            if (authenticated) {
                this.error = false;
                this.setNextNavigationPoint(NavigationPoint.OVERVIEW);
            }
            else {
                this.errorMessage = AuthenticationModel.AUTHENTICATION_FAILED;
                this.error = true;
                this.password = null;
                this.setNextNavigationPoint(NavigationPoint.LOGIN);
            }   
        } catch (PasswordException ex) {
            this.errorMessage = AuthenticationModel.UNEXPECTED_ERROR;
            this.password = null;
            this.error = true;
            this.setNextNavigationPoint(NavigationPoint.LOGIN);
        }
    }
    
    
    /**
     * Gets called when the registration button on the login page was selected.
     * 
     * @param   e   the action event.
     */
    public void onLoginPageRegister(ActionEvent e) {
        this.setNextNavigationPoint(NavigationPoint.REGISTER);
    }
    
    
    /**
     * Gets called when the cancel button on the registration page was selected.
     * 
     * @param   e   the action event.
     */
    public void onRegisterPageCancel(ActionEvent e) {
        this.setNextNavigationPoint(NavigationPoint.LOGIN);
    }
    
    
    /**
     * Registers a new user.
     * 
     * @param   e   the action event.
     */
    public void onRegisterPageRegister(ActionEvent e) {
        Address address = Address.createAddress()
                .areaCode(this.areaCode.trim())
                .city(this.city.trim())
                .country(this.country.trim())
                .street(String.format("%s %s", this.street.trim(), this.houseNumber.trim()))
                .build();
        
        final Password password = PasswordService.generate(this.password.toCharArray());
        
        User user = User.createUser()
                .address(address)
                .company(this.company.trim())
                .eMailAddress(this.eMailAddress.trim())
                .firstName(this.firstName.trim())
                .lastName(this.lastName.trim())
                .password(password).build();
        
        this.userService.createUser(user);
        
        this.areaCode = "";
        this.city = "";
        this.company = "";
        this.country = "";
        this.errorMessage = "";
        this.firstName = "";
        this.houseNumber = "";
        this.lastName = "";
        this.password = "";
        this.street = "";
            
        this.setNextNavigationPoint(NavigationPoint.LOGIN);
    }
    
    // --------------- Protected methods ---------------

    /**
     * Initializes the {@link AuthenticationModel}.
     */
    @Override
    protected void initializeCore() {
        this.setNextNavigationPoint(NavigationPoint.LOGIN);
    }
}
