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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the different age groups that can be targeted with an advertisement.
 * 
 * @author fkre
 */
@RequiredArgsConstructor
public enum TargetAge {
    
    // --------------- Enum fields ---------------
    
    /**
     * Indicates that the target is an adult (ages between 25-64 years old).
     */
    ADULTS(1<<1, 64l),
    
    /**
     * Indicates that the target is a child (ages between 0-14 years old).
     */
    CHILDREN(1<<2, 14l),
    
    /**
     * Indicates that the target is a youth (ages between 65 years and older).
     */
    SENIORS(1<<3, Long.MAX_VALUE),
    
    /**
     * Indicates that the target is a youth (ages between 15-24 years old).
     */
    YOUTH(1<<4, 24l);
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the integer representation of the flags of this enum.
     */
    @Getter
    private final int flagValue;
    
    /**
     * Stores the long representation of the upper bounry of the age range 
     * represented by the age group.
     */
    @Getter
    private final long upperAgeBoundy;
    
    // --------------- Public static methods ---------------
    
    
    /**
     * Returns the age for the specified flag value.
     * 
     * @param flagValue that defines the target age.
     * @return  the target age.
     */
    public static TargetAge of(int flagValue) {
        switch(flagValue) {
            case 1<<1:
                return TargetAge.ADULTS;
            case 1<<2:
                return TargetAge.CHILDREN;
            case 1<<3:
                return TargetAge.SENIORS;
            default:
                return TargetAge.YOUTH;
        }
    }
}
