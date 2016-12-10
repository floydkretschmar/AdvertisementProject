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

import de.oth.fkretschmar.advertisementproject.business.repositories.CampaignRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.TargetContextRepository;

import de.oth.fkretschmar.advertisementproject.entities.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.Content;
import de.oth.fkretschmar.advertisementproject.entities.TargetContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Campaign} instances.
 *
 * @author fkre
 */
@RequestScoped
public class ContentService implements Serializable {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Campaign} entites.
     */
    @Inject
    private CampaignRepository campaignRepository;

    /**
     * Stores the repository used to manage {@link Content} entites.
     */
    @Inject
    private ContentRepository contentRepository;

    /**
     * Stores the repository used to manage {@link TargetContext} entites.
     */
    @Inject
    private TargetContextRepository contextRepository;
    

    // --------------- Public methods ---------------
    
    
    /**
     * Creates a new {@link Content} and links it to the specified campaign.
     * 
     * @param   campaign    to which the content will be linked.
     * @param   content     the content that will be saved.
     * @return  the created {@link Content}.
     */
    @Transactional
    public Campaign createContentForCampaign(
            Campaign campaign, Content content) {
        this.createContent(content);
        
        // 3. add the content to the campaign
        campaign = this.campaignRepository.merge(campaign);
        campaign.addContent(content);
        return campaign;
    }
    
    
    /**
     * Retrieves an advertisement content that best matches the provided 
     * {@link TargetContext}. 
     * 
     * @param context   the context that specifies the targets for the requestet
     *                  content.
     * @return          the best matching content.
     */
    public Optional<Content> requestContent(TargetContext context) {
        List<Object[]> results = this.contentRepository.findMatchingContents(context);
        
        if(results == null || results.isEmpty())
            return null;
        
        List<MatchingContent> matchingContents = new ArrayList<MatchingContent>();
        
        results.forEach(result -> matchingContents.add(
                    new MatchingContent(
                            this.contentRepository.find(((BigInteger)result[0]).longValue()),
                            ((BigInteger)result[1]).intValue(),
                            ((BigDecimal)result[2]).intValue()
                        )));
        
        // don't just deliver the best content but also take into account the 
        // amount of money being paid per content as well as a little bit of
        // random chance ;)
        Optional<MatchingContent> bestContent = matchingContents.stream().sorted(
                (content1, content2) -> 
                    {
                        double compareValue1 = content1.groupMatches * 
                                        content1.machtesInGroup * 
                                        content1.getContent()
                                                .getPricePerRequest()
                                                .getNumber().doubleValueExact() *
                                        ThreadLocalRandom.current().nextInt(1, 6);
                        double compareValue2 = (content2.groupMatches * 
                                        content2.machtesInGroup * 
                                        content2.getContent()
                                                .getPricePerRequest()
                                                .getNumber().doubleValueExact()) *
                                        ThreadLocalRandom.current().nextInt(1, 6);

                        return Double.compare(compareValue1, compareValue2);
                    }).findFirst();
        
        return bestContent.isPresent() 
                ? Optional.of(bestContent.get().getContent()) : this.requestRandomContent();
    }
    
    
    /**
     * Retrieves a random advertisement that has not been matched with any target
     * context.
     * @return  the content that has been chosen randomly.
     */
    public Optional<Content> requestRandomContent() {
        return this.contentRepository.findRandomContent();
    }
    
    // --------------- Package-Private methods ---------------
    
    
    /**
     * Creates a new content.
     * 
     * @param content the content that will be created.
     */
    @Transactional
    void createContent(Content content) {
        // 1. Persist the context:
        this.contextRepository.persist(content.getContext());
        
        // 2. Persist the content
        this.contentRepository.persist(content);
    }
    
    /**
     * Deletes the specified {@link Content} from the database.
     * 
     * @param   content    that will be deleted.
     */
    @Transactional
    void deleteContent(Content content) {
        this.contextRepository.remove(content.getContext());
        content.setContext(null);
        this.contentRepository.remove(content);
    }
    
    
    // --------------- Private classes ---------------
    
    
    /**
     * Represents a content that matches the provided {@link TargetContext}.
     */
    @AllArgsConstructor
    private class MatchingContent {
        
        /**
         * Stpres the actual content that has been matched.
         */
        @Getter
        private Content content;
        
        /**
         * Stores the number of general target groups that the content has 
         * matched.
         **/
        @Getter
        private int groupMatches;
        
        /**
         * Stores the total number of subsets within the broader target groups
         * that the content has matched.
         */
        @Getter
        private int machtesInGroup;
    }
}
