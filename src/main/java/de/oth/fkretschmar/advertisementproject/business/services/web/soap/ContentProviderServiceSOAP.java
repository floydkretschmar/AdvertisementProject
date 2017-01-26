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
package de.oth.fkretschmar.advertisementproject.business.services.web.soap;

import de.oth.fkretschmar.advertisementproject.business.services.web.ContentRequestResult;
import de.oth.fkretschmar.advertisementproject.business.services.web.IContentProviderService;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * The service that offers functionality relatetd to the generation and
 * management of {@link Content} instances.
 *
 * @author fkre Floyd Kretschmar
 */
@RequestScoped
@WebService(serviceName = "ContentProviderService")
public class ContentProviderServiceSOAP implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores the service used to manage request for contents.
     */
    @Inject
    private IContentProviderService contentProviderService;

    // --------------- Public methods ---------------
    
    /**
     * Retrieves an advertisement content that best matches the provided
     * {@link TargetContext}.
     *
     * @param source that requested the content.
     * @param format the format of the content.
     * @param requestContext the target context of the request.
     * @return the best matching content.
     */
    @WebMethod(action = "requestContent")
    public ContentRequestResult requestContent(
            @WebParam(name = "source") String source,
            @WebParam(name = "format") ContentFormat format,
            @WebParam(name = "context") RequestContext requestContext) {
        
        // if absolutely no target has been defined -> send untargeted content
        if (!requestContext.isTargeted())
            return this.requestUntargetedContent(source, format);
        
        TargetContext context = TargetContext.createTargetContext()
                .targetAges(requestContext.getTargetAgeGroups())
                .targetGenders(requestContext.getTargetGenderGroups())
                .targetMaritalStatus(requestContext.getTargetMaritalStatusGroups())
                .targetPurposeOfUses(requestContext.getTargetPurposeOfUseGroups())
                .build();
        
        return this.contentProviderService.requestContent(
                source, format, context);
    }

    /**
     * Retrieves a random advertisement that has not been matched with any
     * target context.
     *
     * @param source the text that identifies the source of the request.
     * @param format the format that the content is supposed to have.
     * @return the content that has been chosen randomly.
     */
    @WebMethod(action = "requestUntargetedContent")
    public ContentRequestResult requestUntargetedContent(
            @WebParam(name = "source") String source,
            @WebParam(name = "format") ContentFormat format) {
        return this.contentProviderService.requestUntargetedContent(
                source, format);
    }
}
