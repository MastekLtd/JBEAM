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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $
 * 
 * 2     3/18/10 9:11a Sanjay.sharma
 * Updated Save command to handle the new save format
 * 
 * 1     3/05/10 4:26p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 2     3/03/10 4:32p Sandeepa
 * to make the file sync
 * 
 * 2     2/25/10 7:33p Sanjay.sharma
 * Updated command
 * 
 * 1     2/25/10 4:18p Sanjay.sharma
 * Added a new common for saving datagrid preference
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
	
	import mx.collections.ArrayCollection;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class responsible for saving the datagrid preferences
	 * for a given datagrid.
	 */
	public class DatagridPreferenceSaveCommand extends BaseSimpleCommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var body:Object = notification.getBody();
			var datagridPreference:DatagridPreference = createDatagridPreference(body);
			
			var commonProxy:CommonProxy = CommonProxy(facade.retrieveProxy(CommonProxy.NAME));
			commonProxy.saveDatagridPreference(datagridPreference);			
		}
		
		private function createDatagridPreference(data:Object):DatagridPreference
		{
			var columnIds:ArrayCollection = ArrayCollection(data['columnIds']);
			// Disable pretty indentation and printing, we don't want
			// to waste time & space on formatting XML
			XML.prettyPrinting = false;
			
			var xml:String = "<columns>";
			for each(var columnId:String in columnIds)
			{
				xml += "<column>" + columnId + "</column>";
			}
			xml += "</columns>";
			
			var preference:DatagridPreference = new DatagridPreference();
			preference.screenName = data['screenName'];
			preference.datagridName = data['datagridName'];
			preference.datagridColumns = xml.toString();
			
			var shellFacade:ShellFacade = ShellFacade(ShellFacade.getInstance(ShellFacade.NAME));
			var proxy:UserProfileProxy = UserProfileProxy(shellFacade.retrieveProxy(UserProfileProxy.NAME));
			var userProfile:UserProfile = CommonConstants.USER_PROFILE;
			preference.userId = userProfile.userId;

			return preference;
		}
		
	}
}