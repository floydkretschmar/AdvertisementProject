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


import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.base.IDeletable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a password that was picked by the user to secure an account.
 *
 * @author fkre Floyd Kretschmar
 */
@Entity(name = "T_PASSWORD")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Password extends AbstractAutoGenerateKeyedEntity 
        implements IDeletable<Long> {

    // --------------- Private fields ---------------

    /**
     * Stores the hashed presentation of the password.
     */
    @NotNull
    @Column(name = "PASS_VALUE", nullable = false)
    @Getter
    private String value;
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Password}.
     * 
     * @param   value   that contains the user specified password in an unsafe
     *                  mannor.
     * @return the built {@link Password}.
     * @throws  BuilderValidationException  that indicates that one or more of 
     *                                      of the given creation parameters are
     *                                      invalid.
     */
    @Builder(
            builderMethodName = "createPassword", 
            builderClassName = "PasswordBuilder",
            buildMethodName = "build")
    private static Password validateAndCreatePassword(String value) 
            throws BuilderValidationException {
        if(value == null || value.isEmpty())
            throw new BuilderValidationException (
                    Password.class, 
                    "The password can not be null or empty.");
        
        return new Password(value);
    }
}
