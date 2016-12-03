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
package de.oth.fkretschmar.advertisementproject.entities.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Abstract base implementation of any entity.
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     the type of the unique identifier.
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude={"description"})
@ToString
abstract class AbstractEntity<T> implements Serializable, IEntity<T> {
    
    // --------------- Public getters and setters ---------------
    
    /**
     * Gets the unique identifier of the entity.
     * 
     * @return  The unique identifier of the entity as a {@code T}.
     */
    @Override
    public abstract T getId();
}
