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
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.common.events.DaoFaultEvent;
	import com.majescomastek.common.events.DaoResultEvent;
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.model.dao.JbeamDao;
	import com.majescomastek.jbeam.model.delegate.JbeamSoapWsDelegate;
	import com.majescomastek.jbeam.model.vo.JbeamServer;
	import com.majescomastek.jbeam.model.vo.ReqVersion;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	/**
	 * The proxy class which satisfies the data needs of the Shell module.
	 */
	public class ShellProxy extends BaseProxy
	{
		private  var image_path:String = null;
		private  var defaultImage:String = null;
		
		/** The name of this proxy */
		public static const NAME:String = "SHELL_PROXY";
		
		/** The notification constant used to denote the success of the addServerToDatabase operation */
		public static const ADD_SERVER_TO_DATABASE_SUCCEEDED:String = "ADD_SERVER_TO_DATABASE_SUCCEEDED";
		
		/** The notification constant used to denote the failure of the addServerToDatabase operation */
		public static const ADD_SERVER_TO_DATABASE_FAILED:String = "ADD_SERVER_TO_DATABASE_FAILED";
		
		/** The notification constant used to denote the success of the updateServerToDatabase operation */
		public static const UPDATE_SERVER_TO_DATABASE_SUCCEEDED:String = "UPDATE_SERVER_TO_DATABASE_SUCCEEDED";
		
		/** The notification constant used to denote the failure of the updateServerToDatabase operation */
		public static const UPDATE_SERVER_TO_DATABASE_FAILED:String = "UPDATE_SERVER_TO_DATABASE_FAILED";

		/** The notification constant used to denote the success of the getAllServerConfigurations operation */
		public static const GET_ALL_SERVER_CONFIGURATIONS_SUCCEEDED:String =
			"GET_ALL_SERVER_CONFIGURATIONS_SUCCEEDED";
		
		/** The notification constant used to denote the failure of the getAllServerConfigurations operation */
		public static const GET_ALL_SERVER_CONFIGURATIONS_FAILED:String =
			"GET_ALL_SERVER_CONFIGURATIONS_FAILED";

		/** The notification constant used to denote the success of the server configuration removal */		
		public static const REMOVE_SERVER_CONFIGURATION_SUCCEEDED:String = "REMOVE_SERVER_CONFIGURATION_SUCCEEDED";
		
		/** The notification constant used to denote the failure of the server configuration removal */
		public static const REMOVE_SERVER_CONFIGURATION_FAILED:String = "REMOVE_SERVER_CONFIGURATION_FAILED";
		
		/** The notification constant used to denote the success of the server configuration removal */		
		public static const REMOVE_ALL_SERVER_CONFIGURATION_SUCCEEDED:String = "REMOVE_ALL_SERVER_CONFIGURATION_SUCCEEDED";
		
		/** The notification constant used to denote the failure of the server configuration removal */
		public static const REMOVE_ALL_SERVER_CONFIGURATION_FAILED:String = "REMOVE_ALL_SERVER_CONFIGURATION_FAILED";

		/** Notification constant indicates failure of UserAuthentication service */
		public static const USER_AUTHETICATION_SERVICE_FAILED:String = 'USER_AUTHETICATION_SERVICE_FAILED';
		
		/** Notification constant indicates success of UserAuthentication service */
		public static const USER_AUTHETICATION_SERVICE_SUCCEEDED:String = 'USER_AUTHETICATION_SERVICE_SUCCEEDED';
		
		/** Notification constant indicates failure of CheckCompatibility service */
		public static const CHECK_COMPATIBILITY_SERVICE_FAILED:String = 'CHECK_COMPATIBILITY_SERVICE_FAILED';
		
		/** Notification constant indicates success of CheckCompatibility service */
		public static const CHECK_COMPATIBILITY_SERVICE_SUCCEEDED:String = 'CHECK_COMPATIBILITY_SERVICE_SUCCEEDED';
		
		/** Notification constant indicates failure of CheckCompatibility service */
		public static const LOAD_COMPANY_LOGO_SERVICE_FAILED:String = 'LOAD_COMPANY_LOGO_SERVICE_FAILED';
		
		/** Notification constant indicates success of CheckCompatibility service */
		public static const LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED:String = 'LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED';
		
		/**
		 * Create a new ShellProxy object.
		 */
		public function ShellProxy(data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Persist a given server configuration.
		 */
		public function addServerToDatabase(server:JbeamServer):void
		{
			var jbeamDao:JbeamDao = new JbeamDao();
			jbeamDao.addServer(server, addServerToDatabaseResultHandler, addServerToDatabaseFaultHandler);
		}
		
		private function addServerToDatabaseResultHandler(resultEvent:DaoResultEvent):void
		{
			sendNotification(ADD_SERVER_TO_DATABASE_SUCCEEDED);			
		}
		
		private function addServerToDatabaseFaultHandler(faultEvent:DaoFaultEvent):void
		{
			sendNotification(ADD_SERVER_TO_DATABASE_FAILED, faultEvent.fault);									
		}
		/**
		 * Update a given server configuration.
		 */
		public function updateServerToDatabase(server:JbeamServer):void
		{
			var jbeamDao:JbeamDao = new JbeamDao();
			jbeamDao.updateServer(server, updateServerToDatabaseResultHandler, updateServerToDatabaseFaultHandler);
		}
		
		private function updateServerToDatabaseResultHandler(resultEvent:DaoResultEvent):void
		{
			sendNotification(UPDATE_SERVER_TO_DATABASE_SUCCEEDED);			
		}
		
		private function updateServerToDatabaseFaultHandler(faultEvent:DaoFaultEvent):void
		{
			sendNotification(UPDATE_SERVER_TO_DATABASE_FAILED, faultEvent.fault);									
		}
				
		/**
		 * Retrieve all the persisted server configurations.
		 */
		public function getAllServerConfigurations():void
		{
			var jbeamDao:JbeamDao = new JbeamDao();
			jbeamDao.getAllServerConfigurations(getAllServerConfigurationsResultHandler,
				getAllServerConfigurationsFaultHandler);
		}
		
		private function getAllServerConfigurationsResultHandler(resultEvent:DaoResultEvent):void
		{
			sendNotification(GET_ALL_SERVER_CONFIGURATIONS_SUCCEEDED, resultEvent.result);			
		}
		
		private function getAllServerConfigurationsFaultHandler(faultEvent:DaoFaultEvent):void
		{
			sendNotification(GET_ALL_SERVER_CONFIGURATIONS_FAILED, faultEvent.fault);									
		}
		
		/**
		 * Remove a server configuration from the database.
		 */
		public function removeServerConfiguration(server:JbeamServer):void
		{
			var jbeamDao:JbeamDao = new JbeamDao();
			jbeamDao.removeServerConfiguration(server, removeServerConfigurationResultHandler,
				removeServerConfigurationFaultHandler);
		}

		private function removeServerConfigurationResultHandler(resultEvent:DaoResultEvent):void
		{
			sendNotification(REMOVE_SERVER_CONFIGURATION_SUCCEEDED);			
		}
		
		private function removeServerConfigurationFaultHandler(faultEvent:DaoFaultEvent):void
		{
			sendNotification(REMOVE_SERVER_CONFIGURATION_FAILED, faultEvent.fault);
		}
		
		/**
		 * Remove a server configuration from the database.
		 */
		public function removeAllConfiguredServers():void
		{
			var jbeamDao:JbeamDao = new JbeamDao();
			jbeamDao.removeAllConfiguredServers(removeAllServerConfigurationResultHandler,
				removeAllServerConfigurationFaultHandler);
		}

		private function removeAllServerConfigurationResultHandler(resultEvent:DaoResultEvent):void
		{
			sendNotification(REMOVE_ALL_SERVER_CONFIGURATION_SUCCEEDED);			
		}
		
		private function removeAllServerConfigurationFaultHandler(faultEvent:DaoFaultEvent):void
		{
			sendNotification(REMOVE_ALL_SERVER_CONFIGURATION_FAILED, faultEvent.fault);
		}
		
		/**
		 * Authenticate the passed in user.
		 */
		public function userAuthentication(userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(userAuthenticationResultHandler, userAuthenticationFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.userAuthentication(userProfile, [responder], tokenData);
		}
		
		private function userAuthenticationResultHandler(evt:ResultEvent):void
		{
			sendNotification(USER_AUTHETICATION_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function userAuthenticationFaultHandler(evt:FaultEvent):void
		{
			sendNotification(USER_AUTHETICATION_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Check compatibility with the monitor services 
		 */
		public function checkCompatibility(reqVersion:ReqVersion, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(checkCompatibilityResultHandler, checkCompatibilityFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.checkCompatibility(reqVersion, [responder], tokenData);
		}
		
		private function checkCompatibilityResultHandler(evt:ResultEvent):void
		{
			sendNotification(CHECK_COMPATIBILITY_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function checkCompatibilityFaultHandler(evt:FaultEvent):void
		{
			sendNotification(CHECK_COMPATIBILITY_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Load images 
		 */
		public function loadCompanyLogo(defaultImageParam:String):void
		{
			var loader:URLLoader;
			var request:URLRequest; 
			image_path = CommonConstants.SERVER_URL_IM + "/images/client_logo1.jpg";
			trace("In loadCompanyLogo >> "+ image_path);
			defaultImage = defaultImageParam;
			loader = new URLLoader();
			loader.addEventListener(Event.COMPLETE,onComplete);
			loader.addEventListener(IOErrorEvent.IO_ERROR,onError);		
			request = new URLRequest(image_path); 
			loader.load(request);
		}
		
		private function onError(event:IOErrorEvent):void
		{
			trace("Image Loading Failed");
			sendNotification(LOAD_COMPANY_LOGO_SERVICE_FAILED, defaultImage);
//			trace("In ioError_handler >> default image set as "+defaultImage);
		}
		
		private  function onComplete(event:Event):void
		{
			trace("Image Loading Completed");
			sendNotification(LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED, image_path);
//			trace("In onComplete >> image path set as "+image_path);
//			CommonConstants.COMPANY_LOGO_SMALL = image_path;
		}
		
	}
}