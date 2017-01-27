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
package de.oth.fkretschmar.advertisementproject.entities.campaign;

import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author fkre
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TextContentValue implements Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the description part of the text content.
     */
    @Getter
    private final String description;
    
    /**
     * Stores the title part of the text content.
     */
    @Getter
    private final String title;
    
    // --------------- Public static methods ---------------

    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link TextContentValue}.
     * 
     * @param title
     * @param desctiption
     * @return  the built {@link TextContentValue}.
     */
    @Builder(
            builderMethodName = "createTextContentValue", 
            builderClassName = "TextContentValueBuilder",
            buildMethodName = "build")
    private static TextContentValue validateAndCreateTextContentValue(
            String title,
            String description) 
            throws BuilderValidationException {
        if(title == null || title.isEmpty())
            throw new BuilderValidationException(
                    TargetContext.class,
                    "The title can not be null or empty.");
        
        if(description == null || description.isEmpty())
            throw new BuilderValidationException(
                    TargetContext.class,
                    "The description can not be null or empty.");
        
        return new TextContentValue(description, title);
    }
}
