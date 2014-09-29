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

import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.SystemInformation;
include "../../../common/CommonScript.as"

/** The event constant used to denote the request to fetch the system information */
public static const FETCH_SYSTEM_INFORMATION:String = "FETCH_SYSTEM_INFORMATION";

/** The installation data used by this pod to show the installation details */
private var _batchDetails:BatchDetailsData;

[Bindable]
public function get batchDetails():BatchDetailsData
{
	return _batchDetails;
}

public function set batchDetails(value:BatchDetailsData):void
{
	_batchDetails = value;	
}

/** The installation data used by this pod to show the installation details */
private var _systemInformation:SystemInformation;

[Bindable]
public function get systemInformation():SystemInformation
{
	return _systemInformation;
}

public function set systemInformation(value:SystemInformation):void
{
	_systemInformation = value;	
}

/**
 * Handle the startup completion of this view.
 */
public function handleStartupComplete():void
{
	sendDataEvent(FETCH_SYSTEM_INFORMATION, batchDetails);
}


/**
 * Handle the system information retrieval.
 */
public function handleSystemInformationRetrieval(data:Object):void
{
	systemInformation = data as SystemInformation;
}
