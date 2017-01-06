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

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Represents an entity that is keyed by a uniqe string value.
 * 
 * NOTE: This type of entity is used instead of AbstractAutoGenerateKeyedEntity
 *       when I need the ID of the entity to be unique BEFORE saving it for the
 *       first time. This is the case when I am storing the entities in Sets
 *       to allow for problem free eager loading and at the same time I am not
 *       saving the elements of the set as a bulk.
 *       For example: When adding contents to a new campaign I only save the 
 *                    campaign and its contents after the user explicitly pushes
 *                    the save button. So Contents (which are stored in a set) 
 *                    have to have a unique ID before they ever get saved.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractRandomStringKeyedEntity 
        extends AbstractManagedEntity<String> {   
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the unique identifier of an entity.
     */
    @Id
    @NonNull
    @Column(name = "ID", nullable = false)
    @Getter
    private String id;
    
    // --------------- Protected constructors ---------------
    
    /**
     * Creates a new instance of {@link AbstractRandomStringKeyedEntity}.
     */
    protected AbstractRandomStringKeyedEntity() {
        this.id = UUID.randomUUID().toString();
    }
}
