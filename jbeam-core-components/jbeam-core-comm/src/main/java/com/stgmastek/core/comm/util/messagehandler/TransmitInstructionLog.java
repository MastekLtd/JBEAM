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
package com.stgmastek.core.comm.util.messagehandler;

import java.sql.Connection;
import java.util.Map;

import com.stgmastek.core.comm.server.dao.IBatchDAO;
import com.stgmastek.core.comm.util.ClientBook;
import com.stgmastek.core.comm.util.CommConstants;
import com.stgmastek.core.comm.util.ConnectionManager;
import com.stgmastek.core.comm.util.CoreMessage;
import com.stgmastek.core.comm.util.DAOFactory;
import com.stgmastek.core.comm.util.IOutboundMessageProcessor;
import com.stgmastek.core.comm.util.MessageParamParser;
import com.stgmastek.monitor.comm.client.MReqInstructionLog;
import com.stgmastek.monitor.comm.client.MonitorCommServices;

/**
 * Special class to transmit the instruction log related information to the monitor  
 * 
 * @author Mandar Vaidya
 */
public class TransmitInstructionLog implements IOutboundMessageProcessor{

	
	public Boolean processMessage(CoreMessage message) throws Throwable {
		
		//Get the service handle 
		MonitorCommServices services = ClientBook.getBook().getClientClass("MONITOR_WS");		
		
		//Create the map from the message
		Map<String, String> map = MessageParamParser.parse(message.getParam());				
		Integer seqNo = Integer.valueOf(map.get("seqNo"));
		
		//Create BatchDAO object
		IBatchDAO batchdao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();

			MReqInstructionLog instructionLog = batchdao.getInstructionLog(
					seqNo, connection);
			instructionLog.setInstallationCode(CommConstants.INSTALLATION_CODE);

			//Call the web service
			services.updateInstructionLog(instructionLog);
		}finally {
			batchdao.releaseResources(null, null, connection);
		}
		 
		return true;		
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/messagehandler/TransmitBatchDetails.java                                                   $
*
*/