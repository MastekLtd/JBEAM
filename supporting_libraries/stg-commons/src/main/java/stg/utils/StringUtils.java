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
import java.util.Iterator;
import java.util.List;

/**
 * Collection of utility methods to manipulate String.
 * 
 * The purpose of this class is to provide special utilities that are not provided by {@link org.apache.commons.lang.StringUtils} class.
 *
 * @author Kedar Raybagkar
 * @since 1.4
 */
public class StringUtils {

	/**
	 * Default private constructor.
	 */
	private StringUtils() {
	}

	/**
	 * Splits the given string for all occurrences a space character.
	 * 
	 * If the string is null return null.
	 * If the string is empty returns an empty array of size equals zero.
	 * 
	 * @param text String to be split
	 * @return String[]
	 */
	public static String[] split(String text) {
		return split(text, ' ', CharacterIterator.DONE);
	}
	
	/**
	 * Splits the given string for all occurrences of the separator char.
	 * 
	 * If the string is null return null.
	 * If the string is empty returns an empty array of size equals zero.
	 * 
	 * @param text String to be split
	 * @param separatorChar the delimiter char
	 * @return String[]
	 */
	public static String[] split(String text, Character separatorChar) {
		return split(text, separatorChar, CharacterIterator.DONE);
	}
	
	/**
	 * Splits the given string for all the occurrences of the separator char barring those which are escaped using the escape char.
	 * 
	 * If the string is null return null.
	 * If the string is empty returns an empty array of size equals zero.
	 * 
	 * @param text String to be split
	 * @param separatorChar the delimiter char
	 * @param escapeChar the character used for escaping a delimiter
	 * @return String[]
	 * @throws IllegalArgumentException if the separatorChar and escpaeChar are same.
	 */
	public static String[] split(String text, char separatorChar, char escapeChar) {
		List<String> list = psplit(text, separatorChar, escapeChar);
		if (list == null) {
			return null;
		}
		String[] array = new String[list.size()];
		list.toArray(array);
		return array;
	}
	
	/**
	 * Joins the given tokens and forms the string by appropriately adding the space character.
	 * 
	 * Returns null if array is null or empty string if the array is empty.
	 * 
	 * @param array Tokens
	 * @return String
	 */
	public static String join(String[] array) {
		return join(array, ' ', CharacterIterator.DONE);
	}
	
	/**
	 * Joins the given tokens and forms the string by appropriately adding the sepeartorChar.
	 * 
	 * Returns null if array is null or empty string if the array is empty.
	 * 
	 * @param array Tokens
	 * @param separatorChar delimiter character
	 * @return String
	 */
	public static String join(String[] array, char separatorChar) {
		return join(array, separatorChar, CharacterIterator.DONE);
	}

	/**
	 * Joins the given tokens and forms the string by appropriately adding the sepeartorChar and the escapeChar wherever necessary.
	 * 
	 * Returns null if array is null or empty string if the array is empty.
	 * 
	 * @param array Tokens
	 * @param separatorChar delimiter character
	 * @param escapeChar escape character
	 * @return String
	 */
	public static String join(String[] array, char separatorChar, char escapeChar) {
		if (array == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String str : array) {
			StringCharacterIterator iter = new StringCharacterIterator(str);
			char c = iter.first();
			while (c != CharacterIterator.DONE) {
				if (c == separatorChar) {
					if (escapeChar != CharacterIterator.DONE) {
						sb.append(escapeChar);
					}
				}
				sb.append(c);
				c = iter.next();
			}
			sb.append(separatorChar);
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	/**
	 * Returns the count of tokens in the given string separated by a space.
	 * @param text string
	 * @return int count
	 */
	public static int countTokens(String text) {
		return countTokens(text, ' ', CharacterIterator.DONE);
	}

	/**
	 * Returns the count of tokens in the given string separated by the separator char.
	 * 
	 * Return zero if the string is null and 1 if empty.
	 * 
	 * @param text
	 * @param separatorChar
	 * @return int count
	 */
	public static int countTokens(String text, char separatorChar) {
		return countTokens(text, separatorChar, CharacterIterator.DONE);
	}

	/**
	 * Returns the number of tokens in the given string separated with a separator char.
	 * 
	 * The separator escaped with the escape char is considered a part of the token.
	 * 
	 * Return zero if the string is null and 1 if empty.
	 * 
	 * @param text String to be counted for the delimiters.
	 * @param separatorChar Separator used as a delimiter.
	 * @param escapeChar Escape character used for escaping delimiters.
	 * @return int count.
	 */
	public static int countTokens(String text, char separatorChar, char escapeChar) {
		if (text == null) {
			return 0;
		}
		return StringUtils.split(text, separatorChar, escapeChar).length;
	}
	
	/**
	 * Extracts the <i>n</i>th token from the given string after splitting by the given separator char.
	 * Example: The String "Kedar#Raybagkar/#Test" with the separator char # and escape char as / then token 2 will return
	 * null.
	 * 
	 * @param text
	 * @param tokenNumber
	 * @return String
	 */
	public static String extractTokenAt(String text, int tokenNumber) {
		return extractTokenAt(text, ' ', CharacterIterator.DONE, tokenNumber);
	}
	
	/**
	 * Extracts the <i>n</i>th token from the given string after splitting by the given separator char.
	 * Example: The String "Kedar#Raybagkar/#Test" with the separator char # and escape char as / then token 2 will return
	 * "Raybagkar/".
	 * 
	 * @param text String to be tokenized.
	 * @param seperatorChar used as a delimiter char.
	 * @param tokenNumber
	 * @return String
	 */
	public static String extractTokenAt(String text, char seperatorChar, int tokenNumber) {
		return extractTokenAt(text, seperatorChar, CharacterIterator.DONE, tokenNumber);
	}
	
	/**
	 * Extracts the <i>n</i>th token from the given string after splitting by the given separator char and the escape char.
	 * Example: The String "Kedar#Raybagkar/#Test" with the separator char # and escape char as / then token 2 will return
	 * "Raybagkar/#Test".
	 * @param text String from which the token is to be extracted.
	 * @param seperatorChar used as a delimiter.
	 * @param escapeChar Escape character used for escaping delimiters.
	 * @param tokenNumber Token Number.
	 * @return String
	 */
	public static String extractTokenAt(String text, char seperatorChar, char escapeChar, int tokenNumber) {
		String[] array = StringUtils.split(text, seperatorChar, escapeChar);
		if (array == null) {
			return null;
		}
		if (array.length < (tokenNumber)) {
			return null;
		}
		return array[tokenNumber-1];
	}
	
	/**
	 * Extracts all tokens found between separatorCharStart and separatorCharEnd.
	 * @param text from which the tokens are to be extracted.
	 * @param separatorCharStart start of token identifier
	 * @param separatorCharEnd end of token identifier
	 * @param escapeChar escape character to be used for separatorCharStart
	 * @return list of tokens
	 */
	public static String[] extractAllTokens(String text, char separatorCharStart, char separatorCharEnd, char escapeChar, boolean alongWithSeparators) {
	    return pExtract(text, separatorCharStart, separatorCharEnd, escapeChar, alongWithSeparators).toArray(new String[] {});
	}
	
	/**
	 * Extracts all tokens found between separatorCharStart and separatorCharEnd.
	 * @param text from which the tokens are to be extracted.
	 * @param separatorCharStart start of token identifier
	 * @param separatorCharEnd end of token identifier
	 * @param escapeChar escape character to be used for separatorCharStart
	 * @return list of tokens
	 */
	public static List<String> extractAllTokensAsList(String text, char separatorCharStart, char separatorCharEnd, char escapeChar, boolean alongWithSeparators) {
	    return pExtract(text, separatorCharStart, separatorCharEnd, escapeChar, alongWithSeparators);
	}
	
	public static String replaceEach(String text, String[] searchList, String[] replacementList) {
	    return org.apache.commons.lang.StringUtils.replaceEach(text, searchList, replacementList);
	}
	
	/**
	 * Extracts all tokens found between separatorCharStart and separatorCharEnd.
	 * @param text from which the tokens are to be extracted.
	 * @param separatorCharStart start of token identifier
	 * @param separatorCharEnd end of token identifier
	 * @param escapeChar escape character to be used for separatorCharStart
	 * @return list of tokens
	 */
	public static String extractTokensAt(String text, char separatorCharStart, char separatorCharEnd, char escapeChar, int tokenNumber, boolean alongWithSeparators) {
	    List<String> list = pExtract(text, separatorCharStart, separatorCharEnd, escapeChar, alongWithSeparators);
	    return list.get(tokenNumber);
	}
	
	/**
	 * Returns the iterator for all the tokens that are delimited by space.
	 * @param text String to be tokenized.
	 * @return Iterator<String>
	 */
	public static Iterator<String> listTokens(String text) {
		return listTokens(text, ' ', CharacterIterator.DONE);
	}

	/**
	 * Returns the iterator for all the tokens that are delimited by a separator char.
	 * @param text String to be tokenized.
	 * @param separatorChar  to be used as the delimiter.
	 * @return Iterator<String>
	 */
	public static Iterator<String> listTokens(String text, char separatorChar) {
		return listTokens(text, separatorChar, CharacterIterator.DONE);
	}
	
	/**
	 * Returns the iterator for all the tokens that are delimited by a separator char by taking into considerations the escaped delimiters.
	 * 
	 * @param text String to be tokenized.
	 * @param separatorChar used as the delimiter.
	 * @param escapeChar used to escape delimiter.
	 * @return Iterator<String>
	 */
	public static Iterator<String> listTokens(String text, char separatorChar, char escapeChar) {
		return psplit(text, separatorChar, escapeChar).listIterator();
	}
	
	/**
	 * Returns the iterator for all the tokens that are delimited by a separator char by taking into considerations the escaped delimiters.
	 * 
	 * @param text String to be tokenized.
	 * @param separatorCharStart start separator character.
	 * @param separatorCharEnd separator end.
	 * @param escapeChar used to escape delimiter.
	 * @param alongWithSeparators returns the separator characters as well.
	 * @return Iterator<String>
	 */
	public static Iterator<String> listTokens(String text, char separatorCharStart, char separatorCharEnd, char escapeChar, boolean alongWithSeparators) {
	    return pExtract(text, separatorCharStart, separatorCharEnd, escapeChar, alongWithSeparators).listIterator();
	}
	
	/**
	 * Private method that performs split and returns the tokens in the form of List.
	 * 
	 * @param text String to be tokenized.
	 * @param separatorChar used as a delimiter.
	 * @param escapeChar used to escape delimiter.
	 * @return List.
	 */
	private static List<String> psplit(String text, char separatorChar, char escapeChar) {
		if (text == null) {
			return new ArrayList<String>();
		}
		if (separatorChar == escapeChar) {
			throw new IllegalArgumentException("Separator Char and Escape Char must be different.");
		}
		StringCharacterIterator iter = new StringCharacterIterator(text);
		char c = iter.first();
		ArrayList<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		while (c != CharacterIterator.DONE) {
			if (c == escapeChar) {
				c = iter.next();
				if (c != CharacterIterator.DONE) {
					sb.append(c);
				} else {
					throw new IllegalArgumentException("Escape Character found without the character to be ignored.");
				}
			} else if (c == separatorChar) {
				list.add(sb.toString());
				sb.delete(0, sb.length());
			} else {
				sb.append(c);
			}
			c = iter.next();
		}
		if (sb.length() > -1) {
			list.add(sb.toString());
		}
		return list;
	}
	
	/**
	 * Private method to extract the tokens as list.
	 * @param text
	 * @param separatorCharStart
	 * @param separatorCharEnd
	 * @param escapeChar
	 * @param includeSeparators
	 * @return list of strings
	 */
	private static List<String> pExtract(String text, final char separatorCharStart, final char separatorCharEnd, final char escapeChar, final boolean includeSeparators) {
	    if (text == null) {
	        return new ArrayList<String>();
	    }
	    if (separatorCharStart == separatorCharEnd) {
	        return psplit(text, separatorCharStart, escapeChar);
	    }
	    boolean escapeAndSeparatorCharAreSame = (separatorCharStart == escapeChar);
	    StringCharacterIterator iter = new StringCharacterIterator(text);
	    char c = iter.first();
	    ArrayList<String> list = new ArrayList<String>();
	    StringBuilder sb = new StringBuilder();
	    boolean toBeAppended = false;
	    while (c != CharacterIterator.DONE) {
	        if (c == escapeChar && !escapeAndSeparatorCharAreSame) {
	            c = iter.next();
	            if (c == CharacterIterator.DONE) {
	                throw new IllegalArgumentException("Escape Character found without the character to be ignored.");
	            }
	        } else if (c == separatorCharEnd) {
	            if (toBeAppended) {
	                if (includeSeparators) {
	                    sb.append(c);
	                }
	                list.add(sb.toString());
	                sb.delete(0, sb.length());
	                toBeAppended = false;
	            }
	        } else if (c == separatorCharStart){
                c = iter.next();
                if (c == CharacterIterator.DONE) {
                    throw new IllegalArgumentException("Separator Character Start found without any enclosing text.");
                }
                toBeAppended = true;
                if (c == separatorCharStart && escapeAndSeparatorCharAreSame) {
                    toBeAppended = false;
                    c = iter.next();
                }
                if (toBeAppended && includeSeparators) {
                    sb.append(separatorCharStart);
                }
	        }
	        if (toBeAppended) {
	            sb.append(c);
	        }
	        c = iter.next();
	    }
	    return list;
	}
	
	/**
	 * A whole word is identified as a word that does not have a valid {@link Character#isJavaIdentifierPart(char)} before or after the searchStr.
	 * 
	 * @param text in which the search and replace is needed. 
	 * @param word to be searched for
	 * @param newWord to be replaced with
	 * @return replaced text.
	 */
	public static String replaceAllWholeWord(String text, String word, String newWord) {
	    StringBuilder sb = new StringBuilder(text);
	    int index = 0;
	    while (sb.indexOf(word, index) > -1) {
	        index = sb.indexOf(word, index);
	        boolean notAWholeWord = false;
	        if (index > 0) {
	            char c = sb.charAt(index - 1);
	            if (Character.isJavaIdentifierPart(c)) {
	                notAWholeWord = true;
	            }
	            if (index + word.length() < sb.length()) {
	                c = sb.charAt(index + word.length());
	                if (Character.isJavaIdentifierPart(c)) {
	                    notAWholeWord = true;
	                }
	            }
	            if (!notAWholeWord) {
	                sb.replace(index, index + word.length(), newWord);
	            }
	        } else if (index == 0) {
	            if (index + word.length() < sb.length()) {
	                char c = sb.charAt(index + word.length());
	                if (Character.isJavaIdentifierPart(c)) {
	                    notAWholeWord = true;
	                }
	            }
	            if (!notAWholeWord) {
	                sb.replace(0, word.length(), newWord);
	            }
	        }
	        index++;
	    }
	    return sb.toString();
	}
	
    /**
     * A whole word is identified as a word that does not have a valid {@link Character#isJavaIdentifierPart(char)} before or after the searchStr.
     * 
     * @param text in which the search and replace is needed. 
     * @param word to be searched for
     * @return total count of whole words found within the text.
     */
    public static int countAllWholeWord(String text, String word) {
        StringBuilder sb = new StringBuilder(text);
        int count = 0;
        int index = 0;
        while (sb.indexOf(word, index) > -1) {
            index = sb.indexOf(word, index);
            boolean notAWholeWord = false;
            if (index > 0) {
                char c = sb.charAt(index - 1);
                if (Character.isJavaIdentifierPart(c)) {
                    notAWholeWord = true;
                }
                if (index + word.length() < sb.length()) {
                    c = sb.charAt(index + word.length());
                    if (Character.isJavaIdentifierPart(c)) {
                        notAWholeWord = true;
                    }
                }
                if (!notAWholeWord) {
                    count++;
                }
            } else if (index == 0) {
                if (index + word.length() < sb.length()) {
                    char c = sb.charAt(index + word.length());
                    if (Character.isJavaIdentifierPart(c)) {
                        notAWholeWord = true;
                    }
                }
                if (!notAWholeWord) {
                    count++;
                }
            }
            index++;
        }
        return count;
    }
	
	public static void main(String[] args) throws Throwable {
    }
	
	
}
