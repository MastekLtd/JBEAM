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
package com.stgmastek.core.comm.server.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

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

import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.core.comm.exception.MonitorServiceDownException;
import com.stgmastek.core.comm.main.StartCoreCommunication;
import com.stgmastek.core.comm.server.base.ServiceBase;
import com.stgmastek.core.comm.util.BasePoller;
import com.stgmastek.core.comm.util.ClientBook;
import com.stgmastek.core.comm.util.CommConstants;
import com.stgmastek.core.comm.util.IOUtils;
import com.stgmastek.monitor.comm.client.MonitorCommServices;

/**
 * The manager (or the main) class to manage all the servers in the core communication system. 
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
	 * Stop all registered servers, if servers has been created and are running
	 */
	public synchronized void stopAllServices() {
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
			svrFactory.getServiceFactory().setInvoker(new BeanInvoker(impl));
			svrFactory.getInInterceptors().add(new LoggingInInterceptor());
			JettyHTTPServerEngineFactory factory = configureSSLOnTheServer(info.getAddress().getPort());
			Server cxfServer = svrFactory.create();
			cxfServer.getEndpoint().getInFaultInterceptors().add(new GZIPInInterceptor());
			cxfServer.getEndpoint().getInInterceptors().add(new GZIPInInterceptor());
			cxfServer.getEndpoint().getOutInterceptors().add(new GZIPOutInterceptor());
			cxfServer.getEndpoint().getOutFaultInterceptors().add(new GZIPOutInterceptor());
			server = new WSServer(cxfServer, info, factory);
			serviceBook.register(info, server);
			return server;
		}
		
		/**
		 * Destroys the server
		 *  
		 * @param info
		 *		  An instance of WSServerInfo that contains the information of the server to be created			   	 		  
		 *    
		 */		
		public void destroyService(WSServerInfo info){
			if (logger.isInfoEnabled()) {
				logger.info("Waiting for pollers to finish their current pending jobs.");
			}
			for (BasePoller poller : StartCoreCommunication.getPollers()) {
				poller.stopPoller();
			}
			for (BasePoller poller : StartCoreCommunication.getPollers()) {
				while (!poller.getExecutor().isTerminated()) {
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("All pollers have finished their jobs. Stopping all services...");
			}
			WSServer server = null;
			
			//Check whether the server exists
			//If exists then stop the server and assign it to null for garbage collection
			if((server = serviceBook.getServer(info)) != null){
				if(server.isRunning()) {
					try {
						MonitorCommServices monitorCommServices = ClientBook.getBook().getClientClass("MONITOR_WS");
						org.apache.cxf.endpoint.Client monitorClient = ClientProxy.getClient(monitorCommServices);
						monitorClient.destroy();
						if (logger.isInfoEnabled()) {
							logger.info("Destroyed monitor client");
						}
						
						CoreCommServices coreCommServices = ClientBook.getCoreBook().getCoreClientClass(CommConstants.INSTALLATION_CODE);
						org.apache.cxf.endpoint.Client coreClient = ClientProxy.getClient(coreCommServices);
						coreClient.destroy();
						if (logger.isInfoEnabled()) {
							logger.info("Destroyed core client");
						}
						
					} catch (MonitorServiceDownException e) {
					}				
					server.stop();
					serviceBook.deregister(info);
					if (logger.isInfoEnabled()) {
						logger.info("Stopped the " + info.getAddress().getDomain() + "successfully.");
					}
				}
				server = null;
			}			
		}
	}
	
	private JettyHTTPServerEngineFactory configureSSLOnTheServer(int port) {   
        try {   
            TLSServerParameters tlsParams = new TLSServerParameters();   
            KeyStore keyStore = KeyStore.getInstance("JKS");   
            String password = "password";   
            keyStore.load(IOUtils.getResourceAsInputStream(WSServerManager.class, "resources/sslcert/cherry.jks"), password.toCharArray());   
            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());   
            keyFactory.init(keyStore, password.toCharArray());   
            KeyManager[] km = keyFactory.getKeyManagers();   
            tlsParams.setKeyManagers(km);   
  
            keyStore.load(IOUtils.getResourceAsInputStream(WSServerManager.class, "resources/sslcert/truststore.jks"), password.toCharArray());   
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
//            factory.setTLSServerParametersForPort(port, null);
            factory.setTLSServerParametersForPort(port, tlsParams);
            return factory;
        } catch (KeyStoreException kse) {
        	logger.error("Security configuration failed with the following: " + kse.getMessage(), kse);
        } catch (NoSuchAlgorithmException nsa) {   
        	logger.error("Security configuration failed with the following: " + nsa.getMessage(), nsa);
        } catch (FileNotFoundException fnfe) {   
        	logger.error("Security configuration failed with the following: " + fnfe.getMessage(), fnfe);
        } catch (UnrecoverableKeyException uke) {   
        	logger.error("Security configuration failed with the following: " + uke.getMessage(), uke);
        } catch (GeneralSecurityException gse) {   
        	logger.error("Security configuration failed with the following: " + gse.getMessage(), gse);
        } catch (IOException ioe) {   
        	logger.error("Security configuration failed with the following: " + ioe.getMessage(), ioe);
        }
        return null;
    } 
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/util/WSServerManager.java                                                                $
 * 
 * 5     6/23/10 4:42p Mandar.vaidya
 * prefixed  resources folder to the sslcert
 * 
 * 4     6/23/10 2:39p Kedarr
 * Changed the signature of configure ssl on the server method.
 * 
 * 3     6/22/10 5:31p Mandar.vaidya
 * SSL Implementation
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/