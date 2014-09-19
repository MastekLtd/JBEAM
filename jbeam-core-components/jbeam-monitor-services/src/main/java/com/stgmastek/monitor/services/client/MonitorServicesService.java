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


package com.stgmastek.monitor.services.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.1.3
 * Thu Dec 09 10:30:15 IST 2010
 * Generated source version: 2.1.3
 * 
 */


@WebServiceClient(name = "MonitorServicesService", 
                  wsdlLocation = "http://172.16.211.111:15235/MonitorServices?wsdl",
                  targetNamespace = "http://services.server.ws.monitor.stgmastek.com/") 
public class MonitorServicesService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://services.server.ws.monitor.stgmastek.com/", "MonitorServicesService");
    public final static QName MonitorServicesPort = new QName("http://services.server.ws.monitor.stgmastek.com/", "MonitorServicesPort");
    static {
        URL url = null;
        try {
            url = new URL("http://172.16.211.111:15235/MonitorServices?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://172.16.211.111:15235/MonitorServices?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public MonitorServicesService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MonitorServicesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MonitorServicesService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns MonitorServices
     */
    @WebEndpoint(name = "MonitorServicesPort")
    public MonitorServices getMonitorServicesPort() {
        return super.getPort(MonitorServicesPort, MonitorServices.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MonitorServices
     */
    @WebEndpoint(name = "MonitorServicesPort")
    public MonitorServices getMonitorServicesPort(WebServiceFeature... features) {
        return super.getPort(MonitorServicesPort, MonitorServices.class, features);
    }

}
