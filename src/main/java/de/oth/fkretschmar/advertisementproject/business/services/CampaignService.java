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
import de.oth.fkretschmar.advertisementproject.business.repositories.UserRepository;
import de.oth.fkretschmar.advertisementproject.business.services.base.ICampaignService;
import de.oth.fkretschmar.advertisementproject.business.services.base.IContentService;

import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.CampaignState;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.user.User;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Campaign} instances.
 *
 * @author  fkre    Floyd Kretschmar
 */
@RequestScoped
public class CampaignService implements Serializable, ICampaignService {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Campaign} entites.
     */
    @Inject
    private CampaignRepository campaignRepository;

    /**
     * Stores the service used to manage {@link Content} entites.
     */
    @Inject
    private IContentService contentService;

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
    @Override
    public User createCampaignForUser(User user, Campaign campaign) {
        // the campaign is being created for a user that already has an account
        // therefore no .persist is needed for an account
        // also: bill will be added later, when the first payments for the 
        // campaign come in, so there can not be bills right now either.
        
        // 1. merge and set user on campaign
        user = this.userRepository.merge(user);
        campaign.setComissioner(user);
        
        // 2. Campaign is being owned so save the campaign first:
        this.campaignRepository.persist(campaign);
        
        // 3. create the contents of the campaign and set the campaign
        campaign.getContents().forEach(content -> 
                {
                    content.setCampaign(campaign);
                    this.contentService.createContent(content);
                });
                
        // 4. add the campaign to the user:
        user.addCampaign(campaign);
        
        return user;
    }
    
    
    /**
     * Replaces the old version of the specified campaign on the user.
     * 
     * @param   user        the user that will be updated.
     * @param   campaign    the updated campaign.
     * @return  the changed user.
     */
    @Transactional
    @Override
    public User changeCampaignForUser(User user, Campaign campaign) {
        user = this.userRepository.merge(user);
        campaign = this.campaignRepository.merge(campaign);
        
        // entities are equal if their ids are equal; since the IDs havent 
        // changed this trickery works
        for(Campaign oldCampaign : user.getCampaigns()) {
            if(oldCampaign.equals(campaign)) {
                user.removeCampaign(oldCampaign);
                break;
            }
        }
        
        user.addCampaign(campaign);
        
        return user;
    }
    
    /**
     * Cancels the specified {@link Campaign}.
     * 
     * @param   campaign    that will be cancelled.
     * @return              the cancelled campaign.
     */
    @Transactional
    @Override
    public Campaign cancelCampaign(Campaign campaign) {        
        campaign = this.campaignRepository.merge(campaign);
        campaign.setCampaignState(CampaignState.CANCELLED);
        return campaign;
    }
    
    
    /**
     * Ends the specified {@link Campaign}.
     * 
     * @param campaign  that will be ended.
     * @return          the ended campaign.
     */
    @Transactional
    @Override
    public Campaign endCampaign(Campaign campaign) {
        campaign = this.campaignRepository.merge(campaign);
        campaign.setCampaignState(CampaignState.ENDED);
        return campaign;
    }

    // --------------- Private methods ---------------
    
    
//    /**
//     * Deletes all campaign contents form the specified campaign.
//     * @param campaign 
//     */
//    @Transactional
//    private void deleteCampaignContents(Campaign campaign) {        
//        // remove all contents
//        Object[] contents = campaign.getContents().toArray();
//        
//        for (int i = 0; i < contents.length; i++) {
//            if(contents[i] instanceof Content){
//                campaign.removeContent((Content)contents[i]);
//                this.contentService.deleteContent((Content)contents[i]);
//            }
//        }
//    }
}
