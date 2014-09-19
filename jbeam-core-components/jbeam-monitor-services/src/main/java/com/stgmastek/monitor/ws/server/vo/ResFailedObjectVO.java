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

public class ResFailedObjectVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private List<FailedObjectDetails> failedObjectsData;

	/**
	 * Gets the failedObjectsData.
	 *
	 * @return the failedObjectsData
	 */
	public List<FailedObjectDetails> getFailedObjectsData() {
		return failedObjectsData;
	}

	/**
	 * Sets the failedObjectsData.
	 *
	 * @param failedObjectsData 
	 *        The failedObjectsData to set
	 */
	public void setFailedObjectsData(List<FailedObjectDetails> failedObjectsData) {
		this.failedObjectsData = failedObjectsData;
	}
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResFailedObjectVO.java                                                        $
 * 
 * 1     1/06/10 11:35a Grahesh
 * Initial Version
 * 
*
*/