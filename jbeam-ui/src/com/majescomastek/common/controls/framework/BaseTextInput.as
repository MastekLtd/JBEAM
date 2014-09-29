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
 * @author sanjayts
 * 
 *
 * $Revision:: 4                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseTextI $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseTextI $
 * 
 * 4     3/10/10 2:28p Gourav.rai
 * Fixed import Statement
 * 
 * 3     3/10/10 11:50a Sanjay.sharma
 * Updated code to save the original tooltip before making changes
 * 
 * 2     3/10/10 9:51a Sanjay.sharma
 * Added the enableContentTooltip property
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 
 */
package com.majescomastek.common.controls.framework
{
	import flash.events.Event;
	
	import mx.controls.TextInput;

	/**
	 * The event fired off when the `enableContentTooltip` property is changed.
	 */
	[Event(name="enableContentTooltipChanged", type="flash.events.Event")]
	
	/**
	 * The base class for all TextInput components used in an application.
	 */
	public class BaseTextInput extends TextInput implements IBaseControl
	{
		public function BaseTextInput()
		{
			super();
		}
		
		/** The original value of the `toolTip` property */
		private var _originalTooltip:String;
		
		/** Enable the tooltip for the given text input based on the contents of the control */
		private var _enableContentTooltip:Boolean;
		
		/**
		 * Retrieve the `enableContentTooltip` property of this control.
		 */
		public function get enableContentTooltip():Boolean
		{
			return _enableContentTooltip;
		}
		
		/**
		 * Set the `enableContentTooltip` property to the passed in `value` and
		 * fire off the `enableContentTooltipChanged` event.
		 */ 
		public function set enableContentTooltip(value:Boolean):void
		{
			_enableContentTooltip = value;
			setContentTooltip();
			dispatchEvent(new Event("enableContentTooltipChanged"));
		}
		
		/**
		 * Set the value of the tooltip based on the value of the
		 * `enableContentTooltip` property.
		 */
		private function setContentTooltip():void
		{
			if(_enableContentTooltip)
			{
				_originalTooltip = toolTip;
				toolTip = this.text;
			}
			else
			{
				toolTip = _originalTooltip;
			}
		}
		
	}
}