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
import de.oth.fkretschmar.advertisementproject.entities.Content;
import de.oth.fkretschmar.advertisementproject.entities.MatchingContent;
import de.oth.fkretschmar.advertisementproject.entities.TargetContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 * Repository that defines the default CRUD methods for an {@link Content}.
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class ContentRepository extends AbstractJPARepository<Long, Content> {
    
    // --------------- Public constructors ---------------

    /**
     * Creates an new instance of {@link ContentRepository}.
     */
    public ContentRepository() {
        super(Content.class);
    }
    
    
    /**
     * 
     * @param context   the context.
     */
    public void findMatchingContents(TargetContext context) {
        String queryString = 
            "(SELECT    content.ID as ID, " +
            "           COUNT(INNER_TABLE.CONTEXT_SOURCE) as MATCHED_GROUPS, " +
            "           SUM(INNER_TABLE.MATCHED_VALUES) as MATCHES_IN_GROUPS " +
            " FROM " +
            "         (SELECT       tc.ID as CONTEXT_ID, " +
            "                       'T_TARGET_AGE' as CONTEXT_SOURCE, " +
            "                       COUNT(ta.AGE) as MATCHED_VALUES " +
            "          FROM         T_TARGET_CONTEXT tc " +
            "          INNER JOIN   T_TARGET_AGE ta ON ta.CONTEXT_ID = tc.ID " +
            "          WHERE        ta.AGE in (?1) " +
            "          GROUP BY     tc.ID, 'T_TARGET_AGE' " +
            "                UNION ALL " +
            "          SELECT       tc.ID as CONTEXT_ID, " +
            "                       'T_TARGET_GENDER' as CONTEXT_SOURCE, " +
            "                       COUNT(tg.GENDER) as MATCHED_VALUES " +
            "          FROM         T_TARGET_CONTEXT tc " +
            "          INNER JOIN   T_TARGET_GENDER tg ON tg.CONTEXT_ID = tc.ID " +
            "          WHERE        tg.GENDER in (?2) " +
            "          GROUP BY     tc.ID, 'T_TARGET_GENDER' " +
            "                UNION ALL " +
            "          SELECT       tc.ID as CONTEXT_ID, " +
            "                       'T_TARGET_MARITAL_STATUS' as CONTEXT_SOURCE, " +
            "                       COUNT(tm.MARITAL_STATUS) as MATCHED_VALUES " +
            "          FROM         T_TARGET_CONTEXT tc " +
            "          INNER JOIN   T_TARGET_MARITAL_STATUS tm ON tm.CONTEXT_ID = tc.ID " +
            "          WHERE        tm.MARITAL_STATUS in (?3) " +
            "          GROUP BY     tc.ID, 'T_TARGET_MARITAL_STATUS' " +
            "                UNION ALL " +
            "          SELECT       tc.ID as CONTEXT_ID, " +
            "                       'T_TARGET_PURPOSE_OF_USE' as CONTEXT_SOURCE, " +
            "                       COUNT(tp.PURPOSE_OF_USE) as MATCHED_VALUES " +
            "          FROM         T_TARGET_CONTEXT tc " +
            "          INNER JOIN   T_TARGET_PURPOSE_OF_USE tp ON tp.CONTEXT_ID = tc.ID " +
            "          WHERE        tp.PURPOSE_OF_USE in (?4) " +
            "          GROUP BY     tc.ID, 'T_TARGET_PURPOSE_OF_USE') AS INNER_TABLE " +
            "INNER JOIN     T_CONTENT content ON content.CONTEXT_ID = INNER_TABLE.CONTEXT_ID " +
            "GROUP BY       content.ID) AS MATCHING_CONTENTS " +
            "ORDER BY       MATCHING_CONTENTS.MATCHED_GROUPS DESC, MATCHING_CONTENTS.MATCHES_IN_GROUPS DESC";
        
        List<Object> parameters = new ArrayList<Object>();
        parameters.addAll(context.getAge());
        parameters.addAll(context.getGender());
        parameters.addAll(context.getMaritalStatus());
        parameters.addAll(context.getPurposeOfUse());
        
        TypedQuery<Object[]> query = this.createQuery(Object[].class, queryString, parameters.toArray());
        
        List<Object[]> ergebnis = query.getResultList();
        for(Object[] s : ergebnis){
           System.out.println(s[0].toString() + s[1].toString());
        }
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
    
}
