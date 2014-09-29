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

import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.JbeamServer;

import flash.events.Event;
import flash.events.MouseEvent;
import flash.filesystem.File;
import flash.filesystem.FileMode;
import flash.filesystem.FileStream;
import flash.filters.DropShadowFilter;

import mx.collections.ArrayCollection;
import mx.events.ListEvent;

/** The event constant used to denote the request to persist a given server configuration */
public static const ADD_SERVER_CLICK:String = "ADD_SERVER_CLICK";

/** The event constant used to denote the request to update a given server configuration */
public static const UPDATE_SERVER_CLICK:String = "UPDATE_SERVER_CLICK";

/** The event constant used to denote the request to delete a given server configuration */
public static const REMOVE_SERVER_CLICK:String = "REMOVE_SERVER_CLICK";

/** The event constant used to denote the request to navigate to the login screen */
public static const SHOW_LOGIN_SCREEN:String = "SHOW_LOGIN_SCREEN";

/** The event constant used to denote the request to destroy the current view */
public static const CLEANUP_SERVER_COMPONENT_VIEW:String = "CLEANUP_SERVER_COMPONENT_VIEW";

/** The event constant used to denote the change in the server configuration */
public static const SERVER_CONFIGURATION_CHANGE:String = "SERVER_CONFIGURATION_CHANGE";

/** The file selected by the user */
private var file:File;

/** The contents of the selected file */
private var content:String;

/**
 * Clear the file selected for upload
 */
public function clearUploadedFile():void
{
	file = null;
	this.txtUploadedFile.text = "";
   	this.txtUploadedFile.errorString = "";
   	this.txtUploadedFile.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
}

/**
 * Clear the data entered for addition of server
 */
private function onClearServerDetails():void
{
	this.txtServerName.text = this.txtServerIP.text = 
		this.txtServerPort.text = "";
	
	
	this.txtServerName.errorString = "";   	
	this.txtServerName.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	
	this.txtServerIP.errorString = "";
	this.txtServerIP.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	
	this.txtServerPort.errorString = "";
	this.txtServerPort.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	
	this.btnAddServer.label = resourceManager.getString('ServerConfiguration','add_server');
	if(!this.txtServerName.enabled)
	{
		this.txtServerName.enabled = true;
	}   	
}

/**
 * Reset the state of this view.
 */
public function reset():void
{
	clearUploadedFile();
	onClearServerDetails();	
//	sendDataEvent(SERVER_CONFIGURATION_CHANGE, resourceManager.getString('Image', 'stg_billing_logo_small'));
}

 /**
 * To browse the file on local system.
 */
private function browseWindow():void
{
	file = new File();
	file.browse();
	file.addEventListener(Event.SELECT, onFileSelectHandler);
}

/**
 * The function invoked when the file is selected from the
 * 'Browse' window.
 */
private function onFileSelectHandler(event:Event):void
{
	this.txtUploadedFile.text = event.target.name;
	var fileStream:FileStream = new FileStream();
	fileStream.open(file, FileMode.READ);
	content = String(fileStream.readUTFBytes(fileStream.bytesAvailable));
	fileStream.close();
}

/**
 * Remove the selected server from the local database.
 */
private function onRemoveServerButtonClicked(event:MouseEvent):void
{
	var servers:ArrayCollection = this.serversDataGrid.dataProvider as ArrayCollection;
	
 	if( servers.length == 0)
 	{
 		AlertBuilder.getInstance().show("There is no server in the list to remove.");
		return;
 	}
	
	var itemArray:Array = this.serversDataGrid.selectedItems;
	if( itemArray.length > 0)
	{
		for(var i:int = 0; i < itemArray.length; i++)
		{
			sendDataEvent(REMOVE_SERVER_CLICK, itemArray[i]);
  		}
 	}
 	else if( itemArray.length == 0)
 	{
 		AlertBuilder.getInstance().show("Please select a server to remove.");
 	}
}

/**
 * Navigate to the login screen.
 */
private function goBackToLoginScreen():void
{
	sendEvent(SHOW_LOGIN_SCREEN);
}

/**
 * This method processes the data in CSV file and sends it for inserting into 
 * local (i.e. SQLite) database. <p>
 * 
 * This method will be called on click of Upload button.
 */
private function processContent():void
{
	if(validateUploadFile())
	{
		this.txtUploadedFile.text = "";
		var lines:Array = content.split(File.lineEnding);
		for(var i: Number = 1; i < lines.length; i++)
		{
			var line: String = lines[i];
			addServerToDatabase(getServerForLine(line), "Add");
		}		
	}
	
}


/**
 * Create a JbeamServer object for the given configuration string
 * read.
 */
private function getServerForLine(line:String):JbeamServer
{
	var server:JbeamServer = null;
	var values:Array = line.split(",");
	if(values != null && values.length == 3)
	{
		server = new JbeamServer();
		server.serverName = values[0];
		server.ipAddress = values[1];
		server.port = values[2];
	}
	return server;
}

/**
 * Request the mediator to add/update the given server cofiguration
 * to the persistent store.
 */
private function addServerToDatabase(server:JbeamServer, btnLabel:String):void
{
	if(server != null)	
	{
		if(btnLabel == "Add")
		{
			sendDataEvent(ADD_SERVER_CLICK, server);			
		}
		else if(btnLabel == "Update")
		{
			sendDataEvent(UPDATE_SERVER_CLICK, server);
		}			
	}
}

/**
 * Invoked when the Add button is clicked to add the server details.
 */
private function onAddServerButtonClicked(event:MouseEvent):void
{
    if(validateServerDetails())
    {
    	var server:JbeamServer = new JbeamServer();
	    server.serverName = this.txtServerName.text;
	    server.ipAddress = this.txtServerIP.text;
	    server.port = this.txtServerPort.text;
   		addServerToDatabase(server, event.currentTarget.label);
    }    
}

/**
 * Function will be called to validate the server details.
 */
public function validateServerDetails():Boolean
{
	var arrValidators:Array = [valServerName,valIPAddress, valPort];
	if(!CommonUtils.validateControls(arrValidators))
	{
		CommonUtils.showValidationMessage(arrValidators, true);
		return false;
	}
	else
	{
		return true;
	}
}

/**
 * Function will be called to validate the upload of file.
 */
public function validateUploadFile():Boolean
{
	var arrValidators:Array = [valFileName];
	if(!CommonUtils.validateControls(arrValidators))
	{
		CommonUtils.showValidationMessage(arrValidators, true);
		return false;
	}
	else
	{
		return true;
	}
}

/**
 * Handle the successful retrieval of the list of all the available servers.
 */
public function handleServerConfigurationRetrieval(serverList:ArrayCollection):void
{
	this.serversDataGrid.dataProvider = serverList;
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_SERVER_COMPONENT_VIEW);
}


private function itemClickEvent(event:ListEvent):void 
{
   	this.txtServerName.text = event.itemRenderer.data.serverName;
   	this.txtServerName.enabled = false;
   	this.txtServerIP.text = event.itemRenderer.data.ipAddress;
   	this.txtServerPort.text = event.itemRenderer.data.port;
	this.btnAddServer.label = resourceManager.getString('ServerConfiguration','update_server');
}

public function handleLoadImages(data:Object):void
{
	this.header.loadLogo(data);
	this.footer.loadLogo(data);
}

