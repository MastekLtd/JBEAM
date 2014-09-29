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

import com.majescomastek.jbeam.model.vo.ProgressLevelData;
import com.majescomastek.jbeam.model.vo.RequestListenerInfo;

import flash.events.Event;

import mx.collections.ArrayCollection;

[Bindable]
private var progressLevelData:ProgressLevelData;

[Bindable]
private var batchObjectList:ArrayCollection;

[Bindable]
private var batchObjectTitle:String;

/** The event constant used for denoting the click of refresh button */
public static const REFRESH_CLICK:String = "REFRESH_CLICK";

/** The event constant used for denoting the cleanup request for this view */
public static const CLEANUP_BATCH_OBJECT_WINDOW:String = "CLEANUP_BATCH_OBJECT_WINDOW";

/** The event constant used to denote the request to fetch the list of failed objects */
public static const REQUEST_BATCH_OBJECT_DATA:String = "REQUEST_BATCH_OBJECT_DATA";

/**
 * Handle the startup completion of this native window.
 */
public function handleStartupComplete(data:Object):void
{
	if(data == null)	return;
	
	progressLevelData = ProgressLevelData(data['progressLevelData']);
	derivePageState();
	sendDataEvent(REQUEST_BATCH_OBJECT_DATA, progressLevelData);
}

/**
 * Derive the state of the view variables based on the initialization data.
 */
private function derivePageState():void
{
	batchObjectTitle = resourceManager.getString('jbeam','batch_object_details_title')
						+ " - Batch # " + progressLevelData.batchNo + "." + progressLevelData.batchRevNo;
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
	sendDataEvent(REFRESH_CLICK, progressLevelData);
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_BATCH_OBJECT_WINDOW);	
}

/**
 * Handle the success of the retrieval of failed objects data.
 */
public function handleBatchObjectRetrieval(data:Object):void
{
	if(data == null)	return;
	
	batchObjectList = ArrayCollection(data);
}