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

import flash.events.Event;

import mx.collections.ArrayCollection;

[Bindable]
private var batchDetails:BatchDetailsData;

[Bindable]
private var requestListenerInfo:RequestListenerInfo;

[Bindable]
private var failedObjectsList:ArrayCollection;

[Bindable]
private var failedObjectsTitle:String;

[Bindable]
/** The enabled status of the Refresh button */
private var refreshButtonEnabled:Boolean;

/** The event constant used for denoting the close of this title window */
public static const CLOSE_CLICK:String = "CLOSE_CLICK";

/** The event constant used for denoting the click of refresh button */
public static const REFRESH_CLICK:String = "REFRESH_CLICK";

/** The event constant used for denoting the cleanup request for this view */
public static const CLEANUP_FAILED_OBJECT_WINDOW:String = "CLEANUP_FAILED_OBJECT_WINDOW";

/** The event constant used to denote the request to fetch the list of failed objects */
public static const REQUEST_FAILED_OBJECT_DATA:String = "REQUEST_FAILED_OBJECT_DATA";


/**
 * Handle the startup completion of this native window.
 */
public function handleStartupComplete(data:Object):void
{
	if(data == null)	return;
	
	batchDetails = BatchDetailsData(data['batchDetails']);
	requestListenerInfo = RequestListenerInfo(data['requestListenerInfo'])
	derivePageState();
	sendDataEvent(REQUEST_FAILED_OBJECT_DATA, requestListenerInfo);	
}



/**
 * Derive the state of the view variables based on the initialization data.
 */
private function derivePageState():void
{
	failedObjectsTitle = "Failed Object Details - Batch # " +
		requestListenerInfo.batchNo + "." + requestListenerInfo.batchRevNo;
	refreshButtonEnabled = (batchDetails.batchStatus != CommonConstants.CLOSURE || 
			batchDetails.batchStatus != CommonConstants.STOPPED) &&
			batchDetails.execEndTime == null;
}

/**
 * The function invoked when the title window is closed.
 */
public function performCleanup(event:Event):void
{
	this['close']();
	cleanup();
}

/**
 * The function invoked when the refresh button is clicked.
 */
private function refreshClick(event:Event):void
{
	sendDataEvent(REFRESH_CLICK, requestListenerInfo);
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_FAILED_OBJECT_WINDOW);	
}

/**
 * Handle the success of the retrieval of failed objects data.
 */
public function handleFailedObjectsRetreival(data:Object):void
{
	if(data == null)	return;
	
	failedObjectsList = ArrayCollection(data);
}