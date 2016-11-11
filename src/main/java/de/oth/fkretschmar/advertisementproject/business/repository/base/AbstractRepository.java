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
package de.oth.fkretschmar.advertisementproject.business.repository.base;

import de.oth.fkretschmar.advertisementproject.entity.base.IEntity;
import java.io.Serializable;

import java.util.Collection;
import javax.enterprise.context.Dependent;

/**
 * Represents an abstract base repository that defines the default CRUD methods
 * that have to be offered when handling an entity.
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     The type that specifies which entity is being managed by the
 *                  repository.
 * @param   <S>     The type of the collection that multiple entities that are 
 *                  being managed by this repository can be stored in.
 */
@Dependent
public abstract class AbstractRepository<T extends IEntity, S extends Collection<T>> 
        implements IRepository<T, S>, Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the class type of the entity being managed by the repository.
     */
    private Class<T> classType;
    
    // --------------- Public constructors ---------------
    
    
    /**
     * Creates a new instance of {@link AbstractRepository} using the specified
     * class type.
     * 
     * @param   classType   of the entity being managed by the repository.
     */
    public AbstractRepository(Class<T> classType) {
        this.classType = classType;
    }
    
    
    // --------------- Public methods ---------------

    
    /**
     * Gets the class type of the entity being managed by the repository.
     * @return 
     */
    protected Class<T> getClassType() {
        return this.classType;
    }
    
    
    // --------------- Public methods ---------------
    
    /**
     * Finds the entity for the specified id.
     * 
     * @param   id  that specifies the entity that will be found.
     * @return  The entity with the specified id.
     */
    @Override
    public abstract T find(int id);
    
    
    /**
     * Deletes the specified entity.
     * 
     * @param   entity  that will be deleted.
     */
    @Override
    public abstract void delete(T entity);
    
    
    /**
     * Saves the specified entity.
     * 
     * @param   entity  that will be saved
     * @return  The saved entity.
     */
    @Override
    public abstract T save(T entity);
    
    
    /**
     * Saves all specified entities.
     * 
     * @param   entities    that will be saved.
     * @return  The saved entities.
     */
    @Override
    public abstract S save(S entities);
    
    
    /**
     * Updates the specified entity.
     * 
     * @param   entity  that will be updated.
     * @return  The updated entity.
     */
    @Override
    public abstract T update(T entity);
    
    
    /**
     * Updates all specified entities.
     * 
     * @param   entities    that will be updated.
     * @return  The updated entities.
     */
    @Override
    public abstract S update(S entities);
}
