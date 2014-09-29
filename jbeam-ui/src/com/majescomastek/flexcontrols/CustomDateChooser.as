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
	
	import mx.collections.ArrayCollection;
	import mx.controls.Button;
	import mx.controls.DateChooser;

	public class CustomDateChooser extends DateChooser
	{
		public function CustomDateChooser()
		{
			super();
		}
		
		/**
	     *  @private
	     *  Create subobjects in the component. This method creates textfields for
	     *  dates in a month, month scroll buttons, header row, background and border.
	     */
	    override protected function createChildren():void
	    {
	        super.createChildren();
	        
	      	for ( var i:int=0; i < numChildren; i ++)
	      	{
	      		var d:DisplayObject = getChildAt(i);
	      		if(d is Button)
	      		{
	      			(d as Button).visible = false;
	      		}
	      	} 
		}
		
		public function highLightDates(arrayDates:ArrayCollection):void 
		{
			if (arrayDates == null) return; 
			
			var nonWorkingDateArray:Array = new Array();
			
			if (arrayDates.source.length > 0) 
			{
				var arrayDateSource:Array = arrayDates.source;
				for(var i:int = 0 ; i < arrayDateSource.length; i++)
				{
					nonWorkingDateArray.push({rangeStart:new Date(arrayDateSource[i])
								, rangeEnd: new Date(arrayDateSource[i]) });
				}
				this.selectedRanges = nonWorkingDateArray;
			}
			else if (arrayDates.source.length == 0)
			{
//				nonWorkingDateArray.push({rangeStart:new Date(arrayDateSource[i])
//								, rangeEnd: new Date(arrayDateSource[i]) });
				this.selectedRanges = nonWorkingDateArray;
				
			}
		}
	}
}