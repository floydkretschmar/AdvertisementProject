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
package de.oth.fkretschmar.advertisementproject.business.services;

import de.oth.fkretschmar.advertisementproject.business.repositories.UserRepository;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.io.Serializable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 * The service that offers functionality relatetd to the state of the
 * application.
 *
 * @author fkre Floyd Kretschmar
 */
@SessionScoped
public class ApplicationService implements Serializable {

    // --------------- Private static fields ---------------
    /**
     * Stores the {@link User} that is currently logged into the system.
     */
    private static User currentUser;

    /**
     * Stores the instance of {@link Lock} used to sychronize the class.
     */
    private static final Lock lock = new ReentrantLock();

    // --------------- Private fields ---------------
    /**
     * Stores the repository used to manage {@link User} entites.
     */
    @Inject
    UserRepository userRepository;

    // --------------- Private static getters ---------------
    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param processCallback The function used to process the current user.
     */
    public static void processCurrentUser(Function<User, User> processCallback) {
        ApplicationService.lock.lock();

        try {
            ApplicationService.currentUser
                    = processCallback.apply(ApplicationService.currentUser);

        } finally {
            ApplicationService.lock.unlock();
        }
    }

    // --------------- Public methods ---------------
    /**
     * Authenticates an user using the specified e-mail and password.
     *
     * @param eMail that identifies the user.
     * @param password that is used to authenticate the user.
     * @return {@code true} if the authentication was a success, otherwise
     * {@code false}.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    public boolean authenticateUser(
            String eMail, char[] password) throws PasswordException {
        User user = this.userRepository.find(eMail);

        try {
            ApplicationService.lock.lock();
            if (user == null || !PasswordService.equals(user.getPassword(), password)) {
                return false;
            } else {
                ApplicationService.currentUser = user;
                return true;
            }
        } finally {
            ApplicationService.lock.unlock();
        }
    }
}
