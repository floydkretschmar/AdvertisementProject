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
package de.oth.fkretschmar.advertisementproject.entities.campaign;

import de.oth.fkretschmar.advertisementproject.entities.exceptions.ContentDataCorruptedException;
import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import de.oth.fkretschmar.advertisementproject.entities.base.AbstractRandomStringKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.base.IDeletable;
import de.oth.fkretschmar.advertisementproject.entities.base.converter.MoneyAttributeConverter;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.SerializationUtils;
import org.joda.money.Money;

/**
 *
 * @author fkre
 */
@Entity(name = "T_CONTENT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@NamedQueries({
    @NamedQuery(
            name = Content.FIND_ALL_ACTIVE,
            query = " select CONTENT "
            + "from T_CONTENT CONTENT "
            + "where CONTENT.numberOfRequests > 0 "
            + "AND CONTENT.campaign.campaignState = de.oth.fkretschmar.advertisementproject.entities.campaign.CampaignState.RUNNING "
            + "AND CONTENT.format = ?1")
})
@ToString(callSuper = true, exclude = "campaign")
public class Content extends AbstractRandomStringKeyedEntity
        implements IDeletable<String> {

    // --------------- Public static constants ---------------
    /**
     * Defines the name of the query to find all active contents (number of
     * requests greater 0 and campaign is not cancelled).
     */
    public static final String FIND_ALL_ACTIVE = "User.findAllActive";

    // --------------- Private fields ---------------
    /**
     * Stores the campaign for which the content was ordered.
     */
    @NotNull
    @ManyToOne
    @Getter
    @Setter
    private Campaign campaign;

    /**
     * Stores the enum that indicates the actual type of the content.
     */
    @NotNull
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    private ContentType contentType
            = ContentType.UNDEFINED;

    /**
     * Stores the target context of the specified content that influinces the
     * price.
     */
    @NotNull
    @OneToOne
    @Getter
    @Setter
    @JoinColumn(name = "CONTEXT_ID")
    private TargetContext context;

    /**
     * Stores the enum that indicates the actual format of the content.
     */
    @NotNull
    @Column(name = "FORMAT", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    private ContentFormat format;

    /**
     * Stores the name of the content that was chosen by the comissioner.
     */
    @NotNull
    @Column(name = "CONTENT_NAME", nullable = false)
    @Getter
    private String name;

    /**
     * Stores the number of contents that have been ordered for the target
     * context.
     */
    @NotNull
    @Column(name = "NUMER_OF_REQUESTS", nullable = false)
    @Getter
    @Setter
    private long numberOfRequests;

    /**
     * Stores the monetary amount tbe creator of the content is willing to pay
     * per request of this campaign content.
     *
     */
    @NotNull
    @Column(name = "PRICE_PER_REQUEST", nullable = false)
    @Getter
    @Setter
    @Convert(converter = MoneyAttributeConverter.class)
    private Money pricePerRequest;

    /**
     * Stores the serialized value.
     */
    @NotNull
    @Column(name = "VALUE", nullable = false)
    @Lob
    private byte[] serializedValue;

    /**
     * Stores the URL path that redirects to the advertised page.
     */
    @NotNull
    @Column(name = "URL_PATH", nullable = false)
    private String targetUrlPath;

    /**
     * Stores the actual value of the content.
     */
    @Transient
    @NotNull
    @Getter
    private Serializable value;

    // --------------- Protected constructors ---------------
    /**
     * Creates a new instance of {@link Content} using the specified value,
     * value type and target url.
     *
     * @param name the name of the content given by the comissioner.
     * @param contentType the enum that indicates the actual type of the object.
     * @param context the target context of the specified order row that
     * influinces the price.
     * @param format the actual format of the content.
     * @param numberOfRequests the number of contents that have been ordered for
     * the target context.
     * @param pricePerRequest the monetary amount tbe creator of the value is
     * willing to pay per request of this campaign value.
     * @param targetUrl the URL that redirects to the advertised page.
     * @param value the actual value of the content
     */
    protected Content(
            String name,
            ContentType contentType,
            TargetContext context,
            ContentFormat format,
            long numberOfRequests,
            Money pricePerRequest,
            URL targetUrl,
            Serializable value) {
        this.setValue(value, contentType);
        this.setTargetUrl(targetUrl);
        this.context = context;
        this.format = format;
        this.name = name;
        this.numberOfRequests = numberOfRequests;
        this.pricePerRequest = pricePerRequest;
    }

    // --------------- Public getters and setters ---------------
    /**
     * Gets the URL that redirects to the advertised page.
     *
     * @return the url.
     */
    public URL getTargetUrl() {
        try {
            return new URL(this.targetUrlPath);
        } catch (MalformedURLException malformedException) {
            throw new ContentDataCorruptedException(
                    "The target URL of the content was corrupted and"
                    + "is no longer valid.");
        }
    }

    /**
     * Sets the value of the advertisment and its type.
     *
     * @param value the actual value of the value.
     * @param contentType the enum that indicates the actual type of the object.
     */
    public void setValue(
            Serializable value, ContentType contentType) {
        if (!contentType.getContentType().isInstance(value)) {
            throw new IllegalArgumentException("The content type has to match the"
                    + "specified content.");
        }

        this.value = value;
        this.serializedValue = SerializationUtils.serialize(value);
        this.contentType = contentType;
    }

    /**
     * Sets the URL that redirects to the advertised page.
     *
     * @param targetUrl that represents the target.
     */
    public void setTargetUrl(URL targetUrl) {
        this.targetUrlPath = targetUrl.toString();
    }

    // --------------- Protected methods ---------------
    /**
     * Performs the work of deserializing the value that was saved as a LOB into
     * the database.
     */
    @Override
    protected void postLoad() {
        this.value = SerializationUtils.deserialize(this.serializedValue);
    }

    // --------------- Public static methods ---------------
    /**
     * The method that builds the basis of the auto generated builder: Validates
     * the input and creates the corresponding {@link Content}.
     *
     * @param name the name of the content given by the comissioner.
     * @param contentType the enum that indicates the actual type of the object.
     * @param context the target context of the specified order row that
     * influinces the price.
     * @param format the actual format of the content.
     * @param numberOfRequests the number of contents that have been ordered for
     * the target context.
     * @param pricePerRequest the monetary amount tbe creator of the value is
     * willing to pay per request of this campaign value.
     * @param targetUrl the URL that redirects to the advertised page.
     * @param value the actual value of the content
     * @return the built {@link Content}.
     * @throws BuilderValidationException that indicates that one or more of of
     * the given creation parameters are invalid.
     */
    @Builder(
            builderMethodName = "createContent",
            builderClassName = "ContentBuilder",
            buildMethodName = "build")
    private static Content validateAndCreateContent(
            String name,
            ContentType contentType,
            TargetContext context,
            ContentFormat format,
            long numberOfRequests,
            Money pricePerRequest,
            URL targetUrl,
            Serializable value) throws BuilderValidationException {
        if (name == null || name.isEmpty()) {
            throw new BuilderValidationException(
                    Content.class,
                    "The name can not be null or empty.");
        }
        
        if (value == null) {
            throw new BuilderValidationException(
                    Content.class,
                    "The content can not be null.");
        }

        if (contentType == ContentType.UNDEFINED || contentType == null) {
            throw new BuilderValidationException(
                    Content.class,
                    "The content type can not be undefined.");
        }

        if (format == null) {
            throw new BuilderValidationException(
                    Content.class,
                    "The format can not be undefined.");
        }

        if (!contentType.getContentType().isInstance(value)) {
            throw new BuilderValidationException(
                    Content.class,
                    "The content type has to match the"
                    + "specified content.");
        }

        if (targetUrl == null) {
            throw new BuilderValidationException(
                    Content.class,
                    "The target URL can not be null.");
        }

        if (numberOfRequests <= 0) {
            throw new BuilderValidationException(
                    Content.class,
                    "The amount can not be smaller or equal to 0.");
        }

        if (context == null) {
            throw new BuilderValidationException(
                    Content.class,
                    "The target context can not be null.");
        }

        if (pricePerRequest == null || pricePerRequest.isNegative()) {
            throw new BuilderValidationException(
                    Content.class,
                    "The price per request can not be null or negative.");
        }

        return new Content(
                name,
                contentType,
                context,
                format,
                numberOfRequests,
                pricePerRequest,
                targetUrl,
                value);
    }
}
