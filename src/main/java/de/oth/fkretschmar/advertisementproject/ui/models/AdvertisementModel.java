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
package de.oth.fkretschmar.advertisementproject.ui.models;

import de.oth.fkretschmar.advertisementproject.business.services.web.ContentRequestParameters;
import de.oth.fkretschmar.advertisementproject.business.services.web.ContentRequestResult;
import de.oth.fkretschmar.advertisementproject.business.services.web.IContentProviderService;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import javax.faces.view.ViewScoped;

/**
 *
 *
 * @author Floyd
 */
@Named
@ViewScoped
public class AdvertisementModel implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores the advertisement content.
     */
    @Getter
    private ContentRequestResult content;

    /**
     * Stores the service used to manage requests for {@link Content} entites.
     */
    @Inject
    private IContentProviderService contentService;

    // --------------- Public methods ---------------
    /**
     * Requests a random content from the database for display on every page.
     */
    @PostConstruct
    private void initializeContent() {
        ContentRequestParameters params = new ContentRequestParameters();

        params.setSource("webvert");
        params.setFormat(ContentFormat.WIDE_SKYSCRAPER);
        params.setTargetAgeGroups(EnumSet.of(TargetAge.ADULTS));
        params.setTargetGenderGroups(EnumSet.allOf(TargetGender.class));
        params.setTargetMaritalStatusGroups(EnumSet.of(
                TargetMaritalStatus.DIVORCED,
                TargetMaritalStatus.SINGLE,
                TargetMaritalStatus.WIDOWED));
        params.setTargetPurposeOfUseGroups(EnumSet.of(
                TargetPurposeOfUse.BUSINESS));
        
        this.content = this.contentService.requestContent(params);
    }
}
