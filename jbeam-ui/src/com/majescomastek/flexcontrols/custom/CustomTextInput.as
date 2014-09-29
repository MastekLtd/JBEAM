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
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	
	import mx.controls.TextInput;

	public class CustomTextInput extends TextInput
	{
		public function CustomTextInput()
		{
			super();
			this.addEventListener(FocusEvent.FOCUS_IN,onFocusIn);
			this.addEventListener(FocusEvent.FOCUS_OUT,onFocusOut);
		}
		
		private function onFocusIn(event:FocusEvent):void
		{	
			if (this.errorString != '') 
			{				
		        dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OVER, true, false,x + 10,y + 10));
			}   
		}
		
		//To hide the validation message
		private function onFocusOut(event:FocusEvent):void
		{
			if (this.errorString != '') 
			{				
		        dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OUT, true, false,x + 10,y + 10));
			}
		}
		
	}
}