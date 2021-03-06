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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author fkre
 */
@Dependent
@FacesConverter("de.oth.fkretschmar.advertisementproject.ui.converters.LocalDateConverter")
public class LocalDateConverter extends ExtendedTimeConverter<LocalDate> {
    
    // --------------- Public constructors ---------------

    /**
     * Creates a new instance of {@link LocalDateTimeConverter}.
     */
    public LocalDateConverter() {
        super(LocalDate.class);
    }

    // --------------- Protected methods ---------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Date convertToDate(LocalDate temporal) {
        return Date.from(temporal.atStartOfDay(
                ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LocalDate convertFromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
