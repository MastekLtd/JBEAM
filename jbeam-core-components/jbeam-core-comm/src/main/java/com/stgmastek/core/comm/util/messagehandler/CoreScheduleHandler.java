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
package com.stgmastek.core.comm.util.messagehandler;

import stg.pr.engine.scheduler.ISchedule;

import com.stgmastek.core.comm.exception.MonitorServiceDownException;
import com.stgmastek.core.comm.server.vo.CReqProcessRequestScheduleVO;
import com.stgmastek.core.comm.server.vo.ScheduleData;
import com.stgmastek.core.comm.util.ClientBook;
import com.stgmastek.monitor.comm.client.MonitorCommServices;

/**
 * Special class to handle schedule related data
 * 
 * @author Mandar Vaidya
 */
public class CoreScheduleHandler{

	private static CoreScheduleHandler coreScheduleHandler;
	private MonitorCommServices service;
	private final String CANCEL_STR = "] Cancelled";
	private final String NO_CANCEL_STR = "] Could not be cancelled";
	
	private CoreScheduleHandler(){}

	public static CoreScheduleHandler getInstance(){
		if(coreScheduleHandler == null)
			coreScheduleHandler = new CoreScheduleHandler();
		
		return coreScheduleHandler;
	}
	
	/**
	 * Sets the service by using the given installation code
	 *   
	 */
	public void setService() {
		//Get the service handle 
		try {
			service = ClientBook.getBook().getClientClass("MONITOR_WS");
		} catch (MonitorServiceDownException e) {
		}
	}
	/**
	 * Proxy method to call the refreshProcessRequestSchedule method of monitor-comm
	 * service.
	 * 
	 * @param requestScheduleVO
	 * 		  The reference of {@link CReqProcessRequestScheduleVO}.
	 * 
	 * @throws Throwable
	 */
	public void refreshScheduleData(CReqProcessRequestScheduleVO requestScheduleVO) 
		throws Throwable {
		service.refreshProcessRequestSchedule(createCReqProcessRequestScheduleVO(requestScheduleVO));	 
	}
	/**
	 * Helper class to convert {@link CReqProcessRequestScheduleVO} 
	 * to {@link com.stgmastek.monitor.comm.client.CReqProcessRequestScheduleVO}
	 * 
	 * @param requestScheduleVO
	 *  	  The reference of {@link CReqProcessRequestScheduleVO}.
	 * 
	 * @return reference to {@link com.stgmastek.monitor.comm.client.CReqProcessRequestScheduleVO} 
	 */
	private com.stgmastek.monitor.comm.client.CReqProcessRequestScheduleVO createCReqProcessRequestScheduleVO(
			CReqProcessRequestScheduleVO requestScheduleVO){
		com.stgmastek.monitor.comm.client.CReqProcessRequestScheduleVO cReqProcessRequestScheduleVO
					= new com.stgmastek.monitor.comm.client.CReqProcessRequestScheduleVO();
		
		cReqProcessRequestScheduleVO.setScheduleData(createScheduleData(requestScheduleVO.getScheduleData()));
		return cReqProcessRequestScheduleVO;
	}

	/**
	 * Helper class to convert {@link ScheduleData} 
	 * to {@link com.stgmastek.monitor.comm.client.ScheduleData}
	 * 
	 * @param scheduleData
	 *  	  The reference of {@link ScheduleData}.
	 * 
	 * @return reference to {@link com.stgmastek.monitor.comm.client.ScheduleData} 
	 */
	private com.stgmastek.monitor.comm.client.ScheduleData createScheduleData(
			ScheduleData scheduleData) {
		com.stgmastek.monitor.comm.client.ScheduleData data = 
			new com.stgmastek.monitor.comm.client.ScheduleData();
		data.setInstallationCode(scheduleData.getInstallationCode());
		data.setBatchName(scheduleData.getBatchName());
		data.setSchId(scheduleData.getSchId());
		data.setSchStat(scheduleData.getSchStat());
		data.setSkipFlag(scheduleData.getSkipFlag());		
		data.setEmailIds(scheduleData.getEmailIds());
	    data.setEndDt(scheduleData.getEndDt());
	    data.setEndOccur(scheduleData.getEndOccur());
	    data.setEndReason(scheduleData.getEndReason());
	    data.setEndTime(scheduleData.getEndTime());
	    data.setEntryDt(scheduleData.getEntryDt());
	    data.setFixedDate(scheduleData.getFixedDate());
	    data.setFreqType(scheduleData.getFreqType());
	    data.setFutureSchedulingOnly(scheduleData.getFutureSchedulingOnly());
	    data.setKeepAlive(scheduleData.getKeepAlive());
	    data.setModifyDt(scheduleData.getModifyDt());
	    data.setModifyId(scheduleData.getModifyId());
	    data.setOccurCounter(scheduleData.getOccurCounter());
	    data.setOnWeekDay(scheduleData.getOnWeekDay());
	    data.setProcessClassNm(scheduleData.getProcessClassNm());
	    data.setRecur(scheduleData.getRecur());
	    data.setReqStat(scheduleData.getReqStat());	    
	    data.setStartDt(scheduleData.getStartDt());
	    data.setStartTime(scheduleData.getStartTime());
	    data.setUserId(scheduleData.getUserId());
	    data.setWeekdayCheckFlag(scheduleData.getWeekdayCheckFlag());
		
		return data;
	}
	
	
	public String getScheduleMessage(Integer schId, Integer[] status, String schStat){		
		StringBuilder sb = new StringBuilder();
		sb.append("Queued Request for [");
		sb.append(schId);
		sb.append(getCancelStatus(status[0]));
		sb.append(", Schedule for [");
		sb.append(schId);
		sb.append(getCancelStatus(status[1]));
		if (status[1].intValue() == 0) {
			sb.append(". \nThe schedule in core was already ");
			sb.append(ISchedule.SCHEDULE_STATUS.resolve(schStat).getDescription());
		}
		return sb.toString();
	}
	
	private String getCancelStatus(int stat){
		String status = null;
		if (stat == 1) {
			status = CANCEL_STR;
		} else {
			status = NO_CANCEL_STR;
		}
		return status;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/messagehandler/TransmitBatchDetails.java                                                   $
 * 
 * 4     6/21/10 11:38a Lakshmanp
 * added the code to get dao from DAOFactory and passed the connection parameter to dao methods
 * 
 * 3     12/18/09 4:00p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/