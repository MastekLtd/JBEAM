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

import com.stgmastek.monitor.ws.server.dao.IMonitorDAO;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorDAOGetInstallationCodes extends TestCase{

	/**
	 * Tests Get Batch data condition 1   
	 */
	public void testGetInstallationCodes(){
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<String> installationCodeList = dao.getInstallationCodeList(connection);
			for (String installationCode : installationCodeList) {
				System.out.println("Installation code  = " + installationCode);
			}
			assertNotNull(installationCodeList);
			
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchData.java                                                    $
 * 
 * 10    7/10/10 2:18p Mandar.vaidya
 * Added new method to getInstructionParameters. Tested with latest data.
 * 
 * 9     6/23/10 11:19a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao method and handled connection leak.
 * 
 * 8     4/01/10 4:29p Mandar.vaidya
 * Tested with latest data.
 * 
 * 7     3/17/10 5:39p Mandar.vaidya
 * 
 * 6     3/17/10 4:39p Mandar.vaidya
 * Tested with updated data.
 * 
 * 5     1/08/10 10:16a Grahesh
 * Corrected the object hierarchy
 * 
 * 4     1/06/10 5:05p Grahesh
 * Modified the implementation
 * 
 * 3     1/06/10 10:49a Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/