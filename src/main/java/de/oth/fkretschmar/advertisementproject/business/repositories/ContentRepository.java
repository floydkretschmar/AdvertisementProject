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
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import javax.enterprise.context.Dependent;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 * Repository that defines the default CRUD methods for an {@link Content}.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@Dependent
public class ContentRepository extends AbstractJPARepository<Long, Content> {
    
    // --------------- Private static constants ---------------
    
    private static final String MATCHING_CONTENTS_PROCEDURE 
            = "P_FIND_MATCHING_CONTENTS";
    
    // --------------- Public constructors ---------------

    /**
     * Creates an new instance of {@link ContentRepository}.
     */
    public ContentRepository() {
        super(Content.class);
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Finds a random content.
     * 
     * @return a random content.
     */
    public Optional<Content> findRandomContent() {
        List<Content> results = this.findAll();
        int randomPos = ThreadLocalRandom.current().nextInt(results.size());
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(randomPos));
    }
    
    /**
     * Retrieves all contents that best match the provided 
     * {@link TargetContext}. 
     * 
     * @param context   the context that specifies the targets for the requestet
     *                  content.
     * @return          the matching contents.
     */
    public List<Object[]> findMatchingContents(TargetContext context) {
        StoredProcedureQuery query 
                = this.getEntityManager().createStoredProcedureQuery(
                        ContentRepository.MATCHING_CONTENTS_PROCEDURE);
        
        query.registerStoredProcedureParameter(
                "TARGET_AGE", 
                String.class, 
                ParameterMode.IN);
        query.registerStoredProcedureParameter(
                "TARGET_GENDER", 
                String.class, 
                ParameterMode.IN);
        query.registerStoredProcedureParameter(
                "TARGET_MARITAL_STATUS", 
                String.class, 
                ParameterMode.IN);
        query.registerStoredProcedureParameter(
                "TARGET_PURPOSE_OF_USE", 
                String.class, 
                ParameterMode.IN);
        
        query.setParameter(
                "TARGET_AGE", 
                this.buildParameterString(context.getAge()));
        query.setParameter(
                "TARGET_GENDER", 
                this.buildParameterString(context.getGender()));
        query.setParameter(
                "TARGET_MARITAL_STATUS", 
                this.buildParameterString(context.getMaritalStatus()));
        query.setParameter(
                "TARGET_PURPOSE_OF_USE", 
                this.buildParameterString(context.getPurposeOfUse()));
        
        return query.getResultList();
    }
    
    // --------------- Protected methods ---------------

    /**
     * Creates a set to store multiple {@link Content} instances.
     
     * @return  A set that can store multiple {@link Content} instances.
     */
    @Override
    protected Collection<Content> createCollection() {
        return new ArrayList<Content>();
    }
    
    
    // --------------- Private methods ---------------
    
    
    /**
     * Converts an enum set into a string to be used by the procedure for finding
     * matching contents.
     * 
     * @param <T>   the type of the enum that is being managed by the enum set.
     * @param enumValues    the actual enum set containing the values.
     * @return  the parameter string.
     */
    private <T extends Enum<T>> String buildParameterString(EnumSet<T> enumValues) {
        String parameterString = "";
        
        for (Enum<T> enumValue : enumValues) {
            parameterString += String.format("%s;", enumValue.name());
        }
        
        return parameterString;
    }
}
