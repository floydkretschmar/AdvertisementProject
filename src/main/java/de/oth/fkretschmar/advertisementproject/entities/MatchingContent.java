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

import de.oth.fkretschmar.advertisementproject.entities.base.MonetaryAmountAttributeConverter;
import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a content that matches the provided {@link TargetContext}.
 */
@AllArgsConstructor
public class MatchingContent {

    /**
     * Stpres the id of the actual content that has been matched.
     */
    @Getter
    private long contentId;

    /**
     * Stores the monetary amount tbe creator of the content is willing to pay
     * per request of this campaign content.
     */
    @Getter
    private MonetaryAmount pricePerRequest;

    /**
     * Stores the number of general target groups that the content has matched.
    *
     */
    @Getter
    private int groupMatches;

    /**
     * Stores the total number of subsets within the broader target groups that
     * the content has matched.
     */
    @Getter
    private int machtesInGroup;
}
