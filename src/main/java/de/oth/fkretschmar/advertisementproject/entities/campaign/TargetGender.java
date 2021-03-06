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

/**
 * Defines the different genders that can be targeted with an advertisement.
 * 
 * @author fkre
 */
public enum TargetGender {
    
    // --------------- Enum fields ---------------
    
    /**
     * Indicates that the target identifies as a female.
     */
    FEMALE(1<<1),
    
    /**
     * Indicates that the target identifies as a male.
     */
    MALE(1<<2),
    
    /**
     * Indicates that the target neither identifies as male or female.
     */
    OTHER(1<<3);
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the integer representation of the flags of this enum.
     */
    @Getter
    private final int flagValue;
    
    // --------------- Private constructors ---------------
    
    /**
     * Creates a new instance of {@link TargetGender} using the specified value.
     * 
     * @param flagValue the flagValue indicating the bit position of the flag.
     */
    private TargetGender(int flagValue){
        this.flagValue = flagValue;
    }
    
    // --------------- Public static methods ---------------
    
    
    /**
     * Returns the age for the specified flag value.
     * 
     * @param flagValue that defines the target age.
     * @return  the target age.
     */
    public static TargetGender of(int flagValue) {
        switch(flagValue) {
            case 1<<1:
                return TargetGender.FEMALE;
            case 1<<2:
                return TargetGender.MALE;
            default:
                return TargetGender.OTHER;
        }
    }
}
