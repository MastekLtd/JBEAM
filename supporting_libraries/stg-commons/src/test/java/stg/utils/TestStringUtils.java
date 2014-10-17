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
package stg.utils;

import junit.framework.TestCase;

/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add multi-line description of the class indicating the objectives/purpose
 * of the class and the usage with each sentence ending with a period.
 *
 * @author kedar460043
 * @since
 */
public class TestStringUtils extends TestCase {

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
	 * Test method for {@link stg.utils.StringUtils#split(java.lang.String)}.
	 */
	public void testSplitString() {
		String[] array =StringUtils.split("Kedar Raybagkar");
		assertEquals(2, array.length);
		assertEquals("Kedar", array[0]);
		assertEquals("Raybagkar", array[1]);
		
		array = StringUtils.split("Kedar/ Raybagkar");
		assertEquals(2, array.length);
		assertEquals("Kedar/", array[0]);
		assertEquals("Raybagkar", array[1]);
		
		
		array = StringUtils.split(" Kedar Raybagkar ");
		assertEquals(4, array.length);
		assertEquals("", array[0]);
		assertEquals("Kedar", array[1]);
		assertEquals("Raybagkar", array[2]);
		assertEquals("", array[3]);
		
		array = StringUtils.split("");
		assertEquals(1, array.length);
		assertEquals("", array[0]);
		
		array = StringUtils.split(null);
		assertEquals(0, array.length);
	}

	/**
	 * Test method for {@link stg.utils.StringUtils#split(java.lang.String, java.lang.Character)}.
	 */
	public void testSplitStringCharacter() {
		String[] array =StringUtils.split("Kedar Raybagkar", '#');
		assertEquals(1, array.length);
		assertEquals("Kedar Raybagkar", array[0]);
		
		array = StringUtils.split("Kedar# Raybagkar", '#');
		assertEquals(2, array.length);
		assertEquals("Kedar", array[0]);
		assertEquals(" Raybagkar", array[1]);
		
		array = StringUtils.split("Kedar/# Raybagkar", '#');
		assertEquals(2, array.length);
		assertEquals("Kedar/", array[0]);
		assertEquals(" Raybagkar", array[1]);
		
		
		array = StringUtils.split("#Kedar# Raybagkar# ", '#');
		assertEquals(4, array.length);
		assertEquals("", array[0]);
		assertEquals("Kedar", array[1]);
		assertEquals(" Raybagkar", array[2]);
		assertEquals(" ", array[3]);
		
		array = StringUtils.split("", '#');
		assertEquals(1, array.length);
		assertEquals("", array[0]);
		
		array = StringUtils.split(null, '#');
		assertEquals(0, array.length);
	}

	/**
	 * Test method for {@link stg.utils.StringUtils#split(java.lang.String, char, char)}.
	 */
	public void testSplitStringCharChar() {
		String[] array =StringUtils.split("Kedar Raybagkar", '#', '/');
		assertEquals(1, array.length);
		assertEquals("Kedar Raybagkar", array[0]);
		
		array = StringUtils.split("Kedar# Raybagkar", '#', '/');
		assertEquals(2, array.length);
		assertEquals("Kedar", array[0]);
		assertEquals(" Raybagkar", array[1]);
		
		array = StringUtils.split("Kedar/# Raybagkar", '#', '/');
		assertEquals(1, array.length);
		assertEquals("Kedar# Raybagkar", array[0]);
		
		
		array = StringUtils.split("/#Kedar/# Raybagkar# ", '#', '/');
		assertEquals(2, array.length);
		assertEquals("#Kedar# Raybagkar", array[0]);
		assertEquals(" ", array[1]);
		
		array = StringUtils.split("/#Kedar/# Raybagkar/# ", '#', '/');
		assertEquals(1, array.length);
		assertEquals("#Kedar# Raybagkar# ", array[0]);
				
		array = StringUtils.split("/#Kedar/# Raybagkar/## ", '#', '/');
		assertEquals(2, array.length);
		assertEquals("#Kedar# Raybagkar#", array[0]);
		assertEquals(" ", array[1]);
		
		array = StringUtils.split(" #/#Kedar/# Raybagkar/## ", '#', '/');
		assertEquals(3, array.length);
		assertEquals(" ", array[0]);
		assertEquals("#Kedar# Raybagkar#", array[1]);
		assertEquals(" ", array[2]);
		
		try {
			array = StringUtils.split(" #/#Kedar/# Raybagkar/## /", '#', '/');
			fail("Did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("Escape Character found without the character to be ignored.", e.getMessage());
		}
		
		array = StringUtils.split("Kedar#Raybagkar//", '#', '/');
		assertEquals(2, array.length);
		assertEquals("Kedar", array[0]);
		assertEquals("Raybagkar/", array[1]);
		
		array = StringUtils.split("Kedar##Raybagkar/#", '#', '/');
		assertEquals(3, array.length);
		assertEquals("Kedar", array[0]);
		assertEquals("", array[1]);
		assertEquals("Raybagkar#", array[2]);
		
		
		array = StringUtils.split("", '#', '/');
		assertEquals(1, array.length);
		assertEquals("", array[0]);
		
		array = StringUtils.split(null, '#', '/');
		assertEquals(0, array.length);
	}
	
	public void testJoinString() {
		String[] array = new String[] {"Kedar", "Raybagkar"};
		String str = StringUtils.join(array);
		assertEquals("Kedar Raybagkar", str);
		
		array = new String[] {"Kedar ", "Raybagkar"};
		str = StringUtils.join(array);
		assertEquals("Kedar  Raybagkar", str);
		
		array = new String[] {"", "Kedar ", "Raybagkar", ""};
		str = StringUtils.join(array);
		assertEquals(" Kedar  Raybagkar ", str);
		
		array = null;
		str = StringUtils.join(array);
		assertEquals(null, str);
		array = new String[] {};
		str = StringUtils.join(array);
		assertEquals("", str);
		
	}

	public void testJoinStringCharacter() {
		String[] array = new String[] {"Kedar", "Raybagkar"};
		String str = StringUtils.join(array, '#');
		assertEquals("Kedar#Raybagkar", str);
		
		array = new String[] {"Kedar#", "Raybagkar"};
		str = StringUtils.join(array, '#');
		assertEquals("Kedar##Raybagkar", str);
		
		array = new String[] {"", "Kedar#", "Raybagkar", ""};
		str = StringUtils.join(array, '#');
		assertEquals("#Kedar##Raybagkar#", str);
		
		array = null;
		str = StringUtils.join(array, '#');
		assertEquals(null, str);
		array = new String[] {};
		str = StringUtils.join(array, '#');
		assertEquals("", str);
		
	}
	
	public void testJoinStringCharacterCharacter() {
		String[] array = new String[] {"Kedar", "Raybagkar"};
		String str = StringUtils.join(array, '#', '/');
		assertEquals("Kedar#Raybagkar", str);
		
		array = new String[] {"Kedar#", "Raybagkar"};
		str = StringUtils.join(array, '#', '/');
		assertEquals("Kedar/##Raybagkar", str);
		
		array = new String[] {"", "Kedar#", "Raybagkar", ""};
		str = StringUtils.join(array, '#', '/');
		assertEquals("#Kedar/##Raybagkar#", str);
		
		array = null;
		str = StringUtils.join(array, '#', '/');
		assertEquals(null, str);
		array = new String[] {};
		str = StringUtils.join(array, '#', '/');
		assertEquals("", str);
		
	}
	
	public void testCountTokensString() {
		String str ="Kedar Raybagkar";
		assertEquals(2, StringUtils.countTokens(str));
		
		str = "Kedar# Raybagkar";
		assertEquals(2, StringUtils.countTokens(str));
		
		str = "Kedar  Raybagkar";
		assertEquals(3, StringUtils.countTokens(str));
		
		str = " Kedar  Raybagkar ";
		assertEquals(5, StringUtils.countTokens(str));
		
		str = "";
		assertEquals(1, StringUtils.countTokens(str));
		
		str = null;
		assertEquals(0, StringUtils.countTokens(str));
	}
	
	public void testCountTokensStringCharacter() {
		String str ="Kedar#Raybagkar";
		assertEquals(2, StringUtils.countTokens(str, '#'));
		
		str = "Kedar/##Raybagkar";
		assertEquals(3, StringUtils.countTokens(str, '#'));
		
		str = "#Kedar##Raybagkar#";
		assertEquals(5, StringUtils.countTokens(str, '#'));
		
		str = "/#Kedar/##Raybagkar/#";
		assertEquals(5, StringUtils.countTokens(str, '#'));
		
		str = "";
		assertEquals(1, StringUtils.countTokens(str, '#'));
		
		str = null;
		assertEquals(0, StringUtils.countTokens(str, '#'));
	}
	
	public void testCountTokensStringCharacterCharacter() {
		String str ="Kedar#Raybagkar";
		assertEquals(2, StringUtils.countTokens(str, '#', '/'));
		
		str = "Kedar/##Raybagkar";
		assertEquals(2, StringUtils.countTokens(str, '#', '/'));
		
		str = "#Kedar##Raybagkar#";
		assertEquals(5, StringUtils.countTokens(str, '#', '/'));
		
		str = "/#Kedar/##Raybagkar/#";
		assertEquals(2, StringUtils.countTokens(str, '#', '/'));
		
		str = "";
		assertEquals(1, StringUtils.countTokens(str, '#', '/'));
		
		str = null;
		assertEquals(0, StringUtils.countTokens(str, '#', '/'));
	}
	
	public void testExtractTokenStringInt() {
		String str =StringUtils.extractTokenAt("Kedar Raybagkar", 2);
		assertEquals("Raybagkar", str);
		
		str = StringUtils.extractTokenAt("Kedar/ Raybagkar", 2);
		assertEquals("Raybagkar", str);
		
		
		str = StringUtils.extractTokenAt(" Kedar Raybagkar ", 2);
		assertEquals("Kedar", str);
		
		str = StringUtils.extractTokenAt("", 2);
		assertEquals(null, str);
		
		str = StringUtils.extractTokenAt(null, 2);
		assertEquals(null, str);
	}
	
	public void testExtractTokenStringCharacterInt() {
		String str =StringUtils.extractTokenAt("Kedar#Raybagkar", '#', 2);
		assertEquals("Raybagkar", str);
		
		str = StringUtils.extractTokenAt("Kedar/#Raybagkar", '#', 2);
		assertEquals("Raybagkar", str);
		
		
		str = StringUtils.extractTokenAt("#Kedar#Raybagkar#", '#', 2);
		assertEquals("Kedar", str);
		
		str = StringUtils.extractTokenAt("", '#', 2);
		assertEquals(null, str);
		
		str = StringUtils.extractTokenAt(null, '#', 2);
		assertEquals(null, str);
	}
	
	public void testExtractTokenStringCharacterCharacterInt() {
		String str =StringUtils.extractTokenAt("Kedar#Raybagkar", '#', '/', 2);
		assertEquals("Raybagkar", str);
		
		str = StringUtils.extractTokenAt("Kedar/#Raybagkar", '#', '/', 2);
		assertEquals(null, str);
		
		str = StringUtils.extractTokenAt("#Kedar#Raybagkar#", '#', '/', 2);
		assertEquals("Kedar", str);
		
		str = StringUtils.extractTokenAt("", '#', '/', 2);
		assertEquals(null, str);
		
		str = StringUtils.extractTokenAt(null, '#', '/', 2);
		assertEquals(null, str);
	}

	public void testWholeWord() {
        String str = "is kdris IS $is is$ |is Is iS $iS _is is_ _is_ is1 1is 1is1";
        assertEquals("kdr kdris IS $is is$ |kdr Is iS $iS _is is_ _is_ is1 1is 1is1", StringUtils.replaceAllWholeWord(str, "is", "kdr"));
        assertEquals(2, StringUtils.countAllWholeWord(str, "is"));
	}
}
