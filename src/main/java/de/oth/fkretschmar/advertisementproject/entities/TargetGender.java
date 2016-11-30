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

/**
 * Defines the different genders that can be targeted with an advertisement.
 * 
 * @author fkre
 */
public enum TargetGender {
    
    /**
     * Indicates that the target identifies as a female.
     */
    FEMALE,
    
    /**
     * Indicates that the target identifies as a male.
     */
    MALE,
    
    /**
     * Indicates that the target neither identifies as male or female.
     */
    OTHER;
}
