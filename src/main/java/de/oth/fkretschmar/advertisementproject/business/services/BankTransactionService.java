/*
 * Copyright (C) 2016 Admin
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

import de.jreichl.service.web.TransactionFailedException_Exception;
import de.jreichl.service.web.TransactionWSService;
import de.oth.fkretschmar.advertisementproject.business.annotation.BankTransaction;
import de.oth.fkretschmar.advertisementproject.business.services.base.ITransactionService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceRef;
import org.joda.money.Money;

/**
 *
 * @author Admin
 */
@BankTransaction
@RequestScoped
public class BankTransactionService implements ITransactionService {

    /**
     * Stores the transaction service used to execute the transaction.
     */
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/BankReichl/TransactionWS.wsdl")
    private TransactionWSService service;

    /**
     * Transfers the specified amount from the sender to the recipient using the
     * specified transaction reason.
     *
     * @param   amount      the amount that will be transfered.
     * @param   sender      the sender of the transaction.
     * @param   recipient   the recipient of the transaction.
     * @param   description the description explaining the reason for the
     * transaction.
     * @throws TransactionFailedException   that indicates that the transaction
     *                                      has not been successfull.
     */
    @Override
    public void transfer(
            Money amount,
            Account sender,
            Account recipient,
            String description) throws TransactionFailedException {
        if (sender instanceof BankAccount && recipient instanceof BankAccount) {
            try {
                    de.jreichl.service.web.ITransactionWS port = service.getTransactionWSPort();
                    // TODO process result here
                    boolean result = port.transfer(
                            amount.getAmountMinorLong(), 
                            ((BankAccount)sender).getIban(), 
                            ((BankAccount)recipient).getIban(), 
                            description);
                    System.out.println("Result = "+result);

            } catch (TransactionFailedException_Exception ex) {
                // TODO: find better way of determining the failure reason
                if (ex.getFaultInfo().getMessage().contains("Nicht genügend Geld")) {
                    throw new TransactionFailedException(
                            ex.getFaultInfo().getMessage(), 
                            TransactionFailureReason.SENDER_OUT_OF_MONEY);
                }
                else if (ex.getFaultInfo().getMessage().contains(
                        String.format(
                                "%s is not a valid IBAN", 
                                ((BankAccount)sender).getIban()))) {
                    throw new TransactionFailedException(
                            ex.getFaultInfo().getMessage(), 
                            TransactionFailureReason.SENDER_NOT_VALID);
                }
                else if (ex.getFaultInfo().getMessage().contains(
                        String.format(
                                "%s is not a valid IBAN", 
                                ((BankAccount)recipient).getIban()))) {
                    throw new TransactionFailedException(
                            ex.getFaultInfo().getMessage(), 
                            TransactionFailureReason.RECIPIENT_NOT_VALID);
                }
            }
        }

    }

}
