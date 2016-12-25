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
package de.oth.fkretschmar.advertisementproject.business.services.base;

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;

/**
 *
 * @author fkre
 */
public interface IAccountService extends IEntityService<String, Account> {

    // --------------- Public methods ---------------
    
    
    /**
     * Creates the specified {@link Account}.
     * 
     * @param   account    that will be saved.
     */
    public void createAccount(Account account);
    
    
    /**
     * Deletes the specified {@link Account} from the database.
     * 
     * @param   account    that will be deleted.
     */
    public void deleteAccount(Account account);
}
