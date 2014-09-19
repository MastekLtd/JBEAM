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
package com.stgmastek.monitor.comm.util.messagehandler;

import java.sql.Connection;
import java.util.Map;

import com.stgmastek.core.comm.client.CReqInstructionLog;
import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.util.ClientBook;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;
import com.stgmastek.monitor.comm.util.IOutboundMessageProcessor;
import com.stgmastek.monitor.comm.util.MessageParamParser;
import com.stgmastek.monitor.comm.util.MonitorMessage;

/**
 * Special class for the "Run Report" functionality 
 */
public final class RunReport implements IOutboundMessageProcessor {

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.comm.util.IMessageProcessor#processMessage(com.stgmastek.monitor.comm.util.MonitorMessage)
	 */
	
	public Boolean processMessage(MonitorMessage message) throws Throwable {
		//Get the client stub  
		CoreCommServices bServices = ClientBook.getBook().getClientClass(message.getInstallationCode());
		
		//Get the parameters 
		Map<String, String> map = MessageParamParser.parse(message.getParam());
		
		//Call the appropriate DAO method
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			CReqInstructionLog reqInstructionLog = dao.getInstructionLog(Integer.valueOf(map.get("seqNo")),connection);
		
		//Call the appropriate service 
		bServices.addInstructionLog(reqInstructionLog);
		}finally{
			dao.releaseResources(null, null, connection);
		}
		return true;
	}

}
