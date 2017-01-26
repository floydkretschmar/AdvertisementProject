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
package de.oth.fkretschmar.advertisementproject.business.services.web.rest;

import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author fkre
 */
public class RequestTargetAge extends RequestEnum<TargetAge>{

    /**
     * Creates a new instance of {@link RequestContentFormat} using the specified parameter.
     *
     * @param param the parameter of the REST request that will be converted to
     * the corresponding content format.
     * @throws WebApplicationException indicating that the provided REST param
     * is not a valid content formats.
     */
    public RequestTargetAge(String param) {
        super(param);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected TargetAge parseValue(String formattedValue) {
        return TargetAge.valueOf(formattedValue);
    }
    
}
