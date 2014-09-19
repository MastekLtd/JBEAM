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

/**
 * Interface for message processors 
 * 
 * Contains the method declarations for the implementing classes
 *
 * @author grahesh.shanbhag
 *
 */
public interface IMessageProcessor {

	/**
	 * The method to process a message 
	 * 
	 * @param message
	 * 		  The message under consideration 
	 * @param context
	 * 		  The batch context 
	 * @return true, if the message is successfully processed 
	 * @throws Throwable
	 * 		   Any exception occurred during the processing of the message
	 */
	public Boolean processMessage(Message message, BatchContext context) throws Throwable; 
		
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/IMessageProcessor.java                                                                              $
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/