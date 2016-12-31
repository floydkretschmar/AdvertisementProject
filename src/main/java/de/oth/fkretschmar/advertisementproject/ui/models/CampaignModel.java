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

import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.CampaignState;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.Collection;
import java.util.List;
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
     * Stores the state for which campaigns are being displayed by this model.
     */
    @Getter
    private CampaignState campaignState;
    

    // --------------- Public getter und setter ---------------
    
    
    /**
     * Gets all campaigns for the current user.
     * 
     * @return 
     */
    public Collection<Campaign> getCampaigns() {
        Collection<Campaign> campaigns = this.applicationModel.processCurrentUser(
                        user -> user.getCampaigns());
        
        if (this.campaignState == null)
            this.campaignState = CampaignState.RUNNING;
        
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
        if(campaignStateName.equalsIgnoreCase(CampaignState.RUNNING.name())) {
            this.campaignState = CampaignState.RUNNING;
        }
        else if (campaignStateName.equalsIgnoreCase(CampaignState.CANCELLED.name())) {
            this.campaignState = CampaignState.CANCELLED;
        }
        else if (campaignStateName.equalsIgnoreCase(CampaignState.ENDED.name())) {
            this.campaignState = CampaignState.ENDED;
        }
    }
    

    // --------------- Public methods ---------------
    
    /**
     * Gets the contents for the specified campaign id.
     * 
     * @param id    the id of the campaign for which the contents
     * @return 
     */
    public Collection<Content> getContentForCampaignId(String id) {
        Optional<Campaign> selectedCampaign = 
                this.applicationModel.processCurrentUser(
                        user -> user.getCampaigns()
                                .stream()
                                .filter(campaign -> campaign.getId() == Long.parseLong(id))
                                .findFirst());
        
        if (!selectedCampaign.isPresent())
            throw new IllegalArgumentException("The id does not belong to a "
                    + "valid campaign");
        
        return selectedCampaign.get().getContents();
    }
    
    
    /**
     * Gets the types of contents that exist for a specified campaign.
     * 
     * @param   id  the id of the campaign for which the content types will be
     *              returned.
     * @return 
     */
    public Collection<String> getContentTypesForCampaign(String id) {
        Optional<Campaign> selectedCampaign = this.applicationModel.processCurrentUser(
                        user -> user.getCampaigns()
                                .stream()
                                .filter(campaign -> campaign.getId() == Long.parseLong(id))
                                .findFirst());
        
        if (!selectedCampaign.isPresent())
            throw new IllegalArgumentException("The id does not belong to a valid campaign");
        
        List<String> contentTypes = selectedCampaign.get().getContents()
                .stream()
                .sorted((content1, content2) -> 
                        content1.getContentType().name().compareTo(content2.getContentType().name()))
                .map(content -> 
                {
                        String formattedName = ContentType.getFormattedName(content.getContentType());
                        if(formattedName.equals(ContentType.IMAGE_URL_FORMATTED_NAME))
                            return ContentType.IMAGE_FORMATTED_NAME;
                        return formattedName;
                })
                .distinct()
                .collect(Collectors.toList());
        
        return contentTypes;
    }
}
