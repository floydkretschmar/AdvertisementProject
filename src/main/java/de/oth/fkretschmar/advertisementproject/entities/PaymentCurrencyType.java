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
package de.oth.fkretschmar.advertisementproject.entities;

/**
 * Defines the currency types of a payment.
 * 
 * @author fkre
 */
public enum PaymentCurrencyType {
    
    // --------------- Enum fields ---------------
    
    /**
     * The currency type for euro.
     */
    EURO("EUR"),
    
    /**
     * The currency type for US dollar.
     */
    US_DOLLAR("USD"),
    
    /**
     * The currency type for yen.
     */
    YEN("YEN");
    
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the descriptor of the currency.
     */
    private String descriptor;
    
    
    // --------------- Private constructors ---------------
    
    
    /**
     * Creates a new instance of the currency type.
     * 
     * @param   descriptor  that describes the currency. 
     */
    private PaymentCurrencyType(String descriptor) {
        this.descriptor = descriptor;
    }
    
    
    // --------------- Public getters ---------------
    
    
    /**
     * Gets the descriptor of the currency.
     * 
     * @return  the descriptor.
     */
    public String getDescriptor() {
        return this.descriptor;
    }
}
