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
package de.oth.fkretschmar.advertisementproject.entities.user;

import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an address of an user.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Embeddable
public class Address  implements Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the textual representation of the area code.
     */
    @NotNull
    @Column(name = "AREA_CODE")
    @Getter
    @Setter
    private String areaCode;
    
    /**
     * Stores the name of the city in which the street can be found.
     */
    @NotNull
    @Column(name = "CITY")
    @Getter
    @Setter
    private String city;
    
    /**
     * Stores the text that represents the country in which the address can be 
     * found.
     */
    @Column(name = "COUNTRY")
    @Getter
    @Setter
    private String country;
    
    /**
     * Stores the textual representation of the house number.
     */
    @Column(name = "HOUSE_NUMBER")
    @Getter
    @Setter
    private String houseNumber;
    
    /**
     * Stores the textual representation of the street including the street 
     * number.
     */
    @NotNull
    @Column(name = "STREET")
    @Getter
    @Setter
    private String street;
        
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Address}.
     * 
     * @param areaCode      the textual representation of the area code.
     * @param city          the name of the city in which the street can be 
     *                      found.
     * @param country       the text that represents the country in which the 
     *                      address can be found.
     * @param houseNumber   textual representation of the house number.
     * @param street        the textual representation of the street including 
     *                      the street number.
     * @return the built {@link Address}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createAddress", 
            builderClassName = "AddressBuilder",
            buildMethodName = "build")
    private static Address validateAndCreateAddress(
            String areaCode, 
            String city, 
            String country, 
            String houseNumber,
            String street) throws BuilderValidationException {
        if(areaCode == null || areaCode.isEmpty())
            throw new BuilderValidationException(
                    Address.class, 
                    "The area code can not be null or empty.");
        
        if(city == null || city.isEmpty())
            throw new BuilderValidationException(
                    Address.class, 
                    "The city can not be null or empty.");
        
        if(houseNumber == null || houseNumber.isEmpty())
            throw new BuilderValidationException(
                    Address.class, 
                    "The house number can not be null or empty.");
        
        if(street == null || street.isEmpty())
            throw new BuilderValidationException(
                    Address.class, 
                    "The street can not be null or empty.");
        
        return new Address(areaCode, city, country, houseNumber, street);
    }
    
}
