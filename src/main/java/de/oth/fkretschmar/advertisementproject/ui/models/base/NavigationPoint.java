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
package de.oth.fkretschmar.advertisementproject.ui.models.base;

import lombok.AccessLevel;
import lombok.Getter;

/**
 *
 * @author fkre
 */
public enum NavigationPoint {
    
    // --------------- Enum fields ---------------
    
    /**
     * Defines the name of the login navigation point.
     */
    LOGIN("login.xhtml"),
    
    
    /**
     * Defines the name of the overview navigation point.
     */
    OVERVIEW("overview.xhtml"),
    
    
    /**
     * Defines the name of the registration navigation point.
     */
    REGISTER("register.xhtml"),
    
    
    /**
     * Indicates that the navigation point is undefined.
     */
    UNDEFINED("");
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the value that represents the actual name of the JSF page being
     * navigated to.
     */
    @Getter
    private String value;
    
    // --------------- Public constructors ---------------
    
    /**
     * Creates a new instance of {@link NavigationPoint} using the specified
     * value.
     * 
     * @param value     the value that represents the actual name of the JSF page being
     *                  navigated to.
     */
    private NavigationPoint(String value) {
        this.value = value;
    }
}
