/*
 * Copyright (C) 2017 fkre
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

import de.oth.fkretschmar.advertisementproject.business.annotation.BillCreated;
import de.oth.fkretschmar.advertisementproject.business.events.EntityEvent;
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Represents the helper class that enables the observe events that are being
 * fired in ejb beans.
 *
 * @author fkre
 */
@Stateless
public class BillCreatedListener {
    
    /**
     * Stores the service used to manage the entire application.
     */
    @Inject
    private ApplicationModel applicationModel;
    
    /**
     * Listens to any event that indicates that a bill has been created.
     * 
     * @param billCreatedEvent the event being fired when a bill has created.
     */
    public void billCreatedListener(
            @Observes @BillCreated EntityEvent<Bill> billCreatedEvent) {
        this.applicationModel.processAndChangeCurrentUser(user -> 
        {
            Campaign eventCampaign = billCreatedEvent.getEntity().getCampaign();
            
            if(user != null && user.getId().equals(eventCampaign.getComissioner().getId())) {
                Campaign targetCampaign = null;
                for(Campaign campaign : user.getCampaigns()) {
                    if(campaign.getId().longValue() == eventCampaign.getId().longValue()) {
                        targetCampaign = campaign;
                        break;
                    }
                }
                
                if (targetCampaign != null) {
                    targetCampaign.addBill(billCreatedEvent.getEntity());
                }
            }
            
            return user;
        });
    }
}
