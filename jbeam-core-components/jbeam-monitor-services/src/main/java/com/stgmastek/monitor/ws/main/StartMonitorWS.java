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
package com.stgmastek.monitor.ws.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.stg.logger.LogLevel;
import com.stgmastek.monitor.services.client.MonitorServices;
import com.stgmastek.monitor.ws.server.base.ServiceBase;
import com.stgmastek.monitor.ws.server.util.WSServerAddress;
import com.stgmastek.monitor.ws.server.util.WSServerInfo;
import com.stgmastek.monitor.ws.server.util.WSServerManager;
import com.stgmastek.monitor.ws.util.ClientBook;
import com.stgmastek.monitor.ws.util.Configurations;
import com.stgmastek.monitor.ws.util.Constants;
import com.stgmastek.monitor.ws.util.DeploymentMap;
import com.stgmastek.monitor.ws.util.Constants.COMMAND;

/**
 * Main class that would start all the services for the UI  
 * 
 * @author grahesh.shanbhag
 *
 */
public class StartMonitorWS { 
	
	private static final Logger logger = Logger.getLogger("Monitor-Services");
	static List<DeploymentMap> servicesList;
	private static String[] strBundleDetailsArray_;
	
	/**
	 * Main method that would have to be called to up the services needed by the UI  
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
					logger.log(LogLevel.NOTICE, "Server's published address is " + info.getAddress().getAddressURL());
					logger.log(LogLevel.NOTICE, "Started services : " + serviceClass.getSimpleName());					
				}
				WSServerManager.getInstance().createSecureServer(
						serviceClass, serviceImpl.newInstance(), info);
				if (logger.isEnabledFor(LogLevel.NOTICE)) {
					logger.log(LogLevel.NOTICE, "Secure Server's published address is " + info.getAddress().getSecureAddressURL());
					logger.log(LogLevel.NOTICE, "Started secure services : " + serviceClass.getSimpleName());					
				}
			} catch (Exception e) {
				throw e;
			}
		}
		
		//Cache the clients  
		ClientBook.getBook();
	}
	
	private static void stop() {
		String monitorServiceURL = Configurations.getConfigurations().getConfigurations("MONITOR_WS","MONITOR_UI_WS","SERVICES");
		if(monitorServiceURL != null) {
			MonitorServices monitorServices = getClientStub(monitorServiceURL);
			try {
				if (logger.isEnabledFor(LogLevel.NOTICE)) {
					logger.log(LogLevel.NOTICE, "Stopping MonitorServices");
				}
				monitorServices.stopMonitorServices();
			} catch (Throwable e) {
				if (e.getCause() instanceof java.net.ConnectException) {
					if (logger.isEnabledFor(LogLevel.NOTICE)) {
						logger.log(LogLevel.NOTICE, "Monitor services stopped successfully");
					}
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
		Configurations config = 
			Configurations.getConfigurations().loadConfigurations();
		HashMap<String, String> versionHM = new HashMap<String, String>();
		String[] versionArray = strBundleDetailsArray_[1].split(".");
		if (versionArray.length > 1) {
			versionHM.put("MAJOR", versionArray[0]);
			versionHM.put("MINOR", versionArray[1]);
		} else {
			versionHM.put("MAJOR", "0"); //dummy
			versionHM.put("MINOR", "0"); //dummy
		}
		config.getConfigurations("MONITOR_WS").put("UI_VERSION", versionHM);
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
        String localFile = StartMonitorWS.class.getProtectionDomain().getCodeSource().getLocation().toString();
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
	
	private static void printUsage() {
		System.out.println("Usage java -classpath <classpath> StartMonitorCommunication <-option> log4j.properties");
		System.out.println("\twhere option can be either of the following");
		System.out.println("\t\t-shutdown \t Shuts the service");
		System.out.println("\t\t-startup \t Starts the service");
	}
	
	/**
	 * Helper method that would actually load the URL and return the client stub 
	 * 
	 * @param value
	 * 		  The IP and the PORT for which the client have to be cached
	 * @return the client stub
	 */
	public static MonitorServices getClientStub(String value){

		String urlString = "http://" + value + "/"
				+ MonitorServices.class.getSimpleName();
		try {
			JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
			proxyFactory.setServiceClass(MonitorServices.class);
			proxyFactory.setAddress(urlString);
			
			MonitorServices client = (MonitorServices) proxyFactory.create();
			return client;
		} catch (Exception e) {
			System.err.println(urlString
					+ " Server is already down");
		}
		return null;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/main/StartMonitorWS.java                                                                $
 * 
 * 5     7/10/10 3:31p Mandar.vaidya
 * Removed mailer functionality.
 * 
 * 4     7/09/10 9:26a Kedarr
 * Changes  made for print executor object.
 * 
 * 3     6/23/10 10:30a Lakshmanp
 * removed the connection prameter to loadConfigurations
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/