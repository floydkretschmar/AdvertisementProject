/*
 * Copyright (C) 2017 fkre
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
package de.oth.fkretschmar.advertisementproject.business.services.web;

import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import java.io.Serializable;
import java.net.URL;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents the transfer class that only publishes a small subsection of
 * information for content requests.
 *
 * @author fkre
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ContentRequestResult")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class ContentRequestResult implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores the enum that indicates the actual format of the content.
     */
    @Getter
    @XmlElement(name = "format")
    @NonNull
    private ContentFormat format;

    /**
     * Stores the URL that redirects to the advertised page.
     */
    @Getter
    @XmlElement(name = "targetPage")
    @NonNull
    public URL targetPage;

    /**
     * Stores the enum that indicates the actual type of the content.
     */
    @Getter
    @XmlElement(name = "type")
    @NonNull
    private ContentType type;

    /**
     * Stores the actual value of the content.
     */
    @XmlElement(name = "value")
    @NonNull
    private String value;

    // --------------- Public static methods ---------------
    /**
     * The method that builds the basis of the auto generated builder: Validates
     * the input and creates the corresponding {@link Content}.
     *
     * @param format the actual format of the content.
     * @param targetPage the URL that redirects to the advertised page.
     * @param type the enum that indicates the actual type of the object.
     * @param value the actual value of the content
     * @return the built {@link Content}.
     * @throws BuilderValidationException that indicates that one or more of of
     * the given creation parameters are invalid.
     */
    @Builder(
            builderMethodName = "createContentDTO",
            builderClassName = "ContentDTOBuilder",
            buildMethodName = "build")
    private static ContentRequestResult validateAndCreateContent(
            ContentFormat format,
            URL targetPage,
            ContentType type,
            Serializable value) throws BuilderValidationException {
        if (value == null) {
            throw new BuilderValidationException(
                    ContentRequestResult.class,
                    "The content can not be null.");
        }

        if (type == ContentType.UNDEFINED || type == null) {
            throw new BuilderValidationException(
                    ContentRequestResult.class,
                    "The content type can not be undefined.");
        }

        if (format == null) {
            throw new BuilderValidationException(
                    ContentRequestResult.class,
                    "The format can not be undefined.");
        }

        if (targetPage == null) {
            throw new BuilderValidationException(
                    ContentRequestResult.class,
                    "The target URL can not be null.");
        }

        String serializedValue = "";

        if (type == ContentType.IMAGE_URL) {
            serializedValue = ((URL) value).toExternalForm();
        } else if (type == ContentType.TEXT) {
            serializedValue = (String) value;
        }

        return new ContentRequestResult(
                format,
                targetPage,
                type,
                serializedValue);
    }
}
