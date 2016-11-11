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
package de.oth.fkretschmar.advertisementproject.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Represents an entity that is keyed by a uniqe string value.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@MappedSuperclass
public abstract class AbstractStringKeyedEntity 
        extends AbstractManagedEntity<String> {   
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the unique identifier of an entity.
     */
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;
    
    
    // --------------- Protected constructors ---------------
    
    /**
     * Creates a new instance of {@link AbstractStringKeyedEntity}.
     */
    protected AbstractStringKeyedEntity() {
        super();
    }
    
    // --------------- Public constructors ---------------
    
    
    /** 
     * Creates a new instance of {@link AbstractStringKeyedEntity} using the
     * specified id.
     * 
     * @param   id  that uniqely identifies the entity. 
     */
    public AbstractStringKeyedEntity(String id) {
        this();
        this.id = id;
    }
    
    // --------------- Public getters ---------------
    
    
    /**
     * Gets the unique identifier of the entity.
     * 
     * @return  The unique identifier of the entity as a {@link Long}.
     */
    @Override
    public String getId() {
        return this.id;
    }
    
    
    // --------------- Protected methods ---------------
    
    
    /**
     * Returns a hash code for an {@link AbstractNumericKeyedEntity} object.
     * 
     * @return  A hash code value for an {@link AbstractNumericKeyedEntity} object.
     */
    @Override
    protected int hashCodeCore() {
        return this.id.hashCode();
    }
}
