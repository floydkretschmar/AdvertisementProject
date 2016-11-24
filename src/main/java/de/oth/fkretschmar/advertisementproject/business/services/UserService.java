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

import de.oth.fkretschmar.advertisementproject.business.repositories.AddressRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.AccountRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.UserRepository;
import de.oth.fkretschmar.advertisementproject.entities.Account;
import de.oth.fkretschmar.advertisementproject.entities.Address;
import de.oth.fkretschmar.advertisementproject.entities.Password;
import de.oth.fkretschmar.advertisementproject.entities.User;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality related to the management of 
 * {@link User} instances.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@RequestScoped
public class UserService implements Serializable {

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
     * Creates a new {@link Account} and links it to the already existing 
     * specified {@link User}.
     * 
     * @param   user        to which a new account will be added.
     * @param   account     that will be added to the user.
     * @return              the changed user.
     */
    public User addAccountToUser(User user, Account account) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        account = this.accountRepository.persist(account);
        user = this.userRepository.merge(user);
        user.addAccount(account);
        return user;
    }
    
    
    /**
     * Changes the {@link Password} of the specified {@link User}.
     * 
     * @param   user                    whose password will be changed.
     * @param   newPassword             that represents the new password in its
     *                                  unsafe form.
     * @return  the User whose password was changed.
     */
    @Transactional
    public User changePassword(User user, char[] newPassword){
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        Password newSafePassword = PasswordService.generate(newPassword);
        Password currentPassword = user.getPassword();
        
        this.passwordService.delete(currentPassword);
        user = this.userRepository.merge(user);
        user.setPassword(this.passwordService.create(newSafePassword));
        
        return user;
    }
    
    
    /**
     * Creates a new {@link User} using the data specified on the user object.
     *
     * @param   user    that contains the data for the new user that will be 
     *                  created.
     * @return          the saved user.
     */
    @Transactional
    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        final Address address = user.getAddress();
        user.setAddress(this.addressRepository.persist(address));

        final Password password = user.getPassword();
        user.setPassword(this.passwordService.create(password));
        
        user.addAccounts(this.accountRepository.persist(user.getAccounts()), true);

        // persist the user:
        return this.userRepository.persist(user);
    }
    
    
    /**
     * Removes the {@link User} and all its subsequent data.
     * 
     * @param   user    that will be deleted.
     */
    @Transactional
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        // remove address
        this.addressRepository.remove(user.getAddress());
        user.setAddress(null);
        
        // remove password
        this.passwordService.delete(user.getPassword());
        user.setPassword(null);
        
        // remove all accounts
        Object[] accounts = user.getAccounts().toArray();
        
        for (int i = 0; i < accounts.length; i++) {
            if(accounts[0] instanceof Account){
                user.removeAccount((Account)accounts[i]);
                this.accountRepository.remove((Account)accounts[i]);
            }
        }
        
        this.userRepository.remove(user);
    }
    
    
    /**
     * Finds an {@link User} using the unique e-mail address.
     * 
     * @param   eMailAddress    used to identify the user.
     * @return  the user with the specified e-mail address.
     */
    public User findForEMail(String eMailAddress) {
        return this.userRepository.find(eMailAddress);
    }

    
    /**
     * Deletes an {@link Account} from an already existing {@link User}.
     * 
     * @param   user    from which the account will be deleted.
     * @param   account that will be deleted.
     * @return          the changed user.
     */
    public User removeAccountFromUser(User user, Account account) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        user = this.userRepository.merge(user);
        user.removeAccount(account);
        this.accountRepository.remove(account);
        return user;
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
