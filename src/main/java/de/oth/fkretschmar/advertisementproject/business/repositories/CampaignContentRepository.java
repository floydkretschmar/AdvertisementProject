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
import de.oth.fkretschmar.advertisementproject.entities.CampaignContent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Repository that defines the default CRUD methods for an {@link CampaignContent}.
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class CampaignContentRepository extends AbstractJPARepository<Long, CampaignContent> {
    
    // --------------- Public constructors ---------------

    /**
     * Creates an new instance of {@link CampaignContentRepository}.
     */
    public CampaignContentRepository() {
        super(CampaignContent.class);
    }
    
    // --------------- Protected methods ---------------

    /**
     * Creates a set to store multiple {@link CampaignContent} instances.
     
     * @return  A set that can store multiple {@link CampaignContent} instances.
     */
    @Override
    protected Collection<CampaignContent> createCollection() {
        return new ArrayList<CampaignContent>();
    }
    
}
