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

import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.ProgramNameConstants;
import com.majescomastek.jbeam.facade.installationlist.InstallationModuleFacade;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.RequestListenerInfo;

import flash.events.Event;
import flash.events.TimerEvent;
import flash.utils.Timer;

import mx.collections.ArrayCollection;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request to fetch the list of all installations/environments */
public static const INSTALLATION_REQUEST:String = "INSTALLATION_REQUEST";

/** The event constant used to denote the request to refresh the installations list */
public static const REQUEST_LIST_REFRESH:String = "REQUEST_LIST_REFRESH";

/** The event constant used to denote the click on the Failed objects link */
public static const FAILED_OBJECT_CLICK:String = "FAILED_OBJECT_CLICK";

/** The event constant used to denote the request to navigate to default view */
public static const DEFAULT_VIEW_REQUEST:String = "DEFAULT_VIEW_REQUEST";

/** The event constant used to represent the request to refresh this pod */
public static const REFRESH_INSTALLATION:String = "REFRESH_INSTALLATION";

/** The event constant used to denote the click on the Details button*/
public static const SHOW_BATCH_DETAILS_CLICK:String = "SHOW_BATCH_DETAILS_CLICK";

/** The event constant used to denote the request to navigate to default view */
public static const INSTALLATION_PODS_VIEW_REQUEST:String = "INSTALLATION_PODS_VIEW_REQUEST";

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

private var shouldRefresh:Boolean

/** The timer used by this module to handle the periodic updation of its Lists */
private var timer:Timer;

public function registerInstallationListTimer():void
{	// Create a timer which will request a refresh every 5 seconds
	// this time can be picked from properties file to make this 
	// time configurable
	timer = new Timer(CommonConstants.DEFAULT_POLL_TIME);
	timer.addEventListener(TimerEvent.TIMER, requestListRefresh, false, 0, false);
	timer.start();
}

public function stopTimer():void
{
	if(timer != null)
	{
		timer.stop();
		trace("Timer stopped");
	}	
}


/**
 * Send event to refresh the respective Lists with fresh data.
 */
private function requestListRefresh(event:TimerEvent):void
{
	if(shouldRefresh)
	{
		sendEvent(REQUEST_LIST_REFRESH);		
	}
}
 
/**
 * Handle the startup completion of the Installtion list module.
 */
public function handleStartupComplete():void
{
	shouldRefresh = true;
	registerInstallationListTimer();
	sendDataEvent(INSTALLATION_REQUEST, CommonConstants.USER_PROFILE);		
}

/**
 * Handle the successful completion of the installation list retrieval
 * service by creating Lists, creating timers etc.
 */
public function handleInstallationRetrieval(installationsList:ArrayCollection):void
{
	if(installationsList == null) return;
	
	var retrievedInstallationsList:ArrayCollection = installationsList;
	
	if(retrievedInstallationsList == null)
	{
		retrievedInstallationsList = new ArrayCollection();
		retrievedInstallationsList.addItem(installationsList);
	}
	
	var refreshableInstallation:String = null;
	
	for(var i:int = 0 ; i < retrievedInstallationsList.length ; i++)
	{
		if(retrievedInstallationsList[i] != null)
		{
			var installationData:InstallationData = retrievedInstallationsList[i];
			if(!isBatchClosed(installationData) && !isFreshInstallation(installationData) && isBatchCompleted(installationData))
			{
				shouldRefresh = true;
				break;
			}
			else
			{
				shouldRefresh = false;
//				stopTimer();
			}
		}
	}
	this.dgInstallations.dataProvider = retrievedInstallationsList;	
}


/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:InstallationModuleFacade =
		InstallationModuleFacade.getInstance(InstallationModuleFacade.NAME);
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	stopTimer();
	Facade.removeCore(InstallationModuleFacade.NAME);
}



/**
 * The function invoked when the Switch To Portlet View link is clicked.
 */
private function onSwitchToPortletViewClick(event:Event):void
{	
	var data:Object = {
		'toggle': 'toggle',
		'programName': ProgramNameConstants.INSTALLATION_LIST_MODULE_PROGRAM_NAME		
			
	};
	sendDataEvent(INSTALLATION_PODS_VIEW_REQUEST, data);
//	sendEvent(DEFAULT_VIEW_REQUEST);
}

/**
 * Create and return a RequestListenerInfo object based on the
 * InstallationData object.
 */
public function getRequestListenerInfo(installationData:InstallationData):RequestListenerInfo
{
	var info:RequestListenerInfo = new RequestListenerInfo();
	info.batchNo = installationData.batchNo;
	info.batchRevNo = installationData.batchRevNo;
	info.installationCode = installationData.installationCode;
	info.listenerId = 0;
	return info;
}

/**
 * The function invoked by the mediator to request the pod to refresh
 * its contents.
 */
public function handleRefreshRequest():void
{
	sendDataEvent(INSTALLATION_REQUEST, CommonConstants.USER_PROFILE);		
}



/**
 * Determine whether this batch is closed or not based on the 
 * batch status and the batch end time.
 */ 
private function isBatchClosed(installationData:InstallationData):Boolean
{
	// If the batchStatus is CLOSURE & the batch end time
	// is not NULL, the batch has been closed.
	var closed:Boolean = installationData.batchEndTime != null &&
		installationData.batchStatus == CommonConstants.CLOSURE;
	return closed; 
}

/**
 * Determine whether this installation is fresh (newly created) or not based on the 
 * batch number and batch revision number.
 */
private function isFreshInstallation(installationData:InstallationData):Boolean
{
	// If the batchNo & batchRevNo are null, the batch is invalid.
	var invalid:Boolean = installationData.batchNo == 0 && installationData.batchRevNo == 0;
	return invalid;
}

/**
 * Determine whether this installation is fresh (newly created) or not based on the 
 * batch number and batch revision number.
 */
private function isRunBatchRequested(installationData:InstallationData):Boolean
{
	var invalid:Boolean = installationData.runBatchDetails != null && 
		installationData.runBatchDetails.installationCode != null ;
	return invalid;
}

/**
 * Determine whether the batch is completed or not based on the 
 * batch end reason.
 */
private function isBatchCompleted(installationData:InstallationData):Boolean
{
	var invalid:Boolean = installationData.batchEndReason == null ||
		(installationData.batchEndReason != null &&  
			(installationData.batchEndReason == CommonConstants.BATCH_COMPLETED
				|| installationData.batchEndReason == CommonConstants.PRE_ISSUED_STOP)
		);
	return invalid;
}
