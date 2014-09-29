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
import com.majescomastek.flexcontrols.CustomDateChooser;
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.CalendarData;

import flash.events.Event;
import flash.events.MouseEvent;

import mx.collections.ArrayCollection;
import mx.collections.Sort;
import mx.collections.SortField;
import mx.containers.Grid;
import mx.containers.GridItem;
import mx.containers.GridRow;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.events.CalendarLayoutChangeEvent;
import mx.resources.ResourceBundle;
import mx.resources.ResourceManager;



private var nonWorkingDays:ArrayCollection = new ArrayCollection();
private var nonWorkingDatesList:ArrayCollection = new ArrayCollection();
private var myGrid:Grid = new Grid();
private var calendarTypeArray:Array = new Array();

private var currentYearVal:int = 0;

private var currentYearStepperVal:int = 0;
private var curValue:int = 0;

private const MAX_YEAR_VAL:int = 2100;

/** The calendar list to be displayed */
[Bindable]
private var calendarList:ArrayCollection = new ArrayCollection();

private function createCalendar():void 
{
	var idValue:int = 0;
	var gridRow:GridRow;
	var gridItem:GridItem;
	var dateChooserObj:CustomDateChooser = null;
	var disabledDates:Array = new Array();	
//	disabledDates.push({rangeEnd: new Date()});	
	var today:Date = new Date();
	var yesterday:Date = new Date(today.fullYear, today.month, today.date-1);
	disabledDates.push({rangeEnd:yesterday});

	for(var i:int =0 ; i < 3; i++)
	{
		gridRow = new GridRow();
		gridRow.id = "myGridRow_"+ i;
		
		for(var j:int =0 ; j < 4; j++)
		{			
			gridItem = new GridItem();
			
			dateChooserObj = new CustomDateChooser();
			dateChooserObj.highLightDates(nonWorkingDays);

			switch(i){
				case 0:
					idValue = i + j;					
					break;
				case 1:
					idValue = i + j + 3;
					break;
				case 2:
					idValue = i + j + 6;
					break;
			}
			gridItem.id = "myGridItem_"+ idValue;
			dateChooserObj.id = "dc_" + idValue;
			dateChooserObj.displayedMonth = idValue;
			dateChooserObj.height = 145;
			dateChooserObj.allowDisjointSelection = true;
			dateChooserObj.allowMultipleSelection = true;
			dateChooserObj.addEventListener(CalendarLayoutChangeEvent.CHANGE,
															 changeDateEvent);
//			dateChooserObj.removeEventListener(CalendarLayoutChangeEvent.CHANGE,changeDateEvent, false); 
			dateChooserObj.disabledRanges= disabledDates;			

			gridItem.addChild(dateChooserObj);
			gridRow.addChild(gridItem);
		}
		myGrid.addChild(gridRow);		
	}
	this.schedulerCanvas.addChild(myGrid);
	
	setDefaultCalendarYear();
		
	this.nsYear.value = dateChooserObj.displayedYear;	
	currentYearVal = dateChooserObj.displayedYear;	
}

private function setDefaultCalendarYear():void 
{
//	this.nsYear.enabled = true;
//	this.nsYear.value = getNearestAvailableCalendarYear();
	this.nsYear.minimum = new Date().fullYear;	
	this.nsYear.maximum = MAX_YEAR_VAL;
}

private function changeDateEvent(eventObj:CalendarLayoutChangeEvent):void 
{
    // Make sure selectedDate is not null.
    if (eventObj.newDate == null) 
	{
        return; 
    }
    var customDateChooser:CustomDateChooser = eventObj.currentTarget as CustomDateChooser;
    var dateStr:String = eventObj.newDate.toDateString();

	if(nonWorkingDays == null)
		nonWorkingDays = new ArrayCollection();
		
	if(nonWorkingDays.contains(dateStr)) 
	{
		nonWorkingDays.removeItemAt(nonWorkingDays.getItemIndex(dateStr));
	}
	else 
	{
		nonWorkingDays.addItem(dateStr);
	}
	
	nonWorkingDays.sort;	
	this.dgDatesList.dataProvider = nonWorkingDays;
	var dateChooserObj:CustomDateChooser = eventObj.currentTarget as CustomDateChooser;
	dateChooserObj.highLightDates(nonWorkingDays);
	
	if(nonWorkingDays != null && nonWorkingDays.length > 0)
	{
//		setCurrentCalendarYear(this.nsYear.value);
	}
	else if(nonWorkingDays.length == 0)
	{
		setDefaultCalendarYear();
//		clearCalendar();		
	}
}

private function changeCalendarYearEvent(event:Event):void 
{
	var currentYear:String = null;
	currentYear = event.currentTarget.data;
	if(currentYear == null)
	{		
		currentYear = event.target.data.year;
		curValue = 1;
	}
	else
	{
		curValue = 0;
	}
	currentYearVal = Number(currentYear);
	if(curValue == 0)
	{
		checkCalendarYear(currentYearVal);
		return;
	}
	
	changeCalendarYear(currentYearVal);
}

private function changeCalendarYear(currentYear:int):void 
{
	var idValue:int = 0;	
	var myGridArray:Array = myGrid.getChildren();

	for(var i:int =0 ; i < myGridArray.length; i++)
	{
		var myGridRowArray:Array = myGridArray[i].getChildren();
		for(var j:int =0 ; j < myGridRowArray.length; j++)
		{
			var myGridRowItemArray:Array = myGridRowArray[j].getChildren();
			for(var k:int =0 ; k < myGridRowItemArray.length; k++)
			{
				
				switch(i)
				{
					case 0:
						idValue = i + j;					
						break;
					case 1:
						idValue = i + j + 3;
						break;
					case 2:
						idValue = i + j + 6;
						break;
				}
				if(myGridRowItemArray[k].id == "dc_" + idValue)
				{
					myGridRowItemArray[k].displayedYear = currentYear;		
				}
			}		
		}	
	}
}
public function displayDate(item:Object, column:DataGridColumn):String 
{
	return this.dateFormater.format(item);
}
private function checkCalendarYear(calYear:int):void 
{
	if(existsCalendarYear(calYear))
	{	
		populateCalendarData(String(calYear));
//		AlertBuilder.getInstance().show("This year is already defined. \n Please choose different year.");		
//		return;
	}
	else
	{
		clearDatesList();
	}
	changeCalendarYear(calYear);
}

//private function checkCalendarYearAtStart(calYear:int):void 
//{
//	if(existsCalendarYear(calYear))
//	{
//		this.nsYear.value = calYear + 1;	
//	}
//}
private function existsCalendarYear(calYear:int):Boolean 
{
	var stopFlag:Boolean = false;	
	for(var m:int =0 ; m < calendarList.length; m++)
	{
		if(calendarList[m] != null && calendarList[m].year == calYear)
		{
			stopFlag = true;
			break;
		}
	}
	return stopFlag;
}



private function getNearestAvailableCalendarYear():int 
{
	var availableYear:int = new Date().fullYear;
	
	CommonUtils.sortArrayCollection(calendarList, "year", true);
	
	for(var m:int =0 ; m < calendarList.length; m++)
	{
		if(calendarList[m] != null && calendarList[m].year == availableYear)
		{
			availableYear++;
		}
	}
	return availableYear;
}

private function clearCalendarData():void 
{	
	this.dgCalendarList.selectedItem = null;
	nonWorkingDatesList = null;
	this.nsYear.value = getNearestAvailableCalendarYear();
	checkCalendarYear(this.nsYear.value);
//	changeCalendarYear(this.nsYear.value);
//	this.comboCalendarType.selectedIndex = calendarTypeArray.indexOf("-Select-");
	clearCalendar();
	clearDatesList();
	setDefaultCalendarYear();
	
}

private function clearDatesList():void
{
	this.dgDatesList.dataProvider = null;
	nonWorkingDays = null;	
}

private function clearCalendar():void 
{
	var idValue:int = 0;
	var myGridArray:Array = myGrid.getChildren();
	for(var i:int =0 ; i < myGridArray.length; i++)
	{
		var myGridRowArray:Array = myGridArray[i].getChildren();
		for(var j:int =0 ; j < myGridRowArray.length; j++)
		{
			var myGridRowItemArray:Array = myGridRowArray[j].getChildren();
			for(var k:int =0 ; k < myGridRowItemArray.length; k++)
			{
				switch(i)
				{
					case 0:
						idValue = i + j;					
						break;
					case 1:
						idValue = i + j + 3;
						break;
					case 2:
						idValue = i + j + 6;
						break;
				}
				if(myGridRowItemArray[k].id == "dc_" + idValue)
				{
					if(myGridRowItemArray[k].selectedRanges.length > 0)
					{
						myGridRowItemArray[k].selectedRanges = new Array();
					}		
				}	
			}		
		}	
	}
}
private function displaySelectedItem(event:MouseEvent):void 
{
	if(event == null || event.target == null || event.target.data == null)
		return;
	
	if(this.nsYear.value != event.target.data.year)
	{
		changeCalendarYearEvent(event as Event);
	}
	
	this.nsYear.value = event.target.data.year;
//	setCurrentCalendarYear(this.nsYear.value);
	populateCalendarData(this.nsYear.value.toString());
	
}

private function populateCalendarData(calendarYear:String):void
{
	var calendarData:CalendarData = new CalendarData();
	calendarData.installationCode = installationData.installationCode;
	calendarData.calendarName = resourceManager.getString('jbeam','batch_calendar');
	calendarData.year = calendarYear;
	sendDataEvent(CalendarModule.CALENDAR_CLICK, calendarData);	
}

//private function setCurrentCalendarYear(currentYear:int):void 
//{
//	this.nsYear.enabled = false;
//	this.nsYear.value = getNearestAvailableCalendarYear();
//	this.nsYear.value = currentYear;
//	this.nsYear.minimum = currentYear;
//	this.nsYear.maximum = currentYear + 10;
//}

private function displayCalendarDates(dateList:ArrayCollection):void 
{
    var nonWorkingDates:Array = new Array();
	var dateStr:String = null;
	
	var dateListSrc:Array = dateList.source;
	if(nonWorkingDays != null)
	{
    	nonWorkingDays.removeAll();
		nonWorkingDays = null;			
	}
	
	nonWorkingDays = new ArrayCollection();
	
	for(var im:int = 0 ; im < dateListSrc.length; im++)
	{
		nonWorkingDates.push({rangeStart:new Date(dateListSrc[im].nonWorkingDate)
				, rangeEnd: new Date(dateListSrc[im].nonWorkingDate) });
		dateStr = new Date(dateListSrc[im].nonWorkingDate).toDateString();
		nonWorkingDays.addItem(dateStr);
	}
	
	var disabledDates:Array = new Array();	
	//	disabledDates.push({rangeEnd: new Date()});	
	var today:Date = new Date();
	var yesterday:Date = new Date(today.fullYear, today.month, today.date-1);
	disabledDates.push({rangeEnd:yesterday});
	
	dgDatesList.dataProvider = nonWorkingDays;
	
	var idValue:int = 0;
	var myGridArray:Array = myGrid.getChildren();
	for(var i:int =0 ; i < myGridArray.length; i++)
	{
		var myGridRowArray:Array = myGridArray[i].getChildren();
		for(var j:int =0 ; j < myGridRowArray.length; j++)
		{
			var myGridRowItemArray:Array = myGridRowArray[j].getChildren();
			for(var k:int =0 ; k < myGridRowItemArray.length; k++)
			{
				switch(i)
				{
					case 0:
						idValue = i + j;					
						break;
					case 1:
						idValue = i + j + 3;
						break;
					case 2:
						idValue = i + j + 6;
						break;
				}
				if(myGridRowItemArray[k].id == "dc_" + idValue)
				{
					myGridRowItemArray[k].selectedRanges = nonWorkingDates;							
				}
			}		
		}	
	}
}
	
	
