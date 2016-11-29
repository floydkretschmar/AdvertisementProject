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

/**
 * The abstract base implementation of an builder to create entities.
 *
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     the type of object that is built by this builder.
 */
public abstract class AbstractBuilder<T> {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the object that is being build by the builder.
     */
    private final T object;
    
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link AbstractEntityBuilder}.
     * 
     * @param   object  the object that is being created.
     */
    protected AbstractBuilder(T object) {
        this.object = object;
    }

    
    // --------------- Protected methods ---------------
    
    
    /**
     * Gets the object that is being build by the builder
     * 
     * @return 
     */
    protected T getObject() {
        return this.object;
    }
    
    
    // --------------- Public methods ---------------
    
    
    /**
     * Builds an new object using the data specified by the other builder
     * methods.
     * 
     * @return  the object after it has been built.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an object failed.
     */
    public T build() throws EntityBuilderValidationException {
        this.validate(this.object);
        return this.object;
    }
    
    
    // --------------- Protected methods ---------------
        
    
    /**
     * Validates whether or not the object is in a consitent state before 
     * returning it to the user via the build method.
     * 
     * @param   object  that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an object failed.
     */
    protected void validate(T object) 
            throws EntityBuilderValidationException {
        
    }
}
