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
	/**
	 * The class used for holding progress level data.
	 * 
	 * TODO: Generate getters/setters.
	 */
	public class ProgressLevelData extends BaseValueObject
	{
		public function ProgressLevelData()
		{
			super();
		}
		
		public var batchNo:Number;
		
		public var batchRevNo:Number;
		
		public var endDatetime:String;
		
		public var failedOver:String;
		
		public var indicatorNo:Number;
		
		public var installationCode:String;
		
		public var prgActivityType:String;
		
		public var startDatetime:String;
		
		public var status:String;
		
		public var cycleNo:String;
		
		public var prgLevelType:String;
		
		public var serialNo:Number;

		public var timeTaken:Number;
	}
}