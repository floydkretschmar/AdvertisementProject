///*
// * Copyright (C) 2016 fkre
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package de.oth.fkretschmar.advertisementproject.entities;
//
//import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
//import de.oth.fkretschmar.advertisementproject.entities.base.MonetaryAmountAttributeConverter;
//import javax.money.MonetaryAmount;
//import javax.persistence.Column;
//import javax.persistence.Convert;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//import javax.validation.constraints.NotNull;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
///**
// *
// * @author fkre
// */
//@Entity(name = "T_CAMPAIGN_CONTENT")
//@NoArgsConstructor(access = AccessLevel.PACKAGE)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@ToString(callSuper = true)
//public class CampaignContent extends AbstractAutoGenerateKeyedEntity {
//    
//    // --------------- Private fields ---------------
//    
//    /** 
//     * Stores the content that is being ordered for the campaign.
//     */
//    @ManyToOne
//    @Getter
//    @Setter
//    @JoinColumn(name = "CONTENT_ID")
//    private Content content;
//    
//    /**
//     * Stores the target context of the specified content that influinces the
//     * price.
//     */
//    @NotNull
//    @OneToOne
//    @Getter
//    @Setter
//    @JoinColumn(name = "CONTEXT_ID")
//    private TargetContext context;
//    
//    /**
//     * Stores the number of contents that have been ordered for the target 
//     * context.
//     */
//    @NotNull
//    @Column(name = "NUMER_OF_REQUESTS", nullable = false)
//    @Getter
//    private long numberOfRequests;
//    
//    /**
//     * Stores the monetary amount tbe creator of the content is willing to pay
//     * per request of this campaign content.
//     **/
//    @NotNull
//    @Column(name = "ITEM_PRICE", nullable = false)
//    @Getter
//    @Setter
//    @Convert(converter = MonetaryAmountAttributeConverter.class)
//    private MonetaryAmount pricePerRequest;
//    
//    
//    // --------------- Private static methods ---------------
//    
//    
//    /**
//     * The method that builds the basis of the auto generated builder:
//     * Validates the input and creates the corresponding {@link CampaignContent}.
//     * 
//     * @param   content         the content that will be wrapped.
//     * @param   numberOfRequests    the number of contents that have been 
//     *                              ordered for the target context.
//     * @param   context         the target context of the specified order row 
//     *                          that influinces the price.
//     * @param   pricePerRequest the monetary amount tbe creator of the value 
//                          is willing to pay per request of this campaign 
//                          value.
//     * @return  the built {@link CampaignContent}.
//     * @throws  BuilderValidationException  that indicates that one or more of 
//     *                                      of the given creation parameters are
//     *                                      invalid.
//     */
//    @Builder(
//            builderMethodName = "createCampaignContent", 
//            builderClassName = "CampaignContentBuilder",
//            buildMethodName = "build")
//    private static CampaignContent validateAndCreateCampaignContent(
//            Content content,
//            TargetContext context,
//            long numberOfRequests,
//            MonetaryAmount pricePerRequest) throws BuilderValidationException {
//        
//        if (numberOfRequests <= 0) {
//            throw new BuilderValidationException(
//                    CampaignContent.class,
//                    "The amount can not be smaller or equal to 0.");
//        }
//        
//        if (context == null) {
//            throw new BuilderValidationException(
//                    CampaignContent.class,
//                    "The target context can not be null.");
//        }
//        
//        if (pricePerRequest == null || pricePerRequest.isNegative()) {
//            throw new BuilderValidationException(
//                    CampaignContent.class,
//                    "The price per request can not be null or negative.");
//        }
//        
//        return new CampaignContent(
//                content, 
//                context, 
//                numberOfRequests, 
//                pricePerRequest);
//    }
//}
