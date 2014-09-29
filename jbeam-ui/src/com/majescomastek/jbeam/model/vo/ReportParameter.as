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
 * @author Gourav Rai
 * 
 *
 * $Revision:: 3                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/ReportParameter.as $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/ReportParameter.as $
 * 
 * 3     4/25/10 7:54p Gourav.rai
 * 
 * 2     4/12/10 6:53p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     3/26/10 11:05a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.model.vo
{
	import mx.collections.ArrayCollection;
	
	[Bindable]
	public class ReportParameter extends BaseValueObject
	{		
		public function ReportParameter()
		{
			super();
		}
		
		private var _installationCode:String;
		private var _reportId:String;
		private var _paramName:String;
		private var _dataType:String;
		private var _fieldMaxlength:String;
		private var _fixedLength:String;
		private var _defaultValue:String;
		private var _hint:String;
		private var _label:String;
		private var _queryFlag:String;
		private var _query:String;
		private var _mandatoryFlag:String;
		private var _staticDynamicFlag:String;
		private var _entities:ArrayCollection;//List<ConfigParameter>
		private var _paramValue:String;
		
		private var _requestId:String;
		private var _requestDate:String;
		private var _userId:String;
		private var _requestState:String;
		private var _scheduleDate:String;
		private var _startDate:String;
		private var _endDate:String;
		private var _processName:String;
		private var _reqLogFileName:String;
		private var _scheduleId:String;		
		private var _menuId:String;
		private var _dateSpecificParamValue:String;
		
		public function set installationCode(value:String):void
		{
			this._installationCode = value;
		}
		public function get installationCode():String
		{
			return this._installationCode;
		}
		public function set reportId(value:String):void
		{
			this._reportId = value;
		}
		public function set paramName(value:String):void
		{
			this._paramName = value;
		}
		public function set dataType(value:String):void
		{
			this._dataType = value;
		}
		public function set fieldMaxlength(value:String):void
		{
			this._fieldMaxlength = value;
		}
		public function set fixedLength(value:String):void
		{
			this._fixedLength = value;
		}
		public function set defaultValue(value:String):void
		{
			this._defaultValue = value;
		}
		public function set hint(value:String):void
		{
			this._hint = value;
		}
		public function set label(value:String):void
		{
			this._label = value;
		}
		public function set queryFlag(value:String):void
		{
			this._queryFlag = value;
		}
		public function set query(value:String):void
		{
			this._query = value;
		}
		public function set mandatoryFlag(value:String):void
		{
			this._mandatoryFlag = value;
		}
		public function set staticDynamicFlag(value:String):void
		{
			this._staticDynamicFlag = value;
		}
		public function set entities(value:ArrayCollection):void
		{
			this._entities = value;
		}		
		public function set paramValue(value:String):void
		{
			this._paramValue = value;
		}
		
		
		public function get reportId():String
		{
			return this._reportId;
		}
		public function get paramName():String
		{
			return this._paramName;
		}
		public function get dataType():String
		{
			return this._dataType;
		}
		public function get fieldMaxlength():String
		{
			return this._fieldMaxlength;
		}
		public function get fixedLength():String
		{
			return this._fixedLength;
		}
		public function get defaultValue():String
		{
			return this._defaultValue;
		}
		public function get hint():String
		{
			return this._hint;
		}
		public function get label():String
		{
			return this._label;
		}
		public function get queryFlag():String
		{
			return this._queryFlag;
		}
		public function get query():String
		{
			return this._query;
		}
		public function get mandatoryFlag():String
		{
			return this._mandatoryFlag;
		}
		public function get staticDynamicFlag():String
		{
			return this._staticDynamicFlag;
		}
		public function get entities():ArrayCollection
		{
			return this._entities;
		}
		public function get paramValue():String
		{
			return this._paramValue;
		}
		
		
		
		public function set requestId(value:String):void
		{
			this._requestId = value;
		}
		public function get requestId():String
		{
			return this._requestId;
		}
		
		public function set requestDate(value:String):void
		{
			this._requestDate = value;
		}
		public function get requestDate():String
		{
			return this._requestDate;
		}
		
		public function set userId(value:String):void
		{
			this._userId = value;
		}
		public function get userId():String
		{
			return this._userId;
		}
		
		public function set requestState(value:String):void
		{
			this._requestState = value;
		}
		public function get requestState():String
		{
			return this._requestState;
		}
		
		public function set scheduleDate(value:String):void
		{
			this._scheduleDate = value;
		}
		public function get scheduleDate():String
		{
			return this._scheduleDate;
		}
		
		public function set startDate(value:String):void
		{
			this._startDate = value;
		}
		public function get startDate():String
		{
			return this._startDate;
		}
		
		public function set endDate(value:String):void
		{
			this._endDate = value;
		}
		public function get endDate():String
		{
			return this._endDate;
		}
		
		public function set processName(value:String):void
		{
			this._processName = value;
		}
		public function get processName():String
		{
			return this._processName;
		}
		
		public function set reqLogFileName(value:String):void
		{
			this._reqLogFileName = value;
		}
		public function get reqLogFileName():String
		{
			return this._reqLogFileName;
		}
		
		public function set scheduleId(value:String):void
		{
			this._scheduleId = value;
		}
		public function get scheduleId():String
		{
			return this._scheduleId;
		}
		
		public function set menuId(value:String):void
		{
			this._menuId = value;
		}
		public function get menuId():String
		{
			return this._menuId;
		}
		
		public function set dateSpecificParamValue(value:String):void
		{
			this._dateSpecificParamValue = value;
		}
		public function get dateSpecificParamValue():String
		{
			return this._dateSpecificParamValue;
		}
	}
}