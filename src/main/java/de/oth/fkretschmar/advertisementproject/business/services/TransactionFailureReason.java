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

/**
 *
 * @author Admin
 */
public enum TransactionFailureReason {
    
    /**
     * Indicates that the transaction failed because the recipient account is
     * not a valid account.
     */
    RECIPIENT_NOT_VALID,
    
    /**
     * Indicates that the transaction failed because the sender account is
     * not a valid account.
     */
    SENDER_NOT_VALID,
    
    /**
     * Indicates that the transaction failed because the sender account is
     * out of money.
     */
    SENDER_OUT_OF_MONEY;
}
