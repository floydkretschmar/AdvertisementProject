/*
 * Copyright (C) 2016 Admin
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
package de.oth.fkretschmar.advertisementproject.ui.models;

import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 *
 * @author Admin
 */
@Named
@ConversationScoped
public class ContentModel extends AbstractModel {

    
    // --------------- Public methods ---------------
    
    /**
     * Will filter the list of contents for the content type specified.
     * 
     * @param contents    the contents that will be filtered.
     * @param contentTypeName   the well formatted name that represents the 
     *                          content type.
     * @return 
     */
    public Collection<Content> getContentWithType(Collection<Content> contents, String contentTypeName) {
        ContentType contentType = ContentType.getContentType(contentTypeName);
        
        // combined IMAGE and IMAGE_URL into one group for the sake of more 
        // intuitive display in the JSF -> take that into account when filtering
        // contents        
        return contents.stream()
                .filter(content -> content.getContentType() == contentType ||
                                (contentType == ContentType.IMAGE 
                                    && content.getContentType() == ContentType.IMAGE_URL))
                .sorted((content1, content2) -> 
                        content1.getContentType().name().compareTo(content2.getContentType().name()))
                .collect(Collectors.toList());
    }
    
    
    
    /**
     * Gets the types of contents that exist for a specified campaign.
     * 
     * @param   contents    the contents for which the content types will be 
     *                      extracted.
     * @return 
     */
    public Collection<String> getContentTypesForContents(Collection<Content> contents) {        
        List<String> contentTypes = contents.stream()
                .sorted((content1, content2) -> 
                        content1.getContentType().name().compareTo(content2.getContentType().name()))
                .map(content -> 
                {
                        String formattedName = ContentType.getFormattedName(content.getContentType());
                        if(formattedName.equals(ContentType.IMAGE_URL_FORMATTED_NAME))
                            return ContentType.IMAGE_FORMATTED_NAME;
                        return formattedName;
                })
                .distinct()
                .collect(Collectors.toList());
        
        return contentTypes;
    }
}
