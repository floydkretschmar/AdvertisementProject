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
package de.oth.fkretschmar.advertisementproject.business.services;

import lombok.Getter;

/**
 *
 * @author Admin
 */
public class TransactionFailedException extends Exception {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the reason indicating why the transaction failed.
     */
    @Getter
    private final TransactionFailureReason reason;

    // --------------- Public constructors ---------------
    
    /**
     * Constructs an instance of <code>TransactionFailedException</code> with
     * the specified detail message and reason.
     *
     * @param msg       the detail message.
     * @param reason    the reason the exception occurred.
     */
    public TransactionFailedException(String msg, TransactionFailureReason reason) {
        super(msg);
        this.reason = reason;
    }
}
