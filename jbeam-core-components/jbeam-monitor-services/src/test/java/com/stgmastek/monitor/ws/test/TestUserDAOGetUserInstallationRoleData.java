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
import com.stgmastek.monitor.ws.server.vo.ReqUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.UserInstallationRole;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method getUserInstallationRoleData  
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 2 $  
 */
public class TestUserDAOGetUserInstallationRoleData extends TestCase {
	
	ReqUserDetailsVO reqUserDetailsVO;
	
	
	protected void setUp() throws Exception {
		reqUserDetailsVO = new ReqUserDetailsVO();
		UserProfile userProfile = new UserProfile();
		userProfile.setUserId("mandar.vaidya");
		reqUserDetailsVO.setUserProfile(userProfile);
	}
	/**
	 * Tests Role Details   
	 */
	public void testGetUserInstallationRoleData(){		
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<UserInstallationRole> userRoleList= dao.getUserInstallationRoleData(reqUserDetailsVO, connection);
			for(UserInstallationRole userrolelist: userRoleList)
			   System.out.println("User Id:"+userrolelist.getUserId() +" \tInstallation Code:"+ userrolelist.getInstallationCode() +"\t Role ID:"+userrolelist.getRoleId());	

			//assertNotNull(info);
			//assertNotNull(reqUserDetailsVO);		
			
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
 * 2     7/07/10 5:43p Mandar.vaidya
 * Overridden setUp method to test method
 * 
 * 1     7/01/10 4:54p Lakshmanp
 * intial version
 * 
*
*/
