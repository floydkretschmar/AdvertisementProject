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

import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import java.util.Optional;
import javax.jws.WebService;

/**
 *
 * @author Admin
 */
@WebService
public interface IContentProviderService {
    
    /**
     * Retrieves an advertisement content that best matches the provided 
     * {@link TargetContext}. 
     * 
     * @param source    the text that identifies the source of the request.
     * @param format    the format that the content is supposed to have.
     * @param context   the context that specifies the targets for the requestet
     *                  content.
     * @return          the best matching content.
     */
    public Optional<Content> requestContent(
            String source, ContentFormat format, TargetContext context);
    
    
    /**
     * Retrieves a random advertisement that has not been matched with any target
     * context.
     * 
     * @param   source  the text that identifies the source of the request.
     * @param   format  the format that the content is supposed to have.
     * @return  the content that has been chosen randomly.
     */
    public Optional<Content> requestRandomContent(
            String source, ContentFormat format);
}
