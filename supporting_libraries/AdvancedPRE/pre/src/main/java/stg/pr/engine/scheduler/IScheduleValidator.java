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
* $Revision: 2958 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/scheduler/IScheduleValidator.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/scheduler/IScheduleValidator.java $
 * 
 * 5     2/04/09 3:16p Kedarr
 * Added static keyword to a final variable.
 * 
 * 4     7/11/05 7:08p Kedarr
 * Added a new method to set the ProcessRequestScheduleEntityBean to
 * assist validation as well as assists scheduling for
 * IPreDefinedFrequency.
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:01p Kedarr
* Revision 1.3  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.2  2003/10/23 09:07:31  kedar
* Changes made for scheduling and added a new Marker Interface
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 9/18/03    Time: 4:21p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/scheduler
 * Organising Imports
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:21p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/scheduler
 * Changes made for $Revision: 2958 $ in the javadoc.
*
*/

package stg.pr.engine.scheduler;

import java.sql.Connection;

import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.datasource.IDataSourceFactory;

/**
* Class to validate the Schedule.
*
* @version $Revision: 2958 $
* @author   Kedar C. Raybagkar
*/
public interface IScheduleValidator extends ISchedule
{
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2958              $";
    
    /**
    * Set the connection object
    *
    * @param pcon Connection
    */
    public void setConnection(Connection pcon);

    /**
     * Validates the schedule.
     * @param plSchId  long Schedule Id associated with the Request.
     * @return boolean True if the schedule is valid and false if not.
     */
    public boolean validateSchedule(long plSchId);
    
    
    /**
     * Sets the schedule in a form of EntityBean.
     * One can make use of the set and get methods to access all the
     * attributes the schedule that is being processed.
     * 
     * @param bean ProcessRequestScheduleEntityBean
     */
    public void setScheduleBean(ProcessRequestScheduleEntityBean bean);
    
    /**
     * Sets the data source factory from which, a JDBC connection object can be accessed.
     * 
     * The main JDBC connection to the schema where the PRE related tables are created is 
     * passed using the {@link #setConnection(Connection)} method. The data source thus 
     * set should be used to access different schema other than the one provided already.
     * 
     * @param dsFactory Data Source Factory
     */
    public void setDataSourceFactory(IDataSourceFactory dsFactory);
    
}