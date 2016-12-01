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


import de.oth.fkretschmar.advertisementproject.entities.base.LocalDateAttributeConverter;
import java.time.LocalDate;
import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author fkre
 */
@Entity(name = "T_REGULAR_PAYMENT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true)
public class RegularPaymentStatement extends PaymentStatement {
    
    /**
     * Stores the end date of the regular payment.
     */
    @Column(name = "END_DATE")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate endDate;
    
    
    /**
     * Stores the interval in which the payment will be made.
     */
    @NotNull
    @Column(name = "PAYMENT_INTERVAL")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private PaymentInterval interval;
    
    
    /**
     * Stores the start date of the regular payment.
     */
    @NotNull
    @Column(name = "START_DATE")
    @Getter(AccessLevel.PUBLIC)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate startDate;
    
    
    // --------------- Private constructors ---------------
    
    
    /**
     * Creates a new instance of {@link RegularPaymentStatement} using the 
     * specified start date, end date and payment interval.
     * 
     * @param   moneyAmount         the monetary value of the payment that 
     *                              consists of the amount and the currency type.
     * @param   reason              the reason for the payment.
     * @param   recipientAccount    the account of the recipient of the payment.
     * @param   senderAccount       the account of the sender of the payment.
     * @param   endDate             the end date of the regular payment.
     * @param   startDate           the start date of the regular payment.
     * @param   interval            the interval in which the payment will be 
     *                              made.
     */
    private RegularPaymentStatement(
            Money moneyAmount, 
            String reason, 
            Account recipientAccount, 
            Account senderAccount,
            LocalDate startDate, 
            LocalDate endDate, 
            PaymentInterval interval) {
        super(moneyAmount, reason, recipientAccount, senderAccount);
        this.endDate = endDate;
        this.interval = interval;
        this.startDate = startDate;
    }
    
    
    // --------------- Public static methods ---------------
    
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link RegularPaymentStatement}.
     *     
     * @param   monetaryAmount         the monetary value of the payment that 
     *                              consists of the amount and the currency type.
     * @param   reason              the reason for the payment.
     * @param   recipientAccount    the account of the recipient of the payment.
     * @param   senderAccount       the account of the sender of the payment.
     * @param   endDate             the end date of the regular payment.
     * @param   startDate           the start date of the regular payment.
     * @param   interval            the interval in which the payment will be 
     *                              made.
     * @return  the built {@link RegularPaymentStatement}.
     */
    @Builder(
            builderMethodName = "createRegularPaymentStatement", 
            builderClassName = "RegularPaymentStatementBuilder",
            buildMethodName = "build")
    private static RegularPaymentStatement validateAndCreateRegularPaymentStatement(
            MonetaryAmount monetaryAmount, 
            String reason, 
            Account recipientAccount, 
            Account senderAccount,
            LocalDate startDate, 
            LocalDate endDate, 
            PaymentInterval interval) {
        Money moneyAmount = PaymentStatement.validateStatementInputData(
                monetaryAmount, 
                reason, 
                recipientAccount, 
                senderAccount);
        
        if(startDate == null)
            throw new BuilderValidationException(
                    RegularPaymentStatement.class,
                    "The start date can not be null.");
        
        if(startDate.isBefore(LocalDate.now()))
            throw new BuilderValidationException(
                    RegularPaymentStatement.class,
                    "The start date can not be earlier than the current day.");
            
        if(endDate == null) 
            throw new BuilderValidationException(
                    RegularPaymentStatement.class,
                    "The end date can not be null");
            
        if(endDate.isBefore(LocalDate.now()))
            throw new BuilderValidationException(
                    RegularPaymentStatement.class,
                    "The end date can not be earlier than the current day.");
        
        if(interval == PaymentInterval.UNDEFINED) {
            throw new BuilderValidationException(
                    RegularPaymentStatement.class,
                    "The payment interval of the regular payment has to be "
                            + "defined.");
        }
        
        return new RegularPaymentStatement(
                moneyAmount, 
                reason, 
                recipientAccount, 
                senderAccount,
                startDate, 
                endDate, 
                interval);
    }

}
