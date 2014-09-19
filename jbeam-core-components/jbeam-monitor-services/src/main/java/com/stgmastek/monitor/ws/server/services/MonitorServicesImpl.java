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
package com.stgmastek.monitor.ws.server.services;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import javax.mail.MessagingException;

import com.stgmastek.core.comm.client.CResProcessRequestScheduleVO;
import com.stgmastek.core.comm.client.ScheduleData;
import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.exception.CommException;
import com.stgmastek.monitor.ws.server.base.ServiceBaseImpl;
import com.stgmastek.monitor.ws.server.dao.IExecuteReportDAO;
import com.stgmastek.monitor.ws.server.dao.IMonitorDAO;
import com.stgmastek.monitor.ws.server.dao.IUserDAO;
import com.stgmastek.monitor.ws.server.util.WSServerManager;
import com.stgmastek.monitor.ws.server.vo.BaseResponseVO;
import com.stgmastek.monitor.ws.server.vo.BatchDetails;
import com.stgmastek.monitor.ws.server.vo.BatchInfo;
import com.stgmastek.monitor.ws.server.vo.BatchObject;
import com.stgmastek.monitor.ws.server.vo.ConfigParameter;
import com.stgmastek.monitor.ws.server.vo.DropDownEntry;
import com.stgmastek.monitor.ws.server.vo.ExecuteReport;
import com.stgmastek.monitor.ws.server.vo.InstallationData;
import com.stgmastek.monitor.ws.server.vo.InstallationEntity;
import com.stgmastek.monitor.ws.server.vo.InstructionLog;
import com.stgmastek.monitor.ws.server.vo.InstructionParameter;
import com.stgmastek.monitor.ws.server.vo.MenuData;
import com.stgmastek.monitor.ws.server.vo.MonitorCalendarData;
import com.stgmastek.monitor.ws.server.vo.ProcessRequestScheduleData;
import com.stgmastek.monitor.ws.server.vo.ProgressLevelData;
import com.stgmastek.monitor.ws.server.vo.Report;
import com.stgmastek.monitor.ws.server.vo.ReportParameter;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ReqBatchDetails;
import com.stgmastek.monitor.ws.server.vo.ReqCalendarVO;
import com.stgmastek.monitor.ws.server.vo.ReqInstallationVO;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;
import com.stgmastek.monitor.ws.server.vo.ReqListenerInfo;
import com.stgmastek.monitor.ws.server.vo.ReqProcessRequestScheduleVO;
import com.stgmastek.monitor.ws.server.vo.ReqSearchBatch;
import com.stgmastek.monitor.ws.server.vo.ReqUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.ReqVersionVO;
import com.stgmastek.monitor.ws.server.vo.ResBatchDataVO;
import com.stgmastek.monitor.ws.server.vo.ResBatchInfo;
import com.stgmastek.monitor.ws.server.vo.ResBatchObjectVO;
import com.stgmastek.monitor.ws.server.vo.ResCalendarVO;
import com.stgmastek.monitor.ws.server.vo.ResDropDownEntry;
import com.stgmastek.monitor.ws.server.vo.ResFailedObjectVO;
import com.stgmastek.monitor.ws.server.vo.ResGraphVO;
import com.stgmastek.monitor.ws.server.vo.ResInstallationEntitiesVO;
import com.stgmastek.monitor.ws.server.vo.ResInstallationVO;
import com.stgmastek.monitor.ws.server.vo.ResInstructionVO;
import com.stgmastek.monitor.ws.server.vo.ResListenerVO;
import com.stgmastek.monitor.ws.server.vo.ResMenuDetails;
import com.stgmastek.monitor.ws.server.vo.ResProcessRequestScheduleVO;
import com.stgmastek.monitor.ws.server.vo.ResRoleDetailsVO;
import com.stgmastek.monitor.ws.server.vo.ResSystemDetails;
import com.stgmastek.monitor.ws.server.vo.ResUserDetails;
import com.stgmastek.monitor.ws.server.vo.ResUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.ResUserProfile;
import com.stgmastek.monitor.ws.server.vo.RoleData;
import com.stgmastek.monitor.ws.server.vo.SystemDetails;
import com.stgmastek.monitor.ws.server.vo.UserCredentials;
import com.stgmastek.monitor.ws.server.vo.UserDetails;
import com.stgmastek.monitor.ws.server.vo.UserInstallationRole;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.BaseEmail;
import com.stgmastek.monitor.ws.util.CommonUtils;
import com.stgmastek.monitor.ws.util.Configurations;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.Constants;
import com.stgmastek.monitor.ws.util.DAOFactory;
import com.stgmastek.monitor.ws.util.NewUserEMail;
import com.stgmastek.monitor.ws.util.PasswordResetEmail;
import com.stgmastek.monitor.ws.util.corecomm.servicehandler.ScheduleHandler;

/**
 * The service (implementation) class for all services pertaining to the JBEAM
 * Monitor related operations
 * 
 * This class consists of the implementation of all the service declared in the
 * {@link MonitorServices} .
 * 
 * @author mandar.vaidya
 * 
 */
public class MonitorServicesImpl extends ServiceBaseImpl implements
		MonitorServices {

	/**
	 * Service to authenticate a JBEAM Monitor User. This service will be
	 * available to all users.
	 * 
	 * @param userCredentials
	 *            The user credentials
	 * @see UserCredentials
	 * @return The user profile
	 */

	public ResUserDetails userAuthentication(UserCredentials userCredentials) {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		ResUserDetails resUserDetails = new ResUserDetails();
		UserDetails userDetails = null;
		List<UserInstallationRole> userInstallationRoleList = null;
		ReqUserDetailsVO reqUserDetailsVO = null;
		UserProfile userProfile;

		try {
			if (userCredentials == null) {
				return getErrorResponse(ResUserDetails.class,
						new CommException(Constants.BATCH_DETAILS_NOT_FOUND));
			}
			userProfile = new UserProfile();
			userProfile.setUserId(userCredentials.getUserId());

			reqUserDetailsVO = new ReqUserDetailsVO();
			reqUserDetailsVO.setUserProfile(userProfile);

			connection = ConnectionManager.getInstance().getDefaultConnection();

			userCredentials.setPassword(CommonUtils
					.getEncryptedPassword(userCredentials.getPassword()));
			userDetails = dao.userAuthentication(userCredentials, connection);
			if (userDetails == null) {
				return getErrorResponse(ResUserDetails.class,
						new CommException(Constants.INVALID_USER_ID),
						Constants.INVALID_USER_ID);
			}
			if ("N".equalsIgnoreCase(userDetails.getConnectRole())) {
				return getErrorResponse(ResUserDetails.class,
						new CommException(Constants.ACCESS_DENIED));
			}

			userInstallationRoleList = dao.getUserInstallationRoleData(
					reqUserDetailsVO, connection);
			resUserDetails.setUserDetails(userDetails);

			if (userInstallationRoleList != null
					&& userInstallationRoleList.size() > 0) {
				resUserDetails
						.setUserInstallationRoleList(userInstallationRoleList);
			}
			return getOKResponse(resUserDetails);

		} catch (CommDatabaseException e) {
			return getErrorResponse(ResUserDetails.class, e);
		} catch (CommException e) {
			return getErrorResponse(ResUserDetails.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the installation data
	 * 
	 * @return The list of installation data as an instance of
	 *         {@link ResInstallationVO}
	 */
	public ResInstallationVO getInstallationData(ReqBatch batch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResInstallationVO resInstallationVO = new ResInstallationVO();
		try {
			if (batch == null) {
				return getErrorResponse(ResInstallationVO.class,
						new CommException(Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();

			List<InstallationData> installationsList = dao.getInstallationData(
					batch, connection);
			// Get the progress level for each installation item
			// that has the batch number and the batch revision number
			for (InstallationData instData : installationsList) {
				if (instData.getBNo() != null) {
					ReqBatch tmpBatch = new ReqBatch();
					tmpBatch.setInstallationCode(instData.getInstCode());
					tmpBatch.setBatchNo(instData.getBNo());
					tmpBatch.setBatchRevNo(instData.getBRevNo());
					List<ProgressLevelData> prgList = dao.getProgressLevelData(
							tmpBatch, connection);
					instData.setProgressLevelDataList(prgList);

				}
				List<InstallationEntity> entityList = dao.getBatchEntityData(
						instData.getInstCode(), connection);
				instData.setEntityList(entityList);

				TimeZone tz = TimeZone.getTimeZone(instData.getTimezoneId());
				instData.setTimezoneOffset(tz.getOffset(System
						.currentTimeMillis()));
				String shortName = tz.getDisplayName(tz
						.inDaylightTime(new Date()), TimeZone.SHORT);
				instData.setTimezoneShortName(shortName);
			}
			resInstallationVO.setInstallationDataList(installationsList);
			return getOKResponse(resInstallationVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResInstallationVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the installation data for the supplied batch that was run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The list of installation data as an instance of
	 *         {@link ResInstallationVO}
	 * 
	 */

	public ResInstallationVO getInstallationDetailsForBatch(ReqBatch batch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResInstallationVO resInstallationVO = new ResInstallationVO();

		try {
			if (batch == null) {
				return getErrorResponse(ResInstallationVO.class,
						new CommException(Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<InstallationData> installationsList = dao.getInstallationData(
					batch, connection);
			// Get the progress level for each installation item
			// that has the batch number and the batch revision number
			for (InstallationData instData : installationsList) {
				if (instData.getBNo() != null) {
					ReqBatch tmpBatch = new ReqBatch();
					tmpBatch.setInstallationCode(instData.getInstCode());
					tmpBatch.setBatchNo(instData.getBNo());
					tmpBatch.setBatchRevNo(instData.getBRevNo());
					List<ProgressLevelData> prgList = dao.getProgressLevelData(
							tmpBatch, connection);
					instData.setProgressLevelDataList(prgList);

				}
				List<InstallationEntity> entityList = dao.getBatchEntityData(
						instData.getInstCode(), connection);
				instData.setEntityList(entityList);

				TimeZone tz = TimeZone.getTimeZone(instData.getTimezoneId());
				instData.setTimezoneOffset(tz.getOffset(System
						.currentTimeMillis()));
				String shortName = tz.getDisplayName(tz
						.inDaylightTime(new Date()), TimeZone.SHORT);
				instData.setTimezoneShortName(shortName);
			}
			resInstallationVO.setInstallationDataList(installationsList);
			return getOKResponse(resInstallationVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResInstallationVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the system information for the supplied batch that was run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The system information as an instance of {@link ResSystemDetails}
	 */

	public ResSystemDetails getSystemInfo(ReqBatch batch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResSystemDetails resSystemDetails = new ResSystemDetails();
		SystemDetails systemDetails = null;
		try {
			if (batch == null) {
				return getErrorResponse(ResSystemDetails.class,
						new CommException(Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			systemDetails = dao.getSystemInfo(batch, connection);
			resSystemDetails.setSystemDetails(systemDetails);
			return getOKResponse(resSystemDetails);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResSystemDetails.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the batch data for the supplied batch that was run Gets
	 * instruction parameters list for the provided batch information (@link
	 * ReqBatch}. This list contains batch parameters for the SPECIAL run batch.
	 * 
	 * This method will get only SPECIAL batch parameters as below: <li>Create
	 * the installation data using reqBatch data.</li> <li>Get the entity list
	 * using this installation data.</li> <li>Gets the raw instruction
	 * parameters list using reqBatch data.</li> <li>If both the above mentioned
	 * lists are not null, then the required set of parameters are obtained by
	 * iterating through these lists</li>
	 * 
	 * @param batch
	 *            The batch information
	 * @return The batch data as an instance of {@link ResBatchInfo}
	 */

	public ResBatchInfo getBatchData(ReqBatch batch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResBatchInfo batchInfo = new ResBatchInfo();
		BatchDetails batchDetails = null;
		List<InstallationEntity> entityList;
		List<InstructionParameter> instructionParameterList;
		List<InstructionParameter> newInstructionParameterList = null;
		try {
			if (batch == null) {
				return getErrorResponse(ResBatchInfo.class, new CommException(
						Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchDetails = dao.getBatchData(batch, connection);
			entityList = dao.getBatchEntityData(batch.getInstallationCode(),
					connection);

			if (batch.getBatchNo() != null) {
				instructionParameterList = dao.createInstructionParamsList(
						batch, connection);
				if (instructionParameterList != null && entityList != null) {
					newInstructionParameterList = CommonUtils
							.getInstructionParameterList(
									instructionParameterList, entityList);
					if (newInstructionParameterList != null) {
						batchInfo
								.setInstructionParametersList(newInstructionParameterList);
					}
				}
			}

			batchInfo.setBatchDetails(batchDetails);

			return getOKResponse(batchInfo);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResBatchInfo.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the batch information data for the supplied batch that was
	 * run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The batch information as an instance of {@link BatchInfo}
	 */

	public ResBatchDataVO getBatchInfo(ReqBatch batch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResBatchDataVO batchDataVO = new ResBatchDataVO();
		List<BatchInfo> list;
		try {
			if (batch == null) {
				return getErrorResponse(ResBatchDataVO.class,
						new CommException(Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();

			list = dao.getBatchInfo(batch, connection);
			// Get the progress level for each installation item
			// that has the batch number and the batch revision number
			for (BatchInfo batchInfo2 : list) {
				if (batchInfo2.getBatchNo() != null) {
					ReqBatch tmpBatch = new ReqBatch();
					tmpBatch.setInstallationCode(batch.getInstallationCode());
					tmpBatch.setBatchNo(batchInfo2.getBatchNo());
					tmpBatch.setBatchRevNo(batchInfo2.getBatchRevNo());
					List<ProgressLevelData> prgList = dao.getProgressLevelData(
							tmpBatch, connection);
					batchInfo2.setProgressLevelDataList(prgList);

					List<InstallationEntity> entityList = dao
							.getBatchEntityData(batch.getInstallationCode(),
									connection);
					batchInfo2.setEntityList(entityList);
				}
			}

			batchDataVO.setBatchDataList(list);
			return getOKResponse(batchDataVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResBatchDataVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the listener details for the supplied batch that was run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The listener details as an instance of {@link ResListenerVO}
	 */

	public ResListenerVO getListenerInfo(ReqBatch batch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		try {
			if (batch == null) {
				return getErrorResponse(ResListenerVO.class, new CommException(
						Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			ResListenerVO listenerVO = new ResListenerVO();
			listenerVO.setListenerData(dao.getListenerInfo(batch, connection));
			return getOKResponse(listenerVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResListenerVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the failed object details for the supplied batch that was
	 * run
	 * 
	 * @param reqListenerInfo
	 *            The listener information for which the failed object details
	 *            are to be fetched
	 * @return The failed object details as an instance of
	 *         {@link ResFailedObjectVO}
	 */

	public ResFailedObjectVO getFaliedObjectDetails(
			ReqListenerInfo reqListenerInfo) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		try {
			if (reqListenerInfo == null) {
				return getErrorResponse(ResFailedObjectVO.class,
						new CommException(Constants.LISTENER_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			ResFailedObjectVO failedObjectVO = new ResFailedObjectVO();
			failedObjectVO.setFailedObjectsData(dao.getFaliedObjectDetails(
					reqListenerInfo, connection));
			return getOKResponse(failedObjectVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResFailedObjectVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the progress graph data for the supplied batch that was
	 * run
	 * 
	 * @param batch
	 *            The batch information
	 * 
	 * @return The batch progress data for graph as an instance of
	 *         {@link ResGraphVO}
	 */

	public ResGraphVO getGraphData(ReqBatch batch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		BatchDetails batchDetails = null;
		try {
			if (batch == null) {
				return getErrorResponse(ResGraphVO.class, new CommException(
						Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			ResGraphVO graphVO = new ResGraphVO();
			batchDetails = dao.getBatchData(batch, connection);
			graphVO.setGraphDataList(dao.getGraphData(batch, connection));
			graphVO.setBatchDetails(batchDetails);
			return getOKResponse(graphVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResGraphVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the batch entity (like PRE, POLICY, POST etc.) data for
	 * the supplied installation data
	 * 
	 * @param reqInstallationVO
	 *            The installation information
	 * 
	 * @return The batch entity data as an instance of
	 *         {@link ResInstallationEntitiesVO}
	 */

	public ResInstallationEntitiesVO getBatchEntityData(
			ReqInstallationVO reqInstallationVO) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResInstallationEntitiesVO installationEntitiesVO = new ResInstallationEntitiesVO();
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			if (reqInstallationVO == null) {
				return getErrorResponse(ResInstallationEntitiesVO.class,
						new CommException(
								Constants.INSTALLATION_DETAILS_NOT_FOUND));
			}
			installationEntitiesVO
					.setInstallationEntities(dao
							.getBatchEntityData(reqInstallationVO
									.getInstallationCode(), connection));
			return getOKResponse(installationEntitiesVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResInstallationEntitiesVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the completed batch data for the supplied search criteria.
	 * 
	 * @param searchBatch
	 *            The search criteria
	 * @return The list of completed batches a list of {@link ResBatchInfo}
	 */

	public ResBatchInfo getBatchCompletedData(ReqSearchBatch searchBatch) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResBatchInfo batchInfo = new ResBatchInfo();
		try {
			if (searchBatch == null) {
				return getErrorResponse(ResBatchInfo.class, new CommException(
						Constants.BATCH_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchInfo.setBatchDetailsList(dao.getBatchCompletedData(
					searchBatch, connection));
			return getOKResponse(batchInfo);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResBatchInfo.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to run the batch as per given instructions.
	 * 
	 * @param reqInstructionLog
	 *            The requested instruction log
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */

	public ResBatchInfo runBatch(ReqInstructionLog reqInstructionLog) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResBatchInfo resBatchInfo = null;
		Integer instructionLogSeqNo = 0;
		BatchDetails batchDetails = null;
		Boolean isRevisionBatch = false;
		try {
			if (reqInstructionLog == null) {
				return getErrorResponse(ResBatchInfo.class, new CommException(
						Constants.INSTRUCTION_DETAILS_NOT_FOUND));
			}
			
			connection = ConnectionManager.getInstance().getDefaultConnection();
			// When the batch is resumed, batch no and batch rev no are sent in
			// instruction parameters
			List<InstructionParameter> instructionParametersList = reqInstructionLog
					.getInstructionParameters();
			
			if(instructionParametersList != null) {
				for (InstructionParameter parameter : instructionParametersList) {
					if (parameter.getValue() != null
							&& !parameter.getValue().equals("")) {
						if ("BATCH_NO".equals(parameter.getName())) {
							reqInstructionLog.setBatchNo(Integer.parseInt(parameter
									.getValue()));
							isRevisionBatch = true;
						} else if ("BATCH_REV_NO".equals(parameter.getName())) {
							reqInstructionLog.setBatchRevNo(Integer
									.parseInt(parameter.getValue()));
						}
					}
				}
			}
			resBatchInfo = new ResBatchInfo();

			instructionLogSeqNo = dao.getInstructionLogSeqNo(connection);
			if (instructionLogSeqNo == null) {
				return getErrorResponse(ResBatchInfo.class, new CommException(
						Constants.INSTRUCTION_LOG_SEQUENCE_NUMBER_IS_NULL));
			}
			reqInstructionLog.setSeqNo(instructionLogSeqNo);

			int i = dao.insertInstructionLog(reqInstructionLog, connection,
					Constants.RUN_BATCH_MSG);
			// Add instruction parameters only if the instruction log is added
			// successfully.
			if (i == 1) {

				LinkedHashMap<String, String> entityValuesMap = CommonUtils
						.getInstructionParameterList(reqInstructionLog
								.getEntityValues());
				dao.addInstructionParameters(instructionLogSeqNo, reqInstructionLog,
						instructionParametersList, entityValuesMap, connection);
			}
			batchDetails = new BatchDetails();
			batchDetails.setInstallationCode(reqInstructionLog
					.getInstallationCode());				

			batchDetails.setInstructionSeqNo(instructionLogSeqNo);
			resBatchInfo.setRevisionBatch(isRevisionBatch);
			resBatchInfo.setBatchDetails(batchDetails);
			resBatchInfo.setScheduledBatch(CommonUtils
					.identifyScheduledBatch(reqInstructionLog));
			
			return getOKResponse(resBatchInfo);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResBatchInfo.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to stop the batch as per given instructions.
	 * 
	 * @param reqInstructionLog
	 *            The instruction to stop the batch
	 * 
	 * @return {@link ResInstallationVO} to send the status of the request
	 *         served
	 */

	public ResInstallationVO stopBatch(ReqInstructionLog reqInstructionLog) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResInstallationVO resInstallationVO = new ResInstallationVO();
		InstallationData installationData = null;
		try {
			if (reqInstructionLog == null) {
				return getErrorResponse(ResInstallationVO.class, new CommException(
						Constants.INSTRUCTION_DETAILS_NOT_FOUND));
			}
			
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			Integer instructionLogSeqNo = dao
					.getInstructionLogSeqNo(connection);
			if (instructionLogSeqNo == null) {
				return getErrorResponse(
						ResInstallationVO.class,
						new CommException(
								Constants.INSTRUCTION_LOG_SEQUENCE_NUMBER_IS_NULL));
			}
			reqInstructionLog.setSeqNo(instructionLogSeqNo);

			Integer stopBatchSuccess = dao.insertInstructionLog(
					reqInstructionLog, connection, Constants.STOP_BATCH_MSG);
			if (stopBatchSuccess == 1) {
				installationData = new InstallationData();
				installationData.setInstCode(reqInstructionLog
						.getInstallationCode());
			}
			resInstallationVO.setInstallationData(installationData);
			return getOKResponse(resInstallationVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResInstallationVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the details for given calendar data
	 * 
	 * @param calendarVO
	 *            The calendar data
	 * 
	 * @return {@link ResCalendarVO} the calendar details with non working days
	 *         list.
	 */

	public ResCalendarVO getCalendarData(ReqCalendarVO calendarVO) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResCalendarVO resCalendarVO = new ResCalendarVO();
		List<MonitorCalendarData> calendarDataList = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			if (calendarVO == null
					|| (calendarVO != null && calendarVO.getCalendarData() == null)) {
				return getErrorResponse(ResCalendarVO.class, new CommException(
						Constants.CALENDAR_DETAILS_NOT_FOUND));
			}
			calendarDataList = dao.getSingleCalendarDetails(calendarVO
					.getCalendarData(), connection);
			resCalendarVO.setCalendarList(calendarDataList);
			return getOKResponse(resCalendarVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResCalendarVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get all the existing calendars
	 * 
	 * @return {@link ResCalendarVO} the calendar details with non working days
	 *         list.
	 */

	public ResCalendarVO getCalendars(ReqCalendarVO calendarVO) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResCalendarVO resCalendarVO = new ResCalendarVO();
		List<MonitorCalendarData> calendarDataList = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			if (calendarVO == null
					|| (calendarVO != null && calendarVO.getCalendarData() == null)) {
				return getErrorResponse(ResCalendarVO.class, new CommException(
						Constants.CALENDAR_DETAILS_NOT_FOUND));
			}
			calendarDataList = dao.getCalendarDetails(calendarVO
					.getCalendarData(), connection);
			resCalendarVO.setCalendarList(calendarDataList);
			return getOKResponse(resCalendarVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResCalendarVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to define a calendar
	 * 
	 * @param calendarVO
	 *            The calendar data
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */

	public BaseResponseVO defineCalendar(ReqCalendarVO calendarVO) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		MonitorCalendarData calendarData = null;
		List<MonitorCalendarData> calendarDataList = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			if (calendarVO == null) {
				return getErrorResponse(new CommException(
						Constants.CALENDAR_DETAILS_NOT_FOUND));
			}
			calendarDataList = calendarVO.getCalendarList();
			calendarData = calendarVO.getCalendarData();

			if (calendarData != null)
				dao.deleteCalendar(calendarData, connection);

			if (calendarDataList != null) {
				int i = dao.addCalendar(calendarDataList, connection);
				if (i == 1) {
					dao.addCalendarMessageToOQueue(calendarData, connection);
				}
			}

			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the batch object details for the supplied batch that was
	 * run.
	 * 
	 * @param reqBatchDetails
	 *            The batch details
	 * 
	 * @return The list of batch object details as an instance of
	 *         {@link ResBatchObjectVO}
	 */

	public ResBatchObjectVO getBatchObjectDetails(
			ReqBatchDetails reqBatchDetails) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResBatchObjectVO resBatchObjectVO = new ResBatchObjectVO();
		List<BatchObject> batchObjectList = null;
		try {
			if (reqBatchDetails == null) {
				return getErrorResponse(ResBatchObjectVO.class,
						new CommException(Constants.USER_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchObjectList = dao.getBatchObjectDetails(reqBatchDetails,
					connection);
			resBatchObjectVO.setBatchObjectList(batchObjectList);
			return getOKResponse(resBatchObjectVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResBatchObjectVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the menu details for the supplied user details.
	 * 
	 * @param userProfile
	 *            The user details with the user role id
	 * 
	 * @return The menu details as an instance of {@link ResMenuDetails}
	 */

	public ResMenuDetails getMenuDetails(UserProfile userProfile) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResMenuDetails resMenuDetails = new ResMenuDetails();
		List<ResMenuDetails> menuList = null;
		try {
			if (userProfile == null) {
				return getErrorResponse(ResMenuDetails.class,
						new CommException(Constants.USER_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();
			menuList = new ArrayList<ResMenuDetails>();
			List<MenuData> priorMenuList = dao.getPriorMenuData(userProfile,
					connection);
			List<MenuData> list = dao.getMenuData(userProfile, connection);
			for (MenuData priorMenuData : priorMenuList) {
				ResMenuDetails parentMenu = new ResMenuDetails();
				parentMenu.setParentFunctionId(priorMenuData
						.getPriorFunctionId());
				List<MenuData> children = new ArrayList<MenuData>();
				for (MenuData menuData : list) {
					if (parentMenu.getParentFunctionId().equals(
							menuData.getPriorFunctionId())) {
						menuData.getFunctionName();
						children.add(menuData);
					}
				}
				parentMenu.setChildren(children);
				menuList.add(parentMenu);
			}
			resMenuDetails.setMenuList(menuList);
			if(userProfile.getInstallationCode() != null) {
				resMenuDetails.setInstallationCode(userProfile.getInstallationCode());
			}
			return getOKResponse(resMenuDetails);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResMenuDetails.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the configured roles.
	 * 
	 * @return The role details as an instance of {@link ResRoleDetailsVO}
	 */
	public ResRoleDetailsVO getRoleData() {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		ResRoleDetailsVO resRoleDetails = new ResRoleDetailsVO();
		List<RoleData> roleDataList = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			roleDataList = dao.getRoleData(connection);
			resRoleDetails.setRoleDataList(roleDataList);
			return getOKResponse(resRoleDetails);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResRoleDetailsVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to manage user. This method manages the user details and it's
	 * assigned installation role list. If the user already exists, then the
	 * user details are updated and the assigned installations and roles are
	 * deleted from the USER_INSTALLATION_ROLE table. Else the user details are
	 * inserted into USER_MASTER and USER_INSTALLATION_ROLE tables.
	 * 
	 * @param reqUserDetailsVO
	 *            The request object contains user details and it's assigned
	 *            installation role list
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */
	public BaseResponseVO manageUser(ReqUserDetailsVO reqUserDetailsVO) {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		String randomPassword;
		UserProfile userData;
		List<UserInstallationRole> userInstallationRoleList;
		Integer updateFlag = 0;
		try {

			if (reqUserDetailsVO == null
					|| (reqUserDetailsVO != null && reqUserDetailsVO
							.getUserProfile() == null)) {
				return getErrorResponse(new CommException(
						Constants.USER_DETAILS_NOT_FOUND));
			}
			// retrieve the user profile from ReqUserDetailsVO object
			userData = reqUserDetailsVO.getUserProfile();

			// retrieve the list of user, installations and roles from
			// ReqUserDetailsVO object
			userInstallationRoleList = reqUserDetailsVO
					.getUserInstallationRoles();

			// Get the connection
			connection = ConnectionManager.getInstance().getDefaultConnection();

			// Get the random password
			randomPassword = CommonUtils.generateRandomPassword();

			// Encrypt the random password and set it in retrieved user profile
			userData.setPassword(CommonUtils
					.getEncryptedPassword(randomPassword));

			// Get the user profile
			UserProfile userProfile = dao.getUserDetails(userData, connection);

			// If user does not exist then the userProfile object will be null.
			// Then insert user data in USER_MASTER table
			// Else update the existing user and delete the existing user
			// installation role details
			if (userProfile == null) {
				dao.addUser(userData, connection);
			} else {
				dao.updateUserByAdmin(userData, connection);
				dao.deleteUserInstallationRoleDetails(userData, connection);
				updateFlag = 1;
			}
			// If the userInstallationRoleList object contains any data insert
			// it in the user_installation_role table
			if (userInstallationRoleList != null
					&& userInstallationRoleList.size() > 0) {
				dao.addUserInstallationRoleDetails(userInstallationRoleList,
						connection);
			}

			try {
				BaseEmail email = null;
				UserProfile emailUserData = new UserProfile();
				emailUserData.setUserId(userData.getUserId());
				emailUserData.setUserName(userData.getUserName());
				emailUserData.setEmailId(userData.getEmailId());

				if (updateFlag == 0) {
					emailUserData.setPassword(randomPassword);
					email = new NewUserEMail(emailUserData);
				} else if (updateFlag == 1) {
					if (userData.getForcePasswordFlag() != null
							&& "Y".equals(userData.getForcePasswordFlag())) {
						emailUserData.setPassword(randomPassword);
						email = new PasswordResetEmail(emailUserData);
					}
				}
				if (email != null) {
					email.sendEmail();
				}
			} catch (MessagingException e) {
			} catch (IOException e) {
			}
			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} catch (CommException e) {
			return getErrorResponse(e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the existing user installation role details.
	 * 
	 * @param reqUserDetailsVO
	 *            The request contains user and installation details
	 * 
	 * @return The existing user installation role details as an instance of
	 *         {@link ResUserDetailsVO}
	 */
	public ResUserDetailsVO getUserInstallationRoleDetails(
			ReqUserDetailsVO reqUserDetailsVO) {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		ResUserDetailsVO resUserDetailsVO = new ResUserDetailsVO();
		List<UserInstallationRole> userInstallationRoles = null;
		UserProfile userProfile = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			userInstallationRoles = dao.getUserInstallationRoleData(
					reqUserDetailsVO, connection);
			if (reqUserDetailsVO != null) {
				userProfile = dao.getUserDetails(reqUserDetailsVO
						.getUserProfile(), connection);
				resUserDetailsVO.setUserProfile(userProfile);
			}
			resUserDetailsVO.setUserInstallationRoles(userInstallationRoles);
			return getOKResponse(resUserDetailsVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResUserDetailsVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the installations list
	 * 
	 * @return The list of installations as an instance of
	 *         {@link ResInstallationVO}
	 */

	public ResInstallationVO getInstallationsList() {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		ResInstallationVO resInstallationVO = new ResInstallationVO();
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			resInstallationVO.setInstallationsList(dao
					.getInstallationsList(connection));
			return getOKResponse(resInstallationVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResInstallationVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to update user profile. This service will be invoked when an user
	 * submits a request to change the profile.
	 * 
	 * @param reqUserDetailsVO
	 *            The request contains user details
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */

	public BaseResponseVO updateUserProfile(ReqUserDetailsVO reqUserDetailsVO) {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		UserProfile userProfile = null;
		String hintAnswer;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			if (reqUserDetailsVO == null) {
				return getErrorResponse(new CommException(
						Constants.USER_DETAILS_NOT_FOUND));
			}

			hintAnswer = reqUserDetailsVO.getUserProfile().getHintAnswer();
			userProfile = dao.getUserDetails(reqUserDetailsVO.getUserProfile(),
					connection);
			if (userProfile.getHintAnswer() == null
					|| (userProfile.getHintAnswer() != null && !(userProfile
							.getHintAnswer().equalsIgnoreCase(hintAnswer)))) {
				hintAnswer = CommonUtils.getEncryptedPassword(hintAnswer);
				reqUserDetailsVO.getUserProfile().setHintAnswer(hintAnswer);
			}
			dao.updateUser(reqUserDetailsVO.getUserProfile(), connection);

			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} catch (CommException e) {
			return getErrorResponse(e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to change the password.
	 * 
	 * @param userCredentials
	 *            The user credentials
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */

	public BaseResponseVO changePassword(UserCredentials userCredentials) {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		try {
			if (userCredentials == null) {
				return getErrorResponse(new CommException(
						Constants.USER_DETAILS_NOT_FOUND));
			}
			String oldPassword = CommonUtils
					.getEncryptedPassword(userCredentials.getPassword());
			String newPassword = CommonUtils
					.getEncryptedPassword(userCredentials.getNewPassword());
			if (oldPassword.equals(newPassword)) {
				return getErrorResponse(new CommException(
						Constants.INVALID_PASSWORD));
			}

			connection = ConnectionManager.getInstance().getDefaultConnection();
			userCredentials.setPassword(oldPassword);
			
			UserDetails userDetails = dao.userAuthentication(userCredentials, connection);
			
			if(userDetails == null) {
				return getErrorResponse(new CommException(
						Constants.INVALID_PASSWORD));				
			}
			userCredentials.setPassword(oldPassword);
			userCredentials.setNewPassword(newPassword);
			dao.changePassword(userCredentials, connection);
			return getOKResponse();
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} catch (CommException e) {
			return getErrorResponse(e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to reset the password. This service will reset the old password
	 * with new random password when the user has forgotten the password and
	 * sent a request to generate new password. This password will be reset only
	 * after getting the correct answer for the hint question.
	 * 
	 * @param userProfile
	 *            The user Profile
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */

	public BaseResponseVO resetPassword(UserProfile userProfile) {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		UserProfile userData = null;
		String hintAnswer;
		String randomPassword;
		UserCredentials userCredentials = null;
		try {
			userCredentials = new UserCredentials();
			connection = ConnectionManager.getInstance().getDefaultConnection();
			if (userProfile == null) {
				return getErrorResponse(new CommException(
						Constants.USER_DETAILS_NOT_FOUND));
			}
			hintAnswer = CommonUtils.getEncryptedPassword(userProfile
					.getHintAnswer());
			userData = dao.getUserDetails(userProfile, connection);
			if (userData == null) {
				return getErrorResponse(new CommException(
						Constants.USER_DETAILS_NOT_FOUND));
			}
			if (userData.getHintAnswer() != null
					&& userData.getHintAnswer().equals(hintAnswer)){
				randomPassword = CommonUtils.generateRandomPassword();
				
				userCredentials.setUserId(userProfile.getUserId());
				userCredentials.setNewPassword(CommonUtils
						.getEncryptedPassword(randomPassword));
				
				dao.resetPassword(userCredentials, connection);
				
				userProfile.setPassword(randomPassword);
				userProfile.setEmailId(userData.getEmailId());
				userProfile.setUserName(userData.getUserName());
				PasswordResetEmail email = new PasswordResetEmail(userProfile);
				email.sendEmail();
				return getOKResponse();				

			}else {
				return getErrorResponse(new CommException(
						Constants.INVALID_ANSWER));
			}
		} catch (CommDatabaseException e) {
			return getErrorResponse(e);
		} catch (CommException e) {
			return getErrorResponse(e);
		} catch (IOException e) {
			return getErrorResponse(e);
		} catch (MessagingException e) {
			return getErrorResponse(e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to get the security details for the supplied user data
	 * 
	 * @param userProfile
	 *            The user Profile
	 * 
	 * @return The user details as an instance of {@link ResUserProfile}
	 */

	public ResUserProfile getSecurityDetails(UserProfile userProfile) {
		IUserDAO dao = DAOFactory.getUserDAO();
		Connection connection = null;
		ResUserProfile resUserProfile;
		try {
			resUserProfile = new ResUserProfile();
			connection = ConnectionManager.getInstance().getDefaultConnection();
			userProfile = dao.getSecurityDetails(userProfile, connection);
			if (userProfile == null) {
				return getErrorResponse(ResUserProfile.class,
						new NullPointerException(Constants.INVALID_USER_ID));
			} else {
				if (userProfile != null
						&& userProfile.getHintQuestion() != null) {
					resUserProfile.setUserProfile(userProfile);
				} else {
					return getErrorResponse(ResUserProfile.class,
							new NullPointerException(
									Constants.USER_ID_NOT_REGISTERED));
				}
			}
			return getOKResponse(resUserProfile);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResUserProfile.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * This operation retrieve the reports for the given company.
	 * 
	 * @param userProfile
	 *            contains the user details.
	 * @return {@link ExecuteReport}
	 */
	public ExecuteReport retrieveReportDropDownOptionForCompany(
			UserProfile userProfile) {
		IExecuteReportDAO dao = DAOFactory.getExecuteReportDAO();
		Connection connection = null;
		ExecuteReport executeReport = new ExecuteReport();
		List<Report> reportsList = null;
		try {

			connection = ConnectionManager.getInstance().getDefaultConnection();
			reportsList = dao.getReports(userProfile, connection);
			executeReport.setReports(reportsList);
			return getOKResponse(executeReport);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ExecuteReport.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * This operation retrieve the retrieval type and details of report id.
	 * 
	 * @param report
	 *            contains the report details.
	 * @return {@link ExecuteReport}
	 */
	public ExecuteReport retrieveParametersForReport(Report report) {
		IExecuteReportDAO dao = DAOFactory.getExecuteReportDAO();
		Connection connection = null;
		ExecuteReport executeReports = new ExecuteReport();
		boolean reportType = false;
		String reportUrl = null;
		String reportPdfUrl = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();

			List<Report> reports = dao.getReportDetails(report, connection);

			if (reports == null) {
				throw new CommException(
						Constants.REPORT_MASTER_ERROR);
			}
			executeReports.setReports(reports);

			List<ReportParameter> reportParameters = dao
					.getReportParametesForReportId(report, connection);
			executeReports.setReportParameters(reportParameters);

			for (ReportParameter reportParameter : executeReports
					.getReportParameters()) {
				if (CommonUtils.checkNull(reportParameter.getDataType())
						.equals(Constants.DATATYPE_H)
						&& CommonUtils
								.checkNull(reportParameter.getQueryFlag())
								.equals(Constants.STATUS_Y)
						&& !CommonUtils.checkNull(reportParameter.getQuery())
								.equals("")) {
					String query = reportParameter.getQuery();
					query = query.substring(0, query.length() - 1);
					List<ConfigParameter> entities = dao.getEntity(query,
							connection);
					if (entities != null && entities.size() > 0) {
						reportParameter.setEntities(entities);
					}
					if (Constants.FAILURE_FLAG.equals(reportParameter
							.getSuccessFailureFlag())) {
						throw new CommException(
								Constants.REPORT_PARAMETER_ERROR);
					}
				}
			}
			if (Constants.FAILURE_FLAG.equals(CommonUtils
					.checkNull(executeReports.getSuccessFailureFlag()))) {
				throw new CommException(
						Constants.REPORT_PARAMETER_ERROR);
			}

			for (Report rpt : executeReports.getReports()) {
				if (Constants.PDF.equals(rpt.getReportType())) {
					reportType = true;
					break;
				}
			}

			if (reportType) {
				executeReports.setReportUrl(reportPdfUrl);
			} else {
				executeReports.setReportUrl(reportUrl);
			}
			return getOKResponse(executeReports);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ExecuteReport.class, e);
		} catch (CommException e) {
			return getErrorResponse(ExecuteReport.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Retrieve the list of Drop down entries based on the Drop down identifier
	 * list passed in.
	 * 
	 * @param dropDownIdentifierList
	 *            The list of the drop down identifiers.
	 * @return The instance of ({@link ResDropDownEntry} which contains the list of Drop down entries for the given Drop down
	 *         identifiers.
	 */
	public ResDropDownEntry retrieveDropDownEntries(
			List<ConfigParameter> dropDownIdentifierList) {
		ResDropDownEntry resDropDownEntry = new ResDropDownEntry();
		List<ConfigParameter> dropDownEntryInputList = dropDownIdentifierList;
		List<DropDownEntry> dropDownEntryResultList = new ArrayList<DropDownEntry>(
				dropDownEntryInputList.size());

		try {
			Configurations.getConfigurations().loadConfigurations();

			for (Iterator<ConfigParameter> iter = dropDownEntryInputList
					.iterator(); iter.hasNext();) {
				ConfigParameter configParameter = iter.next();
				if (Configurations.getConfigurations().processEntry(
						configParameter.getMasterCode(),
						dropDownEntryResultList)) {
					iter.remove();
				}
			}
			resDropDownEntry.setDropDownEntries(dropDownEntryResultList);
			return getOKResponse(resDropDownEntry);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResDropDownEntry.class, e);
		}
	}

	/**
	 * This operation process the request for the given report id.
	 * 
	 * @param executeReport
	 *            contains report details
	 *            
	 * @param reqInstructionLog
	 *            contains the instruction data with parameters.
	 *            
	 * @return {@link ResBatchInfo}
	 */
	public ResBatchInfo processRequest(ExecuteReport executeReport, 
			ReqInstructionLog reqInstructionLog) {
		IMonitorDAO monitorDao = DAOFactory.getMonitorDAO();
		ResBatchInfo resBatchInfo = null;
		Integer instructionLogSeqNo = 0;
		BatchDetails batchDetails = null;
		IExecuteReportDAO executeReportDAO = DAOFactory.getExecuteReportDAO();
		Connection connection = null;
		List<InstructionParameter> instructionParameters;
		try {
			if (executeReport == null) {
				return getErrorResponse(ResBatchInfo.class, new CommException(
						Constants.EXECUTE_REPORT_DETAILS_NOT_FOUND));
			}
			if (reqInstructionLog == null) {
				return getErrorResponse(ResBatchInfo.class, new CommException(
						Constants.INSTRUCTION_DETAILS_NOT_FOUND));
			}
			connection = ConnectionManager.getInstance().getDefaultConnection();

			List<Report> reportsList = executeReport.getReports();
			String reportName = null;
			if (reportsList != null && reportsList.size() > 0) {
				reportName = reportsList.get(0).getReportName();
			} else {
				reportName = executeReportDAO.getReportName(executeReport,
						connection);
			}

			if (reportName == null) {
				throw new CommException(
						Constants.REPORT_NAME_ERROR);
			}
			executeReport.setReportName(reportName);

			instructionParameters = new ArrayList<InstructionParameter>();
			Integer parameterCounter = 0;
			for (ReportParameter reportParameter : executeReport
					.getReportParameters()) {
				InstructionParameter parameter = new InstructionParameter();
				parameter.setSlNo(parameterCounter);
				parameter.setName(reportParameter.getParamName());
				parameter.setValue(reportParameter.getParamValue());
				parameter.setType(reportParameter.getParamDataType());
				instructionParameters.add(parameter);
				parameterCounter++;
			}

			reqInstructionLog.setInstructionParameters(instructionParameters);

			instructionLogSeqNo = monitorDao.getInstructionLogSeqNo(connection);
			if (instructionLogSeqNo == null) {
				return getErrorResponse(ResBatchInfo.class, new CommException(
						Constants.INSTRUCTION_LOG_SEQUENCE_NUMBER_IS_NULL));
			}
			reqInstructionLog.setSeqNo(instructionLogSeqNo);

			int i = monitorDao.insertInstructionLog(reqInstructionLog,
					connection, Constants.RUN_REPORT_MSG);
			// Add instruction parameters only if the instruction log is added
			// successfully.
			if (i == 1 && instructionParameters.size() > 0) {
				i = monitorDao.addInstructionParameters(instructionLogSeqNo, reqInstructionLog,
						instructionParameters, null, connection);
			}
			resBatchInfo = new ResBatchInfo();
			if(i == 1) {
				batchDetails = new BatchDetails();
				batchDetails.setInstallationCode(reqInstructionLog
						.getInstallationCode());
				batchDetails.setInstructionSeqNo(instructionLogSeqNo);
				
				resBatchInfo.setBatchDetails(batchDetails);
				resBatchInfo.setScheduledBatch(CommonUtils
						.identifyScheduledBatch(reqInstructionLog));
			}
			return getOKResponse(resBatchInfo);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResBatchInfo.class, e);
		} catch (CommException e) {
			return getErrorResponse(ResBatchInfo.class, e);
		} finally {
			executeReportDAO.releaseResources(null, null, connection);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.ws.server.services.MonitorServices#checkCompatibility(com.stgmastek.monitor.ws.server.vo.ReqVersionVO)
	 */
	
	public BaseResponseVO checkCompatibility(ReqVersionVO version) {
		try {
			if (version == null) {
				return getErrorResponse(new CommException("Requested version data not found"));
			}
			String majorVersion = Configurations.getConfigurations().getConfigurations("MONITOR_WS", "UI_VERSION", "MAJOR");
			String minorVersion = Configurations.getConfigurations().getConfigurations("MONITOR_WS", "UI_VERSION", "MINOR");
			if(majorVersion != null && minorVersion != null) {
				if(version.getMajorVersion().equals(majorVersion) && 
						version.getMinorVersion().equals(minorVersion)) {
					return getOKResponse();
				} else {
					return getErrorResponse(new CommException("Incompatible Version"));
				}				
			} else {
				return getErrorResponse(new CommException("Incompatible Version"));
			}				
		} catch (Exception e) {
			return getErrorResponse(new CommException("Exception while cheking version compatibility"));
		}
	}

	/**
	 * Service to details of instruction log for given instruction sequence number.
	 * 
	 * @param reqInstructionLog
	 *            The requested instruction log
	 * 
	 * @return The data for instruction details with sequence number as an instance of
	 *         {@link ResInstructionVO}.
	 */
	public ResInstructionVO checkInstructionLog(ReqInstructionLog reqInstructionLog) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResInstructionVO resInstructionVO = null;
		InstructionLog instructionLog = null;
		try {			
			if (reqInstructionLog == null) {
				return getErrorResponse(ResInstructionVO.class, new CommException(
						Constants.INSTRUCTION_DETAILS_NOT_FOUND));
			}
			resInstructionVO = new ResInstructionVO();
			connection = ConnectionManager.getInstance().getDefaultConnection();
			if(reqInstructionLog.getSeqNo() != null) {
				instructionLog = dao.getInstructionLog(reqInstructionLog.getSeqNo(), connection);
				if(instructionLog != null) {
					resInstructionVO.setInstructionLog(instructionLog);
				}
			}
			return getOKResponse(resInstructionVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResInstructionVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to stop MONITOR-SERVICES.
	 */
	public void stopMonitorServices() {
		WSServerManager.getInstance().stopMonitorServices();
		
	}

	/**
	 * Service to get all the records from PROCESS_REQUEST_SCHEDULE table.
	 * 
	 * @param reqScheduleVO
	 * 		  The reference to {@link ReqProcessRequestScheduleVO}. 
	 * 		  It contains installation code. 
	 * 
	 * @return {@link ResProcessRequestScheduleVO}
	 */
	@Override
	public ResProcessRequestScheduleVO getProcessRequestScheduleData(ReqProcessRequestScheduleVO reqScheduleVO) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResProcessRequestScheduleVO resScheduleVO = new ResProcessRequestScheduleVO();
		try {
			if(reqScheduleVO == null)
				return getErrorResponse(ResProcessRequestScheduleVO.class, new CommException(
						Constants.REQ_SCHEDULE_IS_NULL));
			
			connection = ConnectionManager.getInstance().getDefaultConnection();

			List<ProcessRequestScheduleData> processRequestScheduleList = 
					dao.getProcessRequestScheduleList(reqScheduleVO.getInstallationCode(), connection);
			resScheduleVO.setProcessRequestScheduleList(processRequestScheduleList);
			return getOKResponse(resScheduleVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResProcessRequestScheduleVO.class, e);
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Service to refresh all the records from PROCESS_REQUEST_SCHEDULE table for provided 
	 * schedule_ids.
	 * 
	 * @param reqScheduleVO
	 * 		  The reference to {@link ReqProcessRequestScheduleVO}. 
	 * 		  It contains installation code and processRequestSchedule data with schedule id (schId) 
	 * 
	 * @return The schedule data wrapped in {@link ResProcessRequestScheduleVO}
	 */
	@Override
	public ResProcessRequestScheduleVO refreshProcessRequestScheduleData(
			ReqProcessRequestScheduleVO reqScheduleVO) {
		
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResProcessRequestScheduleVO resScheduleVO = null;
		List<ProcessRequestScheduleData> reqScheduleList = null;
		

		try {
			
			if(reqScheduleVO == null)
				return getErrorResponse(ResProcessRequestScheduleVO.class, new CommException(
						Constants.REQ_SCHEDULE_IS_NULL));
			// Set the installation code for the core-comm services
			ScheduleHandler.getInstance().setService(reqScheduleVO.getInstallationCode());
			
			// Get the data for active schedules from core comm
			CResProcessRequestScheduleVO cResScheduleVO = ScheduleHandler
								.getInstance().getProcessRequestSchedule(reqScheduleVO);
			
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			// Update these records in monitor schema
			dao.updateProcessRequestSchedule(cResScheduleVO, connection);
			
			//Get the updated schedules list from monitor schema
			reqScheduleList = dao.getProcessRequestScheduleList(
					reqScheduleVO.getInstallationCode(), connection);

			//create response object
			resScheduleVO = new ResProcessRequestScheduleVO();
			
			//Set the updated schedules list from monitor schema in response
			resScheduleVO.setProcessRequestScheduleList(reqScheduleList);
			return getOKResponse(resScheduleVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResProcessRequestScheduleVO.class, e);
		} catch (CommException e) {
			return getErrorResponse(ResProcessRequestScheduleVO.class, e);	
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}
	
	/**
	 * Service to cancel a schedule for the provided schedule id (schId).
	 *  
	 * @param reqScheduleVO
	 * 		  The reference to {@link ReqProcessRequestScheduleVO}. 
	 * 		  It contains installation code and processRequestSchedule data with schedule id (schId) 
	 * 
	 * @return Updated schedule data wrapped in {@link ResProcessRequestScheduleVO}
	 */
	@Override
	public ResProcessRequestScheduleVO cancelSchedule(ReqProcessRequestScheduleVO reqScheduleVO) {
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		ResProcessRequestScheduleVO resProcessRequestScheduleVO = null;

		try {
			
			if(reqScheduleVO == null)
				return getErrorResponse(ResProcessRequestScheduleVO.class, new CommException(
						Constants.REQ_SCHEDULE_IS_NULL));
			
			// Set the installation code for the core-comm services
			ScheduleHandler.getInstance().setService(reqScheduleVO.getInstallationCode());
			
			// Cancel the active schedule if any in core schema and get the updated record in response.
			CResProcessRequestScheduleVO responseScheduleVO = 
				ScheduleHandler.getInstance().cancelSchedule(reqScheduleVO);
			
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			//Update the data in monitor schema using the updated schedule data from core schema.
			List<ScheduleData> resScheduleList = responseScheduleVO.getProcessRequestScheduleList();
			for (ScheduleData scheduleData : resScheduleList) {
				dao.updateProcessRequestSchedule(scheduleData, connection);				
			}
			
			//Get the updated schedules list of schedules from monitor schema
			List<ProcessRequestScheduleData> processRequestScheduleList = 
				dao.getProcessRequestScheduleList(reqScheduleVO.getInstallationCode(), connection);
			
			//Set the updated schedules list from monitor schema in response
			resProcessRequestScheduleVO = new ResProcessRequestScheduleVO();
			resProcessRequestScheduleVO.setInstallationCode(reqScheduleVO.getInstallationCode());
			resProcessRequestScheduleVO.setDescription(responseScheduleVO.getDescription());
			resProcessRequestScheduleVO.setProcessRequestScheduleList(processRequestScheduleList);
			return getOKResponse(resProcessRequestScheduleVO);
		} catch (CommDatabaseException e) {
			return getErrorResponse(ResProcessRequestScheduleVO.class, e);
		} catch (CommException e) {
			return getErrorResponse(ResProcessRequestScheduleVO.class, new CommException("Could not send message to Core-comm."));			
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

}
/*
 * Revision Log ------------------------------- $Log::
 * /Product_Base/Projects/Batch
 * /Code/Java/MonitorServices/src/com/stgmastek/monitor
 * /ws/server/services/MonitorServicesImpl.java $
 * 
 * 30 7/14/10 5:52p Mandar.vaidya Added server side validation for CONNECT role
 * in userAuthentication method Added senderEmailId (derviced from Configuration
 * table) in manageUser and set in Email
 * 
 * 29 7/13/10 4:24p Mandar.vaidya Modified the implementation of
 * userAuthentication method to get user installation role details and set to
 * ResUserDetails.
 * 
 * 28 7/10/10 2:17p Mandar.vaidya Added code to get instructionParametersList
 * from getBatchData service
 * 
 * 27 7/09/10 9:26a Kedarr Changes made to make use of the PRE Mailer
 * 
 * 26 7/08/10 3:18p Mandar.vaidya Renamed the service createUser as manageUser
 * 
 * 25 7/07/10 5:41p Mandar.vaidya Modified the method
 * getUserInstallationRoleDetails to getUserProfile
 * 
 * 24 7/02/10 3:38p Lakshmanp modified createUser method to set random password
 * and send an email
 * 
 * 23 7/01/10 5:16p Mandar.vaidya Added service to get the existing user
 * installation role details.
 * 
 * 22 7/01/10 5:10p Lakshmanp Changed to generate random password
 * 
 * 21 6/30/10 3:14p Lakshmanp Added service to create user.
 * 
 * 20 6/25/10 3:55p Lakshmanp added request role details param
 * 
 * 19 6/25/10 2:11p Lakshmanp added implementataion method get role data to get
 * roles details.
 * 
 * 18 6/23/10 11:15a Lakshmanp modified the dao object as from DAOFactory, added
 * connection object to dao method and handled connection leak.
 * 
 * 17 6/10/10 4:02p Mandar.vaidya Added new webMethod getMenuDetails for getting
 * menu for Batch details as per the logged in user role.
 * 
 * 16 4/08/10 5:03p Mandar.vaidya Added webparam for getCalendars webmethod
 * 
 * 15 4/01/10 4:32p Mandar.vaidya Changed return type of runBatch from
 * BaseResponseVO to ResBatchInfo.
 * 
 * 14 3/17/10 4:36p Mandar.vaidya Added getBatchObjectDetails web method.
 * 
 * 13 2/24/10 9:47a Grahesh Removed a method for schedule batch
 * 
 * 12 2/18/10 7:49p Grahesh Implementation for Calendar Module
 * 
 * 11 1/13/10 4:23p Grahesh Corrected the java doc comments
 * 
 * 10 1/08/10 10:15a Grahesh Corrected the signature and object hierarchy
 * 
 * 9 1/06/10 5:05p Grahesh Corrected the signature and object hierarchy
 * 
 * 8 1/06/10 11:31a Mandar.vaidya Corrected the signature and object hierarchy
 * 
 * 7 1/06/10 10:49a Grahesh Changed the object hierarchy
 * 
 * 6 12/30/09 4:01p Grahesh Implementation for stopping the batch
 * 
 * 5 12/30/09 3:55p Grahesh Implementation for stopping the batch
 * 
 * 4 12/30/09 2:07p Grahesh Dumping the stack trace onto the console
 * 
 * 3 12/30/09 1:07p Grahesh Corrected the javadoc for warnings
 * 
 * 2 12/17/09 12:01p Grahesh Initial Version
 */