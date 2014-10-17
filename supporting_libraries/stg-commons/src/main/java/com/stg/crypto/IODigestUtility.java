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

import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.crypto.digests.GeneralDigest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.io.DigestInputStream;
import org.bouncycastle.crypto.io.DigestOutputStream;
import org.bouncycastle.util.encoders.Hex;

/**
 * IODigestUtility is a checksum generator optimized for IO streaming.
 *
 * @author kedar.raybagkar
 * @since
 */
public class IODigestUtility{

	/**
	 * Wraps the given {@link InputStream} against a digest
	 * @param is input stream
	 * @return input stream
	 */
	public static DigestInputStream wrapDigestInputStream(GeneralDigest digest, InputStream is) {
		digest.reset();
		return new DigestInputStream(is, digest);
	}

	/* (non-Javadoc)
	 * @see com.stg.crypto.IDigester#getDigestChecksum()
	 */
	public static String getDigestChecksum(DigestInputStream dis) {
		byte[] bytes = new byte[dis.getDigest().getDigestSize()];
		dis.getDigest().doFinal(bytes, 0);
		return CryptoHelper.toString(Hex.encode(bytes));
	}
	
	/**
	 * Wraps the given {@link OutputStream} against a digest.
	 * 
	 * @param os output stream to be wrapped.
	 * @return output stream.
	 */
	public static DigestOutputStream wrapDigestOutputStream(GeneralDigest digest, OutputStream os) {
		digest.reset();
		return new DigestOutputStream(os, digest);
	}
	
	/**
	 * Helper method that returns an IODigestUtility configured for MD5 Digest.
	 * 
	 * @return IODigestUtility
	 */
	public static DigestInputStream getMD5IODigest(InputStream is) {
		return wrapDigestInputStream(new MD5Digest(), is);
	}
	
	/**
	 * Helper method that returns an IODigestUtility configured for SHA1 Digest.
	 * 
	 * @return IODigestUtility
	 */
	public static DigestInputStream getSHA1IODigest(InputStream is) {
		return wrapDigestInputStream(new SHA1Digest(), is);
	}
}
