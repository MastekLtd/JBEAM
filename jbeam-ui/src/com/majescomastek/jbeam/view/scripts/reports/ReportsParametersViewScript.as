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
 */
include "ReportAttachedScheduleScript.as";

import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.BusinessConstants;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.facade.reports.ReportsFacade;
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.ConfigParameter;
import com.majescomastek.jbeam.model.vo.ExecuteReport;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.Report;
import com.majescomastek.jbeam.model.vo.ReportParameter;
import com.majescomastek.jbeam.model.vo.ReqInstructionLog;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.utils.StringUtil;
import mx.validators.Validator;

/**
 * This file forms part of the MajescoMastek 
 * SBS
 * Copyright (c) MajescoMastek 2009. 
 * All rights reserved.
 * @author Ritesh Umathe
 * 
 *
 * $Revision:: 6                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/Report $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/Report $
 * 
 * 6     4/25/10 7:55p Gourav.rai
 * 
 * 5     4/25/10 3:31p Shrinivas
 * Filter is added for null values ..
 * 
 * 4     4/25/10 11:53a Gourav.rai
 * Changes on Run Report
 * 
 * 3     4/13/10 11:57a Shrinivas
 * Order of collection changed.
 * 
 * 2     4/12/10 6:55p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     4/07/10 12:02p Gourav.rai
 * Added by Gourav Rai
 * 
 */
 
 /** The data held by this module */
[Bindable]public var moduleData:Object = {};

/**
* Report DropDown Option For Company
*/ 
[Bindable]private var reports:ArrayCollection;//List<Report>
/**
* Report DropDown Option For report format
*/
[Bindable]private var reportFormats:ArrayCollection;//List<ConfigParameter>
[Bindable]private var reportParameters:ArrayCollection;//List<ReportParameter>

private const ONLINE:String = "ONLINE";
private const OFFLINE:String = "OFFLINE";
private const ONLINE_NO_REPORT_PARAMETER:String = "NO_REPORT_PARAMETER_ONLINE";
private const OFFLINE_NO_REPORT_PARAMETERS:String = "NO_REPORT_PARAMETER_OFFLINE";
private var cacheExecuteReport:ExecuteReport = null;
private var report:Report = null;

[Bindable]
private var skipFlagList:ArrayCollection;

/**
 * To populate the skip flag combo box 
 */
private function createSkipFlagList():void
{
	skipFlagList = new ArrayCollection();
    skipFlagList.addItem({flagObject:"Skip Schedule", flagValue:"SS"});
    skipFlagList.addItem({flagObject:"Postpone Day by 1 (D+)", flagValue:"D+"});
    skipFlagList.addItem({flagObject:"Advance Day by 1 (D-)", flagValue:"D-"});
    skipFlagList.addItem({flagObject:"Not Applicable", flagValue:"NA"});	
}

private function showCalendar(event:Event):void
{
	var skipFlagVal:String = event.currentTarget.selectedItem.flagValue; 
	if(skipFlagVal == "NA")
	{
		this.lblCalendar.visible = false;
		this.txtBatchCalendar.visible = false;
	}
	else
	{
		this.lblCalendar.visible = true;
		this.txtBatchCalendar.visible = true;
	}
}

/**
* Get reult for Report DropDown Option For Company and set into local variable reportDropDown
*/  
public function reportDropDownResult(result:ExecuteReport):void
{
	this.reports = result.reports;
	this.reportParameters = null;
	createSkipFlagList();
}

/**
* Label Function For Report Drop Down
*/
private function reportDropDownLabel(item:Object):String
{
	if(item is Report)
	{
//		trace('reportName = ' +item.reportName+'['+item.reportId+']');
		return item.reportName+'['+item.reportId+']';
	}
	else if(item is Object)
	{
		return item.description;
	}
	return CommonConstants.BLANK_STRING;
}

/**
* On Change of ReportDropDown send event to fetch data
*/
private function onReportChange(event:Event):void
{
	var data:Object = moduleData;
	var instData:InstallationData = data.previousModuleData.installationData;
	if(instData == null)
	{
		instData = CommonConstants.INSTALLATION_DATA;				
	}
//	trace('Installation Code = ' + instData.installationCode);
	
	sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"");
	var _report:Report = event.currentTarget.selectedItem as Report;	
	if(_report!=null)
	{
		_report.installationCode = instData.installationCode;
		sendDataEvent(ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT,_report);
	}
	else
	{
		this.currentState = "BASE";
		attachScheduleVisible = false;
	}
}

public function onRetrieveParametersForReportResult(executeReport:ExecuteReport):void
{
	this.runReportVisible = true;
	var installationData:InstallationData = CommonConstants.INSTALLATION_DATA;
	
	var dt:Date = new Date();
	dt.setTime(dt.getTime() + dt.getTimezoneOffset() * 1000 * 60 + installationData.timezoneOffset);
	
	executeReport.systemDate = CommonUtils.formatDate(dt, CommonConstants.US_DATE_FORMAT);
	executeReport.hours = CommonUtils.doStartDateLabelHH(dt);
	executeReport.minutes = CommonUtils.doStartDateLabelNN(dt);
	executeReport.seconds = CommonUtils.doStartDateLabelSS(dt);
	
	this.cacheExecuteReport = executeReport;
	this.reportParameters = executeReport.reportParameters;
	for each(report in cacheExecuteReport.reports)
	{
		repType.selectedItem = report.reportType; 
	}	
	if(this.reportParameters!=null && this.reportParameters.length>0)
	{
		this.currentState = "";
	}
	else
	{
		this.currentState = ONLINE_NO_REPORT_PARAMETER;
	}
	
	reset(true);
	
	if(cacheExecuteReport.systemDate!=null)
	{
		schDateId.text = cacheExecuteReport.systemDate;
	}
		
	var hrIndex:Number=isNaN(Number(cacheExecuteReport.hours))?0:Number(cacheExecuteReport.hours);
	var mmIndex:Number=isNaN(Number(cacheExecuteReport.minutes))?0:Number(cacheExecuteReport.minutes);
	var ssIndex:Number=isNaN(Number(cacheExecuteReport.seconds))?0:Number(cacheExecuteReport.seconds);
	schTimeId.setTime(hrIndex,mmIndex,ssIndex);
	this.currentState = OFFLINE;
}

private function performByExeRep(event:Event):void
{
	var item:ConfigParameter = event.currentTarget.selectedItem as ConfigParameter;
	switch(item.subCode)
	{
		case ONLINE:
			if(reportParameters!=null && reportParameters.length>0)
			{
				this.currentState = "";
			}
			else
			{
				this.currentState = ONLINE_NO_REPORT_PARAMETER;
			}
			break;
		case OFFLINE:
			if(reportParameters!=null && reportParameters.length>0)
			{
				this.currentState = OFFLINE;
			}
			else
			{
				this.currentState = OFFLINE_NO_REPORT_PARAMETERS;
			}			
			break;
		default:
			trace("Invalid Action: "+item.subCode);
			break;
	}
}

private function reset(doNotReset:Boolean = false):void
{
	if(!doNotReset)
	{
		repId.selectedIndex = 0;		
	}
	exeRepId.selectedIndex = 0;
	schTimeId.setTime(0,0,0);
	schDateId.text = "";
	schDateId.errorString = "";
	emailIds.text = "";
	repType.selectedIndex = 0;
	if(attachScheduleVisible)
	{
		this.frequencyId.selectedIndex = 0;
		this.recurEveryId.text = "";
		
		this.endOnId.dtId.selected = false;
		this.endOnId.endDateId.text = "";
		this.endOnId.endTimeId.hhIndex = 0;
		this.endOnId.endTimeId.mmIndex = 0;
		this.endOnId.endTimeId.ssIndex = 0;
		
		this.endOnId.occId.selected = false;
		this.endOnId.occurenceId.text = "";
		
		this.yesId.selected = false;
		this.noId.selected = false;
		
		this.chkSunday.selected = false;
		this.chkMonday.selected = false;
		this.chkTuesday.selected = false;
		this.chkWednesday.selected = false;
		this.chkThursday.selected = false;
		this.chkFriday.selected = false;
		this.chkSaturday.selected = false;
		
		this.startTimeOnlyId.hhIndex = 0;
		this.startTimeOnlyId.mmIndex = 0;
		this.startTimeOnlyId.ssIndex = 0;
		
		this.endTimeOnlyId.hhIndex = 0;
		this.endTimeOnlyId.mmIndex = 0;
		this.endTimeOnlyId.ssIndex = 0;
		
		this.comboSkipFlag.selectedIndex = 0;
	}
}


/**
* Function retrieves system date
*/
public function setSystemDate(result:ExecuteReport):void
{
	CommonConstants.SYSTEM_DATE = new Date();
//	AlertBuilder.getInstance().show("SYSTEM_DATE = " + CommonConstants.SYSTEM_DATE);
//	this.executeReport.systemDate = new Date(Number(result.systemDate));
}
/**
* Function retrieves drop down data.
*/
public function setDropDownData(dataArray:ArrayCollection):void
{
	var frequencyFlag:Boolean = false;
	var weekDaysFlag:Boolean = false;
	var reportTypeFlag:Boolean = false;
	for each(var item:Object in dataArray)
	{
		if(reportTypeFlag)
		{
			break;
		}
		switch(String(item.dropDownKey))
		{
			case BusinessConstants.FREQUENCY_CODES:
				frequencyFlag = true;				
				item.dropDownValue.sort = getSort("orderNo");
				item.dropDownValue.refresh();
				frequecies = item.dropDownValue;
				break;
			case BusinessConstants.WEEKDAY_CODES:
				weekDaysFlag = true;
				item.dropDownValue.sort = getSort("subCode");
				item.dropDownValue.refresh();
				weekDays = item.dropDownValue;
				break;
			case BusinessConstants.REPORT_FORMAT_CODES:
				reportTypeFlag = true;			
				item.dropDownValue.sort = getSort("subCode");
				item.dropDownValue.refresh();
				reportFormats = item.dropDownValue;
				break;
			default:
				trace("DropDown item: "+item.dropDownKey);
				break;
		}
	}
}

private function onRunReport(event:Event):void
{
	sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"");
//	clearValidation();
	switch(exeRepId.selectedItem.subCode)
	{
		case ONLINE:
			sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"");
			break;
		case OFFLINE:
			offlineReportGenerate();
			break;
		default:
			break;
	}
}

private function canOfflineDataSubmit():Boolean
{
	var validatorArr:Array = new Array();
	for(var idx:int = 0; reportParameters!=null && idx<reportParameters.length; idx++)
	{
		var basePara:BaseParameterContainer = baseParamContId[idx] as BaseParameterContainer;
		var baseParameterChild:BaseParameterChild = basePara.getChildAt(0) as BaseParameterChild;
		var validator:Validator = baseParameterChild.getValidator() as Validator;
		if(validator!=null)
		{
			validatorArr.push(validator);
		}
	}
	validatorArr.push(emailValId);
	validatorArr.push(schDtValId);
	if(attachScheduleVisible)
	{
		validatorArr.push(freqValId);
		validatorArr.push(recurEveryValId);
		if(endOnId.dtId.selected)
			validatorArr.push(endOnId.dateValId);
		else if(endOnId.occId.selected)
			validatorArr.push(endOnId.occurenceValId);
			
	}
	var canSubmit:Boolean = CommonUtils.validateControls(validatorArr);
	var reportId:String = repId.selectedItem.reportId as String;
	if(!schDateId.isValidDate())
	{
		schDateId.errorString = resourceManager.getString('jbeam', 'valid_date_msg');
		sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,schDateId.errorString);
		return false;
	}
	
	return canSubmit;
}
private function offlineReportGenerate():void
{
	var canSubmit:Boolean = canOfflineDataSubmit();
	if(canSubmit)
	{
		var exeRep:ExecuteReport = getExecuteReport();	
		sendDataEvent(ReportsFacade.REPORT_GENERATE_OFFLINE,exeRep);
	}
	else
	{
		sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"Please enter value for required fields to generate report.");
	}		
}
public function offlineReportGenerateResult(runBatchReportData:BatchDetailsData):void
{
	reset();
	var reportRequest:String = CommonConstants.RUN_REPORT_REQUEST_SUBMITTED
	if(attachScheduleVisible)
	{
		reportRequest = CommonConstants.SCHEDULE_BATCH_REQUEST_SUBMITTED;
		resetVisibility();
	}
	this.currentState = "BASE";
//	sendDataEvent(ReportsFacade.RETRIEVE_REPORT_DROP_DOWN_OPTION,CommonConstants.INSTALLATION_DATA.installationCode);
	sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE, 
			"[Installation: "+ runBatchReportData.installationCode
			 + "] - " + reportRequest
			 + new Date(Number(runBatchReportData.responseTime)));
}

private function attachedSchedule():void
{
	sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"");
	clearValidation();
	var canSubmit:Boolean = canOfflineDataSubmit();
	if(canSubmit)
	{
		attachScheduleVisible = true;
		attachedScheduleHistory('ATTACHED_SCHEDULE');
		runReportVisible = false;
	}
	else
	{
		sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"Please enter value for required fields for attached schedule.");
	}
}
private function attachedScheduleHistory(view:String):void
{
	sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"");
	sendDataEvent(ReportsModule.SELECT_VIEW,view);
	clearValidation();
}

public function getDataForScheduleHistory():ExecuteReport
{
	return this.cacheExecuteReport;
}

public function getDataForAttachedSchedule():ExecuteReport
{
	var exeRep:ExecuteReport = cacheExecuteReport;
	exeRep.reportId = repId.selectedItem.reportId;	
//	exeRep.reportParameters = getReportParameter(reportParameters);			
	exeRep.startDate = exeRep.systemDate = StringUtil.trim(schDateId.text);
	if(schTimeId.hhIndex!=-1 && schTimeId.mmIndex!=-1 && schTimeId.ssIndex!=-1)
	{
		exeRep.hours = schTimeId.hhIndex<10?"0"+schTimeId.hhIndex:schTimeId.hhIndex.toString();
		exeRep.minutes = schTimeId.mmIndex<10?"0"+schTimeId.mmIndex:schTimeId.mmIndex.toString();
		exeRep.seconds = schTimeId.ssIndex<10?"0"+schTimeId.ssIndex:schTimeId.ssIndex.toString();
	}
	else
	{
		exeRep.hours = "00";
		exeRep.minutes = "00";
		exeRep.seconds = "00";
	}
	
	exeRep.startTime = exeRep.hours+":"+exeRep.minutes+":"+exeRep.seconds;
	return exeRep;
}

private function getExecuteReport():ExecuteReport
{
	var exeRep:ExecuteReport = new ExecuteReport();
	exeRep.reportId = repId.selectedItem.reportId;
	exeRep.reports = cacheExecuteReport.reports;
	exeRep.headingDetails = cacheExecuteReport.headingDetails;
	exeRep.query = cacheExecuteReport.query;
	exeRep.queryResults = cacheExecuteReport.queryResults;
	exeRep.reportParameters = getReportParameter(reportParameters);
	exeRep.reportUrl = cacheExecuteReport.reportUrl;
	exeRep.reportDataDetails = cacheExecuteReport.reportDataDetails; 		
	exeRep.startDate = exeRep.systemDate = StringUtil.trim(schDateId.text);
	exeRep.hours = schTimeId.hhIndex==-1?"00":schTimeId.hhIndex.toString();
	exeRep.minutes = schTimeId.mmIndex==-1?"00":schTimeId.mmIndex.toString();
	exeRep.seconds = schTimeId.ssIndex==-1?"00":schTimeId.ssIndex.toString();
	exeRep.startTime = exeRep.hours+":"+exeRep.minutes+":"+exeRep.seconds;
	
	return exeRep;
}
private function getReportParameter(_reportParams:ArrayCollection):ArrayCollection
{
	var temp:ArrayCollection = new ArrayCollection();
	var rptParam:ReportParameter = null;
	
//	rptParam = new ReportParameter();
//	rptParam.paramName = "fromExcel";
//	rptParam.paramValue = "Y?"+cacheExecuteReport.reportPath;
//	rptParam.dataType = "S";
//	temp.addItem(rptParam);
	
//	rptParam = new ReportParameter();
//	rptParam.paramName = "server";
//	rptParam.paramValue = cacheExecuteReport.reportServerUrl;
//	rptParam.dataType = "S";
//	temp.addItem(rptParam);
	
	if(repType.selectedIndex > 0)
	{
		rptParam = new ReportParameter();
		rptParam.paramName = "desformat";
		rptParam.paramValue = repType.selectedLabel as String;
		rptParam.dataType = "S";
		temp.addItem(rptParam);		
	}
	
	for each(var rep:Report in cacheExecuteReport.reports)
	{
		rptParam = new ReportParameter();
		rptParam.paramName = "report";
		rptParam.paramValue = rep.programName==null?"":rep.programName;
		rptParam.dataType = "S";
		temp.addItem(rptParam);

		
		rptParam = new ReportParameter();
		rptParam.paramName = "report_id";
		rptParam.paramValue = rep.reportId;
		rptParam.dataType = "S";
		temp.addItem(rptParam);
	}
	
	rptParam = new ReportParameter();
	rptParam.paramName = "destype";
	rptParam.paramValue = "CACHE";
	rptParam.dataType = "S";
	temp.addItem(rptParam);
	
	rptParam = new ReportParameter();
	rptParam.paramName = "user_id";
	rptParam.paramValue = CommonConstants.USER_PROFILE.userId;
	rptParam.dataType = "S";
	temp.addItem(rptParam);
	
	rptParam = new ReportParameter();
	rptParam.paramName = "dept_id";
	rptParam.paramValue = "REPORT";
	rptParam.dataType = "S";
	temp.addItem(rptParam);
	
	
	rptParam = new ReportParameter();
	rptParam.paramName = "mail_id";
	rptParam.paramValue = emailIds.text;
	rptParam.dataType = "S";
	temp.addItem(rptParam);
	
	rptParam = new ReportParameter();
	rptParam.paramName = "BATCH_RUN_DATE";
	rptParam.paramValue = schDateId.text + " " + schTimeId.getStringValue();
	rptParam.dataType = "S";
	temp.addItem(rptParam);
	
	rptParam = new ReportParameter();
	rptParam.paramName = "SCHEDULE_DATE";
	rptParam.paramValue = schDateId.text + " " + schTimeId.getStringValue();
	rptParam.dataType = "S";
	temp.addItem(rptParam);

	if(attachScheduleVisible)
	{	
		var scheduleParams:ReportParameter = null;
	
		var validatorArr:Array = new Array();
		validatorArr.push(freqValId);
		validatorArr.push(recurEveryValId);
		
		var first:Boolean = CommonUtils.validateControls(validatorArr);
		var second:Boolean = endOnId.isValid();
		var third:Boolean = true;	
		var selectedWeekDays:String = getDayFlag(this.chkSunday) 
 					+ getDayFlag(this.chkMonday)
				    + getDayFlag(this.chkTuesday)
				    + getDayFlag(this.chkWednesday)
				    + getDayFlag(this.chkThursday)
				    + getDayFlag(this.chkFriday)
				    + getDayFlag(this.chkSaturday);
		
		if(weekDaysValidate(frequencyId.selectedItem.subCode) && selectedWeekDays.length==0)
		{
			third = false;
			if(second)
			{
				sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"Please select a Weekday");
			}
		}
		
		if(first && second && third && isStartEndTimeValid())
		{
			scheduleParams = new ReportParameter;
			scheduleParams.paramName = "FREQUENCY";
			scheduleParams.paramValue = frequencyId.selectedItem.subCode;
			scheduleParams.dataType = "S";
			temp.addItem(scheduleParams);
			
			scheduleParams = new ReportParameter;
			scheduleParams.paramName = "RECUR_EVERY";
			scheduleParams.paramValue = recurEveryId.text;
			scheduleParams.dataType = "I";
			temp.addItem(scheduleParams);
			
			scheduleParams = new ReportParameter;
			scheduleParams.paramName = "FUTURE_SCHEDULE";
			scheduleParams.paramValue = groupId.selection.name;
			scheduleParams.dataType = "S";
			temp.addItem(scheduleParams);
			
			if(enableDays && selectedWeekDays.length > 0)
			{
				scheduleParams = new ReportParameter;
				scheduleParams.paramName = "WEEK_DAY";
				scheduleParams.paramValue = selectedWeekDays;
				scheduleParams.dataType = "S";
				temp.addItem(scheduleParams);
			}
			
			if(endOnId.dtId.selected)
			{
				var endOnDateTime:String = endOnId.endDateId.text + " " + endOnId.endTimeId.getStringValue();
				scheduleParams = new ReportParameter;
				scheduleParams.paramName = "END_ON_DATE";
				scheduleParams.paramValue = endOnDateTime;
				scheduleParams.dataType = "S";
				temp.addItem(scheduleParams);			
			}
			if(endOnId.occId.selected)
			{
				scheduleParams = new ReportParameter;
				scheduleParams.paramName = "END_ON_OCCURRENCE";
				scheduleParams.paramValue = endOnId.occurenceId.text;
				scheduleParams.dataType = "S";
				temp.addItem(scheduleParams);				
			}
			if(startTimeOnlyId.enabled)
			{
				scheduleParams = new ReportParameter;
				scheduleParams.paramName = "START_TIME";
				scheduleParams.paramValue = startTimeOnlyId.getStringValue();	
				scheduleParams.dataType = "S";
				temp.addItem(scheduleParams);				
			}
			if(endTimeOnlyId.enabled)
			{
				scheduleParams = new ReportParameter;
				scheduleParams.paramName = "END_TIME";
				scheduleParams.paramValue = endTimeOnlyId.getStringValue();	
				scheduleParams.dataType = "S";
				temp.addItem(scheduleParams);				
			}
			
			scheduleParams = new ReportParameter;
			scheduleParams.paramName = "SKIP_FLAG";
			scheduleParams.paramValue = comboSkipFlag.selectedItem.flagValue; 
			scheduleParams.dataType = "S";
			temp.addItem(scheduleParams);
			
			if(this.comboSkipFlag.selectedItem != "NA")
			{
				scheduleParams = new ReportParameter;
				scheduleParams.paramName = "CALENDAR";
				scheduleParams.paramValue = txtBatchCalendar.text;
				scheduleParams.dataType = "S";
				temp.addItem(scheduleParams);
			}
			
		}
	}
	
	
	if(_reportParams != null || _reportParams.length > 0)
	{
		for each(var _repParam:ReportParameter in _reportParams)
		{
			rptParam = new ReportParameter();
			rptParam.reportId = _repParam.reportId;
			rptParam.paramName = _repParam.paramName;
			rptParam.dateSpecificParamValue = _repParam.dateSpecificParamValue;
			rptParam.dataType = _repParam.dataType;
			rptParam.paramValue = _repParam.paramValue;
			rptParam.staticDynamicFlag = _repParam.staticDynamicFlag;
			temp.addItem(rptParam);
		}
	}
	return temp;
}

public function getReqInstructionLogData():ReqInstructionLog
{
	var reqInstructionLog:ReqInstructionLog = new ReqInstructionLog();
	reqInstructionLog.installationCode = CommonConstants.USER_PROFILE.installationCode;
	reqInstructionLog.instructingUser = CommonConstants.USER_PROFILE.userId;
	reqInstructionLog.instructionTime = new Date().getTime();
	reqInstructionLog.message = CommonConstants.RUNEREPORT;
	return reqInstructionLog; 
}
