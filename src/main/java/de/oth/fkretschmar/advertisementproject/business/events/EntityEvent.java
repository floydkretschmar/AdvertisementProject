/*
 * Copyright (C) 2017 Admin
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
package de.oth.fkretschmar.advertisementproject.business.events;

import de.oth.fkretschmar.advertisementproject.entities.base.IEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author  Admin
 * @param   <T>     the entity type that was being effected.
 */
@RequiredArgsConstructor
public class EntityEvent<T extends IEntity<?>> {
    
    // --------------- Private methods ---------------
    
    /**
     * Stores the entity that was effected by the event.
     */
    @Getter
    private final T entity;
}
