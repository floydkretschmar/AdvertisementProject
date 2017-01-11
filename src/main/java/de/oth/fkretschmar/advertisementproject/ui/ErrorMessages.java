/*
 * Copyright (C) 2017 Admin
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

/**
 *
 * @author Admin
 */
public class ErrorMessages {

    // --------------- Public static fields ---------------
    
    /**
     * Stores the error message for a failed authentication during login.
     */
    public static final String LOGIN_AUTHENTICATION_FAILED
            = "The given e-mail address and/or password are incorrect.";
    
    /**
     * Stores the error message for unexpected errors during login.
     */
    public static final String LOGIN_UNEXPECTED_ERROR 
            = "An unexpected error occurred. Please try again.";
    
    /**
     * Stores the error message for the error that occurres when no payment 
     * account has been specified for a new campaign.
     */
    public static final String NEW_CAMPAIGN_ACCOUNT_NEEDED 
            = "A payment account has to be created and chosen for the campaign.";
    
    /**
     * Stores the error message for the error that occurres when no payment 
     * account has been specified for a new campaign.
     */
    public static final String NEW_CAMPAIGN_CONTENT_NEEDED 
            = "At least one content has to be created for a new campaign.";
    
    /**
     * Stores the error message for a failed authentication during login.
     */
    public static final String REGISTRATION_EMAIL_ALREADY_IN_USE
            = "The provided e-mail address is already in use.";
    
}
