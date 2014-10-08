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
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/JDBCLogger.java 8     9/03/09 5:44p Kedarr $
 * 
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/JDBCLogger.java $ Revision 1.4 2004/01/17 08:27:45 kedar Changes made
 * 
 * 8     9/03/09 5:44p Kedarr
 * Changed the name of the variable to standard java naming convention.
 * 
 * 7     1/29/09 9:58p Kedarr
 * Minor change to add static to final variables.
 * 
 * 6     3/17/08 12:28p Kedarr
 * 
 * 5     3/20/07 10:14a Kedarr
 * Changes made to accept the connectionwrapper object in the start sql
 * log method as well as to put the respective query in the wrapper class.
 * 
 * 4     3/14/07 10:48a Kedarr
 * Changes made such that the Query logger will now represent the pool.
 * 
 * 3     10/26/05 11:47a Kedarr
 * Changes made to display the Connection Info as well as to get the
 * appropriate timelimit for critical sql operation.
 * 
 * 2     6/06/05 6:17p Kedarr
 * Changes made to enable Log4J
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
 * for the level of logging for SQL time from DEBUG to INFO
 * 
 * Revision 1.3 2004/01/16 13:21:17 kedar Changes made to add <JDBC> message
 * while logging
 * 
 * 
 * 2 12/09/03 2:12p Kedarr Changed for the default Critical time limit for the
 * SQL Query Revision 1.2 2003/12/04 04:48:24 kedar The Default value specifed
 * for the critical query.
 * 
 * 
 * 1 12/01/03 1:35p Kedarr Revision 1.1 2003/11/28 09:47:21 kedar Added a new
 * package for Tunning SQL queries.
 * 
 *  
 */
package jdbc.tuning;

//import java.util.HashMap;
import java.util.Hashtable;

import org.apache.log4j.Logger;

//import sun.security.action.GetLongAction;


public class JDBCLogger {

    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    private static final String REVISION = "$Revision:: 8         $";

    /**
     * Returns the revision number of the .class.
     *
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }

    /**
     * Stores the query fired within a particular Thread.
     * Comment for <code>QueryTime</code>.
     */
    private static Hashtable<Thread, Long> queryTime = new Hashtable<Thread, Long>();
    
    /**
     * Stores the next time against a resultset.
     */
    private static Hashtable<String, Long> nextTime = new Hashtable<String, Long>();

//    private static final HashMap mapLoggers = new HashMap();

    /**
     * Starts the logging for a particular query executed within a particular thread.
     *
     * @param t Thread in which the query is executed.
     * @param sql SQL query.
     * @param con ConnectionWrapper
     */
    public static void startLogSqlQuery(Thread t, String sql, ConnectionWrapper con) {
        //     if (QueryTime.get(t) != null)
        //       System.out.println("WARNING: overwriting sql query log time for " +
        // sql);
        Long time = System.currentTimeMillis();
        queryTime.put(t, time);
        con.addQuery(t, sql);
    }

    /**
     * Ends the logging for the already specified query in a particular thread.
     *
     * @param t Thread in which, the query was executed.
     * @param sql SQL query
     * @param con ConnectionWrapper.
     */
    public static void endLogSqlQuery(Thread t, String sql, ConnectionWrapper con) {
        Logger logger_ = Logger.getLogger(con.strLoggerName_ + ".Query");
        long time = System.currentTimeMillis();
        time -= queryTime.get(t);
        if (time > con.getCriticalOperationTimeLimit()) { //It is a warning so do not check for logger.
            StringBuffer sbuffer = new StringBuffer();
            sbuffer.append("Con #");
            sbuffer.append(con.realConnection().toString());
            sbuffer.append(" Time: ");
            sbuffer.append(time);
            sbuffer.append(" milis. [");
            sbuffer.append(sql);
            sbuffer.append("]");
            logger_.warn(sbuffer.toString());
        }
        else{
        	if (logger_.isInfoEnabled()) {	//Check for whether info is enabled.
                StringBuffer sbuffer = new StringBuffer();
                sbuffer.append("Con #");
                sbuffer.append(con.realConnection().toString());
                sbuffer.append(" Time: ");
                sbuffer.append(time);
                sbuffer.append(" milis. [");
                sbuffer.append(sql);
                sbuffer.append("]");
                logger_.info(sbuffer.toString());
        	}
        }
        queryTime.remove(t);
        con.removeQuery(t);
    }

    /**
     * Unimplemented methods.
     *
     * @param t
     * @param sql
     * @param con 
     */
    public static void startLogSqlNext(Thread t, String sql, ConnectionWrapper con) {
        Long time = Long.valueOf(System.currentTimeMillis());
        nextTime.put(t + sql, time);
    }

    /**
     * Unimplemented methods.
     *
     * @param t
     * @param sql
     * @param con 
     */
    public static void endLogSqlNext(Thread t, String sql, ConnectionWrapper con) {
    	Logger logger_ = Logger.getLogger(con.strLoggerName_ + ".Query.Next");
        long time = System.currentTimeMillis();
        time -= ((Long) nextTime.get(t + sql)).longValue();
        if (time > con.getCriticalOperationTimeLimit()) { //It is a warning so do not check for logger.
            StringBuffer sbuffer = new StringBuffer();
            sbuffer.append("Con #");
            sbuffer.append(con.realConnection().toString());
            sbuffer.append(" Time: ");
            sbuffer.append(time);
            sbuffer.append(" milis. [");
            sbuffer.append(sql);
            sbuffer.append("]");
            logger_.warn(sbuffer.toString());
        }
        else{
        	if (logger_.isInfoEnabled()) {	//Check for whether info is enabled.
                StringBuffer sbuffer = new StringBuffer();
                sbuffer.append("Con #");
                sbuffer.append(con.realConnection().toString());
                sbuffer.append(" Time: ");
                sbuffer.append(time);
                sbuffer.append(" milis. [");
                sbuffer.append(sql);
                sbuffer.append("]");
                logger_.info(sbuffer.toString());
        		
        	}
        }
        nextTime.remove(t + sql);
    }
    
//    private static Logger getLogger(String strLoggerName) {
//        if (mapLoggers.containsKey(strLoggerName)) {
//            return (Logger) mapLoggers.get(strLoggerName);
//        }
//        mapLoggers.put(strLoggerName, Logger.getLogger(strLoggerName + ".Query"));
//        return getLogger(strLoggerName);
//    }

}
