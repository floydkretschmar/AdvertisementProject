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
import de.oth.fkretschmar.advertisementproject.business.services.base.IAccountService;
import de.oth.fkretschmar.advertisementproject.business.services.base.IEntityService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author fkre
 */
@RequestScoped
public class AccountService 
        implements Serializable, IAccountService, IEntityService<String, Account> {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private AccountRepository accountRepository;


    // --------------- Public methods ---------------
    
    
    /**
     * Creates the specified {@link Account}.
     * 
     * @param   account    that will be saved.
     */
    @Transactional
    @Override
    public void createAccount(Account account) {
        this.accountRepository.persist(account);
    }
    
    
    /**
     * Deletes the specified {@link Account} from the database.
     * 
     * @param   account    that will be deleted.
     */
    @Transactional
    @Override
    public void deleteAccount(Account account) {
        this.accountRepository.remove(account);
    }
    
    
    /**
     * Finds the account that is being identified by the id.
     * @param   id  that identifies the account.
     * @return      the account.
     */
    @Override
    public Account find(String id) {
        return this.accountRepository.find(id);
    }

}
