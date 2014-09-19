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
package com.stgmastek.monitor.comm.util;


/**
 * Special base class for all the collators (special program meant for collating data and storing
 * it in tables for easier access)
 *   
 * @author grahesh.shanbhag
 * 
 */
public abstract class Collator extends BasePoller {
	
	/** The key needed to collate the data */
	protected CollatorKey collatorKey;
	
	/** Flag is needed to iterate or loop once more than the actual STOP signal*/
	protected Boolean CHECK_STOP = false;
	
	
	/**
	 * Constructor that takes the dedicated connection and the key as the input
	 * 
	 * @param collatorKey
	 * 		  The collator key attributes
	 */
	public Collator(CollatorKey collatorKey) {
		this.collatorKey = collatorKey;
	}
	
	/**
	 * Sets the graphId.
	 *
	 * @param graphId 
	 *        The graphId to set.
	 */
	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	/**
	 * Gets the graphId.
	 *
	 * @return the graphId
	 */
	public String getGraphId() {
		return graphId;
	}

	private String graphId;

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/Collator.java                                                                        $
 * 
 * 4     3/15/10 12:20p Mandar.vaidya
 * Removed the constant GRAPH_COLLATOR.
 * Added the field graphId with getter & setter methods.
 * 
 * 3     3/12/10 4:28p Kedarr
 * Removed unnecessary prepared statements from the base poller
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/