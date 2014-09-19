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
import com.stgmastek.monitor.comm.client.MReqBatch;
import com.stgmastek.monitor.comm.client.MonitorCommServices;

/**
 * Special class to transmit to the monitor the batch related information 
 * 
 * @author grahesh.shanbhag
 */
public class TransmitBatchDetails implements IOutboundMessageProcessor{

	
	public Boolean processMessage(CoreMessage message) throws Throwable {
		
		//Get the service handle 
		MonitorCommServices services = ClientBook.getBook().getClientClass("MONITOR_WS");		
		
		//Create the map from the message
		Map<String, String> map = MessageParamParser.parse(message.getParam());				
		Integer batchNo = Integer.valueOf(map.get("batchNo"));
		Integer batchRevNo = Integer.valueOf(map.get("batchRevNo"));
		
		//Create BatchDAO object
		IBatchDAO batchdao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			//First select the batch details for both the batchNo and the batchRevNo
			MReqBatch batchData = batchdao.getBatchData(batchNo, batchRevNo,connection); 
			batchData.setInstallationCode(CommConstants.INSTALLATION_CODE);
			//Call the web service
			if(message.getMessage().equals("BSADDBATCH"))
				services.addBatch(batchData);
			else if(message.getMessage().equals("BSUPDBATCH"))
				services.updateBatch(batchData);
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
 * 4     6/21/10 11:38a Lakshmanp
 * added the code to get dao from DAOFactory and passed the connection parameter to dao methods
 * 
 * 3     12/18/09 4:00p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/