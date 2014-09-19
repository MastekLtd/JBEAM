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
package com.stgmastek.monitor.ws.util.corecomm.servicehandler;

import java.util.List;

import com.stgmastek.core.comm.client.CReqProcessRequestScheduleVO;
import com.stgmastek.core.comm.client.CResProcessRequestScheduleVO;
import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.core.comm.client.ScheduleData;
import com.stgmastek.monitor.ws.exception.CommException;
import com.stgmastek.monitor.ws.server.vo.ProcessRequestScheduleData;
import com.stgmastek.monitor.ws.server.vo.ReqProcessRequestScheduleVO;
import com.stgmastek.monitor.ws.util.ClientBook;

public class ScheduleHandler {

	private static ScheduleHandler scheduleHandler;
	private CoreCommServices service;
	
	private ScheduleHandler(){}

	public static ScheduleHandler getInstance(){
		if(scheduleHandler == null)
			scheduleHandler = new ScheduleHandler();
		
		return scheduleHandler;
	}
	
	/**
	 * Sets the service by using the given installation code
	 *
	 * @param installationCode 
	 *		  The installationCode to set 				   
	 */
	public void setService(String installationCode) {
		service = ClientBook.getBook().getClientClass(installationCode);
	}
	
	/**
	 * Get the schedule data from core-comm service to refresh the data in monitor 
	 * schema.
	 *  
	 * @param reqScheduleVO
	 * 			The instance of {@link ReqProcessRequestScheduleVO} 
	 * 
	 * @return response object for core-comm ({@link CResProcessRequestScheduleVO}
	 * 
	 */
	public CResProcessRequestScheduleVO getProcessRequestSchedule(ReqProcessRequestScheduleVO reqScheduleVO)
			throws CommException{
		return service.getProcessReqSchedule(createReqScheduleVO(reqScheduleVO));
	}
	
	/**
	 * Helper method to convert request from monitor {@link ReqProcessRequestScheduleVO} 
	 * to request of core {@link CReqProcessRequestScheduleVO} for multiple records. 
	 *  
	 * @param reqScheduleVO
	 * 			The instance of {@link ReqProcessRequestScheduleVO} 
	 * 
	 * @return request object for core-comm ({@link CReqProcessRequestScheduleVO}
	 */
	private CReqProcessRequestScheduleVO createReqScheduleVO(
			ReqProcessRequestScheduleVO reqScheduleVO){
		CReqProcessRequestScheduleVO requestScheduleVO = new CReqProcessRequestScheduleVO();
		List<ProcessRequestScheduleData> scheduleList =	reqScheduleVO.getProcessRequestScheduleList();
		for (ProcessRequestScheduleData scheduleData : scheduleList) {
			requestScheduleVO.getProcessRequestScheduleList().add(createScheduleData(scheduleData));
		}
		return requestScheduleVO;
	}
	
	/**
	 * Cancels a schedule using core-comm service and refreshes the data in monitor 
	 * schema.
	 *  
	 * @param reqScheduleVO
	 * 			The instance of {@link ReqProcessRequestScheduleVO} 
	 * 
	 * @return response object for core-comm ({@link CResProcessRequestScheduleVO}
	 */
	public CResProcessRequestScheduleVO cancelSchedule(ReqProcessRequestScheduleVO reqScheduleVO) 
			throws CommException{ 		 
		return service.cancelCoreSchedule(createReqScheduleVO(reqScheduleVO));
	}
	
	/**
	 * Helper method to convert request from monitor {@link ReqProcessRequestScheduleVO} 
	 * to request of core {@link CReqProcessRequestScheduleVO} for single record. 
	 *  
	 * @param reqScheduleVO
	 * 			The instance of {@link ReqProcessRequestScheduleVO} 
	 * 
	 * @return request object for core-comm ({@link CReqProcessRequestScheduleVO}
	 */
//	private CReqProcessRequestScheduleVO createReqScheduleVOWithPRSData(
//			ReqProcessRequestScheduleVO reqScheduleVO){
//		CReqProcessRequestScheduleVO requestScheduleVO = new CReqProcessRequestScheduleVO();
//		
//		requestScheduleVO.setScheduleData(
//				createScheduleData(reqScheduleVO.getProcessRequestScheduleData()));
//		return requestScheduleVO;
//	}
	
	/**
	 * Helper method to convert schedule data from monitor {@link ProcessRequestScheduleData} 
	 * to schedule data of core {@link ScheduleData} for single record. 
	 * 
	 * @param reqScheduleData
	 * 			The instance of {@link ProcessRequestScheduleData}
	 *  
	 * @return schedule data of core {@link ScheduleData}
	 */
	private ScheduleData createScheduleData(ProcessRequestScheduleData reqScheduleData){
		ScheduleData scheduleData = new ScheduleData();
		if(reqScheduleData != null)
			scheduleData.setSchId(reqScheduleData.getSchId());
		return scheduleData;
	}
}
