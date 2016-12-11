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
package de.oth.fkretschmar.advertisementproject.entities.exceptions;
/**
 * The exception that is being thrown if the validation of the set data failed
 * in the build method of a builder.
 * 
 * @author fkre
 */
public class BuilderValidationException extends RuntimeException {

    /**
     * Creates an instance of {@link BuilderValidationException} using the 
     * specified detail message.
     *
     * @param   <T>             the type of the builder in which the exception
     *                          occurred.
     * @param   exceptionSource class in which the exception occurred.
     * @param   message         the detail message.
     */
    public <T> BuilderValidationException(
            Class<T> exceptionSource, 
            String message) {
        super(String.format("%s: %s", exceptionSource.toString(), message));
    }
}
