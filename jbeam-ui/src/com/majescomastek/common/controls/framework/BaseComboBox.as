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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseCombo $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/controls/framework/BaseCombo $
 * 
 * 2     3/15/10 5:26p Ritesh.chavan
 * changes made by sanjay
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 
 */
package com.majescomastek.common.controls.framework
{
	import mx.controls.ComboBox;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridListData;
	import mx.controls.dataGridClasses.DataGridListData;
	import mx.controls.listClasses.BaseListData;

	/**
	 * The base class for all ComboBox components used in an application.
	 */
	public class BaseComboBox extends ComboBox implements IBaseControl
	{
		public function BaseComboBox()
		{
			super();
		}
		
	}
}