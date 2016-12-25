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

import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;

/**
 *
 * @author Admin
 */
public interface IBillService {
    
    /**
     * Creates a new {@link Bill} and links it to the already existing 
     * specified {@link Campaign}.
     * 
     * @param   campaign    to which the bill will be linked.
     * @param   bill        that will be created.
     * @return              the changed campaign.
     */
    public Campaign createBillForCampaign(Campaign campaign, Bill bill);
    
    
    /**
     * Deletes the specified {@link Bill} from the database.
     * 
     * @param   bill    that will be deleted.
     */
    public void deleteBill(Bill bill);
}
