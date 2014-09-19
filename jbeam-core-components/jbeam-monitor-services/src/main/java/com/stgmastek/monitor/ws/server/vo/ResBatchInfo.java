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

import java.util.List;

public class ResBatchInfo extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private BatchDetails batchDetails;
	
	private List<BatchDetails> batchDetailsList;
	
	private List<InstructionParameter> instructionParametersList;
	
	private Boolean scheduledBatch;
	
	private Boolean revisionBatch;

	/**
	 * Gets the batchDetailsList.
	 *
	 * @return the batchDetailsList
	 */
	public List<BatchDetails> getBatchDetailsList() {
		return batchDetailsList;
	}

	/**
	 * Sets the batchDetailsList.
	 *
	 * @param batchDetailsList 
	 *        The batchDetailsList to set
	 */
	public void setBatchDetailsList(List<BatchDetails> batchDetailsList) {
		this.batchDetailsList = batchDetailsList;
	}

	/**
	 * Gets the batchDetails.
	 *
	 * @return the batchDetails
	 */
	public BatchDetails getBatchDetails() {
		return batchDetails;
	}

	/**
	 * Sets the batchDetails.
	 *
	 * @param batchDetails 
	 *        The batchDetails to set
	 */
	public void setBatchDetails(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}

	/**
	 * Sets the instructionParametersList
	 *
	 * @param instructionParametersList 
	 *        The instructionParametersList to set.
	 */
	public void setInstructionParametersList(
			List<InstructionParameter> instructionParametersList) {
		this.instructionParametersList = instructionParametersList;
	}

	/**
	 * Gets the instructionParametersList
	 *
	 * @return the instructionParametersList
	 */
	public List<InstructionParameter> getInstructionParametersList() {
		return instructionParametersList;
	}

	/**
	 * Gets the scheduledBatch
	 *
	 * @return the scheduledBatch
	 */
	public Boolean isScheduledBatch() {
		return scheduledBatch;
	}

	/**
	 * Sets the scheduledBatch
	 *
	 * @param scheduledBatch 
	 *        The scheduledBatch to set.
	 */
	public void setScheduledBatch(Boolean scheduledBatch) {
		this.scheduledBatch = scheduledBatch;
	}

	/**
	 * Gets the revisionBatch
	 *
	 * @return the revisionBatch
	 */
	public Boolean isRevisionBatch() {
		return this.revisionBatch;
	}

	/**
	 * Sets the revisionBatch
	 *
	 * @param revisionBatch 
	 *        The revisionBatch to set
	 */
	public void setRevisionBatch(Boolean revisionBatch) {
		this.revisionBatch = revisionBatch;
	}

	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResBatchInfo.java                                                             $
 * 
 * 4     7/10/10 2:03p Mandar.vaidya
 * Added the field instructionParameterList.
 * 
 * 3     1/06/10 5:04p Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/