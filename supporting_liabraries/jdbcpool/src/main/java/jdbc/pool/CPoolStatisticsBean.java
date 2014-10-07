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
 * $Revision: 11 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CPoolStatisticsBean.java 11    3/17/08 2:46p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CPoolStatisticsBean.java $
 * 
 * 11    3/17/08 2:46p Kedarr
 * Added REVISION number
 * 
 * 10    3/17/08 1:10p Kedarr
 * Updated java doc. Added new statistical data collections. Changed the
 * public methods to protected methods.
 * 
 * 9     3/22/07 10:44a Kedarr
 * Changes made for printing statistical data.
 * 
 * 8     3/20/07 10:18a Kedarr
 * New API added for various parameters.
 * 
 * 7     3/14/07 10:53a Kedarr
 * Changes made to capture the leaked ResultSet count
 * 
 * 6     3/05/06 4:23p Kedarr
 * Added a new variables for enhanced statistical data collection.
 * 
 * 5     1/12/06 10:36a Kedarr
 * Added runtime memory printing in the toString() method only.
 * 
 * 4     11/11/05 5:53p Kedarr
 * Added JavaDoc to the class.
 * 
 * 3     8/29/05 1:47p Kedarr
 * Removed unwanted methods that were reflecting the attributes rather
 * than the statistics of the pool.
 * 
 * 2     5/20/05 11:32a Kedarr
 * Added javadoc
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
* Revision 1.1  2006/03/01 16:06:46  kedar
* JDBC POOL classes
*
* 
*/
package jdbc.pool;

/**
 * Pool Statistics Bean.
 * 
 * Stores various statistical data collected by the pool against a timestamp.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 11 $
 *
 */
public class CPoolStatisticsBean
{

    /**
     * Revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    public static final String REVISION = "$Revision: 11 $";

    /**
     * Count of Bad Connections encountered during the life of the pool is stored in this variable.
     * Comment for <code>lBadConnectionCount_</code>.
     */
    private long lBadConnectionCount_;

    /**
     * Count of Leaked Connections encountered during the life of the pool is stored in this variable.
     * Comment for <code>lLeakedConnectionCount_</code>.
     */
    private long lLeakedConnectionCount_;

    /**
     * Current free connections available in the pool is stored in this class.
     * Comment for <code>lCurrentFreeConnectionCount_</code>.
     */
    private long lCurrentFreeConnectionCount_;

    /**
     * Count of concurrent usage of the JDBC Connections that was reached during the life of the pool.
     * Comment for <code>lConnectionsHighCount_</code>.
     */
    private long lConnectionsHighCount_;

    /**
     * Count of current JDBC Connections in use is stored in this variable.
     * Comment for <code>lCurrentUsedConnectionCount_</code>.
     */
    private long lCurrentUsedConnectionCount_;

    /**
     * Stores the pool name.
     * Comment for <code>strPoolName_</code>.
     */
    private String strPoolName_;
    
    /**
     * Count of Leaked Statements encountered during the life of the pool is stored in this variable.
     * Comment for <code>lLeakedStatementCount_</code>.
     */
    private long lLeakedStatementCount_;

    /**
     * Count of Leaked ResultSet encountered during the life of the pool is stored in this variable.
     * Comment for <code>lLeakedResultSetCount_</code>.
     */
    private long lLeakedResultSetCount_;
    
    /**
     * Number of connections closed by the pool.
     * Comment for <code>noOfConnectionsClosed</code>.
     */
    private int noOfConnectionsClosed;
    
    /**
     * Number of connections created by the pool.
     * Comment for <code>noOfConnectionsCreated</code>.
     */
    private int noOfConnectionsCreated; 
    
    /**
     * Number of connections locked or lend by the pool.
     * Comment for <code>noOfConnectionsLocked</code>.
     */
    private int noOfConnectionsLocked;
    
    /**
     * Number of Connections un-locked or returned to the pool.
     * Comment for <code>inoOfConnectionsUnlocked</code>.
     */
    private int noOfConnectionsUnlocked;
    
    /**
     * Number of Requests made to get the connections.
     * This may not be equal to connections locked as the pool may throw an
     * exception if it is unable to service the request.
     * Comment for <code>noOfConnectionsRequested</code>.
     */
    private int noOfConnectionsRequested;
    
    /**
     * Number of Requests made to create new Connections.
     * The number of new connections created will depend on capacity-increament or
     * initial-pool-size. 
     * Comment for <code>noOfNewConnectionsRequested</code>.
     */
    private int noOfNewConnectionsRequested;
    
    
    /**
     * Highest number of application requests concurrently waiting for a connection from 
     * this instance of the connection pool.
     * Comment for <code>lNoOfWaitersHigh_</code>.
     */
    private long lNoOfWaitersHigh_;
    
    /**
     * Current number of application requests waiting for a connection.
     * Comment for <code>lNoOfCurrentWaiters_</code>.
     */
    private long lNoOfCurrentWaiters_;
    
    /**
     * Wait time high in mili seconds.
     * Comment for <code>lWaitTimeInMiliSeconds_</code>.
     */
    private long lWaitTimeHighInMiliSeconds_;
    
    /**
     * Time stamp of the data.
     * Comment for <code>timeStamp</code>.
     */
    private long timeStamp;

    /**
     * Start time when the pool was initialized.
     * Comment for <code>lPoolStartTimeInMillis_</code>.
     */
    private long lPoolStartTimeInMillis_;

    /**
     * Total wait time.
     * Comment for <code>lWaitTimeTotalInMilliSeconds_</code>.
     */
    private long lWaitTimeTotalInMilliSeconds_;

    /**
     * Average connection creation delay.
     * Comment for <code>lAverageConnectionDelayTimeInMillis_</code>.
     */
    private long lAverageConnectionDelayTimeInMillis_;

    /**
     * Total number of connections created from when the pool was instantiated.
     * Comment for <code>lTotalNoOfConnectionsCreated_</code>.
     */
    private long lTotalNoOfConnectionsCreated_;

    /**
     * Prints the statistical attributes only if self check thread is enabled.
     * Comment for <code>bPrintStatisticalAttributes_</code>.
     */
    private boolean bPrintStatisticalAttributes_ = false;

    /**
     * Number of unavailable connections.
     * Comment for <code>lNoOfUnavailableConnections_</code>.
     */
    private long lNoOfUnavailableConnections_;
    
    /**
     * High time when the connections were unavailable.
     * Comment for <code>lUnavailableConnectionsHighTime_</code>.
     */
    private long lUnavailableConnectionsHighTime_;

    /**
     * Stores the high count of unavailable connections.
     * Comment for <code>lNoOfUnavailableConnectionsHigh_</code>.
     */
    private long lNoOfUnavailableConnectionsHigh_;


    /**
     * Default Constructor.
     */
    protected CPoolStatisticsBean()
    {
        super();
        timeStamp = System.currentTimeMillis();
    }

    /**
     * Bad Connections are connections which die without actualy
     * closing them or which are killed from outside.
     * 
     * @return long Bad Connection Count
     */
    public long getBadConnectionCount()
    {
        return lBadConnectionCount_;
    }


    /**
     * Concurrent Connections in use during the life cycle of this pool.
     * If Max pool capacity is kept as 100 but connection high is only 40
     * means that 60 are un-wanted connections.
     * 
     * @return long Connection High Count.
     */
    public long getConnectionsHighCount()
    {
        return lConnectionsHighCount_;
    }

    /**
     * Free connections available in this pool.
     * This may vary from the maximum capacity of the pool as the
     * connections might be closed and the pool will be shrinked.
     * The connections will be created if the total connection
     * available count is less than the maximum capacity of the pool. 
     * 
     * @return long available connections in this pool.
     */
    public long getCurrentFreeConnectionCount()
    {
        return lCurrentFreeConnectionCount_;
    }

    /**
     * Current used connections from this pool.
     * 
     * @return long used connection count.
     */
    public long getCurrentUsedConnectionCount()
    {
        return lCurrentUsedConnectionCount_;
    }

    /**
     * A Connection leak occurs when a connection obtained from the pool was not closed explicitly by
     * calling close() and then was disposed by the garbage collector and returned to the connection pool.
     * 
     * @return long leaked connection count.
     */
    public long getLeakedConnectionCount()
    {
        return lLeakedConnectionCount_;
    }

    /**
     * Pool Identifier.
     * 
     * @return String pool name
     */
    public String getPoolName()
    {
        return strPoolName_;
    }

    /**
     * The pool sets the Bad Connection Count set by the pool manager.
     * 
     * @param l bad connection count
     */
    protected void setBadConnectionCount(long l)
    {
        lBadConnectionCount_ = l;
    }

    /**
     * The pool sets the Connections High count.
     * 
     * @param l
     */
    protected void setConnectionsHighCount(long l)
    {
        lConnectionsHighCount_ = l;
    }

    /**
     * The pool sets teh current free connections count.
     * 
     * @param l
     */
    protected void setCurrentFreeConnectionCount(long l)
    {
        lCurrentFreeConnectionCount_ = l;
    }

    /**
     * The pool sets the current used connections count.
     * 
     * @param l
     */
    protected void setCurrentUsedConnectionCount(long l)
    {
        lCurrentUsedConnectionCount_ = l;
    }


    /**
     * The pool sets the connection leakage count.
     * @param l
     */
    protected void setLeakedConnectionCount(long l)
    {
        lLeakedConnectionCount_ = l;
    }

    /**
     * The pool sets the pool name as set by the pool manager.
     * @param string
     */
    protected void setPoolName(String string)
    {
        strPoolName_ = string;
    }

    /**
     * @return Returns the lLeakedStatementCount_.
     */
    public long getLeakedStatementCount()
    {
        return lLeakedStatementCount_;
    }
    /**
     * @param leakedStatementCount_ The lLeakedStatementCount_ to set.
     */
    protected void setLeakedStatementCount(long leakedStatementCount_)
    {
        lLeakedStatementCount_ = leakedStatementCount_;
    }

    /**
     * @return Returns the lLeakedResultSetCount_.
     */
    public long getLeakedResultSetCount()
    {
        return lLeakedResultSetCount_;
    }
    /**
     * @param leakedResultSetCount_ The lLeakedResultSetCount_ to set.
     */
    protected void setLeakedResultSetCount(long leakedResultSetCount_)
    {
        lLeakedResultSetCount_ = leakedResultSetCount_;
    }
    
    /**
     * Returns Number of connections closed by the pool.
     *
     * @return Returns the noOfConnectionsClosed.
     */
    public int getNoOfConnectionsClosed() {
        return noOfConnectionsClosed;
    }

    /**
     * Set the number of connections closed by the pool.
     *
     * @param noOfConnectionsClosed The noOfConnectionsClosed to set.
     */
    protected void setNoOfConnectionsClosed(int noOfConnectionsClosed) {
        this.noOfConnectionsClosed = noOfConnectionsClosed;
    }

    /**
     * Returns the number of connections created by the pool.
     *
     * @return Returns the noOfConnectionsCreated.
     */
    public int getNoOfConnectionsCreated() {
        return noOfConnectionsCreated;
    }

    /**
     * Set the number of connections created by the pool.
     *
     * @param noOfConnectionsCreated The noOfConnectionsCreated to set.
     */
    protected void setNoOfConnectionsCreated(int noOfConnectionsCreated) {
        this.noOfConnectionsCreated = noOfConnectionsCreated;
    }

    /**
     * Returns the number of connections locked or lend by the pool.
     *
     * @return Returns the noOfConnectionsLocked.
     */
    public int getNoOfConnectionsLocked() {
        return noOfConnectionsLocked;
    }

    /**
     * Set the number of connections locked or lend by the pool.
     *
     * @param noOfConnectionsLocked The noOfConnectionsLocked to set.
     */
    protected void setNoOfConnectionsLocked(int noOfConnectionsLocked) {
        this.noOfConnectionsLocked = noOfConnectionsLocked;
    }

    /**
     * Returns the number of Requests made to get the connections.
     * 
     * This may not be equal to connections locked as the pool may throw an
     * exception if it is unable to service the request.
     *
     * @return Returns the noOfConnectionsRequested.
     */
    public int getNoOfConnectionsRequested() {
        return noOfConnectionsRequested;
    }

    /**
     * Set the number of Requests made to get the connections.
     *
     * @param noOfConnectionsRequested The noOfConnectionsRequested to set.
     */
    protected void setNoOfConnectionsRequested(int noOfConnectionsRequested) {
        this.noOfConnectionsRequested = noOfConnectionsRequested;
    }

    /**
     * Returns the number of Connections un-locked or returned to the pool.
     *
     * @return Returns the noOfConnectionsUnlocked.
     */
    public int getNoOfConnectionsUnlocked() {
        return noOfConnectionsUnlocked;
    }

    /**
     * Set the number of Connections un-locked or returned to the pool.
     *
     * @param noOfConnectionsUnlocked The noOfConnectionsUnlocked to set.
     */
    protected void setNoOfConnectionsUnlocked(int noOfConnectionsUnlocked) {
        this.noOfConnectionsUnlocked = noOfConnectionsUnlocked;
    }

    /**
     * Returns the number of Requests made to create new Connections.
     * 
     * The number of new connections created will depend on capacity-increament or
     * initial-pool-size. 

     * @return Returns the noOfNewConnectionsRequested.
     */
    public int getNoOfNewConnectionsRequested() {
        return noOfNewConnectionsRequested;
    }

    /**
     * Sets the number of Requests made to create new Connections.
     *
     * @param noOfNewConnectionsRequested The noOfNewConnectionsRequested to set.
     */
    protected void setNoOfNewConnectionsRequested(int noOfNewConnectionsRequested) {
        this.noOfNewConnectionsRequested = noOfNewConnectionsRequested;
    }

    /**
     * Returns the timestamp when the data was collected.
     *
     * @return Returns the timeStamp.
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        long lFreeMemory = Runtime.getRuntime().freeMemory();
        long lTotalMemory = Runtime.getRuntime().totalMemory();
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append("Pool Started On,");
        sbuf.append(lPoolStartTimeInMillis_);
        sbuf.append(", Connections High,");
        sbuf.append(lConnectionsHighCount_);
        sbuf.append(", Leaked ResultSet,");
        sbuf.append(lLeakedResultSetCount_);
        sbuf.append(", Leaked Statements,");
        sbuf.append(lLeakedStatementCount_);
        sbuf.append(", Leaked Connections,");
        sbuf.append(lLeakedConnectionCount_);
        sbuf.append(", Current Used Connections,");
        sbuf.append(lCurrentUsedConnectionCount_);
        sbuf.append(", Current Free Connections,");
        sbuf.append(lCurrentFreeConnectionCount_);
        sbuf.append(", Bad Connections Found,");
        sbuf.append(lBadConnectionCount_);
        sbuf.append(", Current Waiters,");
        sbuf.append(lNoOfCurrentWaiters_);
        sbuf.append(", Waiters High,");
        sbuf.append(lNoOfWaitersHigh_);
        sbuf.append(", Wait Time High,");
        sbuf.append(lWaitTimeHighInMiliSeconds_);
        sbuf.append(", Total Wait Time,");
        sbuf.append(lWaitTimeTotalInMilliSeconds_);
        sbuf.append(", Average Connection Delay,");
        sbuf.append(lAverageConnectionDelayTimeInMillis_);
        sbuf.append(", Connections Total,");
        sbuf.append(lTotalNoOfConnectionsCreated_);
        sbuf.append(", Unavailable Connections,");
        sbuf.append(lNoOfUnavailableConnections_);
        sbuf.append(", Unavailable Connections High,");
        sbuf.append(lNoOfUnavailableConnectionsHigh_);
        sbuf.append(", Unavailable Connections High Time,");
        sbuf.append(lUnavailableConnectionsHighTime_);
        sbuf.append(", Used Memory,");
        sbuf.append(lTotalMemory - lFreeMemory);
        sbuf.append(", Total Memory,");
        sbuf.append(lTotalMemory);
        sbuf.append(", HeapMemoryUsed,");
        sbuf.append(lHeapMemoryUsage);
        sbuf.append(", HeapMemoryMax,");
        sbuf.append(lHeapMemoryUsageMax);
        sbuf.append(", NonHeapMemoryUsage,");
        sbuf.append(lNonHeapMemoryUsage);
        sbuf.append(", NonHeapMemoryMax,");
        sbuf.append(lNonHeapMemoryUsageMax);
        if (bPrintStatisticalAttributes_) {
            sbuf.append(",[ST]Connections Requested,");
            sbuf.append(noOfConnectionsRequested);
            sbuf.append(",[ST]New Connections Requested,");
            sbuf.append(noOfNewConnectionsRequested);
            sbuf.append(",[ST]Connections Created,");
            sbuf.append(noOfConnectionsCreated);
            sbuf.append(",[ST]Connections Locked,");
            sbuf.append(noOfConnectionsLocked);
            sbuf.append(",[ST]Connections UnLocked,");
            sbuf.append(noOfConnectionsUnlocked);
            sbuf.append(",[ST]Connections Closed,");
            sbuf.append(noOfConnectionsClosed);
        }
        
        return sbuf.toString();
    }
    
    /**
     * Set the highest number of application requests concurrently waiting for a connection from 
     * this instance of the connection pool.
     *
     * @param noOfWaitersHigh
     */
    protected void setWaitersHighCount(long noOfWaitersHigh) {
        lNoOfWaitersHigh_ = noOfWaitersHigh;
    }
    
    /**
     * Returns the highest number of application requests concurrently waiting for a connection from 
     * this instance of the connection pool.
     *
     * @return waiters high count
     */
    public long getWaitersHighCount() {
        return lNoOfWaitersHigh_;
    }
    
    /**
     * Set the current number of application requests waiting for a connection.
     *
     * @param noOfCurrentWaiters
     */
    protected void setCurrentWaitersCount(long noOfCurrentWaiters) {
        lNoOfCurrentWaiters_ = noOfCurrentWaiters;
    }
    
    /**
     * Returns the current number of application requests waiting for a connection.
     *
     * @return current waiter count.
     */
    public long getCurrentWaitersCount() {
        return lNoOfCurrentWaiters_;
    }
    
    /**
     * Sets the wait time high in milliseconds.
     *
     * Wait time is highest number of milliseconds that an application waited for a connection from this 
     * instance of the connection pool since the connection pool was instantiated.
     *
     * @param timeInMiliSeconds
     */
    protected void setWaitTimeHighInMillis(long timeInMiliSeconds) {
        lWaitTimeHighInMiliSeconds_ = timeInMiliSeconds;
    }
    
    /**
     * Returns the wait time high in milliseconds. 
     *
     * Wait time is highest number of seconds that an application waited for a connection from this 
     * instance of the connection pool since the connection pool was instantiated.
     *
     * @return time in milliseconds
     */
    public long getWaitTimeHighInMillis() {
        return lWaitTimeHighInMiliSeconds_;
    }
    
    /**
     * Sets the pool start time in milliseconds.
     *
     * Time when the pool was initialized and is ready for service.
     *
     * @param poolStartTime
     */
    protected void setPoolStartTimeInMillis(long poolStartTime) {
        lPoolStartTimeInMillis_ = poolStartTime;
    }
    
    /**
     * Returns the pool start time in milliseconds.
     *
     * Time when the pool was initialized and is ready for service.
     *
     * @return time in milliseconds.
     */
    public long getPoolStartTimeInMillis() {
        return lPoolStartTimeInMillis_;
    }
    
    /**
     * Set the wait time total in milliseconds.
     *
     * Total time lost by the application in waiting for a connection from this 
     * instance of the connection pool since the connection pool was instantiated.
     *
     * @param waitTimeTotal
     */
    protected void setWaitTimeTotalInMillis(long waitTimeTotal) {
        lWaitTimeTotalInMilliSeconds_ = waitTimeTotal;
    }
    
    /**
     * Returns the total time lost by the application in waiting for a connection from this 
     * instance of the connection pool since the connection pool was instantiated.
     *
     * This attribute can help in determining the productivity lost due to waiting on the JDBC
     * Connection.
     * 
     * @return time in milliseconds
     */
    public long getWaitTimeTotalInMillis() {
        return lWaitTimeTotalInMilliSeconds_;
    }
    
    /**
     * Set the connection delay time.
     * 
     * Connection delay time is the average time in milliseconds it takes to create a 
     * physical database connection. The time is calculated as the sum of the time it 
     * takes to create all physical database connections in the connection pool divided 
     * by the total number of connections created.
     *
     * @param timeInMilliseconds
     */
    protected void setAverageConnectionDelayTimeInMillis(long timeInMilliseconds) {
        lAverageConnectionDelayTimeInMillis_ = timeInMilliseconds;
    }
    
    /**
     * Returns the connection delay time.
     * 
     * Connection delay time is the average time in milliseconds it takes to create a 
     * physical database connection. The time is calculated as the sum of the time it 
     * takes to create all physical database connections in the connection pool divided 
     * by the total number of connections created.
     *
     * @return time in milliseconds
     */
    public long getAverageConnectionDelayTimeInMillis() {
        return lAverageConnectionDelayTimeInMillis_;
    }

    /**
     * Sets the connections total.
     *
     * Total number of database connections created in this instance of the connection pool 
     * since the connection pool was instantiated. This count may be more than the maximum
     * capacity of the pool as the pool may get shrinked at times.
     * 
     * @param totalNoOfConnectionsCreated 
     */
    protected void setTotalConnectionsCreated(long totalNoOfConnectionsCreated) {
        lTotalNoOfConnectionsCreated_ = totalNoOfConnectionsCreated;
    }
    
    /**
     * Returns the connections Total.
     *
     * Connections Total is total number of database connections created in this instance of the 
     * connection pool since the connection pool was instantiated. This count may be more than 
     * the maximum capacity of the pool as the pool may get shrinked at times.
     *
     * @return Total number of connections created.
     */
    public long getTotalConnectionsCreated() {
        return lTotalNoOfConnectionsCreated_;
    }
    
    /**
     * Prints the statisical data collected identified as <b>[ST]</b>.
     * 
     * This is enabled only if the shrink pool interval is greater than ZERO.
     *
     * @param bvalue
     */
    protected void setPrintStatisticalAttributes(boolean bvalue) {
        this.bPrintStatisticalAttributes_ = bvalue;
    }
    
    /**
     * Set the current number of connections that are currently unavailable to applications 
     * because the connection is being tested or refreshed.
     *
     * @param plNoOfUnavailableConnections
     */
    protected void setNoOfUnavailableConnections(long plNoOfUnavailableConnections) {
        this.lNoOfUnavailableConnections_ = plNoOfUnavailableConnections;
    }
    
    /**
     * Returns current number of connections that are currently unavailable to applications 
     * because the connection is being tested or refreshed.
     *
     * @return number of unavailable connections.
     */
    public long getNoOfUnavailableConnections() {
        return lNoOfUnavailableConnections_;
    }
    
    /**
     * Set the high time where the connections were unavailable. 
     *
     * @param milliseconds
     * 
     * @see #getNoOfUnavailableConnections()
     */
    protected void setUnavailableConnectionsHighTime(long milliseconds) {
        this.lUnavailableConnectionsHighTime_ = milliseconds;
    }
    
    /**
     * Returns the high time where the connections were unavailable. 
     *
     * @return time in milliseconds.
     * 
     * @see #getNoOfUnavailableConnections()
     */
    public long getUnavailableConnectionsHighTime() {
        return lUnavailableConnectionsHighTime_;
    }
    
    /**
     * Set the highest number of connections that were unavailable to applications 
     * because the connections were being tested or refreshed.
     *
     * @param plNoOfUnavailableConnectionsHigh
     */
    protected void setNoOfUnavailableConnectionsHigh(long plNoOfUnavailableConnectionsHigh) {
        this.lNoOfUnavailableConnectionsHigh_ = plNoOfUnavailableConnectionsHigh;
    }
    
    /**
     * Returns the highest number of connections that were unavailable to applications 
     * because the connections were being tested or refreshed for the given instance of the pool.
     *
     * @return higest number of connections unavailable.
     */
    public long getNoOfUnavailableConnectionsHigh() {
        return lNoOfUnavailableConnectionsHigh_;
    }
    
    /**
	 * @param lHeapMemoryUsage the lHeapMemoryUsage to set
	 */
	protected void setHeapMemoryUsage(long lHeapMemoryUsage) {
		this.lHeapMemoryUsage = lHeapMemoryUsage;
	}

	/**
	 * @return the lHeapMemoryUsage
	 */
	public long getHeapMemoryUsage() {
		return lHeapMemoryUsage;
	}

	/**
	 * @param lNonHeapMemoryUsage the lNonHeapMemoryUsage to set
	 */
	protected void setNonHeapMemoryUsage(long lNonHeapMemoryUsage) {
		this.lNonHeapMemoryUsage = lNonHeapMemoryUsage;
	}

	/**
	 * @return the lNonHeapMemoryUsage
	 */
	public long getNonHeapMemoryUsage() {
		return lNonHeapMemoryUsage;
	}

	/**
	 * @param lHeapMemoryUsageMax the lHeapMemoryUsageMax to set
	 */
	public void setHeapMemoryUsageMax(long lHeapMemoryUsageMax) {
		this.lHeapMemoryUsageMax = lHeapMemoryUsageMax;
	}

	/**
	 * @return the lHeapMemoryUsageMax
	 */
	public long getHeapMemoryUsageMax() {
		return lHeapMemoryUsageMax;
	}

	/**
	 * @param lNonHeapMemoryUsageMax the lNonHeapMemoryUsageMax to set
	 */
	public void setNonHeapMemoryUsageMax(long lNonHeapMemoryUsageMax) {
		this.lNonHeapMemoryUsageMax = lNonHeapMemoryUsageMax;
	}

	/**
	 * @return the lNonHeapMemoryUsageMax
	 */
	public long getNonHeapMemoryUsageMax() {
		return lNonHeapMemoryUsageMax;
	}

	private long lHeapMemoryUsage;
	
    private long lNonHeapMemoryUsage;
    
    private long lHeapMemoryUsageMax;
    
    private long lNonHeapMemoryUsageMax;
    
}
