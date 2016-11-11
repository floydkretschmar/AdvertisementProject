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
package de.oth.fkretschmar.advertisementproject.entity;

import de.oth.fkretschmar.advertisementproject.entity.base.AbstractAutoGenerateKeyedEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Represents an user using the system.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@Entity(name = "T_USER")
@NamedQueries({
    @NamedQuery(
            name = User.IS_EMAIL_IN_USE_QUERY, 
            query = " select case when (count(USR) > 0) then true "
                    + "      else false end "
                    + " from T_USER USR "
                    + "where USR.eMailAddress = ?1"),
    @NamedQuery(
            name = User.FIND_FOR_EMAIL_QUERY, 
            query = " select USR"
                    + " from T_USER USR "
                    + "where USR.eMailAddress = ?1")
})
public class User extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Public static constants ---------------
    
    /**
     * Defines the name of the query to check, whether or not an email is 
     * already in use by a different user.
     */
    public static final String IS_EMAIL_IN_USE_QUERY = "User.isEMailAlreadyInUse";
    
    
    /**
     * Defines the name of the query to select an user by its unique e-mail 
     * address.
     */
    public static final String FIND_FOR_EMAIL_QUERY = "User.findForEMail";
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the accounts an user has specified.
     */
    @OneToMany
    //@JoinColumn(name = "ACCOUNT_USER_ID")
    //@JoinColumn(name = "ACCOUNT_USER_ID", referencedColumnName = "ID")
    private final Set<Account> accounts;
    
    /**
     * Stores the address of the user.
     */
    @NotNull
    @OneToOne
    //@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;
    
    /**
     * Stores the name of the company the user is buying ads for.
     */
    @Column(name = "COMPANY")
    private String company;
    
    /**
     * Stores the e-mail address of the user that also functions as the login
     * name.
     */
    @NotNull
    @Column(name = "E_MAIL", unique = true)
    private String eMailAddress;
    
    /**
     * Stores the first name of the user.
     */
    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    /**
     * Stores the last name of the user.
     */
    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;
    
    /**
     * Stores the current password of the user.
     */
    @NotNull
    @OneToOne
    //@JoinColumn(name = "PASSWORD_ID")
    //@JoinColumn(name = "PASSWORD_ID", referencedColumnName = "ID")
    private Password password;
    
    
    // --------------- Protected constructor ---------------
    
    
    /**
     * Creates a new instance of {@link User}.
     */
    protected User() {
        super();
        this.accounts = new HashSet<Account>();
    }
    
    
    // --------------- Public constructor ---------------
    
    /**
     * Creates a new instance of {@link User} using the specified e-mail address,
     * password, first name, last name and address.
     * 
     * @param eMailAddress  that can be used to contact and uniquely identify a 
     *                      user.
     * @param password      the user password.
     * @param firstName     the first name of the user.
     * @param lastName      the last name of the user.
     * @param address       the address of the user.
     */
    public User(
            String eMailAddress, 
            Password password,
            String firstName,
            String lastName,
            Address address) {
        this();
        this.eMailAddress = eMailAddress;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    
    
    // --------------- Public getters and setters ---------------

    
    /**
     * Gets the address information of the user.
     * 
     * @return  A instance of {@link Address} that contains all the address 
     *          information of the user.
     */
    public Address getAddress() {
        return this.address;
    }
 
    
    /**
     * Gets the bank accounts an user has specified.
     * 
     * @return  the bank accounts of an user.
     */
    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(this.accounts);
    }
    
    
    /**
     * Gets the name of the company the user is buying ads for.
     * 
     * @return  A String that contains the company name.
     */
    public String getCompany() {
        return this.company;
    }
    
    
    /**
     * Gets the e-mail address of the user.
     * 
     * @return  A String that contains the e-mail address.
     */
    public String geteMailAddress() {
        return this.eMailAddress;
    }

    
    /**
     * Gets the first name of the user.
     * 
     * @return  A String that contains the first name. 
     */
    public String getFirstName() {
        return this.firstName;
    }

    
    /**
     * Gets the last name of the user.
     * 
     * @return  A String that contains the last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    
    /**
     * Gets the password of the user.
     * 
     * @return  A {@link Password} that represents the current user password.
     */
    public Password getPassword() {
        return this.password;
    }

    
    /**
     * Sets the address information of the user.
     * 
     * @param   address that contains all the address information of the user.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    
    /**
     * Sets the name of the company the user is buying ads for.
     * 
     * @param company   that contains the company name.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    
    /**
     * Sets the first name of the user.
     * 
     * @param   firstName   that contains the first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    /**
     * Sets the last name of the user.
     * 
     * @param   lastName    that contains the last name. 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    /**
     * Sets the password of the user.
     * 
     * @param   password    that contains a new user password.
     */
    public void setPassword(Password password) {
        this.password = password;
    }   
    
    
    // --------------- Public methods ---------------
    
    /**
     * Adds a new account to the user.
     * 
     * @param   account     that will be added.
     * @return  <code>true</code> if the account was added otherwise
     *          <code>false</code>
     */
    public boolean addAccount(Account account) {
        return this.accounts.add(account);
    }
    
    
    /**
     * Adds a range of new accounts to the user.
     * 
     * @param   accounts    that will be added.
     * @param   clear       that indicates whether or not the old account list
     *                      should be cleared before the new accounts are being
     *                      added.
     */
    public void addAccounts(Set<Account> accounts, boolean clear) {
        if(clear)
            this.accounts.clear();
        
        for (Account account : accounts) {
            this.addAccount(account);
        }
    }
    
    
    /**
     * Removes an existing account from the user.
     * 
     * @param   account     that will be removed.
     * @return  <code>true</code> if the account was removed otherwise
     *          <code>false</code>
     */
    public boolean removeAccount(Account account) {
        return this.accounts.remove(account);
    }
}
