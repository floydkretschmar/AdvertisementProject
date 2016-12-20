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
package de.oth.fkretschmar.advertisementproject.entities.campaign;

import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author fkre
 */
@Entity(name = "T_CAMPAIGN")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Campaign extends AbstractAutoGenerateKeyedEntity {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores all the bills that have been payed so far during the campaign.
     */
    @NotNull
    @OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
    private final Collection<Bill> bills
            = new ArrayList<Bill>();
    
    /**
     * Stores the state of the campaign.
     */
    @NotNull
    @Column(name = "CAMPAIGN_STATE")
    @Getter
    @Setter
    private CampaignState campaignState = CampaignState.RUNNING;
    
    /**
     * Stores the user hat has comissioned the campaign.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "COMISSIONER_ID")
    @Getter
    @Setter
    private User comissioner;
    
    /**
     * Stores the contents that make up the campaign.
     */
    @NotNull
    @OneToMany(mappedBy = "campaign")
    private final Collection<Content> contents
            = new ArrayList<Content>();
    
    /**
     * Stores the interval in which the order is being paid for.
     */
    @NotNull
    @Column(name = "PAYMENT_INTERVAL", updatable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private PaymentInterval interval;
    
    /**
     * Stores the account used to make the payments for this campaign.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_ACCOUNT_ID")
    @Getter
    @Setter
    private Account paymentAccount;
    
    // --------------- Private constructors ---------------
    
    
    /**
     * Crreates a new {@link Campaign} using the specified comissioner, account
     * and payment interval.
     * 
     * @param acount        the account used to make the payments for this 
     *                      campaign.
     * @param interval      the interval in which the order is being paid for.
     */
    private Campaign(Account paymentAccount, PaymentInterval interval) {
        super();
        this.paymentAccount = paymentAccount;
        this.interval = interval;
    }
    
    // --------------- Public getters and setters ---------------
    
    
    /**
     * Gets all the bills that have been payed so far during the campaign.
     * 
     * @return the bills as an unmodifieable list.
     */
    public Collection<Bill> getBills() {
        return Collections.unmodifiableCollection(this.bills);
    }
    
    
    /**
     * Gets the contents that make up the campaign.
     * 
     * @return the contents as an unmodifieable list.
     */
    public Collection<Content> getContents() {
        return Collections.unmodifiableCollection(this.contents);
    }
    
    // --------------- Public methods ---------------
    
    
    /**
     * Adds a bill to the list of payed bill.
     * 
     * @param bill  that will be added.
     * @return      {@code true} if the bill was added, otherwise {@code false}
     */
    public boolean addBill(Bill bill) {
        return this.bills.add(bill);
    }
    
    /**
     * Adds a content to the list of contents.
     * 
     * @param content  that will be added.
     * @return          {@code true} if the content was added, otherwise 
     *                  {@code false}
     */
    public boolean addContent(Content content) {
        return this.contents.add(content);
    }
    
    /**
     * Removes a content to the list of contents.
     * 
     * @param content  that will be removed.
     * @return          {@code true} if the content was removed, otherwise 
     *                  {@code false}
     */
    public boolean removeContent(Content content) {
        return this.contents.remove(content);
    }
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Campaign}.
     * 
     * @param   campaign 
     * @return  the built {@link Campaign}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createCampaign", 
            builderClassName = "CampaignBuilder",
            buildMethodName = "build")
    private static Campaign validateAndCreateCampaign(
            Account paymentAccount, 
            PaymentInterval interval) throws BuilderValidationException {
        
        if (paymentAccount == null) {
            throw new BuilderValidationException(
                    Campaign.class,
                    "The payment account can not be null.");
        }
        
        if (interval == PaymentInterval.UNDEFINED) {
            throw new BuilderValidationException(
                    Campaign.class,
                    "The payment interval has to be defined.");
        }
        
        return new Campaign(paymentAccount, interval);
    }
}
