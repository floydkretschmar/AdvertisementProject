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

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import java.lang.reflect.ParameterizedType;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author fkre
 */
public class ConverterFactory {
    
    /**
     * Creates a new entity converter via injection through the CDI manager.
     * 
     * @param   injectionPoint    the point of the injection.
     * @param   accountConverter   the injected converter for accounts.
     * @return  the fitting entity converter for the specified entity.
     */
    @Produces
    public IEntityConverter createEntityConverter(
            InjectionPoint injectionPoint,
            @New EntityConverter<String, Account> accountConverter) {
        // see documentation in EntityServiceFactory
        ParameterizedType type = (ParameterizedType)injectionPoint.getType();
        Class entityIdType = (Class)type.getActualTypeArguments()[0];
        Class entityType = (Class)type.getActualTypeArguments()[1];
        
        if (entityType.isInstance(Account.class)) {
            accountConverter.setEntityIdType(entityIdType);
            return accountConverter;
        }
        
        return null;
    }
}
