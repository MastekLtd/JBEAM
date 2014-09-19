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
package com.stgmastek.core.comm.server.vo;

public class CResSystemInfo extends CBaseResponseVO {

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/* The fields from the SYSTEM_INFO table and are self explanatory */
	private Integer batchNo;
	private Integer batchRevNo;
	private String javaVersion;
	private String preVersion;
	private String osConfig;
	private String outputDirPath;
	private String outputDirFreeMem;
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
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CResSystemInfo.java                                                                   $
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/