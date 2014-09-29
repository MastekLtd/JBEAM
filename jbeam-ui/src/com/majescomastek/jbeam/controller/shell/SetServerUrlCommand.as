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
package com.majescomastek.jbeam.controller.shell
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.model.vo.JbeamServer;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class responsible for setting the webservice server url
	 * based on the selected server configuration by the user.
	 */
	public class SetServerUrlCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			var server:JbeamServer = JbeamServer(notification.getBody());
			CommonConstants.SERVER_URL = "http://" + server.ipAddress + ":" + server.port + "/MonitorServices";
		}
		
	}
}