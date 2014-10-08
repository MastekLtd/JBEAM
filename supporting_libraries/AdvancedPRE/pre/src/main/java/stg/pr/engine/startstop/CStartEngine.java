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
 * $Revision: 3320 $
 *
 * $Header: http://172.16.209.156:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/startstop/CStartEngine.java 1419 2010-06-15 03:27:43Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/startstop/CStartEngine.java $
 * 
 * 11    11/11/09 3:16p Kedarr
 * Changes made to identify the java version to 1 point 6.
 * 
 * 10    9/01/09 8:27a Kedarr
 * Removed un-necessary null check and removed the poolconfig file
 * parameter as now this is being stored in pr.properties.
 * 
 * 9     2/04/09 1:15p Kedarr
 * Added static keyword to a final variable.
 * 
 * 8     9/15/08 10:06a Kedarr
 * Changes made for:
 * 1. Enabling and Disabling of log4j logger so that the same properties
 * can be used for both the processes.
 * 2. Reading the JVM properties for PRE through the pr.properties file.
 * 
 * 7     6/20/08 5:06p Kedarr
 * Changes are made to supress an exception that might be caused due to
 * the closing of stream. The error/exception is printed as a warning and
 * only if the loglevel is set to finest.
 * 
 * 6     4/07/08 2:23p Kedarr
 * Changed the Revision from private to public and thus avoid the method
 * getVersion() or showVersion().
 * 
 * 5     3/22/08 12:32a Kedarr
 * Removed the static keyword from the REVISION variable and made it
 * private. In case of interfaces made it as public.
 * 
 * 4     3/22/08 12:14a Kedarr
 * Added REVISION variable.
 * 
 * 3     6/06/06 2:36p Kedarr
 * Implemented interface IStartStop which is a marker interface.
 * 
 * 2     6/06/06 2:35p Kedarr
 * Updated javadoc.
 * 
 * 1     6/01/06 1:05p Kedarr
 * The PRE Controller. Class responsible to start, reboot, bounce and
 * shutdown the PRE.
 * 
 */
package stg.pr.engine.startstop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import stg.utils.CSettings;

import com.stg.logger.LogLevel;

/**
 * Process Request Engine Controller.
 * 
 * This class is responsible for starting the PRE, rebooting / bouncing the PRE.
 * 
 * @version $Revision: 3320 $
 * @author kedarr
 * @since 23.00
 * 
 */
public class CStartEngine implements IStartStop {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 3320              $";

    /**
     * Stores the maximum reboot counter. Comment for
     * <code>iRebootMaxCounter_</code>.
     */
    private int iRebootMaxCounter_;

    /**
     * Stores the sleep time in between reboots. Comment for
     * <code>lRebootSleepTime_</code>.
     */
    private long lRebootSleepTime_;

    /**
     * Stores the current reboot counter. Comment for
     * <code>iRebootCounter_</code>.
     */
    private int iRebootCounter_;

    /**
     * Defines REBOOT mode. Comment for <code>REBOOT</code>.
     */
    public final static String REBOOT = "Reboot";

    /**
     * Defines the BOUNCE mode. Comment for <code>BOUNCE</code>.
     */
    public final static String BOUNCE = "Bounce";

    /**
     * Defines the SHUTDOWN mode. Comment for <code>SHUTDOWN</code>.
     */
    public final static String SHUTDOWN = "Shutdown";

    /**
     * OS related FILE SEPARATOR. Comment for <code>FILE_SEPARATOR</code>.
     */
    final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * Stores the log4j logger. Comment for <code>logger_</code>.
     */
    private Logger logger_ = null;

//    /**
//     * Format for logger pattern layout.
//     */
//    private final String LOG4J_LAYOUT_PATTERN = "<%d{EEEE MMM dd, yyyy HH:mm:ss:SSS}><%p><%t><%c>%m%n";

    private String strLog4jFile_;

    /**
     * Constructor.
     * 
     * @param log4jFile
     *            Log4j configuration file.
     * @throws IOException
     * @throws FileNotFoundException
     * 
     */
    public CStartEngine(String log4jFile) throws FileNotFoundException,
            IOException {
        super();
        strLog4jFile_ = log4jFile;
        enableLog4j();
        if (logger_.isEnabledFor(LogLevel.FINE)) {
            logger_.log(LogLevel.FINE, "Initializing");
        }
    }

    /**
     * Main method.
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        CStartEngine engine = new CStartEngine(args[1]);
        engine.start(args);
    }

    /**
     * Starts the controller.
     * 
     * Method responsible to handle start, reboot, shutdown of PRE.
     * 
     * @param args
     */
    public void start(String[] args) {
        if (logger_.isEnabledFor(LogLevel.FINE)) {
            logger_.log(LogLevel.FINE, "Initialized.");
        }
        if (!SystemUtils.IS_JAVA_1_6) {
        	logger_.log(LogLevel.NOTICE, "Unable to start. Requires Java 1.6");
        	return;
        }

        boolean bStart = true;
        String[] cmd = null;
        while (bStart) {
            CSettings settings = null;
            ErrReader errReader = null;
            OutReader outReader = null;
            Process process = null;
            try {
                if (logger_.isEnabledFor(LogLevel.FINER)) {
                    logger_.log(LogLevel.FINER, "Loading Runtime settings.");
                }
                settings = CSettings.getInstance();
                settings.load(args[0]);
                try {
                    iRebootMaxCounter_ = Integer.parseInt(CSettings.get(
                            "pr.rebootmaxcounter", "-1"));
                    lRebootSleepTime_ = Long.parseLong(CSettings.get(
                            "pr.sleepbeforereboottime", "5"));

                } catch (NumberFormatException e) {
                    logger_
                            .fatal(
                                    "Please check the properties 'rebootmaxcounter' and 'sleepbeforereboottime' ",
                                    e);
                    throw e;
                }
                if (cmd == null) {
                    cmd = buildCommand(null, args);
                }
                if (logger_.isEnabledFor(LogLevel.FINE)) {
                    logger_.log(LogLevel.FINE, "Starting Engine...");
                }
                disableLog4j();
                process = Runtime.getRuntime().exec(cmd);
                BufferedReader out = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                BufferedReader err = new BufferedReader(new InputStreamReader(
                        process.getErrorStream()));
                outReader = new OutReader(out);
                errReader = new ErrReader(err);
                outReader.setFlag("");
                errReader.setFlag("");
                outReader.start();
                errReader.start();

                if (outReader != null) {
                    outReader.waitForDone();
                }
                if (errReader != null) {
                    errReader.waitForDone();
                }
                outReader.shutdown();
                errReader.shutdown();
                out.close();
                err.close();
                process.waitFor();
                enableLog4j();
                Properties properties = getMessageFromPRE();
                String strAction = properties.getProperty("action");
                if (logger_.isEnabledFor(LogLevel.FINEST)) {
                    logger_.log(LogLevel.FINEST, "Action initiated #"
                            + strAction);
                }
                if (strAction.equals(REBOOT)) {
                    ArrayList<String> list = new ArrayList<String>();
                    if (Integer.parseInt(properties.getProperty("counter")) <= 0) {
                        iRebootCounter_ = 1;
                    } else {
                        iRebootCounter_++;
                    }
                    if (iRebootCounter_ > iRebootMaxCounter_) {
                        logger_
                                .log(
                                        LogLevel.NOTICE,
                                        iRebootCounter_
                                                + " Re-boot attempt(s) failed to start the engine");
                        bStart = false; // shutdown
                    }
                    if (bStart) {
                        list.add("-Dpre.reboot.attempt=" + iRebootCounter_);
                        sleep(lRebootSleepTime_,
                                "Re-booting the Engine after a wait of "
                                        + lRebootSleepTime_
                                        + " minute(s).");
                        cmd = buildCommand(list, args);
                    }
                } else if (strAction.equals(BOUNCE)) {
                    logger_
                            .log(LogLevel.NOTICE,
                                    "Process Request Engine has CRASHED. The PRE will be bounced. ");
                    logger_.log(LogLevel.NOTICE, "Bouncing the engine");
                    iRebootCounter_ = 0;
                    sleep(lRebootSleepTime_,
                            "Bouncing the Engine after a wait of "
                                    + lRebootSleepTime_
                                    + " minute(s).");
                } else if (strAction.equals(SHUTDOWN)) {
                    if (iRebootCounter_ > iRebootMaxCounter_) {
                        logger_
                                .log(
                                        LogLevel.NOTICE,
                                        iRebootCounter_
                                                + " Re-boot attempt(s) failed to start the engine");
                    }
                    if (logger_.isEnabledFor(LogLevel.FINE)) {
                        logger_.log(LogLevel.FINE,
                                "Shuting down the controller.");
                    }
                    bStart = false;
                }
            } catch (IOException e) {
                bStart = false;
            } catch (InterruptedException e) {
                bStart = false;
            } finally {
                if (settings != null) {
                    enableLog4j();
                    if (logger_.isEnabledFor(LogLevel.FINEST)) {
                        logger_.log(LogLevel.FINEST,
                                "Destroying Runtime settings.");
                    }
                    settings.destroy();
                }
                // if (process != null) {
                // process.destroy();
                // }
            }
        } // end of while
        if (logger_.isEnabledFor(LogLevel.FINE)) {
            logger_.log(LogLevel.FINE, "Shutdown complete.");
        }
        disableLog4j();
    }

    /**
     * Sleeps for the specified time.
     * 
     * @param time
     * @param message
     *            Message to be logged.
     */
    public void sleep(long time, String message) {
        try {
            logger_.log(LogLevel.NOTICE, message);
            TimeUnit.MINUTES.sleep(time);
//            Thread.sleep(time);
        } catch (InterruptedException e) {
            // do nothing
        }

    }

    /**
     * Command builder.
     * 
     * @param extraCommands
     *            if any
     * @param args
     *            Program arguments for PRE.
     * @return Command
     */
    public String[] buildCommand(ArrayList<String> extraCommands, String[] args) {
        long lCurrentTimestamp = System.currentTimeMillis();
        ArrayList<String> commandBuilder = new ArrayList<String>();
        if (logger_.isEnabledFor(LogLevel.FINER)) {
            logger_.log(LogLevel.FINER, "Building command.");
        }
        commandBuilder.add(System.getProperty("java.home") + FILE_SEPARATOR
                + "bin" + FILE_SEPARATOR + "java");
        if (CSettings.get("pr.javaruntimevmargs", null) != null) {
            // following code is to handle the space delimiter within double
            // quotes. Example value for this property
            // can be =-Xms128M -Xmx128M -D.pre.home="/u0201/apps/stg pre"
            StringCharacterIterator sci = new java.text.StringCharacterIterator(
                    CSettings.get("pr.javaruntimevmargs"));
            boolean bEscapeCharacter = false;
            boolean bQuoted = false;
            StringBuffer cmdBuffer = new StringBuffer();
            for (char c = sci.first(); c != CharacterIterator.DONE; c = sci
                    .next()) {
                switch (c) {
                case '\\':
                    if (bEscapeCharacter) {
                        cmdBuffer.append(c + "" + c);
                        bEscapeCharacter = false;
                    } else {
                        bEscapeCharacter = true;
                    }
                    break;
                case ' ':
                    if (!bQuoted) {
                        commandBuilder.add(cmdBuffer.toString());
                        cmdBuffer.delete(0, cmdBuffer.length());
                    } else {
                        cmdBuffer.append(c);
                    }
                    bEscapeCharacter = false;
                    break;
                case '"':
                    if (!bEscapeCharacter) {
                        if (!bQuoted) {
                            bQuoted = true;
                        } else {
                            bQuoted = false;
                        }
                    }
                    bEscapeCharacter = false;
                    break;
                default:
                    cmdBuffer.append(c);
                    break;
                } // end of switch case.
            } // end for string character iterator
            if (cmdBuffer.length() > 0) {
                commandBuilder.add(cmdBuffer.toString());
            }
        } // pr.javaruntimevmarg != null
        if (extraCommands != null) {
            if (logger_.isEnabledFor(LogLevel.FINEST)) {
                logger_.log(LogLevel.FINEST, "Adding extra commands if any.");
            }
            commandBuilder.addAll(extraCommands);
        }
        if (logger_.isEnabledFor(LogLevel.FINER)) {
            logger_.log(LogLevel.FINER, "Adding constants.");
        }
        commandBuilder.add("-classpath");
        String classpath="";
//        if (CSettings.get("pr.reportService","OFF").equalsIgnoreCase("ON")) {
//        	File directory = new File(CSettings.get("pr.birt.home") + "/lib");
//        	if (!directory.exists()) {
//        		throw new IllegalArgumentException("Directory is non-existant. Check property birt.home " + CSettings.getInstance().getSource("pr").getConfigFile().getAbsolutePath());
//        	}
//        	ArrayList<String> list = new ArrayList<String>();
//        	list.add(".jar");
//        	list.add(".zip");
//        	classpath = getExtraClasspath(directory, list);
//        	System.out.println(classpath);
//        }
        if (CSettings.get("pr.javaextraclasspath", null) != null) {
            commandBuilder.add(System.getProperty("java.class.path")
                    + File.pathSeparatorChar
                    + CSettings.get("pr.javaextraclasspath")
                    + File.pathSeparatorChar
                    + classpath
                    );
        } else {
            commandBuilder.add(System.getProperty("java.class.path") + File.pathSeparatorChar + classpath);
        }
        commandBuilder.add("stg.pr.engine.CProcessRequestEngine");
        commandBuilder.add(args[0]);
        commandBuilder.add(args[1]);
        String[] cmd = new String[commandBuilder.size()];
        commandBuilder.toArray(cmd);
        if (logger_.isEnabledFor(LogLevel.FINEST)) {
            logger_.log(LogLevel.FINEST, "Command " + commandBuilder);
            logger_
                    .log(
                            LogLevel.FINEST,
                            "Elapsed Time taken to build command "
                                    + (System.currentTimeMillis() - lCurrentTimestamp)
                                    + " ms.");
        }
        return cmd;
    } // end of buildCommand(..)

    /**
     * Reads messsage from PRE.
     * 
     * @return Properties.
     */
    private Properties getMessageFromPRE() {
        logger_.log(LogLevel.FINEST, "Reading message method start.");
        String strTmpDir = System.getProperty("java.io.tmpdir");
        String strFileSeparator = System.getProperty("file.separator");
        if (!strTmpDir.endsWith(strFileSeparator)) {
            strTmpDir = strTmpDir + strFileSeparator;
        }
        File file = new File(strTmpDir + "pre.ini");
        FileInputStream fis = null;
        Properties properties = new Properties();
        try {
            fis = new FileInputStream(file);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            logger_.log(LogLevel.FINEST, "FNFE. Unable to read the message.");
        } catch (IOException e) {
            logger_.log(LogLevel.FINEST, "IOE. Unable to read the message.");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // dummy
                }
            }
        }
        if (properties.size() == 0) {
            properties.setProperty("action", "Shutdown");
            properties.setProperty("attempt", "0");
        }
        if (!file.delete()) {
            logger_.log(LogLevel.FINEST, "Unable to delete file #" + file.getName());
        }
        logger_.log(LogLevel.FINEST, "Reading message method end.");
        return properties;
    } // end of getMessageFromPRE()

    /**
     * Helper base class for both STDOUT and STDERR Readers.
     */
    private class Reader extends Thread {

        private boolean _isRunning = true;

        private String _flag = null;

        public Reader(String name) {
            super(name);
        }

        public synchronized void setFlag(String flag) {
            _flag = flag;
            notifyAll();
        }

        public synchronized void waitForDone() throws InterruptedException {
            while (_flag != null) {
                wait();
            }
        }

        public synchronized void shutdown() {
            _isRunning = false;
            setFlag(null);
            interrupt();
        }

        public synchronized boolean isRunning() {
            return _isRunning;
        }

    } // End of class Reader

    /**
     * Helper class for reading the STDOUT reader on the Command. The thread is
     * tiggered to start reading when setCommand(command) is called, after
     * completion is calls setCommand(null) it notify that it is done..
     * 
     * The main caller thread will wait for the setCommand(null) trigger until
     * it continues and a new caller can run.
     */
    private class OutReader extends Reader {

        private BufferedReader reader;

        public OutReader(BufferedReader reader) {
            super("OutReader");
            this.reader = reader;
        }

        public void run() {
            try {
                while (isRunning()) {
                    try {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        } // end while
                    } catch (InterruptedIOException e) {
                        // do not log as it is bound to come on interrupt.
                    } catch (IOException e) {
                        enableLog4j();
                        if (logger_.isEnabledFor(LogLevel.FINEST)) {
                            logger_.warn("Exception occured while reading OUT",
                                    e);
                        }
                    }
                    setFlag(null);
                } // end while
            } catch (Throwable t) {
                enableLog4j();
                if (logger_.isEnabledFor(LogLevel.FINEST)) {
                    logger_.warn("Exception occured while reading OUT", t);
                }
            }
        }

    } // End of class OutReader

    /**
     * Helper class for reading the STDERR reader on the Command.
     * 
     * The thread is triggered to start reading when setCommand(command) is
     * called, after completion is calls setCommand(null) it notify that it is
     * done..
     * 
     * The main caller thread will wait for the setCommand(null) trigger until
     * it continues and a new caller can run.
     */
    private class ErrReader extends Reader {

        /**
         * Stores the REVISION number of the class from the configuration
         * management tool.
         */

        private BufferedReader reader;

        public ErrReader(BufferedReader reader) {
            super("ErrReader");
            this.reader = reader;
        }

        public void run() {
            try {
                while (isRunning()) {
                    try {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.err.println(line);
                        }
                    } catch (InterruptedIOException e) {
                        // do not log as it is bound to come on interrupt.
                    } catch (IOException e) {
                        enableLog4j();
                        if (logger_.isEnabledFor(LogLevel.FINEST)) {
                            logger_.warn("Exception occured while reading OUT",
                                    e);
                        }
                    }
                    setFlag(null);
                }
            } catch (Throwable t) {
                enableLog4j();
                if (logger_ != null) {
                    if (logger_.isEnabledFor(LogLevel.FINEST)) {
                        logger_.warn("Exception occured while reading ERR", t);
                    }
                }
            }
        }
    } // End of class ErrReader


    /**
     * Enables the log4j logger.
     */
    private synchronized void enableLog4j() {
        if (logger_ == null) {
            PropertyConfigurator.configure(strLog4jFile_);
            logger_ = Logger.getLogger("Controller");
        }
    }

    /**
     * Disables the log4j Logger.
     * 
     * @see LogManager#shutdown()
     */
    private synchronized void disableLog4j() {
        if (logger_ != null) {
            LogManager.shutdown();
            logger_ = null;
        }
    }
    
//    /**
//     * Creates extra classpath from the files ending with pattern list within the directory supplied.
//     *  
//     * @param directory to be used for finding files.
//     * @param patternList file extensions list
//     * @return String representation of the classpath
//     */
//    private String getExtraClasspath(File directory, final List<String> patternList) {
//    	File[] files = directory.listFiles(new FilenameFilter() {
//			
//			public boolean accept(File arg0, String arg1) {
//				for (String extenssion : patternList) {
//					if (arg1.toLowerCase().endsWith(extenssion)) {
//						return true;
//					}
//				}
//				return false;
//			}
//		});
//    	StringBuilder sb = new StringBuilder();
//    	for (File file : files) {
//    		sb.append(file.getPath());
//    		sb.append(File.pathSeparatorChar);
//    	}
//    	return sb.toString();
//    }

}
