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

import java.io.Serializable;

public class SystemDetails implements Serializable{

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/** the fields from the BATCH_SYSTEM_INFO table and are self explanatory */
	private Integer batchNo;
	private Integer batchRevNo;
	private String javaVersion;
	private String preVersion;
	private String osConfig;
	private String outputDirPath;
	private String outputDirFreeMem;
	private Integer maxMemory;
	private Integer usedMemory;
	
	/**
	 * Gets the batchNo
	 *
	 * @return the batchNo
	 */
	public Integer getBatchNo() {
		return batchNo;
	}
	/**
	 * Sets the batchNo
	 *
	 * @param batchNo 
	 *        The batchNo to set.
	 */
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * Gets the batchRevNo
	 *
	 * @return the batchRevNo
	 */
	public Integer getBatchRevNo() {
		return batchRevNo;
	}
	/**
	 * Sets the batchRevNo
	 *
	 * @param batchRevNo 
	 *        The batchRevNo to set.
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}
	/**
	 * Gets the javaVersion
	 *
	 * @return the javaVersion
	 */
	public String getJavaVersion() {
		return javaVersion;
	}
	/**
	 * Sets the javaVersion
	 *
	 * @param javaVersion 
	 *        The javaVersion to set.
	 */
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	/**
	 * Gets the preVersion
	 *
	 * @return the preVersion
	 */
	public String getPreVersion() {
		return preVersion;
	}
	/**
	 * Sets the preVersion
	 *
	 * @param preVersion 
	 *        The preVersion to set.
	 */
	public void setPreVersion(String preVersion) {
		this.preVersion = preVersion;
	}
	/**
	 * Gets the osConfig
	 *
	 * @return the osConfig
	 */
	public String getOsConfig() {
		return osConfig;
	}
	/**
	 * Sets the osConfig
	 *
	 * @param osConfig 
	 *        The osConfig to set.
	 */
	public void setOsConfig(String osConfig) {
		this.osConfig = osConfig;
	}
	/**
	 * Gets the outputDirPath
	 *
	 * @return the outputDirPath
	 */
	public String getOutputDirPath() {
		return outputDirPath;
	}
	/**
	 * Sets the outputDirPath
	 *
	 * @param outputDirPath 
	 *        The outputDirPath to set.
	 */
	public void setOutputDirPath(String outputDirPath) {
		this.outputDirPath = outputDirPath;
	}
	/**
	 * Gets the outputDirFreeMem
	 *
	 * @return the outputDirFreeMem
	 */
	public String getOutputDirFreeMem() {
		return outputDirFreeMem;
	}
	/**
	 * Sets the outputDirFreeMem
	 *
	 * @param outputDirFreeMem 
	 *        The outputDirFreeMem to set.
	 */
	public void setOutputDirFreeMem(String outputDirFreeMem) {
		this.outputDirFreeMem = outputDirFreeMem;
	}
	/**
	 * @param maxMemory the maxMemory to set
	 */
	public void setMaxMemory(Integer maxMemory) {
		this.maxMemory = maxMemory;
	}
	/**
	 * @return the maxMemory
	 */
	public Integer getMaxMemory() {
		return maxMemory;
	}
	/**
	 * @param usedMemory the usedMemory to set
	 */
	public void setUsedMemory(Integer usedMemory) {
		this.usedMemory = usedMemory;
	}
	/**
	 * @return the usedMemory
	 */
	public Integer getUsedMemory() {
		return usedMemory;
	}
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/SystemDetails.java                                                            $
 * 
 * 2     3/22/10 1:42p Mandar.vaidya
 * Added maxMemory and usedMemory with getter and setter methods.
 * 
 * 1     1/06/10 2:48p Grahesh
 * Initial Version
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/