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
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.facade.reports.ReportsFacade;
import com.majescomastek.jbeam.model.vo.ExecuteReport;
import com.majescomastek.jbeam.model.vo.ReportParameter;

import flash.events.Event;
import flash.filters.DropShadowFilter;

import mx.collections.ArrayCollection;
import mx.collections.Sort;
import mx.collections.SortField;
 
[Bindable]private var frequecies:ArrayCollection = null;
[Bindable]private var weekDays:ArrayCollection = null;
[Bindable]public var enableDays:Boolean = true;
[Bindable]public var executeReport:ExecuteReport = null; 
[Bindable]
public var attachScheduleVisible:Boolean = false;
[Bindable]
public var runReportVisible:Boolean = false;
private var reccurrenceFlag:Boolean = false;

/**
* Function dispatches event with data.
*/
private function sendDataEvent(eventName:String, eventData:Object):void
{
	dispatchEvent(new CustomDataEvent(eventName, eventData, true));
}

/**
 * Dispatch an event with the given name.
 */
public function sendEvent(eventName:String):void
{
	dispatchEvent(new Event(eventName, true));
}
/**
* Function dispatches event with data.
*/
private function onBack(event:Event):void
{
	resetVisibility();
}

private function resetVisibility():void
{
	attachScheduleVisible = false;
	runReportVisible = true;	
}
	
/**
 * 
 * @param field
 * @return 
 * 
 */
private function getSort(field:String):Sort
{
	var sort:Sort = new Sort();
	sort.fields = [new SortField(field)];
	sort.compareFunction = 
						function myCompare(itemA:Object, itemB:Object, fields:Array = null):int
						{
							var a:int = int(itemA[field]);
							var b:int = int(itemB[field]);
							if (isNaN(a) && isNaN(b))
				            	return 0;
				            if (isNaN(a))
				            	return 1;
				            if (isNaN(b))
				            	return -1;
				            if (a < b)
				            	return -1;
				            if (a > b)
				            	return 1;
				            return 0;
						}
 
	return sort;
}

private function onFrequencyChange(event:Event):void
{
	setCurrentView(event.currentTarget.selectedItem.subCode);	
}

/**
 * 
 * @param value
 * 
 */
private function setCurrentView(value:String):void
{
	this.currentState = value;
}

/**
 * 
 * 
 */
private function onPredefined():void
{
	
}

/**
 * 
 * @param _executeReport
 * 
 */
public function getDisplayData(_executeReport:ExecuteReport):void
{
	this.executeReport = _executeReport;
	assigneValue(executeReport);
}

private function assigneValue(_executeReport:ExecuteReport):void
{
	clear();
	var freq:String = "";	
	for each(var item:Object in frequencyId.dataProvider)
	{
		if(_executeReport.frequency == item.subCode)
		{
			frequencyId.selectedItem = item;
			freq = _executeReport.frequency;
			break;
		}
	}
	setCurrentView(freq);
	_executeReport.systemDate =	CommonUtils.doStartDateLabelUSD(new Date());
	_executeReport.hours = CommonUtils.doStartDateLabelHH(new Date());
	_executeReport.minutes = CommonUtils.doStartDateLabelNN(new Date());
	_executeReport.seconds = CommonUtils.doStartDateLabelSS(new Date());
	
	recurEveryId.text = _executeReport.recurrence;	
//	startDateId.text = _executeReport.systemDate;
	groupId.selection = _executeReport.futureSchedule == "N"?noId:yesId;
//	startTimeId.setTime(int(_executeReport.hours),int(_executeReport.minutes),int(_executeReport.seconds));
	
//	for(var idx:int = 0; _executeReport.weekDay!=null && idx<_executeReport.weekDay.length; idx++)
//	{
//		var value:int = int(_executeReport.weekDay.charAt(idx));
//		if(value == 1)
//		{
//			wdId[idx].selected = true;
//		}
//		else
//		{
//			wdId[idx].selected = false;
//		}
//	}
	if(_executeReport.weekStartTime!=null)
	{
		var st:Array = _executeReport.weekStartTime.split(":");
		startTimeOnlyId.setTime(int(st[0]),int(st[1]),int(st[2]));
	}
	else
	{
		startTimeOnlyId.setTime(-1,-1,-1);
	}
	
	if(_executeReport.weekEndTime!=null)
	{
		var et:Array = _executeReport.weekEndTime.split(":");
		endTimeOnlyId.setTime(int(et[0]),int(et[1]),int(et[2]));
	}
	else
	{
		endTimeOnlyId.setTime(-1,-1,-1);
	}
}

private function clear():void
{
	frequencyId.selectedIndex = 0;
	recurEveryId.text = "";
//	startDateId.text = "";
//	startTimeId.setTime(-1,-1,-1);
	groupId.selection = yesId;	
	weekDays.refresh();
	startTimeOnlyId.setTime(-1,-1,-1);
	endTimeOnlyId.setTime(-1,-1,-1);
//	freqUsgId.text = "";
	clearValidation();
}

private var dayFlag:String = "0";
 
private function getDayFlag(componentObj:CheckBox):String
{
	if(componentObj.selected)
	{
		dayFlag = "1";
	}
	else
	{
		dayFlag = "0";
	}
	return dayFlag;
}

private function clearValidation():void
{
	frequencyId.errorString = "";
	frequencyId.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	recurEveryId.errorString = "";
	recurEveryId.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	endOnId.clearValidation();
}

private function weekDaysValidate(subCode:String):Boolean
{
	switch(subCode)
	{
		case "WEEK":
			return true;
		case "LAST_MTH":
			return true;
		case "FIRST_MTH":
			return true;
		case "SECOND_MTH":
			return true;
		case "THIRD_MTH":
			return true;
		case "FOURTH_MTH":
			return true;
		default:
			return false;
	}
	return false;
}

private function isStartEndTimeValid():Boolean
{
	if(startTimeOnlyId.enabled && endTimeOnlyId.enabled)
	{
		var hh:int = int(startTimeOnlyId.hhIndex);
		var tohh:int = int(endTimeOnlyId.hhIndex);
		var mm:int = int(startTimeOnlyId.mmIndex);
		var tomm:int = int(endTimeOnlyId.mmIndex);
		var ss:int = int(startTimeOnlyId.ssIndex);
		var toss:int = int(endTimeOnlyId.ssIndex);
		
		var schHH:int = int(executeReport.hours);
		var schMM:int = int(executeReport.minutes);
		var schSS:int = int(executeReport.seconds);
		
		if(hh==-1 && tohh==-1
		&& mm==-1 && tomm==-1
		&& ss==-1 && toss==-1)
		{
			return true;
		}
		else if(hh>-1 && tohh>-1
		&& mm>-1 && tomm>-1
		&& ss>-1 && toss>-1)
		{
			if(schHH == hh && schMM == mm && schSS == ss)
			{
				return true;
			}
			else if(schHH == tohh && schMM == tomm && schSS == toss)
			{
				return true;
			}
			else
			{
				sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"Schedule time should be matched with start time or end time.");
				return false;
			}
		}
		else
		{
			sendDataEvent(ReportsFacade.SHOW_ALERT_MESSAGE,"Invalid Time..!");
			return false;
		}
	}
	return true;
}
