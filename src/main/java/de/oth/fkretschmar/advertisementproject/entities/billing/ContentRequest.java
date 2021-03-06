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
package de.oth.fkretschmar.advertisementproject.entities.billing;

import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Floyd
 */
@Entity(name = "T_CONTENT_REQUEST")
@NamedQueries({
    @NamedQuery(
            name = ContentRequest.FIND_FOR_PAYMENT_INTERVAL, 
            query = " SELECT    contentRequest "
                    + " FROM    T_CONTENT_REQUEST contentRequest "
                    + " WHERE   contentRequest.content.campaign.interval = ?1 "
                    + " AND     contentRequest.bill IS NULL"
                    + " AND     contentRequest.generationDate > current_date")
})
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ContentRequest extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Static constants ---------------
    
    /**
     * Defines the name of the query to check, whether or not an email is 
     * already in use by a different user.
     */
    public static final String FIND_FOR_PAYMENT_INTERVAL 
            = "ContentRequest.findForPaymentInterval";
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the bill in which this request has been billed.
     */
    @ManyToOne
    @Getter
    @Setter
    private Bill bill;
    
    
    /**
     * Stores the content that has been requested.
     */
    @NotNull
    @ManyToOne
    @Getter
    private Content content;
    
    
    /**
     * Stores the source responsible for the request of the content.
     */
    @NotNull
    @Getter
    private String requestSource;
    
    // --------------- Private constructors ---------------

    
    /**
     * Creates a new instance of {@link ContentRequestLog} using the specified
     * content and request source.
     * 
     * @param content           the content that was requested.
     * @param requestSource     the source responsible for the request.
     */
    private ContentRequest(Content content, String requestSource) {
        this.content = content;
        this.requestSource = requestSource;
    }
    
    
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link ContentRequest}.
     * 
     * @param content           the content that was requested.
     * @param requestSource     the source responsible for the request.
     * @return  the built {@link ContentRequest}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createContentRequestLog", 
            builderClassName = "ContentRequestLogBuilder",
            buildMethodName = "build")
    private static ContentRequest validateAndCreateContentRequestLog(
            Content content, 
            String requestSource) throws BuilderValidationException {
        
        if (content == null) {
            throw new BuilderValidationException(
                    ContentRequest.class,
                    "The content can not be null.");
        }
        
        if (requestSource == null || requestSource.isEmpty()) {
            throw new BuilderValidationException(
                    ContentRequest.class,
                    "The request source can not be null or empty.");
        }
        
        return new ContentRequest(content, requestSource);
    }
    
}
