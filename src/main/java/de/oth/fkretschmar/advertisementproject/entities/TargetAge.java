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
 * Defines the different age groups that can be targeted with an advertisement.
 * 
 * @author fkre
 */
public enum TargetAge {
    
    /**
     * Indicates that the target is an adult (ages between 25-64 years old).
     */
    ADULTS,
    
    /**
     * Indicates that the target is a child (ages between 0-14 years old).
     */
    CHILDREN,
    
    /**
     * Indicates that the target is a youth (ages between 65 years and older).
     */
    SENIORS,
    
    /**
     * Indicates that the target is a youth (ages between 15-24 years old).
     */
    YOUTH;
}
