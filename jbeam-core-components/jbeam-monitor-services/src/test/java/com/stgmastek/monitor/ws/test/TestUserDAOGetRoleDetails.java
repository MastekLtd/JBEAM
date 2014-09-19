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
import java.sql.Date;
import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.dao.IUserDAO;
import com.stgmastek.monitor.ws.server.vo.RoleData;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method role details  
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 1 $  
 */
public class TestUserDAOGetRoleDetails extends TestCase {
	/**
	 * Tests Role Details   
	 */
	public void testGetRoleDetails(){		
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<RoleData> roleDataList = dao.getRoleData(connection);

			System.out.println("Role Id		Role Name	Eff Date   Exp Date");
			for(RoleData roleData : roleDataList) {
				System.out.print(roleData.getRoleId()+"\t");
				System.out.print(roleData.getRoleName()+"\t");
				System.out.print(new Date(roleData.getEffDate())+"\t");
				System.out.print(new Date(roleData.getExpDate())+"\n");
			}
			//assertNotNull(info);
			assertNotNull(roleDataList);
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestUserDAOGetRoleDetails.java                                                     $
 * 
 * 1     6/25/10 2:13p Lakshmanp
 * initial version
*
*
*/
