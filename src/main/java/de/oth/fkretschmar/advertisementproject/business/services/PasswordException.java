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
package de.oth.fkretschmar.advertisementproject.business.services;

/**
 * The exception that gets thrown if a error occurres during password handling.
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class PasswordException extends Exception {
    
    /**
     * Constructs an instance of <code>PasswordServiceException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     * @param cause the actual cause.
     */
    public PasswordException(String msg, Throwable cause) {
        super(String.format("%s: %s", PasswordService.class, msg), cause);
    }
}
