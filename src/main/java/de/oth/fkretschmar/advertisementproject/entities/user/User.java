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

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.base.AbstractStringKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.base.converter.LocalDateAttributeConverter;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
                    + "where USR.id = ?1")
})
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class User extends AbstractStringKeyedEntity {
    
    // --------------- Static constants ---------------
    
    /**
     * Defines the name of the query to check, whether or not an email is 
     * already in use by a different user.
     */
    public static final String IS_EMAIL_IN_USE_QUERY = "User.isEMailAlreadyInUse";
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the accounts an user has specified.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private final Set<Account> accounts = new HashSet<Account>();
    
    /**
     * Stores the address of the user.
     */
    @NotNull
    @Getter
    @Setter
    private Address address;
    
    
    /**
     * Stores the birthdate of the user.
     */
    @NotNull
    @Column(name = "BIRTHDATE")
    @Getter
    @Setter
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate birthdate;
    
    
    /**
     * Stores the campaigns comissioned by the user.
     */
    @NotNull
    @OneToMany(mappedBy = "comissioner", fetch = FetchType.EAGER)
    private final Set<Campaign> campaigns = new HashSet<Campaign>();
    
    /**
     * Stores the name of the company the user is buying ads for.
     */
    @Column(name = "COMPANY")
    @Getter
    @Setter
    private String company;
    
    /**
     * Stores the first name of the user.
     */
    @NotNull
    @Column(name = "FIRST_NAME", nullable = false)
    @Getter
    @Setter
    private String firstName;
    
    /**
     * Stores the last name of the user.
     */
    @NotNull
    @Column(name = "LAST_NAME", nullable = false)
    @Getter
    @Setter
    private String lastName;
    
    /**
     * Stores the current password of the user.
     */
    @NotNull
    @Getter
    @Setter
    private Password password;
        
    
    // --------------- Private constructor ---------------
    
    
    /**
     * Creates a new instance of {@link User} using the email address.
     * 
     * @param eMailAddress  that can be used to contact and uniquely identify a 
     *                      user.
     * @param address       the address of the user.
     * @param birthdate     the birthdate of the user.
     * @param company       the name of the company the user is buying ads for.
     * @param firstName     the first name of the user.
     * @param lastName      the last name of the user.
     * @param password      the current password of the user.
     */
    private User(
            String eMailAddress,
            Address address, 
            LocalDate birthDate,
            String company, 
            String firstName,
            String lastName,
            Password password) {
        super(eMailAddress);
        this.address = address;
        this.birthdate = birthDate;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
    
    
    // --------------- Public getters and setters ---------------
 
    
    /**
     * Gets the bank accounts an user has specified.
     * 
     * @return  the bank accounts of an user.
     */
    public Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(this.accounts);
    }
    
    
    /**
     * Gets the campaigns comissioned by the user.
     * 
     * @return  the campaigns of an user.
     */
    public Collection<Campaign> getCampaigns() {
        return Collections.unmodifiableCollection(this.campaigns);
    }
    
    /**
     * Gets the e-mail address of the user.
     * 
     * @return  A String that contains the e-mail address.
     */
    public String geteMailAddress() {
        return this.getId();
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
     * Adds a new campaign to the user.
     * 
     * @param   campaign     that will be added.
     * @return  <code>true</code> if the campaign was added otherwise
     *          <code>false</code>
     */
    public boolean addCampaign(Campaign campaign) {
        return this.campaigns.add(campaign);
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
    
    
    /**
     * Removes an existing campaign from the user.
     * 
     * @param   campaign     that will be removed.
     * @return  <code>true</code> if the campaign was removed otherwise
     *          <code>false</code>
     */
    public boolean removeCampaign(Campaign campaign) {
        return this.campaigns.remove(campaign);
    }
    
    
    // --------------- Static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link User}.
     * 
     * @param eMailAddress  that can be used to contact and uniquely identify a 
     *                      user.
     * @param address       the address of the user.
     * @param birthdate     the birthdate of the user.
     * @param company       the name of the company the user is buying ads for.
     * @param firstName     the first name of the user.
     * @param lastName      the last name of the user.
     * @param password      the current password of the user.
     * @return  the built {@link User}.
     */
    @Builder(
            builderMethodName = "createUser", 
            builderClassName = "UserBuilder",
            buildMethodName = "build")
    private static User validateAndCreateUser(
            String eMailAddress,
            Address address, 
            LocalDate birthdate,
            String company, 
            String firstName,
            String lastName,
            Password password) throws BuilderValidationException {
        if(address == null)
            throw new BuilderValidationException(
                    User.class,
                    "The address can not be null.");
        
        if(birthdate == null)
            throw new BuilderValidationException(
                    User.class,
                    "The birthdate can not be null.");
        
        if(firstName == null || firstName.isEmpty())
            throw new BuilderValidationException(
                    User.class,
                    "The first name can not be null or empty.");
        
        if(lastName == null || lastName.isEmpty())
            throw new BuilderValidationException(
                    User.class,
                    "The first name can not be null or empty.");
        
        if(password == null)
            throw new BuilderValidationException(
                    User.class,
                    "The password can not be null.");
        
        return new User(
                eMailAddress,
                address,
                birthdate,
                company,
                firstName,
                lastName,
                password);
    }
}
