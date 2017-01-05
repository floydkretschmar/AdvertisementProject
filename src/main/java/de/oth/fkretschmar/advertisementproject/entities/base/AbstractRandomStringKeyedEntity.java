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
