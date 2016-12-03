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


import de.oth.fkretschmar.advertisementproject.entities.base.AbstractStringKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.base.EntityState;
import de.oth.fkretschmar.advertisementproject.entities.base.IUndeletableEntity;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents an paypal account used to pay for orders.
 * 
 * @author fkre
 */
@Entity(name = "T_ACCOUNT")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class Account extends AbstractStringKeyedEntity 
        implements IUndeletableEntity<String> {   
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the state of the entity.
     **/
    @Column(name = IUndeletableEntity.ENTITY_STATE_COLUMN_NAME)
    @Enumerated(EnumType.STRING)
    private EntityState state = EntityState.CREATED;
    
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
    
    // --------------- Public getters and setters ---------------
    
    /**
     * Gets the state of the entity.
     * @return  the entity state.
     */
    @Override
    public EntityState getEntityState() {
        return this.state;
    }

    /**
     * Sets the state of the entity.
     * @param state     the new state of the entity.s
     */
    @Override
    public void setEntityState(EntityState state) {
        this.state = state;
    }
}
