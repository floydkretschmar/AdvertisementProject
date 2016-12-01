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
package de.oth.fkretschmar.advertisementproject.entities.base;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts a provided local date to the corresponding date when 
 * working with JPA.
 * 
 * Implementation as seen at http://www.thoughts-on-java.org/persist-localdate-
 * localdatetime-jpa/
 *
 * @author fkre
 */
@Converter
public class LocalDateAttributeConverter 
        implements AttributeConverter<LocalDate, Date> {

    /**
     * Converts a provided {@link LocalDate} to a {@link Date} that will be used
     * within for storage within the database.
     * 
     * @param   localDate   the {@link LocalDate} that will be converted.
     * @return              the converted {@link Date}.
     */
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
    	return (localDate == null ? null : Date.valueOf(localDate));
    }

    /**
     * Converts a provided {@link Date} to a {@link LocalDate} that will be used
     * within the java program.
     * 
     * @param   sqlDate the {@link Date} that will be converted.
     * @return  the converted {@link LocalDate}
     */
    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
    	return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
    
}
