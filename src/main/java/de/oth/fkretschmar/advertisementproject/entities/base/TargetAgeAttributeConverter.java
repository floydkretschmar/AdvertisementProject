/*
 * Copyright (C) 2016 Floyd
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

import de.oth.fkretschmar.advertisementproject.entities.TargetAge;
import javax.persistence.Converter;

/**
 *
 * @author Floyd
 */
@Converter
public class TargetAgeAttributeConverter extends FlagFieldAttributeConverter<TargetAge> {
    
    // --------------- Public constructors ---------------

    /**
     * Creates a new instance of {@link TargetAgeAttributeConverter}.
     */
    public TargetAgeAttributeConverter() {
        super(TargetAge.class);
    }
    
    // --------------- Protected methods ---------------
    
    /**
     * Gets all values of the {@link TargetAge} enum.
     * @return  an array with all {@link TargetAge} enum values.
     */
    @Override
    protected TargetAge[] getAllEnumValues() {
        return TargetAge.values();
    }
}
