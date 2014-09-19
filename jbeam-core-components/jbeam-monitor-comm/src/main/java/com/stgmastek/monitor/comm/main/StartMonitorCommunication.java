/*
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package com.stgmastek.monitor.comm.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.stg.logger.LogLevel;
import com.stgmastek.monitor.comm.client.MonitorCommServices;
import com.stgmastek.monitor.comm.server.base.ServiceBase;
import com.stgmastek.monitor.comm.server.util.WSServerAddress;
import com.stgmastek.monitor.comm.server.util.WSServerInfo;
import com.stgmastek.monitor.comm.server.util.WSServerManager;
import com.stgmastek.monitor.comm.util.BasePoller;
import com.stgmastek.monitor.comm.util.ClientBook;
import com.stgmastek.monitor.comm.util.CommConstants;
import com.stgmastek.monitor.comm.util.Configurations;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.Constants;
import com.stgmastek.monitor.comm.util.DeploymentMap;
import com.stgmastek.monitor.comm.util.OutBoundQueuePoller;
import com.stgmastek.monitor.comm.util.Constants.COMMAND;

/**
 * Main class that would start all the services for the monitor communication system  
 * 
 * @author grahesh.shanbhag
 *
 */
public class StartMonitorCommunication { 

	private static final Logger logger = Logger
	.getLogger("Monitor-Comm");


	static List<DeploymentMap> servicesList;
	private static String[] strBundleDetailsArray_;
	private final static List<BasePoller> pollers = new ArrayList<BasePoller>();
	
	/**
	 * Main method that would have to be called to up the CORE Communication System  
	 * 
	 * @param args
	 * 		  Any runtime arguments, none in this case 
	 */
	public static void main(String[] args) throws Throwable {	
	
		if (args.length < 2) {
			printUsage();
			throw new IllegalArgumentException();
		}
		COMMAND command;
		try {
			command = Constants.COMMAND.resolve(args[0]);
		} catch (Throwable t) {
			printUsage();
			throw t;
		}
		
		String log4jProp = args[1];
		if ("".equals(log4jProp)) {
			printUsage();
			throw new IllegalArgumentException();
		}
		PropertyConfigurator.configure(args[1]);
		
		//Boot the configurations 		
		boot();

		switch (command) {
		case startup:
			start();
			break;
		case shutdown:
			stop();
			break;
		default:
			break;
		}
	}
	
	private static void start() throws Exception {
		//Loads or UP all the services as configured in the MONITOR_CONFIG table 
		for(int i=0; i<servicesList.size(); i++){
			DeploymentMap map = servicesList.get(i);
			
			Class<? extends ServiceBase> serviceClass = map.getServiceInterface(); 
			Class<?> serviceImpl = map.getServiceImpl();
			
			WSServerAddress address = new WSServerAddress();			
			address.setHost(map.getAddress());
			address.setPort(Integer.valueOf(map.getPort()));
			address.setDomain(serviceClass.getSimpleName());			
			
			WSServerInfo info = new WSServerInfo(serviceClass.getSimpleName(), address);
			try {
				WSServerManager.getInstance().createServer(
								serviceClass, serviceImpl.newInstance(), info);
				if (logger.isEnabledFor(LogLevel.NOTICE)) {
					logger.log(LogLevel.NOTICE, "Setting the server's publish address to be " + info.getAddress().getAddressURL());
					logger.log(LogLevel.NOTICE, "Starting services : " + serviceClass.getSimpleName());
				}
			} catch (Exception e) {
				throw e;
			}
		}
		
		//Cache the clients  
		ClientBook.getBook();
		
		//Start the poller
		//For monitor it would be only out bound queue
//		new Thread(new OutBoundQueuePoller()).start();
		pollers.add(new OutBoundQueuePoller());
		for (BasePoller poller : pollers) {
			new Thread(poller).start();
		}
	}
	
	private static void stop() {
		MonitorCommServices monitorCommServices = ClientBook.getBook().getMonitorClientClass("MONITOR_WS");
		try {
			if (logger.isEnabledFor(LogLevel.NOTICE)) {
				logger.log(LogLevel.NOTICE, "Stopping MonitorCommServices");
			}
			monitorCommServices.stopMonitorComm();
		} catch (Throwable e) {
			if (e.getCause() instanceof java.net.ConnectException) {
				
				if (logger.isEnabledFor(LogLevel.NOTICE)) {
					logger.log(LogLevel.NOTICE, "Stop message transmitted successfully..");
				}
			}
		}
	}
	
	/**
	 * Helper method to populate the constants and fetch the configurations as 
	 * set in the MONITOR_CONFIG table  
	 *  
	 * @throws Exception
	 * 		   Any exception occurred during loading 
	 * 		   of the configurations 
	 */
	static void boot() throws Exception {
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");
		System.setProperty("org.mortbay.log.class", "com.stgmastek.logger.JettyLog");
		getBundleDetails();
		printVersion();
		Configurations config;
		Connection conn = null;
		try {
			conn = ConnectionManager.getInstance().getDefaultConnection();
			config = Configurations.getConfigurations().loadConfigurations(conn);			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException se) {
					//dummy.
				}
			}
		}
		HashMap<String, String> versionHM = new HashMap<String, String>();
		String[] versionArray = strBundleDetailsArray_[1].split(".");
		if (versionArray.length > 1) {
			versionHM.put("MAJOR", versionArray[0]);
			versionHM.put("MINOR", versionArray[1]);
		} else {
			versionHM.put("MAJOR", "0");
			versionHM.put("MINOR", "0");
		}
		config.getConfigurations("MONITOR_WS").put("VERSION", versionHM);
		
		CommConstants.POLLING_WAIT_PERIOD = 
				Integer.valueOf(config.getConfigurations("MONITOR_WS", "OUTBOUND_Q_POLLER", "WAIT_PERIOD"));		
		servicesList = config.getHostedServices();
		
	}
	
	static void printVersion() {
		if (strBundleDetailsArray_[0].equals("Unknown")) {
			logger.error("Illegal Usage or Development Usage. Please make use of the distributed files.");
		} else {
	        StringBuilder sb = new StringBuilder();
			sb.append("Product Name: \"");
	        sb.append(strBundleDetailsArray_[0]);
	        sb.append("\" Version: \"");
	        sb.append(strBundleDetailsArray_[1]);
	        sb.append("\" Packaged On \"");
	        sb.append(strBundleDetailsArray_[2]);
	        sb.append("\"");
	        if (logger.isEnabledFor(LogLevel.NOTICE)) {
	        	logger.log(LogLevel.NOTICE, sb.toString());
	        }
		}
	}
	
	static void getBundleDetails() {
        strBundleDetailsArray_ = new String[] {"Unknown", "Unknown", "Unknown", Integer.MIN_VALUE + ""};
        String localFile = StartMonitorCommunication.class.getProtectionDomain().getCodeSource().getLocation().toString();
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
            strBundleDetailsArray_[3] = (String) attributes.getValue("Build-Number");
        } catch (MalformedURLException e) {
            //do nothing
        } catch (FileNotFoundException fnfe) {
            //do nothing
        } catch (IOException ioe) {
            //do nothing
        }
	}

	public static List<BasePoller> getPollers() {
		return pollers;
	}
	
	private static void printUsage() {
		System.out.println("Usage java -classpath <classpath> StartMonitorCommunication <-option> log4j.properties");
		System.out.println("\twhere option can be either of the following");
		System.out.println("\t\t-shutdown \t Shuts the service");
		System.out.println("\t\t-startup \t Starts the service");
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/main/StartMonitorCommunication.java                                                       $
 * 
 * 3     6/18/10 12:49p Lakshmanp
 * connection leak taken care
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/