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
 * @author Sandeep A
 * 
 *
 * $Revision:: 5                    $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/csr/com/majescomastek/Link/DateGridColumn.as 5      $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/csr/com/majescomastek/Link/DateGridColumn.as         $
 * 
 * 5     2/08/10 3:02p Sandeepa
 * Done PMD changes
 * 
 * 4     1/04/10 11:44a Sanjay.sharma
 * updated date grid column to handle nested properties
 * 
 * 3     12/24/09 5:12p Sanjay.sharma
 * updated DateGridColumn to remove the resource lookups
 * 
 * 2     11/12/09 2:08p Sandeepa
 * 
 * 
 */
package com.majescomastek.flexcontrols
{
	import mx.utils.ObjectUtil;

	public class DateGridColumn extends SortableDataGridColumn
	{
		public function DateGridColumn(columnName:String=null)
		{
			super(columnName);
		}

		private function mySort(itemA:Object, itemB:Object, fields:Array=null):int
		{
			var dateA:Date = getDate(itemA);
			var dateB:Date = getDate(itemB);
			var compareInt:int = ObjectUtil.dateCompare(dateA, dateB);
			
			if(hasComplexSortableField)
			{
				return (this.sortDescending ? -1 * compareInt : compareInt);
			}
			else
			{
				return compareInt;
			}
		}

		private function getDate(o:Object):Date
		{
			var stringDate:String = getStringValue(o);
			var formattedDate:Date = LocaleRestrictedDateField.stringToDate(stringDate);
			return formattedDate;
		}
		
		override protected function getCustomSort():Function
		{
			return mySort;
		}

	}
	
}