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

import de.oth.fkretschmar.advertisementproject.entities.base.IEntity;

/**
 * The abstract base implementation of an builder to create entities.
 *
 * @author  fkre    Floyd Kretschmar
 * @param   <S>     the type of the id identifying the entity that is built by
 *                  this builder.
 * @param   <T>     the type of entity that is built by this builder.
 */
public abstract class AbstractEntityBuilder<S, T extends IEntity<S>> 
        extends AbstractBuilder<T> {
    
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link AbstractEntityBuilder}.
     * 
     * @param   entity  the entity that is being created.
     */
    protected AbstractEntityBuilder(T entity) {
        super(entity);
    }
    
    
    // --------------- Public methods ---------------
    
    /**
     * Creates the entity using a specific description.
     * 
     * @param   description    the text that describes an entity.
     * @return  the address builder used to build the entity.
     */
    public AbstractEntityBuilder<S, T> withDescription(String description) {
        this.getObject().setDescription(description);
        return this;
    }
}
