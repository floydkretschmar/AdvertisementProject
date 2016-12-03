/*
 * Copyright (C) 2016 fkre
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
package de.oth.fkretschmar.advertisementproject.entities;

import java.io.Serializable;
import java.net.URL;
import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author fkre
 */
@Entity(name = "T_CAMPAIGN_CONTENT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CampaignContent extends Content {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the target context of the specified content that influinces the
     * price.
     */
    @NotNull
    @OneToOne
    @Getter
    private TargetContext context;
    
    /**
     * Stores the number of contents that have been ordered for the target 
     * context.
     */
    @NotNull
    @Column(name = "NUMER_OF_REQUESTS", nullable = false)
    @Getter
    private long numberOfRequests;
    
    /**
     * Stores the monetary amount tbe creator of the content is willing to pay
     * per request of this campaign content.
     **/
    @NotNull
    private Money pricePerRequest;
    
    
    // --------------- Public getters and setters ---------------

    /**
     * Gets the monetary amount tbe creator of the content is willing to pay
     * per request of this campaign content.
     * 
     * @return  the {@link MonetaryAmount} object that represents the amount.
     */
    public MonetaryAmount getPricePerRequest() {
        return this.pricePerRequest.getValue();
    }
    
    
    /**
     * Sets the monetary amount tbe creator of the content is willing to pay
     * per request of this campaign content.
     * 
     * @param   monetaryAmount   the amount.
     */
    public void setPricePerRequest(MonetaryAmount monetaryAmount) {
        this.pricePerRequest.setValue(monetaryAmount);
    }
    
    
    // --------------- Private constructors ---------------
    
    
    /**
     * Creates a new instance of {@link Content} using the specified 
     * content, value type and target url.
     * 
     * @param   contentType     the enum that indicates the actual type of the 
     *                          object.
     * @param   description     the text that gives a short description of the
     *                          content.
     * @param   targetUrl       the URL that redirects to the advertised page.
     * @param   value           the actual value of the content.
     * @param   numberOfRequests    the number of contents that have been 
     *                              ordered for the target context.
     * @param   context         the target context of the specified order row 
     *                          that influinces the price.
     * @param   pricePerRequest the monetary amount tbe creator of the value 
                          is willing to pay per request of this campaign 
     */
    private CampaignContent(
            ContentType contentType, 
            String description, 
            URL targetUrl,
            Serializable value,
            TargetContext context,
            long numberOfRequests,
            Money pricePerRequest) {
        super(contentType, description, targetUrl, value);
        this.context = context;
        this.numberOfRequests = numberOfRequests;
        this.pricePerRequest = pricePerRequest;
    }
    
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link CampaignContent}.
     * 
     * @param   content         the content that will be wrapped.
     * @param   numberOfRequests    the number of contents that have been 
     *                              ordered for the target context.
     * @param   context         the target context of the specified order row 
     *                          that influinces the price.
     * @param   pricePerRequest the monetary amount tbe creator of the value 
                          is willing to pay per request of this campaign 
                          value.
     * @return  the built {@link CampaignContent}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createCampaignContent", 
            builderClassName = "CampaignContentBuilder",
            buildMethodName = "build")
    private static CampaignContent validateAndCreateCampaignContent(
            Content content,
            TargetContext context,
            long numberOfRequests,
            MonetaryAmount pricePerRequest) throws BuilderValidationException {
        
        if (numberOfRequests <= 0) {
            throw new BuilderValidationException(
                    CampaignContent.class,
                    "The amount can not be smaller or equal to 0.");
        }
        
        if (context == null) {
            throw new BuilderValidationException(
                    CampaignContent.class,
                    "The target context can not be null.");
        }
        
        Money moneyAmount = Money.createMoney()
                .monetaryAmount(pricePerRequest).build();
        
        return new CampaignContent(
                content.getContentType(), 
                content.getDescription(),
                content.getTargetUrl(), 
                content.getValue(), 
                context, 
                numberOfRequests, 
                moneyAmount);
    }
}
