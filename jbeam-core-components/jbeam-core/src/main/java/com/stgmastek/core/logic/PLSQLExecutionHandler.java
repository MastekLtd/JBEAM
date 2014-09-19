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
package com.stgmastek.core.logic;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import stg.utils.StringUtils;

import com.stgmastek.core.common.ApplicationConnection;
import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IAppDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.PLSQLParam;
import com.stgmastek.core.util.Constants.OBJECT_STATUS;

/**
 * Class to execute PLSQL types of job.
 * 
 * Simple execution class that implements the {@link BaseExecutionHandler}
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public final class PLSQLExecutionHandler extends BaseExecutionHandler {

	private static Logger logger = Logger.getLogger(PLSQLExecutionHandler.class);

	/**
	 * Constructor that takes the batch object and its object map entry
	 * 
	 */
	protected PLSQLExecutionHandler() {
	}

	/**
	 * The main execution method for PLSQL batch jobs.
	 * <p />
	 * Way it works -
	 * <OL>
	 * <LI>Quantifies the parameters and the signature of the PLSQL object to be
	 * invoked
	 * <LI>Mark the object as 'UC'
	 * <LI>If the configuration for setting the global parameter is set in the
	 * CORE_CONFIG, then it would first set the global parameters i.e. call to
	 * the stored procedure set_core_app_val_lis(?, ?, ?, ?, ?)
	 * <LI>Using the JOB_SCHEDULE.JOB_DETAIL as set for the batch job it
	 * determines -
	 * <UL>
	 * <LI>The stored procedure to call
	 * <LI>The values of parameters as set
	 * <UL>
	 * <LI>Creates the callable string in the JAVA format for calling stored
	 * procedure {call PKG_1.PRC_1(?,?,? ...)}
	 * <LI>Sets the parameters as supplied and determined
	 * <LI>Executes the stored procedure
	 * <LI>After execution it checks the status (JOB_SCHEDULE.JOB_STATUS)of the
	 * object. If the object has marked itself '99' then it throws an exception,
	 * If not then it returns.
	 * </OL>
	 * 
	 * @param batchObject
	 *            The object under consideration.
	 * @param objectMapDetails
	 *            The object map details of the batch object under
	 *            consideration.
	 * @param batchContext
	 *            The context for the batch.
	 * @throws BatchException
	 *             Any exception occurred during the execution of the PLSQL
	 *             object.
	 * @see BaseExecutionHandler#execute(BatchObject, ObjectMapDetails,
	 *      BatchContext)
	 */
	public BatchObject execute(BatchObject batchObject, ObjectMapDetails objectMapDetails, BatchContext batchContext) throws BatchException {

		CallableStatement cstmt = null;

		// Get the procedure name
		List<PLSQLParam> params = getParamsList(batchObject.getTaskname(), objectMapDetails);
		String callableString = computeCallableString(params, objectMapDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Query Built [" + callableString + "] Parameters #" + params.size());
		}
		// Create the connection for execution
		ApplicationConnection appConnection = null;
		IAppDao dao = DaoFactory.getAppDao();
		try {
			appConnection = batchContext.getApplicationConnection();
			// First mark the status as 'UC'
			dao.markUC(appConnection, batchObject);

			String afterExecutionStatus = dao.checkBatchObjectStatus(batchObject, appConnection);

			// Second Step would be to set the global parameters if needed
			if (Constants.SET_GLOBAL_PARAMETERS) {
				dao.setGlobalParameters(appConnection, batchContext, batchObject);
			}

			// Third step would be execute the batch object
			cstmt = appConnection.prepareCall(callableString);

			// Set the parameters
			for (int i = 0; i < params.size(); i++) {
				PLSQLParam param = params.get(i);
				if (!param.getType().equals(":DT")) {
					cstmt.setObject(i + 1, !params.get(i).getValue().equals("") ? params.get(i).getValue() : null);
				} else {
					Date paramDtVal;
					try {
						paramDtVal = new SimpleDateFormat(Constants.BATCH_JOB_DATE_FORMAT).parse(param.getValue().replaceAll("'", ""));
						cstmt.setDate(i + 1, new java.sql.Date(paramDtVal.getTime()));
					} catch (Exception pe) {
						throw new BatchException(pe);
					}
				}
			}

			// Execute the stored procedure
			cstmt.execute();

			afterExecutionStatus = dao.checkBatchObjectStatus(batchObject, appConnection);
			OBJECT_STATUS status;
			try {
	            status = OBJECT_STATUS.resolve(afterExecutionStatus);
            } catch (IllegalArgumentException e) {
            	status = OBJECT_STATUS.FAILED;
            }
            batchObject.setStatus(status);
            if (status == OBJECT_STATUS.UNDERCONSIDERATION || status == OBJECT_STATUS.FAILED) {
            	throw new BatchException("Execution FAILED. Please check the table error log for further details");
            }
		} catch (SQLException sqe) {
			throw new BatchException(sqe);
		} finally {
			dao.releaseResources(null, cstmt, appConnection);
		}
		// Return the batch object with the updated status
		return batchObject;
	}

	/**
	 * Helper method to return the parameter list EX: N(1,2,3)would return
	 * [1,2,3]
	 * 
	 * @param taskName
	 *            the task name as string
	 * @return list of parameters
	 */
	private List<PLSQLParam> getParamsList(String taskName, ObjectMapDetails objectMapDetails) {

		List<PLSQLParam> retList = new ArrayList<PLSQLParam>();

		String objectName = objectMapDetails.getObjectName();

		// It is safe to do the sub string
		objectName = objectName.substring(objectName.indexOf("(") + 1, objectName.indexOf(")"));

		String[] paramTypes = StringUtils.split(objectName, ','); // Unlike
																  // String.split
																  // this may
																  // return
																  // length 0 if
																  // it was an
																  // empty
																  // string.

		// There are occurrences when parameter is passed from the batchObject
		// but is not
		// configured in the object map. In this case there are no parameters to
		// be supplied
		// when this would be the case then then the first element would be ""
		if (paramTypes.length == 0) {
			return retList;
		}
		if (paramTypes.length <= 1) {
			if ("".equals(paramTypes[0].trim())) {
				return retList;
			}
		}
		boolean variableParameters = false;
		if (paramTypes[paramTypes.length - 1].endsWith("...")) {
			variableParameters = true;
		}

		String paramList = "";
		int paramCount = 0;
		if (taskName.indexOf("(") >= 0) {
			paramList = taskName.substring(taskName.indexOf("(") + 1, taskName.indexOf(")"));

			char[] chars = paramList.toCharArray();
			StringBuilder sb = new StringBuilder();
			boolean addParameter = false;
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				switch (c) {
					case ',':
						addParameter = true;
						break;
					default:
						sb.append(c);
						addParameter = (i == (chars.length - 1));
						break;
				}
				if (addParameter) {
					retList.add(paramCount, new PLSQLParam(paramTypes[paramCount++], sb.toString()));
					if (paramCount == paramTypes.length) {
						if (variableParameters) {
							paramCount--;
						}
					}
					sb = new StringBuilder();
				}
				addParameter = false;
			}
		}
		// if (!variableParameters) {
		// for (; paramCount < paramTypes.length; paramCount++){
		// retList.add(paramCount, new PLSQLParam(paramTypes[paramCount], ""));
		// }
		// }
		return retList;

	}

	/**
	 * Helper method to construct the callable string
	 * 
	 * @param parameterList
	 *            The parameter list as a list of {@link PLSQLParam}
	 * @return the callable string
	 */
	private String computeCallableString(List<PLSQLParam> parameterList, ObjectMapDetails objectMapDetails) {

		StringBuilder sb = new StringBuilder();
		String objectName = objectMapDetails.getObjectName();
		sb.append("{");
		sb.append("call ");
		sb.append(objectName.substring(0, objectName.indexOf("(")));
		sb.append("(");
		for (int i = 0; i < parameterList.size(); i++)
			sb.append(i > 0 ? ",?" : "?");
		sb.append(")");
		sb.append("}");

		return sb.toString();
	}

}

/*
 * Revision Log ------------------------------- $Log::
 * /Product_Base/Projects/Batch
 * /Code/Java/Core/src/com/stgmastek/core/logic/PLSQLExecutionHandler.java $
 * 
 * 27 6/30/10 4:56p Kedarr Changed the method signature of the mark UC method to
 * accept the batch Object.
 * 
 * 26 6/30/10 4:31p Kedarr Added an SOP.
 * 
 * 25 6/30/10 3:31p Kedarr Dummy to be Roll backed.
 * 
 * 24 6/23/10 3:55p Kedarr Chagned the logic to mark the object as 99 in case
 * the status of the object is UC instead of 99.
 * 
 * 23 5/18/10 4:08p Kedarr reverted
 * 
 * 22 5/18/10 2:38p Kedarr Added functionality to add n null parameters if the
 * object is configured for n parameters where in the values in batch executor
 * have fewer parameters.
 * 
 * 21 4/28/10 10:22a Kedarr Updated javadoc
 * 
 * 20 4/19/10 4:31p Mandar.vaidya Changed the error message.
 * 
 * 19 4/16/10 4:59p Kedarr Reverted back the earlier changes to trim the space.
 * 
 * 18 4/16/10 4:34p Kedarr Changes made for variable parameters.
 * 
 * 17 4/16/10 2:16p Kedarr Changes made for variable parameters.
 * 
 * 16 3/09/10 3:17p Kedarr Changes made to use java sql connection as now
 * CConnection implements java sql connection. Also, Dao Factory is used to
 * fetch the appropriate dao
 * 
 * 15 3/03/10 5:39p Grahesh Removed batchContext.getApplicationConnection() from
 * called IBatchDao constructor and added in called methods.
 * 
 * 14 1/13/10 4:16p Grahesh Corrected the java doc comments
 * 
 * 13 1/07/10 6:20p Grahesh Updated Java Doc comments
 * 
 * 12 1/04/10 4:39p Grahesh Corrected the logic that checks whether the object
 * name from the object map has any parameters or not
 * 
 * 11 12/29/09 6:02p Grahesh Correcting the creation of the callable statement
 * string
 * 
 * 10 12/24/09 5:19p Grahesh Removed the single quote from date field during
 * execution.
 * 
 * 9 12/24/09 3:21p Grahesh Implemented the logic where by special execution
 * handler classes can be configured from the outside through configurations.
 * Though, the default implementation would be the core
 * 
 * 8 12/24/09 2:33p Grahesh Corrected the logic for BATCH_JOB_DATE_FORMAT
 * 
 * 7 12/23/09 3:57p Grahesh Moved the logic of object map details and type check
 * from special handler classes to super class
 * 
 * 6 12/23/09 1:53p Grahesh Corrected the logic for throwing exception. Changed
 * 'PC' to '99'
 * 
 * 5 12/23/09 12:32p Grahesh Changes done to create the callable not upon the
 * object map, but instead from the parameters supplied from the task name
 * 
 * 4 12/23/09 11:55a Grahesh Changes done to separate batch run date from batch
 * execution date time
 * 
 * 3 12/18/09 11:34a Grahesh Updated the comments
 * 
 * 2 12/17/09 11:46a Grahesh Initial Version
 */