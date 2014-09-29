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
	[Bindable]
	public class ConfigParameter extends BaseValueObject
	{
		private var _masterCode:String;
		private var _subCode:String;
		private var _description:String;
		private var _numericValue:String;
		private var _charValue:String;
		private var _charValue2:String;
		private var _dateValue:String;
		private var _orderNo:String;
		private var _parentId:String;
		private var _excludeAllOption:String;
		
		public function ConfigParameter()
		{
			super();
		}
		
		public function get masterCode():String {
      		 return this._masterCode;
     	}
     
     	public function set masterCode(value:String):void {
       		this._masterCode = value; 
     	}
		
		public function get subCode():String {
      		 return this._subCode;
     	}
     
     	public function set subCode(value:String):void {
       		this._subCode = value; 
     	}
     	
     	public function get description():String {
      		 return this._description;
     	}
     
     	public function set description(value:String):void {
       		this._description = value; 
     	}
     	
     	public function get numericValue():String {
      		 return this._numericValue;
     	}
     
     	public function set numericValue(value:String):void {
       		this._numericValue = value; 
     	}
     	
     	public function get charValue():String {
      		 return this._charValue;
     	}
     
     	public function set charValue(value:String):void {
       		this._charValue = value; 
     	}
     	
     	public function get charValue2():String {
      		 return this._charValue2;
     	}
     
     	public function set charValue2(value:String):void {
       		this._charValue2 = value; 
     	}
     	
     	public function get dateValue():String {
      		 return this._dateValue;
     	}
     
     	public function set dateValue(value:String):void {
       		this._dateValue = value; 
     	}
     	
     	public function get orderNo():String {
      		 return this._orderNo;
     	}
     
     	public function set orderNo(value:String):void {
       		this._orderNo = value; 
     	}
     	
     	public function get parentId():String {
      		 return this._parentId;
     	}
     
     	public function set parentId(value:String):void {
       		this._parentId = value; 
     	}
     	
     	public function get excludeAllOption():String {
      		 return this._excludeAllOption;
     	}
     
     	public function set excludeAllOption(value:String):void {
       		this._excludeAllOption = value; 
     	}
	}
}