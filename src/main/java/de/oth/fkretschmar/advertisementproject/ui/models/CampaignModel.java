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
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.CampaignState;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Admin
 */
@Named
@ViewScoped
public class CampaignModel implements Serializable  {

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
                .sorted((campaign1, campaign2) -> campaign1.getName().compareTo(campaign2.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Sets the campaign state of the campaigns that will be displayed.
     *
     * @param campaignState the campaign state.
     */
    public void setCampaignState(CampaignState campaignState) {
        this.campaignState = campaignState;
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
     * Formats the bill header item to a readable string.
     * 
     * @param   element   the bill header that will be formatted.
     * @return  the formated bill header.
     */
    public String formatBillHeaderItem(Object element) {
        LocalDateTime billHeader = (LocalDateTime)element;
        return billHeader.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }
    

    /**
     * Gets the headers to display bills for the specified campaign.
     *
     * @param campaign the campaign for which the campaign headers will be
     * extracted.
     * @return the extracted bill headers.
     */
    public Collection<LocalDateTime> getBillHeadersForCampaign(Campaign campaign) {
        Collection<LocalDateTime> billHeaders = new ArrayList<LocalDateTime>();

        for (Bill bill : campaign.getBills()) {
            billHeaders.add(bill.getGenerationDate());
        }

        return billHeaders;
    }

    /**
     * Gets the number of campaigns a user has that are in the specified state.
     *
     * @param campaignState the campaign state.
     * @return the number of campaigns.
     */
    public int getCampaignCountForState(CampaignState campaignState) {
        return this.applicationModel.processCurrentUser(
                user -> user.getCampaigns()
                        .stream()
                        .filter(campaign -> campaign.getCampaignState() == campaignState)
                        .collect(Collectors.toList()).size());
    }

    /**
     * Gets the types of contents that exist for a specified campaign.
     *
     * @param campaign the campaign for which the content types will be
     * extracted.
     * @return the collection of content types for all the specified contents.
     */
    public Collection<String> getContentTypesForCampaign(Campaign campaign) {
        List<String> contentTypes = campaign.getContents().stream()
                .sorted((content1, content2)
                        -> content1.getContentType().name().compareTo(content2.getContentType().name()))
                .map(content -> {
                    return content.getContentType().getLabel();
                })
                .distinct()
                .sorted((contentType1, contentType2) -> contentType1.compareTo(contentType2))
                .collect(Collectors.toList());

        return contentTypes;
    }

    /**
     * Retrieves the campaign state for the specified string representation.
     *
     * @param campaignStateName the string representation of the campaign state.
     * @return the campaign state.
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
