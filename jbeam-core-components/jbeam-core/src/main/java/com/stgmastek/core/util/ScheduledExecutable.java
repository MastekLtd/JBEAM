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
package com.stgmastek.core.util;

import java.io.Serializable;

/**
 * The listener request class. 
 * It consists of all the fields as in the PROCESS_REQUEST table.
 * 
 * For more information check 
 * <a href="http://stgpedia.stgil-india.com/index.php/PROCESS_REQUEST" >STGPedia</a> 
 * for the field (column) related information.
 * 
 * @author grahesh.shanbhag
 *
 */
public class ScheduledExecutable implements Serializable{
	
	/** The serial version UID */
	private static final long serialVersionUID = -1127444643206828111L;

	
	/** The Process Request Identifier */
	private Integer reqId;
	
	/** The listener identifier */
	private Integer listenerId;
	
	/**
	 * Returns the reqId
	 * 
	 * @return the reqId
	 */
	public Integer getReqId() {
		return reqId;
	}
	
	/**
	 * Sets the reqId
	 * 
	 * @param reqId 
	 *        The reqId to set
	 */
	public void setReqId(Integer reqId) {
		this.reqId = reqId;
	}

	/**
	 * Returns the listenerId
	 * 
	 * @return the listenerId
	 */
	public Integer getListenerId() {
		return listenerId;
	}
	
	/**
	 * Sets the listenerId
	 * 
	 * @param listenerId 
	 *        The listenerId to set
	 */
	public void setListenerId(Integer listenerId) {
		this.listenerId = listenerId;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/ScheduledExecutable.java                                                                            $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/