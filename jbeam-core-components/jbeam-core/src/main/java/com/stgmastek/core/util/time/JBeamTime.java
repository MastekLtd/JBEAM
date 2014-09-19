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

import com.stgmastek.core.util.BatchContext;

/**
 * Interface that defines common methods to fetch the time.
 *
 * The implementation classes should either make use of parameters supplied to fetch database time or simply 
 * ignore them to fetch the time.
 * 
 * @author Kedar Raybagkar
 * @since 3.3
 */
public interface JBeamTime {

	/**
	 * Returns the current Timestamp based on the implementation.
	 * 
	 * @param connection To be used in case a database time is needed.
	 * @return Timestamp
	 */
	public Timestamp getCurrentTimestamp(Connection connection);
	
	/**
	 * Returns the current Timestamp based on the implementation
	 * @param context The respective JDBC connection will be fetched from the context if database time is required. 
	 * @return Timestamp
	 */
	public Timestamp getCurrentTimestamp(BatchContext context);
	
}
