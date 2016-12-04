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
import de.oth.fkretschmar.advertisementproject.entities.base.IDeletable;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author fkre
 */
@Entity(name = "T_CONTENT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class Content extends AbstractAutoGenerateKeyedEntity
        implements IDeletable<Long> {
    
    // --------------- Private fields ---------------

    
    /**
     * Stores the enum that indicates the actual type of the object.
     */
    @NotNull
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    private ContentType contentType 
            = ContentType.UNDEFINED;
    
    /**
     * Stores a text used to describe the entity.
     */
    @Column(name = "DESCRIPTION")
    @Getter
    @Setter
    private String description;
    
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
     * Creates a new instance of {@link Content} using the specified 
     * value, value type and target url.
     * 
     * @param   contentType     the enum that indicates the actual type of the 
     *                          object.
     * @param   description     the text that gives a short description of the
     *                          content.
     * @param   targetUrl       the URL that redirects to the advertised page.
     * @param   value           the actual value of the content.
     */
    protected Content(
            ContentType contentType, 
            String description, 
            URL targetUrl,
            Serializable value) {
        this.setValue(value, contentType);
        this.setTargetUrl(targetUrl);
        this.setDescription(description);
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
            throw new ContentDataCorruptedException(
                    "The target URL of the content was corrupted and"
                            + "is no longer valid.");
        }
    }
    
    
    /**
     * Sets the value of the advertisment and its type.
     * 
     * @param   value         the actual value of the value.
     * @param   contentType     the enum that indicates the actual type of the 
     *                          object.
     */
    public void setValue(
            Serializable value, ContentType contentType) {
        if(!contentType.getContentType().isInstance(value))
            throw new IllegalArgumentException("The content type has to match the"
                    + "specified content.");
        
        this.value = value;
        this.serializedValue = SerializationUtils.serialize(value);
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
     * Performs the work of deserializing the value that was saved as a LOB
     * into the database.
     */
    @Override
    protected void postLoad() {
        this.value = SerializationUtils.deserialize(this.serializedValue);
    }
    
    /**
     * Validates that the inputs for the content are valid.
     * 
     * @param   contentType     the enum that indicates the actual type of the 
     *                          object.
     * @param   description     the text that gives a short description of the
     *                          content.
     * @param   targetUrl       the URL that redirects to the advertised page.
     * @param   value           the actual value of the content.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    protected static void validateContentInput(
            ContentType contentType, 
            String description, 
            URL targetUrl,
            Serializable value) 
            throws BuilderValidationException {
        if (value == null) {
            throw new BuilderValidationException(
                    Content.class,
                    "The content can not be null.");
        }
        
        if (contentType == ContentType.UNDEFINED) {
            throw new BuilderValidationException(
                    Content.class,
                    "The content type can not be undefined.");
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
    }
    
    // --------------- Public static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Content}.
     * 
     * @param   contentType     the enum that indicates the actual type of the 
     *                          object.
     * @param   description     the text that gives a short description of the
     *                          content.
     * @param   targetUrl       the URL that redirects to the advertised page.
     * @param   value           the actual value of the content.
     * @return  the built {@link Content}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createContent", 
            builderClassName = "ContentBuilder",
            buildMethodName = "build")
    private static Content validateAndCreateContent(
            ContentType contentType, 
            String description, 
            URL targetUrl,
            Serializable value) throws BuilderValidationException {
        Content.validateContentInput(
                contentType, description, targetUrl, value);
        
        return new Content(contentType, description, targetUrl, value);
    }
}
