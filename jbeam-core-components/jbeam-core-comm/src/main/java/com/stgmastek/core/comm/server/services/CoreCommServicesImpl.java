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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.exception.CommException;
import com.stgmastek.core.comm.server.base.ServiceBaseImpl;
import com.stgmastek.core.comm.server.dao.IBatchDAO;
import com.stgmastek.core.comm.server.dao.IStatusDAO;
import com.stgmastek.core.comm.server.util.WSServerManager;
import com.stgmastek.core.comm.server.vo.CBaseResponseVO;
import com.stgmastek.core.comm.server.vo.CReqCalendarLog;
import com.stgmastek.core.comm.server.vo.CReqInstruction;
import com.stgmastek.core.comm.server.vo.CReqInstructionLog;
import com.stgmastek.core.comm.server.vo.CReqProcessRequestScheduleVO;
import com.stgmastek.core.comm.server.vo.CResProcessRequestScheduleVO;
import com.stgmastek.core.comm.server.vo.ScheduleData;
import com.stgmastek.core.comm.util.CommConstants;
import com.stgmastek.core.comm.util.ConnectionManager;
import com.stgmastek.core.comm.util.DAOFactory;
import com.stgmastek.core.comm.util.messagehandler.CoreScheduleHandler;

/**
 * The service (implementation) class for all services needed by the monitor system to communicate 
 * to the core system. 
 * 
 * @author mandar.vaidya
 *
 */
public class CoreCommServicesImpl extends ServiceBaseImpl implements CoreCommServices{
	
	/**
	 * Service to interrupt the batch proceedings 
	 * 
	 * @param instruction
	 * 		  The instruction for the batch 
	 * @return {@link CBaseResponseVO} to send the status of the request served
	 */
	public CBaseResponseVO interruptBatch(CReqInstruction instruction) {
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			dao.interruptBatch(instruction,connection);
			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to add instruction log with parameters from JBEAM_MONITOR to JBEAM_CORE
	 *  
	 * @return {@link CBaseResponseVO} to send the status of the request served
	 */
	public CBaseResponseVO addInstructionLog(CReqInstructionLog reqInstructionLog) {
			IBatchDAO dao = DAOFactory.getBatchDAO();
			Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			Integer requestId = dao.addInstructionLog(reqInstructionLog,connection);
			ScheduleData scheduleData = dao.getProcessRequestScheduleData(requestId, connection);
			if(scheduleData != null){
				CReqProcessRequestScheduleVO requestScheduleVO = new CReqProcessRequestScheduleVO();
				scheduleData.setInstallationCode(CommConstants.INSTALLATION_CODE);
				requestScheduleVO.setScheduleData(scheduleData);
				CoreScheduleHandler.getInstance().setService();				
				CoreScheduleHandler.getInstance().refreshScheduleData(requestScheduleVO);				
			}
			return getOKResponse();			
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} catch (Throwable e) {
			return getErrorResponse(new CommException(e));
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to add calendar log with non-working days from JBEAM_MONITOR to JBEAM_CORE
	 *  
	 * @return {@link CBaseResponseVO} to send the status of the request served
	 */
	public CBaseResponseVO addCalendarLog(CReqCalendarLog reqCalendarLog) {
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;		
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			dao.defineCalendar(reqCalendarLog,connection);
			return getOKResponse();			
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to stop CORE-COMM services.
	 *  
	 */
	public void stopCoreComm() {
		WSServerManager.getInstance().stopAllServices();
	}

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
	public CResProcessRequestScheduleVO getProcessReqSchedule(CReqProcessRequestScheduleVO requestScheduleVO) {		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		List<ScheduleData> activeProcessRequestScheduleList = new ArrayList<ScheduleData>(); 
		CResProcessRequestScheduleVO resProcessRequestScheduleVO = new CResProcessRequestScheduleVO();
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();

			List<ScheduleData> processRequestScheduleList = requestScheduleVO.getProcessRequestScheduleList();
			for (ScheduleData requestScheduleData : processRequestScheduleList) {
				ScheduleData scheduleData = dao.getProcessRequestScheduleData(requestScheduleData, connection);
				scheduleData.setInstallationCode(CommConstants.INSTALLATION_CODE);
				activeProcessRequestScheduleList.add(scheduleData);
			}				
			resProcessRequestScheduleVO.setProcessRequestScheduleList(activeProcessRequestScheduleList);
			return getOKResponse(resProcessRequestScheduleVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(CResProcessRequestScheduleVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

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
	public CResProcessRequestScheduleVO cancelCoreSchedule(
			CReqProcessRequestScheduleVO requestScheduleVO) {
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;		
		CResProcessRequestScheduleVO resProcessRequestScheduleVO = new CResProcessRequestScheduleVO();
		List<ScheduleData> scheduleList = new ArrayList<ScheduleData>();
		ScheduleData resScheduleData = null;
		String scheduleMessage = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<ScheduleData> requestScheduleList = requestScheduleVO.getProcessRequestScheduleList();
			StringBuilder builder = new StringBuilder();
			for (ScheduleData reqScheduleData : requestScheduleList) {
				Integer[] status = dao.cancelSchedule(reqScheduleData,connection);
				resScheduleData = dao.getProcessRequestScheduleData(reqScheduleData, connection);
				if(resScheduleData != null){
					resScheduleData.setInstallationCode(CommConstants.INSTALLATION_CODE);					
					scheduleMessage = CoreScheduleHandler.getInstance().getScheduleMessage(
									resScheduleData.getSchId(), status, resScheduleData.getSchStat());
					builder.append(scheduleMessage);
					if(requestScheduleList.size() > 1){
						builder.append("\n================================\n");			
					}
					scheduleList.add(resScheduleData);
				}				
			}
			resProcessRequestScheduleVO.setDescription(builder.toString());
			resProcessRequestScheduleVO.setProcessRequestScheduleList(scheduleList);
			
			return getOKResponse(resProcessRequestScheduleVO);			
		} catch (CommDatabaseException e) {
			return getErrorResponse(CResProcessRequestScheduleVO.class, e);
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}

	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/services/CoreCommServicesImpl.java                                                       $
 * 
 * 5     6/21/10 11:36a Lakshmanp
 * added the code to get dao from DAOFactory and passed the connection parameter to dao methods
 * 
 * 4     4/09/10 8:48a Mandar.vaidya
 * Added new Service implementation for addCalendarLog
 * 
 * 3     12/18/09 3:02p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/