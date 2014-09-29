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
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.RequestListenerInfo;
import com.majescomastek.jbeam.view.components.batchdetails.BatchRevisionPod;
import com.majescomastek.jbeam.view.components.installationlist.FailedObjectsWindow;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.core.Window;
import mx.events.ListEvent;

[Bindable]
private var batchDetails:BatchDetailsData;

[Bindable]
private var listenerList:ArrayCollection;

[Bindable]
private var listenerTitle:String;

[Bindable]
/** The enabled status of the Refresh button */
private var refreshButtonEnabled:Boolean;

/** The event constant used for denoting the click of refresh button */
public static const REFRESH_CLICK:String = "REFRESH_CLICK";

/** The event constant used for denoting the cleanup request for this view */
public static const CLEANUP_LISTENER_WINDOW:String = "CLEANUP_LISTENER_WINDOW";

/** The event constant used to denote the request to fetch the list of listeners */
public static const REQUEST_LISTENER_DATA:String = "REQUEST_LISTENER_DATA";

/**
 * Handle the startup completion of this native window.
 */
public function handleStartupComplete(data:Object):void
{
	if(data == null)	return;
	
			
	batchDetails = BatchDetailsData(data['batchDetails']);
	derivePageState();
	sendDataEvent(REQUEST_LISTENER_DATA, batchDetails);
}

/**
 * Derive the state of the view variables based on the initialization data.
 */
private function derivePageState():void
{
	listenerTitle = resourceManager.getString('jbeam','listener_details_title')
						+ " - Batch # " + batchDetails.batchNo + "." + batchDetails.batchRevNo;
	refreshButtonEnabled = (batchDetails.batchStatus != CommonConstants.CLOSURE || 
			batchDetails.batchStatus != CommonConstants.STOPPED) &&
			batchDetails.execEndTime == null;
}

/**
 * The function invoked when the title window is closed.
 */
public function performCleanup(event:Event):void
{
	this.close();
	cleanup();
}

/**
 * The function invoked when the refresh button is clicked.
 */
private function refreshClick(event:Event):void
{
	sendDataEvent(REFRESH_CLICK, batchDetails);
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_LISTENER_WINDOW);	
}

/**
 * Handle the success of the retrieval of failed objects data.
 */
public function handleListenerRetrieval(data:Object):void
{
	if(data == null)	return;
	
	listenerList = ArrayCollection(data);
}

/**
 * The function invoked when the failed objects link is clicked.
 */
private function onFailedObjectClick(event:ListEvent):void
{
	var window:FailedObjectsWindow = new FailedObjectsWindow();
	
	var dgNoOfObjectsFailed:String = event.itemRenderer.data.noOfObjectsFailed;
	var dgListenerId:Number = Number(event.itemRenderer.data.listenerId);
	if(dgNoOfObjectsFailed != '0'){
		(window as Window).open();
		var data:Object = {
			'view': window,
			'requestListenerInfo': getRequestListenerInfo(dgListenerId),
			'batchDetails': batchDetails
		};
		sendDataEvent(BatchRevisionPod.FAILED_OBJECT_CLICK, data);		
	}
}

/**
 * Create and return a RequestListenerInfo object based on the
 * BatchDetailsData object.
 */
private function getRequestListenerInfo(dgListenerId:Number):RequestListenerInfo
{
	var info:RequestListenerInfo = new RequestListenerInfo();
	info.batchNo = batchDetails.batchNo;
	info.batchRevNo = batchDetails.batchRevNo;
	info.installationCode = batchDetails.installationCode;
	info.listenerId = dgListenerId;
	return info;
}