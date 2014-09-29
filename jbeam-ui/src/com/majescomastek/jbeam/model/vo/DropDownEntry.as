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
	
	[Bindable]
	public class DropDownEntry extends BaseValueObject
	{
		private var _dropDownKey:String;
		private var _dropDownValue:ArrayCollection;
		
		public function DropDownEntry()
		{
			super();
		}
		
		public function get dropDownKey():String 
		{
      		 return this._dropDownKey;
     	}
     
     	public function set dropDownKey(value:String):void 
     	{
       		this._dropDownKey = value; 
     	}
		
		public function get dropDownValue():ArrayCollection
		{
      		 return this._dropDownValue;
     	}
     
     	public function set dropDownValue(value:ArrayCollection):void 
     	{
       		this._dropDownValue = value; 
     	}
 	}
}