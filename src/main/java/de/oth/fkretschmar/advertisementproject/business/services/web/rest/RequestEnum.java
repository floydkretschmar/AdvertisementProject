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

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author fkre
 * @param <T> the type of the enum.
 */
@RequiredArgsConstructor
public abstract class RequestEnum<T extends Enum> {

    // --------------- Private fields ---------------
    /**
     * Stores the actual content format that is being wrapped by this request
     * format.
     */
    @Getter
    private final T enumValue;

    // --------------- Public constructors ---------------
    /**
     * Creates a new instance of {@link RequestEnum} using the specified parameter.
     *
     * @param param the parameter of the REST request that will be converted to
     * the corresponding enum value.
     * @throws WebApplicationException indicating that the provided REST param
     * is not a valid enum value.
     */
    public RequestEnum(String param) {
        String toUpper = param.toUpperCase();
        try {
            this.enumValue = this.parseValue(toUpper);
        } catch (Exception e) {
            // default behavior is to send 404 on error.
            // do something else if you want
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    

    // --------------- Protected methods ---------------
    
    /**
     * Performs the work of actually creating the enum for the specified
     * formatted string.
     * 
     * @param formattedValue the string that represents the enum value.
     * @return the wrapped enum value.
     */
    protected abstract T parseValue(String formattedValue);
}
