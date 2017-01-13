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

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.campaign.PaymentInterval;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;
import de.oth.fkretschmar.advertisementproject.business.annotation.EnumBundle;
import de.oth.fkretschmar.advertisementproject.ui.converters.IConverter;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import de.oth.fkretschmar.advertisementproject.business.annotation.MessageBundle;
import java.util.ResourceBundle;

/**
 * 
 * 
 * @author Floyd
 */
@Named
@RequestScoped
public class ResourcePickerModel implements Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the resource bundle that contains the translation texts for 
     * enums.
     */
    @Inject
    @EnumBundle
    private transient ResourceBundle enums;
    
    // --------------- Public methods ---------------
    
    
    
    /**
     * Picks the correct label for the specified content format.
     * 
     * @param   element   the content format label.
     * @return  the formated content format label.
     */
    public String pickContentFormatLabel(Object element) {
        ContentFormat contentFormat = (ContentFormat)element;
        switch(contentFormat) {
            case HALF_PAGE:
                return this.enums.getString("CONTENTFORMAT_HalfPage");
            case LARGE_RECTANGLE:
                return this.enums.getString("CONTENTFORMAT_LargeRectangle");
            case LEADERBOARD:
                return this.enums.getString("CONTENTFORMAT_Leaderboard");
            case MEDIUM_RECTANGLE:
                return this.enums.getString("CONTENTFORMAT_MediumRectangle");
            default:
                return this.enums.getString("CONTENTFORMAT_WideSkyscraper");
        }
    }
    
    
    /**
     * Picks the correct label for the specified content type.
     * 
     * @param   element   the content type label.
     * @return  the formated content type label.
     */
    public String pickContentTypeLabel(Object element) {
        ContentType contentType = (ContentType)element;
        switch(contentType) {
            case IMAGE_URL:
                return this.enums.getString("CONTENTYPE_Image");
            case TEXT:
                return this.enums.getString("CONTENTYPE_Text");
            default:
                return "";
        }
    }
    
    
    /**
     * Picks the correct label for the specified payment interval.
     * 
     * @param   element   the payment interval label.
     * @return  the formated payment interval label.
     */
    public String pickPaymentIntervalLabel(Object element) {
        PaymentInterval contentType = (PaymentInterval)element;
        switch(contentType) {
            case MONTHLY:
                return this.enums.getString("PAYMENTINTERVAL_Monthly");
            case QUATERLY:
                return this.enums.getString("PAYMENTINTERVAL_Quaterly");
            default:
                return this.enums.getString("PAYMENTINTERVAL_Yearly");
        }
    }
    
    
    /**
     * Picks the correct label for the specified target age.
     * 
     * @param   element   the target age label.
     * @return  the formated target age label.
     */
    public String pickTargetAgeLabel(Object element) {
        TargetAge targetAge = (TargetAge)element;
        switch(targetAge) {
            case ADULTS:
                return this.enums.getString("TARGETAGE_Adults");
            case CHILDREN:
                return this.enums.getString("TARGETAGE_Children");
            case SENIORS:
                return this.enums.getString("TARGETAGE_Seniors");
            default:
                return this.enums.getString("TARGETAGE_Youth");
        }
    }
    
    
    /**
     * Picks the correct label for the specified target gender.
     * 
     * @param   element   the target gender label.
     * @return  the formated target gender label.
     */
    public String pickTargetGenderLabel(Object element) {
        TargetGender targetGender = (TargetGender)element;
        switch(targetGender) {
            case FEMALE:
                return this.enums.getString("TARGETGENDER_FEMALE");
            case MALE:
                return this.enums.getString("TARGETGENDER_MALE");
            default:
                return this.enums.getString("TARGETGENDER_OTHER");
        }
    }
    
    
    /**
     * Picks the correct label for the specified target marital status.
     * 
     * @param   element   the target marital status label.
     * @return  the formated target marital status label.
     */
    public String pickTargetMaritalStatusLabel(Object element) {
        TargetMaritalStatus targetMaritalStatus = (TargetMaritalStatus)element;
        switch(targetMaritalStatus) {
            case DIVORCED:
                return this.enums.getString("TARGETMARITALSTATUS_Divorced");
            case IN_RELATIONSHIP:
                return this.enums.getString("TARGETMARITALSTATUS_InRelationship");
            case MARRIED:
                return this.enums.getString("TARGETMARITALSTATUS_Married");
            case SINGLE:
                return this.enums.getString("TARGETMARITALSTATUS_Single");
            default:
                return this.enums.getString("TARGETMARITALSTATUS_Widowed");
        }
    }
    
    
    /**
     * Picks the correct label for the specified target purpose of use.
     * 
     * @param   element   the target purpose of use label.
     * @return  the formated target purpose of use label.
     */
    public String pickTargetPurposeOfUseLabel(Object element) {
        TargetPurposeOfUse targetPurposeOfUse = (TargetPurposeOfUse)element;
        switch(targetPurposeOfUse) {
            case BUSINESS:
                return this.enums.getString("TARGETPURPOSEOFUSE_Business");
            default:
                return this.enums.getString("TARGETPURPOSEOFUSE_Private");
        }
    }
}
