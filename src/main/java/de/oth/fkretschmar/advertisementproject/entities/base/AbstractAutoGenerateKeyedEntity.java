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

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Represents an entity that is keyed by an auto generated numeric value.
 * 
 * @author  fkre  Floyd Kretschmar
 */
@MappedSuperclass
public abstract class AbstractAutoGenerateKeyedEntity 
        extends AbstractManagedEntity<Long> {   
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the unique identifier of an entity.
     */
    @Id
    @Column(name = "ID")
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    // --------------- Public constructors ---------------
    
    
    /**
     * Creates a new instance of {@link AbstractAutoGenerateKeyedEntity}.
     */
    public AbstractAutoGenerateKeyedEntity() {
        super();
    }
    
    
    // --------------- Public getters ---------------
    
    
    /**
     * Gets the unique identifier of the entity.
     * 
     * @return  The unique identifier of the entity as a {@link Long}.
     */
    @Override
    public Long getId() {
        return this.id;
    }
    
    
    // --------------- Protected methods ---------------
    
    
    /**
     * Returns a hash code for an {@link AbstractAutoGenerateKeyedEntity} object.
     * 
     * @return  A hash code value for an {@link AbstractAutoGenerateKeyedEntity} object.
     */
    @Override
    protected int hashCodeCore() {
        return Long.hashCode(this.id);
    }
}
