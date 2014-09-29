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
 * @author Gourav Rai
 *
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/components/reports/Bas $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/components/reports/Bas $
 * 
 * 2     4/12/10 6:53p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     4/07/10 12:00p Gourav.rai
 * Added by Gourav Rai
 * 
 * 1     3/26/10 10:48a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.view.components.reports
{
	import com.majescomastek.jbeam.model.vo.ReportParameter;
	
	import mx.containers.HBox;
	import mx.validators.Validator;

	public class BaseParameterChild extends HBox
	{
		public function BaseParameterChild()
		{
			super();
		}
		public var parameter:ReportParameter = null;
		
		virtual public function getValidator():Validator
		{
			return null;
		}
	}
}