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
//import de.oth.fkretschmar.advertisementproject.entities.interfaces.IPassword;
//import de.oth.fkretschmar.advertisementproject.entities.interfaces.IUser;
//import de.oth.fkretschmar.advertisementproject.entities.interfaces.IAddress;
//
///**
// * Defines the methods a builder used to create an {@link IUser} needs to 
// * provide.
// * 
// * @author Floyd Kretschmar
// */
//public interface IUserBuilder extends IEntityBuilder<String, IUser> {
//    
//    // --------------- Methods ---------------
//    
//    
//    /**
//     * Creates the address using the address information of the user.
//     * 
//     * @param   address     that contains all the address information of the user.
//     * @return  the user builder used to build the user.
//     */
//    public IUserBuilder withAddress(IAddress address);
//
//    
//    /**
//     * Creates the address using the name of the company the user is buying ads 
//     * for.
//     * 
//     * @param   company   that contains the company name.
//     * @return  the user builder used to build the user.
//     */
//    public IUserBuilder withCompany(String company);
//
//    
//    /**
//     * Creates the address using  the first name of the user.
//     * 
//     * @param   firstName   that contains the first name.
//     * @return  the user builder used to build the user.
//     */
//    public IUserBuilder withFirstName(String firstName);
//
//    
//    /**
//     * Creates the address using  the last name of the user.
//     * 
//     * @param   lastName    that contains the last name. 
//     * @return  the user builder used to build the user.
//     */
//    public IUserBuilder withLastName(String lastName);
//
//    
//    /**
//     * Creates the address using the password of the user.
//     * 
//     * @param   password    that contains a user password.
//     * @return  the user builder used to build the user.
//     */
//    public IUserBuilder withPassword(IPassword password);
//}
