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

import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.interfaces.IAddress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Represents an address of an user.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@Entity(name = "T_ADDRESS")
public class Address extends AbstractAutoGenerateKeyedEntity implements IAddress {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the textual representation of the area code.
     */
    @NotNull
    @Column(name = "AREA_CODE")
    private String areaCode;
    
    /**
     * Stores the name of the city.
     */
    @NotNull
    @Column(name = "CITY")
    private String city;
    
    /**
     * Stores the name of the country.
     */
    @Column(name = "COUNTRY")
    private String country;
    
    /**
     * Stores the textual representation of the street including the street 
     * number.
     */
    @NotNull
    @Column(name = "STREET")
    private String street;
    
    
    // --------------- Protected constructors ---------------
    
    /**
     * Creates a new instance of {@link Address}.
     */
    protected Address() {
        super();
    }
    
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link Address} using the specified area code, 
     * city and street.
     * 
     * @param   areaCode    that identifies the area of the address.
     * @param   city        that contains the city name.
     * @param   street      that contains the street.
     */
    public Address(String areaCode, String city, String street) {
        this();
        this.areaCode = areaCode;
        this.city = city;
        this.street = street;
    }
    
    // --------------- Public getters and setters ---------------
    
    
    /**
     * Gets the textual representation of the area code.
     * 
     * @return  A String that contains the textual represenation of the area 
     *          code.
     */
    public String getAreaCode() {
        return this.areaCode;
    }

    /**
     * Gets the name of the city.
     * 
     * @return  A String that contains the city name.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Gets the name of the country.
     * 
     * @return  A String that contains the name of the country.
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Gets the textual representation of the street including the street number.
     * 
     * @return  A String that contains the textual representation of the street 
     *          including the streetnumber.
     */
    public String getStreet() {
        return this.street;
    }

    /**
     * Sets the textual representation of the area code.
     * 
     * @param   areaCode    that is the textual representation of the area code.
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * Sets the name of the city.
     * 
     * @param   city    that contains the name of the city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the name of the country.
     * 
     * @param   country that contains the name of the county.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets the textual representation of the street including the street number.
     * 
     * @param   street  that contains the textual representation of the steet 
     *                  including the street number.
     */
    public void setStreet(String street) {
        this.street = street;
    }
}
