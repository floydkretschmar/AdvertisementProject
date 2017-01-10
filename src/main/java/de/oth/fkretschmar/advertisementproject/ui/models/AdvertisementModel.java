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
package de.oth.fkretschmar.advertisementproject.ui.models;

import de.oth.fkretschmar.advertisementproject.business.services.base.IContentProviderService;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import java.io.Serializable;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * 
 * @author Floyd
 */
@Named
@ViewScoped
public class AdvertisementModel implements Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the advertisement content.
     */
    @Getter
    private Content content;
    

    /**
     * Stores the service used to manage requests for {@link Content} entites.
     */
    @Inject
    private IContentProviderService contentService;
    
    
    // --------------- Public methods ---------------
    
    /**
     * Requests a random content from the database for display on every page.
     */
    @PostConstruct
    private void initializeContent() {
        Optional<Content> requestedContent 
                = this.contentService.requestRandomContent("webvert");
        
        if (requestedContent.isPresent()) {
            this.content = requestedContent.get();
        }
        else {
            this.content = null;
        }
    }
}
