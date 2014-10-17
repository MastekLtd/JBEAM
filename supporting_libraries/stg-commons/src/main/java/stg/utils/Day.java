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

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class that represents a date and provides simple operations to perform date arithmetic.
 * 
 * This is another date class, but more convenient that <tt>java.util.Date</tt>
 * or <tt>java.util.Calendar</tt>
 * 
 * @version $Revision: 1 $
 * @author Kedar Raybagkar
 * @author Cay Horstmann
 */
public class Day implements Cloneable, Serializable, Comparable<Day> {
    
    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = -2664229357401710268L;

	/**
     * This variable stores the revision number of this particular class.
     * <code>code comment for REVISION</code>
     */
    public static final String REVISION = "$Revision: 1 $";

    private boolean lastDayAdjustment;
    
    /**
     * Constructs today's date. 
     * Supports milliseconds up to 3 digits.
     */

    public Day()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);
        milliseconds = calendar.get(Calendar.MILLISECOND);
        initDay = day;
        lastDayAdjustment = true;
    }

    /**
     * Constructs a specific date
     * 
     * @param yyyy
     *            year (full year, e.g., 1996, <i>not </i> starting from 1900)
     * @param m
     *            month
     * @param d
     *            day
     * @exception IllegalArgumentException
     *                if yyyy m d not a valid date
     */

    public Day(int yyyy, int m, int d)
    {
        //      GregorianCalendar calendar = new GregorianCalendar();
        year = yyyy;
        month = m;
        day = d;
        hour = 0;
        minutes = 0;
        seconds = 0;
        milliseconds = 0;
        if (!isValid())
            throw new IllegalArgumentException();
        initDay = day;
        lastDayAdjustment = true;
    }
    
    /**
     * Constructs a specific date
     * 
     * @param yyyy
     *            year (full year, e.g., 1996, <i>not </i> starting from 1900)
     * @param m
     *            month
     * @param d
     *            day
     * @param hour
     * @param minutes
     * @param seconds
     * @exception IllegalArgumentException
     *                if yyyy m d not a valid date
     */
    public Day(int yyyy, int m, int d, int hour, int minutes, int seconds)
    {
        //        GregorianCalendar calendar = new GregorianCalendar();
        year = yyyy;
        month = m;
        day = d;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = 0;
        if (!isValid())
            throw new IllegalArgumentException();
        if (hour < 0 || hour > 23)
            throw new IllegalArgumentException();
        if (minutes < 0 || minutes > 59)
            throw new IllegalArgumentException();
        if (seconds < 0 || seconds > 59)
            throw new IllegalArgumentException();
        initDay = day;
        lastDayAdjustment = true;
    }

    /**
     * Constructs a specific date
     * 
     * @param yyyy
     *            year (full year, e.g., 1996, <i>not </i> starting from 1900)
     * @param m
     *            month
     * @param d
     *            day
     * @param hour
     * @param minutes
     * @param seconds
     * @param miliseconds
     * @exception IllegalArgumentException
     *                if yyyy m d not a valid date
     */
    public Day(int yyyy, int m, int d, int hour, int minutes, int seconds, int miliseconds)
    {
        //        GregorianCalendar calendar = new GregorianCalendar();
        year = yyyy;
        month = m;
        day = d;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = miliseconds;
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
        initDay = day;
        lastDayAdjustment = true;
    }

    /**
     * Constructs a specific date
     * 
     * @param dt
     *            java.sql.Date
     * @exception IllegalArgumentException
     *                if not a valid date
     */

    public Day(java.sql.Date dt)
    {
        setDay(dt.getTime());
        initDay = day;
        lastDayAdjustment = true;
    }

    /**
     * Constructs a specific date
     * 
     * @param timestamp
     *            java.sql.Timestamp
     * @exception IllegalArgumentException
     *                if not a valid date
     */

    public Day(java.sql.Timestamp timestamp)
    {
        setDay(timestamp.getTime());
        initDay = day;
        lastDayAdjustment = true;
    }

    /**
     * Constructs a specific date
     * 
     * @param dt
     *            java.util.Date
     * @exception IllegalArgumentException
     *                if not a valid date
     */

    public Day(java.util.Date dt)
    {
        setDay(dt.getTime());
        initDay = day;
        lastDayAdjustment = true;
    }

    /**
     * Constructs a specific date from a julian
     * 
     * @param julian
     *            integer
     * @exception IllegalArgumentException
     *                if not a valid date
     */

    public Day(int julian)
    {
        fromJulian(julian);
        initDay = day;
        lastDayAdjustment = true;
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
    
    public Day(long time)
    {
        setDay(time);
        initDay = day;
        lastDayAdjustment = true;
    }
    

    public void setLastDayAdjustment(boolean bLastDayAdjustment) {
        this.lastDayAdjustment = bLastDayAdjustment;
    }
    
    public boolean isLastDayAdjustment() {
        return lastDayAdjustment;
    }
    
    /**
     * Sets the class to behave in strict mode.
     * For example the date is Feb 28 and then we advance the month by one then if this is
     * set to fixed date then the advanced date would be Mar 28 and not Mar 31.
     * 
     * @param bfixeddate true to set it as fixed date.
     * @since 11
     */
    public void setFixedDate(boolean bfixeddate) {
    	bFixedDate_ = bfixeddate;
    }
    
    /**
     * Returns the fixed date.
     * 
     * @return boolean
     * @since 11
     */
    public boolean isFixedDate() {
    	return bFixedDate_;
    }

    /**
     * Advances this day by n days. For example. d.advance(30) adds thirdy days
     * to d
     * 
     * @param n
     *            the number of days by which to change this day (can be < 0)
     */

    public void advance(int n)
    {
        fromJulian(toJulian() + n);
    }

    /**
     * Date Arithmetic function. Adds the specified (signed) amount of time to
     * the given time field, based on the calendar's rules.
     * 
     * A fixed date is that if a schedule is set on Feb 28 and it is to be advanced by one month
     * then next month date will be Mar 28 and not Mar 31. So 28 is the date that is fixed.
     * This is applicable to advance method for fields like {@link Calendar#MONTH} and 
     * {@link Calendar#YEAR}.
     *  
     * @param field
     *            Calendar fields {@link Calendar}.
     * @param amount
     *            the amount of date or time to be added to the field.
     * @exception IllegalArgumentException
     *                if an unknown field is given.
     * @see #setFixedDate(boolean)
     * @see #isFixedDate()
     */
    public void advance(int field, int amount)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setTimeInMillis(this.getTimeInMillis());
        boolean isOnLastDate = (day == calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        while (true) {	//This is to incorporate the FIXED DATE.
        	//In FIXED DATE, the date (day) is important and it should not change in accordance to the month.
        	//E.g. Fixed on every 30th of the month then Feb must be skipped as Feb does not have 30th.
        	calendar.add(field, amount);
	        int nyear = calendar.get(Calendar.YEAR);
	        int nmonth = calendar.get(Calendar.MONTH) + 1;
	        int nday = calendar.get(Calendar.DAY_OF_MONTH);
	        int nhour = calendar.get(Calendar.HOUR_OF_DAY);
	        int nminutes = calendar.get(Calendar.MINUTE);
	        int nseconds = calendar.get(Calendar.SECOND);
	        int mseconds = calendar.get(Calendar.MILLISECOND);
	        if (bFixedDate_ && (field == Calendar.MONTH || field == Calendar.YEAR)) { // This is only for Month & Year
	        	try {
	        		//Parse the date with the old day and rest all the new fields from the advanced date.
	        		//Generates an exception means that the respective month does not have that day then advance again
	        		//till the parse does not throw an exception.
	        	    new Day(nyear, nmonth, day, nhour, nminutes, nseconds, mseconds);
		        	year = nyear;
		        	month = nmonth;
		        	//day is not set as the day must be constant for Fixed Date.
		        	hour = nhour;
		        	minutes = nminutes;
		        	seconds = nseconds;
		        	milliseconds = mseconds;
	        		break;
	        	} catch (Throwable e) {
	        	    e.printStackTrace();
	        		//do nothing.
	        	}
	        } else {
	        	year = nyear;
	        	month = nmonth;
	        	day = nday;
	        	hour = nhour;
	        	minutes = nminutes;
	        	seconds = nseconds;
	        	milliseconds = mseconds;
	        	break;
	        }
        }
        if (!bFixedDate_) {
            if (lastDayAdjustment) {
                if (isOnLastDate)
                {
                    if (field == Calendar.MONTH || field == Calendar.YEAR)
                    {
                        if (day != calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                        {
                            day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        }
                    }
                } // is on last date
            } else {
                if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) >= initDay) {
                    day = initDay;
                }
            }
        } //if not fixed date
    }

    /**
     * Gets the day of the month
     * 
     * @return the day of the month (1...31)
     */

    public int getDay()
    {
        return day;
    }

    /**
     * Gets the day of the month
     * 
     * @return the day of the month (1...31)
     * @see #getDay()
     */
    @Deprecated
    public int day()
    {
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
     * Gets the month
     * 
     * @return the month (1...12)
     * @see #getMonth()
     */
    @Deprecated
    public int month()
    
    {
    	return month;
    }
    
    /**
     * Gets the year
     * 
     * @return the year (counting from 0, <i>not </i> from 1900)
     */

    public int getYear()
    {
        return year;
    }

    /**
     * Gets the year
     * 
     * @return the year (counting from 0, <i>not </i> from 1900)
     * @see #getYear()
     */
    @Deprecated
    public int year()
    {
    	return year;
    }
    
    /**
     * Gets the hour
     * 
     * @return the hour
     */
    public int getHour()
    {
        return (hour);
    }

    /**
     * Gets the hour
     * 
     * @return the hour
     * @see #getHour()
     */
    @Deprecated
    public int hour()
    {
    	return (hour);
    }
    
    /**
     * Gets the minutes
     * 
     * @return the minutes
     */
    public int getMinutes()
    {
        return (minutes);
    }

    /**
     * Gets the minutes
     * 
     * @return the minutes
     * @see #getMinutes()
     */
    @Deprecated
    public int minutes()
    {
    	return (minutes);
    }
    
    /**
     * Gets the seconds
     * 
     * @return the seconds
     */
    public int getSeconds()
    {
        return (seconds);
    }

    /**
     * Gets the seconds
     * 
     * @return the seconds
     * @see #getSeconds()
     */
    @Deprecated
    public int seconds()
    {
    	return (seconds);
    }

    /**
     * Gets the milliseconds
     * 
     * @return the milliseconds
     */
    public int getMilliseconds()
    {
        return (milliseconds);
    }

    /**
     * Gets the milliseconds
     * 
     * @return the milliseconds
     * @see #getMilliseconds()
     */
    @Deprecated
    public int milliseconds()
    {
    	return (milliseconds);
    }

    /**
     * Gets the weekday
     * 
     * @return the weekday ({@link Calendar#SUNDAY}, ..., {@link Calendar#SATURDAY})
     */

    public int weekday()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setTimeInMillis(this.getTimeInMillis());
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

    public int daysBetween(Day b)
    {
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

    public int daysBetween(java.sql.Date dt)
    {
        Day b = new Day(dt);
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

    public int daysBetween(java.util.Date dt)
    {
        Day b = new Day(dt);
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
    public int monthsBetween(Day b)
    {
        Calendar gcThisDay = new GregorianCalendar();
        gcThisDay.clear();
        gcThisDay.setTime(this.getUtilDate());
        Calendar gcTarget = new GregorianCalendar();
        gcTarget.clear();
        gcTarget.setTime(b.getUtilDate());

        int iMonthsBetween = 0;
        if (gcThisDay.after(gcTarget))
        {
            while (gcThisDay.after(gcTarget))
            {
                gcTarget.add(Calendar.MONTH, 1);
                if (gcThisDay.after(gcTarget))
                    iMonthsBetween++;
                if (gcThisDay.equals(gcTarget))
                {
                    iMonthsBetween++;
                    break;
                }
            }
        }
        else if (gcThisDay.before(gcTarget))
        {
            while (gcThisDay.before(gcTarget))
            {
                gcThisDay.add(Calendar.MONTH, 1);
                if (gcThisDay.before(gcTarget))
                {
                    iMonthsBetween++;
                }
                else if (gcThisDay.equals(gcTarget))
                {
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
    public int monthsBetween(java.sql.Date dt)
    {
        Day b = new Day(dt);
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
    public int monthsBetween(java.util.Date dt)
    {
        Day b = new Day(dt);
        return monthsBetween(b);
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
    public int compareTo(java.util.Date dt)
    {
        return daysBetween(dt);
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
    public int compareTo(java.sql.Date dt)
    {
        return daysBetween(dt);
    }

    /**
     * Compares two Dates.
     * 
     * @param day
     *            the Date to be compared.
     * @return the value 0 if the argument Date is equal to this Date; a value
     *         less than 0 if this Date is before the Date argument; and a value
     *         greater than 0 if this Date is after the Date argument.
     */
    public int compareTo(Day day)
    {
        return daysBetween(day);
    }

    /**
     * Compares this day with a given Day.
     * 
     * Time is compared to with the milliseconds.
     * 
     * @param b Day to be compared with.
     * @return long.
     */
    public long compareInSeconds(Day b)
    {
//        String strVersion = System.getProperty("java.runtime.version");
//        if (strVersion.startsWith("1.1") || strVersion.startsWith("1.2")
//                || strVersion.startsWith("1.3"))
//        {
            return this.getTimeInMillis() - b.getTimeInMillis(); 
//        }
//        else
//        {
//            GregorianCalendar calendar = new GregorianCalendar();
//            calendar.clear();
//            calendar.setTime(this.getUtilDate());
//            GregorianCalendar toCal = new GregorianCalendar();
//            toCal.clear();
//            toCal.setTime(b.getUtilDate());
//            return calendar.getTimeInMillis() - toCal.getTimeInMillis();
//        }
    }

    /**
     * Compares this day with the given Date.
     * @param dt Date to be compared with.
     * @return long.
     */
    public long compareInSeconds(java.util.Date dt)
    {
        Day b = new Day(dt);
        return compareInSeconds(b);
    }

    /**
     * Compares this day with the given time stamp.
     * @param ts Timestamp to be compared with.
     * @return long.
     */
    public long compareInSeconds(java.sql.Timestamp ts)
    {
        Day b = new Day(ts);
        return compareInSeconds(b);
    }

    public long compareInSeconds(long time)
    {
    	Day b = new Day(time);
    	return compareInSeconds(b);
    }
    
    /**
     * A string representation of the day.
     * 
     * @return a string representation of the day
     */
    public String toString()
    {
		if (objSimpleDateFormat == null) {
			return getUtilDate().toString();
		}
		return objSimpleDateFormat.format(new Long(this.getTimeInMillis()));
    }
    
    /**
     * Makes a bitwise copy of a Day object
     * 
     * @return a bitwise copy of a Day object
     */
    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch (CloneNotSupportedException e)
        { // this shouldn't happen, since we are Cloneable
            return null;
        }
    }

    /**
     * Compares this Day against another object
     * 
     * @param obj
     *            another object
     * @return true if the other object is identical to this Day object
     */
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass()))
            return false;
        Day b = (Day) obj;
        return day == b.day && month == b.month && year == b.year;
    }
    
    /* (non-Javadoc)
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
    public java.sql.Date getSQLDate()
    {
        return new java.sql.Date(this.getTimeInMillis());
    }

    /**
     * Converts the Day Object into java.util.Date
     * 
     * @return java.util.Date
     * @exception IllegalArgumentException
     *                if not a valid date
     */
    public java.util.Date getUtilDate()
    {
        return new java.util.Date(this.getTimeInMillis());
    }

    /**
     * Converts the Day Object into java.sql.Timestamp
     * 
     * @return java.sql.Timestamp
     * @exception IllegalArgumentException
     *                if unable to convert
     */
    public java.sql.Timestamp getTimestamp()
    {
        return new java.sql.Timestamp(this.getTimeInMillis());
    }

    /**
     * Computes the number of days between two dates
     * 
     * @return true if this is a valid date
     * @exception IllegalArgumentException
     *                if not a valid date
     */
    private boolean isValid()
    {
    	if (year < 0 || month < 0 || day < 0 || hour < 0 || minutes < 0 || seconds < 0 || milliseconds < 0) {
    		throw new IllegalArgumentException();
    	}
        Day t = new Day();
        t.fromJulian(this.toJulian());
        return t.day == day && t.month == month && t.year == year;
    }

    /**
     * Converts the Calendar Date to Julian Day.
     * 
     * @return The Julian day number that begins at noon of this day Positive
     *         year signifies A.D., negative year B.C. Remember that the year
     *         after 1 B.C. was 1 A.D.
     * 
     * A convenient reference point is that May 23, 1968 noon is Julian day
     * 2440000.
     * 
     * Julian day 0 is a Monday.
     * 
     * This algorithm is from Press et al., Numerical Recipes in C, 2nd ed.,
     * Cambridge University Press 1992
     */
    public int toJulian()
    {
        int jy = year;
        if (year < 0)
            jy++;
        int jm = month;
        if (month > 2)
            jm++;
        else
        {
            jy--;
            jm += 13;
        }
        int jul = (int) (java.lang.Math.floor(365.25 * jy)
                + java.lang.Math.floor(30.6001 * jm) + day + 1720995.0);

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

    public void fromJulian(int j)
    {
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
        day = jb - jd - (int) (30.6001 * je);
        month = je - 1;
        if (month > 12)
            month -= 12;
        year = jc - 4715;
        if (month > 2)
            --year;
        if (year <= 0)
            --year;
    }

    /**
     * Returns the maximum number of days in a month.
     * 
     * @return int
     */
    public int getMonthDays()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setTime(this.getUtilDate());
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Sets this day with the given year, month and day.
     * 
     * Internally calls {@link #setDay(int, int, int, int, int, int)}
     * and passes ZERO for hour, minutes and seconds.
     * 
     * @param year
     * @param month
     * @param day
     */
    public void setDay(int year, int month, int day)
    {
        setDay(year, month, day, 0, 0, 0);
    }

    /**
     * Sets the Day for the given year, month, day, hour, minutes and seconds.
     * 
     * Internally calls {@link #setDay(int, int, int, int, int, int)}
     * and passes ZERO for milliseconds.
     *
     * @param year
     * @param month 
     * @param day 
     * @param hour 
     * @param minutes
     * @param seconds
     */
    public void setDay(int year, int month, int day, int hour, int minutes,
            int seconds)
    {
        setDay(year, month, day, hour, minutes, seconds, 0);
    }

    /**
     * Sets the Day for the given year, month, day, hour, minutes, seconds and milliseconds.
     * 
     * @param year
     * @param month 
     * @param day 
     * @param hour 
     * @param minutes
     * @param seconds
     * @param milliseconds
     */
    public void setDay(int year, int month, int day, int hour, int minutes,
            int seconds, int milliseconds)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        if (!isValid())
            throw new IllegalArgumentException();
        if (hour < 0 || hour > 23)
            throw new IllegalArgumentException();
        if (minutes < 0 || minutes > 59)
            throw new IllegalArgumentException();
        if (seconds < 0 || seconds > 59)
            throw new IllegalArgumentException();
        if (milliseconds < 0 || milliseconds > 999)
            throw new IllegalArgumentException();
    }

    /**
     * Sets this day with the given Day.
     * @param pday Day.
     */
    public void setDay(Day pday)
    {
        this.setDay(pday.getYear(), pday.getMonth(), pday.getDay(), pday.getHour(), pday
                .getMinutes(), pday.getSeconds(),pday.getMilliseconds());
    }

    /**
     * Sets the current day to a new day.
     * 
     * @param psdt
     *            java.sql.Date
     */
    public void setDay(java.sql.Date psdt)
    {
        setDay(psdt.getTime());
    }

    /**
     * Sets the current day to a new day.
     * 
     * @param pudt
     *            java.util.Date
     */
    public void setDay(java.util.Date pudt)
    {
        setDay(pudt.getTime());
    }

    /**
     * Sets the current day to a new day.
     * 
     * @param pts
     *            java.sql.Timestamp
     */
    public void setDay(java.sql.Timestamp pts)
    {
        setDay(pts.getTime());
    }
    
    /**
     * Sets the day for the given time.
     * @param time
     */
    public void setDay(long time) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setTimeInMillis(time);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);
        milliseconds = calendar.get(Calendar.MILLISECOND);
    }
    
    /**
     * Clears the current fields and sets the Day to the current time.
     * 
     * Internally sets the time to {@link System#currentTimeMillis()}
     * toString() 
     */
    public void clear() {
        setDay(System.currentTimeMillis());
    }

    /**
     * Returns the maximum value for the given field.
     * @param field Calendar values for fields.
     * @return int
     */
    public int getMaximum(int field)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(year, month - 1, day, hour, minutes, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        return (calendar.getActualMaximum(field));
    }

    /**
     * Returns the minimum value for the given field.
     * @param field Calendar constants for fields.
     * @return int.
     */
    public int getMinimum(int field)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(year, month - 1, day, hour, minutes, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        return (calendar.getActualMinimum(field));
    }

    /**
     * Returns the time in milliseconds.
     * 
     * @return time in millis
     * @see Calendar#getTimeInMillis()
     */
    public long getTimeInMillis() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(year, month - 1, day, hour, minutes, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        return calendar.getTimeInMillis();
    }
    
    /**
     * Displays the time difference between two dates.
     * 
     * @param day1 Day.
     * @param day2 Day to be compared with.
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
     * @param ts1 Timestamp.
     * @param ts2 Timestamp to be compared with.
     * @return String String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     */
    public static String verboseTimeDifference(Timestamp ts1, Timestamp ts2) {
        return verboseTimeDifference(new Day(ts1), new Day(ts2));
    }
    
    /**
     * Displays the time difference between two {@link java.sql.Date}
     * 
     * Internally calls {@link #verboseTimeDifference(Day, Day)}
     * 
     * @param dt1 Date.
     * @param dt2 Date to be compared with.
     * @return String String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     */
    public static String verboseTimeDifference(java.sql.Date dt1, java.sql.Date dt2) {
        return verboseTimeDifference(new Day(dt1), new Day(dt2));
    }
    
    /**
     * Displays the time difference between two {@link java.util.Date}s.
     * 
     * Internally calls {@link #verboseTimeDifference(Day, Day)}
     * 
     * @param dt1 Date.
     * @param dt2 Date to be compared with.
     * @return String String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     */
    public static String verboseTimeDifference(java.util.Date dt1, java.util.Date dt2) {
        return verboseTimeDifference(new Day(dt1), new Day(dt2));
    }
    
    /**
     * Displays the time difference between two numbers representing time.
     * 
     * @param time1 Time.
     * @param time2 Time to be compared with.
     * @return String representation of the time difference.
     * @see #verboseTimeDifference(Day, Day)
     * @see Calendar#getTimeInMillis()
     */
    public static String verboseTimeDifference(long time1, long time2) {
        Day day1 = new Day(time1);
        Day day2 = new Day(time2);
        assert(time1 == day1.getTimeInMillis());
        assert(time2 == day2.getTimeInMillis());
        return verboseTimeDifference(day1, day2);
    }
    
    /**
     * Verbose date difference in <i>Y</i>ears, <i>M</i>onths, <i>D</i>ays, <i>H</i>ours, <i>M</i>inutes and <i>S</i>econds.
     * 
     * <code><br>
     * Day d1 = new Day(1972, 3, 27, 22, 32, 8);<br>
     * Day d2 = new Day(1984, 2, 11, 23, 59, 59);<br>
     * </code>
     * The output will be 11<i>y</i> 10<i>M</i> 13<i>d</i> 1<i>H</i> 27<i>m</i> 51<i>s</i> 212<i>S</i>
     * 
     * @param day to be compared with. 
     * @return String
     */
    public String verboseTimeDifference(Day day) {
        Day d = new Day(day.getTimeInMillis());
        d.setLastDayAdjustment(false);
        assert(d.getTimeInMillis() == day.getTimeInMillis());
        StringBuffer strBuffer = new StringBuffer();
        int multifyFactor = 1;
        if (this.compareInSeconds(d) < 0) {
            multifyFactor=-1;
        }
        //multiply factor is most important and this determines if the date is to be advanced in future or advanced backward.
        int years = 0;
        while (true) {
            d.advance(Calendar.YEAR, 1 * multifyFactor);
            if (this.compareInSeconds(d)*multifyFactor < 0) {
                break;
            }
            years++;
        }
        d.advance(Calendar.YEAR, -1 * multifyFactor);
        assert(d.getTimeInMillis() == day.getTimeInMillis());
        if (years > 0) {
            strBuffer.append(years);
            strBuffer.append("y ");
        }
        int months = 0;
        while (true) {
            d.advance(Calendar.MONTH, 1 * multifyFactor);
            if (this.compareInSeconds(d)*multifyFactor < 0) {
                break;
            }
            months++;
        }
        d.advance(Calendar.MONTH, -1 * multifyFactor);
        assert(d.getTimeInMillis() == day.getTimeInMillis());
        if (months > 0) {
            strBuffer.append(months);
            strBuffer.append("M ");
        }
        long time = this.getTimeInMillis() - d.getTimeInMillis();
        long days = time/(1000*60*60*24);
        if (days != 0) {
            strBuffer.append(days*(days<0?-1:1));
            strBuffer.append("d ");
        }
        time-=days*(1000*60*60*24);
        long hours = time/(1000*60*60);
        if (hours != 0) {
            strBuffer.append(hours*(hours<0?-1:1));
            strBuffer.append("H ");
        }
        time-=hours*(1000*60*60);
        long minutes = time/(1000*60);
        if (minutes != 0) {
            strBuffer.append(minutes*(minutes<0?-1:1));
            strBuffer.append("m ");
        }
        time-=minutes*(1000*60);
        long seconds = time/1000;
        if (seconds != 0) {
            strBuffer.append(seconds*(seconds<0?-1:1));
            strBuffer.append("s ");
        }
        time-=seconds*1000;
        long milliseconds = time;
        if (milliseconds != 0) {
            strBuffer.append(milliseconds*(milliseconds<0?-1:1));
            strBuffer.append("S");
        }
        if (strBuffer.length() == 0) { //no time difference
            return "0S";
        }
        return strBuffer.toString().trim(); 
    }
    
    public void setToStringFormat(SimpleDateFormat format) {
    	if (format == null) {
    		throw new NullPointerException("Format cannot be blank while explicitly calling setToStringFormat");
    	}
        objSimpleDateFormat = (SimpleDateFormat) format.clone();
    }
    
    /**
     * Returns a GregorianCalenar for this day. 
     * @return GregorianCalendar
     */
    public GregorianCalendar getCalendar() {
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.setTimeInMillis(getTimeInMillis());
		return calendar;
    }
    
    /**
     * Returns true if this day is before the given day.
     * @param day to be compared.
     * @return boolean
     */
    public boolean isBefore(Day day) {
    	return (compareInSeconds(day) < 0);
    }
    
    /**
     * Returns true if this day is before the given date.
     * @param date to be compared.
     * @return boolean
     */
    public boolean isBefore(java.util.Date date) {
    	return (compareInSeconds(date) < 0);
    }
    
    /**
     * Returns true if this day is before the given timestamp.
     * @param timestamp to be compared with
     * @return boolean
     */
    public boolean isBefore(java.sql.Timestamp timestamp) {
    	return (compareInSeconds(timestamp) < 0);
    }
    
    /**
     * Returns true if this day is before the given sql date.
     * @param sqlDate to be compared with
     * @return boolean
     */
    public boolean isBefore(java.sql.Date sqlDate) {
    	return (compareInSeconds(sqlDate) < 0);
    }
    
    /**
     * Returns true if this day is before the given time
     * @param time to be compared with.
     * @return boolean
     */
    public boolean isBefore(long time) {
    	return (compareInSeconds(time) < 0);
    }
    
    /**
     * Returns true if this day is greater than the given day.
     * @param day to be compared with.
     * @return boolean
     */
    public boolean isAfter(Day day) {
    	return (compareInSeconds(day) > 0);
    }
    
    /**
     * Returns true if this day is greater than the given date.
     * @param date to be compared with.
     * @return boolean
     */
    public boolean isAfter(java.util.Date date) {
    	return (compareInSeconds(date) > 0);
    }
    
    /**
     * Returns true if this day is greater than the given time stamp.
     * @param timestamp to be compared with.
     * @return boolean
     */
    public boolean isAfter(java.sql.Timestamp timestamp) {
    	return (compareInSeconds(timestamp) > 0);
    }
    
    /**
     * Returns true if this day is greater than the given time sql date.
     * @param sqlDate to be compared with.
     * @return boolean
     */
    public boolean isAfter(java.sql.Date sqlDate) {
    	return (compareInSeconds(sqlDate) > 0);
    }
    
    /**
     * Returns true if this day is greater than the given time.
     * @param time to be compared with.
     * @return boolean
     */
    public boolean isAfter(long time) {
    	return (compareInSeconds(time) > 0);
    }
    
    /**
     * Returns true if the year of this day is a leap year.
     * @return boolean
     */
    public boolean isLeapYear() {
    	return Day.isLeapYear(year);
    }
    
    /**
     * Returns true if the given year is a leap year or else returns false.
     * A leap year is an year where the february month has 29 days.
     * 
     * @param yyyy
     * @return boolean
     */
    public static boolean isLeapYear(int yyyy) {
    	Day day = new Day(yyyy, 2, 1);
    	return day.getMaximum(Calendar.DATE) == 29;
    }
    
    /**
     * Denotes day.
     * Comment for <code>day</code>
     */
    private int          day;

    /**
     * Denotes month.
     * Comment for <code>month</code>
     */
    private int          month;

    /**
     * Denotes year.
     * Comment for <code>year</code>
     */
    private int          year;

    /**
     * Denotes hour.
     * Comment for <code>hour</code>
     */
    private int          hour;

    /**
     * Denotes minutes.
     * Comment for <code>minutes</code>
     */
    private int          minutes;
    

    /**
     * Denotes seconds.
     * Comment for <code>seconds</code>
     */
    private int         seconds;
    
    /**
     * Denotes miliseconds.
     */
    private int         milliseconds;

    /**
     * Stores whether the fixed date is to be considered of not.
     * A fixed date is that if a schedule is set on Feb 28 and it is to be advanced by one month
     * then next month date will be Mar 28 and not Mar 31. So 28 is the date that is fixed.
     * This is applicable to advance method for fields like {@link Calendar#MONTH} and 
     * {@link Calendar#YEAR}. 
     */
    private boolean bFixedDate_ = false;
    

    /**
     * SimpleFormat object to convert the Day to a readable text representation of the Day.
     * @see SimpleDateFormat
     */
    private SimpleDateFormat objSimpleDateFormat = null;
    
    
    private final int initDay;
    
}

