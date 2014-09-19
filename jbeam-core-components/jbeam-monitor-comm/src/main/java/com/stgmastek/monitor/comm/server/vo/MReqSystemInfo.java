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

public class MReqSystemInfo extends MBaseRequestVO implements Serializable {

	/** Default Serial Version UID	 */
	private static final long serialVersionUID = 1L;
	private String installationCode ;
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
	 * Returns the javaVersion
	 * 
	 * @return the javaVersion
	 */
	public String getJavaVersion() {
		return javaVersion;
	}
	/**
	 * Sets the javaVersion to set
	 * 
	 * @param javaVersion
	 * 		  The javaVersion to set
	 */
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	/**
	 * Returns the preVersion
	 * 
	 * @return the preVersion
	 */
	public String getPreVersion() {
		return preVersion;
	}
	/**
	 * Sets the preVersion to set
	 * 
	 * @param preVersion
	 * 		  The preVersion to set
	 */
	public void setPreVersion(String preVersion) {
		this.preVersion = preVersion;
	}
	/**
	 * Returns the osConfig
	 * 
	 * @return the osConfig
	 */
	public String getOsConfig() {
		return osConfig;
	}
	/**
	 * Sets the osConfig to set
	 * 
	 * @param osConfig
	 * 		  The osConfig to set
	 */
	public void setOsConfig(String osConfig) {
		this.osConfig = osConfig;
	}
	/**
	 * Returns the outputDirPath
	 * 
	 * @return the outputDirPath
	 */
	public String getOutputDirPath() {
		return outputDirPath;
	}
	/**
	 * Sets the outputDirPath to set
	 * 
	 * @param outputDirPath
	 * 		  The outputDirPath to set
	 */
	public void setOutputDirPath(String outputDirPath) {
		this.outputDirPath = outputDirPath;
	}
	/**
	 * Returns the outputDirFreeMem
	 * 
	 * @return the outputDirFreeMem
	 */
	public String getOutputDirFreeMem() {
		return outputDirFreeMem;
	}
	/**
	 * Sets the outputDirFreeMem to set
	 * 
	 * @param outputDirFreeMem
	 * 		  The outputDirFreeMem to set
	 */
	public void setOutputDirFreeMem(String outputDirFreeMem) {
		this.outputDirFreeMem = outputDirFreeMem;
	}
	/**
	 * Sets the maxMemory.
	 *
	 * @param maxMemory 
	 *        The maxMemory to set.
	 */
	public void setMaxMemory(Integer maxMemory) {
		this.maxMemory = maxMemory;
	}
	/**
	 * Gets the maxMemory.
	 *
	 * @return the maxMemory
	 */
	public Integer getMaxMemory() {
		return maxMemory;
	}
	/**
	 * Sets the usedMemory.
	 *
	 * @param usedMemory 
	 *        The usedMemory to set.
	 */
	public void setUsedMemory(Integer usedMemory) {
		this.usedMemory = usedMemory;
	}
	/**
	 * Gets the usedMemory.
	 *
	 * @return the usedMemory
	 */
	public Integer getUsedMemory() {
		return usedMemory;
	}
	
	

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/vo/MReqSystemInfo.java                                                             $
 * 
 * 3     3/22/10 1:28p Mandar.vaidya
 * Added maxMemory and usedMemory with getter and setter methods.
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/