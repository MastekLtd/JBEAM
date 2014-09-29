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

import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.BusinessConstants;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.ConfigParameter;
import com.majescomastek.jbeam.model.vo.Entity;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.InstructionParameter;
import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
import com.majescomastek.jbeam.view.components.viewschedule.SkipScheduleComponent;

import flash.events.Event;
import flash.events.KeyboardEvent;
import flash.ui.Keyboard;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.controls.CheckBox;
import mx.controls.List;
import mx.events.CloseEvent;
import mx.events.ListEvent;
import mx.validators.Validator;


private var instParamsList:ArrayCollection = null;

[Bindable]
public var selectedItem:Object;
  
[Bindable]
public var batchTypeList:ArrayCollection;
  
[Bindable]
public var clearParamsList:ArrayCollection = new ArrayCollection();
  
[Bindable]
public var params:ArrayCollection;
  
[Bindable]
public var endOnTimeMinList:ArrayCollection;
  
[Bindable]
public var endOnTimeList:ArrayCollection;

[Bindable]
public var instCode:String = null;

public var instTimeZoneOffset:Number = 0;
public var instTimeZoneShortName:String = null;
	
private function selectParams(event:ListEvent):void
{
	this.selectedItem = List(event.target).selectedItem;
	clearParamsList.addItem(this.selectedItem);
}

private function addToParamsList(event:Event):void
{		
	params = lstParamList.dataProvider as ArrayCollection;
	if(params == null)
	{
		params = new ArrayCollection();
	}	
	
	if(comboEntity.selectedItem.data == 0)
	{
		Alert.show("Please select Entity");
	}
	else if(txtBatchParam.text == "")
	{
		Alert.show("Please enter value");
		txtBatchParam.setFocus();
	}
	else if(txtBatchParam.errorString.length >0)
	{
		Alert.show('Please enter valid value');
		txtBatchParam.setFocus();
		return;
	}
	else 
	{
		params.addItem(comboEntity.selectedItem.label + "=" + 	txtBatchParam.text);
		txtBatchParam.text = "";
		lstParamList.dataProvider = params;
		var newParamString:String = "";
		if(params != null)
		{
			for(var i:int = 0; i < params.length; i++)
			{
				if(newParamString == "")
				{
					newParamString = params[i].toString();	
				}	
				else
				{
					newParamString =  newParamString + ";" + params[i].toString();
				}
			}
			this.paramListData.text = newParamString;
		}
	}
}

//Reset the Parameter list
private function clearParams():void
{
	try
	{
		if(clearParamsList.length == 0)
		{
			lstParamList.dataProvider = null;
		}	
		else if(clearParamsList.length > 0) 
		{
			for(var i:int = 0; i < lstParamList.dataProvider.length; i++)
			{
				for(var j:int = 0; j < clearParamsList.length; j++)
				{
					if(lstParamList.dataProvider[i] == clearParamsList[j])
					{
						lstParamList.dataProvider.removeItemAt(i);	
					}
				}
			}
			clearParamsList.removeAll();
		}
	}catch(err:Error){}
}
	
private function cancelRecurrence():void
{
	clearRecurrenceData();
	this.recurrencePanel.visible = false;
	this.btnAttachRecurrence.enabled = true;
	this.btnRun.enabled = true;
	this.btnRun.visible = true;
	reccurrenceFlag = false;
	this.chkDateRun.selected = false;
	clearBatchParams();
}

//Reset all the fields 
private function clearAllParams():void
{
	this.txtBatchName.text = "";
	this.txtBatchDate.text = "";
	this.txtEndTime.text = "";
	this.chkDateRun.selected = false;
	
	clearBatchParams();
}

//Reset all the fields 
private function clearBatchParams():void
{
	this.comboEntity.selectedIndex = 0;
	this.txtBatchParam.text = "";
	this.txtBusDate.text = "";
	this.lstParamList.dataProvider = null;		
	this.comboEntity.enabled = true;
	this.txtBatchParam.enabled = true;
	this.txtBusDate.enabled = true;
	this.lstParamList.enabled = true;	
	this.txtBatchDate.errorString = '';
}

private function clearRecurrenceData():void
{
	clearWeekDays();
	this.comboSkipFlag.selectedIndex = 0;
	this.recurrenceStepper.value = recurrenceStepper.minimum;
	if(radEndOnDate.selected)
	{
//		radEndOnDate.selected = false;		
		clearEndOnDate();
//		disableEndOnDate();
	}
	if(radEndOnOccurence.selected)
	{
		radEndOnOccurence.selected = false;			
		this.endOnOccuranceStepper.value = recurrenceStepper.minimum;
		this.endOnOccuranceStepper.enabled = false;
	}
}

private function removeParams():void
{
 	var selectedIndices:Array = lstParamList.selectedIndices; 		
	Alert.show(selectedIndices.toString());  
}
 	
private function clearEndOnDate():void
{
	this.dcEndOnDate.selectedDate = null;
	this.comboEndOnHr.dataProvider = endOnTimeList;
	this.comboEndOnMM.dataProvider = endOnTimeMinList;
	this.comboEndOnSS.dataProvider = endOnTimeMinList;
}

private function clearExecuteOnDate():void
{
	this.dcEndOnDate.selectedDate = null;
 	this.comboEndOnHr.dataProvider = endOnTimeList;
 	this.comboEndOnMM.dataProvider = endOnTimeMinList;
 	this.comboEndOnSS.dataProvider = endOnTimeMinList;
}

private function clearWeekDays():void
{
	this.chkSunday.selected = false;
	this.chkMonday.selected = false;
	this.chkTuesday.selected = false;
	this.chkWednesday.selected = false;
	this.chkThursday.selected = false;
	this.chkFriday.selected = false;
	this.chkSaturday.selected = false;
	
}
 	
private function focusRadEndOnDate():void
{
	if(!this.radEndOnDate.selected)
	{
 		radEndOnDate.selected = true;
 		this.endOnOccuranceStepper.value = endOnOccuranceStepper.minimum; 			
 	}
	else
	{
		this.endOnOccuranceStepper.enabled = false;
 		this.endOnOccuranceStepper.value = endOnOccuranceStepper.minimum; 			
		this.dcEndOnDate.enabled = true;
		this.comboEndOnHr.enabled = true;
		this.comboEndOnMM.enabled = true;
		this.comboEndOnSS.enabled = true;
	}
 }
 
 private function focusRadEndOnOccurence():void
 { 		
 	if(!this.radEndOnOccurence.selected)
 	{
 		radEndOnOccurence.selected = true;
 		clearEndOnDate();
 	}
	else
	{
		this.endOnOccuranceStepper.enabled = true;
		disableEndOnDate();
	}
 }

private function disableEndOnDate():void
{
	this.dcEndOnDate.text = "";
	this.dcEndOnDate.errorString = '';
	this.dcEndOnDate.enabled = false;
	this.comboEndOnHr.enabled = false;
	this.comboEndOnMM.enabled = false;
	this.comboEndOnSS.enabled = false;		
}
 	
private function doDateLabel(item:Date):String 
{
	return dateFormatterDB.format(item);
}
	
private function doStartDateLabel(item:Date):String 
{
	return formatDateTimeHHNNSS.format(item);
}

private function doStartDateLabelUSD(item:Date):String 
{
	return formatDateTimeUSD.format(item);
}

private function doStartDateLabelHH(item:Date):String 
{
	return formatDateTimeHH.format(item);
}

private function doStartDateLabelNN(item:Date):String 
{
	return formatDateTimeNN.format(item);
}

private function doStartDateLabelSS(item:Date):String 
{
	return formatDateTimeSS.format(item);
}
 /**
  * This function would handle the batch parameters panel.
  * If the checkbox for 'Date Run' is selected, 
  * then the batch parameters will be deactivated.
  * Else these will be active.  
  *  
  */
private function handleBatchParameters(event:Event):void
{
 	if(this.chkDateRun.selected == true && 
		(this.btnAttachRecurrence.enabled == false || this.btnAttachRecurrence.enabled == true))
 	{
		disableBatchParams(); 		
 	}
	else if(this.chkDateRun.selected == false && this.btnAttachRecurrence.enabled == false)
 	{
		//warning 		
		Alert.show("This action will cancel recurrance and you can run Special batch.\n" +
			" Are you sure you want to cancel recurrance ?",
			"Confirm cancellation",Alert.YES|Alert.NO, null, cancelHandler, null, Alert.NO);
 	}
 	else if(this.chkDateRun.selected == false && this.btnAttachRecurrence.enabled == true)
 	{
 		this.comboEntity.enabled = true;
		this.txtBatchParam.enabled = true;
		this.lstParamList.enabled = true;
		this.txtBusDate.enabled = true;
 	}
}

private function cancelHandler(event:CloseEvent):void 
{
	if (event.detail==Alert.YES)
	{
		cancelRecurrence();
	}
	else if(event.detail==Alert.NO)
	{
		this.chkDateRun.selected = true;
	}
} 

private function disableBatchParams():void
{
	this.comboEntity.selectedIndex = 0;
	this.txtBatchParam.text = "";
	this.lstParamList.dataProvider = null;
	this.paramListData.text = "";
	
	this.comboEntity.enabled = false;
	this.txtBatchParam.enabled = false;
	this.lstParamList.enabled = false;
	
	this.txtBusDate.enabled = false;
	this.txtBusDate.text = "";
}
 	
 /**
 * Event handler function called when the button 'Run' is pressed.
 *  
 * If pressed key is ENTER then the function initializeRunBatchWebservice()
 * will be called. 
 */
private function onEnterClick(evt:KeyboardEvent):void
{
	if(evt.keyCode == Keyboard.ENTER)
	{
	}
}
 	
/**
 * Function will be called to validate the server 
 * details.
 */
public function validateBatchDetails():Boolean
{
	var arrValidators:Array = [valBatchDate, valStartDate, strBatchDate, strStartDate];
	
	if(reccurrenceFlag && this.radEndOnDate.selected)
		arrValidators = [valBatchDate, valStartDate, valEndOnDate, strBatchDate, strStartDate, strEndOnDate];
	
	if(Validator.validateAll(arrValidators).length != 0)
	{
		CommonUtils.showValidationMessage(arrValidators, true);
		return false;
	}
	else
	{
		return true;
	}
}

/**
 * Function will be called to validate the details.
 */
public function validateDates():Boolean
{
	if(dcEndOnDate.text.length == 0)
		return true;
	
	var compareResult:Boolean = false;
	if(txtBatchDate.text.length > 0 && dcEndOnDate.text.length > 0)
	{
		var effDate:Date = new Date(Date.parse(this.txtBatchDate.text));
		var expDate:Date = new Date(Date.parse(this.dcEndOnDate.text));
		compareResult = CommonUtils.compareDates(effDate, expDate);
		if(!compareResult)
			AlertBuilder.getInstance().show(resourceManager.getString('jbeam','end_on_date_greater_than_start_Date'));
	}
	return compareResult;
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
[Bindable]
private var skipFlagList:ArrayCollection;
[Bindable]
private var keepAliveList:ArrayCollection;


/**
 * To populate the entity combo box 
 */
public function populateEntityDataProvider(batchEntityList:ArrayCollection):void
{	
	if(batchEntityList == null) return;
	
	entityDataProvider = new ArrayCollection();
	entityDataProvider.addItem({label:"-SELECT-", data:0});
	if(batchEntityList != null)
	{
		for(var i:int =0 ; i < batchEntityList.length; i++)
		{
			var entityData:Entity = batchEntityList[i];
			entityDataProvider.addItem({label:entityData.entityName, data:(i+1), 
				desc:entityData.description, noReqPram:entityData.numberOfRequiredParameters});				
		}			
	}
}

/**
 * To populate the skip flag combo box 
 */
private function createSkipFlagList():void
{
//	skipFlagList = new ArrayCollection();
//    skipFlagList.addItem({flagObject:"Skip Schedule", flagValue:"SS"});
//    skipFlagList.addItem({flagObject:"Postpone Day by 1 (D+)", flagValue:"D+"});
//    skipFlagList.addItem({flagObject:"Advance Day by 1 (D-)", flagValue:"D-"});
//    skipFlagList.addItem({flagObject:"Not Applicable", flagValue:"NA"});	
	sendEvent(GET_DROP_DOWN_DATA_REQUEST);
}

public function handleDropDownResult(data:Object):void
{
	var dropDownData:ArrayCollection = data as ArrayCollection;
	skipFlagList = new ArrayCollection();
//	for each(var config:ConfigParameter in dropDownData)
//	{
//    	skipFlagList.addItem({flagObject:config.description , flagValue:config.subCode});		
//	}
	for each(var item:Object in dropDownData)
	{
		if(String(item.dropDownKey) == BusinessConstants.SKIP_SCHEDULE_CODE)
			skipFlagList = item.dropDownValue;				
	}
//    skipFlagList.addItem({flagObject:"Skip Schedule", flagValue:"SS"});
//    skipFlagList.addItem({flagObject:"Postpone Day by 1 (D+)", flagValue:"D+"});
//    skipFlagList.addItem({flagObject:"Advance Day by 1 (D-)", flagValue:"D-"});
//    skipFlagList.addItem({flagObject:"Not Applicable", flagValue:"NA"});	
	
}

/**
 * To populate the Keep Alive combo box 
 */
private function createKeepAliveList():void
{
	keepAliveList = new ArrayCollection();
    keepAliveList.addItem({flagObject:"No", flagValue:"N"});
    keepAliveList.addItem({flagObject:"Yes", flagValue:"Y"});
}

/**
 * To populate the end on time 
 */
private function initEndOnTimeList(installationData:InstallationData):void
{
	var dt:Date = new Date();
	dt.setTime(dt.getTime() + dt.getTimezoneOffset() * 1000 * 60 + installationData.timezoneOffset);
	
	var executeOnHH:String =  CommonUtils.formatDate(dt, "HH");
	var executeOnMM:String =  CommonUtils.formatDate(dt, "NN");
	var executeOnSS:String =  CommonUtils.formatDate(dt, "SS");
	
	this.txtStartDate.text = CommonUtils.formatDate(dt, CommonConstants.US_DATE_FORMAT);
	    
	endOnTimeList = new ArrayCollection();
	for(var i:int= 0; i < 24; i++)
	{
		if(i < 10)
		{
			endOnTimeList.addItem({object:"0" + i , value:"" + i});
		}
		else
		{
			endOnTimeList.addItem({object:"" + i , value:"" + i});
		}
	}
     
	this.comboStartDateHr.selectedIndex = Number(executeOnHH);
	
	endOnTimeMinList = new ArrayCollection();
	for(var j:int= 0; j < 60; j++)
	{
		if(j < 10)
		{
			endOnTimeMinList.addItem({object:"0" + j, value:"" + j});
		}
		else
		{
			endOnTimeMinList.addItem({object:"" + j , value:"" + j});
		}	
	}
	this.comboStartDateMM.selectedIndex = Number(executeOnMM);
	this.comboStartDateSS.selectedIndex = Number(executeOnSS);
	
}	
//Called on the click of Link Button 'Failed Objects'
private function attachRecurrence():void
{
	var windowTitleString:String = "Recurrence Window";
	
//	newAttachScheduleWindow(windowTitleString, "null");
	this.recurrencePanel.visible = true;
	this.btnAttachRecurrence.enabled = false;
	this.btnAttach.label = "Schedule";
	this.btnRun.enabled = false;
	this.btnRun.visible = false;
	this.recurrencePanel.title = windowTitleString;
	reccurrenceFlag = true;
	this.chkDateRun.selected = true;
	disableBatchParams();
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

private function preapreDataToRunBatch():ReqInstructionLog
{
	var batchDate:String = txtBatchDate.text;	 		
 	this.txtBatchDate.text = batchDate.toUpperCase();
 	
 	if(this.chkDateRun.selected == false && paramListData.text.length > 0)
 	{
		this.hBatchType.text = CommonConstants.SPECIAL_RUN;
 	}
 	else
	{
		this.hBatchType.text = CommonConstants.DATE_RUN;
	}
	
	var busDate:String = null;
	if(this.txtBusDate.text.length > 0)
	{
		var dt:Date = new Date(Date.parse(txtBusDate.text));
	//	dt.setTime(dt.getTime() + dt.getTimezoneOffset() * 1000 * 60 + instTimeZoneOffset);
	//	trace("Date with TimeZone = "+CommonUtils.formatDate(dt));
		
		busDate = txtBusDate.text + " 00:00:00";//CommonUtils.doRequestTypeFormat(dt, instTimeZoneShortName);
		trace("Business Date = "+ busDate);		
	}
	else
	{
		trace("Business Date not provided.");		
	}
	var executeOnDateTime:String = txtStartDate.text 
			+ " " + comboStartDateHr.selectedItem.object
			+ ":" + comboStartDateMM.selectedItem.object
			+ ":" + comboStartDateSS.selectedItem.object;
	
	var reqInstructionLog: ReqInstructionLog =	createReqInstructionLog(executeOnDateTime, busDate);			
	return reqInstructionLog;
}

private function validateEntities():Boolean
{
	
	if(paramListData.text.length == 0 && !chkDateRun.selected)
	{
		AlertBuilder.getInstance().show(CommonConstants.BATCH_TYPE_NOT_SELECTED);
		return false;
	}
	return true;	
}

private function createReqInstructionLog(executeOnDateTime:String, busDate:String):ReqInstructionLog
{
	
 	var reqInstructionLog:ReqInstructionLog = null;
	
	//Check if the instruction parameter list is empty
	// If found null create new list.
 	if(instParamsList == null)
 	{
 		instParamsList = new ArrayCollection();
 	}
 	
 	//Batch name is optional
	if(txtBatchName.text.length > 0 ) 
	{
		instParamsList.addItem({name:"BATCH_NAME", slNo:1, type:"S"
						, value:txtBatchName.text});
	}
	instParamsList.addItem({name:"BATCH_TYPE", slNo:2, type:"S"
						, value:hBatchType.text});
	instParamsList.addItem({name:"BATCH_RUN_DATE", slNo:3, type:"TS"
						, value:txtBatchDate.text + " 23:59:59"});
	if(busDate != null)
	{
		instParamsList.addItem({name:"BUSINESS_DATE", slNo:13, type:"TS"
							, value:busDate});		
	}
	if(txtEndTime.text.length > 0 ) 
	{
		instParamsList.addItem({name:"BATCH_ENDS_IN_MINUTES", slNo:4, type:"I"
						, value:txtEndTime.text});
	}
	instParamsList.addItem({name:"SCHEDULE_DATE", slNo:11, type:"TS"
									, value:executeOnDateTime});
									
	//If the reccurrenceFlag is true, then the batch will be scheduled 
	//i.e. the instruction parameters will be used. 
	if(reccurrenceFlag)
	{						
		var weekDays:String = getWeekDays();
		instParamsList.addItem({name:"FREQUENCY", slNo:5, type:"S"
								, value:txtFrequency.text});
		instParamsList.addItem({name:"RECUR_EVERY", slNo:6, type:"I"
								, value:recurrenceStepper.value});
		
		
		instParamsList.addItem({name:"WEEK_DAY", slNo:8, type:"S"
									, value:weekDays});

		instParamsList.addItem({name:"SKIP_FLAG", slNo:9, type:"S"
									, value:comboSkipFlag.selectedItem.subCode});
		
		instParamsList.addItem({name:"KEEP_ALIVE", slNo:12, type:"S"
									, value:comboKeepAlive.selectedItem.flagValue});
		
		
		var endOnDateTime:String = null;
		
		if(this.radEndOnDate.selected)		
		{
			endOnDateTime = dcEndOnDate.text 
				+ " " + comboEndOnHr.selectedItem.object
				+ ":" + comboEndOnMM.selectedItem.object
				+ ":" + comboEndOnSS.selectedItem.object;
		}
								
		if(this.radEndOnDate.selected && endOnDateTime != null && endOnDateTime.length > 0 )
		{						
			instParamsList.addItem({name:"END_ON_DATE", slNo:7, type:"TS"
								, value:endOnDateTime});
		}
		
		if(this.radEndOnOccurence.selected)
		{		
			var noOFweekdays:String = weekDays;
			var j:int = 0;
			for (var i2:int;i2 < noOFweekdays.length ; i2++) 
			{
				if("1" == noOFweekdays.charAt(i2))
				{
					j++;	
				}
			}
			
			
			trace("No of non-working days in a week = " + j);
			if(j == 0)
			{
				instParamsList.addItem({name:"END_ON_OCCURRENCE", slNo:7, type:"I"
									, value:endOnOccuranceStepper.value});				
			}
			else
			{
				instParamsList.addItem({name:"END_ON_OCCURRENCE", slNo:7, type:"I"
										, value:endOnOccuranceStepper.value * j});				
			}
		}
									
		
		if(this.comboSkipFlag.selectedItem != "NA")
		{
			instParamsList.addItem({name:"CALENDAR", slNo:10, type:"S"
									, value:"BATCH CALENDAR"});	
		}
	}
	
	//Batch date is mandatory
	if(validateBatchDetails() && validateEntities() && validateDates())
	{
		reqInstructionLog = new ReqInstructionLog();
 		reqInstructionLog.installationCode = instCode;
 		reqInstructionLog.instructingUser = CommonConstants.USER_PROFILE.userId;
		reqInstructionLog.instructionTime = new Date().getTime();
		reqInstructionLog.entityValues = paramListData.text;
		reqInstructionLog.message = CommonConstants.BSRUNBATCH;
		
		var instructionParametersList:ArrayCollection = new ArrayCollection();
		var instParam:InstructionParameter;
		
		for(var i2:int = 0; i2 < instParamsList.length; i2++)
		{
			instParam = new InstructionParameter();
			instParam.name = instParamsList[i2].name;
			instParam.slNo = instParamsList[i2].slNo;
			instParam.type = instParamsList[i2].type;
			instParam.value = instParamsList[i2].value;
			instructionParametersList.addItem(instParam);
		}
		instParamsList = null;
 		reqInstructionLog.instructionParameters = instructionParametersList;	 					
	}
	else
	{
		//When the validation fails, make remove previous data from list. 
		if(instParamsList != null)
		{
	 		instParamsList.removeAll();
	 		instParamsList = null;	 			
	 	}
	}
	return reqInstructionLog; 
}

private function getWeekDays():String
{
	var weekDaysCombo:String = getDayFlag(this.chkSunday) 
		+ getDayFlag(this.chkMonday)
		+ getDayFlag(this.chkTuesday)
		+ getDayFlag(this.chkWednesday)
		+ getDayFlag(this.chkThursday)
		+ getDayFlag(this.chkFriday)
		+ getDayFlag(this.chkSaturday);
	
	trace("Weekdays = " + weekDaysCombo);
	return weekDaysCombo;
}
