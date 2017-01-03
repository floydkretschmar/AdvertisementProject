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

import java.net.MalformedURLException;
import java.net.URL;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;

/**
 * Validates the specified IBAN.
 *
 * @author Floyd
 */
@FacesValidator("ibanValidator")
public class IbanValidator implements Validator {

    /**
     * Validates the given value on whether or not it is an IBAN.
     *
     * @param context the JSF context of the validation.
     * @param component the component of the validation.
     * @param value the value that is being validatet.
     * @throws ValidatorException that indicates that the validation is failed
     * and the value is not an IBAN.
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
            IbanUtil.validate(value.toString());
        } catch (IbanFormatException formatEx) {
            FacesMessage msg = new FacesMessage(
                    "Validation failed.", 
                    "Not a valid IBAN format");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } catch (InvalidCheckDigitException checksumEx) {
            FacesMessage msg = new FacesMessage(
                    "Validation failed.", 
                    "The checksum of the IBAN is invalid");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } catch (UnsupportedCountryException unsupportedEx) {
            FacesMessage msg = new FacesMessage(
                    "Validation failed.", 
                    "The country of the IBAN is unsupported");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
