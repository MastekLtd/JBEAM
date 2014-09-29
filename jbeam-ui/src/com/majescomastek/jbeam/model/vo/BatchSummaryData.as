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
	 * The class used for holding batch summary data.
	 * 
	 * TODO: Generate getters/setters.
	 */
	[Bindable]
	public class BatchSummaryData extends BaseValueObject
	{
		public function BatchSummaryData()
		{
			super();
		}
		
		public var batchNo:Number;
		
		public var batchRevNo:Number;
		
		public var batchEndReason:String;
		
		public var execEndTime:Date;
		
		public var execStartTime:Date;
		
		public var installationCode:String;

		public var instructionSeqNo:String;
		
		public var batchStatus:String;		
		
		public var batchName:String;
		
		public var batchType:String;

		public var failedOver:String;

		public var processId:Number;
		
		public var instructionParametersList:ArrayCollection = new ArrayCollection();
		
	}
}