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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 * A simple utility class that obfuscates the given IO stream.
 * Use static methods for hard-coded encryption otherwise, use non-static methods
 * to make use of Randomized encryption.
 * 
 * @author Kedar Raybagkar
 * @since V1.0R27
 */
public class IOObfuscator {

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
	private static final byte[] keyBytes = new byte[] { (byte) 0x00,
			(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05,
			(byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0a,
			(byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f };
	// (byte)0x10, (byte)0x11, (byte)0x12, (byte)0x13, (byte)0x14, (byte)0x15,
	// (byte)0x16, (byte)0x17 };

	/**
     * 
     */
	private static final byte[] ivBytes = new byte[] { (byte) 0x00,
			(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x00, (byte) 0x01,
			(byte) 0x02, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01 };

	private int strength;

	/**
	 * Indicates that the keys should be generated randomly using with the given
	 * strength.
	 * 
	 * @param strength of the key.
	 */
	public IOObfuscator(int strength) {
		this.strength = strength;
	}

	/**
	 * Returns an wrapped OutputStream. The obfuscation either encryption or
	 * decryption is determined by the mode. Remember to call close().
	 * 
	 * @param out
	 *            OutputStream that needs to be encrypted.
	 * @param mode
	 *            {@link Cipher#ENCRYPT_MODE} or {@link Cipher#DECRYPT_MODE}
	 * @return Encrypted OutputStream
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws FileNotFoundException
	 */
	public static OutputStream wrappedOuputStream(OutputStream out,
			int mode) throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, FileNotFoundException {
		// return out;
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		cipher.init(mode, key, ivSpec);
		return (new CipherOutputStream(out, cipher));
	}

	/**
	 * Returns a wrapped InputStream. The obfuscation either encryption or
	 * decryption is determined by the mode. Remember to call close().
	 * 
	 * @param in
	 *            Encrypted Source
	 * @param mode
	 *            {@link Cipher#ENCRYPT_MODE} or {@link Cipher#DECRYPT_MODE}
	 * @return InputStream
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws FileNotFoundException
	 */
	public static InputStream wrappedInputStream(InputStream in, int mode)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, FileNotFoundException {
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		cipher.init(mode, key, ivSpec);
		return (new CipherInputStream(in, cipher));
	}

	public OutputStream getEncryptedOutputStream(OutputStream os)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			IOException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		byte[] ivBytes = SecureRandom.getSeed(16);
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(strength);
		Key key = generator.generateKey();
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
		return new MyOS(os, cipher, key, ivBytes);
	}

	/**
	 * The passed {@link InputStream} is wrapped around with an decrypt'ed input stream.
	 * 
	 * @param is InputStream that needs to be wrapped.
	 * @return InputStream
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IOException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws InvalidAlgorithmParameterException
	 */
	public InputStream getDecrytedInputStream(InputStream is)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			IOException, NoSuchPaddingException, InvalidKeyException,
			InvalidKeySpecException, InvalidAlgorithmParameterException {
		int len = is.read();
		if (len < 0) {
			return is;
		}
		byte[] bytes = new byte[len];
		is.read(bytes);
		len = is.read();
		if (len < 0) {
			return is;
		}
		try {
			byte[] ivBytes = new byte[len];
			is.read(ivBytes);
			SecretKeySpec keySpec;
			try {
				keySpec = new SecretKeySpec(Base64.decode(bytes), "AES");
			} catch (ArrayIndexOutOfBoundsException e) {
				return is;
			}
			Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));
			return new CipherInputStream(is, cipher);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ObfuscationException("The file is not obfuscated this class.");
		}
	}
}

final class MyOS extends CipherOutputStream {

	private boolean isWritten;

	private Key key;

	private byte[] ivBytes;

	/**
	 * @param os
	 * @param c
	 * @param bytes 
	 */
	public MyOS(OutputStream os, Cipher c, Key key, byte[] bytes) {
		super(os, c);
		isWritten = false;
		this.key = key;
		this.ivBytes = bytes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.crypto.CipherOutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		if (!isWritten) {
			persist();
		}
		super.write(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.crypto.CipherOutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException {
		if (!isWritten) {
			persist();
		}
		super.write(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.crypto.CipherOutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (!isWritten) {
			persist();
		}
		super.write(b, off, len);
	}

	private void persist() throws IOException {
		synchronized (this) {
			if (!isWritten) {
				byte[] keyBytes = Base64.encode(key.getEncoded());
				super.out.write(keyBytes.length);
				super.out.write(keyBytes);
				super.out.write(ivBytes.length);
				super.out.write(ivBytes);
				isWritten = true;
			}
		}
	}
}