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
package de.oth.fkretschmar.advertisementproject.business.service;

import de.oth.fkretschmar.advertisementproject.business.HashHelper;
import de.oth.fkretschmar.advertisementproject.business.repository.PasswordRepository;
import de.oth.fkretschmar.advertisementproject.entity.Password;

import java.math.BigInteger;
import java.util.Arrays;

import javax.inject.Inject;

/**
 *
 * @author fkre
 */
public class PasswordService {

    // --------------- Private fields ---------------

    /**
     * Stores the repository used to manage {@link Password} entites.
     */
    @Inject
    PasswordRepository passwordRepository;
    

    // --------------- Public methods ---------------
    
    
    /**
     * Deletes the specified {@link Password} from the database.
     * 
     * @param   password    that will be deleted.
     */
    public void delete(Password password) {
        this.passwordRepository.delete(password);
    }
    
    
    /**
     * Saves the specified {@link Password}.
     * 
     * @param   password    the password that will be saved.
     * @return              the saved password.
     */
    public Password save(Password password) {
        return this.passwordRepository.save(password);
    }
    
    
    // --------------- Punlic  static methods ---------------
    
    
    /**
     * Creates a new safe hashed {@link Password} from an unsafe password.
     * 
     * @param   unsafePassword  that contains the unsafe password.
     * @return  a safe hashed password.
     */
    public static Password create(char[] unsafePassword) {
        byte[] salt = HashHelper.generateSalt();
        byte[] value = HashHelper.hashValue(unsafePassword, salt);
        
        // delete the unsafe password from memory
        Arrays.fill(unsafePassword, '0');

        String valueString = PasswordService.convertToHex(salt) + ":"
                + PasswordService.convertToHex(value);
        
        return new Password(valueString);
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
     */
    public static boolean equals(Password password, char[] compareUnsafePassword) {
        String[] parts = password.getValue().split(":");
        byte[] salt = PasswordService.convertFromHex(parts[0]);
        byte[] value = PasswordService.convertFromHex(parts[1]);
        
        byte[] testHash = HashHelper.hashValue(compareUnsafePassword, salt);
        return HashHelper.equals(value, testHash);
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
