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
package de.oth.fkretschmar.advertisementproject.business.services.web;

import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

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
     * @param source that requested the content.
     * @param format the format of the content.
     * @param requestContext the target context of the request.
     * @return the best matching content.
     */
    public ContentRequestResult requestContent(
            @WebParam(name = "source") String source,
            @WebParam(name = "format") ContentFormat format,
            @WebParam(name = "context") RequestContext requestContext);

    /**
     * Retrieves an advertisement content that best matches the provided
     * {@link TargetContext}.
     *
     * @param source that requested the content.
     * @param format the format of the content.
     * @return the best matching content.
     */
    public ContentRequestResult requestUntargetedContent(
            @WebParam(name = "source") String source,
            @WebParam(name = "format") ContentFormat format);
}
