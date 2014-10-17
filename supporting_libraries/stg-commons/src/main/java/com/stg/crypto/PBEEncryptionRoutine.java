/*
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */

package com.stg.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 * Password based Encryption routine. 
 * Class encrypt and decrypt the parameter string.
 * 
 * @author Kedar Raybagkar
 * @version $Revision: 1 $
 * @since 16.00
 */
public class PBEEncryptionRoutine {

    /**
     * Stores the VERSION number of the class from the configuration management
     * tool.
     */
    public final static String REVISION = "$Revision:: 1                  $";
    
    private static final String PROVIDER;

    /**
     * Cipher to encrypt a given String.
     */
    private Cipher encryptCipher;

    /**
     * Cipher to decrypt a given String.
     */
    private Cipher decryptCipher;

    /**
     * Random characters used for cihper.
     */
    private final char[] pass = S.getPass();

    /**
     * The byte array used as salt.
     */
    private final byte[] salt = S.getSalt();

    private final int iterations = S.getIterations();
    
    static {
    	BouncyCastleProvider bcProvider = new BouncyCastleProvider();
    	String name = bcProvider.getName();
    	PROVIDER = name;
    	Provider installedProvider = Security.getProvider(name);
    	if (installedProvider == null) {
    		Security.addProvider(bcProvider);
    	}
    }

    /**
     * Defines the pass
     * 
     * @throws SecurityException
     */
    public PBEEncryptionRoutine() throws SecurityException {

//        Security.addProvider(new BouncyCastleProvider());
        init(pass, salt, iterations);
    }

    /**
     * Initializes the Decoder and Encoder ciphers.
     * 
     * @param pass
     * @param salt
     * @param iterations
     * @throws SecurityException
     */
    private void init(char[] pass, byte[] salt, int iterations)
            throws SecurityException {
        try {

            PBEParameterSpec ps = new javax.crypto.spec.PBEParameterSpec(salt,
                    iterations);
            Provider provider = Security.getProvider(PROVIDER);
            SecretKeyFactory kf = SecretKeyFactory
                    .getInstance("PBEWithMD5AndDES", provider);

            SecretKey k = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(
                    pass));

            encryptCipher = Cipher
                    .getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding", provider);

            encryptCipher.init(Cipher.ENCRYPT_MODE, k, ps);

            decryptCipher = Cipher
                    .getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding", provider);

            decryptCipher.init(Cipher.DECRYPT_MODE, k, ps);

        } catch (NoSuchAlgorithmException e) {
          throw new SecurityException(
          "Could not initialize PBEEncryptionRoutine: "
                  + e.getMessage());
		} catch (InvalidKeySpecException e) {
	          throw new SecurityException(
	                  "Could not initialize PBEEncryptionRoutine: "
	                          + e.getMessage());
		} catch (NoSuchPaddingException e) {
	          throw new SecurityException(
	                  "Could not initialize PBEEncryptionRoutine: "
	                          + e.getMessage());
		} catch (InvalidKeyException e) {
	          throw new SecurityException(
	                  "Could not initialize PBEEncryptionRoutine: "
	                          + e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
	          throw new SecurityException(
	                  "Could not initialize PBEEncryptionRoutine: "
	                          + e.getMessage());
		}
    }

    /**
     * 
     * convenience method for encrypting a string.
     * 
     * 
     * 
     * @param str
     *            Description of the Parameter
     * 
     * @return String the encrypted string.
     * 
     * @exception SecurityException
     *                Description of the Exception
     * 
     */
    private synchronized String encrypt(byte[] salt, String str)
            throws SecurityException {
        try {
            init(pass, salt, iterations);
            String str2 = CryptoHelper.toString(Base64.encode(encryptCipher.doFinal(CryptoHelper.toByteArray(str))));
            StringBuffer buf = new StringBuffer();
            buf.append("[");
            buf.append(CryptoHelper.toString(Base64.encode(salt)));
            buf.append("]");
            buf.append(str2);
            return CryptoHelper.toString(Base64.encode(CryptoHelper.toByteArray(buf.toString())));
        } catch (Throwable e) {
          throw new SecurityException("Could not encrypt: " + e.getMessage(), e);
		}
    }

    /**
     * 
     * convenience method for encrypting a string.
     * 
     * 
     * 
     * @param str
     *            Description of the Parameter
     * 
     * @return String the encrypted string.
     * 
     * @exception SecurityException
     *                Description of the Exception
     * 
     */
    private synchronized String decrypt(byte[] salt, String str)
			throws SecurityException {
		try {
			init(pass, salt, iterations);
			return CryptoHelper.toString(decryptCipher.doFinal(Base64
					.decode(str)));
		} catch (Throwable e) {
			throw new SecurityException("Could not decrypt: " + e.getMessage(), e);
		}
	}

    /**
     * Encodes and encrypts the given String.
     * 
     * @param str
     *            String to be encoded and encrypted
     * @throws SecurityException
     * @return encoded string
     */
    public synchronized String encode(String str) throws SecurityException {
        byte[] salt = new byte[8];
        Random rand = new Random();
        rand.nextBytes(salt);
        return encrypt(salt, str);
    }

    /**
     * Decodes and decrypts the given String.
     * 
     * @param str
     *            String to be decoded.
     * @throws SecurityException
     * @return Decrypted String
     */
    public synchronized String decode(String str) throws SecurityException {
    	try {
	        String newStr = CryptoHelper.toString(Base64.decode(str));
	        String strSalt = newStr.substring(1, newStr.indexOf("]"));
	        byte[] saltBytes = Base64.decode(strSalt);
	        String strEncrypted = newStr.substring(strSalt.length() + 2);
	        return decrypt(saltBytes, strEncrypted);
    	} catch (Throwable e) {
    		throw new SecurityException("Could not decrypt..", e);
    	}
    }

    /**
     * Inner class.
     * 
     * @author kraybag
     */
    private final static class S {

    	private final static char[] PASS = "h0k9dlq".toCharArray();
        
//    	private final static char[] PASS = "q109lkdhq-0e81;ldiappri109unzhgytiajkkdyajn*&^%$d1ap@#$q>.<dm".toCharArray();

    	private final static byte[] SALT = new byte[] { (byte) 0xa3, (byte) 0x21, (byte) 0x24,
            (byte) 0x2c
            //, (byte) 0xf2, (byte) 0xd2, (byte) 0x3e,
            //(byte) 0x19 
            };
        
        /**
         * Returns the pass.
         * 
         * @return Pass.
         */
        protected final static char[] getPass() {
            
            return PASS;
        }

        /**
         * Returns the number of iterations.
         * 
         * @return iterations
         */
        protected final static int getIterations() {
            return 100;
        }

        /**
         * Returns the salt.
         * 
         * This method is to hide the constant value from the code and the
         * javadoc.
         * 
         * @return salt.
         */
        protected final static byte[] getSalt() {
            return SALT;
        }
    }

}