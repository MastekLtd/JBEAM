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
 * @author sanjayts
 *
 * $Revision:: 6                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/proxy/CommonProxy.as  $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/proxy/CommonProxy.as  $
 * 
 * 6     3/19/10 3:17p Shrinivas
 * retriveParticularDropDownEntires() function is added to get the particluar drop down entries
 * 
 * 5     2/24/10 5:19p Sanjay.sharma
 * Added code for datagrid preference functionality
 * 
 * 4     1/04/10 6:52p Sanjay.sharma
 * updated proxy
 * 
 * 3     12/07/09 7:59p Sanjay.sharma
 * updated proxy
 * 
 * 2     12/07/09 2:00p Sanjay.sharma
 * updated common proxy
 * 
 * 1     12/07/09 1:44p Sanjay.sharma
 * added new proxy class for the common services.
 * 
 * 
 */
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.model.delegate.common.CommonWSDelegate;
	import com.majescomastek.jbeam.model.vo.ConfigParameter;
	import com.majescomastek.jbeam.model.vo.DatagridPreference;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * The Proxy class for the Common services.
	 */
	public class CommonProxy extends BaseProxy
	{
		/** The name of this proxy class. */
		public static const NAME:String = 'COMMON_PROXY';

		/** The notification which indicates the success of the `retrieveDropDownEntries' operation */		
		public static const RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED:String =
			"RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED";
		
		/** The notification which indicates the failure of the `retrieveDropDownEntries' operation */
		public static const RETRIEVE_DROP_DOWN_ENTRIES_FAILED:String =
			"RETRIEVE_DROP_DOWN_ENTRIES_FAILED";
		
		/** The notification which indicates the success of the `retrieveSystemDate' operation */		
		public static const RETRIEVE_SYSTEM_DATE_SUCCEEDED:String =
			"RETRIEVE_SYSTEM_DATE_SUCCEEDED";
		
		/** The notification which indicates the failure of the `retrieveSystemDate' operation */
		public static const RETRIEVE_SYSTEM_DATE_FAILED:String =
			"RETRIEVE_SYSTEM_DATE_FAILED";

		/** The notification which indicates the success of the `getUserDepartments' operation */		
		public static const GET_USER_DEPARTMENTS_SUCCEEDED:String =
			"GET_USER_DEPARTMENTS_SUCCEEDED";
		
		/** The notification which indicates the failure of the `getUserDepartments' operation */
		public static const GET_USER_DEPARTMENTS_FAILED:String =
			"GET_USER_DEPARTMENTS_FAILED";
		
		/** The notification which indicates the success of the `retrieveDatagridPreference' operation */		
		public static const RETRIEVE_DATAGRID_PREFERENCE_SUCCEEDED:String =
			"RETRIEVE_DATAGRID_PREFERENCE_SUCCEEDED";
		
		/** The notification which indicates the failure of the `retrieveDatagridPreference' operation */
		public static const RETRIEVE_DATAGRID_PREFERENCE_FAILED:String =
			"RETRIEVE_DATAGRID_PREFERENCE_FAILED";
		
		/** The notification which indicates the success of the `saveDatagridPreference' operation */		
		public static const SAVE_DATAGRID_PREFERENCE_SUCCEEDED:String =
			"SAVE_DATAGRID_PREFERENCE_SUCCEEDED";
		
		/** The notification which indicates the failure of the `saveDatagridPreference' operation */
		public static const SAVE_DATAGRID_PREFERENCE_FAILED:String =
			"SAVE_DATAGRID_PREFERENCE_FAILED";
			
		/**
		 * Constructor the Proxy with name as <code>NAME</code> and the initial data
		 * passed in.
		 */
		public function CommonProxy(data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
	 	 * Retrieve the list of Drop down entries based on the Drop down identifier
	 	 * list passed in.
		 */
		public function retrieveDropDownEntries(keys:ArrayCollection):void
		{
			var configParameterLst:ArrayCollection = createRetrieveDropDownEntriesRequestObject(keys);
			retrieveEntries(configParameterLst);
		}
		
		/**
		 * Retrive the list of particular drop down entires based on the Drop down identifier
	 	 * list passed in but here combination master code with sub code, charValue,etc is excpted.
		 */ 
		public function retriveParticularDropDownEntires(keys:ArrayCollection):void
		{
			retrieveEntries(keys);
		}
		
		/**
		 * This function creates the ConfigParameter collection for the remote object.
		 */ 
		private function createRetrieveDropDownEntriesRequestObject(keys:ArrayCollection):ArrayCollection
		{
			var configParameterLst:ArrayCollection = new ArrayCollection();
			for(var i:uint = 0; i< keys.length; i++)
			{
				var key:String = String(keys.getItemAt(i));
				var configParameter:ConfigParameter = new ConfigParameter();
				configParameter.masterCode = key;
				configParameterLst.addItem(configParameter); 
			}
			return configParameterLst;
		}
		
		/**
		 * Invoke the delegate to retrieve the drop down options.
		 */
		private function retrieveEntries(keys:ArrayCollection, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(retrieveDropDownEntriesResultHandler, retrieveDropDownEntriesFaultHandler);
			var delegate:CommonWSDelegate = new CommonWSDelegate();
			delegate.retrieveDropDownEntries(keys, [responder], tokenData);
		}
		
		private function retrieveDropDownEntriesResultHandler(event:ResultEvent):void
		{
			sendNotification(RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED, event.result);
		}

		private function retrieveDropDownEntriesFaultHandler(event:FaultEvent):void
		{
			sendNotification(RETRIEVE_DROP_DOWN_ENTRIES_FAILED, event.fault);
		}
		
		/**
		 * Retrieve the system/application date for the given application in
	 	 * mm/dd/yyyy format.
	 	 */
		public function retrieveSystemDate(tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(retrieveSystemDateResultHandler, retrieveSystemDateFaultHandler);
			var delegate:CommonWSDelegate = new CommonWSDelegate();
//			delegate.retrieveSystemDate([responder], tokenData);
		}
		
		private function retrieveSystemDateResultHandler(event:ResultEvent):void
		{
			sendNotification(RETRIEVE_SYSTEM_DATE_SUCCEEDED, event.result);
		}

		private function retrieveSystemDateFaultHandler(event:FaultEvent):void
		{
			sendNotification(RETRIEVE_SYSTEM_DATE_FAILED, event.fault);
		}
		
		/**
		 * Retrieve the list of departments for the passed in user details.
		 */
		public function getUserDepartments(userProfile:UserProfile, responder:IResponder=null):void
		{
			var delegate:CommonWSDelegate = new CommonWSDelegate();
			
			delegate.getUserDepartments(userProfile);
		}
		
		private function getUserDepartmentsResultHandler(event:ResultEvent):void
		{
			sendNotification(GET_USER_DEPARTMENTS_SUCCEEDED, event.result);
		}

		private function getUserDepartmentsFaultHandler(event:FaultEvent):void
		{
			sendNotification(GET_USER_DEPARTMENTS_FAILED, event.fault);
		}
		
		/**
		 * Invoke the <code>retrieveDatagridPreference</code> function of the delegate
		 * class and register the Result and Fault handlers to deal with the webservice
		 * result and fault events respectively.
		 */
		public function retrieveDatagridPreference(datagridPreference:DatagridPreference):void
		{
			var delegate:CommonWSDelegate = new CommonWSDelegate();
//			setFaultHandler(retrieveDatagridPreferenceFaultHandler);
//			setResultHandler(retrieveDatagridPreferenceResultHandler);
			delegate.retrieveDatagridPreference(datagridPreference);
		}
		
		/**
		 * The fault handler for the `retrieveDatagridPreference' operation.
		 */
		private function retrieveDatagridPreferenceFaultHandler(event:FaultEvent):void
		{
			sendNotification(RETRIEVE_DATAGRID_PREFERENCE_FAILED, event.fault);
		}

		/**
		 * The result handler for the `retrieveDatagridPreference' operation.
		 */
		private function retrieveDatagridPreferenceResultHandler(event:ResultEvent):void
		{
			sendNotification(RETRIEVE_DATAGRID_PREFERENCE_SUCCEEDED, event.result);
		}
		
		/**
		 * Invoke the <code>saveDatagridPreference</code> function of the delegate
		 * class and register the Result and Fault handlers to deal with the webservice
		 * result and fault events respectively.
		 */
		public function saveDatagridPreference(datagridPreference:DatagridPreference):void
		{
			var delegate:CommonWSDelegate = new CommonWSDelegate();
//			setFaultHandler(saveDatagridPreferenceFaultHandler);
//			setResultHandler(saveDatagridPreferenceResultHandler);
			delegate.saveDatagridPreference(datagridPreference);
		}
		
		/**
		 * The fault handler for the `saveDatagridPreference' operation.
		 */
		private function saveDatagridPreferenceFaultHandler(event:FaultEvent):void
		{
			sendNotification(SAVE_DATAGRID_PREFERENCE_FAILED, event.fault);
		}
		
		/**
		 * The result handler for the `saveDatagridPreference' operation.
		 */
		private function saveDatagridPreferenceResultHandler(event:ResultEvent):void
		{
			sendNotification(SAVE_DATAGRID_PREFERENCE_SUCCEEDED, event.result);
		}

	}
}