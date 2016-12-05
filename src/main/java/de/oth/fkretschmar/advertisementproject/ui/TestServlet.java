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
package de.oth.fkretschmar.advertisementproject.ui;

import de.oth.fkretschmar.advertisementproject.business.SerializableRenderedImage;
import de.oth.fkretschmar.advertisementproject.business.repositories.TargetContextRepository;
import de.oth.fkretschmar.advertisementproject.business.services.ApplicationService;
import de.oth.fkretschmar.advertisementproject.business.services.CampaignService;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordService;
import de.oth.fkretschmar.advertisementproject.business.services.UserService;
import de.oth.fkretschmar.advertisementproject.entities.Account;

import de.oth.fkretschmar.advertisementproject.entities.Address;
import de.oth.fkretschmar.advertisementproject.entities.Content;
import de.oth.fkretschmar.advertisementproject.entities.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.BuilderValidationException;
import de.oth.fkretschmar.advertisementproject.entities.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.CampaignContent;
import de.oth.fkretschmar.advertisementproject.entities.Password;
import de.oth.fkretschmar.advertisementproject.entities.PaymentInterval;
import de.oth.fkretschmar.advertisementproject.entities.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.TargetPurposeOfUse;
import de.oth.fkretschmar.advertisementproject.entities.User;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.money.Monetary;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

/**
 *
 * @author fkre Floyd Kretschmar
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/Test"})
public class TestServlet extends HttpServlet {

    @Inject
    private UserService userService;
    
    @Inject
    private ApplicationService authService;
    
    @Inject
    private PasswordService passService;
    
//    @Inject
//    private ContentService adService;
    
    @Inject
    private CampaignService campaignService;
    
    @Inject
    private TargetContextRepository targetRepo;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Transactional
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");
            
            try {
                // Create user including address and password (register screen):
                
                Address address = Address.createAddress()
                                    .areaCode("95689")
                                    .city("Regensburg")
                                    .country("Deutschland")
                                    .street("Dechbettener Straße 7").build();

                User user = User.createUser()
                        .eMailAddress("fkretschmar@googlemail.com")
                        .password(PasswordService.generate("Testpw".toCharArray()))
                        .firstName("Floyd") 
                        .lastName("Kretschmar") 
                        .address(address)
                        .company("OptWare").build();

                this.userService.create(user);

                // Authenticate User:
                
                this.authService.authenticateUser(user.geteMailAddress(), "Testpw".toCharArray());

                // Change password:
                
                ApplicationService.processCurrentUser(currentUser -> {
                        try {
                            this.userService.changePassword(
                                    currentUser,
                                    "Testpw5".toCharArray());
                        }
                        catch (PasswordException ex) {}
                    });
                
                // create and remove accounts for user:
                
                Account acc = BankAccount.createBankAccount()
                        .iban("DE948309535956456")
                        .bic("GENOD43945").build();
                
                ApplicationService.processCurrentUser(currentUser -> {
                    this.userService.addAccountToUser(currentUser, acc);
                });
                
                
                Account acc2 = BankAccount.createBankAccount()
                        .iban("DE55555555555555555555")
                        .bic("GENOD43945").build();

                ApplicationService.processCurrentUser(currentUser -> {
                    this.userService.addAccountToUser(user, acc2);
                    this.userService.removeAccountFromUser(user, acc2);
                });
                

                // Create contents and add/delete them to/from the user:
                File file = new File("E:\\Augen_einer_Katze.jpg");

                SerializableRenderedImage image = new SerializableRenderedImage(ImageIO.read(file));

                Content ad = Content.createContent()
                            .value(image)
                            .contentType(ContentType.IMAGE)
                            .targetUrl(new URL("https://www.google.de")).build();
                ApplicationService.processCurrentUser(currentUser -> {
                    this.userService.addContentToUser(currentUser, ad);
                });

                Content ad2 = Content.createContent()
                            .value(new URL("https://upload.wikimedia.org/wikipedia/commons/7/7b/Gr%C3%BCne_Augen_einer_Katze.JPG"))
                            .contentType(ContentType.IMAGE_URL)
                            .targetUrl(new URL("https://www.google.de")).build();
                
                ApplicationService.processCurrentUser(currentUser -> {
                    this.userService.addContentToUser(currentUser, ad2);
                });

                Content ad3 = Content.createContent()
                            .value("Das ist mein Werbetext")
                            .contentType(ContentType.TEXT)
                            .targetUrl(new URL("https://www.google.de")).build();
                
                ApplicationService.processCurrentUser(currentUser -> {
                    this.userService.addContentToUser(currentUser, ad3);
                    this.userService.removeContentFromUser(currentUser, ad3);
                });

                
                // Create campaign:
                
                TargetContext context = TargetContext.createTargetContext()
                        .targetAges(EnumSet.of(TargetAge.IRRELEVANT))
                        .targetGenders(EnumSet.of(TargetGender.IRRELEVANT))
                        .targetMaritalStatus(EnumSet.of(
                                TargetMaritalStatus.IRRELEVANT))
                        .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.IRRELEVANT)).build();
                
                CampaignContent ccon1 = CampaignContent.createCampaignContent()
                            .content(ad)
                            .context(context)
                            .numberOfRequests(100000)
                            .pricePerRequest(Monetary.getDefaultAmountFactory()
                                    .setCurrency("EUR")
                                    .setNumber(0.04).create()).build();

                TargetContext context1 = TargetContext.createTargetContext()
                        .targetAges(EnumSet.of(TargetAge.CHILDREN, TargetAge.YOUTH, TargetAge.ADULTS))
                        .targetGenders(EnumSet.of(TargetGender.FEMALE, TargetGender.MALE, TargetGender.OTHER))
                        .targetMaritalStatus(EnumSet.of(
                                TargetMaritalStatus.SINGLE))
                        .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.BUSINESS)).build();
                
                CampaignContent ccon2 = CampaignContent.createCampaignContent()
                            .content(ad2)
                            .context(context1)
                            .numberOfRequests(500000)
                            .pricePerRequest(Monetary.getDefaultAmountFactory()
                                    .setCurrency("EUR")
                                    .setNumber(0.10).create()).build();
                
                Campaign campaign = ApplicationService.processCurrentUser(currentUser -> {
                    try {
                        return Campaign.createCampaign()
                                .comissioner(user)
                                .interval(PaymentInterval.MONTHLY)
                                .paymentAccount(acc)
                                .build();
                    }
                    catch (BuilderValidationException ex) {}
                    return null;
                });
                
                if (campaign != null) {
                    campaign.addContent(ccon1);
                    campaign.addContent(ccon2);
                    
                    this.campaignService.create(campaign);
                }
            }
            catch (PasswordException ex) {}
            catch (BuilderValidationException ex) {}
            
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
