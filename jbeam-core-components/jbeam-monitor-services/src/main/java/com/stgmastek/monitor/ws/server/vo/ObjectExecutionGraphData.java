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

public class ObjectExecutionGraphData implements Serializable, Comparable<ObjectExecutionGraphData> {

	private static final long serialVersionUID = 1L;

	private String installationCode;
	private String graphId;
	private Long collectTime;
	private String graphXAxis;
	private String graphYAxis;
	private Double graphValue;
	private Integer batchNo;
	private Integer batchRevNo;

	/**
	 * Gets the installationCode
	 * 
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}

	/**
	 * Sets the installationCode
	 * 
	 * @param installationCode
	 *            The installationCode to set.
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}

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
	 *            The batchNo to set.
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
	 *            The batchRevNo to set.
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}

	/**
	 * Gets the graphId.
	 * 
	 * @return the graphId
	 */
	public String getGraphId() {
		return graphId;
	}

	/**
	 * Sets the graphId.
	 * 
	 * @param graphId
	 *            The graphId to set
	 */
	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	/**
	 * Gets the collectTime.
	 * 
	 * @return the collectTime
	 */
	public Long getCollectTime() {
		return collectTime;
	}

	/**
	 * Sets the collectTime.
	 * 
	 * @param collectTime
	 *            The collectTime to set
	 */
	public void setCollectTime(Long collectTime) {
		this.collectTime = collectTime;
	}

	/**
	 * Gets the graphXAxis.
	 * 
	 * @return the graphXAxis
	 */
	public String getGraphXAxis() {
		return graphXAxis;
	}

	/**
	 * Sets the graphXAxis.
	 * 
	 * @param graphXAxis
	 *            The graphXAxis to set
	 */
	public void setGraphXAxis(String graphXAxis) {
		this.graphXAxis = graphXAxis;
	}

	/**
	 * Gets the graphYAxis.
	 * 
	 * @return the graphYAxis
	 */
	public String getGraphYAxis() {
		return graphYAxis;
	}

	/**
	 * Sets the graphYAxis.
	 * 
	 * @param graphYAxis
	 *            The graphYAxis to set
	 */
	public void setGraphYAxis(String graphYAxis) {
		this.graphYAxis = graphYAxis;
	}

	/**
	 * Gets the graphValue.
	 * 
	 * @return the graphValue
	 */
	public Double getGraphValue() {
		return graphValue;
	}

	/**
	 * Sets the graphValue.
	 * 
	 * @param graphValue
	 *            The graphValue to set
	 */
	public void setGraphValue(Double graphValue) {
		this.graphValue = graphValue;
	}

	/**
	 * Gets the serialVersionUID.
	 * 
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	
	public int compareTo(ObjectExecutionGraphData o) {
		if (this.getGraphId().equals(o.getGraphId()) && this.batchNo == o.batchNo && this.batchRevNo == o.batchRevNo) {
			return new Long(this.collectTime - o.collectTime).intValue();
		}
		return 0;
	}

}

/*
 * Revision Log ------------------------------- $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ObjectExecutionGraphData.java               $
 * 
 * 2     3/12/10 10:56a Mandar.vaidya
 * Added new fields and removed some of the old fields.
 * 
 * 1 1/06/10 12:09p Grahesh Initial Version
 */