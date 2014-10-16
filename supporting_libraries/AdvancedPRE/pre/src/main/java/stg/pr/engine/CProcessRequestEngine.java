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
 * $Revision: 3829 $
 *
 * 
 */

package stg.pr.engine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.sql.DataSource;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import stg.customclassloader.CCustomClassLoaderFactory;
import stg.customclassloader.IPREClassLoaderClient;
import stg.database.CBeanException;
import stg.database.CDynamicDataContainer;
import stg.database.CDynamicDataContainerException;
import stg.database.CDynamicQueryException;
import stg.pr.beans.CProcessStatusBean;
import stg.pr.beans.ProcessReqParamsController;
import stg.pr.beans.ProcessReqParamsEntityBean;
import stg.pr.beans.ProcessRequestController;
import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleController;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.IJOBMonitor.JOB_STATUS;
import stg.pr.engine.IProcessRequest.REQUEST_SOURCE;
import stg.pr.engine.IProcessRequest.REQUEST_STATUS;
import stg.pr.engine.IProcessRequest.REQUEST_TYPE;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.mailer.CMailer;
import stg.pr.engine.mailer.EMail;
import stg.pr.engine.scheduler.CRequestScheduler;
import stg.pr.engine.scheduler.IRequestIdGenerator;
import stg.pr.engine.scheduler.ISchedule.SCHEDULE_STATUS;
import stg.pr.engine.startstop.CStartEngine;
import stg.pr.engine.startstop.IReboot;
import stg.pr.engine.startstop.IStartStop;
import stg.pr.engine.startstop.IStopEvent;
import stg.pr.engine.vo.RequestStatusVO;
import stg.utils.CDate;
import stg.utils.CDateException;
import stg.utils.CSettings;
import stg.utils.Day;
import stg.utils.PREDataType;
import stg.utils.StringUtils;

import com.hazelcast.config.Config;
import com.hazelcast.config.ConfigLoader;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Member;
import com.stg.logger.LogLevel;
import com.stg.logger.LoggingOutputStream;

//import de.schlichtherle.license.LicenseContent;
//import stg.pr.engine.license.LicenseParamImpl;
//import stg.pr.engine.license.LicenseRequestor;
//import stg.pr.engine.license.LicenseVerifier;

/**
 * This Class will act as an engine to execute requested processes in the
 * background.
 * <p>
 * It continuously scans the PROCESS_REQUEST table and executes queued requests
 * with status = 'Q' This Engine spawns threads for processnig multiple requests
 * at the same time thus taking maximum advantage of the CPU.
 * </p>
 * 
 * @version $Revision: 3829 $
 * @author Kedar C. Raybagkar
 **/
public class CProcessRequestEngine implements Runnable, IPREClassLoaderClient {

	// public instance constants and class constants

	// /** constant for defining the request log buffer size **/
	// public static final int REQUEST_LOG_BUFFER_SIZE = 32 * 1024; // 32 KB
	//    
	// /** constant for defining the engine log buffer size **/
	// public static int ENGINE_LOG_BUFFER_SIZE = 16 * 1024; // 16 KB

	// public instance variables

	// public class(static) variables

	// protected instance constants and class constants

	// protected instance variables

	// protected class(static) variables

	// package instance constants and class constants

	// package instance variables

	// package class(static) variables

	// private instance constants and class constants

	private static final String ORDER_BY_CLAUSE = "ORDER BY priority";

	/**
	 * The version control system will update this revision variable and thus
	 * the revision numbers will automatically get updated. Comment for
	 * <code>REVISION</code>
	 */
	private static final String REVISION = "$Revision: 3829 $";

	// private instance variables

	/**
	 * Instance of CProcessRequestEngine. Comment for <code>instance_</code>.
	 */
	private static CProcessRequestEngine instance_;

	/**
	 * Maxium number of threads that the PRE will spawn for StandAlone Requests.
	 */
	private final int iStandAloneMaximumThread_;

	/**
	 * Maxium number of threads that the PRE will spawn for Group Request.
	 */
	private final int iGroupMaximumThread_;

	/**
	 * Increamented when a Thread is created and decreamented when the thread
	 * processing is over for StandAlone Requests. This variable is identified
	 * as volatile as multiple threads can modify the value.
	 */
	private AtomicInteger iThreadCounter_;

	/**
	 * Increamented when a Thread is created and decreamented when the thread
	 * processing is over. This variable is identified as volatile as multiple
	 * threads can modify the value.
	 */
	private AtomicInteger iGrpThreadCounter_;

	/**
	 * Connection which is been used by the Engine.
	 */
	private Connection staticConnection_;

	/**
	 * PrintWriter reference used to write engine log
	 */
	private static Logger objEngineLogger_ = Logger.getLogger("Engine");

	/**
	 * Reboot the engine. If the variable is set to true then the engine reboots
	 * itself. Comment for <code>bReBoot</code>.
	 */
	private AtomicBoolean bReBoot = new AtomicBoolean(false);

	/**
	 * Sleep time before the engine reboots itself. Default value -1. Comment
	 * for <code>lRebootSleepTime</code>.
	 */
	private long lRebootSleepTime = -1;

	/**
	 * Reboot counter that keeps the count of number of reboots. Default value
	 * -1. Comment for <code>lRebootCounter</code>.
	 */
	private AtomicInteger iRebootCounter = new AtomicInteger(-1);

	/**
	 * The maximum number of reboots can the engine do. Default value -1.
	 * Comment for <code>lRebootMaxCounter</code>.
	 */
	private int lRebootMaxCounter = -1;

	/**
	 * FILTER_CONDITION for the request_type. Comment for
	 * <code>FILTER_CONDITION</code>.
	 */
	private String FILTER_CONDITION;

	// /** Reference variable for the Connection Pool object for StandAlone
	// Service Engine
	// */
	// private ConnectionPool pool_;
	//    
	// /** Reference variable for the Connection Pool object for Grouped Service
	// Engine
	// */
	// private ConnectionPool poolGrouped_;

	// /**
	// * JDBC Connection Pool Manager.
	// * Comment for <code>poolManager_</code>.
	// */
	// private jdbc.pool.CConnectionPoolManager poolManager_;

	/**
	 * This variable holds the time interval for which the engine waits when
	 * there are no queued requests. The max size is picked up from waitinterval
	 * property of pr.properties. Once the wait interval expires the engine once
	 * again queries for queued requests. This value can be changed in the
	 * properties file and re-loaded using
	 * {@link stg.utils.CReloadPropertiesHandler} to reflect the change.
	 * 
	 */
	private long lWaitInterval_;

	/**
	 * This variable holds the path where the request log file is created The
	 * path is picked up from requestlogfilepath property of pr.properties This
	 * path does not include the name of the file The name of the log file is
	 * the Request Id picked up from the PROCESS_REQUEST table
	 */
	private String strReqLogFilePath_;

	/**
	 * This variable holds the http url with which the log file can file can be
	 * viewed The path is picked up from requestlogfileurl property of
	 * pr.properties This path does not include the name of the file The name of
	 * the log file is the Request Id picked up from the PROCESS_REQUEST table
	 */
	private String strReqLogFileUrl_;

	/**
	 * This variable holds the extension for the request log file, for eg: html,
	 * txt, etc.. The path is picked up from requestlogfileextension property of
	 * pr.properties
	 */
	private String strReqLogFileExtension_;

	/**
	 * This variable holds the format in which date values have to be stored in
	 * the Param_Val field of the PROCESS_REQ_PARAMS table The dateformat is
	 * picked up from paramdateformat property of pr.properties
	 */
	private SimpleDateFormat paramSimpleDateFormat_;

	/**
	 * This variable holds the format in which time values (Datetime field
	 * values) have to be stored in the Param_Val field of the
	 * PROCESS_REQ_PARAMS table The timeformat is picked up from paramtimeformat
	 * property of pr.properties
	 */
	private SimpleDateFormat paramSimpleTimeFormat_;

	/**
	 * This variables holds the date time format picture.
	 */
	private SimpleDateFormat paramSimpleDateTimeFormat_;

	/**
	 * This variable holds the delimiter which is to be used to delimit array
	 * values while storing them as a string in the Param_Val field of the
	 * PROCESS_REQ_PARAMS table It is picked up from paramarrvaluedelim property
	 * of pr.properties
	 */
	private String strParamArrValueDelim_;

	// /** This attribute is set to true when the engine exits the indefinite
	// loop
	// * This gives a signal to the addShutdownHook thread that the engine is
	// terminated and
	// * the resources can be released
	// */
	// private boolean bEngineTerminationConfirmed_;

	/**
	 * This attribute is set to true when the engine exits the indefinite loop
	 * This gives a signal to the addShutdownHook thread that the engine is
	 * terminated and the resources can be released
	 */
	private AtomicBoolean bEngineTerminated_ = new AtomicBoolean(false); // This variable can be
	// replaced with
	// tEngine_.isInterrupted()
	// ??

	/**
	 * This attribute is set to true when the engine exits the indefinite loop
	 * This gives a signal to the addShutdownHook thread that the engine is
	 * terminated and the resources can be released
	 */
	private  AtomicBoolean bGroupedEngineTerminated_ = new AtomicBoolean(false); // This variable can be
	// replaced with
	// tMain_.isInterrupted()
	// ??

	/**
	 * This thread object refers to the main thread instance that is spawned in
	 * the startEngine() method. The scanning and execution of grouped requests
	 * is done in this thread.
	 */
	private Thread tEngine_;

	/**
	 * This thread object refers to the thread instance that is spawned in the
	 * startEngine() method. The scanning and execution of standalone requests
	 * is done in this thread.
	 */
	private Thread tMain_;

	/**
	 * This thread object refers to the thread instance that is spawned in the
	 * Shut Down hook placed on the main method. The termination of the engine
	 * depends on this thread getting interrupted.
	 */
	private Thread tInterrupt_;

	/**
	 * This attribute is used to store the message that is repeatedly used
	 * across from multiple places.
	 */
	private MessageFormat mfSleepMessageForEngine_ = new MessageFormat(
			"Queued requests does not exist, {0} service will sleep for {1, number} seconds and scanning will re-start ....");

	/**
	 * All the threads that are created by the engine are added in this vector
	 * and as and when. the thread finishes the process then it is removed from
	 * the vector.The terminateEngine() method will wait till all the threads
	 * that are already in process gets over. The engine will not abort the
	 * process if execution of some process is beeing processed.
	 */
	private Vector<Thread> vectorThreads_;

	/**
	 * All the threads that are created by the engine are added in this vector
	 * and as and when. the thread finishes the process then it is removed from
	 * the vector.The terminateEngine() method will wait till all the threads
	 * that are already in process gets over. The engine will not abort the
	 * process if execution of some process is beeing processed.
	 */
	private Vector<Thread> vectorThreadsGroup_;

	/**
	 * This attribute is used to store the extra query conditions for the
	 * Dynamic Data Container.
	 */
	private HashMap<String, String> hmWhereCondition_;

	/**
	 * This attribute is used to store the value of the Group Engine ON or OFF.
	 */
	private boolean bGroupEngineToBeStarted_;

	/**
	 * This attribute is used to store the time interval of the Stuck Thread
	 * Monitor.
	 */
	private long lStuckThreadMonitorInterval_;

	/**
	 * Thread that is created for monitoring the Stuck Threads.
	 */
	private Thread tMonitorThread_;

	/**
	 * This variable is used to store the instance of the CHttpServer.
	 * 
	 * Comment for <code>httpserver_</code>
	 */
	private CHttpServer httpserver_;

	/**
	 * This variable is used to store the Number of Stuck Threads that are
	 * marked by the Monitor Thread.
	 * 
	 * Comment for <code>iStuckThreads_</code>
	 */
	private final AtomicInteger iStuckThreads_ = new AtomicInteger(0);

	/**
	 * This variable is used to store the Number of Stuck Threads that cross the
	 * maximum limit.
	 * 
	 * Comment for <code>iStuckThreadThatCrossMaxLimit_</code>
	 */
	private final AtomicInteger iStuckThreadThatCrossMaxLimit_ = new AtomicInteger(0);

	/**
	 * Maximum queued requests to be fetched in a single fetch. Comment for
	 * <code>iMaximumFetchSizeAtATime_</code>
	 */
	private int iMaximumFetchSizeAtATime_;

	/**
	 * The time interval after which, the Process Monitor will send escalation
	 * mails. This is applicable only for JOBs that have crossed MAX STUCK
	 * THREAD Limit. Comment for <code>jobMonitorEscalationTimeInterval_</code>
	 */
	private int jobMonitorEscalationTimeInterval_;

	/**
	 * This variable stores the message that will be set when the engine is
	 * being terminated. Termination can occur if Stop Request is given and/or
	 * if JDBC Exception occurs. Comment for <code>stopMessageForEmail_</code>
	 */
	private volatile String stopMessageForEmail_;

	// /**
	// * Byte Array Output stream for writing exception to string.
	// * Comment for <code>bytes_</code>.
	// */
	// private ByteArrayOutputStream bytes_;

	// /**
	// * PrintWriter wrapper arround the Byte Array Output stream.
	// * Comment for <code>pwException_</code>.
	// */
	// private PrintWriter pwException_;

	/**
	 * New Line character identifier. Comment for <code>NEW_LINE</code>.
	 */
	private final String NEW_LINE = System.getProperty("line.separator");

	private PREInfo info = new PREInfoImpl();

	/**
	 * Stores whether the cluster handshake process was done or not. True if
	 * done and false if not done. It is expected that the group engine should
	 * wait till the hand shake is true.
	 */
	private boolean bClusterHandShakeDone_ = false;

	/**
	 * Final variable to store the context.
	 */
	private PREContextImpl context_;

//	/**
//	 * License Content.
//	 */
//	private LicenseContent licContent_;

	/**
	 * Data Source for the queue.
	 */
	private IDataSourceFactory dataSourceFactory_;

	private volatile HeartBeatState state_;

	private ConcurrentHashMap<String, Service> services_ = new ConcurrentHashMap<String, Service>();

	private Member member_;

	// /**
	// * Stores true if licensed.
	// */
	// private boolean bValidLicense_;

	// private class(static) variables

	// constructors

	/**
	 * Constructor of the class.
	 * <p>
	 * This constructor accepts a String variable that has the path of the init
	 * file that has the path of all the property files that have to be loaded.
	 * The engine only accesses properties from the pr.properties file. However
	 * if the processes, that have to be executed, require any more property
	 * files, then the path of these files can also be mentioned in this init
	 * file
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>
	 * Once the property file is loaded using the CSettings class, all the
	 * attributes that the engine might access are retrieved and initialized to
	 * the corresponding private member variables</li>
	 * 
	 * <li>
	 * The max size defined for both the log files are checked, against the
	 * respective buffer sizes, to see that max size's specified are greater
	 * than the buffer sizes</li>
	 * 
	 * <li>
	 * Engine log file is initialized and a database connection pool is created</li>
	 * 
	 * <li>
	 * A Shutdown hook is added that listens for a termination event for eg:
	 * CTRL-C Once the event occurs appropriate action is taken to release
	 * resources.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param pstrInitFile
	 *            String path of the init file that has the path of all the
	 *            property files that have to be loaded
	 * @param pstrLoggerFile
	 *            Log4J.properties file.
	 * @throws Exception
	 * 
	 */
	private CProcessRequestEngine(String pstrInitFile, String pstrLoggerFile,
			int iRebootCntr) throws Exception {

		try {
			PropertyConfigurator.configure(pstrLoggerFile);
			CSettings.getInstance().load(pstrInitFile);
			objEngineLogger_.log(LogLevel.NOTICE, info.toString());
			context_ = new PREContextImpl();
			context_.setPREinfo(info);
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Initializing Engine");
			}
			setRebootCounter(iRebootCntr);
//			System.setProperty("java.util.prefs.PreferencesFactory",
//					"stg.utils.prefs.PREPreferencesFactoryImpl");
//			System.setProperty("java.util.prefs.PreferencesFactory.file",
//					CSettings.get("pr.java.util.prefs.PreferencesFactory.file",
//							null));
//			try {
//				installLic(); // always install the license so that if the
//				// license is changed in between it will get
//				// reflected.
//				licContent_ = readLicense();
//				LicenseVerifier.verify(licContent_);
//				objEngineLogger_.log(LogLevel.NOTICE, "Licensed to "
//						+ licContent_.getHolder().getName() + " for usage of "
//						+ licContent_.getConsumerAmount() + " threads.");
//				objEngineLogger_.log(LogLevel.NOTICE, "Valid up to "
//						+ licContent_.getNotAfter());
//				Day now = new Day();
//				objEngineLogger_.log(LogLevel.NOTICE, "Days remaining "
//						+ Math.abs(now.daysBetween(licContent_.getNotAfter())));
//			} catch (LicenseContentException lce) {
//				generateLicenseRequest();
//				objEngineLogger_.log(LogLevel.FATAL, "Invalid license.");
////				System.err.println(lce.getLocalizedMessage());
//				throw new Exception(lce.getLocalizedMessage());
//			} catch (Exception e2) {
//			    objEngineLogger_.log(LogLevel.FATAL, "Invalid license.");
//				generateLicenseRequest();
////				System.err.println(e2.getLocalizedMessage());
//				throw new Exception(e2.getLocalizedMessage());
//			}

			lStuckThreadMonitorInterval_ = Math.abs(Long.parseLong(CSettings
					.get("pr.stuckthreadmonitorinterval", "0")));
			if (lStuckThreadMonitorInterval_ <= 0) {
				throw new IllegalArgumentException(
						"Property stuckthreadmonitorinterval should be greater than zero");
			}
			if (objEngineLogger_.isEnabledFor(LogLevel.FINEST)) {
				objEngineLogger_.log(LogLevel.FINEST,
						"Redirecting System.out and System.err to log4j");
			}
			System.setErr(new PrintStream(new LoggingOutputStream(Logger
					.getLogger("System.err"), Level.WARN), true));
			System.setOut(new PrintStream(new LoggingOutputStream(Logger
					.getLogger("System.out"), Level.INFO), true));
		} catch (Exception e) {
			throw e;
		}

		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("CLASSPATH="
					+ System.getProperty("java.class.path"));
		}

		strReqLogFilePath_ = CSettings.get("pr.requestlogfilepath");
		strReqLogFileUrl_ = CSettings.get("pr.requestlogfileurl");
		strReqLogFileExtension_ = CSettings.get("pr.requestlogfileextension");
		paramSimpleDateFormat_ = new SimpleDateFormat(CSettings.get("pr.paramdateformat"));
		paramSimpleTimeFormat_ = new SimpleDateFormat(CSettings.get("pr.paramtimeformat"));
		paramSimpleDateTimeFormat_ = new SimpleDateFormat(CSettings.get("pr.paramdatetimeformat"));
		strParamArrValueDelim_ = CSettings.get("pr.paramarrvaluedelim");

		FILTER_CONDITION = CSettings.get("pr.requesttypefilter", "XYZ");
		if (!FILTER_CONDITION.equals("INCLUDE")
				&& !FILTER_CONDITION.equals("EXCLUDE")) {
			throw new CProcessRequestEngineException(
					"Invalid FILTER type specified for property requesttypefilter.");
		}
		iStandAloneMaximumThread_ = CSettings
				.getInteger("pr.standalonemaxiumthreads");
		iGroupMaximumThread_ = CSettings.getInteger("pr.groupmaxiumthreads");
		if (iStandAloneMaximumThread_ <= 0) {
			throw new CProcessRequestEngineException(
					"Invalid maximum threads for StandAlone engine configured.");
		}
		if (iGroupMaximumThread_ <= 0) {
			throw new CProcessRequestEngineException(
					"Invalid maximum threads for Group engine configured.");
		}
		String strRequestTypes = CSettings.get("pr.processrequesttype", null);
		if (strRequestTypes == null) {
			throw new CProcessRequestEngineException(
					"Invalid processrequesttype defined. There must be a default value.");
		}
		StringTokenizer tokenizer = new StringTokenizer(strRequestTypes, ",");
		StringBuffer buffer = new StringBuffer();
		while (tokenizer.hasMoreTokens()) {
			buffer.append("'");
			buffer.append(tokenizer.nextElement());
			buffer.append("'");
			buffer.append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		if (FILTER_CONDITION.equals("INCLUDE")) {
			FILTER_CONDITION = "req_type IN (" + buffer.toString() + ")";
		} else {
			FILTER_CONDITION = "req_type NOT IN (" + buffer.toString() + ")";
		}

		try {
			(CCustomClassLoaderFactory.getInstance()).getClassLoader(this); 
			// dummy call As this is a SingleTon Factory Pattern
		} catch (Exception e) {
			throw e;
			// throw new CProcessRequestEngineException("ClassLoader: " +
			// e.getMessage());
		}

		iMaximumFetchSizeAtATime_ = Math
				.abs(Integer.parseInt(CSettings.get(
						"pr.maximumfetchsizeinsinglefetch", CSettings
								.get("pr.dbcon"))));
		iThreadCounter_ = new AtomicInteger(1);
		iGrpThreadCounter_ = new AtomicInteger(1);
		setRebootMaxCounter(Integer.parseInt(CSettings.get(
				"pr.rebootmaxcounter", "-1")));
		setRebootSleepTime(Long.parseLong(CSettings.get(
				"pr.sleepbeforereboottime", "5")));

		hmWhereCondition_ = new HashMap<String, String>();
		hmWhereCondition_.put("getScheduledTime", " <= ");

		bGroupEngineToBeStarted_ = ((CSettings.get("pr.groupengine", "OFF")
				.equals("OFF")) ? false : true);

		httpserver_ = new CHttpServer(); // instantiate http server

		// bEngineTerminationConfirmed_ = false;

		vectorThreads_ = new Vector<Thread>(); // Inital size will be equal to
		// the total threads
		vectorThreadsGroup_ = new Vector<Thread>(); // =================== ""
		// =======================

		// These NET properties are available only in JDK1.4.x and above.
		// Added by Kedar on July 31, 2004 .....SouthField Detroit USA
		System.setProperty("http.keepAlive", CSettings.get("pr.http.keepAlive",
				"true"));
		System.setProperty("http.maxConnections", CSettings.get(
				"pr.http.maxConnections", "5"));
		System.setProperty("sun.net.client.defaultConnectTimeout", CSettings
				.get("pr.sun.net.client.defaultConnectTimeout", "10000"));
		System.setProperty("sun.net.client.defaultReadTimeout", CSettings.get(
				"pr.sun.net.client.defaultReadTimeout", "10000"));

		try {
			jobMonitorEscalationTimeInterval_ = Math.abs(Integer
					.parseInt(CSettings.get(
							"pr.jobmonitorescalationtimeinterval", "30")));
		} catch (NumberFormatException e) {
			objEngineLogger_
					.error("Invalid value specified for jobmonitorescalationtimeinterval in properties.");
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_
						.info("Defaulting jobmonitorescalationtimeinterval to 30 minutes.");
			}
			jobMonitorEscalationTimeInterval_ = 30;
		}

		/*
		 * Anonymous inner class passed as a parameter to addShutdownHook This
		 * thread is registered with the JVM, and the JVM starts this just
		 * before shutting down. For eg: when u try to abort by pressing Ctrl-C.
		 * Hence run method is called and in turn terminateEngine method is
		 * called where resources are released.
		 */
		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("Add ShutDown Hook ....");
		}
		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("ShutDown Hook Added....");
			objEngineLogger_.info("Initializing Connection Pool ....");
		}
		Class<?> c = Class.forName(CSettings.get("pr.dataSourceFactory",
				"stg.pr.engine.datasource.defaultimpl.PREDataSourceFactory"));
		Object obj = c.newInstance();
		if (obj instanceof IDataSourceFactory) {
			dataSourceFactory_ = (IDataSourceFactory) obj;
			if (!dataSourceFactory_.initialize(new File(CSettings
					.get("pr.dataSourceFactoryConfigFile")))) {
				throw new CProcessRequestEngineException(
						"Could not initialize the data source factory.");
			}
		} else {
			throw new CProcessRequestEngineException(
					"Class does not implement IDataSource");
		}

		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_
					.info("Initiating the cluster using the configuration from file "
							+ System.getProperty("hazelcast.config"));
		}
		Config config = ConfigLoader.load(System.getProperty("hazelcast.config"));
		Hazelcast.init(config);

        loadStartupClasses();
        
        for (Entry<String, Service> entry : services_.entrySet()) {
            Service service = entry.getValue();
            if (service instanceof Singleton<?>) {
                Singleton<?> singleton = (Singleton<?>) service;
                context_.addSingletonIfAbsent(entry.getKey(), singleton);
                if (objEngineLogger_.isInfoEnabled()) {
                    objEngineLogger_.info("Service " + entry.getKey() + ":" + entry.getValue().getClass().getName() +  "made available to the context.. ");
                }
            } else {
                if (objEngineLogger_.isInfoEnabled()) {
                    objEngineLogger_.info("Service " + entry.getKey() + ":" + entry.getValue().getClass().getName() +  "is unavailable to the context.. ");
                }
            }
        }
		
		startWebServer();

//		if (CSettings.get("pr.reportService", "OFF").equalsIgnoreCase("ON")) {
//			if (objEngineLogger_.isInfoEnabled()) {
//				objEngineLogger_.info("Starting the Report Services.");
//			}
//			try {
//				EngineConfig reportConfig = new EngineConfig();
//				reportConfig.setBIRTHome(CSettings.get("pr.birt.home"));
//				reportConfig.setLogConfig(CSettings.get("pr.birt.log.dir"),
//						java.util.logging.Level.parse(CSettings
//								.get("pr.birt.log.level")));
//				reportConfig.setProperty("ThreadPoolServiceSize", CSettings
//						.getInt("pr.birt.threadpoolsize", 3));
//				reportService = ReportService.getService(reportConfig);
//				if (objEngineLogger_.isEnabledFor(LogLevel.NOTICE)) {
//					objEngineLogger_.log(LogLevel.NOTICE,
//							"Report Services started");
//				}
//			} catch (ReportServiceException e) {
//				objEngineLogger_.fatal("Report Service exception encountered",
//						e);
//				if (objEngineLogger_.isEnabledFor(LogLevel.NOTICE)) {
//					objEngineLogger_.log(LogLevel.NOTICE,
//							"Unable to start the report services.");
//				}
//			}
//		}

		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("Engine Initialized");
		}
		// This thread is now assigned upfront as in the Terminate engine if
		// called from Reboot Sequence then
		// the shutdown hook has to be removed from the Runtime instance.
		tInterrupt_ = new Thread("shutdown") {

			public void run() {
				objEngineLogger_.log(LogLevel.NOTICE,
						"ShutDown Hook In Running mode.");
				terminateEngine();
			}
		};
		Runtime.getRuntime().addShutdownHook(tInterrupt_);

	}
	
	private void loadStartupClasses() throws Exception {
        String str = CSettings.get("pr.startUpClasses");
        
        if (!(str == null || "".equals(str))) {
            Iterator<String> iter = StringUtils.listTokens(str, ';');
            while (iter.hasNext()) {
                String clazzName = iter.next();
                String key = clazzName;
                if (StringUtils.countTokens(clazzName, ':') > 1) {
                    key = StringUtils.extractTokenAt(clazzName, ':', 1);
                    clazzName = StringUtils.extractTokenAt(clazzName, ':', 2);
                }
                Class<?> clazz = Class.forName(clazzName);
                Object obj1 = clazz.newInstance();
                if (obj1 instanceof Service) {
                    Service instance = (Service) obj1;
                    instance.init(context_);
                    services_.put(key, instance);
                }
            }
        }
	}

//	/**
//	 * 
//	 */
//	private void generateLicenseRequest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
//		LicenseRequestor licenseRequestor = new LicenseRequestor();
//		licenseRequestor.generateRequest();
//		return;
//	}
//
	// finalize method, if any

	// main method

	/**
	 * Entry point of the engine It first checks whether one argument is passed.
	 * If no argument is found an exception is thrown with an appropriate
	 * message. If arguments are passed correctly an object of this class is
	 * created and engine starts execution.
	 * 
	 * @param args
	 *            expects args array of length 1, containing the path of init
	 *            file
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		CProcessRequestEngine objPre = null;

		try {

			if (args.length < 2) {
				throw new Exception(
						"Usage: java stg.pr.engine.CProcessRequestEngine <prinit.properties file path> <log4j.properties file path>");
			}
			if (!SystemUtils.IS_JAVA_1_6) {
				throw new Exception("Unable to start. Requires Java 1.6");
			}

			String str = System.getProperty("pre.reboot.attempt");
			int iRebtCnt = -1;
			if (str != null) {
				iRebtCnt = Integer.parseInt(str);
			}

			try {
				// if ("true".equalsIgnoreCase(System.getProperty("genlicreq",
				// "false"))) {
				// LicenseRequestor licenseRequestor = new LicenseRequestor();
				// licenseRequestor.generateRequest();
				// return;
				// }
				objPre = CProcessRequestEngine.getInstance(args[0], args[1],
						iRebtCnt);
				objPre.startEngine();
			} catch (Exception e) {
				if (objPre != null) {
					if (!objPre.isReboot()) {
						throw e;
					}
				} else {
					throw e;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (objPre != null) {
				if (objPre.isReboot()
						&& objPre.getRebootCounter() > objPre
								.getRebootMaxCounter()) {
					objPre.setReboot(false);
				}
			}
		}
		// System.exit(100);

	}

	// public methods of the class in the following order

	/**
	 * Creates an instance of the CProcessRequestEngine.
	 * 
	 * @param pstrFile
	 *            The initial properties file to be loaded by CSettings.
	 * @param pstrLoggerFile
	 *            Log4J.properties file.
	 * @param iRebootCnt
	 *            Reboot counter.
	 * 
	 * @return CProcessRequestEngine Engine
	 * @throws Exception
	 */
	protected synchronized static CProcessRequestEngine getInstance(
			String pstrFile, String pstrLoggerFile, int iRebootCnt)
			throws Exception {
		if (instance_ == null) {
			instance_ = new CProcessRequestEngine(pstrFile, pstrLoggerFile,
					iRebootCnt);
		}
		return (instance_);
	}

	/**
	 * Returns the created instance of the Engine. May return null in case
	 * engine is not started.
	 * 
	 * @return CProcessRequestEngine Engine.
	 */
	public static CProcessRequestEngine getInstance() {
		// if (instance_ == null)
		// {
		// throw new CProcessRequestEngineException("Engine Not Started");
		// }
		return (instance_);
	}

	/**
	 * Starts the StandAlone Request Service Engine & Grouped Request Service
	 * Engine
	 * 
	 * @throws Exception
	 *             in case of any errors.
	 */
	public void startEngine() throws Exception {
		tMain_ = Thread.currentThread();
		// Sequence changed on 2.1.2006 by Kedar.
		// The initial sequence was that of starting the job monitor thread and
		// then starting the stand alone
		// engine and then group engine. But in the job monitor thread we are
		// checking for the stand alone engine
		// thread if not alive then do a shutdown of the job monitor thread.
		// This used to work well in case of
		// SUN Solaris 9 machine but failed on LINUX. Therefore this change is
		// made.
		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("Starting the Engine..");
		}
		member_ = Hazelcast.getCluster().getLocalMember();
		Iterator<Member> iterator = Hazelcast.getCluster().getMembers()
				.iterator(); // cluster_.getClusterTopology().getNodes().iterator();
		Member topoNode = iterator.next();
		if (topoNode.equals(member_)) {
			state_ = HeartBeatState.RUNNING;
			context_.getMap().put("state", state_);
		} else {
			state_ = HeartBeatState.PASSIVE;
			// if (!context_.getPREVersion().equals(strBundleDetailsArray_[1]))
			// {
			// throw new
			// CProcessRequestEngineException("Cluster configuration does not match.");
			// }
		}
		StringBuffer sbuffer = getDefaultEmailAttributes();
		sbuffer.append("Cluster Mode    : " + state_.name());
		sendDefaultMail("Engine is starting..."
				+ (((getRebootCounter() > 0) ? "Reboot Attempt #"
						+ getRebootCounter() : "")), sbuffer.toString(), true);
		if (Hazelcast.getConfig().getNetworkConfig().getJoin().getTcpIpConfig()
				.getMembers().size() > 1) {
			objEngineLogger_.log(LogLevel.NOTICE,
					"PRE is configured for cluster mode on "
							+ Hazelcast.getConfig().getNetworkConfig()
									.getJoin().getTcpIpConfig().getMembers()
									.size() + " different machines.");
		} else {
			objEngineLogger_
					.log(LogLevel.NOTICE,
							"PRE is configured for cluster mode restricted to a single machine.");
		}
		objEngineLogger_.log(LogLevel.NOTICE, "Going into " + state_.name()
				+ " mode.");
		ClusterListener listener = new ClusterListener(this);
		Hazelcast.getCluster().addMembershipListener(listener);
		if (state_.equals(HeartBeatState.PASSIVE)) {
			deactivate();
			if (state_.equals(HeartBeatState.STOP)) {
				objEngineLogger_.log(LogLevel.NOTICE, "Initiating "
						+ state_.name() + "....");
				bEngineTerminated_.set(true);
				bGroupedEngineTerminated_.set(true);
				stopMessageForEmail_ = "Stop Request was initiated";
			} else {
				context_.getMap().put("state", HeartBeatState.RUNNING);
				state_ = HeartBeatState.RUNNING;
				objEngineLogger_.log(LogLevel.NOTICE, "Initiating "
						+ state_.name() + "....");
			}
		}
		if (state_ == HeartBeatState.RUNNING) {
			CMailer.getInstance(CSettings.get("pr.mailtype"))
					.loadAndTransportSerializedEmails(this);
		}
		// if (bValidLicense_) {
		tEngine_ = new Thread(this, "grpeng"); // Name the thread.
		tEngine_.start();
		// }

		if (lStuckThreadMonitorInterval_ > 0) {
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Initializing JOB Monitor...");
			}
			tMonitorThread_ = new MonitorThread("JobMonitorThread");
			tMonitorThread_.start();
		} else {
			objEngineLogger_
					.log(
							LogLevel.NOTICE,
							"Monitor Thread is currently OFF. "
									+ "To Start the monitor thread to identify the STUCK THREAD, have the value of the stuckthreadmonitorinterval property to a non zero positive integer.");
		}
		

		try {
			startServiceEngine();
		} catch (CProcessRequestEngineException e) {
			objEngineLogger_
					.error(
							"Error While Starting the Service Engine for Normal Requests",
							e);
			objEngineLogger_
					.error("Stand Alone engine aborted.. Wait till PRE terminiates..");
			throw e;
		} finally {
			// if (cluster_.getClusterTopology().getNodes().size() == 1) {
			// clusterMap_.remove("state");
			// }
		}
		objEngineLogger_.log(LogLevel.NOTICE,
				"Stand Alone engine stopped.. Wait till PRE terminates.");

	}

	/**
	 * Thread for running StandAlone Service Engine.
	 * 
	 */
	public void run() {
		try {
			if (bGroupEngineToBeStarted_) {
				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_.info("Starting the Group Engine");
				}
//				if (((Properties) licContent_.getExtra()).getProperty(
//						"groupEngine").equalsIgnoreCase("off")) {
//					objEngineLogger_.log(LogLevel.NOTICE,
//							"Not licensed for Group Request Processing");
//					bGroupEngineToBeStarted_ = false;
//					return;
//				}
				startGroupServiceEngine();
			} else {
				objEngineLogger_
						.log(
								LogLevel.NOTICE,
								"Group Engine Not Started. "
										+ "To Start the group engine have the value of the groupengine property to ON ");
			}
		} catch (Exception e) {
			objEngineLogger_
					.error(
							"Error While Starting the Service Engine for Group Requests",
							e);
			// no need to throw exception. just log it.
		}
		objEngineLogger_.log(LogLevel.NOTICE,
				"Group Engine stopped.. Wait till PRE terminates.");
	}

	/**
	 * Returns a list of classes that need to be loaded by the system
	 * classloader
	 * 
	 * @return a String array with the classes that need to be loaded by the
	 *         System classloader.
	 */
	public String[] getSystemLoadedClasses() {
		StringTokenizer stz = new StringTokenizer(CSettings
				.get("pr.systemloadedclasses"), ";");
		String[] saSystemLoadedClasses = new String[stz.countTokens()];
		int iElement = 0;

		while (stz.hasMoreTokens()) {
			saSystemLoadedClasses[iElement] = stz.nextToken();
			iElement++;
		}

		return saSystemLoadedClasses;
	}

	/**
	 * Returns the path from where the class loader will load the classes
	 * 
	 * @return a String with the path where the classes can be found
	 */
	public String getCustomClassLoaderClassPath() {
		return CSettings.get("pr.objclasspathforclassloader");
	}

	/**
	 * Indicates whether the custom classloader has to reload classes or not
	 * 
	 * @return a boolean indicating whether the custom classloader has to reload
	 *         classes or not
	 */
	public boolean isReload() {
		return (((CSettings.get("pr.reloadobjclasses").equals("Y")) ? true
				: false));
	}

	/**
	 * To stop the engine.
	 * 
	 * @param pobj
	 *            IStartStop
	 */
	public void stopEngine(IStartStop pobj) {
		if (pobj instanceof IReboot) {
			objEngineLogger_.log(LogLevel.NOTICE, "Rebooting the engine.");
			setReboot(true);
			setRebootCounter(-1); // This is because someone is doing a reboot
			// and it is not happening due to error.
			setRebootSleepTime(1);
			context_.getMap().put("state", HeartBeatState.REBOOT);
			state_ = HeartBeatState.REBOOT;
		} else {
			context_.getMap().put("state", HeartBeatState.STOP);
			state_ = HeartBeatState.STOP;
		}
		bEngineTerminated_.set(true);
		bGroupedEngineTerminated_.set(true);
		stopMessageForEmail_ = (isReboot() ? "Reboot" : "Stop")
				+ " 'Request' requested. Normal Termination.";
		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_
					.info("Stopping The StandAlone Service Engine ....");
		}
		if (vectorThreads_.size() == 0) {
			tMain_.interrupt();
		}
		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("Stopping The Grouped Service Engine ....");
		}
		if (vectorThreadsGroup_.size() == 0) {
			if (tEngine_ != null) {
				tEngine_.interrupt();
			}
		}
		if (lStuckThreadMonitorInterval_ > 0) {
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Stopping Monitor Thread");
			}
			tMonitorThread_.interrupt();
		}

		// if (httpserver_.isStarted())
		// {
		// if (objEngineLogger_.isInfoEnabled()) {
		// objEngineLogger_.info("Stopping Web Service");
		// }
		// httpserver_.stop();
		// }
		// stopWebServer();

		// closeAll();
	}

	// protected constructors and methods of the class

	// package constructors and methods of the class

	// private constructors and methods of the class

	/**
	 * Starts service for scanning standalone requests
	 * 
	 * @throws Exception
	 */
	private void startServiceEngine() throws Exception {
		try {
			objEngineLogger_.log(LogLevel.NOTICE,
					"Start Service Engine for Stand Alone Requests....");

			startServiceForNormalRequests();

		} catch (Exception e) {
			stopMessageForEmail_ = exceptionToString(e);
			objEngineLogger_.error(
					"Exception caught in the main Stand Alone engine", e);
			throw e;
		} finally {
			if (tMonitorThread_.isAlive()) {
				tMonitorThread_.interrupt();
			}
			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_
						.debug("Finally Block of startServiceEngine(). Please wait till the termination is confirmed.");
			}
		}
	}

	/**
	 * Starts the Stand Alone service engine.
	 * 
	 * Gets the connection from the pool and Continuously scans the
	 * PROCESS_REQUEST table for queued standalone requests and executes them
	 * sequentially
	 * 
	 * <p>
	 * Scanning continues in a loop until the bEngineTerminated_ attribute is
	 * false. This attribute is set to true when the engine is interrupted, and
	 * this loop exits.
	 * </p>
	 * <p>
	 * The query picks up requests, from the table PROCESS_REQUEST, that have
	 * the Req_Stat='Q' and GRP_ST_IND = 'S'. If requests are found then each
	 * record is processed sequentially If no records are found then the engine
	 * waits for a specified time (picked up from property file) and then
	 * resumes scanning
	 * </p>
	 * 
	 * <p>
	 * This method will <b>spawn Threads</b> for processing stand alone
	 * requests, thus taking maximum advantage of the CPU. The threads are
	 * created by taking into account the number of connections available in the
	 * pool - 2. As two connections are internally used by the Engine, One for
	 * making updates for status and another for processing Grouped Requests.
	 * </p>
	 * 
	 * @throws CProcessRequestEngineException
	 * 
	 */
	private void startServiceForNormalRequests()
			throws CProcessRequestEngineException {
		boolean isQueuedReqFound = false;
		ProcessRequestController objPrCont_ = null;

		StringBuffer reqLogFileName;
		StringBuffer reqLogFileUrl;
		// final int iNoOfThreads_ = (bValidLicense_?Integer.MAX_VALUE:2);

		try {

			reqLogFileName = new StringBuffer(50);
			reqLogFileUrl = new StringBuffer(50);

			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Starting StandAlone Engine ....");
			}
			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_
						.info("Getting JDBC Connection for the StandAlone Engine ....");
			}
			// staticConnection_ =
			// poolManager_.getConnection(CSettings.get("pr.dsforstandaloneeng"));
			DataSource ds = dataSourceFactory_.getDataSource(CSettings
					.get("pr.dsforstandaloneeng"));
			if (ds == null) {
				throw new CProcessRequestEngineException(
						"NullPointerException for DataSource");
			}
			staticConnection_ = ds.getConnection();
			CDynamicDataContainer objDdc_ = new CDynamicDataContainer();
			objDdc_.addWhereClause(FILTER_CONDITION);
			objDdc_.addOrderByClause(ORDER_BY_CLAUSE);

			ProcessRequestEntityBean objPrEb_ = new ProcessRequestEntityBean();

			objPrCont_ = new ProcessRequestController(staticConnection_);
			setRebootCounter(-1); // Ideally here the Engine can be considered
			// as started.
			checkClusterPRE();
			if (!tMain_.isInterrupted() || !bEngineTerminated_.get()) {
				objEngineLogger_.log(LogLevel.NOTICE,
						"Stand Alone Engine Started..");
			}
			Iterator<RequestStatusVO> failOverRequestIterator = context_
					.getFailedOverRequests().iterator();
			while (!tMain_.isInterrupted()) {
				context_.setPREActive(true);
				boolean bTerminate = false;
//				try {
//					LicenseVerifier.verify(readLicense());
//				} catch (LicenseContentException lce) {
//					bTerminate = true;
//					objEngineLogger_.fatal(lce.getLocalizedMessage());
//				} catch (Exception e) {
//					bTerminate = true;
//					objEngineLogger_.fatal(e.getLocalizedMessage());
//				}
				if (bTerminate) {
					if (tEngine_ != null) {
						tEngine_.interrupt();
					}
					break;
				}
				try {
					isQueuedReqFound = false;
					boolean failOverRequest = false;
					if (!bEngineTerminated_.get()) {
						if (objEngineLogger_.isDebugEnabled()) {
							objEngineLogger_
									.debug("Entered infintite loop, Initializing Request Entity Bean ....");
						}
						setReboot(false);
						objPrEb_.initialize();
						if (objEngineLogger_.isDebugEnabled()) {
							objEngineLogger_.debug("Building query ....");
						}

						if (objEngineLogger_.isDebugEnabled()) {
							objEngineLogger_
									.debug("Querying for queued requests ....");
						}
						if (failOverRequestIterator.hasNext()) {
							RequestStatusVO vo = failOverRequestIterator.next();
							objEngineLogger_.log(LogLevel.NOTICE,
									"Re-Executing Failed-Over Request #"
											+ vo.getReqId());
							objPrEb_.setReqId(vo.getReqId());
							objPrEb_.setReqStat(vo.getStatus());
							objDdc_.build(staticConnection_, objPrEb_);
							objDdc_
									.setMaximumFetchSize(iMaximumFetchSizeAtATime_);
							objDdc_.setPartialFetch(true);
							failOverRequestIterator.remove();
							isQueuedReqFound = objDdc_.executeQuery(
									staticConnection_, objPrCont_, objPrEb_);
							failOverRequest = true;
						} else {
							objPrEb_.setReqStat(REQUEST_STATUS.QUEUED.getID());
							objPrEb_.setGrpStInd(REQUEST_TYPE.STANDALONE
									.getID());
							objPrEb_
									.setScheduledTime(getCurrentTimestamp(staticConnection_));

							objDdc_.build(staticConnection_, objPrEb_,
									hmWhereCondition_);
							objDdc_
									.setMaximumFetchSize(iMaximumFetchSizeAtATime_);
							objDdc_.setPartialFetch(true);
							// This has been added later by Kedar on 3/1/2003
							isQueuedReqFound = objDdc_.executeQuery(
									staticConnection_, objPrCont_, objPrEb_);
							failOverRequest = false;
						}
					} // If engine is not terminated then only do the above
					// stuff.
					if (isQueuedReqFound) {// pending requests exist

						if (objEngineLogger_.isInfoEnabled()) {
							objEngineLogger_
									.info("Queued requests exists ....");
						}

						// The bEngineTerminated_ flag with an OR condition was
						// added on 21.01.2004 by Kedar.
						// This is because if the StopEngine tires terminate the
						// engine and if there is a process
						// already running then the engine should not enter the
						// while loop.
						while (objDdc_.next()
								&& !(tMain_.isInterrupted() || bEngineTerminated_.get())) {
							if (iThreadCounter_.get() <= iStandAloneMaximumThread_
//									&& iThreadCounter_.get() <= licContent_
//											.getConsumerAmount()
							        )
							// if ( iThreadCounter_ <
							// CSettings.getInt(("pr.standalonemaximumthreads",
							// "2")) && iThreadCounter_ <=
							// licContent_.getConsumerAmount())
							{

								ProcessRequestEntityBean objPrEb = (ProcessRequestEntityBean) objDdc_
										.get();

								reqLogFileName.delete(0, reqLogFileName
										.length());
								reqLogFileUrl.delete(0, reqLogFileUrl.length());
								reqLogFileName.append(strReqLogFilePath_);
								reqLogFileName.append(objPrEb.getReqId());
								reqLogFileName.append(".");
								reqLogFileName.append(strReqLogFileExtension_);

								reqLogFileUrl.append(strReqLogFileUrl_);
								reqLogFileUrl.append(objPrEb.getReqId());
								reqLogFileUrl.append(".");
								reqLogFileUrl.append(strReqLogFileExtension_);

								// objEngineLogger_.info("Initialize Request Log File ....");

								updateRequestStatus(staticConnection_,
										objPrCont_, objPrEb,
										REQUEST_STATUS.LAUNCHING, reqLogFileUrl
												.toString());

								// Create a new thread and start the process.
								// Increament the Thread counter so that if
								// thread counter reaches
								// Maximum of the connections available in pool
								// then the engine should not spawn a new
								// thread.
								ThreadProcess tp = new ThreadProcess(
										"Thread-ReqId#" + objPrEb.getReqId());
								// tp.join(); //Joins the new thread to the
								// parent (tEngine).
								addStandAloneProcess(tp);
								tp.setProcessRequestEntityBean(objPrEb);
								tp.setLogFileName(reqLogFileName.toString());
								tp.setLogFileNameURL(reqLogFileUrl.toString());
								tp.setPool(dataSourceFactory_); // So that the
								// ThreadProcess
								// can request
								// connection
								// from Pool
								tp.setFailedOver(failOverRequest);
								tp.start();
							} // if ( iThreadCounter_ <
							// iTotalConnectionsInPool_)

							// If all the available threads are spawned then
							// wait for 10 seconds. This should be
							// parameterised.
							// If thread.sleep is not called then for that while
							// loop it will take a huge CPU time and rest of the
							// threads will be slow.
							// The above comment is debatable. So commented
							// currently.
							boolean bInWaitMode = false;
							if (iThreadCounter_.get() > iStandAloneMaximumThread_) {
								bInWaitMode = true;
								if (objEngineLogger_
										.isEnabledFor(LogLevel.FINE)) {
									objEngineLogger_
											.log(
													LogLevel.FINE,
													"Maximum thread spawning capacity (#"
															+ (iThreadCounter_.get() - 1)
															+ ") has reached. Going into Wait mode till one of the JOB gets over.");
								}
							}
							long lCurrentTime = System.currentTimeMillis();
							while (iThreadCounter_.get() > iStandAloneMaximumThread_) {
								// try
								// {
								// Thread.sleep(10000);
								// }
								// catch (InterruptedException ie)
								// {
								// System.exit(1);
								// }
							}
							long lWaitTime = System.currentTimeMillis()
									- lCurrentTime;
							if (bInWaitMode) {
								bInWaitMode = false;
								if (objEngineLogger_
										.isEnabledFor(LogLevel.FINE)) {
									objEngineLogger_
											.log(
													LogLevel.FINE,
													"Wait Over. Waited for #"
															+ lWaitTime
															+ " milliseconds for some JOB to get over.");
								}
							}

						} // end of while(objDdc.next())

					} // end of if objDdc.getTotalRows() > 0
					else {
						if (!failOverRequestIterator.hasNext()) { // no failover
							// jobs.
							lWaitInterval_ = (Math.abs(Long.parseLong(CSettings
									.get("pr.waitinterval"))));
							if (!(bEngineTerminated_.get() || tMain_.isInterrupted())) {
								if (objEngineLogger_.isInfoEnabled()) {
									objEngineLogger_
											.info(mfSleepMessageForEngine_
													.format(new Object[] {
															"StandAlone",
															lWaitInterval_ }));
								}
								try {
									Thread.yield();
									TimeUnit.SECONDS.sleep(lWaitInterval_);
									// Thread.sleep(lWaitInterval_);
								} catch (InterruptedException ie) {
									if (objEngineLogger_.isInfoEnabled()) {
										objEngineLogger_
												.info("Engine Thread Interrupted ..");
									}
									break;
								}
							} else {
								if (bEngineTerminated_.get()) {
									tMain_.interrupt();
								}
							}
						}
					} // end of else
				} catch (CDynamicDataContainerException cdce) {
					// This is under the assumption that the IO Exception can
					// never be thrown by the object
					// executed by the Engine as it can only throw
					// CProcessRequestEngineException. This exception
					// means that something wrong has happened in the Engine
					// itself and so engine should get terminated.
					stopMessageForEmail_ = exceptionToString(cdce);
					objEngineLogger_
							.fatal(
									"CDynamicQueryException Caught. Terminating StandAlone Engine",
									cdce);
					setReboot(true);
					break;
				} catch (CDynamicQueryException cdqe) {
					// This is under the assumption that the IO Exception can
					// never be thrown by the object
					// executed by the Engine as it can only throw
					// CProcessRequestEngineException. This exception
					// means that something wrong has happened in the Engine
					// itself and so engine should get terminated.
					stopMessageForEmail_ = exceptionToString(cdqe);
					objEngineLogger_
							.fatal(
									"CDynamicQueryException Caught. Terminating StandAlone Engine",
									cdqe);
					setReboot(true);
					break;
				} catch (IOException ioe) {
					// This is under the assumption that the IO Exception can
					// never be thrown by the object
					// executed by the Engine as it can only throw
					// CProcessRequestEngineException. This exception
					// means that something wrong has happened in the
					// Connection.
					try {
						staticConnection_ = refreshJDBCConnection(4,
								staticConnection_, CSettings
										.get("pr.dsforstandaloneeng"));
					} catch (Exception e) {
						stopMessageForEmail_ = exceptionToString(ioe);
						objEngineLogger_
								.fatal(
										"IOException Caught. Terminating StandAlone Engine",
										ioe);
						setReboot(true);
						;
						break;
					}
				} catch (SQLException se) {
					// This is under the assumption that the SQL Exception can
					// never be thrown by the object
					// executed by the Engine as it can only throw
					// CProcessRequestEngineException. This exception
					// means that something wrong has happened in the
					// Connection.
					objEngineLogger_.error("SQLException caught", se);
					try {
						staticConnection_ = refreshJDBCConnection(4,
								staticConnection_, CSettings
										.get("pr.dsforstandaloneeng"));
						// StackTraceElement[] elements = se.getStackTrace();
						// for (int i = 0; i < elements.length; i++) {
						//							
						// }
					} catch (Exception e) {
						stopMessageForEmail_ = exceptionToString(se);
						objEngineLogger_
								.fatal(
										"SQLException Caught. Terminating StandAlone Engine",
										se);
						setReboot(true);
						break;
					}
				} catch (RuntimeException ree) {
					objEngineLogger_.error("Runtime Exception Caught", ree);
					// Just catch; no need to throw exception.
				} catch (CProcessRequestEngineException cree) {
					objEngineLogger_.error(
							"CProcessRequestEngineException Caught", cree);
					// Just catch; no need to throw exception.
				} catch (Exception e) {
					objEngineLogger_.error("Exception Caught", e);
					// Just catch; no need to throw exception.
				} catch (Error e) {
					if (e instanceof ThreadDeath) {
						objEngineLogger_
								.fatal("Process killed through the PRE Web Service");
					} else {
						stopMessageForEmail_ = exceptionToString(e);
						objEngineLogger_.fatal(e);
						throw e;
					}
				}

			} // end of while true

		} catch (ConnectException e) {
			stopMessageForEmail_ = exceptionToString(e);
			objEngineLogger_
					.fatal(
							"ConnectException Caught. Terminating StandAlone Engine",
							e);
			if (getRebootCounter() > 0) {
				setReboot(true);
			}
		} catch (SQLException e) {
			stopMessageForEmail_ = exceptionToString(e);
			objEngineLogger_.fatal(
					"SQLException Caught. Terminating StandAlone Engine", e);
			if (getRebootCounter() > 0) {
				setReboot(true);
			}
		} catch (IOException e) {
			stopMessageForEmail_ = exceptionToString(e);
			objEngineLogger_.fatal(
					"IOException Caught. Terminating StandAlone Engine", e);
			if (getRebootCounter() > 0) {
				setReboot(true);
			}
		} catch (Exception e) {
			throw new CProcessRequestEngineException(e.getMessage(), e);
		} // end of 1st try catch block
		finally {
			((PREContextImpl) context_).setPREActive(false);
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_
						.info("Releasing Stand Alone Resources And Confirming Termination ...");
			}
			if (objPrCont_ != null) {
				try {
					objPrCont_.close();
				} catch (SQLException e) {
					objEngineLogger_.error(
							"Caught exception while closing PRController.", e);
				}
			}
			// pool_.stopShrinking();
			reqLogFileName = null; // Nullifying the variables.
			reqLogFileUrl = null; // Nullifying the variables.
			if (tInterrupt_ != null) {
				if (tInterrupt_.isAlive()) {
					if (objEngineLogger_.isInfoEnabled()) {
						objEngineLogger_
								.info("Interrupting Shutdown Hook from Stand Alone Requests...");
					}
					tInterrupt_.interrupt();
				}
			}
			bEngineTerminated_.set(true);
		}
	} // end of startServiceForNormalRequests

	/**
	 * Processes a request and executes it.
	 * 
	 * <p>
	 * <ul>
	 * <li>
	 * Fetches parameters for the request from the table PROCESS_REQ_PARAMS and
	 * populates them in a hashmap</li>
	 * <li>
	 * Initializes Request LogFile with the name of the log file being <request
	 * id>.log</li>
	 * <li>
	 * Instantiate an object of the class that is mentioned in the
	 * process_class_nm field of the PROCESS_REQUEST table and then cast it to
	 * IProcessRequest. If the cast fails an appropriate exception is logged in
	 * the Engine and Request Log</li>
	 * <li>
	 * Updates the Req_Stat field in PROCESS_REQUEST table with 'P', calls
	 * various set..() methods on the object, which set the parameters required
	 * for the process, and then finally call the processRequest() method which
	 * actually has the logic of the process. Depending on success or failure of
	 * execution of the process, either 'S' or 'E' is updated in the
	 * PROCESS_REQUEST table.</li>
	 * </ul>
	 * </p>
	 * 
	 * In Case of an Error ({@link java.lang.Error}) the error is logged and
	 * thrown to the main Engine. This error may or may not get logged and
	 * totaly depends on the JVM implementation.
	 * 
	 * @param pcon
	 *            Connection object
	 * @param pobjPrCont
	 *            Controller object reference for table PROCESS_REQUEST
	 * @param pobjPrEb
	 *            EntityBean object reference for table PROCESS_REQUEST
	 * @param pobjDdcParams
	 *            DataContainer object reference for querying table
	 *            PROCESS_REQ_PARAMS
	 * @param pobjParamsCont
	 *            Controller object reference for table PROCESS_REQ_PARAMS
	 * @param pobjParamsEb
	 *            EntityBean object reference for table PROCESS_REQ_PARAMS
	 * @param objProcessRequest_
	 *            Object of IProcessRequest.
	 * @param logFileName
	 *            Log file name against which the response stream is to be released.
	 * @param pstrLogFileURL
	 *            Logger file
	 * @return bSuccess true if request is processed and executed successfully,
	 *         else false
	 * 
	 */
	private boolean processEachRequest(Connection pcon,
			ProcessRequestController pobjPrCont,
			ProcessRequestEntityBean pobjPrEb,
			CDynamicDataContainer pobjDdcParams,
			ProcessReqParamsController pobjParamsCont,
			ProcessReqParamsEntityBean pobjParamsEb,
			ProcessRequestServicer objProcessRequest_, File logFileName,
			String pstrLogFileURL) {
		long lStartTime = System.currentTimeMillis();
		boolean bSuccess = true;
//		Throwable objThrowable ;
		HashMap<String, Object> hmParams = null;
		// PrintWriter objRequestLogger_ = null;

		// IProcessRequest objProcessRequest_ = null;
		boolean bScheduleExists = false;
		boolean bScheduleExecuted = false;
		boolean retryPossible = pobjPrEb.getRetryCnt() < pobjPrEb
				.getRetryTimes();
		boolean toBeRetryed = false;
		long lProcessEndTime = 0;
		try {
			bScheduleExists = (pobjPrEb.getSchId() != 0);

			objEngineLogger_.log(LogLevel.NOTICE,
					"Processing Stand Alone Request for request id "
							+ pobjPrEb.getReqId());
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Fetch parameters of request "
						+ pobjPrEb.getReqId());
			}

			hmParams = fetchRequestParams(pcon, pobjPrEb.getReqId(),
					pobjDdcParams, pobjParamsCont, pobjParamsEb);

			// objEngineLogger_.info("Initialize Request Log File ....");
			//            
			// objRequestLogger_ = new PrintWriter(new CFileBufferedOutputStream
			// (new File(pstrLogFile.toString()),
			// iRequestLogFileMaxSize_,
			// REQUEST_LOG_BUFFER_SIZE, false), true);
			//            
			// objEngineLogger_.info("Instantiate the Process Class which has been requested .....");
			// objProcessRequest_ =
			// instantiateReqProcessObject(objRequestLogger_,
			// pobjPrEb.getProcessClassNm());

			if (objProcessRequest_ != null) {

				updateRequestStatus(pcon, pobjPrCont, pobjPrEb,
						REQUEST_STATUS.PROCESSING, pstrLogFileURL);

				objProcessRequest_.setDataSourceFactory(dataSourceFactory_);
				objProcessRequest_.setConnection(pcon);
				objProcessRequest_.setParams(hmParams);
				objProcessRequest_.setRequestId(pobjPrEb.getReqId());
				objProcessRequest_.setLogFile(logFileName);
//				objProcessRequest_.setResponseWriter(objRequestLogger_);
				objProcessRequest_.setUserId(pobjPrEb.getUserId());
				objProcessRequest_.setSource(REQUEST_SOURCE.OFFLINE);

				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_.info("Execute method on object ....");
				}
				long lProcessStartTime = System.currentTimeMillis();
				if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
					objEngineLogger_.log(LogLevel.FINE,
							"{4}Elapsed Time before calling processRequest() method #"
									+ (lProcessStartTime - lStartTime)
									+ " milliseconds");
				}
				boolean bJOBStatus = objProcessRequest_.processRequest();
				lProcessEndTime = System.currentTimeMillis();
				if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
					objEngineLogger_.log(LogLevel.FINE,
							"{5}Elapsed Time taken by the JOB's underlying processRequest() to complete #"
									+ (lProcessEndTime - lProcessStartTime)
									+ " milliseconds");
				}
				if (bJOBStatus) {
					updateRequestStatus(pcon, pobjPrCont, pobjPrEb,
							REQUEST_STATUS.COMPLETED, pstrLogFileURL);
				} else {
					updateRequestStatus(pcon, pobjPrCont, pobjPrEb,
							REQUEST_STATUS.ERROR, pstrLogFileURL);
					if (retryPossible)
						toBeRetryed = true;
				}
			} // Process Class is instantiated
		} // try block
		catch (RuntimeException re) {
			if (retryPossible)
				toBeRetryed = true;
//			objThrowable = re; // This line must be the first line in this
			// block.
			bSuccess = false;

			objEngineLogger_.fatal("RunTime Exception Caught..", re);
//			re.printStackTrace(logFileName);
			try {
				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_
							.info("Update Request Status to Error due to Exception.");
				}
				updateRequestStatus(staticConnection_, pobjPrCont, pobjPrEb,
						REQUEST_STATUS.ERROR, pstrLogFileURL);

			} catch (Exception ex) {
				objEngineLogger_.error(ex);
//				ex.printStackTrace(logFileName);
			}
		} catch (Exception e) {
			if (e instanceof CProcessRequestEngineException) {
				if (retryPossible)
					toBeRetryed = true;
			}
//			objThrowable = e; // This line must be the first line in this block.
			bSuccess = false;

			objEngineLogger_.log(LogLevel.NOTICE, "Exception Caught..");
			objEngineLogger_.error(e);
//			e.printStackTrace(logFileName);
			try {
				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_
							.info("Update Request Status to Error due to Exception.");
				}
				updateRequestStatus(staticConnection_, pobjPrCont, pobjPrEb,
						REQUEST_STATUS.ERROR, pstrLogFileURL);

			} catch (Exception ex) {
				objEngineLogger_.error(
						"Cound not update the request status to Error.", ex);
//				ex.printStackTrace(logFileName);
			}
		} catch (ThreadDeath tderror) {
			toBeRetryed = false; // As this thread was killed there should not
			// be a retry option.
//			objThrowable = tderror; // This line must be the first line in this
			// block.
			bSuccess = false;

			objEngineLogger_
					.fatal("Thread Has Been Terminated from the Web Service....Initiating cleanup...");
			try {
				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_
							.info("Update Request Status to Error due to Thread Termination.");
				}
				updateRequestStatus(staticConnection_, pobjPrCont, pobjPrEb,
						REQUEST_STATUS.KILLED, pstrLogFileURL);

			} catch (Exception ex) {
				objEngineLogger_.error("Unable to update due to ThreadDeath. ",
						ex);
			}
			throw tderror;
		} catch (Error error) {
			toBeRetryed = false;
//			objThrowable = error; // This line must be the first line in this
			// block.
			bSuccess = false;

			objEngineLogger_.fatal("Error Encountered..", error);
//			error.printStackTrace(logFileName);
			try {
				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_
							.info("Update Request Status to Error due to Exception.");
				}
				updateRequestStatus(staticConnection_, pobjPrCont, pobjPrEb,
						REQUEST_STATUS.ERROR, pstrLogFileURL);

			} catch (Exception ex) {
				objEngineLogger_
						.error(
								"Caught Exception while updating the request status to Error.",
								ex);
//				ex.printStackTrace(logFileName);
			}
			throw error;
		} finally {
			if (toBeRetryed) {
				try {
					long newRequestId = proCreateRetry(pcon, pobjPrEb,
							pobjParamsEb, pobjDdcParams, pobjPrCont,
							pobjParamsCont);
					if (objEngineLogger_.isEnabledFor(LogLevel.NOTICE)) {
						objEngineLogger_
								.log(
										LogLevel.NOTICE,
										"The request will be retryed after "
												+ pobjPrEb.getRetryTime()
												+ " "
												+ pobjPrEb.getRetryTimeUnit()
												+ ((newRequestId != -1) ? ". New Request Id# "
														+ newRequestId
														: ""));
					}
				} catch (SQLException e) {
					objEngineLogger_
							.error(
									"Unable to retry the failed request due to SQLException",
									e);
					toBeRetryed = false; // This way the
					// sendScheduleUnExecutedMail may
					// get called.
				} catch (ClassNotFoundException e) {
					objEngineLogger_
							.error(
									"Unable to retry the failed request due to ClassNotFoundException",
									e);
					toBeRetryed = false; // This way the
					// sendScheduleUnExecutedMail may
					// get called.
				} catch (InstantiationException e) {
					objEngineLogger_
							.error(
									"Unable to retry the failed request due to InstantiationException",
									e);
					toBeRetryed = false; // This way the
					// sendScheduleUnExecutedMail may
					// get called.
				} catch (IllegalAccessException e) {
					objEngineLogger_
							.error(
									"Unable to retry the failed request due to IllegalAccessException",
									e);
					toBeRetryed = false; // This way the
					// sendScheduleUnExecutedMail may
					// get called.
				}
			}
			if (!toBeRetryed) {
				if (pobjPrEb.getSchId() != 0) {
					REQUEST_TYPE type = null;
					try {
						type = REQUEST_TYPE.resolve(pobjPrEb.getGrpStInd());
					} catch (IllegalArgumentException e) {
						// this should never arise but still..
						objEngineLogger_.error(
								"Unrecognized grouped/standalone indicator "
										+ pobjPrEb.getGrpStInd());
					}
					if (type == REQUEST_TYPE.STANDALONE) {
						try {
							if (objEngineLogger_.isDebugEnabled()) {
								objEngineLogger_
										.debug("Initializing Scheduler.");
							}
							CRequestScheduler crs = new CRequestScheduler(
									pobjPrEb.getSchId(), pobjPrEb, hmParams,
									paramSimpleDateFormat_, paramSimpleTimeFormat_,
									paramSimpleDateTimeFormat_, context_);
							if (objEngineLogger_.isDebugEnabled()) {
								objEngineLogger_
										.debug("Scheduler Initialized.");
							}
							crs.setConnection(pcon);
							if (objEngineLogger_.isDebugEnabled()) {
								objEngineLogger_
										.debug("Trying to Schedule....");
							}
							long lScheduleStartTime = System
									.currentTimeMillis();
							if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
								objEngineLogger_
										.log(
												LogLevel.FINE,
												"{6}Elapsed Time taken to initialize the JOB's associated schedule #"
														+ (lScheduleStartTime - lProcessEndTime)
														+ " milliseconds");
							}
							crs.schedule();
							long lScheduleEndTime = System.currentTimeMillis();
							if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
								objEngineLogger_
										.log(
												LogLevel.FINE,
												"{7}Elapsed Time taken to complete the JOB's associated schedule #"
														+ (lScheduleEndTime - lScheduleStartTime)
														+ " milliseconds");
							}
							bScheduleExecuted = true;
						} finally {
							//TODO something
						}
					} // Request is a STANDALONE Request
				} // Schedule Id is not null
			}
			try {
				if (objProcessRequest_ != null) {
					objEngineLogger_.log(LogLevel.NOTICE,
							"Request Processed for request id "
									+ pobjPrEb.getReqId()
									+ " ...Initiating Clean Up Process..");
					objProcessRequest_.endProcess();
					objEngineLogger_.log(LogLevel.NOTICE,
							"Cleanup Process executed for request id "
									+ pobjPrEb.getReqId());
				}
			} catch (Throwable e) {
				objEngineLogger_
						.error(
								"Caught Exception while updating the request status to Error.",
								e);
				// rare to get an exception. But if it comes then just log it.
			} finally {
				if (objProcessRequest_ != null) {
					objProcessRequest_.close();
				}
			}

			// This has to be done because the user will never know if the
			// schedule
			// was not executed due to any exceptions.
			// The process_request table must be altered to have specific email
			// ids
			// associated for that request for mailing purpose from PRE. This
			// change
			// however is not made as of now but should be done in near future.
			// Kedar...30.06.2004....Atlanta US.
			if (bScheduleExists
					&& pobjPrEb.getGrpStInd().equals(
							REQUEST_TYPE.STANDALONE.getID())
					&& !bScheduleExecuted && !toBeRetryed) {
				objEngineLogger_.log(LogLevel.NOTICE, "Associated schedule #"
						+ pobjPrEb.getSchId()
						+ " could not be executed for the Request #"
						+ pobjPrEb.getReqId());
				// String strEmailIds = ""; // Do not initialize it to null.
				try {
					updateSchedule(SCHEDULE_STATUS.TERMINATED, pcon, pobjPrEb
							.getSchId());
				} catch (Exception e) {
//					objThrowable = e;
						// This is relevent only if there is no primary error
						// encountered. Otherwise,
						// the main error will be hidden.
				}
//				if (CSettings.get("pr.mailnotification", "OFF").equals("ON")) {
//					sendScheduleUnExecutedMail(pobjPrEb.getReqId(), pobjPrEb
//							.getSchId(), pobjPrEb.getJobId(), pobjPrEb
//							.getJobName(), objThrowable, pobjPrEb.getEmailIds());
//				} // CSettings.get("pr.mailnotification", "OFF").equals("ON")
			} // bScheduleExists && !bScheduleExecuted

			// if (objRequestLogger_ != null) {
			// objRequestLogger_.close();
			// }
			if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
				objEngineLogger_.log(LogLevel.FINE,
						"{8}Elapsed Time for job execution #"
								+ (System.currentTimeMillis() - lStartTime)
								+ " milliseconds");
			}
		}

		return bSuccess;
	} // end of processEachRequest

	/**
	 * Returns the parameters associated with the request in a hashtable. </p>
	 * <p>
	 * However before the parameters are populated they are converted into the
	 * datatype mentioned in the Param_Data_Type
	 * 
	 * <ul>
	 * <li>
	 * In case the data type for the field is mentioned as Date, then Param_Val
	 * field should have a string value in the format as mentioned in the
	 * paramdateformat property of pr.properties</li>
	 * <li>
	 * In case the data type for the field is mentioned as Timestamp, then
	 * Param_Val field should have a string value with the date part, in the
	 * format as mentioned in the paramdateformat property of pr.properties, and
	 * with the time part in the format as mentioned in the paramtimeformat
	 * property of pr.properties</li>
	 * <li>
	 * In case the data type for the field is some type of array, then Param_Val
	 * field should have a string value with all the elements of array delimited
	 * by a delimiter as mentioned in the paramarrvaluedelim property of
	 * pr.properties</li>
	 * </ul>
	 * </p>
	 * 
	 * @param pcon
	 *            Connection object
	 * @param plReqId
	 *            Request Id of the request for which the parameters are to be
	 *            fetched
	 * @param pobjDdcParams
	 *            DataContainer object reference for querying table
	 *            PROCESS_REQ_PARAMS
	 * @param pobjParamsCont
	 *            Controller object reference for table PROCESS_REQ_PARAMS
	 * @param pobjParamsEb
	 *            EntityBean object reference for table PROCESS_REQ_PARAMS
	 * @return HashMap object that contains all the parameters after conversion
	 *         to corresponding data type
	 * @throws Exception
	 * 
	 */
	private HashMap<String, Object> fetchRequestParams(Connection pcon,
			long plReqId, CDynamicDataContainer pobjDdcParams,
			ProcessReqParamsController pobjParamsCont,
			ProcessReqParamsEntityBean pobjParamsEb) throws Exception {

		HashMap<String, Object> hmParams = null;
		Object objValue = null;

		try {

			pobjParamsEb.initialize();
			pobjParamsEb.setReqId(plReqId);
			pobjDdcParams.build(pcon, pobjParamsEb);
			pobjDdcParams.addOrderByClause("ORDER BY param_no ASC");

			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_
						.debug("Querying for parameters for this request ....");
			}
			pobjDdcParams.executeQuery(pcon, pobjParamsCont, pobjParamsEb);

			if (pobjDdcParams.getTotalRows() > 0) {
				if (objEngineLogger_.isDebugEnabled()) {
					objEngineLogger_.debug(pobjDdcParams.getTotalRows()
							+ " parameters defined for this request ....");
				}
				hmParams = new LinkedHashMap<String, Object>();

				while (pobjDdcParams.next()) {
					pobjParamsEb = (ProcessReqParamsEntityBean) pobjDdcParams
							.get();
					objValue = convertToReqDataType(pobjParamsEb.getParamVal(),
							pobjParamsEb.getParamDataType());
					hmParams.put(pobjParamsEb.getParamFld(), objValue);
				}
			} else {
				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_
							.info("No parameters defined for this request ....");
				}
			}

		} catch (CProcessRequestEngineException cpree) {
			throw cpree;
		} catch (Exception e) {
			objEngineLogger_.error(
					"Caught exception while fetching parameters.", e);
			throw e;
		} finally {
		}

		return hmParams;
	} // end of fetchRequestParams

	/**
	 * Instantiate an object of the class, mentioned in the process_class_nm
	 * field of the PROCESS_REQUEST table.
	 * 
	 * This class is loaded using a custom classloader that loads the latest
	 * version of the class file from disk. The new object is then cast to
	 * IProcessRequest. If the cast fails an appropriate exception is logged in
	 * the Engine and Request Log
	 * 
	 * @param pstrClassName
	 *            Class name to be instantiated.
	 * @return An object reference to IProcessRequest interface
	 * @throws LinkageError
	 * @throws CProcessRequestEngineException
	 *             when instantiation or casting fails
	 * 
	 */
	private ProcessRequestServicer instantiateReqProcessObject(String pstrClassName) throws LinkageError,
			CProcessRequestEngineException {

		ProcessRequestServicer objProcessRequest = null;

		try {

			long lLoadingClassStartTime = System.currentTimeMillis();
			ClassLoader pcl = (CCustomClassLoaderFactory.getInstance())
					.getClassLoader(this);

			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_.debug("Loading PRE class " + pstrClassName);
			}

			Class<?> objClass = pcl.loadClass(pstrClassName);
			long lLoadingClassEndTime = System.currentTimeMillis();
			if (objEngineLogger_.isEnabledFor(LogLevel.FINER)) {
				objEngineLogger_.log(LogLevel.FINER, "Loaded PRE " + objClass);
			}
			if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
				objEngineLogger_
						.log(
								LogLevel.FINE,
								"{2}Elapsed Time to Load Class "
										+ objClass
										+ " #"
										+ (lLoadingClassEndTime - lLoadingClassStartTime));
			}
			Object obj = objClass.newInstance();
			if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
				objEngineLogger_
						.log(
								LogLevel.FINE,
								"{3}Elapsed Time to instantiate Class "
										+ objClass
										+ " #"
										+ (System.currentTimeMillis() - lLoadingClassEndTime));
			}

			if (obj instanceof ProcessRequestServicer) {
				objProcessRequest = (ProcessRequestServicer) obj;
			} else {
				throw new CProcessRequestEngineException(
						"The business object in the request does not implement IProcessRequest");
			}

		} catch (LinkageError le) {
			objEngineLogger_.error(le.getMessage(), le);
			throw le;
		} catch (Exception e) {
			objEngineLogger_.error(e.getMessage(), e);
			throw new CProcessRequestEngineException(e.getMessage());
		} finally {

		}

		return objProcessRequest;
	} // end of instantiateReqProcessObject

	/**
	 * Converts String value passed as the first parameter into the data type as
	 * specified by the second parameter
	 * 
	 * 
	 * @param pstrValue
	 *            String value that has to be converted
	 * @param pstrReqDataType
	 *            Data Type to which the string value has to be converted
	 * @return converted object
	 * @throws CProcessRequestEngineException
	 *             when conversion fails
	 * 
	 */
	private Object convertToReqDataType(String pstrValue, String pstrReqDataType)
			throws CProcessRequestEngineException {
		try {
			if (pstrValue == null || pstrValue.equals("")) {
				return null;
			}
			PREDataType type = PREDataType.resolve(pstrReqDataType);
			if (type == null) {
				throw new CProcessRequestEngineException(
						"Unknown Data Type associated with the parameter. Unable to convert.");
			}
			switch (type) {
			case BOOLEAN:
				return "true".equalsIgnoreCase(pstrValue);
			case INTEGER:
				return new Integer(pstrValue);
			case LONG:
				return new Long(pstrValue);
			case DOUBLE:
				return new Double(pstrValue);
			case DATE:
				return paramSimpleDateFormat_.parse(pstrValue);
			case TIMESTAMP:
				return new Timestamp(paramSimpleDateTimeFormat_.parse(pstrValue).getTime());
//				return CDate
//						.getUDFTimestamp(pstrValue, strParamDateTimeFormat_);
			case STRING:
				return pstrValue;
			default:
				return convertStringToObjectArray(pstrValue, type,
						strParamArrValueDelim_);
			}
		} catch (CProcessRequestEngineException cpree) {
			throw cpree;
		} catch (Exception e) {
			throw new CProcessRequestEngineException(exceptionToString(e));
		}

	} // end of convertToReqDataType

	/**
	 * Converts delimited string into the appropriate type of array as specified
	 * 
	 * 
	 * @param pstrInput
	 *            Delimited string that has to be converted to an appropriate
	 *            type of array
	 * @param pDataType
	 *            Data type of the array
	 * @param pstrArrDelim
	 *            Delimiter used by pstrInput parameter to delimit elements
	 * @return Object array with elements converted into an appropriate type as
	 *         specified in pstrDataType
	 * @throws Exception
	 *             When conversion fails
	 * 
	 */
	private Object[] convertStringToObjectArray(String pstrInput,
			PREDataType pDataType, String pstrArrDelim) throws Exception {

		Object[] objRetValArr = null;
		int iElements = 0;

		StringTokenizer stzStr = new StringTokenizer(pstrInput, pstrArrDelim);
		switch (pDataType) {
		case BOOLEAN_ARRAY:
			objRetValArr = new Boolean[stzStr.countTokens()];
			while (stzStr.hasMoreTokens()) {
				objRetValArr[iElements] = "true".equalsIgnoreCase(stzStr
						.nextToken());
				iElements++;
			}
			break;
		case STRING_ARRAY:
			objRetValArr = new String[stzStr.countTokens()];
			while (stzStr.hasMoreTokens()) {
				objRetValArr[iElements] = stzStr.nextToken();
				iElements++;
			}
			break;
		case INTEGER_ARRAY:
			objRetValArr = new Integer[stzStr.countTokens()];
			while (stzStr.hasMoreTokens()) {
				objRetValArr[iElements] = Integer.valueOf(stzStr.nextToken());
				iElements++;
			}
			break;
		case LONG_ARRAY:
			objRetValArr = new Long[stzStr.countTokens()];
			while (stzStr.hasMoreTokens()) {
				objRetValArr[iElements] = Long.valueOf(stzStr.nextToken());
				iElements++;
			}
			break;
		case DOUBLE_ARRAY:
			objRetValArr = new Double[stzStr.countTokens()];
			while (stzStr.hasMoreTokens()) {
				objRetValArr[iElements] = Double.valueOf(stzStr.nextToken());
				iElements++;
			}
			break;
		case DATE_ARRAY:
			objRetValArr = new java.sql.Date[stzStr.countTokens()];
			while (stzStr.hasMoreTokens()) {
				objRetValArr[iElements] = paramSimpleDateFormat_.parse(stzStr.nextToken());
				iElements++;
			}
			break;
		default: // timestamp
			objRetValArr = new java.sql.Timestamp[stzStr.countTokens()];
			while (stzStr.hasMoreTokens()) {
				objRetValArr[iElements] = new java.sql.Timestamp(paramSimpleDateTimeFormat_.parse(stzStr.nextToken()).getTime());
				iElements++;
			}
			break;
		}
		return objRetValArr;
	} // convertStringToObjectArray

	/**
	 * Updates req_stat field and other appropriate fields in PROCESS_REQUEST
	 * table depending on the preqStat parameter passed
	 * 
	 * @param pcon
	 *            Connection object
	 * @param pobjPrCont
	 *            Controller object reference for table PROCESS_REQUEST
	 * @param pobjPrEb
	 *            EntityBean object reference for table PROCESS_REQUEST
	 * @param preqStat
	 *            status value that is to be updated
	 * @param preqLogFileUrl
	 *            Request Log File Url to be updated, only if status is Success
	 *            / Error
	 * @throws Exception
	 */
	// Whether synchronized must be there or not Should be debated.
	private synchronized void updateRequestStatus(Connection pcon,
			ProcessRequestController pobjPrCont,
			ProcessRequestEntityBean pobjPrEb, // this variable is not being
			// used...Kedar...30/01/2004
			REQUEST_STATUS preqStat, String preqLogFileUrl) throws Exception {
		ProcessRequestController objPRController = null;
		try {
			RequestStatusVO vo = new RequestStatusVO();
			vo.setReqId(pobjPrEb.getReqId());
			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_.debug("Begin Transaction ...");
			}
			pcon.setAutoCommit(false);

			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_.debug("Update request table with status as '"
						+ preqStat + "' ....");
			}
			if (preqStat == REQUEST_STATUS.LAUNCHING) {

				pobjPrEb.setReqStartDt(getCurrentTimestamp(pcon));
				vo.setStatus(preqStat.getID());
				context_.addOrUpdate(vo);

			} else if (preqStat == REQUEST_STATUS.COMPLETED
					|| preqStat == REQUEST_STATUS.ERROR) {
				pobjPrEb.setReqEndDt(getCurrentTimestamp(pcon));
				pobjPrEb.setReqLogfileNm(preqLogFileUrl);
				pobjPrEb.setVerboseTimeElapsed(stg.utils.Day
						.verboseTimeDifference(pobjPrEb.getReqEndDt(), pobjPrEb
								.getReqStartDt()));
				context_.remove(vo);
			} else {
				vo.setStatus(preqStat.getID());
				context_.addOrUpdate(vo);
			}

			pobjPrEb.setReqStat(preqStat.getID());
			pobjPrEb.setRStatus(false);

			objPRController = new ProcessRequestController(pcon);
			// if (!pobjPrCont.update(pobjPrEb)) {
			// throw new
			// Exception("Updation of request processing status failed ....");
			// }
			if (!objPRController.update(pobjPrEb)) {
				throw new Exception(
						"Updation of request processing status failed ....");
			}

			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_.debug("Commit Transaction ...");
			}
			pcon.commit();

		} catch (Exception e) {

			try {
				objEngineLogger_.error("Rollback Transaction ...", e);
				pcon.rollback();
			} catch (Exception ex) {
				throw ex;
			}

			throw e;
		} finally {
			if (objPRController != null) {
				objPRController.close();
			}
			try {
				if (objEngineLogger_.isDebugEnabled()) {
					objEngineLogger_.debug("Setting AutoCommit to true ...");
				}
				pcon.setAutoCommit(true);
			} catch (SQLException sqe) {
				throw sqe;
			}
		}

	}

	/**
	 * Empties the Connection pool and closes the engine log file
	 */
	private void closeAll() {
		objEngineLogger_.log(LogLevel.NOTICE, "Initiating Clean Shutdown");

		if (lStuckThreadMonitorInterval_ > 0) {
			objEngineLogger_.log(LogLevel.NOTICE, "Stopping Monitor Thread");
			if (tMonitorThread_.isAlive()) {
				tMonitorThread_.interrupt();
			} else {
				objEngineLogger_.log(LogLevel.NOTICE,
						"Monitor Thread already stopped.");
			}
		}
		while (!(vectorThreads_.isEmpty() && vectorThreadsGroup_.isEmpty())) // bGroupedEngineTerminationConfirmed_))
		{
			objEngineLogger_.log(LogLevel.NOTICE,
					"Waiting while the Current processes are finished....");
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Total Standalone Processes running "
						+ vectorThreads_.size());
				objEngineLogger_.info("Total Grouped    Processes running "
						+ vectorThreadsGroup_.size());
				objEngineLogger_
						.info("Shutdown Hook Sleeping for 10 minutes...");
			}
			try {
				if (tInterrupt_ != null) {
					Thread.yield();
					TimeUnit.MINUTES.sleep(10);
					// Thread.sleep(600000l);
					// The following two lines are commented as suggested by
					// Eclipse editor. Instead of these
					// two lines the above two lines are used. We should get the
					// same results....Kedar..23/09/2003
					// tInterrupt_.yield();
					// tInterrupt_.sleep(600000l);
				}
			} catch (InterruptedException ie) {
				// dummy exception. do nothing. The Running process will
				// interrupt this thread.
			}
		}
		if (tMonitorThread_ != null) {
			tMonitorThread_.interrupt();
			while (tMonitorThread_.isAlive()) {
			}
		}

		// if (objEngineLogger_.isInfoEnabled()) {
		// objEngineLogger_.info("Closing PreparedStatements ...");
		// }
		// if (objPrCont_ != null) {
		// objPrCont_.close();
		// }

		try {
			if (dataSourceFactory_ != null) {
				try {
					if (staticConnection_ != null) {
						staticConnection_.close();
					}
				} catch (SQLException e) {
					// No need to throw exception.
				}
				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_.info("Emptying Data Sources ....");
				}
				if (dataSourceFactory_.shutdown()) {
					objEngineLogger_
							.info("All Data Source emptied successfully.");
				}
			}

		} catch (Exception e) {
			objEngineLogger_.error(e);
			// No need to throw exception. PRE is closing down.
		}

		// if (objEngineLogger_ != null) {
		// objEngineLogger_.info("Closing System Log ....");
		// objEngineLogger_.close();
		// }
		// if (httpserver_.isStarted())
		// {
		// if (objEngineLogger_.isInfoEnabled()) {
		// objEngineLogger_.info("Stoping Web Service");
		// }
		// httpserver_.stop();
		// }
		stopWebServer();
		if (!Thread.currentThread().getName().equals("shutdown")) {
			if (Hazelcast.getCluster().getMembers().size() == 1) {
				printPossibleMemoryLeaks();
			}
		}
		Iterator<Service> iter = services_.values().iterator();
		while (iter.hasNext()) {
		    Service instance = iter.next();
		    instance.destroy(context_);
		    iter.remove();
		}
//		if (reportService != null) {
//			reportService.shutdown();
//			if (objEngineLogger_.isInfoEnabled()) {
//				objEngineLogger_
//						.info("Waiting for the current reports, if any, to finish.");
//			}
//			while (!reportService.isTerminated()) {
//				try {
//					TimeUnit.SECONDS.sleep(20);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				if (objEngineLogger_.isInfoEnabled()) {
//					objEngineLogger_.info("Waiting for Active("
//							+ reportService.getCurrentActiveCount()
//							+ ") and Pending("
//							+ reportService.getCurrentPendingQueueSize()
//							+ ") reports to be completed.");
//				}
//			}
//		}
		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("Engine Termination Confirmed....");
		}

		// bEngineTerminationConfirmed_ = true;
	}

	/**
	 * This method is called when the engine is terminated
	 * 
	 * This method is called from the thread, that is attached to the shutdown
	 * hook,when CTRL-C is pressed. The moment this method is called
	 * bEngineTerminated_ is set to true and this method waits until the main
	 * thread sets the bEngineTerminationConfirmed_ to true. Once
	 * bEngineTerminationConfirmed_ is set to true, closeAll() method is called
	 * which releases all resources
	 * 
	 */
	private void terminateEngine() {
		// a good practice.
		if (tMain_ != null) {
			objEngineLogger_.log(LogLevel.NOTICE,
					"Interrupting main engine thread ...");
//			try {
//				LicenseVerifier.verify(readLicense());
//			} catch (Exception e) {
//				if (stopMessageForEmail_ != null
//						|| !"".equals(stopMessageForEmail_)) {
//					stopMessageForEmail_ = stopMessageForEmail_
//							+ "\n\nLicense has expired. Please contact the help desk and get it re-issued.\n";
//				} else {
//					stopMessageForEmail_ = "\n\nLicense has expired. Please contact the help desk and get it re-issued.\n";
//				}
//			}
			if (stopMessageForEmail_ == null) {
				stopMessageForEmail_ = "Engine is being stopped due to JVM is being terminated. "
						+ "Such a situation may arise by pressing ctrl+c.";
			}
			// int iRebootCnt = (iRebootCounter < 0)?0:iRebootCounter;
			StringBuffer sbuffer = getDefaultEmailAttributes();
			sbuffer.append("Stopped On      :");
			sbuffer.append(new Date());
			sbuffer.append(NEW_LINE);
			sbuffer.append(NEW_LINE);
			sbuffer.append("Engine is being terminated due to ");
			sbuffer.append(NEW_LINE);
			sbuffer.append(NEW_LINE);
			sbuffer.append(stopMessageForEmail_);
			if (isReboot() && getRebootCounter() >= getRebootMaxCounter()) { // This
				// condition
				// is
				// because
				// the
				// controller
				// will
				setReboot(false); // update the counter by 1 and then it will
				// not
			} // initiate a reboot.
			if (isReboot()) {
				createIniFile(CStartEngine.REBOOT, getRebootCounter());
				sbuffer.append(NEW_LINE);
				sbuffer.append(NEW_LINE);
				sbuffer
						.append("Engine will attempt a Reboot. Please do not try to start the engine manually. ");
				sbuffer.append("Engine will wait for ");
				// if ((double)lRebootSleepTime / 60000 < 1) {
				// sbuffer.append("#" + lRebootSleepTime +
				// " milliseconds before attempting restart.");
				// } else {
				sbuffer.append("#" + getRebootSleepTime()
						+ " minute(s) before attempting restart.");
				// }
				// sbuffer.append(NEW_LINE);
				// sbuffer.append(NEW_LINE);
				// sbuffer.append("Attempt #");
				// sbuffer.append(iRebootCounter);
			} else {
				sbuffer.append(NEW_LINE);
				sbuffer.append(NEW_LINE);
				sbuffer.append("Contact help desk !!");
			}
			sendDefaultMail("Engine is being terminated."
					+ ((isReboot()) ? " Engine will attempt Reboot sequence."
							: ""), sbuffer.toString(), false);
			tMain_.interrupt();
			if (bGroupEngineToBeStarted_) {
				tEngine_.interrupt();
			}
			bEngineTerminated_.set(true);
			bGroupedEngineTerminated_.set(true);
			// waiting for the engine thread to complete its activity and
			// confirm
			closeAll();
			// while (!bEngineTerminationConfirmed_) {
			// try {
			// objEngineLogger_.debug("Waiting for all the other processes are terminated.");
			// this.wait();
			// } catch (InterruptedException e) {
			// objEngineLogger_.debug("Interrupted. Check if engine termination is confirmed.");
			// //do nothing.
			// }
			// }
			if (tInterrupt_ != Thread.currentThread()) {
				Runtime.getRuntime().removeShutdownHook(tInterrupt_);
			}

			// if (objEngineLogger_.isDebugEnabled()) {
			// objEngineLogger_.debug("Waiting for " +
			// CMailer.getCurrentlyRunningAsychMailThreadCount() +
			// " Asynchronous email(s) to be transported..");
			// }
			// while (CMailer.getCurrentlyRunningAsychMailThreadCount() > 0) {
			// //wait..do nothing This being a daemon thread will kill the non
			// daemon threads.
			// }
			// if (objEngineLogger_.isDebugEnabled()) {
			// objEngineLogger_.debug("Transported all Asynchronous Emails....");
			// }
			CSettings.getInstance().destroy();
			// CConnectionPoolManager.getInstance().destroy(true);
			// instance_ = null;
			// tMain_ = null;
	        Iterator<Service> iter = services_.values().iterator();
	        while (iter.hasNext()) {
	            Service instance = iter.next();
	            instance.destroy(context_);
	            iter.remove();
	        }
//			if (reportService != null) {
//				reportService.shutdown();
//				while (!reportService.isTerminated()) {
//					try {
//						TimeUnit.SECONDS.sleep(5);
//						if (objEngineLogger_.isInfoEnabled()) {
//							objEngineLogger_.info("Waiting for the BIRT Report service to terminate. Current pending report count #" + 
//									reportService.getCurrentActiveCount() + reportService.getCurrentPendingQueueSize());
//						}
//					} catch (InterruptedException e) {
//						//
//					}
//				}
//				try {
//					reportService.destroy();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			objEngineLogger_.log(LogLevel.NOTICE, "Engine Terminated.");
		} else {
			// objEngineLogger_.error("Engine thread null ...");
		}
	}

	/**
	 * This inner class extends Thread and executes different StandAlone
	 * requests.
	 * 
	 */
	class ThreadProcess extends Thread implements IJOBMonitor {
		/**
		 * Stores the REVISION number of the class from the configuration
		 * management tool.
		 */
		public final String REVISION = "$Revision:: 3829              $";

//		private PrintWriter objRequestLogger_;
		private ProcessRequestServicer objProcessRequest_;
		private Connection con_;
		private ProcessRequestController objPrCont_;
		private ProcessRequestEntityBean objPrEb_;
		private CDynamicDataContainer objDdcParams_;
		private ProcessReqParamsController objParamsCont_;
		private ProcessReqParamsEntityBean objParamsEb_;
		private String strLogFileName;
		private String strLogFileNameURL;
		// private IDataSourceFactory dataSourceFact_;

		private long lStartTimeInMillis_ = -1;

		private JOB_STATUS jobStatus_ = JOB_STATUS.NORMAL;

		private long jobCheckedAtTime_;

		private long lActualInitializedTimeInMillis_;

		private long lProcessEndTime_;

		private boolean failedOver;

		public ThreadProcess() {
			super();
			lActualInitializedTimeInMillis_ = System.currentTimeMillis();
		}

		public ThreadProcess(String name) {
			super(name);
			lActualInitializedTimeInMillis_ = System.currentTimeMillis();
		}

		public ProcessRequestServicer getIntantiatedObject() {
			return objProcessRequest_;
		}

		public void setConnection(Connection pcon) {
			con_ = pcon;
		}

		public void setProcessRequestEntityBean(
				ProcessRequestEntityBean pobjPREB) {
			objPrEb_ = pobjPREB;
		}

		public void setLogFileName(String pstrFileName) {
			strLogFileName = pstrFileName;
		}

		public void setLogFileNameURL(String pstrLogFileNameURL) {
			strLogFileNameURL = pstrLogFileNameURL;
		}

		public void setFailedOver(boolean failOver) {
			this.failedOver = failOver;
		}

		public void setPool(IDataSourceFactory dataSourceFactory_) {
			// dataSourceFact_ = dataSourceFactory_;
		}

		public void run() {
			lStartTimeInMillis_ = lProcessEndTime_ = System.currentTimeMillis(); // just
			// initializing
			// the
			// process
			// end
			// time
			if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
				objEngineLogger_
						.log(
								LogLevel.FINE,
								"{1} Thread Wait Time #"
										+ (lStartTimeInMillis_ - lActualInitializedTimeInMillis_)
										+ " milliseconds...");
			}
			boolean bScheduleExists = false;
			boolean bErrorBeforeCallingProcessRequest = true; // default will be
			// true.
			Throwable objThrowable = new Throwable();
			try {
				if (objPrEb_.getSchId() > 0) {
					bScheduleExists = true;
				}
				con_ = dataSourceFactory_.getDataSource(
						CSettings.get("pr.dsforstandaloneeng")).getConnection();
				objPrCont_ = new ProcessRequestController(con_);
				objParamsCont_ = new ProcessReqParamsController(con_);
				objParamsEb_ = new ProcessReqParamsEntityBean();
				objDdcParams_ = new CDynamicDataContainer();

				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_.info("Initialize Request Log File ....");
				}

				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_
							.info("Instantiate the Process Class which has been requested .....");
				}
				objProcessRequest_ = instantiateReqProcessObject(
						objPrEb_.getProcessClassNm());
				objProcessRequest_.setFailedOver(failedOver);

				// If this boolean variable "bErrorBeforeCallingProcessRequest"
				// is true in finally block then it means
				// that the call to the method processEachRequest has not gone
				// thus the schedule is not done.
				// So send MAIL.
				bErrorBeforeCallingProcessRequest = false;
				processEachRequest(con_, objPrCont_, objPrEb_, objDdcParams_,
						objParamsCont_, objParamsEb_,
						objProcessRequest_,
						new File(strLogFileName), strLogFileNameURL);
				lProcessEndTime_ = System.currentTimeMillis();
				// }
				// catch (InterruptedException ie)
				// {
				// objThrowable = ie;
				// try {
				// if (objEngineLogger_.isInfoEnabled()) {
				// objEngineLogger_.info("Update Request Status to Error due to Exception.");
				// }
				// updateRequestStatus(staticConnection_, objPrCont_, objPrEb_,
				// IProcessRequest.ERROR_STATUS, strLogFileNameURL);
				// } catch (Exception ex) {
				// objEngineLogger_.error(ex);
				// }
				// objEngineLogger_.log(LogLevel.NOTICE,
				// "Thread Process is being Killed. Initiating clean shutdown of the process. Request Id #"
				// + objPrEb_.getReqId(), ie);
				// }
				// catch (Exception e)
				// {
				// objThrowable = e;
				// try {
				// if (objEngineLogger_.isInfoEnabled()) {
				// objEngineLogger_.info("Update Request Status to Error due to Exception.");
				// }
				// updateRequestStatus(staticConnection_, objPrCont_, objPrEb_,
				// IProcessRequest.ERROR_STATUS, strLogFileNameURL);
				// } catch (Exception ex) {
				// objEngineLogger_.error(ex);
				// }
				// throw new IllegalArgumentException(e.getMessage());
				// }
				// catch (Error error)
				// {
				// objThrowable = error;
				// try {
				// if (objEngineLogger_.isInfoEnabled()) {
				// objEngineLogger_.info("Update Request Status to Error due to Exception.");
				// }
				// updateRequestStatus(staticConnection_, objPrCont_, objPrEb_,
				// IProcessRequest.ERROR_STATUS, strLogFileNameURL);
				// } catch (Exception ex) {
				// objEngineLogger_.error(ex);
				// }
				// throw new IllegalArgumentException(error.getMessage());
			} catch (Throwable throwable) {
				lProcessEndTime_ = System.currentTimeMillis();
				objThrowable = throwable;
				try {
					if (objEngineLogger_.isInfoEnabled()) {
						objEngineLogger_
								.info("Update Request Status to Error due to Exception.");
					}
					if (throwable instanceof ThreadDeath) {
						updateRequestStatus(staticConnection_, objPrCont_,
								objPrEb_, REQUEST_STATUS.KILLED,
								strLogFileNameURL);
					} else {
						updateRequestStatus(staticConnection_, objPrCont_,
								objPrEb_, REQUEST_STATUS.ERROR,
								strLogFileNameURL);
					}
				} catch (Exception ex) {
					objEngineLogger_.error(
							"Unable to update request status to Error", ex);
				}
				if (throwable instanceof RuntimeException) {
					throw (RuntimeException) throwable;
				} else if (throwable instanceof Error) {
					throw (Error) throwable;
				}
				throw new IllegalArgumentException(throwable.getMessage(),
						throwable);
			} finally {
				if (objParamsCont_ != null) {
					try {
						objParamsCont_.close();
					} catch (SQLException e) {
						objEngineLogger_.error(
								"Exception caught while closing PRPController",
								e);
					}
				}

				if (objPrCont_ != null) {
					try {
						objPrCont_.close();
					} catch (SQLException e) {
						objEngineLogger_.error(
								"Exception caught while closing PRController",
								e);
					}
				}
				objProcessRequest_ = null;
				// This has to be done because the user will never know if the
				// schedule
				// was not executed due to any exceptions.
				// The process_request table must be altered to have specific
				// email ids
				// associated for that request for mailing purpose from PRE.
				// This change
				// however is not made as of now but should be done in near
				// future.
				// Kedar...01.07.2004....Atlanta US.
				if (bScheduleExists && bErrorBeforeCallingProcessRequest) {
					objEngineLogger_
							.log(
									LogLevel.NOTICE,
									"Associated schedule #"
											+ objPrEb_.getSchId()
											+ " could not be executed for the Request #"
											+ objPrEb_.getReqId());
					try {
						updateSchedule(SCHEDULE_STATUS.TERMINATED, con_,
								objPrEb_.getSchId());
					} catch (Exception e) {
						// This exception is already logged so no need to be
						// logged.
						if (objThrowable != null) {
							objThrowable = e; // This is relevent only if there
							// is no primary error
							// encountered. Otherwise,
							// the main error will be hidden.
						}
					}
					if (CSettings.get("pr.mailnotification", "OFF")
							.equals("ON")) {
						sendScheduleUnExecutedMail(objPrEb_.getReqId(),
								objPrEb_.getSchId(), objPrEb_.getJobId(),
								objPrEb_.getJobName(), objThrowable, objPrEb_
										.getEmailIds());
					} // CSettings.get("pr.mailnotification",
					// "OFF").equals("ON")
				} // bScheduleExists && !bScheduleExecuted
				try {
					con_.close();
				} catch (SQLException e1) {
					// No need to throw exception.
				}
				if (jobStatus_ == JOB_STATUS.STUCK && iStuckThreads_.get() > 0) {
					// This variable is reduced by -1 as the stuck thread has
					// finished its job.
					iStuckThreads_.decrementAndGet();
				} else if ((jobStatus_ == JOB_STATUS.CRITICAL || jobStatus_ == JOB_STATUS.TOBETERMINATED)
						&& iStuckThreadThatCrossMaxLimit_.get() > 0) {
					// This variable is reduced by -1 as the stuck thread has
					// finished its job.
					iStuckThreadThatCrossMaxLimit_.decrementAndGet();
				}

				// nullifying the variables.
				objPrCont_ = null;
				objPrEb_ = null;
				objDdcParams_ = null;
				objParamsCont_ = null;
				objParamsEb_ = null;
				strLogFileName = null;
				strLogFileNameURL = null;
				removeStandAloneProcess(this);
				if (vectorThreads_.size() == 0 && bEngineTerminated_.get()) {
					// try {
					// if (pool_ != null) {
					// // pool_.stopShrinking();
					// pool_.emptyPool(CSettings.get("pr.dsforstandaloneeng"));
					// }
					//                        
					// } catch (Exception e) {
					// objEngineLogger_.error(e);
					// }
					if (objEngineLogger_.isDebugEnabled()) {
						objEngineLogger_
								.debug("StandAlone: Iterrupting Shutdown Hook...");
					}
					if (tInterrupt_ != null) {
						// Earlier this thread if not null was then Alive as
						// well. But because of Reboot sequence
						// This thread can be not null but not alive and
						// therfore this condition is added.
						if (tInterrupt_.isAlive()) {
							tInterrupt_.interrupt();
						} else {
							tMain_.interrupt();
						}
					} else {
						tMain_.interrupt();
					}
					if (lStuckThreadMonitorInterval_ > 0) {
						tMonitorThread_.interrupt();
					}
				} // vectorThreads_.size() == 0 && bEngineTerminated_
				if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
					long lTime = System.currentTimeMillis();
					objEngineLogger_.log(LogLevel.FINE,
							"{9}Clean-up Time delay for the Thread #"
									+ (lTime - lProcessEndTime_)
									+ " milliseconds...");
					objEngineLogger_.log(LogLevel.FINE,
							"{10}Elapsed Time for the Thread run() #"
									+ (lTime - lActualInitializedTimeInMillis_)
									+ " milliseconds..");
				}
			} // End of finally block.
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IJOBMonitor#getInitializedTime()
		 */
		public long getInitializedTime() {
			return lActualInitializedTimeInMillis_;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getStartTime()
		 */
		public long getStartTime() {
			if (lStartTimeInMillis_ != -1) {
				return lStartTimeInMillis_;
			}
			return -1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getConnection()
		 */
		public Connection getConnection() {
			return con_;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getStuckThreadLimit()
		 */
		public long getStuckThreadLimit() {
			return (TimeUnit.MINUTES.toMillis(objPrEb_.getStuckThreadLimit()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getStuckThreadMaxLimit()
		 */
		public long getStuckThreadMaxLimit() {
			return (TimeUnit.MINUTES
					.toMillis(objPrEb_.getStuckThreadMaxLimit()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getRequestBean()
		 */
		public ProcessRequestEntityBean getRequestBean() {
			return objPrEb_;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getUserId()
		 */
		public String getUserId() {
			return objPrEb_.getUserId();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#markStuckThread(boolean)
		 */
		public void setJobStatus(JOB_STATUS status) {
		    switch (status) {
                case STUCK:
                    iStuckThreads_.incrementAndGet();
                
                    break;
                case CRITICAL:
                    iStuckThreads_.decrementAndGet(); // Reduce the thread from the Stuck Thread as
                    iStuckThreadThatCrossMaxLimit_.incrementAndGet();
                    break;
                default:
                    break;
            }
            jobStatus_ = status;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#setMonitorCheckTime(long)
		 */
		public void setMonitorCheckTime(long time) {
			this.jobCheckedAtTime_ = time;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getMonitorCheckTime()
		 */
		public long getMonitorCheckTime() {
			return jobCheckedAtTime_;
		}

        @Override
        public JOB_STATUS getJobStatus() {
            return jobStatus_;
        }
	} // end of inner class ThreadProcess

	/**
	 * Starts the service engine for the Grouped Requests. Gets the connection
	 * from the pool and Continuously scans the PROCESS_REQUEST table for queued
	 * grouped requests and executes them sequentially
	 * 
	 * <p>
	 * Scanning continues in a loop until the tEngine_ thread is interrupted.
	 * </p>
	 * <p>
	 * The query picks up requests, from the table PROCESS_REQUEST, that have
	 * the Req_Stat='Q' and GRP_ST_IND = 'G'. If requests are found then each
	 * record is processed sequentially depending on the sequence given in the
	 * group. If no records are found then the engine waits for a specified time
	 * (picked up from property file) and then resumes scanning
	 * </p>
	 * 
	 */
	private void startGroupServiceEngine() {

		if (objEngineLogger_.isInfoEnabled()) {
			objEngineLogger_.info("Starting Group Service Engine...");
		}

		Connection conForGroupedRequest = null;
		PreparedStatement psm = null;
		PreparedStatement psmu = null;
		try {
			objEngineLogger_.log(Level.INFO,
					"Waiting for the cluster handshake.");
			while (!bClusterHandShakeDone_) {
				// wait do nothing.
				if (bEngineTerminated_.get() || bGroupedEngineTerminated_.get()) {
					tEngine_.interrupt();
					break; // break the while loop.
				}
			}
			if (!(bEngineTerminated_.get() || bGroupedEngineTerminated_.get())) {
				if (objEngineLogger_.isDebugEnabled()) {
					objEngineLogger_
							.debug("Getting Connection from the pool ....");
				}
				// conForGroupedRequest =
				// poolManager_.getConnection(CSettings.get("pr.dsforgroupeng"));
				conForGroupedRequest = dataSourceFactory_.getDataSource(
						CSettings.get("pr.dsforgroupeng")).getConnection();

				psmu = conForGroupedRequest
						.prepareStatement("UPDATE process_request SET req_stat = ? WHERE grp_id = ? AND req_stat = ?");
				psm = conForGroupedRequest
						.prepareStatement("Select distinct grp_id from process_request where grp_st_ind = ? and req_stat = ? and scheduled_time <= ?");

				objEngineLogger_.log(LogLevel.NOTICE,
						"Group Service Engine Started....");
			}

			while (!tEngine_.isInterrupted()) {
				boolean bTerminate = false;
//				try {
//					LicenseVerifier.verify(readLicense());
//				} catch (LicenseContentException lce) {
//					bTerminate = true;
//					objEngineLogger_.fatal(lce.getLocalizedMessage());
//				} catch (Exception e) {
//					bTerminate = true;
//					objEngineLogger_.fatal(e.getLocalizedMessage());
//				}
				if (bTerminate) {
					if (tEngine_ != null) {
						tEngine_.interrupt();
					}
					break;
				}

				ResultSet rs = null;
				try {
					boolean bDoesRequestExists = false;
					if (!bGroupedEngineTerminated_.get()) {
						psm.clearParameters();
						psm.setString(1, "G");
						psm.setString(2, REQUEST_STATUS.QUEUED.getID());
						psm.setTimestamp(3,
								getCurrentTimestamp(conForGroupedRequest));
						rs = psm.executeQuery();
						while (rs.next() && !tEngine_.isInterrupted()) {
							if (iGrpThreadCounter_.get() <= iGroupMaximumThread_) {
								long lGroupId = rs.getLong(1);
								bDoesRequestExists = true;
								try {
									if (objEngineLogger_.isInfoEnabled()) {
										objEngineLogger_
												.info("Begin Group Transaction ...");
									}
									conForGroupedRequest.setAutoCommit(false);
									if (objEngineLogger_.isDebugEnabled()) {
										objEngineLogger_
												.debug("Update Status to... "
														+ REQUEST_STATUS.LAUNCHING
																.getDescription());
									}
									psmu.setString(1, REQUEST_STATUS.LAUNCHING
											.getID());
									psmu.setLong(2, lGroupId);
									psmu.setString(3, REQUEST_STATUS.QUEUED
											.getID());
									psmu.executeUpdate();
									if (objEngineLogger_.isDebugEnabled()) {
										objEngineLogger_
												.debug("Commit Transaction ...");
									}
									conForGroupedRequest.commit();
									//
								} catch (Exception e) {
									try {
										objEngineLogger_.error(
												"Rollback Transaction ...", e);
										conForGroupedRequest.rollback();
									} catch (Exception ex) {
										throw ex;
									}

									throw e;
								} finally {
									try {
										conForGroupedRequest
												.setAutoCommit(true);
									} catch (Exception ex) {
										if (objEngineLogger_
												.isEnabledFor(Level.WARN)) {
											objEngineLogger_
													.warn(
															"Dummy Exception and can be ignored.",
															ex);
										}
									}
								}
								CGroupedThreadProcess cgtp = new CGroupedThreadProcess(
										"Thread-Grp#" + lGroupId);
								cgtp.setGroupId(lGroupId);
								addGroupProcess(cgtp);
								cgtp.start();
								// startServiceForGroupedRequests(conForGroupedRequest,
								// rs.getLong(1));
							}
							if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
								objEngineLogger_
										.log(
												LogLevel.FINE,
												"Group Engine: Maximum thread spawning capacity (#"
														+ (iGrpThreadCounter_.get() - 1)
														+ ") has reached. Going into Wait mode till one of the JOB gets over.");
							}
							long lCurrentTime = System.currentTimeMillis();
							while (iGrpThreadCounter_.get() > iGroupMaximumThread_) {
								// try
								// {
								// Thread.sleep(10000);
								// }
								// catch (InterruptedException ie)
								// {
								// System.exit(1);
								// }
							}
							long lWaitTime = System.currentTimeMillis()
									- lCurrentTime;
							if (objEngineLogger_.isEnabledFor(LogLevel.FINE)) {
								objEngineLogger_
										.log(
												LogLevel.FINE,
												"Group Engine :Wait Over. Waited for #"
														+ lWaitTime
														+ " milliseconds for some JOB to get over.");
							}
						}
					} // If group engine is not terminated.
					if (!bDoesRequestExists) {
						lWaitInterval_ = (Math.abs(Long.parseLong(CSettings
								.get("pr.waitinterval"))));
						if (objEngineLogger_.isInfoEnabled()) {
							objEngineLogger_.info(mfSleepMessageForEngine_
									.format(new Object[] { "Group",
											lWaitInterval_ }));
						}
						try {
							Thread.yield();
							TimeUnit.SECONDS.sleep(lWaitInterval_);
							// Thread.sleep(lWaitInterval_);
						} catch (InterruptedException ie) {
							// This is not an exception.
							objEngineLogger_.log(LogLevel.NOTICE,
									"Grouped Engine Thread Interrupted ..");
							break;
						}
					} // end of else
				} catch (IOException ioe) {
					// This is under the assumption that the IO Exception can
					// never be thrown by the object
					// executed by the Engine as it can only throw
					// CProcessRequestEngineException. This exception
					// means that something wrong has happened in the
					// Connection.
					try {
						closeSQLStatement(psmu);
						closeSQLStatement(psm);
						conForGroupedRequest = refreshJDBCConnection(4,
								conForGroupedRequest, CSettings
										.get("pr.dsforgroupeng"));
						// Connection gets refereshed but not the statements.
						// Therefore the statemetns are refreshed/recreated
						// here.
						psmu = conForGroupedRequest
								.prepareStatement("UPDATE process_request SET req_stat = ? WHERE grp_id = ? AND req_stat = ?");
						psm = conForGroupedRequest
								.prepareStatement("Select distinct grp_id from process_request where grp_st_ind = ? and req_stat = ? and scheduled_time <= ?");

					} catch (Exception e) {
						stopMessageForEmail_ = exceptionToString(ioe);
						objEngineLogger_
								.fatal(
										"Grouped Request: IOException Caught. Terminating GroupedRequest Engine",
										ioe);
						setReboot(true);
						break;
					}
				} catch (SQLException se) {
					// This is under the assumption that the SQL Exception can
					// never be thrown by the object
					// executed by the Engine as it can only throw
					// CProcessRequestEngineException. This exception
					// means that something wrong has happened in the Engine
					// itself and so engine should get terminated.
					try {
						closeSQLStatement(psmu);
						closeSQLStatement(psm);
						conForGroupedRequest = refreshJDBCConnection(4,
								conForGroupedRequest, CSettings
										.get("pr.dsforgroupeng"));
						// Connection gets refereshed but not the statements.
						// Therefore the statemetns are refreshed/recreated
						// here.
						psmu = conForGroupedRequest
								.prepareStatement("UPDATE process_request SET req_stat = ? WHERE grp_id = ? AND req_stat = ?");
						psm = conForGroupedRequest
								.prepareStatement("Select distinct grp_id from process_request where grp_st_ind = ? and req_stat = ? and scheduled_time <= ?");
					} catch (Exception e) {
						stopMessageForEmail_ = exceptionToString(se);
						objEngineLogger_
								.fatal(
										"Grouped Request: SQLException Caught. Terminating GroupedRequest Engine",
										se);
						setReboot(true);
						break;
					}
				} catch (RuntimeException ree) {
					objEngineLogger_.error(
							"Grouped Request: Runtime Exception Caught", ree);
					// no need to throw exception. Just catch.
				} catch (Exception e) {
					objEngineLogger_.error("Grouped Request: Exception Caught",
							e);
					// no need to throw exception. Just catch.
				} finally {
					if (rs != null) {
						rs.close();
					}
				}
			} // end of while
		} catch (Exception e) {
			objEngineLogger_.fatal("Grouped Request Main: Exception Caught", e);
			// no need to throw exception. Just catch.
		} finally {
			closeSQLStatement(psmu);
			closeSQLStatement(psm);
			try {
				if (conForGroupedRequest != null) {
					if (objEngineLogger_.isInfoEnabled()) {
						objEngineLogger_
								.info("Releasing Grouped Connection to the pool ....");
					}
					conForGroupedRequest.close();
				}
			} catch (SQLException e) {
				// do nothing
			}
			// objEngineLogger_.log(LogLevel.NOTICE,
			// "Grouped Request Engine Stopped ..");
			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_
						.debug("Interrupting Shutdown Hook from Grouped Requests...");
			}
			// try {
			// poolManager_.emptyPool(CSettings.get("pr.dsforgroupeng"));
			// } catch (SQLException e) {
			// //do nothing
			// }
			if (tInterrupt_ != null) {
				if (tInterrupt_.isAlive()) {
					tInterrupt_.interrupt();
				} else {
					if (tMain_.isAlive()) {
						tMain_.interrupt();
					}
				}
			} else {
				if (tMain_.isAlive()) {
					tMain_.interrupt();
				}
			}
		}
	} // end of method void startGroupServiceEngine()

	/**
	 * <p>
	 * The query picks up requests, from the table PROCESS_REQUEST, that have
	 * the Req_Stat='Q' and GRP_ST_IND = 'G' for the specified group. If
	 * requests are found then each record is processed sequentially depending
	 * on the priority defined in the group. If no records are found then the
	 * engine waits for a specified time (picked up from property file) and then
	 * resumes scanning
	 * </p>
	 * 
	 * @param pconForGroupedRequest
	 *            Connection object.
	 * @param plGroupId
	 *            Group id to be serviced.
	 * @throws CProcessRequestEngineException
	 */
	private void startServiceForGroupedRequests(
			Connection pconForGroupedRequest, long plGroupId)
			throws CProcessRequestEngineException {
		boolean isQueuedReqFound = false;

		ProcessRequestController objPrCont_ = null;
		ProcessRequestController objPRC = null;
		ProcessReqParamsController objPRPC = null;
		ProcessReqParamsEntityBean objPRPEB = null;
		CDynamicDataContainer objCDC = null;

		StringBuffer reqLogFileName;
		StringBuffer reqLogFileUrl;

		try {

			reqLogFileName = new StringBuffer(50);
			reqLogFileUrl = new StringBuffer(50);

			objEngineLogger_.log(LogLevel.NOTICE,
					"Processing the Grouped Request for id " + plGroupId);
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_
						.info("Starting service for scanning queued grouped requests ....");
			}
			CDynamicDataContainer objDdc_ = new CDynamicDataContainer();
			objDdc_.addWhereClause(FILTER_CONDITION);
			objDdc_
					.addOrderByClause(" Order By grp_id, grp_req_seq_no, priority");

			ProcessRequestEntityBean objPrEb_ = new ProcessRequestEntityBean();
			// ProcessReqParamsEntityBean objPrparamsEb_ = new
			// ProcessReqParamsEntityBean();
			objPRC = new ProcessRequestController(pconForGroupedRequest);
			objPRPC = new ProcessReqParamsController(pconForGroupedRequest);
			objPRPEB = new ProcessReqParamsEntityBean();
			objCDC = new CDynamicDataContainer();

			objPrCont_ = new ProcessRequestController(pconForGroupedRequest);

			try {

				if (objEngineLogger_.isInfoEnabled()) {
					objEngineLogger_
							.info("Entered infintite loop, Initializing Request Entity Bean ....");
				}

				isQueuedReqFound = false;
				if (!bGroupedEngineTerminated_.get()) {
					objPrEb_.initialize();
					objPrEb_.setReqStat(REQUEST_STATUS.LAUNCHING.getID());
					objPrEb_.setGrpStInd(REQUEST_TYPE.GROUPED.getID());
					objPrEb_.setGrpId(plGroupId);
					objPrEb_
							.setScheduledTime(getCurrentTimestamp(pconForGroupedRequest));

					if (objEngineLogger_.isDebugEnabled()) {
						objEngineLogger_.debug("Building query ....");
					}
					objDdc_.build(pconForGroupedRequest, objPrEb_,
							hmWhereCondition_);
					// This has been added later by Kedar on 3/1/2003

					if (objEngineLogger_.isDebugEnabled()) {
						objEngineLogger_
								.debug("Querying for queued requests ...."
										+ objDdc_.getQuery());
					}

					isQueuedReqFound = objDdc_.executeQuery(
							pconForGroupedRequest, objPrCont_, objPrEb_);
				}
				if (isQueuedReqFound) {// pending requests exist

					if (objEngineLogger_.isDebugEnabled()) {
						objEngineLogger_.debug("Queued requests exists ....");
					}

					boolean bToProcessOthers = true;
					while (objDdc_.next()) {
						ProcessRequestEntityBean objPrEb = (ProcessRequestEntityBean) objDdc_
								.get();
						if (bToProcessOthers) {
							if (tEngine_.isInterrupted()
									|| bGroupedEngineTerminated_.get()) {
								if (objEngineLogger_
										.isEnabledFor(LogLevel.NOTICE)) {
									objEngineLogger_
											.log(
													LogLevel.NOTICE,
													"The Engine is being terminated. The Group Request #"
															+ objPrEb
																	.getGrpId()
															+ " even though not complete will be allowed to be executed once PRE is restarted.");
								}
								break;
							}
						} else {
							if (tEngine_.isInterrupted()
									|| bGroupedEngineTerminated_.get()) {
								if (objEngineLogger_
										.isEnabledFor(LogLevel.NOTICE)) {
									objEngineLogger_
											.log(
													LogLevel.NOTICE,
													"The Engine is being terminated. Please wait till the remaining requests for the Group #"
															+ objPrEb
																	.getGrpId()
															+ " are marked as Cancelled.");
								}
							}
						}
						objPRPEB.initialize();

						reqLogFileName.delete(0, reqLogFileName.length());
						reqLogFileUrl.delete(0, reqLogFileUrl.length());
						reqLogFileName.append(strReqLogFilePath_);
						reqLogFileName.append(objPrEb.getReqId());
						reqLogFileName.append(".");
						reqLogFileName.append(strReqLogFileExtension_);

						reqLogFileUrl.append(strReqLogFileUrl_);
						reqLogFileUrl.append(objPrEb.getReqId());
						reqLogFileUrl.append(".");
						reqLogFileUrl.append(strReqLogFileExtension_);

						// objEngineLogger_.debug("Initialize Request Log File ....");

						if (bToProcessOthers) {
							ProcessRequestServicer objProcessRequest_ = null;
							updateRequestStatus(pconForGroupedRequest, objPRC,
									objPrEb, REQUEST_STATUS.LAUNCHING,
									reqLogFileUrl.toString());

							if (objEngineLogger_.isInfoEnabled()) {
								objEngineLogger_
										.info("Initialize Request Log File ....");
							}

							try {
								if (objEngineLogger_.isInfoEnabled()) {
									objEngineLogger_
											.info("Instantiate the Process Class which has been requested .....");
								}
								objProcessRequest_ = instantiateReqProcessObject(objPrEb_
										.getProcessClassNm());

								bToProcessOthers = processEachRequest(
										pconForGroupedRequest, objPRC, objPrEb,
										objCDC, objPRPC, objPRPEB,
										objProcessRequest_, new File(
												reqLogFileName.toString()),
										reqLogFileUrl.toString());
							} catch (RuntimeException re) {
								try {
									if (objEngineLogger_.isInfoEnabled()) {
										objEngineLogger_
												.info("Update Request Status to Error due to Exception.");
									}
									updateRequestStatus(staticConnection_,
											objPrCont_, objPrEb_,
											REQUEST_STATUS.ERROR, reqLogFileUrl
													.toString());
								} catch (Exception e) {
									objEngineLogger_
											.error(
													"Caught exception while updating status to Error. ",
													e);
								}
								throw re;
							} catch (Exception e) {
								try {
									if (objEngineLogger_.isInfoEnabled()) {
										objEngineLogger_
												.info("Update Request Status to Error due to Exception.");
									}
									updateRequestStatus(staticConnection_,
											objPrCont_, objPrEb_,
											REQUEST_STATUS.ERROR, reqLogFileUrl
													.toString());
								} catch (Exception ex) {
									objEngineLogger_
											.error(
													"Caught exception while updating status to Error. ",
													ex);
								}
								throw e;
							} catch (Error error) {
								try {
									if (objEngineLogger_.isInfoEnabled()) {
										objEngineLogger_
												.info("Update Request Status to Error due to Exception.");
									}
									updateRequestStatus(staticConnection_,
											objPrCont_, objPrEb_,
											REQUEST_STATUS.ERROR, reqLogFileUrl
													.toString());
								} catch (Exception ex) {
									objEngineLogger_
											.error(
													"Caught exception while updating status to Error. ",
													ex);
								}
								throw error;
							} finally {
								objProcessRequest_ = null;
							}
						} // If bToProcessOthers
						else {
							try {
								if (objEngineLogger_.isInfoEnabled()) {
									objEngineLogger_
											.info("Update Request Status to "
													+ REQUEST_STATUS.SUSPENDED
															.getDescription());
								}
								updateRequestStatus(pconForGroupedRequest,
										objPRC, objPrEb,
										REQUEST_STATUS.SUSPENDED, reqLogFileUrl
												.toString());
							} catch (Exception e) {
								objEngineLogger_
										.error(
												"Caught exception while updating status to Error. ",
												e);
								// do not throw just log it.
							}
						}

					}// end of while(objDdc.next())

					// If the engine is terminated then update the status of
					// other grouped request to Q so that
					// next time the remaining processes can be completed.
					// Added on 30/04/2004....Kedar.
					if ((tEngine_.isInterrupted() || bGroupedEngineTerminated_.get())
							&& bToProcessOthers) {
						PreparedStatement psm = null;
						try {
							psm = pconForGroupedRequest
									.prepareStatement("UPDATE process_request set req_stat = ? WHERE grp_id = ? and req_stat = ?");
							psm.setString(1, REQUEST_STATUS.QUEUED.getID());
							psm.setLong(2, plGroupId);
							psm.setString(3, REQUEST_STATUS.LAUNCHING.getID());
							psm.executeUpdate();
						} catch (Exception e) {
							objEngineLogger_
									.error(
											"Caught exception while updating status to Queued. ",
											e);
							// just log the exception. Need not be thrown.
							// INFO Needs to be re-validated if this needs to be
							// thrown.
						} finally {
							try {
								if (psm != null) {
									psm.close();
								}
							} catch (Exception e) {
								if (objEngineLogger_.isEnabledFor(Level.WARN)) {
									objEngineLogger_
											.warn(
													"Exception can be ignored..Dummy exception.",
													e);
								}
							}
						}

					} // ((tEngine.isInterrupted() ||
					// bGroupedEngineTerminated_))
				}// end of if objDdc.getTotalRows() > 0
			} catch (Exception e) {
				objEngineLogger_.error(
						"Processing GroupedRequest: Exception Caught", e);
				// Just catch. No need to throw.
			}

		} catch (Exception e) {
			objEngineLogger_.error(
					"Processing GroupedRequest: Exception Caught", e);
			// Just catch. No need to throw.
		} // end of 1st try catch block
		finally {
			objEngineLogger_.log(LogLevel.NOTICE,
					"Releasing Group Resources...");
			if (objPrCont_ != null) {
				try {
					objPrCont_.close();
				} catch (SQLException e) {
					objEngineLogger_
							.error(
									"Processing GroupedRequest: Exception Caught while closing PRController",
									e);
				}
			}
			if (objPRC != null) {
				try {
					objPRC.close();
				} catch (SQLException e) {
					objEngineLogger_
							.error(
									"Processing GroupedRequest: Exception Caught while closing PRController",
									e);
				}
			}
			if (objPRPC != null) {
				try {
					objPRPC.close();
				} catch (SQLException e) {
					objEngineLogger_
							.error(
									"Processing GroupedRequest: Exception Caught while closing PRPController",
									e);
				}
			}
			reqLogFileName = null; // Nullifying the variables.
			reqLogFileUrl = null; // Nullifying the variables.
			objEngineLogger_.log(LogLevel.NOTICE,
					"Grouped Request processed for id " + plGroupId);
		}
	} // end of method void startServiceForGroupedRequests

	/**
	 * This inner class extends Thread and executes different Grouped requests.
	 * 
	 */
	class CGroupedThreadProcess extends Thread implements IJOBMonitor {

		/**
		 * Stores the REVISION number of the class from the configuration
		 * management tool.
		 */
		public final String REVISION = "$Revision:: 3829              $";

		private Logger groupLogger_;

		private long lActualInitializedTimeInMillis_;

		private long lStartTimeInMillis_ = -1;

		public CGroupedThreadProcess() {
			super();
			groupLogger_ = Logger.getLogger("GroupEngine");
			lActualInitializedTimeInMillis_ = System.currentTimeMillis();
		}

		public CGroupedThreadProcess(String name) {
			super(name);
			groupLogger_ = Logger.getLogger("GroupEngine");
			lActualInitializedTimeInMillis_ = System.currentTimeMillis();
		}

		private long lGrpId_;

		private Connection conGrp_;

		private JOB_STATUS jobStatus_;

		private long jobCheckedAtTime_ = -1;

		public void setGroupId(long plGrpId) {
			lGrpId_ = plGrpId;
		}

		public IProcessRequest getIntantiatedObject() {
			return null;
		}

		public void run() {
			lStartTimeInMillis_ = System.currentTimeMillis();
			if (groupLogger_.isEnabledFor(LogLevel.FINE)) {
				groupLogger_
						.log(
								LogLevel.FINE,
								"{1}Thread Wait Time "
										+ (lStartTimeInMillis_ - lActualInitializedTimeInMillis_)
										+ " milliseconds...");
			}
			try {
				// conGrp_ =
				// poolManager_.getConnection(CSettings.get("pr.dsforgroupeng"));
				conGrp_ = dataSourceFactory_.getDataSource(
						CSettings.get("pr.dsforgroupeng")).getConnection();
				startServiceForGroupedRequests(conGrp_, lGrpId_);
			} catch (Error error) {
				groupLogger_.fatal("java.lang.Error Caught", error);
				throw error;
			} catch (RuntimeException ree) {
				groupLogger_.fatal("Runtime Exception Caught", ree);
				throw ree;
			} catch (CProcessRequestEngineException cree) {
				groupLogger_.fatal("CProcessRequestEngineException Caught",
						cree);
				// Just catch. Can't throw a catched exception.
			} catch (Exception e) {
				groupLogger_.fatal("Exception Caught", e);
				// Just catch. Can't throw a catched exception.
			} finally {
				try {
					conGrp_.close();
				} catch (SQLException e1) {
					// do nothing
				}

				removeGroupProcess(this);
				if (vectorThreadsGroup_.size() == 0
						&& bGroupedEngineTerminated_.get()) {
					// try
					// {
					// poolManager_.emptyPool(CSettings.get("pr.dsforgroupeng"));
					// } catch (Exception e) {
					// objEngineLogger_.error(e);
					// }
					if (groupLogger_.isDebugEnabled()) {
						groupLogger_
								.debug("Grouped Engine: Iterrupting Shutdown Hook...");
					}
					if (tInterrupt_ != null) {
						// Earlier this thread if not null was then Alive as
						// well. But because of Reboot sequence
						// This thread can be not null but not alive and
						// therfore this condition is added.
						if (tInterrupt_.isAlive()) {
							tInterrupt_.interrupt();
						} else {
							tEngine_.interrupt();
						}
					} else {
						tEngine_.interrupt();
					}
				} // (vectorThreadsGroup_.size() == 0 &&
				// bGroupedEngineTerminated_)
				if (groupLogger_.isEnabledFor(LogLevel.FINE)) {
					groupLogger_
							.log(
									LogLevel.FINE,
									"{10}Elapsed Time for the Thread run() #"
											+ (System.currentTimeMillis() - lActualInitializedTimeInMillis_)
											+ " milliseconds...");
				}
			} // finally
		} // run()

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getStartTime()
		 */
		public long getStartTime() {
			return lStartTimeInMillis_;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getConnection()
		 */
		public Connection getConnection() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getStuckThreadLimit()
		 */
		public long getStuckThreadLimit() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getStuckThreadMaxLimit()
		 */
		public long getStuckThreadMaxLimit() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getRequestBean()
		 */
		public ProcessRequestEntityBean getRequestBean() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getUserId()
		 */
		public String getUserId() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#markStuckThread(boolean)
		 */
		public void setJobStatus(JOB_STATUS status) {
			jobStatus_ = status;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getStuckThread()
		 */
		public int getStuckThread() {
			return jobStatus_.getID();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#setMonitorCheckTime(long)
		 */
		public void setMonitorCheckTime(long time) {
			this.jobCheckedAtTime_ = time;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IMonitorThread#getMonitorCheckTime()
		 */
		public long getMonitorCheckTime() {
			return jobCheckedAtTime_;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see stg.pr.engine.IJOBMonitor#getInitializedTime()
		 */
		public long getInitializedTime() {
			return lActualInitializedTimeInMillis_;
		}

        public JOB_STATUS getJobStatus() {
            return jobStatus_;
        }

	} // End of class CGroupedThreadProcess

	/**
	 * Internal class to monitor the stuck thread.
	 * 
	 * @author Kedar C. Raybagkar
	 */
	class MonitorThread extends Thread {
		/**
		 * Stores the REVISION number of the class from the configuration
		 * management tool.
		 */
		public final String REVISION = "$Revision:: 3829              $";

		private Logger monitorLogger_;

//		private Day dayLicenseAlert_ = null;

		public MonitorThread(String name) {
			super(name);
			monitorLogger_ = Logger.getLogger("JobMonitor");
//			dayLicenseAlert_ = new Day();
		}

		/**
		 * Message Format.
		 */
		private MessageFormat mfSleepMessage_ = new MessageFormat(
				"Will sleep for {0, number} minute(s).");

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			monitorLogger_.log(LogLevel.NOTICE, "Started...");
			try {
				// while (!tMonitorThread_.isInterrupted())
				while (true) {
					// if (tMain_ != null)
					// {
					// if (!tMain_.isAlive())
					// {
					// break;
					// }
					// }
//					boolean bTerminate = false;
//					LicenseContent content = null;
//					try {
//						content = readLicense();
//						LicenseVerifier.verify(content);
//					} catch (LicenseContentException e) {
//						bTerminate = true;
//						monitorLogger_.fatal(e.getLocalizedMessage());
//					} catch (Exception e) {
//						bTerminate = true;
//						monitorLogger_.fatal(e.getLocalizedMessage());
//					}
//					if (bTerminate) {
//						if (tEngine_ != null) {
//							tEngine_.interrupt();
//						}
//						if (tMain_ != null) {
//							tMain_.interrupt();
//						}
//						break;
//					}

//					if (dayLicenseAlert_.compareTo(content.getNotAfter()) * -1 <= Integer
//							.parseInt(CSettings.get(
//									"pr.licenseAlertDaysBeforeExpiry", "10"))
//							&& dayLicenseAlert_.compareTo(new Day()) == 0) {
//						sendDefaultMail(
//								"License will expire on "
//										+ content.getNotAfter(),
//								"Please get the license renewed before it expires.",
//								true);
//						dayLicenseAlert_.advance(1);
//					}
					try {
						if (monitorLogger_.isInfoEnabled()) {
							monitorLogger_
									.info(mfSleepMessage_
											.format(new Object[] { lStuckThreadMonitorInterval_ }));
						}
						Thread.yield();
						TimeUnit.MINUTES.sleep(lStuckThreadMonitorInterval_);
						// Thread.sleep(lStuckThreadMonitorInterval_);
					} catch (InterruptedException e) {
						monitorLogger_.log(LogLevel.NOTICE,
								"Thread Interrupted.");
						// do nothing. This thread is supposed to be notified
						// with Interrupted Exception.
						// break;
						// e.printStackTrace();
					}
					if (monitorLogger_.isInfoEnabled()) {
						monitorLogger_.info("Checking for Stuck Threads.");
					}
					try {
						boolean bProcessRRunning = checkForStuckThread();
						if (bEngineTerminated_.get() || !tMain_.isAlive()) // Currently
						// as
						// the
						// StandAlone
						// Service
						// Threads
						// are
						// monitored.
						{
							monitorLogger_
									.log(LogLevel.NOTICE,
											"Engine is being terminated...Stopping Job Monitor.");
							if (bProcessRRunning) {
								monitorLogger_
										.log(LogLevel.NOTICE,
												"Unable to stop JobMonitor as job(s) are currently running.");
								startWebServer(); // Try restarting the web
								// server if not started.
							} else {
								if (tEngine_ != null) {
									tEngine_.interrupt();
								}
								break;
							}
						}
					} catch (Error error) {
						monitorLogger_.error("Error caught", error);
						// Monitor job should never get terminated.
					} catch (RuntimeException re) {
						monitorLogger_.error("RunTime Exception caught ", re);
						// Monitor job should never get terminated.
					} catch (Exception e) {
						monitorLogger_.error("Exception caught", e);
						// Monitor job should never get terminated.
					}
				} // end of while
				if (tMonitorThread_.isInterrupted()) {
					monitorLogger_.log(LogLevel.NOTICE,
							"Stopping the monitor thread.");
				}
			} catch (Throwable e) {
				monitorLogger_.error("Exception caught in Monitor Thread.", e);
				// No need to throw. Just catch and log.
			} finally {
				stopWebServer();
				// the following if condition is because the pool has its own
				// selfcheck thread running. This thread keeps on running
				// and therefore the engine does not terminiate. The thread runs
				// only if the connection idle time is > 0.
				// The closeAll() method tries to interrupt this thread thus
				// forcing it to break the cycle (loop). Therefore a new thread
				// is
				// spawned and the closeAll() method is called from there.
				// Kedar. 04/10/2008.
				// if
				// (poolManager_.getPoolAttributes(CSettings.get("pr.dsforstandaloneeng")).getConnectionIdleTimeout()
				// > 0
				// ||
				// poolManager_.getPoolAttributes(CSettings.get("pr.dsforgroupeng")).getConnectionIdleTimeout()
				// > 0) {
				Thread th = new Thread("JobMonitor-Clean-Shutdown") {
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.lang.Thread#run()
					 */
					public void run() {
						try {
							closeAll();
						} catch (Throwable t) {
							// dummy catch.
						} finally {
							Hazelcast.shutdownAll();
						}
						super.run();
					}
				};
				th.start();
				// }
			}
			if (monitorLogger_.isInfoEnabled()) {
				monitorLogger_
						.info("Checking to see if any pending emails are to be transported");
			}
			try {
				CMailer.getInstance(CSettings.get("pr.mailtype")).shutdown(
						instance_);
			} catch (Throwable e) {
				monitorLogger_.warn(e.getMessage(), e);
			}
			if (monitorLogger_.isInfoEnabled()) {
				monitorLogger_.log(LogLevel.NOTICE, "Monitor thread stopped.");
			}
		}

		/**
		 * Method to check the status of each thread that is running.
		 * 
		 * @return boolean TRUE if there are any threads that are currently
		 *         running else FALSE.
		 */
		private boolean checkForStuckThread() {
			boolean bProcessAreRunningCurrently = false;
			if (monitorLogger_.isInfoEnabled()) {
				monitorLogger_.info("Currently Running Processes #"
						+ vectorThreads_.size());
			}
			int iJobsTerminated = 0;
			StringBuffer messageBuffer = null;
			StringBuffer subjectBuffer = null;
			IJOBMonitor[] jobs = getStandAloneJobs();
			for (int i = 0; i < jobs.length; i++) {
				try {
					bProcessAreRunningCurrently = true;
					IJOBMonitor threadMonitor = jobs[i];
					if (threadMonitor.getStartTime() > -1) {
						long lCurrentTimeInMilis = System.currentTimeMillis(); // (new
						// GregorianCalendar()).getTimeInMillis();
						if (threadMonitor.getJobStatus().getID() > 0) {
							if (monitorLogger_.isInfoEnabled()) {
								monitorLogger_
										.info("STUCK. Still Running !! Request Id #"
												+ threadMonitor
														.getRequestBean()
														.getReqId()
												+ " Job : "
												+ threadMonitor
														.getRequestBean()
														.getJobId()
												+ " - "
												+ threadMonitor
														.getRequestBean()
														.getJobName());
							}
						}

						if (threadMonitor.getJobStatus() == JOB_STATUS.NORMAL) {
							if ((lCurrentTimeInMilis - threadMonitor
									.getStartTime()) > threadMonitor
									.getStuckThreadLimit()) {
								threadMonitor.setJobStatus(JOB_STATUS.STUCK);
								if (monitorLogger_.isInfoEnabled()) {
									monitorLogger_
											.info("Marked as STUCK !! Request Id"
													+ threadMonitor
															.getRequestBean()
															.getReqId());
									monitorLogger_
											.info("Setting the Thread Priority To Max #"
													+ Thread.MAX_PRIORITY);
								}
								try {
									((Thread) threadMonitor)
											.setPriority(Thread.MAX_PRIORITY);
								} catch (SecurityException e) {
									monitorLogger_.error(
											"Unable to set the priority.", e);
									// dummy catch.
								}
								if (subjectBuffer == null) {
									subjectBuffer = new StringBuffer();
									messageBuffer = new StringBuffer();
								}
								subjectBuffer.delete(0, subjectBuffer.length());
								messageBuffer.delete(0, messageBuffer.length());
								subjectBuffer.append(" Request Id #");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getReqId());
								subjectBuffer.append(" Job #");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getJobId());
								subjectBuffer.append(" - ");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getJobName());
								messageBuffer.append("Job Has Crossed The ");
								messageBuffer.append(TimeUnit.MILLISECONDS
										.toMinutes(threadMonitor
												.getStuckThreadLimit()));
								messageBuffer
										.append(" Minute(s) Stuck Thread Limit. The PRE has set the ");
								messageBuffer
										.append(" priority of this thread to MAX.");
								if (CSettings.get("pr.mailnotification", "OFF")
										.equals("ON")) {
									try {
										CMailer.sendMonitorMail(CMailer.NORMAL,
												subjectBuffer.toString(),
												messageBuffer.toString());
									} catch (Throwable e) {
										monitorLogger_.error(
												"Unable to send the Message due to : "
														+ e.getMessage(), e);
										// Notification is not a must. So if PRE
										// is not able to send an email it
										// ignores the error.
									}
								}
							} // if ((lCurrentTimeInMilis -
							// threadMonitor.getStartTime()) >
							// threadMonitor.getStuckThreadLimit())
						} // if stuckThread() == 0
						if (threadMonitor.getJobStatus() == JOB_STATUS.STUCK) {
							if ((lCurrentTimeInMilis - threadMonitor
									.getStartTime()) > threadMonitor
									.getStuckThreadMaxLimit()) {
								threadMonitor.setJobStatus(JOB_STATUS.CRITICAL);
								threadMonitor
										.setMonitorCheckTime(lCurrentTimeInMilis);
								long lRequestId = threadMonitor
										.getRequestBean().getReqId();
								monitorLogger_
										.warn("STUCK. Crossed the MAX Stuck Thread Limit. Request Id #"
												+ lRequestId
												+ " Job : "
												+ threadMonitor
														.getRequestBean()
														.getJobId()
												+ " - "
												+ threadMonitor
														.getRequestBean()
														.getJobName());
								if (subjectBuffer == null) {
									subjectBuffer = new StringBuffer();
									messageBuffer = new StringBuffer();
								}
								subjectBuffer.delete(0, subjectBuffer.length());
								messageBuffer.delete(0, messageBuffer.length());
								subjectBuffer.append(" Request Id #");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getReqId());
								subjectBuffer.append(" Job #");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getJobId());
								subjectBuffer.append(" - ");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getJobName());
								messageBuffer.append("Job Has Crossed The ");
								messageBuffer.append(TimeUnit.MILLISECONDS
										.toMinutes(threadMonitor
												.getStuckThreadMaxLimit()));
								messageBuffer
										.append(" Minute(s) Stuck Thread MAX Limit. The job has taken");
								messageBuffer
										.append(" time even after setting thread priority to MAX by PRE.");
								if (CSettings.get("pr.terminateprocess", "N")
										.equals("Y")) {
									if (threadMonitor.getIntantiatedObject() instanceof ITerminateProcess) {
										if (monitorLogger_.isInfoEnabled()) {
											monitorLogger_
													.info("Requesting termination of job \""
															+ ((threadMonitor
																	.getRequestBean()
																	.getJobId() == null) ? ""
																	: threadMonitor
																			.getRequestBean()
																			.getJobId()
																			.toUpperCase(
																					Locale.US))
															+ "\" Request #"
															+ lRequestId);
										}
										messageBuffer
												.append(" PRE has initiated the termination process.");
										((ITerminateProcess) threadMonitor
												.getIntantiatedObject())
												.terminate("Stuck Thread.");
										threadMonitor.setJobStatus(JOB_STATUS.TOBETERMINATED);
										iJobsTerminated++;
									}
								}
								if (CSettings.get("pr.mailnotification", "OFF")
										.equals("ON")) {
									try {
										CMailer.sendMonitorMail(
												CMailer.CRITICAL, subjectBuffer
														.toString(),
												messageBuffer.toString());
									} catch (Throwable e) {
										monitorLogger_
												.error(
														"Unable to send the Message due to : ",
														e);
										// Notification is not a must. So if PRE
										// is not able to send an email it
										// ignores the error.
									}
								} // if pr.mailnotification equals ON
							} // if ((lCurrentTimeInMilis -
							// threadMonitor.getStartTime()) >
							// threadMonitor.getStuckThreadMaxLimit())
						} // stuckThread() == 1
						else if (threadMonitor.getJobStatus().getID() >= 2) {

							if ((lCurrentTimeInMilis - threadMonitor
									.getMonitorCheckTime()) > TimeUnit.MINUTES
									.toMillis(jobMonitorEscalationTimeInterval_)) {
								threadMonitor
										.setMonitorCheckTime(lCurrentTimeInMilis);
								long lRequestId = threadMonitor
										.getRequestBean().getReqId();
								if (subjectBuffer == null) {
									subjectBuffer = new StringBuffer();
									messageBuffer = new StringBuffer();
								}
								subjectBuffer.delete(0, subjectBuffer.length());
								messageBuffer.delete(0, messageBuffer.length());
								subjectBuffer.append(" Request Id #");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getReqId());
								subjectBuffer.append(" Job #");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getJobId());
								subjectBuffer.append(" - ");
								subjectBuffer.append(threadMonitor
										.getRequestBean().getJobName());
								messageBuffer.append("Job is alive. Crossed ");
								messageBuffer
										.append(TimeUnit.MILLISECONDS
												.toMinutes(lCurrentTimeInMilis
														- threadMonitor
																.getStartTime()));
								messageBuffer
										.append(" minutes. Please attend this process.");
								if (CSettings.get("pr.terminateprocess", "N")
										.equals("Y")) {
									if (threadMonitor.getJobStatus() == JOB_STATUS.TOBETERMINATED) {
										if (monitorLogger_.isInfoEnabled()) {
											monitorLogger_
													.info("The job \""
															+ ((threadMonitor
																	.getRequestBean()
																	.getJobId() == null) ? ""
																	: threadMonitor
																			.getRequestBean()
																			.getJobId()
																			.toUpperCase(
																					Locale.US))
															+ "\" Request #"
															+ lRequestId
															+ " is unable to terminate itself even after invoking the terminate method.");
										}
									}
								}
								if (CSettings.get("pr.mailnotification", "OFF")
										.equals("ON")) {
									try {
										CMailer.sendMonitorMail(
												CMailer.CRITICAL, subjectBuffer
														.toString(),
												messageBuffer.toString());
									} catch (Throwable e) {
										monitorLogger_
												.error(
														"Unable to send the Message due to : ",
														e);
										// Notification is not a must. So if PRE
										// is not able to send an email it
										// ignores the error.
									}
								} // if pr.mailnotification equals ON
							}
						}
					} // threadMonitor.getStartTime() > -1
					if (bEngineTerminated_.get()) {
						Object obj = threadMonitor.getIntantiatedObject();
						if (obj instanceof IStopEvent) {
							IStopEvent stopEvent = (IStopEvent) obj;
							if (monitorLogger_.isInfoEnabled()) {
								monitorLogger_
										.info("Notifiying Engine Stop Event to Request Id # "
												+ threadMonitor
														.getRequestBean()
														.getReqId()
												+ " JOB #"
												+ threadMonitor
														.getRequestBean()
														.getJobId()
												+ " - "
												+ threadMonitor
														.getRequestBean()
														.getJobName());
							}
							try {
								stopEvent.notifyEngineStopEvent();
							} catch (RuntimeException e) {
								monitorLogger_
										.error(
												"Caught Runtime Exception while notifying the stop event.",
												e);
								// Engine is being terminated. So just log and
								// go ahead.
							} catch (Exception e) {
								monitorLogger_
										.error(
												"Caught Exception while notifying the stop event.",
												e);
								// Engine is being terminated. So just log and
								// go ahead.
							}
						}
					}
				} catch (NullPointerException npe) {
					// One of the thread has finished its job therefore the
					// thread was removed and raised a nullpointer exception.
				}
			} // For loop vector threads iteration.
			if (monitorLogger_.isInfoEnabled()) {
				monitorLogger_.info("Current Stuck Threads are #"
						+ iStuckThreads_ + " Threads That Crossed Max Limit #"
						+ iStuckThreadThatCrossMaxLimit_);
			}
			boolean bBounce = false;
			if (iStandAloneMaximumThread_ == iStuckThreadThatCrossMaxLimit_.get()) {
				bBounce = true;
			}
			if ((bEngineTerminated_.get() || !tMain_.isAlive())
					&& (iStuckThreadThatCrossMaxLimit_.get() == getCurrentRunningStandAloneProcess() && iStuckThreadThatCrossMaxLimit_.get() > 0)) { // Thread
				// main
				// is
				// dead
				// and
				// if
				// current
				// running
				// stand
				// alone
				// process
				// count
				// ==
				// stuck
				// thread
				// count.
				bBounce = true;
			}
			if (bBounce && iJobsTerminated > 0) {
				bBounce = false;
				if (monitorLogger_.isInfoEnabled()) {
					monitorLogger_
							.info("Even though all the jobs have been identified as STUCK there are #"
									+ iJobsTerminated
									+ " jobs whose terminate method is invoked.");
					monitorLogger_
							.info("Engine will wait for the job(s) to terminate itself gracefully. Incase the job does not terminate itself, the PRE will try to forcefully terminate it.");
				}
			}
			if (bBounce) {
				String strMessage = "";
				if (isReboot()) {
					strMessage = "Reboot(Abort)";
				} else if (bEngineTerminated_.get() || !tMain_.isAlive()) {
					strMessage = "Shut Down (Abort)";
				} else {
					strMessage = "Bounce (Abort)";
				}
				monitorLogger_
						.log(LogLevel.NOTICE,
								"Process Request Engine is being " + strMessage
										+ " !!");
				monitorLogger_
						.log(
								LogLevel.NOTICE,
								"Please take a look at all the jobs with status equals \""
										+ REQUEST_STATUS.PROCESSING.getID()
										+ "\". These jobs will not be reprocessed by the PRE.");
				if (isReboot() || (bEngineTerminated_.get() || !tMain_.isAlive())) {
					monitorLogger_.log(LogLevel.NOTICE,
							"All the threads have gone into a STUCK MODE and the PRE "
									+ strMessage
									+ " sequence has been executed.");
				} else {
					monitorLogger_
							.log(
									LogLevel.NOTICE,
									"All the threads have gone into a STUCK MODE and the PRE cannot therefore service any request.");
				}
				StringBuffer strBuffer = new StringBuffer();
				if (isReboot() || (bEngineTerminated_.get() || !tMain_.isAlive())) {
					strBuffer
							.append("All the threads have gone into a STUCK MODE and the PRE "
									+ strMessage
									+ " sequence has been executed.");
				} else {
					strBuffer
							.append("All the threads have gone into a STUCK MODE and the PRE cannot therefore service ");
					strBuffer.append("any request. Bouncing the PRE. ");
				}
				strBuffer
						.append("Please take a look at the PRE queue for all processes with status equal to \"");
				strBuffer.append(REQUEST_STATUS.PROCESSING.getID());
				strBuffer
						.append("\". These processes will not be reprocessed by the PRE after it is restarted. ");
				strBuffer
						.append("Please read the log file for details about the processes that were STUCK.");
				strBuffer.append(NEW_LINE);
				strBuffer.append(NEW_LINE);
				if (bEngineTerminated_.get() || !tMain_.isAlive()) {
					strBuffer
							.append("Please start the PRE manually. PRE will not attempt a reboot sequence.");
					strBuffer.append(NEW_LINE);
					strBuffer.append(NEW_LINE);
				} else {
					strBuffer
							.append("Please do not try to manually start the PRE. PRE will attempt a reboot sequence.");
					strBuffer.append(NEW_LINE);
					strBuffer.append(NEW_LINE);
				}
				strBuffer
						.append("Please contact Help Desk for further assistance.");
				if (CSettings.get("pr.mailnotification", "OFF").equals("ON")) {
					// CMailer mailer =
					// CMailer.getInstance(CSettings.get("pr.mailtype",
					// "SMTP"));
					EMail email = new EMail();
					try {
						email.setTORecipient(CSettings.get("mail.monitor."
								+ CMailer.CRITICAL + "recepientTO"));
						email.setCCRecipient(CSettings.get("mail.monitor."
								+ CMailer.CRITICAL + "recepientCC"));
					} catch (AddressException e) {
						// Do nothing
					}
					email.setEMailId("CriticalState#"
							+ System.currentTimeMillis());
					email.setSubject("CRITICAL !!!  " + strMessage);
					email.setMessageBody(strBuffer.toString());
					try {
						CMailer.getInstance(CSettings.get("pr.mailtype"))
								.sendMail(email); // This should not be changed
						// to sendAsyncMail as this
						// thread needs to wait till
						// the email is transported.
					} catch (MessagingException e) {
						monitorLogger_.error(
								"Exception caught while sending email", e);
					}
				}
				if (monitorLogger_.isEnabledFor(LogLevel.FINEST)) {
					monitorLogger_.log(LogLevel.FINEST, "Generating INI file");
				}
				if (isReboot()) {
					if (monitorLogger_.isInfoEnabled()) {
						monitorLogger_.info("Reboot (Abort) !!!");
					}
					context_.getMap().put("state", HeartBeatState.REBOOT);
					createIniFile(CStartEngine.REBOOT, -1);
				} else if (bEngineTerminated_.get() || !tMain_.isAlive()) {
					if (monitorLogger_.isInfoEnabled()) {
						monitorLogger_.info("Shut Down (Abort) !!!");
					}
					context_.getMap().put("state", HeartBeatState.STOP);
				} else {
					context_.getMap().put("state", HeartBeatState.BOUNCE);
					createIniFile(CStartEngine.BOUNCE, -1);
				}
				if (monitorLogger_.isInfoEnabled()) {
					monitorLogger_.info("Shuting down the Mailer");
				}
				CMailer.getInstance(CSettings.get("pr.mailtype")).shutdown(
						instance_);
				if (monitorLogger_.isEnabledFor(LogLevel.FINEST)) {
					monitorLogger_.log(LogLevel.FINEST,
							"Just before stopping the JVM");
				}
				Runtime.getRuntime().halt(2);
			}
			return bProcessAreRunningCurrently;
		}

	} // End of class MonitorThread

	/**
	 * Returns the current running Stand Alone process.
	 * 
	 * @return int Current running thread count.
	 */
	protected int getCurrentRunningStandAloneProcess() {
		return vectorThreads_.size();
	}

	/**
	 * Returns the current running Grouped process.
	 * 
	 * @return int Current running thread count.
	 */
	protected int getCurrentRunningGroupedProcess() {
		return vectorThreadsGroup_.size();
	}

	/**
	 * Number of running threads that cross the Stuck Thread limit.
	 * 
	 * @return int
	 */
	protected int getStuckThreads() {
		return iStuckThreads_.get();
	}

	/**
	 * Number of running threads that cross the Stuck Thread maximum limit.
	 * 
	 * @return int
	 */
	protected int getStuckThreadsThatCrossedMaxLimit() {
		return iStuckThreadThatCrossMaxLimit_.get();
	}

	/**
	 * Update the schedule for the appropriate status and returns the additional
	 * email ids associated with the schedule. Exception is logged as well as
	 * thrown.
	 * 
	 * @param scheduleStatus
	 *            String Schedule Status
	 *            {@link stg.pr.engine.scheduler.ISchedule}
	 * @param pcon
	 *            Connection
	 * @param plSchId
	 *            long Schedule Id.
	 * @throws Exception
	 *             return Additional email ids
	 */
	private boolean updateSchedule(SCHEDULE_STATUS scheduleStatus,
			Connection pcon, long plSchId) throws Exception {
		ProcessRequestScheduleController objPRSController = null;
		ProcessRequestScheduleEntityBean objPRSBean = null;
		boolean bReturnValue = false;
		try {
			pcon.setAutoCommit(false);
			objPRSBean = new ProcessRequestScheduleEntityBean();
			objPRSController = new ProcessRequestScheduleController(pcon);
			objPRSController.findByPrimaryKey(objPRSBean, plSchId);
			objPRSBean.setSchStat(scheduleStatus.getID());
			bReturnValue = objPRSController.update(objPRSBean);
			pcon.commit();
		} catch (SQLException e) {
			objEngineLogger_.error("Error while updating the schedule status ("
					+ scheduleStatus + ") for schedule#" + plSchId, e);
			objEngineLogger_
					.error("Unable to get the additional email recepients..");
			throw e;
		} finally {
			if (objPRSController != null) {
				objPRSController.close();
			}
			try {
				pcon.setAutoCommit(true);
			} catch (SQLException e) {
				// do nothing.
				if (objEngineLogger_.isEnabledFor(Level.WARN)) {
					objEngineLogger_.warn("Exception can be ignored..", e);
				}
			}
		}
		return bReturnValue;
	}

	/**
	 * The RPE sends a mail if the pre is unable to execute the schedule
	 * associated with the request.
	 * 
	 * @param plRequestId
	 *            long Request Id
	 * @param plScheduleId
	 *            long Schedule Id
	 * @param pstrJobId
	 *            String Process Name
	 * @param objThrowable
	 *            Throwable
	 * @param pstrAdditionalEmailRecipients
	 */
	private void sendScheduleUnExecutedMail(long plRequestId,
			long plScheduleId, String pstrJobId, String pstrJobName,
			Throwable objThrowable, String pstrAdditionalEmailRecipients) {
		try {
			// CMailer mailer = CMailer.getInstance(CSettings.get("pr.mailtype",
			// "SMTP"));
			EMail email = new EMail();
			if (pstrAdditionalEmailRecipients != null) {
				try {
					email.setTORecipient(CSettings.get("mail.recepientTO")
							+ "," + pstrAdditionalEmailRecipients);
				} catch (AddressException e) {
					objEngineLogger_.warn(
							"Invalid Email Address of recepients. Schedule #"
									+ plScheduleId + " Email(s) #"
									+ pstrAdditionalEmailRecipients, e);
					objEngineLogger_
							.info("Setting default recepient address...");
					email.setTORecipient(CSettings.get("mail.recepientTO"));
				}
			} else {
				email.setTORecipient(CSettings.get("mail.recepientTO"));
			}
			email.setEMailId("USR#" + plRequestId);
			email.setCCRecipient(CSettings.get("mail.recepientCC"));
			StringBuffer sbuf = new StringBuffer(100);
			sbuf.append("Request #");
			sbuf.append(plRequestId);
			sbuf.append(" Job Id #");
			sbuf.append(pstrJobId);
			sbuf.append(" :  Associated Schedule #");
			sbuf.append(plScheduleId);
			sbuf.append(" Could Not Be Executed !!");
			email.setSubject(sbuf.toString());

			sbuf.delete(0, sbuf.length());
			sbuf.append("Request  :");
			sbuf.append(plRequestId);
			sbuf.append(NEW_LINE);
			sbuf.append("Job Id   : ");
			sbuf.append(pstrJobId);
			sbuf.append(NEW_LINE);
			sbuf.append("Job Name : ");
			sbuf.append(pstrJobName);
			sbuf.append(NEW_LINE);
			sbuf.append(NEW_LINE);
			sbuf
					.append("The schedule associated with the request could not be executed because of the following reason:");
			sbuf.append(NEW_LINE);
			sbuf.append(NEW_LINE);
			if (objThrowable instanceof ThreadDeath) {
				sbuf
						.append("Error Message: Process was killed from the Web Service.");
				sbuf.append(NEW_LINE);
				sbuf.append(NEW_LINE);
				sbuf.append("Stack Trace: Not available.");

			} else {
				sbuf.append("Error Message: " + objThrowable.getMessage());
				sbuf.append(NEW_LINE);
				sbuf.append(NEW_LINE);
				sbuf.append("Stack Trace: " + exceptionToString(objThrowable));
			}
			sbuf.append(NEW_LINE);
			sbuf.append(NEW_LINE);
			sbuf.append("Corrective Action:");
			sbuf.append(NEW_LINE);
			sbuf.append(NEW_LINE);
			sbuf.append("\t\t Contact Support Team or Help Desk.");
			if (objThrowable instanceof ThreadDeath) {
				sbuf
						.append(" Please do not execute this process till you have a word with the support team.");
			}
			sbuf.append(NEW_LINE);
			email.setMessageBody(sbuf.toString());

			CMailer.getInstance(CSettings.get("pr.mailtype")).sendAsyncMail(
					email);
		} catch (Throwable e) {
			objEngineLogger_.error("Unable to send the Mail Message due to : ",
					e);
			// Need not be thrown. Email is a facility but not a necessity. Log
			// this error.
		}
	}

	/**
	 * Sends a default mail to the monitor normal receipents named in
	 * mail.properties.
	 * 
	 * @param pstrSubject
	 *            String Mail subject.
	 * @param pstrBody
	 *            String Mail body.
	 * @param bMethodIndicator
	 *            True indicates message will be emailed through Asynchronous
	 *            way.
	 */
	private void sendDefaultMail(String pstrSubject, String pstrBody,
			boolean bMethodIndicator) {
		if (CSettings.get("pr.mailnotification", "OFF").equals("ON")) {
			try {
				// CMailer mailer =
				// CMailer.getInstance(CSettings.get("pr.mailtype", "SMTP"));
				EMail email = new EMail();
				email.setEMailId(Thread.currentThread() + "#"
						+ System.currentTimeMillis());
				email.setTORecipient(CSettings.get(
						"mail.monitor.normal.recepientTO", ""));
				email.setCCRecipient(CSettings.get(
						"mail.monitor.normal.recepientCC", ""));
				email.setSubject(pstrSubject);
				email.setMessageBody(pstrBody);
				if (bMethodIndicator) {
					CMailer.getInstance(CSettings.get("pr.mailtype"))
							.sendAsyncMail(email);
				} else {
					CMailer.getInstance(CSettings.get("pr.mailtype")).sendMail(
							email);
				}
			} catch (Throwable e) {
				objEngineLogger_.error("Exception while sending email", e);
				// Need not be thrown. Email is a facility but not a necessity.
				// Log this error.
			}
		}
	}

	/**
	 * This method gets the status of all the currently running processes. If
	 * the process implements {@link IProcessStatus} then the status is gathered
	 * from the executed class.
	 * 
	 * @return Iterator of collection of {@link stg.pr.beans.CProcessStatusBean}
	 */
	public Iterator<CProcessStatusBean> getProcessStatus() {
		ArrayList<CProcessStatusBean> alProcess = new ArrayList<CProcessStatusBean>();
		IJOBMonitor[] jobs = getStandAloneJobs();
		for (int i = 0; i < jobs.length; i++) {
			IJOBMonitor threadMonitor = jobs[i];
			Object obj = threadMonitor.getIntantiatedObject();
			ProcessRequestEntityBean objPRB = threadMonitor.getRequestBean();
			CProcessStatusBean objCPSB = new CProcessStatusBean();
			objCPSB.setStartTime(threadMonitor.getStartTime());
			objCPSB.setRequestId(objPRB.getReqId());
			objCPSB.setProcessName(objPRB.getJobId() + " - "
					+ objPRB.getJobName());
			switch (threadMonitor.getJobStatus()) {
			case STUCK:
				objCPSB.setThreadStatus("Stuck. Warning!!");
				break;
			case CRITICAL:
				objCPSB.setThreadStatus("Stuck. Critical!!");
				break;
			default:
				objCPSB.setThreadStatus("Normal");
				break;
			}
			if (obj instanceof IProcessStatus) {
				IProcessStatus objStatus = (IProcessStatus) obj;
				try {
					objCPSB.setStatus(objStatus.getStatus());
				} catch (Exception e) {
					objCPSB
							.setStatus("Unable to determine the status. (Raised Exception)"
									+ exceptionToString(e));
					// this is again for information. Need not be thrown.
				}
			} else {
				objCPSB
						.setStatus("Status UnKnown. The class does not implement IProcessStatus.");
				// this is again for information. Need not be thrown.
			}
			alProcess.add(objCPSB);
		}
		return alProcess.iterator();
	}

	/**
	 * Returns the Stack Trace.
	 * 
	 * @param throwable
	 *            Throwable
	 * @return String
	 */
	private String exceptionToString(Throwable throwable) { // This method need
		// not synch as now
		// the bytes are
		// being created
		// within.
		ByteArrayOutputStream bytes_ = new ByteArrayOutputStream();
		PrintWriter pwException_ = new PrintWriter(bytes_, true);
		throwable.printStackTrace(pwException_);
		return bytes_.toString();
	}

	/**
	 * Returns the version number of the class.
	 * 
	 * @return String
	 */
	public String getVersion() {
		return REVISION;
	}

	/**
	 * Returns the current timestamp.
	 * 
	 * The value is taken from Database if the property
	 * <code>pr.currenttimestamp</code> is set to <b>DATABASE</b> otherwise the
	 * server time where PRE is installed is returned. <b>By Default the Engine
	 * will take the SERVER time where PRE is installed.</b>
	 * 
	 * @param con
	 *            JDBC Connection
	 * 
	 * @return Current Timestamp
	 * @throws SQLException
	 * @throws IOException
	 * @throws CDateException
	 */
	private Timestamp getCurrentTimestamp(Connection con)
			throws CDateException, IOException, SQLException {
		if (CSettings.get("pr.currenttimestamp", "SERVER").equals("DATABASE")) {
			return CDate.getCurrentSQLTimestamp(con);
		}
		return new Timestamp((new Date()).getTime());
	}

	/**
	 * Adds a job to the Stand Alone Process Vector.
	 * 
	 * Indicates that a new process has started.
	 * 
	 * @param t
	 *            Job
	 * @since 22.01
	 */
	private synchronized void addStandAloneProcess(Thread t) {
		iThreadCounter_.incrementAndGet();
		vectorThreads_.add(t); // This is used while Termination of the Engine.
	}

	/**
	 * Removes the job from the Stand Alone Process Vector.
	 * 
	 * Indicates that process has finished its job.
	 * 
	 * @param t
	 *            Job
	 * @since 22.01
	 */
	private synchronized void removeStandAloneProcess(Thread t) {
		iThreadCounter_.decrementAndGet();
		vectorThreads_.remove(t); // This is used to remoce the thread.
	}

	/**
	 * Adds a job to the Group Process Vector.
	 * 
	 * Indicates that a new process has started.
	 * 
	 * @param t
	 *            Job
	 * @since 22.01
	 */
	private synchronized void addGroupProcess(Thread t) {
		iGrpThreadCounter_.incrementAndGet();
		vectorThreadsGroup_.add(t); // This is used while Termination of the
		// Engine.
	}

	/**
	 * Removes a job from the Group Process Vector.
	 * 
	 * Indicates that a new process has started.
	 * 
	 * @param t
	 *            Job
	 * @since 22.01
	 */
	private synchronized void removeGroupProcess(Thread t) {
		iGrpThreadCounter_.decrementAndGet();
		vectorThreadsGroup_.remove(t); // This is used while Termination of the
		// Engine.
	}

	/**
	 * Returns the IJOBMonitor array for all the jobs in the Stand Alone Process
	 * Vector.
	 * 
	 * @return Array of Jobs
	 * @since 22.01
	 */
	private synchronized IJOBMonitor[] getStandAloneJobs() {
		IJOBMonitor[] jobs = new IJOBMonitor[vectorThreads_.size()];
		for (int i = 0; i < jobs.length; i++) {
			jobs[i] = (IJOBMonitor) vectorThreads_.get(i);
		}
		return jobs;
	}

	/**
	 * Creates a file that stores the message that needs to be passed to the
	 * Controller.
	 * 
	 * This message can be for Bouncing or Rebooting the engine. Rebooting does
	 * a clean shutdown of the engine where as in bounce, the engine simply
	 * exits.
	 * 
	 * @param action
	 * @param counter
	 */
	private void createIniFile(String action, int counter) {
		String strTmpDir = System.getProperty("java.io.tmpdir");
		String strFileSeparator = System.getProperty("file.separator");
		if (!strTmpDir.endsWith(strFileSeparator)) {
			strTmpDir = strTmpDir + strFileSeparator;
		}
		File file = new File(strTmpDir + "pre.ini");
		try {
			PrintWriter ps = new PrintWriter(new FileWriter(file), true);
			ps.println("action=" + action);
			ps.println("counter=" + counter);
			ps.flush();
			ps.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Starts the web server if not started.
	 * 
	 * @return boolean True if started.
	 */
	private boolean startWebServer() {
		if (!httpserver_.isStarted()) {
			/*
			 * Starting the Web Services. The web server starts on the desired
			 * port. If port not set then default port of 8080 is used.
			 */
			if (CSettings.get("pr.webservice", "OFF").equals("ON")) {
				objEngineLogger_.log(LogLevel.NOTICE, "Starting Web Services");
				try {
					httpserver_.start(Integer.parseInt(CSettings.get(
							"pr.webserverport", "8080")));
					while (!httpserver_.isStarted() && !httpserver_.isError()) {
						// do nothing
					}
					if (httpserver_.isError()) {
						objEngineLogger_.error(
								"Unable to start the Web Service.", httpserver_
										.getError());
					}
					// httpserver_.start();
				} catch (NumberFormatException nfe) {
					objEngineLogger_
							.error(
									"Unable to start the Web Service. Invalid Port Number specified.",
									nfe);
				}
			}
		}
		return httpserver_.isStarted();
	}

	/**
	 * Stops the web server is started.
	 * 
	 * @return boolean True if stopped.
	 */
	private boolean stopWebServer() {
		if (httpserver_.isStarted()) {
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Stopping Web Service");
			}
			httpserver_.stop();
		}
		return httpserver_.isStarted();
	}

	/**
	 * Checks the other participating PRE in the cluster.
	 */
	private void checkClusterPRE() {
		// String strAddressPort = CSettings.get("pr.clusteraddress", "");
		// if (strAddressPort.equals("")) {
		// objEngineLogger_.info("PRE is not configured in a cluster mode.");
		// bClusterHandShakeDone_ = true;
		// return;
		// }
		// if (!httpserver_.isStarted()) {
		// objEngineLogger_.info("Web Server is not started therefore PRE will not be able to check the cluster configuration");
		// objEngineLogger_.info("Web Server is a must for cluster configuration. Engine will terminate itself.");
		// bEngineTerminated_ = true;
		// bGroupedEngineTerminated_ = true;
		// tMain_.interrupt();
		// if (tEngine_ != null) {
		// tEngine_.interrupt();
		// }
		// bClusterHandShakeDone_ = true;
		// return;
		// }
		// String[] strAddressPortAry = strAddressPort.split(",");
		// if (strAddressPortAry.length == 0) {
		// objEngineLogger_.log(LogLevel.NOTICE,
		// "PRE is not configured in a cluster mode.");
		// bClusterHandShakeDone_ = true;
		// return;
		// }
		// for (int i = 0; i < strAddressPortAry.length; i++) {
		// if (strAddressPortAry[i] == null || strAddressPortAry[i].equals(""))
		// {
		// objEngineLogger_.error("Invalid cluster address configuration. Skipping Address "
		// + strAddressPortAry[i]);
		// } else {
		// objEngineLogger_.log(LogLevel.NOTICE,
		// "Doing handshake with the cluster PRE.. Address:Port#" +
		// strAddressPortAry[i]);
		// try {
		// PBEEncryptionRoutine routine = new PBEEncryptionRoutine();
		// String strRequest = routine.encode(getPRESignature());
		// if (objEngineLogger_.isEnabledFor(LogLevel.FINEST)) {
		// objEngineLogger_.log(LogLevel.FINEST, "presignature#" + strRequest);
		// }
		// String strVersionCheck = getURLOutput(strAddressPortAry[i],
		// "presignature=" + strRequest);
		//
		// strRequest = routine.encode(getQueueSignature());
		// if (objEngineLogger_.isEnabledFor(LogLevel.FINEST)) {
		// objEngineLogger_.log(LogLevel.FINEST, "queuesignature#" +
		// strRequest);
		// }
		// String strQueueCheck = getURLOutput(strAddressPortAry[i],
		// "queuesignature=" + strRequest);
		//
		// strRequest = routine.encode(getFilterSignature());
		// if (objEngineLogger_.isEnabledFor(LogLevel.FINEST)) {
		// objEngineLogger_.log(LogLevel.FINEST, "filtersignature#" +
		// strRequest);
		// }
		// String strFilterCheck = getURLOutput(strAddressPortAry[i],
		// "filtersignature=" + strRequest);
		// if (!strQueueCheck.equals("EQUALS") ||
		// !strVersionCheck.equals("EQUALS") ||
		// !strFilterCheck.equals("EQUALS")) {
		// objEngineLogger_.error("PRE Cluster configuration does not match. Please check the  "
		// +
		// "configuration...");
		// objEngineLogger_.log(LogLevel.NOTICE,
		// "Unable to start the PRE. PRE will exit.");
		// stopMessageForEmail_ =
		// "PRE Cluster configuration does not match. Please check the configuration.";
		// bEngineTerminated_ = true;
		// bGroupedEngineTerminated_ = true;
		// tMain_.interrupt();
		// if (tEngine_ != null) {
		// tEngine_.interrupt();
		// }
		// }
		// if (strVersionCheck.equals("EQUALS") &&
		// strQueueCheck.equals("EQUALS") && strFilterCheck.equals("EQUALS") ) {
		// // doURLInput(strAddressPortAry[i], getPRESignature());
		// objEngineLogger_.log(LogLevel.NOTICE,
		// "Another PRE is running against the same queue. Address:Port#" +
		// strAddressPortAry[i]);
		// stopMessageForEmail_ =
		// "Another PRE is running against the same queue. Address:Port#" +
		// strAddressPortAry[i];
		// bEngineTerminated_ = true;
		// bGroupedEngineTerminated_ = true;
		// tMain_.interrupt();
		// if (tEngine_ != null) {
		// tEngine_.interrupt();
		// }
		// }
		// } catch (IOException e) {
		// // subsequent PRE is not up. So continue.
		// } catch (CProcessRequestEngineException e) {
		// // subsequent PRE is not up. So continue.
		// }
		// }
		// } //end for
		bClusterHandShakeDone_ = true;
	}

	// /**
	// * Initiate handshake with the clustered PRE.
	// *
	// * @param strAddressPort
	// * @param strQuery
	// * @throws MalformedURLException
	// * @throws IOException
	// * @throws CProcessRequestEngineException
	// */
	// private String getURLOutput(String strAddressPort, String strQuery)
	// throws MalformedURLException, IOException, CProcessRequestEngineException
	// {
	// String strReturn = "Unknown";
	// BufferedReader reader = null;
	// StringBuffer buffer = new StringBuffer();
	// buffer.append("http://");
	// buffer.append(strAddressPort);
	// buffer.append("/status?");
	// buffer.append(strQuery);
	// //Exception must be thrown inorder to stop the PRE requesting the
	// clustered PRE to stop.
	// try {
	// URL url = new URL(buffer.toString());
	// HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
	// objEngineLogger_.info("Handshake initiated...");
	// urlCon.setDoInput(true);
	// urlCon.setDoOutput(true);
	// reader = new BufferedReader(new
	// InputStreamReader(urlCon.getInputStream()));
	// strReturn = reader.readLine();
	// objEngineLogger_.log(LogLevel.FINEST, "Return Value# " + strReturn);
	// // if (strReturn.equals("MISMATCH")) {
	// // objEngineLogger_.log(LogLevel.NOTICE,
	// "Clustered PRE configuration does not match. Please correct the configuration. Cannot initiate stop request.");
	// // throw new CProcessRequestEngineException();
	// // } else if (strReturn.equals("EQUALS")) {
	// // objEngineLogger_.info("Configuration matched...");
	// // objEngineLogger_.info("Requesting stop....");
	// // } else {
	// //
	// objEngineLogger_.error("Unable to determine the response. Cannot initiate stop request.");
	// // throw new CProcessRequestEngineException();
	// // }
	// } catch (MalformedURLException e) {
	// objEngineLogger_.error("Malformed URL. Cluster address is not configured properly.");
	// objEngineLogger_.log(LogLevel.NOTICE,
	// "Unable to do the handshake with the clustered PRE.");
	// if (objEngineLogger_.isEnabledFor(LogLevel.FINER)) {
	// objEngineLogger_.log(LogLevel.FINER, "MalformedURLException", e);
	// }
	// throw e;
	// } catch (ConnectException e) {
	// objEngineLogger_.error("ConnectException. Clustered PRE not up.");
	// objEngineLogger_.log(LogLevel.NOTICE,
	// "Unable to do the handshake with the clustered PRE.");
	// if (objEngineLogger_.isEnabledFor(LogLevel.FINER)) {
	// objEngineLogger_.log(LogLevel.FINER, "ConnectException", e);
	// }
	// throw e;
	// }catch (IOException e) {
	// objEngineLogger_.error("Received IO Exception.");
	// objEngineLogger_.log(LogLevel.NOTICE,
	// "Unable to do the handshake with the clustered PRE.");
	// if (objEngineLogger_.isEnabledFor(LogLevel.FINER)) {
	// objEngineLogger_.log(LogLevel.FINER, "IOException", e);
	// }
	// throw e;
	// } finally {
	// if (reader != null) {
	// try {
	// reader.close();
	// } catch (IOException e) {
	// }
	// }
	// }
	// return strReturn;
	// }

	// /**
	// * Requests stop request to the clustered PRE.
	// * @param strAddressPort
	// * @param strQuery
	// */
	// private void doURLInput(String strAddressPort, String strQuery) {
	// BufferedReader reader = null;
	// StringBuffer buffer = new StringBuffer();
	// buffer.append("http://");
	// buffer.append(strAddressPort);
	// buffer.append("/stop?");
	// buffer.append(strQuery);
	// try {
	// URL url = new URL(buffer.toString());
	// HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
	// urlCon = (HttpURLConnection) url.openConnection();
	// urlCon.setDoInput(true);
	// urlCon.setDoOutput(true);
	// reader = new BufferedReader(new
	// InputStreamReader(urlCon.getInputStream()));
	// String str = reader.readLine();
	// if (str.equals("STOPPED")) {
	// objEngineLogger_.log(LogLevel.NOTICE,
	// "Clustered PRE stopped successfully. Currently running JOBs will not be terminated.");
	// }
	// } catch (MalformedURLException e) {
	// objEngineLogger_.error("Malformed URL. Cluster address is not configured properly");
	// objEngineLogger_.log(LogLevel.NOTICE,
	// "Please check the clustered PRE manually. Unable to do handshake.");
	// if (objEngineLogger_.isEnabledFor(LogLevel.FINER)) {
	// objEngineLogger_.log(LogLevel.FINER, "MalformedURLException", e);
	// }
	// // exceptionToString(e);
	// } catch (IOException e) {
	// objEngineLogger_.error("IO Exception. Cluster address is not configured properly");
	// // exceptionToString(e);
	// objEngineLogger_.log(LogLevel.NOTICE,
	// "Please check the clustered PRE manually. Unable to do handshake.");
	// if (objEngineLogger_.isEnabledFor(LogLevel.FINER)) {
	// objEngineLogger_.log(LogLevel.FINER, "IOException", e);
	// }
	// } finally {
	// if (reader != null) {
	// try {
	// reader.close();
	// } catch (IOException e) {
	// }
	// }
	// }
	// }

	// /**
	// * Generates the Signature for filter conditions.
	// *
	// * @return PRE Singature
	// */
	// public String getFilterSignature() {
	// // int iSignature = -1;
	// objEngineLogger_.log(LogLevel.FINEST, "Generating Signature..");
	// StringBuffer buffer = new StringBuffer();
	// buffer.append(CSettings.get("pr.requesttypefilter"));
	// buffer.append("#");
	// buffer.append(CSettings.get("pr.processrequesttype"));
	// buffer.append(REVISION);
	// return buffer.toString();
	// }
	//
	// /**
	// * Generates the Signature for queue.
	// *
	// * @return PRE Singature
	// */
	// public String getQueueSignature() {
	// // int iSignature = -1;
	// objEngineLogger_.log(LogLevel.FINEST, "Generating Signature..");
	// // S =
	// dataSourceFactory_.getDataSource(CSettings.get("pr.dsforstandaloneeng")).getClass().getName();
	// // StringBuffer buffer = new StringBuffer();
	// // CPoolAttribute attributes =
	// poolManager_.getPoolAttributes(CSettings.get("pr.dsforstandaloneeng"));
	// // buffer.append(attributes.getURL());
	// // buffer.append(attributes.getUser());
	// //// buffer.append(attributes.getPassword()); //With new PBE algorithm in
	// JDBCPool the encrypted password will not be the same.
	// // buffer.append(attributes.getDriver());
	// // attributes =
	// poolManager_.getPoolAttributes(CSettings.get("pr.dsforgroupeng"));
	// // buffer.append(attributes.getURL());
	// // buffer.append(attributes.getUser());
	// //// buffer.append(attributes.getPassword()); //With new PBE algorithm in
	// JDBCPool the encrypted.
	// // buffer.append(attributes.getDriver());
	// // iSignature = buffer.toString().hashCode();
	// // buffer.delete(0, buffer.length());
	// // buffer.append("queuesignature=");
	// return "9810818y516dj17t418hoin13y6661001";
	// }
	//    
	// /**
	// * Generates the Signature for PRE.
	// *
	// * @return PRE Singature
	// */
	// public String getPRESignature() {
	// objEngineLogger_.log(LogLevel.FINEST, "Generating Signature..");
	// StringBuffer buffer = new StringBuffer();
	// buffer.append(strBundleDetailsArray_[0]);
	// buffer.append(strBundleDetailsArray_[1]);
	// buffer.append(strBundleDetailsArray_[2]);
	// return buffer.toString();
	// }

	/**
	 * Refreshes the JDBC connection.
	 * 
	 * The method will check the passed connection if it is active. If found
	 * active it will throw an exception. This method should be called only when
	 * an IO or SQLException is received. The method expects that the passed
	 * connection is not active.
	 * 
	 * The method then returns the connection to the pool and tries to get a new
	 * connection from the pool identified by the parameter
	 * <code>pstrPoolIdentifier</code>. This is done for <code>iTries</code>.
	 * Even after the said tires if the JDBC connection is not refreshed then
	 * the method throws an exception.
	 * 
	 * @param iTries
	 *            Number of tries the method should do the refresh.
	 * @param pcon
	 *            Inactive JDBC Connection.
	 * @param pstrPoolIdentifier
	 *            Pool Identifier.
	 * @return Connection
	 * @throws Exception
	 */
	private Connection refreshJDBCConnection(int iTries, Connection pcon,
			String pstrPoolIdentifier) throws Exception {
		try {
			// if (pcon instanceof jdbc.tuning.ConnectionWrapper) {
			// ConnectionWrapper wrapper = (ConnectionWrapper) pcon;
			// if (wrapper.isActive()) {
			// throw new
			// CProcessRequestEngineException("Connection is active.");
			// }
			// }
			pcon.close();
		} catch (SQLException sqle) {
			// dummy catch.
		}
		Exception exception = null;
		Connection con = null;
		for (int i = 0; i < iTries; i++) {
			if (objEngineLogger_.isInfoEnabled()) {
				objEngineLogger_.info("Waiting for #" + lWaitInterval_
						+ " Seconds before refreshing the JDBC Connection");
			}
			try {
				Thread.yield();
				TimeUnit.SECONDS.sleep(lWaitInterval_);
				// Thread.sleep(lWaitInterval_);
			} catch (InterruptedException ie) {
				// do nothing
			}
			exception = null;
			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_.debug("Refreshing JDBCConnection. Try #"
						+ (i + 1) + " of #" + iTries);
			}
			// it is expected to get exceptions.
			try {
				// con = poolManager_.getConnection(pstrPoolIdentifier);
				con = dataSourceFactory_.getDataSource(pstrPoolIdentifier)
						.getConnection();
				if (objEngineLogger_.isDebugEnabled()) {
					objEngineLogger_
							.debug("JDBCConnection refreshed...Restarting the infinite loop.");
				}
			} catch (SQLException sqle) {
				exception = sqle;
			} catch (IOException ioe) {
				exception = ioe;
			} catch (Exception e) {
				exception = e;
			}
			if (con != null)
				return con;
			if (objEngineLogger_.isDebugEnabled()) {
				objEngineLogger_
						.debug("Unable to refresh JDBCConnection even after attempt #"
								+ (i + 1));
			}
		}
		throw exception;
	}

	/**
	 * Kills the given process.
	 * 
	 * @param strRequestId
	 * @param strUserId
	 * @param strReason
	 * @return ProcessRequestEntityBean
	 */
	@SuppressWarnings("deprecation")
	protected synchronized ProcessRequestEntityBean killProcess(
			String strRequestId, String strUserId, String strReason) {
		for (Iterator<Thread> iterator = vectorThreads_.iterator(); iterator
				.hasNext();) {
			ThreadProcess tp = (ThreadProcess) iterator.next();
			ProcessRequestEntityBean bean = tp.getRequestBean();
			if (strRequestId.equals(bean.getReqId() + "")) {
				try {
					objEngineLogger_.log(LogLevel.NOTICE, "JOB " + strRequestId
							+ " is being terminated by user#" + strUserId
							+ " for reason " + strReason);
					tp.stop();
				} catch (Throwable t) {
				}
				return bean;
			}
		}
		return null;
	}

	/**
	 * Returns the {@link PREContext}.
	 * 
	 * @return PREContext
	 */
	protected PREContext getContext() {
		return context_;
	}

//	/**
//	 * Reads the license information.
//	 * 
//	 * @return license contents.
//	 * @throws Exception
//	 */
//	private LicenseContent readLicense() throws Exception {
//		LicenseManager lm = new LicenseManager(new LicenseParamImpl());
//		return lm.verify();
//	}

//	/**
//	 * Installs the license.
//	 * 
//	 * @throws Exception
//	 */
//	private void installLic() throws Exception {
//		LicenseManager lm = new LicenseManager(new LicenseParamImpl());
//		File licenseFile = new File(CSettings.get("pr.licensefile",
//				"pre.license"));
//		lm.install(licenseFile);
//	}

	/**
	 * @param st
	 */
	private void closeSQLStatement(Statement st) {
		if (st == null)
			return;
		try {
			st.close();
		} catch (Throwable t) {
		}
	}

	/**
	 * Invokes logical stop on the given process if the process has implemented
	 * {@link ITerminateProcess}.
	 * 
	 * @param strRequestId
	 * @param userId
	 * @param reason
	 * @return boolean True indicates the invoke of stop was a success otherwise
	 *         false.
	 */
	protected synchronized boolean invokeStopProcess(String strRequestId,
			String userId, String reason) {
		for (Iterator<Thread> iterator = vectorThreads_.iterator(); iterator
				.hasNext();) {
			ThreadProcess tp = (ThreadProcess) iterator.next();
			ProcessRequestEntityBean bean = tp.getRequestBean();
			if (userId == null || "".equals(userId)) {
				userId = "Not Supplied";
			}
			if (reason == null || "".equals(reason)) {
				reason = "User requested";
			}
			if (strRequestId.equals(bean.getReqId() + "")) {
				if (tp.getIntantiatedObject() instanceof ITerminateProcess) {
					if (objEngineLogger_.isInfoEnabled()) {
						objEngineLogger_
								.info("Requesting USER Logical Termination of job \""
										+ ((tp.getRequestBean().getJobId() == null) ? ""
												: tp.getRequestBean()
														.getJobId()
														.toUpperCase(Locale.US))
										+ "\" Request #"
										+ strRequestId
										+ " UserId #"
										+ userId
										+ " Reason : "
										+ reason);
					}
					((ITerminateProcess) tp.getIntantiatedObject())
							.terminate(reason);
					tp.setJobStatus(JOB_STATUS.TOBETERMINATED);
					return true;
				} else {
					throw new RuntimeException(
							"JOB does not implement ITerminateProcess process.");
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if to be rebooted.
	 * 
	 * @return boolean
	 */
	protected boolean isReboot() {
		return bReBoot.get();
	}

	/**
	 * Sets the reboot flag.
	 * 
	 * @param reboot
	 * @return boolean
	 */
	protected boolean setReboot(boolean reboot) {
		bReBoot.set(reboot);
		return reboot;
	}

	/**
	 * Returns the sleep time.
	 * 
	 * @return long
	 */
	protected long getRebootSleepTime() {
		return lRebootSleepTime;
	}

	/**
	 * Set the sleep time.
	 * 
	 * @param lrebootsleeptime
	 */
	protected void setRebootSleepTime(long lrebootsleeptime) {
		lRebootSleepTime = lrebootsleeptime;
	}

	/**
	 * Returns the maximum reboot counter.
	 * 
	 * @return int
	 */
	protected int getRebootMaxCounter() {
		return lRebootMaxCounter;
	}

	/**
	 * Sets the reboot maximum counter.
	 * 
	 * @param lrebootmaxcounter
	 */
	protected void setRebootMaxCounter(int lrebootmaxcounter) {
		lRebootMaxCounter = lrebootmaxcounter;
	}

	/**
	 * Returns the current reboot counter.
	 * 
	 * @return int
	 */
	protected int getRebootCounter() {
		return iRebootCounter.get();
	}

	/**
	 * Sets the current reboot counter.
	 * 
	 * @param irebootcounter
	 */
	protected void setRebootCounter(int irebootcounter) {
		iRebootCounter.set(irebootcounter);
	}

	/**
	 * Activates the engine for the given state.
	 * 
	 * @param state
	 */
	public void activate(HeartBeatState state) {
		objEngineLogger_.info("Activating state " + state.name() + " ...");
		synchronized (instance_) {
			if (state_ != state) {
				state_ = state;
				instance_.notifyAll();
			}
		}
	}

	/**
	 * Deactivates the engine and plungs into un-conditional wait till activate
	 * is called.
	 */
	public void deactivate() {
		synchronized (instance_) {
			try {
				while (state_ == HeartBeatState.PASSIVE) {
					instance_.wait();
				}
			} catch (InterruptedException e) {
				// do nothing.
			}
		}
	}

	/**
	 * Returns the current heart beat state.
	 * 
	 * @return HeartBeatState
	 */
	public HeartBeatState getHeartBeatState() {
		return state_;
	}

	private StringBuffer getDefaultEmailAttributes() {
		StringBuffer sbuffer = new StringBuffer(100);
		sbuffer.append("Product Name          :");
		sbuffer.append(info.getName());
		sbuffer.append(NEW_LINE);
		sbuffer.append("Product Version       :");
		sbuffer.append(info.getVersion());
		sbuffer.append(NEW_LINE);
		sbuffer.append("Packaged On           :");
		sbuffer.append(info.getBundledOn());
		sbuffer.append(NEW_LINE);
		sbuffer.append("Cluster Node          :");
		sbuffer.append(member_.getInetSocketAddress().toString());
		sbuffer.append(NEW_LINE);
		if (state_ != HeartBeatState.STOP) {
			try {
				sbuffer.append("Cluster Participants  :");
				for (String member : Hazelcast.getConfig().getNetworkConfig()
						.getJoin().getTcpIpConfig().getMembers()) {
					sbuffer.append("[");
					sbuffer.append(member);
					sbuffer.append("];");
				}
			} catch (IllegalStateException e) {
				sbuffer
						.append("Unable to retrieve as Cluster is being shutdown");
			}
		} else {
			sbuffer.append("Cluster Operation  : Shutdown");
		}
		sbuffer.append(NEW_LINE);
		return sbuffer;
	}

	/**
	 * Prints all possible memory leaks. The caller should check if this
	 * instance is the only instance on the cluster topology.
	 */
	private void printPossibleMemoryLeaks() {
		for (Iterator<String> enumerator = context_.getAttributeNames()
				.iterator(); enumerator.hasNext();) {
			String str = enumerator.next();
			System.err.println("Possible Memory Leak [Key#" + str
					+ "\t Object#"
					+ context_.getAttribute(str).getClass().getName() + "]");
		}
	}

	/**
	 * Creates or update the request on Retry.
	 * 
	 * @param pcon
	 * @param prBean
	 * @param pobjParamsEb
	 * @param pobjDdcParams
	 * @param pobjPrCont
	 * @param pobjParamsCont
	 * @return request id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private long proCreateRetry(Connection pcon,
			ProcessRequestEntityBean prBean,
			ProcessReqParamsEntityBean pobjParamsEb,
			CDynamicDataContainer pobjDdcParams,
			ProcessRequestController pobjPrCont,
			ProcessReqParamsController pobjParamsCont) throws SQLException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		boolean procreate = "CREATE".equals(CSettings.get("pr.retryOption",
				"UPDATE"));
		IRequestIdGenerator generator = null;
		long newRequestId = -1L;
		try {
			if (procreate) {
				Class<?> objClass = this.getClass().getClassLoader().loadClass(
						CSettings.get("pr.schedulerequestidgenerator"));
				Object obj = objClass.newInstance();
				if (obj instanceof IRequestIdGenerator) {
					generator = (IRequestIdGenerator) obj;
				} else {
					throw new ClassCastException("");
				}
				generator.setConnection(pcon);
				newRequestId = generator.generateRequestId();
			}
			ProcessRequestEntityBean prEBean;
			if (procreate) {
				prEBean = (ProcessRequestEntityBean) prBean.clone();
			} else {
				prEBean = prBean;
			}
			prEBean.setRStatus(false);
			if (procreate)
				prEBean.setReqId(newRequestId);
			prEBean.setReqStat(REQUEST_STATUS.QUEUED.getID());
			Day day = new Day(prBean.getScheduledTime());
			TimeUnit unit = TimeUnit.valueOf(prBean.getRetryTimeUnit());
			if (unit == TimeUnit.MINUTES) {
				day.advance(Calendar.MINUTE, prBean.getRetryTime());
			} else {
				day.advance(Calendar.HOUR_OF_DAY, prBean.getRetryTime());
			}
			prEBean.setScheduledTime(day.getTimestamp());
			prEBean.setRetryCnt(prEBean.getRetryCnt() + 1);
			pcon.setAutoCommit(false);
			if (procreate) {
				pobjPrCont.create(prEBean);
			} else {
				pobjPrCont.update(prEBean);
			}
			if (procreate) {
				pobjDdcParams.beforeFirst();
				while (pobjDdcParams.next()) {
					ProcessReqParamsEntityBean paramsEBean = (ProcessReqParamsEntityBean) ((ProcessReqParamsEntityBean) pobjDdcParams
							.get()).clone();
					paramsEBean.setRStatus(false);
					paramsEBean.setReqId(newRequestId);
					pobjParamsCont.create(paramsEBean);
				}

			}
			pcon.commit();
			pcon.setAutoCommit(true);
		} catch (ClassNotFoundException cnfe) {
			throw cnfe;
		} catch (CBeanException e) {
			throw new SQLException(e);
		} catch (CDynamicDataContainerException e) {
			throw new SQLException(e);
		} finally {
			if (pcon != null) {
				pcon.setAutoCommit(true);
			}
		}
		return newRequestId;
	}

} // end of CProcessRequestEngine.java
