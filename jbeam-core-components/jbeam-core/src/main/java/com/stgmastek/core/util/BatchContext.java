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
package com.stgmastek.core.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import stg.pr.engine.PREContext;
import stg.pr.engine.datasource.IDataSourceFactory;

import com.stgmastek.core.common.ApplicationConnection;
import com.stgmastek.core.common.BatchConnection;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.Constants.CONTEXT_KEYS;
 
/**
 * The context for the batch to be executed.
 * All parameters and processing information would be stored in this class for 
 * usage in various classes. Provides a uniform mechanism for accessing 
 * appropriate database connections 
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public class BatchContext implements Serializable {

	/** The Serial Version UID */
	private static final long serialVersionUID = 6762648622116192479L;

	/** The PRE Context of the scheduled job */
	private transient PREContext preContext = null;
		
	/** The current batch information */
	private transient BatchInfo batchInfo = null;
	
	/** 
	 * The batch parameters. 
	 * These parameters are initially provided Process Request Engine framework 
	 * and masked into BatchParams  
	 * Along with the parameters that are set in the PROCESS_REQ_PARAMS table, 
	 * it has additional information for system usage.
	 * For setting up of parameters check  
	 * <a href="http://stgpedia.stgil-india.com/index.php/PROCESS_REQ_PARAMS">STGPedia</a> 
	 * for related information.
	 */
	private transient BatchParams requestParams = null;
	
	
	private transient boolean batchStateSaved = false;
		
	/**
	 * The default constructor 
	 */
	public BatchContext(){}
	
	/**
	 * The overloaded constructor that takes the PRE context and the 
	 * request parameters as arguments 
	 * 
	 * @param preContext
	 * 		  The PRE context @see {@link PREContext}
	 * @param requestParams
	 * 		  The batch parameters @see {@link BatchParams}
	 */
	public BatchContext(PREContext preContext, BatchParams requestParams, IDataSourceFactory dsFactory){
		this.preContext = preContext;	
		this.requestParams = requestParams;
		ConnectionManager.getInstance(dsFactory);
	}
	
	/**
	 * The overloaded constructor that only the request parameters as arguments 
	 * Designed to work in DEV mode 
	 * 
	 * @param requestParams
	 * 		  The batch parameters @see {@link BatchParams}
	 */
	public BatchContext(BatchParams requestParams){
		this.requestParams = requestParams;
	}
	
	
	/**
	 * Returns the preContext
	 * 
	 * @return the preContext
	 */
	public PREContext getPreContext() {
		return preContext;
	}

	/**
	 * Sets the preContext
	 * 
	 * @param preContext 
	 *        The preContext to set
	 */
	public void setPreContext(PREContext preContext) {
		this.preContext = preContext;
	}

	/**
	 * Returns the requestParams
	 * 
	 * @return the requestParams
	 */
	public BatchParams getRequestParams() {
		return requestParams;
	}

	/**
	 * Sets the requestParams
	 * 
	 * @param requestParams 
	 *        The requestParams to set
	 */
	public void setRequestParams(BatchParams requestParams) {
		this.requestParams = requestParams;
	}

	/**
	 * Returns the {@link BatchInfo}.
	 * 
	 * @return the batchInfo
	 */
	public BatchInfo getBatchInfo() {
		return batchInfo;
	}

	/**
	 * Sets the batchInfo
	 * 
	 * @param batchInfo 
	 *        The batchInfo to set
	 */
	public void setBatchInfo(BatchInfo batchInfo) {
		this.batchInfo = batchInfo;
	}
	
	/**
	 * Returns the connection specific to the BATCH (and or the PRE) schema 
	 * 
	 * @return a new connection specific to the BATCH (and or the PRE) schema
	 * @throws BatchException
	 * 		   Any exception thrown while fetching the connection from the BATCH (and or PRE) connection pool 
	 */
	public BatchConnection getBATCHConnection() throws BatchException {
		return ConnectionManager.getInstance().getBATCHConnection();
	}

	/**
	 * Returns the connection specific to the Application schema 
	 * 
	 * @return a new connection specific to the Application schema
	 * @throws BatchException
	 * 		   Any exception thrown while fetching the connection from the Application connection pool 
	 */
	public ApplicationConnection getApplicationConnection() throws BatchException {
			return ConnectionManager.getInstance().getApplicationConnection();
	}

	/**
	 * Returns whether the batch is to be stopped 
	 * (as the case maybe i.e. for DEV or PRE)
	 * 
	 * @return an indicator to either stop 
	 * 		   the current batch and or revision 
	 */
	public Boolean isBatchToBeStopped() {
		
		Date endTime = getBatchInfo().getExecutionEndTime();
		Date currentTime = new Date();
		
		//If end of time, then set the reason appropriately 
		if(endTime != null && endTime.before(currentTime)){
			getBatchInfo().setClosureReason(Constants.CLOSURE_REASON.END_OF_TIME.name());
			return true;
		}
		
		Object endBatch = getPreContextAttribute(getBatchInfo().getBatchNo(), CONTEXT_KEYS.JBEAM_EXIT.name());
		
		if(endBatch != null && ((String)endBatch).equals("Y")){
			String reason = (String)getPreContextAttribute(getBatchInfo().getBatchNo(), CONTEXT_KEYS.JBEAM_EXIT_REASON.name());
			if(reason != null)
				getBatchInfo().setClosureReason(reason);
			
			return true;
		}
		
		return false;
	}


	/**
	 * Sets the batch state saved value.
	 * @param batchStateSaved the batchStateSaved to set
	 */
	public void setBatchStateSaved(boolean batchStateSaved) {
		this.batchStateSaved = batchStateSaved;
	}

	/**
	 * Returns true if the batch state has been saved.
	 * @return the batchStateSaved
	 */
	public boolean isBatchStateSaved() {
		return batchStateSaved;
	}
	
	public Serializable getPreContextAttribute(Integer batchNo, String key){
		Map<String, Serializable> map = (Map<String, Serializable>)preContext.getAttribute(batchNo.toString());
		if (map == null) {
			return null;
		}
		return map.get(key);
	}
	
	public void setPreContextAttribute(Integer batchNo, String key, Serializable value){
		
		Map<String, Serializable> map = (Map<String, Serializable>)preContext.getAttribute(batchNo.toString());
		if (map == null) {
			map = new HashMap<String, Serializable>();
		}
		map.put(key, value);
		
		preContext.setAttribute(batchNo.toString(), (Serializable)map);
	}
	
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(super.toString());
		buff.append("\n");
		buff.append("Batch Info :\n");
		buff.append(batchInfo.toString());
		buff.append("\n");
		buff.append("preContext :\n");
		buff.append(preContext);
		return buff.toString();
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/BatchContext.java                                                                                   $
 * 
 * 12    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 11    4/27/10 9:27a Kedarr
 * Added batch state saved boolean variable.
 * 
 * 10    4/22/10 1:30p Kedarr
 * Refactored (renamed) the method stop batch to is batch to be stopped.
 * 
 * 9     3/26/10 2:24p Mandar.vaidya
 * Removed unused variable.
 * 
 * 8     3/26/10 12:50p Kedarr
 * Changes made to accept a list of pollers instead of just one poller.
 * 
 * 7     3/25/10 9:57a Kedarr
 * As the context is different for each batch job the get batch info method should not check if the object is in the context.
 * 
 * 6     2/25/10 10:41a Grahesh
 * Changes made to make use of context keys enum from constants
 * 
 * 5     2/24/10 5:09p Grahesh
 * Modified the method get batch info to return a new instance or an existing instance from the context. Also changes made to make use of constants class  for context keys.
 * 
 * 4     12/22/09 10:28a Grahesh
 * Updation done to include the closure reason.
 * 
 * 3     12/21/09 5:11p Grahesh
 * Added code closure reason
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/