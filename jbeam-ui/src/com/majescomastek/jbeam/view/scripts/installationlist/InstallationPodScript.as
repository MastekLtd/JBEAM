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
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.common.ProgramNameConstants;
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.BatchSummaryData;
import com.majescomastek.jbeam.model.vo.Entity;
import com.majescomastek.jbeam.model.vo.GraphData;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.InstructionLog;
import com.majescomastek.jbeam.model.vo.InstructionParameter;
import com.majescomastek.jbeam.model.vo.ProgressLevelData;
import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
import com.majescomastek.jbeam.model.vo.RequestListenerInfo;
import com.majescomastek.jbeam.model.vo.UserInstallationRole;
import com.majescomastek.jbeam.view.components.installationlist.FailedObjectsWindow;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.core.Window;

/** The installation data used by this pod to show the installation details */
private var _installationData:InstallationData;

/** The batch details used by this pod */
private var _batchDetails:BatchDetailsData;

[Bindable]
/** The variable holding the visible state of the failed object link */
private var failedLinkVisible:Boolean;

[Bindable]
/** The variable holding the visible state of the failed object graph */
private var failedGraphVisible:Boolean;

[Bindable]
/** The variable holding the visible state of the batchProgressBar */
private var batchProgressBarVisible:Boolean;

[Bindable]
/** The variable holding the visible state of the Panel which contains the Batch Status data */
private var batchStatusPanelVisible:Boolean;

private var progressBarLength:uint= 10;

[Bindable]
/** The dataprovider for the entity list */
private var entityDataProvider:ArrayCollection;

[Bindable]
/** The dataprovider for the failed object graph data */
private var pieDataProvider:ArrayCollection;

[Bindable]
/** The enabled status of the Details button */ 
private var detailsButtonEnabled:Boolean;

[Bindable]
/** The enabled status of the Start button */
private var startButtonEnabled:Boolean;

[Bindable]
/** The visible status of the Start button */
private var startButtonVisible:Boolean;

[Bindable]
/** The label of the Start button */
private var startButtonLabel:String;

[Bindable]
/** The enabled status of the Stop button */
private var stopButtonEnabled:Boolean;

[Bindable]
/** The status of the Stop Request */
private var stopRequestSubmitted:Boolean;


[Bindable]
/** The visible status of the Stop button */
private var stopButtonVisible:Boolean;

/** The event constant used to denote the request to show default view */
public static const DEFAULT_VIEW_REQUEST:String = "DEFAULT_VIEW_REQUEST";

/** The event constant used to denote the click on the Details button*/
public static const SHOW_BATCH_DETAILS_CLICK:String = "SHOW_BATCH_DETAILS_CLICK";

/** The event constant used to denote the click on the Start button*/
public static const START_CLICK:String = "START_CLICK";

/** The event constant used to denote the click on the Stop button*/
public static const STOP_CLICK:String = "STOP_CLICK";

/** The event constant used to denote the click on the Failed objects link */
public static const FAILED_OBJECT_CLICK:String = "FAILED_OBJECT_CLICK";

/** The event constant used to denote the click on the Batch object link */
public static const BATCH_OBJECT_CLICK:String = "BATCH_OBJECT_CLICK";

/** The event constant used to represent the request to refresh this pod */
public static const REFRESH_POD:String = "REFRESH_POD";

/** The event constant used to represent the request to refresh this pod */
public static const REFRESH_POD_FOR_INSTRUCTION:String = "REFRESH_POD_FOR_INSTRUCTION";

/** The event constant used to denote the request to fetch the pie chart data */
public static const FETCH_FAILED_OBJECT_GRAPH_DATA:String = "FETCH_FAILED_OBJECT_GRAPH_DATA";

/** The event constant used to denote the request to the batch */
public static const RUN_BATCH_REQUEST:String = 'RUN_BATCH_REQUEST';

/** The event constant used to denote the request to the batch */
public static const GET_BATCH_DATA_REQUEST:String = 'GET_BATCH_DATA_REQUEST';

private var refreshPodCount:Number = 0;

[Bindable]
public function get installationData():InstallationData
{
	return _installationData;
}

public function set installationData(value:InstallationData):void
{
	_installationData = value;
	
	derivePageState();
}

[Bindable]
public function get batchDetails():BatchDetailsData
{
	return _batchDetails;
}

public function set batchDetails(value:BatchDetailsData):void
{
	_batchDetails = value;
}

/**
 * Handle the startup completion of this view.
 */
public function handleStartupComplete():void
{
	if(!isFreshInstallation())
	{
		sendDataEvent(FETCH_FAILED_OBJECT_GRAPH_DATA, getGraphRequestData());
	}
}

/**
 * Derive the state of the view variables based on the value of the
 * InstallationData attributes.
 */
private function derivePageState():void
{
	this.id = _installationData.installationCode;
	setInstallationBasedRoles(
		CommonConstants.USER_INSTALLATION_ROLES, 
		installationData.installationCode);
	
	if(installationData.runBatchDetails == null)
	{
		createBatchDetails();
	}
	else
	{
		batchDetails = installationData.runBatchDetails;
	}
	failedLinkVisible = _installationData.totalFailedObjects > 0 ? true : false;
	detailsButtonEnabled = !isFreshInstallation();
	startButtonVisible = CommonConstants.HAVE_OPERATOR_ROLE;
	if(startButtonVisible)
	{
		if(isFreshInstallation())
		{
			startButtonEnabled = !isFreshInstallation();
			
		}
		else
		{
			startButtonEnabled = isRunBatchRequested() || 
								 isBatchCompleted();			
		}
	}
	if(isFreshInstallation() || isBatchCompleted())
	{
		startButtonLabel = CommonConstants.START;
	}
	else
	{
		startButtonLabel = CommonConstants.RESUME;		
	}
	stopButtonVisible = CommonConstants.HAVE_OPERATOR_ROLE;
	if(stopButtonVisible)
	{
		stopButtonEnabled = !stopRequestSubmitted && 
			(installationData.batchStatus != null && 
			installationData.batchStatus != CommonConstants.CLOSURE) &&
			installationData.batchEndTime == null;
	}
	
	if(installationData.progressLevelDataList.length > 0)
	{
		batchProgressBarVisible = true;
		batchStatusPanelVisible = true;
	}
	else
	{
		batchProgressBarVisible = false;
		batchStatusPanelVisible = false;
	}	
}

/**
 * Function to set the user roles for the session
 */    
private function setInstallationBasedRoles(
			userInstallationRoleDetailsList:ArrayCollection, installationCode:String):void
{
	if(userInstallationRoleDetailsList != null &&
		userInstallationRoleDetailsList.length > 0)
	{
		CommonUtils.resetRoles();
		
		for each(var userInstallationRole:UserInstallationRole 
			in userInstallationRoleDetailsList)
		{
			if(installationCode == userInstallationRole.installationCode)
			{
				switch(userInstallationRole.roleId)
				{				
					case CommonConstants.USER:				
						CommonUtils.setHaveUserRole(true);
						break;
					case CommonConstants.OPERATOR:
						CommonUtils.setHaveOperatorRole(true);
						break;
				}
			}			
		}
	}	
}  

/**
 * Handle the successful completion of the Installation data retrieval service.
 */
public function handleGetInstallationSuccess(data:Object):void
{
	var list:ArrayCollection = data as ArrayCollection;
	if(list == null || list.length == 0)	return;
	 
	var retrievedInstallationData:InstallationData = list.getItemAt(0) as InstallationData;
	if(installationData.installationCode == retrievedInstallationData.installationCode)
	{
		installationData = retrievedInstallationData;	
		populateEntityDataProvider();
		populateBatchProgressBar();
		derivePageState();
	}
}

/**
 * Create the request data required for refreshing this pod along with the
 * mediator name for which the refresh should happen.
 */
public function getRequestDataForMediator(mediatorName:String):Object
{
	return {
		'installationData': installationData,
		'batchDetails': batchDetails,
		'mediatorName': mediatorName,
		'graphData': getGraphRequestData(),
		'userProfile': CommonConstants.USER_PROFILE
	};
}

/**
 * Prepare the graph data object based on the installation details
 * of this pod.
 */
private function getGraphRequestData():GraphData
{
	var graphData:GraphData = new GraphData();
	graphData.installationCode = installationData.installationCode;
	graphData.batchNo = installationData.batchNo;
	graphData.batchRevNo = installationData.batchRevNo;
	graphData.graphId = CommonConstants.FAILED_OBJECTS_GRAPH_IDENTIFIER;
	return graphData;
}

/**
 * The function invoked when the failed objects link is clicked.
 */
private function onFailedObjectClick(event:Event):void
{
	var window:FailedObjectsWindow = new FailedObjectsWindow();
	(window as Window).open();
	var data:Object = {
		'view': window,
		'requestListenerInfo': getRequestListenerInfo(),
		'batchDetails': batchDetails
	};
	sendDataEvent(FAILED_OBJECT_CLICK, data);
}

/**
 * Create and return a RequestListenerInfo object based on the
 * InstallationData object.
 */
public function getRequestListenerInfo():RequestListenerInfo
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
	if(!isBatchClosed() && !isFreshInstallation() && isBatchCompleted())
	{
		sendEvent(REFRESH_POD);
	}
}



/**
 * Determine whether this batch is closed or not based on the 
 * batch status and the batch end time.
 */ 
private function isBatchClosed():Boolean
{
	// If the batchStatus is CLOSURE & the batch end time
	// is not NULL, the batch has been closed.
	var closed:Boolean = this.installationData.batchEndTime != null &&
							this.installationData.batchStatus == CommonConstants.CLOSURE;
	return closed; 
}

/**
 * Determine whether this installation is fresh (newly created) or not based on the 
 * batch number and batch revision number.
 */
private function isFreshInstallation():Boolean
{
	// If the batchNo & batchRevNo are null, the batch is invalid.
	var invalid:Boolean = this.installationData.batchNo == 0 && this.installationData.batchRevNo == 0;
	return invalid;
}

/**
 * Determine whether this installation is fresh (newly created) or not based on the 
 * batch number and batch revision number.
 */
private function isRunBatchRequested():Boolean
{
	var invalid:Boolean = this.installationData.runBatchDetails != null && 
					this.installationData.runBatchDetails.installationCode != null ;
	return invalid;
}

/**
 * Determine whether the batch is completed or not based on the 
 * batch end reason.
 */
private function isBatchCompleted():Boolean
{
//	var invalid:Boolean = this.installationData.batchEndReason != null &&  
//				(this.installationData.batchEndReason != CommonConstants.BATCH_COMPLETED
//					|| this.installationData.batchEndReason != CommonConstants.PRE_ISSUED_STOP);
	var invalid:Boolean = this.installationData.batchEndReason == null ||
			(this.installationData.batchEndReason != null &&  
				(this.installationData.batchEndReason == CommonConstants.BATCH_COMPLETED
					|| this.installationData.batchEndReason == CommonConstants.PRE_ISSUED_STOP)
			 );
	return invalid;
}

/**
 * Handle the successful retrieval of the graph data for the failed
 * object graph.
 */
public function handleGraphDataRetrieval(data:Object):void
{
	if(!data)	return;
	pieDataProvider = ArrayCollection(data);
	
	// make the graph visible only if the data is present
	failedGraphVisible = pieDataProvider.length > 0 ? true : false; 	
}



/** 
 * Display batch details  
 */
private function showBatchDetails(evt:Event):void
{
	if(installationData != null)
	{		
		CommonUtils.setInstallationData(installationData);		
	}
	var data:Object = {
		'installationData': installationData,
		'programName': ProgramNameConstants.BATCH_DETAILS_MODULE_PROGRAM_NAME
	};
	sendDataEvent(SHOW_BATCH_DETAILS_CLICK, data);
}

/** 
 * The function invoked when the Start button is clicked. 
 * It will shift control to Schedule Batch Module   
 */
private function onStartButtonClick(evt:Event):void
{
	var data:Object = {
		'installationData': installationData,
		'programName': ProgramNameConstants.SCHEDULE_BATCH_MODULE_PROGRAM_NAME
	};
	if(isFreshInstallation() || isBatchCompleted())
	{
		if(installationData != null)
		{		
			CommonUtils.setInstallationData(installationData);		
		}
		sendDataEvent(START_CLICK, data);
	}
	else
	{
		var reqInstructionLog:ReqInstructionLog = 
			createReqInstructionLog(CommonConstants.BSRUNBATCH);
		if(reqInstructionLog != null)
		{
			reqInstructionLog.instructionParameters = 
				createInstructionParameters();
			sendDataEvent(RUN_BATCH_REQUEST, reqInstructionLog);		
		}
		
	}
}

/**
 * Handle the run batch service result
 */
public function handleRunBatchServiceResult(data:Object):void
{		
	var batchDetails:BatchDetailsData = data as BatchDetailsData;
	
	if(batchDetails.installationCode == installationData.installationCode)
	{
		this.installationData.runBatchDetails = batchDetails;
		AlertBuilder.getInstance().show
			("[Installation: "+ batchDetails.installationCode
			 + "] \n" + CommonConstants.RESUME_BATCH_REQUEST_SUBMITTED
			 + new Date(Number(batchDetails.responseTime)));
		
	}
}

/** 
 * The function invoked when the Stop button is clicked.    
 */
private function onStopButtonClick(evt:Event):void
{
	var reqInstructionLog:ReqInstructionLog = 
		createReqInstructionLog(CommonConstants.BSSTOBATCH);
	if(!stopRequestSubmitted)
	{
		sendDataEvent(STOP_CLICK, reqInstructionLog);		
	}
}

private function createInstructionParameters():ArrayCollection
{
	var instructionParametersList:ArrayCollection = new ArrayCollection();
	var instParamBatchNo:InstructionParameter = new InstructionParameter();
	instParamBatchNo.name = "BATCH_NO";
	instParamBatchNo.slNo = 1;
	instParamBatchNo.type = "I";
	instParamBatchNo.value = installationData.batchNo.toString();
	instructionParametersList.addItem(instParamBatchNo);
	var instParamBatchRevNo:InstructionParameter = new InstructionParameter();
	instParamBatchRevNo.name = "BATCH_REV_NO";
	instParamBatchRevNo.slNo = 2;
	instParamBatchRevNo.type = "I";
	instParamBatchRevNo.value = installationData.batchRevNo.toString();
	instructionParametersList.addItem(instParamBatchRevNo);
	return instructionParametersList;
	
}
private function createReqInstructionLog(reqMessage:String):ReqInstructionLog
{
	var reqInstructionLog:ReqInstructionLog = new ReqInstructionLog();
 	reqInstructionLog.installationCode = installationData.installationCode;
 	reqInstructionLog.instructingUser = CommonConstants.USER_PROFILE.userId;
	reqInstructionLog.instructionTime = new Date().getTime();
	reqInstructionLog.message = reqMessage;
	reqInstructionLog.batchNo = installationData.batchNo;
	reqInstructionLog.batchRevNo = installationData.batchRevNo;
	return reqInstructionLog;
}

/**
 * The function invoked by the mediator to request the pod to stop 
 * the current batch.
 */
public function handleStopBatchSuccess(data:Object):void
{
	if(data == null) return;
	
	var instData:InstallationData = data as InstallationData;
	if(instData.installationCode == installationData.installationCode)
	{
		stopRequestSubmitted = true;
		stopButtonEnabled = false;
		AlertBuilder.getInstance().show
			("[Installation: "+ instData.installationCode
		 	+ "] \n" + CommonConstants.STOP_BATCH_REQUEST_SUBMITTED
		 	+ new Date(Number(instData.responseTime)));
	}
}

/**
 * To display the progress bar. 
 */
public function populateBatchProgressBar():void
{
	//Entity list lenght + 2 for Init and closure
	if(entityDataProvider == null) return;
	
	progressBarLength = entityDataProvider.length + 2;
		
	var progressLevelDataList:ArrayCollection = installationData.progressLevelDataList;
	if(progressLevelDataList != null && progressLevelDataList.length > 0)
	{
		for(var i:int = 0; i < progressLevelDataList.length; i++)
		{	
			var progressLevelData:ProgressLevelData = progressLevelDataList[i];
			var nextProgressLevelDataValue:ProgressLevelData = new ProgressLevelData();
			
			//Condition to display the batch current status.	
			if(i == 0 && progressLevelData != null) 
			{	
				for(var ent:int = 0; ent < entityDataProvider.length; ent++)
				{
					if(progressLevelData.prgLevelType == null)
					{
						if(progressLevelData.prgActivityType == CommonConstants.INITIALIZATON)
						{
							this.instProgressBar.label = CommonConstants.INITIALIZATON;
							this.instProgressBar.setProgress(1, entityDataProvider.length+2);
						}
						else if(progressLevelData.prgActivityType == CommonConstants.STOPPED)
						{
							for(var k:int = 0; k < entityDataProvider.length; k++)
							{
								if(progressLevelDataList[i + 1] != null)
								{
									nextProgressLevelDataValue = progressLevelDataList[i + 1];
								
									if(entityDataProvider[k].label == nextProgressLevelDataValue.prgLevelType)
									{
										this.instProgressBar.label = entityDataProvider[k].label + " - " + CommonConstants.STOPPED;
										this.instProgressBar.setProgress(k + 1 , entityDataProvider.length+2);
										break;
									}
								}									
							}								
						}
						else if(progressLevelData.prgActivityType == CommonConstants.CLOSURE)
						{
							this.instProgressBar.label = CommonConstants.CLOSURE;
							this.instProgressBar.setProgress(entityDataProvider.length+2, entityDataProvider.length+2);
						}
						break;
					}
					else 
					{
						if(entityDataProvider[ent].label == progressLevelData.prgLevelType)
						{
							this.instProgressBar.label = entityDataProvider[ent].label;
							this.instProgressBar.setProgress((ent+1), entityDataProvider.length+2);
							break;
						}
					}
				}
			}
		}
		batchProgressBarVisible = true;
		batchStatusPanelVisible = true;
	} 
	else
	{
		batchProgressBarVisible = false;
		batchStatusPanelVisible = false;
	}
}

/**
 * To populate the entity combo box 
 */
public function populateEntityDataProvider():void
{	
	var batchEntityList:ArrayCollection = installationData.entityList;
	entityDataProvider = new ArrayCollection();
	entityDataProvider.addItem({label:"-SELECT-", data:0});
	if(batchEntityList != null)
	{
		for(var i:int =0 ; i < batchEntityList.length; i++)
		{
			var entityData:Entity = batchEntityList[i];
			entityDataProvider.addItem({label:entityData.entityName, data:(i+1)});				
		}			
	}
}

/**
 * The function invoked by the mediator to request the pod to refresh
 * itself for loading new batch.
 */
public function handleRefreshRequestForInstruction():void
{
	if(isRunBatchRequested())
	{
		batchDetails = installationData.runBatchDetails;
		if(!batchDetails.isScheduledBatch)	
		{
			sendEvent(REFRESH_POD_FOR_INSTRUCTION);
		}
	}
	else
	{
		this.installationData.runBatchDetails = null;
		handleRefreshRequest();
	}
}

/**
 * Determine whether this installation is fresh (newly created) or not based on the 
 * batch number and batch revision number.
 */
private function isClearInstallationPodRequested(runBatchData:BatchDetailsData):Boolean
{
	var invalid:Boolean = runBatchData != null && 
			(runBatchData.installationCode	== this.installationData.installationCode &&
				runBatchData.instructionSeqNo != null) && 
		!runBatchData.isScheduledBatch;
	return invalid;
}

/**
 * Handle the successful retrieval of the instruction log for the supplied 
 * instruction sequence number.
 */
public function handleInstrictionLogRetrieval(data:Object):void
{
	if(!data) return;
	
	var instructionLog:InstructionLog = data as InstructionLog;
	if(batchDetails != null && batchDetails.installationCode == instructionLog.installationCode)
	{
		if(instructionLog.batchAction == null)
		{
			refreshPodCount++;	
			if(this.startButtonLabel == CommonConstants.RESUME)
			{
				clearInstallationPod(true);
			}
			else
			{
				clearInstallationPod(false);				
			}
		}
		else
		{
			refreshPodCount = 0;
			if("BATCH STARTED" == instructionLog.batchAction || "BATCH RESUMED" == instructionLog.batchAction)
			{
				stopRequestSubmitted = false;
				batchDetails.instructionSeqNo = String(instructionLog.seqNo);
				sendDataEvent(GET_BATCH_DATA_REQUEST, batchDetails);		
			}
			else if("BATCH LOCKED" == instructionLog.batchAction)
			{
				AlertBuilder.getInstance().show("A batch is currently being executed " +
					"in the same environment. \nPlease contact your system administrator.");
//				this.installationData.runBatchDetails = null;
//				this.instProgressBar.indeterminate = false;
//				handleRefreshRequest();
				sendEvent(DEFAULT_VIEW_REQUEST);
			}
		}
		
		if(refreshPodCount == 20)
		{
			AlertBuilder.getInstance().show(
				"It seems that one of the following system is down for this environment." +
				" \n\t1) monitor-comm " +
				" \n\t2) core-comm " +
				" \n\t3) PRE" +
				"\nPlease contact your system administrator. " +
				"\n Once confirmed from the team please run a fresh batch");
			instructionLog = null;
			refreshPodCount = 0;
		}
		
	}
}
/**
 * Handle the successful retrieval of the batch data for the supplied 
 * instruction sequence number.
 */
public function handleBatchDataRetrieval(data:Object):void
{
	var runBatchData:BatchDetailsData = null;
	if(this.installationData.runBatchDetails != null)
	{
		runBatchData = this.installationData.runBatchDetails; 
	}
	if(!data )
	{
		if(isClearInstallationPodRequested(runBatchData))
		{
			var revisionBatch:Boolean = runBatchData.isRevisionBatch;
			clearInstallationPod(revisionBatch);
		}
	}
	else
	{
		this.installationData.runBatchDetails = null;
		assignNewBatchData(BatchSummaryData(data));
		this.instProgressBar.indeterminate = false;
		handleRefreshRequest();
	}
}

/**
 * Assign the new batch details to installation data.
 */
private function assignNewBatchData(batchData:BatchSummaryData):void
{
	if(batchData != null)
	{
		installationData.batchNo = batchData.batchNo;
		installationData.batchRevNo = batchData.batchRevNo;
		installationData.batchStatus = batchData.batchStatus;
		installationData.batchStartTime = batchData.execStartTime;
		installationData.batchEndTime = batchData.execEndTime;		
		installationData.batchEndReason = batchData.batchEndReason;
	}
}
/**
 * Create a batch details object using the installation data
 */
private function createBatchDetails():void
{
	if(installationData != null)
	{
		batchDetails = new BatchDetailsData();
		batchDetails.batchNo = installationData.batchNo;
		batchDetails.batchRevNo = installationData.batchRevNo;
		batchDetails.batchStatus = installationData.batchStatus;
		batchDetails.execStartTime = installationData.batchStartTime;
		batchDetails.execEndTime = installationData.batchEndTime;		
		batchDetails.batchEndReason = installationData.batchEndReason;
		batchDetails.timezoneId = installationData.timezoneId;		
		batchDetails.timezoneOffset = installationData.timezoneOffset;		
		batchDetails.timezoneShortName = installationData.timezoneShortName;	
	}
}
private function clearInstallationPod(revisionBatch:Boolean):void
{
	if(revisionBatch)
	{
		this.fsInstallationData.label = CommonConstants.RESUMING_BATCH;					
	}
	else
	{
		this.fsInstallationData.label = CommonConstants.LOADING_BATCH;					
	}
	this.batchStatusPanelVisible = false;
	this.txtPLActivityType.text = "";
	this.Installation_startTime.text = "";
	this.Installation_endTime.text = "";
	this.executedObjCount.text = "";
	this.lBtnFailedObjCount.label = "";
	this.lblFailedObjCount.text = "";
	this.failedGraphVisible = false;
	this.instProgressBar.indeterminate = true;
	this.instProgressBar.label = "";
	this.detailsButtonEnabled = false;
	//For start button, the start Button Enabled property is having value !startButtonEnabled 
	//so if specified true here, while display it will false i.e. the start button will be disabled.
	this.startButtonEnabled = true;
}