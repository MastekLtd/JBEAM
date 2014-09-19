/*
 * This file forms part of the STG Mastek Group 
 * Copyright (c) STG Mastek Group 2009 - 2010.  All  rights reserved
 */
package com.stgmastek.core.aspects;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.stgmastek.core.util.Constants;


/**
 * Aspect class for logging into text files those methods marked for logging
 */
public aspect Logging
{
	private static DataOutputStream dos = null;

	pointcut callPointcut( ) : execution(@Log * *(..));
	
	after(): callPointcut(){
		
		if(!Constants.LOGGING_ENABLED)
			return;
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n[" + new Date() + "]:");
		sb.append(thisJoinPointStaticPart.getSignature().getName());
		if(thisJoinPoint.getArgs() != null) {
			sb.append("{");
			for (Object obj : thisJoinPoint.getArgs()) {
				if (obj != null) {
					sb.append(obj.toString());
				} else {
					sb.append("null");
				}
				sb.append(",") ; 
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("}");
		} else {
			sb.append("{}");
		}
		writeLog(sb.toString());
		sb = null;
	}
	
	synchronized static DataOutputStream getWriter(){
		if(Logging.dos != null){
			return Logging.dos;
		}else{
			try {
				Logging.dos = new DataOutputStream(FileUtils.openOutputStream(new File(Constants.LOG_FILE_PATH)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Logging.dos = new DataOutputStream(System.out);
			} catch (IOException e) {
				e.printStackTrace();
				Logging.dos = new DataOutputStream(System.out);
			}
			return Logging.dos;
		}
	}
	
	
	private synchronized void writeLog(String s){
		try {			
			getWriter().writeBytes(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}