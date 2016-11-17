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

import de.oth.fkretschmar.advertisementproject.business.repository.base.AbstractJPARepository;
import de.oth.fkretschmar.advertisementproject.entities.Password;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Repository that defines the default CRUD methods for a {@link Password}
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class PasswordRepository extends AbstractJPARepository<Password> {
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link PasswordRepository}.
     */
    public PasswordRepository() {
        super(Password.class);
    }
    
    // --------------- Protected methods ---------------
    
    
    /**
     * Creates a set to store multiple {@link Password} instances.
     * 
     * @return  A set that can store multiple {@link Password} instances.
     */
    @Override
    protected Collection<Password> createCollection() {
        return new ArrayList<Password>();
    }
}
