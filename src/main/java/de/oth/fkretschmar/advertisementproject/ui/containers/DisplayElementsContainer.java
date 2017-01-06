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
package de.oth.fkretschmar.advertisementproject.ui.containers;

import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.billing.BankAccount;
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.billing.PayPalAccount;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.campaign.ContentType;
import de.oth.fkretschmar.advertisementproject.ui.AccountType;
import de.oth.fkretschmar.advertisementproject.ui.ElementType;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;

/**
 *
 * @author Admin
 */
@FacesComponent("displayElementsContainer")
public class DisplayElementsContainer extends CreateEntityContainer {

    // --------------- Public methods ---------------
    
    /**
     * Filters the elements list using the specified criteria.
     *
     * @param elements the elements that will be filtered.
     * @param criteria the criteria to apply.
     * @param selector that specifies the type of elements.
     * @return the filtered collection.
     */
    public Collection<?> filterElementsForCriteria(
            Collection<Object> elements,
            Object criteria,
            String selector) {
        if (selector.equals(ElementType.CONTENT)) {
            return elements.stream()
                    .map(element -> (Content) element)
                    .filter(content -> content.getContentType() == ContentType.getContentType(criteria.toString()))
                    .sorted((content1, content2)
                            -> content1.getContentType().name().compareTo(content2.getContentType().name()))
                    .collect(Collectors.toList());
        } else if (selector.equals(ElementType.ACCOUNT)) {
            return elements.stream()
                    .map(element -> (Account) element)
                    .filter(account -> (account instanceof BankAccount && criteria.toString().equals(AccountType.BANK_ACCOUNT))
                    || (account instanceof PayPalAccount && criteria.toString().equals(AccountType.PAYPAL_ACCOUNT)))
                    .collect(Collectors.toList());
        } else if (selector.equals(ElementType.BILL)) {
            return elements.stream()
                    .map(element -> (Bill) element)
                    .filter(bill -> bill.getGenerationDate().equals(criteria))
                    .collect(Collectors.toList());
        }

        return null;
    }

    
    /**
     * Redirects the formatting of the header item to the specified method.
     * @param headerItem
     * @return 
     */
    public String formatHeaderItemAction(Object headerItem) {
        MethodExpression formatHeaderItemAction = (MethodExpression) getAttributes().get("formatHeaderItemAction");
        Object result = formatHeaderItemAction.invoke(getFacesContext().getELContext(), new Object[]{headerItem});
        return (String)result;
    }
}
