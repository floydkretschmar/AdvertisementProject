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

import de.oth.fkretschmar.advertisementproject.business.services.base.IEntityService;
import de.oth.fkretschmar.advertisementproject.entities.base.IEntity;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import lombok.AccessLevel;
import lombok.Setter;

/**
 *
 * @author fkre
 * @param <T> the entity type.
 */
public class EntityConverter<T extends IEntity<?>>
        implements Converter, Serializable, IConverter<T> {

    // --------------- Private fields ---------------
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Setter(AccessLevel.PACKAGE)
    private IEntityService<T> entityService;

    /**
     * Stores the class type of the entity.
     */
    @Setter(AccessLevel.PACKAGE)
    private Class<T> entityType;

    // --------------- Public methods ---------------
    /**
     * Converts the string value to the corresponding entity.
     *
     * @param context the context of the conversion.
     * @param component the component of the value to be converted.
     * @param value the value that will be converted.
     * @return the converted object.
     */
    @Override
    public Object getAsObject(
            FacesContext context,
            UIComponent component,
            String value) {
        if (value == null) {
            return "";
        }

        T entity = this.entityService.find(value);

        if (entity == null) {
            return "";
        }
        return entity;
    }

    /**
     * Converts the given object into an identifying string value.
     *
     * @param context the context of the conversion.
     * @param component the component of the value to be converted.
     * @param value the value that will be converted.
     * @return the converted string.
     */
    @Override
    public String getAsString(
            FacesContext context,
            UIComponent component,
            Object value) {
        if (value == null) {
            return null;
        }

        Class<?> nextClass = value.getClass();
        boolean hasFoundBaseEntity = false;

        // the entity converter could be converting base classes like for example
        // Account -> also lookup superclasses recursively if the value class is
        // no match.
        while (nextClass != null && !hasFoundBaseEntity) {
            if (nextClass.equals(this.entityType)) {
                hasFoundBaseEntity = true;
            }
            nextClass = nextClass.getSuperclass();
        }

        if (!hasFoundBaseEntity) {
            return null;
        }

        return entityType.cast(value).getId().toString();
    }
}
