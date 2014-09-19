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

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.ResUserDetails;
import com.stgmastek.monitor.ws.server.vo.UserCredentials;
import com.stgmastek.monitor.ws.server.vo.UserDetails;


/**
 * JUnit class to test the UserDAO method user authentication 
 * 
 * @author mandar440346
 *
 */
public class TestUserDAOUserAuthentication extends TestCase{
		
	private UserCredentials userCredentials = null;

	
	protected void setUp() throws Exception {
		userCredentials = new UserCredentials();
		userCredentials.setUserId("jbeam");
		userCredentials.setPassword("jbeam");
	}

	/**
	 * Tests UserAuthentication   
	 */
	public void testUserAuthentication(){		
//		IUserDAO dao = DAOFactory.getUserDAO();
//		Connection connection = null;
//		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResUserDetails resUserDetails = impl.userAuthentication(userCredentials);
//			UserDetails userDetails = dao.userAuthentication(userCredentials, connection);
			assertNotNull(resUserDetails);			

			if(resUserDetails != null) {
				UserDetails userDetails = resUserDetails.getUserDetails();
				if(userDetails != null)
					System.out.println("User authenticated successfully. \nUser name =  " + userDetails.getUserName());
			}
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestUserDAOUserAuthentication.java                                                 $
 * 
 * 1     7/13/10 3:42p Lakshmanp
 * Initial Version
*
*
*/