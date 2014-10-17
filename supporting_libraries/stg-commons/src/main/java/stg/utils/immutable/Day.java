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

package stg.utils.immutable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * Class that represents a date and provides simple operations to perform date
 * arithmetic.
 * 
 * This is immutable class and once constructed will never change. This is another date class, but 
 * more convenient that <tt>java.util.Date</tt> or <tt>java.util.Calendar</tt>
 * 
 * @version $Revision: 1 $
 * @author Kedar Raybagkar
 * @author Cay Horstmann
 */
public final class Day implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 231086469331845348L;

    /**
     * This variable stores the revision number of this particular class.
     * <code>code comment for REVISION</code>
     */
    public static final String REVISION = "$Revision: 1 $";

    /**
     * Denotes day. Comment for <code>day</code>
     */
    private final int day;

    /**
     * Denotes month. Comment for <code>month</code>
     */
    private final int month;

    /**
     * Denotes year. Comment for <code>year</code>
     */
    private final int year;

    /**
     * Denotes hour. Comment for <code>hour</code>
     */
    private final int hour;

    /**
     * Denotes minutes. Comment for <code>minutes</code>
     */
    private final int minutes;

    /**
     * Denotes seconds. Comment for <code>seconds</code>
     */
    private final int seconds;

    /**
     * Denotes miliseconds.
     */
    private final int milliseconds;

    private final TimeZone tzone;

    /**
     * Constructs today's date. Supports milliseconds up to 3 digits.
     */

    private Day(TimeZone zone) {
        Calendar calendar = GregorianCalendar.getInstance(zone);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);
        milliseconds = calendar.get(Calendar.MILLISECOND);
        tzone = zone;
    }

    /**
     * Constructs a specific date
     * 
     * @param yyyy
     *            year (full year, e.g., 1996, <i>not </i> starting from 1900)
     * @param month
     *            month
     * @param day
     *            day
     * @param hour
     * @param minutes
     * @param seconds
     * @param miliseconds
     * @exception IllegalArgumentException
     *                if yyyy m d not a valid date
     */
    private Day(int yyyy, int month, int day, int hour, int minutes, int seconds, int miliseconds, TimeZone zone) {
        this.year = yyyy;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = miliseconds;
        tzone = zone;
        if (!isValid())
            throw new IllegalArgumentException();
        if (hour < 0 || hour > 23)
            throw new IllegalArgumentException();
        if (minutes < 0 || minutes > 59)
            throw new IllegalArgumentException();
        if (seconds < 0 || seconds > 59)
            throw new IllegalArgumentException();
        if (miliseconds < 0 || miliseconds > 999)
            throw new IllegalArgumentException();
    }

    /**
     * Constructs a specific date from the time.
     * 
     * @param time
     *            long
     * @exception IllegalArgumentException
     *                if not a valid date
     * @see System#currentTimeMillis()
     */

    private Day(long time, TimeZone zone) {
        GregorianCalendar calendar = getCalendarInstance(zone);
        calendar.setTimeInMillis(time);
        tzone = zone;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);
        milliseconds = calendar.get(Calendar.MILLISECOND);
    }

    /**
     * Advances this day by n days. For example. d.advance(30) adds thirdy days
     * to d
     * 
     * @param n
     *            the number of days by which to change this day (can be < 0)
     */

    public Day advance(int n) {
        return fromJulian(toJulian() + n);
    }

    /**
     * Date Arithmetic function. Adds the specified (signed) amount of time to
     * the given time field, based on the calendar's rules.
     * 
     * A fixed date is that if a schedule is set on Feb 28 and it is to be
     * advanced by one month then next month date will be Mar 28 and not Mar 31.
     * So 28 is the date that is fixed. This is applicable to advance method for
     * fields like {@link Calendar#MONTH} and {@link Calendar#YEAR}.
     * 
     * @param field
     *            Calendar fields {@link Calendar}.
     * @param amount
     *            the amount of date or time to be added to the field.
     * @exception IllegalArgumentException
     *                if an unknown field is given.
     */
    public Day advance(int field, int amount) {
        return advance(field, amount, false);
    }

    /**
     * Date Arithmetic function. Adds the specified (signed) amount of time to
     * the given time field, based on the calendar's rules.
     * 
     * A fixed date is that if a schedule is set on Feb 28 and it is to be
     * advanced by one month then next month date will be Mar 28 and not Mar 31.
     * So 28 is the date that is fixed. This is applicable to advance method for
     * fields like {@link Calendar#MONTH} and {@link Calendar#YEAR}.
     * 
     * @param field
     *            Calendar fields {@link Calendar}.
     * @param amount
     *            the amount of date or time to be added to the field.
     * @exception IllegalArgumentException
     *                if an unknown field is given.
     */
    public Day advance(int field, int amount, boolean fixedDate) {
        int newYear = year;
        int newMonth = month;
        int newDay = day;
        int newHour = hour;
        int newMinutes = minutes;
        int newSeconds = seconds;
        int newMilliseconds = milliseconds;
        Calendar calendar = getCalendar();
        boolean isOnLastDate = (day == calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        while (true) { // This is to incorporate the FIXED DATE.
            // In FIXED DATE, the date (day) is important and it should not
            // change in accordance to the month.
            // E.g. Fixed on every 30th of the month then Feb must be skipped as
            // Feb does not have 30th.
            calendar.add(field, amount);
            int nyear = calendar.get(Calendar.YEAR);
            int nmonth = calendar.get(Calendar.MONTH) + 1;
            int nday = calendar.get(Calendar.DAY_OF_MONTH);
            int nhour = calendar.get(Calendar.HOUR_OF_DAY);
            int nminutes = calendar.get(Calendar.MINUTE);
            int nseconds = calendar.get(Calendar.SECOND);
            int mseconds = calendar.get(Calendar.MILLISECOND);
            if (fixedDate && (field == Calendar.MONTH || field == Calendar.YEAR)) { // This
                                                                                    // is
                                                                                    // only
                                                                                    // for
                                                                                    // Month
                                                                                    // &
                                                                                    // Year
                try {
                    // Parse the date with the old day and rest all the new
                    // fields from the advanced date.
                    // Generates an exception means that the respective month
                    // does not have that day then advance again
                    // till the parse does not throw an exception.
                    Day.createDay(nyear, nmonth, day, nhour, nminutes, nseconds, mseconds);
                    newYear = nyear;
                    newMonth = nmonth;
                    // day is not set as the day must be constant for Fixed
                    // Date.
                    newHour = nhour;
                    newMinutes = nminutes;
                    newSeconds = nseconds;
                    newMilliseconds = mseconds;
                    break;
                } catch (Throwable e) {
                    // do nothing.
                }
            } else {
                newYear = nyear;
                newMonth = nmonth;
                newDay = nday;
                newHour = nhour;
                newMinutes = nminutes;
                newSeconds = nseconds;
                newMilliseconds = mseconds;
                break;
            }
        } // end while

        if (!fixedDate) {
            if (isOnLastDate) {
                if (field == Calendar.MONTH || field == Calendar.YEAR) {
                    if (day != calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                        newDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    }
                }
            } // is on last date
        } // if not fixed date
        return Day.createDay(newYear, newMonth, newDay, newHour, newMinutes, newSeconds, newMilliseconds, tzone);
    }

    /**
     * Gets the day of the month
     * 
     * @return the day of the month (1...31)
     */

    public int getDay() {
        return day;
    }

    /**
     * Gets the month
     * 
     * @return the month (1...12)
     */

    public int getMonth()

    {
        return month;
    }

    /**
     * Gets the year
     * 
     * @return the year (counting from 0, <i>not </i> from 1900)
     */

    public int getYear() {
        return year;
    }

    /**
     * Gets the hour
     * 
     * @return the hour
     */
    public int getHour() {
        return (hour);
    }

    /**
     * Gets the minutes
     * 
     * @return the minutes
     */
    public int getMinutes() {
        return (minutes);
    }

    /**
     * Gets the seconds
     * 
     * @return the seconds
     */
    public int getSeconds() {
        return (seconds);
    }

    /**
     * Gets the milliseconds
     * 
     * @return the milliseconds
     */
    public int getMilliseconds() {
        return (milliseconds);
    }

    /**
     * Returns time zone.
     * 
     * @return time zone
     */
    public TimeZone getTimeZone() {
        return tzone;
    }

    /**
     * Gets the weekday
     * 
     * @return the weekday ({@link Calendar#SUNDAY}, ...,
     *         {@link Calendar#SATURDAY})
     */

    public int weekday() {
        Calendar calendar = getCalendarInstance(tzone);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * The number of days between this and day parameter
     * 
     * @param b
     *            any date
     * @return the number of days between this and day parameter and b (> 0 if
     *         this day comes after b)
     */

    public int daysBetween(Day b) {
        return (toJulian() - b.toJulian());
    }

    /**
     * The number of days between this and day parameter
     * 
     * @param dt
     *            any java.sql.Date
     * @return int the number of days between this and day parameter and b (> 0
     *         if this day comes after b)
     */

    public int daysBetween(java.sql.Date dt) {
        Day b = Day.createDay(dt);
        return daysBetween(b);
    }

    /**
     * The number of days between this and day parameter
     * 
     * @param dt
     *            any java.util.Date
     * @return int the number of days between this and day parameter and b (> 0
     *         if this day comes after b)
     */

    public int daysBetween(java.util.Date dt) {
        Day b = Day.createDay(dt);
        return daysBetween(b);
    }

    /**
     * The number of months between this and day parameter
     * 
     * @param b
     *            any date
     * @return the number of days between this and day parameter and b (> 0 if
     *         this day comes after b)
     */
    public int monthsBetween(Day b) {
        Calendar gcThisDay = getCalendarInstance(tzone);
        gcThisDay.setTime(this.getUtilDate());
        Calendar gcTarget = getCalendarInstance(tzone);
        gcTarget.setTime(b.getUtilDate());

        int iMonthsBetween = 0;
        if (gcThisDay.after(gcTarget)) {
            while (gcThisDay.after(gcTarget)) {
                gcTarget.add(Calendar.MONTH, 1);
                if (gcThisDay.after(gcTarget))
                    iMonthsBetween++;
                if (gcThisDay.equals(gcTarget)) {
                    iMonthsBetween++;
                    break;
                }
            }
        } else if (gcThisDay.before(gcTarget)) {
            while (gcThisDay.before(gcTarget)) {
                gcThisDay.add(Calendar.MONTH, 1);
                if (gcThisDay.before(gcTarget)) {
                    iMonthsBetween++;
                } else if (gcThisDay.equals(gcTarget)) {
                    iMonthsBetween++;
                    break;
                }
            }
        }

        return (iMonthsBetween);
    }

    /**
     * The number of months between this and day parameter
     * 
     * @param dt
     *            any date
     * @return int the number of days between this and day parameter and b (> 0
     *         if this day comes after b)
     */
    public int monthsBetween(java.sql.Date dt) {
        Day b = Day.createDay(dt);
        return monthsBetween(b);
    }

    /**
     * The number of months between this and day parameter
     * 
     * @param dt
     *            any date
     * @return int the number of days between this and day parameter and b (> 0
     *         if this day comes after b)
     */
    public int monthsBetween(java.util.Date dt) {
        Day b = Day.createDay(dt);
        return monthsBetween(b);
    }

    /**
     * Compares a given time in long format with this instance.
     * 
     * @param time
     *            the time to be compared.
     * @return the value 0 if the argument is equal to this Date; a value
     *         less than 0 if this Date is before the argument; and a value
     *         greater than 0 if this Date is after the given argument.
     */
    public long compareTo(long time) {
        return compareTo(new Day(time, this.tzone));
    }

    /**
     * Compares two Dates.
     * 
     * @param dt
     *            the Date to be compared.
     * @return the value 0 if the argument Date is equal to this Date; a value
     *         less than 0 if this Date is before the Date argument; and a value
     *         greater than 0 if this Date is after the Date argument.
     */
    public long compareTo(java.util.Date dt) {
        return compareTo(Day.createDay(dt));
    }
    
    /**
     * Compares two Dates.
     * 
     * @param dt
     *            the Date to be compared.
     * @return the value 0 if the argument Date is equal to this Date; a value
     *         less than 0 if this Date is before the Date argument; and a value
     *         greater than 0 if this Date is after the Date argument.
     */
    public long compareTo(java.sql.Date dt) {
        return compareTo(Day.createDay(dt));
    }

    /**
     * Compares two Dates.
     * 
     * @param target
     *            the Date to be compared.
     * @return the value 0 if the argument Date is equal to this Date; a value
     *         less than 0 if this Date is before the Date argument; and a value
     *         greater than 0 if this Date is after the Date argument.
     */
    public long compareTo(Day target) {
        return this.getTimeInMillis()-target.getTimeInMillis();
    }

    /**
     * A string representation of the day.
     * 
     * @return a string representation of the day
     */
    public String toString() {
        return FastDateFormat.getInstance("yyyy/MM/dd hh:mm:ss:SSS", tzone).format(getTimeInMillis());
    }

    /**
     * Compares this Day against another object
     * 
     * @param obj
     *            another object
     * @return true if the other object is identical to this Day object
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Day)) {
            return false;
        }
        Day b = (Day) obj;
        if (day != b.day)
            return false;
        if (month != b.month)
            return false;
        if (year != b.year)
            return false;
        if (hour != b.hour)
            return false;
        if (minutes != b.minutes)
            return false;
        if (seconds != b.seconds)
            return false;
        if (milliseconds != b.milliseconds)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Converts the Day Object into java.sql.Date
     * 
     * @return java.sql.Date
     */
    public java.sql.Date getSQLDate() {
        return new java.sql.Date(this.getTimeInMillis());
    }

    /**
     * Converts the Day Object into java.util.Date
     * 
     * @return java.util.Date
     * @exception IllegalArgumentException
     *                if not a valid date
     */
    public java.util.Date getUtilDate() {
        return new java.util.Date(this.getTimeInMillis());
    }

    /**
     * Converts the Day Object into java.sql.Timestamp
     * 
     * @return java.sql.Timestamp
     * @exception IllegalArgumentException
     *                if unable to convert
     */
    public java.sql.Timestamp getTimestamp() {
        return new java.sql.Timestamp(this.getTimeInMillis());
    }

    /**
     * Computes the number of days between two dates
     * 
     * @return true if this is a valid date
     * @exception IllegalArgumentException
     *                if not a valid date
     */
    private boolean isValid() {
        if (year < 0 || month < 0 || day < 0 || hour < 0 || minutes < 0 || seconds < 0 || milliseconds < 0) {
            throw new IllegalArgumentException();
        }
        getCalendar(); //if instance got created everything is valid
        return true;
    }

    /**
     * Converts the Calendar Date to Julian Day.
     * 
     * @return The Julian day number that begins at noon of this day Positive
     *         year signifies A.D., negative year B.C. Remember that the year
     *         after 1 B.C. was 1 A.D.
     * 
     *         A convenient reference point is that May 23, 1968 noon is Julian
     *         day 2440000.
     * 
     *         Julian day 0 is a Monday.
     * 
     *         This algorithm is from Press et al., Numerical Recipes in C, 2nd
     *         ed., Cambridge University Press 1992
     */
    public int toJulian() {
        int jy = year;
        if (year < 0)
            jy++;
        int jm = month;
        if (month > 2)
            jm++;
        else {
            jy--;
            jm += 13;
        }
        int jul = (int) (java.lang.Math.floor(365.25 * jy) + java.lang.Math.floor(30.6001 * jm) + day + 1720995.0);

        int IGREG = 15 + 31 * (10 + 12 * 1582);
        // Gregorian Calendar adopted Oct. 15, 1582

        if (day + 31 * (month + 12 * year) >= IGREG)
        // change over to Gregorian calendar
        {
            int ja = (int) (0.01 * jy);
            jul += 2 - ja + (int) (0.25 * ja);
        }
        return jul;
    }

    /**
     * Converts a Julian day to a calendar date
     * 
     * This algorithm is from Press et al., Numerical Recipes in C, 2nd ed.,
     * Cambridge University Press 1992
     * 
     * @param j
     *            the Julian date
     */

    public Day fromJulian(int j) {
        int ja = j;

        int JGREG = 2299161;
        /*
         * the Julian date of the adoption of the Gregorian calendar
         */

        if (j >= JGREG)
        /*
         * cross-over to Gregorian Calendar produces this correction
         */
        {
            int jalpha = (int) (((float) (j - 1867216) - 0.25) / 36524.25);
            ja += 1 + jalpha - (int) (0.25 * jalpha);
        }
        int jb = ja + 1524;
        int jc = (int) (6680.0 + ((float) (jb - 2439870) - 122.1) / 365.25);
        int jd = (int) (365 * jc + (0.25 * jc));
        int je = (int) ((jb - jd) / 30.6001);
        int day = jb - jd - (int) (30.6001 * je);
        int month = je - 1;
        if (month > 12)
            month -= 12;
        int year = jc - 4715;
        if (month > 2)
            --year;
        if (year <= 0)
            --year;
        return Day.createDay(year, month, day, hour, minutes, seconds, milliseconds, tzone);
    }

    /**
     * Returns the maximum number of days in a month.
     * 
     * @return int
     */
    public int getMonthDays() {
        return getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    public static Day createDay() {
        return new Day(TimeZone.getDefault());
    }
    
    public static Day createDay(TimeZone zone) {
        return new Day(zone);
    }

    /**
     * Sets this day with the given year, month and day.
     * 
     * Internally calls {@link #createDay(int, int, int, int, int, int)} and passes
     * ZERO for hour, minutes and seconds.
     * 
     * @param year
     * @param month
     * @param day
     */
    public static Day createDay(int year, int month, int day) {
        return createDay(year, month, day, 0, 0, 0);
    }

    /**
     * Sets this day with the given year, month and day.
     * 
     * Internally calls {@link #createDay(int, int, int, int, int, int)} and passes
     * ZERO for hour, minutes and seconds.
     * 
     * @param year
     * @param month
     * @param day
     */
    public static Day createDay(int year, int month, int day, TimeZone zone) {
        return createDay(year, month, day, 0, 0, 0, 0, zone);
    }
    
    /**
     * Sets the Day for the given year, month, day, hour, minutes and seconds.
     * 
     * Internally calls {@link #createDay(int, int, int, int, int, int)} and passes
     * ZERO for milliseconds.
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minutes
     * @param seconds
     */
    public static Day createDay(int year, int month, int day, int hour, int minutes, int seconds) {
        return createDay(year, month, day, hour, minutes, seconds, 0);
    }

    /**
     * Sets the Day for the given year, month, day, hour, minutes and seconds.
     * 
     * Internally calls {@link #createDay(int, int, int, int, int, int)} and passes
     * ZERO for milliseconds.
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minutes
     * @param seconds
     */
    public static Day createDay(int year, int month, int day, int hour, int minutes, int seconds, TimeZone zone) {
        return createDay(year, month, day, hour, minutes, seconds, 0, zone);
    }
    
    /**
     * Sets the Day for the given year, month, day, hour, minutes, seconds and
     * milliseconds.
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minutes
     * @param seconds
     * @param milliseconds
     */
    public static Day createDay(int year, int month, int day, int hour, int minutes, int seconds, int milliseconds) {
        return createDay(year, month, day, hour, minutes, seconds, milliseconds, TimeZone.getDefault());
    }

    public static Day createDay(int year, int month, int day, int hour, int minutes, int seconds, int milliseconds, TimeZone zone) {
        return new Day(year, month, day, hour, minutes, seconds, milliseconds, zone);
    }
    
    /**
     * Sets the current day to a new day.
     * 
     * @param psdt
     *            java.sql.Date
     */
    public static Day createDay(java.sql.Date psdt) {
        return Day.createDay(psdt.getTime());
    }

    /**
     * Sets the current day to a new day.
     * 
     * @param pudt
     *            java.util.Date
     */
    public static Day createDay(java.util.Date pudt) {
        return Day.createDay(pudt.getTime());
    }

    /**
     * Sets the current day to a new day.
     * 
     * @param pts
     *            java.sql.Timestamp
     * @return Day representing the given time stamp
     */
    public static Day createDay(java.sql.Timestamp pts) {
        return Day.createDay(pts.getTime());
    }

    /**
     * Sets the day for the given time.
     * 
     * @param time
     * @return Day representing given time.
     */
    public static Day createDay(long time) {
        return new Day(time, TimeZone.getDefault());
    }

    /**
     * Sets the day for the given time.
     * 
     * @param time
     * @param zone timezone
     * @return Day representing given time and time zone.
     */
    public static Day createDay(long time, TimeZone zone) {
        return new Day(time, zone);
    }
    
    /**
     * Returns the maximum value for the given field.
     * 
     * @param field
     *            Calendar values for fields.
     * @return int maximum value for the given field.
     */
    public int getMaximum(int field) {
        Calendar calendar = getCalendarInstance(tzone);
        return calendar.getActualMaximum(field);
    }

    /**
     * Returns the minimum value for the given field.
     * 
     * @param field
     *            Calendar constants for fields.
     * @return int minimum value for the given field.
     */
    public int getMinimum(int field) {
        Calendar calendar = getCalendar();
        return calendar.getActualMinimum(field);
    }

    /**
     * Returns the time in milliseconds.
     * 
     * @return time in millis
     * @see Calendar#getTimeInMillis()
     */
    public long getTimeInMillis() {
        return getCalendar().getTimeInMillis();
    }

    /**
     * Displays the time difference between two dates.
     * 
     * @param day1
     *            Day.
     * @param day2
     *            Day to be compared with.
     * @return String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     */
    public static String verboseTimeDifference(Day day1, Day day2) {
        return day1.verboseTimeDifference(day2);
    }

    /**
     * Displays the time difference between two {@link java.sql.Timestamp}
     * 
     * Internally calls {@link #verboseTimeDifference(Day, Day)}
     * 
     * @param ts1
     *            Timestamp.
     * @param ts2
     *            Timestamp to be compared with.
     * @return String String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     */
    public static String verboseTimeDifference(Timestamp ts1, Timestamp ts2) {
        return verboseTimeDifference(Day.createDay(ts1), Day.createDay(ts2));
    }

    /**
     * Displays the time difference between two {@link java.sql.Date}
     * 
     * Internally calls {@link #verboseTimeDifference(Day, Day)}
     * 
     * @param dt1
     *            Date.
     * @param dt2
     *            Date to be compared with.
     * @return String String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     */
    public static String verboseTimeDifference(java.sql.Date dt1, java.sql.Date dt2) {
        return verboseTimeDifference(Day.createDay(dt1), Day.createDay(dt2));
    }

    /**
     * Displays the time difference between two {@link java.util.Date}s.
     * 
     * Internally calls {@link #verboseTimeDifference(Day, Day)}
     * 
     * @param dt1
     *            Date.
     * @param dt2
     *            Date to be compared with.
     * @return String String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     */
    public static String verboseTimeDifference(java.util.Date dt1, java.util.Date dt2) {
        return verboseTimeDifference(Day.createDay(dt1), Day.createDay(dt2));
    }

    /**
     * Displays the time difference between two numbers representing time.
     * 
     * @param time1
     *            Time.
     * @param time2
     *            Time to be compared with.
     * @return String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     * @see Calendar#getTimeInMillis()
     */
    public static String verboseTimeDifference(long time1, long time2) {
        return verboseTimeDifference(Day.createDay(time1), Day.createDay(time2));
    }

    /**
     * Verbose date difference in <i>Y</i>ears, <i>M</i>onths, <i>D</i>ays,
     * <i>H</i>ours, <i>M</i>inutes and <i>S</i>econds.
     * 
     * <code><br>
     * Day d1 = new Day(1972, 3, 27, 22, 32, 8);<br>
     * Day d2 = new Day(1984, 2, 11, 23, 59, 59);<br>
     * </code> The output will be 11<i>y</i> 10<i>M</i> 13<i>d</i> 1<i>H</i>
     * 27<i>m</i> 51<i>s</i> 212<i>S</i>
     * 
     * @param day
     *            to be compared with.
     * @return String
     */
    public String verboseTimeDifference(Day day) {
        Day d = Day.createDay(day.getTimeInMillis());
        StringBuffer strBuffer = new StringBuffer();
        int multifyFactor = 1;
        if (this.compareTo(d) < 0) {
            multifyFactor = -1;
        }
        // multiply factor is most important and this determines if the date is
        // to be advanced in future or advanced backward.
        int years = 0;
        while (true) {
            d = d.advance(Calendar.YEAR, 1 * multifyFactor);
            if (this.compareTo(d) * multifyFactor < 0) {
                break;
            }
            years++;
        }
        d = d.advance(Calendar.YEAR, -1 * multifyFactor);
        if (years > 0) {
            strBuffer.append(years);
            strBuffer.append("y ");
        }
        int months = 0;
        while (true) {
            d = d.advance(Calendar.MONTH, 1 * multifyFactor);
            if (this.compareTo(d) * multifyFactor < 0) {
                break;
            }
            months++;
        }
        d = d.advance(Calendar.MONTH, -1 * multifyFactor);
        if (months > 0) {
            strBuffer.append(months);
            strBuffer.append("M ");
        }
        long time = this.getTimeInMillis() - d.getTimeInMillis();
        long days = time / (1000 * 60 * 60 * 24);
        if (days != 0) {
            strBuffer.append(days * (days < 0 ? -1 : 1));
            strBuffer.append("d ");
        }
        time -= days * (1000 * 60 * 60 * 24);
        long hours = time / (1000 * 60 * 60);
        if (hours != 0) {
            strBuffer.append(hours * (hours < 0 ? -1 : 1));
            strBuffer.append("H ");
        }
        time -= hours * (1000 * 60 * 60);
        long minutes = time / (1000 * 60);
        if (minutes != 0) {
            strBuffer.append(minutes * (minutes < 0 ? -1 : 1));
            strBuffer.append("m ");
        }
        time -= minutes * (1000 * 60);
        long seconds = time / 1000;
        if (seconds != 0) {
            strBuffer.append(seconds * (seconds < 0 ? -1 : 1));
            strBuffer.append("s ");
        }
        time -= seconds * 1000;
        long milliseconds = time;
        if (milliseconds != 0) {
            strBuffer.append(milliseconds * (milliseconds < 0 ? -1 : 1));
            strBuffer.append("S");
        }
        if (strBuffer.length() == 0) { // no time difference
            return "0S";
        }
        return strBuffer.toString().trim();
    }

    /**
     * Returns true if this day is before the given day.
     * 
     * @param day
     *            to be compared.
     * @return boolean
     */
    public boolean isBefore(Day day) {
        return (compareTo(day) < 0);
    }

    /**
     * Returns true if this day is before the given date.
     * 
     * @param date
     *            to be compared.
     * @return boolean
     */
    public boolean isBefore(java.util.Date date) {
        return (compareTo(date) < 0);
    }

    /**
     * Returns true if this day is before the given timestamp.
     * 
     * @param timestamp
     *            to be compared with
     * @return boolean
     */
    public boolean isBefore(java.sql.Timestamp timestamp) {
        return (compareTo(timestamp) < 0);
    }

    /**
     * Returns true if this day is before the given sql date.
     * 
     * @param sqlDate
     *            to be compared with
     * @return boolean
     */
    public boolean isBefore(java.sql.Date sqlDate) {
        return (compareTo(sqlDate) < 0);
    }

    /**
     * Returns true if this day is before the given time
     * 
     * @param time
     *            to be compared with.
     * @return boolean
     */
    public boolean isBefore(long time) {
        return (compareTo(time) < 0);
    }

    /**
     * Returns true if this day is greater than the given day.
     * 
     * @param day
     *            to be compared with.
     * @return boolean
     */
    public boolean isAfter(Day day) {
        return (compareTo(day) > 0);
    }

    /**
     * Returns true if this day is greater than the given date.
     * 
     * @param date
     *            to be compared with.
     * @return boolean
     */
    public boolean isAfter(java.util.Date date) {
        return (compareTo(date) > 0);
    }

    /**
     * Returns true if this day is greater than the given time stamp.
     * 
     * @param timestamp
     *            to be compared with.
     * @return boolean
     */
    public boolean isAfter(java.sql.Timestamp timestamp) {
        return (compareTo(timestamp) > 0);
    }

    /**
     * Returns true if this day is greater than the given time sql date.
     * 
     * @param sqlDate
     *            to be compared with.
     * @return boolean
     */
    public boolean isAfter(java.sql.Date sqlDate) {
        return (compareTo(sqlDate) > 0);
    }

    /**
     * Returns true if this day is greater than the given time.
     * 
     * @param time
     *            to be compared with.
     * @return boolean
     */
    public boolean isAfter(long time) {
        return (compareTo(time) > 0);
    }

    /**
     * Returns true if the year of this day is a leap year.
     * 
     * @return boolean
     */
    public boolean isLeapYear() {
        return Day.isLeapYear(year);
    }

    /**
     * Returns true if the given year is a leap year or else returns false. A
     * leap year is an year where the february month has 29 days.
     * 
     * @param yyyy
     * @return boolean
     */
    public static boolean isLeapYear(int yyyy) {
        return Day.createDay(yyyy, 2, 1).getCalendar().getActualMaximum(Calendar.DATE) == 29; 
    }

    private GregorianCalendar getCalendarInstance(TimeZone zone) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setLenient(false);
        calendar.setTimeZone(zone);
        return calendar;
    }
    
    /**
     * Returns the calendar instance representing this Day.
     * @return Calendar
     */
    public Calendar getCalendar() {
        Calendar calendar = getCalendarInstance(tzone);
        calendar.set(year, (month - 1), day, hour, minutes, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        calendar.getTime();
        return calendar;
    }
}
