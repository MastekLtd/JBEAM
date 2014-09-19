/*
 * This file forms part of the STG Mastek Group 
 * Copyright (c) STG Mastek Group 2009 - 2010.  All  rights reserved
 */
package com.stgmastek.monitor.comm.logging;

/**
 * Aspect class for logging into text files those methods marked for logging
 */
public aspect Logging
{
//	static DataOutputStream dos = null;
//	static{
//		try {
//			dos = new DataOutputStream(new FileOutputStream("C:/batch-ws.log", true));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
//	pointcut callPointcut( ) : execution(@Log * *(..));
//	static void println(String s){System.out.println(s);}
	
//	Object around(): callPointcut(){
//		StringBuilder sb = new StringBuilder();
//		sb.append("[CORE-WS:" + new Date() + "]\n");
//		sb.append("\t Service Name: " + thisJoinPointStaticPart.getSignature().getName()+ "\n");
//		sb.append("\t Parameters: " + thisJoinPoint.getArgs().toString() + "\n"); 
//		Object obj = proceed();
//		sb.append("\t Return: " + obj.toString() + "\n");
//		writeLog(sb.toString());
//		sb = null;
//		return obj;
//	}

	
//	private synchronized void writeLog(String s){
//		try {
//			dos.writeBytes(s);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}