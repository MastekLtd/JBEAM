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
	import mx.collections.Sort;
	import mx.collections.SortField;
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
	public class CommonUtils
	{
		private static var BLAZEDS_EXCEPTION_STR_REGEX:RegExp = /There was an unhandled failure on the server. /;
		
		/** These are few of the metacharacters which have special meaning in regular expressions */
		private static var REGEX_METACHARACTERS:Array =
			['.', '?', '+', '-', ']', '[', '^', '$', '*', '|'];
		
		private static var REGEX_MAP:Object = {};
		
		private static const resourceManager:IResourceManager =
			ResourceManager.getInstance();
		
		private static var configParameter:ConfigParameter;
		
		private static const MINUS_PATTERN:RegExp = /-/g;
		
		public static function closeErrorTip():void{
			if(ToolTipManager.currentTarget != null)
			ToolTipManager.currentTarget.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OUT, true, false,ToolTipManager.currentTarget.x - 10,ToolTipManager.currentTarget.y - 10));
		}
		/**
		 * Retrieve a list of default drop down options to be used by the
		 * SelectOneComboBox custom combo box component.
		 */
		public static function getSelectOneDataprovider(labelField:String, valueField:String):ArrayCollection
		{
			var obj:Object = {};
			obj[valueField] = CommonConstants.DROP_DOWN_CODE_DEFAULT_VALUE;
			obj[labelField] = resourceManager.getString('jbeam', 'default_label');

			var list:ArrayCollection = new ArrayCollection([obj]);
			return list;
		}
		
		/**
		 * Convert a given numeric String to it's <code>Number</code> equivalent. This utility
		 * method is multi-lingual enabled in the sense that the conversion would be performed
		 * in a locale sensitive manner.<p>
		 * E.g. If for French locale the amount string is "2 00", the resulting number would be 2
		 * whereas for English locale "2.00" would give the same number.
		 * 
		 * @param inAmount The amount string.
		 * @return A <code>Number</code> equivalent of <code>inAmount</code>.
		 */
		public static function getAmountAsNumber(inAmount:String):Number
		{
			var thousandRegex:RegExp = getRegex(ResourceUtils.getString('SharedResources', 'thousandsSeparatorFrom'));
			var decimalRegex:RegExp = getRegex(ResourceUtils.getString('SharedResources', 'decimalSeparatorFrom'));
	        if(inAmount != null)
	        {			
				inAmount = inAmount.replace(thousandRegex, '');
				inAmount = inAmount.replace(decimalRegex, '.');
			}
			return new Number(inAmount);
		}
		
		private static function getRegex(separator:String):RegExp
		{
			// If the separator is a regex metacharacter, escape it before
			// creating a RegExp object to avoid unexpected behavior. Here we are
			// caching the created RegExp for performance reasons.
			var regexStr:String;
			if(REGEX_METACHARACTERS.indexOf(separator) > -1)
			{
				regexStr = '\\' + separator;
			}
			else
			{
				regexStr = separator;
			}
			var regex:RegExp = REGEX_MAP[regexStr];
			if(regex == null)
			{
				REGEX_MAP[regexStr] = new RegExp(regexStr,'g');
				return REGEX_MAP[regexStr];
			}
			else
			{
				return regex;
			}
		}
		
		//To show the validation message
		public static function showValidationMessages(arrContollers:Array):Boolean 
		{
			var isValidated:Boolean = true;
			for(var i:int=0; i < arrContollers.length ;i++) 
			{
				if (arrContollers[i].errorString != '') 
				{
					//If control is outside the viewable area then scroll & then show error popup
					arrContollers[i].dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OUT, true, false,arrContollers[i].x - 10,arrContollers[i].y - 10));
					arrContollers[i].dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OVER, true, false,arrContollers[i].x + 10,arrContollers[i].y + 10));
					arrContollers[i].setFocus();
					isValidated = false;
					break;
				}
			}
			return isValidated;
		}
		
		/**
		 * Function to show error message for component.
		 */
		public static function showValidationMessage(arrContollers:Array, isValidatorsArr:Boolean, isScroll:Boolean=false):void
		{
			var objCurrentControl:Object;
	
		    for(var i:int=0; i < arrContollers.length ;i++) 
			{
		    	if (isValidatorsArr == true)
		    		objCurrentControl = arrContollers[i].source;
		    	else
		    		objCurrentControl = arrContollers[i];
		    	
				if (objCurrentControl.errorString != '') 
				{
					//If control is outside the viewable area then scroll & then show error popup
//					if (isScroll == true) showControlInViewArea(arrContollers[i]);
					//objCurrentControl = arrContollers[i]; 
					
					objCurrentControl.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OUT, true, false,objCurrentControl.x - 10,objCurrentControl.y - 10));
			        objCurrentControl.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OVER, true, false,objCurrentControl.x + 10,objCurrentControl.y + 10));
			        objCurrentControl.setFocus();
					break;
				}                        
		    }
		}

		/**
		 * Remove error string for the controls which 
		 * are specified in the `arrInputControls'.
		 */
		public static function removeErrorString(arrInputControls:Array):void {
		    for(var i:int=0; i < arrInputControls.length ;i++) {
				arrInputControls[i].errorString = '';
			}
		}
		
		/**
		 * Function to hide all error messages.
		 */
		public static function hideValidationMessage(arrContollers:Array, isValidatorsArr:Boolean, isScroll:Boolean=false):void
		{
			var objCurrentControl:Object;
			
		    for(var i:int=0; i < arrContollers.length ;i++)
		    {
		    	if (isValidatorsArr == true)
		    		objCurrentControl = arrContollers[i].source;
		    	else
		    		objCurrentControl = arrContollers[i];
	
				if (objCurrentControl.errorString != '') 
				{
			        objCurrentControl.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OVER, true, false,objCurrentControl.x + 10,objCurrentControl.y + 10));
					objCurrentControl.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OUT, true, false,objCurrentControl.x - 10,objCurrentControl.y - 10));
					objCurrentControl.errorString = '';
				}                        
		    }
		}
	
		/**
		 * Function to change style of the amount if it negative.
		 * If amount is negative it also calls the formatAmount function
		 * to format the amount.
		 */
		public static function formatTextAmount(evt:Event):void {
			var txt:String = evt.currentTarget.text;
			if(txt.indexOf('-') >= 0) {
				evt.currentTarget.text = formatAmount(txt);;
				evt.currentTarget.styleName = 'txtNegative';
			}
		}
		
		/** 
		 * function to format the amount 
		 */
		private static function formatAmount(txt:String):String {
			if(!txt) return CommonConstants.BLANK_STRING;	
			if(txt.indexOf('-') >= 0 && txt.length != 1) {
				txt = txt.replace(MINUS_PATTERN, '');
				txt = CommonConstants.NEG_AMT_PRE_STRING + txt + CommonConstants.NEG_AMT_POST_STRING;
			}
			return txt;
		}
		
		/** 
		 * Function to format the date 
		 * If the format string is null then default date format will be 
		 * US date time format.
		 */
		public static function formatDate(date:Date, formatStr:String=null):String {				
			var txt:String = null;
			var dateFromatter:DateFormatter = new DateFormatter();
			if(formatStr == null)
			{
				dateFromatter.formatString = CommonConstants.US_DATETIME_FORMAT;
			}
			else
			{
				dateFromatter.formatString = formatStr;				
			}
			
			if(date == null)
			{
				txt = "---";
			}
			else
			{
				txt = dateFromatter.format(date);				
			}
			return txt;
		}
		
		/**
		 * This function used to convert the string which is different formats to Date object.
		 * Input parameters 1) Date which is in string type.
		 * 					2) Format of the input date.
		 * Output			Date object( with MM/DD/YYYY format).  
		 * 
		*/
		public static function getFormattedDate(valueString:String, inputFormat:String):Date
		{
			var mask:String
	        var temp:String;
	        var dateString:String = "";
	        var monthString:String = "";
	        var yearString:String = "";
	        var hourString:String = "";
	        var minString:String = "";
	        var secondString:String = "";
	        
	        var dateField:DateField = new DateField();
	        //dateField.monthNames = [resourceManager.getString('SharedResources','monthNames)];
	        var arrMonthNames:Array = dateField.monthNames; 
	        var j:int = 0;
	
	        var n:int = inputFormat.length;
	        for (var i:int = 0; i < n; i++,j++)
	        {
	            temp = "" + valueString.charAt(j);
	            mask = "" + inputFormat.charAt(i);
	
	            if (mask == "M")
	            {    
                    monthString += temp;
	            }
	            else if (mask == "D")
	            {
                    dateString += temp;
	            }
	            else if (mask == "Y")
	            {
	                yearString += temp;
	            }
	            else if (mask == "h")
	            {
	                hourString += temp;
	            }
	            else if (mask == "m")
	            {
	                minString += temp;
	            }
	             else if (mask == "s")
	            {
	                secondString += temp;
	            }
	        }
			if(monthString.length == 3){
				for(var k:int = 1; k <= arrMonthNames.length; k++){
					if(monthString == arrMonthNames[k-1].substring(0,3)){
						monthString = k.toString();
					}
				}
			}
	        temp = "" + valueString.charAt(inputFormat.length - i + j);
	        if (!(temp == "") && (temp != " "))
	            return null;
	
	        var monthNum:Number = Number(monthString);
	        var dayNum:Number = Number(dateString);
	        var yearNum:Number = Number(yearString);
	        var hourNum:Number = Number(hourString);
	        var minNum:Number = Number(minString);
	        var secondNum:Number = Number(secondString);
	
	        if (isNaN(yearNum) || isNaN(monthNum) || isNaN(dayNum) || isNaN(hourNum) || isNaN(minNum) || isNaN(secondNum))
	            return null;
	
	        if (yearString.length == 2 && yearNum < 70)
	            yearNum+=2000;
	
	        var newDate:Date = new Date(yearNum, monthNum-1, dayNum, hourNum, minNum, secondNum);
	
 	        /* if (dayNum != newDate.getDate() || (monthNum) != newDate.getMonth())
	            return null; */
	            
	        return newDate;
		}
		
		/**
		 * Function to format date in the format that service accepts. 
		 * @param value, date string
		 * @param inputFormat, format in which to format date
		 * @return formatted date
		 * 
		 */		
		public static function doRequestTypeFormat(value:Object, timeZoneShortName:String):String
		{
			var dateValue:Date = value as Date;
			if(dateValue == null)return "";
			
			var year:Number = dateValue.getFullYear();
			var month:String = dateValue.getMonth()<9?"0"+(dateValue.getMonth() + 1):""+(dateValue.getMonth() + 1);
			var day:String = dateValue.getDate()<10?"0"+dateValue.getDate():""+dateValue.getDate();
			var hour:String = dateValue.getHours()<10?"0"+dateValue.getHours():""+dateValue.getHours();
			var minutes:String = dateValue.getMinutes()<10?"0"+dateValue.getMinutes():""+dateValue.getMinutes();
			var sec:String = dateValue.getSeconds()<10?"0"+dateValue.getSeconds():""+dateValue.getSeconds();
			var milliseconds:String = dateValue.getMilliseconds().toString();
			milliseconds = '000'.substr(milliseconds.length) + milliseconds;
			var offset:String = parseTimeZoneShortName(timeZoneShortName);//getTimeOffset(dateValue.getTimezoneOffset());
			offset = offset.substr(3,offset.length);
			
			var dateString:String = year + '-' + month + '-' + day + '' +
				'T' + hour + ':' + minutes + ':' + sec;// + '' + offset;
			
			return dateString;
		}
		
		private static function getTimeOffset(offset:Number):String
		{
			var timeOffset:String = '';
			var offPostFix:String;
			
			if (offset / 60 > 0 && offset / 60 < 10 ) 
			{
				timeOffset += "-0" + (int(offset / 60) ).toString() + ':';
				offset = (offset % 60);
			} 
			else if (offset < 0 && offset / 60 > -10 ) 
			{
				timeOffset += "+0" + ( -1 * int(offset / 60) ).toString() + ':' ;
				offset = (-1 * offset % 60);
			}
			
			
			if(offset < 10)
			{
				offPostFix = '0'+offset.toString();
			}
			else
			{
				offPostFix = offset.toString();
			}
			timeOffset += offPostFix;
			
			return timeOffset;
		}
		
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
			{abbr:"GMT", zone:"GMT+00:00"},
			/* Indian Standard Time*/
			{abbr:"IST", zone:"GMT+05:30"}
		];
		
		
		/**
		 * Goes through the timze zone abbreviations looking for matching GMT time.
		 * */
		private static function parseTimeZoneShortName(shortName:String):String 
		{
			for each (var obj:Object in timeZoneAbbreviations) {
//				trace(obj.abbr +" == "+ shortName +" >>>> " + (obj.abbr == shortName));
				if(obj.abbr == shortName){
					return obj.zone;
				}
			}
			return shortName;
		}
		
		/**
		 * Validate and return the state of the controls whose validators
		 * are specified in the `validatorArray'.
		 */
		public static function validateControls(validatorArray:Array):Boolean
		{
//			// First enable the validators since they are disabled by default.
//			for(var i:uint = 0; i < validatorArray.length; ++i)
//			{
//				validatorArray[i].enabled = true;
//			}
//			
//			// Validate the Validators
//		    var validatorErrorArray:Array = Validator.validateAll(validatorArray);
//		    
//		    // Again disable the validators and revert them to their previous state
//		    for(var j:uint = 0; j < validatorArray.length; ++j)
//			{
//				validatorArray[j].enabled = false;
//			}
//			
//			// If the length of the Error Array is 0, it implies that the validation
//			// was successful.
//		    return validatorErrorArray.length == 0;
			
			
			
			
			var validator:Validator = null;
			for each(validator in validatorArray)
			{
				validator.enabled = true;
			}
			var errors:Array = Validator.validateAll(validatorArray);
			
//			if(showErrorToolTip)
//			{
				for each(var item:ValidationResultEvent in errors)
				{
					item.currentTarget.source.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OVER, true, false));
					item.currentTarget.source.setFocus();
					break;
				}
//			}
			for each(validator in validatorArray)
			{
				validator.enabled = false;
			}
			return errors.length == 0;

		}
		
		
		
		/**
		 * Display the error message based on the fault object received
		 * when the web service fails.
		 */
		public static function showWsFault(fault:Fault):void
		{
			var faultString:String = fault.faultString;
			var errorStr:String = faultString.replace(BLAZEDS_EXCEPTION_STR_REGEX, CommonConstants.BLANK_STRING);
			Alert.show(errorStr);
		}
		
		/**
		 * Display the error message based on the fault object received
		 * when the web service fails.
		 */
		public static function showDbFault(error:SQLError):void
		{
			var faultString:String = error.message;
			Alert.show(faultString);
		}
		
		public static var salutation:String;

		public static var parsedObject:Object = {};   
		public static var parsedInstructionObject:Object = {};   
		
		public static function parseString(parsableString:String):void {
		    try {
		        // Remove everything before the question mark, including
		        // the question mark.
		        var myPattern:RegExp = /.*\?/;  
		        parsableString = parsableString.replace(myPattern, "");
		
		        // Create an Array of name=value Strings.
		        var params:Array = parsableString.split("&");
		        
		       //*  // Print the params that are in the Array.
		        var keyStr:String;
		        // Set the values of the salutation.
		        for (var i:int = 0; i < params.length; i++) {
		            var tempA:Array = params[i].split("=");                        
		            if (tempA[0] == "bpmsInstcode") {
		                parsedObject.bpmsInstcode = tempA[1];
		            } 
		            if (tempA[0] == "bpmsBatchNo") {
		                parsedObject.bpmsBatchNo = tempA[1];
		            } 
		            if (tempA[0] == "bpmsBatchRevNo") {
		                parsedObject.bpmsBatchRevNo = tempA[1];
		            } 
		        }                    
		        if (StringUtil.trim(parsedObject.bpmsInstcode) != "" && 
		            StringUtil.trim(parsedObject.bpmsBatchNo) != "") {
		                salutation = "Welcome " + 
		                parsedObject.bpmsInstcode + " " + parsedObject.bpmsBatchNo + "!";                           
		        } else {
		            salutation = "Full name not entered."
		        } //*/
				//                    Alert.show(salutation);
		    } catch (e:Error) {
				//                    trace(e);
		    }
		}
		
		public static function parseInstructionString(parsableString:String):void {
		    try {
		        // Remove everything before the question mark, including
		        // the question mark.
		        var myPattern:RegExp = /.*\?/;  
		        parsableString = parsableString.replace(myPattern, "");
		
		        // Create an Array of name=value Strings.
		        var params:Array = parsableString.split("&");
		        
		       //*  // Print the params that are in the Array.
		        var keyStr:String;
		        // Set the values of the salutation.
		        for (var i:int = 0; i < params.length; i++) {
		            var tempA:Array = params[i].split("=");                        
		            if (tempA[0] == "instCode") {
		                parsedInstructionObject.instCode = tempA[1];
		            } 
		            if (tempA[0] == "instructionSeqNo") {
		                parsedInstructionObject.instructionSeqNo = tempA[1];
		            } 
		        }                    
		    } catch (e:Error) {
				//                    trace(e);
		    }
		}
		
		public static function replaceString(objString:String):String {		    
		    objString = objString.replace("_", " ");
		    return objString; 
		    
		}
		
		public static function getServerUrl():String
		{
			return CommonConstants.SERVER_URL;
		}
		
		public static function getLoggedInUser():UserProfile
		{
			return CommonConstants.USER_PROFILE;
		}
		
		public static function setLoggedInUser(userProfile:UserProfile):void
		{
			CommonConstants.USER_PROFILE = userProfile;
		}
		
		public static function getInstallationData():InstallationData
		{
			return CommonConstants.INSTALLATION_DATA;
		}
		
		public static function setInstallationData(installationData:InstallationData):void
		{
			CommonConstants.INSTALLATION_DATA = installationData;
		}
		
		public static function getBatchDetailsData():BatchDetailsData
		{
			return CommonConstants.BATCH_DETAILS_DATA;
		}
		
		public static function setBatchDetailsData(batchDetailsData:BatchDetailsData):void
		{
			CommonConstants.BATCH_DETAILS_DATA = batchDetailsData;
		}
		
		public static function getUserInstallationRoles():ArrayCollection
		{
			return CommonConstants.USER_INSTALLATION_ROLES;
		}
		
		public static function setUserInstallationRoles(userInstallationRoles:ArrayCollection):void
		{
			CommonConstants.USER_INSTALLATION_ROLES = userInstallationRoles;
		}
		
		public static function getHaveAdminRole():Boolean
		{
			return CommonConstants.HAVE_ADMIN_ROLE;
		}
		
		public static function setHaveAdminRole(value:Boolean):void
		{
			CommonConstants.HAVE_ADMIN_ROLE = value;
		}
		
		public static function getHaveUserRole():Boolean
		{
			return CommonConstants.HAVE_USER_ROLE;
		}
		public static function setHaveUserRole(value:Boolean):void
		{
			CommonConstants.HAVE_USER_ROLE = value;
		}
		
		public static function getHaveOperatorRole():Boolean
		{
			return CommonConstants.HAVE_OPERATOR_ROLE;
		}
		
		public static function setHaveOperatorRole(value:Boolean):void
		{
			CommonConstants.HAVE_OPERATOR_ROLE = value;
		}
		
		public static function resetRoles():void
		{
			CommonConstants.HAVE_ADMIN_ROLE = false;
			CommonConstants.HAVE_OPERATOR_ROLE = false;
			CommonConstants.HAVE_USER_ROLE = false;
		}
		
		/**
		 * Retrieve the default options to be used by native windows
		 * in this application.
		 */
		public static function getDefaultOptionsForNativeWindow():NativeWindowInitOptions
		{
			var nativeWindowInitOptions:NativeWindowInitOptions = new NativeWindowInitOptions();	 
			nativeWindowInitOptions.type = NativeWindowType.NORMAL;
			nativeWindowInitOptions.systemChrome = NativeWindowSystemChrome.STANDARD;
			nativeWindowInitOptions.maximizable = true;
			return nativeWindowInitOptions;
		}
		
		/**
		 * Retrieve the IME status in this application.
		 */
		public static function getIMEStatus():void
		{
			if (Capabilities.hasIME)
			{
			    if (IME.enabled)
			    {
			        trace("IME is installed and enabled.");
			    }
			    else
			    {
			        trace("IME is installed but not enabled. Please enable your IME and try again.");
			    }
			}
			else
			{
			    trace("IME is not installed. Please install an IME and try again.");
			}
		}
		
		public static function focusInHandlerNum(event:FocusEvent):void
		{
		    if (Capabilities.hasIME)
		    {
		        IME.enabled = false;
		    }
		}
		public static function focusOutHandlerNum(event:FocusEvent):void
		{
		    if (Capabilities.hasIME)
		    {
		        IME.enabled = true;
		    }
		}
		
		/**
		 * Enable only number entry as text input. No text is permitted.
		 */
		public static function enableNumberEntry(event:Event):void
		{
			event.currentTarget.addEventListener(FocusEvent.FOCUS_IN, focusInHandlerNum);
			event.currentTarget.addEventListener(FocusEvent.FOCUS_OUT, focusOutHandlerNum);
			event.currentTarget.restrict = "0-9";	
		}
		
		/**
		 * Enable only number entry as text input. No text is permitted.
		 */
		public static function enablePasswordEntry(event:Event):void
		{
			event.currentTarget.addEventListener(FocusEvent.FOCUS_IN, focusInHandlerNum);
			event.currentTarget.addEventListener(FocusEvent.FOCUS_OUT, focusOutHandlerNum);
			event.currentTarget.restrict = "0-9a-zA-Z@#$%^*~";	
		}

		/**
		 * Comapres two dates. 
		 */
		public static function compareDates(date1 : Date, date2 : Date):Boolean
		{
		    var date1Timestamp : Number = date1.getTime ();
		    var date2Timestamp : Number = date2.getTime ();

		    var result:Boolean = false;
		
		    if (date1Timestamp >= date2Timestamp)
		    {	    	
		        result = false;
		    }
		    else if (date1Timestamp < date2Timestamp)
		    {
		        result = true;
		    }
		
		    return result;
		}
		public static function doStartDateLabel(item:Date):String 
		{
			var formatDateTimeUSD:DateFormatter  = new DateFormatter();
			formatDateTimeUSD.formatString = "DD/MM/YYYY";
			return formatDateTimeUSD.format(item);
		}
		
		public static function doStartDateLabelUSD(item:Date):String 
		{
			var formatDateTimeUSD:DateFormatter  = new DateFormatter();
			formatDateTimeUSD.formatString = "MM/DD/YYYY";
			return formatDateTimeUSD.format(item);
		}
		
		public static function doStartDateLabelUSDdatetime(item:Date):String 
		{
			var formatDateTimeUSD:DateFormatter  = new DateFormatter();
			formatDateTimeUSD.formatString = "MM/DD/YYYY HH:NN:SS";
			return formatDateTimeUSD.format(item);
		}
		
		public static function doStartDateLabelTime(item:Date):String 
		{
			var formatDateTimeHHNNSS:DateFormatter  = new DateFormatter();
			formatDateTimeHHNNSS.formatString = "HH:NN:SS";
			return formatDateTimeHHNNSS.format(item);
		}
		
		public static function doStartDateLabelHH(item:Date):String 
		{
			var formatDateTimeHH:DateFormatter  = new DateFormatter();
			formatDateTimeHH.formatString = "HH";
			return formatDateTimeHH.format(item);
		}
		
		public static function doStartDateLabelNN(item:Date):String 
		{
			var formatDateTimeNN:DateFormatter  = new DateFormatter();
			formatDateTimeNN.formatString = "NN";
			return formatDateTimeNN.format(item);
		}
		
		public static function doStartDateLabelSS(item:Date):String 
		{
			var formatDateTimeSS:DateFormatter  = new DateFormatter();
			formatDateTimeSS.formatString = "SS";
			return formatDateTimeSS.format(item);
		}
		
		
		public static function sortArrayCollection(arrColl:ArrayCollection, fieldName:String, isNumeric:Boolean):void 
		{
			/* Create the SortField object for the "data" field in the ArrayCollection object, and make sure we do a numeric sort. */
			var dataSortField:SortField = new SortField();
			dataSortField.name = fieldName;
			dataSortField.numeric = isNumeric;
			
			/* Create the Sort object and add the SortField object created earlier to the array of fields to sort on. */
			var numericDataSort:Sort = new Sort();
			numericDataSort.fields = [dataSortField];
			
			/* Set the ArrayCollection object's sort property to our custom sort, and refresh the ArrayCollection. */
			arrColl.sort = numericDataSort;
			arrColl.refresh();
		}

	}
}