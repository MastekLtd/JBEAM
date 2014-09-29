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
include "ScheduleBatchScript.as"

import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.facade.schedulebatch.ScheduleBatchModuleFacade;
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.ReqInstructionLog;

import flash.events.Event;
import flash.events.FocusEvent;
import flash.geom.Point;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.core.IToolTip;
import mx.events.CloseEvent;
import mx.managers.ToolTipManager;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.mxml.HTTPService;
import mx.validators.Validator;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request for the cleanup of this module */
public static const CLEANUP_SCHEDULE_BATCH:String = 'CLEANUP_SCHEDULE_BATCH';

/** The event constant used to denote the request to the batch */
public static const RUN_BATCH_REQUEST:String = 'RUN_BATCH_REQUEST';

/** The event constant used to denote the request to get the drop down data */
public static const GET_DROP_DOWN_DATA_REQUEST:String = 'GET_DROP_DOWN_DATA_REQUEST';

/** The event constant used to denote the request to navigate to default view */
public static const NAVIGATE_DEFAULT_VIEW_REQUEST:String = "NAVIGATE_DEFAULT_VIEW_REQUEST";

[Bindable]
/** Hold the data related to this module */
private var _moduleInfo:Object;

[Bindable]
/** The dataprovider for the entity list */
private var entityDataProvider:ArrayCollection;

private var reccurrenceFlag:Boolean = false;

private var serverUrl:String = null;

public var myToolTip:IToolTip;

/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:ScheduleBatchModuleFacade =
		ScheduleBatchModuleFacade.getInstance(ScheduleBatchModuleFacade.NAME);
	facade.startup(this);
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_SCHEDULE_BATCH);
	Facade.removeCore(ScheduleBatchModuleFacade.NAME);
}

public function get moduleInfo():Object
{
	return _moduleInfo;
}

public function set moduleInfo(value:Object):void
{
	_moduleInfo = value;
}

/**
 * Handle the startup completion of the ScheduleBatch module.
 * At times when the ScheduleBatchModule is loaded after clicking on menu item 
 * 'Run Batch' the data.installationData can be null.
 * When the user clicks on 'Start' button in the installation pod, 
 * the data.installationData is available. 
 */
public function handleStartupComplete():void
{	
	var data:Object = moduleInfo;
	if(!data)	return;
	
	var batchEntityList:ArrayCollection = null;
	var installationData:InstallationData = data.installationData;	
	if(installationData == null)
	{
		installationData = CommonConstants.INSTALLATION_DATA;
	}
	if(!installationData) return;
	
	instCode = installationData.installationCode;
	instTimeZoneOffset = installationData.timezoneOffset;
	instTimeZoneShortName = installationData.timezoneShortName;
	
//	trace("Time zone = "+ installationData.timezoneId + " >> " + installationData.timezoneOffset);
//	var dt:Date = new Date();
//	dt.setTime(dt.getTime() + dt.getTimezoneOffset() * 1000 * 60 + installationData.timezoneOffset);
//	trace("Date with TimeZone = "+CommonUtils.formatDate(dt));
	
	batchEntityList = installationData.entityList;
	
	populateEntityDataProvider(batchEntityList);
	initEndOnTimeList(installationData);
	createSkipFlagList();
	createKeepAliveList();
//	createICDURL();
}

public function createICDURL():void
{
	serverUrl = CommonConstants.SERVER_URL;
	serverUrl = serverUrl.substring(0, serverUrl.lastIndexOf(":"));
	serverUrl = serverUrl + ":8080/ICDService/services";
	trace(serverUrl);
//	this.icdEndPointURL.text = serverUrl;
}
/**
 * Handle the run batch service result
 */
public function handleRunBatchServiceResult(data:Object):void
{
	clearAllParams();				
	var batchDetails:BatchDetailsData = data as BatchDetailsData;
	if(hBatchType.text == CommonConstants.DATE_RUN) {
		AlertBuilder.getInstance().show
			("[Installation: "+ batchDetails.installationCode
				+ "] \n" + CommonConstants.RUN_BATCH_REQUEST_SUBMITTED
				+ new Date(Number(batchDetails.responseTime)));
	} else {
		AlertBuilder.getInstance().show
			("[Installation: "+ batchDetails.installationCode
				+ "] \n" + CommonConstants.RUN_BATCH_REQUEST_SUBMITTED
				+ new Date(Number(batchDetails.responseTime))
				+ "\n\n"
				+ resourceManager.getString('jbeam','batch_object_details_message'));
	}
//	sendDataEvent(NAVIGATE_DEFAULT_VIEW_REQUEST, batchDetails);
}


//Changes Done on 29-May-2012 (Mandar) -- Start
/**
 * Send request to run batch.
 */
public function sendRunBatchRequest():void
{
	var reqInstructionLog:ReqInstructionLog = preapreDataToRunBatch();
	if(reqInstructionLog == null)		return;
	
	sendDataEvent(RUN_BATCH_REQUEST, reqInstructionLog);	
}


/**
 * Send request to run batch.
 */
public function runBatch(event:Event):void
{
	sendRunBatchRequest();
//	confirmICDServiceStatus();
}


//public function confirmICDServiceStatus():void
//{
//	Alert.show(resourceManager.getString("jbeam", "icd_service_check"),
//		"Warning",Alert.OK|Alert.CANCEL,this,confirmICDService,null,Alert.OK);
//}

//public function confirmICDService(eventObj:CloseEvent):void
//{
//	if(eventObj.detail==Alert.OK)
//	{
//		sendRunBatchRequest();
//	}
//	else if(eventObj.detail==Alert.CANCEL)
//	{
//		//no code required here unless you want to notify the user that they have cancelled the operation
//	}
//	
//}

//To check if the ICD services are up.
//public function checkICDService(evt:Event):void
//{
//	if(validateICDUrl()){
//		var http:HTTPService = new HTTPService();
//		
//		// register event handlers (resultHandler and faultHandler functions)
//		http.addEventListener( ResultEvent.RESULT, resultHandler );
//		http.addEventListener( FaultEvent.FAULT, faultHandler );
//		
//		// specify the url to request, the method and result format
////		http.url = this.icdEndPointURL.text;
//		http.method = "GET";
//		http.resultFormat = "text";
//		
//		// send the request
//		http.send();		
//	}
//}
//
public function resultHandler(evt:ResultEvent):void
{
	trace(evt.statusCode);	
	var statusCode:int = evt.statusCode;
	var statusResponse:String = null;
	switch(statusCode)
	{
		case 200:
		{
			statusResponse = resourceManager.getString('jbeam', 'success200');	
			break;
		}
		case 404:
		{
			statusResponse = resourceManager.getString('jbeam', 'error404');	
			break;
		}
		case 503:
		{
			statusResponse = resourceManager.getString('jbeam', 'error503');	
			break;
		}
		default:
		{
			statusResponse = resourceManager.getString('jbeam', 'errorUnknown');	
			break;
		}
	}
	AlertBuilder.getInstance().show(statusResponse);		
}
public function faultHandler(evt:FaultEvent):void
{
//	trace(evt.message);	
	if(evt.fault.faultCode == "Server.Error.Request" || evt.fault.faultString == "HTTP request error")
	{
		AlertBuilder.getInstance().show(resourceManager.getString('jbeam', 'wrong_url'));
	}
	else
	{
		AlertBuilder.getInstance().show(evt.fault.faultCode + " - " + evt.fault.faultDetail);			
	}
}
//
//public function validateICDUrl():Boolean
//{
//	var arrValidators:Array = [valICDUrl];//, strBusDate];
//	if(Validator.validateAll(arrValidators).length != 0)
//	{
//		CommonUtils.showValidationMessage(arrValidators, true);
//		return false;
//	}
//	else
//	{
//		return true;
//	}
//}
//Changes Done on 29-May-2012 (Mandar) -- end

/**
 * Handling foucs out of batchParam text
 */ 
private function batchParamFoucsOutHandler(event:Event):void
{	
	var entity:Object = comboEntity.selectedItem ;
	if(entity != null && comboEntity.selectedIndex > 0 )
	{	
		if(txtBatchParam.text.length > 0 && new Number(entity.noReqPram) > 1)
		{
			if(new Number(entity.noReqPram)!= getNoOfArgs(txtBatchParam.text))
			{
				txtBatchParam.errorString = entity.desc;
			}
			else
			{
				txtBatchParam.errorString = "";
			}
		}
		else
		{
			txtBatchParam.errorString = "";
		}
	}
	destroyToolTip();
}
 
private function getNoOfArgs(args:String):uint
{
	if(args != null)
	{	
		var newArgs:String = args.replace(CommonConstants.SEP_ESC_CHAR, CommonConstants.REP_BY_SPECIAL_CHAR);		
		return newArgs.split(CommonConstants.PARAM_SEPERATOR).length;
	}
	return 0;
}
private function enityComboChangeHandler(event:Event):void
{
	batchParamFoucsOutHandler(event);
}
private function createToolTip():void
{
	if(myToolTip)
	{
		destroyToolTip();
	}
	else
	{
		var entity:Object = comboEntity.selectedItem ;
		if(entity != null && comboEntity.selectedIndex > 0 )
		{	
			var ptx:Point = new Point(txtBatchParam.x, txtBatchParam.y);
			myToolTip = ToolTipManager.createToolTip(comboEntity.selectedItem.desc,txtBatchParam.localToGlobal(ptx).x
			,txtBatchParam.localToGlobal(ptx).y+23);
		}
	}
	
}
private function destroyToolTip():void
{
	if(myToolTip)
	{
		ToolTipManager.destroyToolTip(myToolTip);
		myToolTip = null;
	}
	
}
private function batchParamFocusInHandler(event:FocusEvent):void
{
	createToolTip();
}

public function handleInstallationListRetrieval(list:ArrayCollection):void
{
	trace("here");
}