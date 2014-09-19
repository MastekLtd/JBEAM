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
package com.stgmastek.monitor.ws.server.dao.impl;

import com.stgmastek.monitor.ws.server.dao.IStatusDAO;
import com.stgmastek.monitor.ws.util.BaseDAO;

/**
 * DAO class for all Status related I/O to the database 
 * 
 * @author mandar.vaidya
 * @since $Revision: 2 $ 
 */
public class StatusDAO extends BaseDAO implements IStatusDAO {

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/impl/StatusDAO.java                                                          $
 * 
 * 2     6/23/10 11:08a Lakshmanp
 * removed parameterized constructor and added connection parameter to all dao methods
 * 
 * 1     6/22/10 10:49a Lakshmanp
 * Initial Version
*
*
*/