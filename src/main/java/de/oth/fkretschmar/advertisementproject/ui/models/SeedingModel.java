/*
 * Copyright (C) 2017 fkre
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
package de.oth.fkretschmar.advertisementproject.ui.models;

import de.oth.fkretschmar.advertisementproject.business.services.PasswordService;
import de.oth.fkretschmar.advertisementproject.business.services.UserServiceException;
import de.oth.fkretschmar.advertisementproject.business.services.base.IAccountService;
import de.oth.fkretschmar.advertisementproject.business.services.base.ICampaignService;
import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentFormat;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.campaign.PaymentInterval;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;
import de.oth.fkretschmar.advertisementproject.entities.user.Address;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 *
 * @author fkre
 */
@Named
@RequestScoped
public class SeedingModel {

    // --------------- Private fields ---------------
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private IAccountService accountService;

    /**
     * Stores the repository used to manage {@link Campaign} entities.
     */
    @Inject
    private ICampaignService campaignService;

    /**
     * Stores the repository used to manage {@link User} entities.
     */
    @Inject
    private IUserService userService;

    // --------------- Public methods ---------------
    /**
     * Initializes the database of the application with valid data.
     *
     * @return
     */
    public String seed() {
        try {
            // Create Andrea:
            this.createAndrea();
        } catch (UserServiceException | MalformedURLException ex) {
            Logger.getLogger(SeedingModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return "login";
        }
    }
    
    /**
     * Creates the user Andrea Meier.
     * @throws MalformedURLException
     * @throws UserServiceException 
     */
    private void createAndrea() throws MalformedURLException, UserServiceException {
        // Create Address
        Address andreaAddress = Address.createAddress()
                .areaCode("93047")
                .city("Regensburg")
                .country("Deutschland")
                .houseNumber("3")
                .street("Kapuzinergasse")
                .build();

        // Create user
        User andrea = User.createUser()
                .address(andreaAddress)
                .birthdate(LocalDate.of(1986, Month.MAY, 16))
                .company("McDonalds Regensburg")
                .eMailAddress("andrea.meier@mcdonalds.de")
                .firstName("Andrea")
                .lastName("Meier")
                .password(PasswordService.generate("andreapw".toCharArray()))
                .build();

        this.userService.createUser(andrea);

        // Create accounts and add them to user
        Account andreaBankAccount = BankAccount.createBankAccount()
                .bic("BYLADEM1DQE")
                .iban("DE23772300000000000061")
                .build();
        Account andreaPayPalAccount = PayPalAccount.createPayPalAccount()
                .name("andrea.meier@mcdonalds.de")
                .build();

        andrea = this.accountService.createAccountForUser(andrea, andreaBankAccount);
        andrea = this.accountService.createAccountForUser(andrea, andreaPayPalAccount);

        // Create Campaign
        Campaign mcDonaldsCampaign = Campaign.createCampaign()
                .interval(PaymentInterval.MONTHLY)
                .name("Mc Donalds - Regensburg Kampagne")
                .paymentAccount(andreaBankAccount)
                .build();

        Content mcDContent1 = Content.createContent()
                .name("McNuggets Skyscraper")
                .contentType(ContentType.IMAGE_URL)
                .context(TargetContext.createTargetContext()
                        .targetAges(EnumSet.of(TargetAge.CHILDREN, TargetAge.YOUTH))
                        .targetGenders(EnumSet.of(TargetGender.MALE))
                        .targetMaritalStatus(EnumSet.allOf(TargetMaritalStatus.class))
                        .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.PRIVATE))
                        .build())
                .description("A wide skyscraper ad for chicken mc nuggets.")
                .format(ContentFormat.WIDE_SKYSCRAPER)
                .numberOfRequests(50000)
                .pricePerRequest(Money.ofMinor(CurrencyUnit.EUR, 25))
                .targetUrl(new URL("http://www.mcdonalds.de/"))
                .value(new URL("https://infiniteingenuity.files.wordpress.com/2015/03/skyscraper.jpg"))
                .build();

        Content mcDContent2 = Content.createContent()
                .name("Oatmeal Skyscraper")
                .contentType(ContentType.IMAGE_URL)
                .context(TargetContext.createTargetContext()
                        .targetAges(EnumSet.of(TargetAge.CHILDREN, TargetAge.YOUTH))
                        .targetGenders(EnumSet.of(TargetGender.MALE))
                        .targetMaritalStatus(EnumSet.allOf(TargetMaritalStatus.class))
                        .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.PRIVATE))
                        .build())
                .description("A wide skyscraper ad for oat meal.")
                .format(ContentFormat.WIDE_SKYSCRAPER)
                .numberOfRequests(40000)
                .pricePerRequest(Money.ofMinor(CurrencyUnit.EUR, 20))
                .targetUrl(new URL("http://www.mcdonalds.de/"))
                .value(new URL("http://www.pghcitypaper.com/infopages/mcdonalds160x600example.jpg"))
                .build();

        Content mcDContent3 = Content.createContent()
                .name("McNuggets leaderboard")
                .contentType(ContentType.IMAGE_URL)
                .context(TargetContext.createTargetContext()
                        .targetAges(EnumSet.of(TargetAge.CHILDREN, TargetAge.YOUTH))
                        .targetGenders(EnumSet.of(TargetGender.MALE))
                        .targetMaritalStatus(EnumSet.allOf(TargetMaritalStatus.class))
                        .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.PRIVATE))
                        .build())
                .description("A leaderboard ad for chicken mc nuggets.")
                .format(ContentFormat.LEADERBOARD)
                .numberOfRequests(40000)
                .pricePerRequest(Money.ofMinor(CurrencyUnit.EUR, 20))
                .targetUrl(new URL("http://www.mcdonalds.de/"))
                .value(new URL("https://infiniteingenuity.files.wordpress.com/2015/03/leaderboard.jpg"))
                .build();

        Content mcDContent4 = Content.createContent()
                .name("McNuggets rectangle")
                .contentType(ContentType.IMAGE_URL)
                .context(TargetContext.createTargetContext()
                        .targetAges(EnumSet.of(TargetAge.CHILDREN, TargetAge.YOUTH))
                        .targetGenders(EnumSet.of(TargetGender.MALE))
                        .targetMaritalStatus(EnumSet.allOf(TargetMaritalStatus.class))
                        .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.PRIVATE))
                        .build())
                .description("A medium rectangle ad for chicken mc nuggets.")
                .format(ContentFormat.MEDIUM_RECTANGLE)
                .numberOfRequests(40000)
                .pricePerRequest(Money.ofMinor(CurrencyUnit.EUR, 20))
                .targetUrl(new URL("http://www.mcdonalds.de/"))
                .value(new URL("https://infiniteingenuity.files.wordpress.com/2015/03/rectangle.jpg"))
                .build();

        mcDonaldsCampaign.addContent(mcDContent1);
        mcDonaldsCampaign.addContent(mcDContent2);
        mcDonaldsCampaign.addContent(mcDContent3);
        mcDonaldsCampaign.addContent(mcDContent4);

        this.campaignService.createCampaignForUser(andrea, mcDonaldsCampaign);
    }
}
