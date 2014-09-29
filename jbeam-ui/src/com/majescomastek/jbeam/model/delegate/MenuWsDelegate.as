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
package com.majescomastek.jbeam.model.delegate
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.model.vo.MenuDetails;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.ObjectProxy;
	
	/**
	 * The delegate class for the JBEAM Batch details Menu used for invoking
	 * SOAP based webservices..
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class MenuWsDelegate extends BaseSoapWsDelegate
	{
		public function MenuWsDelegate()
		{			
			super();
		}
		
		/**
		 * Retrieve the completed batch search result list for provided batch data.
		 */
		public function getMenuDetails(userProfile:UserProfile,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getMenuDetailsResultHandler, defaultFaultHandler);
			invoke("getMenuDetails", getInputForGetMenuDetails(userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetMenuDetails(userProfile:UserProfile):XML
		{
			var parentXml:XML = 
				<s:getMenuDetails xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
			         <userProfile>						
			            <installationCode>{userProfile.installationCode}</installationCode>
			            <userId>{userProfile.userId}</userId>
			         </userProfile>
			      </s:getMenuDetails>;					
			return parentXml;
		}
		
		
		
		private function getMenuDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_MENU_DETAILS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedGetMenuDetailsResult:Object = evt.result;
				var menuDetailsResultDataList:ArrayCollection = retrievedGetMenuDetailsResult.menuList;
				var instCode:String = retrievedGetMenuDetailsResult.installationCode;
				var menuDetailsList:ArrayCollection = new ArrayCollection();
				var menuServiceResult:Object = new Object();
				if(menuDetailsResultDataList != null && 
							menuDetailsResultDataList.length > 0)
				{
					for(var i:uint = 0; i < menuDetailsResultDataList.length; ++i)
					{
						var retrievedGetMenuDetailsResultData:Object = 
								menuDetailsResultDataList.getItemAt(i);
						var menuDetailsResultData:MenuDetails = 
								createGetMenuDetailsResultData(retrievedGetMenuDetailsResultData);
						var retrievedChildrenList:ArrayCollection = 
								retrievedGetMenuDetailsResultData.children;
								
						for(var j:uint = 0; j < retrievedChildrenList.length; ++j)
						{
							var retrievedChildren:ObjectProxy = retrievedChildrenList[j];
							var childrenData:MenuDetails =
								createChildrenData(retrievedChildren);
							childrenData.functionId = i + j + 1;
							if(menuDetailsResultData.children == null)
							{
								menuDetailsResultData.children = new ArrayCollection();
							}
							menuDetailsResultData.children.addItem(childrenData);
						}
						menuDetailsList.addItem(menuDetailsResultData);
					}
					menuServiceResult['menuDetailsList'] = menuDetailsList;
					menuServiceResult['instCode'] = instCode;
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(menuServiceResult, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a MenuDetails object based on the data returned by the webservice.
		 */
		private function createGetMenuDetailsResultData(retrievedGetMenuDetailsResultData:Object):MenuDetails
		{
			var menuDetailsResultData:MenuDetails = new MenuDetails();
			menuDetailsResultData.functionName = retrievedGetMenuDetailsResultData['parentFunctionId'];
			return menuDetailsResultData;
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
	}
}