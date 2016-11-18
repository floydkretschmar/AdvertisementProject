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
public class AddressBuilder extends AbstractEntityBuilder<Long, Address> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link AddressBuilder}.
     */
    private AddressBuilder() {
        super(new Address());
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link AddressBuilder}.
     * 
     * @return 
     */
    public static AddressBuilder create() {
        return new AddressBuilder();
    }
    
    // --------------- Public methods ---------------
    
    /**
     * Creates the address using the specified area code.
     * 
     * @param   areaCode    the textual representation of the area code.
     * @return  the address builder used to build the address.
     */
    public AddressBuilder withAreaCode(String areaCode) {
        this.getEntity().setAreaCode(areaCode);
        return this;
    }
    
    
    /**
     * Creates the address using the specified city.
     * 
     * @param   city    the name of the city in which the street can be found.
     * @return  the address builder used to build the address.
     */
    public AddressBuilder withCity(String city) {
        this.getEntity().setCity(city);
        return this;
    }
    
    
    /**
     * Creates the address using the specified country.
     * 
     * @param   country     the text that represents the country in which the 
     *                      address can be found.
     * @return  the address builder used to build the address.
     */
    public AddressBuilder withCountry(String country) {
        this.getEntity().setCountry(country);
        return this;
    }
    
    
    /**
     * Creates the address using the specified street.
     * 
     * @param   street      the textual representation of the street including 
     *                      the street number.
     * @return  the address builder used to build the address.
     */
    public AddressBuilder withStreet(String street) {
        this.getEntity().setStreet(street);
        return this;
    }
    
    
    // --------------- Protected methods ---------------

    
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
            throw new EntityBuilderValidationException(
                    AddressBuilder.class, 
                    "The area code can not be null or empty.");
        
        if(entity.getCity() == null || entity.getCity().isEmpty())
            throw new EntityBuilderValidationException(
                    AddressBuilder.class, 
                    "The city can not be null or empty.");
        
        if(entity.getStreet() == null || entity.getStreet().isEmpty())
            throw new EntityBuilderValidationException(
                    AddressBuilder.class, 
                    "The street can not be null or empty.");
    }
}
