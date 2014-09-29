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
package com.majescomastek.flexcontrols
{
	import flash.display.DisplayObject;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFieldType;
	import flash.text.TextFormat;
	import flash.text.TextFormatAlign;
	
	import mx.controls.Label;
	import mx.controls.Text;
	import mx.core.UITextField;
	
	public class MultilineLabel extends Text
	{
		public function MultilineLabel()
		{
			super();
		}
		
		override protected function createChildren() : void 
		{ 
			// Create a UITextField to display the label. 
			if (!textField) 
			{ 
				textField = new UITextField(); 
				textField.styleName = this; 
				addChild(DisplayObject(textField)); 
			} 
			super.createChildren(); 
			textField.multiline = true; 
			textField.wordWrap = true;
//			textField.isPopUp = true;
//			textField.autoSize = TextFieldAutoSize.LEFT;
//			textField.truncateToFit();
//			textField.autoSize = TextFormatAlign.JUSTIFY 
		}		
	}
}