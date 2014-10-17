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

/**
 * Verbose a given number in words.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 1 $
 *
 */
public final class Verbose
{
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 1                 $";
    
    /**
    * Defines all the number from 0 to 9 in words
    */
    private final static String[] ONES =
        {
            "",
            "One ",
            "Two ",
            "Three ",
            "Four ",
            "Five ",
            "Six ",
            "Seven ",
            "Eight ",
            "Nine ",
            "Ten " };
    
    /**
    * Defines all the numbers from 11 to 19 in words.
    */
    private final static String[] TEENS =
        {
            "",
            "Eleven ",
            "Twelve ",
            "Thirteen ",
            "Fourteen ",
            "Fifteen ",
            "Sixteen ",
            "Seventeen ",
            "Eighteen ",
            "Nineteen " };
    
    /**
    * Defines all the numbers from 10 to 90 in words
    */
    private final static String[] TENS =
        {
            "",
            "Ten ",
            "Twenty ",
            "Thirty ",
            "Forty ",
            "Fifty ",
            "Sixty ",
            "Seventy ",
            "Eighty ",
            "Ninety " };
    /**
    * Defines 100, 1000, 100000, 10000000 in words.
    */
    private final static String[] INDIAN =
        { "Hundred ", "Thousand ", "Lakh ", "Crore " };

    private static long integerno = 0;
    private static long decimalno = 0;

    /**
     * Internally {@link #inIndianFormat(String, boolean)} method is called with boolean 
     * parameter value as true and the verbosed value is returned.
     * @param snum String representing a double value.
     * @return String verbosed in Indian Format (European format)with the currency symbol attached.
     */
    public static String inIndianFormat(String snum)
    {
        return inIndianFormat(snum, true);
    }

    /**
     * Number is verbosed in Indian Format (European format) with scientific/currency identifier.
     * <BR>
     * E.g.
     * <UL>
     *     <LI>inIndianFormat( 123.21, false ) <BR>
     *         123.21 is returnd as One Hundred Twenty Three And Point Two One. <BR>
     *     <LI>inIndianFormat( 123.21, true )<BR>
     *         123.21 is returnd as Rupees One Hundred Twenty Three And paise Twenty One Only.
     *  </UL><BR>
     * 
     * @param snum String representing a double value,
     * @param attachCurrency boolean true/false
     * @return String verbosed number.
     */
    public static String inIndianFormat(String snum, boolean attachCurrency)
    {
        integerno = 0;
        decimalno = 0;
        String word = "";
        breakNumber(snum);
        if (integerno < 0)
            return "Rupess Nil ";
        if (integerno + decimalno == 0)
        {
            return "Zero Paise Only.";
        }
        word = convertNumber(integerno);
        if (word.length() > 0 && attachCurrency == true)
            word = "Rupees " + word;
        if (decimalno > 0)
        {
            if (word.length() > 0)
                word += "And ";
            if (attachCurrency == true)
            {
                word += "Paise " + convertNumber(decimalno);
            }
            else
            {
                word += "Point ";
                String s = "";
                while (decimalno > 0)
                {
                    long d = decimalno % 10;
                    decimalno = (decimalno - d) / 10;
                    s = convertNumber(d) + s;
                }
                word += s;
            }
        }
        if (attachCurrency == true)
            word += "Only.";
        return (word);
    }

    /**
     * Converts a string verbosed in American Format with currency Symbol
     * of Dollar attached.
     * 
     * @param snum String representing a double value
     * @return String. Verbosed number.
     */
    public static String inAmericanFormat(String snum)
    {
        integerno = 0;
        decimalno = 0;
        String[] AMERICAN =
            { "Thousand ", "Million ", "Billion ", "Trillion " };
        String word = "";
        breakNumber(snum);
        if (integerno < 0)
            return "Dollars Nill ";
        if (integerno + decimalno == 0)
        {
            return "Zero Cents Only.";
        }
        if (integerno > Long.parseLong("999999999999999"))
        {
            return "Cannot Convert..";
        }
        long mno;
        int cnt = 1;
        String dummy = "";
        while (integerno > 0)
        {
            mno = integerno % 1000;
            integerno = (integerno - mno) / 1000;
            dummy = convertNumber(mno);
            if (cnt > 1)
            {
                if (dummy.length() > 0)
                {
                    word = AMERICAN[cnt - 2] + word;
                }
            }
            word = dummy + word;
            dummy = "";
            cnt++;
        }
        if (word.length() > 0)
            word = "Dollars " + word;
        if (decimalno > 0)
        {
            if (word.length() > 0)
                word += "And ";
            word += "Cents " + convertNumber(decimalno);
        }
        return word + "Only.";
    }

    /**
     * Breaks the number in 2 parts. Integer and Fraction.
     * 
     * @param snum String value representing the number
     */
    private static void breakNumber(String snum)
    {
        double mno;
        try
        {
            mno = Double.parseDouble(snum);
        }
        catch (NumberFormatException e)
        {
            integerno = -1;
            return;
        } //If cound not convert

//        String word = "";
        if (mno == 0)
        {
            return;
        }
        int pos = snum.indexOf('.');
        try
        {
            if (pos > 0)
            {
                integerno = Long.parseLong(snum.substring(0, pos));
                decimalno = 0;
                snum = snum + "000";
                try
                {
                    decimalno =
                        Long.parseLong(snum.substring(pos + 1, pos + 3));
                }
                catch (NumberFormatException e)
                {
                    decimalno = 0;
                }
            }
            else
            {
                integerno = Long.parseLong(snum);
            }
        }
        catch (NumberFormatException e)
        {
            integerno = -1;
        }
        return;
    }

    /**
     * Conversion utility that converts a number into words.
     * 
     * @param number Number that needs to be verbosed.
     * @return String
     */
    private static String convertNumber(long number)
    {
        int cnt = 1, red = 0;
        long mno;
        String inwords = "", name1 = "";

        while (number > 0)
        {
            if (cnt == 2 || cnt == 6)
            {
                mno = number % 10;
                number = (number - mno) / 10;
            }
            else
            {
                mno = number % 100;
                number = (number - mno) / 100;
            }
            if (mno > 0)
            {
                if (mno <= 10)
                {
                    name1 = ONES[(int) mno];
                }
                else if (mno < 20)
                {
                    name1 = TEENS[(int) mno - 10];
                }
                else
                {
                    long onespos = mno % 10;
                    long tenspos = (mno - onespos) / 10;
                    name1 = TENS[(int) tenspos] + ONES[(int) onespos];
                }
            }
            if (cnt > 1)
            {
                if (name1.length() > 0 || cnt == 5)
                {
                    inwords = INDIAN[red] + inwords;
                }
                red++;
                if (red == 4)
                    red = 0;
            }
            inwords = name1 + inwords;
            name1 = "";
            cnt++;
        }
        return inwords;
    }

}
