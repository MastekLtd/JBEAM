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
 * @author Pankaj.Ingle
 *
 * $Revision:: 3                 $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/proxy/MenuDetailsProxy.as 3     12/12/09 5:54p Y                         $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/proxy/MenuDetailsProxy.as                                                 $
 * 
 * 
 */
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.model.delegate.MenuWsDelegate;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	
	/**
	 * The proxy class for the MenuDetails value object.
	 * 
	 * Encapsulates all the logic for processing and dealing with
	 * the MenuDetails class returned by the data provider.	 
	 */
	public class MenuDetailsProxy extends BaseProxy
	{
		
		public function MenuDetailsProxy(data:Object=null)
		{
			super(NAME, data);
		}
		
		/** Name of the proxy */
		public static const NAME:String = 'MENU_DETAILS_PROXY';
		
		/** Notification constant indicates successful retrieval of menu details */
		public static const MENU_DETAILS_SERIVCE_SUCCEEDED:String =
			"MENU_DETAILS_SERIVCE_SUCCEEDED";
		
		/** Notification constant indicates unsuccessful retrieval of menu details */
		public static const MENU_DETAILS_SERIVCE_FAILED:String =
			"MENU_DETAILS_SERIVCE_FAILED";
			
		/**
		 * Function which will retrieve the menu details
		 */
		public function getMenuDetails(userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getMenuResultHandler, getMenuFaultHandler);
			var delegate:MenuWsDelegate = new MenuWsDelegate();
			delegate.getMenuDetails(userProfile, [responder], tokenData);
		}
		
		private function getMenuResultHandler(evt:ResultEvent):void
		{
			sendNotification(MENU_DETAILS_SERIVCE_SUCCEEDED, evt.result);
		}
		
		private function getMenuFaultHandler(evt:FaultEvent):void
		{
			sendNotification(MENU_DETAILS_SERIVCE_FAILED, evt.fault);
		}

		
		
		
		
	}
}