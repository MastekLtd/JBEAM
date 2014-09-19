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

import com.stgmastek.core.comm.client.CReqCalendarLog;
import com.stgmastek.core.comm.client.CalendarData;
import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.util.ClientBook;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;
import com.stgmastek.monitor.comm.util.IOutboundMessageProcessor;
import com.stgmastek.monitor.comm.util.MessageParamParser;
import com.stgmastek.monitor.comm.util.MonitorMessage;

/**
 * Special class for the "Run Batch" functionality 
 */
public final class AddCalendar implements IOutboundMessageProcessor {

	
	public Boolean processMessage(MonitorMessage message) throws Throwable {
		
		//Get the client stub  
		CoreCommServices bServices = ClientBook.getBook().getClientClass(message.getInstallationCode());
		
		//Get the parameters 
		Map<String, String> map = MessageParamParser.parse(message.getParam());
		
		//Call the appropriate DAO method 
		IBatchDAO dao = DAOFactory.getBatchDAO();
		CalendarData calendarData = new CalendarData();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			calendarData.setCalendarName(map.get("calendarName"));
			calendarData.setYear(map.get("year"));
			CReqCalendarLog reqCalendarLog = dao.getCalendarData(calendarData, message.getInstallationCode(),connection);
			reqCalendarLog.setCalendarData(calendarData);
			//Call the appropriate service 
			bServices.addCalendarLog(reqCalendarLog);
		}finally{
			dao.releaseResources(null, null, connection);
		}
		return true;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/messagehandler/AddCalendar.java                                                      $
 * 
 * 3     6/18/10 12:46p Lakshmanp
 * added connection parameter for dao methods and modified getting dao object from daofactory
 * 
 * 2     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 1     4/09/10 11:03a Mandar.vaidya
 * Initial Version of message handler for calendar.
 * 
 * 3     12/21/09 10:20a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/