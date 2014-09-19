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
import com.stgmastek.monitor.ws.server.vo.MonitorCalendarData;
import com.stgmastek.monitor.ws.server.vo.ReqCalendarVO;
import com.stgmastek.monitor.ws.server.vo.ResCalendarVO;

/**
 * JUnit class to test the MonitorServices method GetCalendarData  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorServicesGetCalendarData extends TestCase{
	ReqCalendarVO calendarVO;
	
	
	protected void setUp() throws Exception {
		calendarVO = new ReqCalendarVO();
		MonitorCalendarData calendarData = new MonitorCalendarData();
		
		calendarData.setInstallationCode("BILLING-DV");
		calendarData.setCalendarName("Batch Calendar");
		calendarData.setYear("2010");
		
		calendarVO.setCalendarData(calendarData);
	}

	/**
	 * Tests Get Calendar Data 
	 */
	public void testGetCalendarData(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResCalendarVO resCalendarVO = impl.getCalendarData(calendarVO);
			List<MonitorCalendarData> list = resCalendarVO.getCalendarList();
			for (MonitorCalendarData calendarData : list) {
				System.out.println("Calendar Data = " + calendarData.getNonWorkingDate());
			}
			assertNotNull(resCalendarVO);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Tests Get Calendar Data 
	 */
	public void testGetCalendars(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResCalendarVO resCalendarVO = impl.getCalendars(calendarVO);
			List<MonitorCalendarData> list = resCalendarVO.getCalendarList();
			for (MonitorCalendarData calendarData : list) {
				System.out.println("Calendar Data = " + calendarData.getYear());
			}
			assertNotNull(resCalendarVO);
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
*/