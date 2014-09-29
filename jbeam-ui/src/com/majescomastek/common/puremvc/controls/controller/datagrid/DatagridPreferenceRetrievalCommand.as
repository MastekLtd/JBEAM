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
 * $Revision:: 1                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $
 * 
 * 1     3/05/10 4:26p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 2     3/04/10 5:10p Sanjay.sharma
 * Updated command to handle retrieval of default preference
 * 
 * 1     3/03/10 12:51p Admin
 * 
 * 2     2/25/10 7:35p Sanjay.sharma
 * Updated command
 * 
 * 1     2/25/10 9:18a Sanjay.sharma
 * Added new command for retrieval of datagrid preference
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.controller.datagrid
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.model.proxy.UserProfileProxy;
	import com.majescomastek.jbeam.model.vo.DatagridPreference;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class responsible for retrieving the datagrid preference
	 * for the passed in preference criteria.
	 */
	public class DatagridPreferenceRetrievalCommand extends BaseSimpleCommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var data:Object = notification.getBody();
			var datagridPreference:DatagridPreference = retrieveDatagridPreference(data);
			
			var commonProxy:CommonProxy = CommonProxy(facade.retrieveProxy(CommonProxy.NAME));
			commonProxy.retrieveDatagridPreference(datagridPreference);
		}
		
		/**
		 * Retrieve the DatagridPreference object based on the passed in notification
		 * data.
		 */
		private function retrieveDatagridPreference(data:Object):DatagridPreference
		{
			var shellFacade:ShellFacade = ShellFacade(ShellFacade.getInstance(ShellFacade.NAME));
			var proxy:UserProfileProxy = UserProfileProxy(shellFacade.retrieveProxy(UserProfileProxy.NAME));
			var userProfile:UserProfile = CommonConstants.USER_PROFILE;
			
			var datagridPreference:DatagridPreference = new DatagridPreference();
			datagridPreference.screenName = data['screenName'];
			datagridPreference.datagridName = data['datagridName'];
			if(data['retrieveDefault'] !== true)
			{
				datagridPreference.userId = userProfile.userId;
			}
			return datagridPreference;			
		}
		
	}
}