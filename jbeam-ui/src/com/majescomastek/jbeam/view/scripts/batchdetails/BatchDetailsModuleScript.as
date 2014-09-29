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
// ActionScript file
include "../../../common/CommonScript.as"

import com.majescomastek.common.containers.dashboard.Pod;
import com.majescomastek.common.events.AddSelectedPodsEvent;
import com.majescomastek.common.events.LayoutChangeEvent;
import com.majescomastek.common.util.dashboard.PodLayoutManager;
import com.majescomastek.common.util.dashboard.StateManager;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.ProgramNameConstants;
import com.majescomastek.jbeam.facade.batchdetails.BatchDetailsModuleFacade;
import com.majescomastek.jbeam.facade.shell.ShellFacade;
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.ReqSearchBatch;
import com.majescomastek.jbeam.model.vo.SearchBatchData;
import com.majescomastek.jbeam.view.components.batchdetails.BatchRevisionPod;
import com.majescomastek.jbeam.view.components.batchdetails.BatchSummaryPod;
import com.majescomastek.jbeam.view.components.batchdetails.ObjectExecutionGraphPod;
import com.majescomastek.jbeam.view.components.batchdetails.PerScanExecutionCountGraphPod;
import com.majescomastek.jbeam.view.components.batchdetails.SystemInformationPod;
import com.majescomastek.jbeam.view.components.installationlist.ClosedPodListView;

import flash.events.TimerEvent;
import flash.utils.Timer;

import mx.collections.ArrayCollection;
import mx.controls.CheckBox;
import mx.managers.PopUpManager;

import org.osmf.events.TimeEvent;
import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request to fetch the installation details*/
public static const INSTALLATION_DATA_REQUEST:String = "INSTALLATION_DATA_REQUEST";

/** The event constant used to denote the request to fetch the batch details*/
public static const BATCH_DETAILS_REQUEST:String = "BATCH_DETAILS_REQUEST";

/** The event constant used to denote the id of the PodLayoutManager */
public static const BATCH_DETAILS_POD_MANAGER:String = "BATCH_DETAILS_POD_MANAGER";

/** The event constant used to denote the creation complete of all the pods to signal the pod creation */
public static const BATCH_PODS_CREATION_COMPLETE:String = "BATCH_PODS_CREATION_COMPLETE";

/** The event constant used to denote the creation complete of all the pods to signal the pod creation */
public static const BATCH_REVISION_PODS_CREATION_COMPLETE:String = "BATCH_REVISION_PODS_CREATION_COMPLETE";

/** The event constant used to denote the request to refresh the batch pods in this pod container */
public static const REQUEST_BATCH_POD_REFRESH:String = "REQUEST_BATCH_POD_REFRESH";

/** The event constant used to denote the request to refresh the object execution graph pods in this pod container */
public static const REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH:String = "REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH";

public static const SEARCH_BATCH_REQUEST:String = 'SEARCH_BATCH_REQUEST';

/** The id of the current pod manager used */
public static const POD_MANAGER_ID:String = "POD_MANAGER";

// Array of PodLayoutManagers
private var podLayoutManagers:Object = new Object();

// Stores the xml data keyed off of a PodLayoutManager.
private var podDataDictionary:Dictionary = new Dictionary();

//Array of all the pods
private var podsListArray:Array = new Array();

// Stores PodLayoutManagers keyed off of a Pod.
// Used for podLayoutManager calls after pods have been created for the first time.
// Also, used for look-ups when saving pod content ViewStack changes.
private var podHash:Object = new Object();

/** The timer used by this module to handle the periodic updation of its pods */
private var objectExecGraphTimer:Timer;

/** The timer used by this module to handle the periodic updation of its pods */
private var timer:Timer;

private var getBatchDetailTimer:Timer = new Timer(CommonConstants.DEFAULT_POLL_TIME,CommonConstants.DEFAULT_POLL_COUNT);

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

/** Hold the data related to this module */
private var _installationData:InstallationData;

public function get installationData():InstallationData
{
	return _installationData;
}

public function set installationData(value:InstallationData):void
{
	_installationData = value;
}

/**
 * Handle the successful registration of the mediator for BatchRevisionPod.
 */
public function handleBatchPodStartupCompletion():void
{
	// Create a timer which will request a refresh every 5 seconds
	// this time can be picked from properties file to make this 
	// time configurable
	timer = new Timer(CommonConstants.DEFAULT_POLL_TIME);
	timer.addEventListener(TimerEvent.TIMER, requestBatchRevisionPodRefresh, false, 0, false);
	timer.start();
}

/**
 * Send event to refresh the respective pods with fresh data.
 */
private function requestBatchRevisionPodRefresh(event:TimerEvent):void
{
	sendEvent(REQUEST_BATCH_POD_REFRESH);	
}

/**
 * Handle the successful registration of the mediator for ObjectExecutionGraphPod.
 */
public function handleObjectExecGraphPodStartupCompletion():void
{
	// Create a timer which will request a refresh every 5 seconds
	// this time can be picked from properties file to make this 
	// time configurable
	objectExecGraphTimer = new Timer(CommonConstants.DEFAULT_POLL_TIME);
	objectExecGraphTimer.addEventListener(TimerEvent.TIMER, requestObjectExecGraphPodRefresh, false, 0, false);
	objectExecGraphTimer.start();
}

/**
 * Send event to refresh the respective pods with fresh data.
 */
private function requestObjectExecGraphPodRefresh(event:TimerEvent):void
{
	sendEvent(REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH);	
}

/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:BatchDetailsModuleFacade =
		BatchDetailsModuleFacade.getInstance(BatchDetailsModuleFacade.NAME);
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
	if(objectExecGraphTimer != null)
	{
		objectExecGraphTimer.stop();
	}
	Facade.removeCore(BatchDetailsModuleFacade.NAME);
}


/**
 * Handle the startup completion of the BatchDetails module.
 */
public function handleBatchDetailsModuleStartupComplete():void
{	
	//This installationData will give information about the installation code only.	
	installationData = moduleInfo.installationData;
	if(installationData == null)
	{
		installationData = CommonConstants.INSTALLATION_DATA;
	}
	
	//This will load the current batch details
	var programName:String = moduleInfo.previousModuleData.programName;
	if(programName != null && programName != ProgramNameConstants.SEARCH_BATCH_MODULE_PROGRAM_NAME && programName != ProgramNameConstants.SCHEDULE_BATCH_MODULE_PROGRAM_NAME)
	{
		var data:Object = 
			{
				'installationData': installationData,
				'userProfile': CommonConstants.USER_PROFILE
			};
		sendDataEvent(INSTALLATION_DATA_REQUEST, data);
	}
	else if(programName != null && programName == ProgramNameConstants.SCHEDULE_BATCH_MODULE_PROGRAM_NAME)
	{
		getRunBatchDetails();
	}
	else
	{
		//This will load the search result batch details
		sendDataEvent(BATCH_DETAILS_REQUEST, installationData);
	}
	
}

private function getRunBatchDetails():void {
	var searchBatch:ReqSearchBatch = createSearchData();
	sendDataEvent(SEARCH_BATCH_REQUEST, searchBatch);
}

private function createSearchData():ReqSearchBatch
{		
	var searchBatch:ReqSearchBatch = new ReqSearchBatch(); 
	searchBatch.instructionSeqNo = moduleInfo.instructionSeqNo;
	searchBatch.batchDate = "";
	return searchBatch;
}

/**
 * Handle the successful completion of the Installation data retrieval service.
 */
public function handleGetInstallationSuccess(data:Object):void
{
	var list:ArrayCollection = data as ArrayCollection;
	if(list == null || list.length == 0)	return;
	
	var instData:InstallationData = list.getItemAt(0) as InstallationData;
	sendDataEvent(BATCH_DETAILS_REQUEST, instData);	
}
/**
 * Handle the startup completion of the BatchDetails module.
 */
public function handleBatchDetailsListRetrieval(list:ArrayCollection):void
{
	if((list == null || list.length == 0) && moduleInfo.previousModuleData.programName == ProgramNameConstants.SCHEDULE_BATCH_MODULE_PROGRAM_NAME)
	{
		//after running a special batch, if request to get details doesn't return any result
		//send the request again instead of showing error message
		sendDataEvent(BATCH_DETAILS_REQUEST, installationData);
		return;
	}
	var manager:PodLayoutManager = new PodLayoutManager();
	manager.container = canvasPod;
	manager.id = BATCH_DETAILS_POD_MANAGER;
	manager.addEventListener(LayoutChangeEvent.UPDATE, StateManager.setPodLayout);
	podDataDictionary[manager] = list;
	podLayoutManagers[manager.id] = manager;
	StateManager.setViewIndex(0); // Save the view index.
	viewStack.selectedIndex = 0;
	var podList:ArrayCollection = addPods(manager);
	sendDataEvent(BATCH_PODS_CREATION_COMPLETE, podList);
}



// Adds the pods to a view.
private function addPods(manager:PodLayoutManager):ArrayCollection
{
	var installationModuleInfo:Object = moduleInfo;
	var installationData:InstallationData = installationModuleInfo.installationData;
	var displayRevNo:Number = 1;
	if(installationData == null)
	{
		installationData = CommonConstants.INSTALLATION_DATA;
	}
	
	displayRevNo = installationData.batchRevNo;
	// Loop through the pod nodes for each view node.
	var list:ArrayCollection = ArrayCollection(podDataDictionary[manager]);
	var podList:ArrayCollection = new ArrayCollection();	
	var batchDetailsData:BatchDetailsData =  null;
	for(var i:uint = 0; i < list.length; ++i)
	{
		batchDetailsData = BatchDetailsData(list.getItemAt(i));		
		batchDetailsData.timezoneId = installationData.timezoneId;
		batchDetailsData.timezoneShortName = installationData.timezoneShortName;
		batchDetailsData.timezoneOffset = installationData.timezoneOffset;
		
		if(batchDetailsData != null)
		{
			
			var batchRevisionPod:BatchRevisionPod = createBatchRevisionPodForData(batchDetailsData);
			batchRevisionPod.id = "BatchRevisionPod" + "-" + batchDetailsData.batchNo + "-" + batchDetailsData.batchRevNo + "-" + Math.random();
			if(isBatchClosed(batchDetailsData) && displayRevNo != batchDetailsData.batchRevNo && list.length > 1)
			{
				batchRevisionPod.windowState = 2;				
			}
			podsListArray.push(batchRevisionPod);
			podList.addItem(batchRevisionPod);
			
			var objectExecutionGraphPod:ObjectExecutionGraphPod = createObjectExecutionGraphPodForData(batchDetailsData);
			objectExecutionGraphPod.id = "ObjectExecutionGraphPod" + "-" + batchDetailsData.batchNo + "-" + batchDetailsData.batchRevNo + "-" + Math.random();
			if(isBatchClosed(batchDetailsData) && displayRevNo != batchDetailsData.batchRevNo && list.length > 1)
			{
				objectExecutionGraphPod.windowState = 2;				
			}
			podsListArray.push(objectExecutionGraphPod);
			podList.addItem(objectExecutionGraphPod);
			
			var perScanExecutionCountGraphPod:PerScanExecutionCountGraphPod = createPerScanExecutionCountGraphPodForData(batchDetailsData);
			perScanExecutionCountGraphPod.id = "PerScanExecutionCountGraphPod" + "-" + batchDetailsData.batchNo + "-" + batchDetailsData.batchRevNo + "-" + Math.random();
			if(isBatchClosed(batchDetailsData) && displayRevNo != batchDetailsData.batchRevNo && list.length > 1)
			{
				perScanExecutionCountGraphPod.windowState = 2;				
			}
			podsListArray.push(perScanExecutionCountGraphPod);
			podList.addItem(perScanExecutionCountGraphPod);
		}
	}
	if(batchDetailsData != null)
	{	
		var batchSummaryPod:BatchSummaryPod = createBatchSummaryPodForData(batchDetailsData);
		podList.addItem(batchSummaryPod);
		podsListArray.push(batchSummaryPod);
		
		var systemInformationPod:SystemInformationPod = createSystemInformationPodForData(batchDetailsData);
		podList.addItem(systemInformationPod);
		podsListArray.push(systemInformationPod);
		batchDetailsData = null;
	}
	if(podsListArray != null || podsListArray.length > 0)
	{
		manager.showPods(podsListArray);
	}	
	return podList;
}

/**
 * Create a new BatchRevisionPod for each Batch Revison.
 */
private function createBatchRevisionPodForData(batchDetails:BatchDetailsData):BatchRevisionPod
{
	var pod:BatchRevisionPod = new BatchRevisionPod();
	pod.batchDetails = batchDetails;
	return pod;
}

/**
 * Create a new BatchSummaryPod for a batch
 */
private function createBatchSummaryPodForData(batchDetails:BatchDetailsData):BatchSummaryPod
{
	var pod:BatchSummaryPod = new BatchSummaryPod();
	pod.batchDetails = batchDetails;
	pod.id = this.toString() + "-" + batchDetails.batchNo;
	return pod;
}

/**
 * Create a new ObjectExecutionGraphPod for each Batch Revison.
 */
private function createObjectExecutionGraphPodForData(batchDetails:BatchDetailsData):ObjectExecutionGraphPod
{
	var pod:ObjectExecutionGraphPod = new ObjectExecutionGraphPod();
	pod.batchDetails = batchDetails;
	return pod;
}

/**
 * Create a new PerScanExecutionCountGraphPod for each Batch Revison.
 */
private function createPerScanExecutionCountGraphPodForData(batchDetails:BatchDetailsData):PerScanExecutionCountGraphPod
{
	var pod:PerScanExecutionCountGraphPod = new PerScanExecutionCountGraphPod();
	pod.batchDetails = batchDetails;
	return pod;
}

/**
 * Create a new SystemInformationPod for a batch
 */
private function createSystemInformationPodForData(batchDetails:BatchDetailsData):SystemInformationPod
{
	var pod:SystemInformationPod = new SystemInformationPod();
	pod.batchDetails = batchDetails;
	pod.id = this.toString() + "-" + batchDetails.batchNo;
	return pod;
}

/**
 * The function invoked when the customize your settings link is clicked.
 */
private function onCustomizeSettingClick(event:Event):void
{
	var closedPodsWindow:ClosedPodListView = new ClosedPodListView();
	var popUp:ClosedPodListView = 
		ClosedPodListView(PopUpManager.createPopUp(viewStack,ClosedPodListView,false));
	popUp.x = (this.width/2)-150;
	popUp.y = this.y+100;
	var closedPods:ArrayCollection = new ArrayCollection();
	for(var i:int=0; i < podsListArray.length;i++)
	{
		//Alert.show(podsList[i]);
		var pod:Pod = Pod(podsListArray[i]);
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
	for(var i:int=0;i<podsListArray.length;i++)
	{
		var pod:Pod = Pod(podsListArray[i]);
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
 * Determine whether this batch is closed or not based on the 
 * batch status and the batch end time.
 */ 
private function isBatchClosed(batchDetails:BatchDetailsData):Boolean
{
	// If the batchStatus is CLOSURE & the batch end time
	// is not NULL, the batch has been closed.
	var closed:Boolean = batchDetails.execEndTime != null &&
		(batchDetails.batchStatus == CommonConstants.CLOSURE ||
			batchDetails.batchStatus == CommonConstants.STOPPED);
	return closed; 
}

public function handleSearchBatchDetailsListRetrieval(list:ArrayCollection):void {
	handleBatchDetailsTimer(list.length != 0);
	if(list.length == 0) {
		return;
	}
	var searchBatchData:SearchBatchData = list[0];
	installationData = new InstallationData();
	var moduleInstallationData:InstallationData = moduleInfo.previousModuleData.installationData;
	
	if(moduleInstallationData == null)
	{
		moduleInstallationData = CommonConstants.INSTALLATION_DATA;
	}
	
	installationData.installationCode = moduleInstallationData.installationCode;   	
	installationData.timezoneId = moduleInstallationData.timezoneId;   	
	installationData.timezoneOffset = moduleInstallationData.timezoneOffset;   	
	installationData.timezoneShortName = moduleInstallationData.timezoneShortName;   	
	installationData.batchNo = searchBatchData.batchNo;
	installationData.batchRevNo = searchBatchData.batchRevNo;
	sendDataEvent(BATCH_DETAILS_REQUEST, installationData);
}

private function handleBatchDetailsTimer(isBatchDetailsFound:Boolean):void {
	if(!isBatchDetailsFound) {
		if(!getBatchDetailTimer.hasEventListener(TimerEvent.TIMER)) {
			getBatchDetailTimer.addEventListener(TimerEvent.TIMER, sendGetDataDetailsRequest, false, 0, false);
			getBatchDetailTimer.start();
		}
	} else {
		getBatchDetailTimer.removeEventListener(TimerEvent.TIMER, sendGetDataDetailsRequest, false);
		getBatchDetailTimer.stop();
	}
}

private function sendGetDataDetailsRequest(event:TimerEvent):void
{
	if(getBatchDetailTimer.currentCount < getBatchDetailTimer.repeatCount)
		getRunBatchDetails();
	else {
		handleBatchDetailsListRetrieval(new ArrayCollection());
	}
}

/**
 * 
 * If fault occures while getting batch details of recently run batch,
 * stop getBatchDetailTimer and naivgate to default page.
 */
public function handleSearchBatchDetailsListRetrievalFailure():void
{
	getBatchDetailTimer.removeEventListener(TimerEvent.TIMER, sendGetDataDetailsRequest, false);
	getBatchDetailTimer.stop();
}
