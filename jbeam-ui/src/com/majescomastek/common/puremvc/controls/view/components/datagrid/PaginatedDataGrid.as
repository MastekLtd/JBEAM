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
 * $Revision:: 1                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/compon $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/compon $
 * 
 * 1     3/18/10 9:09a Sanjay.sharma
 * Added new custom component
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.view.components.datagrid
{
	import com.majescomastek.common.controls.framework.BaseDataGrid;
	import com.majescomastek.common.puremvc.controls.model.screenvo.DataGridContainerHeaderInfoScreenVO;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;

	public class PaginatedDataGrid extends BaseDataGrid
	{
		
		public function PaginatedDataGrid()
		{
			super();
			addEventListener(ITEM_CHANGED,onChange,false,0,true);
		}
		
		/**
		 * Event constant used to indicate the change of filter item
		 * in the header of the grid.
		 * If datagrid has filterable columns then only this will we 
		 * used.
		 */
		public static const ITEM_CHANGED:String = "ITEM_CHANGED";
		
		
		/**
		 * @private
		 * for comaring the values.
		 */
		private var valueStr:String;
		
		/**
		 * @private
		 * To store the selected filter items.
		 * 
		 */
		private var headerSelectedItems:ArrayCollection = new ArrayCollection();
		
		/**
		 * @private 
		 * Function adds the selected item in the filter combobox to the 
		 *  headerSelectedItems collection.
		 */
		private function addHeaderSelectedItem(object:DataGridContainerHeaderInfoScreenVO):void
		{
			headerSelectedItems.addItem(object);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function set dataProvider(value:Object):void
		{
			super.dataProvider = value;
			headerSelectedItems = new ArrayCollection();
		}
		
		/**
		 * @private
		 * This get called on change of the item in the filter combobox
		 * in the datagrid header.
		 */
		private function onChange(event:Event):void
		{
			var headerInfo:DataGridContainerHeaderInfoScreenVO = new DataGridContainerHeaderInfoScreenVO();
			var isItemExist:Boolean = false;
			for each(var listGridInfo:DataGridContainerHeaderInfoScreenVO in headerSelectedItems)
			{
				if(listGridInfo.dataField == event.target.dataField){
					if(event.target.selectedIndex > 0)
					{
						listGridInfo.selectedItem = event.target.selectedItem;
					}
					else
					{
						listGridInfo.selectedItem = null;
					}
					
					isItemExist = true;
				}
			}
			if(!isItemExist)
			{
				headerInfo.dataField = event.target.dataField;
				headerInfo.selectedItem = event.target.selectedItem;
				addHeaderSelectedItem(headerInfo);
			}
			//headerSelectedItems[event.target.dataField] = event.target.selectedItem;
			applyFilter(event.target.dataField, event.target.selectedItem);
		}		
		
		/**
		 * function which returns the selcted item of the 
		 * filter combobox.
		 */
		public function getHeaderInfo(dataField:String):Object
		{
			for each(var headerInfo:DataGridContainerHeaderInfoScreenVO in headerSelectedItems)
			{
				if(headerInfo.dataField == dataField)
				{
					return headerInfo.selectedItem;
				}
			}
			return null;
		}
		
		/**
		 * Function which generates comparable value which would
		 * be used in filtering the records.
		 */
		private function applyFilter(columnDataField:String, selectedItem:Object):void
		{
			var completeList:ArrayCollection = this.dataProvider as ArrayCollection;
			valueStr = "";
			for each(var headerInfo:DataGridContainerHeaderInfoScreenVO in headerSelectedItems)
			{
				if(headerInfo.dataField != null && headerInfo.selectedItem != null)
				{
					valueStr += getFieldValue(headerInfo.selectedItem, headerInfo.dataField);
				}
			}
			if(valueStr!=""){
				completeList.filterFunction = filter;
				completeList.refresh();
			}
			else
			{
				completeList.filterFunction = null;
				completeList.refresh();
			} 
			//this.invalidateList();
		}
		
		/**
		 * Function filters the records in the grid.
		 */
		private function filter(currentItem:Object):Boolean
		{												
		 	 
		 	var _temp:String = "";
		 	for each(var headerInfo:DataGridContainerHeaderInfoScreenVO in headerSelectedItems)
			{
				if(headerInfo.dataField != null && headerInfo.selectedItem != null)
				{
					_temp += getFieldValue(currentItem, headerInfo.dataField);
				}
			}
		 	return valueStr==_temp;
			
		} 


		/**
		 * function which returns value of a property in given 
		 * value object.
		 */
		private function getFieldValue(listItem:Object, columnDataField:String):String
		{
			var itemVal:String;
			if(columnDataField.indexOf(".") != -1)
			{
				var fields:Array = columnDataField.split(".");
			  	for each(var f:String in fields)
			  	{
					listItem = listItem[f];
					if(!listItem)	break;
				}
				if(listItem)
				{	
					itemVal = listItem as String;
				}
			}
			else
			{
				itemVal = listItem[columnDataField];
			}
			return itemVal;
		}
		
		 	}
}