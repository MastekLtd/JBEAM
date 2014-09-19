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
package com.stgmastek.core.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.stgmastek.core.logic.Processor;
import com.stgmastek.core.util.Constants;

/**
 * The Core would be normally initiated and executed from the Processor class
 * Though for testing purpose this class is introduced. 
 * 
 */
public class StartCore {

	static Date today = new Date();
	
	public static void main(String[] args) throws Exception{
		Constants.MODE = Constants.DEV;
		long timeIn = System.currentTimeMillis();
		getProcessor().processRequest();
		long timeOut = System.currentTimeMillis();
		System.out.println("Time taken ----------------------->" + (timeOut - timeIn));
	}

	
	static Processor getProcessor() throws Exception{
		Processor processor = new Processor();
		HashMap<String, Object> map = new HashMap<String, Object>();
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.DAY_OF_MONTH, 50);
//		Date startDate = c.getTime();		
//		Date startDate = new Date();
//		map.put("BATCH_DATE", startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		map.put(Constants.PROCESS_REQUEST_PARAMS.BATCH_RUN_DATE.name(), sdf.parse("03-MAR-2010 23:59:59"));
		
//		map.put(Constants.PROCESS_REQUEST_PARAMS.BATCH_NAME.name(), "dsdf");
//		map.put(Constants.PROCESS_REQUEST_PARAMS.BATCH_TYPE.name(), "DATE");
		
		
		
//		map.put("GENERAL_1", "366224");
//		map.put("GENERAL_2", "368057");
//		map.put("GENERAL_3", "368335");
		
				
//		map.put(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name(), 1109);
//		map.put("BATCH_ENDS_IN_MINUTES", 1);//The time the current batch should stop
		
		
//		map.put("BATCH_NO", 699);
		
		
//		map.put("BATCH_NO", 347);
//		map.put("BATCH_REV_NO", 1);
//		Date endDate = new Date(startDate.getTime() + ( 1 * 30 * 1000));
//		System.out.println("The batch starts at : " + startDate);
//		System.out.println("The batch ends at : " + endDate);
//		map.put("BATCH_ENDS_IN_MINUTES", 1 * 30 * 1000);//The time the current batch should stop
//		map.put("POLICY_1", "NYHP0002");
//		map.put("POLICY_2", "PL-1");
//		map.put("POLICY_3", "PL-4");
//		map.put("POLICY_4", "PL-5");		
//		map.put("POLICY_2", "NYHO00004");
//		map.put("POLICY_2", "NYCA1000");
//		map.put("POLICY_1", "ALL");
//		map.put("POLICY_1", "NYA0002");
//		map.put("POLICY_2", "MCT0000100");
//		map.put("POLICY_3", "PLR-1");
//		map.put("POLICY_4", "PLR-2");
//		map.put("POLICY_1", "ALL");
//		map.put("AGENCY_1", "AG-12346");
//		map.put("ACCOUNT_1", "ABC123");
//		map.put("CLAIM_1", "ALL");
//		map.put("GENERAL_1", "229");
//		map.put("POLICY_3", "C223");
////		map.put("POLICY_4", "D123");
//		map.put("AGENCY_1", "ALL");
//		map.put("ACCOUNT_1", "ALL");
////		map.put("ACCOUNT_2", "ABC123");
//		map.put("CLAIM_1", "ALL");
////		map.put("CLAIM_2", "CL234");
//		map.put("PRE_1", "ALL");
		map.put("POST_1", "ALL");
		processor.setParams(map);
		processor.setRequestId(100);
		return processor;	
	}
 
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/main/StartCore.java                                                                                      $
 * 
 * 6     3/05/10 5:23p Mandar.vaidya
 * Change in batch run date
 * 
 * 5     1/06/10 1:13p Grahesh
 * Formatting of the batch run date
 * 
 * 4     12/23/09 1:53p Grahesh
 * Using BATCH_ENDS_IN_MINUTES instead of BATCH_END_TIME
 * 
 * 3     12/23/09 11:55a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/