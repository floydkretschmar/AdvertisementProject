/*
 * Copyright (C) 2017 Admin
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
package de.oth.fkretschmar.advertisementproject.ui;

import de.oth.fkretschmar.advertisementproject.ui.annotations.MessageBundle;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * Implementation as seen http://stackoverflow.com/questions/28045667/injecting-
 * resourcebundle-via-managedproperty-doesnt-seem-to-work-inside-named
 *
 * @author Admin
 */
@Dependent
public class MessageBundleFactory {

    /**
     * Injects the resource bundle that stores message translations.
     *
     * @return the resource bundle.
     */
    @Produces
    @MessageBundle
    public ResourceBundle getMessageBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(
                context,
                "#{messages}",
                PropertyResourceBundle.class);
    }
}
