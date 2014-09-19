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

import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.InstallationEntity;
import com.stgmastek.monitor.ws.server.vo.ReqInstallationVO;
import com.stgmastek.monitor.ws.server.vo.ResInstallationEntitiesVO;

/**
 * JUnit class to test the MonitorServices method GetBatchEntityData()  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorServicesGetBatchEntityData extends TestCase{
	ReqInstallationVO reqInstallationVO = null;
	
	
	protected void setUp() throws Exception {
		reqInstallationVO = new ReqInstallationVO();
		reqInstallationVO.setInstallationCode("BILLING-DV");
	}

	/**
	 * Tests Get Batch Completed Data
	 */
	public void testGetBatchEntityData(){
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResInstallationEntitiesVO resInstallationEntitiesVO = impl.getBatchEntityData(reqInstallationVO);
			List<InstallationEntity> installationEntities = resInstallationEntitiesVO.getInstallationEntities();
			
			for(InstallationEntity installationEntities2 : installationEntities){
				
				System.out.println("Entity #"+ installationEntities2.getEntity()
							+ "  >>> Lookup Column = "+ installationEntities2.getLookupColumn()
							+ "  >>> Lookup Value = "+ installationEntities2.getLookupValue()
							+ "  >>> VAlue Column = "+ installationEntities2.getValueColumn()
							+ "  >>> Precedence Order = "+ installationEntities2.getPrecedenceOrder()
							+ "  >>> On Error Flag = "+ installationEntities2.getOnErrorFailAll()
							);
			}
//			
			//assertNotNull(info);
			assertNotNull(installationEntities);
			
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchCompletedData.java                                           $
 * 
*
*/