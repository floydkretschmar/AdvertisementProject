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

import java.util.Locale;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
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
public class MonetaryAmountAttributeConverter 
        implements AttributeConverter<MonetaryAmount, String> {

    /**
     * Converts a provided {@link MonetaryAmount} to a {@link String} that will 
     * be used within for storage within the database.
     * 
     * @param   monetaryAmount      the {@link MonetaryAmount} that will be 
     *                              converted.
     * @return                      the converted {@link String}.
     */
    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        MonetaryAmountFormat germanFormat 
                = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        return germanFormat.format(monetaryAmount);
    }

    /**
     * Converts a provided {@link String} to a {@link MonetaryAmount} that will 
     * be used within the java program.
     * 
     * @param   moneyString the {@link String} that will be converted.
     * @return  the converted {@link MonetaryAmount}
     */
    @Override
    public MonetaryAmount convertToEntityAttribute(String moneyString) {
         MonetaryAmountFormat germanFormat 
                = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        
        return germanFormat.parse(moneyString);
    }
    
}
