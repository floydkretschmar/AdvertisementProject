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
package de.oth.fkretschmar.advertisementproject.business.service;

/**
 * Represents the error that occurs when an e-mail is already in use.
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class UserServiceException extends RuntimeException {

    /**
     * Creates a new instance of <code>EMailAlreadyInUseException</code> using
     * the specified error message.
     * 
     * @param   errorMessage    that contains the detailed description of the 
     *                          error.
     */
    public UserServiceException(String errorMessage) {
        super(errorMessage);
    }
}
