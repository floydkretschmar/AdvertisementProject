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

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fkre
 */
@Entity(name = "T_REGULAR_PAYMENT")
public class RegularPayment extends Payment {
    
    /**
     * Stores the end date of the regular payment.
     */
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    
    /**
     * Stores the interval in which the payment will be made.
     */
    @NotNull
    @Column(name = "PAYMENT_INTERVAL")
    private PaymentInterval interval;
    
    
    /**
     * Stores the start date of the regular payment.
     */
    @NotNull
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    
    // --------------- Protected constructors ---------------
    
    
    /**
     * Creates a new instance of {@link Payment}.
     */
    protected RegularPayment() {
        super();
    }
    
    
    // --------------- Public getters and setters ---------------

    
    /**
     * Gets the end date of the regular payment.
     * 
     * @return the date when the regular payment will end.
     */
    public Date getEndDate() {
        return this.endDate;
    }

    
    /**
     * Gets the interval in which the payment will be made.
     * 
     * @return  the interval in which payments will be made as defined in
     *          {@link PaymentInterval}.
     */
    public PaymentInterval getInterval() {
        return this.interval;
    }
    

    /**
     * Gets the start date of the regular payment.
     * 
     * @return  the date when the regular payment starts.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    
    /**
     * Sets the end date of the regular payment.
     * 
     * @param endDate the date when the regular payment will end.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    /**
     * Sets the start date of the regular payment.
     * 
     * @param   interval  the date when the regular payment starts.
     */
    public void setInterval(PaymentInterval interval) {
        this.interval = interval;
    }
}
