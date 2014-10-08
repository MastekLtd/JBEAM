/**
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
 *
 * $Revision: 2382 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/com/stg/crypto/IOObfuscationUtility.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/com/stg/crypto/IOObfuscationUtility.java $
 * 
 * 1     11/16/09 3:32p Kedarr
 * 
 * 1     10/27/09 11:21a Kedarr
 * Added a new class to obfuscate the stream.
 *
 */
package com.stg.crypto;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * A simple utility class that Obfuscate IO streams.
 *  
 * @author Kedar Raybagkar
 * @since V1.0R27 
 */
public class IOObfuscationUtility {

    static {
    	BouncyCastleProvider bcProvider = new BouncyCastleProvider();
    	String name = bcProvider.getName();
    	Provider installedProvider = Security.getProvider(name);
    	if (installedProvider == null) {
    		Security.addProvider(bcProvider);
    	}
    }


    /**
     * 
     */
    private final byte[]		    keyBytes = new byte[] {
            (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07,
            (byte)0x08, (byte)0x09, (byte)0x0a, (byte)0x0b, (byte)0x0c, (byte)0x0d, (byte)0x0e, (byte)0x0f };
//            (byte)0x10, (byte)0x11, (byte)0x12, (byte)0x13, (byte)0x14, (byte)0x15, (byte)0x16, (byte)0x17 };
    
    /**
     * 
     */
    private final byte[]		    ivBytes = new byte[] { 
    		(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03,
    		(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01 };


	/**
	 * Returns an wrapped OutputStream.
	 * The obfuscation either encryption or decryption is determined by the mode.
	 * Remember to call close().
	 *   
	 * @param out OutputStream that needs to be encrypted.
	 * @param mode {@link Cipher#ENCRYPT_MODE} or {@link Cipher#DECRYPT_MODE}  
	 * @return Encrypted OutputStream
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws FileNotFoundException
	 */
	public OutputStream getObfuscatedOutputStream(OutputStream out, int mode) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, FileNotFoundException {
//		return out;
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		cipher.init(mode, key, ivSpec);
		return (new CipherOutputStream(out, cipher));
	}

	/**
	 * Returns a wrapped InputStream.
	 * The obfuscation either encryption or decryption is determined by the mode.
	 * Remember to call close().
	 * 
	 * @param in Encrypted Source
	 * @param mode {@link Cipher#ENCRYPT_MODE} or {@link Cipher#DECRYPT_MODE}  
	 * @return InputStream
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws FileNotFoundException
	 */
	public InputStream getObfuscatedInputStream(InputStream in, int mode) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, FileNotFoundException {
//		return in;
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		cipher.init(mode, key, ivSpec);
		return (new CipherInputStream(in, cipher));
	}

}
