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
package com.majescomastek.flexcontrols.custom
{
	import com.majescomastek.util.CommonUtil;
	
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	
	import mx.controls.TextInput;
	
	public class ValidatorInput extends TextInput
	{
		public function ValidatorInput()
		{
			super();
			this.addEventListener(FocusEvent.FOCUS_IN, showValidationErrorMessage);
			this.addEventListener(FocusEvent.FOCUS_OUT, hideValidationErrorMessage);
		}

		/**
		 * The function to show error tooltip and set focus.
		 */
		public function showValidationErrorMessage(event:FocusEvent):void {
			
			var currentObject:Object = event.currentTarget;
			var parentObject:Object = currentObject.parent;
			if (currentObject.errorString != '') 
			{
				currentObject.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OUT, true, false,currentObject.x - 10,currentObject.y - 10));
		        currentObject.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OVER, true, false,currentObject.x + 10,currentObject.y + 10));
		        currentObject.setFocus();
			}                        
		}
		
		/**
		 * The function to remove error tooltip message.
		 */
		public function hideValidationErrorMessage(currentObject:Object):void {
			CommonUtil.closeErrorTip();
		}
	}
}