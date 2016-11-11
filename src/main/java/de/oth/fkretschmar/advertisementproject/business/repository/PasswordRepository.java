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
package de.oth.fkretschmar.advertisementproject.business.repository;

import de.oth.fkretschmar.advertisementproject.business.repository.base.AbstractJPASetRepository;
import de.oth.fkretschmar.advertisementproject.entity.Password;

import java.util.HashSet;
import java.util.Set;

/**
 * Repository that defines the default CRUD methods for a {@link Password}
 * 
 * @author fkre Floyd Kretschmar
 */
public class PasswordRepository extends AbstractJPASetRepository<Password> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link PasswordRepository}.
     */
    public PasswordRepository() {
        super(Password.class);
    }
    
    // --------------- Protected methods ---------------
    
    
    /**
     * Creates a set to store multiple passwords.
     * 
     * @return  A set that can store multiple passwords.
     */
    @Override
    protected Set<Password> createCollection() {
        return new HashSet<Password>();
    }
}
