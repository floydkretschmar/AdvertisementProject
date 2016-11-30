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
 * Defines the marital status that can be targeted with an advertisement.
 * 
 * @author fkre
 */
public enum TargetMaritalStatus {
    
    /**
     * Indicates that the target is divorced.
     */
    DIVORCED,
    
    /**
     * Indicates that the target is in a relationship.
     */
    IN_RELATIONSHIP,
    
    /**
     * Indicates that the target is married.
     */
    MARRIED,
    
    /**
     * Indicates that the target is single.
     */
    SINGLE,
    
    /**
     * Indicates that the target is widowed.
     */
    WIDOWED;
}
