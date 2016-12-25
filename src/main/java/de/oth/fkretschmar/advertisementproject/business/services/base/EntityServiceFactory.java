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
package de.oth.fkretschmar.advertisementproject.business.services.base;

import de.oth.fkretschmar.advertisementproject.business.services.AccountService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import java.lang.reflect.ParameterizedType;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author fkre
 */
@Dependent
public class EntityServiceFactory {
    
    /**
     * Creates a new entity service via injection through the CDI manager.
     * 
     * @param injectionPoint    the point of the injection.
     * @param accountService    an account service.
     * @return  the fitting entity service for the specified entity.
     */
    @Produces
    public IEntityService createEntityService(
            InjectionPoint injectionPoint,
            @New AccountService accountService) {
        // reflection being sexy as fuck:
        
        // get the type of the class that is being injected
        ParameterizedType type = (ParameterizedType)injectionPoint.getType();
        
        // we know we are injecting an IEntityService the second type argument
        // is the entity for which the service is working
        Class entityClass = (Class)type.getActualTypeArguments()[1];
        
        // if the entity is an entity -> return an service that manages that entity
        // being injected by the CDI manager to make sure all the injections 
        // on the service are working.
        if(entityClass.isInstance(Account.class)) {
            return accountService;
        }
        
        return null;
    }
}
