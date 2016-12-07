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

import de.oth.fkretschmar.advertisementproject.business.repositories.BillRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.CampaignRepository;
import de.oth.fkretschmar.advertisementproject.entities.Bill;
import de.oth.fkretschmar.advertisementproject.entities.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.CampaignContent;
import de.oth.fkretschmar.advertisementproject.entities.CampaignState;
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
    BillService billService;
    
    /**
     * Stores the repository used to manage {@link Campaign} entites.
     */
    @Inject
    CampaignRepository campaignRepository;
    
    /**
     * Stores the service that manages {@link CampaignContent} entities.
     */
    @Inject
    CampaignContentService campaignContentService;
    

    // --------------- Public methods ---------------
    
    /**
     * Creates the specified {@link Campaign}.
     * 
     * @param   campaign   the campaign that will be saved.
     * @return            the saved campaign.
     */
    @Transactional
    public Campaign create(Campaign campaign) {
        // the campaign is being created for a user that already has an account
        // therefore no .persist is needed for an account
        // also: bill will be added later, when the first payments for the 
        // campaign come in, so there can not be bills right now either.
        this.campaignContentService.create(campaign.getContents());
        
        this.campaignRepository.persist(campaign);
        return campaign;
    }
    
    
    /**
     * Cancels the specified {@link Campaign}.
     * 
     * @param   campaign    that will be cancelled.
     */
    @Transactional
    public void cancel(Campaign campaign) {        
        campaign = this.campaignRepository.merge(campaign);
        campaign.setCampaignState(CampaignState.CANCELLED);
        this.deleteCampaignContents(campaign);
    }
    
    
    /**
     * Ends the specified {@link Campaign}.
     * @param campaign that will be ended.
     */
    @Transactional
    public void end(Campaign campaign) {
        campaign = this.campaignRepository.merge(campaign);
        campaign.setCampaignState(CampaignState.ENDED);
        this.deleteCampaignContents(campaign);
    }

    // --------------- Private methods ---------------
    
    
    /**
     * Deletes all campaign contents form the specified campaign.
     * @param campaign 
     */
    @Transactional
    private void deleteCampaignContents(Campaign campaign) {        
        // remove all contents
        Object[] contents = campaign.getContents().toArray();
        
        for (int i = 0; i < contents.length; i++) {
            if(contents[i] instanceof CampaignContent){
                campaign.removeContent((CampaignContent)contents[i]);
                this.campaignContentService.delete((CampaignContent)contents[i]);
            }
        }
    }
}
