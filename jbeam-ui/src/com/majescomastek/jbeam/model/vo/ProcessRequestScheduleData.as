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
	public class ProcessRequestScheduleData extends BaseValueObject
	{
		public function ProcessRequestScheduleData()
		{
			super();
		}
		
		public var checked:Boolean;
		
		public var installationCode:String;
		
		public var batchName:String;
		
		public var schId:Number;
				
		public var endReason:String;
		
		public var schStat:String;	
		
		public var freqType:String;

		public var recur:Number;
		
		public var onWeekDay:String;
		
		public var skipFlag:String;

		public var keepAlive:String;
		
		public var endOccur:Number;
		
		public var endDt:Date;
		
		public var startDt:Date;

		public var occurCounter:Number;
		
		public var userId:String;		
		

	}
}