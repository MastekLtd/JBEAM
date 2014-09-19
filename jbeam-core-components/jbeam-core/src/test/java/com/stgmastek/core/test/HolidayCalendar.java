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
package com.stgmastek.core.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.PREContext;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.scheduler.ICalendar;
import stg.utils.Day;

public class HolidayCalendar implements ICalendar {

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ICalendar#isWorkDay(stg.utils.Day)
	 */
	
	public boolean isWorkDay(Day arg0) throws SQLException {
		return false;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ICalendar#setConnection(java.sql.Connection)
	 */
	
	public void setConnection(Connection arg0) {

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
