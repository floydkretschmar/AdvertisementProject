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

import java.lang.reflect.ParameterizedType;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.convert.Converter;

/**
 *
 * @author fkre
 */
public class ConverterFactory {
    
    /**
     * Creates a new entity converter via injection through the CDI manager.
     * 
     * @param   injectionPoint    the point of the injection.
     * @param   converter         the injected converter.
     * @return  the fitting entity converter for the specified entity.
     */
    @Produces
    public Converter createEntityConverter(
            InjectionPoint injectionPoint,
            @New EntityConverter converter) {
        // see documentation in EntityServiceFactory
        
        ParameterizedType type = (ParameterizedType)injectionPoint.getType();
        Class entityIdType = (Class)type.getActualTypeArguments()[0];
        
        converter.setEntityIdType(entityIdType);
        
        return converter;
    }
}
