/*
 * Copyright (C) 2016 fkre
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
package de.oth.fkretschmar.advertisementproject.business.service;

import de.oth.fkretschmar.advertisementproject.business.repository.UserRepository;
import de.oth.fkretschmar.advertisementproject.entity.User;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;

/**
 * The service that offers functionality relatetd to the state of the 
 * application.
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class ApplicationService {

    // --------------- Private static fields ---------------
    /**
     * Stores the <code>User</code> that is currently logged into the system.
     */
    private static User currentUser;

    /**
     * Stores the instance of <code>Lock</code> used to sychronize the class.
     */
    private static final Lock lock = new ReentrantLock();

    // --------------- Private fields ---------------
    /**
     * Stores the repository used to manage <code>User</code> entites.
     */
    @Inject
    UserRepository userRepository;

    // --------------- Private static getters ---------------
   
    
    /**
     * Provides thread safe processing of the <code>User</code> that is
     * currently logged into the system.
     * 
     * @param   processCallback    The method used to process the current user.
     */
    public static void processCurrentUser(Consumer<User> processCallback) {
        ApplicationService.lock.lock();

        try {
            processCallback.accept(ApplicationService.currentUser);

        } finally {
            ApplicationService.lock.unlock();
        }
    } 
            
    
    /**
     * Provides thread safe processing of the <code>User</code> that is
     * currently logged into the system.
     *
     * @param   <T>                 that defines the result type of the 
     *                              processing function.
     * @param   processCallback     The function used to process the 
     *                              current user.
     * @return  the process result of the function callback.
     */
    public static <T> T processCurrentUser(Function<User, T> processCallback) {
        ApplicationService.lock.lock();

        try {
            return processCallback.apply(ApplicationService.currentUser);

        } finally {
            ApplicationService.lock.unlock();
        }
    }

    // --------------- Public methods ---------------
    
    /**
     * Authenticates an user using the specified e-mail and password.
     *
     * @param eMail
     * @param password
     * @return 
     * @throws UserLoginFailedException
     */
    public boolean authenticateUser(String eMail, char[] password) {
//        try {
//            User user = this.userRepository.findForEmail(eMail);
//
//            if (user == null || !user.getPassword().equalsCore(password)) {
//                throw new UserLoginFailedException();
//            } else {
//                ApplicationService.lock.lock();
//                ApplicationService.currentUser = user;
//            }
//
//        } catch (HashingException ex) {
//            throw new UserLoginFailedException();
//        } finally {
//            ApplicationService.lock.unlock();
//        }
        try {
            User user = this.userRepository.findForEmail(eMail);

            if (user == null || !user.getPassword().equals(password)) {
                return false;
            } else {
                ApplicationService.lock.lock();
                ApplicationService.currentUser = user;
                return true;
            }
        } finally {
            ApplicationService.lock.unlock();
        }
    }
}
