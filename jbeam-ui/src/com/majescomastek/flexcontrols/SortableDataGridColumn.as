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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/flexcontrols/SortableDataGridColumn $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/flexcontrols/SortableDataGridColumn $
 * 
 * 4     3/10/10 9:51a Sanjay.sharma
 * Changed import of BaseDataGridColumn
 * 
 * 3     2/25/10 7:25p Sanjay.sharma
 * Extended new parent class BaseDataGridColumn instead of DataGridColumn
 * 
 * 2     2/04/10 3:11p Purva.mukewar
 * Flex PMD code review done
 * 
 * 1     1/04/10 11:35a Sanjay.sharma
 * added new components
 * 
 * 
 */
package com.majescomastek.flexcontrols
{
	import com.majescomastek.common.controls.framework.BaseDataGridColumn;

	/**
	 * The base class for all the DataGrid columns which need the sortable
	 * behaviour. 
	 */
	public class SortableDataGridColumn extends BaseDataGridColumn
	{
		protected var valueCallbackSet:Boolean;
		
		/**
		 * The callback function which would be invoked in case custom processing
		 * is required to retrieve the value of the datagrid cell. This function
		 * takes a single parameter which would be the entire row in consideration.
		 */
		private var _valueCallbackFunction:Function;
		
		/** The field which contains the date value to be sorted */
		private var _sortableField:String = null;
		
		/** The components of the complex data field */
		[ArrayElementType("Object")]
		protected var complexSortableFieldNameComponents:Array;
		
		/** Whether the sortable field has complex value or not */
		protected var hasComplexSortableField:Boolean;
		
		public function SortableDataGridColumn(columnName:String=null)
		{
			super(columnName);
		}
		
		public function set valueCallbackFunction(valueCallbackFunction:Function):void
		{
			_valueCallbackFunction = valueCallbackFunction;
			valueCallbackSet = true;
		}
		
		public function get valueCallbackFunction():Function
		{
			return _valueCallbackFunction;
		}
		
		public function set sortableField(field:String):void
		{
			_sortableField = field;
			if(_sortableField && _sortableField.indexOf('.') != -1)
			{
				hasComplexSortableField = true;
				complexSortableFieldNameComponents = _sortableField.split('.');
			}
			this.sortCompareFunction = getCustomSort();
			this.sortable = true;
		}
		
		public function get sortableField():String
		{
			return _sortableField;
		}
		
		protected function deriveComplexSortableColumnData( data:Object ):Object
	    {
	        var currentRef:Object = data;
	        if (complexSortableFieldNameComponents) 
	        {
	            for(var i:int=0; i < complexSortableFieldNameComponents.length; i++ )
	                currentRef = currentRef[complexSortableFieldNameComponents[i]];
	        }
	        return currentRef;
	    }
	    
	    protected function getCustomSort():Function
	    {
	    	return null;
	    }
	    
	    protected function getStringValue(o:Object):String
	    {
	    	var val:String = "";
			if(valueCallbackSet)
			{
				val = valueCallbackFunction(o);
			}
			else
			{
				if(hasComplexSortableField)
				{
					val = String(deriveComplexSortableColumnData(o));
				}
				else
				{
					val = o[sortableField];
				}
			}
			return val;
	    }
	    
	}
}