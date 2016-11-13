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

import de.oth.fkretschmar.advertisementproject.business.HashingException;
import de.oth.fkretschmar.advertisementproject.business.service.ApplicationService;
import de.oth.fkretschmar.advertisementproject.business.service.PasswordService;
import de.oth.fkretschmar.advertisementproject.business.service.UserServiceException;
import de.oth.fkretschmar.advertisementproject.business.service.UserService;
import de.oth.fkretschmar.advertisementproject.entity.Account;
import de.oth.fkretschmar.advertisementproject.entity.Address;
import de.oth.fkretschmar.advertisementproject.entity.BankAccount;
import de.oth.fkretschmar.advertisementproject.entity.Password;
import de.oth.fkretschmar.advertisementproject.entity.PayPalAccount;
import de.oth.fkretschmar.advertisementproject.entity.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            //User user = this.userService.findForEMail("fkretschmar@googlemail.com");
//            this.userService.delete(user);
            
            Address address = new Address(
                    "95689", 
                    "Regensburg", 
                    "Dechbettener Straße 7");
            address.setCountry("Deutschland");
            
            //Password pw = userService.create("Testpassword".toCharArray());
            User user = new User(
                    "fkretschmar@googlemail.com", 
                    passService.create("Testpw".toCharArray()), 
                    "Floyd", 
                    "Kretschmar", 
                    address);
            user.setCompany("Optware");
            
            BankAccount acc = new BankAccount("DE948309535956456", "GENOD43945");
            acc.setDescription("RB");
            
            user.addAccount(acc);
            
            acc = new BankAccount("DE555555555555555", "GENOD43945");
            acc.setDescription("RB2");
            
            user.addAccount(acc);
            this.userService.save(user);
            
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
