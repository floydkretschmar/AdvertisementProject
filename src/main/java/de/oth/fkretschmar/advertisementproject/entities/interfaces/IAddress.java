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
package de.oth.fkretschmar.advertisementproject.entities.interfaces;

/**
 * The interface that defines the methods of an address to other components of 
 * the class.
 * 
 * @author  fkre    Floyd Kretschmar
 */
public interface IAddress extends IEntity<Long> {
    
    /**
     * Gets the textual representation of the area code.
     * 
     * @return  A String that contains the textual represenation of the area 
     *          code.
     */
    public String getAreaCode();

    /**
     * Gets the name of the city in which the street can be found.
     * 
     * @return  A String that contains the city name.
     */
    public String getCity();

    /**
     * Gets the text that represents the country in which the address can be 
     * found.
     * 
     * @return  A String that contains the name of the country.
     */
    public String getCountry();

    /**
     * Gets the textual representation of the street including the street number.
     * 
     * @return  A String that contains the textual representation of the street 
     *          including the streetnumber.
     */
    public String getStreet();

    /**
     * Sets the textual representation of the area code.
     * 
     * @param   areaCode    that is the textual representation of the area code.
     */
    public void setAreaCode(String areaCode);

    /**
     * Sets the name of the city in which the street can be found.
     * 
     * @param   city    that contains the name of the city.
     */
    public void setCity(String city);

    /**
     * Sets the text that represents the country in which the address can be 
     * found.
     * 
     * @param   country that contains the name of the county.
     */
    public void setCountry(String country);

    /**
     * Sets the textual representation of the street including the street number.
     * 
     * @param   street  that contains the textual representation of the steet 
     *                  including the street number.
     */
    public void setStreet(String street);
}
