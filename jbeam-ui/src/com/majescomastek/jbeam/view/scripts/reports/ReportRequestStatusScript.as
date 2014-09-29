// ActionScript file
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
 * @author Ritesh Umathe
 * 
 *
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/Report $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/Report $
 * 
 * 2     4/25/10 7:55p Gourav.rai
 * 
 * 1     4/07/10 12:02p Gourav.rai
 * Added by Gourav Rai
 * 
 */
 
import com.majescomastek.common.events.CustomDataEvent;
import com.majescomastek.flexcontrols.LocaleRestrictedDateField;
import com.majescomastek.jbeam.common.BusinessConstants;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.ExecuteReport;
import com.majescomastek.jbeam.model.vo.ReportParameter;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.utils.ObjectUtil;
 
public var reportUrl:String = "";
public var frequecies:ArrayCollection = null;
public var weekDays:ArrayCollection = null;

private var requestStatus:Object = {A:"ALL",Q:"Queued",I:"Initializing",P:"Processing",S:"Completed",E:"Completed With Fatal Errors",C:"Cancelled by PRE.",X:"Cancelled by User"};
/**
* Function dispatches event with data.
*/
private function sendDataEvent(eventName:String, eventData:Object):void
{
	dispatchEvent(new CustomDataEvent(eventName, eventData, true));
}
/**
* Function dispatches event with data.
*/
private function onBack(event:Event):void
{
	sendDataEvent(ReportsModule.SELECT_VIEW, "BACK");
}

/**
 * 
 * @param executeReport
 * 
 */
public function getReportRequestStatusData(executeReport:ExecuteReport):void
{
	clear();
	if(executeReport!=null)
	{
		reportUrl = executeReport.reportUrl == null?"--":executeReport.reportUrl;
		requestId.text = executeReport.requestId == null?"--":executeReport.requestId;
		scheduleId.text = executeReport.scheduleId == null?"--":executeReport.scheduleId;
		userId.text = executeReport.userId == null?"--":executeReport.userId;
		scheduleStartDateTimeId.text = executeReport.startDate == null?"--":executeReport.startDate;
		scheduleEndDateTimeId.text = executeReport.endDate == null?"--":executeReport.endDate;
		reqStatuId.text = executeReport.requestState == null?"--":requestStatus[executeReport.requestState];
		dgReportRequestStatusId.dataProvider = executeReport.requestReportParameters==null?new ArrayCollection():executeReport.requestReportParameters;
	}
}

/**
 * 
 * 
 */
private function clear():void
{
	requestId.text = "--";
	scheduleId.text = "--";
	userId.text = "--";
	scheduleStartDateTimeId.text = "--";
	scheduleEndDateTimeId.text = "--";
	reqStatuId.text = "--";
	dgReportRequestStatusId.dataProvider = null;
}

/**
 * 
 * @param itemA
 * @param itemB
 * @return 
 * 
 */
private function sortByRequestId(itemA:ReportParameter, itemB:ReportParameter):int
{
	try
	{
		var value1:Number = Number(itemA.requestId);
    	var value2:Number = Number(itemB.requestId);
		return ObjectUtil.numericCompare(value1,value2);
	}
	catch(e:Error)
	{		
	}
	return 0; 
}

/**
 * 
 * @param itemA
 * @param itemB
 * @return 
 * 
 */
private function sortReqLogFileName(itemA:ReportParameter, itemB:ReportParameter):int
{
	try
	{
		if(itemA.reqLogFileName == null && itemA.reqLogFileName == null)
			return 0;
		if (itemA.reqLogFileName == null)
			return 1;
		if (itemA.reqLogFileName == null)
			return -1;
			
		var value1:Number = Number(itemA.requestId);
    	var value2:Number = Number(itemB.requestId);
		return ObjectUtil.numericCompare(value1,value2);
	}
	catch(e:Error)
	{		
	}
	return 0; 
}

/**
 * 
 * @param itemA
 * @param itemB
 * @return 
 * 
 */
private function sortBySchedule(itemA:ReportParameter, itemB:ReportParameter):int
{
	try
	{
		var value1:Number = Number(itemA.scheduleId);
    	var value2:Number = Number(itemB.scheduleId);
		return ObjectUtil.numericCompare(value1,value2);
	}
	catch(e:Error)
	{		
	}
	return 0; 
}

/**
 * 
 * @param itemA
 * @param itemB
 * @return 
 * 
 */
private function sortByRequestDate(itemA:ReportParameter, itemB:ReportParameter):int
{
	try
	{
		var dateA:Date = CommonUtils.getFormattedDate(itemA.requestDate, resourceManager.getString('SharedResources','dateFormat'))
		var dateB:Date = CommonUtils.getFormattedDate(itemB.requestDate, resourceManager.getString('SharedResources','dateFormat'));
		return ObjectUtil.dateCompare(dateA,dateB);
	}
	catch(e:Error)
	{		
	}	
 	return 0;
}

/**
 * 
 * @param itemA
 * @param itemB
 * @return 
 * 
 */
private function sortByScheduleDate(itemA:ReportParameter, itemB:ReportParameter):int
{
	try
	{
		var dateA:Date = CommonUtils.getFormattedDate(itemA.scheduleDate, resourceManager.getString('SharedResources','dateFormat'))		
		var dateB:Date = CommonUtils.getFormattedDate(itemB.scheduleDate, resourceManager.getString('SharedResources','dateFormat'));
		return ObjectUtil.dateCompare(dateA,dateB);
	}
	catch(e:Error)
	{		
	}	
 	return 0;
}

/**
 * 
 * @param itemA
 * @param itemB
 * @return 
 * 
 */
private function sortByStartDate(itemA:ReportParameter, itemB:ReportParameter):int
{
	try
	{
		var dateA:Date = CommonUtils.getFormattedDate(itemA.startDate, resourceManager.getString('SharedResources','dateFormat'))
		var dateB:Date = CommonUtils.getFormattedDate(itemB.startDate, resourceManager.getString('SharedResources','dateFormat'));
		return ObjectUtil.dateCompare(dateA,dateB);
	}
	catch(e:Error)
	{		
	}	
 	return 0;
}

/**
 * 
 * @param itemA
 * @param itemB
 * @return 
 * 
 */
private function sortByEndDate(itemA:ReportParameter, itemB:ReportParameter):int
{
	try
	{		
		var dateA:Date = CommonUtils.getFormattedDate(itemA.endDate, resourceManager.getString('SharedResources','dateFormat'))
		var dateB:Date = CommonUtils.getFormattedDate(itemB.endDate, resourceManager.getString('SharedResources','dateFormat'));
		return ObjectUtil.dateCompare(dateA,dateB);
	}
	catch(e:Error)
	{		
	}	
 	return 0;
}

/**
 * 
 * @param item
 * @param column
 * @return 
 * 
 */
private function myLabelFunction(item:ReportParameter, column:DataGridColumn):String
{
	try
	{
		switch(column.dataField)
		{						
			case "startDate":
				break;
			case "endDate":
				break;
			default:
				return CommonConstants.BLANK_STRING;
				break;
		}
	}
	catch(e:Error)
	{
		
	}
	return CommonConstants.BLANK_STRING;
}

/**
* Function retrieves drop down data.
*/
public function setDropDownData(dataArray:ArrayCollection):void
{
	var frequencyFlag:Boolean = false;
	var weekDaysFlag:Boolean = false;
	for each(var item:Object in dataArray)
	{
		if(frequencyFlag && weekDaysFlag)
		{
			break;
		}
		switch(String(item.dropDownKey))
		{
			case BusinessConstants.FREQUENCY_CODES:
				frequencyFlag = true;
				frequecies = item.dropDownValue;				
				break;
			case BusinessConstants.WEEKDAY_CODES:
				weekDaysFlag = true;
				weekDays = item.dropDownValue;
				break;
			default:
				trace("DropDown item in ReportRequestStatus: "+item.dropDownKey);
				break;
		}
	}
}


private function getDate(value:String):Date
{
	var dateTime:Array = value.split(" ");
	var dateStr:String = dateTime[0];
	var time:Array = dateTime[1].split(":");
	var hh:Number = 0;
	var mm:Number = 0;
	var ss:Number = 0;
	for(var idx:int = 0; idx<time.length;idx++)
	{
		if(idx == 0)
		{
			hh = Number(time[idx]);
		}
		else if(idx == 1)
		{
			mm = Number(time[idx]);
		}
		else if(idx == 2)
		{
			ss = Number(time[idx]);	
		}		
	}
	
	var date:Date = LocaleRestrictedDateField.stringToDate(dateStr,resourceManager.getString('SharedResources','dateFormat'));
	date.setHours(hh,mm,ss,0);
	/* trace(date.toDateString());
	trace(date.toLocaleDateString());
	trace(date.toLocaleString());
	trace(date.toLocaleTimeString());
	trace(date.toString());
	trace(date.toTimeString());
	trace(date.toUTCString()); */
	return date;
}