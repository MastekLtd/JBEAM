// ActionScript file
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
 * @author Gourav Rai
 * 
 *
 * $Revision:: 1                        $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/ReportsModuleScript.as 1   $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/scripts/reports/ReportsModuleScript.as   $
 * 
 * 1     4/07/10 12:02p Gourav.rai
 * Added by Gourav Rai
 * 
 * 2     3/23/10 3:26p Gourav.rai
 * 
 * 1     3/22/10 2:27p Gourav.rai
 * Added By Gourav Rai
 * 
 */

import com.majescomastek.jbeam.facade.reports.ReportsFacade;

import flash.display.DisplayObject;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The constant is to select desired view*/
public static const SELECT_VIEW:String = "SELECT_VIEW";

/** The data held by this module */
private var moduleData:Object = {};

/**Property to hold the alert message.*/
[Bindable]public var alertMessage:String = ''; 

public static const REPORTS_PARAMETERS_VIEW:int = 0;
public static const REPORT_ENGINE_REQUEST_LOG:int = 1;
public static const REPORT_REQUEST_STATUS:int = 2;
public static const REPORT_ATTACHED_SCHEDULE:int = 3;

public function init():void
{
	var facade:ReportsFacade = ReportsFacade.getInstance(ReportsFacade.NAME+this.uid);
	facade.startup(this);
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	Facade.removeCore(ReportsFacade.NAME+this.uid);
}

/**
 * @inheritDoc
 */
[Bindable]
public function get moduleInfo():Object
{
	return moduleData;
}

/**
 * @inheritDoc
 */
public function set moduleInfo(value:Object):void
{
	moduleData = value;
}

/**
 * Retrieve the name of the notification Object
 */
public function getNotificationDisplayObject(index:int):DisplayObject
{	
	return vsId.getChildAt(index);
}

public function selectVSChild(index:int):void
{
	this.vsId.selectedIndex = index;
}

public function showAlertMessage(_alertMessage:String):void
{
	if(dontDisplay(_alertMessage))
	{
		this.alertMessage = _alertMessage;
	}
}

/**
 * Get the moduleInfo
 */
public function getModuleData():Object
{
	return {
		'moduleInfo': moduleInfo		
	};
}
private function dontDisplay(_alertMessage:String):Boolean
{
	/* var searchSuccess:String = resourceManager.getString('jbeam','search_submit_success');
	var searchFailed:String = resourceManager.getString('jbeam','search_submit_failed');
	var detailsSuccess:String = resourceManager.getString('jbeam','detail_submit_success');
	var detailsFailed:String = resourceManager.getString('jbeam','detail_submit_failed');
	var noRecords:String = resourceManager.getString('jbeam','no_records_for_search_criteria');
	if(_alertMessage == noRecords)
	{
		switch(alertMessage)
		{
			case searchSuccess:
				return false;
			case searchFailed:
				return false;
			case detailsSuccess:
				return false;
			case detailsFailed:
				return false;
			default:
				return true;
		}
	} */
	return true;
}