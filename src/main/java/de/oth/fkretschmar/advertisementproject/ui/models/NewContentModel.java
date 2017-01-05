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

import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Admin
 */
@Named
@RequestScoped
public class NewContentModel extends AbstractModel {

    // --------------- Private fields ---------------

    /**
     * Stores the value of the content that will be created.
     */
    @Getter
    @Setter
    private String contentValue;

    /**
     * Stores the description of the content that will be created.
     */
    @Getter
    @Setter
    private String description;

    /**
     * Stores the number of times the content that will be created can be
     * requested.
     */
    @Getter
    @Setter
    private long numberOfRequests;

    /**
     * Stores the pre decimal point amount of the monetary amount that a single
     * request for the content that will be created is worth.
     */
    @Getter
    @Setter
    private long preDecimalPointAmount;

    /**
     * Stores the post decimal point amount of the monetary amount that a single
     * request for the content that will be created is worth.
     */
    @Getter
    @Setter
    private long postDecimalPointAmount;

    /**
     * Stores the type of the content that will be created.
     */
    @Getter
    @Setter
    private ContentType selectedContentType = ContentType.IMAGE_URL;

    /**
     * Stores the age groups that will be targeted by the content that will be
     * created.
     */
    @Getter
    @Setter
    private List<String> selectedAges;

    /**
     * Stores the gender groups that will be targeted by the content that will
     * be created.
     */
    @Getter
    @Setter
    private List<String> selectedGenders;

    /**
     * Stores the marital status groups that will be targeted by the content
     * that will be created.
     */
    @Getter
    @Setter
    private List<String> selectedMaritalStatus;

    /**
     * Stores the purpose of use groups that will be targeted by the content
     * that will be created.
     */
    @Getter
    @Setter
    private List<String> selectedPurposesOfUse;

    /**
     * Stores the webpage to which the content that will be created, will
     * redirect when clicked.
     */
    @Getter
    @Setter
    private String targetPage;

    // --------------- Public getters and setters ---------------
    /**
     * Gets the list of types the content can have.
     *
     * @return the content types as a list.
     */
    public List<ContentType> getContentTypes() {
        ArrayList<ContentType> filteredTypes = new ArrayList<ContentType>();
        ContentType[] types = ContentType.values();

        for (int i = 0; i < types.length; i++) {
            if (types[i] != ContentType.UNDEFINED) {
                filteredTypes.add(types[i]);
            }
        }

        return filteredTypes;
    }

    /**
     * Gets the list of ages a content can target.
     *
     * @return the list of target ages.
     */
    public TargetAge[] getTargetAges() {
        return TargetAge.values();
    }

    /**
     * Gets the list of genders a content can target.
     *
     * @return the list of target genders.
     */
    public TargetGender[] getTargetGenders() {
        return TargetGender.values();
    }

    /**
     * Gets the list of maritals status a content can target.
     *
     * @return the list of target status.
     */
    public TargetMaritalStatus[] getTargetMaritalStatus() {
        return TargetMaritalStatus.values();
    }

    /**
     * Gets the list of purposes of use a content can target.
     *
     * @return the list of target purposes.
     */
    public TargetPurposeOfUse[] getTargetPurposesOfUse() {
        return TargetPurposeOfUse.values();
    }

    // --------------- Public getters ---------------
    
    /**
     * Retrieves the content that is being built by the page.
     * @return  the newly created content.
     */
    public Content getContent() {
        EnumSet<TargetAge> ages = EnumSet.noneOf(TargetAge.class);
        this.selectedAges.forEach(age -> ages.add(TargetAge.of(Integer.parseInt(age))));

        EnumSet<TargetGender> genders = EnumSet.noneOf(TargetGender.class);
        this.selectedGenders.forEach(gender -> genders.add(TargetGender.of(Integer.parseInt(gender))));

        EnumSet<TargetMaritalStatus> status = EnumSet.noneOf(TargetMaritalStatus.class);
        this.selectedMaritalStatus.forEach(singleStatus -> status.add(TargetMaritalStatus.of(Integer.parseInt(singleStatus))));

        EnumSet<TargetPurposeOfUse> purposes = EnumSet.noneOf(TargetPurposeOfUse.class);
        this.selectedPurposesOfUse.forEach(purpose -> purposes.add(TargetPurposeOfUse.of(Integer.parseInt(purpose))));

        try {
            return Content.createContent()
                    .contentType(this.selectedContentType)
                    .context(TargetContext.createTargetContext()
                            .targetAges(ages)
                            .targetGenders(genders)
                            .targetMaritalStatus(status)
                            .targetPurposeOfUses(purposes).build())
                    .description(this.description)
                    .numberOfRequests(this.numberOfRequests)
                    .targetUrl(new URL(this.targetPage))
                    .pricePerRequest(Money.ofMinor(
                            CurrencyUnit.EUR, 
                            this.preDecimalPointAmount * 100 + this.postDecimalPointAmount))
                    .value(this.selectedContentType == ContentType.IMAGE_URL ? new URL(this.contentValue) : this.contentValue)
                    .build();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
