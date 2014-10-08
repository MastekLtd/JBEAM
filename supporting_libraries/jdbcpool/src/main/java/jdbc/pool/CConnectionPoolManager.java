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
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CConnectionPoolManager.java 36    11/13/09 11:58a Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CConnectionPoolManager.java $
 * 
 * 36    11/13/09 11:58a Kedarr
 * Changes made to identify the existance of the config pool file and
 * whether it is not a directory.
 * 
 * 35    8/28/09 11:38a Kedarr
 * Implemented the data source get methods.
 * 
 * 34    1/29/09 9:56p Kedarr
 * Added new functionality that forces the pool to shutdown also to change
 * attributes on the fly.
 * 
 * 33    1/26/09 5:33p Kedarr
 * Functionality to forecfully destroy an in-use pool implemented.
 * 
 * 32    3/17/08 3:15p Kedarr
 * Added a delete method to delete a pool configuration from the file.
 * Updated javadoc.
 * 
 * 31    3/20/07 10:22a Kedarr
 * New API added for retrieving statistics collection history and "now"
 * running queries.
 * 
 * 30    3/02/07 10:07a Kedarr
 * Added / modified some debug messages.
 * 
 * 29    5/02/06 4:47p Kedarr
 * Removed the shut-down-hook implementation from the Pool. It was
 * observed that the pool was being used by another thread and the
 * shut-down-hook was executed in parallel thus this thread always used to
 * throw PoolInUseException.
 * 
 * 28    2/27/06 1:45p Kedarr
 * Changes made in javadoc.
 * 
 * 27    2/23/06 12:45p Kedarr
 * Added a destroy method that makes the instance to null so that one can
 * again create the instance. The getPoolAttribute() method has been
 * changed to not set the password to null. Also, mincor changes are done
 * for shutdown hook.
 * 
 * 26    12/06/05 11:31a Kedarr
 * Changes made to the Main method. Also, now it is not necessary to
 * initialize the log4j logger by the pool itself. One can pass a null
 * value in the method getInstance(..) for log4j properties file. It is
 * important that before giving a call to this getInstance() please have
 * the log4j initialized and ready for use.
 * 
 * 25    11/24/05 12:35p Kedarr
 * Changed to display the Bundled-On date along with the Version
 * 
 * 24    11/24/05 10:15a Kedarr
 * Changes made to remove the logic of Reading and Storing the Pool
 * Attributes in the configuration file and the implementation is left to
 * class CPoolAttributeManager. Also, updated Javadoc.
 * 
 * 23    11/11/05 12:44p Kedarr
 * Changes made for logging.
 * 
 * 22    11/09/05 4:11p Kedarr
 * Changes made to save the XML configuration file if the file does not
 * have the element max-usage-per-jdbc-connection.
 * 
 * 21    11/09/05 4:04p Kedarr
 * Changes made to read and write the XML configuration tag
 * "max-usage-per-jdbc-connection". Updated few log messages.
 * 
 * 20    10/17/05 12:15p Kedarr
 * Re-Added Shutdown Hook. Added synchornized to methods like
 * createPool(..) also updated the javadoc.
 * 
 * 19    9/14/05 1:28p Kedarr
 * Changes made to uncomment the shut down hook that was commented in
 * previous version.
 * 
 * 18    9/05/05 10:29a Kedarr
 * Changes made for adding additional fatal messages and removed shutdown
 * hook.
 * 
 * 17    8/30/05 12:05p Kedarr
 * Changes made to encrypt and decrypt the password and store the same in
 * the XML Configuration file.
 * 
 * 16    8/29/05 4:21p Kedarr
 * Object is cloned so that even if the values are changed  in the outer
 * class the same will not be reflected in the pool.
 * 
 * 15    8/29/05 1:43p Kedarr
 * Changes made to add a new pool and to modify exsting pool. This will
 * save the configuration of these pools to XML Configuration file.
 * 
 * 14    7/29/05 3:37p Kedarr
 * Updated for JavaDoc for missing tags. Added a runtime shutdown hook to
 * close all the pools.
 * 
 * 13    7/20/05 11:01a Kedarr
 * Changed for Wait Time when All In Use.
 * 
 * 12    7/07/05 11:48a Kedarr
 * Updated JavaDoc.
 * 
 * 11    7/07/05 11:43a Kedarr
 * Changes made for not creating a pooled connection if the connection
 * inactivity time out interval is less than or equal to zero.
 * 
 * 10    6/30/05 11:08a Kedarr
 * Change made in the fatal message to display the pool name.
 * 
 * 9     6/15/05 11:01a Kedarr
 * Changes made for catching exception while emptying pool in
 * emptyAllPools() method. The pool will remain alive till all the
 * connections are in use. The Manager will attempt to close all other
 * pools.
 * 
 * 8     6/06/05 6:16p Kedarr
 * Changes made to enable Log4J
 * 
 * 7     5/20/05 11:40a Kedarr
 * Resolved bug. If only one pool was defined in the configuration file
 * then the Pool was throwing exception. This is now resolved. Corrections
 * are made to throw a valid exception if the configuration does not
 * reflect the right configuration.
 * 
 * 6     5/17/05 2:12p Kedarr
 * Changed the method emptyAllPools(). It was giving concurrent
 * modification error for Hashmap values. As emptyAllPools was internally
 * calling emptyPool(String) and in emptyPool we were removing the pool
 * from the hashmap. This should have been done as iterator.remove.
 * 
 * 5     5/17/05 10:58a Kedarr
 * Changes made for Javadoc and some changes in the main method.
 * 
 * 4     5/16/05 11:54a Kedarr
 * Major Change.
 * 
 * Changes made to read xml file.
 * 
 * 3     5/09/05 6:11p Kedarr
 * Added a Version Printing message at the start of the pool.
 * 
 * 2     5/09/05 5:16p Kedarr
 * Forcefully emptying of pools is removed. Instead the pool will throw
 * SQLException if the pool is in use while emptying.
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
 * Revision 1.1  2006/03/01 16:06:46  kedar
 * JDBC POOL classes
 *
 * Revision 1.4  2004/05/03 06:28:43  kedar
 * Un used method/variables commented out.
 *
 * 
 * 2     2/09/04 11:00a Kedarr
 * Changes made to CSettings.get(String, String) has been incorporated in
 * this class.
 * Revision 1.3  2004/02/06 10:40:51  kedar
 * Changes made to CSettings.get(String, String) has been incorporated 
 * in this class.
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
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 9/19/03    Time: 10:08a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Organising Imports
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 12/06/03   Time: 2:30p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Drastically changed. This change was made as the Engine wanted 2 pools.
 * The Pool Manager now can handle multiple pools unlike before.
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/database
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:47p
 * Created in $/ProcessRequestEngine/gmac/database
 * Initial Version
 *
 */

package jdbc.pool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.stg.logger.LogLevel;

/**
 * Represents a JDBC Connection Pool manager.
 * 
 * This class is based on the Singleton Factory Design Pattern. In a namespace only one instance of this class
 * can be instantiated. This class will manage all the JDBC Pools and is responsible to create pools and destroy
 * pools.
 */
public class CConnectionPoolManager
{

    /**
     * Stores the revision number of the code.
     * Comment for <code>REVISION</code>
     */
    public static final String REVISION = "$Revision: 36 $";

    /**
     * Pools created by this Pool manager are stored in this variable. Comment
     * for <code>mapPools_</code>
     */
    private Map<String, ConnectionPool> mapPools_ = Collections.synchronizedMap(new HashMap<String, ConnectionPool>());
    
    /**
     * DataSource map.
     */
    private Map<String, JDBCDataSource> mapDataSource_ = Collections.synchronizedMap(new HashMap<String, JDBCDataSource>());
    
    
    /**
     * Stores the pool attributes in a map.
     * Comment for <code>myPoolAttributes_</code>
     */
    private Map<String, CPoolAttribute> myPoolAttributes_ = null;

    /**
     * Pool Attribute manager responsible to read and store the configuration files.
     * Comment for <code>poolAttributeManager_</code>.
     */
    private CPoolAttributeManager poolAttributeManager_;

//    /**
//     * Shut down hook that is added in the Runtime instance.
//     * Comment for <code>shutDownHookThread</code>.
//     */
//    private Thread shutDownHookThread;

    /**
     * Pool Manager instance. Comment for <code>instance_</code>
     */
    private static CConnectionPoolManager instance_;

    /**
     * Logger instance. Comment for <code>logger_</code>
     */
    private static Logger logger_ = Logger.getLogger("PoolManager");
    
    /**
     * Stores the version number of the pool.
     */
    final static String[] VERSION = new String[] {"Unknown", "Unknown", "Unknown"};

    /**
     * Private constructor is provided so that this class cannot be instantiated without its
     * {@link #getInstance(String, File)} method.
     * 
     * @param log4jConfigFile Log4j.properties file. If null the property is not read but assumes that log4j is initialized and ready for use.
     * @param configurationFile Pool Configuration  file, either .properties or .xml
     * 
     * @throws ParseException
     * @throws ConfigurationException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private CConnectionPoolManager(String log4jConfigFile, File configurationFile) throws ParseException, ConfigurationException, IOException, SQLException, ClassNotFoundException
    {
        if (log4jConfigFile != null){
            PropertyConfigurator.configureAndWatch(log4jConfigFile);
        }
        try {
            printVersion();
        } catch (FileNotFoundException e) {
            //do nothing 
        } catch (IOException e) {
            //do nothing..
        }
        if (configurationFile.isDirectory()) {
        	throw new IllegalArgumentException("Configuration File cannot be a directory #" + configurationFile.getPath());
        }
        if (!configurationFile.exists()) {
        	throw new IllegalArgumentException("Configuration File does not exists #" + configurationFile.getPath());
        }
        poolAttributeManager_ = CPoolAttributeManager.getPoolAttributeManager(configurationFile);
        myPoolAttributes_ = poolAttributeManager_.getAllPoolAttributes();
        logger_.info("Intializing Pool Manager...");
        logger_.info("Creating start up Pools...");
        for (CPoolAttribute attribute : myPoolAttributes_.values()) {
            if (attribute.isToBeSaved()){
                try {
                    updatePoolAttributes(attribute, false);
                    attribute = myPoolAttributes_.get(attribute.getPoolName());
                } catch (ConfigurationException e) {
                    e.printStackTrace();
                } catch (InvalidPoolAttributeException e) {
                    e.printStackTrace();
                }
            } else {
                myPoolAttributes_.put(attribute.getPoolName(), attribute);
            }
            if (logger_.isDebugEnabled()){
                logger_.debug("Pool Attributes loaded " + attribute);
            }
            if (attribute.isLoadOnStartup()){
                createPool(attribute);
            }
        } //end for
//        boolean bShutDownHook = false;
//        Method[] methods = Runtime.getRuntime().getClass().getDeclaredMethods();
//        for (int i = 0; i < methods.length; i++) {
//            Method method = methods[i];
//            if (method.getName().equals("addShutdownHook")){
//                bShutDownHook = true;
//                break;
//            }
//        }
//        if (bShutDownHook){
//            shutDownHookThread = new Thread(){
//                /* (non-Javadoc)
//                 * @see java.lang.Thread#run()
//                 */
//                public void run() {
//                    logger_.warn("Initiating Pool Shutdown from Shut Down Hook. Such a shut down should not happen within server life cycle.");
//                    while (true){
//                        if (!emptyAllPools()){
//                            try {
//                                sleep(1000);
//                            } catch (InterruptedException e) {
//                            }
//                        } else {
//                            break;
//                        }
//                    }
//                    logger_.log(LogLevel.NOTICE, "Pool Shutdown successful.");
//                } 
//            };
//            Runtime.getRuntime().addShutdownHook(shutDownHookThread);
//        }
        logger_.log(LogLevel.NOTICE, "Pool Manager created...");
    }

    /**
     * Returns the JDBC Connection from the specified pool.
     * 
     * If the pool is not created then this method will try to create the specified pool. If the pool could not be 
     * created then a null is returned.
     * 
     * @param pstrPoolName Pool Name.
     * @return ConnectionPool object
     * @throws ClassNotFoundException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public Connection getConnection(String pstrPoolName) throws ClassNotFoundException, ClassNotFoundException, IOException, SQLException
    {
        if (logger_.isDebugEnabled()){
            logger_.debug("Getting connection from Pool #" + pstrPoolName);
        }
        try {
            return mapPools_.get(pstrPoolName).getConnection();
        } catch (NullPointerException e) {
            if (!mapPools_.containsKey(pstrPoolName)){
                if (myPoolAttributes_.containsKey(pstrPoolName)){
//                    synchronized (this) { //This is not required as createPool method in itself is synchronized
                        createPool(myPoolAttributes_.get(pstrPoolName));
//                    }
                    return getConnection(pstrPoolName);
                }
                logger_.fatal("Pool Attributes Not defined or not loaded.");
            }
            logger_.fatal("Pool Does not exists in the configuration file");
        }
        return null;
    }

    /**
     * Releases the connection back to the pool. 
     * 
     * This method is provided for backward compatibility and the programmers are adivsed 
     * to use the close() method on the JDBC Connection object. Internally this method does
     * <code>con.close()</code>. 
     * 
     * @param con
     *            Connection
     * @throws IOException
     * @throws SQLException
     */
    public void releaseConnection(Connection con) throws IOException,
            SQLException
    {
        if (logger_.isDebugEnabled()) {
            logger_.debug("Connection released to the pool");
        }
        con.close();
    }

    /**
     * Destroys the specified pool.
     * 
     * @param strPoolName Name of the pool.
     * @param bForceShutdown True to force-fuly shutdown the pool.
     * @throws SQLException If the pool is being used.
     */
    public synchronized void emptyPool(String strPoolName, boolean bForceShutdown) throws SQLException
    {
        if (!mapPools_.containsKey(strPoolName)) {
            logger_.warn("Unrecognized pool or pool already emptyied.");
        }
        else {
	        ConnectionPool pool = (ConnectionPool) mapPools_.get(strPoolName);
	        if (logger_.isInfoEnabled()) {
		        logger_.info("Emptying pool " + pool.getPoolName());
	        } 
	        pool.emptyPool(bForceShutdown);
	        mapPools_.remove(strPoolName);
	        mapDataSource_.remove(strPoolName);
        }
    }

    /**
     * Destroys all the pools created by the Manager.
     * 
     * It is advised to call this method till this method returns true. It may be possible that the JDBC
     * connection is still in use (may be leaked therefore till the Garbage Collection happens it is still
     * in use) and then the Manager will return false.
     * @param bForceShutdown True to force shutdown.
     * @return boolean True if all pools are emptied.
     */
    public synchronized boolean emptyAllPools(boolean bForceShutdown)
    {
        boolean bReturn = true;
        if (mapPools_.size() < 1) {
            if (logger_.isInfoEnabled()){
                logger_.info("Pool(s) already emptied or Pools(s) not created.");
            }
            return true;
        }
        String[] poolNamesArray = new String[mapPools_.size()];
        poolNamesArray = (String[]) mapPools_.keySet().toArray(poolNamesArray);
        for (int i = 0; i < poolNamesArray.length; i++) {
            try {
                emptyPool(poolNamesArray[i], bForceShutdown);
            } catch (Exception e) {
                logger_.fatal("Unable to empty pool \"" + poolNamesArray[i] + "\" due to:", e);
                bReturn = false;
            }
        }
        if (logger_.isInfoEnabled()) {
            logger_.info("All pools emptyied.");
        }
        return bReturn;
    }

    /**
     * Returns the Pool Manager instance.
     * 
     * @return CConnectionPoolManager
     */
    public static CConnectionPoolManager getInstance()
    {
        if (instance_ == null)
        {
            throw new IllegalArgumentException(
                    "Pool Manager Not Initialized Properly.");
        }
        return instance_;
    }

    /**
     * Constructs the Pool Manager by reading the configuration xml file.
     * <b>log4<i>J</i></b> configuration file name can be passed as null and the pool assumes that
     * the logger is initialized and ready for usage.
     * 
     * @param log4JConfigFile Log4J.properties file.
     * @param configurationFile Configuration XML file.
     * @return instance of the CConnectionPoolManager
     * 
     * @throws ConfigurationException
     * @throws ParseException
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public synchronized static CConnectionPoolManager getInstance(String log4JConfigFile, File configurationFile) throws ConfigurationException, ParseException, IOException, SQLException, ClassNotFoundException
    {
        if (instance_ == null)
        {
            instance_ = new CConnectionPoolManager(log4JConfigFile, configurationFile);
        }
        return (instance_);
    }
    
    

    /**
     * Returns the Pool Statistics for the specified pool.
     * 
     * @param strPoolName Name of the pool
     * @throws NullPointerException if the pool is not created.
     * @return CPoolStatisticsBean
     */
    public CPoolStatisticsBean getPoolStatistics(String strPoolName)
    {
        return (((ConnectionPool) mapPools_.get(strPoolName)).getStatistics());
    }

    /**
     * Returns an iterator for a collection of statistics for all the pools.
     * 
     * @return Iterator
     */
    public Iterator<CPoolStatisticsBean> getAllPoolStatistics() {
        ArrayList<CPoolStatisticsBean> alist = new ArrayList<CPoolStatisticsBean>(mapPools_.size());
        for (Entry<String, ConnectionPool> entry : mapPools_.entrySet()) {
        	alist.add(entry.getValue().getStatistics());
        }
        return alist.iterator();
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable
    {
        if (logger_.isInfoEnabled()) {
            logger_.info("Finalizer call to destroy initiated.");
        }
        destroy(true);
        super.finalize();
    }

    /**
     * Main method given for test purpose.
     * Accepts two arguments. One is the configuration file along with path and
     * other is the pool name that will be used to get connections from the pool.
     * 
     * @param args String[] 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        if (args.length < 3){
            System.out.println("usage CConnectionPoolManager <log4jconfigfile> <pool configuration> <pool name>");
            System.out.println("where log4j configuration file along with physical path"); 
            System.out.println("pool configuration is the configuration xml or properties file along with the physical path");
            System.out.println("pool name will be used to get connections from the pool defined in the pool configuration file.");
            return;
        }
        CConnectionPoolManager manager = CConnectionPoolManager.getInstance(args[0], (new File(args[1])));
        
        Connection con1 = manager.getConnection("MYSQL");
        Connection con2 = manager.getConnection("MYSQL");
        Connection con3 = manager.getConnection("MYSQL");
        Connection con4 = manager.getConnection("MYSQL");
        Connection con5 = manager.getConnection("MYSQL");
        long time = System.currentTimeMillis();
        try {
            manager.getConnection("MYSQL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(System.currentTimeMillis() - time);
        con1.close();
        con2.close();
        con3.close();
        con4.close();
        con5.close();
        
//                for (int i = 0; i < 4; i++)
//                {
//                    Connection con1 = manager.getConnection(args[2]);
//                    con1.close();
//                }
                manager.emptyAllPools(true);
    }
    
//    private URL getURL() {
//
//        this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
//        try {
//            Enumeration enumeration = this.getClass().getClassLoader().getResources("CConnectionPoolManager.class");
//            while (enumeration.hasMoreElements()) {
//                URL url = (URL) enumeration.nextElement();
//                if (url.getFile().endsWith("jdbc/pool/CConnectionPoolManager.class")) {
//                   return new URL(url.getFile().substring(0, url.getFile().lastIndexOf(".jar")+4)); 
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    
    /**
     * Stores the version number array of the JDBCPool in the variable {@link #VERSION}.
     * 
     * String[0] element is the product name.
     * String[1] element is the version number.
     * String[2] element is the date on which the JDBCPool was released.
     * 
     */
    private void getVersion() {
        String localFile = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        localFile = localFile.concat("!/");
        String tmpString = "jar:";
        String localJarFileString = tmpString.concat(localFile);
        URL localJarFileURL;
        try {
            localJarFileURL = new URL(localJarFileString);
            JarURLConnection localJarFile = (JarURLConnection) localJarFileURL.openConnection();
            Manifest mf = localJarFile.getManifest();
            Attributes attributes = mf.getMainAttributes();
            VERSION[0] = (String) attributes.getValue("Bundle-Name");
            VERSION[1] = (String) attributes.getValue("Bundle-Version");
            VERSION[2] = (String) attributes.getValue("Bundled-On");
        } catch (MalformedURLException e) {
            //do nothing
        } catch (IOException e) {
            //do nothing
        }

//        URL url = getURL();
//        if (url != null)
//        {
//            try {
//                String file = url.getFile();
//                if (file.lastIndexOf(".jar") > -1) {
//                    url = new URL(file.substring(0, file.lastIndexOf(".jar")+4));
//                } else {
//                    logger_.warn("Illegal Execution. Execution is not as distributed.");
//                    return;
//                }
//                JarInputStream jis = null;
//                try {
//                    jis = new JarInputStream(url.openStream());
//                    Manifest manifest = jis.getManifest();
//                    Attributes attributes = manifest.getMainAttributes();
//                    VERSION[0] = (String) attributes.getValue("Bundle-Name");
//                    VERSION[1] = (String) attributes.getValue("Bundle-Version");
//                    VERSION[2] = (String) attributes.getValue("Bundled-On");
//                } finally {
//                    if (jis != null) {
//                        jis.close();
//                    }
//                }
//            } catch (Exception e) {
//                //Do nothing
//            }
//            return;
//        } else {
//            logger_.debug("Unable to find the class.");
//            logger_.warn("Illegal Execution. Execution is not as distributed.");
//        }
//        return;
    }
    
    /**
     * Prints the version of the JDBCPool.
     * 
     * @since 06.01.001
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void printVersion() throws FileNotFoundException, IOException
    {
        StringBuffer sbuffer = new StringBuffer(100);
        getVersion();
        sbuffer.append("Product Name: \"");
        sbuffer.append(VERSION[0]);
        sbuffer.append("\" Version: \"");
        sbuffer.append(VERSION[1]);
        sbuffer.append("\" Bundled-On \"");
        sbuffer.append(VERSION[2]);
        sbuffer.append("\"");
        logger_.log(LogLevel.NOTICE, sbuffer.toString());
    }
    
    /**
     * Creates the pool with the given attributes.
     * 
     * @param attribute Attributes of the pool.
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @since 12.00.001 
     */
    private synchronized void createPool(CPoolAttribute attribute) throws IOException, SQLException, ClassNotFoundException{
        if (!mapPools_.containsKey(attribute.getPoolName()))
        {
            if (logger_.isInfoEnabled()) {
                logger_.info("Creating Pool " + attribute.getPoolName());
            }
            ConnectionPool pool = new ConnectionPool(attribute, this);
            pool.initializePool();
            mapPools_.put(attribute.getPoolName(), pool);
            mapDataSource_.put(attribute.getPoolName(), new JDBCDataSource(pool));
        }
    }
    
    /**
     * Returns the pool attributes for the specified pool.
     * 
     * If the pool is not found in the configurations then the method returns null.
     * 
     * @param pstrPoolName Name of the pool.
     * @return CPoolAttribute
     * @since 12.00.001
     */
    public CPoolAttribute getPoolAttributes(String pstrPoolName){
        if (myPoolAttributes_.containsKey(pstrPoolName)){
            CPoolAttribute attrib = null;
            try {
                attrib = (CPoolAttribute) ((CPoolAttribute) myPoolAttributes_
                        .get(pstrPoolName)).clone();
//                attrib.setPassword(null);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return attrib;
        }
        return null;
    }
    
    /**
     * The underlying pool will invoke this method after it destroys itself.
     * 
     * @param pstrPoolName Pool Name
     */
    protected synchronized void poolDestroyed(String pstrPoolName){
        if (mapPools_.containsKey(pstrPoolName)){
            if (logger_.isInfoEnabled()){
                logger_.info("Pool Destroyed \"" + pstrPoolName + "\".");
            }
            mapPools_.remove(pstrPoolName);
            mapDataSource_.remove(pstrPoolName);
        }
    }
    
    /**
     * Updates the attributes of an existing pool.
     * 
     * The <code>applyNow</code> will determine if the existing pool is to be bounced or not.
     * True to apply during runtime. This will forcefully shutdown the pool and re-initialize. So any program
     * working on a particular connection object acquired before the call to this methods will result into SQLException. 
     *
     * @param pattribute new attributes.
     * @param applyNow true to apply the attributes on an existing pool.
     * @throws ConfigurationException
     * @throws InvalidPoolAttributeException If an invalid attribute is specified.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @since 12.00.001 
     */
    public synchronized void updatePoolAttributes(CPoolAttribute pattribute, boolean applyNow) throws ConfigurationException, InvalidPoolAttributeException, SQLException, IOException, ClassNotFoundException {
        CPoolAttribute attribute = null;
        try {
            attribute = (CPoolAttribute) pattribute.clone();
        } catch (CloneNotSupportedException e) {
            logger_.error("Unable to store the atrributes. Attributes will be loaded only after re-start.");
            // CPoolAttribute supports clone.
        }
        if (logger_.isDebugEnabled()){
            logger_.debug("Validating pool attributes...");
        }
        checkPoolAttributes(attribute);
        poolAttributeManager_.update(attribute);
        poolAttributeManager_.save();
        if (applyNow) {
            logger_.info("Appling attributes @ runtime.");
            try {
                myPoolAttributes_.put(attribute.getPoolName(), (CPoolAttribute) attribute.clone());
            } catch (CloneNotSupportedException e) {
                logger_.error("Unable to store the atrributes. Attributes will be loaded only after re-start.");
            }
            logger_.info("Attributes loaded.");
            if (mapPools_.containsKey(attribute.getPoolName())){
                    logger_.info("Modifying the pool");
                ConnectionPool pool = (ConnectionPool) mapPools_.get(attribute.getPoolName());
                pool.changeAttributes(attribute);
            }
        }
    }
    
    /**
     * Update pool attributes for the given set.
     *
     * @param attributes Array of CPoolAttrubute
     * @throws ConfigurationException
     * @throws InvalidPoolAttributeException
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @since 12.00.001 
     */
    public synchronized void updatePoolAttributes(CPoolAttribute[] attributes, boolean applyNow) throws ConfigurationException, InvalidPoolAttributeException, SQLException, IOException, ClassNotFoundException{
        for (int i = 0; i < attributes.length; i++) {
            updatePoolAttributes(attributes[i], applyNow);
        }
    }
    
    /**
     * Creates a new pool based on the Pool Attributes passed and stores them in xml configuration file.
     * 
     * A new pool is created if the configuration is not defined in the xml file. 
     * 
     * @param pattribute Pool
     * @throws ConfigurationException If the pool exists in the xml configuration file or any other exception caused while addind or saving.
     * @throws InvalidPoolAttributeException If the attribute validation fails.
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @throws IOException 
     * @since 12.00.001 
     */
    public synchronized void addNewPool(CPoolAttribute pattribute) throws ConfigurationException, InvalidPoolAttributeException, IOException, SQLException, ClassNotFoundException{
        CPoolAttribute attribute = null;
        try {
            attribute = (CPoolAttribute) pattribute.clone();
        } catch (CloneNotSupportedException e1) {
            logger_.error("Unable to store the atrributes. Attributes will be loaded only after re-start.");
            // CPoolAttribute supports clone.
        }
        if (logger_.isDebugEnabled()){
            logger_.debug("Validating pool attributes");
        }
        checkPoolAttributes(attribute);
        if (logger_.isDebugEnabled()){
            logger_.debug("Pool Attributes Found Ok.");
            logger_.debug("Creating new pool #" + attribute.getPoolName());
        }
        poolAttributeManager_.create(attribute);
        poolAttributeManager_.save();
        if (logger_.isInfoEnabled()){
            logger_.info("Loading attributes..");
        }
        myPoolAttributes_.put(attribute.getPoolName(), attribute);
        if (logger_.isInfoEnabled()){
            logger_.info("Attributes loaded..");
        }
        if (attribute.isLoadOnStartup()){
            createPool(attribute);
        }
    }
    
    /**
     * Creates new pools based on the Pool Attributes passed and stores them in xml configuration file.
     *
     * Internally gives a call to {@link #addNewPool(CPoolAttribute)}.
     *
     * @param attributes Array of Pools
     * @throws ConfigurationException
     * @throws InvalidPoolAttributeException
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @since 12.00.001 
     */
    public synchronized void addNewPools(CPoolAttribute[] attributes) throws ConfigurationException, InvalidPoolAttributeException, IOException, SQLException, ClassNotFoundException{
        for (int i = 0; i < attributes.length; i++) {
            addNewPool(attributes[i]);
        }
    }

    /**
     * Validates the attributes of a pool.
     *
     * This method might throw a NullPointerException if any attribute value is found to be null.
     * 
     * @param attribute Pool to be validated.
     * @throws InvalidPoolAttributeException 
     * @since 12.00.001 
     */
    private void checkPoolAttributes(CPoolAttribute attribute) throws InvalidPoolAttributeException {
        if (attribute.getPoolName().equals("")){
            throw new InvalidPoolAttributeException("Pool Name");
        }
        if (attribute.getDriver().equals("")){
            throw new InvalidPoolAttributeException("Driver");
        }
        if (attribute.getVendor().equals("")){
            throw new InvalidPoolAttributeException("Vendor");
        }
        if (attribute.getURL().equals("")){
            throw new InvalidPoolAttributeException("URL");
        }
        if (attribute.getUser().equals("")){
            throw new InvalidPoolAttributeException("User");
        }
        if (attribute.getPassword() == null || attribute.getPassword().equals("")){
            throw new InvalidPoolAttributeException("Password");
        }
        if (attribute.getInitialPoolSize() < 0){
            throw new InvalidPoolAttributeException("Initial Pool Size");
        }
        if (attribute.getCapacityIncreament() <= 0){
            throw new InvalidPoolAttributeException("Capacity Increament");
        }
        if (attribute.getMaximumCapacity() <= 0){
            throw new InvalidPoolAttributeException("Maximum Capacity");
        }
        if (attribute.getCriticalOperationTimeLimit() < 0){
            throw new InvalidPoolAttributeException("Critical Opertaion Time Limit");
        }
        if (attribute.getStatisticalHistoryRecordCount() <= 0) {
            throw new InvalidPoolAttributeException("Statistical History record must be non-zero positive number.");
        }
    }
    
    /**
     * Destroys the instance of the Pool Manager.
     * 
     * The method blocks the execution till all the pools are emptied successfully.
     * @param bForceShutdown True to shutdown forcefully.
     */
    public void destroy(boolean bForceShutdown) {
        if (logger_.isDebugEnabled()){
            logger_.debug("Destroying Instance. Trying to empty all the pools.");
        }
        while (!emptyAllPools(bForceShutdown)){
            if (logger_.isDebugEnabled()){
                logger_.debug("Pools are being used. Pool manager will wait for 1 second and will try again till all pools are empty.");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                //do nothing
            }
        }
//        Runtime.getRuntime().removeShutdownHook(shutDownHookThread);
//        poolAttributeManager_.destroy();
        logger_.log(LogLevel.NOTICE, "Pool Manager Instance destroyed.");
        instance_= null;
    }
    
    
    /**
     * Returns the enumeration of the currently running queries.
     *
     * The queries returned are those queries that are created through the JDBC call. If the query calls
     * a procedure or a function then the queries executed within the function, procedure or package
     * will not be returned. They are outside the perview of the JDBCPool. The call itself will be 
     * displayed.
     * 
     * The queries are wrapped inside the CObjectWrapper class. The following code is required to read
     * the query and the start time of the query execution.
     * <code>
     * <br>
     *   Enumeration enumaration = getCurrentRunningQueries("<pool name>");<br>
     *   while (enumaration.hasMoreElements()) {<br>
     *       CObjectWrapper objWrapper = (CObjectWrapper) enumaration.nextElement();<br>
     *       objWrapper.getTime();<br>
     *       String query = (String) objWrapper.getUnderLyingObject();<br>
     *   }<br>
     *   <br>
     * </code>
     *
     * @param pstrPoolName name of the pool.
     * @throws IllegalArgumentException is thrown if the pool is non-existant.
     * @return Enumeration
     */
    public Enumeration<CObjectWrapper> getCurrentRunningQueries(String pstrPoolName) {
        if (logger_.isDebugEnabled()){
            logger_.debug("Getting currnet running queries from Pool #" + pstrPoolName);
        }
        try {
            return mapPools_.get(pstrPoolName).getCurrentRunningQueries().elements();
        } catch (NullPointerException e) {
            logger_.fatal("Pool is non-existant.");
            throw new IllegalArgumentException("Pool is non-existant.");
        }
    }
    
    /**
     * Returns the collection of the {@link CPoolStatisticsBean} in an ArrayList.
     *
     * Statistical data is collected by the pool based on the pool attribute
     * <code>keep-inmemory-statistics-history-count</code>.
     *
     * @param pstrPoolName Name of the pool.
     * @return ArrayList
     */
    public ArrayList<CPoolStatisticsBean> getPoolStatisticsHistory(String pstrPoolName) {
        try {
            return mapPools_.get(pstrPoolName).getStatisticsHistory();
        } catch (NullPointerException e) {
            logger_.fatal("Pool is non-existant.");
            throw new IllegalArgumentException("Pool is non-existant.");
        }
    }
    
    /**
     * Delete the pool configuration from the configuration file.
     * 
     * @param poolName Pool to be destroyed.
     * @param bForceShutdown True to shutdown the in-use pool forcefully.
     * @throws ConfigurationException
     * @throws SQLException
     */
    public synchronized void delete(String poolName, boolean bForceShutdown) throws ConfigurationException, SQLException {
        logger_.info("Deleting pool #" + poolName);
        this.emptyPool(poolName, bForceShutdown);
        poolAttributeManager_.delete(poolName);
    }
    
    
    /**
     * Returns the DataSource associated with the pool.
     * 
     * @param pstrPoolName datasource or pool name
     * @return DataSource
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public synchronized DataSource getDataSource(String pstrPoolName) throws IOException, SQLException, ClassNotFoundException {
        if (logger_.isDebugEnabled()){
            logger_.debug("Getting DataSource for Pool #" + pstrPoolName);
        }
        DataSource dataSource = null;
        dataSource = mapDataSource_.get(pstrPoolName);
        if (dataSource == null) {
            if (!mapDataSource_.containsKey(pstrPoolName)){
                if (myPoolAttributes_.containsKey(pstrPoolName)){
                    createPool((CPoolAttribute)myPoolAttributes_.get(pstrPoolName));
                    return getDataSource(pstrPoolName);
                }
                logger_.fatal("Pool Attributes Not defined or not loaded.");
            }
            logger_.fatal("Pool Does not exists in the configuration file");
        }
        return dataSource;
       
    }
}