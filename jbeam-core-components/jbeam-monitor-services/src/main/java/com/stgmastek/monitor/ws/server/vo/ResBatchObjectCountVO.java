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

public class ResBatchObjectCountVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	private BatchInfo batchInfo;
	private BatchObjectCount batchObjectCount;
	
	/**
	 * Returns the batchInfo
	 * 
	 * @return the batchInfo
	 */
	
	public BatchInfo getBatchInfo() {
		return batchInfo;
	}
	/**
	 * Sets the batchInfo to set
	 * 
	 * @param batchInfo
	 * 		  The batchInfo to set
	 */
	
	public void setBatchInfo(BatchInfo batchInfo) {
		this.batchInfo = batchInfo;
	}
	/**
	 * Gets the batchObjectCount.
	 *
	 * @return the batchObjectCount
	 */
	public BatchObjectCount getBatchObjectCount() {
		return batchObjectCount;
	}
	/**
	 * Sets the batchObjectCount.
	 *
	 * @param batchObjectCount 
	 *        The batchObjectCount to set
	 */
	public void setBatchObjectCount(BatchObjectCount batchObjectCount) {
		this.batchObjectCount = batchObjectCount;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResBatchObjectCountVO.java                                                       $
 * 
 * 5     1/06/10 5:04p Grahesh
 * Changed the object hierarchy
 * 
 * 4     1/06/10 10:49a Grahesh
 * Changed the object hierarchy
 * 
 * 3     1/06/10 10:46a Grahesh
 * Renamed BatchObjectCountVO.java to ResBatchObjectCountVO.java
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/