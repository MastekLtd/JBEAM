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

import java.util.HashMap;

import com.stgmastek.monitor.comm.util.messagehandler.AddCalendar;
import com.stgmastek.monitor.comm.util.messagehandler.RunBatch;
import com.stgmastek.monitor.comm.util.messagehandler.RunReport;
import com.stgmastek.monitor.comm.util.messagehandler.StopBatch;

/**
 * Message constants to register all messages that could be processed in the system  
 */
public final class MessageConstants {

	private MessageConstants() {
	}

	public static final HashMap<String, Class<? extends IMessageProcessor>> MESSAGE_MAP 
								= new HashMap<String, Class<? extends IMessageProcessor>>();

	static {
		MESSAGE_MAP.put("BSRUNBATCH", RunBatch.class);
		MESSAGE_MAP.put("BSSTOBATCH", StopBatch.class);
		MESSAGE_MAP.put("BSCALENDAR", AddCalendar.class);
		MESSAGE_MAP.put("RUNEREPORT", RunReport.class);
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/MessageConstants.java                                                                $
 * 
 * 5     4/09/10 8:51a Mandar.vaidya
 * Added new message BSCALENDAR
 * 
 * 4     1/06/10 10:02a Grahesh
 * Added handler for stopping a batch
 * 
 * 3     12/21/09 10:20a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/