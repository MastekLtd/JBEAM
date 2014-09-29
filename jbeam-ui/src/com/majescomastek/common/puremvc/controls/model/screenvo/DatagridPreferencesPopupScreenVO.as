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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/model/scree $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/model/scree $
 * 
 * 1     3/18/10 8:54a Sanjay.sharma
 * Added new folder screenvo
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.model.screenvo
{
	import com.majescomastek.common.controls.framework.BaseDataGrid;
	import com.majescomastek.common.puremvc.controls.view.components.datagrid.DatagridPreferencesPopup;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	
	[Bindable]
	public class DatagridPreferencesPopupScreenVO
	{
		public function DatagridPreferencesPopupScreenVO()
		{
		}
		
		private var _view:DatagridPreferencesPopup;
		private var _targetDatagrid:BaseDataGrid;
		private var _screenName:String;
		private var _dataGridId:String;
		
		public function set view(value:DatagridPreferencesPopup):void
		{
			_view = value; 
		}
		public function get view():DatagridPreferencesPopup
		{
			return _view; 
		}
				
		public function set targetDatagrid(value:BaseDataGrid):void
		{
			_targetDatagrid = value; 
		}
		public function get targetDatagrid():BaseDataGrid
		{
			return _targetDatagrid; 
		}
		
		public function set screenName(value:String):void
		{
			_screenName = value; 
		}
		public function get screenName():String
		{
			return _screenName; 
		}
		
		public function set dataGridId(value:String):void
		{
			_dataGridId = value; 
		}
		public function get dataGridId():String
		{
			return _dataGridId; 
		}
	}
}