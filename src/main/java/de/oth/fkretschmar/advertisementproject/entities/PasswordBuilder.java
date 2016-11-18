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
package de.oth.fkretschmar.advertisementproject.entities;

/**
 *
 * @author fkre
 */
public class PasswordBuilder extends AbstractEntityBuilder<Long, Password> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link PasswordBuilder}.
     */
    private PasswordBuilder(String value) {
        super(new Password(value));
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link PasswordBuilder}.
     * 
     * @param value
     * @return 
     */
    public static PasswordBuilder create(String value) {
        return new PasswordBuilder(value);
    }        
    
    // --------------- Protected methods ---------------

    
    /**
     * Validates the {@link Password} and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the password that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(Password entity) 
            throws EntityBuilderValidationException {
        if(entity.getValue() == null || entity.getValue().isEmpty())
            throw new EntityBuilderValidationException(
                    PasswordBuilder.class, 
                    "The value can not be null or empty.");
    }
}
