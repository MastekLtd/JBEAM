/**
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
 *
 */
		/**
		 * List of timezone abbreviations and matching GMT times.
		 * Modified form original code at:
		 * http://blog.flexexamples.com/2009/07/27/parsing-dates-with-timezones-in-flex/
		 * */
		private static var timeZoneAbbreviations:Array = [
			/* Hawaii-Aleutian Standard/Daylight Time */
			{abbr:"HAST", zone:"GMT-10:00"},
			{abbr:"HADT", zone:"GMT-09:00"},
			/* Alaska Standard/Daylight Time */
			{abbr:"AKST", zone:"GMT-09:00"},
			{abbr:"ASDT", zone:"GMT-08:00"},
			/* Pacific Standard/Daylight Time */
			{abbr:"PST", zone:"GMT-08:00"},
			{abbr:"PDT", zone:"GMT-07:00"},
			/* Mountain Standard/Daylight Time */
			{abbr:"MST", zone:"GMT-07:00"},
			{abbr:"MDT", zone:"GMT-06:00"},
			/* Central Standard/Daylight Time */
			{abbr:"CST", zone:"GMT-06:00"},
			{abbr:"CDT", zone:"GMT-05:00"},
			/* Eastern Standard/Daylight Time */
			{abbr:"EST", zone:"GMT-05:00"},
			{abbr:"EDT", zone:"GMT-04:00"},
			/* Atlantic Standard/Daylight Time */
			{abbr:"AST", zone:"GMT-04:00"},
			{abbr:"ADT", zone:"GMT-03:00"},
			/* Newfoundland Standard/Daylight Time */
			{abbr:"NST", zone:"GMT-03:30"},
			{abbr:"NDT", zone:"GMT-02:30"},
			/* London Standard/Daylight Time */
			{abbr:"BST", zone:"GMT+01:00"},
			{abbr:"GMT", zone:"GMT+00:00"}
		];
		
		/**
		 * Return local system timzezone abbreviation.
		 * */
		public static function getTimeZone():String
		{
			var nowDate:Date = new Date();
			nowDate.setTime(nowDate.getTime() + nowDate.getTimezoneOffset() * 1000 * 60 + -28800000);
			var DST:Boolean = isObservingDTS();
			var GMT:String = buildTimeZoneDesignation(nowDate, DST);
			
			return parseTimeZoneFromGMT(GMT);
		}
		
		/**
		 * Determines if local computer is observing daylight savings time for US and London.
		 * */
		public static function isObservingDTS():Boolean
		{
			var winter:Date = new Date(2011, 01, 01); // after daylight savings time ends
			var summer:Date = new Date(2011, 07, 01); // during daylight savings time
			var now:Date = new Date();
			
			var winterOffset:Number = winter.getTimezoneOffset();
			var summerOffset:Number = summer.getTimezoneOffset();
			var nowOffset:Number = now.getTimezoneOffset();
			
			if((nowOffset == summerOffset) && (nowOffset != winterOffset)) {
				return true;
			} else {
				return false;
			}	
		}
		
		/**
		 * Goes through the timze zone abbreviations looking for matching GMT time.
		 * */
		private static function parseTimeZoneFromGMT(gmt:String):String 
		{
			for each (var obj:Object in timeZoneAbbreviations) {
				if(obj.zone == gmt){
					return obj.abbr;
				}
			}
			return gmt;
		}
		
		/**
		 * Goes through the timze zone abbreviations looking for matching GMT time.
		 * */
		private static function parseTimeZoneShortName(shortName:String):String 
		{
			for each (var obj:Object in timeZoneAbbreviations) {
				if(obj.abbr == shortName){
					return obj.zone;
				}
			}
			return shortName;
		}
		
		/**
		 * Method to build GMT from date and timezone offset and accounting for daylight savings.
		 * 
		 * Originally code befor modifications:
		 * http://flexoop.com/2008/12/flex-date-utils-date-and-time-format-part-i/
		 * */
		private static function buildTimeZoneDesignation( date:Date, dts:Boolean  ):String 
		{
			if ( !date ) {
				return "";
			}
			
			var timeZoneAsString:String = "GMT";
			var timeZoneOffset:Number;
			
			// timezoneoffset is the number that needs to be added to the local time to get to GMT, so
			// a positive number would actually be GMT -X hours
			if ( date.getTimezoneOffset() / 60 > 0 && date.getTimezoneOffset() / 60 < 10 ) {
				timeZoneOffset = (dts)? ( date.getTimezoneOffset() / 60 ):( date.getTimezoneOffset() / 60 - 1 );
				timeZoneAsString += "-0" + timeZoneOffset.toString();
			} else if ( date.getTimezoneOffset() < 0 && date.timezoneOffset / 60 > -10 ) {
				timeZoneOffset = (dts)? ( date.getTimezoneOffset() / 60 ):( date.getTimezoneOffset() / 60 + 1 );
				timeZoneAsString += "+0" + ( -1 * timeZoneOffset ).toString();
			} else {
				timeZoneAsString += "+00";
			}
			
			// add zeros to match standard format
			timeZoneAsString += "00";
			return timeZoneAsString;
		}