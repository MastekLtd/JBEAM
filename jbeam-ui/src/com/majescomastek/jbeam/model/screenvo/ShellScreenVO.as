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
package com.majescomastek.jbeam.model.screenvo
{
	[Bindable]
	public class ShellScreenVO
	{
		public function ShellScreenVO()
		{
			
		}
		private var _label:String;
		private var _code:String;
		private var _data:String;
		private var _source:String;
		
		public function set label(val:String):void{
			this._label = val;
		}
		public function get label():String{
			return this._label;
		}
		public function set code(val:String):void{
			this._code = val;
		}
		public function get code():String{
			return this._code;
		}
		public function set data(val:String):void{
			this._data = val;
		}
		public function get data():String{
			return this._data;
		}
		public function set source(val:String):void{
			this._source = val;
		}
		public function get source():String{
			return this._source;
		}
	
	}
}