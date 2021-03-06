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
package de.oth.fkretschmar.advertisementproject.ui.containers;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Admin
 */
@FacesComponent("createEntityContainer")
public class CreateEntityContainer extends UINamingContainer {

    /**
     * Invokes the specified notified listener passing the attribute.
     * @param   event   the ajax event of the listener.
     */
    public void notifiedListener(AjaxBehaviorEvent event) {
        MethodExpression notifiedListener = (MethodExpression) getAttributes().get("notifiedListener");
        Object entity = (Object) event.getComponent().getAttributes().get("entity");
        
        if (entity != null)
            notifiedListener.invoke(getFacesContext().getELContext(), new Object[] { entity });
    }
}