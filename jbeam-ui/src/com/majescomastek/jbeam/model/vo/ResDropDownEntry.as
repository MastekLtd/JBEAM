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
	public class ResDropDownEntry extends BaseValueObject
	{
		private var _dropDownEntries:ArrayCollection;
		
		public function ResDropDownEntry()
		{
			super();
		}
		
		public function get dropDownEntries():ArrayCollection {
      		 return this._dropDownEntries;
     	}
     
     	public function set dropDownEntries(value:ArrayCollection):void {
       		this._dropDownEntries = value; 
     	}
		
	}
}