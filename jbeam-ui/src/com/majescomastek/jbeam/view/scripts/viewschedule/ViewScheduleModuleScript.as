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
include "../../../common/CommonScript.as"

import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.facade.viewschedule.ViewScheduleModuleFacade;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;
import com.majescomastek.jbeam.model.vo.ReqProcessRequestSchedule;
import com.majescomastek.jbeam.model.vo.ResProcessRequestSchedule;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.events.CloseEvent;

import org.puremvc.as3.multicore.patterns.facade.Facade;


/** The event constant used to denote the request to fetch the list of all schedules */
public static const GET_SCHEDULE_DATA_REQUEST:String = "GET_SCHEDULE_DATA_REQUEST";

/** The event constant used to denote the request to navigate to default view */
public static const DEFAULT_VIEW_REQUEST:String = "DEFAULT_VIEW_REQUEST";

/** The event constant used to denote the click on the schedule id link*/
public static const SCHEDULE_DETAILS_CLICK:String = "SCHEDULE_DETAILS_CLICK";

/** The event constant used to denote the click on the schedule status link*/
public static const SCHEDULE_STATUS_REFRESH_REQUEST:String = "SCHEDULE_STATUS_REFRESH_REQUEST";

/** The event constant used for denoting the click of refresh button */
public static const REFRESH_CLICK:String = "REFRESH_CLICK";

/** The event constant used for denoting the click of Cancel button */
public static const CANCEL_CLICK:String = "CANCEL_CLICK";

/** The event constant used for denoting the click of End Reason image */
public static const END_REASON_CLICK:String = "END_REASON_CLICK";

[Bindable]
private	var activeSchedulesList:ArrayCollection;

[Bindable]
private	var cancelledSchedulesList:ArrayCollection;

[Bindable]
private var scheduleTypesList:ArrayCollection;

/** Hold the data related to this module */
private var _moduleInfo:Object;

public function get moduleInfo():Object
{
	return _moduleInfo;
}

public function set moduleInfo(value:Object):void
{
	_moduleInfo = value;
}

/**
 * Handle the startup completion of the Installtion list module.
 */
public function handleStartupComplete():void
{
	createScheduleTypesList();
	this.comboScheduleTypes.selectedIndex = 0;
	
	var reqProcessRequestSchedule:ReqProcessRequestSchedule = new ReqProcessRequestSchedule();
	reqProcessRequestSchedule.installationCode = getInstallationCode();
	sendDataEvent(GET_SCHEDULE_DATA_REQUEST, reqProcessRequestSchedule);		
}
/**
 * Handle the successful completion of the installation list retrieval
 * service by creating Lists, creating timers etc.
 */
public function handleGetScheduleDataServiceResult(data:Object):void
{
	scheduleServiceResultHandler(data);
}

public function handleRefreshScheduleDataServiceResult(data:Object):void
{
	scheduleServiceResultHandler(data);
}

public function handleCancelScheduleServiceResult(data:Object):void
{
	if(data == null) return;
	
	var resProcessReqSch:ResProcessRequestSchedule = data as ResProcessRequestSchedule;

	if(resProcessReqSch.installationCode == getInstallationCode())
	{
		AlertBuilder.getInstance().show
			("[Installation: "+ resProcessReqSch.installationCode
				+ "] \n================================\n" 
				+ resProcessReqSch.successFailureFlag);
		
	}
	var listOFSchedules:Object = resProcessReqSch.processRequestSchedulesList;
	scheduleServiceResultHandler(listOFSchedules);
}

//Loop over your data provider and either check or uncheck all boxes
//private function selectAllCheckboxes():void{
//	
//	var allRows:int = activeSchedulesList.length;
//	
//	for (var i:int = 0; i < allRows; i++){
//		if (selectAll.selected == true){
//			trace ("Checked is true");
//			activeSchedulesList[i].checked = true;
//		}else{
//			trace ("Checked is false");
//			activeSchedulesList[i].checked = false;
//		}
//	}
//	
//	//After the loop, reset the data provider to refresh the check boxes
//	dgActiveSchedules.dataProvider = activeSchedulesList;
//}

public function scheduleServiceResultHandler(data:Object):void
{
	if(data == null) return;
	
	var retrievedScheduleList:ArrayCollection = data as ArrayCollection;
	activeSchedulesList = new ArrayCollection();
	cancelledSchedulesList = new ArrayCollection();
	
	if(retrievedScheduleList == null)
	{
		retrievedScheduleList = new ArrayCollection();
		retrievedScheduleList.addItem(data);
	}
	else
	{		
		for(var i:uint = 0; i < retrievedScheduleList.length; ++i)
		{
			var processRequestScheduleData:ProcessRequestScheduleData = retrievedScheduleList[i];
			if(processRequestScheduleData.schStat == "A")
			{
				activeSchedulesList.addItem(retrievedScheduleList[i]);
			}
			else //if(processRequestScheduleData.schStat == "C" || processRequestScheduleData.schStat == "X")
			{
//				if(cancelledSchedulesList.length < 20)
				cancelledSchedulesList.addItem(processRequestScheduleData);
			}
		}
	}
	showSelectedSchedules();
}

private function checkActiveSchedules():void
{
	if(activeSchedulesList == null || 
		(activeSchedulesList != null && activeSchedulesList.length == 0))
	{
//		this.btnRefresh.enabled = false;
//		this.btnCancelSelected.enabled = false;
		this.btnRefresh.visible = false;
		this.btnCancelSelected.visible = false;
		this.comboScheduleTypes.selectedIndex = 1;
		this.dgActiveSchedules.dataProvider = cancelledSchedulesList;		
		AlertBuilder.getInstance().show("No active schedules available");
		return;
	}	
//	this.btnRefresh.enabled = true;
//	this.btnCancelSelected.enabled = true;
	this.btnRefresh.visible = true;
	this.btnCancelSelected.visible = true;
}
private function refreshProcessRequestSchedule(activeSchedulesList:ArrayCollection):void
{
	var reqProcessRequestSchedule:ReqProcessRequestSchedule = new ReqProcessRequestSchedule();
	reqProcessRequestSchedule.processRequestSchedulesList = activeSchedulesList;
	reqProcessRequestSchedule.installationCode = getInstallationCode();
	
	sendDataEvent(SCHEDULE_STATUS_REFRESH_REQUEST, reqProcessRequestSchedule);			
}

public function getInstallationCode():String
{
	var installationData:InstallationData = CommonConstants.INSTALLATION_DATA;
	if(installationData == null) return null;
		
	trace(installationData.installationCode);
	return installationData.installationCode;
}


/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:ViewScheduleModuleFacade =
		ViewScheduleModuleFacade.getInstance(ViewScheduleModuleFacade.NAME);
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	Facade.removeCore(ViewScheduleModuleFacade.NAME);
}

/**
 * The function invoked when the refresh button is clicked.
 */
private function refreshClick(event:Event):void
{
	checkActiveSchedules();
	refreshProcessRequestSchedule(activeSchedulesList);
}

/**
 * The function invoked when the value in view schedule combobox is changed
 */
private function showSchedules(event:Event):void
{
	showSelectedSchedules();	
}

private function showSelectedSchedules():void
{
	if(this.comboScheduleTypes.selectedIndex == 0)
	{
		this.dgActiveSchedules.dataProvider = activeSchedulesList;
		checkActiveSchedules();
	}
	else if(this.comboScheduleTypes.selectedIndex == 1)
	{
		this.dgActiveSchedules.dataProvider = cancelledSchedulesList;
//		this.btnRefresh.enabled = false;
//		this.btnCancelSelected.enabled = false;
		this.btnRefresh.visible = false;
		this.btnCancelSelected.visible = false;

	}
}

private function createScheduleTypesList():void
{
	scheduleTypesList = new ArrayCollection();
//	scheduleTypesList.addItem({name:'- Select -', value:''});
	scheduleTypesList.addItem({name:'Active Schedules', value:'A'});
	scheduleTypesList.addItem({name:'Non-Active Schedules (last 20 records)', value:'C'});
}


private var schedulesList:ArrayCollection;

private function cancelSelectedSchedules(event:Event):void
{
	schedulesList = new ArrayCollection();
	if(activeSchedulesList.length > 0)
	{
		for(var i:uint = 0; i < activeSchedulesList.length; ++i)
		{
			//		var processRequestScheduleData:ProcessRequestScheduleData = ;
			if(activeSchedulesList[i].checked)
			{
				schedulesList.addItem(activeSchedulesList[i]);
				trace(activeSchedulesList[i].schId + " is checked for cancellation");
			}
		}		
	}
	
	var scheduleIds:String = "[";
	if(schedulesList.length > 0)
	{
		for(var j:uint = 0; j < schedulesList.length; ++j)
		{
			scheduleIds = scheduleIds + schedulesList[j].schId + ", ";			
		}
		scheduleIds = scheduleIds.substring(0, scheduleIds.length - 2);
		scheduleIds = scheduleIds + "]";			
		Alert.show("Are you sure you want to cancel following schedule/s?\n" + scheduleIds,
					"Confirm cancellation",Alert.YES|Alert.NO, null, cancelHandler, null, Alert.NO);
		scheduleIds = null;	
	}
	else
	{
		AlertBuilder.getInstance().show("Please select schedule/s for cancellation.");
	}
}

private function cancelHandler(event:CloseEvent):void 
{
	if (event.detail==Alert.YES)
	{
		fireCancelCommand();
	}
	else if(event.detail==Alert.NO)
	{
//		AlertBuilder.getInstance().show("No cancelling of the schedule");
	}
} 

private function fireCancelCommand():void
{
	if(schedulesList.length > 0)
	{
		var reqProcessRequestSchedule:ReqProcessRequestSchedule = new ReqProcessRequestSchedule();
		reqProcessRequestSchedule.processRequestSchedulesList = schedulesList;
		reqProcessRequestSchedule.installationCode = getInstallationCode();
		sendDataEvent(ViewScheduleModule.CANCEL_CLICK, reqProcessRequestSchedule);		
	}
	
}
