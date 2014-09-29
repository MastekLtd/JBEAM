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
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.screenvo.ShellScreenVO;
import com.majescomastek.jbeam.view.components.shell.Shell;

import flash.filesystem.File;
import flash.filesystem.FileMode;
import flash.filesystem.FileStream;

import mx.events.ItemClickEvent;

[Bindable]
/** The variable holding the visible state of the installation header */
public var instHeaderVisible:Boolean;


[Bindable]
/** The variable holding the visible state of the installation value */
public var instValueVisible:Boolean;

[Bindable]
/** The variable holding the visible state of the server date header */
public var serverDateHeaderVisible:Boolean;

[Bindable]
/** The variable holding the visible state of the server date value */
public var serverDateValueVisible:Boolean;

/**
 * The function invoked when the banner link is clicked.
 */
private function linkClickHandler(event:ItemClickEvent):void
{
	var vo:ShellScreenVO = ShellScreenVO(event.item);
	switch(vo.code)
	{
		case Shell.LOGOUT:
			sendEvent(Shell.LOGOUT_CLICK);
			break;
		case Shell.HOME:
			sendEvent(Shell.HOME_CLICK);
			break;
	}
}
[Bindable]
private var clientLogo:String;

public function loadFile():void
{
	clientLogo = resourceManager.getString('Image', 'stg_billing_logo_small');
	trace("In TopBanner >> loadFile >>"+clientLogo);
}

public function loadLogo(data:Object):void
{
	clientLogo = data as String;//CommonConstants.COMPANY_LOGO_SMALL;
	if(clientLogo == null)
		clientLogo = resourceManager.getString('Image', 'stg_billing_logo_small');
	trace("In TopBanner >> loadLogo >>"+clientLogo);
}
