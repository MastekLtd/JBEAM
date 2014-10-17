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

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

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
public class TestDay extends TestCase {

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
	 * Test method for {@link stg.utils.Day#Day()}.
	 */
	public void testDay() {
		Calendar cal = Calendar.getInstance();
		Day day = Day.createDay();
		assertEquals(cal.get(Calendar.YEAR), day.getYear());
		assertEquals(cal.get(Calendar.MONTH)+1, day.getMonth());
		assertEquals(cal.get(Calendar.DATE), day.getDay());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), day.getHour());
		assertEquals(cal.get(Calendar.MINUTE), day.getMinutes());
		assertEquals(cal.get(Calendar.SECOND), day.getSeconds());
		assertTrue(cal.get(Calendar.MILLISECOND)<= day.getMilliseconds());
	}

	/**
	 * Test method for {@link stg.utils.Day#Day(int, int, int)}.
	 */
	public void testDayIntIntInt() {
		Day day = Day.createDay(2011, 11, 21);
		assertEquals(2011, day.getYear());
		assertEquals(11, day.getMonth());
		assertEquals(21, day.getDay());
		assertEquals(0, day.getHour());
		assertEquals(0, day.getMinutes());
		assertEquals(0, day.getSeconds());
		assertEquals(0, day.getMilliseconds());
		try {
			day = Day.createDay(2011, 2, 31);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
		try {
			day = Day.createDay(-2011, 2, 1);
			fail("Should have thrown exception" + day.toString());
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
		try {
			day = Day.createDay(2011, -2, 1);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
		try {
			day = Day.createDay(2011, 2, -1);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
		try {
			day = Day.createDay(-2011, -2, -1);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
	}

	/**
	 * Test method for {@link stg.utils.Day#Day(int, int, int, int, int, int)}.
	 */
	public void testDayIntIntIntIntIntInt() {
		Day day = Day.createDay(2011, 11, 21, 11, 23, 49);
		assertEquals(2011, day.getYear());
		assertEquals(11, day.getMonth());
		assertEquals(21, day.getDay());
		assertEquals(11, day.getHour());
		assertEquals(23, day.getMinutes());
		assertEquals(49, day.getSeconds());
		assertEquals(0, day.getMilliseconds());
		try {
			day = Day.createDay(2011, 11, 21, 25, 11, 59);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
		try {
			day = Day.createDay(2011, 11, 21, 12, 60, 59);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
		try {
			day = Day.createDay(2011, 11, 21, 25, 11, 60);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
		try {
			day = Day.createDay(2011, 11, 21, -12, -11, -11);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
	}

	/**
	 * Test method for {@link stg.utils.Day#Day(int, int, int, int, int, int, int)}.
	 */
	public void testDayIntIntIntIntIntIntInt() {
		Day day = Day.createDay(2011, 11, 21, 11, 23, 49, 356);
		assertEquals(2011, day.getYear());
		assertEquals(11, day.getMonth());
		assertEquals(21, day.getDay());
		assertEquals(11, day.getHour());
		assertEquals(23, day.getMinutes());
		assertEquals(49, day.getSeconds());
		assertEquals(356, day.getMilliseconds());
		try {
			day = Day.createDay(2011, 11, 21, 11, 11, 11, -356);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue("Caught IllegalArgumentException", true);
		}
	}

	/**
	 * Test method for {@link stg.utils.Day#Day(java.sql.Date)}.
	 */
	public void testDayDate() {
		Calendar cal = Calendar.getInstance();
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		Day day = Day.createDay(date);
		assertEquals(cal.get(Calendar.YEAR), day.getYear());
		assertEquals(cal.get(Calendar.MONTH)+1, day.getMonth());
		assertEquals(cal.get(Calendar.DATE), day.getDay());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), day.getHour());
		assertEquals(cal.get(Calendar.MINUTE), day.getMinutes());
		assertEquals(cal.get(Calendar.SECOND), day.getSeconds());
		assertTrue(cal.get(Calendar.MILLISECOND)<= day.getMilliseconds());
	}

	/**
	 * Test method for {@link stg.utils.Day#Day(java.sql.Timestamp)}.
	 */
	public void testDayTimestamp() {
		Calendar cal = Calendar.getInstance();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
		Day day = Day.createDay(timestamp);
		assertEquals(cal.get(Calendar.YEAR), day.getYear());
		assertEquals(cal.get(Calendar.MONTH)+1, day.getMonth());
		assertEquals(cal.get(Calendar.DATE), day.getDay());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), day.getHour());
		assertEquals(cal.get(Calendar.MINUTE), day.getMinutes());
		assertEquals(cal.get(Calendar.SECOND), day.getSeconds());
		assertTrue(cal.get(Calendar.MILLISECOND)<= day.getMilliseconds());
	}

	/**
	 * Test method for {@link stg.utils.Day#Day(java.util.Date)}.
	 */
	public void testDayDate1() {
		Calendar cal = Calendar.getInstance();
		java.util.Date date = new java.util.Date(cal.getTimeInMillis());
		Day day = Day.createDay(date);
		assertEquals(cal.get(Calendar.YEAR), day.getYear());
		assertEquals(cal.get(Calendar.MONTH)+1, day.getMonth());
		assertEquals(cal.get(Calendar.DATE), day.getDay());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), day.getHour());
		assertEquals(cal.get(Calendar.MINUTE), day.getMinutes());
		assertEquals(cal.get(Calendar.SECOND), day.getSeconds());
		assertTrue(cal.get(Calendar.MILLISECOND)<= day.getMilliseconds());
	}

	/**
	 * Test method for {@link stg.utils.Day#Day(long)}.
	 */
	public void testDayLong() {
		long time = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		Day day = Day.createDay(time);
		assertEquals(cal.get(Calendar.YEAR), day.getYear());
		assertEquals(cal.get(Calendar.MONTH)+1, day.getMonth());
		assertEquals(cal.get(Calendar.DATE), day.getDay());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), day.getHour());
		assertEquals(cal.get(Calendar.MINUTE), day.getMinutes());
		assertEquals(cal.get(Calendar.SECOND), day.getSeconds());
		assertEquals(cal.get(Calendar.MILLISECOND), day.getMilliseconds());
	}

	/**
	 * Test method for {@link stg.utils.Day#advance(int)}.
	 */
	public void testAdvanceInt() {
		Day day = Day.createDay(2011, 12, 31);
		Day newDay = day.advance(1);
		assertEquals(2012, newDay.getYear());
		assertEquals(1, newDay.getMonth());
		assertEquals(1, newDay.getDay());
		assertEquals(2011, day.getYear());
		assertEquals(12, day.getMonth());
		assertEquals(31, day.getDay());

		day = Day.createDay(2011, 2, 28);
		newDay = day.advance(1);
		assertEquals(2011, newDay.getYear());
		assertEquals(3, newDay.getMonth());
		assertEquals(1, newDay.getDay());
        assertEquals(2011, day.getYear());
        assertEquals(2, day.getMonth());
        assertEquals(28, day.getDay());

		day = Day.createDay(2011, 2, 20);
		newDay = day.advance(1);
		assertEquals(2011, newDay.getYear());
		assertEquals(2, newDay.getMonth());
		assertEquals(21, newDay.getDay());

		assertEquals(2011, day.getYear());
		assertEquals(2, day.getMonth());
		assertEquals(20, day.getDay());
		
		day = Day.createDay(2011, 12, 31);
		newDay = day.advance(-1);
		assertEquals(2011, newDay.getYear());
		assertEquals(12, newDay.getMonth());
		assertEquals(30, newDay.getDay());

		day = Day.createDay(2011, 1, 1);
		newDay = day.advance(-1);
		assertEquals(2010, newDay.getYear());
		assertEquals(12, newDay.getMonth());
		assertEquals(31, newDay.getDay());
		
	}


	/**
	 * Test method for {@link stg.utils.Day#daysBetween(stg.utils.Day)}.
	 */
	public void testDaysBetweenDay() {
		Day day1 = Day.createDay(2011, 1, 1);
		Day day2 = Day.createDay(2011, 1, 31);
		assertEquals(-30, day1.daysBetween(day2));
		assertEquals(30, day2.daysBetween(day1));
	}

	/**
	 * Test method for {@link stg.utils.Day#monthsBetween(stg.utils.Day)}.
	 */
	public void testMonthsBetweenDay() {
		Day day1 = Day.createDay(2011, 1, 1);
		Day day2 = Day.createDay(2011, 1, 31);
		assertEquals(0, day1.monthsBetween(day2));
		day1 = Day.createDay(2011, 1, 1);
		day2 = Day.createDay(2011, 2, 1);
		assertEquals(1, day1.monthsBetween(day2));
	}

	/**
	 * Test method for {@link stg.utils.Day#compareTo(stg.utils.Day)}.
	 */
	public void testCompareToDay() {
		Day day1 = Day.createDay();
		Day day2 = Day.createDay(1882, 1, 31);
		assertTrue(day1.compareTo(day2) > 0);
		assertTrue(day2.compareTo(day1) < 0);
		day1 = Day.createDay(2011, 1, 31);
		day2 = Day.createDay(2011, 1, 31, 5, 5, 5);
		assertFalse(day1.compareTo(day2) == 0);
	}
	
	@Test
	public void testCompareToDayWithTimezone() {
	    Day sourceDay = Day.createDay(2013,3,30, 9, 10, 0,0);
	    Day targetDay = Day.createDay(2013,3,29, 23, 40, 0, 0, TimeZone.getTimeZone("America/New_York"));
//	    System.out.println("Kedar " + sourceDay + "\t" + targetDay);
//	    System.out.println(sourceDay.compareTo(targetDay));
	    assertTrue(sourceDay.compareTo(targetDay) == 0);
	}

	/**
	 * Test method for {@link stg.utils.Day#compareInSeconds(stg.utils.Day)}.
	 */
	public void testCompareInSecondsDay() {
		Day day1 = Day.createDay();
		Day day2 = Day.createDay(1882, 1, 31);
		assertTrue(day1.compareTo(day2) > 0);
		assertTrue(day2.compareTo(day1) < 0);
		day1 = Day.createDay(2011, 1, 31);
		day2 = Day.createDay(2011, 1, 31, 5, 5, 5);
		assertTrue(day1.compareTo(day2) < 0);
	}

	/**
	 * Test method for {@link stg.utils.Day#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		Day day1 = Day.createDay(2011, 1, 31);
		Day day2 = Day.createDay(2011, 1, 31);
		assertEquals(day1, day2);
		day1 = Day.createDay(2011, 1, 31);
		day2 = Day.createDay(2011, 1, 31, 5, 5, 5);
		assertNotSame(day1, day2);
	}

	/**
	 * Test method for {@link stg.utils.Day#isBefore(stg.utils.Day)}.
	 */
	public void testIsBeforeDay() {
		Day day1 = Day.createDay(2001, 1, 3);
		Day day2 = Day.createDay();
		assertTrue(day1.isBefore(day2));
		
		day1 = Day.createDay(2001, 1, 3);
		day2 = Day.createDay(2001, 1, 3, 1,2,3);
		assertTrue(day1.isBefore(day2));
	}

	/**
	 * Test method for {@link stg.utils.Day#isAfter(stg.utils.Day)}.
	 */
	public void testIsAfterDay() {
		Day day1 = Day.createDay(2001, 1, 3);
		Day day2 = Day.createDay();
		assertTrue(day2.isAfter(day1));
		day1 = Day.createDay(2001, 1, 3);
		day2 = Day.createDay(2001, 1, 3, 1,2,3);
		assertTrue(day2.isAfter(day1));
	}


	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	public void testToString() {
		Day day = Day.createDay(2011, 3, 29, 16, 27, 22, 693);
//		System.out.println(day.toString());
//		assertEquals(day.getUtilDate().toString(), day.toString());
//		assertEquals("Day[Tuesday, 29 Mar(03) of 2011, Hour 16, Minutes 27, Seconds 22, Milliseconds 693]", day.toString());
//		day.setToStringFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));
//		assertEquals("2011-03-29 16:27:22:693", day.toString());
//		day.clear();
//		assertNotSame("2011-03-29 16:27:22:693", day.toString());
	}
	
	public void testIsLeapYear() {
		assertFalse(Day.isLeapYear(1800));
		assertFalse(Day.isLeapYear(1900));
		assertFalse(Day.isLeapYear(2100));
		assertTrue(Day.isLeapYear(2000));
		assertTrue(Day.isLeapYear(2004));
		assertTrue(Day.isLeapYear(2008));
		assertFalse(Day.isLeapYear(2011));
		assertTrue(Day.isLeapYear(2012));
	}

	public void testDST() {
		Day day = Day.createDay(2011, 3, 12, 2, 30, 0);
		day.advance(1);
		
//        GregorianCalendar calendar = new GregorianCalendar();
//        calendar.setLenient(false);
//        calendar.clear();
////        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(2011, 2, 12, 2, 0, 0);
//        Day day3 =  Day.createDay(calendar.getTimeInMillis());
//        System.out.println(day + "\n" + day3);
////        calendar.setTimeInMillis(day.getTimeInMillis());
//        System.out.println("YEAR=" + calendar.get(Calendar.YEAR) + " MONTH=" + (calendar.get(Calendar.MONTH) + 1) 
//        		+ " DATE=" + calendar.get(Calendar.DATE) + " HOUR=" + calendar.get(Calendar.HOUR_OF_DAY) 
//        		+ " MINUTE=" + calendar.get(Calendar.MINUTE) + " SECONDS=" + calendar.get(Calendar.SECOND)
//        		+ " MILLIS=" + calendar.get(Calendar.MILLISECOND));
//
//        calendar.add(Calendar.HOUR_OF_DAY, 1);
//        System.out.println("YEAR=" + calendar.get(Calendar.YEAR) + " MONTH=" + (calendar.get(Calendar.MONTH) + 1) 
//        		+ " DATE=" + calendar.get(Calendar.DATE) + " HOUR=" + calendar.get(Calendar.HOUR_OF_DAY) 
//        		+ " MINUTE=" + calendar.get(Calendar.MINUTE) + " SECONDS=" + calendar.get(Calendar.SECOND)
//        		+ " MILLIS=" + calendar.get(Calendar.MILLISECOND));
//		day3.advance(1);
//		System.out.println(day3);
	}
}
