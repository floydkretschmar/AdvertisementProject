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
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TextContentValue;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 *
 * @author Admin
 */
@Named
@ViewScoped
public class NewContentModel implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores the title of the text content that will be created.
     */
    @Getter
    @Setter
    private String contentTitle;

    /**
     * Stores the URL of the image content that will be created.
     */
    @Getter
    @Setter
    private String contentUrl;

    /**
     * Stores the description of the text content that will be created.
     */
    @Getter
    @Setter
    private String description;

    /**
     * Stores the name of the content given by the comissioner.
     */
    @Getter
    @Setter
    private String name;

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
     * Stores the format of the content that will be created.
     */
    @Getter
    @Setter
    private ContentFormat selectedFormat;

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

    /**
     * Stores the wizard model that is managing the creation wizard for the
     * current content.
     */
    @Inject
    private WizardModel wizardModel;

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
     * Gets the list of formats the content can have.
     *
     * @return the formats as a list.
     */
    public List<ContentFormat> getFormats() {
        return Arrays.asList(ContentFormat.values());
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

    // --------------- Public methods ---------------
    /**
     * Retrieves the content that is being built by the page.
     *
     * @return the newly created content.
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
            Serializable contentValue = null;

            if (this.selectedContentType == ContentType.IMAGE_URL) {
                contentValue = new URL(this.contentUrl);
            } else {
                contentValue = TextContentValue.createTextContentValue()
                        .description(this.description)
                        .title(this.contentTitle)
                        .build();
            }

            Content content = Content.createContent()
                    .name(this.name.trim())
                    .contentType(this.selectedContentType)
                    .context(TargetContext.createTargetContext()
                            .targetAges(ages)
                            .targetGenders(genders)
                            .targetMaritalStatus(status)
                            .targetPurposeOfUses(purposes).build())
                    .format(this.selectedFormat)
                    .numberOfRequests(this.numberOfRequests)
                    .targetUrl(new URL(this.targetPage))
                    .pricePerRequest(Money.ofMinor(
                            CurrencyUnit.EUR,
                            this.preDecimalPointAmount * 100 + this.postDecimalPointAmount))
                    .value(contentValue)
                    .build();
            this.resetModel();

            return content;
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    
    /**
     * Resets the Model to its original state.
     */
    public void resetModel() {
        this.name = "";
        this.contentTitle = "";
        this.contentUrl = "";
        this.description = "";
        this.numberOfRequests = 0;
        this.postDecimalPointAmount = 0;
        this.preDecimalPointAmount = 0;
        this.selectedAges.clear();
        this.selectedContentType = ContentType.IMAGE_URL;
        this.selectedFormat = ContentFormat.HALF_PAGE;
        this.selectedGenders.clear();
        this.selectedMaritalStatus.clear();
        this.selectedPurposesOfUse.clear();
        this.targetPage = "";
        this.wizardModel.resetWizard();
    }
}
