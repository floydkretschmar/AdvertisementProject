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

import java.awt.image.RenderedImage;
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
     */
    IMAGE(RenderedImage.class),
    
    /**
     * Indicates that the content of the advertisement is an URL linking to an
     * image.
     */
    IMAGE_URL(URL.class),
    
    /**
     * Indicates that the content of the advertisement is a plain text.
     */
    TEXT(String.class),
    
    /**
     * Indicates that the content of the advertisement has not been defined.
     */
    UNDEFINED(Object.class);
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the actual type that is represented by the enum.
     */
    @Getter(AccessLevel.PUBLIC)
    private final Class contentType;
}
