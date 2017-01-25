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

import de.oth.fkretschmar.advertisementproject.business.services.web.RequestContext;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;

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
        this.content = this.applicationModel.processCurrentUser(user -> {
            long ageInYears = ChronoUnit.YEARS.between(
                    user.getBirthdate(), LocalDate.now());
            RequestContext params = new RequestContext();

            // set age group according to the age of the user
            if (ageInYears < TargetAge.CHILDREN.getUpperAgeBoundy()) {
                params.setTargetAgeGroups(EnumSet.of(TargetAge.CHILDREN));
            } else if (ageInYears < TargetAge.YOUTH.getUpperAgeBoundy()) {
                params.setTargetAgeGroups(EnumSet.of(TargetAge.YOUTH));
            } else if (ageInYears < TargetAge.ADULTS.getUpperAgeBoundy()) {
                params.setTargetAgeGroups(EnumSet.of(TargetAge.ADULTS));
            } else {
                params.setTargetAgeGroups(EnumSet.of(TargetAge.SENIORS));
            }
            
            // Webvert is a application for business customers, so it is fair to
            // assume that you are at work when using it.
            params.setTargetPurposeOfUseGroups(EnumSet.of(
                    TargetPurposeOfUse.BUSINESS));

            ContentRequestResult receivedContent = this.contentService.requestContent(
                    "webvert",
                    ContentFormat.WIDE_SKYSCRAPER,
                    params);
            return receivedContent;
        });
    }
}
