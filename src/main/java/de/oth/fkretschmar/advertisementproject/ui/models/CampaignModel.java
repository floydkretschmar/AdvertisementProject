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
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
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
import lombok.Setter;

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
    @Setter
    private CampaignState campaignState;

    // --------------- Public getter und setter ---------------
    /**
     * Gets all campaigns for the current user.
     *
     * @return the campaigns currently available for the user.
     */
    public Collection<Campaign> getCampaigns() {
        Collection<Campaign> campaigns = this.applicationModel.retrieveDataFromCurrentUser(
                user -> user.getCampaigns());

        if (this.campaignState == null) {
            this.campaignState = CampaignState.RUNNING;
        }

        return campaigns.stream()
                .filter(campaign -> campaign.getCampaignState() == this.campaignState)
                .sorted((campaign1, campaign2) -> campaign1.getName().compareTo(campaign2.getName()))
                .collect(Collectors.toList());
    }

    // --------------- Public methods ---------------
    /**
     * Cancels the specified campaign.
     *
     * @param campaign that will be cancelled.
     * @return the next navigation point.
     */
    public String cancelCampaign(Campaign campaign) {
        this.applicationModel.changeCurrentUser(user
                -> this.campaignService.cancelCampaign(campaign), false);

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

        return billHeaders.stream()
                .sorted((bill1, bill2) -> bill2.compareTo(bill1))
                .collect(Collectors.toList());
    }

    /**
     * Gets the number of campaigns a user has that are in the specified state.
     *
     * @param campaignState the campaign state.
     * @return the number of campaigns.
     */
    public int getCampaignCountForState(CampaignState campaignState) {
        return this.applicationModel.retrieveDataFromCurrentUser(
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
    public Collection<ContentType> getContentTypesForCampaign(Campaign campaign) {
        List<ContentType> contentTypes = campaign.getContents().stream()
                .sorted((content1, content2)
                        -> content1.getContentType().name().compareTo(content2.getContentType().name()))
                .map((content) -> content.getContentType())
                .distinct()
                .collect(Collectors.toList());

        return contentTypes;
    }
}
