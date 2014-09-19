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
package com.stgmastek.monitor.ws.server.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.service.invoker.BeanInvoker;
import org.apache.cxf.transport.http.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.http.gzip.GZIPOutInterceptor;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.apache.log4j.Logger;

import com.stgmastek.monitor.services.client.MonitorServices;
import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.main.StartMonitorWS;
import com.stgmastek.monitor.ws.server.base.ServiceBase;
import com.stgmastek.monitor.ws.util.Configurations;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.IOUtils;

/**
 * The manager (or the main) class to manage all the servers in the Monitor WS system. 
 * It implements the singleton pattern.
 *   
 * @author grahesh.shanbhag
 *
 */
public final class WSServerManager {
	
	private static final Logger logger = Logger.getLogger(WSServerManager.class);

	/** The singleton instance of the manager */
	private static WSServerManager instance = new WSServerManager();
	
	/** The singleton instance of the server factory. There would never be a need to multiple instances of the factory */
	private static WSFactoryImpl factoryImpl = instance.new WSFactoryImpl(); 
	
	/** The singleton instance of the service book. Serves as a dictionary for hold all the servers created*/
	private static ServiceBook serviceBook = new ServiceBook();
	
	/**
	 * Private constructor to avoid instantiation from the outside
	 */
	private WSServerManager(){}
	
	/**
	 * Public static instance to get the handle to the manager 
	 * 
	 * @return the manager instance. 
	 */
	public static WSServerManager getInstance(){		
		return instance; 
	}
	
	/**
	 * Creates the servers with the information supplied
	 * 
	 * @param serviceClass
	 * 		  The skeleton or the interface class. Note the skeleton or the interface class must 
	 * 		  extend the base service class @see {@link ServiceBase}
	 * @param impl
	 * 		  An instance of the implementation class 
	 * @param info
	 *		  An instance of WSServerInfo that contains the information of the server to be created			   	 		  
	 * @return true if the service is created successfully, false otherwise. 
	 */
	public synchronized boolean createServer(Class<? extends ServiceBase> serviceClass, Object impl, WSServerInfo info){
		if(!serviceBook.isServerRegistered(info))		
			factoryImpl.createService(serviceClass, impl, info);
		else
			startService(info);
		
		return true;
	}
	
	public synchronized boolean createSecureServer(Class<? extends ServiceBase> serviceClass, Object impl, WSServerInfo info){
		if(!serviceBook.isServerRegistered(info))		
			factoryImpl.createSecureService(serviceClass, impl, info);
		else
			startService(info);
		
		return true;
	} 
	
	/**
	 * Starts a server, if the server is created and shutdown or not running
	 *  
	 * @param info
	 * 		  An instance of the WSServerInfo that holds the server information 
	 */
	public synchronized void startService(WSServerInfo info) {
		WSServer server = serviceBook.getServer(info);		
		if(!server.isRunning())
			serviceBook.getServer(info).start();
	}
	
	/**
	 * Stops a server, if the server is created and is running
	 *  
	 * @param info
	 * 		  An instance of the WSServerInfo that holds the server information 
	 */
	public synchronized void stopService(WSServerInfo info){
		WSServer server = serviceBook.getServer(info);
		if(server.isRunning())
			server.stop();
	}
	
	/**
	 * Stop registered server, if server has been created and is running
	 */
	public void stopMonitorServices() {
		for (WSServerInfo info : serviceBook.getServerInfo()) {
			factoryImpl.destroyService(info);
		}
	}
	
	/**
	 * Inner class to implement the server factory 
	 * 
	 * @author grahesh.shanbhag
	 *
	 */
	class WSFactoryImpl implements WSServerFactory{
		
		/**
		 * Creates the server 
		 * 
		 * @param serviceClass
		 * 		  The skeleton or the interface class. Note the skeleton or the interface class must 
		 * 		  extend the base service class @see {@link ServiceBase}
		 * @param impl
		 * 		  An instance of the implementation class 
		 * @param info
		 *		  An instance of WSServerInfo that contains the information of the server to be created			   	 		  
		 * @return the newly created WSServer instance   
		 */
		public WSServer createService(Class<? extends ServiceBase> serviceClass, Object impl,
											WSServerInfo info){			
			
			WSServer server = null;
			
			//Check whether the server exists
			//If exists then start the server and exit
			if((server = serviceBook.getServer(info)) != null){
				if(!server.isRunning())
					server.start();
				
				return server;
			}
			
			JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();		
			svrFactory.setServiceClass(serviceClass);
			svrFactory.setAddress(info.getAddress().getAddressURL());
			svrFactory.setServiceBean(impl);
			svrFactory.getInInterceptors().add(new LoggingInInterceptor());
			JettyHTTPServerEngineFactory jfactory = new JettyHTTPServerEngineFactory();
			try {
				jfactory.createJettyHTTPServerEngine(info.getAddress().getPort(), "http");
			} catch (GeneralSecurityException e) {
				throw new Error(e.getMessage(), e);
			} catch (IOException e) {
				throw new Error(e.getMessage(), e);
			}
			Server cxfServer = svrFactory.create();
			server = new WSServer(cxfServer, info, jfactory);
			serviceBook.register(info, server);
			return server;
		}
		
		/**
		 * Creates the server 
		 * 
		 * @param serviceClass
		 * 		  The skeleton or the interface class. Note the skeleton or the interface class must 
		 * 		  extend the base service class @see {@link ServiceBase}
		 * @param impl
		 * 		  An instance of the implementation class 
		 * @param info
		 *		  An instance of WSServerInfo that contains the information of the server to be created			   	 		  
		 * @return the newly created WSServer instance   
		 */
		public WSServer createSecureService(Class<? extends ServiceBase> serviceClass, Object impl,
											WSServerInfo info){			
			
			WSServer server = null;
			
			//Check whether the server exists
			//If exists then start the server and exit
			if((server = serviceBook.getServer(info)) != null){
				if(!server.isRunning())
					server.start();
				
				return server;
			}
			
			JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();		
			svrFactory.setServiceClass(serviceClass);
			svrFactory.setAddress(info.getAddress().getSecureAddressURL());
			svrFactory.getServiceFactory().setInvoker(new BeanInvoker(impl));
			svrFactory.getInInterceptors().add(new LoggingInInterceptor());
			JettyHTTPServerEngineFactory factory = configureSSLOnTheServer(info.getAddress().getPort());
			Server cxfServer = svrFactory.create();
			cxfServer.getEndpoint().getInInterceptors().add(new GZIPInInterceptor());
			cxfServer.getEndpoint().getInFaultInterceptors().add(new GZIPInInterceptor());
			cxfServer.getEndpoint().getOutInterceptors().add(new GZIPOutInterceptor());
			cxfServer.getEndpoint().getOutFaultInterceptors().add(new GZIPOutInterceptor());
			server = new WSServer(cxfServer, info, factory);
			serviceBook.register(info, server);
			return server;
		}
		
		private JettyHTTPServerEngineFactory configureSSLOnTheServer(int port) {   
	        try {   
	            TLSServerParameters tlsParams = new TLSServerParameters();   
	            KeyStore keyStore = KeyStore.getInstance("JKS");   
	            String password = "password";   
	            keyStore.load(IOUtils.getResourceAsInputStream(this.getClass(), "resources/sslcert/cherry.jks"), password.toCharArray());   
	            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());   
	            keyFactory.init(keyStore, password.toCharArray());   
	            KeyManager[] km = keyFactory.getKeyManagers();   
	            tlsParams.setKeyManagers(km);
	            
	            keyStore.load(IOUtils.getResourceAsInputStream(this.getClass(), "resources/sslcert/truststore.jks"), password.toCharArray());   
	            TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());   
	            trustFactory.init(keyStore);   
	            TrustManager[] tm = trustFactory.getTrustManagers();   
	            tlsParams.setTrustManagers(tm);   
	            FiltersType filter = new FiltersType();   
	            filter.getInclude().add(".*_EXPORT_.*");   
	            filter.getInclude().add(".*_EXPORT1024_.*");   
	            filter.getInclude().add(".*_WITH_DES_.*");   
	            filter.getInclude().add(".*_WITH_NULL_.*");   
	            filter.getExclude().add(".*_DH_anon_.*");   
	            tlsParams.setCipherSuitesFilter(filter);   
	            ClientAuthentication ca = new ClientAuthentication();   
	            ca.setRequired(true);   
	            ca.setWant(true);   
	            tlsParams.setClientAuthentication(ca);   
	            JettyHTTPServerEngineFactory factory = new JettyHTTPServerEngineFactory();
	            factory.setTLSServerParametersForPort(port, tlsParams);
	            return factory;
	        } catch (KeyStoreException e) {   
	        	logger.error("Security configuration failed with the following: ", e);   
	        } catch (NoSuchAlgorithmException e) {   
	        	logger.error("Security configuration failed with the following: ", e);   
	        } catch (FileNotFoundException e) {   
	        	logger.error("Security configuration failed with the following: ", e);   
	        } catch (UnrecoverableKeyException e) {   
	        	logger.error("Security configuration failed with the following: ", e);   
	        } catch (GeneralSecurityException e) {   
	        	logger.error("Security configuration failed with the following: ", e);   
	        } catch (IOException e) {   
	        	logger.error("Security configuration failed with the following: ", e);   
	        }   
	        return null;
	    } 
		/**
		 * Destroys the server
		 *  
		 * @param info
		 *		  An instance of WSServerInfo that contains the information of the server to be created			   	 		  
		 * @return the newly created WSServer instance   
		 */		
		public boolean destroyService(WSServerInfo info){
			
			WSServer server = null;

			// Check whether the server exists
			// If exists then stop the server and assign it to null for garbage
			// collection
			if ((server = serviceBook.getServer(info)) != null) {
				if (server.isRunning()) {
					String monitorServiceURL = Configurations
							.getConfigurations().getConfigurations(
									"MONITOR_WS", "MONITOR_UI_WS", "SERVICES");
					if (monitorServiceURL != null) {
						MonitorServices monitorServices = StartMonitorWS
								.getClientStub(monitorServiceURL);
						org.apache.cxf.endpoint.Client monitorClient = ClientProxy
								.getClient(monitorServices);
						monitorClient.destroy();
						System.out.println("Destroyed monitor-services client");

						server.stop();
						try {
							ConnectionManager.getInstance().destroy();
						} catch (CommDatabaseException e) {
						}
						try {
							TimeUnit.MINUTES.sleep(1);
						} catch (InterruptedException e) {
						}
						System.out.println("Stopped "
								+ info.getAddress().getDomain());
					}
				}

				serviceBook.deregister(info);
				server = null;
				// Runtime.getRuntime().gc();
			}
			return true;
		}

	}
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/util/WSServerManager.java                                                        $
 * 
 * 3     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/