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
public class AddressBuilder extends AbstractEntityBuilder<Long, Address>{
    
    // --------------- Protected methods ---------------

    /**
     * Creates a new and empty address to further process.
     * 
     * @return  the address.
     */
    @Override
    protected Address createEntity() {
        return new Address();
    }

    
    /**
     * Validates the address entity and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the address that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(Address entity) 
            throws EntityBuilderValidationException {
        if(entity.getAreaCode() == null || entity.getAreaCode().isEmpty())
            throw new EntityBuilderValidationException("The area code can not be"
                    + " null or empty.");
        
        if(entity.getCity() == null || entity.getCity().isEmpty())
            throw new EntityBuilderValidationException("The city can not be null"
                    + " or empty.");
        
        if(entity.getStreet() == null || entity.getStreet().isEmpty())
            throw new EntityBuilderValidationException("The street can not be "
                    + " null or empty.");
    }
}
