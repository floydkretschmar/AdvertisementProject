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

import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.ui.ErrorMessages;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 *
 * NOTE: I am not using ViewScoped and ManagedBean because it is old technology.
 *       Instead I am using CDI beans with named and the JSF 2.2 ViewScoped 
 *       annotation.
 * 
 * @author Floyd Kretschmar
 */
@Named
@ViewScoped
public class LoginModel implements Serializable  {

    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;
    
    /**
     * Stores the e-mail address of the user, used to login with the system.
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
    
    
    
    
    // --------------- Public constructors ---------------

    /**
     * Creates a new instance of {@link LoginModel}.
     */
    public LoginModel() {
        this.password = "";
        this.eMailAddress = "";
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Performs the login for the specified e-mail address and password.
     * @return  the next navigation point.
     */
    public String login() {
        try {
            boolean authenticated = this.applicationModel.authenticateUser(
                    this.eMailAddress, this.password);
            
            if (authenticated) {
                this.error = false;
                return "overview";
            }
            else {
                this.errorMessage = ErrorMessages.LOGIN_AUTHENTICATION_FAILED;
                this.error = true;
                this.password = null;
                return null;
            }   
        } catch (PasswordException ex) {
            this.errorMessage = ErrorMessages.LOGIN_UNEXPECTED_ERROR;
            this.password = null;
            this.error = true;
            return null;
        }
    }
}
