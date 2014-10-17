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

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.crypto.digests.GeneralDigest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.util.encoders.Hex;

/**
 * Helper routines for Cryptography.
 *
 * @author Kedar Raybagkar
 * @since  16.02
 */
public class CryptoHelper {

    /**
     * Convert a byte array of 8 bit characters into a String.
     * 
     * @param bytes the array containing the characters
     * @param length the number of bytes to process
     * @return a String representation of bytes
     */
    public static String toString(
        byte[] bytes,
        int    length)
    {
        char[]  chars = new char[length];
        
        for (int i = 0; i != chars.length; i++)
        {
            chars[i] = (char)(bytes[i] & 0xff);
        }
        
        return new String(chars);
    }
    
    /**
     * Convert a byte array of 8 bit characters into a String.
     * 
     * @param bytes the array containing the characters
     * @return a String representation of bytes
     */
    public static String toString(
        byte[]  bytes)
    {
        return toString(bytes, bytes.length);
    }
    
    /**
     * Convert the passed in String to a byte array by
     * taking the bottom 8 bits of each character it contains.
     * 
     * @param string the string to be converted
     * @return a byte array representation
     */
    public static byte[] toByteArray(
        String string)
    {
        byte[]  bytes = new byte[string.length()];
        char[]  chars = string.toCharArray();
        
        for (int i = 0; i != chars.length; i++)
        {
            bytes[i] = (byte)chars[i];
        }
        
        return bytes;
    }

    /**
     * Generates a checksum for the given String.
     * 
     * @param str for which the checksum is to be generated.
     * @return String representation of the MD5 checksum
     * @throws IOException
     */
    public static String generateMD5Checksum(String str) {
    	return generateMD5Checksum(toByteArray(str));
    }
    
    /**
     * Generates a checksum for the given bytes.
     * 
     * @param bytes against which checksum will be generated.
     * @return String representation of the checksum.
     */
    public static String generateMD5Checksum(byte[] bytes) {
    	return generateChecksum(new MD5Digest(), bytes);
    }
    
    /**
     * This reads the stream fully to generate the MD5 checksum.
     * This may lead to OutOfMemory as the stream is fully read before generating the checksum.
     * Use {@link IODigestUtility} to read large streams and generate checksums.
     * 
     * @param is input steam
     * @return digest generated using MD5.
     * @throws IOException
     */
    public static String generateMD5Checksum(InputStream is) throws IOException {
    	byte[] bytes = IOUtils.toByteArray(is);
		return generateChecksum(new MD5Digest(), bytes);
    }
    
    /**
     * This is a general purpose checksum generator method.
     * 
     * @param digest Any class that extends {@link GeneralDigest}.
     * @param bytes bytes on which the checksum is to be generated.
     * @return checksum as per the digest.
     * @see CryptoHelper#generateMD5Checksum(byte[])
     * @see CryptoHelper#generateMD5Checksum(InputStream)
     * @see CryptoHelper#generateMD5Checksum(String)
     * @see CryptoHelper#generateSHA1Checksum(byte[])
     * @see CryptoHelper#generateSHA1Checksum(InputStream)
     * @see CryptoHelper#generateSHA1Checksum(String)
     */
    public static String generateChecksum(GeneralDigest digest, byte[] bytes) {
    	digest.update(bytes, 0, bytes.length);
    	byte[] checkSum = new byte[digest.getDigestSize()];	
    	digest.doFinal(checkSum, 0);
    	return toString(Hex.encode(checkSum));
    }
    
    
    
    /**
     * Returns the SHA1 checksum for the given string.
     * @param str against which the SHA1 checksum is to be generated.
     * @return String representation of the SHA1 checksum.
     */
    public static String generateSHA1Checksum(String str) {
    	return generateSHA1Checksum(toByteArray(str));
    }
    
    /**
     * Returns the SHA1 checksum for the given bytes.
     * @param bytes against which the SHA1 checksum is to be generated.
     * @return String representation of the SHA1 checksum.
     */
    public static String generateSHA1Checksum(byte[] bytes) {
    	return generateChecksum(new SHA1Digest(), bytes);
    }
    
    /**
     * This reads the stream fully to generate the SHA1 checksum.
     * This may lead to OutOfMemory as the stream is fully read before generating the checksum.
     * Use {@link IODigestUtility} to read large streams and generate checksums.
     * 
     * @param is input steam
     * @return digest generated using SHA1.
     * @throws IOException
     * @see IODigestUtility
     */
    public static String generateSHA1Checksum(InputStream is) throws IOException {
    	byte[] bytes = IOUtils.toByteArray(is);
    	return generateChecksum(new SHA1Digest(), bytes);
    }
    
}
