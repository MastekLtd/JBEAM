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

/**
 * A class for formatting numbers that follows <tt>printf</tt> conventions.
 * 
 * Also implements C-like <tt>atoi</tt> and <tt>atof</tt> functions
 * 
 */

public class SimpleFormat {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 1                 $";

/**
     * Formats the number following <tt>printf</tt> conventions.
     * 
     * Main limitation: Can only handle one format parameter at a time Use
     * multiple Format objects to format more than one number
     * 
     * @param s
     *            the format string following printf conventions The string has
     *            a prefix, a format code and a suffix. The prefix and suffix
     *            become part of the formatted output. The format code directs
     *            the formatting of the (single) parameter to be formatted. The
     *            code has the following structure
     *            <ul>
     *            <li> a % (required)
     *            <li> a modifier (optional)
     *            <dl>
     *            <dt> +
     *            <dd> forces display of + for positive numbers
     *            <dt> 0
     *            <dd> show leading zeroes
     *            <dt> -
     *            <dd> align left in the field
     *            <dt> space
     *            <dd> prepend a space in front of positive numbers
     *            <dt> #
     *            <dd> use "alternate" format. Add 0 or 0x for octal or
     *            hexadecimal numbers. Don't suppress trailing zeroes in general
     *            floating point format.
     *            <dt> #
     *            <dd> after the width & precision denotes the format in which
     *            the decimal/floating number is to be parsed. E.g. A Format
     *            <b>|%5.1#,###.#f|</b> and a number 1210.1 will be returned as
     *            <b>| 1,210.1|</b>. E.g.2) <b>|%+010.1##,##,##,###.#f|</b>
     *            returns <b>|+00001,210.1|</b>. E.g. 3) for -1210.1
     *            <b>|%+010.1##,##,##,###.#()f|</b> returns <b>|(00001,210.1)|</b>
     *            <dt> ()
     *            <dd> after the # denotes the format in which the -Negative
     *            value should placed inside the Bracket. E.g. A Format
     *            <b>|%5.1#,###.#()f|</b> and a number -1210.1 will be returned
     *            as <b>| (1,210.1)|</b>
     *            <dt> D+
     *            <dd> after the # denotes the format that the Dr side is
     *            Positve and a negative value will suiffixed with a Cr and
     *            postive value will be by a Dr sign. E.g. A Format
     *            <b>|%5.1#,###.#D+f|</b> and a number -1210.1 will be returned
     *            as <b>| 1,210.1 Cr|</b>
     *            <dt> D-
     *            <dd> after the # denotes the format that the Dr side is
     *            Negative and a positive value will suiffixed with a Cr and
     *            negative value will be by a Dr sign. E.g. A Format
     *            <b>|%5.1#,###.#D-f|</b> and a number -1210.1 will be returned
     *            as <b>| 1,210.1 Dr|</b>
     *            </dl>
     *            <li> an integer denoting field width (optional)
     *            <li> a period followed by an integer denoting precision
     *            (optional)
     *            <li> a format descriptor (required)
     *            <dl>
     *            <dt>f
     *            <dd> floating point number in fixed format
     *            <dt>,
     *            <dd> floating point number in fixed format without the Decimal
     *            Point.
     *            <dt>e, E
     *            <dd> floating point number in exponential notation (scientific
     *            format). The E format results in an uppercase E for the
     *            exponent (1.14130E+003), the e format in a lowercase e.
     *            <dt>g, G
     *            <dd> floating point number in general format (fixed format for
     *            small numbers, exponential format for large numbers). Trailing
     *            zeroes are suppressed. The G format results in an uppercase E
     *            for the exponent (if any), the g format in a lowercase e.
     *            <dt>d, i
     *            <dd> integer in decimal
     *            <dt>x
     *            <dd> integer in hexadecimal
     *            <dt>o
     *            <dd> integer in octal
     *            <dt>s
     *            <dd> string
     *            <dt>c
     *            <dd> character
     *            </dl>
     *            </ul>
     * @exception IllegalArgumentException
     *                if bad format
     * 
     */

    public SimpleFormat(String s) {
        width = 0;
        precision = -1;
        preFixSign = 0;
        decimalAdded = 0;
        pre = new StringBuffer();
        post = new StringBuffer();
        leading_zeroes = false;
        show_plus = false;
        alternate = false;
        show_space = false;
        left_align = false;
        no_decimal = false;
        bracketFlag = false;
        fmt = ' ';
        accountingFmt = new StringBuffer();
        // int state = 0;
        int length = s.length();
        int parse_state = 0;
        // 0 = prefix, 1 = flags, 2 = width, 3 = precision,
        // 4 = format, 5 = end
        int i = 0;

        while (parse_state == 0) {
            if (i >= length)
                parse_state = 5;
            else if (s.charAt(i) == '%') {
                if (i < length - 1) {
                    if (s.charAt(i + 1) == '%') {
                        pre.append('%');
                        i++;
                    } else
                        parse_state = 1;
                } else
                    throw new java.lang.IllegalArgumentException();
            } else
                pre.append(s.charAt(i));
            i++;
        }
        while (parse_state == 1) {
            if (i >= length)
                parse_state = 5;
            else if (s.charAt(i) == ' ')
                show_space = true;
            else if (s.charAt(i) == '-')
                left_align = true;
            else if (s.charAt(i) == '+')
                show_plus = true;
            else if (s.charAt(i) == '0')
                leading_zeroes = true;
            else if (s.charAt(i) == '#')
                alternate = true;
            else {
                parse_state = 2;
                i--;
            }
            i++;
        }
        while (parse_state == 2) {
            if (i >= length)
                parse_state = 5;
            else if ('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                width = width * 10 + s.charAt(i) - '0';
                i++;
            } else if (s.charAt(i) == '.') {
                parse_state = 3;
                precision = 0;
                i++;
            } else if (s.charAt(i) == ',') {
                parse_state = 3;
                precision = 0;
                no_decimal = true;
                StringBuffer sbuf = new StringBuffer(s);
                sbuf = sbuf.replace(i, i + 1, ".");
                s = sbuf.toString();
                i++;
            } else
                parse_state = 4;
        }
        while (parse_state == 3) {
            if (i >= length)
                parse_state = 5;
            else if ('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                precision = precision * 10 + s.charAt(i) - '0';
                i++;
            } else
                parse_state = 4;
        }
        if (parse_state == 4) {
            if (i >= length)
                parse_state = 5;
            else if (s.charAt(i) == '#') {
                if (no_decimal)
                    throw new java.lang.IllegalArgumentException(
                            "Cannot Apply Picture Format for floating point number without the Decimal Point ");
                if (left_align)
                    throw new java.lang.IllegalArgumentException(
                            "Cannot Left Align number for a given Picture Format");
                while (parse_state == 4) {
                    if (i >= length)
                        parse_state = 5;
                    if (s.charAt(i) == '#' || s.charAt(i) == '.'
                            || s.charAt(i) == ',') {
                        accountingFmt.append(s.charAt(i));
                        i++;
                    } else if (s.charAt(i) == '(') {
                        if (accountingSign)
                            throw new java.lang.IllegalArgumentException(
                                    "() in valid picture format after 'A'");
                        i++;
                        bracketFlag = true; // For Negation
                    } else if (s.charAt(i) == ')') {
                        i++;
                        if (!bracketFlag)
                            throw new java.lang.IllegalArgumentException(
                                    "() in valid picture format");
                    } else if (s.charAt(i) == 'D') {
                        if (bracketFlag)
                            throw new java.lang.IllegalArgumentException(
                                    "'A' in valid picture format after '()'");
                        accountingSign = true;
                        i++;
                    } else if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                        if (!accountingSign)
                            throw new java.lang.IllegalArgumentException(
                                    "+ in valid picture format");
                        isDrPositive = (s.charAt(i) == '+');
                        i++;
                    } else
                        parse_state = 5;
                }
                SpecialString sp = new SpecialString(accountingFmt.toString());
                if (width != sp.occurs("#"))
                    throw new java.lang.IllegalArgumentException(
                            "No Of # does not match the width");
                if (sp.occurs(".") > 1)
                    throw new java.lang.IllegalArgumentException(
                            "One decimal point is accepted");
                if (sp.indexOf(".", 1) >= 0) {
                    if (accountingFmt.indexOf(",", accountingFmt.indexOf(".")) > 0)
                        throw new java.lang.IllegalArgumentException(
                                "No Commas must be present after Decimal Point");
                    if (accountingFmt.substring(accountingFmt.indexOf(".") + 1)
                            .length() != precision)
                        throw new java.lang.IllegalArgumentException(
                                "No. of # does not match the precision");
                }
                if (i >= length)
                    parse_state = 5;
                else
                    fmt = s.charAt(i);
                if (fmt != 'd' && fmt != 'f')
                    throw new java.lang.IllegalArgumentException(
                            "# allowed only for numeric functions (d or f)");
                i++;
            } else {
                fmt = s.charAt(i);
                i++;
            }
        }
        if (i < length)
            post = new StringBuffer(s.substring(i, length));
        if (fmt == ' ')
            throw new java.lang.IllegalArgumentException("Format Not Specified");
    }

    /**
     * prints a formatted number following printf conventions
     * 
     * @param fmt
     *            the format string
     * @param x
     *            the double to print
     * @return String
     */
    public static String format(String fmt, double x) {
        return new SimpleFormat(fmt).format(x);
    }

    /**
     * prints a formatted number following printf conventions
     * 
     * @param fmt
     *            the format string
     * @param x
     *            the int to print
     * @return String
     */

    public static String format(String fmt, int x) {
        return new SimpleFormat(fmt).format(x);
    }

    /**
     * prints a formatted number following printf conventions
     * 
     * @param fmt
     *            the format string
     * @param x
     *            the long to print
     * @return String
     */

    public static String format(String fmt, long x) {
        return new SimpleFormat(fmt).format(x);
    }

    /**
     * prints a formatted number following printf conventions
     * 
     * @param fmt
     *            the format string
     * @param x
     *            the character to print
     * @return String
     */

    public static String format(String fmt, char x) {
        return new SimpleFormat(fmt).format(x);
    }

    /**
     * prints a formatted number following printf conventions
     * 
     * @param fmt
     *            the format string
     * @param x
     *            a string to print
     * @return String
     */

    public static String format(String fmt, String x) {
        return new SimpleFormat(fmt).format(x);
    }

    /**
     * Converts a string of digits (decimal, octal or hex) to an integer
     * 
     * @param s
     *            a string
     * @return the numeric value of the prefix of s representing a base 10
     *         integer
     */

    public static int atoi(String s) {
        return (int) atol(s);
    }

    /**
     * Converts a string of digits (decimal, octal or hex) to a long integer
     * 
     * @param s
     *            a string
     * @return the numeric value of the prefix of s representing a base 10
     *         integer
     */

    public static long atol(String s) {
        int i = 0;

        while (i < s.length() && Character.isWhitespace(s.charAt(i)))
            i++;
        if (i < s.length() && s.charAt(i) == '0') {
            if (i + 1 < s.length()
                    && (s.charAt(i + 1) == 'x' || s.charAt(i + 1) == 'X'))
                return parseLong(s.substring(i + 2), 16);
            else
                return parseLong(s, 8);
        } else
            return parseLong(s, 10);
    }

    private static long parseLong(String s, int base) {
        int i = 0;
        int sign = 1;
        long r = 0;

        while (i < s.length() && Character.isWhitespace(s.charAt(i)))
            i++;
        if (i < s.length() && s.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if (i < s.length() && s.charAt(i) == '+') {
            i++;
        }
        while (i < s.length()) {
            char ch = s.charAt(i);
            if ('0' <= ch && ch < '0' + base)
                r = r * base + ch - '0';
            else if ('A' <= ch && ch < 'A' + base - 10)
                r = r * base + ch - 'A' + 10;
            else if ('a' <= ch && ch < 'a' + base - 10)
                r = r * base + ch - 'a' + 10;
            else
                return r * sign;
            i++;
        }
        return r * sign;
    }

    /**
     * Converts a string of digits to a <tt>double</tt>
     * 
     * @param s
     *            a string
     * @return double
     */

    public static double atof(String s) {
        int i = 0;
        int sign = 1;
        double r = 0; // integer part
        // double f = 0; // fractional part
        double p = 1; // exponent of fractional part
        int state = 0; // 0 = int part, 1 = frac part

        while (i < s.length() && Character.isWhitespace(s.charAt(i)))
            i++;
        if (i < s.length() && s.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if (i < s.length() && s.charAt(i) == '+') {
            i++;
        }
        while (i < s.length()) {
            char ch = s.charAt(i);
            if ('0' <= ch && ch <= '9') {
                if (state == 0)
                    r = r * 10 + ch - '0';
                else if (state == 1) {
                    p = p / 10;
                    r = r + p * (ch - '0');
                }
            } else if (ch == '.') {
                if (state == 0)
                    state = 1;
                else
                    return sign * r;
            } else if (ch == 'e' || ch == 'E') {
                long e = (int) parseLong(s.substring(i + 1), 10);
                return sign * r * Math.pow(10, e);
            } else
                return sign * r;
            i++;
        }
        return sign * r;
    }

    /**
     * Formats a <tt>double</tt> into a string (like sprintf in C)
     * 
     * @param x
     *            the number to format
     * @return the formatted string
     * @exception IllegalArgumentException
     *                if bad argument
     */

    public String format(double x) {
        String r;
        if (precision < 0)
            precision = 6;
        int s = 1;
        if (x < 0) {
            x = -x;
            s = -1;
        }
        if (fmt == 'f')
            r = fixed_format(x);
        else if (fmt == 'e' || fmt == 'E' || fmt == 'g' || fmt == 'G')
            r = exp_format(x);
        else
            throw new java.lang.IllegalArgumentException();

        return pad(sign(s, r));
    }

    /**
     * Formats an integer into a string (like sprintf in C)
     * 
     * @param x
     *            the number to format
     * @return the formatted string
     */

    public String format(int x) {
        long lx = x;
        if (fmt == 'o' || fmt == 'x' || fmt == 'X')
            lx &= 0xFFFFFFFFL;
        return format(lx);
    }

    /**
     * Formats a long integer into a string (like sprintf in C)
     * 
     * @param x
     *            the number to format
     * @return the formatted string
     */

    public String format(long x) {
        String r;
        int s = 0;
        if (fmt == 'd' || fmt == 'i') {
            if (x < 0) {
                r = ("" + x).substring(1);
                s = -1;
            } else {
                r = "" + x;
                s = 1;
            }
        } else if (fmt == 'o')
            r = convert(x, 3, 7, "01234567");
        else if (fmt == 'x')
            r = convert(x, 4, 15, "0123456789abcdef");
        else if (fmt == 'X')
            r = convert(x, 4, 15, "0123456789ABCDEF");
        else
            throw new java.lang.IllegalArgumentException();

        return pad(sign(s, r));
    }

    /**
     * Formats a character into a string (like sprintf in C)
     * 
     * @param c
     *            the value to format
     * @return the formatted string
     */

    public String format(char c) {
        if (fmt != 'c')
            throw new java.lang.IllegalArgumentException();

        String r = "" + c;
        return pad(r);
    }

    /**
     * Formats a string into a larger string (like sprintf in C)
     * 
     * @param s
     *            the value to format
     * @return the formatted string
     */
    public String format(String s) {
        if (fmt != 's')
            throw new java.lang.IllegalArgumentException();
        // if (precision >= 0 && precision < s.length())
        // s = s.substring(0, precision);
        if (width >= 0 && width < s.length())
            s = s.substring(0, width);
        return pad(s);
    }

    /**
     * A main method that displays the functionality of various formats that can
     * be used in conjunction with SimpleFormat.
     * 
     * @param a
     *            Arguments.
     */
    public static void main(String[] a) {
        double x = 1.23456789012;
        double y = 123;
        double z = 1.2345e30;
        double w = 1.02;
        double u = 1.234e-5;
        int d = 0xCAFE;
        System.out.println("      double x = 1.23456789012; ");
        System.out.println("      double y = 123;  ");
        System.out.println("      double z = 1.2345e30;  ");
        System.out.println("      double w = 1.02;  ");
        System.out.println("      double u = 1.234e-5;  ");
        System.out.println("      int    d = 0xCAFE; \n");
        System.out.println("Format %f \t\t"
                + SimpleFormat.format("x = |%f|", x));
        System.out.println("Format %20f \t\t"
                + SimpleFormat.format("u = |%20f|", u));
        System.out.println("Format % .5f \t\t"
                + SimpleFormat.format("x = |% .5f|", x));
        System.out.println("Format %20.5f \t\t"
                + SimpleFormat.format("w = |%20.5f|", w));
        System.out.println("Format %20,5f \t\t"
                + SimpleFormat.format("w = |%20,5f|", w));
        System.out.println("Format %020.5f \t\t"
                + SimpleFormat.format("x = |%020.5f|", x));
        System.out.println("Format %020,5f \t\t"
                + SimpleFormat.format("x = |%020,5f|", x));
        System.out.println("Format %+20.5f \t\t"
                + SimpleFormat.format("x = |%+20.5f|", x));
        System.out.println("Format %-20.5f \t\t"
                + SimpleFormat.format("x = |%+020.5f|", x));
        System.out.println("Format % 020.5f \t"
                + SimpleFormat.format("x = |% 020.5f|", x));
        System.out.println("Format %#+20.5f \t"
                + SimpleFormat.format("y = |%#+20.5f|", y));
        System.out.println("Format %#+20,5f \t"
                + SimpleFormat.format("y = |%#+20,5f|", y));
        System.out.println("Format %-+20.5f \t"
                + SimpleFormat.format("y = |%-+20.5f|", y));
        System.out.println("Format %20.5f \t\t"
                + SimpleFormat.format("z = |%20.5f|\n", z));

        System.out.println("Format %e \t\t"
                + SimpleFormat.format("x = |%e|", x));
        System.out.println("Format %20e \t\t"
                + SimpleFormat.format("u = |%20e|", u));
        System.out.println("Format % .5e \t\t"
                + SimpleFormat.format("x = |% .5e|", x));
        System.out.println("Format %20.5e \t\t"
                + SimpleFormat.format("w = |%20.5e|", w));
        System.out.println("Format %20,5e \t\t"
                + SimpleFormat.format("w = |%20,5e|", w));
        System.out.println("Format %20.5e \t\t"
                + SimpleFormat.format("x = |%020.5e|", x));
        System.out.println("Format %20,5e \t\t"
                + SimpleFormat.format("x = |%020,5e|", x));
        System.out.println("Format %20.5e \t\t"
                + SimpleFormat.format("x = |%+20.5e|", x));
        System.out.println("Format %+20.5e \t\t"
                + SimpleFormat.format("x = |%+020.5e|", x));
        System.out.println("Format %+20,5e \t\t"
                + SimpleFormat.format("x = |%+020,5e|", x));
        System.out.println("Format % 020.5e \t"
                + SimpleFormat.format("x = |% 020.5e|", x));
        System.out.println("Format %#+20.5e \t"
                + SimpleFormat.format("y = |%#+20.5e|", y));
        System.out.println("Format %-+20.5e \t"
                + SimpleFormat.format("y = |%-+20.5e|\n", y));

        System.out.println("Format %g \t\t"
                + SimpleFormat.format("x = |%g|", x));
        System.out.println("Format %g \t\t"
                + SimpleFormat.format("z = |%g|", z));
        System.out.println("Format %g \t\t"
                + SimpleFormat.format("w = |%g|", w));
        System.out.println("Format %g \t\t"
                + SimpleFormat.format("u = |%g|", u));
        System.out.println("Format %.2g \t\t"
                + SimpleFormat.format("y = |%.2g|", y));
        System.out.println("Format %#.2g \t\t"
                + SimpleFormat.format("y = |%#.2g|\n", y));

        System.out.println("Format %d \t\t"
                + SimpleFormat.format("d = |%d|", d));
        System.out.println("Format %20d \t\t"
                + SimpleFormat.format("d = |%20d|", d));
        System.out.println("Format %020d \t\t"
                + SimpleFormat.format("d = |%020d|", d));
        System.out.println("Format %+20d \t\t"
                + SimpleFormat.format("d = |%+20d|", d));
        System.out.println("Format % 020d \t\t"
                + SimpleFormat.format("d = |% 020d|", d));
        System.out.println("Format %-20d \t\t"
                + SimpleFormat.format("d = |%-20d|", d));
        System.out.println("Format %20.8d \t\t"
                + SimpleFormat.format("d = |%20.8d|", d));
        System.out.println("Format %x \t\t"
                + SimpleFormat.format("d = |%x|", d));
        System.out.println("Format %20X \t\t"
                + SimpleFormat.format("d = |%20X|", d));
        System.out.println("Format %#20x \t\t"
                + SimpleFormat.format("d = |%#20x|", d));
        System.out.println("Format %020X \t\t"
                + SimpleFormat.format("d = |%020X|", d));
        System.out.println("Format %20.8x \t\t"
                + SimpleFormat.format("d = |%20.8x|", d));
        System.out.println("Format %o \t\t"
                + SimpleFormat.format("d = |%o|", d));
        System.out.println("Format %020o \t\t"
                + SimpleFormat.format("d = |%020o|", d));
        System.out.println("Format %#20o \t\t"
                + SimpleFormat.format("d = |%#20o|", d));
        System.out.println("Format %#020o \t\t"
                + SimpleFormat.format("d = |%#020o|", d));
        System.out.println("Format %20.12o \t\t"
                + SimpleFormat.format("d = |%20.12o|\n", d));

        System.out.println("String / Character Formating .....");
        System.out.println("Format %-20s \t\t"
                + SimpleFormat.format("s = |%-20s|", "Hello"));
        System.out.println("Format %-20c \t\t"
                + SimpleFormat.format("s = |%-20c|\n", '!'));

        // regression test to confirm fix of reported bugs
        System.out.println("regression test to confirm fix of reported bugs");
        System.out.println("Format %i (Long.MIN_VALUE)  "
                + SimpleFormat.format("|%i|\n", Long.MIN_VALUE));

        System.out.println("Format %6.2e (0.0) \t"
                + SimpleFormat.format("    |%6.2e|", 0.0));
        System.out.println("Format %6.2g (0.0) \t"
                + SimpleFormat.format("    |%6.2g|\n", 0.0));

        System.out.println("Format %20.5#,##,##,###,##,##,###.#####f "
                + SimpleFormat.format(
                        "w = |%20.5#,##,##,###,##,##,###.#####f|",
                        1234567890.12345));
        System.out.println("Format %020.5#,##,##,###,##,##,###.#####f "
                + SimpleFormat.format(
                        "x = |%020.5#,##,##,###,##,##,###.#####f|",
                        1234567890.12345));
        System.out.println("Format %+20.5#,##,##,###,##,##,###.#####f "
                + SimpleFormat.format(
                        "x = |%+20.5#,##,##,###,##,##,###.#####f|",
                        1234567890.12345));
        System.out.println("Format % 020.5#,##,##,###,##,##,###.#####f "
                + SimpleFormat.format(
                        "x = |% 020.5#,##,##,###,##,##,###.#####f|",
                        1234567890.12345));
        System.out.println("Format %#+20.5#,##,##,###,##,##,###.#####f "
                + SimpleFormat.format(
                        "y = |%#+20.5#,##,##,###,##,##,###.#####f|",
                        1234567890.12345));
        System.out.println("Format %+20.5#,##,##,###,##,##,###.#####f "
                + SimpleFormat.format(
                        "y = |%+20.5#,##,##,###,##,##,###.#####f|",
                        1234567890.12345));
        System.out.println("Format %20.5#,##,##,###,##,##,###.#####f "
                + SimpleFormat.format(
                        "z = |%20.5#,##,##,###,##,##,###.#####f|\n",
                        1234567890.12345));

        System.out.println("Format %6.0f (9.999) \t"
                + SimpleFormat.format("    |%6.0f|", 9.999));
        System.out.println("Format %6,0f (9.999) \t"
                + SimpleFormat.format("    |%6,0f|\n", 9.999));

        System.out.println("Format %6.2#,###.##f\t"
                + SimpleFormat.format("    |%6.2#,###.##f|\n", 1129.99));
        System.out.println("Format %06.2#,###.##f\t"
                + SimpleFormat.format("    |%06.2#,###.##f|\n", 1129.99));
        System.out.println("Format %+6.2#,###.##f\t"
                + SimpleFormat.format("    |%+6.2#,###.##f|\n", 1129.99));
        System.out.println("Format %6.2#,###.##f\t"
                + SimpleFormat.format("    |%6.2#,###.##f|\n", -1129.99));
        d = -1;
        System.out.println("Format %X \t\t"
                + SimpleFormat.format("d = |%X|", d));

        System.out.println("");
        System.out.println("Please Note The Difference ...");
        System.out.println("Format %10.0f (123456.34)\t"
                + SimpleFormat.format("|%10.0f|", 123456.34));
        System.out.println("Format %10.0f (123456.99)\t"
                + SimpleFormat.format("|%10.0f|", 123456.99));
        System.out.println("Format %10.0#,##,##,##,###f (123456.34) "
                + SimpleFormat.format("|%10.0#,##,##,##,###f|", 123456.34));
        System.out.println("Format %10.0#,##,##,##,###f (123456.99) "
                + SimpleFormat.format("|%10.0#,##,##,##,###f|", 123456.99));

        System.out.println("");
        System.out.println("Format %10.0#,##,##,##,###()f (-123456.99) "
                + SimpleFormat.format("|%10.0#,##,##,##,###()f|", -123456.99));
        System.out.println("Format %10.0#,##,##,##,###()f (123456.99) "
                + SimpleFormat.format("|%10.0#,##,##,##,###()f|", 123456.99));
        System.out.println("");
        System.out.println("");
        System.out.println("Format %10.2#,##,##,###.##D+f (-123456.99) "
                + SimpleFormat.format("|%10.2#,##,##,###.##D+f|", -123456.99));
        System.out.println("Format %10.2#,##,##,###.##D+f (123456.99) "
                + SimpleFormat.format("|%10.2#,##,##,###.##D+f|", 123456.99));
        System.out.println("");
        System.out.println("Format %10.2#,##,##,###.##D-f (-123456.99) "
                + SimpleFormat.format("|%10.2#,##,##,###.##D-f|", -123456.99));
        System.out.println("Format %10.2#,##,##,###.##D-f (123456.99) "
                + SimpleFormat.format("|%10.2#,##,##,###.##D-f|", 123456.99));
        System.out.println("Format %10.2#,##,##,###.##D-f (0) "
                + SimpleFormat.format("|%10.2#,##,##,###.##D-f|", 0.0));
    }

    private static String repeat(char c, int n) {
        if (n <= 0)
            return "";
        StringBuffer s = new StringBuffer(n);
        for (int i = 0; i < n; i++)
            s.append(c);
        return s.toString();
    }

    private static String convert(long x, int n, int m, String d) {
        if (x == 0)
            return "0";
        String r = "";
        while (x != 0) {
            r = d.charAt((int) (x & m)) + r;
            x = x >>> n;
        }
        return r;
    }

    private String pad(String r) {
        String p = repeat(' ', width + preFixSign + decimalAdded - r.length());
        if (left_align) {
            return pre + r + p + post;
        } else
            return pre + p + r + post;
    }

    private String sign(int s, String r) {
        String p = "";
        preFixSign = 0;
        decimalAdded = 0;
        if (s < 0) {
            p = "-";
            preFixSign = 1;
        } else if (s > 0) {
            if (show_plus) {
                p = "+";
                preFixSign = 1;
            } else if (show_space) {
                p = " ";
                preFixSign = 1;
            }
        } else {
            if (fmt == 'o' && alternate && r.length() > 0 && r.charAt(0) != '0')
                p = "0";
            else if (fmt == 'x' && alternate)
                p = "0x";
            else if (fmt == 'X' && alternate)
                p = "0X";
        }
        int w = 0;
        if (leading_zeroes) {
            w = width;
        } else if ((fmt == 'd' || fmt == 'i' || fmt == 'x' || fmt == 'X' || fmt == 'o')
                && precision > 0)
            w = precision;

        if (r.indexOf(".") >= 0) {
            if (r.length() != (width + 1))
                decimalAdded = 1;
        }
        if (leading_zeroes)
            return applyAF(p + repeat('0', (w + decimalAdded - r.length())) + r);
        else {
            if (accountingFmt.length() > 0)
                return applyAF(repeat(' ', (width + decimalAdded) - r.length())
                        + (p + repeat('0', (w - r.length())) + r));
            else {
                return (p + repeat('0', (w - r.length())) + r);
            }
        }

    }

    private String fixed_format(double d) {
        boolean removeTrailing = (fmt == 'G' || fmt == 'g') && !alternate;
        // remove trailing zeroes and decimal point

        if (d > 0x7FFFFFFFFFFFFFFFL)
            return exp_format(d);
        if (precision == 0) {
            if (no_decimal)
                return (long) (d + 0.5) + "";
            return (long) (d + 0.5) + (removeTrailing ? "" : ".");
        }

        long whole = (long) d;
        double fr = d - whole; // fractional part
        if (fr >= 1 || fr < 0)
            return exp_format(d);

        double factor = 1;
        StringBuffer leading_zeroes = new StringBuffer();
        for (int i = 1; i <= precision && factor <= 0x7FFFFFFFFFFFFFFFL; i++) {
            factor *= 10;
            leading_zeroes.append("0");
        }
        long l = (long) (factor * fr + 0.5);
        if (l >= factor) {
            l = 0;
            whole++;
        } // CSH 10-25-97

        String z = leading_zeroes.toString() + l;
        z = "." + z.substring(z.length() - precision, z.length());

        if (removeTrailing) {
            int t = z.length() - 1;
            while (t >= 0 && z.charAt(t) == '0')
                t--;
            if (t >= 0 && z.charAt(t) == '.')
                t--;
            z = z.substring(0, t + 1);
        }

        if (no_decimal) {
            SpecialString ss = new SpecialString(z);
            ss.substitute(".", "");
            z = ss.toString();
        }

        return whole + z;
    }

    private String exp_format(double d) {
        String f = "";
        int e = 0;
        double dd = d;
        double factor = 1;
        if (d != 0) {
            while (dd > 10) {
                e++;
                factor /= 10;
                dd = dd / 10;
            }
            while (dd < 1) {
                e--;
                factor *= 10;
                dd = dd * 10;
            }
        }
        if ((fmt == 'g' || fmt == 'G') && e >= -4 && e < precision)
            return fixed_format(d);

        d = d * factor;
        f = f + fixed_format(d);

        if (fmt == 'e' || fmt == 'g')
            f = f + "e";
        else
            f = f + "E";

        String p = "000";
        if (e >= 0) {
            f = f + "+";
            p = p + e;
        } else {
            f = f + "-";
            p = p + (-e);
        }

        return f + p.substring(p.length() - 3, p.length());
    }

    private String applyAF(String stno) {
        StringBuffer rtst = new StringBuffer();
        StringBuffer pictureFormat = new StringBuffer(accountingFmt.toString());
        if (!pictureFormat.toString().equals("")) {
            if (preFixSign == 1)
                pictureFormat.insert(0, '#'); // &&
            // accountingFmt.length()
            // == stno.length()
            int j = 0;
            boolean isSuppressCommas = true;
            for (int i = 0; i < pictureFormat.length(); i++) {
                char c = pictureFormat.charAt(i);
                char a = stno.charAt(j);
                if (isSuppressCommas) {
                    if (a > '0') {
                        isSuppressCommas = false;
                        if (c != ',') {
                            rtst.append(a);
                            j++;
                        }
                    } else {
                        if (c != ',') {
                            rtst.append(a);
                            j++;
                        }
                    }
                } else {
                    if (c == ',')
                        rtst.append(c);
                    else {
                        rtst.append(a);
                        j++;
                    }
                }
            }
        } else
            rtst = new StringBuffer(stno);
        if (bracketFlag) {
            SpecialString spst = new SpecialString(rtst.toString());
            if (spst.occurs("-") > 0) {
                spst.substitute("-", "");
                rtst.append("(");
                rtst.append(spst.toString());
                rtst.append(")");
            }
        } else if (accountingSign) {
            SpecialString spst = new SpecialString(rtst.toString());
            if (spst.occurs("-") > 0) {
                spst.substitute("-", "");
                rtst = new StringBuffer(spst.toString());
                if (isDrPositive)
                    rtst.append(" Cr");
                else
                    rtst.append(" Dr");
            } else {
                if (isDrPositive)
                    rtst.append(" Dr");
                else
                    rtst.append(" Cr");
            }
        }
        return rtst.toString();
    }

    private int width;

    private int precision;

    private int preFixSign;

    private int decimalAdded;

    private StringBuffer pre;

    private StringBuffer post;

    private boolean leading_zeroes;

    private boolean show_plus;

    private boolean alternate;

    private boolean show_space;

    private boolean left_align;

    private boolean no_decimal;

    private boolean bracketFlag = false;

    private char fmt; // one of cdeEfgGiosxXos

    private StringBuffer accountingFmt;

    private boolean accountingSign = false;

    private boolean isDrPositive = false;
}
