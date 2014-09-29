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
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseDateF $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseDateF $
 * 
 * 2     3/15/10 5:27p Ritesh.chavan
 * changes made by sanjay
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 
 */
package com.majescomastek.common.controls.framework
{
	import com.majescomastek.flexcontrols.LocaleRestrictedDateField;
	
	import mx.controls.DateField;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridListData;
	import mx.controls.dataGridClasses.DataGridListData;
	import mx.controls.listClasses.BaseListData;
	import mx.controls.listClasses.ListData;
	import mx.events.FlexEvent;
	
	/**
	 * The base class for all DateField components used in an application.
	 */
	public class BaseDateField extends DateField implements IBaseControl
	{
		public function BaseDateField()
		{
			super();
		}
		
		private var _listData:BaseListData;

		// Can be either Datagrid or AdvancedDataGrid
		private var _dataGrid:Object;
		
		private var _dataField:String;
		
		private var _complexFieldNameComponents:Array;
		
		private var _data:Object;

		private var _hasComplexFieldName:Boolean;
		
		[Bindable("dataChange")]
		override public function get listData():BaseListData
		{
		    return _listData;
		}
		
		override public function set listData(value:BaseListData):void
		{
		    _listData = value;
		    if(!value)	return;
		    
		    // Here value is either DataGridListData or AdvancedDataGridListData
		    // The listData method is generic and accepts BaseListData
		    _dataGrid = value.owner;   
		    if(value is DataGridListData)
		    {
		    	_dataField = DataGridListData(value).dataField;
		    }
		    else if(value is AdvancedDataGridListData)
		    {
		    	_dataField = AdvancedDataGridListData(value).dataField;
		    }
		    if (_dataField.indexOf( "." ) != -1 )
	        {
	        	_hasComplexFieldName = true;
	            _complexFieldNameComponents = _dataField.split( "." );
			}
		}
		
		protected function deriveComplexColumnData( data:Object ):Object 
	    {
	        var currentRef:Object = data;
	        if (_complexFieldNameComponents) 
	        {
	            for (var i:int=0; i < _complexFieldNameComponents.length; i++)
	                currentRef = currentRef[_complexFieldNameComponents[i]];
	        }
	        return currentRef;
    	}
    	
    	override public function get data():Object
	    {
	        return _data;
	    }
	
	    override public function set data(value:Object):void
	    {
	        var dateStr:String;
	
	        _data = value;
	
	        if (_listData && (_listData is DataGridListData || _listData is AdvancedDataGridListData))
	        {
	        	if(_hasComplexFieldName)
	        	{
	        		dateStr = String(deriveComplexColumnData(value));
	        	}
	        	else
	        	{
	        		dateStr = String(_data[_dataField]);
	        	}
	        }
	        else if (_listData is ListData && ListData(_listData).labelField in _data)
	        {
	            dateStr = _data[ListData(_listData).labelField];
	        }
	        else if (_data is String)
	        {
	            dateStr = String(_data);
	        }
	        else
	        {
	            dateStr = LocaleRestrictedDateField.dateToString(_data as Date);
	        }
	        this.text = dateStr;
	        dispatchEvent(new FlexEvent(FlexEvent.DATA_CHANGE));
	    }
		
	}
}