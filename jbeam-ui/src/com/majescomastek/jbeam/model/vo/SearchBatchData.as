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
	 * The class used for holding batch details data.
	 * 
	 * TODO: Generate getters/setters.
	 */
	[Bindable]
	public class SearchBatchData extends BaseValueObject
	{
		public function SearchBatchData()
		{
			super();
		}
		
		public var batchNo:Number;
		
		public var batchRevNo:Number;
		
		public var batchName:String;
		
		public var batchType:String;

		public var batchEndReason:String;
		
		public var execEndTime:String;
		
		public var execStartTime:String;
		
		public var installationCode:String;

		public var processId:Number;
	}
}