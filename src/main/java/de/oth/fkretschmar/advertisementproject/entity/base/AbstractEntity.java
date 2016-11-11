/*
 * Copyright (C) 2016 Floyd Kretschmar (fkre)
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
package de.oth.fkretschmar.advertisementproject.entity.base;

import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.MappedSuperclass;

/**
 * Represents the abstract base implementation of any entity.
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     the type of the unique identifier.
 */
@MappedSuperclass
abstract class AbstractEntity<T> implements Serializable, IEntity<T> {
    
    /**
     * Stores a text used to describe the entity.
     */
    @Column(name = "DESCRIPTION")
    private String description;
    
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link AbstractEntity}.
     */
    public AbstractEntity() {
        
    }
    
    // --------------- Public getters ---------------

    
    /**
     * Gets a text used to describe the entity.
     * 
     * @return  the text used to describe the entity.
     */
    public String getDescription() {
        return this.description;
    }
    
    
    /**
     * Gets the unique identifier of the entity.
     * 
     * @return  The unique identifier of the entity as a {@code T}.
     */
    @Override
    public abstract T getId();
    
    
    /**
     * Sets a text used to describe the entity.
     * 
     * @param   description that describes the entity.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    // --------------- Public methods ---------------
    
    /**
     * Compares this object to the specified object.  The result is {@code true} 
     * if and only if the argument is not {@code null} and is a 
     * {@link AbstractEntity} object that is the same as 
     * this object.
     *
     * @param   obj   the object to compare with.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        return this.equalsCore((AbstractEntity) obj);
    }
    
    
    /**
     * Returns a hash code for an {@link AbstractEntity} object.
     * 
     * @return A hash code value for an {@link AbstractEntity} object.
     */
    @Override
    public final int hashCode() {
        return this.hashCodeCore();
    }
    
    
    /**
     * Returns the default String representation of a entity.
     * 
     * @return  A String that represents a default entity.
     */
    @Override
    public String toString() {
        return "[" + this.getClass() + "ID: " + this.getId() + "]";
    }
    
    // --------------- Protected methods ---------------
    
    /**
     * Compares this entity to the specified entity. The result is {@code true} 
     * if and only if the given entity is exactly the same as this entity. Per 
     * default two entities are exactly the same, if the have the same unique
     * identifier.
     *
     * @param   entity   the object to compare with.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     */
    protected boolean equalsCore(AbstractEntity entity) {
        return this.getId() == entity.getId();
    }

    
    /**
     * Returns a hash code for an {@link AbstractEntity} object.
     * 
     * @return  A hash code value for an {@link AbstractEntity} object.
     */
    protected abstract int hashCodeCore();
}
