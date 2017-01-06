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
import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRequestRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.TargetContextRepository;
import de.oth.fkretschmar.advertisementproject.business.services.base.IContentProviderService;
import de.oth.fkretschmar.advertisementproject.business.services.base.IContentService;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.billing.ContentRequest;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.money.Money;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Content} instances.
 *
 * @author  fkre    Floyd Kretschmar
 */
@RequestScoped
public class ContentService implements Serializable, IContentService, IContentProviderService {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Campaign} entites.
     */
    @Inject
    private CampaignRepository campaignRepository;
    
    /**
     * Stores the service used to manage {@link Campaign} entites.
     */
    @Inject
    private CampaignService campaignService;

    /**
     * Stores the repository used to manage {@link Content} entites.
     */
    @Inject
    private ContentRepository contentRepository;

    /**
     * Stores the repositorz used to manage {@link ContentRequest} entities.
     */
    @Inject
    private ContentRequestRepository contentRequestRepository;
    
    /**
     * Stores the repository used to manage {@link TargetContext} entites.
     */
    @Inject
    private TargetContextRepository contextRepository;
    

    // --------------- Public methods ---------------
    
    
    
    /**
     * Creates a new content.
     * 
     * @param content the content that will be created.
     */
    @Transactional
    public void createContent(Content content) {
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
    public void deleteContent(Content content) {
        this.contextRepository.remove(content.getContext());
        content.setContext(null);
        this.contentRepository.remove(content);
    }
    
    
    /**
     * Creates a new {@link Content} and links it to the specified campaign.
     * 
     * @param   campaign    to which the content will be linked.
     * @param   content     the content that will be saved.
     * @return  the created {@link Content}.
     */
    @Transactional
    @Override
    public Campaign createContentForCampaign(
            Campaign campaign, Content content) {
        // campaign = not owner -> set content first on campaign
        campaign = this.campaignRepository.merge(campaign);
        campaign.addContent(content);
                
        // then set campaign and create content
        content.setCampaign(campaign);
        this.createContent(content);
        
        return campaign;
    }
    
    
    /**
     * Retrieves an advertisement content that best matches the provided 
     * {@link TargetContext}. 
     * 
     * @param source    the text that identifies the source of the request.
     * @param context   the context that specifies the targets for the requestet
     *                  content.
     * @return          the best matching content.
     */
    @Transactional
    @Override
    public Optional<Content> requestContent(String source, TargetContext context) {
        List<Object[]> results = this.contentRepository.findMatchingContents(context);
        
        if(results == null || results.isEmpty())
            return null;
        
        List<MatchingContent> matchingContents = new ArrayList<MatchingContent>();
        
        MoneyFormatterBuilder formaterBuilder = new MoneyFormatterBuilder().appendAmount().appendCurrencyCode();
        MoneyFormatter formater = formaterBuilder.toFormatter(Locale.GERMANY);  
        results.forEach(result -> matchingContents.add(
                    new MatchingContent(
                            (String)result[0],
                            formater.parseMoney((String)result[1]),
                            ((BigInteger)result[2]).intValue(),
                            ((BigDecimal)result[3]).intValue()
                        )));
        
        // don't just deliver the best matching content but also take into 
        // account the amount of money being paid per content as well as a 
        // little bit of random chance ;)
        Optional<MatchingContent> bestContent = matchingContents.stream().sorted(
                (content1, content2) -> 
                    {
                        double compareValue1 = (10 * content1.groupMatches * 
                                        content1.machtesInGroup * 
                                        content1.getPricePerRequest().getAmountMinorLong()) +
                                        ThreadLocalRandom.current().nextInt(5, 10);
                        double compareValue2 = (10 * content2.groupMatches * 
                                        content2.machtesInGroup * 
                                        content2.getPricePerRequest().getAmountMinorLong()) +
                                        ThreadLocalRandom.current().nextInt(5, 10);

                        return Double.compare(compareValue1, compareValue2);
                    }).findFirst();
        
        if(bestContent.isPresent()) {
            Content content = this.contentRepository.find(bestContent.get().getContentId());
            this.createContentRequest(source, content);
            return Optional.of(content);
        }
        
        return this.requestRandomContent(source);
    }
    
    
    /**
     * Retrieves a random advertisement that has not been matched with any target
     * context.
     * 
     * @param   source  the text that identifies the source of the request.
     * @return  the content that has been chosen randomly.
     */
    @Transactional
    @Override
    public Optional<Content> requestRandomContent(String source) {
        Optional<Content> randomContent = this.contentRepository.findRandomContent();
        
        if(randomContent.isPresent()) {
            this.createContentRequest(source, randomContent.get());
        }
            
        return randomContent;
    }
    
    
    // --------------- Private methods ---------------
    
    
    /**
     * Creates a content request for the specified content and source.
     * 
     * @param   source  the text that identifies the source of the request.
     * @param   content the content that was requested.
     */
    @Transactional
    private void createContentRequest(String source, Content content) {
        this.contextRepository.merge(content.getContext());
        this.contentRepository.merge(content);
        
        // remove one from the number of requests of this specific content
        content.setNumberOfRequests(content.getNumberOfRequests() - 1);
        
        // if the number of requests on this content are depleted 
        // -> make sure the campaign still has a non depleted content
        // -> if not: end campaign
        if (content.getNumberOfRequests() == 0) {
            boolean campaignHasRunningContent = false;
            
            for (Content campaignContent : content.getCampaign().getContents()) {
                if(campaignContent.getNumberOfRequests() > 0) {
                    campaignHasRunningContent = true;
                    break;
                }
            }
            
            if (!campaignHasRunningContent) {
                this.campaignService.endCampaign(content.getCampaign());
            }
        }
        
        ContentRequest request = ContentRequest.createContentRequestLog()
                .content(content)
                .requestSource(source).build();
        this.contentRequestRepository.persist(request);
    }
    
    // --------------- Private classes ---------------
    
    /**
    * Represents a content that matches the provided {@link TargetContext}.
    */
   @AllArgsConstructor
   private class MatchingContent {

       /**
        * Stpres the id of the actual content that has been matched.
        */
       @Getter
       private final String contentId;

       /**
        * Stores the monetary amount tbe creator of the content is willing to pay
        * per request of this campaign content.
        */
       @Getter
       private final Money pricePerRequest;

       /**
        * Stores the number of general target groups that the content has matched.
       *
        */
       @Getter
       private final int groupMatches;

       /**
        * Stores the total number of subsets within the broader target groups that
        * the content has matched.
        */
       @Getter
       private final int machtesInGroup;
   }
}
