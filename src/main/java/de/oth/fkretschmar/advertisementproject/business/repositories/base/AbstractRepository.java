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
package de.oth.fkretschmar.advertisementproject.business.repositories.base;

import de.oth.fkretschmar.advertisementproject.entities.base.IEntity;
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
 */
@Dependent
public abstract class AbstractRepository<S, T extends IEntity<S>> 
        implements Serializable {
    
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
     * Finds the entity for the specified id.
     * 
     * @param   id  that specifies the entity that will be found.
     * @return  The entity with the specified id.
     */
    public abstract T find(S id);
    
    
    /**
     * Merges the specified entity.
     * 
     * @param   entity  that will be updated.
     * @return  The updated entity.
     */
    public abstract T merge(T entity);
    
    
    /**
     * Merges all specified entities.
     * 
     * @param   entities    that will be updated.
     * @return  The updated entities.
     */
    public abstract Collection<T> merge(Collection<T> entities);
    
    
    /**
     * Persists the specified entity.
     * 
     * @param   entity  that will be saved
     * @return  The saved entity.
     */
    public abstract T persist(T entity);
    
    
    /**
     * Persists all specified entities.
     * 
     * @param   entities    that will be saved.
     * @return  The saved entities.
     */
    public abstract Collection<T> persist(Collection<T> entities);
    
    
    /**
     * Removes the specified entity.
     * 
     * @param   entity  that will be deleted.
     */
    public abstract void remove(T entity);
    
    
    /**
     * Removes all specified entities.
     * 
     * @param   entities  that will be deleted.
     */
    public abstract void remove(Collection<T> entities);
    
    
    // --------------- Protected methods ---------------
    
    
    /**
     * Creates a collection of the managed entity.
     * 
     * @return  A collection that can store multiple entries of the managed
     *          entity.
     */
    protected abstract Collection<T> createCollection();

    
    /**
     * Gets the class type of the entity being managed by the repository.
     * 
     * @return  the class type of the entity.
     */
    protected Class<T> getEntityClassType() {
        return this.classType;
    }
}
