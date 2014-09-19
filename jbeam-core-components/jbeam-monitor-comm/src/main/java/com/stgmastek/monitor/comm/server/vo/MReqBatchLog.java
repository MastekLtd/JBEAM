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

public class MReqBatchLog extends MBaseRequestVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3054285621953270291L;

	private List<BatchLog> list;

	/**
	 * Sets the list
	 *
	 * @param list 
	 *        The list to set
	 */
	public void setList(List<BatchLog> list) {
		this.list = list;
	}

	/**
	 * Gets the list
	 *
	 * @return the list
	 */
	public List<BatchLog> getList() {
		return list;
	}
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/vo/MReqBatchLog.java                                                               $
 * 
 * 6     3/22/10 12:09p Mandar.vaidya
 * Added 2 more fields usedMemoryBefore and usedMemoryAfter with getter and setter methods.
 * 
 * 5     3/19/10 1:02p Mandar.vaidya
 * Added the field operationCode with getter and setter methods.
 * 
 * 4     3/15/10 12:48p Mandar.vaidya
 * Added the field cycleNo with getter and setter methods.
 * 
 * 3     12/18/09 4:19p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/