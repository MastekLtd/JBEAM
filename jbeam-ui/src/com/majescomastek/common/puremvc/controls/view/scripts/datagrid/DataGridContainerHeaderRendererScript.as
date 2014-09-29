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
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/script $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/script $
 * 
 * 2     3/18/10 11:43a Sanjay.sharma
 * Used dataField instead of sortField.name
 * 
 * 1     3/18/10 9:09a Sanjay.sharma
 * Added header renderer script
 * 
 * 
 */
import com.majescomastek.common.controls.framework.BaseDataGridColumn;
import com.majescomastek.common.puremvc.controls.view.components.datagrid.PaginatedDataGrid;
import com.majescomastek.flexcontrols.DateGridColumn;
import com.majescomastek.flexcontrols.LocaleRestrictedDateField;
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;

import mx.collections.ArrayCollection;
import mx.collections.Sort;
import mx.collections.SortField;
import mx.controls.dataGridClasses.DataGridListData;
import mx.controls.listClasses.BaseListData;
import mx.utils.ObjectUtil;

private var _listData:BaseListData;
private var _dataGrid:PaginatedDataGrid;
private var _dataField:String;
private var _isDateColumn:Boolean = false;
		
public function get dataField():String
{
	return this._dataField;
}
[Bindable] private var _headerLabel:String;


override public function set listData(value:BaseListData):void
{
    _listData = value;
    _dataGrid = value.owner as PaginatedDataGrid;
    this.labelField = _dataField = (value as DataGridListData).dataField;
    _headerLabel = value.label;		        
}

/**
 * @inherited doc.
 */
override public function set data(value:Object):void
{
	super.data = value;
	
	var column:BaseDataGridColumn = value as BaseDataGridColumn;
	if(column is DateGridColumn) _isDateColumn = true;
	//column.sortable = false;
	var dataProv:ArrayCollection = _dataGrid.dataProvider as ArrayCollection;				
    if(dataProv!=null && column.useFilter)
    {
    	column.sortable = false;
    	createFilter(dataProv, column.headerText);
    	if(_dataGrid.getHeaderInfo(column.dataField) == null )
    	{
    		this.selectedIndex = 0;
    	}
    	else
    	{
    		this.selectedIndex = getSelectedItemIndex();	
    	}
    }
 }
 
/**
 * Function which returns the selected value index
 */
private function getSelectedItemIndex():int
{
	 var dataProv:ArrayCollection = this.dataProvider as ArrayCollection;
	 var selectedItemVal:String = getFieldValue(_dataGrid.getHeaderInfo(_dataField), _dataField);
	 for(var idx:int = 1; idx<dataProv.length; ++idx)
	{
		var currentItem:Object = dataProv.getItemAt(idx);
		var fieldVal:String = getFieldValue(currentItem, _dataField);
		
		if(fieldVal!=null && fieldVal == selectedItemVal)
		{
			return idx;
		}
	} 
	return 0; 
}

/**
 * Function which returns returns the value of the property in value
 * object.
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

/**
 * Function creates filter items in the combobox
 * which can be used to filter the data in the data grid.
 */	
private function createFilter(completeList:ArrayCollection, columnHeaderText:String):void
{
	
	var filterItems:ArrayCollection = new ArrayCollection();
	var filterComparator:Object = new Object();
	
	for each( var listItem:Object in completeList)
	{
		if(_dataField)
		{
			var fieldValue:String = getFieldValue(listItem, _dataField);
			if(fieldValue != null && fieldValue != CommonConstants.BLANK_STRING
			       && filterComparator[fieldValue] == null)
			{
				filterItems.addItem(listItem);
				filterComparator[fieldValue] = fieldValue;
			}
		}
		else
		{
			AlertBuilder.getInstance().show("Data field required.");
		}
	}	
	var sortedItems:ArrayCollection = applySorting(filterItems);
	sortedItems.addItemAt(columnHeaderText,0);
	this.dataProvider = sortedItems;
}

/**
 * Function which applies sorting to the filter elements
 */
private function applySorting(_list:ArrayCollection):ArrayCollection
{
	var sort:Sort = new Sort();
	sort.fields = [new SortField(_dataField)];
	if(_isDateColumn){
		sort.compareFunction = dateCompareFunction;
	}
	else
	{
		sort.compareFunction = stringCompareFunction;	
	}
	
	_list.sort = sort;
	_list.refresh();
	return new ArrayCollection(_list.toArray());
}

/**
 * Function which compares strings.
 */
private function stringCompareFunction(a:Object, b:Object, fields:Array = null):int
{
	if (a == null && b == null)
    	return 0;
	if (a == null)
		return 1;
	if (b == null)
		return -1;	
	return ObjectUtil.stringCompare(String(getFieldValue(a, _dataField)), String(getFieldValue(b, _dataField)));
}

/**
 * Function which compares dates.
 */
private function dateCompareFunction(outPutA:Object, outPutB:Object, fields:Array = null):int
{
	try
	{
		var dateA:Date = LocaleRestrictedDateField.stringToDate(getFieldValue(outPutA, _dataField));
		var dateB:Date = LocaleRestrictedDateField.stringToDate(getFieldValue(outPutB, _dataField));
		return ObjectUtil.dateCompare(dateA,dateB)*-1;
	}
	catch(e:Error)
	{
		trace(e.message);
	}
	return 0;
}

/**
 * Function which will be called on change of the combobox.
 */
private function onItemChange(event:Event):void
{
	 var listOfItems:ArrayCollection = this.dataProvider as ArrayCollection;
	if(listOfItems!=null && listOfItems.length>0)
	{
		 var myEvent:Event = new Event(PaginatedDataGrid.ITEM_CHANGED,true);
		dispatchEvent(myEvent); 
	} 
}