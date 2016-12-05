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

import java.util.EnumSet;
import javax.persistence.AttributeConverter;

/**
 * Defines conversion operations for enum attributes that are representing flag
 * fields.
 * 
 * @author  Floyd Kretschmar
 * @param   <T>     the type of the enum that is being managed by the enum set 
 *                  that will be converted.
 */
public abstract class FlagFieldAttributeConverter<T extends Enum<T> & IFlagField> 
        implements AttributeConverter<EnumSet<T>, Long> {
    
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the type of the enum being managed by the enum set that is being
     * converted by this converter.
     */
    private Class<T> enumType;
    
    // --------------- Protected constructor ---------------
    
    /**
     * Creates a new instance of {@link FlagFieldAttributeConverter} using the
     * specified enum type.
     * 
     * @param enumType the type of the enum being managed by the enum set that 
     *                 is being converted by this converter.
     */
    protected FlagFieldAttributeConverter(Class<T> enumType) {
        this.enumType = enumType;
    }
    
    
    // --------------- Public methods ---------------

    /**
     * Converts a provided enum set of {@link IFlagField} to a {@link Long} 
     * that will be used within for storage within the database.
     * 
     * @param   targetGroups  the set that will be converted.
     * @return                the converted {@link Integer}.
     */
    @Override
    public Long convertToDatabaseColumn(EnumSet<T> targetGroups) {
    	Long totalValue = 0l;
        
        for (IFlagField targetGroup : targetGroups) {
            totalValue |= targetGroup.getFlagValue();
        }
        
        return totalValue;
    }

    /**
     * Converts a provided {@link Long} to a enum set of {@link IFlagField} 
     * that will be used within for storage within the database.
     * 
     * @param   flagField   the flag field that will be converted.
     * @return              the converted enum set of {@link IFlagField}.
     */
    @Override
    public EnumSet<T> convertToEntityAttribute(Long flagField) {
        EnumSet<T> returnSet = EnumSet.noneOf(this.enumType);
        
        for (T enumValue : this.getAllEnumValues()) {
            int enumFlagValue = enumValue.getFlagValue();
            if ((enumFlagValue&flagField) == enumFlagValue)
            {
               returnSet.add(enumValue);
            }
        }
        
        return returnSet;
    }
    
    
    // --------------- Protected methods ---------------
    
    /**
     * Gets all the values of the enum being managed by the enum set.
     * 
     * @return an array of all the enum fields.
     **/
    protected abstract T[] getAllEnumValues();
}
