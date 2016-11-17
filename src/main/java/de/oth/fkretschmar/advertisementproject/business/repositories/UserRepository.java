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
package de.oth.fkretschmar.advertisementproject.business.repositories;

import de.oth.fkretschmar.advertisementproject.business.repositories.base.AbstractJPARepository;
import de.oth.fkretschmar.advertisementproject.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 * Repository that defines the default CRUD methods for a {@link User}
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class UserRepository extends AbstractJPARepository<Long, User> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link UserRepository}.
     */
    public UserRepository() {
        super(User.class);
    }
    
    
    // --------------- Public methods ---------------
    
    /**
     * Checks whether or not an e-Mail is already in use with a different user.
     * 
     * @param   eMail   that will be validated.
     * @return  <code>true</code> if the e-Mail is already in use.
     *          <code>false</code> otherwise
     */
    public boolean iseMailAlreadyInUse(String eMail) {
        TypedQuery<Integer> query = this.accessQuery(
                Integer.class, 
                User.IS_EMAIL_IN_USE_QUERY,
                eMail);
        
        Integer result = query.getSingleResult();
        return result == 1;
    }
    
    
    /**
     * Finds a user using its unique e-mail.
     * 
     * @param   eMail   that uniqly identifies an user.
     * @return  An <code>User</code> that is identified by the email or <code>
     *          null</code> if no user with this e-mail exists.
     */
    public User findForEmail(String eMail) {
        TypedQuery<User> query = this.accessQuery(
                User.class, 
                User.FIND_FOR_EMAIL_QUERY, 
                eMail);
        
        List<User> users = query.getResultList();
        
        if(users.isEmpty())
            return null;
        else
            return users.get(0);
    }

    
    // --------------- Protected methods ---------------
    
    /**
     * Creates a set to store multiple {@link User} instances.
     * 
     * @return  A set that can store multiple {@link User} instances.
     */
    @Override
    protected Collection<User> createCollection() {
        return new ArrayList<User>();
    }
}
