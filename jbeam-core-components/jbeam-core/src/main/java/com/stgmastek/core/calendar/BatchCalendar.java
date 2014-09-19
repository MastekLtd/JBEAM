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

package com.stgmastek.core.calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.stgmastek.core.aspects.DatabaseAgnosticCandidate;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.PREContext;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.scheduler.ICalendar;
import stg.utils.Day;

/**
 * The Calendar implementation that the batch will utilize to identify non-working days.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class BatchCalendar implements ICalendar{

	private Connection connection = null;
	
	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ICalendar#isWorkDay(stg.utils.Day)
	 */
	@DatabaseAgnosticCandidate
	public boolean isWorkDay(Day day) throws SQLException {
	    ResultSet rs = null;
	    PreparedStatement ps = null;
	    try {
            ps = connection.prepareStatement("SELECT 'X' FROM calendar_log WHERE upper(calendar_name)='BATCH CALENDAR' and non_working_date = ?");
            day.advance(1);
            ps.setTimestamp(1, day.getTimestamp());
            rs = ps.executeQuery();
            if (rs.next()) return false;    //as this is a non-working day.
            return true;            //otherwise return true.
        } finally {
            if (rs != null) {
            	try {
            		rs.close();
				} catch (SQLException e2) {
				}
            }
            if (ps != null) {
                try {
                	ps.close();
                } catch (SQLException e2) {
                }
            }
        }
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ICalendar#setConnection(java.sql.Connection)
	 */
	public void setConnection(Connection arg0) {
		connection = arg0;
	}

	public void setPREContext(PREContext arg0) {
		
	}

	public void setProcessRequestEntityBean(ProcessRequestEntityBean arg0) {
		
	}

	public void setRequestParameters(HashMap<String, Object> arg0) {
		
	}

	public void setScheduleBean(ProcessRequestScheduleEntityBean arg0) {
		
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ICalendar#setDataSourceFactory(stg.pr.engine.datasource.IDataSourceFactory)
	 */
	public void setDataSourceFactory(IDataSourceFactory arg0) {
		
	}

}

/*
* Revision Log
* -------------------------------
* $Log: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/calendar/BatchCalendar.java $
 * 
 * 6     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 5     4/27/10 8:47a Kedarr
 * Added upper clause to the query for calendar name.
 * 
 * 4     4/14/10 1:11p Kedarr
 * Changes made to advance the day by 1 as the batch always runs for a
 * future date.
 * 
 * 3     2/25/10 10:50a Grahesh
 * Added the header and footer comments
*
*/