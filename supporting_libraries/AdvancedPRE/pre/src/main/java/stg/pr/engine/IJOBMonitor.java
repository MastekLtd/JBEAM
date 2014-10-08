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
* $Revision: 3374 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/IJOBMonitor.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/IJOBMonitor.java $
 * 
 * 7     2/04/09 3:54p Kedarr
 * Added static keyword to a final variable.
 * 
 * 6     4/11/08 9:06a Kedarr
 * New API added to capture the time when the JOB object was initialized.
 * This is the time when the object's constructor was called.
 * 
 * 5     5/09/05 1:16p Kedarr
 * Renamed the class from IMonitorThread to IJobMonitor and also added a
 * revision variable.
 * 
 * 4     5/06/05 1:40p Kedarr
 * Added two new methods that will help the Process Monitor to escalate
 * warnings after regular intervals for the jobs that are alive even after
 * they cross the Max Stuck Thread Limit.
 * 
 * 2     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.3  2004/05/03 06:29:49  kedar
* Changes made for getting the intantiated object that implements IProcessRequest
* interface. These changes were made due to addition of ITerminateProcess
* interface to the existing set of Engine classes.
*
* Revision 1.2  2004/04/12 10:13:17  kedar
* Updated Javadoc
*
* Revision 1.1  2004/02/06 10:35:11  kedar
* New Interface for Monitoring Threads within ProcessRequestEngine
*
* 
*/
package stg.pr.engine;

import java.sql.Connection;

import stg.pr.beans.ProcessRequestEntityBean;

/**
 * Class that helps to monitor the Engine Process Thread.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 3374 $
 *
 */
public interface IJOBMonitor
{
    public enum JOB_STATUS {
        NORMAL(0), STUCK(1), CRITICAL(2), TOBETERMINATED(3);
        
        private int id;

        private JOB_STATUS(int i) {
            this.id = i;
        }
        
        public int getID() {
            return id;
        }
    }
    
    /**
     * Revision number.
     * Comment for <code>REVISION</code>
     */
    public static final String REVISION = "$Revision: 3374 $";
    
    /**
     * Time when the Thread object was constructed or initialized by the Engine.
     * 
     * @return time
     */
    public long getInitializedTime();
    
    /**
     * Start Time of the Thread execution.
     * It can return -1 if the thread is not yet started. 
     * @return time
     */
    public long getStartTime();
    
    /**
     * Connection object.
     * 
     * @return Connection
     */
    public Connection getConnection();
    
    /**
     * Stuck Thread Limit that is associated with the request.
     * 
     * @return int Time
     */
    public long getStuckThreadLimit();
    
    /**
     * Stuck Thread Max Limit that is associated with the request.
     * 
     * @return int Time
     */
    public long getStuckThreadMaxLimit();
    
    /**
     * Entity Bean for table process_request is returned.
     * @return ProcessRequestEntityBean
     */
    public ProcessRequestEntityBean getRequestBean();
    
    /**
     * User Id associated with the Request.
     * @return String
     */
    public String getUserId();
    
    /**
     * The PRE sets the flag to the executing thread if the thread is STUCK.
     * @param status JOB_STATUS
     */
    public void setJobStatus(JOB_STATUS status);

    /**
     * Returns the Stuck Thread flag associated with the thread.
     * @return {@link JOB_STATUS}
     */
    public JOB_STATUS getJobStatus();

    /**
     * Returns the instantiated object.
     * @return Object.
     */
    public IProcessRequest getIntantiatedObject();
    
    /**
     * Sets the JOB Monitor Check Initiation Time.
     * @param time Time when Process Monitor checked this job.  
     */
    public void setMonitorCheckTime(long time);
    
    /**
     * Returns the last time when the Process Monitor checked this job.
     * @return long Time.
     */
    public long getMonitorCheckTime();
}
