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
package com.stgmastek.monitor.comm.server.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.stgmastek.core.comm.client.CReqProcessRequestScheduleVO;
import com.stgmastek.monitor.comm.server.base.ServiceBase;
import com.stgmastek.monitor.comm.server.vo.MBaseResponseVO;
import com.stgmastek.monitor.comm.server.vo.MReqBatch;
import com.stgmastek.monitor.comm.server.vo.MReqBatchLog;
import com.stgmastek.monitor.comm.server.vo.MReqBatchProgress;
import com.stgmastek.monitor.comm.server.vo.MReqInstructionLog;
import com.stgmastek.monitor.comm.server.vo.MReqSystemInfo;

/**
 * The service (interface) class for all services in the monitor communication system
 * 
 * @author mandar.vaidya
 *
 */
@WebService
public interface MonitorCommServices extends ServiceBase{
		
	/**
	 * Service to update batch log
	 * 
	 * @param requestBatchLog
	 * 		  The batch log data @see {@link MReqBatchLog}
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	MBaseResponseVO updateBatchLog(@WebParam(name="requestBatchLog")MReqBatchLog requestBatchLog);
	
	/**
	 * Service to add batch 
	 * 
	 * @param batch
	 * 		  The batch data @see {@link MReqBatch}
	 * 
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	MBaseResponseVO addBatch(@WebParam(name="batch")MReqBatch batch);
	
	/**
	 * Service to update batch
	 * 
	 * @param batch
	 * 		  The batch data @see {@link MReqBatch}
	 * 
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	MBaseResponseVO updateBatch(@WebParam(name="batch")MReqBatch batch);
	
	
	/**
	 * Service to update batch progress
	 * 
	 * @param reqBatchProgress
	 * 		  The batch progress @see {@link MReqBatchProgress}
	 * 
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	MBaseResponseVO updateBatchProgress(@WebParam(name="reqBatchProgress")MReqBatchProgress reqBatchProgress);	
	
	
	/**
	 * Service to update the monitor database with the system information 
	 * on which the batch is run 
	 * 
	 * @param systemInfo
	 * 		  The system information to be updated	
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	MBaseResponseVO updateSystemInformation(@WebParam(name="systemInfo")MReqSystemInfo systemInfo);	
	
	/**
	 * Service to update instruction log
	 * 
	 * @param instructionLog
	 * 		  The instruction log data @see {@link MReqInstructionLog}
	 * 
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	MBaseResponseVO updateInstructionLog(@WebParam(name="instructionLog")MReqInstructionLog instructionLog);
	
	/**
	 * Service to stop MONITOR-COMM services.
	 */
	@WebMethod
	public void stopMonitorComm();
	
	/**
	 * Service to update the monitor database with the latest schedule request.
	 * The data from core schema will be inserted in PROCESS_REQUEST_SCHEDULE table 
	 * of monitor schema. 
	 * 
	 * @param requestScheduleVO
	 * 		  The reference of {@link CReqProcessRequestScheduleVO}
	 * 	
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	public MBaseResponseVO refreshProcessRequestSchedule(
			@WebParam(name = "requestScheduleVO") CReqProcessRequestScheduleVO requestScheduleVO);
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/services/MonitorCommServices.java                                                  $
 * 
 * 4     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 3     12/18/09 4:16p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/