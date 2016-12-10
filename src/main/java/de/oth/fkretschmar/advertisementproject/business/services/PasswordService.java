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
package de.oth.fkretschmar.advertisementproject.business.services;

import de.oth.fkretschmar.advertisementproject.business.HashHelper;
import de.oth.fkretschmar.advertisementproject.business.HashingException;
import de.oth.fkretschmar.advertisementproject.business.repositories.PasswordRepository;
import de.oth.fkretschmar.advertisementproject.entities.BuilderValidationException;
import de.oth.fkretschmar.advertisementproject.entities.Password;
import java.io.Serializable;

import java.math.BigInteger;
import java.util.Arrays;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality relatetd to the generation and 
 * management of {@link Password} instances.
 *
 * @author fkre
 */
@RequestScoped
public class PasswordService implements Serializable {

    // --------------- Private fields ---------------

    /**
     * Stores the repository used to manage {@link Password} entites.
     */
    @Inject
    PasswordRepository passwordRepository;
    

    // --------------- Public methods ---------------
    
    
    /**
     * Creates the specified {@link Password}.
     * 
     * @param   password    the password that will be saved.
     */
    @Transactional
    public void createPassword(Password password) {
        this.passwordRepository.persist(password);
    }
    
    
    /**
     * Deletes the specified {@link Password} from the database.
     * 
     * @param   password    that will be deleted.
     */
    @Transactional
    public void delete(Password password) {
        this.passwordRepository.remove(password);
    }
    
    
    // --------------- Punlic  static methods ---------------
    
    
    /**
     * Creates a new safe hashed {@link Password} from an unsafe password.
     * 
     * @param   unsafePassword  that contains the unsafe password.
     * @return  a safe hashed password.
     * @throws PasswordException that indicates an error during the 
     *                                  processing of passwords.
     */
    public static Password generate(
            char[] unsafePassword) throws PasswordException {
        try {
        byte[] salt = HashHelper.generateSalt();
        byte[] value = HashHelper.hashValue(unsafePassword, salt);
        
        // remove the unsafe password from memory
        Arrays.fill(unsafePassword, '0');

        String valueString = PasswordService.convertToHex(salt) + ":"
                + PasswordService.convertToHex(value);
        
        return Password.createPassword().value(valueString).build();
        } 
        catch (HashingException ex) {
            throw new PasswordException(
                    "The hashing of the new password failed.",
                    ex);
        }
        catch (BuilderValidationException ex) {
            throw new PasswordException(
                    "The building of the new password failed.",
                    ex);
        }
    }
    
    
    /**
     * Compares a {@link Password} to an unsafe password. The result is 
     * {@code true} if and only if the given unsafe password is exactly the same 
     * as this password.
     *
     * @param password              that will be the basis of the comparison.
     * @param compareUnsafePassword that will be compared with the password.
     * @return {@code true} if the passwords are the same; {@code false}
     * otherwise.
     * @throws PasswordException that indicates an error during the 
     *                                  processing of passwords.
     */
    public static boolean equals(
            Password password, 
            char[] compareUnsafePassword) throws PasswordException {
        String[] parts = password.getValue().split(":");
        byte[] salt = PasswordService.convertFromHex(parts[0]);
        byte[] value = PasswordService.convertFromHex(parts[1]);
        
        try {
            byte[] testHash = HashHelper.hashValue(compareUnsafePassword, salt);
            return HashHelper.equals(value, testHash);
        } 
        catch (HashingException ex) {
            throw new PasswordException(
                    "The hashing of the unsafe comparison password failed.", 
                    ex);
        }
    }
    

    // --------------- Private static methods ---------------
    
    
    /**
     * Converts a textual representation of a hashed value into a byte array.
     *
     * @param hashedValue that will be converted.
     * @return A byte array that contains the hashed value.
     */
    private static byte[] convertFromHex(String hashedValue) {
        byte[] bytes = new byte[hashedValue.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hashedValue.substring(
                    2 * i, 2 * i + 2),
                    16);
        }
        return bytes;
    }

    
    /**
     * Converts the byte array containing the hashed value into a savable text.
     *
     * @param hashedValue that will be converted.
     * @return A String that contains the hashed value in text form.
     */
    private static String convertToHex(byte[] hashedValue) {
        BigInteger bi = new BigInteger(1, hashedValue);
        String hex = bi.toString(16);
        int paddingLength = (hashedValue.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
