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
package com.majescomastek.jbeam.common
{
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.ConfigParameter;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import flash.display.NativeWindowInitOptions;
	import flash.display.NativeWindowSystemChrome;
	import flash.display.NativeWindowType;
	import flash.errors.SQLError;
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	import flash.system.Capabilities;
	import flash.system.IME;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.DateField;
	import mx.events.ValidationResultEvent;
	import mx.formatters.DateFormatter;
	import mx.managers.ToolTipManager;
	import mx.resources.IResourceManager;
	import mx.resources.ResourceManager;
	import mx.rpc.Fault;
	import mx.utils.StringUtil;
	import mx.validators.Validator;
	
	[ResourceBundle('Application')]
	[ResourceBundle('FlexControls')]
	[ResourceBundle('SharedResources')]
	public class ScheduleUtils
	{
		public static function showWeekDays(str:String):String
		{
			var mytext:String = "";
			if(str.charAt(0) == "1")
				mytext = mytext + "Sun, ";
			if(str.charAt(1) == "1")
				mytext = mytext + "Mon, ";
			if(str.charAt(2) == "1")
				mytext = mytext + "Tue, ";
			if(str.charAt(3) == "1")
				mytext = mytext + "Wed, ";
			if(str.charAt(4) == "1")
				mytext = mytext + "Thu, ";
			if(str.charAt(5) == "1")
				mytext = mytext + "Fri, ";
			if(str.charAt(6) == "1")
				mytext = mytext + "Sat,";			
			
			if(mytext.lastIndexOf(",") != -1)
			{
				mytext = mytext.substring(0,mytext.lastIndexOf(","));
			}
			return mytext;
		}
		
		public static function showSkipSchedule(str:String):String
		{
			var mytext:String = "";
			switch(str)
			{
				case "SS":
				{
					mytext = "Skip Schedule"
					break;
				}
				case "D+":
				{
					mytext = "Postpone Day by 1 (D+)"
					break;
				}
				case "D-":
				{
					mytext = "Advance Day by 1 (D-)"
					break;
				}
				case "NA":
				{
					mytext = "Not Applicable"
					break;
				}
			}
			return mytext;
		}
		
		public static function showScheduleStatus(str:String):String
		{
			var mytext:String = "";
			switch(str)
			{
				case "A":
				{
					mytext = "Active"
					break;
				}
				case "F":
				{
					mytext = "Finished"
					break;
				}
				case "X":
				{
					mytext = "PRE Terminated"
					break;
				}
				case "C":
				{
					mytext = "User Cancelled"
					break;
				}
			}
			return mytext;
		}
		
		public static function showKeepAlive(str:String):String
		{
			var mytext:String = "";
			switch(str)
			{
				case "Y":
				{
					mytext = "Yes"
					break;
				}
				case "N":
				{
					mytext = "No"
					break;
				}
			}
			return mytext;
		}
	}
}