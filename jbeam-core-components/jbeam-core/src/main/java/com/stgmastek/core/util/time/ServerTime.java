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
package com.stgmastek.core.util.time;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import com.stgmastek.core.util.BatchContext;

/**
 * Fetches the date time from the server.
 *
 * This is as good as doing <code>new Date()</code>.
 * The parameters to the methods are simply ignored.
 *
 * @author Kedar Raybagkar
 * @since 3.3
 */
public class ServerTime implements JBeamTime {

	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.time.JBeamTime#getCurrentTimestamp(java.sql.Connection)
	 */
	
	public Timestamp getCurrentTimestamp(Connection connection) {
		return new Timestamp((new Date()).getTime());
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.time.JBeamTime#getCurrentTimestamp(com.stgmastek.core.util.BatchContext)
	 */
	
	public Timestamp getCurrentTimestamp(BatchContext context) {
		return new Timestamp((new Date()).getTime());
	}
}
