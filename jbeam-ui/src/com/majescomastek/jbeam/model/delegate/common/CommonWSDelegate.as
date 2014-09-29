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
 *
 * $Revision:: 4                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/delegate/common/Commo $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/delegate/common/Commo $
 * 
 * 4     2/24/10 5:20p Sanjay.sharma
 * Added code for datagrid preference functionality
 * 
 * 3     1/07/10 2:23p Gourav.rai
 * 
 * 2     1/04/10 6:52p Sanjay.sharma
 * updated delegate
 * 
 * 1     12/08/09 6:08p Sandeepa
 * 
 * 1     12/07/09 2:00p Sanjay.sharma
 * added teh common delegate
 * 
 * 
 */
package com.majescomastek.jbeam.model.delegate.common
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.model.delegate.BaseSoapWsDelegate;
	import com.majescomastek.jbeam.model.vo.ConfigParameter;
	import com.majescomastek.jbeam.model.vo.DatagridPreference;
	import com.majescomastek.jbeam.model.vo.DropDownEntry;
	import com.majescomastek.jbeam.model.vo.ExecuteReport;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.ObjectProxy;
	
	/**
	 * The delegate class for the Common methods used for invoking
	 * SOAP based webservices.
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class CommonWSDelegate extends BaseSoapWsDelegate
	{
		public function CommonWSDelegate()
		{
			super();
		}
		
		/**
		 * Retrieve the list of all the drop downs for the given keys.
		 */
		public function retrieveDropDownEntries(keys:ArrayCollection, externalResponders:Array,
			tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(retrieveDropDownEntriesResultHandler, defaultFaultHandler);
			invoke("retrieveDropDownEntries", getInputForRetrieveDropDownEntries(keys),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForRetrieveDropDownEntries(keys:ArrayCollection):XML
		{
			var parentXml:XML = 
					<s:retrieveDropDownEntries xmlns:s="http://services.server.ws.monitor.stgmastek.com/" >
				         
					</s:retrieveDropDownEntries>;
					
					for each(var configParams:ConfigParameter in keys)
					{
						parentXml.appendChild(createDropDownIdentifierList(configParams));
					} 
			return parentXml;
		}
		
		private function createDropDownIdentifierList(configParams:ConfigParameter):XML
		{
			var childXml:XML = 
				<DropDownIdentifierList>
	            	<masterCode>{configParams.masterCode}</masterCode>
	            </DropDownIdentifierList>;			
			return childXml;
		}
		
		private function retrieveDropDownEntriesResultHandler(evt:ResultEvent):void
		{
			// TODO: Change the service so that fault is thrown at the server side
			// instead of returning a response even when the ws fails. Also make
			// sure an appropriate fault message is provided by the service impl.
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.RETRIEVE_REPORTS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedDropDownEntries:ArrayCollection = evt.result.dropDownEntries;	
				var dropDownEntriesList:ArrayCollection = new ArrayCollection();
				if(retrievedDropDownEntries != null && retrievedDropDownEntries.length > 0)
				{
					for(var i:uint = 0; i < retrievedDropDownEntries.length; ++i)
					{
						var retrievedDropDownEntry:Object = retrievedDropDownEntries.getItemAt(i);
						var dropDownEntry:DropDownEntry = createDropDownData(retrievedDropDownEntry);
						dropDownEntriesList.addItem(dropDownEntry);
					}
				}			
//				// Create a new event containing the webservice result and invoke the
//				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(dropDownEntriesList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		/**
		 * Create a run batch request object based on the data returned by the webservice.
		 */
		private function createDropDownData(retrievedDropDownEntry:Object):DropDownEntry
		{
			var dropDownEntry:DropDownEntry = new DropDownEntry();
			dropDownEntry.dropDownKey = retrievedDropDownEntry['dropDownKey'];
			var retrievedConfigParams:ArrayCollection = retrievedDropDownEntry['dropDownValue'];
			var configParams:ArrayCollection = new ArrayCollection();
			for each(var obj:ObjectProxy in retrievedConfigParams)
			{
				configParams.addItem(createConfigParameters(obj));
			}
			dropDownEntry.dropDownValue = configParams;
			return dropDownEntry;
		}
		
		/**
		 * Create a run batch request object based on the data returned by the webservice.
		 */
		private function createConfigParameters(retrievedConfigParameter:Object):ConfigParameter
		{
			var configParameter:ConfigParameter = new ConfigParameter();
			configParameter.description = retrievedConfigParameter['description'];
			configParameter.subCode = retrievedConfigParameter['subCode'];
			return configParameter;
		}
		/**
		 * Retrieve the system/application date for the given application in
	 	 * mm/dd/yyyy format.
	 	 */
		public function retrieveSystemDate(externalResponders:Array,tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(retrieveSystemDateResultHandler, defaultFaultHandler);
			invoke("retrieveSystemDate", getInputForRetrieveSystemDate(),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForRetrieveSystemDate():XML
		{
			var parentXml:XML = <s:retrieveSystemDate xmlns:s="http://services.server.ws.monitor.stgmastek.com/" />;
			return parentXml;
		}
		
		private function retrieveSystemDateResultHandler(evt:ResultEvent):void
		{
			// TODO: Change the service so that fault is thrown at the server side
			// instead of returning a response even when the ws fails. Also make
			// sure an appropriate fault message is provided by the service impl.
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.RETRIEVE_SYSTEM_DATE_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var executeReport:ExecuteReport = new ExecuteReport();
				var retrievedReports:ObjectProxy = evt.result as ObjectProxy;				
				executeReport.systemDate = String(retrievedReports.systemDate);
//				// Create a new event containing the webservice result and invoke the
//				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(executeReport, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		/**
		 * Retrieve the list of departments for the passed in user details.
		 */
		public function getUserDepartments(userProfile:UserProfile):void
		{
			
//			var asyncToken:AsyncToken = remoteObject.getUserDepartments(userProfile);
//			asyncToken.addResponder(responder);
//			asyncToken.tokenData = tokenData;
		}
		
		/**
		 * Retrieve the datagrid preference for the passed in datagrid criteria.
		 */
		public function retrieveDatagridPreference(datagridPreference:DatagridPreference):void
		{
			
//			remoteObject.retrieveDatagridPreference(datagridPreference);
		}
		
		/**
		 * Save/update the passed in datagrid preference.
		 */
		public function saveDatagridPreference(datagridPreference:DatagridPreference):void
		{
			
//			remoteObject.saveDatagridPreference(datagridPreference);
		}

	}
}