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
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Admin
 */
@Named
@RequestScoped
public class CampaignModel extends AbstractModel {

    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;
    

    // --------------- Public getter and setter ---------------
    
    /**
     * Gets the contents for the specified campaign id.
     * 
     * @param id    the id of the campaign for which the contents
     * @param contentTypeName   the well formatted name that represents the 
     *                          content type.
     * @return 
     */
    public List<Content> getContentForCampaignId(String id, String contentTypeName) {
        Optional<Campaign> selectedCampaign = this.applicationModel.processCurrentUser(
                        user -> user.getCampaigns()
                                .stream()
                                .filter(campaign -> campaign.getId() == Long.parseLong(id))
                                .findFirst());
        
        if (!selectedCampaign.isPresent())
            throw new IllegalArgumentException("The id does not belong to a valid campaign");
        
        ContentType contentType = ContentType.getContentType(contentTypeName);
        
        return selectedCampaign.get().getContents()
                .stream()
                .filter(content -> content.getContentType() == contentType)
                .sorted((content1, content2) -> 
                        content1.getContentType().name().compareTo(content2.getContentType().name()))
                .collect(Collectors.toList());
    }
    
    
    /**
     * Gets the types of contents that exist for a specified campaign.
     * 
     * @param   id  the id of the campaign for which the content types will be
     *              returned.
     * @return 
     */
    public List<String> getContentTypesForCampaign(String id) {
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
                .map(content -> ContentType.getFormattedName(content.getContentType()))
                .distinct()
                .collect(Collectors.toList());
        
        return contentTypes;
    }
    
    /**
     * Gets all campaigns for the current user.
     * 
     * @return 
     */
    public List<Campaign> getCurrentUserCampaigns() {
        Collection<Campaign> campaigns = this.applicationModel.processCurrentUser(
                        user -> user.getCampaigns());
        
        return new ArrayList<Campaign>(campaigns);
    }
}
