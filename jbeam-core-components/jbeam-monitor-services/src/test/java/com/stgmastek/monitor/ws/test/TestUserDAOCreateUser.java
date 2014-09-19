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
import com.stgmastek.monitor.ws.server.vo.ReqUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.UserInstallationRole;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.Configurations;

/**
 * JUnit class to test the UserDAO method Manage user  
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 2 $  
 */
public class TestUserDAOCreateUser extends TestCase {
	
	ReqUserDetailsVO reqUserDetailsVO;
	
	
	protected void setUp() throws Exception {
		Configurations.getConfigurations().loadConfigurations();
		
		UserInstallationRole userInstallationRole = new UserInstallationRole();
		UserInstallationRole userInstallationRole1 = new UserInstallationRole();
//		"INSERT INTO user_master ( user_id, user_name, telephone_no, fax_no, email_id, " +
//		"eff_date, exp_date, created_on, created_by, password, assigned_role) " +
//		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUserId("jigneshv");
		userProfile.setUserName("Jignesh Vyas");
		userProfile.setTelephoneNo("02234324324");
		userProfile.setEmailId("mandar.vaidya@mastek.com");
		userProfile.setEffDate(1262025000000l);
		userProfile.setExpDate(1252025033000l);
		userProfile.setCreatedOn(1262025000000l);
		userProfile.setCreatedBy("jigneshv");
		userProfile.setAdminRole("N");
		userProfile.setConnectRole("N");
		String randomPassword = "abcd123";
		userProfile.setPassword(randomPassword);
		
		List<UserInstallationRole> userInstallationRoles = new ArrayList<UserInstallationRole>();
		
		userInstallationRole.setUserId("jigneshv");
		userInstallationRole.setInstallationCode("TEST_DEV1");
		userInstallationRole.setRoleId("VIEWER");
		userInstallationRoles.add(userInstallationRole);
		
		userInstallationRole1.setUserId("jigneshv");
		userInstallationRole1.setInstallationCode("TEST_UAT");
		userInstallationRole1.setInstallationCode("TEST_PROD");
		userInstallationRole1.setRoleId("OPERATOR");
		userInstallationRoles.add(userInstallationRole1);
		
		reqUserDetailsVO = new ReqUserDetailsVO();
		reqUserDetailsVO.setUserProfile(userProfile);
		reqUserDetailsVO.setUserInstallationRoles(userInstallationRoles);
	}
	
	
	/**
	 * Tests Role Details   
	 */
	public void testManageUser(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
//		Connection connection = null;
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
			System.out.println("Before call to creating user ");
			impl.manageUser(reqUserDetailsVO);
			System.out.println("user is created..");


			//assertNotNull(info);
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestUserDAOCreateUser.java                                                         $
 * 
 * 2     7/08/10 3:34p Mandar.vaidya
 * Tested with latest data
 * 
 * 1     6/30/10 3:11p Lakshmanp
 * Initial version
*
*/
