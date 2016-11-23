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
package de.oth.fkretschmar.advertisementproject.entities.base;

/**
 * Defines the default methods that describe an entity.
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     that defines the type of the id.
 */
public interface IEntity<T> {
    
    // --------------- Public getters and setters ---------------

    
    /**
     * Gets a text used to describe the entity.
     * 
     * @return  the text used to describe the entity.
     */
    public String getDescription();
    
    
    /**
     * Gets the unique identifier of the entity.
     * 
     * @return  The unique identifier of the entity as a {@code T}.
     */
    public T getId();
    
    /**
     * Sets a text used to describe the entity.
     * 
     * @param   description that describes the entity.
     */
    public void setDescription(String description);
}
