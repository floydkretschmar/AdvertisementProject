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
package de.oth.fkretschmar.advertisementproject.business.services.base;

import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.business.services.UserServiceException;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.user.Password;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.util.Collection;

/**
 *
 * @author Admin
 */
public interface IUserService extends IEntityService<User> {
    /**
     * Authenticates an user using the specified e-mail and password.
     *
     * @param eMail that identifies the user.
     * @param password that is used to authenticate the user.
     * @return {@code true} if the authentication was a success, otherwise
     * {@code false}.
     * @throws UserServiceException that indicates that the login failed.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    public User authenticateUser(
            String eMail, char[] password) throws UserServiceException;
    
    
    /**
     * Changes the {@link Password} of the specified {@link User}.
     * 
     * @param   user                    whose password will be changed.
     * @param   newPassword             that represents the new password in its
     *                                  unsafe form.
     * @return  the User whose password was changed.
     * @throws  PasswordException       that indicates an error during the 
     *                                  processing of passwords.
     */
    public User changePassword(
            User user, char[] newPassword);
    
    
    /**
     * Applies all changes of the specified changed user to the the {@link User}.
     * 
     * @param user          that will be changed.
     * @param changedUser   that contains all the changes that will be made to 
     *                      the user.
     * @return  the changed user.
     */
    public User changeUserBasicInformation(User user, User changedUser);
    
    
    /**
     * Creates a new {@link Account} and links it to the already existing 
     * specified {@link User}.
     * 
     * @param   user        to which a new account will be added.
     * @param   account     that will be added to the user.
     * @return              the changed user.
     */
    public User createAccountForUser(User user, Account account);
    
    
    /**
     * Creates a new {@link User} using the data specified on the user object.
     * 
     * @param   user    that contains the data for the new user that will be 
     *                  created.§
     * @throws  UserServiceException    if the creation of the user failed.
     */
    public void createUser(User user) throws UserServiceException;
    
    
    /**
     * Removes the {@link User} and all its subsequent data.
     * 
     * @param   user    that will be deleted.
     */
    public void deleteUser(User user);
    
    
//    /**
//     * Finds an {@link User} using the unique e-mail address.
//     * 
//     * @param   eMailAddress    used to identify the user.
//     * @return  the user with the specified e-mail address.
//     */
//    public User findUserForEMail(String eMailAddress);

    
    /**
     * Deletes an {@link Account} from an already existing {@link User}.
     * 
     * @param   user    from which the account will be deleted.
     * @param   account that will be deleted.
     * @return          the changed user.
     */
    public User deleteAccountFromUser(User user, Account account);
}
