/*
* This file forms part of the STG Mastek Group 
* Copyright (c) STG Mastek Group 2009 - 2010.  All  rights reserved
*/
package com.stgmastek.core.aspects;
 
import java.util.Date;

import com.stgmastek.core.exception.BatchStopException;
import com.stgmastek.core.util.BatchContext;

/**
 * Aspect to check the end time if applicable 
 * If end time is realized then aspect throws BatchStopException that indicates 
 * the system to stop its proceedings after the first logical completion once the exception 
 * is raised 
 * This aspect would work only if the Marker marked method that have JobContext 
 * as one of its arguments. It is needed to get the end time. 
 *   
 * @author grahesh.shanbhag
 *
 */
public aspect CheckEndTime {

	/** The point cut is an annotation Marker */
	pointcut callPointcut() : execution(@Marker * *(..));

	/**
	 * Before advice 
	 * As the name of the aspect suggests, it checks for end time for the batch 
	 * and issues BatchStopException when the end time is realized 
	 * It checks for - <p />
	 * <OL>
	 * 	<LI> If the end time set is realized
	 * 	<LI> If there is an indication from the listener to stop the batch. 
	 * 		 This is a valid case when objects marked as on-fail-exit = 'Y' fails.   
	 * </OL> 
	 * 
	 * @throws BatchStopException
	 * 		   Exception thrown if the end time is realized 
	 */
	before() throws BatchStopException : callPointcut() {
		
		//Get the job context 
		Object[] args = thisJoinPoint.getArgs();
		BatchContext context = null;
		for(Object obj : args){
			if(obj instanceof BatchContext){
				context = (BatchContext)obj;
				break;
			}
		}		
		
		//Check for end time 
		if (context != null && context.getBatchInfo() != null) {
			if(context.getBatchInfo().getExecutionEndTime() != null){
				if(context.getBatchInfo().getExecutionEndTime().before(new Date())){
					throw new BatchStopException("Execution End time is less than the server time which, is not possible.");
				}
			}
		}
		
		//Check whether there is an attribute to check whether 
		if (context != null) {
			Object toExit = context.getPreContext().getAttribute("EXIT");
			if(toExit != null && ((String)toExit).equals("Y"))
				throw new BatchStopException();
		}
	}
}
