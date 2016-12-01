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

import de.oth.fkretschmar.advertisementproject.business.HashHelper;
import de.oth.fkretschmar.advertisementproject.business.repositories.AdvertisementRepository;
import de.oth.fkretschmar.advertisementproject.entities.Advertisement;
import java.io.Serializable;

import java.math.BigInteger;
import java.util.Arrays;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Advertisement} instances.
 *
 * @author fkre
 */
@RequestScoped
public class AdvertisementService implements Serializable {

    // --------------- Private fields ---------------

    /**
     * Stores the repository used to manage {@link Advertisement} entites.
     */
    @Inject
    AdvertisementRepository advertisementRepository;
    

    // --------------- Public methods ---------------
    
    
    /**
     * Creates the specified {@link Advertisement}.
     * 
     * @param   advertisement   the advertisement that will be saved.
     * @return                  the saved advertisement.
     */
    @Transactional
    public Advertisement create(Advertisement advertisement) {
        return this.advertisementRepository.persist(advertisement);
    }
    
    
    /**
     * Deletes the specified {@link Advertisement} from the database.
     * 
     * @param   advertisement    that will be deleted.
     */
    @Transactional
    public void delete(Advertisement advertisement) {
        this.advertisementRepository.remove(advertisement);
    }
    
    
    /**
     * @param   id 
     */
    @Transactional
    public Advertisement find(long id) {
        return this.advertisementRepository.find(id);
    }
}
