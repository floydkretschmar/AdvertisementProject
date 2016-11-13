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
package de.oth.fkretschmar.advertisementproject.business;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Represents a bundle of usefull methods used for hashing of unsecured data.
 * 
 * Implementation of the password hashing as seen at http://howtodoinjava.com/
 * security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 * 
 * @author  fkre    Floyd Kretschmar
 */
public class HashHelper {
    
    // --------------- Public static constants ---------------
    
    /**
     * Defines the textual representation of the SHA1PRNG algorithm.
     */
    public static final String SALT_ALG_SHA1PRNG = "SHA1PRNG";
    
    /**
     * Defines the textual representation of the PBKDF2 algorithm.
     */
    public static final String HASH_ALG_PBKDF2 = "PBKDF2WithHmacSHA1";
    
    // --------------- Private static constants ---------------
    
    /**
     * Defines the number of iterations that will be used for the hashing.
     */
    private static final int ITERATIONS = 1000;
    
    /**
     * Defines the number of bits the generated hash will be long.
     */
    private static final int KEY_LENGTH = 192; // bits
    
    // --------------- Public static methods ---------------
    
    /**
     * Compares two hashed values with each other. The result is {@code true} 
     * if and only if the two given hashes are exactly the same. 
     *
     * @param   hash1   the first hash to compare with.
     * @param   hash2   the second hash to compare with.
     * @return  {@code true} if the hashes are the same;
     *          {@code false} otherwise.
     */
    public static boolean equals(byte[] hash1, byte[] hash2) {
        if(hash1 == null || hash2 == null)
            return false;
        
        int diff = hash1.length ^ hash2.length;
        
        for(int i = 0; i < hash1.length && i < hash2.length; i++)
        {
            diff |= hash1[i] ^ hash2[i];
        }
        
        return diff == 0;
    }

    /**
     * Generates the salt used to randomize the result of the hashing
     * using the SHA1PRNG algorithm.
     * 
     * @return  A {@code byte} array that contains the randomized salt.
     * @throws  HashingException    if the algorithm used to generate the
     *                              does not exist.
     */
    public static byte[] generateSalt()
            throws HashingException
    {
        return HashHelper.generateSalt(HashHelper.SALT_ALG_SHA1PRNG);
    }
    
    /**
     * Generates the salt used to randomize the result of the hashing
     * using the specified generation algorithm.
     * 
     * @param   saltGenerationAlgorithm The algorithm used to generate the salt.
     * @return  A {@code byte} array that contains the randomized salt.
     * @throws  HashingException    if the algorithm used to generate the
     *                              does not exist.
     */
    public static byte[] generateSalt(String saltGenerationAlgorithm) 
            throws HashingException
    {
        try {
            SecureRandom sr = SecureRandom.getInstance(saltGenerationAlgorithm);
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        }
        catch(NoSuchAlgorithmException e) {
            throw new HashingException(e);
        }
    }
        
    
    /**
     * Hashes the provided unsecured password using the specified salt and 
     * the PBKDF2W algorithm.
     * 
     * @param   unsecuredValue       that will be hashed.
     * @param   salt                 The salt used to randomize the hash.
     * @return  A String that contains the hash.
     * @throws  HashingException     that indicates that the hashing has failed.
     */
    public static byte[] hashValue(
            char[] unsecuredValue, 
            byte[] salt) 
            throws HashingException {
        return HashHelper.hashValue(unsecuredValue, 
                salt, 
                HashHelper.HASH_ALG_PBKDF2);
    }
    
    /**
     * Hashes the provided unsecured password using the specified salt and 
     * generation algorithm.
     * 
     * @param   unsecuredValue          that will be hashed.
     * @param   salt                    The salt used to randomize the hash.
     * @param   hashGenerationAlgorithm The algorithm used for hashing.
     * @return  A String that contains the hashed password.
     * @throws  HashingException        that indicates that the hashing has 
     *                                  failed.
     */
    public static byte[] hashValue(
            char[] unsecuredValue, 
            byte[] salt,
            String hashGenerationAlgorithm) 
            throws HashingException {
        try {
            // specify the hashing algorithm and parameters
            PBEKeySpec spec = new PBEKeySpec(
                unsecuredValue,
                salt,
                ITERATIONS,
                KEY_LENGTH
            );
            
            // hash the password
            SecretKeyFactory key = SecretKeyFactory.getInstance(hashGenerationAlgorithm);
            byte[] hashedValue = key.generateSecret(spec).getEncoded();
            
            return hashedValue;
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new HashingException(e);
        }
    }
}
