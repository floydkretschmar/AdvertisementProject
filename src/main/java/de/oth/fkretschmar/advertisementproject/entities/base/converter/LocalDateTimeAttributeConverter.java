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
package de.oth.fkretschmar.advertisementproject.entities.base.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts a provided local date time to the corresponding time stamp when 
 * working with JPA.
 * 
 * Implementation as seen at http://www.thoughts-on-java.org/persist-localdate-
 * localdatetime-jpa/
 *
 * @author fkre
 */
@Converter
public class LocalDateTimeAttributeConverter 
        implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * Converts a provided {@link LocalDateTime} to a {@link Timestamp} that will be used
     * within for storage within the database.
     * 
     * @param   localDateTime   the {@link LocalDateTime} that will be converted.
     * @return                  the converted {@link Timestamp}.
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
    	return (localDateTime == null ? null : Timestamp.valueOf(localDateTime));
    }

    /**
     * Converts a provided {@link Timestamp} to a {@link LocalDateTime} that will be used
     * within the java program.
     * 
     * @param   sqlTimestamp the {@link Timestamp} that will be converted.
     * @return  the converted {@link LocalDateTime}
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
    	return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }
    
}
