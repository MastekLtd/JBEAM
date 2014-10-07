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
 * $Revision: 31105 $
 *
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/MyOwnCalendar.java 3     3/11/09 6:12p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/MyOwnCalendar.java $
 * 
 * 3     3/11/09 6:12p Kedarr
 * Added revision
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
import java.util.Map;

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
public class MyOwnCalendar implements ICalendar {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 31105             $";


    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#isWorkDay(stg.utils.Day)
     */
    public boolean isWorkDay(Day day) throws SQLException {
        // If it is a Friday then return false else return true.
        // All Fridays are non-work days.
    	day.advance(1);
    	if (day.getYear() == 2011) {
    		if (day.getMonth() == 5) {
    			if (day.getDay() == 20 || (day.getDay() >= 22 && day.getDay() <=23) || (day.getDay() >=26 && day.getDay() <=27) ) {
    				return false;
    			}
    		}
    	}
        return true;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ICalendar#setConnection(java.sql.Connection)
     */
    public void setConnection(Connection connection) {
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
     * @see stg.pr.engine.scheduler.ICalendar#setScheduleBean(stg.pr.beans.ProcessRequestScheduleEntityBean)
     */
    public void setScheduleBean(ProcessRequestScheduleEntityBean bean) {
        
    }

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ICalendar#setDataSourceFactory(stg.pr.engine.datasource.IDataSourceFactory)
	 */
	public void setDataSourceFactory(IDataSourceFactory factory) {
		
	}

    public void setRequestParameters(Map<String, Object> phmParameters) {
    }

}
