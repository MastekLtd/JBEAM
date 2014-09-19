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

import java.util.HashMap;

import com.stgmastek.core.util.messagehandler.InterruptBatch;

/**
 * Message constant class to register the messages in the system 
 * 
 * @author grahesh.shanbhag
 *
 */
public final class MessageConstants {
	
	/**
	 * Private constructor to avoid any outside instantiation 
	 */
	private MessageConstants() {
	}

	/**
	 * MESSAGE_MAP containing the registered message within the system.
	 * 
	 * If a new message is to be introduced then it is imperative an entry in the map is made. 
	 *  
	 */
	private static HashMap<String, Class<? extends IMessageProcessor>> MESSAGE_MAP 
								= new HashMap<String, Class<? extends IMessageProcessor>>();

	/** Registered messages */
	static {
		MESSAGE_MAP.put("BSSTOBATCH", InterruptBatch.class);
		
	}
	
	
	/**
	 * Returns the map containing the registered messages within the system.
	 * 
	 * @return an instance of HashMap<String, Class<? extends IMessageProcessor>> containing the registered messages within the system. 
	 */
	public static HashMap<String, Class<? extends IMessageProcessor>> getMessageMap(){
		return MESSAGE_MAP;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/MessageConstants.java                                                                               $
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/