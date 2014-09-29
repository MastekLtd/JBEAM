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
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.common.ProgramNameConstants;
import com.majescomastek.jbeam.facade.searchbatch.SearchBatchModuleFacade;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.ReqSearchBatch;

import flash.events.Event;
import flash.events.FocusEvent;

import mx.collections.ArrayCollection;
import mx.events.ListEvent;

import org.puremvc.as3.multicore.patterns.facade.Facade;

import flash.ui.Keyboard;
import flash.events.KeyboardEvent;

include "../../../common/CommonScript.as"

/** The event constant used to denote the request to search the batch data*/
public static const SEARCH_BATCH_REQUEST:String = 'SEARCH_BATCH_REQUEST';

/** The event constant used to denote the click on the batch completed search result item click */
public static const SHOW_BATCH_DETAILS_CLICK:String = "SHOW_BATCH_DETAILS_CLICK";


[Bindable]
private var batchEndReasonList:ArrayCollection;

[Bindable]
private var batchTypeList:ArrayCollection;

[Bindable]
private var searchBatchResultList:ArrayCollection;

[Bindable]
private var retrievedSearchBatchResultList:ArrayCollection;

private function createBatchEndReasonList():void
{
	batchEndReasonList = new ArrayCollection();
	batchEndReasonList.addItem({name:'- Select -', value:''});
	batchEndReasonList.addItem({name:'BATCH COMPLETED', value:'BATCH_COMPLETED'});
	batchEndReasonList.addItem({name:'USER INTERRUPTED', value:'USER_INTERRUPTED'});
	batchEndReasonList.addItem({name:'END OF TIME', value:'END_OF_TIME'});
	batchEndReasonList.addItem({name:'BATCH FAILED', value:'BATCH_FAILED'});
	batchEndReasonList.addItem({name:'PRE ISSUED STOP', value:'PRE_ISSUED_STOP'});
}
		
private function createBatchTypeList():void
{
	batchTypeList = new ArrayCollection();
	batchTypeList.addItem({name:'- Select -', value:''});
	batchTypeList.addItem({name:'DATE RUN', value:'DATE'});
	batchTypeList.addItem({name:'SPECIAL RUN', value:'SPECIAL'});
//	batchTypeList.addItem({name:'SCHEDULED', value:'SCHEDULED'});
}

/**Property to hold the alert message.*/
[Bindable]
public var alertMessage:String = '';

[Bindable]
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

/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:SearchBatchModuleFacade =
		SearchBatchModuleFacade.getInstance(SearchBatchModuleFacade.NAME);
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
//	sendEvent(CLEANUP_SCHEDULE_BATCH);
	Facade.removeCore(SearchBatchModuleFacade.NAME);
}

public function showAlertMessage(_alertMessage:String):void
{
	this.alertMessage = _alertMessage;
}

/**
 * Handle the startup completion of the Search Batch module.
 */
public function handleStartupComplete():void
{	
	CommonUtils.getIMEStatus();
	
	enableNumberEntry();
	
	var data:Object = moduleInfo;
	if(!data)	return;
	
	createBatchEndReasonList();
	createBatchTypeList();
	
}

/**
 * Enable number entry in phone no field. No text is permitted.
 */
public function enableNumberEntry():void
{
	this.txtBatchNo.addEventListener(FocusEvent.FOCUS_IN, CommonUtils.focusInHandlerNum);
	this.txtBatchNo.addEventListener(FocusEvent.FOCUS_OUT, CommonUtils.focusOutHandlerNum);
	this.txtBatchNo.restrict = "0-9";
	
}

/**
 * Handle the search batch result.
 */
public function handleSearchBatchServiceResult(searchData:ArrayCollection):void
{	
	if(searchData == null || (searchData != null && searchData.length == 0)) 
	{
		sendDataEvent(SearchBatchModuleFacade.SHOW_ALERT_MESSAGE, "No Records found");	
	}
	else
	{
		retrievedSearchBatchResultList = searchData;
		
		if(retrievedSearchBatchResultList == null)
		{
			retrievedSearchBatchResultList = new ArrayCollection();
			retrievedSearchBatchResultList.addItem(searchData);
		}
		
		searchBatchResultList = new ArrayCollection();
		
		for(var i:int = 0 ; i < retrievedSearchBatchResultList.length ; i++)
		{
			if(retrievedSearchBatchResultList[i] != null)
			{
				retrievedSearchBatchResultList[i].execStartTime = retrievedSearchBatchResultList[i].execStartTime;
				
				if(retrievedSearchBatchResultList[i].batchEndReason == null 
						|| retrievedSearchBatchResultList[i].batchEndReason == undefined)
				{
					retrievedSearchBatchResultList[i].batchEndReason = "INCOMPLETE";
					retrievedSearchBatchResultList[i].execEndTime = "----";		
				}
				else 
				{
					retrievedSearchBatchResultList[i].batchEndReason 
						= CommonUtils.replaceString(retrievedSearchBatchResultList[i].batchEndReason);
					retrievedSearchBatchResultList[i].execEndTime = retrievedSearchBatchResultList[i].execEndTime;
				}						
				searchBatchResultList.addItem(retrievedSearchBatchResultList[i]);
			}
		}
		sendDataEvent(SearchBatchModuleFacade.SHOW_ALERT_MESSAGE, "The search results in "+searchBatchResultList.length + " records.");	
	}
}

/**
 * If pressed key is ENTER then SEARCH_BATCH_REQUEST event will
 * be dispathched.
 */
private function onEnterClick(evt:KeyboardEvent):void
{
	if(evt.keyCode == Keyboard.ENTER)
	{
		searchData();
	}	
}

private function searchData():void
{		
	//Check and empty the search result list
	if(searchBatchResultList != null && searchBatchResultList.length > 0)
	{
		searchBatchResultList = null; 
	}
	var searchBatch:ReqSearchBatch = createSearchData();
	sendDataEvent(SEARCH_BATCH_REQUEST, searchBatch);
}
private function createSearchData():ReqSearchBatch
{		
	var searchBatch:ReqSearchBatch = new ReqSearchBatch(); 
	var installationData:InstallationData = moduleInfo.previousModuleData.installationData;
	if(installationData == null)
	{
		installationData = CommonConstants.INSTALLATION_DATA;
	}
	searchBatch.installationCode = installationData.installationCode;
	searchBatch.batchNo = Number(txtBatchNo.text);
	searchBatch.batchName = txtBatchName.text;
	searchBatch.batchType = comboBatchType.selectedItem.value;
	searchBatch.batchDate = txtBatchDate.text;
	searchBatch.batchEndReason = this.comboStatus.selectedItem.value;
	searchBatch.instructionSeqNo = "";
	return searchBatch;
}
	
 // Event handler for the DateField change event.
private function dateChanged(date:Date):void 
{
 	if (date != null)
 	{
		txtBatchDate.text = formatDate.format(date);
  	}
}
      
private function formatBatchDate(item:Date):String 
{
	return formatDateTimeUSD.format(item);
}   
		
private function clearAllParams():void
{
	txtBatchNo.text = "";
	txtBatchName.text = "";
	comboBatchType.selectedIndex = 0;
	comboStatus.selectedIndex = 0;
	txtBatchDate.text = "";		
}

private function clearSearchData():void
{
	searchBatchResultList = null;
	this.alertMessage = "";
}
	
	
private function itemClickEvent(event:ListEvent):void 
{
   	var installationData:InstallationData = new InstallationData();
   	var moduleInstallationData:InstallationData = moduleInfo.previousModuleData.installationData;
   
   	if(moduleInstallationData == null)
   	{
   		moduleInstallationData = CommonConstants.INSTALLATION_DATA;
   	}
   	
   	installationData.installationCode = moduleInstallationData.installationCode;   	
   	installationData.timezoneId = moduleInstallationData.timezoneId;   	
   	installationData.timezoneOffset = moduleInstallationData.timezoneOffset;   	
   	installationData.timezoneShortName = moduleInstallationData.timezoneShortName;   	
   	installationData.batchNo = event.itemRenderer.data.batchNo;
   	installationData.batchRevNo = event.itemRenderer.data.batchRevNo;
   	
	showBatchDetails(installationData);
}
    
/** 
 * Display batch details  
 */
private function showBatchDetails(installationData:InstallationData):void
{
	var data:Object = {
		'installationData': installationData,
		'programName': ProgramNameConstants.BATCH_DETAILS_MODULE_PROGRAM_NAME
	};
	sendDataEvent(SHOW_BATCH_DETAILS_CLICK, data);
}
