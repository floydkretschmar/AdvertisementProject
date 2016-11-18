///*
// * Copyright (C) 2016 Floyd
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package de.oth.fkretschmar.advertisementproject.entities.interfaces;
//
//import de.oth.fkretschmar.advertisementproject.entities.interfaces.IAddress;
//
///**
// * Defines the methods a builder used to create an {@link Address} needs to 
// * provide.
// * 
// * @author  Floyd Kretschmar
// * @param   <T>     the type of the address entity that will be build.
// */
//public interface IAddressBuilder<T extends IAddress> extends IEntityBuilder<Long, T> {
//    
//    // --------------- Methods ---------------
//    
//    /**
//     * Creates the address using the specified area code.
//     * 
//     * @param   areaCode    the textual representation of the area code.
//     * @return  the address builder used to build the address.
//     */
//    public IAddressBuilder withAreaCode(String areaCode);
//    
//    
//    /**
//     * Creates the address using the specified city.
//     * 
//     * @param   city    the name of the city in which the street can be found.
//     * @return  the address builder used to build the address.
//     */
//    public IAddressBuilder withCity(String city);
//    
//    
//    /**
//     * Creates the address using the specified country.
//     * 
//     * @param   country     the text that represents the country in which the 
//     *                      address can be found.
//     * @return  the address builder used to build the address.
//     */
//    public IAddressBuilder withCountry(String country);
//    
//    
//    /**
//     * Creates the address using the specified street.
//     * 
//     * @param   street      the textual representation of the street including 
//     *                      the street number.
//     * @return  the address builder used to build the address.
//     */
//    public IAddressBuilder withStreet(String street);
//}
