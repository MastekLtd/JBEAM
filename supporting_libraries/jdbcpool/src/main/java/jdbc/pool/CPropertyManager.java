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
 * $Revision: 7 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CPropertyManager.java 7     1/29/09 9:50p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CPropertyManager.java $
 * 
 * 7     1/29/09 9:50p Kedarr
 * The properties file input stream was left unclosed. Rectified the code.
 * 
 * 6     3/17/08 2:44p Kedarr
 * Added REVISION number
 * 
 * 5     3/17/08 1:13p Kedarr
 * Updated java doc. Implemented delete(..) method. Updated methods to add
 * syncrhonized clause.
 * 
 * 4     3/20/07 10:17a Kedarr
 * Changes made to read and store the new attribute.
 * 
 * 3     5/02/06 4:35p Kedarr
 * Changes made for adding a new attribute for Algorithm.
 * Updated javadoc.
 * 
 * 2     2/15/06 5:32p Kedarr
 * Changes made in the Javadoc.
 * 
 * 1     11/24/05 10:18a Kedarr
 * Extendes the abstract class CPoolAttributeManager and implements the
 * abstract methods. The purpose of this class is to manage properties
 * file.
 * 
*/
package jdbc.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

/**
 * Reads and Stores the pool configuration in a properties file. 
 * The property file structure is as defined below.
 * <table border="1" cellpadding="1" cellspacing="0">
 * <tr bgcolor="#C0C0C0">
 * <td><b>Property</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>driver</td>
 * <td>JDBC driver as provided by the Database Vendor</td>
 * </tr>
 * <tr>
 * <td>vendor</td>
 * <td>Database Vendor</td>
 * </tr>
 * <tr>
 * <td>url</td>
 * <td>URL to the database as required by the JDBC driver.</td>
 * </tr>
 * <tr>
 * <td>user</td>
 * <td>Database User Id.</td>
 * </tr>
 * <tr>
 * <td>password</td>
 * <td>Database password for the supplied user id.</td>
 * </tr>
 * <tr>
 * <td>initial-connections</td>
 * <td>Initial number of JDBC connection that the pool will keep alive through out the life cycle of the pool. </td>
 * </tr>
 * <tr>
 * <td>capacity-increament</td>
 * <td>The number of JDBC Connections that will be created if all the connections are in use. Pool will ensure that
 * the number of JDBC Connections will not cross the maximum capacity.
 * </td>
 * </tr>
 * <tr>
 * <td>maximum-capacity</td>
 * <td>The maximum capacity of the pool.</td>
 * </tr>
 * <tr>
 * <td>inactive-time-out</td>
 * <td>The physical JDBC connection will be closed if the idle time is greater than the inactive timeout. The pool will
 * not be shrinked for the initial number of JDBC connections. If the value specified is -1 then the connections will
 * not be poooled and that every time a new JDBC connection will be created and closed upon usage.
 * </td>
 * </tr>
 * <tr>
 * <td>shrink-pool-interval</td>
 * <td>This determines the pool shrink period. The pool will do a self check after the said interval and will close 
 * the idle connections if any.</td>
 * </tr>
 * <tr>
 * <td>critical-operation-time-limit</td>
 * <td>The time taken to execute the queries if crosses the critical opertaion time then all such queries are logged
 * as WARN(ing).</td>
 * </tr>
 * <tr>
 * <td>max-usage-per-jdbc-connection</td>
 * <td>The maximum usage per JDBC Connection. If the connection counter goes beyond this value the physical JDBC 
 * connection is closed by the pool.</td>
 * </tr>
 * <td>in-use-wait-time</td>
 * <td>The time the pool will wait before throwing an exception if the maximum capacity is reached and that there are 
 * no free connections in the pool.</td>
 * </tr>
 * <td>load-on-startup</td>
 * <td>The pool gets created upon startup of the pool whether there is a request to be serviced or not. If the value
 * is set to false then the pool gets created upon first invocation.</td>
 * </tr>
 * </table>
 * <br>
 * <table>
 * <tr><td>#defines all the pools separated with <b>;</b>.</td></tr>
 * <tr><td>pools=TSG;SUN3;</td></tr>
 * <tr><td>#for each pool defined above define the pool attribute</td></tr>
 * <tr><td>TSG.driver=oracle.jdbc.driver.OracleDriver</td></tr> 
 * <tr><td>TSG.vendor=ORACLE</td></tr>
 * <tr><td>TSG.url=jdbc:oracle:thin:@192.100.192.114:1521:SUN2DB1</td></tr> 
 * <tr><td>TSG.user=test</td></tr>
 * <tr><td>TSG.password=rendev</td></tr>
 * <tr><td>TSG.initial-connections=5</td></tr>
 * <tr><td>TSG.capacity-increament=1</td></tr>
 * <tr><td>TSG.maximum-capacity=5</td></tr>
 * <tr><td>TSG.inactive-time-out=6</td></tr>
 * <tr><td>TSG.shrink-pool-interval=1</td></tr>
 * <tr><td>TSG.critical-operation-time-limit=1000</td></tr>
 * <tr><td>TSG.max-usage-per-jdbc-connection=4</td></tr>
 * <tr><td>TSG.in-use-wait-time=7</td></tr>
 * <tr><td>TSG.load-on-startup=false</td></tr>
 * <tr><td> </td></tr>
 * <tr><td>SUN3.driver=com.mysql.jdbc.Driver</td></tr> 
 * <tr><td>SUN3.vendor=MySQL</td></tr>
 * <tr><td>SUN3.url=jdbc:mysql:@//localhost/pre</td></tr> 
 * <tr><td>SUN3.user=test</td></tr>
 * <tr><td>SUN3.password=useme</td></tr>
 * <tr><td>SUN3.initial-connections=5</td></tr>
 * <tr><td>SUN3.capacity-increament=1</td></tr>
 * <tr><td>SUN3.maximum-capacity=5</td></tr>
 * <tr><td>SUN3.inactive-time-out=6</td></tr>
 * <tr><td>SUN3.shrink-pool-interval=1</td></tr>
 * <tr><td>SUN3.critical-operation-time-limit=1000</td></tr>
 * <tr><td>SUN3.max-usage-per-jdbc-connection=4</td></tr>
 * <tr><td>SUN3.in-use-wait-time=7</td></tr>
 * <tr><td>SUN3.load-on-startup=true</td></tr>
 * </table>
 * @version $Revision: 7 $
 * @author kedarr
 * @since 13.01
 * 
 */
public class CPropertyManager extends CPoolAttributeManager {

    /**
     * Pool Properties.
     * Comment for <code>properties_</code>.
     */
    private Properties properties_;
    
    /**
     * Logger.
     * Comment for <code>logger_</code>.
     */
    private Logger logger_;

    /**
     * Constructs the Configuration Manager around a supplied properties file.
     * 
     * @param file Properties file 
     * @throws ConfigurationException 
     *
     */
    protected CPropertyManager(File file) throws ConfigurationException {
        super(file);
        properties_ = new Properties();
        logger_ = Logger.getLogger(this.getClass().getName());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            properties_.load(fis);
        } catch (FileNotFoundException e) {
            logger_.error("FileNotFoundException", e);
            throw new ConfigurationException("File not found");
        } catch (IOException e) {
            logger_.error("IOException", e);
            throw new ConfigurationException("IO exception");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //dummy.
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see jdbc.pool.CPoolAttributeManager#save(jdbc.pool.CPoolAttribute)
     */
    public synchronized boolean update(CPoolAttribute attribute) throws ConfigurationException {
        StringTokenizer tokenizer = new StringTokenizer(properties_.getProperty("pools"), ";");
        boolean bPoolConfigurationFound = false;
        while(tokenizer.hasMoreTokens()){
            if (tokenizer.nextToken().equals(attribute.getPoolName())){
                bPoolConfigurationFound = true;
                break;
            }
        }
        if (!bPoolConfigurationFound){
            throw new ConfigurationException("Pool Configuration does not exists. Could not update.");
        }
        String strPoolName = attribute.getPoolName();
        properties_.setProperty(strPoolName + ".driver", attribute.getDriver());
        properties_.setProperty(strPoolName + ".vendor", attribute.getVendor());
        properties_.setProperty(strPoolName + ".url", attribute.getURL());
        properties_.setProperty(strPoolName + ".user", attribute.getUser());
        properties_.setProperty(strPoolName + ".password", attribute.getPassword());
        properties_.setProperty(strPoolName + ".initial-connections", attribute.getInitialPoolSize() + "");
        properties_.setProperty(strPoolName + ".capacity-increament", attribute.getCapacityIncreament() + "");
        properties_.setProperty(strPoolName + ".maximum-capacity", attribute.getMaximumCapacity() + "");
        properties_.setProperty(strPoolName + ".inactive-time-out", attribute.getConnectionIdleTimeout() + "");
        properties_.setProperty(strPoolName + ".shrink-pool-interval", attribute.getShrinkPoolInterval() + "");
        properties_.setProperty(strPoolName + ".critical-operation-time-limit", attribute.getCriticalOperationTimeLimit() + "");
        properties_.setProperty(strPoolName + ".max-usage-per-jdbc-connection", attribute.getMaxUsagePerJDBCConnection() + "");
        properties_.setProperty(strPoolName + ".in-use-wait-time", attribute.getInUseWaitTime() + "");
        properties_.setProperty(strPoolName + ".load-on-startup", attribute.isLoadOnStartup() + ""); 
        properties_.setProperty(strPoolName + ".pool-algorithm", attribute.getPoolAlgorithm()); 
        properties_.setProperty(strPoolName + ".inmemory-statistics-history-size", attribute.getStatisticalHistoryRecordCount() + ""); 
        properties_.setProperty(strPoolName + ".sql-query", attribute.getSqlQuery() + ""); 
        
        return true;
    }

    /* (non-Javadoc)
     * @see jdbc.pool.CPoolAttributeManager#getAllPoolAttributes()
     */
    public Map<String, CPoolAttribute> getAllPoolAttributes() throws ConfigurationException {
        StringTokenizer tokenizer = new StringTokenizer(properties_.getProperty("pools"),";");
        Map<String, CPoolAttribute> map = Collections.synchronizedMap(new HashMap<String, CPoolAttribute>());
        while (tokenizer.hasMoreTokens()) {
            String strPoolName = tokenizer.nextToken();
            map.put(strPoolName, getPoolAttribute(strPoolName));
        }
        return map;
    }

    /* (non-Javadoc)
     * @see jdbc.pool.CPoolAttributeManager#getPoolAttribute(java.lang.String)
     */
    public CPoolAttribute getPoolAttribute(String strPoolName) throws ConfigurationException {
        CPoolAttribute attribute = new CPoolAttribute();
        if (logger_.isDebugEnabled()){
            logger_.debug("Loading properties for pool name #" + strPoolName);
        }
        attribute.setPoolName(strPoolName);
        attribute.setDriver(properties_.getProperty(strPoolName + ".driver"));
        attribute.setVendor(properties_.getProperty(strPoolName + ".vendor"));
        attribute.setURL(properties_.getProperty(strPoolName + ".url"));
        attribute.setUser(properties_.getProperty(strPoolName + ".user"));
        attribute.setPassword(properties_.getProperty(strPoolName + ".password"));
        attribute.setInitialPoolSize(Integer.parseInt(properties_.getProperty(strPoolName + ".initial-connections")));
        attribute.setCapacityIncreament(Integer.parseInt(properties_.getProperty(strPoolName + ".capacity-increament")));
        attribute.setMaximumCapacity(Integer.parseInt(properties_.getProperty(strPoolName + ".maximum-capacity")));
        attribute.setConnectionIdleTimeout(Integer.parseInt(properties_.getProperty(strPoolName + ".inactive-time-out")));
        attribute.setShrinkPoolInterval(Integer.parseInt(properties_.getProperty(strPoolName + ".shrink-pool-interval")));
        attribute.setCriticalOperationTimeLimit(Integer.parseInt(properties_.getProperty(strPoolName + ".critical-operation-time-limit")));
        int iMaxUsage = Integer.parseInt(properties_.getProperty(strPoolName + ".max-usage-per-jdbc-connection", "-1"));
        if (iMaxUsage < 2) {
            iMaxUsage = -1;
        }
        attribute.setMaxUsagePerJDBCConnection(iMaxUsage);

        try {
            attribute.setInUseWaitTime(Integer.parseInt(properties_.getProperty(strPoolName + ".in-use-wait-time")));
        } catch (NoSuchElementException e) {
            attribute.setInUseWaitTime(-1);
        }
        
        try {
            if (properties_.getProperty(strPoolName + ".load-on-startup", "false").equals("true")) {
                attribute.setLoadOnStartup(true);
            } else {
                attribute.setLoadOnStartup(false);
            }

        } catch (NoSuchElementException nsee) {
        }

        try {
            attribute.setPoolAlgorithm(properties_.getProperty(strPoolName + ".pool-algorithm"));
        } catch (NoSuchElementException e) {
            attribute.setPoolAlgorithm(IPool.FIFO_ALGORITHM);
            attribute.setToBeSaved(true);
        }

        try {
            int i = Integer.parseInt(properties_.getProperty(strPoolName + ".inmemory-statistics-history-size", "-1") );
            if (i == -1) {
                attribute.setToBeSaved(true);
            }
            attribute.setStatisticalHistoryRecordCount(i);
        } catch (NoSuchElementException e) {
            attribute.setStatisticalHistoryRecordCount(1);
            attribute.setToBeSaved(true);
        }
        
    	String sqlQuery = properties_.getProperty(strPoolName + ".sql-query", null);
    	if (sqlQuery == null) {
    		throw new ConfigurationException("sql-query attribute must be defined.");
    	}
    	attribute.setSqlQuery(sqlQuery);
        
        if (logger_.isDebugEnabled()){
            logger_.debug("Pool Attributes Read " + attribute);
        }
        return attribute;
    }

    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    public final static String REVISION = "$Revision:: 7         $";

    /* (non-Javadoc)
     * @see jdbc.pool.CPoolAttributeManager#create(jdbc.pool.CPoolAttribute)
     */
    public synchronized boolean create(CPoolAttribute attribute) throws ConfigurationException {
        StringTokenizer tokenizer = new StringTokenizer(properties_.getProperty("pools"), ";");
        boolean bPoolConfigurationFound = false;
        while(tokenizer.hasMoreTokens()){
            if (tokenizer.nextToken().equals(attribute.getPoolName())){
                bPoolConfigurationFound = true;
                break;
            }
        }
        if (bPoolConfigurationFound){
            throw new ConfigurationException("Pool Configuration already exists. Could not create.");
        }
        String strPools = properties_.getProperty("pools");
        if (!strPools.endsWith(";")){
            strPools = strPools + ";" + attribute.getPoolName();
        } else {
            strPools = strPools + attribute.getPoolName() + ";";
        }
        String strPoolName = attribute.getPoolName();
        properties_.setProperty("pools", strPools);
        properties_.setProperty(strPoolName + ".driver", attribute.getDriver());
        properties_.setProperty(strPoolName + ".vendor", attribute.getVendor());
        properties_.setProperty(strPoolName + ".url", attribute.getURL());
        properties_.setProperty(strPoolName + ".user", attribute.getUser());
        properties_.setProperty(strPoolName + ".password", attribute.getPassword());
        properties_.setProperty(strPoolName + ".initial-connections", attribute.getInitialPoolSize() + "");
        properties_.setProperty(strPoolName + ".capacity-increament", attribute.getCapacityIncreament() + "");
        properties_.setProperty(strPoolName + ".maximum-capacity", attribute.getMaximumCapacity() + "");
        properties_.setProperty(strPoolName + ".inactive-time-out", attribute.getConnectionIdleTimeout() + "");
        properties_.setProperty(strPoolName + ".shrink-pool-interval", attribute.getShrinkPoolInterval() + "");
        properties_.setProperty(strPoolName + ".critical-operation-time-limit", attribute.getCriticalOperationTimeLimit() + "");
        properties_.setProperty(strPoolName + ".max-usage-per-jdbc-connection", attribute.getMaxUsagePerJDBCConnection() + "");
        properties_.setProperty(strPoolName + ".in-use-wait-time", attribute.getInUseWaitTime() + "");
        properties_.setProperty(strPoolName + ".load-on-startup", attribute.isLoadOnStartup() + ""); 
        properties_.setProperty(strPoolName + ".pool-algorithm", attribute.getPoolAlgorithm()); 
        properties_.setProperty(strPoolName + ".inmemory-statistics-history-size", attribute.getStatisticalHistoryRecordCount() + ""); 
        properties_.setProperty(strPoolName + ".sql-query", attribute.getSqlQuery()); 
        
        return true;
    }

    /* (non-Javadoc)
     * @see jdbc.pool.CPoolAttributeManager#save()
     */
    public void save() throws ConfigurationException{
        if (logger_.isDebugEnabled()){
            logger_.debug("Saving the configuration to disk..");
        }
        try {
            PrintWriter pwOut = new PrintWriter(new FileWriter(getConfigurationFile()), true);
            writeTemplate(pwOut, getConfigurationFile().getName());
            StringTokenizer tokenizer = new StringTokenizer(properties_.getProperty("pools"), ";");
            pwOut.println("");
            pwOut.println("pools=" + properties_.getProperty("pools"));
            pwOut.println("");
            while (tokenizer.hasMoreTokens()) {
                String strPoolName = tokenizer.nextToken();
                pwOut.println(strPoolName + ".driver=" + properties_.getProperty(strPoolName + ".driver"));
                pwOut.println(strPoolName + ".vendor=" + properties_.getProperty(strPoolName + ".vendor"));
                pwOut.println(strPoolName + ".url=" + properties_.getProperty(strPoolName + ".url"));
                pwOut.println(strPoolName + ".user=" + properties_.getProperty(strPoolName + ".user"));
                pwOut.println(strPoolName + ".password=" + properties_.getProperty(strPoolName + ".password"));
                pwOut.println(strPoolName + ".initial-connections=" + properties_.getProperty(strPoolName + ".initial-connections"));
                pwOut.println(strPoolName + ".capacity-increament=" + properties_.getProperty(strPoolName + ".capacity-increament"));
                pwOut.println(strPoolName + ".maximum-capacity=" + properties_.getProperty(strPoolName + ".maximum-capacity"));
                pwOut.println(strPoolName + ".inactive-time-out=" + properties_.getProperty(strPoolName + ".inactive-time-out"));
                pwOut.println(strPoolName + ".shrink-pool-interval=" + properties_.getProperty(strPoolName + ".shrink-pool-interval"));
                pwOut.println(strPoolName + ".critical-operation-time-limit=" + properties_.getProperty(strPoolName + ".critical-operation-time-limit"));
                pwOut.println(strPoolName + ".max-usage-per-jdbc-connection=" + properties_.getProperty(strPoolName + ".max-usage-per-jdbc-connection"));
                pwOut.println(strPoolName + ".in-use-wait-time=" + properties_.getProperty(strPoolName + ".in-use-wait-time"));
                pwOut.println(strPoolName + ".load-on-startup=" + properties_.getProperty(strPoolName + ".load-on-startup"));
                pwOut.println(strPoolName + ".pool-algorithm=" + properties_.getProperty(strPoolName + ".pool-algorithm"));
                pwOut.println(strPoolName + ".inmemory-statistics-history-size=" + properties_.getProperty(strPoolName + ".inmemory-statistics-history-size"));
                pwOut.println(strPoolName + ".sql-query=" + properties_.getProperty(strPoolName + ".sql-query"));
                pwOut.println("");
            }
        } catch (IOException e) {
            logger_.error("IOException ", e);
            throw new ConfigurationException("Unable to save the file. IOException");
        }
        if (logger_.isDebugEnabled()){
            logger_.debug("Configuration saved to disk..");
        }
    }
    
    /**
     * Writes the header of the property file.
     *
     * @param out PrintWriter
     * @param filename Name of the property file.
     */
    private void writeTemplate(PrintWriter out, String filename){
        out.print("# Product Name: \"");
        out.print(CConnectionPoolManager.VERSION[0]);
        out.print("\" Version: \"");
        out.print(CConnectionPoolManager.VERSION[1]);
        out.print("\" Bundled-On \"");
        out.print(CConnectionPoolManager.VERSION[2]);
        out.println("\"");
        out.println("#--Last updated on: " + new Date());
        out.println("# If your application is in use, please do not edit the " + filename + " file.");
        out.println("# Any changes made to that file while the application is active will not have any effect");
        out.println("# on the Pool's configuration and are likely to be lost. If your application is inactive,");
        out.println("# you may edit this file with an PROPERTIES editor. If you do so, please refer to the JDBC Pools");
        out.println("# documentation.");
        out.println("");
    }

    /* (non-Javadoc)
     * @see jdbc.pool.CPoolAttributeManager#delete(java.lang.String)
     */
    public synchronized boolean delete(String strPoolName) throws ConfigurationException {
        
        StringTokenizer tokenizer = new StringTokenizer(properties_.getProperty("pools"), ";");
        
        boolean bPoolConfigurationFound = false;
        
        while(tokenizer.hasMoreTokens()){
            if (tokenizer.nextToken().equals(strPoolName)){
                bPoolConfigurationFound = true;
                break;
            }
        }
        if (!bPoolConfigurationFound) {
            throw new ConfigurationException("Pool Configuration not found.");
        }
        
        String strPools_ = properties_.getProperty("pools").replaceAll(strPoolName + ";", "");
        properties_.setProperty("pools", strPools_);
        properties_.remove(strPoolName + ".driver");
        properties_.remove(strPoolName + ".vendor");
        properties_.remove(strPoolName + ".url");
        properties_.remove(strPoolName + ".user");
        properties_.remove(strPoolName + ".password");
        properties_.remove(strPoolName + ".initial-connections");
        properties_.remove(strPoolName + ".capacity-increament");
        properties_.remove(strPoolName + ".maximum-capacity");
        properties_.remove(strPoolName + ".inactive-time-out");
        properties_.remove(strPoolName + ".shrink-pool-interval");
        properties_.remove(strPoolName + ".critical-operation-time-limit");
        properties_.remove(strPoolName + ".max-usage-per-jdbc-connection");
        properties_.remove(strPoolName + ".in-use-wait-time");
        properties_.remove(strPoolName + ".load-on-startup"); 
        properties_.remove(strPoolName + ".pool-algorithm"); 
        properties_.remove(strPoolName + ".inmemory-statistics-history-size");
        properties_.remove(strPoolName + ".sql-query");
        save();
        return true;
    }
    
}
