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

import junit.framework.TestCase;

/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add multi-line description of the class indicating the objectives/purpose
 * of the class and the usage with each sentence ending with a period.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class PBEEncryptionRoutineTest extends TestCase {
	
	final String PLAIN_TEXT = "Kedar Raybagkar";

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.stg.crypto.PBEEncryptionRoutine#encode(java.lang.String)}.
	 */
	public void testEncode() {
		PBEEncryptionRoutine routine = new PBEEncryptionRoutine();
		String encoded = routine.encode(PLAIN_TEXT);
		assertNotSame("Encrypted Message failed if equals plain text", encoded, PLAIN_TEXT);
	}

	/**
	 * Test method for {@link com.stg.crypto.PBEEncryptionRoutine#decode(java.lang.String)}.
	 */
	public void testDecode() {
		PBEEncryptionRoutine routine = new PBEEncryptionRoutine();
		String encoded = routine.encode(PLAIN_TEXT);
		assertNotSame("Encrypted Message failed if equals plain text", encoded, PLAIN_TEXT);
		
		routine = new PBEEncryptionRoutine();
		String decoded = routine.decode(encoded);
		assertTrue("Message must equals to the plain text", PLAIN_TEXT.equals(decoded));
	}

	
	/**
	 * Test method for {@link com.stg.crypto.PBEEncryptionRoutine#decode(java.lang.String)}.
	 */
	public void testDecode2() {
		PBEEncryptionRoutine routine = new PBEEncryptionRoutine();
		try {
			routine.decode(PLAIN_TEXT);
			fail ("should have thrown security exception");
		} catch (SecurityException e) {
			assertTrue(e.getMessage().indexOf("Could not") > -1);
		}
	}
	
}
