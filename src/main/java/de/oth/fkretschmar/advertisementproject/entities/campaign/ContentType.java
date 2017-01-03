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
package de.oth.fkretschmar.advertisementproject.entities.campaign;

import java.net.URL;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the different types of content that an advertisement can come with.
 * 
 * @author Floyd
 */
@RequiredArgsConstructor
public enum ContentType {
    
    // --------------- Enum fields ---------------
    
    /**
     * Indicates that the content of the advertisement is an image.
     * 
     * NOTE: Theoretically fully implemented (please look at my beautiful 
     *       SerializableRendererImage for reference) but sending images over
     *       the network/storing them in the DB is not really the way to do it
     *       so I am disableing this for now.
     */
//    IMAGE(RenderedImage.class),
    
    /**
     * Indicates that the content of the advertisement is an URL linking to an
     * image.
     */
    IMAGE_URL(URL.class, ContentType.IMAGE_URL_FORMATTED_NAME),
    
    /**
     * Indicates that the content of the advertisement is a plain text.
     */
    TEXT(String.class, ContentType.TEXT_FORMATTED_NAME),
    
    /**
     * Indicates that the content of the advertisement has not been defined.
     */
    UNDEFINED(Object.class, ContentType.UNDEFINED_FORMATTED_NAME);
    
    // --------------- Public static constants ---------------
    
    /**
     * Defines the formatted name of the IMAGE_URL state of the enum.
     */
    public static final String IMAGE_URL_FORMATTED_NAME = "Image URL";
    
    /**
     * Defines the formatted name of the TEXT state of the enum.
     */
    public static final String TEXT_FORMATTED_NAME = "Text";
    
    /**
     * Defines the formatted name of the UNDEFINED state of the enum.
     */
    public static final String UNDEFINED_FORMATTED_NAME = "Undefined";
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the actual type that is represented by the enum.
     */
    @Getter
    private final Class contentType;
    
    
    /**
     * Stores the label that describes the content type.
     */
    @Getter
    private final String label;
    
    // --------------- Public static methods ---------------
    
    
    /**
     * Returns the content type that corresponds with the well formatted name.
     * 
     * @param   formattedName   the well formatted name that represents the 
     *                          content type.
     * @return  the content type.
     */
    public static ContentType getContentType(String formattedName) {
//        if (formattedName.equals(ContentType.IMAGE_FORMATTED_NAME))
//            return ContentType.IMAGE;
        if (formattedName.equals(ContentType.IMAGE_URL_FORMATTED_NAME))
            return ContentType.IMAGE_URL;
        else if (formattedName.equals(ContentType.TEXT_FORMATTED_NAME))
            return ContentType.TEXT;
        else
            return ContentType.UNDEFINED;
    }
}
