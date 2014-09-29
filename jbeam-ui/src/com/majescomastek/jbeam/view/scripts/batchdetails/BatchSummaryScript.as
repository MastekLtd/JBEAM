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
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.InstructionParameter;
import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
import com.majescomastek.jbeam.model.vo.UserInstallationRole;

import mx.collections.ArrayCollection;

[Bindable]
/** The variable holding the visible state of the batch parameters */
private var batchParametersVisible:Boolean;

[Bindable]
/** The dataprovider for the batch parameters list */
private var batchParametersDataProvider:ArrayCollection;

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

/** The batch data used by this pod to show the batch details */
private var _batchDetails:BatchDetailsData;

/** The event constant used to denote the request to fetch the batch summary */
public static const FETCH_BATCH_SUMMARY:String = "FETCH_BATCH_SUMMARY";

/** The event constant used to denote the click on the Stop button*/
public static const STOP_CLICK:String = "STOP_CLICK";

/** The event constant used to denote the request to the batch */
public static const RUN_BATCH_REQUEST:String = 'RUN_BATCH_REQUEST';

/** The event constant used to represent the request to refresh this pod */
public static const REFRESH_BATCH_SUMMARY_POD:String = "REFRESH_BATCH_SUMMARY_POD";

[Bindable]
public function get batchDetails():BatchDetailsData
{
	return _batchDetails;
}

public function set batchDetails(value:BatchDetailsData):void
{
	_batchDetails = value;
	
}

/** The batch data used by this pod to show the batch summary */
private var _batchSummary:BatchSummaryData;

[Bindable]
public function get batchSummary():BatchSummaryData
{
	return _batchSummary;
}

public function set batchSummary(value:BatchSummaryData):void
{
	_batchSummary = value;
	
}
/**
 * Handle the startup completion of this view.
 */
public function handleStartupComplete():void
{
	//The revision number is hardcoded to display the objects for which batch was run.  
//	batchDetails.batchRevNo = 1;
//	derivePageState();
	sendDataEvent(FETCH_BATCH_SUMMARY, batchDetails);
}
/**
 * Handle the batch summary retrieval.
 */
public function handleBatchSummaryRetrieval(data:Object):void
{
	if(data == null ) return;
	
	batchSummary = data as BatchSummaryData;
	
	if(batchSummary.instructionParametersList != null &&
		batchSummary.instructionParametersList.length > 0)
	{
		batchParametersVisible = true;	
		batchParametersDataProvider = batchSummary.instructionParametersList;
	} 
	sendEvent(REFRESH_BATCH_SUMMARY_POD);			
//	derivePageState();
}

/**
 * Handle the successful completion of the Batch data retrieval service.
 */
public function handleGetBatchSuccess(data:Object):void
{
	var list:ArrayCollection = data as ArrayCollection;
	if(list == null || list.length == 0)	return;
	
	var retrievedBatchDetailsData:BatchDetailsData = list.getItemAt(0) as BatchDetailsData;
	var installationData:InstallationData = createInstallationData();
	batchDetails = retrievedBatchDetailsData;
	if(batchDetails.batchRevNo < installationData.batchRevNo)
	{
		createBatchDetails(installationData);	
	}
//	trace("handleGetBatchSuccess >> Batch Rev No = " + batchDetails.batchRevNo);	
	derivePageState();
}

/**
 * The function invoked by the mediator to request the pod to refresh
 * its contents.
 */
public function handleRefreshRequest():void
{
	var installationData:InstallationData = createInstallationData();
	if(!isBatchClosed(installationData))// && !isFreshInstallation(installationData))
	{
		if(installationData.batchRevNo == batchDetails.batchRevNo)
		{
			sendEvent(REFRESH_BATCH_SUMMARY_POD);			
		}
	}
}

/**
 * Create the request data required for refreshing this pod along with the
 * mediator name for which the refresh should happen.
 */
public function getRequestDataForMediator(mediatorName:String):Object
{
	if(batchDetails == null) 
	{
		return {};
	}
	else
	{
		var installationData:InstallationData = new InstallationData();
		installationData.batchNo = batchDetails.batchNo;
		installationData.batchRevNo = batchDetails.batchRevNo;
		installationData.installationCode = batchDetails.installationCode;
		return {
			'installationData': installationData,
			'mediatorName': mediatorName
		};
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
 * Derive the state of the view variables based on the value of the
 * InstallationData attributes.
 */
private function derivePageState():void
{
	var installationData:InstallationData = createInstallationData();	

	if(installationData == null) return;
	//	trace("derivePageState >> Batch Rev No = " + installationData.batchRevNo);
	
	if(batchDetails.installationCode == installationData.installationCode)
	{
		setInstallationBasedRoles(
			CommonConstants.USER_INSTALLATION_ROLES, 
			installationData.installationCode);
		startButtonVisible = CommonConstants.HAVE_OPERATOR_ROLE;
		if(startButtonVisible)
		{
			startButtonEnabled = !isBatchClosed(installationData) || isBatchCompleted(installationData);
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
	}
	
}
/** 
 * The function invoked when the Start button is clicked. 
 * It will shift control to Schedule Batch Module   
 */
private function onStartButtonClick(evt:Event):void
{
	var installationData:InstallationData = createInstallationData();
	var data:Object = {
		'installationData': installationData,
		'programName': ProgramNameConstants.SCHEDULE_BATCH_MODULE_PROGRAM_NAME
	};
	if(!isFreshInstallation(installationData) || !isBatchCompleted(installationData))
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
	var batchData:BatchDetailsData = data as BatchDetailsData;
	
	if(batchData.installationCode == batchDetails.installationCode)
	{
		AlertBuilder.getInstance().show
			("[Installation: "+ batchData.installationCode
				+ "] \n" + CommonConstants.RESUME_BATCH_REQUEST_SUBMITTED
				+ new Date(Number(batchData.responseTime)));
		
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

/**
 * The function invoked by the mediator to request the pod to stop 
 * the current batch.
 */
public function handleStopBatchSuccess(data:Object):void
{
	if(data == null) return;
	
	var instData:InstallationData = data as InstallationData;
	if(instData.installationCode == batchDetails.installationCode)
	{
		stopRequestSubmitted = true;
		stopButtonEnabled = false;
		AlertBuilder.getInstance().show
			("[Installation: "+ instData.installationCode
				+ "] \n" + CommonConstants.STOP_BATCH_REQUEST_SUBMITTED
				+ new Date(Number(instData.responseTime)));
	}
}
private function createInstructionParameters():ArrayCollection
{
	var instructionParametersList:ArrayCollection = new ArrayCollection();
	var instParamBatchNo:InstructionParameter = new InstructionParameter();
	instParamBatchNo.name = "BATCH_NO";
	instParamBatchNo.slNo = 1;
	instParamBatchNo.type = "I";
	instParamBatchNo.value = batchDetails.batchNo.toString();
	instructionParametersList.addItem(instParamBatchNo);
	var instParamBatchRevNo:InstructionParameter = new InstructionParameter();
	instParamBatchRevNo.name = "BATCH_REV_NO";
	instParamBatchRevNo.slNo = 2;
	instParamBatchRevNo.type = "I";
	instParamBatchRevNo.value = batchDetails.batchRevNo.toString();
	instructionParametersList.addItem(instParamBatchRevNo);
	return instructionParametersList;
	
}
private function createReqInstructionLog(reqMessage:String):ReqInstructionLog
{
	var reqInstructionLog:ReqInstructionLog = new ReqInstructionLog();
	reqInstructionLog.installationCode = batchDetails.installationCode;
	reqInstructionLog.instructingUser = CommonConstants.USER_PROFILE.userId;
	reqInstructionLog.instructionTime = new Date().getTime();
	reqInstructionLog.message = reqMessage;
	reqInstructionLog.batchNo = batchDetails.batchNo;
	reqInstructionLog.batchRevNo = batchDetails.batchRevNo;
	return reqInstructionLog;
}




/**
 * Determine whether this batch is closed or not based on the 
 * batch status and the batch end time.
 */ 
private function isBatchClosed(installationData:InstallationData):Boolean
{
	// If the batchStatus is CLOSURE / STOPPED & the batch end time
	// is not NULL, the batch has been closed/ stopped.
	var closed:Boolean = installationData.batchEndTime != null &&
		(installationData.batchStatus == CommonConstants.CLOSURE ||
			installationData.batchStatus == CommonConstants.STOPPED);
	
	//	trace ("BatchSummaryPod >>installationData.batchEndTime is " + installationData.batchEndTime);
	//	trace ("BatchSummaryPod >>installationData.batchStatus is " + installationData.batchStatus);
	//	trace ("BatchSummaryPod >>isBatchClosed is " + closed);
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
	//	trace ("isRunBatchRequested is " + invalid);
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
	//	trace ("BatchSummaryPod >>installationData.batchEndReason is " + installationData.batchEndReason);
	//	trace ("isBatchCompleted is " + invalid);
	return invalid;
}

/**
 * Create a batch details object using the installation data
 */
private function createInstallationData():InstallationData
{
	var installationData:InstallationData = null;
	if(batchDetails != null)
	{
//		//	trace("createInstallationData >> Batch Rev No = " + batchDetails.batchRevNo);
//		trace("createInstallationData >> Batch end reason = " + batchDetails.batchEndReason);
		installationData = new InstallationData();
		installationData.installationCode = batchDetails.installationCode;
		installationData.batchNo = batchDetails.batchNo;
		installationData.batchRevNo = batchDetails.batchRevNo;
		installationData.batchStatus = batchDetails.batchStatus;
		installationData.batchStartTime = batchDetails.execStartTime;
		installationData.batchEndTime = batchDetails.execEndTime;	
		installationData.batchEndReason = batchDetails.batchEndReason;
		installationData.timezoneId = batchDetails.timezoneId;	
		installationData.timezoneOffset = batchDetails.timezoneOffset;	
		installationData.timezoneShortName = batchDetails.timezoneShortName;
	}
	return installationData;
}

/**
 * Create a batch details object using the installation data
 */
private function createBatchDetails(installationData:InstallationData):void
{
	if(installationData != null)
	{
//		batchDetails = new BatchDetailsData();
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