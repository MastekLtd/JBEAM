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
package com.stgmastek.core.comm.server.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.stgmastek.core.comm.server.base.ServiceBase;
import com.stgmastek.core.comm.server.vo.CBaseResponseVO;
import com.stgmastek.core.comm.server.vo.CReqCalendarLog;
import com.stgmastek.core.comm.server.vo.CReqInstruction;
import com.stgmastek.core.comm.server.vo.CReqInstructionLog;
import com.stgmastek.core.comm.server.vo.CReqProcessRequestScheduleVO;
import com.stgmastek.core.comm.server.vo.CResProcessRequestScheduleVO;

/**
 * The service (interface) class for all services needed by the monitor system to communicate 
 * to the core system. 
 * 
 * @author mandar.vaidya
 *
 */
@WebService
public interface CoreCommServices extends ServiceBase{
	
	
	/**
	 * Service to interrupt the batch proceedings 
	 * 
	 * @param instruction
	 * 		  The instruction for the batch 
	 * @return {@link CBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	public CBaseResponseVO interruptBatch (@WebParam(name="instruction") CReqInstruction instruction);
	
	
	/**
	 * Service to add instruction log with parameters 
	 *  
	 * @return {@link CBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	public CBaseResponseVO addInstructionLog(
			@WebParam(name="reqInstructionLog") CReqInstructionLog reqInstructionLog);
	
	/**
	 * Service to add calendar log with non-working days from JBEAM_MONITOR to JBEAM_CORE
	 *  
	 * @return {@link CBaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	public CBaseResponseVO addCalendarLog(
			@WebParam(name = "reqCalendarLog") CReqCalendarLog reqCalendarLog);
	
	/**
	 * Service to stop CORE-COMM services.
	 *  
	 */
	@WebMethod
	public void stopCoreComm();

	/**
	 * Service to retrieve the active schedule list for the supplied schedule ids (wrapped
	 * in {@link CReqProcessRequestScheduleVO}
	 * 
	 * @param requestScheduleVO
	 * 			The reference to {@link CReqProcessRequestScheduleVO}
	 * 
	 * @return list of active schedules from PROCESS_REQUEST_SCHEDULE table wrapped in 
	 * 			{@link CResProcessRequestScheduleVO}.
	 */
	@WebMethod
	public CResProcessRequestScheduleVO getProcessReqSchedule(CReqProcessRequestScheduleVO requestScheduleVO);
	
	/**
	 * Service to cancel the active schedule for the supplied schedule id (wrapped
	 * in {@link CReqProcessRequestScheduleVO}
	 * 
	 * @param requestScheduleVO
	 * 			The reference to {@link CReqProcessRequestScheduleVO}
	 * 
	 * @return list of active schedules from PROCESS_REQUEST_SCHEDULE table wrapped in 
	 * 			{@link CResProcessRequestScheduleVO}.
	 */
	@WebMethod
	public CResProcessRequestScheduleVO cancelCoreSchedule(CReqProcessRequestScheduleVO requestScheduleVO);
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/services/CoreCommServices.java                                                           $
 * 
 * 4     4/09/10 8:45a Mandar.vaidya
 * Added new Service addCalendarLog
 * 
 * 3     12/18/09 3:02p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/