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

package com.stgmastek.monitor.comm.server.vo;

import java.io.Serializable;
import java.util.List;

public class MReqBatchProgress extends MBaseRequestVO implements Serializable {

	/** Default Serial Version UID	 */
	private static final long serialVersionUID = 5854480082309258972L;
	
	private List<BatchProgress> batchProgressList;

	/**
	 * Sets the batchProgressList
	 *
	 * @param batchProgressList 
	 *        The batchProgressList to set
	 */
	public void setBatchProgressList(List<BatchProgress> batchProgressList) {
		this.batchProgressList = batchProgressList;
	}

	/**
	 * Gets the batchProgressList
	 *
	 * @return the batchProgressList
	 */
	public List<BatchProgress> getBatchProgressList() {
		return batchProgressList;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/vo/MReqBatchProgress.java                                                          $
 * 
 * 4     3/11/10 2:22p Mandar.vaidya
 * Added failedOver field with getter & setter methods.
 * 
 * 3     12/18/09 4:19p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/