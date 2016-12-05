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

import de.oth.fkretschmar.advertisementproject.business.repositories.CampaignContentRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.TargetContextRepository;
import de.oth.fkretschmar.advertisementproject.entities.CampaignContent;
import de.oth.fkretschmar.advertisementproject.entities.TargetContext;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link CampaignContent} instances.
 *
 * @author fkre
 */
@RequestScoped
public class CampaignContentService implements Serializable {

    // --------------- Private fields ---------------

    /**
     * Stores the repository used to manage {@link CampaignContent} entites.
     */
    @Inject
    CampaignContentRepository campaignContentRepository;

    /**
     * Stores the repository used to manage {@link TargetContext} entites.
     */
    @Inject
    TargetContextRepository contextRepository;

    // --------------- Public methods ---------------
    
    
    /**
     * Creates the specified {@link CampaignContent}.
     * 
     * @param   campaignContent   the campaignContent that will be saved.
     */
    @Transactional
    public void create(CampaignContent campaignContent) {
        // the actual Content that is wrapped within a campaign content is 
        // chosen from a list of already created contents so simply perisiting 
        // the campaign content without saving the content is valid.
        this.contextRepository.persist(campaignContent.getContext());
        
        this.campaignContentRepository.persist(campaignContent);
    }
    
    /**
     * Creates the specified instances of {@link CampaignContent}.
     * 
     * @param campaignContents the campaignContent instances that will be saved.
     */
    public void create(Collection<CampaignContent> campaignContents) {
        campaignContents.forEach(campaignContent -> this.create(campaignContent));
    }
    
    /**
     * Deletes the specified {@link CampaignContent} from the database.
     * 
     * @param   campaignContent    that will be deleted.
     */
    @Transactional
    public void delete(CampaignContent campaignContent) {
        this.contextRepository.remove(campaignContent.getContext());
        
        campaignContent.setContext(null);
        
        // Don't actually remove the content because the user could still chose
        // to use it in a different campaign, but set it to null on this 
        // particular campaign content.
        campaignContent.setContent(null);
        
        this.campaignContentRepository.remove(campaignContent);
    }
    
    
    /**
     * Deletes the specified instances of {@link CampaignContent} from the 
     * database.
     * 
     * @param   campaignContents    that will be deleted.
     */
    public void delete(Collection<CampaignContent> campaignContents) {
        campaignContents.forEach(campaignContent -> this.delete(campaignContent));
    }
}
