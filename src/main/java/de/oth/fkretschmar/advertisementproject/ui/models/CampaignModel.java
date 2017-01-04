/*
 * Copyright (C) 2016 Admin
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

import de.oth.fkretschmar.advertisementproject.business.services.base.ICampaignService;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.CampaignState;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Admin
 */
@Named
@ViewScoped
public class CampaignModel extends AbstractModel {

    // --------------- Private fields ---------------
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;

    /**
     * Stores the service used to manage {@link Campaign} entites.
     */
    @Inject
    private ICampaignService campaignService;

    /**
     * Stores the state for which campaigns are being displayed by this model.
     */
    @Getter
    private CampaignState campaignState;

    // --------------- Public getter und setter ---------------
    /**
     * Gets all campaigns for the current user.
     *
     * @return the campaigns currently available for the user.
     */
    public Collection<Campaign> getCampaigns() {
        Collection<Campaign> campaigns = this.applicationModel.processCurrentUser(
                user -> user.getCampaigns());

        if (this.campaignState == null) {
            this.campaignState = CampaignState.RUNNING;
        }

        return campaigns.stream()
                .filter(campaign -> campaign.getCampaignState() == this.campaignState)
                .collect(Collectors.toList());
    }

    /**
     * Sets the campaign state of the campaigns that will be displayed.
     *
     * @param campaignStateName the name of the campaign state.
     */
    public void setCampaignState(String campaignStateName) {
        this.campaignState = this.getCampaignStateForName(campaignStateName);
    }

    // --------------- Public methods ---------------
    /**
     * Cancels the specified campaign.
     *
     * @param campaign that will be cancelled.
     * @return the next navigation point.
     */
    public String cancelCampaign(Campaign campaign) {
        this.applicationModel.processAndChangeCurrentUser(user
                -> {
            user.removeCampaign(campaign);
            user.addCampaign(this.campaignService.cancelCampaign(campaign));
            return user;
        });
        
        return "overview";
    }
    
    /**
     * Gets the number of campaigns a user has that are in the specified 
     * state.
     * 
     * @param campaignStateName the string representation of the campaign state.
     * @return  the number of campaigns.
     */
    public int getCampaignCountForState(String campaignStateName) {
        CampaignState state = this.getCampaignStateForName(campaignStateName);
        
        return this.applicationModel.processCurrentUser(
                        user -> user.getCampaigns()
                                .stream()
                                .filter(campaign -> campaign.getCampaignState() == state)
                                .collect(Collectors.toList()).size());
    }

    /**
     * Gets the contents for the specified campaign id.
     *
     * @param id the id of the campaign for which the contents
     * @return the collection of contents available for the specified campaign.
     */
    public Collection<Content> getContentForCampaignId(String id) {
        Optional<Campaign> selectedCampaign
                = this.applicationModel.processCurrentUser(
                        user -> user.getCampaigns()
                                .stream()
                                .filter(campaign -> campaign.getId() == Long.parseLong(id))
                                .findFirst());

        if (!selectedCampaign.isPresent()) {
            throw new IllegalArgumentException("The id does not belong to a "
                    + "valid campaign");
        }

        return selectedCampaign.get().getContents();
    }
    
    
    /**
     * Retrieves the campaign state for the specified string representation.
     * 
     * @param campaignStateName the string representation of the campaign state.
     * @return  the campaign state.
     */
    private CampaignState getCampaignStateForName(String campaignStateName) {
        if (campaignStateName.equalsIgnoreCase(CampaignState.RUNNING.name())) {
            return CampaignState.RUNNING;
        } else if (campaignStateName.equalsIgnoreCase(CampaignState.CANCELLED.name())) {
            return CampaignState.CANCELLED;
        } else {
            return CampaignState.ENDED;
        }
    }
}
