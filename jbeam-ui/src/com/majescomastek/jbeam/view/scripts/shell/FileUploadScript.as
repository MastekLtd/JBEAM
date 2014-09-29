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
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.FileReference;
	
	import mx.collections.ArrayCollection;

	private var file:File = new File();
	private var fileStream:FileStream  = new FileStream();
	private var linedata:ArrayCollection = new ArrayCollection();
           
    private var content:String;
    
	/**
	 * To browse the file on local system.
	 */
   	private function browseWindow():void {
   		file.browse();
	   	file.addEventListener(Event.SELECT,openfileSRCHandler);
   	}
	    
   /**
    *  Read in the selected file when the file is selected
    */
	private function openfileSRCHandler(event:Event):void{
		//Alert.show("You selected a file name "+event.target.name+" that is " + event.target.size+" bytes in size");
        this.txtUploadedFile.text = event.target.name;
		fileStream.open(file, FileMode.READ);
		content = String(fileStream.readUTFBytes(fileStream.bytesAvailable));
		fileStream.close();
		trace("*** File loaded.");		
	}
	
	/**
	 * This method processes the data in CSV file and sends it for inserting into 
	 * local (i.e. SQLite) database. 
	 * This method will be called on click of Upload button.
	 * 
	 */
	private function processContent():void {
		//Remove file name from the uploaded file textbox 
		this.txtUploadedFile.text = "";
		//var ending:String = new String("\n\g");
		// Split the whole file into lines
		var values:Array;
		var lines:Array = content.split("\r\n");
		//Alert.show("File split into " + lines.length + " lines");
		// Split each line into data content – start from 1 instead of 0 as this is a header line.
		for ( var i: Number = 1; i < lines.length; i++ ) {
			var line: String = lines[i];
			values = line.split(",");
			//	Alert.show("line split in " + values);
			// Add values to arraycollection
			//	linedata.addItem({serverName:values[0], ipAddress:values[1], port:values[2]});
			if(values != null || values[0] != ""){				
				addToServer(values);
			}
		}
		//this.serversDataGrid.dataProvider = linedata;
	}
	/**
	 * This method collects the array for server as a parameter, createes
	 * a JbeamServer instance for persistance and sends it for insertion.
	 */
	private function addToServer(values:Array):void	{
    	var server:JbeamServer = new JbeamServer();
    	server.serverName = values[0];
    	server.ipAddress = values[1];
    	server.port = values[2];
	    if(server.serverName != ''){
    		addServerToDatabase(server);
    	}else{
    		return;
    	}
	}