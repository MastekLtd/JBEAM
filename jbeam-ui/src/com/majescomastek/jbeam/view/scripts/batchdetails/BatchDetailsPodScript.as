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

import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.Entity;
import com.majescomastek.jbeam.model.vo.GraphData;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.ProgressLevelData;
import com.majescomastek.jbeam.model.vo.RequestListenerInfo;
import com.majescomastek.jbeam.view.components.batchdetails.ListenerWindow;
import com.majescomastek.jbeam.view.components.installationlist.FailedObjectsWindow;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.core.Window;
include "../../../common/CommonScript.as"

/** The installation data used by this pod to show the installation details */
private var _batchDetails:BatchDetailsData;

[Bindable]
/** The variable holding the visible state of the failed object link */
private var failedLinkVisible:Boolean;

[Bindable]
/** The variable holding the visible state of the failed object link */
private var listenerNoStyle:String;

[Bindable]
/** The variable holding the visible state of the failed object graph */
private var failedGraphVisible:Boolean;

[Bindable]
/** The dataprovider for the failed object graph data */
private var pieDataProvider:ArrayCollection;

[Bindable]
/** The style change status of the Listener button */
private var changeListenerButtonStyle:Boolean;

[Bindable]
/** The variable holding the visible state of the batchProgressBar */
private var batchProgressBarVisible:Boolean;

private var progressBarLength:uint= 10;

[Bindable]
/** The dataprovider for the entity list */
private var entityDataProvider:ArrayCollection;

/** The event constant used to denote the click on the Failed objects link */
public static const FAILED_OBJECT_CLICK:String = "FAILED_OBJECT_CLICK";

/** The event constant used to denote the click on the Listener button  */
public static const LISTENER_CLICK:String = "LISTENER_CLICK";

/** The event constant used to denote the click on the Batch object link */
public static const BATCH_OBJECT_CLICK:String = "BATCH_OBJECT_CLICK";

/** The event constant used to represent the request to refresh this pod */
public static const REFRESH_POD:String = "REFRESH_POD";

/** The event constant used to denote the request to fetch the pie chart data */
public static const FETCH_FAILED_OBJECT_GRAPH_DATA:String = "FETCH_FAILED_OBJECT_GRAPH_DATA";

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
	derivePageState();
	if(!isFreshInstallation())
	{
		sendDataEvent(FETCH_FAILED_OBJECT_GRAPH_DATA, getGraphRequestData());
	}
}

/**
 * Derive the state of the view variables based on the value of the
 * BatchDetailsData attributes.
 */
private function derivePageState():void
{
	this.id = _batchDetails.batchNo + "_" + _batchDetails.batchRevNo;
	this.title = batchDetails.installationCode+" | Batch # "+ batchDetails.batchNo+ ": "+batchDetails.batchRevNo;
	this.fsBatchDetailsData.label = batchDetails.installationCode+" | Batch # "+ batchDetails.batchNo+ ": "+batchDetails.batchRevNo;
	failedLinkVisible = _batchDetails.noOfObjectsFailed > 0 ? true : false;
	
	changeListenerButtonStyle = (batchDetails.batchStatus == CommonConstants.CLOSURE || 
			batchDetails.batchStatus == CommonConstants.STOPPED) &&
			batchDetails.execEndTime != null;
		
	if(changeListenerButtonStyle)
	{
		listenerNoStyle = "subButton";
	}
	else
	{
		listenerNoStyle = "mainButton";		
	}
}

/**
 * Handle the successful completion of the Batch data retrieval service.
 */
public function handleGetBatchSuccess(data:Object):void
{
	var list:ArrayCollection = data as ArrayCollection;
	if(list == null || list.length == 0)	return;
	 
	var retrievedBatchDetailsData:BatchDetailsData = list.getItemAt(0) as BatchDetailsData;
	if(batchDetails.batchRevNo == retrievedBatchDetailsData.batchRevNo)
	{
		batchDetails = retrievedBatchDetailsData;
		CommonUtils.setBatchDetailsData(batchDetails);
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
			'mediatorName': mediatorName,
			'graphData': getGraphRequestData()
		};
	}
}

/**
 * Prepare the graph data object based on the installation details
 * of this pod.
 */
private function getGraphRequestData():GraphData
{
	var graphData:GraphData = new GraphData();
	graphData.installationCode = batchDetails.installationCode;
	graphData.batchNo = batchDetails.batchNo;
	graphData.batchRevNo = batchDetails.batchRevNo;
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
 * BatchDetailsData object.
 */
private function getRequestListenerInfo():RequestListenerInfo
{
	var info:RequestListenerInfo = new RequestListenerInfo();
	info.batchNo = batchDetails.batchNo;
	info.batchRevNo = batchDetails.batchRevNo;
	info.installationCode = batchDetails.installationCode;
	info.listenerId = 0;
	return info;
}
/**
 * The function invoked when the listener button is clicked.
 */
private function onListenerButtonClick(event:Event):void
{
	var window:ListenerWindow = new ListenerWindow();
	(window as Window).open();
	var data:Object = {
		'view': window,
		'batchDetails': batchDetails
	};
	sendDataEvent(LISTENER_CLICK, data);
}



/**
 * The function invoked by the mediator to request the pod to refresh
 * its contents.
 */
public function handleRefreshRequest():void
{
	if(!isBatchClosed() && !isFreshInstallation())
	{
		var installationData:InstallationData = CommonConstants.INSTALLATION_DATA;
		if(installationData.batchRevNo == batchDetails.batchRevNo)
		{
			sendEvent(REFRESH_POD);			
		}
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
	var closed:Boolean = this.batchDetails.execEndTime != null &&
							(this.batchDetails.batchStatus == CommonConstants.CLOSURE ||
							this.batchDetails.batchStatus == CommonConstants.STOPPED);
	return closed; 
}

/**
 * Determine whether this installation is fresh (newly created) or not based on the 
 * batch number and batch revision number.
 */
private function isFreshInstallation():Boolean
{
	// If the batchNo & batchRevNo are null, the batch is invalid.
	var invalid:Boolean = this.batchDetails.batchNo == 0 && this.batchDetails.batchRevNo == 0;
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
 * To display the progress bar. 
 */
public function populateBatchProgressBar():void
{
	//Entity list lenght + 2 for Init and closure
	if(entityDataProvider == null) return;
	  
	
	progressBarLength = entityDataProvider.length + 2;
		
	var progressLevelDataList:ArrayCollection = batchDetails.progressLevelDataList;
	
	if(progressLevelDataList != null && progressLevelDataList.length > 0)
	{
		batchProgressBarVisible = true;
	} 
	else
	{
		batchProgressBarVisible = false;
	}
	
	for(var i:int = 0; i < progressLevelDataList.length; i++)
	{	
		var progressLevelData:ProgressLevelData = progressLevelDataList[i];
		var nextProgressLevelDataValue:ProgressLevelData = new ProgressLevelData();
		
		//Condition to display the batch current status.
		if(i == 0 && progressLevelData != null) 
		{
			if(batchDetails.batchRevNo == progressLevelData.batchRevNo 
				&& batchDetails.batchStatus == progressLevelData.prgActivityType)
			{
				this.txtPLActivityType.text = batchDetails.batchStatus;				
			}
			else
			{
				this.txtPLActivityType.text = progressLevelData.prgActivityType;				
			}
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
}

/**
 * To populate the entity combo box 
 */
public function populateEntityDataProvider():void
{	
	var batchEntityList:ArrayCollection = batchDetails.entityList;
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
