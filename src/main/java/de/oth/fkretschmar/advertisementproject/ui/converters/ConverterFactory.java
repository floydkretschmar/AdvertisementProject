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
package de.oth.fkretschmar.advertisementproject.ui.converters;

import de.oth.fkretschmar.advertisementproject.business.services.base.IAccountService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import java.lang.reflect.ParameterizedType;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import de.oth.fkretschmar.advertisementproject.ui.annotations.ConverterInjection;

/**
 *
 * @author fkre
 */
@Dependent
public class ConverterFactory {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the service used to convert accounts.
     */
    @Inject
    private IAccountService accountService;
    
    // --------------- Public methods ---------------
    
    /**
     * Creates a new entity converter via injection through the CDI manager.
     * 
     * @param   <T>                 the entity type.
     * @param   injectionPoint      the point of the injection.
     * @param   accountConverter    the injected converter for accounts.
     * @param   dateConverter       the injected converter for local dates.
     * @return  the fitting entity  converter for the specified entity.
     */
    @Produces
    @ConverterInjection
    public <T> IConverter<T> createEntityConverter(
            InjectionPoint injectionPoint,
            @New EntityConverter<Account> accountConverter,
            @New LocalDateConverter dateConverter) {
        // see documentation in EntityServiceFactory
        ParameterizedType type = (ParameterizedType)injectionPoint.getType();
        Class entityType = (Class)type.getActualTypeArguments()[0];
        
        if (entityType.equals(Account.class)) {
            accountConverter.setEntityService(this.accountService);
            accountConverter.setEntityType(entityType);
            return (IConverter<T>)accountConverter;
        }
        
        return null;
    }
}
