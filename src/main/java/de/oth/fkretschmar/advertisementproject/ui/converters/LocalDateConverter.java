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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.enterprise.context.Dependent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 *
 * Implementation as seen at http://stackoverflow.com/questions/30395398/
 * fconvertdatetime-support-for-java8-localdate-localdatetime
 *
 * @author fkre
 */
@Dependent
@FacesConverter("de.oth.fkretschmar.advertisementproject.ui.converters.LocalDateConverter")
public class LocalDateConverter extends DateTimeConverter implements IConverter<LocalDate> {

    // --------------- Public methods ---------------
    /**
     * Converts the string value to the corresponding local date.
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
        Object object = super.getAsObject(context, component, value);

        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            Date date = (Date) object;
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(
                    instant,
                    ZoneId.systemDefault());
            return localDateTime;
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "value=%s could not be converted to a "
                            + "LocalDate, result super.getAsObject=%s",
                            value,
                            object));
        }
    }

    /**
     * Converts the given local date into an identifying string value.
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
            return super.getAsString(context, component, value);
        }
        
        if (value instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime)value;
            Instant instant = localDateTime.atZone(ZoneId.systemDefault())
                                       .toInstant();
            Date date = Date.from(instant);
            return super.getAsString(context, component, date);
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "value=%s is not a instanceof LocalDate", 
                            value));
        }
    }
}
