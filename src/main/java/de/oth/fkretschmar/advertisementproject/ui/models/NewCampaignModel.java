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
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.PaymentInterval;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author fkre
 */
@Named
@SessionScoped
public class NewCampaignModel extends AbstractModel {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;
    
    /**
     * Stores the new contents that are connected to the campaign created on the
     * page.
     */
    @Getter
    private Collection<Content> newContents = new ArrayList<Content>();
    
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
    
    
    // --------------- Public methods ---------------
    
    
    /**
     * Adds a newly created content to the content list of the campaign that
     * is being created.
     * 
     * @param   content     the content that will be added.
     * @return  the next navigation point.
     */
    public String addNewContent(Content content) {
        this.newContents.add(content);
        return "newCampaign";
    } 
    
    
    /**
     * Cancels the campaign creation and redirects to the campaign overview.
     * 
     * @return  the next navigation point.
     */
    public String cancel() {
        this.reset();
        return "overview";
    } 
    
    
    /**
     * Saves the campaign and redirects to the campaign overview.
     * 
     * @return  the next navigation point.
     */
    public String save() {
        this.reset();
        return "overview";
    } 
    
    
    // --------------- Private methods ---------------
    
    /**
     * Resets the new campaign model to its original state.
     */
    private void reset() {
        this.newContents.clear();
        this.selectedAccount = null;
        this.selectedInterval = null;
    }
}
