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
import com.stgmastek.monitor.ws.server.vo.UserCredentials;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to Reset user password  
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 1 $  
 */
public class TestUserDAOChangePassword extends TestCase {
	
	UserCredentials userCredentials = new UserCredentials();
	
	
	protected void setUp() throws Exception {
		userCredentials.setUserId("mandar.vaidya");
		//this password after encryption should match with DB password 
		//tested in PasswordEncryptionTest as after encryption matching with DB password.
		userCredentials.setPassword("123test");  
		userCredentials.setNewPassword("test123");//new password
		
	}
	
	
	/**
	 * Tests resetPassword   
	 */
	public void testResetPassword(){		
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			System.out.println("Before reset password ");
			dao.changePassword(userCredentials, connection);
			System.out.println("Password has been reset.");


			//assertNotNull(info);
			
		}catch(Exception e){
//			e.printStackTrace();
			fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestUserDAOResetPassword.java                                                      $
 * 
 * 1     7/13/10 3:41p Lakshmanp
 * Initial version
*
*/
