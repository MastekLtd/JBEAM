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

package com.stgmastek.core.comm.client;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.1.3
 * Mon Jan 28 16:40:36 IST 2013
 * Generated source version: 2.1.3
 * 
 */

public final class CoreCommServices_CoreCommServicesPort_Client {

    private static final QName SERVICE_NAME = new QName("http://services.server.comm.core.stgmastek.com/", "CoreCommServicesService");

    private CoreCommServices_CoreCommServicesPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = CoreCommServicesService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        CoreCommServicesService ss = new CoreCommServicesService(wsdlURL, SERVICE_NAME);
        CoreCommServices port = ss.getCoreCommServicesPort();  
        
        {
        System.out.println("Invoking getProcessReqSchedule...");
        com.stgmastek.core.comm.client.CReqProcessRequestScheduleVO _getProcessReqSchedule_arg0 = null;
        com.stgmastek.core.comm.client.CResProcessRequestScheduleVO _getProcessReqSchedule__return = port.getProcessReqSchedule(_getProcessReqSchedule_arg0);
        System.out.println("getProcessReqSchedule.result=" + _getProcessReqSchedule__return);


        }
        {
        System.out.println("Invoking interruptBatch...");
        com.stgmastek.core.comm.client.CReqInstruction _interruptBatch_instruction = null;
        com.stgmastek.core.comm.client.CBaseResponseVO _interruptBatch__return = port.interruptBatch(_interruptBatch_instruction);
        System.out.println("interruptBatch.result=" + _interruptBatch__return);


        }
        {
        System.out.println("Invoking cancelCoreSchedule...");
        com.stgmastek.core.comm.client.CReqProcessRequestScheduleVO _cancelCoreSchedule_arg0 = null;
        com.stgmastek.core.comm.client.CResProcessRequestScheduleVO _cancelCoreSchedule__return = port.cancelCoreSchedule(_cancelCoreSchedule_arg0);
        System.out.println("cancelCoreSchedule.result=" + _cancelCoreSchedule__return);


        }
        {
        System.out.println("Invoking addCalendarLog...");
        com.stgmastek.core.comm.client.CReqCalendarLog _addCalendarLog_reqCalendarLog = null;
        com.stgmastek.core.comm.client.CBaseResponseVO _addCalendarLog__return = port.addCalendarLog(_addCalendarLog_reqCalendarLog);
        System.out.println("addCalendarLog.result=" + _addCalendarLog__return);


        }
        {
        System.out.println("Invoking addInstructionLog...");
        com.stgmastek.core.comm.client.CReqInstructionLog _addInstructionLog_reqInstructionLog = null;
        com.stgmastek.core.comm.client.CBaseResponseVO _addInstructionLog__return = port.addInstructionLog(_addInstructionLog_reqInstructionLog);
        System.out.println("addInstructionLog.result=" + _addInstructionLog__return);


        }
        {
        System.out.println("Invoking stopCoreComm...");
        port.stopCoreComm();


        }

        System.exit(0);
    }

}
