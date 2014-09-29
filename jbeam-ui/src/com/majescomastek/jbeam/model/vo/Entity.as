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
	 * The class used for holding Entity related data.
	 * 
	 * TODO: Generate getters/setters.
	 */
	public class Entity extends BaseValueObject
	{
		public function Entity()
		{
			super();
		}
		
		public var entityName:String;
		
		public var lookupColumn:String;
		
		public var lookupValue:String;
		
		public var precedenceOrder:Number;
		
		public var valueColumn:String;
		
		public var description:String;
		
		public var numberOfRequiredParameters:String;

	}
}