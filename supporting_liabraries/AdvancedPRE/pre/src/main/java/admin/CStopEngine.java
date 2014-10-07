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
 * $Revision: 3309 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/admin/CStopEngine.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/admin/CStopEngine.java $
 * 
 * 22    10/30/09 5:07p Kedarr
 * Corrected the arguments.
 * 
 * 21    10/27/09 11:56a Kedarr
 * Changes made for encrypting signatures.
 * 
 * 20    9/01/09 9:08a Kedarr
 * Removed unnecessary creation of variables and added code to catch
 * specific exceptions.
 * 
 * 19    8/30/09 9:12p Kedarr
 * Added logging messages.
 * 
 * 18    8/30/09 8:25p Kedarr
 * Changes made to make use of the Data Source Factory class introduced in
 * this version.
 * 
 * 17    8/23/09 1:15p Kedarr
 * Changes made to externalize POOL names
 * 
 * 16    4/08/09 5:38p Kedarr
 * Changes made to ping the web server of PRE before submitting the reboot
 * request. This ensures that the request is not submitted if the engine
 * is off.
 * 
 * 15    3/21/09 3:59p Kedarr
 * Changed the sequencing of the CSettings class to log4j.
 * 
 * 14    3/11/09 5:17p Kedarr
 * req type is now accepted as a parameter instead of the class creating
 * it randomly.
 * 
 * 13    2/04/09 11:24a Kedarr
 * Jar input stream closed.
 * 
 * 12    9/30/08 5:14p Kedarr
 * Changes made for incorporating the new mailer functionality.
 * 
 * 11    7/10/08 7:25a Kedarr
 * Corresponding changes made to use the job_id and job_name.
 * 
 * 10    3/01/07 4:43p Kedarr
 * setRequestType() was changed to setReqType() in the process request
 * bean. Corresponding changes made in the class.
 * 
 * 9     6/06/06 2:30p Kedarr
 * Changes made for INCLUDE and EXCLUDE feature incorporated for
 * processrequesttype. Also, added an optional USER ID variable as a third
 * parameter for the class. The shell scripts should pass the USER ID  as
 * whoami on UNIX and %USERNAME% on windows platform.
 * 
 * 8     5/04/06 12:13p Kedarr
 * Changes made for the New JDBC Pool implemented in PRE.
 * Updated javadoc.
 * 
 * 7     8/24/05 2:26p Kedarr
 * The program is modifed to reflect the following new property
 * currenttimestamp that is added in pr.properties file. 
 * If the value is set to DATABASE then the engine will get the current
 * timestamp from the Database. Otherwise the current timestamp will be
 * taken from the server on which the PRE is installed. NOTE that the
 * current Database supported are Oracle, Microsoft SQL
 * and IBM DB2. To make the PRE independent of the Database do not set the
 * value for this property.
 * 
 * 6     8/24/05 10:58a Kedarr
 * 18.04
 * 
 * 5     8/24/05 10:49a Kedarr
 * Changes made for adding logger information and for intantiating the
 * class, to generate the request id, in the same way as PRE does.
 * 
 * 4     7/15/05 2:29p Kedarr
 * Removed All Unused variables. Added a method to return REVISION number.
 * 
 * 3     6/30/05 5:28p Kedarr
 * Formatting changes and added a specific exception if the class does not
 * implement IRequestIdGenerator class.
 * 
 * 2     6/30/05 5:26p Kedarr
 * Changes made to make this class independent of GMAC logic to stop the
 * engine.
 * 
 * 1     1/11/05 6:02p Kedarr
 * This class is created to do a clean shutdown of the engine.
 *
 * Created on Oct 7, 2004
 *
 */
package admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import stg.customclassloader.CCustomClassLoaderFactory;
import stg.customclassloader.IPREClassLoaderClient;
import stg.pr.beans.ProcessRequestController;
import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.IProcessRequest.REQUEST_STATUS;
import stg.pr.engine.IProcessRequest.REQUEST_TYPE;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.mailer.CMailer;
import stg.pr.engine.mailer.EMail;
import stg.pr.engine.scheduler.IRequestIdGenerator;
import stg.utils.CDate;
import stg.utils.CSettings;
import stg.utils.Day;
import stg.utils.HazelcastConfigLoader;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.stg.logger.LogLevel;

/**
 * The basic purpose of this class is to stop the engine gracefully.
 * 
 * This class facilitates the user to stop the Engine and do a clean shutdown. The
 * engine will wait for all the running process to end before shutting itself down. 
 * If all the running processes have crossed the stuck thread max limit then the PRE
 * will kill itself.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision:: 3309      $
 */
public class CStopEngine implements IPREClassLoaderClient {

    /**
     * <b>log4<i>J</i></b> logger instance.
     * Comment for <code>logger</code>.
     */
    private static Logger logger;
    
	/**
	 * Stores the version details of the PRE. 
	 */
	private String[] strBundleDetailsArray_;

    /**
     * Default Constructor.
     * @throws CProcessRequestEngineException 
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public CStopEngine() throws FileNotFoundException, IOException, CProcessRequestEngineException {
        // do nothing.
    }

    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    private static final String REVISION = "$Revision:: 3309       $";

    /**
     * Returns the revision number of the .class.
     *
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }

    /**
     * Main method.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("usage java [-Duserid=\"<userid>\" -Djobid=\"<jobid>\" -Djobname=\"<job name>\" -Dreqtype=\"GENERAL\"] -classpath $CLASSPATH admin.CStopEngine <prinit.properties> ");
            System.out.println("where userid  is the unique identifier for the user. Default value \"SHELL\". This value gets stored in process_request.user_id field.");
            System.out.println("      jobid   is the unique identifier for the job. Default value \"STOPENG\". This value gets stored in process_request.job_id field.");
            System.out.println("      jobname is the job description. Default value \"Stop Engine\". This value gets stored in process_request.job_name field.");
            System.out.println("      reqtype is the job type. Default value \"GENERAL\". This value gets stored in process_request.req_type field.");
            return;
        }
        CStopEngine engine = new CStopEngine();
        engine.stop(args);
    }

    /**
     * Stops the PRE if running.
     * Does the handshake with the PRE using the hostname and port on which the Web Server is hosted.
     * If the web server is not on, then does not stop the PRE. 
     *
     * @param args
     * @throws Exception
     */
    private void stop(String[] args) throws Exception {
        CSettings.getInstance().load(args[0]);
        String NEW_LINE = System.getProperty("line.separator");
        String SYSTEM_USER = System.getProperty("user.name");
        String strUserId = System.getProperty("userid", "SHELL");
        String strJobId = System.getProperty("jobid", "STOPENG");
        String strJobName = System.getProperty("jobname", "Stop Engine");
        String strReqType = System.getProperty("reqtype", "GENERAL");
        System.setProperty("java.util.prefs.PreferencesFactory", "stg.utils.prefs.PREPreferencesFactoryImpl");
        System.setProperty("java.util.prefs.PreferencesFactory.file", CSettings.get("pr.java.util.prefs.PreferencesFactory.file", null));
        logger = Logger.getLogger("CStopEngine");
        logger.getLoggerRepository().getRootLogger().addAppender(
                new ConsoleAppender(new SimpleLayout()));
        logger.getLoggerRepository().getRootLogger().setLevel(Level.ERROR);
        logger.getLoggerRepository().getLogger("JDBC").setLevel(Level.OFF);
    	getBundleDetails();
        printVersion();
        logger.log(LogLevel.NOTICE, "Initializing cluster..");
        logger.info("Using configuration from file " + CSettings.get("pr.hazelcast.config"));
        HazelcastClient hclient = null; 
        try {
            hclient = HazelcastClient.newHazelcastClient(HazelcastConfigLoader.loadClientConfiguration(System.getProperty("hazelcast.config")));
            logger.log(LogLevel.NOTICE, "Cluster initialized.");
        } catch (Throwable e) {
            logger.log(LogLevel.NOTICE, "PRE is not active. If you think that this message is not correct then please kill the process manually.");
            logger.log(LogLevel.NOTICE, "Stop Request not submitted...");
            return;
        } finally {
            if (hclient != null) {
                hclient.shutdown();
            }
        }
        
        Connection conn = null;
        ProcessRequestController prc = null;
        logger.log(LogLevel.NOTICE, "Initializing Data Source Factory...");
        //Load the supposedly data source factory class.
        Class<?> classDataSource = Class.forName(CSettings.get("pr.dataSourceFactory", "stg.pr.engine.datasource.defaultimpl.PREDataSource"));
        //Instantiate supposedly Data Source
        Object object = classDataSource.newInstance();
        IDataSourceFactory dataSourceFactory_;
        if (object instanceof IDataSourceFactory) {
            dataSourceFactory_ = (IDataSourceFactory) object;
            //Now that it is IDataSourceFactory initialize.
            dataSourceFactory_.initialize(new File(CSettings.get("pr.dataSourceFactoryConfigFile")));
        } else {
            throw new CProcessRequestEngineException("Class does not implement IDataSource");
        }
        logger.log(LogLevel.NOTICE, "Data Source Factory initialized...");
        try {
            conn = dataSourceFactory_.getDataSource(CSettings.get("pr.dsforstandaloneeng")).getConnection();
            ClassLoader pcl = (CCustomClassLoaderFactory.getInstance())
                    .getClassLoader(new CStopEngine());

            Class<?> c = pcl.loadClass(CSettings
                    .get("pr.schedulerequestidgenerator"));

            Object obj = c.newInstance();
            if (!(obj instanceof IRequestIdGenerator)) {
                throw new Exception(
                        "Class specified in the property schedulerequestidgenerator does not implement interface IRequestIdGenerator. Engine cannot be stopped.");
            }
            IRequestIdGenerator objGenerator = (IRequestIdGenerator) obj;
            objGenerator.setConnection(conn);
            long lRequestId = objGenerator.generateRequestId();

            if (lRequestId <= 0) {
                throw new Exception(
                        "Request Id generator returned invalid request id #"
                                + lRequestId + ". Engine cannot be stopped.");
            }
            String strFilterCondition = CSettings.get("pr.requesttypefilter", "XYZ");
            if (!strFilterCondition.equals("INCLUDE") && !strFilterCondition.equals("EXCLUDE") ) {
                throw new CProcessRequestEngineException("Invalid FILTER type specified for property requesttypefilter.");
            }
            
            String strRequestTypes = CSettings.get("pr.processrequesttype", null);
            if (strRequestTypes == null) {
                throw new CProcessRequestEngineException("Invalid processrequesttype defined. There must be a default value.");
            } 

            
            ProcessRequestEntityBean bean = new ProcessRequestEntityBean();
            bean.setReqId(lRequestId);
            bean.setUserId(strUserId);
            bean.setReqDt((new Day()).getTimestamp());
            bean.setReqStat(REQUEST_STATUS.QUEUED.getID());
            bean.setProcessClassNm("stg.pr.engine.startstop.CStopEngine");
            bean.setGrpStInd(REQUEST_TYPE.STANDALONE.getID());
            bean.setJobId(strJobId);
            bean.setJobName(strJobName);
            bean.setScheduledTime(
                    (CSettings.get("pr.currenttimestamp","SERVER").equals("DATABASE"))?
                        CDate.getCurrentSQLTimestamp(conn):
                            new Timestamp((new Date()).getTime())
                    );
            bean.setStuckThreadLimit(1);
            bean.setStuckThreadMaxLimit(2);
            bean.setReqType(strReqType);
            conn.setAutoCommit(false);
            prc = new ProcessRequestController(conn);
            prc.create(bean);
            conn.commit();
            prc.close();
            String strSubject = "STOP request has been sent to PRE. Request Id # " + lRequestId;
            logger.log(LogLevel.NOTICE, strSubject);
            logger.log(LogLevel.NOTICE, "STOP request generated by " +  SYSTEM_USER);
            logger
                    .log(LogLevel.NOTICE,"Please tail the console to find whether the engine is terminated.");
            logger
                    .log(LogLevel.NOTICE,"Please note that the engine will wait till all the current running processes are executed.");
            StringBuffer sbuffer = new StringBuffer(100);
            sbuffer.append("Product Name              :");
            sbuffer.append(strBundleDetailsArray_[0]);
            sbuffer.append(NEW_LINE);
            sbuffer.append("Product Version           :");
            sbuffer.append(strBundleDetailsArray_[1]);
            sbuffer.append(NEW_LINE);
            sbuffer.append("Packaged On               :");
            sbuffer.append(strBundleDetailsArray_[2]);
            sbuffer.append(NEW_LINE);
            sbuffer.append("Stop Request Sent On      :");
            sbuffer.append(new Date());
            sbuffer.append(NEW_LINE);
            sbuffer.append("Stop Request Sent By      :");
            sbuffer.append(strUserId);
            sbuffer.append(NEW_LINE);
            sbuffer.append("Sent through user login   :");
            sbuffer.append(SYSTEM_USER);
            sbuffer.append(NEW_LINE);
            email(strSubject, sbuffer.toString(), lRequestId);
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception e1) {
            }
            throw e;
        } finally {
            try {
                if (prc != null)
                    prc.close();
            } catch (SQLException e) {
                // dummy handle exception
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // dummy
            }
            if (dataSourceFactory_ != null) {
                logger.log(LogLevel.NOTICE, "Shutting down the Data Source Factory.");
                dataSourceFactory_.shutdown();
                logger.log(LogLevel.NOTICE, "Data Source Factory shutdown complete.");
            }
        }
    	Hazelcast.shutdownAll();
    }

    /**
     * Returns the version number of the class.
     * 
     * @return String
     */
    public String getVersion() {
        return REVISION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.customclassloader.IPREClassLoaderClient#getCustomClassLoaderClassPath()
     */
    public String getCustomClassLoaderClassPath() {
        return CSettings.get("pr.objclasspathforclassloader");
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.customclassloader.IPREClassLoaderClient#getSystemLoadedClasses()
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

    /*
     * (non-Javadoc)
     * 
     * @see stg.customclassloader.IPREClassLoaderClient#isReload()
     */
    public boolean isReload() {
        return (((CSettings.get("pr.reloadobjclasses").equals("Y")) ? true
                : false));
    }
    
    /**
     * Returns the Bundle name and the version of the bundled pacakge.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws CProcessRequestEngineException
     */
    private  void getBundleDetails() throws FileNotFoundException, IOException, CProcessRequestEngineException
    {
        strBundleDetailsArray_ = new String[] {"Unknown", "Unknown", "Unknown"};

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
            strBundleDetailsArray_[0] = (String) attributes.getValue("Bundle-Name");
            strBundleDetailsArray_[1] = (String) attributes.getValue("Bundle-Version");
            strBundleDetailsArray_[2] = (String) attributes.getValue("Bundled-On");
        } catch (MalformedURLException e) {
            //do nothing
        } catch (FileNotFoundException fnfe) {
            //do nothing
        } catch (IOException ioe) {
            //do nothing
        }
        return;
    }

    /**
     * Prints the version of the Engine.
     * 
     * @throws CProcessRequestEngineException
     * @throws IOException
     * @throws FileNotFoundException
     * @since 16
     */
    private void printVersion() throws FileNotFoundException, IOException, CProcessRequestEngineException {
        StringBuffer sbuffer = new StringBuffer(100);
        sbuffer.append("Product Name: \"");
        sbuffer.append(strBundleDetailsArray_[0]);
        sbuffer.append("\" Version: \"");
        sbuffer.append(strBundleDetailsArray_[1]);
        sbuffer.append("\" Packaged On \"");
        sbuffer.append(strBundleDetailsArray_[2]);
        logger.log(LogLevel.NOTICE, sbuffer.toString());
    }
    
    /**
     * Sends email.
     * 
     * @param subject
     * @param body
     * @param requestId
     */
    private void email(String subject, String body, long requestId) {
//        CMailer mailer = CMailer.getInstance(CSettings.get("pr.mailtype"));
        EMail email = new EMail();
        try {
            email.setTORecipient(CSettings.get("mail.monitor.normal.recepientTO"));
        } catch (AddressException e) {
            logger.error("Recepient TO not set", e);
        }
        try {
            email.setCCRecipient(CSettings.get("mail.monitor.normal.recepientCC"));
        } catch (AddressException e) {
            logger.error("Recepient CC not set");
            e.printStackTrace();
        }
        email.setEMailId("STOP#" + requestId);
        email.setSubject(subject);
        email.setMessageBody(body);
        try {
            CMailer.getInstance(CSettings.get("pr.mailtype")).sendMail(email);
        } catch (MessagingException e) {
            logger.error("Unable to send an email message", e);
        }
    }
    
    /**
     * Accepts the input and returns true if input equals Y.
     * 
     * @return boolean
     */
    protected boolean acceptInput() {
        Scanner scanner = new Scanner(System.in);
        String str;
        str = scanner.nextLine();
        if (str != null) {
            return str.trim().equalsIgnoreCase("Y");
        }
        return false;
    }
}