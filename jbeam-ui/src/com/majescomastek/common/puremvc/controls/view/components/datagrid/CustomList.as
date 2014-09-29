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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/compon $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/compon $
 * 
 * 2     3/18/10 9:10a Sanjay.sharma
 * Updated CustomList
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 2     3/04/10 4:23p Sandeepa
 * 
 * 1     3/03/10 4:23p Sandeepa
 * to work with Custom datagrid
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.view.components.datagrid
{
	import com.majescomastek.common.controls.framework.BaseDataGrid;
	import com.majescomastek.common.controls.framework.BaseDataGridColumn;
	import com.majescomastek.common.controls.framework.BaseList;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.core.ClassFactory;
	import mx.core.IFactory;
	import mx.events.DragEvent;

	public class CustomList extends BaseList
	{

		public function CustomList()
		{
			super();
			
			this.addEventListener(DragEvent.DRAG_COMPLETE, onDragComplete, false, 0, true);
			initProperties();
		}
		
		/** The datagrid which this list controls */
		private var _targetDataGrid:BaseDataGrid;
		
		/** The 'id' of the columns which need to be displayed */
		private var _columnIds:ArrayCollection;
		
		[Bindable]
		public function get columnIds():ArrayCollection
		{
			var ids:Array = [];
			var dgColumns:ArrayCollection = ArrayCollection(this.dataProvider);
			for each(var dgColumn:BaseDataGridColumn in dgColumns)
			{
				if(dgColumn.visible == true)
				{
					ids.push(dgColumn.identifier);
				}
			}
			return new ArrayCollection(ids);
		}
		
		/**
		 * The setter for hiddenColumnsIds.
		 */
		public function set columnIds(value:ArrayCollection):void
		{
			_columnIds = value;
		}
		
		[Bindable]
		public function get targetDataGrid():BaseDataGrid
		{
			return _targetDataGrid;
		}
		
		public function set targetDataGrid(value:BaseDataGrid):void
		{
			_targetDataGrid = value;
		}
		
		private function initProperties():void
		{
			labelField="headerText";
			this.dragEnabled = true;
			this.dragMoveEnabled = true;
			this.dropEnabled = true;
			var classFactory:IFactory = new ClassFactory(BaseDataGridPreferenceCheckBox);
			this.itemRenderer = classFactory;
		}

		/**
		 * The function invoked when the drag event completes.
		 */
		private function onDragComplete(event:Event):void
		{
			targetDataGrid.columns = event.currentTarget.dataProvider.toArray();
		}

	}
}