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

import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author fkre
 */
@Entity(name = "T_ADVERTISEMENT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class Advertisement extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the actual content of the advertisement.
     */
    @Transient
    @Getter(AccessLevel.PUBLIC)
    private Serializable content;
    
    /**
     * Stores the enum that indicates the actual type of the object.
     */
    @Column(name = "CONTENT_TYPE")
    @Enumerated(EnumType.STRING)
    @Getter(AccessLevel.PUBLIC)
    private AdvertisementContentType contentType 
            = AdvertisementContentType.UNDEFINED;
    
    /**
     * Stores the serialized content.
     */
    @Column(name = "CONTENT")
    @Lob
    private byte[] serializedContent;
    
    /**
     * Stores the URL path that redirects to the advertised page.
     */
    @Column(name = "URL_PATH")
    private String targetUrlPath;
    
    
    // --------------- Private constructors ---------------
    
    
    /**
     * Creates a new instance of {@link Advertisement} using the specified 
     * content, content type and target url.
     * 
     * @param   content         the serializable content.
     * @param   contentType     the content type.
     * @param   targetUrl       the target url.
     */
    private Advertisement(
            Serializable content, 
            AdvertisementContentType contentType, 
            URL targetUrl) {
        this.setContent(content, contentType);
        this.setTargetUrl(targetUrl);
    }
    
    
    // --------------- Public getters and setters ---------------
    
    
    /**
     * Gets the URL that redirects to the advertised page.
     * 
     * @return  the url.
     */
    public URL getTargetUrl() {
        try {
            return new URL(this.targetUrlPath);
        } catch (MalformedURLException malformedException) {
            throw new AdvertisementDataCorrupted(
                    "The target URL of the advertisement was corrupted and"
                            + "is no longer valid.");
        }
    }
    
    
    /**
     * Sets the content of the advertisment and its type.
     * 
     * @param   content         the actual content of the advertisement.
     * @param   contentType     the enum that indicates the actual type of the 
     *                          object.
     */
    public void setContent(
            Serializable content, AdvertisementContentType contentType) {
        if(!contentType.getContentType().isInstance(content))
            throw new IllegalArgumentException("The content type has to match the"
                    + "specified content.");
        
        this.content = content;
        this.serializedContent = SerializationUtils.serialize(content);
        this.contentType = contentType;
    }
    
    
    /**
     * Sets the URL that redirects to the advertised page.
     * 
     * @param   targetUrl   that represents the target.
     */
    public void setTargetUrl(URL targetUrl) {
        this.targetUrlPath = targetUrl.toString();
    }
    
    // --------------- Protected methods ---------------
    
    /**
     * Performs the work of deserializing the content that was saved as a LOB
     * into the database.
     */
    @Override
    protected void postLoad() {
        this.content = SerializationUtils.deserialize(this.serializedContent);
    }
    
    // --------------- Public static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Advertisement}.
     * 
     * @param   content         the actual content of the advertisement.
     * @param   contentType     the enum that indicates the actual type of the 
     *                          object.
     * @param   targetUrl       the URL that redirects to the advertised page.
     * @return  the built {@link Advertisement}.
     */
    @Builder(
            builderMethodName = "createAdvertisement", 
            builderClassName = "AdvertisementBuilder",
            buildMethodName = "build")
    private static Advertisement validateAndCreateAdvertisement(
            Serializable content, 
            AdvertisementContentType contentType,
            URL targetUrl) {
        if (content == null) {
            throw new BuilderValidationException(
                    Advertisement.class,
                    "The content can not be null.");
        }
        
        if (contentType == AdvertisementContentType.UNDEFINED) {
            throw new BuilderValidationException(
                    Advertisement.class,
                    "The content type can not be undefined.");
        }
        
        if (!contentType.getContentType().isInstance(content)) {
            throw new BuilderValidationException(
                    Advertisement.class,
                    "The content type has to match the"
                    + "specified content.");
        }
        
        if (targetUrl == null) {
            throw new BuilderValidationException(
                    Advertisement.class,
                    "The target URL can not be null.");
        }
        
        return new Advertisement(content, contentType, targetUrl);
    }
}
