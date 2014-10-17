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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.bouncycastle.crypto.io.DigestInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Add a one liner description of the class with a period at the end.
 * 
 * Add multi-line description of the class indicating the objectives/purpose of
 * the class and the usage with each sentence ending with a period.
 * 
 * @author kedar.raybagkar
 * @since
 */
public class TestCheckSum extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.stg.crypto.CryptoHelper#generateMD5CheckSum(java.lang.String)}
	 * .
	 */
	@Test
	public void testGenerateMD5CheckSumString() {
		String str = "Kedar Raybagkar";
		System.out.print(str + "\t");
		for (int i = 0; i < 10; i++) {
			str = CryptoHelper.generateMD5Checksum(str);
		}
		System.out.println(str);
	}

	/**
	 * Test method for
	 * {@link com.stg.crypto.CryptoHelper#generateMD5CheckSum(java.io.InputStream)}
	 * .
	 */
	@Test
	public void testGenerateMD5CheckSumInputStream() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(
					new File(
							"C:/Users/yashovardhan440076/.m2/repository/org/bouncycastle/bcprov-jdk16/1.46/bcprov-jdk16-1.46.jar"));
			String str = CryptoHelper.generateMD5Checksum(fis);
			assertEquals("881ce7b0e75a764892eafa63af7e4d38", str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testGenerateSHA1CheckSum() {
		try {
			FileInputStream fis = new FileInputStream(
					new File(
							"C:/Users/yashovardhan440076/.m2/repository/org/bouncycastle/bcprov-jdk16/1.46/bcprov-jdk16-1.46.jar"));
			String str = CryptoHelper.generateSHA1Checksum(fis);
			assertEquals("ce091790943599535cbb4de8ede84535b0c1260c", str);
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException caught");
		} catch (IOException e) {
			fail("IOException caught");
		}
	}

	public void testIOStreamMD5Checksum() {
		try {
			InputStream is = IODigestUtility.getMD5IODigest(new FileInputStream(
					new File(
					"C:/Users/yashovardhan440076/.m2/repository/org/bouncycastle/bcprov-jdk16/1.46/bcprov-jdk16-1.46.jar")));
			while(is.available() > 0) {
				byte[] bytes = new byte[1024*1024];
				is.read(bytes);
			}
			is.close();
			String str = IODigestUtility.getDigestChecksum((DigestInputStream) is);
			assertEquals("881ce7b0e75a764892eafa63af7e4d38", str);
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException caught");
		} catch (IOException e) {
			fail("IOException caught");
		}

	}

	public void testIOStreamSHA1Checksum() {
		try {
			InputStream is = IODigestUtility.getSHA1IODigest(new FileInputStream(
					new File(
					"C:/Users/yashovardhan440076/.m2/repository/org/bouncycastle/bcprov-jdk16/1.46/bcprov-jdk16-1.46.jar")));
			while(is.available() > 0) {
				byte[] bytes = new byte[1024];
				is.read(bytes);
			}
			is.close();
			String str = IODigestUtility.getDigestChecksum((DigestInputStream) is);
			assertEquals("ce091790943599535cbb4de8ede84535b0c1260c", str);
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException caught");
		} catch (IOException e) {
			fail("IOException caught");
		}
		
	}
	
	public void testIOStreamSHA1ChecksumFailure() {
		try {
			InputStream is = IODigestUtility.getSHA1IODigest(new FileInputStream(
					new File(
					"C:/Users/yashovardhan440076/.m2/repository/org/bouncycastle/bcprov-jdk16/1.46/bcprov-jdk16-1.46.jar")));
			while(is.available() > 0) {
				byte[] bytes = new byte[1024];
				is.read(bytes);
			}
			is.close();
			assertEquals("ce091790943599535cbb4de8ede84535b0c1260c", IODigestUtility.getDigestChecksum((DigestInputStream) is));
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException caught");
		} catch (IOException e) {
			fail("IOException caught");
		}
	}
	

}
