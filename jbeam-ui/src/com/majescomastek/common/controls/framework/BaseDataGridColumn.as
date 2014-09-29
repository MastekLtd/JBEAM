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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseDataG $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseDataG $
 * 
 * 2     3/18/10 9:47a Sanjay.sharma
 * Updated BaseDataGridColumn
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 3     3/04/10 3:03p Sandeepa
 * to change the value
 * 
 * 2     3/04/10 2:34p Sandeepa
 * to add new features to the datagrid
 * 
 * 1     3/03/10 12:51p Admin
 * 
 * 1     2/25/10 3:07p Sanjay.sharma
 * Added a new custom datagrid column
 * 
 * 
 */
package com.majescomastek.common.controls.framework
{
	import com.majescomastek.common.puremvc.controls.view.components.datagrid.DataGridContainerHeaderRenderer;
	
	import flash.events.Event;
	
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ClassFactory;
	import mx.core.IFactory;

	[Event(name="identifierChangeEvent", type="flash.events.Event")]

	/**
	 * The parent class for all custom datagrid columns which adds the feature
	 * of specifying a unique identifier for each column.
	 */
	public class BaseDataGridColumn extends DataGridColumn implements IBaseControl
	{
		
		/**
		 * Create a new BaseDataGridColumn object.
		 */
		public function BaseDataGridColumn(columnName:String=null)
		{
			super(columnName);
			
			overrideDefaultRenderer();
		}
		
		/**
	     *  @private
	     *  Storage for the mandatory property.
	     */ 
		private var _mandatory:Boolean = false;
		
		/**
		 * The string literal which uniquely identifies this datagrid column. This
		 * is required since the `id' attribute can't be queried at runtime using
		 * a datagrid column reference. Ideally this should be set to a value which
		 * is the same as the `id' attribute of this column.
		 */
		private var _identifier:String;
		
		/**
	     *  @private
	     *  Storage for the useFilter property.
	     */ 
		private var _useFilter:Boolean = false;
		
		/**
		 * Override the default item renderer for this datagrid column to
		 * use `Label` instead of `Text` to avoid the rendering issues.
		 */
		private function overrideDefaultRenderer():void
		{
			var labelFactory:IFactory = new ClassFactory(BaseLabel);
			this.itemRenderer = labelFactory;
			//this.headerRenderer = labelFactory;
		}
		
		[Bindable("identifierChangeEvent")]
		public function get identifier():String
		{
			return _identifier;
		}
		
		public function set identifier(value:String):void
		{
			_identifier = value;
			dispatchEvent(new Event("identifierChangeEvent"));
		}
		
		[Bindable("userFilterChanged")]
		/**
	     *  A flag that indicates whether the user is allowed to apply
	     *  the filter on column at header
	     *  If <code>true</code>, A combobox appears on the header of the 
	     * 	column with distinct values which are exist in the column.
	     *  By using the combobox appears in the header user can filter 
	     *  the records in the datagrid against column.
	     * 
	     *  <p>By default it won't render the comboxbox with filter option in the 
	     *   header.</p>
	     */
		public function get useFilter():Boolean
		{
			return _useFilter;
		}
		
		[Inspectable(category="General", enumeration="false,true", defaultValue="false")]
		public function set useFilter(filter:Boolean):void
		{
			_useFilter = filter;
			filterHeaderRenderer();
			//dispatchEvent(new Event("userFilterChanged"));
		}
		
		[Bindable("userFilterChanged")]
		private function filterHeaderRenderer():void
		{
			if(useFilter)
			{
				var headerFactory:IFactory = new ClassFactory(DataGridContainerHeaderRenderer);
				this.headerRenderer = headerFactory;
			}
			//else 
		}
		
		[Bindable("mandatoryChanged")]
		/**
	     *  A flag that indicates whether the user is allowed to remove the 
	     *  column from the datagrid.
	     *  If <code>true</code>, User can have option to remove the column. 
	     * 
	     *  <p>By default it allow the user to remove the column.</p>
	     */
		public function get mandatory():Boolean
		{
			return _mandatory;
		}
		
		[Inspectable(category="General", enumeration="false,true", defaultValue="false")]
		public function set mandatory(value:Boolean):void
		{
			_mandatory = value;
			dispatchEvent(new Event("mandatoryChanged"));
		}

	}
}