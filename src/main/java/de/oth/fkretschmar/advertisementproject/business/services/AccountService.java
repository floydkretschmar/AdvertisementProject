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

import de.oth.fkretschmar.advertisementproject.business.repositories.AccountRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.UserRepository;
import de.oth.fkretschmar.advertisementproject.business.services.base.IAccountService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author fkre
 */
@RequestScoped
@Default
public class AccountService implements Serializable, IAccountService {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private AccountRepository accountRepository;

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
     * @param user to which a new account will be added.
     * @param account that will be added to the user.
     * @return the changed user.
     */
    @Transactional
    @Override
    public User createAccountForUser(User user, Account account) {
        if (user == null) {
            throw new IllegalArgumentException("The user was not set.");
        }

        this.accountRepository.persist(account);
        user = this.userRepository.merge(user);
        user.addAccount(account);
        return user;
    }

    
    /**
     * Deletes an {@link Account} from an already existing {@link User}.
     *
     * @param user from which the account will be deleted.
     * @param account that will be deleted.
     * @return the changed user.
     */
    @Transactional
    @Override
    public User deleteAccountFromUser(User user, Account account) {
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
     * Finds the account that is being identified by the id.
     * 
     * @param       idAsString  the id that defines the entity in text form.
     * @return      the account.
     */
    @Override
    public Account find(String idAsString) {
        return this.accountRepository.find(idAsString);
    }

}
