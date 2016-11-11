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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Represents an repository that defines the default CRUD methods when using
 * JPA
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     The type that specifies which entity is being managed by the
 *                  repository.
 * @param   <S>     The type of the collection that multiple entities that are 
 *                  being managed by this repository can be stored in.
 */
@Dependent
public abstract class AbstractJPARepository<T extends IEntity, S extends Collection<T>> 
        extends AbstractRepository<T, S> {
    
    // --------------- Private fields ---------------

    /**
     * Stores the entity manager used to persist/load/remove/modify data.
     */
    @PersistenceContext(name = "FKREWS1617_PU")
    private EntityManager entityManager;

    
    // --------------- Public constructor ---------------
    
    
    /**
     * Creates a new instance of {@link JPARepository} using the specified class
     * type.
     * 
     * @param classType 
     */
    public AbstractJPARepository(Class<T> classType) {
        super(classType);
    }
    
    
    // --------------- Public getters ---------------
    
    
    /**
     * Gets the entity manager used to persist/load/remove/modify data.
     * 
     * @return 
     */
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Deletes the specified entity.
     * 
     * @param   entity  that will be deleted.
     */
    @Override
    public final void delete(T entity) {
        // make sure the entity manager has knowledge of the entity
        entity = this.getEntityManager().merge(entity);
        
        // actually remove the entity
        this.getEntityManager().remove(entity);
    }
    
    
    /**
     * Finds the entity for the specified id.
     * 
     * @param   id  that specifies the entity that will be found.
     * @return  The entity with the specified id.
     */
    @Override
    public final T find(int id) {
        return this.getEntityManager().find(this.getClassType(), id);
    }
    
    
    /**
     * Saves the specified entity.
     * 
     * @param   entity          that will be saved
     * @return  The saved entity.
     */
    @Override
    public final T save(T entity) {
        // allow subclasses to save their specific information
        entity = this.saveCore(entity);
        
        // save the actual entity
        this.getEntityManager().persist(entity);
        
        return entity;
    }
    
    
    /**
     * Saves all specified entities.
     * 
     * @param   entities        that will be saved.
     * @return  The saved entities.
     */
    @Override
    public final S save(S entities) {     
        S savedEntities = this.createCollection();
        
        for(T entity : entities) {
            savedEntities.add(this.save(entity));
        }
        
        return savedEntities;
    }
    
    
    /**
     * Updates the specified entity.
     * 
     * @param   entity              that will be updated.
     * @return  The updated entity.
     */
    @Override
    public final T update(T entity) {
        // make sure the entity manager has knowledge of the entity
        entity = this.getEntityManager().merge(entity);
        
        // allow subclasses to update their specific information
        entity = this.updateCore(entity);
                
        // save the actual entity
        this.getEntityManager().persist(entity);
        
        return entity;
    }
    
    
    /**
     * Updates all specified entities.
     * 
     * @param   entities            that will be updated.
     * @return  The updated entities.
     */
    @Override
    public final S update(S entities) {
        S updatedEntities = this.createCollection();
        
        for(T entity : entities) {
            updatedEntities.add(this.update(entity));
        }
        
        return updatedEntities;
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
    
    
    /**
     * Creates a collection of the managed entity.
     * 
     * @return  A collection that can store multiple entries of the managed
     *          entity.
     */
    protected abstract S createCollection();
    
    
    /**
     * Performs all the subsequent saves that are needed to make sure the 
     * relationships of the entity are in a persistent state before saving the
     * entity.
     * 
     * @param   entity  whose relationships have to be brought into a persistent
     *                  state
     * @return  The entity with its relationships in a persistent state.
     */
    protected T saveCore(T entity) {
        // the default implementation has no relationships to manage
        return entity;
    }
    
    
    /**
     * Performs all the subsequent updates that are needed to make sure the 
     * relationships of the entity are in a persistent state before updating the
     * entity.
     * 
     * @param   entity  whose relationships have to be brought into a persistent
     *                  state
     * @return  The entity with its relationships in a persistent state.
     */
    protected T updateCore(T entity) {
        // the default implementation has no relationships to manage
        return entity;
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
