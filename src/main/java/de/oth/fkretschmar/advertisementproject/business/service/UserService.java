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

import de.oth.fkretschmar.advertisementproject.business.repository.AddressRepository;
import de.oth.fkretschmar.advertisementproject.business.repository.AccountRepository;
import de.oth.fkretschmar.advertisementproject.business.repository.UserRepository;
import de.oth.fkretschmar.advertisementproject.entity.Address;
import de.oth.fkretschmar.advertisementproject.entity.Password;
import de.oth.fkretschmar.advertisementproject.entity.User;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality related to user management.
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class UserService {

    // --------------- Private fields ---------------
    /**
     * Stores the repository used to manage <code>Address</code> entites.
     */
    @Inject
    private AddressRepository addressRepository;
    
    /**
     * Stores the repository used to manage <code>BankAccount</code> entities.Í
     */
    @Inject
    private AccountRepository accountRepository;
    
    /**
     * Stores the service that manages passwords.
     */
    @Inject
    private PasswordService passwordService;

    /**
     * Stores the repository used to manage <code>User</code> entites.
     */
    @Inject
    private UserRepository userRepository;

    // --------------- Public methods ---------------
    
    /**
     * Creates a new {@link User} using the data specified on the user object.
     *
     * @param user that contains the data for the new user that will be created.
     * @return
     * @throws UserServiceException
     */
    @Transactional
    public User create(User user) {
        if (this.userRepository.iseMailAlreadyInUse(user.geteMailAddress())) {
            throw new UserServiceException("The chosen e-mail is already in use");
        }

        // save data owned by user first:
        final Address address = user.getAddress();
        if (address == null) {
            throw new UserServiceException("The address of the user was not set");
        }

        user.setAddress(this.addressRepository.save(address));

        final Password password = user.getPassword();
        if (password == null) {
            throw new UserServiceException("The password of the user was not set");
        }

        user.setPassword(this.passwordService.save(password));
        
        user.addAccounts(this.accountRepository.save(user.getAccounts()), true);

        // save the user:
        return this.userRepository.save(user);
    }

    
    /**
     * Changes the password of the specified user.
     * 
     * @param   user                    whose password will be changed.
     * @param   newPassword             that represents the new password in its
     *                                  unsafe form.
     * @return  the User whose password was changed.
     * @throws  UserServiceException    if the user is null, or the validation
     *                                  password does not match the password of
     *                                  the current user, or the confirmation
     *                                  password does not match the new password.
     */
    @Transactional
    public User changePassword(
            User user,
            char[] newPassword)
            throws UserServiceException {
        if (user == null) {
            throw new UserServiceException("The password change failed: "
                    + "the user was not set.");
        }

        // Create a safe version of the new password
        Password newSafePassword = this.passwordService.create(newPassword);
        Password currentPassword = user.getPassword();

        // remove the old password
        this.passwordService.delete(currentPassword);

        // save the new password
        user.setPassword(this.passwordService.save(newSafePassword));

        // save the user
        user = this.userRepository.update(user);
        return user;
    }
    
    
    /**
     * Validates that the specified password is valid for the specified user.
     * 
     * @param user                  whose password will be checked against.
     * @param validationPassword    that reprents the password that will be 
     *                              checked against the password of the user.
     * @return 
     */
    public boolean validatePassword(User user, char[] validationPassword) {
        Password currentPassword = user.getPassword();
        return PasswordService.equals(currentPassword, validationPassword);
    }
}
