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
package com.stgmastek.core.comm.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.core.comm.server.dao.IBatchDAO;
import com.stgmastek.core.comm.server.vo.CReqInstructionLog;
import com.stgmastek.core.comm.server.vo.InstructionParameters;
import com.stgmastek.core.comm.util.ConnectionManager;
import com.stgmastek.core.comm.util.DAOFactory;

/**
 * JUnit class to test the BatchDAO method addInstructionLog 
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestBatchDAOAddInstructionLog extends TestCase{

	CReqInstructionLog instructionLog = null;
	InstructionParameters instructionParameters;
	
	
	protected void setUp() throws Exception {	
		instructionLog = new CReqInstructionLog();
		
		instructionLog.setSeqNo(12);
		instructionLog.setMessage("BSRUNBATCH");
		instructionLog.setInstructingUser("ADMIN");
		instructionLog.setInstructionTime(new Date().getTime());
		
		
		List<InstructionParameters> instructionParametersList = new ArrayList<InstructionParameters>();
		for(int i = 1; i <= 6; i++){
			instructionParameters = new InstructionParameters();			
			instructionParameters.setSlNo(i);
			switch(i){
				case 1:
					//Insert Batch Name
					instructionParameters.setName("BATCH_NAME");
					instructionParameters.setValue("NEW BATCH");
					instructionParameters.setType("S");
					break;
				case 2:
					//Insert Batch Date
					instructionParameters.setName("BATCH_DATE");
					instructionParameters.setValue(String.valueOf(new Date().getTime()));
					instructionParameters.setType("DT");
					break;
				case 3:	
					//Insert Batch Type
					instructionParameters.setName("BATCH_TYPE");
					instructionParameters.setValue("SPECIAL");
					instructionParameters.setType("S");
	//				System.out.println(i + "] Expected Values is Batch Type  ");
					break;
				case 4:	
					//Insert Batch Type
					instructionParameters.setName("POLICY_1");
					instructionParameters.setValue("POL-98789");
					instructionParameters.setType("S");
	//				System.out.println(i + "] Expected Values is Batch Type  ");
					break;
				case 5:	
					//Insert Batch Type
					instructionParameters.setName("AGENCY_2");
					instructionParameters.setValue("AG-435");
					instructionParameters.setType("S");
	//				System.out.println(i + "] Expected Values is Batch Type  ");
					break;
				case 6:	
					//Insert Batch Type
					instructionParameters.setName("BATCH_END_DATE");
					instructionParameters.setValue(String.valueOf(new Date().getTime() + 60032334));
					instructionParameters.setType("DT");
	//				System.out.println(i + "] Expected Values is Batch Type  ");
					break;
			}
			
			instructionParametersList.add(instructionParameters);
			
		}
		instructionLog.setInstructionParametersList(instructionParametersList);
//		parameters.setId(2);
//		parameters.setBatchName("NEW BATCH 2 IN CORE");
//		parameters.setCreatedBy("ADMIN");
//		parameters.setBatchStartTime(new Date().getTime());
//		parameters.setCreatedOn(new Date().getTime());
//		parameters.setInstructionLog("POLICY=POL011;POLICY=POL012;POLICY=POL021;ACCOUNT=AC-23213;ACCOUNT=AC-233;AGENCY=AG-23");
	}
	
	
	
	/**
	 * Test only with mandatory fields  
	 */
	public void testAddinstructionLogMandatoryFields() throws Exception {		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
	try {
		connection = ConnectionManager.getInstance().getDefaultConnection();
		Integer i = dao.addInstructionLog(instructionLog,connection);
		
		assertNotNull(i);
	    }finally {
	    	dao.releaseResources(null, null, connection);
	    }
	}
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/test/TestBatchDAOAddInstructionLog.java                                                         $
 * 
 * 3     6/21/10 11:37a Lakshmanp
 * added the code to get dao from DAOFactory and passed the connection parameter to dao methods
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/