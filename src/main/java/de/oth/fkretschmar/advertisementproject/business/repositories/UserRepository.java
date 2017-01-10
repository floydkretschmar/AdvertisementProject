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

import de.oth.fkretschmar.advertisementproject.business.repositories.base.AbstractRepository;
import de.oth.fkretschmar.advertisementproject.entities.user.User;

import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;

/**
 * Repository that defines the default CRUD methods for an {@link User}
 * 
 * @author  fkre    Floyd Kretschmar
 */
@Dependent
public class UserRepository extends AbstractRepository<String, User> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link UserRepository}.
     */
    public UserRepository() {
        super(User.class);
    }
    
    
    // --------------- Public methods ---------------
    
    /**
     * Checks whether or not an e-Mail is already in use with a different 
     * {@link User}.
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
