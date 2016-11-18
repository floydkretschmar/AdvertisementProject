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
//import de.oth.fkretschmar.advertisementproject.entities.IEntity;
//import java.util.Collection;
//
///**
// *
// * @author Floyd
// */
//public interface IUser extends IEntity<String> {
//    
//    
//    // --------------- Getters and setters ---------------
//    /**
//     * Gets the address information of the user.
//     * 
//     * @return  A instance of {@link IAddress} that contains all the address 
//     *          information of the user.
//     */
//    public IAddress getAddress();
// 
//    
//    /**
//     * Gets an unmodifiable list of the bank accounts an user has specified.
//     * 
//     * @return  the bank accounts of an user.
//     */
//    public Collection<IAccount> getAccounts();
//    
//    
//    /**
//     * Gets the name of the company the user is buying ads for.
//     * 
//     * @return  A String that contains the company name.
//     */
//    public String getCompany();
//    
//    
//    /**
//     * Gets the e-mail address of the user.
//     * 
//     * @return  A String that contains the e-mail address.
//     */
//    public String geteMailAddress();
//
//    
//    /**
//     * Gets the first name of the user.
//     * 
//     * @return  A String that contains the first name. 
//     */
//    public String getFirstName();
//
//    
//    /**
//     * Gets the last name of the user.
//     * 
//     * @return  A String that contains the last name.
//     */
//    public String getLastName();
//
//    
//    /**
//     * Gets the password of the user.
//     * 
//     * @return  A {@link IPassword} that represents the current user password.
//     */
//    public IPassword getPassword();
//
//    
//    /**
//     * Sets the address information of the user.
//     * 
//     * @param   address     that contains all the address information of the user.
//     */
//    public void setAddress(IAddress address);
//
//    
//    /**
//     * Sets the name of the company the user is buying ads for.
//     * 
//     * @param   company   that contains the company name.
//     */
//    public void setCompany(String company);
//
//    
//    /**
//     * Sets the first name of the user.
//     * 
//     * @param   firstName   that contains the first name.
//     */
//    public void setFirstName(String firstName);
//
//    
//    /**
//     * Sets the last name of the user.
//     * 
//     * @param   lastName    that contains the last name. 
//     */
//    public void setLastName(String lastName);
//
//    
//    /**
//     * Sets the password of the user.
//     * 
//     * @param   password    that contains a new user password.
//     */
//    public void setPassword(IPassword password);
//    
//    
//    // --------------- Methods ---------------
//    
//    /**
//     * Adds a new account to the user.
//     * 
//     * @param   account     that will be added.
//     * @return  <code>true</code> if the account was added otherwise
//     *          <code>false</code>
//     */
//    public boolean addAccount(IAccount account);
//    
//    
//    /**
//     * Adds a range of new accounts to the user.
//     * 
//     * @param   accounts    that will be added.
//     * @param   clear       that indicates whether or not the old account list
//     *                      should be cleared before the new accounts are being
//     *                      added.
//     */
//    public void addAccounts(Collection<IAccount> accounts, boolean clear);
//    
//    
//    /**
//     * Removes an existing account from the user.
//     * 
//     * @param   account     that will be removed.
//     * @return  <code>true</code> if the account was removed otherwise
//     *          <code>false</code>
//     */
//    public boolean removeAccount(IAccount account);
//}
