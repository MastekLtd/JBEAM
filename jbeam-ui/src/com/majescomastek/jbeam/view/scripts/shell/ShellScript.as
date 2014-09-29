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
include "../../../common/CommonScript.as"

import com.majescomastek.common.events.PodStateChangeEvent;
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.ProgramNameConstants;
import com.majescomastek.jbeam.common.SWFConstants;
import com.majescomastek.jbeam.facade.shell.ShellFacade;
import com.majescomastek.jbeam.model.vo.MenuDetails;
import com.majescomastek.jbeam.model.vo.UserProfile;
import com.majescomastek.jbeam.view.IModule;
import com.majescomastek.jbeam.view.components.IViewComponent;
import com.majescomastek.jbeam.view.components.shell.Shell;

import flash.events.Event;
import flash.system.ApplicationDomain;

import mx.collections.ArrayCollection;
import mx.events.MenuEvent;

/** Login screen index in view stack */
public static const LOGIN:int = 0; 

/** Menu screen index in viewstack */
public static const MENU_VIEW:int = 1;

/** Change Server Config screen index in viewstack */
public static const CHANGE_SERVER_CONFIG:int = 2;

/** BPMS Dashboard screen index in viewstack */
public static const BPMS_DASHBOARD_INDEX:int = 3;

/** JBEAM Home string constant */
public static const HOME:String = 'HOME';

/** JBEAM Logout string constant */
public static const LOGOUT:String = 'LOGOUT';

/** JBEAM Online help string constant */
public static const ONLINE_HELP:String = 'ONLINE_HELP';

/** JBEAM Conatct string constant */
public static const CONTACT_US:String = 'CONTACT_US';

/** JBEAM Locale string constant */
public static const LOCALE:String = 'LOCALE';

/** Dummy variable  to prevent the Type coercion error*/
private var dummyStateChangeEvt:PodStateChangeEvent;

/** Arbitrary data to be passed to the freshly loaded module */
public var moduleInfo:Object = {};

/** Event constant to indicate the click of Logout link */
public static const LOGOUT_CLICK:String = 'LOGOUT_CLICK';

/** Event constant to indicate the click of Home link */
public static const HOME_CLICK:String = 'HOME_CLICK';

/** The event constant used to denote the request to fetch the menu */
public static const MENU_DETAILS_REQUEST:String = "MENU_DETAILS_REQUEST";

/** The event constant used to denote the request to show default view */
public static const DEFAULT_VIEW_REQUEST:String = "DEFAULT_VIEW_REQUEST";

[Bindable]
private var defaultMenuList:ArrayCollection;

[Bindable]
public var userProfile:UserProfile = new UserProfile();

private static const facade:ShellFacade = ShellFacade.getInstance(ShellFacade.NAME);
//private var facade:ShellFacade;

[Bindable]
public var menuCollection:ArrayCollection ;

[Bindable]
private var defaultProgramName:String;

/**
 * Function will be called on click of the menu item
 */	
private function itemClickHandler(event:MenuEvent):void
{
	var labelVal:String = event.item.functionName;
	var programName:String = null;
	switch(labelVal)
	{
		case CommonConstants.RUN_BATCH:
			programName = ProgramNameConstants.SCHEDULE_BATCH_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.VIEW_SCHEDULE:
			programName = ProgramNameConstants.VIEW_SCHEDULE_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.DEFINE_CALENDAR:
			programName = ProgramNameConstants.CALENDAR_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.CURRENT_BATCH:
			programName = ProgramNameConstants.BATCH_DETAILS_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.SEARCH_BATCH:
			programName = ProgramNameConstants.SEARCH_BATCH_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.MANAGE_USER:
			programName = ProgramNameConstants.MANAGE_USER_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.EDIT_PROFILE:
			programName = ProgramNameConstants.USER_PROFILE_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.CHANGE_PASSWORD:
			programName = ProgramNameConstants.CHANGE_PASSWORD_MODULE_PROGRAM_NAME;
			break;
		case CommonConstants.GENERATE_REPORTS:
			programName = ProgramNameConstants.REPORTS_MODULE_PROGRAM_NAME;
			break;
		default:
			AlertBuilder.getInstance().
				show(event.item.functionName + " module is under construction.");
			break;
	}
	if(!programName)	return;
	
	var data:Object = {};
	data[CommonConstants.KEY_PROGRAM_NAME] = programName;
	loadModule(data);
}

/**
 * The function invoked when the module has finished loading.
 */
private function moduleReadyHandler(event:Event):void
{
	var mod:IModule = modBody.child as IModule;
	mod.moduleInfo = moduleInfo;
}

private function shellCreationComplete(shell:Shell):void
{
	facade.startup(shell);
}

/**
 * Function to load the module.
 * 
 */ 
public function loadModule(data:Object):void
{
	if(ProgramNameConstants.INSTALLATION_MODULE_PROGRAM_NAME == data[CommonConstants.KEY_PROGRAM_NAME])
	{	
		setModuleData(ProgramNameConstants.INSTALLATION_MODULE_PROGRAM_NAME);
	}
	else if(ProgramNameConstants.INSTALLATION_LIST_MODULE_PROGRAM_NAME == data[CommonConstants.KEY_PROGRAM_NAME])
	{
		setModuleData(ProgramNameConstants.INSTALLATION_LIST_MODULE_PROGRAM_NAME);		
	}
	prepareDataForNewModule(data);
	unloadCurrentModule();
	modBody.applicationDomain = ApplicationDomain.currentDomain;
	var url:String = String(SWFConstants.MAP[data[CommonConstants.KEY_PROGRAM_NAME]]);
	modBody.loadModule(url);
}

/**
 * Set the data for the module to be loaded based on the passed in
 * object to the loadModule function along with the data of the
 * current module i.e. module to be unloaded.
 */
private function prepareDataForNewModule(data:Object):void
{
	moduleInfo = data;
	if(modBody.child as IModule != null)
	{
		if(IModule(modBody.child).moduleInfo.hasOwnProperty(CommonConstants.KEY_PREVIOUS_MODULE_DATA))
			delete IModule(modBody.child).moduleInfo[CommonConstants.KEY_PREVIOUS_MODULE_DATA];
		
		moduleInfo[CommonConstants.KEY_PREVIOUS_MODULE_DATA] = IModule(modBody.child).moduleInfo;
	}
}

/**
 * Unload the current module and perform necessary cleanup.
 */
public function unloadCurrentModule():void
{
	var mod:IModule = modBody.child as IModule;
	if(mod)	mod.cleanup();
	modBody.unloadModule();
}

/**
 * The function used to create the data required for loading
 * the start module.
 */ 
private function setModuleData(programName:String):void
{
	this.defaultProgramName = programName; 
}
/**
 * The function used to create the data required for loading
 * the start module.
 */ 
private function getModuleData(batchDetails:Object):Object
{
	var data:Object = {};	
	data[CommonConstants.KEY_PROGRAM_NAME] = this.defaultProgramName;
	if(batchDetails != null)
	{
		data['batchDetails'] = batchDetails;
	}
	return data;
}

/**
 * The function used to create the data required for loading
 * the start module.
 */ 
private function getUserModuleData(editFlag:Boolean):Object
{
	var programName:String = null;
	if(editFlag)
	{
		programName = ProgramNameConstants.CHANGE_PASSWORD_MODULE_PROGRAM_NAME;
	}
	else
	{
		programName = ProgramNameConstants.MANAGE_USER_MODULE_PROGRAM_NAME;
	}
	var data:Object = {};	
	data[CommonConstants.KEY_PROGRAM_NAME] = programName;
	return data;
}


/**
 * Load the default/home module for this application.
 */
public function showDefaultView(data:Object):void
{
	unloadCurrentView();
	createDefaultMenu(menuCollection);
	shellViewStack.selectedIndex = Shell.MENU_VIEW;
	var forcePasswordFlag:String = CommonConstants.USER_PROFILE.forcePasswordFlag;
	if(CommonConstants.YES == forcePasswordFlag)
	{
		loadModule(getUserModuleData(true));		
	}
	else
	{
		if(haveMultipleRoles())
		{
			loadModule(getModuleData(data));
		}
		else if(CommonConstants.HAVE_ADMIN_ROLE)
		{
			loadModule(getUserModuleData(false));
		}
	}
}

/**
 * Determine whether the logged in user have multiple roles or not based on the 
 * roles dervied.
 */
private function haveMultipleRoles():Boolean
{
	var invalid:Boolean = (CommonConstants.HAVE_ADMIN_ROLE && 
				(CommonConstants.HAVE_OPERATOR_ROLE || CommonConstants.HAVE_USER_ROLE)) ||
				(CommonConstants.HAVE_OPERATOR_ROLE || CommonConstants.HAVE_USER_ROLE) ;
	return invalid;
}

/**
 * Determine whether the logged in user have multiple roles or not based on the 
 * roles dervied.
 */
private function haveOperatorUserRole():Boolean
{
	var invalid:Boolean = CommonConstants.HAVE_OPERATOR_ROLE && CommonConstants.HAVE_USER_ROLE;
	return invalid;
}
/**
 * Navigate to the given view.
 */
public function navigateToView(viewId:int):void
{
	var component:IViewComponent = shellViewStack.selectedChild as IViewComponent;
	if(component != null)
	{
		component.cleanup();
	} 
	shellViewStack.selectedIndex = viewId;
}

/**
 * Clean the main viewing area of the screen along with clearing
 * user/global state.
 */
public function cleanupMainView():void
{
	// reset other fields if required.

	// Reset global state
	CommonConstants.SERVER_URL = null;
	CommonConstants.USER_PROFILE = null;
	CommonConstants.USER_INSTALLATION_ROLES = null;
	CommonConstants.HAVE_ADMIN_ROLE = false;
	CommonConstants.HAVE_USER_ROLE = false;
	CommonConstants.HAVE_OPERATOR_ROLE = false;	
	CommonConstants.INSTALLATION_DATA = null;
	unloadCurrentModule();
}

/**
 * The function used for changing the menu contents based on the
 * loaded module.
 */
public function handleMenuStartupCompletion():void
{
	var userData:UserProfile = CommonConstants.USER_PROFILE;
	if(userData.installationCode == null)
	{
		userData.installationCode = CommonConstants.BLANK_STRING;
	}
	sendDataEvent(MENU_DETAILS_REQUEST, userData);
}

public function handleMenuDetailsRetreival(data:Object):void
{
	if(!data) return;
	
	menuCollection = data['menuDetailsList'];
	var instCode:String = data['instCode'];
	if(instCode != null && instCode != CommonConstants.BLANK_STRING)
	{
		menuBar.dataProvider = menuCollection;	
	}
	else
	{
		var userProfile:UserProfile = CommonConstants.USER_PROFILE;
		if(userProfile.defaultView == CommonConstants.PODS_VIEW)
		{
			setModuleData(ProgramNameConstants.INSTALLATION_LIST_MODULE_PROGRAM_NAME);			
		}
		else if(userProfile.defaultView == CommonConstants.LIST_VIEW)
		{
			setModuleData(ProgramNameConstants.INSTALLATION_MODULE_PROGRAM_NAME);			
		}
		sendEvent(DEFAULT_VIEW_REQUEST);		
	}
}

private function showDefaultMenuScreen():void
{
	menuBar.dataProvider = defaultMenuList;
	header.instValue.text = CommonConstants.BLANK_STRING;
	header.instHeaderVisible = false;
	header.instValueVisible = false;
	header.serverDateHeaderVisible = false;
	header.serverDateValueVisible = false;
}
/**
 * The function used for changing the menu contents based on the
 * loaded module.
 */
public function handleMenuChange(data:Object):void
{
	var userProfile:UserProfile = CommonConstants.USER_PROFILE;
	if(userProfile != null)
	{
		header.userHeader.text = userProfile.userName;
	}
	
	if(data == null)
	{
		showDefaultMenuScreen();
	}
	else
	{
		var programName:String = data.programName;
		if(programName == null)
		{
			showDefaultMenuScreen();
		}
		else
		{
			// Read the menu details from `data` and switch to appropriate menu
			switch (programName)
		    {
		    	case ProgramNameConstants.INSTALLATION_LIST_MODULE_PROGRAM_NAME:
		    	case ProgramNameConstants.MANAGE_USER_MODULE_PROGRAM_NAME:
		    	case ProgramNameConstants.CHANGE_PASSWORD_MODULE_PROGRAM_NAME:
		    	case ProgramNameConstants.USER_PROFILE_MODULE_PROGRAM_NAME:
					showDefaultMenuScreen();
		    		break;
		    	case ProgramNameConstants.CALENDAR_MODULE_PROGRAM_NAME:
		    	case ProgramNameConstants.SCHEDULE_BATCH_MODULE_PROGRAM_NAME:
		    	case ProgramNameConstants.BATCH_DETAILS_MODULE_PROGRAM_NAME:
		    		header.instHeaderVisible = true;
		    		header.instValueVisible = true;
		    		header.instValue.text = data.installationData.installationCode;
					header.serverDateHeaderVisible = true;
					header.serverDateValueVisible = true;
					header.serverDateHeader.text = "Server [" + data.installationData.timezoneShortName +"] :";
		    		header.serverDateTime.timezoneOffset = data.installationData.timezoneOffset;
					
					var userData:UserProfile = CommonConstants.USER_PROFILE;
					userData.installationCode = data.installationData.installationCode;
					sendDataEvent(MENU_DETAILS_REQUEST, userData);
		    		break;
		    	
		    }
		}
	}
}

/**
 * Unload the current view by calling its cleanup.
 */
private function unloadCurrentView():void
{
	var component:IViewComponent = shellViewStack.selectedChild as IViewComponent;
	if(component)
	{
		component.cleanup();
	}
}

private function createDefaultMenu(menuCollection:ArrayCollection):ArrayCollection
{
	defaultMenuList = new ArrayCollection();
	var defaultMenuChildren:ArrayCollection = new ArrayCollection();
	if(menuCollection != null && menuCollection.length > 0)
	{
		for(var i:uint = 0; i < menuCollection.length; ++i)
		{
			var retrievedGetMenuDetails:Object = menuCollection.getItemAt(i);
			var menuDetails:MenuDetails = createGetMenuDetails(retrievedGetMenuDetails);
			var retrievedChildrenList:ArrayCollection =	retrievedGetMenuDetails.children;
					 
			if(CommonConstants.USER_MASTER == menuDetails.functionName ||
					CommonConstants.PROFILE == menuDetails.functionName)
			{		 
				for(var j:uint = 0; j < retrievedChildrenList.length; ++j)
				{
					var retrievedChildren:MenuDetails = retrievedChildrenList[j];
					var childrenData:MenuDetails = createChildrenData(retrievedChildren);
					childrenData.functionId = i + j + 1;
					if(menuDetails.children == null)
					{
						menuDetails.children = new ArrayCollection();
					}
					menuDetails.children.addItem(childrenData);
				}
				defaultMenuList.addItem(menuDetails);
			}
		}
	}
	return defaultMenuList;
}

/**
 * Create a MenuDetails object based on the data returned by the webservice.
 */
private function createGetMenuDetails(retrievedGetMenuDetails:Object):MenuDetails
{
	var menuDetails:MenuDetails = new MenuDetails();
	menuDetails.functionName = retrievedGetMenuDetails['functionName'];
	return menuDetails;
}
/**
 * Create a batch Data data object based on the data returned by the webservice.
 */
private function createChildrenData(retrievedChildren:Object):MenuDetails
{
	var childrenData:MenuDetails = new MenuDetails;
	childrenData.functionName = retrievedChildren['functionName'];
	childrenData.parentFunctionId = retrievedChildren['priorFunctionId'];
	return childrenData;
}

public function handleLoadImages(data:Object):void
{
	this.header.loadLogo(data);
	this.footer.loadLogo(data);
}