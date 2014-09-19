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

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.dao.IUserDAO;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method getUserDetails  
 * 
 * @author mandar440346
 * 
 * @since $Revision: 2 $  
 */
public class TestUserDAOGetUserDetails extends TestCase {
	
	UserProfile userProfile;
	
	
	protected void setUp() throws Exception {
		userProfile = new UserProfile();
		userProfile.setUserId("mandarv");
	}
	/**
	 * Tests Role Details   
	 */
	public void testGetUserDetails(){		
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			userProfile = dao.getUserDetails( userProfile, connection);
			if(userProfile == null) {
				throw new Exception("User does not exist");
			}else {
				System.out.println("User Id: " + userProfile.getUserId());	
				System.out.println("User Name: " + userProfile.getUserName());	
				System.out.println("User Email: " + userProfile.getEmailId());	
				System.out.println("Hint Question: " + userProfile.getHintQuestion());	
				System.out.println("Hint Answer: " + userProfile.getHintAnswer());	
				System.out.println("Password: " + userProfile.getPassword());	
				System.out.println("Default View: " + userProfile.getDefaultView());	

			}
			assertNotNull(userProfile);			
		}catch(Exception e){
			System.out.println("User does not exist");
			fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestUserDAOGetUserDetails.java                                                     $
 * 
 * 2     7/10/10 2:20p Mandar.vaidya
 * Tested with latest data.
 * 
 * 1     7/07/10 5:41p Mandar.vaidya
 * Initial version
 * 
 * 1     7/01/10 4:54p Lakshmanp
 * intial version
 * 
*
*/
