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
import de.oth.fkretschmar.advertisementproject.business.repositories.UserRepository;

import de.oth.fkretschmar.advertisementproject.entities.Bill;
import de.oth.fkretschmar.advertisementproject.entities.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.CampaignState;
import de.oth.fkretschmar.advertisementproject.entities.Content;
import de.oth.fkretschmar.advertisementproject.entities.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.User;

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
public class CampaignService implements Serializable {

    // --------------- Private fields ---------------

    /**
     * Stores the service used to manage {@link Bill} entites.
     */
    @Inject
    private BillService billService;
    
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

    /**
     * Stores the repository used to manage {@link User} entites.
     */
    @Inject
    private UserRepository userRepository;
    

    // --------------- Public methods ---------------
    
    /**
     * Creates a new {@link Campaign} and links it to the specified user.
     * 
     * @param   user        the user for which the campaign will be created.
     * @param   campaign   the campaign that will be saved.
     * @return              the saved campaign.
     */
    @Transactional
    public Campaign createCampaignForUser(User user, Campaign campaign) {
        // the campaign is being created for a user that already has an account
        // therefore no .persist is needed for an account
        // also: bill will be added later, when the first payments for the 
        // campaign come in, so there can not be bills right now either.
        
        // 1. create the contents
        campaign.getContents().forEach(content -> this.createContent(content));
        
        // 2. merge and set user on campaign
        user = this.userRepository.merge(user);
        campaign.setComissioner(user);
        
        // 3. save the campaign
        this.campaignRepository.persist(campaign);
        
        // 4. add the campaign to the user:
        user.addCampaign(campaign);
        
        return campaign;
    }
    
    
    /**
     * Creates a new {@link Content} and links it to the specified campaign.
     * 
     * @param   campaign    to which the content will be linked.
     * @param   content     the content that will be saved.
     * @return  the created {@link Content}.
     */
    @Transactional
    public Content createContentForCampaign(
            Campaign campaign, Content content) {
        this.createContent(content);
        
        // 3. add the content to the campaign
        campaign = this.campaignRepository.merge(campaign);
        campaign.addContent(content);
        return content;
    }
    
    /**
     * Cancels the specified {@link Campaign}.
     * 
     * @param   campaign    that will be cancelled.
     * @return              the cancelled campaign.
     */
    @Transactional
    public Campaign cancelCampaign(Campaign campaign) {        
        campaign = this.campaignRepository.merge(campaign);
        campaign.setCampaignState(CampaignState.CANCELLED);
        this.deleteCampaignContents(campaign);
        return campaign;
    }
    
    
    /**
     * Ends the specified {@link Campaign}.
     * 
     * @param campaign  that will be ended.
     * @return          the ended campaign.
     */
    @Transactional
    public Campaign endCampaign(Campaign campaign) {
        campaign = this.campaignRepository.merge(campaign);
        campaign.setCampaignState(CampaignState.ENDED);
        this.deleteCampaignContents(campaign);
        return campaign;
    }

    // --------------- Private methods ---------------
    
    
    /**
     * Creates a new content.
     * 
     * @param content the content that will be created.
     */
    @Transactional
    private void createContent(Content content) {
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
    public void deleteContent(Content content) {
        this.contextRepository.remove(content.getContext());
        content.setContext(null);
        this.contentRepository.remove(content);
    }
    
    /**
     * Deletes all campaign contents form the specified campaign.
     * @param campaign 
     */
    @Transactional
    private void deleteCampaignContents(Campaign campaign) {        
        // remove all contents
        Object[] contents = campaign.getContents().toArray();
        
        for (int i = 0; i < contents.length; i++) {
            if(contents[i] instanceof Content){
                campaign.removeContent((Content)contents[i]);
                this.deleteContent((Content)contents[i]);
            }
        }
    }
}
