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
 * Class to hold the data for Graph
 * 
 * @author grahesh.shanbhag
 * 
 */
public class GraphData extends CollatorKey implements Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	private Integer slNo;
	private String graphXAxis;
	private String graphYAxis;
	private Double graphValue;
	private String graphId;

	/**
	 * Returns the slNo
	 * 
	 * @return the slNo
	 */
	public Integer getSlNo() {
		return slNo;
	}

	/**
	 * Sets the slNo to set
	 * 
	 * @param slNo
	 *            The slNo to set
	 */
	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	/**
	 * @return the graphXAxis
	 */
	public String getGraphXAxis() {
		return graphXAxis;
	}

	/**
	 * @param graphXAxis
	 *            the graphXAxis to set
	 */
	public void setGraphXAxis(String graphXAxis) {
		this.graphXAxis = graphXAxis;
	}

	/**
	 * @return the graphYAxis
	 */
	public String getGraphYAxis() {
		return graphYAxis;
	}

	/**
	 * @param graphYAxis
	 *            the graphYAxis to set
	 */
	public void setGraphYAxis(String graphYAxis) {
		this.graphYAxis = graphYAxis;
	}

	/**
	 * @return the graphValue
	 */
	public Double getGraphValue() {
		return graphValue;
	}

	/**
	 * @param graphValue
	 *            the graphValue to set
	 */
	public void setGraphValue(Double graphValue) {
		this.graphValue = graphValue;
	}
	public void setGraphValue(Integer graphValue) {
		this.graphValue = Double.parseDouble(Integer.toString(graphValue));		
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

}

/*
 * Revision Log ------------------------------- $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/GraphData.java          $
 * 
 * 6     3/15/10 12:27p Mandar.vaidya
 * Added the field graphId with getter & setter methods.
 * 
 * 5     3/12/10 10:50a Kedarr
 * refactored.
 * 
 * 4 3/11/10 4:24p Kedarr Changed the graph data log table and its related
 * changes.
 * 
 * 3 12/21/09 10:20a Grahesh Updated the comments
 * 
 * 2 12/17/09 11:59a Grahesh Initial Version
 */