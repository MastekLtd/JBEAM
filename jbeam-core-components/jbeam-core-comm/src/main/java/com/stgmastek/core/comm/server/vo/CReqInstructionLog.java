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

import java.io.Serializable;
import java.util.List;

public class CReqInstructionLog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/* The fields from the INSTRUCTION_LOG table and self explanatory */
	private Integer seqNo;
	private Integer batchNo;
	private Integer batchRevNo;
	private String message;
	private String messageParam;
	private String instructingUser;
	private Long instructionTime;
	private String batchAction;	
	private Long batchActionTime;
	private List<InstructionParameters> instructionParametersList;
	
	/**
	 * Returns the seqNo
	 * 
	 * @return the seqNo
	 */
	
	public Integer getSeqNo() {
		return seqNo;
	}
	/**
	 * Sets the seqNo to set
	 * 
	 * @param seqNo
	 * 		  The seqNo to set
	 */
	
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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
	 * Returns the message
	 * 
	 * @return the message
	 */
	
	public String getMessage() {
		return message;
	}
	/**
	 * Sets the message to set
	 * 
	 * @param message
	 * 		  The message to set
	 */
	
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Returns the messageParam
	 * 
	 * @return the messageParam
	 */
	
	public String getMessageParam() {
		return messageParam;
	}
	/**
	 * Sets the messageParam to set
	 * 
	 * @param messageParam
	 * 		  The messageParam to set
	 */
	
	public void setMessageParam(String messageParam) {
		this.messageParam = messageParam;
	}
	/**
	 * Returns the instructingUser
	 * 
	 * @return the instructingUser
	 */
	
	public String getInstructingUser() {
		return instructingUser;
	}
	/**
	 * Sets the instructingUser to set
	 * 
	 * @param instructingUser
	 * 		  The instructingUser to set
	 */
	
	public void setInstructingUser(String instructingUser) {
		this.instructingUser = instructingUser;
	}
	/**
	 * Returns the instructionTime
	 * 
	 * @return the instructionTime
	 */
	
	public Long getInstructionTime() {
		return instructionTime;
	}
	/**
	 * Sets the instructionTime to set
	 * 
	 * @param instructionTime
	 * 		  The instructionTime to set
	 */
	
	public void setInstructionTime(Long instructionTime) {
		this.instructionTime = instructionTime;
	}
	/**
	 * Returns the batchAction
	 * 
	 * @return the batchAction
	 */
	
	public String getBatchAction() {
		return batchAction;
	}
	/**
	 * Sets the batchAction to set
	 * 
	 * @param batchAction
	 * 		  The batchAction to set
	 */
	
	public void setBatchAction(String batchAction) {
		this.batchAction = batchAction;
	}
	/**
	 * Returns the batchActionTime
	 * 
	 * @return the batchActionTime
	 */
	
	public Long getBatchActionTime() {
		return batchActionTime;
	}
	/**
	 * Sets the batchActionTime to set
	 * 
	 * @param batchActionTime
	 * 		  The batchActionTime to set
	 */
	
	public void setBatchActionTime(Long batchActionTime) {
		this.batchActionTime = batchActionTime;
	}
	/**
	 * Returns the instructionParametersList
	 * 
	 * @return the instructionParametersList
	 */
	
	public List<InstructionParameters> getInstructionParametersList() {
		return instructionParametersList;
	}
	/**
	 * Sets the instructionParametersList to set
	 * 
	 * @param instructionParametersList
	 * 		  The instructionParametersList to set
	 */
	
	public void setInstructionParametersList(
			List<InstructionParameters> instructionParametersList) {
		this.instructionParametersList = instructionParametersList;
	}
	
	
	
	
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CReqInstructionLog.java                                                               $
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/