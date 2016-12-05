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

import de.oth.fkretschmar.advertisementproject.entities.base.IFlagField;
import lombok.Getter;

/**
 * Defines the different age groups that can be targeted with an advertisement.
 * 
 * @author fkre
 */
public enum TargetAge implements IFlagField {
    
    // --------------- Enum fields ---------------
    
    /**
     * Indicates that no specific age group should be targeted.
     **/
    IRRELEVANT(1<<0),
    
    /**
     * Indicates that the target is an adult (ages between 25-64 years old).
     */
    ADULTS(1<<1),
    
    /**
     * Indicates that the target is a child (ages between 0-14 years old).
     */
    CHILDREN(1<<2),
    
    /**
     * Indicates that the target is a youth (ages between 65 years and older).
     */
    SENIORS(1<<3),
    
    /**
     * Indicates that the target is a youth (ages between 15-24 years old).
     */
    YOUTH(1<<4);
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the integer representation of the flags of this enum.
     */
    @Getter
    private final int flagValue;
    
    // --------------- Private constructors ---------------
    
    /**
     * Creates a new instance of {@link TargetAge} using the specified value.
     * 
     * @param flagValue the value indicating the bit position of the flag.
     */
    private TargetAge(int flagValue){
        this.flagValue = flagValue;
    }
}
