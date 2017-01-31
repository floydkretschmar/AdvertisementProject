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

import de.oth.fkretschmar.advertisementproject.ui.models.VolatileUserModel;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.business.services.UserServiceException;
import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Floyd
 */
@Named
@SessionScoped
public class ApplicationModel implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores the id of the {@link User} that is currently logged into the
     * system.
     */
    private String currentUserId;

    /**
     * Stores the registry of all logged on users.
     */
    @Inject
    private VolatileUserModel registry;

    /**
     * Stores the repository used to manage {@link User} entities.
     */
    @Inject
    private IUserService userService;

    // --------------- Public methods ---------------
    /**
     * Authenticates an user using the specified e-mail and password.
     *
     * @param eMail that identifies the user.
     * @param password that is used to authenticate the user.
     * @return {@code true} if the authentification was successfull, otherwise
     * {@code false}.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    public boolean authenticateUser(String eMail, String password) {
        try {
            User user = this.userService.authenticateUser(eMail, password.toCharArray());
            this.currentUserId = user.getId();
            return true;
        } catch (UserServiceException ex) {
            return false;
        }
    }

    /**
     * Gets the value indicating whether or not a user is logged into the
     * application.
     *
     * @return {@code true} if a user is logged in, otherwise {@code false}
     */
    public boolean isUserAuthenticated() {
        return this.currentUserId != null;
    }

    /**
     * Logs out the user from the application and redirects to the login page.
     *
     * @return the navigation point for the login page.
     */
    public String logOut() {
        // cleanup: invalidate session as well as remove the user from the 
        // registry
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }

    /**
     * Allows processing and changing of the {@link User} that is currently
     * logged into the system.
     *
     * @param processCallback The function used to process the current user.
     * @param reloadData that indicates whether or not the data of the user
     * should be loaded after the change.
     */
    public void changeCurrentUser(Consumer<User> processCallback, boolean reloadData) {
        this.registry.changeUser(this.currentUserId, processCallback, reloadData);
    }

    /**
     * Allows data retrieval from the {@link User} that is currently
     * logged into the system.
     *
     * @param <T> the type of the return value.
     * @param processCallback The function used to process the current user.
     * @return the return value of the processing.
     */
    public <T> T retrieveDataFromCurrentUser(Function<User, T> processCallback) {
        return this.registry.retrieveDataFromUser(
                this.currentUserId, processCallback);
    }

    /**
     * Returns the navigation point for the login and register page if a user
     * has already logged in.
     * @return the nativgation point.
     */
    public String redirectFirstPage() {
        if (this.currentUserId != null) {
            return "overview";
        }

        return null;
    }
    
    /**
     * Redirects the user to the login page if the session has expired.
     * @return the navigation point.
     */
    public String redirectSessionExpired() {
        if (this.currentUserId == null) {
            return "login";
        }
        
        return null;
    }
}
