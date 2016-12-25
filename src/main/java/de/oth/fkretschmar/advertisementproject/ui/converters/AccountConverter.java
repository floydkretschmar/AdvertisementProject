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
package de.oth.fkretschmar.advertisementproject.ui.converters;

import de.oth.fkretschmar.advertisementproject.business.services.base.IAccountService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author fkre
 */
@RequestScoped
public class AccountConverter extends AbstractEntityConverter<Account> {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private IAccountService accountService;

    // --------------- Protected methods ---------------

    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Account find(String id) {
        return this.accountService.find(id);
    }
}
