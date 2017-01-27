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
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
/**
 *
 * @author Admin
 */
public interface IContentService {
    
    /**
     * Creates a new content.
     * 
     * @param content the content that will be created.
     */
    public void createContent(Content content);
    
//    /**
//     * Deletes the specified {@link Content} from the database.
//     * 
//     * @param   content    that will be deleted.
//     */
//    public void deleteContent(Content content);
    
    /**
     * Creates a new {@link Content} and links it to the specified campaign.
     * 
     * @param   campaign    to which the content will be linked.
     * @param   content     the content that will be saved.
     * @return  the created {@link Content}.
     */
    public Campaign createContentForCampaign(
            Campaign campaign, Content content);
}
