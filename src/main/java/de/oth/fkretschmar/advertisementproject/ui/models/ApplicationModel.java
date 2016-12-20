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
import de.oth.fkretschmar.advertisementproject.business.services.UserService;
import de.oth.fkretschmar.advertisementproject.business.services.UserServiceException;
import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Floyd
 */
@Named
@SessionScoped
public class ApplicationModel extends AbstractModel {

    // --------------- Private static fields ---------------

    /**
     * Stores the instance of {@link Lock} used to sychronize the class.
     */
    private static final Lock LOCK = new ReentrantLock();

    // --------------- Private fields ---------------
    
    /**
     * Stores the {@link User} that is currently logged into the system.
     */
    private User currentUser;
    
    /**
     * 
     */
    @Inject
    private IUserService userService;
    
    // --------------- Public methods ---------------
    
    
    /**
     * Authenticates an user using the specified e-mail and password.
     *
     * @param eMail that identifies the user.
     * @param password that is used to authenticate the user.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    public boolean authenticateUser(String eMail, String password) {
        ApplicationModel.LOCK.lock();
        
        try {
            User user = this.userService.authenticateUser(eMail, password.toCharArray());
            this.currentUser = user;
            return true;
        }
        catch (UserServiceException ex) {
            return false;
        } finally {
            ApplicationModel.LOCK.unlock();
        }
    }
    
    
    /**
     * Gets the value indicating whether or not a user is logged into the 
     * application.
     * 
     * @return  {@code true} if a user is logged in, otherwise {@code false}
     */
    public boolean isUserAuthenticated() {
        return this.currentUser != null;
    }
    
    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param processCallback The function used to process the current user.
     */
    public <T> T processCurrentUser(Function<User, T> processCallback) {
        ApplicationModel.LOCK.lock();

        try {
            return processCallback.apply(this.currentUser);

        } finally {
            ApplicationModel.LOCK.unlock();
        }
    }
    
    
    /**
     * Gets the value indicating whether or not a user has logged in.
     * 
     * @return  {@code true} if a user is logged in, otherwise {@code false}.
     */
    public String redirectFirstPage() {
        if(this.currentUser != null)
            return "overview";
        
        return null;
    }
}
