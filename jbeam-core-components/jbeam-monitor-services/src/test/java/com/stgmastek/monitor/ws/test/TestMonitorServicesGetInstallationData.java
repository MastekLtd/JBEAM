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
import com.stgmastek.monitor.ws.server.vo.InstallationData;
import com.stgmastek.monitor.ws.server.vo.InstallationEntity;
import com.stgmastek.monitor.ws.server.vo.ProgressLevelData;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ResInstallationVO;
import com.stgmastek.monitor.ws.server.vo.UserProfile;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorServicesGetInstallationData extends TestCase{
	
	ReqBatch reqBatch = null;
	
	
	protected void setUp() throws Exception {
		reqBatch = new ReqBatch();
		reqBatch.setBatchNo(0);
		reqBatch.setBatchRevNo(0);
		reqBatch.setInstallationCode("BILLING-DV");
		UserProfile userProfile = new UserProfile();
		userProfile.setUserId("jbeam");
		reqBatch.setUserProfile(userProfile);
	}

	/**
	 * Tests Get system information   
	 */
	public void dtestGetInstallationData(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResInstallationVO resInstallationVO = impl.getInstallationData(reqBatch);
			List<InstallationData> listInstallationData = resInstallationVO.getInstallationDataList();
			int i = 0 ;
			for(InstallationData installationData : listInstallationData){
				System.out.println(" ["+i +"] >> Inst code  = "+ installationData.getInstCode());
				System.out.println(" ["+i +"] >> TimeZoneId  = "+ installationData.getTimezoneId());
				System.out.println(" ["+i +"] >> TimeZoneShortName  = "+ installationData.getTimezoneShortName());
				System.out.println(" ["+i +"] >> TimeZoneOffset  = "+ installationData.getTimezoneOffset());
				List<InstallationEntity> instEntityList = installationData.getEntityList();
				for (InstallationEntity installationEntity : instEntityList) {
					System.out.println(" ["+i +"] >> Entity  = "+ installationEntity.getEntity());
				}
				List<ProgressLevelData> progressLevelDataList = installationData.getProgressLevelDataList();
				for (ProgressLevelData progressLevelData : progressLevelDataList) {
					System.out.println(" ["+i +"] >> Activity Type  = "+ progressLevelData.getPrgActivityType());
					
				}
				i++;
			}
			//assertNotNull(info);
			assertNotNull(listInstallationData);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
//			dao.releaseResources(null, null, connection);
		}
	}
	
	/**
	 * Tests Get system information   
	 */
	public void testGetInstallationDataForBatch(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResInstallationVO resInstallationVO = impl.getInstallationData(reqBatch);
			List<InstallationData> listInstallationData = resInstallationVO.getInstallationDataList();
			int i = 0 ;
			for(InstallationData installationData : listInstallationData){
				System.out.print(" ================================");
				System.out.print(" testGetInstallationDataForBatch");
				System.out.println(" ================================");
				System.out.println(" ["+i +"] >> Inst code  = "+ installationData.getInstCode());
				System.out.println(" ["+i +"] >> TimeZoneId  = "+ installationData.getTimezoneId());
				System.out.println(" ["+i +"] >> TimeZoneShortName  = "+ installationData.getTimezoneShortName());
				System.out.println(" ["+i +"] >> TimeZoneOffset  = "+ installationData.getTimezoneOffset());
				List<ProgressLevelData> progressLevelDataList = installationData.getProgressLevelDataList();
				for (ProgressLevelData progressLevelData : progressLevelDataList) {
					System.out.println(" ["+i +"] >> Activity Type  = "+ progressLevelData.getPrgActivityType());
					
				}
				i++;
			}
			//assertNotNull(info);
			assertNotNull(listInstallationData);
			
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetInstallationData.java                                             $
 * 
 * 7     7/14/10 5:42p Mandar.vaidya
 * Tested with latest data
 * 
 * 6     6/23/10 11:23a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao methods.
 * 
 * 5     4/27/10 3:17p Mandar.vaidya
 * Added Entity data to Installation data.
 * 
 * 4     4/14/10 1:56p Mandar.vaidya
 * JUnit test with latest data.
 * 
 * 3     4/14/10 1:39p Mandar.vaidya
 * JUnit test with latest data.
 * 
 * 2     4/13/10 2:16p Mandar.vaidya
 * Tested with latest data
 * 
 * 1     1/08/10 10:16a Grahesh
 * Initial Version
 * 
 * 3     1/06/10 5:05p Grahesh
 * Modified the implementation
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/