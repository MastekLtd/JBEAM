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
 * $Revision:: 21                                                                                                      $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/flexcontrols/SelectOneComboBox.as 2 $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/flexcontrols/SelectOneComboBox.as   $
 * 
 * 21    3/11/10 2:52p Ritesh.chavan
 * changes made by sanjay
 * 
 * 20    2/09/10 4:59p Purva.mukewar
 * Flex PMD code review done
 * 
 * 19    1/04/10 6:51p Sanjay.sharma
 * added new method clearContents and initialization of the combo box to empty arraycollection
 * 
 * 18    12/31/09 5:23p Sanjay.sharma
 * added reset functionality to the drop down custom component
 * 
 * 17    12/18/09 5:20p Sanjay.sharma
 * added utility functions to custom components
 * 
 * 16    12/10/09 9:16a Sanjay.sharma
 * added setter for selectedItemValue
 * 
 * 15    12/01/09 10:23a Narayana
 * Property added to display the -Select One- option dynamically.
 * 
 * 14    11/20/09 10:41a Sanjay.sharma
 * Updated custom component so that the default drop down option is generated on fly.
 * 
 * 13    11/19/09 4:23p Sanjay.sharma
 * Added a configurable value field.
 * 
 * 12    11/16/09 5:15p Sanjay.sharma
 * Added header.
 * 
 * 
 */
package com.majescomastek.flexcontrols
{
	import com.majescomastek.common.controls.framework.BaseComboBox;
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.CommonUtils;
	
	import mx.collections.ArrayCollection;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridListData;
	import mx.controls.dataGridClasses.DataGridListData;
	import mx.controls.listClasses.BaseListData;
	import mx.controls.listClasses.ListData;
	import mx.events.FlexEvent;

	/**
	 * <p>
	 * The Custom ComboBox control which adds a default item to the
	 * combo box when the data provider is set.
	 * <p>
	 * This control assumes that the dataprovider set is of type <code>
	 * ArrayCollection</code> containing ; if not, a runtime exception is thrown.
	 */
	public class SelectOneComboBox extends BaseComboBox
	{
		/** The default prompt string to be used by this control */
		//private var _promptText:String;
		
		/** The combo box value if already present; this maps to the subcode field of ConfigParameter */
		private var _selectedValue:String;
		
		/** The field which would be used for retrieving the value of this component */
		private var _valueField:String;
		
		/** The field which would be used to prevent the addition of `Select one' to the combo box provider*/
		private var _showDefaultOption:Boolean;
		
		private var _listData:BaseListData;

		// Can be either Datagrid or AdvancedDataGrid
		private var _dataGrid:Object;
		
		private var _dataField:String;
		
		private var _complexFieldNameComponents:Array;
		
		private var _data:Object;

		private var _hasComplexFieldName:Boolean;

		public function SelectOneComboBox()
		{
			super();
			super.labelField = "description";	/* The default label field used */
			_valueField = "subCode";	/* The default value field used */
			_showDefaultOption = true; /* Default behavior is true */
			dataProvider = new ArrayCollection();
		}
		
		/**
		 * This function would be fired when the updation of the combo box completes.
		 * If any initial value is specified for the combo box by means of the
		 * _selectedValue attribute, set the selected item of the combo box accordingly.
		 */
		private function setSelected():void
		{
			var list:ArrayCollection = super.dataProvider as ArrayCollection;
			if(!list)	return;
			
			// If no selected value is provided, set the combo box in default state
			// else loop through the dataprovider to set the provided value in
			// the list box. 
			if(_selectedValue == null && list.length > 0)
			{
				this.selectedIndex = 0;
			}
			else
			{ 
				for(var i:Number = 0; i < list.length; ++i)
				{
					if(_valueField && list.getItemAt(i)[_valueField] == _selectedValue)
					{
						selectedIndex = i;
						break;
					}
				}
			}
		}
		
		override public function set dataProvider(value:Object):void
		{	
			var list:ArrayCollection = ArrayCollection(value);
			if(showDefaultOption)
			{
				list = CommonUtils.getSelectOneDataprovider(super.labelField, _valueField);
				for each(var o:Object in value)
				{
					list.addItem(o);
				}
			}
			super.dataProvider = list;
			setSelected();
		}
		
		public function set selectedValue(value:String):void
		{
			_selectedValue = value;
			setSelected();
		}
		
		public function get selectedItemValue():Object
		{
			return this.selectedItem[_valueField];
		}
		
		public function set selectedItemValue(value:Object):void
		{
			this.selectedValue = String(value);
		}
		
		public function set valueField(value:String):void
		{
			_valueField = value;
		}
		
		public function get valueField():String
		{
			return _valueField;			
		}
		
		public function set showDefaultOption(value:Boolean):void
		{
			_showDefaultOption = value;
		}
		
		public function get showDefaultOption():Boolean
		{
			return _showDefaultOption;			
		}
		
		/**
		 * Validate whether the user has made a selection for this combo box
		 * or not. The validity criteria in our case is based on the current
		 * index of this control; if it's zero that means the user has still
		 * not made a selection.
		 */
		public function isValidSelection():Boolean
		{
			return (this.selectedIndex != CommonConstants.DROP_DOWN_INDEX_DEFAULT_VALUE);
		}
		
		/**
		 * Retrieve the interpolated or calculated string value of this drop down component.
		 * If the selection is not valid return false else the selected value of this
		 * control.
		 */
		public function getInterpolatedStringValue():String
		{
			var value:String = isValidSelection() ? String(this.selectedItemValue) : null;
			return value;
		}
		
		/**
		 * Reset the state of the combo box i.e. revert it back to its intial state.
		 */
		public function reset():void
		{
			this.selectedIndex = CommonConstants.DROP_DOWN_INDEX_DEFAULT_VALUE;
		}
		
		/**
		 * Clear the existing contents of this combo box and set the
		 * dataprovider to an empty array collection.
		 */
		public function clearContents():void
		{
			this.dataProvider = new ArrayCollection();
		}
		
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
	        var selectedValueStr:String;

	        _data = value;
	
	        if (_listData && (_listData is DataGridListData || _listData is AdvancedDataGridListData))
	        {
	        	if(_hasComplexFieldName)
	        	{
	        		selectedValueStr = String(deriveComplexColumnData(value));
	        	}
	        	else
	        	{
	        		selectedValueStr = String(_data[_dataField]);
	        	}
	        }
	        else if (_listData is ListData && ListData(_listData).labelField in _data)
	        {
	            selectedValueStr = String(_data[ListData(_listData).labelField]);
	        }
	        else
	        {
	            selectedValueStr = String(_data);
	        }	            
	        selectedItemValue = selectedValueStr;
	        dispatchEvent(new FlexEvent(FlexEvent.DATA_CHANGE));
	    }

	}
}