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
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Random string generator class that generates random string based on the given format.
 *
 * Valid characters are :
 * <li>a<dt>For lower case alphabets.
 * <li>A<dt>For upper case alphabets.
 * <li>9<dt>For numbers.
 * <li>#<dt>For special characters.
 * 
 * The format characters can be repeated in whatever sequence. 
 * The random string generated will have the same length as that of the format.
 * To escape character is / (slash). So to include a / sign as a constant prefix it with a escape character for example //.
 * To add a constant in between the format, place the constant string within open and close brace brackets {}. An escape
 * character within a constant should be escaped for example {ab//cd} will result into ab/cd. So a format say A{z}9#aaA#
 * will always have z as the second character wherein A, 9, # and a will be replaced with randomized Upper Case, Number, 
 * Special Char and a lower case character respectively.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class RandomStringGenerator {
	
	public static enum OPTION {
		/**
		 * Option to generate a non unique (may repeat characters) random string.
		 */
		NON_UNIQUE,
		/**
		 * Option to generate unique but may have case sensitive duplication of characters.
		 * Such as c C or a A.
		 */
		UNIQUE_CASE_SENSITIVE, 
		/**
		 * Option to generate a unique case sensitive random string.
		 * Guarantees that the generated string will not have any duplicate (case sensitive) characters. 
		 */
		UNIQUE_INGNORE_CASE_SENSITIVE;
	}

	public static final char START_CONSTANT = '{';
	public static final char END_CONSTANT = '}';
	public static final char ESCAPE = '/';
	public static final char UPPER_CASE = 'A';
	public static final char LOWER_CASE = 'a';
	public static final char NUMBER = '9';
	public static final char SPECIAL = '#';

	private ArrayList<Character> specialCharsList = new ArrayList<Character>();
	private ArrayList<Character> lowerCaseCharsList = new ArrayList<Character>();
	private ArrayList<Character> upperCaseCharsList = new ArrayList<Character>();
	private ArrayList<Character> numericCharsList = new ArrayList<Character>();
	
	private String format;
	private OPTION option;


	/**
	 * Constructs the class using a default format <code>Aaa#999#AaAAaa</code>.
	 */
	public RandomStringGenerator() {
		this("Aaa#999#AaAAaa", OPTION.NON_UNIQUE);
	}

	/**
	 * Constructs the class using a default format <code>Aaa#999#AaAAaa</code>.
	 * @param option One of the option from {@link OPTION}
	 */
	public RandomStringGenerator(OPTION option) {
		this("Aaa#999#AaAAaa", option);
	}
	
	/**
	 * Constructs the class for the given format with the default non unique option.
	 * 
	 * @param format to be used for generating random 
	 */
	public RandomStringGenerator(String format) {
		this(format, OPTION.NON_UNIQUE);
	}

	/**
	 * Constructs the class for the given format.
	 * 
	 * @param format
	 * @param option
	 */
	public RandomStringGenerator(String format, OPTION option) {
		initialize();
		this.option = option;
		validate(format);
		this.format = format;
	}
	
	/**
	 * Sets the special character array.
	 * The default character array contains  ~,`,!,@,#,$,^,.,: in this sequence.
	 * 
	 * @param specialCharArray
	 */
	public void setSpecialChars(char[] specialCharArray) {
		if (specialCharArray == null) {
			throw new NullPointerException();
		}
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i=0; i<specialCharArray.length; i++) {
			if (Character.isLetter(specialCharArray[i])) {
				throw new IllegalArgumentException("Must be a special character. Special chracter any character other than alpahbets and numbers");
			}
			if (Character.isDigit(specialCharArray[i])) {
				throw new IllegalArgumentException("Must be a special character. Special chracter any character other than alpahbets and numbers");
			}
			list.add(specialCharArray[i]);
		}
		specialCharsList.clear();
		specialCharsList.addAll(list);
	}
	
	/**
	 * Sets the lower case alphabets array.
	 * The default lower case character array contains a to z characters.
	 *  
	 * @param lowerCaseAlphabetsArray
	 */
	public void setLowerCaseAlphabets(char[] lowerCaseAlphabetsArray) {
		if (lowerCaseAlphabetsArray == null) {
			throw new NullPointerException();
		}
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i=0; i<lowerCaseAlphabetsArray.length; i++) {
			if (!Character.isLowerCase(lowerCaseAlphabetsArray[i])) {
				throw new IllegalArgumentException("Must be a lower case alphabet");
			}
			list.add(lowerCaseAlphabetsArray[i]);
		}
		lowerCaseCharsList.clear();
		lowerCaseCharsList.addAll(list);
	}
	
	/**
	 * Sets the upper case alphabets array.
	 * The default upper case character array contains A to Z characters.
	 * 
	 * @param upperCaseAlphabetsArray
	 */
	public void setUpperCaseAlphabets(char[] upperCaseAlphabetsArray) {
		if (upperCaseAlphabetsArray == null) {
			throw new NullPointerException();
		}
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i=0; i<upperCaseAlphabetsArray.length; i++) {
			if (!Character.isUpperCase(upperCaseAlphabetsArray[i])) {
				throw new IllegalArgumentException("Must be an upper case alphabet");
			}
			list.add(upperCaseAlphabetsArray[i]);
		}
		upperCaseCharsList.clear();
		upperCaseCharsList.addAll(list);
	}
	
	/**
	 * Defines the numeric characters.
	 * The default set of numeric characters are 0 to 9.
	 * @param numericArray
	 */
	public void setNumericChars(char[] numericArray) {
		if (numericArray == null) {
			throw new NullPointerException();
		}
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i=0; i<numericArray.length; i++) {
			if (!Character.isDigit(numericArray[i])) {
				throw new IllegalArgumentException("Must be a digit");
			}
			list.add(numericArray[i]);
		}
		numericCharsList.clear();
		numericCharsList.addAll(list);
	}
	
	/**
	 * Static method is given for simplicity.
	 * Internally it will do the following.
	 * <code> RandomStringGenerator rsg = new RandomStringGenerator(format); </code>
	 * <code> rsg.generate();</code>
	 * 
	 * @param format
	 * @return String
	 */
	public static String generateRandom(String format) {
		return new RandomStringGenerator(format).generate();
	}
	
	/**
	 * Static method is given for simplicity.
	 * Internally it will do the following.
	 * <code> RandomStringGenerator rsg = new RandomStringGenerator(); </code>
	 * <code> rsg.generateFormat(length);</code>
	 *
	 * @param length of the format
	 * @return String
	 */
	public static String generateRandomFormat(int length) {
		return new RandomStringGenerator().generateFormat(length);
	}
	
	/**
	 * Generates the random string
	 * Internally it will do the following.
	 * <code> RandomStringGenerator rsg = new RandomStringGenerator(format, option); </code>
	 * <code> rsg.generate();</code>
	 * 
	 * @param format
	 * @param option
	 * @return String
	 */
	public static String generateRandom(String format, OPTION option) {
		RandomStringGenerator rsg = new RandomStringGenerator(format, option);
		return rsg.generate();
	}
	
	/**
	 * Static method is given for simplicity.
	 * Internally it will do the following.
	 * <code> RandomStringGenerator rsg = new RandomStringGenerator(); </code>
	 * <code> rsg.generateRandom();</code>
	 * 
	 * @return String
	 */
	public static String generateRandom() {
		return new RandomStringGenerator().generate();
	}
	
	/**
	 * Static method is given for simplicity.
	 * Internally it will do the following.
	 * <code> RandomStringGenerator rsg = new RandomStringGenerator(format); </code>
	 * <code> rsg.generateRandom();</code>
	 * 
	 * @param option {@link OPTION}
	 * @return String
	 */
	public static String generateRandom(OPTION option) {
		RandomStringGenerator rsg = new RandomStringGenerator(option);
		return rsg.generate();
	}

	
	/**
	 * Returns a random string of a given length.
	 *  
	 * @param length
	 * @return String
	 */
	public static String generateRandom(int length) {
		String format = RandomStringGenerator.generateRandomFormat(length);
		return RandomStringGenerator.generateRandom(format);
	}


	/**
	 * Returns the special characters.
	 * 
	 * @return the specialChars
	 */
	public char[] getSpecialChars() {
		return toCharArray(specialCharsList);
	}

	/**
	 * Returns the lower case alphabets.
	 * 
	 * @return the lowerCaseAlphabets
	 */
	public char[] getLowerCaseAlphabets() {
		return toCharArray(lowerCaseCharsList);
	}

	/**
	 * Returns the upper case alphabets.
	 * 
	 * @return the upperCaseAlphabets
	 */
	public char[] getUpperCaseAlphabets() {
		return toCharArray(upperCaseCharsList);
	}

	/**
	 * Returns the numeric characters.
	 * 
	 * @return the numericChars
	 */
	public char[] getNumericChars() {
		return toCharArray(numericCharsList);
	}

	/**
	 * Returns the special characters.
	 * 
	 * @return the specialChars
	 */
	public List<Character> getSpecialCharsAsList() {
		return cloneList(specialCharsList);
	}
	
	/**
	 * Returns the lower case alphabets.
	 * 
	 * @return the lowerCaseAlphabets
	 */
	public List<Character> getLowerCaseAlphabetsAsList() {
		return cloneList(lowerCaseCharsList);
	}
	
	/**
	 * Returns the upper case alphabets.
	 * 
	 * @return the upperCaseAlphabets
	 */
	public List<Character> getUpperCaseAlphabetsAsList() {
		return cloneList(upperCaseCharsList);
	}
	
	/**
	 * Returns the numeric characters.
	 * 
	 * @return the numericChars
	 */
	public List<Character> getNumericCharsAsList() {
		return cloneList(numericCharsList);
	}
	
	/**
	 * Generates the random string as per the format.
	 * 
	 * @return String
	 */
	public String generate() {
		CharacterIterator iter = new StringCharacterIterator(format);
		char c = iter.first();
		StringBuilder sb = new StringBuilder();
		List<Character> tempLowerCaseCharsList = cloneList(lowerCaseCharsList);
		List<Character> tempUpperCaseCharsList = cloneList(upperCaseCharsList);
		List<Character> tempNumericCharsList = cloneList(numericCharsList);
		List<Character> tempSpecialCharsList = cloneList(specialCharsList);
		
		boolean constantStarted = false;
		while (c != CharacterIterator.DONE) {
			switch (c) {
			  case ESCAPE:
				  c = iter.next();
				  if (c == CharacterIterator.DONE) {
					  throw new IllegalArgumentException("Invalid format, escape character found without any associated character that was to be escaped.");
				  }
				  sb.append(c);
				  break;
			  case LOWER_CASE :
				  if (!constantStarted) {
					  switch (option) {
	                    case NON_UNIQUE:
	                    	sb.append(RandomStringUtils.random(1, toCharArray(lowerCaseCharsList)));
		                    break;
	                    case UNIQUE_CASE_SENSITIVE:
	                    	sb.append(generateUniqueCharacter(c, tempLowerCaseCharsList));
	                        break;
	                    default:
	                    	String str = generateUniqueCharacter(c, tempLowerCaseCharsList);
	                    	sb.append(str);
                			if (!tempUpperCaseCharsList.contains(Character.valueOf(str.toUpperCase().charAt(0)))) {
                				System.out.println(tempLowerCaseCharsList + " \t " + str);
                			}
	                    	if (!tempUpperCaseCharsList.remove(Character.valueOf(str.toUpperCase().charAt(0)))) { //remove it from the upper case char set.
                				System.out.println("Problem unable to remove " + tempUpperCaseCharsList + "\t" + str);
	                    	}
		                    break;
                    }
				  } else {
					  sb.append(c);
				  }
				  break;
			  case UPPER_CASE:
				  if (!constantStarted) {
					  switch (option) {
	                    case NON_UNIQUE:
	                    	sb.append(RandomStringUtils.random(1, toCharArray(upperCaseCharsList)));
		                    break;
	                    case UNIQUE_CASE_SENSITIVE:
	                    	sb.append(generateUniqueCharacter(c, tempUpperCaseCharsList));
	                        break;
	                    default:
                    		String str = generateUniqueCharacter(c, tempUpperCaseCharsList);
                			sb.append(str);
                			if (!tempLowerCaseCharsList.contains(Character.valueOf(str.toLowerCase().charAt(0)))) {
                				System.out.println(tempLowerCaseCharsList + " \t " + str);
                			}
                			if (!tempLowerCaseCharsList.remove(Character.valueOf(str.toLowerCase().charAt(0)))) {
                				System.out.println("Problem unable to remove " + tempLowerCaseCharsList + "\t" + str);
                			}
		                    break;
                    }
				  } else {
					  sb.append(c);
				  }
				  break;
			  case NUMBER :
				  if (!constantStarted) {
					  switch (option) {
						  case NON_UNIQUE:
							  sb.append(RandomStringUtils.random(1, toCharArray(numericCharsList)));
							  break;
		                    default:
		                    	sb.append(generateUniqueCharacter(c, tempNumericCharsList));
		                        break;
					  }
				  } else {
					  sb.append(c);
				  }
				  break;
			  case SPECIAL :
				  if (!constantStarted) {
					  switch (option) {
						  case NON_UNIQUE:
							  sb.append(RandomStringUtils.random(1, toCharArray(specialCharsList)));
							  break;
		                    default:
		                    	sb.append(generateUniqueCharacter(c, tempSpecialCharsList));
		                        break;
					  }
				  } else {
					  sb.append(c);
				  }
				  break;
			  case START_CONSTANT :
				  if (constantStarted) {
					  throw new IllegalArgumentException("Special { character found without an escape character");
				  }
				  if (!constantStarted) constantStarted = true;
				  break;
			  case END_CONSTANT :
				  if (!constantStarted) throw new IllegalArgumentException("Special } character found without an escape character");
				  if (constantStarted) constantStarted = false;
				  break;
			  default :
				  sb.append(c);
			}
			c = iter.next();
		}
		return sb.toString();
	}
		
	/**
	 * Returns a unique character from the given character list.
	 * The generated character is then removed from the list.
	 * 
	 * @param c
	 * @return list
	 */
	private String generateUniqueCharacter(char c, List<Character> list) {
		if (list.size() == 0) {
			StringBuilder ssb = new StringBuilder();
			ssb.append("Format supplied ");
			ssb.append(format);
			ssb.append(" has more number of ");
			ssb.append(c);
			ssb.append(" than the supplied ");
			ssb.append(decode(c));
			throw new IllegalArgumentException(ssb.toString());
		}
		String str = RandomStringUtils.random(1, toCharArray(list));
		list.remove(Character.valueOf(str.charAt(0)));
		return str;
	}
	
	/**
	 * Returns and stores the random format for the specific length.
	 * This method generate a {@link OPTION#NON_UNIQUE} format.
	 * @param length
	 */
	public String generateFormat(int length) {
		List<Character> cList = new ArrayList<Character>();
		if (getSpecialCharsAsList().size() > 0) {
			cList.add(SPECIAL);
		}
		if (getUpperCaseAlphabetsAsList().size() > 0) {
			cList.add(UPPER_CASE);
		}
		if (getLowerCaseAlphabetsAsList().size() > 0) {
			cList.add(LOWER_CASE);
		}
		if (getNumericCharsAsList().size() > 0) {
			cList.add(NUMBER);
		}
		if (cList.size() == 0) {
			throw new IllegalArgumentException("At least one format must be supplied.");
		}
		return RandomStringUtils.random(length, toCharArray(cList));
	}
	
	/**
	 * Initializes all the character arrays with default values.
	 */
	private void initialize() {
		for (char c='a'; c<='z'; c++){
			lowerCaseCharsList.add(c);
		}
		for (char c='A'; c<='Z'; c++){
			upperCaseCharsList.add(c);
		}
		for (char c='0'; c<='9'; c++){
			numericCharsList.add(c);
		}
		char[] chars = new char[] {'~', '`', '!', '@', '$', '^', '.', ':'};
		for (char c : chars){
			specialCharsList.add(c);
		}
	}
	
	/**
	 * Validates the given format.
	 * Valid characters are :
	 * <li>a<dt>For lower case alphabets.
	 * <li>A<dt>For upper case alphabets.
	 * <li>9<dt>For numbers.
	 * <li>#<dt>For special characters.
	 * The format characters can be repeated in whatever sequence. 
	 * The random string generated will have the same length as that of the format.
	 * 
	 * @param format
	 */
	private void validate(String format) {
		CharacterIterator iter = new StringCharacterIterator(format);
		char c = iter.first();
		boolean constantStarted = false;
		while (c != CharacterIterator.DONE) {
			switch (c) {
			case ESCAPE:
				c = iter.next();
				if (c == CharacterIterator.DONE) {
					throw new IllegalArgumentException(
							"Invalid format! Escape character found without any associated character that was to be escaped.");
				}
				break;
			case LOWER_CASE:
				break;
			case UPPER_CASE:
				break;
			case NUMBER:
				break;
			case SPECIAL:
				break;
			case START_CONSTANT:
				if (!constantStarted)
					constantStarted = true;
				break;
			case END_CONSTANT:
				if (constantStarted)
					constantStarted = false;
				break;
			default:
				if (!constantStarted)
					throw new IllegalArgumentException(
							"Invalid format character found '" + c + "'");
			}
			c = iter.next();
		}
	}

	/**
	 * Returns the length of the to be generated random string.
	 * 
	 * @return int
	 */
	public int getReturnLength() {
		return format.length();
	}

	/**
	 * Returns the value of OPTION
	 * @return the option
	 */
	public OPTION getOption() {
		return option;
	}
	
	
	private char[] toCharArray(List<Character> list) {
		char[] characters = new char[list.size()];
		int index = 0;
		for (Character character : list) {
			characters[index++] = character;
		}
		return characters;
	}
	
	private String decode(char c) {
		switch (c) {
		  case LOWER_CASE :
			  return "lower case alphabets " + lowerCaseCharsList.toString();
		  case UPPER_CASE:
			  return "upper case alphabets " + upperCaseCharsList.toString();
		  case NUMBER :
			  return "numeric characters " + numericCharsList.toString();
		  case SPECIAL :
			  return "special characters " + specialCharsList.toString();
		  default :
			  return "un-recognized characters";
		}
	}

	/**
	 * Returns a new list by add all the values from the given list.
	 * @param list from which, the values are to be read
	 * @return List<Character>
	 */
	private List<Character> cloneList(List<Character> list) {
		List<Character> rList = new ArrayList<Character>();
		rList.addAll(list);
		return rList;
	}
}
