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
import de.oth.fkretschmar.advertisementproject.business.services.UserServiceException;
import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.io.Serializable;
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
    private UserRegistry registry;

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
            this.registry.addUser(user);
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
        return this.registry.containsUser(this.currentUserId);
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
        this.registry.removeUser(this.currentUserId);
        return "login";
    }

    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param processCallback The function used to process the current user.
     */
    public void processAndChangeCurrentUser(Function<User, User> processCallback) {
        this.registry.processAndChangeUser(this.currentUserId, processCallback);
    }

    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param <T> the type of the return value.
     * @param processCallback The function used to process the current user.
     * @return the return value of the processing.
     */
    public <T> T processCurrentUser(Function<User, T> processCallback) {
        return this.registry.processCurrentUser(
                this.currentUserId, processCallback);
    }

    /**
     * Gets the value indicating whether or not a user has logged in.
     *
     * @return {@code true} if a user is logged in, otherwise {@code false}.
     */
    public String redirectFirstPage() {
        if (this.currentUserId != null && this.registry.containsUser(this.currentUserId)) {
            return "overview";
        }

        return null;
    }
}
