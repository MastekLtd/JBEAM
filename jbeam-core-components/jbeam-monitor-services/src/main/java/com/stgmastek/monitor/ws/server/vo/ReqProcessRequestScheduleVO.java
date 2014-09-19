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
package com.stgmastek.monitor.ws.server.vo;

import java.util.List;

public class ReqProcessRequestScheduleVO  extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private ProcessRequestScheduleData processRequestScheduleData;
	private List<ProcessRequestScheduleData> processRequestScheduleList;
	
	/**
	 * Gets the installationCode
	 *
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * Sets the installationCode
	 *
	 * @param installationCode 
	 *		  The installationCode to set 				   
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}

	/**
	 * Gets the processRequestScheduleData
	 *
	 * @return the processRequestScheduleData
	 */
	public ProcessRequestScheduleData getProcessRequestScheduleData() {
		return processRequestScheduleData;
	}
	/**
	 * Sets the processRequestScheduleData
	 *
	 * @param processRequestScheduleData 
	 *		  The processRequestScheduleData to set	
	 */
	public void setProcessRequestScheduleData(
			ProcessRequestScheduleData processRequestScheduleData) {
		this.processRequestScheduleData = processRequestScheduleData;
	}
	/**
	 * Gets the processRequestScheduleList
	 *
	 * @return the processRequestScheduleList
	 */
	public List<ProcessRequestScheduleData> getProcessRequestScheduleList() {
		return processRequestScheduleList;
	}
	/**
	 * Sets the processRequestScheduleList
	 *
	 * @param processRequestScheduleList 
	 *		  The processRequestScheduleList to set	
	 */
	public void setProcessRequestScheduleList(
			List<ProcessRequestScheduleData> processRequestScheduleList) {
		this.processRequestScheduleList = processRequestScheduleList;
	}
	
	

}
