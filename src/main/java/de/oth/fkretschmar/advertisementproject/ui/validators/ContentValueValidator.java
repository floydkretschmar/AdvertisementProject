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

import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * Validates the specified content value.
 * 
 * @author Floyd
 */
@FacesValidator("contentValueValidator")
public class ContentValueValidator extends UrlValidator implements Serializable {
    
    /**
     * Validates the given value on whether or not it is a valid content value 
     * for the specified content value type.
     *
     * @param context the JSF context of the validation.
     * @param component the component of the validation.
     * @param value the value that is being validatet.
     * @throws ValidatorException that indicates that the validation is failed
     * and the value is not a valid content value.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) value;
        UIInput selectTypeMenu = (UIInput) component.getAttributes().get("param"); 
        
        if (password == null) {
            return; // Just ignore and let required="true" do its job.
        }

        Object paramValue = selectTypeMenu.getValue();
        
        if (!(paramValue instanceof ContentType) || ((ContentType)paramValue) == ContentType.UNDEFINED) {
            throw new ValidatorException(
                    new FacesMessage("The passed content type is not a valid content type."));
        }
        else if (((ContentType)paramValue) == ContentType.IMAGE_URL) {
            super.validate(context, component, value);
        }
    }

}
