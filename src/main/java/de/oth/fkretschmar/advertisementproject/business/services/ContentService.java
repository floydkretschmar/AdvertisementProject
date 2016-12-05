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
package de.oth.fkretschmar.advertisementproject.business.services;

import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRepository;
import de.oth.fkretschmar.advertisementproject.entities.Content;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Content} instances.
 *
 * @author fkre
 */
@RequestScoped
public class ContentService implements Serializable {

    // --------------- Private fields ---------------

    /**
     * Stores the repository used to manage {@link Content} entites.
     */
    @Inject
    ContentRepository contentRepository;
    

    // --------------- Public methods ---------------
    
    
    /**
     * Creates the specified {@link Content}.
     * 
     * @param   content   the content that will be saved.
     */
    @Transactional
    public void create(Content content) {
        this.contentRepository.persist(content);
    }
    
    /**
     * Creates the specified instances of {@link Content}.
     * 
     * @param contents the content instances that will be saved.
     */
    @Transactional
    public void create(Collection<Content> contents) {
        this.contentRepository.persist(contents);
    }
    
    /**
     * Deletes the specified {@link Content} from the database.
     * 
     * @param   content    that will be deleted.
     */
    @Transactional
    public void delete(Content content) {
        this.contentRepository.remove(content);
    }
}
