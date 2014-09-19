/*
* This file forms part of the STG Mastek Group 
* Copyright (c) STG Mastek Group 2009 - 2010.  All  rights reserved
*/
package com.stgmastek.core.aspects;
 
import org.apache.log4j.Logger;

import com.stgmastek.core.aspects.Email.EMAIL_TYPE;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Configurations;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.ProgressLevel;
import com.stgmastek.core.util.email.EmailAgent;

/**
 * Aspect to send the email  
 *   
 * @author grahesh.shanbhag
 *
 */
public aspect SendEmailAj {

	private static final Logger logger = Logger.getLogger(SendEmailAj.class);
	
	/** The point cut is an annotation Email */
	pointcut callPointcut(Email email) : execution(@Email * *(..)) && @annotation(email);
	
	after(Email email) : callPointcut(email) {
		
		//Get the job context 
		Object[] args = thisJoinPoint.getArgs();
		BatchContext context = null;
		BatchObject batchObject = null;
		ObjectMapDetails objMap = null;
		for(Object obj : args){
			if(obj instanceof BatchContext){
				context = (BatchContext)obj;
			} else if(obj instanceof BatchObject){
				batchObject = (BatchObject)obj;
			} else if (obj instanceof ObjectMapDetails) {
				objMap = (ObjectMapDetails) obj;
			}
		}
		
		//Get the configured notification indicator
		String notification = Configurations.getConfigurations().getConfigurations("CORE", "EMAIL", "NOTIFICATION");
		String str = Configurations.getConfigurations().getConfigurations("CORE", "EMAIL", "MAX_EMAILS_FAILED_OBJ");
		if (str == null || "".equals(str)) str = "100";
		Integer maximumEmailsForFailedBO = 100;
		try {
			maximumEmailsForFailedBO = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		
		//Notify through email only if configured
		if("Y".equalsIgnoreCase(notification)) {
			switch (email.type()) {
				case WHEN_OBJECT_FAILS:
					if (objMap == null) {
						Integer counter = (Integer) context.getPreContext().getAttribute(Constants.CONTEXT_KEYS.JBEAM_BO_FAILED_EMAIL_COUNTER.name());
						if (counter == null) counter = 1;
						if (counter <= maximumEmailsForFailedBO) {
							Boolean lastEmail = (counter.intValue()==maximumEmailsForFailedBO.intValue());
							EmailAgent.getAgent().sendEmail(context, email.type(), batchObject, null, lastEmail);
							synchronized (context.getPreContext()) {
								context.getPreContext().setAttribute(Constants.CONTEXT_KEYS.JBEAM_BO_FAILED_EMAIL_COUNTER.name(), ++counter);
							}
						} else {
							if (logger.isInfoEnabled()) {
								logger.info("Maximum counter reached for failed objects where objMap is null");
							}
						}
					} else {
						if ("99".equals(batchObject.getStatus()) && "y".equalsIgnoreCase(objMap.getOnFailEmail())) {
							Integer counter = (Integer) context.getPreContext().getAttribute(Constants.CONTEXT_KEYS.JBEAM_BO_FAILED_EMAIL_COUNTER.name());
							if (counter == null) counter = 1;
							if (counter <= maximumEmailsForFailedBO) {
								Boolean lastEmail = (counter.intValue()==maximumEmailsForFailedBO.intValue());
								EmailAgent.getAgent().sendEmail(context, email.type(), batchObject, objMap, lastEmail);
								synchronized (context.getPreContext()) {
									context.getPreContext().setAttribute(Constants.CONTEXT_KEYS.JBEAM_BO_FAILED_EMAIL_COUNTER.name(), ++counter);
								}
							} else {
								if (logger.isInfoEnabled()) {
									logger.info("Maximum counter reached for failed objects within objMap");
								}
							}
						} else {
							//do nothing
						}
					}
					break;
				default:
					if (email.type() == EMAIL_TYPE.WHEN_BATCH_ENDS) {
						context.getBatchInfo().setExecutionEndTime(new java.sql.Timestamp(ProgressLevel.getProgressLevel(context.getBatchInfo().getBatchNo()).getEndDatetime().getTime()));
					}
					EmailAgent.getAgent().sendEmail(context, email.type(), batchObject, objMap, Boolean.valueOf(false));
			}
		}
		
	}
}
		
