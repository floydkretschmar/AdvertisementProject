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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents an entity that is keyed by an auto generated numeric value.
 * 
 * @author  fkre  Floyd Kretschmar
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
    @Getter
    private Long id;
}
