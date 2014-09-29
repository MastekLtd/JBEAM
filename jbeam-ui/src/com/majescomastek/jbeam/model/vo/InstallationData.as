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
package com.majescomastek.jbeam.model.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The class used for holding installation data.
	 * 
	 * TODO: Generate getters/setters.
	 */
	[Bindable]
	public class InstallationData extends BaseValueObject
	{
		public function InstallationData()
		{
			super();
		}
		
		public var batchNo:Number;
		
		public var batchRevNo:Number;
		
		public var batchEndReason:String;
		
		public var batchEndTime:Date;
		
		public var batchStartTime:Date;
		
		public var installationCode:String;
		
		public var timezoneId:String;
		
		public var timezoneOffset:Number;
		
		public var timezoneShortName:String;
		
		public var totalFailedObjects:Number;
		
		public var totalObjects:Number;
		
		public var entityList:ArrayCollection = new ArrayCollection();
		
		public var progressLevelDataList:ArrayCollection = new ArrayCollection();
		
		// The status of the batch, CLOSURE, EXECUTION, SCHEDULING etc.
		// This is derived from progress level data but it should have been
		// a returned by the web service
		public var batchStatus:String;
		
		//The details of a run batch request
		//It contains the installation code and the instruction sequence number. 
		public var runBatchDetails:BatchDetailsData;
	}
}