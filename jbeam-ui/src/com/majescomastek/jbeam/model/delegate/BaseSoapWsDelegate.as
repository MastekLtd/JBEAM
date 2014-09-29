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
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.CommonUtils;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.soap.SOAPFault;
	import mx.rpc.soap.mxml.Operation;
	import mx.rpc.soap.mxml.WebService;
	
	/**
	 * The base class for all the delegates which would be used for
	 * retrieving the server side data. This class assumes that SOAP
	 * based web services are used.
	 * 
	 * If needed, common parts can be fleshed out of this class to create
	 * a BaseDelegate class which again can be subclassed for specifically
	 * dealing with SOAP based web services.
	 * 
	 * Here we are adding two levels of responders since it won't be a 
	 * good design decision to let the proxy responder or any externally
	 * attached responder deal with the logic of interpreting the result
	 * and converting it to a standard representation.
	 * 
	 * TODO:
	 * - Add caching for Operation objects since both WebService and
	 * Operation objects are multi-invocation safe.
	 * - Use blazeds instead of directly invoking webservices to avoid
	 * the overhead of creating value objects.
	 */
	public class BaseSoapWsDelegate
	{
		/*private static var _webService:WebService;*/
		private var _webService:WebService;
		
		private var _wsdlPath:String;
		
		/** The name of the wsdl used by this delegate */
		public static const WSDL_NAME:String = "?wsdl";
		
		public function BaseSoapWsDelegate(wsdlPath:String=null)
		{
			super();
			if(wsdlPath == null)
			{
				wsdlPath = CommonUtils.getServerUrl() + WSDL_NAME;
			}
			this.wsdlPath = wsdlPath;
		}
		
		public function set wsdlPath(wsdlPath:String):void
		{
			_wsdlPath = wsdlPath;
			initialize();
		}
		
		public function get wsdlPath():String
		{
			return _wsdlPath;
		}

		protected function initialize():void
		{
			_webService = new WebService();
			trace(_wsdlPath);
			_webService.loadWSDL(_wsdlPath);
			_webService.endpointURI = CommonUtils.getServerUrl();
			trace(_webService.endpointURI + " # " + _webService.port + " # " + _webService.destination);
			_webService.addEventListener(FaultEvent.FAULT, defaultFault); 
		}
		
		/**
		 * Invoke a webservice operation.
		 * 
		 * @param operationName The name of the operation to be invoked as mentioned in WSDL
		 * @param requestData The data to be passed in as request in xml format, as per the WSDL
		 * @param externalResponders The responder array which would handle the webservice result/fault
		 * @param internalResponder The internal responder used for handling result and fault events
		 * 			and pre-processing them so that they can be readily consumed by the client with
		 * 			the client getting involved in parsing the webservice request/response
		 * @param tokenData The arbitrary data which can be attached to the async token returned when
		 * 		  a webservice call is made
		 * 
		 * <p>Note: Special care must be taken to ensure that the XML request is generated is a
		 * case-sensitive way i.e. <authenticationDetails> is different from
		 * <AuthenticationDetails> and depending on the way your WSDL is structured, one
		 * of them would be rejected by the Web Service and result in an error when invoked.
		 */ 
		protected function invoke(operationName:String, requestData:XML,
			externalResponders:Array, internalResponder:IResponder, tokenData:Object=null):void
		{
			var operation:Operation = Operation(_webService.getOperation(operationName));
			operation.request = requestData;
			operation.showBusyCursor = true;
			
			// send() is async call; retrieve the token returned and attach
			// responders/arbitrary data.
			var token:AsyncToken = operation.send();
			token.addResponder(internalResponder);
			token['tokenData'] = tokenData;
			token['externalResponders'] = externalResponders;
		}
		
		private function defaultFault(event:FaultEvent):void 
		{
		    // Handle service fault.
		    if (event.fault is SOAPFault) 
		    {
		         var fault:SOAPFault=event.fault as SOAPFault;
		         var faultElement:XML=fault.element;
		         // You could use E4X to traverse the raw fault element returned in the SOAP envelope.
		         // ...
		    }
		    else
		    {
		    	if(event.fault.faultCode == "Client.NoSuchMethod")
		    	{
				    AlertBuilder.getInstance().show(CommonConstants.CHECK_COMPATIBILITY_FAILED); 
//				    		+ "\n Send following message:"
//				    		+ "\n" + event.fault.faultString);
		    	}
		    	else if(event.fault.faultCode == "Server.Error.Request" || event.fault.faultString == "HTTP request error")
		    	{
				    AlertBuilder.getInstance().show(CommonConstants.SERVICE_BUS_DOWN);
		    	}
				else
				{
				    AlertBuilder.getInstance().show(event.fault.faultCode + " - " + event.fault.faultDetail);			
				}
		    }
		    
		}
		protected function attachResponders(token:AsyncToken, responders:Array):void
		{
			if(responders == null || responders.length == 0)
			{
				return;
			}
			
			var responderCollection:ArrayCollection = new ArrayCollection(responders);
			for each(var responder:IResponder in responderCollection)
			{
				token.addResponder(responder);
			}
		}
		
		/**
		 * The default implementation of a result handler for the ws delegate
		 * class. It simply loops over the external responders attached to the
		 * async token.
		 */
		protected function defaultResultHandler(evt:ResultEvent):void
		{
			var token:AsyncToken = evt.token;
			var responders:Array = token['externalResponders'] as Array;
			for each(var responder:IResponder in responders)
			{
				responder.result(evt);
			}
		}
		
		/**
		 * The default implementation of a fault handler for the ws delegate
		 * class. It simply loops over the external responders attached to the
		 * async token.
		 */
		protected function defaultFaultHandler(evt:FaultEvent):void
		{
			var token:AsyncToken = evt.token;
			var responders:Array = token['externalResponders'] as Array;
			for each(var responder:IResponder in responders)
			{
				responder.fault(evt);
			}
		}

	}
}