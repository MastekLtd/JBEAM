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

import java.sql.Connection;
import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.dao.IUserDAO;
import com.stgmastek.monitor.ws.server.vo.Installation;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method getUserInstallationRoleData  
 * 
 * @author Kedar Raybagkar
 * 
 * @since $Revision: 2 $  
 */
public class TestUserDAOGetInstallationData extends TestCase {
	
	/**
	 * Tests Installation Details   
	 */
	public void testGetUserInstallationRoleData(){		
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<Installation> installationList= dao.getInstallationsList(connection);
			for (Installation installation : installationList) {
				System.out.println(" Installation Code: " + installation.getInstallationCode() 
						+ "\t Desc :" + installation.getInstallationDesc());	
				
			}
			assertNotNull(installationList);
			
		}catch(Exception e){
			System.out.println(" ");
			e.printStackTrace();
			fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestUserDAOGetUserInstallationRoleData.java                                        $
*
*/
