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
package de.oth.fkretschmar.advertisementproject.business.services.base;

import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import javax.transaction.Transactional;

/**
 *
 * @author Admin
 */
public interface ICampaignService {
    
    /**
     * Creates a new {@link Campaign} and links it to the specified user.
     * 
     * @param   user        the user for which the campaign will be created.
     * @param   campaign   the campaign that will be saved.
     * @return              the saved campaign.
     */
    @Transactional
    public User createCampaignForUser(User user, Campaign campaign);
    
    
    /**
     * Replaces the old version of the specified campaign on the user.
     * 
     * @param   user        the user that will be updated.
     * @param   campaign    the updated campaign.
     * @return  the changed user.
     */
    public User changeCampaignForUser(User user, Campaign campaign);
    
    /**
     * Cancels the specified {@link Campaign}.
     * 
     * @param   campaign    that will be cancelled.
     * @return              the cancelled campaign.
     */
    public Campaign cancelCampaign(Campaign campaign);
    
    
    /**
     * Ends the specified {@link Campaign}.
     * 
     * @param campaign  that will be ended.
     * @return          the ended campaign.
     */
    public Campaign endCampaign(Campaign campaign);
}
