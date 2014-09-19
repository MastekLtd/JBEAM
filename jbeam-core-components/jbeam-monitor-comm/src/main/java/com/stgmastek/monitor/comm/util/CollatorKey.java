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

import java.io.Serializable;

/**
 * The collator key class. A POJO class containing fields to uniquely identify a batch.  
 *   
 * @author grahesh.shanbhag
 * 
 */
public class CollatorKey implements Serializable{

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/** The installation code */
	private String installationCode;
	
	/** The batch number */
	private Integer batchNo;
	
	/** The batch revision number */
	private Integer batchRevNo;
	
	private Long time;
	
	
	/**
	 * Returns the installationCode
	 * 
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * Sets the installationCode to set
	 * 
	 * @param installationCode
	 * 		  The installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	/**
	 * Returns the batchNo
	 * 
	 * @return the batchNo
	 */
	public Integer getBatchNo() {
		return batchNo;
	}
	/**
	 * Sets the batchNo to set
	 * 
	 * @param batchNo
	 * 		  The batchNo to set
	 */
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * Returns the batchRevNo
	 * 
	 * @return the batchRevNo
	 */
	public Integer getBatchRevNo() {
		return batchRevNo;
	}
	/**
	 * Sets the batchRevNo to set
	 * 
	 * @param batchRevNo
	 * 		  The batchRevNo to set
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}
	
	/**
	 * Overridden .equals() method to check the equality of two instances of the key
	 * 
	 * @param object
	 * 		  The object to be compared
	 * @return true if the object supplied is equal to the current object
	 */
	
	public boolean equals(Object object) {
		
		if(!(object instanceof CollatorKey))
			return false;
		
		CollatorKey inKey = (CollatorKey) object;
		
		if( this.installationCode != null && this.installationCode.equals(inKey.getInstallationCode()) 
				&& this.batchNo != null && this.batchNo.equals(inKey.getBatchNo())
				&& this.batchRevNo != null && this.batchRevNo.equals(inKey.getBatchRevNo())
		){
			return true;
		}
		
		return false;
	}
	
	/**
	 * General implementation of the hashCode() for storing the objects of CollatorKey as 
	 * key in a Map
	 * 
	 * @return int the hash code
	 */
	
	public int hashCode() {
		return this.installationCode.hashCode() + this.batchNo.hashCode() + this.batchNo.hashCode(); 
	}

	/**
	 * Data collected time stamp to be set.
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * Returns the time when the data was collected.
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/CollatorKey.java                                                                     $
 * 
 * 5     3/15/10 12:19p Mandar.vaidya
 * Removed graphId with getter and setter methods.
 * 
 * 4     3/11/10 4:24p Kedarr
 * Changed the graph data log table and its related changes.
 * 
 * 3     12/21/09 10:20a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/