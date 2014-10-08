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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/CProcessStatusBean.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/beans/CProcessStatusBean.java $
 * 
 * 3     2/04/09 3:44p Kedarr
 * Added static keyword to a final variable.
 * 
 * 2     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 1     1/11/05 10:00a Kedarr
 *
 * Created on Dec 3, 2004
 *
 */
package stg.pr.beans;

/**
 * Records the status of the process.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision:: 2958                 $
 */
public class CProcessStatusBean
{
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2958              $";

    /**
     * Returns the Revision number of the class.
     * Identifies the version number of the source code that generated this class file stored
     * in the configuration management tool.
     * 
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }

    private String processName;
    
    private String status;
    
    private String threadStatus;
    
    private long startTime;
    
    private long requestId;
    
    /**
     * Returns the start time of the process.
     * @return start time of the process.
     */
    public long getStartTime()
    {
        return startTime;
    }
    /**
     * Sets the start time of the process.
     * @param startTime The time when the process was started.
     */
    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }
    
    /**
     * Returns the status of the process.
     * @return Returns the status.
     */
    public String getStatus()
    {
        return status;
    }
    
    /**
     * Sets the status of the process.
     * @param pstrStatus The status of the process.
     */
    public void setStatus(String pstrStatus)
    {
        this.status = pstrStatus;
    }
    
    /**
     * Returns the name of the process.
     * @return Returns the processName.
     */
    public String getProcessName()
    {
        return processName;
    }
    
    /**
     * Sets the name of the process.
     * @param processName The processName to set.
     */
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }
    
    
    /**
     * Returns the status of the running thread.
     * @return Returns the threadStatus.
     */
    public String getThreadStatus()
    {
        return threadStatus;
    }
    
    /**
     * Sets the status of the running thread.
     * @param threadStatus The threadStatus to set.
     */
    public void setThreadStatus(String threadStatus)
    {
        this.threadStatus = threadStatus;
    }
    
    /**
     * Returns the request id.
     * @return Returns the requestId.
     */
    public long getRequestId()
    {
        return requestId;
    }

    /**
     * Sets the request id.
     * @param requestId The requestId to set.
     */
    public void setRequestId(long requestId)
    {
        this.requestId = requestId;
    }
}
