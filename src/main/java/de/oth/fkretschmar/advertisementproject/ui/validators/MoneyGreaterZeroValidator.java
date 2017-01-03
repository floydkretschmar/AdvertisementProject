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

import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * Validates the specified monetary amount.
 *
 *
 * @author Floyd
 */
@FacesValidator("moneyGreaterZeroValidator")
public class MoneyGreaterZeroValidator extends GreaterZeroValidator {

    /**
     * Validates the given monetary value on whether at least one of its two 
     * parts is greater than zero.
     *
     * @param context the JSF context of the validation.
     * @param component the component of the validation.
     * @param value the value that is being validatet.
     * @throws ValidatorException that indicates that the validation is failed
     * and the value is not a valid monetary value greater than zero.
     */
    @Override
    public void validate(
            FacesContext context,
            UIComponent component,
            Object value) throws ValidatorException {
        if (value == null || component.getAttributes().get("otherPart") == null)
            return;
        
        try {
            BigDecimal preDecimalPointAmount = new BigDecimal(value.toString());
            BigDecimal postDecimalPointAmount = new BigDecimal(component.getAttributes().get("otherPart").toString());
        
            if (preDecimalPointAmount.signum() == 0 && postDecimalPointAmount.signum() == 0) {
                FacesMessage msg = new FacesMessage("Validation failed.",
                        "Not both parts of the monetary amount can be zero.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        } catch (NumberFormatException ex) {
            FacesMessage msg = new FacesMessage("Validation failed.", "Not a number");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
