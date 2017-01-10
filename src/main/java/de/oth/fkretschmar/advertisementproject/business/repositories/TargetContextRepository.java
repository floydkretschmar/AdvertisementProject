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
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;

import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.Dependent;

/**
 * Repository that defines the default CRUD methods for an {@link TargetContext}.
 *
 * @author fkre
 */
@Dependent
public class TargetContextRepository extends AbstractRepository<Long, TargetContext> {
    
    // --------------- Public constructors ---------------

    /**
     * Creates an new instance of {@link TargetContextRepository}.
     */
    public TargetContextRepository() {
        super(TargetContext.class);
    }
    
    // --------------- Protected methods ---------------

    /**
     * Creates a set to store multiple {@link TargetContext} instances.
     
     * @return  A set that can store multiple {@link TargetContext} instances.
     */
    @Override
    protected Collection<TargetContext> createCollection() {
        return new ArrayList<TargetContext>();
    }
}
