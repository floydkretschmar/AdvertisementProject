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

import de.oth.fkretschmar.advertisementproject.entities.base.IEntity;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author fkre
 * @param   <T>     the entity type.
 */
public abstract class AbstractEntityConverter<T extends IEntity<?>> 
            implements Converter, Serializable {

    // --------------- Public methods ---------------
    
    
    /**
     * Converts the string value to the corresponding entity.
     * 
     * @param context       the context of the conversion.
     * @param component     the component of the value to be converted.
     * @param value         the value that will be converted.
     * @return  the converted object.
     */
    @Override
    public Object getAsObject(
            FacesContext context,
            UIComponent component,
            String value) {
        if (value == null) {
            return "";
        }

        T entity = this.find(value);
        
        if (entity == null) {
            return "";
        }
        return entity;
    }

    
    /**
     * Converts the given object into an identifying string value.
     * 
     * @param context       the context of the conversion.
     * @param component     the component of the value to be converted.
     * @param value         the value that will be converted.
     * @return  the converted string.
     */
    @Override
    public String getAsString(
            FacesContext context,
            UIComponent component,
            Object value) {
        if (value == null) {
            return null;
        }
        if (!(value instanceof Account)) {
            return null;
        }

        return ((Account)value).getId();
    }

    // --------------- Protected methods ---------------
    
    
    /**
     * Finds the entity defined by the specified id.
     * 
     * @param   id  the id that defines the entity.
     * @return  the entity.
     */
    protected abstract T find(String id);
}
