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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/scheduler/ICalendar.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/scheduler/ICalendar.java $
 * 
 * 2     2/04/09 1:53p Kedarr
 * Added static keyword to a final variable.
 * 
 * 1     6/15/08 10:45p Kedarr
 * Defines the Calendar. This class is used to determine if the given date
 * is a work day or is a non-work day. A work day is a day on which the
 * JOB can be scheduled and executed.
 * 
 */
package stg.pr.engine.scheduler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.PREContext;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.utils.Day;

/**
 * Defines a Calendar.
 * 
 * A Calendar is primarily used for checking whether or not the given day is a valid day for scheduling a job.
 *
 * @author Kedar Raybagkar
 * @version $Revision: 31105 $
 * @since V1.0P23.02.x  
 */
public interface ICalendar {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 31105             $";

    /**
     * Sets the java.sql.Connection object.
     * 
     * It is important that the class should not do any roll back or commit on this connection object.
     * Nor this class should close the connection object.
     * The method should throw an Exception and the scheduler will do the roll back of the transactions.
     * 
     * @param connection
     */
    public void setConnection(Connection connection);
    
    /**
     * Sets the data source factory through which, the connections can be obtained.
     *  
     * @param factory
     */
    public void setDataSourceFactory(IDataSourceFactory factory);
    
    
    /**
     * Returns whether the given Day can be used for scheduling a job.
     * 
     * True indicates that the JOB can be scheduled on this particular day. 
     * False indicates that this particular day is a non work day.
     * 
     * @param day Day to be verified if this is a holiday.
     * @return boolean
     * @throws SQLException
     */
    public boolean isWorkDay(Day day) throws SQLException;
    
    /**
     * Sets the Process Request Entity Bean.
     * 
     * @param pobjPREB ProcessRequestEntityBean
     * @since V1.0R28.x
     */
    public void setProcessRequestEntityBean(ProcessRequestEntityBean pobjPREB);

    /**
     * Sets the Parameters associated with the Request.
     * 
     * @param phmParameters HashMap
     * @since V1.0R28.x
     */
    public void setRequestParameters(Map<String, Object> phmParameters);
    
    /**
     * PRE will set the PREContext object.
     * 
     * @param context PREContext
     * @since V1.0R28.x
     */
    public void setPREContext(PREContext context);
    
    /**
     * Sets the schedule in a form of EntityBean.
     * One can make use of the set and get methods to access all the
     * attributes the schedule that is being processed.
     * 
     * @param bean ProcessRequestScheduleEntityBean
     * @since V1.0R28.x
     */
    public void setScheduleBean(ProcessRequestScheduleEntityBean bean);


}
