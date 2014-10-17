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

// This file was taken from another project not as per the Coding Standards.
package stg.utils;

import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Special String uses methods given in StringBuffer class.
 */

public class SpecialString {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 1                 $";
    

    private boolean ignore_case = false;
    private String escCharStart = "^^", escCharEnd = "^^";
    private boolean exclude_escape_character = true, isEscCharOne = true;
    private StringBuffer st;
    private int escape_char_counter;

    /**
     * Constructs an Object of SpecialString.
     * 
     * @param string
     *            String
     */
    public SpecialString(String string) {
        st = new StringBuffer(string);
    }

    /**
     * Substitutes string within the ESCAPE_CHARACTERs with newstring. <BR>
     * <UL>
     * <LI> If ExcludeEscapeCharacter == true then the text enclosed by
     * ESCAPE_CHARACTERs including the ESCAPE_CHARACTERs are replaced with the
     * new string.
     * <LI> If ExcludeEscapeCharacter == false then the text enclosed by
     * ESCAPE_CHARACTER excluding the ESCAPE_CHARACTERs are replaced with the
     * new string. And the Escape_Counter is incremented by 2.
     * </UL>
     * <BR>
     * 
     * @param newstr
     *            String to be replace with.<BR>
     * @see #setExcludeEscape( boolean )
     */
    public void substitute(String newstr) {
        substitute(extract(), newstr);
    }

    /**
     * Substitute oldstr with newstring. <BR>
     * <UL>
     * <LI> If ExcludeEscapeCharacter == true then the text enclosed by
     * ESCAPE_CHARACTERs including the ESCAPE_CHARACTERs are replaced with the
     * new string.
     * <LI> If ExcludeEscapeCharacter == false then the text enclosed by
     * ESCAPE_CHARACTER excluding the ESCAPE_CHARACTERs are replaced with the
     * new string. And the Escape_Counter is incremented by 2.
     * </UL>
     * <BR>
     * 
     * @param oldstr
     *            String which is to be replaced
     * @param newstr
     *            String which is to be replaced with.<BR>
     * @see #setExcludeEscape( boolean )
     */
    public void substitute(String oldstr, String newstr) {
        StringBuffer x = new StringBuffer().append(st);
        int pos;
        if (ignore_case) {
            pos = x.toString().toLowerCase(Locale.US).indexOf(oldstr.toLowerCase(Locale.US));
        } else {
            pos = x.toString().indexOf(oldstr);
        }
        if (exclude_escape_character) {
            st = new StringBuffer(x.replace(pos, pos + oldstr.length(), newstr).toString());
        } else {
            st = new StringBuffer(x.replace(pos + escCharStart.length(),
                    pos + oldstr.length() - escCharEnd.length(), newstr)
                    .toString());
            if (isEscCharOne)
                escape_char_counter += 2;
            else
                escape_char_counter += 1;
        }
    }

    /**
     * Substitute oldstr with newstring from a given index. <BR>
     * <UL>
     * <LI> This does not work for ESCAPE_CHARACTERs
     * </UL>
     * <BR>
     * 
     * @param oldstr
     *            String which is to be replaced from the given index.
     * @param newstr
     *            String which is to be replaced with.<BR>
     * @param afterIndex
     *            Index from where the oldstr is to be searched & replaced with
     *            newstr
     */
    public void substitute(String oldstr, String newstr, int afterIndex) {
        StringBuffer x = new StringBuffer().append(st);
        int pos;
        if (ignore_case) {
            pos = x.toString().toLowerCase(Locale.US).indexOf(oldstr.toLowerCase(Locale.US),
                    afterIndex - 1);
        } else {
            pos = x.toString().indexOf(oldstr, afterIndex - 1);
        }
        st = new StringBuffer(x.replace(pos, pos + oldstr.length(), newstr).toString());
    }

    /**
     * Extract string placed inside an ESCAPE_CHARACTERs including
     * ESCAPE_CHARACTERs for the first occurance. By Default ESCAPE_CHARACTERs
     * equals "^^"
     * 
     * @see #setEscapeChar( String )
     * @return String Placed inside the ESCAPE_CHARACTER.
     */
    public String extract() {
        if (exclude_escape_character) {
            return extract(1);
        } else {
            return extract(escape_char_counter);
        }
    }

    public String extract(boolean dummy) {
        if (exclude_escape_character) {
            return extract(1, dummy);
        } else {
            return extract(escape_char_counter);
        }
    }

    /**
     * Extract string placed inside an ESCAPE_CHARACTERs after the nth
     * occurance.
     * <UL>
     * <LI> Please note that the n must be in multiples of 2 if setExcludeEscape
     * is set to false.
     * </UL>
     * <BR>
     * By Default ESCAPE_CHARACTER equals "^^".
     * 
     * @param n
     * @return String
     * @see #setEscapeChar( String )
     */
    public String extract(int n) {
        int pos1, pos2;
        try {
            pos1 = indexOf(escCharStart, n);
            if (pos1 < 0)
                return null;
            pos2 = indexOf(escCharEnd, ((isEscCharOne) ? n + 1 : n));
            if (pos2 < 0)
                return null;
            return st.substring(pos1, pos2 + escCharEnd.length());
        } catch (Exception e) {
        }
        return null;
    }

    public String extract(int n, boolean dummyNotUsed) {
        int pos1, pos2;
        try {
            pos1 = indexOf(escCharStart, n);
            if (pos1 < 0)
                return null;
            pos2 = indexOf(escCharEnd, ((isEscCharOne) ? n + 1 : n));
            if (pos2 < 0)
                return null;
            return st.substring(pos1 + escCharStart.length(), pos2);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Returns the position of the searchstr for nth occurance from the
     * begining.
     * 
     * @param searchstr
     *            to be searched within SpecialString object.
     * @param n
     *            starting after nth occurance.
     * @return position within the string for the nth Occurance.
     *         <UL>
     *         <LI> -1 is returned if searchstr is not found <B><I>Otherwise</i></B>
     *         the index position is passed.
     *         </UL>
     */
    public int indexOf(String searchstr, int n) {
        int pos = -2;
        for (int i = 0; i < n; i++) {
            if (ignore_case) {
                pos = st.toString().toLowerCase(Locale.US).indexOf(searchstr.toLowerCase(Locale.US),
                        pos + 1);
            } else
                pos = st.toString().indexOf(searchstr, pos + 1);
        }
        return pos;
    }

    /**
     * Returns the position of the searchstr for nth occurance from the <B>End</B>
     * where formend must always be -1. If found returns the position else
     * returns -1.
     * 
     * @return position within the string for the nth Occurance from the <B>End</B>.
     *         <UL>
     *         <LI> -1 is returned if searchstr is not found <B><I>Otherwise</i></B>
     *         the index position is passed.
     *         </UL>
     * @param searchstr
     * @param n
     *            Occurance
     * @param fromend
     */
    public int indexOf(String searchstr, int n, int fromend) {
        if (fromend != -1)
            return -1;
        int pos = indexOf(searchstr, occurs(searchstr) - n + 1);
        return pos;
    }

    /**
     * Returns the total number of occurances of the ESCAPE_CHARACTER_START.
     * 
     * @return occurance 0(Zero) if the character is not found.
     */
    public int occurs() {
        int no = occurs(escCharStart);
        // if( isEscCharOne ) no = no/2; //For Backward Compatibility.
        return no;
    }

    /**
     * Returns the total number of occurrences of the Search String.
     * 
     * @param searchstr
     *            String to be searched for.
     * @return occurrence 0(Zero) if the character is not found.
     */
    public int occurs(String searchstr) {
        int pos = -2, cnt = 0;
        while (true) {
            if (ignore_case) {
                pos = st.toString().toLowerCase(Locale.US).indexOf(searchstr.toLowerCase(Locale.US),
                        pos + 1);
            } else {
                pos = st.toString().indexOf(searchstr, pos + 1);
            }
            if (pos < 0)
                break;
            cnt++;
        }
        return cnt;
    }

    /**
     * By Default ESCAPE_CHARACTERs equals "^^" Sets the ESCAPE_CHARACTERs to
     * the string passed
     * 
     * @param x
     *            String
     */
    public void setEscapeChar(String x) {
        setEscapeCharStart(x);
        setEscapeCharEnd(x);
    }

    /**
     * By Default ESCAPE_CHARACTER_START equals "^^" Sets the
     * ESCAPE_CHARACTER_START to the string passed.
     * 
     * @param x
     *            String
     */
    public void setEscapeCharStart(String x) {
        if (x.equals(""))
            return;
        escCharStart = x;
        isEscCharOne = escCharStart.equals(escCharEnd);
    }

    /**
     * By Default ESCAPE_CHARACTER_END equals "^^" Sets the ESCAPE_CHARACTER_END
     * to the string passed.
     * 
     * @param x
     *            String
     */
    public void setEscapeCharEnd(String x) {
        if (x.equals(""))
            return;
        escCharEnd = x;
        isEscCharOne = escCharStart.equals(escCharEnd);
    }

    /**
     * By Default the SpecialString is case sensitive.
     * 
     * @param y
     *            boolean ( true/false )
     */
    public void setIgnoreCase(boolean y) {
        ignore_case = y;
    }

    /**
     * Set the Exclude Escape to true or false. ExcludeEscape will determine
     * that the substitute() method will substitute the string with/without the
     * ESCAPE_CHARACTERs depending on Boolean value passed.<BR>
     * Default ExcludeEscape == true. This method Initializes the Escape
     * Counter.<BR>
     * 
     * @param y
     *            boolean ( true/false )
     */
    public void setExcludeEscape(boolean y) {
        escape_char_counter = 1;
        exclude_escape_character = y;

    }

    /**
     * toString() method in the String class is overridden
     */
    public String toString() {
        return st.toString();
    }

    /**
     * Changes Case of the existing String to <i>Title</i> Case. SpecialString
     * object is converted to <i>Title</i> Case. Internally it calls
     * {@link #toTitleCase( String) }.
     */
    public void toTitleCase() {
        st = new StringBuffer(toTitleCase(st.toString()));
    }

    /**
     * Changes Case of the existing String to <i>Title</i> Case. SpecialString
     * object is converted to <i>Title</i> Case.
     * 
     * @param str
     *            String to be converted to <i>Title</i> Case.
     * @return String
     */
    public static String toTitleCase(String str) {
        StringTokenizer stoken = new StringTokenizer(str, "\". ", true);
        StringBuffer str1 = new StringBuffer();
        while (stoken.hasMoreTokens()) {
            String s = stoken.nextToken();
            str1.append(s.substring(0, 1).toUpperCase(Locale.US));
            str1.append(s.substring(1).toLowerCase(Locale.US));
        }
        return str1.toString();
    }

    /**
     * Changes Case of the existing String to <i>Sentence</i> Case which is
     * much more effecient than MS Word 97. This method even checks for ""
     * double quoted String and changes it to <i>Sentence</i> Case.
     * SpecialString object is converted to Sentence Case. Internally it calls
     * {@link #toSentenceCase( String)}.
     */
    public void toSentenceCase() {
        st = new StringBuffer(toSentenceCase(st.toString()));
    }

    /**
     * Changes Case of the existing String to <i>Sentence</i> Case which is
     * much more effecient than MS Word 97. This method even checks for ""
     * double quoted String and changes it to <i>Sentence</i> Case.
     * 
     * @param str
     *            String to be changed to <i>Sentence</i> Case
     * @return String
     */
    public static String toSentenceCase(String str) {
        StringTokenizer stoken = new StringTokenizer(str, ". \"", true);
        boolean toCapitalize = true;
        StringBuffer str1 = new StringBuffer();
        while (stoken.hasMoreTokens()) {
            String s = stoken.nextToken();
            if (s.equals(".") || s.equals("\"")) {
                str1.append(s);
                toCapitalize = true;
            } else {
                if (s.equals(" "))
                    str1.append(s);
                else if (toCapitalize) {
                    str1.append(s.substring(0, 1).toUpperCase(Locale.US));
                    str1.append(s.substring(1).toLowerCase(Locale.US));
                    toCapitalize = false;
                } else
                    str1.append(s.toLowerCase(Locale.US));
            }
        }
        return str1.toString();
    }

    /**
     * Removes special characters from the SpecialString object. Special
     * characters are other than a space, A-Z, a-z, 0-9. Internally it calls
     * {@link #removeSpecialChars(String)}.
     * 
     * @return String
     */
    public String removeSpecialChars() {
        return removeSpecialChars(st.toString());
    }

    /**
     * Removes special characters from the given String. Special characters are
     * other than a space, A-Z, a-z, 0-9.
     * 
     * @param pstr
     *            String from which the special characters are to be removed.
     * @return String.
     */
    public static String removeSpecialChars(String pstr) {
        char a;
        if (pstr == null) {
            return "";
        }
        StringBuffer newstr = new StringBuffer();
        for (int i = 0; i <= pstr.length() - 1; i++) {
            a = pstr.charAt(i);
            if ((a == 32) || ((a >= 65) && (a <= 90))
                    || ((a >= 97) && (a <= 122)) || ((a >= 48) && (a <= 57)))
                newstr.append(a);
        }
        return newstr.toString();
    }
}
