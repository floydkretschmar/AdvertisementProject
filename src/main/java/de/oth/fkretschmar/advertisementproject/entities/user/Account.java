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
package de.oth.fkretschmar.advertisementproject.entities.user;


import de.oth.fkretschmar.advertisementproject.entities.base.AbstractStringKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.base.EntityState;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import de.oth.fkretschmar.advertisementproject.entities.base.IDeletable;

/**
 * Represents an paypal account used to pay for orders.
 * 
 * @author fkre
 */
@Entity(name = "T_ACCOUNT")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class Account extends AbstractStringKeyedEntity {   
    
    // --------------- Protected constructors ---------------
    
    /**
     * Creates a new instance of {@link Account} using the specified identifier
     * using the specified account id.
     * 
     * @param   accountId   that uniquely identifies an account.
     */
    protected Account(String accountId) {
        super(accountId);
    }
}
