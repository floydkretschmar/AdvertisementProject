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

import java.util.Locale;
import javax.enterprise.context.Dependent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.joda.money.Money;
import org.joda.money.format.MoneyAmountStyle;
import org.joda.money.format.MoneyFormatException;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

/**
 *
 * Implementation as seen at http://stackoverflow.com/questions/30395398/
 * fconvertdatetime-support-for-java8-localdate-localdatetime
 *
 * @author fkre
 */
@Dependent
@FacesConverter("de.oth.fkretschmar.advertisementproject.ui.converters.MoneyConverter")
public class MoneyConverter implements Converter {

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
        try {
            MoneyFormatterBuilder formaterBuilder = new MoneyFormatterBuilder().appendAmount(MoneyAmountStyle.LOCALIZED_GROUPING).appendCurrencySymbolLocalized();
            MoneyFormatter formater = formaterBuilder.toFormatter(Locale.GERMANY);
            return formater.parseMoney(value);
        } catch (MoneyFormatException exception) {
            throw new IllegalArgumentException(
                    String.format(
                            "value=%s could not be converted to a "
                            + "Money object",
                            value));
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

        if (!(value instanceof Money)) {
            throw new IllegalArgumentException(
                    String.format(
                            "value=%s is not an instance of Money",
                            value.toString()));
        }

        try {
            MoneyFormatterBuilder formaterBuilder = new MoneyFormatterBuilder().appendAmount(MoneyAmountStyle.LOCALIZED_GROUPING).appendCurrencySymbolLocalized();
            MoneyFormatter formater = formaterBuilder.toFormatter(Locale.GERMANY);
            return formater.print((Money) value);
        } catch (MoneyFormatException exception) {
            throw new IllegalArgumentException(
                    String.format(
                            "value=%s could not be converted to a "
                            + "String",
                            value.toString()));
        }
    }
}
