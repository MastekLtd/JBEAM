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
package com.stgmastek.monitor.ws.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.http.gzip.GZIPOutInterceptor;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;

import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.monitor.ws.server.services.MonitorServices;
import com.stgmastek.monitor.ws.server.vo.MConfig;

/**
 * The utility class to cache and supply the client stubs
 * <p />
 * The caching mechanism is 
 * <p />
 *<TABLE border=1>
 *<TR>
 *	<TD> Installation Code </P>E.g. GMAC, ERIE
 *	</TD>
 *	<TD>
 *		<TABLE border=1>
 *		<TR>
 *			<TD>USER_SERVICES</TD>
 *			<TD>UserServices.class</TD>
 *		</TR>
 *		<TR>
 *			<TD>BATCH_SERVICES</TD>
 *			<TD>BatchServices.class</TD>
 *		</TR>
 *		<TR>
 *			<TD>STATUS_SERVICES</TD>
 *			<TD>StatusServices.class</TD>
 *		</TR>
 *		</TABLE>
 *	</TD>
 *</TR>
 *</TABLE>
 */
public final class ClientServices {
	
	private static final Logger logger = Logger.getLogger(ClientServices.class);
	
	/** Map to hold the core-comm client stubs */
	HashMap<String, HashMap<String, CoreCommServices>> serviceMap = null;

	/** Map to hold the monitor-comm client stubs */
	HashMap<String, HashMap<String, MonitorServices>> monitorServiceMap = null;

	/***/
	private List<MConfig> serviceConfigs = null;
	
	/**
	 * Default Constructor 
	 */
	ClientServices() {
		serviceMap = new HashMap<String, HashMap<String, CoreCommServices>>();
		monitorServiceMap = new HashMap<String, HashMap<String, MonitorServices>>();
	}

	/**
	 * Method to set list of registered services 
	 * 
	 * @param serviceConfigs
	 * 		  The list of registered services 
	 * 		  @see MConfig
	 */
	public void setMap(List<MConfig> serviceConfigs){
		this.serviceConfigs = serviceConfigs;
		setEntries(serviceConfigs);
		setMonitorEntries(serviceConfigs);
	}
	
	/**
	 * Delegated method from the client book class
	 * @see ClientBook#getClientClass(String) 
	 * 
	 * @param installationCode
	 * 		  The installation code 
	 * @return the cached method ready object
	 */
	CoreCommServices getClientClass(String installationCode) {

		HashMap<String, CoreCommServices> map = serviceMap.get(installationCode);
		
		//Do a lazy caching for core comm of those installation that were down 
		// when the first caching round was performed
		CoreCommServices obj = map.get("SERVICES");
		if(obj == null){
			//Get the client stub and cache it
			for(MConfig config : serviceConfigs){
				if(config.getCode2().equals(installationCode)){
					setEntries(config);
					break;
				}
			}
		}
			
		return map.get("SERVICES");

	}
	
	/**
	 * Delegated method from the client book class
	 * @see ClientBook#getClientClass(String) 
	 * 
	 * @param installationCode
	 * 		  The installation code 
	 * @return the cached method ready object
	 */
	MonitorServices getMonitorClientClass(String installationCode) {
		
//		HashMap<String, MonitorCommServices> map = monitorServiceMap.get(installationCode);

		HashMap<String, MonitorServices> map = monitorServiceMap.get(installationCode);
		
		if (map == null || map.size() == 0) {
			setMonitorEntries(serviceConfigs);
			map = monitorServiceMap.get(installationCode);
		}
	
		return map.get("SERVICES");
		
	}
	
	/**
	 * Utility method to process a single config entry 
	 * If the core for some core comm for an installation is down then it 
	 * would simply print it onto the default console and ignore it. 
	 * When the installation is up after some time then it would do the 
	 * lazy caching upon the first request
	 * In other words - 
	 * Say core communication installation for ABC was down when the first round 
	 * of caching was done, then the clients would be cached upon the first request
	 * 
	 * @param entries
	 * 		  The config entries
	 */
	private void setEntries(List<MConfig> entries){
		for(MConfig entry : entries){
			String code2 = entry.getCode2();
			if (serviceMap.containsKey(code2)) {
				HashMap<String, CoreCommServices> map = serviceMap.get(code2);
				String code3 = entry.getCode3();
				map.put(code3, stackClientStub(entry.getValue()));
			} else {
				HashMap<String, CoreCommServices> map = new HashMap<String, CoreCommServices>();
				String code3 = entry.getCode3();
				map.put(code3, stackClientStub(entry.getValue()));
				serviceMap.put(code2, map);
			}
		}
	}
	
	private void setMonitorEntries(List<MConfig> entries){
		for(MConfig entry : entries){
			String code2 = entry.getCode2();
			if (monitorServiceMap.containsKey(code2)) {
				HashMap<String, MonitorServices> map = monitorServiceMap.get(code2);
				String code3 = entry.getCode3();
				if(!map.containsKey(code3)){
					MonitorServices obj = stackMonitorClientStub(entry.getValue());
					if(obj != null)
						map.put(code3, obj);
				}
			} else {
				HashMap<String, MonitorServices> map = new HashMap<String, MonitorServices>();
				String code3 = entry.getCode3();
				MonitorServices obj = stackMonitorClientStub(entry.getValue());
				if(obj != null)
					map.put(code3, obj);
				if(map.size() > 0)
					monitorServiceMap.put(code2, map);
			}
		}
	}

	private void setEntries(MConfig entry){
		ArrayList<MConfig> newClient = new ArrayList<MConfig>();
		newClient.add(entry);
		setEntries(newClient);
	}
	
	
	/**
	 * Helper method that would actually load the URL and return the client stub 
	 * 
	 * @param value
	 * 		  The IP and the PORT for which the client have to be cached
	 * @return the client stub
	 */
	private CoreCommServices stackClientStub(String value){

		String urlString = "https://" + value + "/"
				+ CoreCommServices.class.getSimpleName();
		try {
			JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
			proxyFactory.setServiceClass(CoreCommServices.class);
			proxyFactory.setAddress(urlString);
			
			CoreCommServices client = (CoreCommServices) proxyFactory.create();
			
			configureSSLOnTheClient(client);
			return client;
		} catch (Exception e) {
			System.err.println(urlString
					+ " --> Can not initialize the WSDL from URL ");
		}
		return null;
	}
	
	/**
	 * Configure SSL on the client.
	 * 
	 * @param c object
	 */
	private void configureSSLOnTheClient(Object c) {
        org.apache.cxf.endpoint.Client client = ClientProxy.getClient(c);
        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setAcceptEncoding("gzip");
        httpConduit.setClient(policy);
        client.getInInterceptors().add(new GZIPInInterceptor());
        client.getInFaultInterceptors().add(new GZIPInInterceptor());
        client.getOutInterceptors().add(new GZIPOutInterceptor());
        client.getOutFaultInterceptors().add(new GZIPOutInterceptor());

	    try {
	        TLSClientParameters tlsParams = new TLSClientParameters();
	        tlsParams.setDisableCNCheck(true);
	        KeyStore keyStore = KeyStore.getInstance("JKS");
	        String trustpass = "password";
	
	        keyStore.load(IOUtils.getResourceAsInputStream(ClientServices.class, "resources/sslcert/truststore.jks"), trustpass.toCharArray());
	        TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); 
	        trustFactory.init(keyStore);
	        TrustManager[] tm = trustFactory.getTrustManagers();
	        tlsParams.setTrustManagers(tm);
	
	        keyStore.load(IOUtils.getResourceAsInputStream(ClientServices.class, "resources/sslcert/wibble.jks"), trustpass.toCharArray());
	        KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	        keyFactory.init(keyStore, trustpass.toCharArray());
	        KeyManager[] km = keyFactory.getKeyManagers();
	        tlsParams.setKeyManagers(km);
	        FiltersType filter = new FiltersType();
	        filter.getInclude().add(".*_EXPORT_.*");
	        filter.getInclude().add(".*_EXPORT1024_.*");
	        filter.getInclude().add(".*_WITH_DES_.*");
			filter.getInclude().add(".*_WITH_NULL_.*");
			filter.getExclude().add(".*_DH_anon_.*");
			tlsParams.setCipherSuitesFilter(filter);
	
			httpConduit.setTlsClientParameters(tlsParams);
	    } catch (KeyStoreException e) {
	    	logger.error("Security configuration failed with the following: ", e);
	    } catch (NoSuchAlgorithmException e) {
	    	logger.error("Security configuration failed with the following: ", e);
	    } catch (FileNotFoundException e) {
	    	logger.error("Security configuration failed with the following: ", e);
	    } catch (UnrecoverableKeyException e) {
	    	logger.error("Security configuration failed with the following: ", e);
		} catch (CertificateException e) {
			logger.error("Security configuration failed with the following: ", e);
		} catch (IOException e) {
			logger.error("Security configuration failed with the following: ", e);
		}
	}
	
	/**
	 * Helper method that would actually load the URL and return the client stub 
	 * 
	 * @param value
	 * 		  The IP and the PORT for which the client have to be cached
	 * @return the client stub
	 */
	private MonitorServices stackMonitorClientStub(String value){
		
		String urlString = "https://" + value + "/"
				+ MonitorServices.class.getSimpleName();
		try {
			JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
			proxyFactory.setServiceClass(MonitorServices.class);
			proxyFactory.setAddress(urlString);

			MonitorServices client = (MonitorServices) proxyFactory
					.create();

			configureSSLOnTheClient(client);
			return client;
		} catch (Exception e) {
			System.err.println(urlString
					+ " --> Can not initialize the WSDL from URL ");
		}
		return null;
	}
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/ClientServices.java                                                                  $
 * 
 * 10    6/24/10 10:45a Mandar.vaidya
 * Removed "?wsdl" from urlString
 * 
 * 9     6/23/10 5:22p Mandar.vaidya
 * Modified comments.
 * 
 * 8     6/23/10 4:42p Mandar.vaidya
 * prefixed  resources folder to the sslcert
 * 
 * 7     6/23/10 3:47p Kedarr
 * Changed the method from static to non-static and made changes to return the same client on which the SSL is configured.
 * 
 * 6     6/22/10 5:31p Mandar.vaidya
 * SSL Implementation
 * 
 * 5     1/06/10 11:33a Grahesh
 * Corrected the logic for lazy caching of those client installation that were not up and running when the monitor was turned on.
 * 
 * 4     12/30/09 12:49p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/