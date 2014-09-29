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
 * $Revision:: 4                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/ExecuteReport.as 4 $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/ExecuteReport.as   $
 * 
 * 4     4/25/10 7:54p Gourav.rai
 * 
 * 3     4/12/10 6:53p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 2     4/07/10 11:59a Gourav.rai
 * Added by Gourav Rai
 * 
 * 1     3/26/10 11:06a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.model.vo
{
	import mx.collections.ArrayCollection;
	
	[Bindable]
	public class ExecuteReport extends BaseValueObject
	{
		public function ExecuteReport()
		{
			super();
		}
		
		private var _installationCode:String;
		private var _reports:ArrayCollection;//List<Report>
		private var _reportParameters:ArrayCollection;//List<ReportParameter>
		private var _requestReportParameters:ArrayCollection;//List<ReportParameter> 
		private var _queryResults:ArrayCollection;//List<ReportParameter> ;
		private var _headingDetails:ArrayCollection;//List<ReportParameter> ;
		private var _reportDataDetails:ArrayCollection;//List<ReportParameter> ;
		private var _systemDate:String;
		private var _reportName:String;
		private var _query:String;
		private var _count:String;
		private var _reportUrl:String;
		
	    private var _reportFields:String;
	    private var _hours:String;
	    private var _minutes:String;
	    private var _seconds:String;
		
		private var _reportServerUrl:String;
		private var _reportServerAlias:String;
		private var _reportPath:String;
		private var _reportId:String;
		
		private var _processName:String;
		private var _hoFlag:String;
		private var _processDescription:String;
		private var _displayOrder:String;
		private var _noOfRequests:String;
		private var _requestState:String;
		private var _orderBy:String;
		private var _userId:String;
		private var _startDate:String;
		private var _endDate:String;
		
		private var _toHours:String;
	    private var _toMinutes:String;
	    private var _toSeconds:String;
	    
	    private var _scheduleId:String;
	    private var _requestId:String;
	    
	    private var _occurence:String;
	    private var _frequency:String;
	    private var _recurrence:String;
	    private var _futureSchedule:String;
	    
	    private var _weekDay:String;
	    private var _weekStartTime:String;
		private var _weekEndTime:String;
		private var _scheduleText:String;		
		private var _scheduleStatus:String;
		private var _cancelQueued:String;
		
		private var _startTime:String;
		private var _endTime:String;
		private var _optionEndDate:String;
		
		public function set installationCode(value:String):void
		{
			this._installationCode = value;
		}
		public function get installationCode():String
		{
			return this._installationCode;
		}
		
		public function set reports(value:ArrayCollection):void
		{
			this._reports = value;
		}
		public function get reports():ArrayCollection
		{
			return this._reports;
		}
		
		public function set reportParameters(value:ArrayCollection):void
		{
			this._reportParameters = value;
		}
		public function get reportParameters():ArrayCollection
		{
			return this._reportParameters;
		}
		
		
		public function set requestReportParameters(value:ArrayCollection):void
		{
			this._requestReportParameters = value;
		}
		public function set queryResults(value:ArrayCollection):void
		{
			this._queryResults = value;
		}
		public function set headingDetails(value:ArrayCollection):void
		{
			this._headingDetails = value;
		}
		public function set reportDataDetails(value:ArrayCollection):void
		{
			this._reportDataDetails = value;
		}
		public function set systemDate(value:String):void
		{
			this._systemDate = value;
		}
		public function set reportName(value:String):void
		{
			this._reportName = value;
		}
		public function set query(value:String):void
		{
			this._query = value;
		}
		public function set count(value:String):void
		{
			this._count = value;
		}
		public function set reportUrl(value:String):void
		{
			this._reportUrl = value;
		}		
		public function set reportFields(value:String):void
		{
			this._reportFields = value;
		}
		public function set hours(value:String):void
		{
			this._hours = value;
		}
		public function set minutes(value:String):void
		{
			this._minutes = value;
		}
		public function set seconds(value:String):void
		{
			this._seconds = value;
		}
		
		
		public function get requestReportParameters():ArrayCollection
		{
			return this._requestReportParameters;
		}
		public function get queryResults():ArrayCollection
		{
			return this._queryResults;
		}
		public function get headingDetails():ArrayCollection
		{
			return this._headingDetails;
		}
		public function get reportDataDetails():ArrayCollection
		{
			return this._reportDataDetails;
		}
		public function get systemDate():String
		{
			return this._systemDate;
		}
		public function get reportName():String
		{
			return this._reportName;
		}
		public function get query():String
		{
			return this._query;
		}
		public function get count():String
		{
			return this._count;
		}
		public function get reportUrl():String
		{
			return this._reportUrl;
		}
		public function get reportFields():String
		{
			return this._reportFields;
		}
		public function get hours():String
		{
			return this._hours;
		}
		public function get minutes():String
		{
			return this._minutes;
		}
		public function get seconds():String
		{
			return this._seconds;
		}
		
		
		
		public function set reportServerUrl(value:String):void
		{
			this._reportServerUrl = value;
		}		
		public function get reportServerUrl():String
		{
			return this._reportServerUrl;
		}
		
		public function set reportServerAlias(value:String):void
		{
			this._reportServerAlias = value;
		}		
		public function get reportServerAlias():String
		{
			return this._reportServerAlias;
		}
		
		public function set reportPath(value:String):void
		{
			this._reportPath = value;
		}		
		public function get reportPath():String
		{
			return this._reportPath;
		}
		
		public function set reportId(value:String):void
		{
			this._reportId = value;
		}		
		public function get reportId():String
		{
			return this._reportId;
		}
		
		public function set processName(value:String):void
		{
			this._processName = value;
		}
		public function get processName():String
		{
			return this._processName;
		}
		
		public function set hoFlag(value:String):void
		{
			this._hoFlag = value;
		}
		public function get hoFlag():String
		{
			return this._hoFlag;
		}
		
		public function set processDescription(value:String):void
		{
			this._processDescription = value;
		}
		public function get processDescription():String
		{
			return this._processDescription;
		}
		
		public function set displayOrder(value:String):void
		{
			this._displayOrder = value;
		}
		public function get displayOrder():String
		{
			return this._displayOrder;
		}
		
		public function set noOfRequests(value:String):void
		{
			this._noOfRequests = value;
		}
		public function get noOfRequests():String
		{
			return this._noOfRequests;
		}
		
		public function set requestState(value:String):void
		{
			this._requestState = value;
		}
		public function get requestState():String
		{
			return this._requestState;
		}
		
		public function set orderBy(value:String):void
		{
			this._orderBy = value;
		}
		public function get orderBy():String
		{
			return this._orderBy;
		}
		
		public function set userId(value:String):void
		{
			this._userId = value;
		}
		public function get userId():String
		{
			return this._userId;
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
		
		public function get toHours():String
		{
			return this._toHours;
		}
		public function set toHours(value:String):void
		{
			this._toHours = value;
		}
		
		public function get toMinutes():String
		{
			return this._toMinutes;
		}
		public function set toMinutes(value:String):void
		{
			this._toMinutes = value;
		}
		
		public function get toSeconds():String
		{
			return this._toSeconds;
		}
		public function set toSeconds(value:String):void
		{
			this._toSeconds = value;
		}
		
		public function get scheduleId():String
		{
			return this._scheduleId;
		}
		public function set scheduleId(value:String):void
		{
			this._scheduleId = value;
		}
		
		public function get requestId():String
		{
			return this._requestId;
		}
		public function set requestId(value:String):void
		{
			this._requestId = value;
		}
		
		public function get occurence():String
		{
			return this._occurence;
		}
		public function set occurence(value:String):void
		{
			this._occurence = value;
		}
		
		public function get frequency():String
		{
			return this._frequency;
		}
		public function set frequency(value:String):void
		{
			this._frequency = value;
		}
		
		public function get recurrence():String
		{
			return this._recurrence;
		}
		public function set recurrence(value:String):void
		{
			this._recurrence = value;
		}
		
		public function get futureSchedule():String
		{
			return this._futureSchedule;
		}
		public function set futureSchedule(value:String):void
		{
			this._futureSchedule = value;
		}
		
		public function get weekDay():String
		{
			return this._weekDay;
		}
		public function set weekDay(value:String):void
		{
			this._weekDay = value;
		}
		
		public function get weekStartTime():String
		{
			return this._weekStartTime;
		}
		public function set weekStartTime(value:String):void
		{
			this._weekStartTime = value;
		}
		
		public function get weekEndTime():String
		{
			return this._weekEndTime;
		}
		public function set weekEndTime(value:String):void
		{
			this._weekEndTime = value;
		}
		
		public function get scheduleText():String
		{
			return this._scheduleText;
		}
		public function set scheduleText(value:String):void
		{
			this._scheduleText = value;
		}
		
		public function get scheduleStatus():String
		{
			return this._scheduleStatus;
		}
		public function set scheduleStatus(value:String):void
		{
			this._scheduleStatus = value;
		}
		
		public function get cancelQueued():String
		{
			return this._cancelQueued;
		}
		public function set cancelQueued(value:String):void
		{
			this._cancelQueued = value;
		}
		
		
		public function get startTime():String
		{
			return this._startTime;
		}
		public function set startTime(value:String):void
		{
			this._startTime = value;
		}
		
		public function get endTime():String
		{
			return this._endTime;
		}
		public function set endTime(value:String):void
		{
			this._endTime = value;
		}
		
		public function get optionEndDate():String
		{
			return this._optionEndDate;
		}
		public function set optionEndDate(value:String):void
		{
			this._optionEndDate = value;
		}
	}
}