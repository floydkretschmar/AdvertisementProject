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
package de.oth.fkretschmar.advertisementproject.ui.models;

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.campaign.PaymentInterval;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author fkre
 */
@Named
@ViewScoped
public class NewCampaignModel extends AbstractModel {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;
    
    /**
     * Stores the account selected for the new campaign.
     */
    @Getter
    @Setter
    private Account selectedAccount;
    
    /**
     * Stores the payment interval selected for the new campaign.
     */
    @Getter
    @Setter
    private PaymentInterval selectedInterval;
    
    
    // --------------- Public getter und setter ---------------
    
    
    /**
     * Gets all accounts of the user that is currently logged in.
     * 
     * @return  the user accounts.
     */
    public Collection<Account> getAccounts() {
        return applicationModel.processCurrentUser(user -> user.getAccounts());
    }
    
    /**
     * Gets all possible payment intervals.
     * 
     * @return 
     */
    public PaymentInterval[] getPaymentIntervals() {
        return PaymentInterval.values();
    }
}
