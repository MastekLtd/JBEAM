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

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.stgmastek.core.aspects.Email;
import com.stgmastek.core.aspects.Email.EMAIL_TYPE;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.BatchStopException;
import com.stgmastek.core.exception.ExecutionException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.Constants.OBJECT_STATUS;
import com.stgmastek.core.util.time.JBeamTimeFactory;

/**
 * Final class to provide a single entry point to the handlers to execute a 
 * type of a batch object
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public final class ExecutionHandler {
	
	private static final transient Logger logger = Logger.getLogger(ExecutionHandler.class);
	
	/**
	 * Pool that holds all the execution handler instances.
	 */
	private static final transient HashMap<String, BaseExecutionHandler> executionHandlerPool = new HashMap<String, BaseExecutionHandler>();
	
	/** Private constructor to avoid outside instantiation */
	private ExecutionHandler(){	
	}

	/**
	 * Static method to be used to execute any batch jobs. 
	 * <p />
	 * Each type of job is associated with an specialized execution handler 
	 * class. This method would instantiate and execute appropriate handler 
	 * as needed. The instantiated classes are pooled thus it is recommended to avoid any class
	 * variables as it may lead to concurrency issues.
	 *
	 * Identified job types  - 
	 * <OL>
	 * 	<LI> PL - PLSQL or stored procedures 
	 * 	<LI> EV - Event Parser jobs  
	 * 	<LI> JV - Java jobs 
	 * </OL> 
	 * However, the default implementation for these job types can be changed. 
	 * Way to go about for it - 
	 * <p/>
	 * <OL>
	 * 	<LI> Create a class that extends {@link BaseExecutionHandler} and implement the abstract method 
	 *  <LI> Configure the handler in the CONFIGURATION table against the job type
	 *  <LI> Put the newly created class file in the class path HINT:startEng.sh of the Process Request Engine
	 *  <LI> Run the batch as one would regularly 
	 * </OL>
	 * <b>The above steps are true for even new job types and is not restricted to only 'JV', 'EV' and 'PL'.</b> 
	 * 
	 * This method is called iteratively for each job, even if they belong 
	 * to the same entity and value.
	 * 
	 * <p />
	 * EX. For Policy P1 there are 10 jobs assigned. The way it works - 
	 * <p />
	 * <UL>
	 * 	<LI> One job would be picked up and executed. 
	 *  <LI> Say, jobs #1 to #3 were executed successfully then would be marked 'CO'
	 * 	<LI> If say, job #4 fails, then it is marked as '99' 
	 * 	<LI> Since job#4 failed, the special indicator 'exceptionOccurred' is 
	 * 		 set true and all other jobs i.e. 5 through to 10 would be marked 
	 * 		 as 'SP' without execution. 
	 *		 This indicator is driven through the settings for the entity under 
	 *	 	 consideration as in the BATCH_COLUMN_MAP table.   
	 * <UL>
	 * 
	 * @param batchContext
	 * 		  The context for the batch 
	 * @param batchObject
	 * 		  The batch job or object under consideration 
	 * @param objectMapDetails
	 * 		  The object map details for the batch object under consideration 
	 * @param registeredExecutionHandler
	 * 		  The registered execution handlers configured in the CONFIGURATION table 
	 * @param exceptionOccurred
	 * 		  A special case field to indicate whether any previous 
	 * 		  job failed execution 
	 * @return The batch object under consideration with the updated status 
	 * @throws BatchException
	 * 		   Any exception occurred during execution, and or database I/O
	 * @throws BatchStopException
	 * 		   Special exception raised when the batch has to be stopped
	 * @throws ExecutionException
	 * 		   Special exception when an exception has occurred during 
	 * 		   the execution 
	 */	
	@Email(type = EMAIL_TYPE.WHEN_OBJECT_FAILS)
	public static BatchObject execute(BatchContext batchContext, 
										BatchObject batchObject,
										ObjectMapDetails objectMapDetails,
										HashMap<String, String> registeredExecutionHandler,
										Boolean exceptionOccurred) 
											throws BatchException, 
											BatchStopException, 
											ExecutionException{

		BaseExecutionHandler handler = null;
		try{

			//Get the object Map
			if(objectMapDetails == null)
				throw new BatchException("Object Mapping for " + batchObject.getObjectName() 
						+ " not set in the OBJECT_MAP table");
			
			if(objectMapDetails.getObjectType() == null)
				throw new BatchException("Object Type not set for batch object with sequence number " 
						+ batchObject.getSequence());
			
			if(registeredExecutionHandler.get(objectMapDetails.getObjectType()) == null)
				throw new BatchException("Handler not registered for  " 
						+ objectMapDetails.getObjectType());
				
			
			String handlerClassName = registeredExecutionHandler.get(objectMapDetails.getObjectType());
			if (!executionHandlerPool.containsKey(handlerClassName)) {
				synchronized (executionHandlerPool) {
					if (!executionHandlerPool.containsKey(handlerClassName)) {
						Class<?> clazz = Class.forName(handlerClassName);
						Constructor<?> constructor = clazz.getDeclaredConstructor();
						
						Object handlerObj = constructor.newInstance();
						
						if(!(handlerObj instanceof BaseExecutionHandler))
							throw new BatchException("Handler class " + handlerClassName 
									+ " not an instance of " + BaseExecutionHandler.class.getName());
						executionHandlerPool.put(handlerClassName, (BaseExecutionHandler)handlerObj);
					}
				}
			}
			handler = executionHandlerPool.get(handlerClassName);
			
			if(!exceptionOccurred){
				handler.execute(batchObject, objectMapDetails, batchContext);
//				batchObject.setStatus("CO");
			}else{
				batchObject.setStatus(OBJECT_STATUS.SUSPENDED);
			}
		}catch(Exception e){
			batchObject.setStatus(OBJECT_STATUS.FAILED);
			handleExecutionException(batchObject, objectMapDetails, e);
		}finally{
			batchObject.setDateExecuted(JBeamTimeFactory.getInstance().getCurrentTimestamp(batchContext));
			System.out.println(" batchObject " + batchObject);
		}
		
		return batchObject;
	}
	
	/**
	 * Helper method to handle the execution exception case
	 * If an object is marked as on-fail-exit = 'Y', then it would raise 
	 * BatchStopException for the caller to stop the proceedings  
	 * If an object is marked as on-fail-exit = 'N', then it would raise
	 * ExecutionException to indicate the caller that all other  
	 * jobs in the same category have to be marked 'SP'  
	 * 
	 * @param batchObject
	 * 		  The batch job under consideration
	 * @param objectMapDetails
	 * 		  The object mapping details for the batch object
	 * @param e
	 * 		  The exception that was thrown 
	 * @throws BatchStopException
	 * 		   Special exception raised when the batch has to be stopped
	 * @throws ExecutionException
	 * 		   Special exception when an exception has occurred during 
	 * 		   the execution 
	 */
	static void handleExecutionException(BatchObject batchObject,
											ObjectMapDetails objectMapDetails,
											Throwable e) 
											throws BatchStopException, 
											ExecutionException{
		logger.error("Caught Exception while executing the job " + batchObject.getObjectName(), e);
		batchObject.setErrorType(e.getClass().getSimpleName());
		StringBuilder sb = new StringBuilder();
		throwableToString(sb, e);
		if (sb.length() > Constants.MAX_EXCEPTION_TRACE_LENGTH) {
			sb.delete(Constants.MAX_EXCEPTION_TRACE_LENGTH, sb.length());
		}
		batchObject.setErrorDescription(sb.toString());
		
		//throw execution exception if the object map cannot be fetched
		if(objectMapDetails == null)
			throw new ExecutionException();
		
		//when object map is fetched and on-fail-exit is set as 'Y' stop the batch 
		//else throw execution exception 
		String onFailInd = objectMapDetails.getOnFailExit();
		if( onFailInd != null && onFailInd.equals("Y"))
			throw new BatchStopException(e);
		else
			throw new ExecutionException(e);
	}
	
	/**
	 * Converts a {@link Throwable} into a string. 
	 * @param sb StringBuilder
	 * @param t Throwable
	 */
	public static void throwableToString(StringBuilder sb, Throwable t) {
		StackTraceElement[] stack = t.getStackTrace();
		sb.append(t.getMessage() + " ");
		for(StackTraceElement se : stack){
			sb.append(se.toString() + " ");
		}
		if (t.getCause() != null) {
			sb.append(" caused by ");
			throwableToString(sb, t.getCause());
		}
	}
	
	public static void shutdown() {
	    for (BaseExecutionHandler handler : executionHandlerPool.values()) {
	        try {
	            handler.shutdown();
            } catch (Exception e) {
            }
	    }
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/ExecutionHandler.java                                                                              $
 * 
 * 12    6/23/10 3:54p Kedarr
 * Commented the code that sets the object status to CO. This now should be done by the JOB itself.
 * 
 * 11    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 10    3/24/10 12:58p Kedarr
 * Changes made to send email on object failure.
 * 
 * 9     3/03/10 3:20p Grahesh
 * Added logger
 * 
 * 8     1/07/10 6:20p Grahesh
 * Updated Java Doc comments
 * 
 * 7     12/24/09 3:21p Grahesh
 * Implemented the logic where by special execution handler classes can be configured from the outside through configurations. Though, the default implementation would be the core
 * 
 * 6     12/23/09 3:57p Grahesh
 * Moved the logic of object map details and type check from special handler classes to this generic class
 * 
 * 5     12/23/09 3:24p Grahesh
 * Using the on-fail-exit flag from the batch_executor to object_map
 * 
 * 4     12/23/09 1:52p Grahesh
 * Catching Exception instead of Throwable
 * 
 * 3     12/18/09 11:34a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/