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
import java.util.Date;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.dao.IUserDAO;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method updateUser
 * 
 * @author mandar440346
 * 
 * @since $Revision: 2 $  
 */
public class TestUserDAOUpdateUser extends TestCase {
	
	UserProfile userProfile;
	
	
	protected void setUp() throws Exception {
		userProfile = new UserProfile();
		userProfile.setUserId("mandar.vaidya");
		userProfile.setUserName("Mandar Vaidya");
		userProfile.setEmailId("mandar.vaidya@mastek.com");
		userProfile.setHintQuestion("Who moved my cheese?");
		userProfile.setHintAnswer("mice");
		userProfile.setCreatedBy("mandar.vaidya");
		userProfile.setCreatedOn(new Date().getTime());
		userProfile.setTelephoneNo("02227781272");
		userProfile.setDefaultView("LIST_VIEW");
	}
	/**
	 * Tests Role Details   
	 */
	public void testUpdateUser(){		
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			Integer success = dao.updateUser(userProfile, connection);
			
			System.out.println("User [" + userProfile.getUserId() + "] updated successfully.");
			assertNotNull(success);			
		}catch(Exception e){
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestUserDAOGetUserDetails.java                                                     $
*
*/
