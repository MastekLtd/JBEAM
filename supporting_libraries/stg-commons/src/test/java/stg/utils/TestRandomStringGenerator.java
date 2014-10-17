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

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import stg.utils.RandomStringGenerator.OPTION;

/**
 * Add a one liner description of the class with a period at the end.
 * 
 * Add multi-line description of the class indicating the objectives/purpose of
 * the class and the usage with each sentence ending with a period.
 * 
 * @author Kedar Raybagkar
 * @since
 */
public class TestRandomStringGenerator extends TestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testRandomString1() {
		String format = "A{/{z}aa#999#AaAAaa";
		try {
			long time = System.nanoTime();
			String str = RandomStringGenerator.generateRandom(format);
			assertNotNull("Generated Random", str);
			time = System.currentTimeMillis()-time;
			checkGeneratedFormat(new RandomStringGenerator(), format, str);
			System.out.println("Format \t " + format + " \t Random \t"
					+ str + "\t TimeTaken \t" + time);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
	}

	public void testRandomString2() {
		String str = null;
		String format = "A{{z}aa#999#AaAAaa";
		try {
			str = RandomStringGenerator.generateRandom(format);
			fail("Should have thrown IllegalArgumentException for the given format "
					+ format + " but generated string " + str);
		} catch (Throwable e) {
			assertTrue("Caught IllegalArgumentException",
					(e instanceof IllegalArgumentException));
		}
	}
	
	public void testRandomString3() {
		String format = "A{/{z/}}9a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			String str = RandomStringGenerator.generateRandom(format);
			time = System.currentTimeMillis()-time;
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(new RandomStringGenerator(), format, str);
			System.out.println("Format \t " + format + " \t Random \t"
			        + str + "\t TimeTaken \t" + time);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
	}
	
	public void testRandomString4() {
		String format = "A{z}9a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			String str = RandomStringGenerator.generateRandom(format);
			time = System.currentTimeMillis() - time;
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(new RandomStringGenerator(), format, str);
			System.out.println("Format \t " + format + " \t Random \t"
			        + str + "\t TimeTaken \t" + (System.nanoTime() - time));
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
	}
	
	public void testRandomString5() {
		String format = "A//9a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			String str = RandomStringGenerator.generateRandom(format);
			System.out.println("Format \t " + format + " \t Random \t"
					+ str + "\t TimeTaken \t" + (System.nanoTime() - time));
			assertNotNull("Generated Random", str);
//				assertTrue(str.charAt(1) == '/');
			checkGeneratedFormat(new RandomStringGenerator(), format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
		
	}
	
	public void testRandomString6() {
		String format = "A{kedar}9a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			String str = RandomStringGenerator.generateRandom(format);
			System.out.println("Format \t " + format + " \t Random \t"
					+ str + "\t TimeTaken \t" + (System.nanoTime() - time));
			assertNotNull("Generated Random", str);
			assertTrue(str.substring(1, 6).equals("kedar"));
			checkGeneratedFormat(new RandomStringGenerator(), format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
		
	}
	
	public void testRandomString7() {
		String format = "Aaaa999a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_CASE_SENSITIVE);
			String str = generator.generate();
			System.out.println("Format \t " + format + " \t Random \t"
					+ str + "\t Is Unique TimeTaken \t"
					+ (System.nanoTime() - time));
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(generator, format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}

	}
	
	public void testRandomString71() {
		String format = "Aaaa999a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
			String str = generator.generate();
			System.out.println("Format \t " + format + " \t Random \t"
					+ str + "\t Is Unique TimeTaken \t"
					+ (System.nanoTime() - time));
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(generator, format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
		
	}
	
	public void testRandomString711() {
		String format = "Aaaa999a#9A#9aA#9a";
		try {
			for (int i=0; i<1000; i++) {
				RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
				String str = generator.generate();
				assertNotNull("Generated Random", str);
				checkGeneratedFormat(generator, format, str);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
	}
	
	public void testRandomString8() {
		String format = "Aaaa{abcdefghijklmnopqrstuvwxyz}999a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_CASE_SENSITIVE);
			String str = generator.generate();
			System.out.println("Format \t " + format + " \t Random \t"
					+ str
					+ "\t Barring Constant {..} is Unique. TimeTaken \t"
					+ (System.nanoTime() - time));
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(generator, format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
	}

	public void testRandomString81() {
		String format = "Aaaa{abcdefghijklmnopqrstuvwxyz}999a#9A#9aA#9a";
		try {
			long time = System.nanoTime();
			RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
			String str = generator.generate();
			System.out.println("Format \t " + format + " \t Random \t"
					+ str
					+ "\t Barring Constant {..} is Unique. TimeTaken \t"
					+ (System.nanoTime() - time));
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(generator, format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should have generated Random String for the given format "
					+ format + " but exception is thrown");
		}
		
	}
	
	public void testRandomString9() {
		String format = "Aaaa###########";
		try {
			String str = RandomStringGenerator.generateRandom(format, OPTION.UNIQUE_CASE_SENSITIVE);
			fail("Should not have generated the string #" + str);
		} catch (Throwable e) {
			assertTrue("Caught Exception " + e.getMessage(), true);
		}
	}
	
	public void testRandomString91() {
		String format = "Aaaa###########";
		try {
			String str = RandomStringGenerator.generateRandom(format, OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
			fail("Should not have generated the string #" + str);
		} catch (Throwable e) {
			assertTrue("Caught Exception " + e.getMessage(), true);
		}
	}
	
	public void testRandomString10() {
		String format = "Aaaa###{Aa#9}/{/}";
		try {
			String str = RandomStringGenerator.generateRandom(format, OPTION.UNIQUE_CASE_SENSITIVE);
			System.out.println("Format \t " + format + " \t Random \t"
					+ str);
			assertNotNull("Generated Random", str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}

	public void testRandomString101() {
		String format = "Aaaa###{Aa#9}/{/}";
		try {
			String str = RandomStringGenerator.generateRandom(format, OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
			System.out.println("Format \t " + format + " \t Random \t"
					+ str);
			assertNotNull("Generated Random", str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}
	
	public void testRandomString11() {
		String format = "A#9a#A9a{ri/{/}tesh}";
		try {
			RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
			String str = generator.generate();
			System.out.println("Format \t " + format + " \t Random \t"
					+ str);
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(generator, format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}
	
	public void testRandomString111() {
		String format = "A#9a#A9a{ri/{/}tesh}";
		try {
			RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
			String str = generator.generate();
			System.out.println("Format \t " + format + " \t Random \t"
					+ str);
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(generator, format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}
	
	public void testRandomString12() {
		try {
			String str = RandomStringGenerator.generateRandom(10);
			System.out.println("Random Length Format \t\t Random \t" + str);
			assertNotNull("Generated Random", str);
			assertTrue("Generated Random Length", str.length() == 10);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}
	
	public void testRandomString13() {
		try {
			String format = RandomStringGenerator.generateRandomFormat(10);
			// generator.setSpecialChars(new char[] {' ', '#', '%'});
			String str = RandomStringGenerator.generateRandom(format);
			System.out.println("Random Space Test \t\t Random \t" + str);
			assertNotNull("Generated Random", str);
			checkGeneratedFormat(new RandomStringGenerator(), format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}
	
	
	/**
	 * Test method for
	 * {@link stg.utils.RandomStringGenerator#generateRandom(java.lang.String)}.
	 */
	public void testGenerateRandomString() {
		try {
			String format = RandomStringGenerator.generateRandomFormat(20);
			RandomStringGenerator generator = new RandomStringGenerator(format, OPTION.UNIQUE_CASE_SENSITIVE);
			String str = generator.generate();
			System.out.println("Random space Test \t\t Random \t" + str);
			assertTrue("Generated Random Should not have any space", !str
					.contains(" "));
			checkGeneratedFormat(generator, format, str);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().indexOf("than the supplied special characters") > -1);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}
	
	/**
	 * Test method for
	 * {@link stg.utils.RandomStringGenerator#generateRandom(java.lang.String)}.
	 */
	public void testGenerateRandomString1() {
		try {
			String format = RandomStringGenerator.generateRandomFormat(10);
			RandomStringGenerator generator = new RandomStringGenerator(format);
			String str = generator.generate();
			System.out.println("Random space Test \t\t Random \t" + str);
			assertTrue("Generated Random Should not have any space", !str
					.contains(" "));
			checkGeneratedFormat(generator, format, str);
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Should not have thrown any exception #" + e.getMessage());
		}
	}
	
	@Test
	public void testGenerateRandom2() {
		RandomStringGenerator generator = new RandomStringGenerator("Aaa#9Aaa9", OPTION.UNIQUE_CASE_SENSITIVE);
		generator.setSpecialChars(new char[] {'$','@'});
		for (int i=0; i<30; i++) {
			String str = generator.generate();
			checkGeneratedFormat(generator, "Aaa#9Aaa9", str);
		}
	}
	
	@Test
	public void testGenerateRandom21() {
		RandomStringGenerator generator = new RandomStringGenerator("Aaa#9Aaa9", OPTION.UNIQUE_INGNORE_CASE_SENSITIVE);
		generator.setSpecialChars(new char[] {'$','@'});
		for (int i=0; i<30; i++) {
			String str = generator.generate();
			checkGeneratedFormat(generator, "Aaa#9Aaa9", str);
		}
	}
	
	private void checkGeneratedFormat(RandomStringGenerator generator, String format, String value) {
		boolean constantStarted = false;
		CharacterIterator iter = new StringCharacterIterator(format);
		ArrayList<Character> genChars = new ArrayList<Character>();
		ArrayList<String> genStr = new ArrayList<String>();
		char c = iter.first();
		int index = 0;
		while (c != CharacterIterator.DONE) {
			switch (c) {
			  case RandomStringGenerator.ESCAPE:
				  c = iter.next();
				  assertTrue(c == value.charAt(index));
				  break;
			  case 'a' :
				  if (!constantStarted) {
					  char v = value.charAt(index);
					  assertTrue("Is character lowerCase ? :" + v, Character.isLowerCase(v));
					  assertTrue(generator.getLowerCaseAlphabetsAsList().contains(v));
					  switch (generator.getOption()) {
	                    case UNIQUE_INGNORE_CASE_SENSITIVE:
	                    	String str = Character.valueOf(v).toString();
	                    	assertTrue(genStr + " :" + str, !genStr.contains(str));
	                    	genStr.add(str);
		                    break;
	                    case UNIQUE_CASE_SENSITIVE:
	                    	assertTrue(genChars + " :" + v, !genChars.contains(v));
	                    	genChars.add(v);
	                    	break;
	                    default:
		                    break;
					  }
				  }
				  break;
			  case 'A':
				  if (!constantStarted) {
					  char v = value.charAt(index);
					  assertTrue(Character.isUpperCase(v));
					  assertTrue(generator.getUpperCaseAlphabetsAsList().contains(v));
					  switch (generator.getOption()) {
						  case UNIQUE_INGNORE_CASE_SENSITIVE:
								String str = Character.valueOf(v).toString().toLowerCase();
								assertTrue(genStr + " :" + str, !genStr.contains(str));
								genStr.add(str);
							  break;
						  case UNIQUE_CASE_SENSITIVE:
							  assertTrue(genChars + " :" + v, !genChars.contains(v));
							  genChars.add(v);
							  break;
						  default:
							  break;
					  }
				  }
				  break;
			  case '9' :
				  if (!constantStarted) {
					  char v = value.charAt(index);
					  assertTrue(Character.isDigit(v));
					  assertTrue(generator.getNumericCharsAsList().contains(v));
					  switch (generator.getOption()) {
						  case UNIQUE_INGNORE_CASE_SENSITIVE:
							  assertTrue(genChars + " :" + v, !genChars.contains(v));
							  genChars.add(v);
							  break;
						  case UNIQUE_CASE_SENSITIVE:
							  assertTrue(genChars + " :" + v, !genChars.contains(v));
							  genChars.add(v);
							  break;
						  default:
							  break;
					  }
				  }
				  break;
			  case '#' :
				  if (!constantStarted) {
					  char v = value.charAt(index);
					  switch (generator.getOption()) {
						  case UNIQUE_INGNORE_CASE_SENSITIVE:
							  assertTrue(genChars + " :" + v, !genChars.contains(v));
							  genChars.add(v);
							  break;
						  case UNIQUE_CASE_SENSITIVE:
							  assertTrue(genChars + " :" + v, !genChars.contains(v));
							  genChars.add(v);
							  break;
						  default:
							  assertTrue(!(Character.isUpperCase(v) 
									  || Character.isLowerCase(v)
									  || Character.isDigit(v)));
							  assertTrue(generator.getSpecialCharsAsList().contains(v));
							  break;
					  }
				  }
				  break;
			  case RandomStringGenerator.START_CONSTANT :
				  if (constantStarted) {
					  throw new IllegalArgumentException("Special { character found without an escape character");
				  }
				  if (!constantStarted) constantStarted = true;
				  index--;
				  break;
			  case RandomStringGenerator.END_CONSTANT :
				  if (!constantStarted) throw new IllegalArgumentException("Special } character found without an escape character");
				  if (constantStarted) constantStarted = false;
				  index--;
				  break;
			  default :
				  assertTrue(c == value.charAt(index));
			}
			c = iter.next();
			index++;
		}
	}
}
