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

import de.oth.fkretschmar.advertisementproject.entities.base.AbstractStringKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.base.EntityState;
import de.oth.fkretschmar.advertisementproject.entities.base.IUndeletableEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class User extends AbstractStringKeyedEntity implements IUndeletableEntity<String> {
    
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
    @OneToMany
    @JoinTable(
        name="T_USER_ACCOUNT",
        joinColumns=
            @JoinColumn(name="USER_ID", referencedColumnName="ID"),
        inverseJoinColumns=
            @JoinColumn(name="ACCOUNT_ID", referencedColumnName="ID")
    )
    private final Collection<Account> accounts = new ArrayList<Account>();
    
    /**
     * Stores the address of the user.
     */
    @NotNull
    @OneToOne
    @Getter
    @Setter
    private Address address;
    
    
    /**
     * Stores the campaigns comissioned by the user.
     */
    @NotNull
    @OneToMany(mappedBy = "comissioner")
    private final Collection<Campaign> campaigns = new ArrayList<Campaign>();
    
    /**
     * Stores the name of the company the user is buying ads for.
     */
    @Column(name = "COMPANY")
    @Getter
    @Setter
    private String company;
    
    /**
     * Stores the contents created by a user to be used by a campaign.
     */
    @NotNull
    @OneToMany
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private Collection<Content> contents = new ArrayList<Content>();
    
    /**
     * Stores the state of the entity.
     **/
    @Column(name = IUndeletableEntity.ENTITY_STATE_COLUMN_NAME)
    @Enumerated(EnumType.STRING)
    private EntityState state = EntityState.CREATED;
    
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
    @OneToOne
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
     * @param company       the name of the company the user is buying ads for.
     * @param firstName     the first name of the user.
     * @param lastName      the last name of the user.
     * @param password      the current password of the user.
     */
    private User(
            String eMailAddress,
            Address address, 
            String company, 
            String firstName,
            String lastName,
            Password password) {
        super(eMailAddress);
        this.address = address;
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
     * Gets the contents created by a user to be used by a campaign.
     * 
     * @return  the contents of an user.
     */
    public Collection<Content> getContents() {
        return Collections.unmodifiableCollection(this.contents);
    }
    
    /**
     * Gets the state of the entity.
     * @return  the entity state.
     */
    @Override
    public EntityState getEntityState() {
        return this.state;
    }

    /**
     * Sets the state of the entity.
     * @param state     the new state of the entity.s
     */
    @Override
    public void setEntityState(EntityState state) {
        this.state = state;
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
     * Adds a new content to the user.
     * 
     * @param   content     that will be added.
     * @return  <code>true</code> if the content was added otherwise
     *          <code>false</code>
     */
    public boolean addContent(Content content) {
        return this.contents.add(content);
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
    
    
    /**
     * Removes an existing content from the user.
     * 
     * @param   content     that will be removed.
     * @return  <code>true</code> if the content was removed otherwise
     *          <code>false</code>
     */
    public boolean removeContent(Content content) {
        return this.contents.remove(content);
    }
    
    
    // --------------- Static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link User}.
     * 
     * @param eMailAddress  that can be used to contact and uniquely identify a 
     *                      user.
     * @param address       the address of the user.
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
            String company, 
            String firstName,
            String lastName,
            Password password) throws BuilderValidationException {
        if(address == null)
            throw new BuilderValidationException(
                    User.class,
                    "The address can not be null.");
        
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
                company,
                firstName,
                lastName,
                password);
    }
}
