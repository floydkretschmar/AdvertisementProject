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
package de.oth.fkretschmar.advertisementproject.ui.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.iban4j.BicFormatException;
import org.iban4j.BicUtil;
import org.iban4j.UnsupportedCountryException;

/**
 * Validates the specified BIC.
 *
 * @author Floyd
 */
@FacesValidator("bicValidator")
public class BicValidator implements Validator {

    /**
     * Validates the given value on whether or not it is a BIC.
     *
     * @param context the JSF context of the validation.
     * @param component the component of the validation.
     * @param value the value that is being validatet.
     * @throws ValidatorException that indicates that the validation is failed
     * and the value is not a BIC.
     */
    @Override
    public void validate(
            FacesContext context,
            UIComponent component,
            Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        try {
            BicUtil.validate(value.toString());
        } catch (BicFormatException formatEx) {
            FacesMessage msg = new FacesMessage(
                    "Validation failed.", 
                    "Not a valid BIC format");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } catch (UnsupportedCountryException unsupportedEx) {
            FacesMessage msg = new FacesMessage(
                    "Validation failed.", 
                    "The country of the BIC is unsupported");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
