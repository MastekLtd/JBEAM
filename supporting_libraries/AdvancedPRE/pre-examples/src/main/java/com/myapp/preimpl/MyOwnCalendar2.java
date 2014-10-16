/**
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
 *
 * $Revision: 2959 $
 *
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/MyOwnCalendar2.java 1     3/24/09 10:05a Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/MyOwnCalendar2.java $
 * 
 * 1     3/24/09 10:05a Kedarr
 * 
 * 2     7/10/08 11:48a Kedarr
 * Updated javadoc.
 * 
 * 1     6/16/08 9:37a Kedarr
 * A sample class that demonstrates the Calendar interface.
 * 
 */
package com.myapp.preimpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.PREContext;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.scheduler.ICalendar;
import stg.utils.Day;

/**
 * A class that demonstrates the {@link ICalendar} functionality.
 *
 * This class considers every Friday as a Non-Working day.
 * 
 * @author STG
 * @since V1.0R24.00.x  
 */
public class MyOwnCalendar2 implements ICalendar {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2959              $";


    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#isWorkDay(stg.utils.Day)
     */
    public boolean isWorkDay(Day day) throws SQLException {
        // All Even days are working days
        if (day.getDay()%2 == 0) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#setConnection(java.sql.Connection)
     */
    public void setConnection(Connection connection) {
        // Do not need a connection object.
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#setPREContext(stg.pr.engine.PREContext)
     */
    public void setPREContext(PREContext context) {
        
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#setProcessRequestEntityBean(stg.pr.beans.ProcessRequestEntityBean)
     */
    public void setProcessRequestEntityBean(ProcessRequestEntityBean pobjPREB) {
        
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#setRequestParameters(java.util.HashMap)
     */
    public void setRequestParameters(HashMap<String, Object> phmParameters) {
        
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#setScheduleBean(stg.pr.beans.ProcessRequestScheduleEntityBean)
     */
    public void setScheduleBean(ProcessRequestScheduleEntityBean bean) {
        
    }

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ICalendar#setDataSourceFactory(stg.pr.engine.datasource.IDataSourceFactory)
	 */
	public void setDataSourceFactory(IDataSourceFactory factory) {
	}

}
