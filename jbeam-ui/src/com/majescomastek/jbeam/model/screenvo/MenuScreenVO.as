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
	public class MenuScreenVO
	{
		public function MenuScreenVO()
		{
		}
		private var _children:Array;
		private var _sortingOrder:String;
		private var _menuType:String;
		private var _label:String;
		private var _asMenu:String;
		private var _sourceFile:String;
		private var _description:String;
		private var _menuId:String;
		private var _parentId:String;
		
		public function set children(obj:Array):void{
			this._children = obj;
		}
		public function get children():Array{
			return this._children;
		}
		public function set parentId(obj:String):void{
			this._parentId = obj;
		}
		public function get parentId():String{
			return this._parentId;
		}
		public function set label(obj:String):void{
			this._label = obj;
		}
		public function get label():String{
			return this._label;
		}
		public function set menuId(obj:String):void{
			this._menuId = obj;
		}
		public function get menuId():String{
			return this._menuId
		}
		
		public function set sortingOrder(obj:String):void{
			this._sortingOrder = obj;
		}
		public function get sortingOrder():String{
			return this._sortingOrder
		}
		public function set menuType(obj:String):void{
			this._menuType = obj;
		}
		public function get menuType():String{
			return this._menuType
		}
		
		
		public function set asMenu(obj:String):void{
			this._asMenu = obj;
		}
		public function get asMenu():String{
			return this._asMenu
		}
		public function set sourceFile(obj:String):void{
			this._sourceFile = obj;
		}
		public function get sourceFile():String{
			return this._sourceFile
		}
		public function set description(obj:String):void{
			this._description = obj;
		}
		public function get description():String{
			return this._description
		}
		
	}
}