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
 * $Revision: 36 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/ConnectionPool.java 36    9/28/09 10:28a Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/ConnectionPool.java $
 * 
 * 36    9/28/09 10:28a Kedarr
 * Caught specific exceptions instead of a generic exception block.
 * 
 * 35    9/03/09 8:02p Kedarr
 * Added a comment.
 * 
 * 34    1/29/09 9:54p Kedarr
 * The pool parameters can be invoked while the pool is active. This will
 * force shutdown and re-initialize the pool.
 * 
 * 33    1/26/09 5:32p Kedarr
 * Functionality to forecfully destroy an in-use pool implemented.
 * 
 * 32    6/03/08 1:54p Kedarr
 * Improved some logging messages.
 * 
 * 31    3/18/08 12:44p Kedarr
 * Changed the logging of Statistical messages as well as recording of
 * statistical data.
 * 
 * 30    3/17/08 2:55p Kedarr
 * Added new statistical data collection points that will help identify
 * the correct or optimum configuration for a pool.
 * 
 * 29    3/22/07 11:20a Kedarr
 * Changes made for printing statistical data.
 * 
 * 28    3/22/07 10:44a Kedarr
 * Changes made for printing statistical data.
 * 
 * 27    3/20/07 10:20a Kedarr
 * New statistics collection points added. Captures the "now" running
 * queries.
 * 
 * 26    3/14/07 10:53a Kedarr
 * Changes made to capture the leaked ResultSet count
 * 
 * 25    3/02/07 10:07a Kedarr
 * Added / modified some debug messages.
 * 
 * 24    5/02/06 4:39p Kedarr
 * Changes made for the new pool algorithms.
 * 
 * 23    3/05/06 4:24p Kedarr
 * Added a new variables for enhanced statistical data collection.
 * 
 * 22    2/27/06 1:45p Kedarr
 * Changes made in javadoc. Also, changed the method signature that was
 * protected synchronized to private.
 * 
 * 21    2/23/06 12:21p Kedarr
 * The earlier logic for closing the physical JDBC connection during the
 * shrinking process of the poo has been modifiedl. Now the pool will
 * close the physical JDBC connection available in pool, if idle, even if
 * the given out connection size + free connection size is greater than
 * the initial size of the pool. In earlier case, the shrinking process
 * just checked for the free connection size to be greater than the
 * initial pool size and then only used to close them. The Pool ensures
 * that the closing of the JDBC Connections will be in excess of initial
 * pool size.
 * 
 * 20    11/11/05 12:43p Kedarr
 * Changes made to declare the field leaked connections as volatile.
 * 
 * 19    11/09/05 4:03p Kedarr
 * Changes made in log messages (added #connection.toString()). Also,
 * changes done to incorporate the maximum usage counter per JDBC
 * Connection. The Pool will now close the JDBC Connection if the usage
 * exceeds the specified limit. This limit is not mandatory and the
 * configuration can have a negative -1 value to suppress this behaviour.
 * 
 * 18    9/05/05 10:28a Kedarr
 * Changes made for adding additional debug messages.
 * 
 * 17    9/02/05 10:19a Kedarr
 * Changes made in the logger category. Instead of JDBC.<pool name> it
 * would display JDBC.Pool[<pool name>].
 * 
 * 16    8/30/05 12:04p Kedarr
 * Changes made to decode the password that will now be in encrypted
 * format.
 * 
 * 15    8/29/05 1:45p Kedarr
 * Updated Java Doc. Added a new protected method to update the attributes
 * of the pool. Also, made respective changes for the modifications made
 * in the CPoolStatisticsBean.java.
 * 
 * 14    7/29/05 3:37p Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 13    7/25/05 12:21p Kedarr
 * Updated for JavaDoc invalid tag in method getVersion().
 * 
 * 12    7/20/05 11:08a Kedarr
 * Changed for Wait Time when All In Use.
 * 
 * 11    7/15/05 3:49p Kedarr
 * Removed all unused / unread variables and added a method to show the
 * verson number of the class.
 * 
 * 10    7/07/05 11:43a Kedarr
 * Changes made for not creating a pooled connection if the connection
 * inactivity time out interval is less than or equal to zero.
 * 
 * 9     6/30/05 11:07a Kedarr
 * Changes made in the pool such that if the connection in-activity time
 * out (idle timeout) is specified as <= 0 then the connections will not
 * be pooled. On release of the connection back to the pool the connection
 * will be closed. Also the shrinking / self checks will not be done on
 * such a pool.
 * 
 * 8     6/29/05 3:12p Kedarr
 * Updated a info message.
 * 
 * 7     6/15/05 11:04a Kedarr
 * Changes made to emptyPool() method. The pool will remain alive till all
 * the connections are in use. The self check mechanism will then attempt
 * to empty the pool. Also, the servicing of any requests to
 * getConnection() will be stopped, if the pool is in Destroy mode.
 * 
 * 6     6/07/05 3:05p Kedarr
 * Changed the level of few messages from Notice to Info
 * 
 * 5     6/06/05 6:16p Kedarr
 * Changes made to enable Log4J
 * 
 * 4     5/17/05 11:12a Kedarr
 * Updated javadoc and also changed the LogType for one message to Debug
 * from Info.
 * 
 * 3     5/16/05 11:55a Kedarr
 * Major Change.
 * 
 * Method createPool(...) is now made private.
 * 
 * 2     5/09/05 5:15p Kedarr
 * Forcefully emptying of pools is removed. Instead the pool will throw
 * SQLException if the pool is in use while emptying.
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
 * Revision 1.1  2006/03/01 16:06:46  kedar
 * JDBC POOL classes
 *
 * 
 * 9     4/12/04 4:25p Kedarr
 * Organized the Import statements.
 * Revision 1.11  2004/03/22 11:02:16  kedar
 * Organised import statements.
 *
 * 
 * 8     3/19/04 4:37p Kedarr
 * Changes made for the isClosed() method check while checking for
 * isClosed() in the release !!
 * Revision 1.10  2004/03/18 12:17:53  kedar
 * Added isClosed() check for ConnectionWrapper instance. If the connection
 * is closed() then no need to close again. Aslo message is logged if the 
 * connection is closed.
 *
 * 
 * 7     2/09/04 10:59a Kedarr
 * Changes made to CSettings.get(String, String) has been incorporated 
 *  in this class.
 * Revision 1.9  2004/02/06 10:40:51  kedar
 * Changes made to CSettings.get(String, String) has been incorporated 
 * in this class.
 *
 * 
 * 6     1/23/04 2:35p Kedarr
 * Drastic changes were made to change the vector to a hashmap and change
 * all other methods which referenced vector to use the hashmap.
 * Revision 1.8  2004/01/16 13:31:22  kedar
 * Added a bracket for the if condition in shrink pool
 *
 * Revision 1.7  2004/01/16 13:25:11  kedar
 * Changes made to use HashMap instead of a Vector as a jdbc pool.
 *
 * 
 * 5     1/12/04 12:37p Kedarr
 * Changes made for Locked Objects vector and added debug messages.
 * Revision 1.6  2004/01/08 10:52:22  kedar
 * Changes made for Locked Objects vector and added debug messages.
 *
 * 
 * 4     12/29/03 12:49p Kedarr
 * Changes made in the debug message for the Connection Pool in shrinking
 * method.
 * Revision 1.5  2003/12/09 18:01:02  kedar
 * Changes made in the debug message for the Connection Pool in 
 * shrinking method.
 *
 * 
 * 3     12/09/03 9:35p Kedarr
 * Removed UnUsed variables where ever possible and made
 * the necessary changes.
 * Revision 1.4  2003/12/09 16:02:08  kedar
 * Removed UnUsed variables where ever possible and made
 * the necessary changes.
 *
 * 
 * 2     12/01/03 1:32p Kedarr
 * Revision 1.3  2003/11/28 10:05:34  kedar
 * Changes made for ConnectionWrapper class.
 *
 * 
 * 1     11/03/03 12:00p Kedarr
 * Revision 1.2  2003/10/29 07:08:09  kedar
 * Changes made for changing the Header Information from all the files.
 * These files now do belong to System Task Group International Ltd.
 *
 * Revision 1.1  2003/10/23 06:58:41  kedar
 * Inital Version Same as VSS
 *
 * 
 * *****************  Version 7  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:32p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changes made for $Revision: 36 $ in the javadoc.
 * 
 * *****************  Version 6  *****************
 * User: Kedarr       Date: 20/06/03   Time: 3:23p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changed the Logging of Debug Messages...
 * 
 * *****************  Version 5  *****************
 * User: Kedarr       Date: 20/06/03   Time: 11:33a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changes for CLogger. Changed the way the messages were passed.
 * 
 * *****************  Version 4  *****************
 * User: Kedarr       Date: 12/06/03   Time: 6:24p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * changes made for making the pool object to null once it is removed from
 * the vector.
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 12/06/03   Time: 2:32p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changes made for shrinking pool after a specific interval of time.
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 21/02/03   Time: 11:47a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changes made for removing the System.out.println();
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/database
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:49p
 * Created in $/ProcessRequestEngine/gmac/database
 * Initial Version
 *
 */

package jdbc.pool;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import jdbc.tuning.ConnectionWrapper;

import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;

/**
 * JDBC Connection Pool.
 * 
 * This class is used to create the connection pool for the Connection objects
 * required to to all jdbc work. It is required to set the driver , url of the
 * database , username password , and size of the pool or the total number of
 * Connection objects required It uses one more class PooledConnection which is
 * a wrapper for the Connection object created in this class.
 * 
 * @version $Revision: 36 $
 * @author Kedar C. Raybagkar
 * 
 */
public final class ConnectionPool {

    /**
     * Stores the version number of the class.
     */
    public static final String REVISION = "$Revision: 36 $";

    /**
     * This variable stores the number of connections leaked and caught by the
     * Garbage Collector. Comment for <code>lLeakedConnections_</code>
     */
    private volatile long lLeakedConnections_ = 0;

    /**
     * This variable stores the number of con-current connections used (reached)
     * during the life cycle of this pool. Comment for
     * <code>lConnectionsHigh_</code>
     */
    private volatile long lConnectionsHigh_ = 0;

    /**
     * This variable store the number of connections that were abruptly
     * terminated or were killed. Comment for <code>lBadConnectionCount_</code>
     */
    private volatile long lBadConnectionCount_ = 0;

    /**
     * This variable will store the leaked statement count. Comment for
     * <code>lLeakedStatementsCount_</code>
     */
    private volatile long lLeakedStatementsCount_ = 0;

    /**
     * This variable will store the leaked ResultSet count. Comment for
     * <code>lLeakedResultSetCount_</code>.
     */
    private volatile long lLeakedResultSetCount_ = 0;

    // /**
    // * This variable stores the connections object created by the pool. These
    // * connections are free connections and are ready to be lend. Comment for
    // * <code>unlockedObjectsMap_</code>
    // */
    // private HashMap unlockedObjectsMap_ = null;

    // /**
    // * This variable stores the connections object lent by the pool. Comment
    // for
    // * <code>unlockedObjectsMap_</code>
    // */
    // private Vector lockedObjectsVector_ = null;

    /**
     * The shrinking and/or self check thread. Comment for
     * <code>threadShrink</code>
     */
    private Thread threadShrink = null;
    
    /**
     * Instance of the logger. Comment for <code>logger_</code>
     */
    private Logger logger_;

    /**
     * Attributes of the pool. Comment for <code>myAttributes</code>
     */
    private final CPoolAttribute myAttributes;

    /**
     * This variable stores true if the emptyPool method is attempted.
     * 
     * Comment for <code>bDestroyAttempted_</code>
     */
    private volatile boolean bDestroyAttempted_;

    /**
     * This variable stores the instance of the Pool Manager class. The
     * poolDestroyed method will be called upon after the pool destroys itself.
     * Comment for <code>poolManager_</code>
     */
    private CConnectionPoolManager poolManager_;

    /**
     * Stores the connection usage counter against each JDBC Connection. Comment
     * for <code>hmConnectionUsage_</code>.
     */
    private HashMap<String, Integer> hmConnectionUsage_;

    /**
     * Number of Requests made to get the connections. This may not be equal to
     * connections locked as the pool may throw an exception if it is unable to
     * service the request. Comment for <code>iNoOfConnectionsRequested_</code>.
     */
    private volatile int iNoOfConnectionsRequested_;

    /**
     * Number of connections created by the pool. Comment for
     * <code>iNoOfConnectionsCreated_</code>.
     */
    private volatile int iNoOfConnectionsCreated_;

    /**
     * Number of connections closed by the pool. Comment for
     * <code>iNoOfConnectionsClosed_</code>.
     */
    private volatile int iNoOfConnectionsClosed_;

    /**
     * Number of Connections un-locked or returned to the pool. Comment for
     * <code>iNoOfConnectionsUnlocked_</code>.
     */
    private volatile int iNoOfConnectionsUnlocked_;

    /**
     * Number of connections locked or lend by the pool. Comment for
     * <code>iNoOfConnectionsLocked_</code>.
     */
    private volatile int iNoOfConnectionsLocked_;

    /**
     * Number of Requests made to create new Connections. The number of new
     * connections created will depend on capacity-increament or
     * initial-pool-size. Comment for <code>iNoOfNewConnectionsRequested_</code>.
     */
    private volatile int iNoOfNewConnectionsRequested_;

    /**
     * Number of waiters high waiting for the connection. Comment for
     * <code>iNoOfWaitersHigh_</code>.
     */
    private int iNoOfWaitersHigh_;

    /**
     * Number of current waiters waiting for the connection. Comment for
     * <code>iNoOfCurrentWaiters_</code>.
     */
    private int iNoOfCurrentWaiters_;

    /**
     * Pool. Comment for <code>pool_</code>.
     */
    private IPool pool_;

    /**
     * Stores the Thread and CObjectWrapper. Comment for <code>QueryTime_</code>.
     */
    private volatile Hashtable<Thread, CObjectWrapper> QueryTime_;

    /**
     * Wait time high. Comment for <code>lWaitTimeHigh_</code>.
     */
    private long lWaitTimeHigh_;

    /**
     * Captures the pool start time. Time when the pool got initialized and
     * activated. Comment for <code>lPoolStartTimeInMillis_</code>.
     */
    private final static long lPoolStartTimeInMillis_ = System.currentTimeMillis();

    /**
     * Stores the cumulative wait time from the time when this pool was
     * instantiated. Comment for <code>lWaitTimeTotalInMillis_</code>.
     */
    private long lWaitTimeTotalInMillis_;

    /**
     * Total number of connections created from the time when this pool was
     * instantiated. Comment for <code>lTotalNoOfConnectionsCreated_</code>.
     */
    private long lTotalNoOfConnectionsCreated_;

    /**
     * Total connection creation delay time. Comment for
     * <code>lTotalConnectionDelayTime</code>.
     */
    private long lTotalConnectionDelayTime_;

    /**
     * ArrayList that stores the CPoolStatisticsBean Comment for
     * <code>alStatisticsHistory_</code>.
     */
    private ArrayList<CPoolStatisticsBean> alStatisticsHistory_;

    /**
     * Number of unavailable connections due to self-check or refresh actions
     * taken by pool. This variable must be used as max-capacity - (connections
     * in use + number of unavailable connections).
     *  
     * Comment for <code>lNoOfUnavailableConnections_</code>.
     */
    private volatile long lNoOfUnavailableConnections_;

    /**
     * Highest time spent during which JDBC Connections were unavailable.
     * Comment for <code>lUnavailableConnectionsHighTime_</code>.
     */
    private long lUnavailableConnectionsHighTime_;

    /**
     * Highest number of unavailable connections due to self-check or refresh actions
     * taken by pool. 
     * Comment for <code>lUnavailableConnectionsHigh_</code>.
     */
    private long lNoOfUnavailableConnectionsHigh_;
    
    /**
     * Determines if the pool was terminated.
     */
    private boolean bTerminated_;


    /**
     * This is the protected constructor for the connection pool class
     * 
     * @param attributes
     *            {@link CPoolAttribute}
     * @param manager
     *            {@link CConnectionPoolManager}
     */
    protected ConnectionPool(CPoolAttribute attributes,
            CConnectionPoolManager manager) {
        myAttributes = attributes;
        poolManager_ = manager;
        logger_ = Logger.getLogger("JDBC.Pool[" + myAttributes.getPoolName()
                + "]");
        if (myAttributes.getPoolAlgorithm().equals(IPool.LIFO_ALGORITHM)) {
            if (logger_.isInfoEnabled()) {
                logger_.info("Using Last-In-First-Out Algorithm");
            }
            pool_ = new CStackObjectPool();
        } else {
            if (logger_.isInfoEnabled()) {
                logger_.info("Using First-In-First-Out Algorithm");
            }
            pool_ = new CFIFOObjectPool();
        }
        QueryTime_ = new Hashtable<Thread, CObjectWrapper>();
    }

    /**
     * Changes the default attributes set while creating the object.
     * 
     * @param attribute
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     */
    protected synchronized void changeAttributes(CPoolAttribute attribute) throws SQLException, IOException, ClassNotFoundException {
        if (logger_.isInfoEnabled()) {
            logger_.info("Changing pool attributes..");
        }
        myAttributes.reset(attribute);
        if (logger_.isInfoEnabled()) {
            logger_.info("Pool attributes changed.");
        }
        emptyPool(true);
        initializePool();
    }

    /**
     * Creates Pooled Connections.
     * 
     * This method is responsible for creating the actual connection This method
     * uses java.sql.DriverManager interface and calls the getConnection()
     * method this method is a private method. and to be called from
     * initializePool method of this class
     * 
     * @param iNoOfConnections
     *            Number of connections to be created.
     * 
     * @throws IOException
     * @throws SQLException
     */
    private void createPooledConnection(long iNoOfConnections)
            throws IOException, SQLException {
        // if ((lockedObjectsVector_.size() + unlockedObjectsMap_.size() ==
        // myAttributes
        // .getMaximumCapacity())) {
        if ((pool_.getNumActive() + pool_.getNumIdle() == myAttributes
                .getMaximumCapacity())) {
            throw new SQLException(
                    "Unable to increament pool. Max Capacity reached.");
        }
        iNoOfNewConnectionsRequested_++;
        if (logger_.isDebugEnabled()) {
            logger_.debug("Attempting number of #" + iNoOfConnections
                    + " new JDBC Connections ");
        }

        for (long i = 0; i < iNoOfConnections; i++) {
            // if (lockedObjectsVector_.size() + unlockedObjectsMap_.size() <
            // myAttributes
            // .getMaximumCapacity()) {
            if (pool_.getNumActive() + pool_.getNumIdle() < myAttributes
                    .getMaximumCapacity()) {
                try {
                    long time = System.currentTimeMillis();
                    Connection con = DriverManager.getConnection(myAttributes
                            .getURL(), myAttributes.getUser(), myAttributes
                            .decode(myAttributes.getPassword()));
                    setConnectionDelayTime(System.currentTimeMillis() - time);
                    iNoOfConnectionsCreated_++;
                    lTotalNoOfConnectionsCreated_++;
                    if (logger_.isDebugEnabled()) {
                        logger_.debug("New Connection Created #"
                                + con.toString());
                    }
                    addConnectionToPool(con);
                } catch (SecurityException e) {
                    throw new SQLException("Unable to decode password");
                }
            } else {
                break;
            }
        }
    }

    // Initialize the pool
    /**
     * Initializes the pool.
     * 
     * This is the most imporatant method of this class doing the connection
     * pool operation it loads or registers the driver and calls
     * createConnection() method. then it instantiates the PooledConnection
     * class by passing the connection created by calling the createConnection()
     * method. At the end it calls the addConnection(PooledConnection) method to
     * add the connection to the pool
     * 
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    protected synchronized void initializePool() throws IOException,
            SQLException, ClassNotFoundException {
        // Check our initial values
        // if (lockedObjectsVector_ == null) {
        // lockedObjectsVector_ = new Vector(myAttributes.getMaximumCapacity());
        // }
        // if (unlockedObjectsMap_ == null) {
        // unlockedObjectsMap_ = new HashMap(myAttributes.getMaximumCapacity());
        // }
    	bDestroyAttempted_ = bTerminated_ = false;
        if (hmConnectionUsage_ == null) {
            hmConnectionUsage_ = new HashMap<String, Integer>(myAttributes.getMaximumCapacity());
        }

        if (myAttributes.getConnectionIdleTimeout() <= 0) {
            if (logger_.isInfoEnabled()) {
                logger_
                .info("Connections will not be pooled as Connection In-Activity Timeout is less than or equals ZERO.");
                logger_
                        .info("Overriding the attributes of Capacity Increament, Initial Pool Size and Self Check Interval");
            }
            myAttributes.setShrinkPoolInterval(myAttributes
                    .getConnectionIdleTimeout());
            myAttributes.setCapacityIncreament(1);
            myAttributes.setInitialPoolSize(0);
        }
        if (CPoolAttribute.DEFAULT_SQL_QUERY.equals(myAttributes.getSqlQuery())) {
        	throw new SQLException("Undefined sql-query. Please set the appropriate query");
        }
        alStatisticsHistory_ = new ArrayList<CPoolStatisticsBean>(myAttributes
                .getStatisticalHistoryRecordCount());
        Class.forName(myAttributes.getDriver());
        createPooledConnection(((myAttributes.getInitialPoolSize() <= myAttributes
                .getMaximumCapacity()) ? myAttributes.getInitialPoolSize()
                : myAttributes.getMaximumCapacity()));
        if (logger_.isDebugEnabled()) {
            logger_.debug("Initiate Self Check background process");
        }
        startShrinking();
//        lPoolStartTimeInMillis_ = System.currentTimeMillis();
        CPoolStatisticsBean bean = getStatistics();
        logger_.log(LogLevel.NOTICE, "Pool Created and initialized. Statstics "
                + bean.toString());
        recordStatistics(bean);
        validateQuery();
    }

    /**
     * 
     */
    private void validateQuery() throws IOException, SQLException {
    	Connection con = null;
    	try {
			con = getConnection();
	        isActive(con, logger_, true);
        } catch (IOException e) {
	        throw e;
        } catch (SQLException e) {
        	throw e;
        } catch (RuntimeException e) {
        	throw new SQLException("Invalid Query " + e.getCause().getMessage(), e.getCause());
        } finally {
        	if (con != null) {
        		try {
	                con.close();
                } catch (SQLException e) {
                	//simply ignore
                }
        	}
        	resetStatistics();
        }
    }

	// Adds the PooledConnection to the pool
    /**
     * This method is used to add the connection in the pool it uses the
     * Java.util.Vector pool property to create the connection pool
     * 
     * @param value
     *            Connection.
     */
    private void addConnectionToPool(Connection value) {
        // If the pool is null, create a new vector
        // with the initial size of "size"
        // Add the pooledConnection Object to the vector
        if (logger_.isDebugEnabled()) {
            logger_.debug("Adding Connection to the pool #" + value.toString());
        }
        pool_.add(value);
        // unlockedObjectsMap_.put(value, (new
        // Long(System.currentTimeMillis())));
    }

    /**
     * Releases the connection
     * 
     * @param con
     *            JDBC Connection.
     * @param bGarbageCollected
     *            Is Garbage collected.
     * @param iLeakedStatementCount
     *            Leaked Statement count.
     */
    public synchronized void releaseConnection(Connection con,
            boolean bGarbageCollected, int iLeakedStatementCount) {
        if (bTerminated_) {
            return;
        }
        lLeakedStatementsCount_ += iLeakedStatementCount;
        iNoOfConnectionsUnlocked_++; // Released to Pool.
        try {
            Connection realConnection = null;
            if (con instanceof ConnectionWrapper) {
                realConnection = ((ConnectionWrapper) con).realConnection();
            } else {
                realConnection = con;
            }

            if (bGarbageCollected) {
                lLeakedConnections_++;
            }
            // int iLockedSizeBefore = lockedObjectsVector_.size();
            // int iLockedSize = pool_.getActiveCount();
            // // lockedObjectsVector_.remove(realConnection);
            // if (logger_.isDebugEnabled()) {
            // logger_.debug("Un-Locking Connection #" +
            // realConnection.toString() + " Locked Size Before #"
            // + iLockedSizeBefore + "Locked Size After#"
            // + lockedObjectsVector_.size());
            // }
            if (realConnection == null || realConnection.isClosed()) {
                logger_.warn("Closed Connection Found ......");
                pool_.destroy(realConnection);
            } else {
                if (myAttributes.getConnectionIdleTimeout() <= 0) {
                    if (logger_.isDebugEnabled()) {
                        logger_
                                .debug("Closing the connection as Connections are not to be pooled.");
                    }
                    closeConnection(realConnection);
                } else {
                    if (myAttributes.getMaxUsagePerJDBCConnection() > 0) {
                        String key = realConnection.toString();
                        if (hmConnectionUsage_.containsKey(key)) {
                            int iUsage = hmConnectionUsage_.get(key) + 1;
                            if (iUsage >= myAttributes
                                    .getMaxUsagePerJDBCConnection()) {
                                if (logger_.isDebugEnabled()) {
                                    logger_
                                            .debug("Closing the connection as maximum usage reached. #"
                                                    + key);
                                }
                                closeConnection(realConnection); // This
                                                                    // destroys
                                                                    // the
                                                                    // object
                                                                    // from the
                                                                    // pool.
                                hmConnectionUsage_.remove(key);
                            } else {
                                hmConnectionUsage_.put(key, iUsage);
                                // addConnectionToPool(realConnection);
                                pool_.release(realConnection); // Release and
                                                                // Add.
                            }
                        } else {
                            hmConnectionUsage_.put(key, 1);
                            // addConnectionToPool(realConnection);
                            pool_.release(realConnection); // Release and Add.
                        }
                    } else {
                        // addConnectionToPool(realConnection);
                        pool_.release(realConnection);
                    }
                }
            }
        } catch (SQLException sqe) {
            logger_.error(sqe);
        }
        if (pool_.getNumActive() == 0 && bDestroyAttempted_
                && myAttributes.getShrinkPoolInterval() <= 0) {
            try {
                pool_.destroy();
            } catch (PoolInUseException e) {
                // dummy catch.
            }
            poolManager_.poolDestroyed(getPoolName());
        }
        notify(); //notifyAll is specifically not used.
        CPoolStatisticsBean bean = getStatistics();
        //The following is not for logging but for recording statistics if enabled for FINEST level of logging. 
        if (logger_.isEnabledFor(LogLevel.FINEST)) {
            recordStatistics(bean);
        }
    }

    /**
     * Returns a JDBC connection from the pool if it is free. If there is no
     * free connection available then the pool will try to create a new JDBC
     * connection if the maximum limit is not reached. If all the connections
     * are in use then the pool will try to wait for the specified time interval
     * to see if any other thread releases the connection and if none are
     * released then will throw an SQLException.
     * 
     * @return Connection.
     * @throws IOException
     * @throws SQLException
     */
    public synchronized Connection getConnection() throws IOException,
            SQLException {
        if (bTerminated_) {
            throw new IOException("Pool has been terminated");
        }
        iNoOfConnectionsRequested_++;
        return getConnection(false);
    }

    // Find an available connection
    /**
     * Returns a JDBC connection from the pool if it is free. If there is no
     * free connection available then the pool will try to create a new JDBC
     * connection if the maximum limit is not reached. If all the connections
     * are in use then the pool will try to wait for the specified time interval
     * to see if any other thread releases the connection and if none are
     * released will throw an SQLException.
     * 
     * @param bThrowException
     *            True to throw exception.
     * @return Connection
     * @throws IOException
     * @throws SQLException
     */
    private Connection getConnection(boolean bThrowException)
            throws IOException, SQLException {
        if (bDestroyAttempted_) {
            throw new IOException(
                    "The pool is in process of destroying itself. Cannot service any requests.");
        }
        Connection con = null;
        // for (Iterator iter = unlockedObjectsMap_.keySet().iterator(); iter
        // .hasNext();) {
        while (pool_.getNumIdle() > 0) {
            con = (Connection) pool_.acquire();

            if (logger_.isDebugEnabled()) {
                logger_.debug("{0}Validating the Connection object.");
            }
            if (isActive(con, logger_, false)) {
                // iter.remove();
                // addReleasedConenction(con);
                iNoOfConnectionsLocked_++; // Released
                if (lConnectionsHigh_ < pool_.getNumActive()) {
                    lConnectionsHigh_ = pool_.getNumActive();
                }
                CPoolStatisticsBean bean = getStatistics();
                //if the log is enabled for FINEST level of logging the intention is also to record it.
                if (logger_.isEnabledFor(LogLevel.FINEST)) {
                    logger_.log(LogLevel.FINEST, "getConnection() Statistics " + getStatistics().toString());
                    recordStatistics(bean);
                }
                return new ConnectionWrapper(this, con);
            } else {
                // iter.remove();
                closeConnection(con); // Destroys the object from the pool.
            }
        }

        try {
            createPooledConnection(myAttributes.getCapacityIncreament());
        } catch (SQLException e) {
            if (!bThrowException && myAttributes.getInUseWaitTime() > 0) {
                if (e.getMessage().equalsIgnoreCase(
                        "Unable to increament pool. Max Capacity reached.")) {
                    try {
                        if (logger_.isDebugEnabled()) {
                            logger_
                                    .debug("Waiting "
                                            + myAttributes.getInUseWaitTime()
                                            + " seconds for some other process to release one Connection.");
                        }
                        addWaiter();
                        long time = System.currentTimeMillis();
                        TimeUnit.SECONDS.timedWait(this, myAttributes.getInUseWaitTime());
//                        wait((long) (myAttributes.getInUseWaitTime() * 1000), 999999);
                        long waitTime = System.currentTimeMillis() - time;
                        if (logger_.isDebugEnabled()) {
                            logger_.debug("Actual Wait for " + waitTime + " miliseconds");
                        }
                        
                        removeWaiter(); // Remove the waiter //This needs to be
                                        // debated. Is this the right
                        // place to remove the waiter ? As the time if > than
                        // the attribute
                        // in-use-wait-time then the connection is not yet
                        // released and that
                        // the request is still waiting for the connection.
                        setWaitTime(waitTime);
                        if (logger_.isDebugEnabled()) {
                            if (waitTime < (myAttributes.getInUseWaitTime() * 1000)-50) {
                                logger_
                                        .debug("Somebody released one connection. Let me grab it.");
                            } else {
                                logger_
                                        .debug("All are using JDBC Connection. Its time to see if I can still get one or throw Exception.");
                            }
                        }
                    } catch (InterruptedException e1) {
                        if (logger_.isDebugEnabled()) {
                            logger_.debug("Somebody interupted my wait.");
                        }
                    }
                }
            } else {
                throw e;
            }
        }
        return getConnection(true);
    }

    // While shutting down the pool,u need to first empty it.
    /**
     * This method is called to empty the pool.
     *
     * @param bForceShutdown True to forcefully shutdown.
     * @throws SQLException
     *             If the pool is being used.
     */
    public synchronized void emptyPool(boolean bForceShutdown) throws SQLException {
        bDestroyAttempted_ = true;
        // Iterate over the entire pool closing the JDBC connections
        stopShrinking();
        // for (Iterator iter = unlockedObjectsMap_.keySet().iterator(); iter
        // .hasNext();) {
        // Connection con = (Connection) iter.next();
        // if (logger_.isDebugEnabled()) {
        // logger_.debug("Closing JDBC Connection " + con);
        // }
        // closeConnection(con);
        // iter.remove();
        // } // for
        try {
            if (bForceShutdown) {
                Object[] acquiredObjects = pool_.getAcquiredObjects();
                if (logger_.isInfoEnabled()) {
                    logger_.info("Forceful Shutdown requested. Killing " + acquiredObjects.length + "in-use connections ");
                }
                for (int i = 0; i < acquiredObjects.length; i++) {
                    if (acquiredObjects[i] instanceof Connection) {
                        Connection connection = (Connection) acquiredObjects[i];
                        connection.close();
                        pool_.release(connection);
                    }
                }
            }
            pool_.destroy();
            if (bForceShutdown) {
                bTerminated_ = true;
            }
        } catch (PoolInUseException e) {
            threadShrink = null;
            startShrinking();
            throw new SQLException("Pool is being used. Please try later.");
        }
        // if (!lockedObjectsVector_.isEmpty()) {
        // threadShrink = null;
        // startShrinking();
        // throw new SQLException("Pool is being used. Please try later");
        // log(LogType.NOTICE, "Forcefully closing used Connections ");
        // for (Iterator iter = lockedObjectsVector_.iterator(); iter
        // .hasNext();)
        // {
        // Connection con = (Connection) iter.next();
        // log(LogType.DEBUG, "Closing JDBC Connection " + con);
        // closeConnection(con);
        // iter.remove();
        // } //for
        // }
        CPoolStatisticsBean bean = getStatistics();
        recordStatistics(bean); // This has to be recorded as pool is now empty. Does not depends on log level.
        logger_.log(LogLevel.NOTICE, "Pool Emptied. " + bean.toString());
    } // method emptyPool

    /**
     * Shrinks the pool for Inactive Connections.
     * 
     * The shrinking is done depending upon the property
     * pr.dbconnectioninactivitytime. If the pool connection is inactive for the
     * specified period then the physical connection is closed.
     * 
     * @param logger
     *            Logger.
     */
    private synchronized void shrinkPool(Logger logger) {
        long lShrinkPoolStartTime = System.currentTimeMillis(); 
        if (logger.isDebugEnabled()) {
            logger.debug("Self check process started..");
        }
        lNoOfUnavailableConnections_ = pool_.getNumActive()
                + pool_.getNumIdle();
        recordUnavailableHigh(lNoOfUnavailableConnections_);
        CPoolStatisticsBean bean = getStatistics();
        recordStatistics(bean); // Does not depends on log level. Has to be recorded.
        if (logger.isInfoEnabled()) {
            logger.info("Before Self-Check Statistics: " + bean.toString());
        }
        for (Iterator<CObjectWrapper> iter = pool_.getIdleObjects(); iter.hasNext();) {
            CObjectWrapper wrapper = iter.next();
            Connection element = (Connection) wrapper.getUnderLyingObject();
            // .abs(lConnectionInactivityTime_ * 60000);
            long lTime = wrapper.getTime();
            long lCurrentTime = System.currentTimeMillis();
            if ((pool_.getNumIdle() + pool_.getNumActive()) > myAttributes
                    .getInitialPoolSize()) {
                if (bDestroyAttempted_) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Destroying Pooled Connection " + element);
                    }
                    closeConnection(element);
                    iter.remove();
                } else {
                    if ((lCurrentTime - lTime) >= TimeUnit.MINUTES.toMillis(myAttributes.getConnectionIdleTimeout())) {
                        closeConnection(element);
                        iter.remove();
                        if (logger.isDebugEnabled()) {
                            logger.debug("Pool Connection Closed " + element);
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("{1}Validating the Connection object.");
                        }
                        if (!isActive(element, logger, false)) {
                            if (logger.isDebugEnabled()) {
                                logger
                                .debug("Removing Bad Connection "
                                        + element);
                            }
                            closeConnection(element);
                            iter.remove();
                            lBadConnectionCount_++;
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Connection is found ok. "
                                        + element.toString());
                            }
                        }
                    } // end of connection is not idle
                } // end of else of destroy attempted
            } // pool size > initial pool size
            else {
                if (bDestroyAttempted_) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Pool is being destroyed. Destroying Pooled Connection " + element);
                    }
                    closeConnection(element);
                    iter.remove();
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("{2}Validating the Connection object.");
                    }
                    if (!isActive(element, logger, false)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Removing bad connection " + element);
                        }
                        closeConnection(element);
                        iter.remove();
                        lBadConnectionCount_++;
                    }
                }
            } // if pooled connections size < initial connection size.
        } // For loop of Enumeration

        iNoOfConnectionsClosed_ = iNoOfConnectionsCreated_ = 
            iNoOfConnectionsLocked_ = iNoOfConnectionsUnlocked_ = 
                iNoOfConnectionsRequested_ = iNoOfNewConnectionsRequested_ = 0;
        lNoOfUnavailableConnections_ = 0L;
        
        if (logger.isInfoEnabled()) {
            logger.info("Self check process over.");
        }
        recordUnavailableHighTime(System.currentTimeMillis() - lShrinkPoolStartTime);
    }

    /**
     * Shirnk or Self check Thread routine.
     * 
     * This class is responsible to initiate self checks on the pooled objects,
     * identify all objects that can be closed and closes them.
     * 
     * @author kedarr
     * @version $Revision:: $
     */
    public class CShrinkPoolThread extends Thread {

        /**
         * Logger. Comment for <code>selfChecklogger_</code>.
         */
        private Logger selfChecklogger_;

        /**
         * Shrink / Self Check pool helper class.
         * 
         * This class is responsible to periodically do a self check of all the
         * existing JDBC Connections in the pool as well as shrink the pool if
         * the connections are found to be idle.
         * 
         */
        public CShrinkPoolThread(String name) {
//            super(name); // as the logger is specific to the pool.
            selfChecklogger_ = Logger.getLogger("JDBC.Pool[" + getPoolName()
                    + "].SelfCheck");
        }

        /**
         * Method responsible for shrinking the pool.
         * 
         * The pool is shrinking mechanism is invoked depending upon the
         * property pr.dbshrinkpoolinterval defined. This internally calls
         * {#shrinkPool()} method.
         */
        public void run() {
            try {
                selfChecklogger_
                        .info("Background self check process started...");
                CPoolStatisticsBean statisticsBean = getStatistics();
                recordStatistics(statisticsBean); //This has to be recorded.
                while (!threadShrink.isInterrupted()) {
                    if (selfChecklogger_.isDebugEnabled()) {
                        selfChecklogger_
                                .debug("In Sleep mode and will sleep for "
                                        + myAttributes.getShrinkPoolInterval()
                                        + " minute(s).");
                    }
                    Thread.yield();
                    TimeUnit.MINUTES.sleep(myAttributes.getShrinkPoolInterval());
//                            sleep(((long)(myAttributes.getShrinkPoolInterval()) * 60000L));
                    if (selfChecklogger_.isDebugEnabled()) {
                        selfChecklogger_.debug("Initiating self check");
                    }
                    shrinkPool(selfChecklogger_);
                    statisticsBean = getStatistics();
                    recordStatistics(statisticsBean); //This has to be recorded.
                    if (selfChecklogger_.isEnabledFor(LogLevel.FINEST)) {
                        selfChecklogger_.log(LogLevel.FINEST, "After Self Check Statistics " + statisticsBean.toString());
                    }
                    if (bDestroyAttempted_) {
                        // if (lockedObjectsVector_.size() == 0) {
                        if (pool_.getNumActive() == 0) {
                            try {
                                pool_.destroy();
                            } catch (PoolInUseException e) {
                                // dummy catch.
                            }
                            poolManager_.poolDestroyed(myAttributes
                                    .getPoolName());
                            break;
                        }
                    }
                }
            } catch (InterruptedException ie) {
                // dummy catch.
            }
            selfChecklogger_.info("Background self check process stopped.");
        }
    }

    /**
     * Starts the Pool Shrinking mechanisim.
     * 
     */
    public void startShrinking() {
        if (threadShrink == null) {
            threadShrink = new CShrinkPoolThread(myAttributes.getPoolName()+".SelfCheck");
            if (myAttributes.getShrinkPoolInterval() > 0) {
                threadShrink.start();
            } else {
                logger_
                        .info("Pool will not be shrinked nor self checks will be initiated.");
            }
        } else {
            stopShrinking();
            threadShrink = null;
            startShrinking();
        }
    }

    /**
     * Stops the Pool Shrinking mechanisim.
     * 
     */
    public void stopShrinking() {
        if (threadShrink != null) {
            if (threadShrink.isAlive()) {
                if (logger_.isInfoEnabled()) {
                    logger_.info("Stopping Shrinking Of Pool...");
                }
                threadShrink.interrupt();
            }
        }
    }

    /**
     * Checks whether the underlying physical JDBC connection is active or not.
     * 
     * @param con
     *            Connection
     * @param logger
     *            Logger
     * @return boolean True if active else false.
     */
    private synchronized boolean isActive(Connection con, Logger logger, boolean validateQuery){
        Statement st = null;
        ResultSet rs = null;
        boolean bReturnValue = false;
        try {
            st = con.createStatement();
            rs = st.executeQuery(myAttributes.getSqlQuery());
            if (logger.isDebugEnabled()) {
                logger.debug("Connection found OK.");
            }
            bReturnValue = true;
        } catch (SQLException e) {
        	if (validateQuery) {
        		throw new RuntimeException("Invalid Query", e);
        	}
        	if (logger.isInfoEnabled()) {
        		logger.info("Invalid connection found ", e);
        	}
        	bReturnValue = false;
            if (logger.isDebugEnabled()) {
                logger.debug("Bad Connection found.");
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    // dummy catch;
                }
            }
            if (st != null) {
            	try {
					st.close();
				} catch (SQLException e2) {
					// dummy.
				}
            }
        }
        return bReturnValue;
    }

    // /**
    // * Adds the connection released from the pool to locked objects pool.
    // *
    // * @param con
    // * Connection that is being released by the pool.
    // */
    // private void addReleasedConenction(Connection con) {
    // lockedObjectsVector_.add(con);
    // if (logger_.isDebugEnabled()) {
    // logger_.debug("Locking Connections. #" + con.toString() + " Locked Size
    // #"
    // + lockedObjectsVector_.size());
    // }
    // if (lConnectionsHigh_ < lockedObjectsVector_.size()) {
    // lConnectionsHigh_ = lockedObjectsVector_.size();
    // }
    // }

    /**
     * Closes the physical JDBC connection.
     * 
     * @param con
     *            Connection to be closed.
     */
    private void closeConnection(Connection con) {
        try {
            iNoOfConnectionsClosed_++;
            con.close();
            if (logger_.isDebugEnabled()) {
                logger_.debug("Connection closed #" + con.toString());
            }
        } catch (SQLException e) {
            // dummy exception;
        }
        pool_.destroy(con);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ("Pool Name " + myAttributes.getPoolName() + " Statistics: " + getStatistics());
    }

    /**
     * Returns the pool name.
     * 
     * @return Pool Name
     */
    public String getPoolName() {
        return myAttributes.getPoolName();
    }

    /**
     * Returns the pool attributes.
     * 
     * @return CPoolAttributes
     */
    public CPoolAttribute getPoolAttributes() {
        return myAttributes;
    }

    /**
     * Returns the statistics of this pool.
     * 
     * @return CPoolStatisticsBean
     */
    public CPoolStatisticsBean getStatistics() {
        CPoolStatisticsBean csb = new CPoolStatisticsBean();
        csb.setPoolName(myAttributes.getPoolName());
        csb.setBadConnectionCount(lBadConnectionCount_);
        csb.setConnectionsHighCount(lConnectionsHigh_);
        csb.setLeakedStatementCount(lLeakedStatementsCount_);
        csb.setLeakedConnectionCount(lLeakedConnections_);
        csb.setCurrentFreeConnectionCount(pool_.getNumIdle());
        csb.setCurrentUsedConnectionCount(pool_.getNumActive());
        csb.setLeakedResultSetCount(lLeakedResultSetCount_);
        csb.setWaitersHighCount(iNoOfWaitersHigh_);
        csb.setCurrentWaitersCount(iNoOfCurrentWaiters_);
        csb.setWaitTimeHighInMillis(lWaitTimeHigh_);
        csb.setWaitTimeTotalInMillis(lWaitTimeTotalInMillis_);
        csb.setPoolStartTimeInMillis(lPoolStartTimeInMillis_);
        if (lTotalNoOfConnectionsCreated_ > 0) {
            csb
                    .setAverageConnectionDelayTimeInMillis(lTotalConnectionDelayTime_
                            / lTotalNoOfConnectionsCreated_);
            csb.setTotalConnectionsCreated(lTotalNoOfConnectionsCreated_);
        }
        csb.setNoOfUnavailableConnections(lNoOfUnavailableConnections_);
        csb.setNoOfUnavailableConnectionsHigh(lNoOfUnavailableConnectionsHigh_);
        csb.setUnavailableConnectionsHighTime(lUnavailableConnectionsHighTime_);
        if (myAttributes.getShrinkPoolInterval() > 0) {
            csb.setPrintStatisticalAttributes(true);
            csb.setNoOfConnectionsRequested(iNoOfConnectionsRequested_);
            csb.setNoOfNewConnectionsRequested(iNoOfNewConnectionsRequested_);
            csb.setNoOfConnectionsCreated(iNoOfConnectionsCreated_);
            csb.setNoOfConnectionsLocked(iNoOfConnectionsLocked_);
            csb.setNoOfConnectionsUnlocked(iNoOfConnectionsUnlocked_);
            csb.setNoOfConnectionsClosed(iNoOfConnectionsClosed_);
        }
        csb.setHeapMemoryUsage(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed());
        csb.setHeapMemoryUsageMax(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());
        csb.setNonHeapMemoryUsage(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed());
        csb.setNonHeapMemoryUsageMax(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getMax());
        return csb;
    }

    private void resetStatistics() {
    	lBadConnectionCount_ = lConnectionsHigh_ = lLeakedStatementsCount_ = lLeakedConnections_ = 0; 
    	lLeakedResultSetCount_ = lWaitTimeTotalInMillis_ = lWaitTimeHigh_ = 0;
    	iNoOfWaitersHigh_ = iNoOfCurrentWaiters_ = 0;
    	lNoOfUnavailableConnections_ = lNoOfUnavailableConnectionsHigh_ = lUnavailableConnectionsHighTime_ = 0;
    	iNoOfConnectionsRequested_  = iNoOfNewConnectionsRequested_ = iNoOfConnectionsCreated_ = iNoOfConnectionsLocked_= 0;
    	iNoOfConnectionsUnlocked_ = iNoOfConnectionsClosed_ = 0;
    	hmConnectionUsage_.clear();
    	QueryTime_.clear();
    }
    
    /**
     * Adds the leaked ResultSet Count.
     * 
     * @param iLeakedResultSetCount
     */
    public void addLeakedResultSetCount(int iLeakedResultSetCount) {
        lLeakedResultSetCount_ += iLeakedResultSetCount;
    }

    /**
     * Adds the query in the hashtable.
     * 
     * @param t
     *            Thread
     * @param objWrapper
     *            CObjectWrapper
     */
    public void addQuery(Thread t, CObjectWrapper objWrapper) {
        QueryTime_.put(t, objWrapper);
    }

    /**
     * Removes the query from the hastable.
     * 
     * @param t
     *            Thread
     */
    public void removeQuery(Thread t) {
        QueryTime_.remove(t);
    }

    /**
     * Returns the currently executing queries.
     * 
     * @return Hashtable
     */
    @SuppressWarnings("unchecked")
	protected Hashtable<Thread, CObjectWrapper> getCurrentRunningQueries() {
        return (Hashtable<Thread, CObjectWrapper>) QueryTime_.clone();
    }

    /**
     * Increases the current waiter count by 1.
     * 
     * Method also checks if the current waiters is greater than the waiters
     * high count then it sets the high count to new high (current waiters).
     * 
     */
    private void addWaiter() {
        iNoOfCurrentWaiters_++;
        if (iNoOfCurrentWaiters_ > iNoOfWaitersHigh_) {
            iNoOfWaitersHigh_ = iNoOfCurrentWaiters_;
        }
    }

    /**
     * Decreases the current waiter count by 1.
     * 
     */
    private void removeWaiter() {
        iNoOfCurrentWaiters_--;
    }

    /**
     * Sets the highest wait time.
     * 
     * If the wait time passed is greater than the wait time high then the high
     * wait time is set to new high (passed wait time).
     * 
     * @param waitTime
     */
    private void setWaitTime(long waitTime) {
        lWaitTimeTotalInMillis_ += waitTime;
        if (waitTime > lWaitTimeHigh_) {
            lWaitTimeHigh_ = waitTime;
        }
    }

    /**
     * Delay in creating a new JDBC Connection.
     * 
     * @param lConnectionDelayTime
     */
    private void setConnectionDelayTime(long lConnectionDelayTime) {
        lTotalConnectionDelayTime_ += lConnectionDelayTime;
    }

    /**
     * Returns the statistics collected in an ArrayList.
     * 
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
	protected synchronized ArrayList<CPoolStatisticsBean> getStatisticsHistory() {
        return (ArrayList<CPoolStatisticsBean>) alStatisticsHistory_.clone();
    }

    /**
     * Records the statistics in an ArrayList.
     * 
     * @param bean
     *            {@link CPoolStatisticsBean}
     */
    private void recordStatistics(CPoolStatisticsBean bean) {
        alStatisticsHistory_.add(bean);
        if (alStatisticsHistory_.size() > myAttributes
                .getStatisticalHistoryRecordCount()) {
            alStatisticsHistory_.remove(0);
        }
    }
    
    /**
     * Records unavailable connections high time.
     *
     * @param milliseconds
     */
    private void recordUnavailableHighTime(long milliseconds) {
        if (lUnavailableConnectionsHighTime_ < milliseconds) {
            lUnavailableConnectionsHighTime_ = milliseconds;
        }
    }

    /**
     * Records unavailable connections high .
     *
     * @param plNoOfUnavailableConnections
     */
    private void recordUnavailableHigh(long plNoOfUnavailableConnections) {
        if (lNoOfUnavailableConnectionsHigh_ < plNoOfUnavailableConnections) {
            lNoOfUnavailableConnectionsHigh_ = plNoOfUnavailableConnections;
        }
    }
    
    
} // class
