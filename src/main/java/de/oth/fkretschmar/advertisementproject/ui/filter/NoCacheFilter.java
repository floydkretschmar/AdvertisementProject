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
package de.oth.fkretschmar.advertisementproject.ui.filter;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The filter used to disallow caching of websites for all JSF pages.
 *
 * Implementation as seen on http://stackoverflow.com/questions/10305718/avoid-
 * back-button-on-jsf-web-application
 * 
 * @author fkre
 */
@WebFilter(servletNames={"facesServlet"})
public class NoCacheFilter implements Filter {

    /**
     * Filters all pages and disables the caching.
     * 
     * @param   request
     * @param   response
     * @param   chain
     * @throws  IOException
     * @throws  ServletException 
     */
    @Override
    public void doFilter(
            ServletRequest request, 
            ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest tempRequest = (HttpServletRequest) request;
        HttpServletResponse tempResponse = (HttpServletResponse) response;

        // Skip JSF resources (CSS/JS/Images/etc)
        if (!tempRequest.getRequestURI().startsWith(
                tempRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { 
            tempResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            tempResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            tempResponse.setDateHeader("Expires", 0); // Proxies.
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }
}