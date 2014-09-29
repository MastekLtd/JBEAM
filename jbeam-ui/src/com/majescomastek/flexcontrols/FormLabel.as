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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Mandar Vaidya
 * 
 *
 * $Revision::                                                                                                        $
 *	
 * $Header::  $	
 *
 * $Log:: $
 * 
 * 
 */

package com.majescomastek.flexcontrols
{
	import mx.controls.Text;
	import mx.utils.StringUtil;
	
	public class FormLabel extends Text
	{
		private var _appendText:String = ':'; //resourceManager.getString('BillingUI','colon');
		
		private var _mandatoryText:String = '*';
		
		private var _mandatory:Boolean = false;
		
		private var _appendTextChanged:Boolean = false;
		
		public function FormLabel()
		{
			super();
		}
		
		/**
		 * Over-ride the default implementation of the text method of the Text
		 * object to append text and add in prefix for manadtory text.
		 */
		 override public function set text(value:String):void {
			if(!value) return;

//			if(this._appendText != null && StringUtil.trim(this._appendText) != '')
//				value = value + this._appendText;

        	if(!this._appendTextChanged)
        	{
				if(this._appendText != null && StringUtil.trim(this._appendText) != '')
					value = value + this._appendText;
        	}

			if(this._mandatory)
				value = this._mandatoryText + value; 

			super.text = value;
			
        }
        
/*         override public function set text(value:String):void {
        	if(appendTextChanged)
        	{
        		super.text = value;
        	}
        	else
        	{
        		super.text = value + this._appendText;
        	}
        }
 */        
		public function set appendText(value:String):void {
			if(value == null) return;
			this._appendText = value;
			
			super.text = super.text + this._appendText;

//			var _text:String = text;
//
//			if(value == null) return;
//			this._appendText = value;
//			
//			if(this._appendText != null && StringUtil.trim(this._appendText) != '')
//				value = _text + this._appendText;
//
//			super.text = value;
		}
		 
		public function get appendText():String {
			return this._appendText;
		}
		
		public function set mandatory(value:Boolean):void {
			this._mandatory = value;
		}
		 
		public function get mandatory():Boolean {
			return this._mandatory;
		}

		public function set appendTextChanged(value:Boolean):void {
			this._appendTextChanged = value;
		}
		 
		public function get appendTextChanged():Boolean {
			return this._appendTextChanged;
		}
 	}
}