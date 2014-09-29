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
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	
	import mx.controls.Button;

	public class ActionButton extends Button
	{
		public function ActionButton()
		{
			super();
			this.addEventListener(KeyboardEvent.KEY_DOWN, keyHandler);
			this.enabled = true;
			this.styleName = "button";
		}

		private function keyHandler(event:KeyboardEvent):void{
			//on Press of ENTER key
			if (event.keyCode == 13) {			
				this.dispatchEvent(new MouseEvent(MouseEvent.CLICK));
			}
		}
	}
}