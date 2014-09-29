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
 * $Revision:: 1                                                                                      $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/common/fra $
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/common/fra $
 * 
 * 1     11/11/09 4:06p Sanjay.sharma
 * Added the framework related code.
 * 
 */
package com.majescomastek.jbeam.common.framework
{
	import org.puremvc.as3.multicore.patterns.facade.Facade;

	/**
	 * The base class which extends on the capabilities of the PureMVC Facade.
	 * <p>
	 * Application classes would typically extend this class and complement it
	 * with their own custom logic.
	 */
	public class BaseFacade extends Facade
	{
		
		/**
		 * Create a <code>BaseFacade</code> object with <code>key</code> as the
		 * multiton key.
		 */
		public function BaseFacade(key:String)
		{
			super(key);
		}
		
	}
}