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
import com.stgmastek.monitor.ws.server.vo.BaseResponseVO;
import com.stgmastek.monitor.ws.server.vo.MonitorCalendarData;
import com.stgmastek.monitor.ws.server.vo.ReqCalendarVO;

/**
 * JUnit class to test the MonitorServices method DefineCalendar 
 * 
 * @author Mandar Vaidya
 *
 */
public class TestMonitorServicesDefineCalendar extends TestCase{
	ReqCalendarVO calendarVO;
	
	
	protected void setUp() throws Exception {
		calendarVO = new ReqCalendarVO();
		MonitorCalendarData calendarData = new MonitorCalendarData();		
		calendarData.setInstallationCode("BILLING-DV");
		calendarData.setCalendarName("Batch Calendar");
		calendarData.setYear("2010");
		
		
		calendarData.setNonWorkingDate(new Long(1273429800000L));
		calendarData.setNonWorkingDate(new Long(1277663400000L));
		calendarData.setNonWorkingDate(new Long(1283884200000L));
		calendarData.setNonWorkingDate(new Long(1286735400000L));
		calendarData.setNonWorkingDate(new Long(1280341800000L));
		calendarData.setNonWorkingDate(new Long(1276540200000L));
		calendarVO.setCalendarData(calendarData);
	}

	/**
	 * Tests Define Calendar 
	 */
	public void testDefineCalendar(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			BaseResponseVO responseVO = impl.defineCalendar(calendarVO);
			System.out.println("Response Data = " + responseVO.getResponseType());
			
			assertNotNull(responseVO);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchData.java                                                    $
 * 
*
*/