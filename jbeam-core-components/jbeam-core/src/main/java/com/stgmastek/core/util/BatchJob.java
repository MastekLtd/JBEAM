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
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.IProcessStatus;
import stg.pr.engine.PREContext;
import stg.pr.engine.ProcessRequestServicer;

import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.BatchInProgressException;
import com.stgmastek.core.exception.BatchStopException;

/**
 * The base class for PRE scheduled job classes within Core system. 
 * It includes, but not restricted to Processor and Listener
 * Note: For jobs outside of Core, this class should not be used to create 
 * jobs in Process Request Engine. 
 * Instead use {@link ProcessRequestServicer} & {@link IProcessStatus} directly from PRE framework
 * 
 * If any job has to be scheduled or instantiated and executed from the PRE 
 * then one would have to implement this class within the core system
 * 
 * <p/>
 * The execution happens like  
 * <li>The PRE does the initialization and would complete its call back routines @see {@link stg.pr.engine.IProcessRequest}</li>
 * <li>The over ridden method <i>init(batchContext)</i> would be called </li>
 * <li>The implemented method <i>execute(batchContext)</i> would be called </li>
 * <li>The over ridden destroy <i>destroy(batchContext)</i> would be called </li>
 * 
 * @author grahesh.shanbhag
 */
public abstract class BatchJob extends ProcessRequestServicer implements IProcessStatus{

	private static final Logger logger = Logger.getLogger(BatchJob.class);

	/** 
	 * The batch context 
	 * 
	 * @see BatchContext
	 */
	protected BatchContext batchContext = null;
	
	/**
	 * Status for the PRE console 
	 */
	protected volatile String status;
	
	
	/** 
	 * Call back method from the PRE engine
	 * <p />
	 * This is the main method that would be called by the PRE engine first.
	 * 
	 * @see  ProcessRequestServicer#processRequest()
	 */
	
	
	public boolean processRequest() throws CProcessRequestEngineException {
		return perform();
	}

	
	public PREContext getContext() {
		PREContext context = null;		
		if(Constants.MODE.equals(Constants.DEV))
			context = new CPREContext();
		else
			context = super.getContext();
		return context;
	}
	
	/**
	 * Helper method to get the context for the batch to be executed
	 *  
	 * @return the batch context
	 * 
	 * @see  BatchContext 
	 */
	private BatchContext getbatchContext(){
		return this.batchContext;
	} 
	
	/**
	 * Helper method that the processRequest calls 
	 * 
	 * @return true if the job is performed successfully, false otherwise
	 */
	private boolean perform() throws CProcessRequestEngineException{
		batchContext = new BatchContext(getContext(), new BatchParams(getParams()), getDataSourceFactory());
		getbatchContext().getRequestParams().setRequestId(getRequestId());		

		Throwable t = null;
		try {
			init(batchContext);
			if(logger.isDebugEnabled()) {
				logger.debug("init completed.. going for validate");
			}
			validate(batchContext);
			if(logger.isDebugEnabled()) {
				logger.debug("validate completed.. going for validate");
			}
			execute(batchContext);
		}catch (Throwable e) {
			t = e;
			logger.fatal("Caught exception in perform method", e);
			throw new CProcessRequestEngineException(e.getMessage(), e);
		}finally{
			try {
				if(t != null && t instanceof BatchException)
					if (t instanceof BatchInProgressException) {
						if(logger.isDebugEnabled()) {
							logger.debug("//do nothing as already a batch is in progress so should not call close batch as it will impact the running batch.");
						}
					   //do nothing as already a batch is in progress so should not call close batch as it will impact the running batch.	
					} else {
						destroy(batchContext, ((BatchException)t).callCloseBatch());					
					}
				else
					destroy(batchContext, true);
			} catch (BatchException e) {
				logger.fatal("Error while destroying ", e);
			}
		}
		
		return true;
	}
	
	protected void setAttribute(Integer batchNo, String key, Serializable value){
		Map<String, Serializable> map = (Map<String, Serializable>)getContext().getAttribute(batchNo.toString());
		if (map == null) {
			map = new HashMap<String, Serializable>();
		}
		map.put(key, value);
		
		getContext().setAttribute(batchNo.toString(), (Serializable)map);
	}
	
	protected Serializable getAttribute(Integer batchNo, String key){
		Map<String, Serializable> map = (Map<String, Serializable>)getContext().getAttribute(batchNo.toString());
		if (map == null) {
			return null;
		}
		return map.get(key);
	}
	
	protected Serializable clearAttribute(Integer batchNo, String key){
		Map<String, Serializable> map = (Map<String, Serializable>)getContext().getAttribute(batchNo.toString());
		if (map == null) {
			return null;
		}
		return map.remove(key);
	}
	
	/**
	 * The empty initialization method for the job.  
	 * Any initialization needed would have to be over ridden in the 
	 * sub classes as needed 
	 * 
	 * @param batchContext
	 * 		  The context provided by the core system for the batch 
	 *  
	 * @throws BatchException
	 * 		   Any exception thrown while initialization to be wrapped as BatchException 
	 */
	protected void init(BatchContext batchContext) throws BatchException{}	
	
	
	/**
	 * Method to validate the parameters and set the context for the batch 
	 * 
	 * @param batchContext
	 * 		  The context for the batch
	 * @throws BatchException
	 * 		   Any exception thrown during validation 
	 */	
	protected void validate(BatchContext batchContext) throws BatchException{}
	
	/**
	 * Abstract method to be implemented by the sub classes within the core system 
	 * This is the main method that would be called. 
	 * <p/>Steps<p/> 
	 * <li>The PRE does the initialization and would complete its call back routines @see  IProcessRequest</li>
	 * <li>The over ridden method <i>init(batchContext)</i> would be called </li>
	 * <li>The implemented method <i>execute(batchContext)</i> would be called </li>
	 * <li>The over ridden destroy <i>destroy(batchContext)</i> would be called </li>
	 * 
	 * @param batchContext
	 * 		  The context for the batch provided by the core system
	 *  
	 * @return true if job completed successfully, false otherwise
	 * @throws BatchException
	 * 		   Any exception thrown during execution to be wrapped as BatchException
	 * @throws BatchStopException
	 * 		   An indication from system to stop the batch after the first 
	 * 	  	   logical completion once the exception or the indication is issued  
	 */
	protected abstract boolean execute(BatchContext batchContext) throws BatchException, BatchStopException;	
	
	/**
	 * The destroy method called before PRE decides on destroying the object  
	 * 
	 * @param batchContext
	 * 		  The context provided by the core system 
	 * @param shouldClean
	 * 		  The boolean to actually call the destroy, else would only unlock the processor
	 * @throws BatchException
	 * 		   Any exception thrown during execution to be wrapped as BatchException 
	 */
	protected void destroy(BatchContext batchContext, Boolean shouldClean) throws BatchException {};
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/BatchJob.java                                                                                       $
 * 
 * 7     7/06/10 11:01a Kedarr
 * Do not call close or destroy method in case their is another batch in progress
 * 
 * 6     4/28/10 10:37a Kedarr
 * Updated javadoc
 * 
 * 5     4/12/10 2:24p Kedarr
 * Organized imports
 * 
 * 4     12/23/09 4:12p Grahesh
 * Implementing IProcessStatus for display of status on to the PRE console
 * 
 * 3     12/23/09 11:55a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/