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
import de.oth.fkretschmar.advertisementproject.business.services.AdvertisementService;
import de.oth.fkretschmar.advertisementproject.business.services.ApplicationService;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordService;
import de.oth.fkretschmar.advertisementproject.business.services.UserService;
import de.oth.fkretschmar.advertisementproject.entities.Account;

import de.oth.fkretschmar.advertisementproject.entities.Address;
import de.oth.fkretschmar.advertisementproject.entities.Advertisement;
import de.oth.fkretschmar.advertisementproject.entities.AdvertisementContentType;
import de.oth.fkretschmar.advertisementproject.entities.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.Password;
import de.oth.fkretschmar.advertisementproject.entities.TargetContext;
import de.oth.fkretschmar.advertisementproject.entities.User;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
    @Inject
    private AdvertisementService adService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            
            File file = new File("E:\\Augen_einer_Katze.jpg");
            
            SerializableRenderedImage image = new SerializableRenderedImage(ImageIO.read(file));
            
            Advertisement ad = Advertisement.createAdvertisement()
                        .content(image)
                        .contentType(AdvertisementContentType.IMAGE)
                        .targetUrl(new URL("https://www.google.de")).build();
            
            ad = this.adService.create(ad);
            
            Advertisement ad2 = this.adService.find(ad.getId());
            
            ad = Advertisement.createAdvertisement()
                        .content(new URL("https://upload.wikimedia.org/wikipedia/commons/7/7b/Gr%C3%BCne_Augen_einer_Katze.JPG"))
                        .contentType(AdvertisementContentType.IMAGE_URL)
                        .targetUrl(new URL("https://www.google.de")).build();
            
            ad = this.adService.create(ad);
            
            ad2 = this.adService.find(ad.getId());

//            User user = this.userService.findForEMail("fkretschmar@googlemail.com");
//            
//            this.userService.changePassword(user, "HalloWelt".toCharArray());
            
            //this.userService.delete(user);
            
//            
//            Address address = Address.createAddress()
//                                .areaCode("95689")
//                                .city("Regensburg")
//                                .country("Deutschland")
//                                .street("Dechbettener Straße 7").build();
//            
//            User user = User.createUser()
//                    .eMailAddress("fkretschmar@googlemail.com")
//                    .password(PasswordService.generate("Testpw".toCharArray()))
//                    .firstName("Floyd") 
//                    .lastName("Kretschmar") 
//                    .address(address)
//                    .company("OptWare").build();
//            
//            Account acc = BankAccount.createBankAccount()
//                    .iban("DE948309535956456")
//                    .bic("GENOD43945").build();
//            
//            
//            user.addAccount(acc);
//            
//            acc = BankAccount.createBankAccount()
//                    .iban("DE55555555555555555555")
//                    .bic("GENOD43945").build();
//            
//            user.addAccount(acc);
//            this.userService.create(user);
            
//            
//            authService.authenticateUser(user.geteMailAddress(), "Testpw4".toCharArray());
//            
//            try {
//                int test = ApplicationService.processCurrentUser(currentUser -> {
//                    this.userService.changePassword(
//                            currentUser,
//                            "Testpw5".toCharArray());
//                    return 17;
//                });
//                out.println("Changesuccess");
//            } catch (UserServiceException ex) {
//                out.println(ex.getMessage());
//                Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            
//            try {
//                out.println(userService.create(user, user.getPassword(), user.getAddress()));
//            } catch (UserServiceException ex) {
//                Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            //out.println("Password-ID: " + pw.getId());

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
