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

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.PREContext;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.scheduler.IScheduleEvent;

public class Scheduler implements IScheduleEvent {

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleEvent#performPostAction(long)
	 */
	
	public boolean performPostAction(long arg0) {
		return false;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleEvent#performPreAction(long, long)
	 */
	
	public boolean performPreAction(long arg0, long arg1) {
		return false;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleValidator#setConnection(java.sql.Connection)
	 */
	
	public void setConnection(Connection arg0) {

	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleValidator#setScheduleBean(stg.pr.beans.ProcessRequestScheduleEntityBean)
	 */
	
	public void setScheduleBean(ProcessRequestScheduleEntityBean arg0) {

	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleValidator#validateSchedule(long)
	 */
	
	public boolean validateSchedule(long arg0) {
		return false;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ISchedule#setPrintWriter(java.io.PrintWriter)
	 */
	
	public void setPrintWriter(PrintWriter arg0) {

	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ISchedule#setProcessRequestEntityBean(stg.pr.beans.ProcessRequestEntityBean)
	 */
	
	public void setProcessRequestEntityBean(ProcessRequestEntityBean arg0) {

	}

	
	public void setPREContext(PREContext arg0) {
		
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleValidator#setDataSourceFactory(stg.pr.engine.datasource.IDataSourceFactory)
	 */
	public void setDataSourceFactory(IDataSourceFactory dsFactory) {
		
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ISchedule#setRequestParameters(java.util.HashMap)
	 */
	public void setRequestParameters(HashMap<String, Object> phmParameters) {
		
	}

}
