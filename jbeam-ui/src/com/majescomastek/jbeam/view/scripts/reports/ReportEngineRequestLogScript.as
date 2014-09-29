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
 * $Revision:: 3                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/Report $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/Report $
 * 
 * 3     4/25/10 7:55p Gourav.rai
 * 
 * 2     4/12/10 6:55p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     4/07/10 12:02p Gourav.rai
 * Added by Gourav Rai
 * 
 */
 
import com.majescomastek.common.events.CustomDataEvent;
import com.majescomastek.jbeam.facade.reports.ReportsFacade;
import com.majescomastek.jbeam.model.vo.ExecuteReport;
import com.majescomastek.jbeam.model.vo.Report;

import flash.events.Event;

import mx.utils.StringUtil;
 
 
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
	sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"");
	sendDataEvent(ReportsModule.SELECT_VIEW, "BACK");
}

/**
 * 
 * @param item
 * 
 */
public function getDisplayData(_executeReport:ExecuteReport):void
{
	for each(var report:Report in _executeReport.reports)
	{
		procDescId.text = report.reportName;
		break;
	}
}

/**
 * Function dispatches event with data.
 */
public function onReport():void
{	
	sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"");
	
	var executeReport:ExecuteReport = new ExecuteReport();
	executeReport.hoFlag = "HO1";  	 
	
	var validStartDate:Boolean = true;
	var validEndDate:Boolean = true;
		
	executeReport.requestId = requestId.text==""?null:StringUtil.trim(requestId.text);
	executeReport.scheduleId = scheduleId.text==""?null:StringUtil.trim(scheduleId.text);
	executeReport.userId = userId.text==""?null:StringUtil.trim(userId.text);
	executeReport.reportName = executeReport.processDescription = procDescId.text==""?null:StringUtil.trim(procDescId.text);
	executeReport.noOfRequests = noOfReqId.text==""?"25":StringUtil.trim(noOfReqId.text);
	
	var startDate:String = StringUtil.trim(schStartDateId.text);
	if(startDate.length>0 && !schStartDateId.isValidDate())
	{
		validStartDate = false;
	}
	
	/****************************************************/
	executeReport.startDate = startDate==""?null:startDate;
	executeReport.startTime = schStartTimeId.getStringValue();
	
	if(schStartTimeId.hhIndex==-1)
	{
		executeReport.hours = "00";
	}
	else if(schStartTimeId.hhIndex<10)
	{
		executeReport.hours = "0"+schStartTimeId.hhIndex;
	}
	else
	{
		executeReport.hours = String(schStartTimeId.hhIndex);
	}
	
	if(schStartTimeId.mmIndex==-1)
	{
		executeReport.minutes = "00";
	}
	else if(schStartTimeId.mmIndex<10)
	{
		executeReport.minutes = "0"+schStartTimeId.mmIndex;
	}
	else
	{
		executeReport.minutes = String(schStartTimeId.mmIndex);
	}
	
	if(schStartTimeId.ssIndex==-1)
	{
		executeReport.seconds = "00";
	}
	else if(schStartTimeId.ssIndex<10)
	{
		executeReport.seconds = "0"+schStartTimeId.ssIndex;
	}
	else
	{
		executeReport.seconds = String(schStartTimeId.ssIndex);
	}
	
	schStartTimeId.setStringTime(executeReport.hours,executeReport.minutes,executeReport.seconds);
	
	/****************************************************/
	
	var endDate:String = StringUtil.trim(schEndDateId.text);
	if(endDate.length>0 && !schEndDateId.isValidDate())
	{
		validEndDate = false;
	}
	
	executeReport.endDate = endDate==""?null:endDate;
	
	if(schEndTimeId.hhIndex==-1)
	{
		executeReport.toHours = "23";
	}
	else if(schEndTimeId.hhIndex<10)
	{
		executeReport.toHours = "0"+schEndTimeId.hhIndex;
	}
	else
	{
		executeReport.toHours = String(schEndTimeId.hhIndex);
	}
	
	if(schEndTimeId.mmIndex==-1)
	{
		executeReport.toMinutes = "59";
	}
	else if(schEndTimeId.mmIndex<10)
	{
		executeReport.toMinutes = "0"+schEndTimeId.mmIndex;
	}
	else
	{
		executeReport.toMinutes = String(schEndTimeId.mmIndex);
	}
	
	if(schEndTimeId.ssIndex==-1)
	{
		executeReport.toSeconds = "59";
	}
	else if(schEndTimeId.ssIndex<10)
	{
		executeReport.toSeconds = "0"+schEndTimeId.ssIndex;
	}
	else
	{
		executeReport.toSeconds = String(schEndTimeId.ssIndex);
	}
	
	schEndTimeId.setStringTime(executeReport.toHours,executeReport.toMinutes,executeReport.toSeconds);
	
	/****************************************************/
		
	executeReport.requestState = reqStatusId.selectedItem.subCode;	
	executeReport.orderBy = orderById.selectedItem.subCode;
	executeReport.displayOrder = orderId.selectedItem.subCode;
	
	if(validStartDate && validEndDate)
	{
		sendDataEvent(ReportsFacade.REPORT_REQUEST_STATUS_ENQUIRY, executeReport);
	}
	else
	{
		sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,resourceManager.getString('jbeam', 'valid_date_msg'));
	}
}

/**
 * 
 * @param event
 * 
 */
private function onStartDateFocusOut(event:Event):void
{
	schStartDateId.errorString = "";
	var value:String = StringUtil.trim(schStartDateId.text);
	schStartDateId.text = value;
	if(value.length>0)
	{
		if(schStartDateId.isValidDate())
		{
			schStartTimeId.setTime(0,0,0);
		}
		else
		{
			schStartDateId.errorString = resourceManager.getString('jbeam', 'valid_date_msg');
			schStartTimeId.setTime(-1,-1,-1);
		}
	}
	else
	{		
		schStartTimeId.setTime(-1,-1,-1);
	}	
}


/**
 * 
 * @param event
 * 
 */
private function onEndDateFocusOut(event:Event):void
{
	schEndDateId.errorString = "";
	var value:String = StringUtil.trim(schEndDateId.text);
	schEndDateId.text = value;
	if(value.length>0)
	{
		if(schEndDateId.isValidDate())
		{
			schEndTimeId.setTime(23,59,59);
		}
		else
		{
			schEndDateId.errorString = resourceManager.getString('jbeam', 'valid_date_msg');
			schEndTimeId.setTime(-1,-1,-1);
		}
	}
	else
	{
		schEndTimeId.setTime(-1,-1,-1);
	}
}