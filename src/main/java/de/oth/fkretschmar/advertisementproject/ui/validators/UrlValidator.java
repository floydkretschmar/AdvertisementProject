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
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validates the specified url.
 *
 * @author Floyd
 */
@FacesValidator("urlValidator")
public class UrlValidator implements Validator {

    /**
     * Validates the given value on whether or not it is an URL.
     *
     * @param context the JSF context of the validation.
     * @param component the component of the validation.
     * @param value the value that is being validatet.
     * @throws ValidatorException that indicates that the validation is failed
     * and the value is an URL.
     */
    @Override
    public void validate(
            FacesContext context,
            UIComponent component,
            Object value) throws ValidatorException {
        boolean validate = (boolean)component.getAttributes().get("validate");
        if (value != null && validate) {
            try {
                URL url = new URL(value.toString());
            } catch (MalformedURLException ex) {
                FacesMessage msg = new FacesMessage(
                        "Validation failed.", "Not an URL");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

}
