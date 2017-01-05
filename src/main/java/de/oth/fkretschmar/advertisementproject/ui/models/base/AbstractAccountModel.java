/*
 * Copyright (C) 2016 Floyd
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
package de.oth.fkretschmar.advertisementproject.ui.models.base;

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import javax.inject.Inject;
import de.oth.fkretschmar.advertisementproject.ui.models.ApplicationModel;
import java.util.Collection;

/**
 * The abstract base implementation of an ui model.
 * 
 * @author Floyd
 */
public abstract class AbstractAccountModel extends AbstractModel {
    
    // --------------- Public getters and setters ---------------
    
    /**
     * Gets the label for the specified account.
     * 
     * @param   account the account for which the label will be returned.
     * @return  the account label.
     */
    public String getAccountLabel(Account account) {
        if(account instanceof BankAccount)
            return ((BankAccount)account).getIban();
        else
            return ((PayPalAccount)account).getName();
    }
}
