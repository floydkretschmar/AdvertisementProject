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
import de.oth.fkretschmar.advertisementproject.business.service.base.AbstractService;
import de.oth.fkretschmar.advertisementproject.entity.Account;
import de.oth.fkretschmar.advertisementproject.entity.Address;
import de.oth.fkretschmar.advertisementproject.entity.Password;
import de.oth.fkretschmar.advertisementproject.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality related to the management of {@link User}
 * instances.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@RequestScoped
public class UserService extends AbstractService {

    // --------------- Private fields ---------------
    /**
     * Stores the repository used to manage {@link Address} entites.
     */
    @Inject
    private AddressRepository addressRepository;
    
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private AccountRepository accountRepository;
    
    /**
     * Stores the service that manages {@link Password} entities.
     */
    @Inject
    private PasswordService passwordService;

    /**
     * Stores the repository used to manage {@link User} entites.
     */
    @Inject
    private UserRepository userRepository;

    // --------------- Public methods ---------------
    
    
    /**
     * Creates a new {@link Account} and links it to the specified {@link User}.
     * 
     * @param   user        to which a new account will be added.
     * @param   account     that will be added to the user.
     * @return              the changed user.
     */
    public User addAccountToUser(User user, Account account) {        
        account = this.accountRepository.save(account);
        user.addAccount(account);
        return this.userRepository.save(user);
    }
    
    
    /**
     * Changes the {@link Password} of the specified {@link User}.
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
        // Create a safe version of the new password
        Password newSafePassword = PasswordService.create(newPassword);
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
     * Removes the {@link User} and all its subsequent data.
     * 
     * @param   user    that will be deleted.
     */
    @Transactional
    public void delete(User user) {
        // delete address
        this.addressRepository.delete(user.getAddress());
        user.setAddress(null);
        
        // delete password
        this.passwordService.delete(user.getPassword());
        user.setPassword(null);
        
        // delete all accounts
        for (Account account : user.getAccounts()) {
            user.removeAccount(account);
            this.accountRepository.delete(account);
        }
        
        this.userRepository.delete(user);
    }
    
    /**
     * Finds an {@link User} using the unique e-mail address.
     * 
     * @param   eMailAddress    used to identify the user.
     * @return  the user with the specified e-mail address.
     */
    public User findForEMail(String eMailAddress) {
        return this.userRepository.findForEmail(eMailAddress);
    }

    /**
     * Deletes an {@link Account} from an {@link User}.
     * 
     * @param   user    from which the account will be deleted.
     * @param   account that will be deleted.
     * @return          the changed user.
     */
    public User removeAccountFromUser(User user, Account account) {
        if (user == null) {
            throw new UserServiceException("The password change failed: "
                    + "the user was not set.");
        }
        
        user.removeAccount(account);
        this.accountRepository.delete(account);
        return this.userRepository.save(user);
    }
    
    
    /**
     * Creates a new {@link User} using the data specified on the user object.
     *
     * @param   user    that contains the data for the new user that will be 
     *                  created.
     * @return          the saved user.
     * @throws  UserServiceException    if an error occurred during the saving
     *                                  of an user.
     */
    @Transactional
    public User save(User user) {
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
     * Validates that the specified password is valid for the specified 
     * {@link User}.
     * 
     * @param user                  whose password will be checked against.
     * @param validationPassword    that reprents the password that will be 
     *                              checked against the password of the user.
     * @return                      {@code true} if the password is valid for
     *                              the user, otherwise {@code false}.
     */
    public boolean validatePassword(User user, char[] validationPassword) {
        Password currentPassword = user.getPassword();
        return PasswordService.equals(currentPassword, validationPassword);
    }
}
