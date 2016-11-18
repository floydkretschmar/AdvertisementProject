///*
// * Copyright (C) 2016 Floyd
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package de.oth.fkretschmar.advertisementproject.entities.interfaces;
//
//import de.oth.fkretschmar.advertisementproject.entities.IEntity;
//import de.oth.fkretschmar.advertisementproject.entities.PaymentCurrencyType;
//import de.oth.fkretschmar.advertisementproject.entities.PaymentCurrencyType;
//
///**
// *
// * @author Floyd
// */
//public interface IPayment extends IEntity<Long> {
//    
//    /**
//     * Gets the payment amount in the smallest possible unit of the currency.
//     * 
//     * @return  the amount.
//     */
//    public long getAmount();
//
//    
//    /**
//     * Gets the currency type of the payment amount.
//     * 
//     * @return  the currency of the payment.
//     */
//    public PaymentCurrencyType getCurrency();
//
//    
//    /**
//     * Gets the reason for the payment.
//     * 
//     * @return  the reason for which the payment was authorized.
//     */
//    public String getReason();
//
//    
//    /**
//     * Gets the account of the recipient of the payment.
//     * 
//     * @return  the account of the recipient.
//     */
//    public IAccount getRecipientAccount();
//    
//
//    /**
//     * Gets the account of the sender of the payment.
//     * 
//     * @return the account of the sender.
//     */
//    public IAccount getSenderAccount();
//    
//
//    /**
//     * Sets the payment amount in the smallest possible unit of the currency.
//     * 
//     * @param   amount  that will be set. 
//     */
//    public void setAmount(long amount);
//
//    
//    /**
//     * Sets the currency type of the payment amount.
//     * 
//     * @param   currency    of the payment. 
//     */
//    public void setCurrency(PaymentCurrencyType currency);
//
//    
//    /**
//     * Gets the reason for the payment.
//     * 
//     * @param   reason  for which the payment was authorized. 
//     */
//    public void setReason(String reason);
//
//    /**
//     * Sets the account of the recipient of the payment.
//     * 
//     * @param recipientAccount that will be set on the payment.
//     */
//    public void setRecipientAccount(IAccount recipientAccount);
//
//    
//    /**
//     * Sets the account of the sender of the payment.
//     * 
//     * @param senderAccount that will be set on the payment.
//     */
//    public void setSenderAccount(IAccount senderAccount);
//}
