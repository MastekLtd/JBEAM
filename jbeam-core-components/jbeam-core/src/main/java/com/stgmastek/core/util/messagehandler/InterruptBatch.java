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
package com.stgmastek.core.util.messagehandler;

import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.IMessageProcessor;
import com.stgmastek.core.util.Message;
import com.stgmastek.core.util.Constants.CLOSURE_REASON;
import com.stgmastek.core.util.Constants.CONTEXT_KEYS;
import com.stgmastek.core.util.Constants.MESSAGE_KEY;

/**
 * 
 * Special final class that is used for interrupting a running batch 
 * 
 * @author grahesh.shanbhag
 *
 */
public final class InterruptBatch implements IMessageProcessor{

	/**
	 * The method to interrupt the batch (implementing super class method) 
	 * 
	 * @param message
	 * 		  The message under consideration 
	 * @param context
	 * 		  The batch context 
	 * @return true, if the message is successfully processed 
	 * @throws Throwable
	 * 		   Any exception occurred during the processing of the message
	 */
	
	public Boolean processMessage(Message message, BatchContext context) throws Throwable {
		String userId = Constants.DEFAULT_USER;
		if (message.getParams().containsKey(MESSAGE_KEY.USER_ID.getKey())) {
			userId = message.getParams().get(MESSAGE_KEY.USER_ID.getKey());
		}
		//Inform the batch components to shut down safely
		context.getBatchInfo().setEndUser(userId);
		context.setPreContextAttribute(context.getBatchInfo().getBatchNo(), CONTEXT_KEYS.JBEAM_EXIT.name(), "Y");
		
		//Also set the reason
		context.setPreContextAttribute(context.getBatchInfo().getBatchNo(), CONTEXT_KEYS.JBEAM_EXIT_REASON.name(), CLOSURE_REASON.USER_INTERRUPTED.name());
		
		return true;		
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/messagehandler/InterruptBatch.java                                                                  $
 * 
 * 6     2/25/10 10:27a Grahesh
 * Modified to make use of context keys enum from constants class.
 * 
 * 5     12/22/09 10:28a Grahesh
 * Updation done to include the closure reason.
 * 
 * 4     12/21/09 5:11p Grahesh
 * Added code closure reason
 * 
 * 3     12/18/09 12:35p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/