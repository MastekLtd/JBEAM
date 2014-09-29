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
//include "../../../common/CommonScript.as"

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

/** The file selected by the user */
private var file:File;

/** The contents of the selected file */
private var content:String;

private function browseWindow():void
{
}


/**
 * This method processes the data in CSV file and sends it for inserting into 
 * local (i.e. SQLite) database. <p>
 * 
 * This method will be called on click of Upload button.
 */
public function processContent():void
{
//	file = new File("/resources/MonitorServicesConfiguration.csv");
	file = File.applicationStorageDirectory;
	file = file.resolvePath("app:/resources/MonitorServicesConfiguration.csv");
	var fileStream:FileStream = new FileStream();
	fileStream.open(file, FileMode.READ);
	content = String(fileStream.readUTFBytes(fileStream.bytesAvailable));
	fileStream.close();
	
	var lines:Array = content.split(File.lineEnding);
	for(var i: Number = 1; i < lines.length; i++)
	{
		var line: String = lines[i];
		addServerToDatabase(getServerForLine(line), "Add");
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
		sendDataEvent(ADD_SERVER_CLICK, server);				
	}
}

