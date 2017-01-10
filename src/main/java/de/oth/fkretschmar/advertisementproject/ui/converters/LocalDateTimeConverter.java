/*
 * Copyright (C) 2017 fkre
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author fkre
 */
@Dependent
@FacesConverter("de.oth.fkretschmar.advertisementproject.ui.converters.LocalDateTimeConverter")
public class LocalDateTimeConverter extends ExtendedTimeConverter<LocalDateTime> {
    
    // --------------- Public constructors ---------------

    /**
     * Creates a new instance of {@link LocalDateTimeConverter}.
     */
    public LocalDateTimeConverter() {
        super(LocalDateTime.class);
    }

    // --------------- Protected methods ---------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Date convertToDate(LocalDateTime temporal) {
        Instant instant = temporal.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LocalDateTime convertFromDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(
                instant,
                ZoneId.systemDefault());
    }
}
