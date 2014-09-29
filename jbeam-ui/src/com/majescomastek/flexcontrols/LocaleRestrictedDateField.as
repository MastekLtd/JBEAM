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
 * @author sanjayts
 * 
 *
 * $Revision:: 16                                                                                                      $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/flexcontrols/LocaleRestrictedDateFi $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/flexcontrols/LocaleRestrictedDateFi $
 * 
 * 16    3/11/10 2:53p Ritesh.chavan
 * changes made by sanjay
 * 
 * 15    1/13/10 2:00p Sanjay.sharma
 * fixed formatting for onOpen, added ASDoc and shortened the function
 * 
 * 14    1/10/10 3:43p Sanjay.sharma
 * added new utility method dateToString
 * 
 * 13    1/08/10 10:15p Sanjay.sharma
 * added new method isDateNonBlankAndValid
 * 
 * 12    1/08/10 2:05p Sanjay.sharma
 * added a new utility method get dateString
 * 
 * 11    1/07/10 2:33p Gourav.rai
 * set default system date
 * 
 * 10    1/06/10 10:30a Shrinivas
 * 
 * 9     1/05/10 9:23a Shrinivas
 * Changes done for date editable in datagrid.
 * 
 * 7     12/29/09 6:09p Sanjay.sharma
 * Made this control stateful along with few utility functions
 * 
 * 6     12/24/09 4:55p Sanjay.sharma
 * made stringToDate public
 * 
 * 5     12/18/09 5:20p Sanjay.sharma
 * added utility functions to custom components
 * 
 * 4     12/10/09 4:08p Sanjay.sharma
 * modified the date class to handle null values of value objects
 * 
 * 3     11/16/09 5:15p Sanjay.sharma
 * Added header.
 * 
 * 
 */
package com.majescomastek.flexcontrols
{
	import com.majescomastek.common.controls.framework.BaseDateField;
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.CommonUtils;
	
	import flash.events.Event;
	import flash.events.FocusEvent;
	
	import mx.controls.DateField;
	import mx.core.EventPriority;
	import mx.events.DropdownEvent;
	import mx.events.FlexEvent;
	import mx.utils.StringUtil;
	import mx.validators.DateValidator;

	/**
	 * A custom DateField class in which the length of the user inputted
	 * text depends on the length of the date format for the particular
	 * locale selected.
	 */ 
	public class LocaleRestrictedDateField extends BaseDateField
	{
		/** The variable containing the previous value of this control */
		protected var _oldValue:String;
		
		/** This flag determines whether this control should be validated on blur or not */
		private var _validateOnBlur:Boolean;
		
		public function LocaleRestrictedDateField()
		{
			super();
			
			// Make it editable by default
			super.editable = true;
			
			// Provide an alternative parse function since the one provided by
			// DateField is broken.
			super.parseFunction = LocaleRestrictedDateField.stringToDate;
			
			// Add the necessary listeners
			addEventListener(FlexEvent.INITIALIZE, onInitializeHandler, false, 0, true);
			addEventListener(FocusEvent.FOCUS_IN, onFocusIn, false, EventPriority.EFFECT, true);
			  addEventListener(FocusEvent.KEY_FOCUS_CHANGE, onDataChangeHandler,
				false, EventPriority.EFFECT, true);
			addEventListener(FocusEvent.MOUSE_FOCUS_CHANGE, onDataChangeHandler,
				false, EventPriority.EFFECT, true);
			addEventListener(DropdownEvent.OPEN, onOpen, false, 0, true);			  
		}
		
		/**
		 * The function invoked when the TextInput field gets the focus. We use this
		 * handler function to save the original value of the field as the _oldValue.
		 */
		private function onFocusIn(event:Event):void
		{
			_oldValue = this.text;
		}
		
		/**
		 * The function fired when this DateField is initialized.
		 */
		private function onInitializeHandler(event:Event):void
		{
			// This line when placed in the constructor gives a NULL reference
			// error hence we have placed it here.
			textInput.maxChars = formatString.length;
			
			if(_validateOnBlur)
			{
				// HACK: This dummy assignment is done so as when invalid date is entered
				// the value does not disappear on focus out!
				
				this.selectedDate = null;
			}
			
		}
		
		/**
		 * Provide an alternate implementation which handles null values.
		 */
		public static function stringToDate(valueString:String, inputFormat:String=null):Date
    	{
    		if(!valueString) return null;
    		
    		inputFormat = inputFormat == null ?	new DateField().formatString: inputFormat;
    		return DateField.stringToDate(valueString, inputFormat);
    	}
    	
    	/**
    	 * Convert the passed in date to its string representation based on the
    	 * passed in format or based on the current locale if no format is passed.
    	 */
    	public static function dateToString(date:Date, outputFormat:String=null):String
    	{
    		outputFormat = outputFormat == null ?	new DateField().formatString: outputFormat;
    		return DateField.dateToString(date, outputFormat);    		
    	}
    	
    	/**
    	 * Determine whether the date entered by the user is valid or not. This
    	 * check includes checks for blank/null date string or invalid date string
    	 * based on the current user locale/formatString.
    	 */
    	public function isValidDate():Boolean
    	{
    		var dt:Date = stringToDate(this.text, formatString); 
    		return (dt != null ? true : false);
    	}
    	
    	/**
    	 * If the date text is entered, make sure it is valid.
    	 */
    	public function isDateNonBlankAndValid():Boolean
    	{
    		if(this.text && StringUtil.trim(this.text).length != 0 && this.date == null)
    		{
    			return false;
    		}
    		else
    		{
    			return true;
    		}
    	}
    	
    	/**
		 * Retrieve the old or previous value of this stateful component.
		 */
		public function get oldValue():String
		{
			return _oldValue;
		}
		
		/**
		 * Return <code>true</code> if the value in the date field has changed;
		 * <code>false</code> otherwise.
		 */
		public function get changed():Boolean
		{
			return (_oldValue != this.text);
		}
		
		/**
		 * Revert the value of this control to its old value.
		 */
		public function revert():void
		{
			this.text = _oldValue;
		}
		
		/**
		 * Return the date object representing the text in this control.
		 */
		public function get date():Date
		{
			return stringToDate(this.text, formatString); 
		}
		
		/**
		 * Return the textual date contained in this control if the entered date
		 * is valid; null otherwise.
		 */
		public function get dateString():String
		{
			return (this.date ? this.text : null);
		}
		
		/**
		 * Reset the state of this control.
		 */
		public function reset():void
		{
			this.selectedDate = null;
			this.text = null;
		}

		/**
		 * Retrieve the datagrid flag.
		 */
		[Bindable]
		public function get validateOnBlur():Boolean
		{
			return _validateOnBlur;
		}
		
		/**
		 * Sets the datagrid flag.
		 */
		public function set validateOnBlur(value:Boolean):void
		{
			this._validateOnBlur = value;
		}
		
		/**
		 * The function fired when this DateField is changed.
		 */
		private function onDataChangeHandler(event:Event):void
		{
			if(!_validateOnBlur)	return;
			
			if(!text || text.length == 0)
			{
				this.selectedDate = null;
				this.errorString = null;
				return;
			}
			
    		if(CommonUtils.validateControls([getValidator()]))
    		{
    			this.errorString = null;
    		} 
    		else
    		{
    			this.setFocus();
    			this.focusEnabled =true;
    			this.drawFocus(true);
    			selectedDate = null;
    			event.preventDefault();
    		}
		}
		
		/**
		 * The function validates the datafield.
		 */
		private function getValidator():DateValidator
		{
			var dateVal:DateValidator = new DateValidator();
			dateVal.property = 'text';
			dateVal.source = this;
			dateVal.required = false;
			return dateVal;		
		}
		
		/**
		 * The function invoked when the calendar control is opened to select
		 * a date.
		 */
		private function onOpen(event:DropdownEvent):void
		{
			var value:String = this.text;
			if(StringUtil.trim(value).length == 0)
			{
				dropdown.selectedDate =
					CommonConstants.SYSTEM_DATE ? CommonConstants.SYSTEM_DATE : new Date();
			}
		}

	}
}