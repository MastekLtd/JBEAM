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

import com.majescomastek.common.containers.dashboard.Pod;
import com.majescomastek.common.events.AddSelectedPodsEvent;
import com.majescomastek.common.events.LayoutChangeEvent;
import com.majescomastek.common.util.dashboard.PodLayoutManager;
import com.majescomastek.common.util.dashboard.StateManager;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.ProgramNameConstants;
import com.majescomastek.jbeam.facade.installationlist.InstallationListModuleFacade;
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.view.components.installationlist.ClosedPodListView;
import com.majescomastek.jbeam.view.components.installationlist.InstallationPod;

import flash.events.Event;
import flash.events.TimerEvent;
import flash.utils.Timer;

import mx.collections.ArrayCollection;
import mx.controls.CheckBox;
import mx.managers.PopUpManager;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request to fetch the list of all installations/environments */
public static const INSTALLATION_LIST_REQUEST:String = "INSTALLATION_LIST_REQUEST";

/** The event constant used to denote the id of the PodLayoutManager */
public static const INSTALLATION_POD_MANAGER:String = "INSTALLATION_POD_MANAGER";

/** The event constant used to denote the creation complete of all the pods to signal the pod creation */
public static const PODS_CREATION_COMPLETE:String = "PODS_CREATION_COMPLETE";

/** The event constant used to denote the request to refresh the pods in this pod container */
public static const REQUEST_POD_REFRESH:String = "REQUEST_POD_REFRESH";

/** The event constant used to denote the request to refresh the pods in this pod container */
public static const REQUEST_POD_REFRESH_FOR_INSTRUCTION:String = "REQUEST_POD_REFRESH_FOR_INSTRUCTION";

/** The id of the current pod manager used */
public static const POD_MANAGER_ID:String = "POD_MANAGER";

/** The event constant used to denote the request to navigate to default view */
public static const INSTALLATION_LIST_VIEW_REQUEST:String = "INSTALLATION_LIST_VIEW_REQUEST";

/** Hold the data related to this module */
private var _moduleInfo:Object;

// Array of PodLayoutManagers
private var podLayoutManagers:Object = new Object();

// Stores the xml data keyed off of a PodLayoutManager.
private var podDataDictionary:Dictionary = new Dictionary();

// Stores PodLayoutManagers keyed off of a Pod.
// Used for podLayoutManager calls after pods have been created for the first time.
// Also, used for look-ups when saving pod content ViewStack changes.
private var podHash:Object = new Object();

/** The array used to store the genrated pods */
private var podListArray:Array = new Array();

/** The timer used by this module to handle the periodic updation of its pods */
private var timer:Timer;

/** The timer used by this module to handle the periodic updation of its pods */
private var timerForInstruction:Timer;

/**
 * Handle the successful registration of the InstallationPod mediators.
 */
public function handlePodStartupCompletion():void
{
	registerInstallationPodTimer();
	registerRunBatchInstructionTimer();
}

public function registerInstallationPodTimer():void
{	// Create a timer which will request a refresh every 5 seconds
	// this time can be picked from properties file to make this 
	// time configurable
	timer = new Timer(CommonConstants.DEFAULT_POLL_TIME);
	timer.addEventListener(TimerEvent.TIMER, requestPodRefresh, false, 0, false);
	timer.start();
}


/**
 * Handle the successful registration of the InstallationPod mediators.
 */
public function registerRunBatchInstructionTimer():void
{
	// Create a timer which will request a refresh every 5 seconds
	// this time can be picked from properties file to make this 
	// time configurable
	timerForInstruction = new Timer(CommonConstants.DEFAULT_POLL_TIME);
	timerForInstruction.addEventListener(TimerEvent.TIMER, requestPodRefreshForInstruction, false, 0, false);
	timerForInstruction.start();
}

/**
 * Send event to refresh the respective pods with fresh data.
 */
private function requestPodRefresh(event:TimerEvent):void
{
	sendEvent(REQUEST_POD_REFRESH);	
}
 
/**
 * Send event to refresh the respective pods with fresh data.
 */
private function requestPodRefreshForInstruction(event:TimerEvent):void
{
	sendEvent(REQUEST_POD_REFRESH_FOR_INSTRUCTION);	
} 

/**
 * Handle the startup completion of the Installtion list module.
 */
public function handleStartupComplete():void
{
	sendDataEvent(INSTALLATION_LIST_REQUEST, CommonConstants.USER_PROFILE);		
}

/**
 * Handle the successful completion of the installation list retrieval
 * service by creating pods, creating timers etc.
 */
public function handleInstallationListRetrieval(list:ArrayCollection):void
{
	var manager:PodLayoutManager = new PodLayoutManager();
	manager.container = canvasPod;
	manager.id = INSTALLATION_POD_MANAGER;
	manager.addEventListener(LayoutChangeEvent.UPDATE, StateManager.setPodLayout);
	podDataDictionary[manager] = list;
	podLayoutManagers[manager.id] = manager;
	StateManager.setViewIndex(0); // Save the view index.
	viewStack.selectedIndex = 0;
	var podList:ArrayCollection = addPods(manager);
	sendDataEvent(PODS_CREATION_COMPLETE, podList);
}


/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:InstallationListModuleFacade =
		InstallationListModuleFacade.getInstance(InstallationListModuleFacade.NAME);
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	if(timer != null)
	{
		timer.stop();
	}
	if(timerForInstruction != null)
	{
		timerForInstruction.stop();
	}
	Facade.removeCore(InstallationListModuleFacade.NAME);
}

public function get moduleInfo():Object
{
	return _moduleInfo;
}

public function set moduleInfo(value:Object):void
{
	_moduleInfo = value;
}

// Adds the pods to a view.
private function addPods(manager:PodLayoutManager):ArrayCollection
{
	// Loop through the pod nodes for each view node.
	var list:ArrayCollection = ArrayCollection(podDataDictionary[manager]);
	var podList:ArrayCollection = new ArrayCollection();
	
	for(var i:uint = 0; i < list.length; ++i)
	{
		var viewId:String = manager.id;
		var installationData:InstallationData = InstallationData(list.getItemAt(i));
		var pod:InstallationPod = createPodForData(installationData);		
		podList.addItem(pod);
		podListArray.push(pod);
	}
	manager.showPods(podListArray);
	return podList;
}

/**
 * Create a new InstallationPod for each InstallationData object.
 */
private function createPodForData(installationData:InstallationData):InstallationPod
{
	var runBatchDetailsObj:BatchDetailsData  = moduleInfo.batchDetails;
	if(runBatchDetailsObj != null &&
			runBatchDetailsObj.installationCode == installationData.installationCode)
	{
		installationData.runBatchDetails = runBatchDetailsObj;
	}
	var pod:InstallationPod = new InstallationPod();	
	pod.installationData = installationData;
	return pod;
}

/**
 * The function invoked when the customize your settings link is clicked.
 */
private function onCustomizeSettingClick(event:Event):void
{
	var closedPodsWindow:ClosedPodListView = new ClosedPodListView();
	var popUp:ClosedPodListView = ClosedPodListView(PopUpManager.createPopUp(viewStack,ClosedPodListView,false));
	popUp.x = (this.width/2)-150;
	popUp.y = this.y+100;
	var closedPods:ArrayCollection = new ArrayCollection();
	for(var i:int=0; i < podListArray.length;i++)
	{
		//Alert.show(podsList[i]);
		var pod:Pod = Pod(podListArray[i]);
		if(Pod.WINDOW_STATE_CLOSED == pod.windowState)
		{
			closedPods.addItem(pod);
			popUp.addEventListener(AddSelectedPodsEvent.ADDPODS,addSelectedPods);
		}	
	}	
	popUp.closedPods.dataProvider = closedPods;
}

//Adds the selected pods.
private function addSelectedPods(event:AddSelectedPodsEvent):void
{
	var popUp:ClosedPodListView = ClosedPodListView(event.currentTarget);
	for(var i:int=0;i<podListArray.length;i++)
	{
		var pod:Pod = Pod(podListArray[i]);
		var hBox:HBox = HBox(popUp.getChildByName(pod.id));
		if(hBox != null)
		{
			var checkBox:CheckBox = CheckBox(hBox.getChildByName(pod.id)); 
			if(checkBox != null && checkBox.selected)
			{
				pod.restore();	
			}
		}
	}
}

/**
 * The function invoked when the Switch To Installations List View link is clicked.
 */
private function onSwitchToInstallationsListViewClick(event:Event):void
{	
	var data:Object = {
		'toggle': 'toggle',
		'programName': ProgramNameConstants.INSTALLATION_MODULE_PROGRAM_NAME		
			
	};
	sendDataEvent(INSTALLATION_LIST_VIEW_REQUEST, data);
}