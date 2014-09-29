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
 */
package com.majescomastek.jbeam.common
{
	import flash.display.Sprite;
	
	import mx.controls.Alert;
	
	/**
	 * This class provides further customization to display an alert.
	 * Multilingual feature has been implemented while providing the custom labels
	 * to the labels.
	 */   
	public class AlertBuilder
	{
		private var _message:String;
		private var _title:String;
		private var _closeHandler:Function = null;
		private var _flags:uint = 0x4;
		private var _defaultButtonFlag:uint =0x4;
		private var _parent:Sprite = null;
		private var _iconClass:Class = null;
		private var _noLabel:String = null;
		private var _yesLabel:String = null;
		private var _cancelLabel:String = null;
		private var _okLabel:String = null;
		
		public function AlertBuilder()
		{
			// TODO: add 'no', 'yes' to default properties file
			// setDefaultLabels();
		}
		
		/**
		 * Creates and returns instance of this class.
		 */		
		public static function getInstance():AlertBuilder
		{
			return new AlertBuilder();
		}		
		
		/**
		 * Function which displays the alert.
		 */
		public function show(message:String, title:String = null):Alert
		{
			//alterLabels();
			return Alert.show(message, title, _flags, _parent, _closeHandler, _iconClass, _defaultButtonFlag);
		}
		
		/**
		 * Function used to set the 'No' label.
		 */
		public function setNoLabel(value:String):AlertBuilder
		{
			_noLabel = value;
			return this;
		}
		
		/**
		 * Function used to set the 'Yes' label.
		 */
		public function setYesLabel(value:String):AlertBuilder
		{
			_yesLabel = value;
			return this;
		}
		/**
		 * Function used to set the 'Cancel' label.
		 */
		public function setCancelLabel(value:String):AlertBuilder
		{
			this._cancelLabel = value;
			return this;
		}
		
		/**
		 * Function used to set the 'Ok' label.
		 */
		public function setOkLabel(value:String):AlertBuilder
		{
			this._okLabel = value;
			return this;
		}
		
		/**
		 * Function used to set the close handler.
		 */
		public function setCloseHandler(value:Function):AlertBuilder
		{
			this._closeHandler = value; 
			return this;
		}
		
		/**
		 * Function used to set the default button flag.
		 */
		public function setDefaultButtonFlag(value:uint):AlertBuilder
		{
			this._defaultButtonFlag = value;
			return this;
		}
		
		/**
		 * Function used to set the flags.
		 */
		public function setFlags(value:uint):AlertBuilder
		{
			this._flags = value;
			return this;
		}
		
		/**
		 * Function used to set the parent.
		 */
		public function setParent(value:Sprite):AlertBuilder
		{
			_parent = value;
			return this;
		}
		
		/**
		 * Function overrides the default labels with 
		 * the label that has been set by the user.
		 */
		private function alterLabels():void
		{
			Alert.noLabel = _noLabel;
			Alert.yesLabel = _yesLabel;
			Alert.cancelLabel = _cancelLabel;
			Alert.okLabel = _okLabel;
		}
		
		/**
		 * Function sets the default labels.
		 */
		private function setDefaultLabels():void
		{
			_noLabel = ResourceUtils.getString('BillingUI','no');
			_yesLabel = ResourceUtils.getString('BillingUI','yes');
			_cancelLabel = ResourceUtils.getString('BillingUI','cancel');
			_okLabel = ResourceUtils.getString('BillingUI','ok');
		}

	}
}