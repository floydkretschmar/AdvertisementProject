/*
 * Copyright (C) 2016 fkre
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
package de.oth.fkretschmar.advertisementproject.business.services;

import de.oth.fkretschmar.advertisementproject.business.repositories.CampaignRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.TargetContextRepository;

import de.oth.fkretschmar.advertisementproject.entities.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.Content;
import de.oth.fkretschmar.advertisementproject.entities.TargetContext;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Campaign} instances.
 *
 * @author fkre
 */
@RequestScoped
public class ContentService implements Serializable {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Campaign} entites.
     */
    @Inject
    private CampaignRepository campaignRepository;

    /**
     * Stores the repository used to manage {@link Content} entites.
     */
    @Inject
    private ContentRepository contentRepository;

    /**
     * Stores the repository used to manage {@link TargetContext} entites.
     */
    @Inject
    private TargetContextRepository contextRepository;
    

    // --------------- Public methods ---------------
    
    
    /**
     * Creates a new {@link Content} and links it to the specified campaign.
     * 
     * @param   campaign    to which the content will be linked.
     * @param   content     the content that will be saved.
     * @return  the created {@link Content}.
     */
    @Transactional
    public Campaign createContentForCampaign(
            Campaign campaign, Content content) {
        this.createContent(content);
        
        // 3. add the content to the campaign
        campaign = this.campaignRepository.merge(campaign);
        campaign.addContent(content);
        return campaign;
    }
    
    
    // --------------- Package-Private methods ---------------
    
    
    /**
     * Creates a new content.
     * 
     * @param content the content that will be created.
     */
    @Transactional
    void createContent(Content content) {
        // 1. Persist the context:
        this.contextRepository.persist(content.getContext());
        
        // 2. Persist the content
        this.contentRepository.persist(content);
    }
    
    /**
     * Deletes the specified {@link Content} from the database.
     * 
     * @param   content    that will be deleted.
     */
    @Transactional
    void deleteContent(Content content) {
        this.contextRepository.remove(content.getContext());
        content.setContext(null);
        this.contentRepository.remove(content);
    }
}
