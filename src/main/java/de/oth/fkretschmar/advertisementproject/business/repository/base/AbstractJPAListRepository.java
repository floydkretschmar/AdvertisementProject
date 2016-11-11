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
package de.oth.fkretschmar.advertisementproject.business.repository.base;

import de.oth.fkretschmar.advertisementproject.entity.base.IEntity;

import java.util.List;

/**
 * Represents an repository that defines the default CRUD methods when using
 * JPA where multiple entites are being stored in a set.
 * 
 * @author  fkre
 * @param   <T>     The type that specifies which entity is being managed by the
 *                  repository.
 */
public abstract class AbstractJPAListRepository<T extends IEntity> 
        extends AbstractJPARepository<T, List<T>> {

    /**
     * Creates a new instance of {@link AbstractJPACollectionRepository} using 
     * the specified class type.
     * 
     * @param   classType   of the entity being managed by the repository.
     */
    public AbstractJPAListRepository(Class<T> classType) {
        super(classType);
    }    
}
