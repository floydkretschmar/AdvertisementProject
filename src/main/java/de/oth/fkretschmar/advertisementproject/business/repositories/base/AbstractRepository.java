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

import de.oth.fkretschmar.advertisementproject.entities.base.EntityState;
import de.oth.fkretschmar.advertisementproject.entities.base.IEntity;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.Getter;
import de.oth.fkretschmar.advertisementproject.entities.base.IDeletable;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Represents an repository that defines the default CRUD methods when using
 * the Java Persistance API.
 * 
 * @author  fkre    Floyd Kretschmar
 * @param   <T>     The type that specifies which entity is being managed by the
 *                  repository.
 */
public abstract class AbstractRepository<S, T extends Object & IEntity<S>> {
    
    // --------------- Private static fields ---------------
    
    private static final String PERSISTENCE_ID = "FKREWS1617_PU";
    
    // --------------- Private fields ---------------

    /**
     * Stores the type of the entity.
     */
    private Class<T> entityType;
    
    /**
     * Stores the entity manager used to persist/load/remove/modify data.
     */
    @PersistenceContext(name = AbstractRepository.PERSISTENCE_ID)
    @Getter(AccessLevel.PROTECTED)
    private EntityManager entityManager;

    
    // --------------- Public constructor ---------------
    
    
    /**
     * Creates a new instance of {@link AbstractJPARepository} using the 
     * specified class type.
     * 
     * @param   entityType   the class type of the entity being managed by the
     *                      repository.
     */
    public AbstractRepository(Class<T> entityType) {
        this.entityType = entityType;
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Finds the entity for the specified id.
     * 
     * @param   id  that specifies the entity that will be found.
     * @return  The entity with the specified id.
     */
    public final T find(S id) {
        return this.getEntityManager().find(this.entityType, id);
    }
    
    
    /**
     * Finds all of the entities of this type.
     * 
     * @return  the collection of entities.
     */
    public final List<T> findAll() {
        CriteriaBuilder criteriaBuilder 
                = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entityType);
        Root<T> rootEntry = criteriaQuery.from(this.entityType);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = this.getEntityManager().createQuery(all);

        return (List<T>) allQuery.getResultList();
    }
    
    
    /**
     * Merges the specified entity.
     * 
     * @param   entity              that will be updated.
     * @return  The updated entity.
     */
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
     */
    public final void persist(T entity) {        
        // persist the actual entity
        this.getEntityManager().persist(entity);
    }
    
    
    /**
     * Persists all specified entities.
     * 
     * @param   entities        that will be saved.
     */
    public final void persist(Collection<T> entities) {     
        for(T entity : entities) {
            this.persist(entity);
        }
    }
    
    
    /**
     * Removes the specified entity.
     * 
     * @param   entity  that will be deleted.
     */
    public final void remove(T entity) {
        // make sure the entity manager has knowledge of the entity
        entity = this.merge(entity);
        
        // only actually remove the entity if it is IDeletable
        if(entity instanceof IDeletable) {
            this.getEntityManager().remove(entity);
        } 
        else {
            // otherwise just set the state to deleted
            entity.setState(EntityState.DELETED);
        }
    }
    
    
    /**
     * Removes all specified entities.
     * 
     * @param   entities  that will be deleted.
     */
    public final void remove(Collection<T> entities) {       
        for(T entity : entities) {
            this.remove(entity);
        }
    }
    
    
    // --------------- Protected methods ---------------
    
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
     * Create a query using the specified result type and query string.
     * 
     * @param   <S>             that specifies the result type of the query.
     * @param   resultType      that defines the result type of the query.
     * @param   queryString     that contains the SQL statement.
     * @return  The query specified by the identifier.
     */
    protected final <S extends Object> TypedQuery<S> createQuery(
            Class<S> resultType, 
            String queryString) {
        return this.createQuery(resultType, queryString, null); 
    }
    
    
    /**
     * Create a query using the specified result type, query string and 
     * parameters.
     * 
     * @param   <S>             that specifies the result type of the query.
     * @param   resultType      that defines the result type of the query.
     * @param   queryString     that contains the SQL statement.
     * @param   parameters      that are used during the execution of the query.
     * @return  The query specified by the identifier.
     */
    protected final <S extends Object> TypedQuery<S> createQuery(
            Class<S> resultType,
            String queryString, 
            Object... parameters) {
        TypedQuery<S> typedQuery = 
                this.entityManager.createQuery(queryString, resultType);
        
        if(parameters != null && parameters.length > 0)
            this.setQueryParameters(typedQuery, parameters);
        
        return typedQuery;
    }
    
    
    // --------------- Protected methods ---------------

    /**
     * Creates a collection of the managed entity.
     * 
     * @return  A collection that can store multiple entries of the managed
     *          entity.
     */
    protected abstract Collection<T> createCollection();
    
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
