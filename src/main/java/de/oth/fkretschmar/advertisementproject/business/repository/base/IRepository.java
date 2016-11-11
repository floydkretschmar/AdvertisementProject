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

import java.util.Collection;
import javax.enterprise.context.Dependent;

/**
 * The interface that defines the methods that describe an repository.
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     The type that specifies which entity is being managed by the
 *                  repository.
 */
@Dependent
public interface IRepository<T extends IEntity> {
    
    // --------------- Public methods ---------------
    
    /**
     * Finds the entity for the specified id.
     * 
     * @param   id  that specifies the entity that will be found.
     * @return  The entity with the specified id.
     */
    public T find(int id);
    
    
    /**
     * Deletes the specified entity.
     * 
     * @param   entity  that will be deleted.
     */
    public void delete(T entity);
    
    
    /**
     * Deletes all specified entities.
     * 
     * @param   entities  that will be deleted.
     */
    public void delete(Collection<T> entities);
    
    
    /**
     * Saves the specified entity.
     * 
     * @param   entity  that will be saved
     * @return  The saved entity.
     */
    public T save(T entity);
    
    
    /**
     * Saves all specified entities.
     * 
     * @param   entities    that will be saved.
     * @return  The saved entities.
     */
    public Collection<T> save(Collection<T> entities);
    
    
    /**
     * Updates the specified entity.
     * 
     * @param   entity  that will be updated.
     * @return  The updated entity.
     */
    public T update(T entity);
    
    
    /**
     * Updates all specified entities.
     * 
     * @param   entities    that will be updated.
     * @return  The updated entities.
     */
    public Collection<T> update(Collection<T> entities);
}
