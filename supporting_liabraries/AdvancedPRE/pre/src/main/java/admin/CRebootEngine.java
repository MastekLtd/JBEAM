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
 *
 * $Revision: 3309 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/admin/CRebootEngine.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/admin/CRebootEngine.java $
 * 
 * 20    10/30/09 5:07p Kedarr
 * Corrected the arguments size
 * 
 * 19    10/27/09 2:53p Kedarr
 * Created the logger during the variable declaration.
 * 
 * 18    10/27/09 11:56a Kedarr
 * Changes made for encrypting signatures.
 * 
 * 17    9/01/09 9:08a Kedarr
 * Removed unnecessary creation of variables and added code to catch
 * specific exceptions.
 * 
 * 16    8/30/09 9:12p Kedarr
 * Added logging messages.
 * 
 * 15    8/30/09 8:28p Kedarr
 * Added the code to invoke datasource factory shutdown.
 * 
 * 14    8/30/09 8:22p Kedarr
 * Changes made to make use of the Data Source Factory class introduced in
 * this version.
 * 
 * 13    8/23/09 1:15p Kedarr
 * Changes made to externalize POOL names
 * 
 * 12    4/08/09 5:38p Kedarr
 * Changes made to ping the web server of PRE before submitting the reboot
 * request. This ensures that the request is not submitted if the engine
 * is off.
 * 
 * 11    3/21/09 3:58p Kedarr
 * Changed the sequencing of the CSettings class to log4j.
 * 
 * 10    3/11/09 5:16p Kedarr
 * req type is now accepted as a parameter instead of the class creating
 * it randomly.
 * 
 * 9     2/04/09 11:24a Kedarr
 * Jar input stream closed.
 * 
 * 8     9/30/08 5:14p Kedarr
 * Changes made for incorporating the new mailer functionality.
 * 
 * 7     7/10/08 7:21a Kedarr
 * Corresponding changes made to use the job_id and job_name.
 * 
 * 6     3/21/08 11:31p Kedarr
 * Corrected the spelling mistake for Class from classs in javadoc.
 * 
 * 5     3/18/08 2:39p Kedarr
 * Added javadoc.
 * 
 * 4     3/01/07 4:42p Kedarr
 * setRequestType() was changed to setReqType() in the process request
 * bean. Corresponding changes made in the class.
 * 
 * 3     6/06/06 2:30p Kedarr
 * Changes made for INCLUDE and EXCLUDE feature incorporated for
 * processrequesttype. Also, added an optional USER ID variable as a third
 * parameter for the class. The shell scripts should pass the USER ID  as
 * whoami on UNIX and %USERNAME% on windows platform.
 * 
 * 2     5/04/06 12:13p Kedarr
 * Changes made for the New JDBC Pool implemented in PRE.
 * Updated javadoc.
 * 
 * 1     1/16/06 6:59p Kedarr
 * A helper class to reboot the engine.
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
 * This class will be used for rebooting the engine.
 * 
 * This class will facilitate the user to do a clean shutdown and startup of the engine.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision:: 3309          $
 */
public class CRebootEngine implements IPREClassLoaderClient {

    /**
     * <b>log4<i>J</i></b> logger instance.
     * Comment for <code>logger</code>.
     */
    private static Logger logger = Logger.getLogger("CRebootEngine");
    
	/**
	 * Stores the version details of the PRE. 
	 */
	private String[] strBundleDetailsArray_;

    /**
     * Default Constructor.
     */
    public CRebootEngine() {
        // do nothing.
    }

    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    private static final String REVISION = "$Revision:: 3309      $";

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
            System.out.println("usage java [-Duserid=\"<userid>\" -Djobid=\"<jobid>\" -Djobname=\"<job name>\" -Dreqtype=\"GENERAL\"] -classpath $CLASSPATH admin.CRebootEngine <prinit.properties>");
            System.out.println("where userid  is the unique identifier for the user. Default value \"SHELL\". This value gets stored in process_request.user_id field.");
            System.out.println("      jobid   is the unique identifier for the job. Default value \"REBOOTENG\". This value gets stored in process_request.job_id field.");
            System.out.println("      jobname is the job description. Default value \"Reboot Engine\". This value gets stored in process_request.job_name field.");
            System.out.println("      reqtype is the request type. Default value \"GENERAL\". This value gets stored in process_request.req_type field.");
            return;
        }
        CRebootEngine engine = new CRebootEngine();
        engine.reboot(args);
    }
    
    /**
     * Reboots the engine.
     * Does a handshake with the browser.
     * 
     * @param args
     * @throws Exception
     */
    public void reboot(String[] args) throws Exception {
        CSettings.getInstance().load(args[0]);
        String NEW_LINE = System.getProperty("line.separator");
        String SYSTEM_USER = System.getProperty("user.name");
        String strUserId = System.getProperty("userid", "SHELL");
        String strJobId = System.getProperty("jobid", "REBOOTENG");
        String strJobName = System.getProperty("jobname", "Reboot Engine");
        String strReqType = System.getProperty("reqtype", "GENERAL");
        System.setProperty("java.util.prefs.PreferencesFactory", "stg.utils.prefs.PREPreferencesFactoryImpl");
        System.setProperty("java.util.prefs.PreferencesFactory.file", CSettings.get("pr.java.util.prefs.PreferencesFactory.file", null));
        logger.getLoggerRepository().getRootLogger().addAppender(
                new ConsoleAppender(new SimpleLayout()));
        logger.getLoggerRepository().getRootLogger().setLevel(Level.ERROR);
        logger.getLoggerRepository().getLogger("JDBC").setLevel(Level.OFF);
//        logger.getLoggerRepository().getLogger("PoolManager").setLevel(LogLevel.OFF);
        getBundleDetails();
        printVersion();

        logger.log(LogLevel.NOTICE, "Initializing cluster..");
        HazelcastClient hclient = null; 
        try {
            hclient = HazelcastClient.newHazelcastClient(HazelcastConfigLoader.loadClientConfiguration(System.getProperty("hazelcast.config")));
            logger.log(LogLevel.NOTICE, "Cluster initialized.");
        } catch (Throwable e) {
            logger.log(LogLevel.NOTICE, "PRE is not active. If you think that this message is not correct then please kill the process manually.");
            logger.log(LogLevel.NOTICE, "Reboot Request not submitted...");
            return;
        } finally {
            if (hclient != null) {
                hclient.shutdown();
            }
        }
        
        Connection conn = null;
        ProcessRequestController prc = null;
        
        //Load the data source factory class
        logger.log(LogLevel.NOTICE, "Initializing Data Source Factory...");
        Class<?> classDataSource = Class.forName(CSettings.get("pr.dataSourceFactory", "stg.pr.engine.datasource.defaultimpl.PREDataSource"));
        //Instantiate data source factory class
        Object object = classDataSource.newInstance();
        IDataSourceFactory dataSourceFactory_;
        if (object instanceof IDataSourceFactory) {
            dataSourceFactory_ = (IDataSourceFactory) object;
            //Initialize the Data Source
            dataSourceFactory_.initialize(new File(CSettings.get("pr.dataSourceFactoryConfigFile")));
        } else {
            throw new CProcessRequestEngineException("Class does not implement IDataSource");
        }
        logger.log(LogLevel.NOTICE, "Data Source Factory initialized...");

        try {
            conn = dataSourceFactory_.getDataSource(CSettings.get("pr.dsforstandaloneeng")).getConnection();
            ClassLoader pcl = (CCustomClassLoaderFactory.getInstance())
                    .getClassLoader(new CRebootEngine());

            Class<?> c = pcl.loadClass(CSettings
                    .get("pr.schedulerequestidgenerator"));

            Object obj = c.newInstance();
            if (!(obj instanceof IRequestIdGenerator)) {
                throw new Exception(
                        "Class specified in the property schedulerequestidgenerator does not implement interface IRequestIdGenerator. Engine cannot be rebooted.");
            }
            IRequestIdGenerator objGenerator = (IRequestIdGenerator) obj;
            objGenerator.setConnection(conn);
            long lRequestId = objGenerator.generateRequestId();

            if (lRequestId <= 0) {
                throw new Exception(
                        "Request Id generator returned invalid request id #"
                                + lRequestId + ". Engine cannot be rebooted.");
            }
            String strFilterCondition = CSettings.get("pr.requesttypefilter", "XYZ");
            if (!strFilterCondition.equals("INCLUDE") && !strFilterCondition.equals("EXCLUDE") ) {
                throw new CProcessRequestEngineException("Invalid FILTER type specified for property requesttypefilter.");
            }
            
            String strRequestTypes = CSettings.get("pr.processrequesttype", null);
            if (strRequestTypes == null) {
                throw new CProcessRequestEngineException("Invalid processrequesttype defined. There must be a default value.");
            } 

//            StringTokenizer tokenizer = new StringTokenizer(strRequestTypes, ",");
//            String strProcessRequestType = tokenizer.nextToken();
//            ArrayList list = new ArrayList();
//            while (tokenizer.hasMoreTokens()) {
//                list.add(tokenizer.nextToken());
//            }
//            
//            if (strFilterCondition.equals("EXCLUDE")) {
//                strProcessRequestType = strProcessRequestType.replace(
//                        strProcessRequestType.charAt(0), (char) (strProcessRequestType.charAt(0)+1));
//                while (list.contains(strProcessRequestType)) {
//                    strProcessRequestType = strProcessRequestType.replace(
//                            strProcessRequestType.charAt(0), (char) (strProcessRequestType.charAt(0)+1));
//                }
//            }

            ProcessRequestEntityBean bean = new ProcessRequestEntityBean();
            bean.setReqId(lRequestId);
            bean.setUserId(strUserId);
            bean.setReqDt((new Day()).getTimestamp());
            bean.setReqStat(REQUEST_STATUS.QUEUED.getID());
            bean.setProcessClassNm("stg.pr.engine.startstop.CRebootEngine");
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
            String strSubject = "Reboot request has been sent to PRE. Request Id # " + lRequestId;
            logger.log(LogLevel.NOTICE, strSubject);
            logger.log(LogLevel.NOTICE, "Reboot request generated by " +  SYSTEM_USER);
            logger
                    .log(LogLevel.NOTICE,"Please tail the console to find whether the engine is rebooted.");
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
            sbuffer.append("Reboot Request Sent On    :");
            sbuffer.append(new Date());
            sbuffer.append(NEW_LINE);
            sbuffer.append("Reboot Request Sent By    :");
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
     * Bundle name and the version of the bundled package are populated.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws CProcessRequestEngineException
     */
    private void getBundleDetails() throws FileNotFoundException, IOException, CProcessRequestEngineException
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
     * Helper method for sending emails.
     * @param subject
     * @param body
     * @param requestId 
     */
    private void email(String subject, String body, long requestId) {
        EMail email = new EMail();
//        CMailer mailer = CMailer.getInstance(CSettings.get("pr.mailtype"));
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
        email.setEMailId("Reboot#" + requestId);
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