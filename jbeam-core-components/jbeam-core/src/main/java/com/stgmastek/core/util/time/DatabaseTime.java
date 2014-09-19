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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import stg.utils.CDate;

import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;

/**
 * Fetches the time from the database.
 *
 * This class internally relies on the PRE library class {@link CDate} to fetch the date from database.
 * If in any case an exception is thrown then the exception is logged and a server date time is passed on.
 * This class does not throw any exception.
 *
 * @author Kedar Raybagkar
 * @since 3.3
 */
public class DatabaseTime implements JBeamTime {

	/**
	 * Logger instance. 
	 */
	private static final Logger logger = Logger.getLogger(DatabaseTime.class);
	
	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.time.JBeamTime#getCurrentTimestamp(java.sql.Connection)
	 */
	
	public Timestamp getCurrentTimestamp(Connection connection) {
		if (connection == null) {
			throw new NullPointerException("Connection cannot be null");
		}
		try {
			return CDate.getCurrentSQLTimestamp(connection);
		} catch (IOException e) {
			logger.error("Unable to fetch time from database due to IOException #" + e.getMessage(), e );
		} catch (SQLException e) {
			logger.error("Unable to fetch time from database due to SQLException #" + e.getMessage(), e );
		}
		logger.warn("Fetching server time instead of database.");
		return new Timestamp(new Date().getTime());
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.time.JBeamTime#getCurrentTimestamp(com.stgmastek.core.util.BatchContext)
	 */
	
	public Timestamp getCurrentTimestamp(BatchContext context) {
		if (context == null) {
			throw new NullPointerException("BatchContext cannot be null");
		}
		Connection con = null;
		try {
			con = context.getApplicationConnection();
			return CDate.getCurrentSQLTimestamp(con);
		} catch (IOException e) {
			logger.error("Unable to fetch time from database due to IOException #" + e.getMessage(), e );
		} catch (BatchException e) {
			logger.error("Unable to fetch time from database due to BatchException #" + e.getMessage(), e );
		} catch (SQLException e) {
			logger.error("Unable to fetch time from database due to SQLException #" + e.getMessage(), e );
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		logger.warn("Fetching server time instead of database.");
		return new Timestamp(new Date().getTime());
	}

}
