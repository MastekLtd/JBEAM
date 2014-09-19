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
import com.stgmastek.monitor.ws.server.vo.ConfigParameter;

/**
 * JUnit class to test the Monitor Services retrieveDropDownEntries 
 * 
 * @author Mandar Vaidya
 *
 */
public class TestMonitorServicesRetrieveDropDownEntries extends TestCase{

	private List<ConfigParameter> dropDownIdentifierList;

	/**
	 * Tests retrieveDropDownEntries   
	 */
	public void testRetrieveDropDownEntries(){
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			impl.retrieveDropDownEntries(dropDownIdentifierList);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	
	protected void setUp() throws Exception {
		dropDownIdentifierList = new ArrayList<ConfigParameter>();
		ConfigParameter configParameter1 = new ConfigParameter();
		configParameter1.setMasterCode("FREQUENCY");
		dropDownIdentifierList.add(configParameter1);
		ConfigParameter configParameter2 = new ConfigParameter();
		configParameter2.setMasterCode("MAIL");
		dropDownIdentifierList.add(configParameter2);
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchInfo.java                                                    $
 * 
*
*
*/