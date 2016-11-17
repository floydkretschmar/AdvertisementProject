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

import de.oth.fkretschmar.advertisementproject.entities.interfaces.IEntity;

import java.util.Collection;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Represents an repository that defines the default CRUD methods when using
 * the Java Persistance API.
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     The type that specifies which entity is being managed by the
 *                  repository.
 */
@Dependent
public abstract class AbstractJPARepository<S, T extends IEntity<S>> 
        extends AbstractRepository<T> {
    
    // --------------- Private fields ---------------

    /**
     * Stores the entity manager used to persist/load/remove/modify data.
     */
    @PersistenceContext(name = "FKREWS1617_PU")
    private EntityManager entityManager;

    
    // --------------- Public constructor ---------------
    
    
    /**
     * Creates a new instance of {@link AbstractJPARepository} using the 
     * specified class type.
     * 
     * @param   classType   the class type of the entity being managed by the
     *                      repository.
     */
    public AbstractJPARepository(Class<T> classType) {
        super(classType);
    }
    
    
    // --------------- Public getters ---------------
    
    
    /**
     * Gets the entity manager used to persist/load/remove/modify data.
     * 
     * @return  the entity manager.
     */
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Finds the entity for the specified id.
     * 
     * @param   id  that specifies the entity that will be found.
     * @return  The entity with the specified id.
     */
    @Override
    public final T find(int id) {
        return this.getEntityManager().find(this.getEntityClassType(), id);
    }
    
    
    /**
     * Merges the specified entity.
     * 
     * @param   entity              that will be updated.
     * @return  The updated entity.
     */
    @Override
    public final T merge(T entity) {
        // make sure the entity manager has knowledge of the entity
        entity = this.getEntityManager().merge(entity);
                
        // persist the actual entity
        //this.getEntityManager().persist(entity);
        
        return entity;
    }
    
    
    /**
     * Merges all specified entities.
     * 
     * @param   entities            that will be updated.
     * @return  The updated entities.
     */
    @Override
    public final Collection<T> merge(Collection<T> entities) {
        Collection<T> updatedEntities = this.createCollection();
        
        for(T entity : entities) {
            updatedEntities.add(this.merge(entity));
        }
        
        return updatedEntities;
    }
    
    
    /**
     * Persists the specified entity.
     * 
     * @param   entity          that will be saved
     * @return  The saved entity.
     */
    @Override
    public final T persist(T entity) {        
        // persist the actual entity
        this.getEntityManager().persist(entity);
        
        return entity;
    }
    
    
    /**
     * Persists all specified entities.
     * 
     * @param   entities        that will be saved.
     * @return  The saved entities.
     */
    @Override
    public final Collection<T> persist(Collection<T> entities) {     
        Collection<T> savedEntities = this.createCollection();
        
        for(T entity : entities) {
            savedEntities.add(this.persist(entity));
        }
        
        return savedEntities;
    }
    
    
    /**
     * Removes the specified entity.
     * 
     * @param   entity  that will be deleted.
     */
    @Override
    public final void remove(T entity) {
        // make sure the entity manager has knowledge of the entity
        entity = this.getEntityManager().merge(entity);
        
        // actually remove the entity
        this.getEntityManager().remove(entity);
    }
    
    
    /**
     * Removes all specified entities.
     * 
     * @param   entities  that will be deleted.
     */
    @Override
    public void remove(Collection<T> entities) {        
        for(T entity : entities) {
            this.remove(entity);
        }
    }
    
    
    // --------------- Protected methods ---------------
    
    /**
     * Accesses any named query using the specified query identifier.
     * 
     * @param   queryIdentifier that identifies the query within the entity
     *                          manager.
     * @return  The query specified by the identifier.
     */
    protected final Query accessQuery(String queryIdentifier) {
        return this.accessQuery(Object.class, queryIdentifier); 
    }
    
    
    /**
     * Accesses any named query using the specified result type and query 
     * identifier.
     * 
     * @param   <S>             that specifies the result type of the query.
     * @param   resultType      that defines the result type of the query.
     * @param   queryIdentifier that identifies the query within the entity
     *                          manager.
     * @return  The query specified by the identifier.
     */
    protected final <S extends Object> TypedQuery<S> accessQuery(
            Class<S> resultType, 
            String queryIdentifier) {
        return this.accessQuery(resultType, queryIdentifier, null); 
    }
    
    
    /**
     * Accesses any named query using the specified query identifier and 
     * parameters.
     * 
     * @param   queryIdentifier that identifies the query within the entity
     *                          manager.
     * @param   parameters      that are used during the execution of the query.
     * @return  The query specified by the identifier.
     */
    protected final Query accessQuery(
            String queryIdentifier, 
            Object... parameters) {
        return this.accessQuery(Object.class, queryIdentifier, parameters);
    }
    
    
    /**
     * Accesses any named query using the specified result type, query 
     * identifier and parameters.
     * 
     * @param   <S>             that specifies the result type of the query.
     * @param   resultType      that defines the result type of the query.
     * @param   queryIdentifier that identifies the query within the entity
     *                          manager.
     * @param   parameters      that are used during the execution of the query.
     * @return  The query specified by the identifier.
     */
    protected final <S extends Object> TypedQuery<S> accessQuery(
            Class<S> resultType,
            String queryIdentifier, 
            Object... parameters) {
        TypedQuery<S> typedQuery = 
                this.entityManager.createNamedQuery(queryIdentifier, resultType);
        
        if(parameters != null && parameters.length > 0)
            this.setQueryParameters(typedQuery, parameters);
        
        return typedQuery;
    }
    
    
    // --------------- Private methods ---------------
    
    
    /**
     * Sets all the parameters on a query.
     * 
     * @param   query       on which the parameters are going to be set.
     * @param   parameters  that will be set.
     */
    private void setQueryParameters(Query query, Object... parameters) {
        int parameterPlaceholder = 1;
        
        for (Object parameter : parameters) {
            query.setParameter(parameterPlaceholder, parameter);
            parameterPlaceholder++;
        }
    }
}
