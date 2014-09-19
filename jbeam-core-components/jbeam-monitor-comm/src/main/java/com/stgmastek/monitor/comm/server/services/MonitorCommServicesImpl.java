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


import java.sql.Connection;

import com.stgmastek.core.comm.client.CReqProcessRequestScheduleVO;
import com.stgmastek.core.comm.client.ScheduleData;
import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.base.ServiceBaseImpl;
import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.server.dao.IStatusDAO;
import com.stgmastek.monitor.comm.server.util.WSServerManager;
import com.stgmastek.monitor.comm.server.vo.BatchLog;
import com.stgmastek.monitor.comm.server.vo.BatchProgress;
import com.stgmastek.monitor.comm.server.vo.MBaseResponseVO;
import com.stgmastek.monitor.comm.server.vo.MReqBatch;
import com.stgmastek.monitor.comm.server.vo.MReqBatchLog;
import com.stgmastek.monitor.comm.server.vo.MReqBatchProgress;
import com.stgmastek.monitor.comm.server.vo.MReqInstructionLog;
import com.stgmastek.monitor.comm.server.vo.MReqSystemInfo;
import com.stgmastek.monitor.comm.util.CollatorKey;
import com.stgmastek.monitor.comm.util.Collators;
import com.stgmastek.monitor.comm.util.CommConstants;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;

/**
 * The service (implementation) class for all services in the monitor communication system 
 * 
 * @author mandar.vaidya
 * 
 */
public class MonitorCommServicesImpl extends ServiceBaseImpl implements MonitorCommServices {

	/**
	 * Service to update batch log
	 * 
	 * @param requestBatchLog
	 *        The batch log data @see {@link MReqBatchLog}
	 *        
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */	
	
	public MBaseResponseVO updateBatchLog(MReqBatchLog requestBatchLog) {
		IBatchDAO batchDao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			for(BatchLog log : requestBatchLog.getList()) {
				if (CommConstants.BATCH_LOG_OPERATION.BSADDBALOG.name().equals(
						log.getOperationCode())) {
					batchDao.addBatchLog(log,connection);
				} else if (CommConstants.BATCH_LOG_OPERATION.BSUPDBALOG.name()
						.equals(log.getOperationCode())) {
					batchDao.updateBatchLog(log,connection);
				}
			}
			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			batchDao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to add batch 
	 * 
	 * @param batch
	 *        The batch data @see {@link MReqBatch}
	 *        
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */	
	
	public MBaseResponseVO addBatch(MReqBatch batch) {
		IBatchDAO batchDao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchDao.addBatch(batch,connection);
			
			CollatorKey key = new CollatorKey();
			key.setInstallationCode(batch.getInstallationCode());
			key.setBatchNo(batch.getBatchNo());
			key.setBatchRevNo(batch.getBatchRevNo());
			Collators.getCollators().startRegisteredCollators(key);
			
			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			batchDao.releaseResources(null, null, connection);
		}
	}
	
	/**
	 * Service to update batch
	 * core_loader
	 * @param batch
	 *        The batch data @see {@link MReqBatch}
	 *        
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */	
	
	public MBaseResponseVO updateBatch(MReqBatch batch) {
		IBatchDAO batchDao = DAOFactory.getBatchDAO();
		Connection connection = null;

		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchDao.updateBatch(batch,connection);			
			if(batch.getExecEndTime() != null){
				CollatorKey key = new CollatorKey();
				key.setInstallationCode(batch.getInstallationCode());
				key.setBatchNo(batch.getBatchNo());
				key.setBatchRevNo(batch.getBatchRevNo());
				Collators.getCollators().stopRegisteredCollators(key);
				
			}
			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			batchDao.releaseResources(null, null, connection);
		}
	}
	
	/**
	 * Service to update batch progress
	 * 
	 * @param reqBatchProgress
	 * 		  The batch progress @see {@link MReqBatchProgress}
	 * 
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	
	public MBaseResponseVO updateBatchProgress(MReqBatchProgress reqBatchProgress) {
		IStatusDAO statusDAO = DAOFactory.getStatusDAO();
		Connection connection = null;

		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			for(BatchProgress batchProgress : reqBatchProgress.getBatchProgressList()) {
				if (CommConstants.BATCH_PROGRESS_LEVEL_OPERATION.SSADDBAPRG.name().equals(
						batchProgress.getOperationCode())) {
					statusDAO.addBatchProgress(batchProgress, connection);
				} else if (CommConstants.BATCH_PROGRESS_LEVEL_OPERATION.SSUPDBAPRG.name()
						.equals(batchProgress.getOperationCode())) {
					statusDAO.updateBatchProgress(batchProgress,connection);
				}
			}
			return getOKResponse();
			
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			statusDAO.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to update the monitor database with the system information 
	 * on which the batch is run 
	 * 
	 * @param systemInfo
	 * 		  The system information to be updated	
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	public MBaseResponseVO updateSystemInformation(MReqSystemInfo systemInfo){
		IStatusDAO statusDAO = DAOFactory.getStatusDAO();
		Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			statusDAO.updateSystemInformation(systemInfo,connection);
			
			return getOKResponse();
			
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			statusDAO.releaseResources(null, null, connection);
		}
	}
	/**
	 * Service to update instruction log
	 * 
	 * @param instructionLog
	 * 		  The instruction log data @see {@link MReqInstructionLog}
	 * 
	 * @return {@link MBaseResponseVO} to send the status of the request served
	 */
	public MBaseResponseVO updateInstructionLog(
			MReqInstructionLog instructionLog) {
		IBatchDAO batchDAO = DAOFactory.getBatchDAO();
		Connection connection = null;

		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchDAO.updateInstructionLog(instructionLog,connection);			
			return getOKResponse();
			
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			batchDAO.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to stop MONITOR-COMM services.
	 */
	public void stopMonitorComm() {
		WSServerManager.getInstance().stopAllServices();		
	}

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
	public MBaseResponseVO refreshProcessRequestSchedule(
			CReqProcessRequestScheduleVO requestScheduleVO) {
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			ScheduleData scheduleData = requestScheduleVO.getScheduleData();
			dao.insertProcessRequestSchedule(scheduleData, connection);
			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/services/MonitorCommServicesImpl.java                                              $
 * 
 * 8     6/18/10 12:30p Lakshmanp
 * added connection as parameter for dao methods and modified getting dao object from daofactory
 * 
 * 7     6/17/10 12:33p Kedarr
 * Needs further modifications
 * 
 * 6     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 5     3/19/10 3:03p Mandar.vaidya
 * Modified the implementation of updateBatchLog method.
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