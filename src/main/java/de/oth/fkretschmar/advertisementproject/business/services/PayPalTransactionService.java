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

import de.oth.fkretschmar.advertisementproject.business.services.web.TransactionFailedException;
import de.oth.fkretschmar.advertisementproject.business.annotation.PayPalTransaction;
import de.oth.fkretschmar.advertisementproject.business.services.base.ITransactionService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.joda.money.Money;

/**
 *
 * @author Admin
 */
@PayPalTransaction
@RequestScoped
public class PayPalTransactionService implements ITransactionService {

    // --------------- Private fields ---------------
    
    /**
     * Stores the logger to log information for this class.
     */
    @Inject
    private Logger logger;

    // --------------- Public methods ---------------
    
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
        this.logger.info(
                String.format(
                        "Here could be a pay pal transaction of %s amount "
                        + "from the account %s to the account %s.", 
                        amount.toString(),
                        sender.toString(),
                        recipient.toString()));
    }
}
