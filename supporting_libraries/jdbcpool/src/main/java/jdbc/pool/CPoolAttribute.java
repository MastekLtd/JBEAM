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
 * $Revision: 8 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CPoolAttribute.java 8     1/29/09 9:51p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CPoolAttribute.java $
 * 
 * 8     1/29/09 9:51p Kedarr
 * Added a new method reset.
 * 
 * 7     4/17/08 10:19a Kedarr
 * Removed one unwanted comma from the toString() method.
 * 
 * 6     3/17/08 2:51p Kedarr
 * Changes made to implement PBEEncryptionRoutine. This uses the bigcastle
 * routines, an open source that implements security package of java.
 * 
 * 5     3/20/07 10:19a Kedarr
 * New API added for statistics collection.
 * 
 * 4     5/02/06 4:38p Kedarr
 * Added a new attribute for Algorithm. Removed the debug messages as well
 * as updated the javadoc.
 * 
 * 3     2/23/06 12:12p Kedarr
 * Object.equals() method implemented. Internally this method checks for
 * all the attributes of the pool with the one passed as a parameter.
 * 
 * Did eclipse formatting of the source.
 * 
 * 2     11/09/05 10:25a Kedarr
 * Added a new attribute maxUsagePerJDBCConnection. The Pool will close
 * the JDBC Connection if the connection was used more than the value of
 * this attribute.
 * 
 * 1     10/25/05 3:19p Kedarr
 * 
 * 2     10/25/05 3:19p Kedarr
 * 
 * 1     10/25/05 3:19p Kedarr
 * 
 * 6     8/30/05 12:01p Kedarr
 * Methods added to decode and encode the password and few related methods
 * for the same.
 * 
 * 5     8/29/05 1:47p Kedarr
 * Updated Java Doc as well as added new attributes that were added in the
 * pool but were not added in the class.
 * 
 * 4     7/20/05 11:09a Kedarr
 * Changed for Wait Time when All In Use.
 * 
 * 3     6/30/05 11:06a Kedarr
 * Throwing of exceptions is removed from the Shrink pool interval and
 * connection idle timeout method.
 * 
 * 2     6/06/05 6:16p Kedarr
 * Changes made to enable Log4J
 * 
 * 1     5/16/05 11:57a Kedarr
 * Initial Version. Class stores the attributes of the pool (initial
 * connections, maximum capacity,jdbc.pool.attributes * 
 * Created on May 13, 2005
 *
 */
package jdbc.pool;

import com.stg.crypto.PBEEncryptionRoutine;

/**
 * Stores the pool attributes properties.
 * 
 * @author kedarr
 * @version $Revision: 8 $
 */
public class CPoolAttribute implements Cloneable {

	public static final String DEFAULT_SQL_QUERY = "UNDEFINED";
	
    /**
     * Stores the revision number of the class.
     * 
     * Comment for <code>REVISION</code>
     */
    public static final String REVISION = "$Revision: 8 $";

    /**
     * Comment for <code>poolName</code>
     */
    private String poolName;

    /**
     * Comment for <code>driver</code>
     */
    private String driver;

    /**
     * Comment for <code>user</code>
     */
    private String user;

    /**
     * Comment for <code>password</code>
     */
    private String password;

    /**
     * Comment for <code>URL</code>
     */
    private String URL;

    /**
     * Comment for <code>vendor</code>
     */
    private String vendor;

    /**
     * Comment for <code>initialPoolSize</code>
     */
    private int initialPoolSize;

    /**
     * Comment for <code>maximumCapacity</code>
     */
    private int maximumCapacity;

    /**
     * Comment for <code>capacityIncreament</code>
     */
    private int capacityIncreament;

    /**
     * Comment for <code>shrinkPoolInterval</code>
     */
    private int shrinkPoolInterval;

    /**
     * Comment for <code>connectionIdleTimeout</code>
     */
    private int connectionIdleTimeout;

    /**
     * Comment for <code>criticalOperationTimeLimit</code>
     */
    private int criticalOperationTimeLimit;

    /**
     * Stores the All-In-Use-Wait interval. Time in seconds.
     */
    private int inUseWaitTime;

    /**
     * Stores the number of times a JDBC Connection can be used after which, it
     * would be closed forcefully. Comment for
     * <code>maxUsagePerJDBCConnection</code>.
     */
    private int maxUsagePerJDBCConnection;

    /**
     * Stores the load on startup setting of the pool. Comment for
     * <code>loadOnStartup</code>.
     */
    private boolean loadOnStartup;

    /**
     * Stores whether the attributes needs to be saved in the XML Configuration
     * file. Comment for <code>bToBeSaved_</code>.
     */
    private boolean bToBeSaved_;
    
    /**
     * Pool Algorithm.
     * Defaulted to FIFO.
     * Comment for <code>poolAlgorithm_</code>.
     */
    private String poolAlgorithm = "";
    
    /**
     * JAVADOC: Add what is stored in the variable.
     * Comment for <code>iStatisticalHistoryRecordCount_</code>.
     */
    private int iStatisticalHistoryRecordCount_;
    
    /**
     * SQL to be fired to validate the connection object for stale connections.
     */
    private String sqlQuery_ = DEFAULT_SQL_QUERY;
    
    /**
     * Returns the Capacity Increment.
     * 
     * @return Returns the capacityIncreament.
     */
    public int getCapacityIncreament() {
        return capacityIncreament;
    }

    /**
     * Sets the capacity Increament. This determines the increase in pooled JDBC
     * connections when all created are in use. The pool garuntees that the
     * number of connections will not cross the maximum capacity of the pool.
     * 
     * @param capacityIncreament
     *            The capacityIncreament to set.
     */
    public void setCapacityIncreament(int capacityIncreament) {
        if (capacityIncreament <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacityIncreament = capacityIncreament;
    }

    /**
     * Returns the connection idle timeout interval after which the JDBC
     * connection is liable to be physically closed.
     * 
     * The pool will keep alive the number of connections mentioned in the
     * initial size of the pool.
     * 
     * @return Returns the connectionIdleTimeout.
     */
    public int getConnectionIdleTimeout() {
        return connectionIdleTimeout;
    }

    /**
     * Sets the connection idle timeout interval after which, the connection is
     * liable to be physically closed.
     * 
     * @param connectionIdleTimeout
     *            The connectionIdleTimeout to set.
     */
    public void setConnectionIdleTimeout(int connectionIdleTimeout) {
        this.connectionIdleTimeout = connectionIdleTimeout;
    }

    /**
     * Returns the JDBC driver class name used to create JDBC Connection.
     * 
     * @return Returns the driver.
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Sets the JDBC driver class name used to create JDBC Connection.
     * 
     * @param driver
     *            The driver to set.
     */
    public void setDriver(String driver) {
        if (driver.equals("")) {
            throw new IllegalArgumentException("Invalid driver specified.");
        }
        this.driver = driver;
    }

    /**
     * Returns the initial size of the JDBC connections that are created when
     * the pool gets initialized.
     * 
     * For better performance have this value equals to the max capacity of the
     * pool. Please determine the capacity of the pool with the connections high
     * count.
     * 
     * @return Returns the initialPoolSize.
     */
    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    /**
     * Sets the initial size of the JDBC connections that will be created when
     * the pool gets created.
     * 
     * @param initialPoolSize
     *            The initialPoolSize to set.
     */
    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    /**
     * Returns the maximum capacity of the pool.
     * 
     * @return Returns the maximumCapacity.
     */
    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    /**
     * Sets the maximum capacity of the pool.
     * 
     * @param maximumCapacity
     *            The maximumCapacity to set.
     */
    public void setMaximumCapacity(int maximumCapacity) {
        if (maximumCapacity < 1) {
            throw new IllegalArgumentException("Invalid Maximum Capacity");
        }
        if (maximumCapacity < initialPoolSize) {
            throw new IllegalArgumentException(
                    "Maximum Capacity cannot be less than Initial size of the pool");
        }
        this.maximumCapacity = maximumCapacity;
    }

    /**
     * Returns the password used for creating JDBC connections.
     * 
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password used for creating JDBC connections.
     * 
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        try {
            if (password == null) {
                password="";
            }
            decode(password);
            this.password = password;
            setToBeSaved(false);
        } catch (SecurityException e) {
            try {
                this.password = encode(password);
                setToBeSaved(true);
            } catch (SecurityException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Returns the pool name.
     * 
     * @return Returns the poolName.
     */
    public String getPoolName() {
        return poolName;
    }

    /**
     * Sets the pool name. This pool name must be used to get the connection
     * from the pool.
     * 
     * @param poolName
     *            The poolName to set.
     */
    public void setPoolName(String poolName) {
        if (poolName.equals("")) {
            throw new IllegalArgumentException(
                    "Pool name must not be null or empty");
        }
        this.poolName = poolName;
    }

    /**
     * Returns the shrink pool interval time.
     * 
     * If the shrink pool interval is <= 0 then the pool will not be shrinked nor
     * periodic self checks will be initiated.
     * 
     * @return Returns the shrinkPoolInterval.
     */
    public int getShrinkPoolInterval() {
        return shrinkPoolInterval;
    }

    /**
     * Sets the shrink pool interval time.
     * 
     * If the shrink pool interval is <= 0 then the pool will not be shrinked nor
     * periodic self checks will be initiated.
     * 
     * @param shrinkPoolInterval
     *            The shrinkPoolInterval to set.
     */
    public void setShrinkPoolInterval(int shrinkPoolInterval) {
        this.shrinkPoolInterval = shrinkPoolInterval;
    }

    /**
     * Returns the URL used for creating JDBC connection.
     * 
     * @return Returns the URL.
     */
    public String getURL() {
        return URL;
    }

    /**
     * Sets the URL that will be used to create JDBC connection.
     * 
     * @param url
     *            The URL to set.
     */
    public void setURL(String url) {
        if (url.equals("")) {
            throw new IllegalArgumentException("Invalid URL");
        }
        URL = url;
    }

    /**
     * Returns the database user id.
     * 
     * @return Returns the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the database user id.
     * 
     * @param user
     *            The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returns the JDBC Vendor.
     * 
     * @return Returns the vendor.
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Sets the JDBC Vendor.
     * 
     * @param vendor
     *            The vendor to set.
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * Returnss the critical operation time limit.
     * 
     * @return Returns the criticalOperationTimeLimit.
     */
    public int getCriticalOperationTimeLimit() {
        return criticalOperationTimeLimit;
    }

    /**
     * Sets the critical operation time limit.
     * 
     * @param criticalOperationTimeLimit
     *            The criticalOperationTimeLimit to set.
     */
    public void setCriticalOperationTimeLimit(int criticalOperationTimeLimit) {
        this.criticalOperationTimeLimit = criticalOperationTimeLimit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("Pool Name,");
        sbuffer.append(this.poolName);
        sbuffer.append(", Initial Pool Size,");
        sbuffer.append(this.initialPoolSize);
        sbuffer.append(", Capacity Increament,");
        sbuffer.append(this.capacityIncreament);
        sbuffer.append(",  Maximum Capacity,");
        sbuffer.append(this.maximumCapacity);
        sbuffer.append(",  Idle Timeout,");
        sbuffer.append(this.connectionIdleTimeout);
        sbuffer.append(", Critical Operation Time Limit,");
        sbuffer.append(this.criticalOperationTimeLimit);
        sbuffer.append(", Self Check Interval,");
        sbuffer.append(this.shrinkPoolInterval);
        if (inUseWaitTime > 0) {
            sbuffer.append(", Wait Interval,");
            sbuffer.append(inUseWaitTime);
        }
        sbuffer.append(", URL,");
        sbuffer.append(this.URL);
        sbuffer.append(", Vendor,");
        sbuffer.append(this.vendor);
        sbuffer.append(", User Id,");
        sbuffer.append(this.user);
        sbuffer.append(", Password,*******");
        return sbuffer.toString();
    }

    /**
     * Returns the wait time the pool will wait before throwing SQLException if
     * all the JDBC Connections are in use and the pool is unable to create any
     * new connection.
     * 
     * Time interval in seconds.
     * 
     * @return Returns the inUseWaitTime.
     */
    public int getInUseWaitTime() {
        return inUseWaitTime;
    }

    /**
     * Set the wait time interval in seconds such that the pool will wait before
     * throwing an exception if all the connections are in use and the pool is
     * unable to create a new connection.
     * 
     * @param inUseWaitTime
     *            The inUseWaitTime to set.
     */
    public void setInUseWaitTime(int inUseWaitTime) {
        this.inUseWaitTime = inUseWaitTime;
    }

    /**
     * Returns the load on startup setting.
     * 
     * @return Returns the loadOnStartup.
     */
    public boolean isLoadOnStartup() {
        return loadOnStartup;
    }

    /**
     * Sets the load on startup setting.
     * 
     * @param loadOnStartup
     *            The loadOnStartup to set.
     */
    public void setLoadOnStartup(boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Returns true if the attributes needs to be updated in the xml
     * configuration file.
     * 
     * @return boolean
     */
    protected boolean isToBeSaved() {
        return bToBeSaved_;
    }

    /**
     * Sets true if the attributes needs to be updated in the xml configuration
     * file.
     * 
     * @param toBeSaved
     */
    protected void setToBeSaved(boolean toBeSaved) {
        bToBeSaved_ = toBeSaved;
    }

    /**
     * Encrypts the attributes.
     * 
     * @param str
     * 
     * @return String
     * @throws SecurityException
     */
    protected String encode(String str) throws SecurityException {
        PBEEncryptionRoutine cl = new PBEEncryptionRoutine();
        try {
            cl.decode(str);
            return str;
        } catch (SecurityException se) {
            return cl.encode(str);        
        }
    }

    /**
     * Decrypts the attributes.
     * 
     * @param str
     * 
     * @return String
     * @throws SecurityException
     */
    protected String decode(String str) throws SecurityException {
        PBEEncryptionRoutine cl = new PBEEncryptionRoutine();
        return cl.decode(str);
    }
    
    /**
     * Returns the maximum usage of a JDBC Connection after which, the
     * connection would be closed.
     * 
     * @return Returns the maxUsagePerJDBCConnection.
     */
    public int getMaxUsagePerJDBCConnection() {
        return maxUsagePerJDBCConnection;
    }

    /**
     * Set the maximum usage of a JDBC Connection after which, the connection
     * will be closed.
     * 
     * @param maxUsagePerJDBCConnection
     *            The maxUsagePerJDBCConnection to set.
     */
    public void setMaxUsagePerJDBCConnection(int maxUsagePerJDBCConnection) {
        this.maxUsagePerJDBCConnection = maxUsagePerJDBCConnection;
    }

    /**
     * Compares the attributes of another object.
     * True if all the attributes match.
     * 
     * 
     */
    public boolean compare(CPoolAttribute attrib) {
        if (this.getCapacityIncreament() != attrib.getCapacityIncreament()) {
            return false;
        }
        if (this.getConnectionIdleTimeout() != attrib
                .getConnectionIdleTimeout()) {
            return false;
        }
        if (this.getCriticalOperationTimeLimit() != attrib
                .getCriticalOperationTimeLimit()) {
            return false;
        }
        if (!this.getDriver().equals(attrib.getDriver())) {
            return false;
        }
        if (this.getInitialPoolSize() != attrib.getInitialPoolSize()) {
            return false;
        }
        if (this.getInUseWaitTime() != attrib.getInUseWaitTime()) {
            return false;
        }
        if (this.getMaximumCapacity() != attrib.getMaximumCapacity()) {
            return false;
        }
        if (this.getMaxUsagePerJDBCConnection() != attrib
                .getMaxUsagePerJDBCConnection()) {
            return false;
        }
        if (!decode(attrib.getPassword()).equals(decode(this.password))) {
            return false;
        }
        if (!this.getPoolAlgorithm().equals(attrib.getPoolAlgorithm())) {
            return false;
        }
        if (this.getShrinkPoolInterval() != attrib.getShrinkPoolInterval()) {
            return false;
        }
        if (this.getStatisticalHistoryRecordCount() != attrib.getStatisticalHistoryRecordCount()) {
            return false;
        }
        if (!this.getURL().equals(attrib.getURL())) {
            return false;
        }
        if (!this.getUser().equals(attrib.getUser())) {
            return false;
        }
        if (!this.getVendor().equals(attrib.getVendor())) {
            return false;
        }
        return true;
    }

    /**
     * Returns the Algorithm set using the set method.
     *
     * @return Returns the poolAlgorithm.
     * @see IPool#FIFO_ALGORITHM
     * @see IPool#LIFO_ALGORITHM
     */
    public String getPoolAlgorithm() {
        if (poolAlgorithm == null || poolAlgorithm.equals("")) {
            return IPool.FIFO_ALGORITHM;
        }
        return poolAlgorithm;
    }

    /**
     * Sets the pool Algorithm.
     *
     * @param poolAlgorithm The poolAlgorithm to set.
     * @see IPool#FIFO_ALGORITHM
     * @see IPool#LIFO_ALGORITHM
     */
    public void setPoolAlgorithm(String poolAlgorithm) {
        if (poolAlgorithm == null) {
            poolAlgorithm = "";
        } 
        if (poolAlgorithm.equals(IPool.LIFO_ALGORITHM)) {
            this.poolAlgorithm = IPool.LIFO_ALGORITHM;
        } else if (poolAlgorithm.equals(IPool.FIFO_ALGORITHM)){
            this.poolAlgorithm = IPool.FIFO_ALGORITHM;
        } else {
            this.poolAlgorithm = IPool.FIFO_ALGORITHM;
            bToBeSaved_ = true;
        }
    }
    
    /**
     * Returns the statistical history record count.
     *
     * if this count is greater than zero then the pool will keep the number of 
     * statistical data collected in a descending order.
     * 
     * This will be enabled only if the shrink pool interval is greater than zero.
     * 
     * @return record count. 
     * 
     * @see CPoolStatisticsBean
     * @see #setShrinkPoolInterval(int)
     */
    public int getStatisticalHistoryRecordCount() {
        return iStatisticalHistoryRecordCount_;
    }
    
    /**
     * Set the statistical history record count.
     *
     * if this count is greater than zero then the pool will keep the number of 
     * statistical data {@link CPoolStatisticsBean}} in a descending order.
     * 
     * This will be enabled only if the shrink pool interval is greater than zero.
     *
     * @param recordCount
     */
    public void setStatisticalHistoryRecordCount(int recordCount) {
        if (recordCount <= 0) {
            iStatisticalHistoryRecordCount_ = 1;
        } else {
            iStatisticalHistoryRecordCount_ = recordCount;
        }
    }
    
    /**
     * Resets the attributes with the one supplied provided the name of the pool matches.
     * 
     * @param attributes to be substituted for.
     */
    public boolean reset(CPoolAttribute attributes) {
        if (!attributes.getPoolName().equals(this.getPoolName())) {
            return false;
        }
        this.setCapacityIncreament(attributes.getCapacityIncreament());
        this.setConnectionIdleTimeout(attributes.getConnectionIdleTimeout());
        this.setCriticalOperationTimeLimit(attributes.getCriticalOperationTimeLimit());
        this.setDriver(attributes.getDriver());
        this.setInitialPoolSize(attributes.getInitialPoolSize());
        this.setInUseWaitTime(attributes.getInUseWaitTime());
        this.setLoadOnStartup(attributes.isLoadOnStartup());
        this.setMaximumCapacity(attributes.getMaximumCapacity());
        this.setMaxUsagePerJDBCConnection(attributes.getMaxUsagePerJDBCConnection());
        this.setPassword(attributes.getPassword());
        this.setPoolAlgorithm(attributes.getPoolAlgorithm());
        this.setShrinkPoolInterval(attributes.getShrinkPoolInterval());
        this.setStatisticalHistoryRecordCount(attributes.getStatisticalHistoryRecordCount());
        this.setURL(attributes.getURL());
        this.setUser(attributes.getUser());
        this.setVendor(attributes.getVendor());
        return this.compare(attributes);
    }

	/**
	 * Sets the SQL Query for validating the connection object.
	 * @param sqlquery to set
	 */
	public void setSqlQuery(String sqlquery) {
		this.sqlQuery_ = sqlquery;
		if ("1".equals(sqlquery)) {
			IllegalArgumentException e = new IllegalArgumentException("Invalid SQL Query");
			e.fillInStackTrace();
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Returns the SQL Query used for validating the connection object.
	 * @return the sqlquery
	 */
	public String getSqlQuery() {
		return sqlQuery_;
	}

}
