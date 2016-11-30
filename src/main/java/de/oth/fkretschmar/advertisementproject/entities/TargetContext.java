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

import java.util.EnumSet;

/**
 * Defines the context according to which an advertisement should be targeted.
 * 
 * @author fkre
 */
public class TargetContext  {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the targeted age group.
     */
    private EnumSet<TargetAge> age;
    
    /**
     * Stores the targeted gender group.
     */
    private EnumSet<TargetGender> gender;
    
    /**
     * Stores the targeted marital status group.
     */
    private EnumSet<TargetMaritalStatus> maritalStatus;
    
    /**
     * Stores the targeted 
     */
    private EnumSet<TargetPurposeOfUse> purposeOfUse;
    
    
    /**
     * Creates a new instance of {@link TargetContext}.
     */
    protected TargetContext() {
        this.age = EnumSet.allOf(TargetAge.class);
        this.gender = EnumSet.allOf(TargetGender.class);
        this.maritalStatus = EnumSet.allOf(TargetMaritalStatus.class);
        this.purposeOfUse = EnumSet.allOf(TargetPurposeOfUse.class);
    }
}
