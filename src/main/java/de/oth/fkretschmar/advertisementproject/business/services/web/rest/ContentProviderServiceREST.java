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
package de.oth.fkretschmar.advertisementproject.business.services.web.rest;

import de.oth.fkretschmar.advertisementproject.business.services.web.ContentRequestResult;
import de.oth.fkretschmar.advertisementproject.business.services.web.IContentProviderService;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * The service that offers functionality relatetd to the generation and
 * management of {@link Content} instances.
 *
 * @author fkre Floyd Kretschmar
 */
@RequestScoped
@Path("contentProvider")
public class ContentProviderServiceREST implements Serializable {

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
     * @param ages the targeted age groups.
     * @param genders the targetet gender groups.
     * @param maritalStatus the targeted marital status groups.
     * @param purposesOfUse the targeted purposes of use groups.
     * @return the best matching content.
     */
    @GET
    @Path("requestContent/{source}")
    @Produces(MediaType.APPLICATION_XML)
    public ContentRequestResult requestContent(
            @PathParam("source") String source,
            @QueryParam("format") RequestContentFormat format,
            @QueryParam("targetAge") List<RequestTargetAge> ages,
            @QueryParam("targetGender") List<RequestTargetGender> genders,
            @QueryParam("targetMaritalStatus") List<RequestTargetMaritalStatus> maritalStatus,
            @QueryParam("targetPurposeOfUse") List<RequestTargetPurposeOfUse> purposesOfUse) {
        EnumSet<TargetAge> targetAges
                = this.extractEnumSet(ages, TargetAge.class);
        EnumSet<TargetGender> targetGenders
                = this.extractEnumSet(genders, TargetGender.class);
        EnumSet<TargetMaritalStatus> targetMaritalStatus
                = this.extractEnumSet(maritalStatus, TargetMaritalStatus.class);
        EnumSet<TargetPurposeOfUse> targetPurposesOfUse
                = this.extractEnumSet(purposesOfUse, TargetPurposeOfUse.class);

        TargetContext context = TargetContext.createTargetContext()
                .targetAges(targetAges)
                .targetGenders(targetGenders)
                .targetMaritalStatus(targetMaritalStatus)
                .targetPurposeOfUses(targetPurposesOfUse)
                .build();

        return this.contentProviderService.requestContent(
                source, format.getEnumValue(), context);
    }

    /**
     * Retrieves an advertisement content that best matches the provided
     * {@link TargetContext}.
     *
     * @param source that requested the content.
     * @param format the format of the content.
     * @return the best matching content.
     */
    @GET
    @Path("requestUntargetedContent/{source}")
    @Produces(MediaType.APPLICATION_XML)
    public ContentRequestResult requestUntargetedContent(
            @PathParam("source") String source,
            @QueryParam("format") RequestContentFormat format) {
        return this.contentProviderService.requestUntargetedContent(
                source, format.getEnumValue());
    }
    
    // --------------- Private methods ---------------
    
    /**
     * Extracts the enum set of the target group from the provided parameter
     * list of the REST request.
     * @param <E> the request parameter type.
     * @param <T> the target group type.
     * @param enumValues the list of parameters send with the request.
     * @param enumType the class of the target group.
     * @return the enum set containing all values that define the targeted 
     * groups for the content request.
     */
    private <E extends Enum<E>, T extends RequestEnum<E>> EnumSet<E> extractEnumSet(List<T> enumValues, Class<E> enumType) {
        EnumSet<E> enumSet
                = EnumSet.allOf(enumType);

        if (!enumValues.isEmpty()) {
            enumSet.clear();
            enumValues.forEach(value -> enumSet.add(value.getEnumValue()));
        }
        
        return enumSet;
    }
}
