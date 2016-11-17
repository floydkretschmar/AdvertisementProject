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

import de.oth.fkretschmar.advertisementproject.entities.interfaces.IEntity;

/**
 * The abstract base implementation of an builder to create entities.
 *
 * @author  fkre    Floyd Kretschmar
 * @param   <S>     the type of the id identifying the entity that is built by
 *                  this builder.
 * @param   <T>     the type of entity that is built by this builder.
 */
public abstract class AbstractEntityBuilder<S, T extends IEntity<S>> 
        implements IEntityBuilder<S, T> {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the entity that is being build by the builder.
     */
    private T entity;
    
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link AbstractEntityBuilder}.
     */
    protected AbstractEntityBuilder() {
        this.entity = this.createEntity();
    }

    
    // --------------- Public methods ---------------
    
    
    /**
     * Gets the entity that is being build by the builder
     * 
     * @return 
     */
    protected T getEntity() {
        return this.entity;
    }
    
    
    // --------------- Public methods ---------------
    
    
    /**
     * Builds an new entity using the data specified by the other builder
     * methods.
     * 
     * @param   <U>             the type of the builder.
     * @param   entityBuilder   the entity builder that is being used to build 
     *                          the entity.
     * @return                  the entity after it has been built.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    public <U extends IEntityBuilder<S, T>> T build(U entityBuilder)
            throws EntityBuilderValidationException {
        this.validate(this.entity);
        return this.entity;
    }
    
    
    // --------------- Protected methods ---------------
    
    /**
     * Initializes a new, empty instance of the entity being build by the 
     * builder.
     * 
     * @return  the new, empty instance of the entity.
     */
    protected abstract T createEntity();
    
    
    /**
     * Validates whether or not the entity is in a consitent state before 
     * returning it to the user via the build method.
     * 
     * @param   entity  that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    protected abstract void validate(T entity) 
            throws EntityBuilderValidationException;
}
