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
package de.oth.fkretschmar.advertisementproject.business.services.base;

import de.oth.fkretschmar.advertisementproject.entities.base.IEntity;

/**
 * Defines public methods for services that allow to manage single entities.
 *
 * @author  fkre 
 * @param   <T>     the type of the entity that is being managed.
 */
public interface IEntityService<T extends IEntity<?>> {

    // --------------- Methods ---------------
    
    /**
     * Finds the entity defined by the specified id.
     * 
     * @param   idAsString  the id that defines the entity in text form.
     * @return  the entity.
     */
    public T find(String idAsString);
}
