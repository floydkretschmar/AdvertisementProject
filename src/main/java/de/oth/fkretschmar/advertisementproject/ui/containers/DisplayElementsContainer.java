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

import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.faces.component.FacesComponent;

/**
 *
 * @author Admin
 */
@FacesComponent("displayElementsContainer")
public class DisplayElementsContainer extends CreateEntityContainer {

    // --------------- Public methods ---------------
    
    /**
     * Filters the elements list using the specified criteria.
     * 
     * @param elements  the elements that will be filtered.
     * @param criteria  the criteria to apply.
     * @param selector  that specifies the type of elements. 
     * @return  the filtered collection.
     */
    public Collection<?> filterElementsForCriteria(
            Collection<Object> elements, 
            Object criteria, 
            String selector) {
        if (selector.equals("content")) {
            return elements.stream()
                .map(element -> (Content)element)
                .filter(content -> content.getContentType() == ContentType.getContentType(criteria.toString()))
                .sorted((content1, content2)
                        -> content1.getContentType().name().compareTo(content2.getContentType().name()))
                .collect(Collectors.toList());
        }
        
        return null;
    }
}