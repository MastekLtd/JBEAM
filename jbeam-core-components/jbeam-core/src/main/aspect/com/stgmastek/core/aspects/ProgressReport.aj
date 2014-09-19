/*
* This file forms part of the STG Mastek Group 
* Copyright (c) STG Mastek Group 2009 - 2010.  All  rights reserved
*/
package com.stgmastek.core.aspects;

import java.util.Date;

import com.stgmastek.core.dao.DAOUtil;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.ProgressLevel;

/**
 * Aspect to update the progress level during the execution of the system 
 * This aspect would work only if the LogTime marked method have JobContext 
 * as one of its arguments. The JobContext is needed to get the connection object 
 * to make the necessary database related calls  
 *   
 * @author grahesh.shanbhag
 *
 */
public aspect ProgressReport {

	/** The point cut is an annotation LogTime */
	pointcut callPointcut() : execution(@LogTime * *(..));
	
	/**
	 * Around advice to log the time taken to complete
	 * Logs the time into the PROGRESS_LEVEL table
	 * 
	 * @return the methods return object 
	 * @throws BatchException
	 * 		   Any exception thrown during calculating and 
	 * 		   logging the time taken 
	 */
	Object around() throws BatchException : callPointcut() {
		
		//Get the job context 
		Object[] args = thisJoinPoint.getArgs();
		BatchContext context = null;
		for(Object obj : args){
			if(obj instanceof BatchContext){
				context = (BatchContext)obj;
				break;
			}
		}		
		
		ProgressLevel PL = ProgressLevel.getProgressLevel(context.getBatchInfo().getBatchNo());
		
		Exception toThrow = null;		
		Object returnObj = null;
		
		//Set the Progress Level as IP before the execution of the method marked 
		//for logging time  
		PL.setStatus("IP");
		PL.setStartDatetime(new Date());		
		PL.setErrorDesc(null);
		PL.setEndDatetime(null);
		PL.setFailedOver(context.getBatchInfo().isFailedOver());
		
		//Update the progress level into the table 
		Integer indicatorNo = updateProgress(context, true);		
		PL.setIndicatorNo(indicatorNo);
		
		try{
			//Execute the method 
			returnObj = proceed();
			
			//Set the completion status and time 
			PL.setEndDatetime(new Date());
			PL.setStatus("CO");
		}catch(Exception e){
			
			//In case of exception get the exception stack trace and fill the  
			//the error description attribute 
			StackTraceElement stack[] = e.getStackTrace();
			StringBuilder sb = new StringBuilder();
			sb.append(e.getMessage() + " ");
			for(StackTraceElement se : stack){
				sb.append(se.toString() + " ");
			}
			String s = sb.toString();
			s = (s.length() > 100) 
					? s.substring(0, 100) 
					: s;  
					
			PL.setErrorDesc(s);
			PL.setEndDatetime(new Date());
			PL.setStatus("99");
			toThrow = e;
		}
		
		//Update the end time for the method or execution step marked for logging time 
		PL.setFailedOver(context.getBatchInfo().isFailedOver());
		updateProgress(context, false);
		
		//Throw the throwable as caught by the aspect if any 
		if(toThrow != null)
			throw new BatchException(toThrow);
		
		//Return the object
		return returnObj;
	}

	/**
	 * Helper method to call the appropriate utility method 
	 * that would update the progress
	 *  
	 * @param context
	 * 	 	  The job context instance 
	 * @param newEntry
	 * 		  Indicator whether a new entry and hence insert is realized 
	 * 		  or its an update 
	 * @return The progress indicator number to be used as needed 
	 */
	private static Integer updateProgress(BatchContext context, boolean newEntry){
		Integer iRet = 0;
		try{
			iRet = DAOUtil.updateProgress(context, newEntry);
		}catch(BatchException bme){
			bme.printStackTrace();
		}
		return iRet;
	}
	
}
