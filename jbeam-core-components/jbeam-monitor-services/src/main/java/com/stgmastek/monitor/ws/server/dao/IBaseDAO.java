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
package com.stgmastek.monitor.ws.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * DAO interface base interface for Monitor System.
 * All DAO interfaces must extend this interface.
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 1 $
 */

public interface IBaseDAO {
	public void releaseResources(ResultSet rs, PreparedStatement pstmt, Connection con);
}
/*
* Revision Log
* -------------------------------
* $Header: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/IBaseDAO.java 1     6/23/10 10:52a  $
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/IBaseDAO.java                                                                $
 * 
 * 1     6/23/10 10:52a Lakshmanp
 * initial version
*
*/