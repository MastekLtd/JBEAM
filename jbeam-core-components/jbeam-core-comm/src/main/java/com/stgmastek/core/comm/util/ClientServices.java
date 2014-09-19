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
package com.stgmastek.core.comm.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
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

import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.monitor.comm.client.MonitorCommServices;

/**
 * The utility class to cache and supply the client stubs
 */
public final class ClientServices {
	
	/** Map to hold the client stubs */
	HashMap<String, HashMap<String, MonitorCommServices>> serviceMap = null;
	HashMap<String, HashMap<String, CoreCommServices>> coreServiceMap = null;

	/** The configurations as sey */
	private List<CConfig> serviceConfigs = null;
	
	/**
	 * Default Constructor 
	 */
	ClientServices() {
		serviceMap = new HashMap<String, HashMap<String, MonitorCommServices>>();
		coreServiceMap = new HashMap<String, HashMap<String, CoreCommServices>>();
	}

	/**
	 * Method to set list of registered services 
	 * 
	 * @param serviceConfigs
	 * 		  The list of registered services 
	 */
	public void setMap(List<CConfig> serviceConfigs){
		this.serviceConfigs = serviceConfigs;
		setEntries(serviceConfigs);
		setCoreEntries(serviceConfigs);
	}

	
	/**
	 * Delegated method to return the cached client stubs 
	 * 
	 * @param installationCode
	 * 		  The installation code
	 * @return the cached client stub
	 */
	MonitorCommServices getClientClass(String installationCode) {
		
		HashMap<String, MonitorCommServices> map = serviceMap.get(installationCode);
		
		if (map == null || map.size() == 0) {
			setEntries(serviceConfigs);
			map = serviceMap.get(installationCode);
		}

		return map.get("SERVICES");
	}
	
	/**
	 * Delegated method to return the cached client stubs 
	 * 
	 * @param installationCode
	 * 		  The installation code
	 * @return the cached client stub
	 */
	CoreCommServices getCoreClientClass(String installationCode) {
		
		HashMap<String, CoreCommServices> map = coreServiceMap.get(installationCode);
		
		if (map == null || map.size() == 0) {
			setCoreEntries(serviceConfigs);
			map = coreServiceMap.get(installationCode);
		}
		
		return map.get("SERVICES");
	}
	
	/**
	 * Utility method to process a single configuration entry 
	 * Using the entry it would load the url and cache the client stub
	 * 
	 * @param entries
	 * 		  The config entries
	 */
	private void setEntries(List<CConfig> entries){
		for(CConfig entry : entries){
			String code2 = entry.getCode2();
			if (serviceMap.containsKey(code2)) {
				HashMap<String, MonitorCommServices> map = serviceMap.get(code2);
				String code3 = entry.getCode3();
				if(!map.containsKey(code3)){
					MonitorCommServices obj = stackClientStub(entry.getValue());
					if(obj != null)
						map.put(code3, obj);
				}
			} else {
				HashMap<String, MonitorCommServices> map = new HashMap<String, MonitorCommServices>();
				String code3 = entry.getCode3();
				MonitorCommServices obj = stackClientStub(entry.getValue());
				if(obj != null)
					map.put(code3, obj);
				if(map.size() > 0)
					serviceMap.put(code2, map);
			}
		}
	}
	
	/**
	 * Utility method to process a single configuration entry 
	 * Using the entry it would load the url and cache the client stub
	 * 
	 * @param entries
	 * 		  The config entries
	 */
	private void setCoreEntries(List<CConfig> entries){
		for(CConfig entry : entries){
			String code2 = entry.getCode2();
			if (coreServiceMap.containsKey(code2)) {
				HashMap<String, CoreCommServices> map = coreServiceMap.get(code2);
				String code3 = entry.getCode3();
				if(!map.containsKey(code3)){
					CoreCommServices obj = stackCoreClientStub(entry.getValue());
					if(obj != null)
						map.put(code3, obj);
				}
			} else {
				HashMap<String, CoreCommServices> map = new HashMap<String, CoreCommServices>();
				String code3 = entry.getCode3();
				CoreCommServices obj = stackCoreClientStub(entry.getValue());
				if(obj != null)
					map.put(code3, obj);
				if(map.size() > 0)
					coreServiceMap.put(code2, map);
			}
		}
	}

	/**
	 * Helper method that would actually load the URL and return the client stub 
	 * 
	 * @param value
	 * 		  The IP and the PORT for which the client have to be cached
	 * @return the client stub
	 */
	private MonitorCommServices stackClientStub(String value) {

		String urlString = "https://" + value + "/"
				+ MonitorCommServices.class.getSimpleName();
		try {
			JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
			proxyFactory.setServiceClass(MonitorCommServices.class);
			proxyFactory.setAddress(urlString);
			
			MonitorCommServices client = (MonitorCommServices) proxyFactory.create();
			
			configureSSLOnTheClient(client);
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() != null) e.getCause().printStackTrace();
			System.err.println(urlString
					+ " --> Can not initialize the WSDL from URL ");
		}

		return null;
	}
	
	/**
	 * Helper method that would actually load the URL and return the client stub 
	 * 
	 * @param value
	 * 		  The IP and the PORT for which the client have to be cached
	 * @return the client stub
	 */
	private CoreCommServices stackCoreClientStub(String value){

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

            keyStore.load(IOUtils.getResourceAsInputStream(this.getClass(), "resources/sslcert/truststore.jks"), trustpass.toCharArray());
            TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); 
            trustFactory.init(keyStore);
            TrustManager[] tm = trustFactory.getTrustManagers();
            tlsParams.setTrustManagers(tm);

            keyStore.load(IOUtils.getResourceAsInputStream(this.getClass(), "resources/sslcert/wibble.jks"), trustpass.toCharArray());
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
        } catch (KeyStoreException kse) {
        	printStackTrace(kse);
        } catch (NoSuchAlgorithmException nsa) {
        	printStackTrace(nsa);
        } catch (FileNotFoundException fnfe) {
        	printStackTrace(fnfe);
        } catch (UnrecoverableKeyException uke) {
        	printStackTrace(uke);
		} catch (CertificateException ce) {
			printStackTrace(ce);
		} catch (IOException ioe) {
			printStackTrace(ioe);
		}
	}
	
	private void printStackTrace(Throwable t) {
		if (t != null) {
			t.printStackTrace();
			if (t.getCause() != null) {
				t.getCause().printStackTrace();
			}
		}
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/ClientServices.java                                                                        $
 * 
 * 8     6/24/10 10:45a Mandar.vaidya
 * Removed "?wsdl" from urlString
 * 
 * 7     6/24/10 10:15a Mandar.vaidya
 * Appended "?wsdl" to urlString
 * 
 * 6     6/23/10 4:42p Mandar.vaidya
 * prefixed  resources folder to the sslcert
 * 
 * 5     6/23/10 2:37p Kedarr
 * Changed for configuring SSL
 * 
 * 4     6/22/10 5:31p Mandar.vaidya
 * SSL Implementation
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/