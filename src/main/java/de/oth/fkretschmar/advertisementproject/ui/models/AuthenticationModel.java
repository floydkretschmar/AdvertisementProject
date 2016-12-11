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
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.Arrays;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Floyd
 */
@Named
@RequestScoped
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
     * Stores the user password.
     */
    @Getter
    @Setter
    private String password;
    
    // --------------- Public methods ---------------
    
    
    /**
     * Performs the login for the specified e-mail address and password.
     * 
     * @return the target page to navigate to after authentication.
     */
    public String authenticate() {
        try {
            boolean authenticated = this.applicationService.authenticateUser(
                    this.eMailAddress, this.password.toCharArray());
            
            if (authenticated) {
                this.error = false;
                return "overview.xhtml";
            }
            else {
                this.errorMessage = AuthenticationModel.AUTHENTICATION_FAILED;
                this.error = true;
            this.password = null;
                return "login.xhtml";
            }   
        } catch (PasswordException ex) {
            this.errorMessage = AuthenticationModel.UNEXPECTED_ERROR;
            this.password = null;
            this.error = true;
            return "login.xhtml";
        }
    }
}
