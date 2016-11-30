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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Password extends AbstractAutoGenerateKeyedEntity {

    // --------------- Private fields ---------------

    /**
     * Stores the hashed presentation of the password.
     */
    @NotNull
    @Column(name = "PASS_VALUE")
    @Getter
    private String value;
    
    // --------------- Protected constructors ---------------
    
    /**
     * Creates an instance of {@link Password} using the specified unsafe
     * password value.
     *
     * @param   value   that contains the user specified password in an unsafe
     *                  mannor.
     */
    private Password(String value) {
        super();
        this.value = value;
    }
    
    // --------------- Private static methods ---------------
    
    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link Password}.
     * 
     * @param   value   that contains the user specified password in an unsafe
     *                  mannor.
     * @return the built {@link Password}.
     */
    @Builder(
            builderMethodName = "create", 
            builderClassName = "PasswordBuilder",
            buildMethodName = "build")
    private static Password validateAndCreatePassword(String value) {
        if(value == null || value.isEmpty())
            throw new BuilderValidationException(
                    Password.class, 
                    "The password can not be null or empty.");
        
        return new Password(value);
    }
}
