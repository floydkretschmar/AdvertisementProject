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
public final class UserBuilder extends AbstractEntityBuilder<String, User> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link UserBuilder}.
     * 
     * @param   eMailAddress    the email address that can be used to contact 
     *                          and uniquely identify the user that is being 
     *                          built.
     */
    private UserBuilder(String eMailAddress) {
        super(new User(eMailAddress));
    }
    
    // --------------- Public static methods ---------------
    
    /**
     * Creates a new instance of {@link UserBuilder}.
     *      * 
     * @param   eMailAddress    the email address that can be used to contact 
     *                          and uniquely identify the user that is being 
     *                          built.
     * @return  the builder used to build the user.
     */
    public static UserBuilder create(String eMailAddress) {
        return new UserBuilder(eMailAddress);
    }
    
    
    // --------------- Public methods ---------------

    
    /**
     * Creates the address using the address information of the user.
     * 
     * @param   address     that contains all the address information of the user.
     * @return  the user builder used to build the user.
     */
    public UserBuilder withAddress(Address address) {
        this.getEntity().setAddress(address);
        return this;
    }

    
    /**
     * Creates the address using the name of the company the user is buying ads 
     * for.
     * 
     * @param   company   that contains the company name.
     * @return  the user builder used to build the user.
     */
    public UserBuilder withCompany(String company) {
        this.getEntity().setCompany(company);
        return this;
    }

    
    /**
     * Creates the address using  the first name of the user.
     * 
     * @param   firstName   that contains the first name.
     * @return  the user builder used to build the user.
     */
    public UserBuilder withFirstName(String firstName) {
        this.getEntity().setFirstName(firstName);
        return this;
    }

    
    /**
     * Creates the address using  the last name of the user.
     * 
     * @param   lastName    that contains the last name. 
     * @return  the user builder used to build the user.
     */
    public UserBuilder withLastName(String lastName) {
        this.getEntity().setLastName(lastName);
        return this;
    }

    
    /**
     * Creates the address using the password of the user.
     * 
     * @param   password    that contains a user password.
     * @return  the user builder used to build the user.
     */
    public UserBuilder withPassword(Password password) {
        this.getEntity().setPassword(password);
        return this;
    }
    
    // --------------- Protected methods ---------------
    
    /**
     * Validates the {@link User} and makes sure the attributes are set
     * properly.
     * 
     * @param   entity  the user that will be validated.
     * @throws  EntityBuilderValidationException that occurs when the 
     *          validation of the values of an entity failed.
     */
    @Override
    protected void validate(User entity) 
            throws EntityBuilderValidationException {
        if(entity.getAddress() == null)
            throw new EntityBuilderValidationException(
                    UserBuilder.class,
                    "The address can not be null.");
        
        if(entity.getFirstName() == null || entity.getFirstName().isEmpty())
            throw new EntityBuilderValidationException(
                    UserBuilder.class,
                    "The first name can not be null or empty.");
        
        if(entity.getLastName() == null || entity.getLastName().isEmpty())
            throw new EntityBuilderValidationException(
                    UserBuilder.class,
                    "The first name can not be null or empty.");
        
        if(entity.getPassword() == null)
            throw new EntityBuilderValidationException(
                    UserBuilder.class,
                    "The password can not be null.");
    }
}
