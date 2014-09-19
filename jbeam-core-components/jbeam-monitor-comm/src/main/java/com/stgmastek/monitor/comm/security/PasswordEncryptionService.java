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
package com.stgmastek.monitor.comm.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Base64;

import com.stg.crypto.CryptoHelper;
import com.stgmastek.monitor.comm.exception.CommException;
import com.stgmastek.monitor.comm.util.CommConstants;

/**
 * The class is used for encryption of the password Implements the singleton
 * pattern The encryption methoddology is the Standard Hashing Algorithm that
 * Sun JAVA provides
 * 
 * @author grahesh.shanbhag
 */
public final class PasswordEncryptionService {

	/** Private static instance */
	private static PasswordEncryptionService instance = new PasswordEncryptionService();

	/** Private default constructor to avoid any initialization from the outside */
	private PasswordEncryptionService() {
	}

	/**
	 * Method to encrypt the password. It uses the algorithm provided in the
	 * Constants file to encrypt the password
	 * 
	 * @see CommConstants
	 * 
	 * @param password
	 *        The password to be encrypted
	 * @param encodingType 
	 * 		  The encoding type of the password supplied. 
	 * 		  Usually it is supplied as plain text with encoding type as 'UTF-8'
	 * @return the encrypted value as string
	 * @throws Exception
	 *         Any exception thrown during the encryption methodology
	 */
	public synchronized String encrypt(String password, String encodingType)
			throws CommException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(CommConstants.PASSWORD_ENCRYPTY_ALGO);
		} catch (NoSuchAlgorithmException e) {
			throw new CommException(e);
		}

		try {
			md.update(password.getBytes(encodingType));
		} catch (UnsupportedEncodingException e) {
			throw new CommException(e);
		}

		byte raw[] = md.digest();
		return CryptoHelper.toString(Base64.encode(raw));
	}

	/**
	 * Public static method to return the singleton instance
	 * 
	 * @return the singleton instance of the PasswordEncryptionService
	 */
	public static PasswordEncryptionService getInstance() {
		return instance;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/security/PasswordEncryptionService.java                                                   $
 * 
 * 4     3/05/10 12:14p Kedarr
 * Removed the sun misc Base64 encoder and instead made use of CryptoHelper and bouncy castle provider for base64 encoding.
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/