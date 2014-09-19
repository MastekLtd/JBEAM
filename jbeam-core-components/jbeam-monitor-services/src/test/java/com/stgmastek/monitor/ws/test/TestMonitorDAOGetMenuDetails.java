/*
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
 */
package com.stgmastek.monitor.ws.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.MenuData;
import com.stgmastek.monitor.ws.server.vo.ResMenuDetails;
import com.stgmastek.monitor.ws.server.vo.UserProfile;

/**
 * JUnit class to test the MonitorDAO method get menu data  
 * 
 * @author mandar440346
 *
 */
public class TestMonitorDAOGetMenuDetails extends TestCase{

	/**
	 * Tests Get menu details   
	 */
	public void testGetMenuDetails(){
		UserProfile userProfile = new UserProfile();
		userProfile.setUserId("mpv");
//		userProfile.setInstallationCode("NY-DEMO");
		
		MonitorServicesImpl impl = new MonitorServicesImpl();
//		Connection connection = null;
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
			ResMenuDetails resMenuDetails =  impl.getMenuDetails(userProfile);
			
			List<ResMenuDetails> resMenuDetailsList = new ArrayList<ResMenuDetails>();
//			resMenuDetailsList = dao.getMenuDetails(userDetails, connection);
			if(resMenuDetails != null) {
				if( resMenuDetails.getInstallationCode() != null) {
					System.out.println("Installation code = " + resMenuDetails.getInstallationCode() );
				}
				resMenuDetailsList = resMenuDetails.getMenuList();
				for(ResMenuDetails menuDetails: resMenuDetailsList) {
					System.out.println("menu = " +  menuDetails.getParentFunctionId());
					for(MenuData menuData: menuDetails.getChildren()) {
						System.out.println("\t sub-menu = " +  menuData.getFunctionName());					
					}
					
				}				
			}
			assertNotNull(resMenuDetailsList);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
//			dao.releaseResources(null, null, connection);
		}
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetMenuDetails.java                                                  $
 * 
 * 2     6/23/10 11:24a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao methods.
 * 
 * 1     6/10/10 4:04p Mandar.vaidya
 * Initial Version
*
*/