Release:  16.05.P003		   Dated:  20100505 11:23:53
--------------------------------------------------------
Enhancements
Add a property sql-query to accept a user defined SQL query for validating the JDBC connection.
This is a mandatory property and needs to be added.


Release:  16.04.P009       Dated:  20091120 10:20:09
--------------------------------------------------------
Enhancements

Added a DataSource implementation for the Pool.

Release 16.02.P012       Dated:  20090203 16:24:33
--------------------------------------------------------
Backward compatibility issues will be there. Do not replace the existing pool directly. Method signatures have changed.

For Upgrading from previous versions of the pool, it is strongly recommended to change the existing passwords before deployment.

Enhancements:
1. Implemented Oracle 10.2.0.4 JDBC Drivers.
2. Changed the encryption routine to be more generic.
3. Methods changed to shut down the pool forcefully. This is useful in case of the un-deployment of war file where force shutdown
   of the pool is necessary. 

Library Changes
1. upgraded to bcprov-jdk13-141.jar.
2. upgraded to commons-collections-3.1.jar
3. upgraded to commons-lang-2.4.jar
4. upgraded to commons-logging-1.1.1.jar
5. log4j-1.2.14.jar
6. ojdbc14.jar (10.2.0.4 Oracle JDBC driver).


Release 16.01.P002 (Minor Release)       Dated:  20080603 14:18:17
--------------------------------------------------------
Made compliant with Oracle 10.2.0.3 JDBC drivers.

Can be made compatible with JDK1.3 by changing the associated library files from respective sources. 


Release 16.00.P040 (Major Release)		Dated:	20080318 12:45:46
--------------------------------------------------------
Compliant with JDBC Specs 4.0.

Enhancements:
1.  Strong PB based Encryption routine is added for storing the passwords. Uses javax.security pacakge.

2.  New measurements added

2.1 Number of Unavailable Connections
    Number of currently unavailable connections due to self-check or refresh actions
    taken by pool. This is number of free (idle) connections in the pool at the time
    when the pool initiated the self-check mechanism.

2.2 Unavailable connections high time
    Stores the highest time spent during which JDBC Connections were unavailable.

2.3 Highest number of unavailable connections
    Highest number of unavailable connections due to self-check or refresh actions
    taken by pool.


Libarary Files changes:
1) commons-codec-1.3.jar has been removed.
2) commons-configuration-1.4.jar instead of commons-configuration-1.2.jar.
3) commons-lang-2.3.jar instead of commons-lang-2.1.jar
4) bc-prov-jdk13-138.jar has been introduced. (http://www.bouncycastle.org/)
5) log4j-1.2.14.jar instead of log4j-1.2.9.jar

Compatible with JDK1.3+


Release 15.01.P015 (Minor Change)		Dated:	20070322 11:21:57
--------------------------------------------------------
New API added. Backward compatible.

Method isActive() added in the Connection Wrapper class. This enables to check if the connection is active
or not.

Enhancements:

1. JDBC Query logger now logs queries against the pool. Earlier the JDBC query was logged as <JDBC.Query>
thus making it difficult to identify underlying JDBC Connection against which the query was fired. This
will help in case one has multiple pools.

2. Leaked ResultSet count is now being captured and is printed in the statistics.

3. API has been provided in the Pool Manager to get the "now" running queries from the pool itself. An administrative 
screen can be developed to display all currently running queries.

4. New measurements added in the pool.
4.1 Pool Start Time.
     * Time when the pool was initialized and is ready for service.
4.2 Connections Total.
     * Connections Total is total number of database connections created in this instance of the
     * connection pool since the connection pool was instantiated. This count may be more than
     * the maximum capacity of the pool as the pool may get shrinked at times.
4.3 Connection Delay time.
     * Connection delay time is the average time in milliseconds it takes to create a
     * physical database connection. The time is calculated as the sum of the time it
     * takes to create all physical database connections in the connection pool divided
     * by the total number of connections created.
4.4 Wait Time Total.
     * Total time lost by the application in waiting for a connection from this
     * instance of the connection pool since the connection pool was instantiated.
4.5 Current Waiters.
     * Current number of application requests waiting for a connection.
4.6 Waiters High.
     * Returns the highest number of application requests concurrently waiting for a connection from
     * this instance of the connection pool.
4.7 Wait Time High.
     * Wait time is highest number of seconds that an application waited for a connection from this
     * instance of the connection pool since the connection pool was instantiated.

5. New pool attribute added.
The pool now collects statistical data in a ArrayList that is stored in-memory. The record count or the
size can be defined as a Property "inmemory-statistics-history-size". An administrative screen can collect the
statistics and can show them in a graphical manner. This depends on the "shrink-pool-interval" property. If
the "shrink-pool-interval" is <= 0 then the statistics will not be collected. By default, if the pool configuration
does not have this attribute defined then pool will add 1 by default. API has been provided in CConnectionPoolManager
to get the statistics from the respective pool.

6. CPoolStatisticsBean.toString() method changed.
The sequence of the toString() method of the CPoolStatisticBean has been altered. New parameters are added.
Leaked ResultSet count is added in the beginning.

7. Bundle Naming conventions changed.
Naming conventions changed for the JDBCPool bundles. A prefix D or P will be added against the build number. "D"
indicates that the build is a development build and it may have bugs or is partially complete. "P" indicates that
the build is a production stable build. Example. JDBCPool15.01.D023.jar indicates that this is a development build
and JDBCPool15.01.P024.jar indicates that the build is a production stable build.


Release 15.00 (Major Change)		Dated:	20060515 13:36:45
--------------------------------------------------------
New API Added. Backward compatible.

Added a algorithm. Now one can choose between First-In-First-Out (FIFO) or Stack which is nothing
but Last-In-First-Out (LIFO) algorithm. Valid values for this attribute are FIFO, LIFO. XML or Properties 
configuration now has the following attribute:
XML        --> <pool-algorithm>FIFO</pool-algorithm>
PROPERTIES --> .pool-algorithm=LIFO

If the properties or XML configuration does not have any pool-algorithm attribute then by default the
pool will add the FIFO algorithm.

The pool now does a caching of statements. The pool will now return an existing OPEN statement if it finds 
the similar statement already created and will not create a new statement . The pool is also enhanced to 
catch all open ResultSets and the pool will try closing them upon closure of Open Statements and/or freeing 
of a JDBC Connection object.


Release 14.00 (Major Change)		Dated:	20060314 10:58:36
--------------------------------------------------------
New API Added.

Added a destroy method in the CConnectionPoolManager.
Added a equals method in CPoolAttribute for comparision.
Added few statistical data collection points that will help us to identify appropriate pool settings.

Libraries are upgraded.
1) commons-configuration-1.2.jar instead of commons-configuration-1.1.jar
2) commons-lang-2.1.jar

Following libraries are removed completly.
1) commons-digester.jar
2) commons-beanutils-core.jar
3) commons-beanutils-bean-collections.jar

For JDK 1.3 compatible the jars have been updated and two new jars have been placed.
1) serializer.jar (new)
2) xalan.jar (new)

For further details about configurations packaged follow the link
http://jakarta.apache.org/commons/configuration/dependencies.html

Release 13.04 (Major Change)		Dated:	15-Feb-2006
--------------------------------------------------------
No API Change.

A bug was resolved. The XML configuration used to return the pool attributes of the last pool defined in the
configuration for all the previous pools. This is resolved in this version. The versions from 13.01 till 13.03
have this bug and the pool is to be replaced with this 13.04 version. This is applicable to those who store
the pool configuration in XML file instead of properties.

Release 13.03 (Minor Change)
--------------------------------------------------------
No API Change.

Minor changes made in the logging of pool statistics. The Runtime memory is now printed in the SOP. This feature
was present earlier but later on removed in higher versions.

Release 13.02 (Minor Change)		Dated:	06-Dec-2005
--------------------------------------------------------
No API Change.

Now the log4j file is not mandatory to be passed to the pool manager getInstance(..) method. It can be passed as null 
but one has to ensure that the log4j logger has been configured prior to calling getInstance(..) method. The Pool
will use the same log4j logger settings. Thus the facility to use the logger of the application rather than its own
is enabled in this release.


Release 13.01 (Major Change)		Dated:	24-Nov-2005
--------------------------------------------------------
No API Change.

For existing customers using XML configuration, please note that  initalconfiguration.xml or file called
configuration.xml is removed. Simply pass the poolconfig.xml to the PoolManager.

Now, the pool configuration can be stored in PROPERTIES file as well. This is given for java versions lower than JDK1.4 
upto JDK 1.2.x. The lib folder contains lots of libraries but in case one is using the pool with PROPERTIES file then 
the following libraries should be sufficient.

1) commons-codec-1.3.jar
2) commons-configuration-1.1.zip
3) commons-lang-2.0.jar
4) log4j-1.2.9.jar
5) classes12.zip

Please refer to the sample pool.properties or poolconfig.xml files in the config directory of the zip for 
the structure and sample values. At runtime one change the pool attributes and/or create a new non-existant pool 
and the same will get stored in Properties or XML file depending on what is being used.

If you have to use JDK 1.3 and an XML configuration please refer to documentation for Release 07.00 (Major Change) in
the version history below. For convenience, the extra libraries required for JDK1.3 compatibility viz. xercesImpl.jar 
and xml-apis.jar are placed inside lib/forjdk1.3 directory.

You need not change the log4j.properties provided you supply the property 
-Djdbc.home=<log file path prior to logs directory>
to java at runtime. Otherwise one needs to change the logfile path at two locations in the file to match the physical
settings on the server. To give an example the if the java argument is passed as ...
java -Djdbc.home=/u01/project/r3app 
then the logs will be created inside /u01/project/r3app/logs directory. It is important to create the directory logs
physically, the pool will not create it.


Release 13.00 (Major Change)		Dated:	11-Nov-2005
--------------------------------------------------------
No API Changes.

Added a new parameter max-usage-per-jdbc-connection to the XML configuration file.  
The pool will now close the JDBC Connection if the usage exceeds the specified limit. 
This limit is not mandatory and the configuration can have a negative -1 value to suppress 
this behaviour.


Release 12.03 (Minor Change)		Dated:	17-Oct-2005
--------------------------------------------------------
No API Changes.

Changed the createPool(..) method from the CConnectionPoolManager synchronized. Also updated the
JavaDoc. Improvised the reading of jar file.

Release 12.02 (Minor Change)		Dated:	14-Sep-2005
--------------------------------------------------------
No API Changes.

Added few debuging messages. Also, un-commented the Runtime shut-down hook.
Changes made in the logger category. Instead of JDBC.<pool name> it would display JDBC.Pool[<pool name>].

Release 12.01 (Minor Change)		Dated:	30-Aug-2005
--------------------------------------------------------
Internal API changes made. For existing API implementation No Change.

Changes made to save the password in an ecnrypted format. A new library has been added
and is kept in lib folder. Please ensure that this "commons-codec-1.3.jar" library  is placed
appropriately in the classpath.


Release 12.00 (Major Change)		Dated:	29-Aug-2005
--------------------------------------------------------
New API added. For existing API implementation No Change.

New methods are added in the Pool Manager class that will help to modify the exiting
pool attributes as well as saving a new pool configuration in the configuration xml
file. Changes are made in the display part of the statistics for removing the Pool Attributes
that were being displayed earlier. Added a new InvalidAttributeException that will be thrown
by the manager if the attributes supplied are invalid.

Release 11.01 (Minor Change)		Dated:	20-Jul-2005
--------------------------------------------------------
No API Change. 

A new wait time is introduced. This will enable the pool to wait for certain time
if the all the connections are in USE and that the pool is unable to create new
connection as the maximum limit is reached. This will not throw an exception till
the specified time limit is over.

The following line is added in poolconfig.xml to specify the time limit for wait.

<pool name...
>
   <in-use-wait-time>7</in-use-wait-time>
</pool>



Release 11.00 (Major Change)		Dated:	07-Jul-2005
--------------------------------------------------------
No API Change. 

The change is done in the inactive-time-out time interval. Now one can specify
a timeout of less than equal to ZERO. In such a case, the JDBC connections will not be pooled.
On release of the JDBC connection back to the pool the connection will be closed immediately.



Release 10.00 (Major Change)
--------------------------------------------------------

No Change in API.

PoolManager changed for the following reason:
Changes made for catching exception while emptying pool in
emptyAllPools() method. The pool will remain alive till all the
connections are in use. The Manager will attempt to close all other
pools.

ConnectionPool changed for following reason:
Changes made to emptyPool() method. The pool will remain alive till all
the connections are in use. The self check mechanism will then attempt
to empty the pool. Also, the servicing of any requests to
getConnection() will be stopped, if the pool is in Destroy mode.

Release 09.00 (Major Change)		Dated:	07-Jun-2005
--------------------------------------------------------
Made the Pool compliant with Oracle Version 9.2.0.6 drivers. (classes12.zip)

No Change in the Pool API. API changed with respect to classes12.zip. Should be internal to the
pool, unless used by any other class outside the pool.

Release 08.00 (Major Change)
--------------------------------------------------------
Log4J logger enabled in the pool. The pool api has also been changed for the startup.

CConnectionPoolManager.getInstance("c:/log4.jproperties), new File("c:/configuration.xml));

Also, please go through log4j.properties inside the config directory.


Release 07.00 (Major Change)
--------------------------------------------------------
PoolManager will now read XML configuration file using jakarta configuration package.

The following code needs to be incorporated.

CConnectionPoolManager poolManager = CConnectionPoolManager.getInstance((new File("c:/config.xml"))));
where config.xml is the xml that indicates what all xml files needs to be loaded.
Sample config.xml and poolconfig.xml are placed within config directory.

One can specify pools that are needed to be loaded during startup. Otherwise, the pool manager
will create the pools, defined within the xml file, as and when they are requested for.

poolManager.createPool(....) is no longer valid. Also, the Constructor of the CConnectionPoolManager
is also changed to accept only one configuration file.

Following libraries are required to run the pool.
commons-configuration-1.1.zip
commons-logging.jar
commons-digester.jar
commons-lang-2.0.jar
commons-collections-3.1.jar
commons-beanutils-core.jar
commons-beanutils-bean-collections.jar

The above libraries can be downloaded from the following site:
http://jakarta.apache.org/commons/
For simplicity, they have been provided in the distribution zip file.

Also, classes12.zip from Oracle corporation is required if Oracle RDBMS is being used.

Please read the following for runtime dependencies.
http://jakarta.apache.org/commons/configuration/dependencies.html

This version is compatible with JDK1.4.x. If you have lower versions of JDK then
kindly read the runtime dependencies and download the required components.


Release 06.01 (Minor Change)		Dated:	09-May-2005
--------------------------------------------------------
The pool will print its own version just as soon as it starts.

Release 06.00 (Major Change)		Dated:	09-May-2005
--------------------------------------------------------
The empty pool will not forcefully collect the JDBC connections. Instead the pool will
throw SQLException.


Release 05.02
--------------------------------------------------------
No API changes in this release.

Rectified the pool class to display the the leaked statements count in the
statistics bean.


Release 05.01
--------------------------------------------------------
Changed the API.

LogType class has been introduced. Now instead of a int value a name must be passed to the logger. A LogType can be represented as
Off, Notice, Error, Info, Debug and All.

CConnectionPoolManager has been changed. The method 
getInstance(String pstrLogFileName, int piLogFileMaxSize, int piBufferSize, boolean pbAppend, String strLogType)
now accepts log file name, log file maximum size, log buffer size, append boolean and the string representing a LogType.


