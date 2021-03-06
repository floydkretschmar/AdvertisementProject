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
 * Defines the different purposes of use that can be targeted with an 
 * advertisement.
 * 
 * @author fkre
 */
public enum TargetPurposeOfUse {
    
    /**
     * Indicates that the target is operating in a business environment.
     */
    BUSINESS(1<<1),
    
    /**
     * Indicates that the target is operating in a private environment.
     */
    PRIVATE(1<<2);
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the integer representation of the flags of this enum.
     */
    @Getter
    private final int flagValue;
    
    // --------------- Private constructors ---------------
    
    /**
     * Creates a new instance of {@link TargetPurposeOfUse} using the specified 
     * value.
     * 
     * @param flagValue the flagValue indicating the bit position of the flag.
     */
    private TargetPurposeOfUse(int flagValue){
        this.flagValue = flagValue;
    }
    
    // --------------- Public static methods ---------------
    
    
    /**
     * Returns the age for the specified flag value.
     * 
     * @param flagValue that defines the target age.
     * @return  the target age.
     */
    public static TargetPurposeOfUse of(int flagValue) {
        switch(flagValue) {
            case 1<<1:
                return TargetPurposeOfUse.BUSINESS;
            default:
                return TargetPurposeOfUse.PRIVATE;
        }
    }
}
