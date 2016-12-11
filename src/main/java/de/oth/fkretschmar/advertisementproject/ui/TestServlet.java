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
import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.TargetContextRepository;
import de.oth.fkretschmar.advertisementproject.business.services.ApplicationService;
import de.oth.fkretschmar.advertisementproject.business.services.BillService;
import de.oth.fkretschmar.advertisementproject.business.services.CampaignService;
import de.oth.fkretschmar.advertisementproject.business.services.ContentService;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordService;
import de.oth.fkretschmar.advertisementproject.business.services.UserService;
import de.oth.fkretschmar.advertisementproject.entities.user.Account;
import de.oth.fkretschmar.advertisementproject.entities.user.Address;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.entities.user.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.billing.BillItem;
import de.oth.fkretschmar.advertisementproject.entities.exceptions.BuilderValidationException;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.user.Password;
import de.oth.fkretschmar.advertisementproject.entities.campaign.PaymentInterval;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetAge;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetGender;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetMaritalStatus;
import de.oth.fkretschmar.advertisementproject.entities.campaign.TargetPurposeOfUse;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.EnumSet;
import java.util.Optional;
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
    private ContentService adService;
    
    @Inject
    private ContentRepository contentRepo;
    
    @Inject
    private CampaignService campaignService;
    
    @Inject
    private BillService billService;

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

                this.userService.createUser(user);

                // Authenticate User:
                
                this.authService.authenticateUser(user.geteMailAddress(), "Testpw".toCharArray());

                // Change password:
                
                ApplicationService.processCurrentUser(currentUser -> {
                    try {
                        return this.userService.changePassword(
                                currentUser,
                                "Testpw5".toCharArray());
                    } catch (PasswordException ex) {
                        Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                    }
                });
                
                // create and remove accounts for user:
                
                Account acc = BankAccount.createBankAccount()
                        .iban("DE948309535956456")
                        .bic("GENOD43945").build();
                
                ApplicationService.processCurrentUser(currentUser -> {
                   return this.userService.createAccountForUser(currentUser, acc);
                });
                
                
                Account acc2 = BankAccount.createBankAccount()
                        .iban("DE55555555555555555555")
                        .bic("GENOD43945").build();

                ApplicationService.processCurrentUser(currentUser -> {
                    User tempUser = this.userService.createAccountForUser(currentUser, acc2);
                    return this.userService.deleteAccountFromUser(tempUser, acc2);
                });
                

                
                // Create campaign:
                
                TargetContext context = TargetContext.createTargetContext()
                        .targetAges(EnumSet.allOf(TargetAge.class))
                        .targetGenders(EnumSet.allOf(TargetGender.class))
                        .targetMaritalStatus(EnumSet.allOf(
                                TargetMaritalStatus.class))
                        .targetPurposeOfUses(EnumSet.allOf(TargetPurposeOfUse.class)).build();

                // Create contents and add/delete them to/from the user:
                File file = new File("E:\\Augen_einer_Katze.jpg");

                SerializableRenderedImage image = new SerializableRenderedImage(ImageIO.read(file));
                
                Content ad = Content.createContent()
                            .value(image)
                            .contentType(ContentType.IMAGE)
                            .targetUrl(new URL("https://www.google.de"))
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
                
                Content ad2 = Content.createContent()
                            .value(new URL("https://upload.wikimedia.org/wikipedia/commons/7/7b/Gr%C3%BCne_Augen_einer_Katze.JPG"))
                            .contentType(ContentType.IMAGE_URL)
                            .targetUrl(new URL("https://www.google.de"))
                            .context(context1)
                            .numberOfRequests(500000)
                            .pricePerRequest(Monetary.getDefaultAmountFactory()
                                    .setCurrency("EUR")
                                    .setNumber(0.10).create()).build();
                
//                Campaign campaign = ApplicationService.processCurrentUser(currentUser -> {
//                    try {
                Campaign campaign = Campaign.createCampaign()
                                .interval(PaymentInterval.MONTHLY)
                                .paymentAccount(acc)
                                .build();
//                    }
//                    catch (BuilderValidationException ex) {}
//                    return null;
//                });
                
                if (campaign != null) {
                    campaign.addContent(ad);
                    campaign.addContent(ad2);
                    
                    final Campaign tmpCamp = campaign;
                    
                    ApplicationService.processCurrentUser(currentUser -> {
                        return this.campaignService.createCampaignForUser(currentUser, tmpCamp);
                    });
                                        
                    campaign = tmpCamp;
                    
                    TargetContext context3 = TargetContext.createTargetContext()
                        .targetAges(EnumSet.of(TargetAge.SENIORS))
                        .targetGenders(EnumSet.of(TargetGender.MALE, TargetGender.OTHER))
                        .targetMaritalStatus(EnumSet.of(
                                TargetMaritalStatus.MARRIED,
                                TargetMaritalStatus.WIDOWED))
                        .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.PRIVATE)).build();
                    
                    Content ad3 = Content.createContent()
                                .value("Das ist mein Werbetext")
                                .contentType(ContentType.TEXT)
                                .targetUrl(new URL("https://www.google.de"))
                                .context(context3)
                                .numberOfRequests(200000)
                                .pricePerRequest(Monetary.getDefaultAmountFactory()
                                        .setCurrency("EUR")
                                        .setNumber(0.20).create()).build();
                    
                    campaign = this.adService.createContentForCampaign(campaign, ad3);
                }
                
                // create bill:
                
                BillItem bi1 = BillItem.createBillItem()
                        .content(ad)
                        .contentRequests(500)
                        .build();
                
                BillItem bi2 = BillItem.createBillItem()
                        .content(ad2)
                        .contentRequests(3000)
                        .build();
                
                BillItem[] bitems = { bi1, bi2 };
                
                Bill bill = Bill.createBill()
                        .items(bitems)
                        .build();
                
                campaign = this.billService.createBillForCampaign(campaign, bill);
                
                
                for(int i = 0; i < 50; i++) {
                    Optional<Content> bestContent = this.adService.requestContent(
                            "myself",
                            TargetContext.createTargetContext()
                                .targetAges(EnumSet.of(TargetAge.CHILDREN))
                                .targetGenders(EnumSet.of(TargetGender.FEMALE))
                                .targetMaritalStatus(EnumSet.of(TargetMaritalStatus.SINGLE))
                                .targetPurposeOfUses(EnumSet.of(TargetPurposeOfUse.PRIVATE)).build());
                    out.println(bestContent.get().getId());
                }
                out.println("<br>");
                for(int i = 0; i < 50; i++) {
                    Optional<Content> bestContent = this.adService.requestRandomContent("myself");
                    out.println(bestContent.get().getId());
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
