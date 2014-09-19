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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.stgmastek.core.comm.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddInstructionLog_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addInstructionLog");
    private final static QName _CancelCoreScheduleResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "cancelCoreScheduleResponse");
    private final static QName _AddInstructionLogResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addInstructionLogResponse");
    private final static QName _AddCalendarLogResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addCalendarLogResponse");
    private final static QName _StopCoreComm_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "stopCoreComm");
    private final static QName _AddCalendarLog_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addCalendarLog");
    private final static QName _GetProcessReqScheduleResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "getProcessReqScheduleResponse");
    private final static QName _InterruptBatch_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "interruptBatch");
    private final static QName _InterruptBatchResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "interruptBatchResponse");
    private final static QName _StopCoreCommResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "stopCoreCommResponse");
    private final static QName _GetProcessReqSchedule_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "getProcessReqSchedule");
    private final static QName _CancelCoreSchedule_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "cancelCoreSchedule");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.stgmastek.core.comm.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InterruptBatchResponse }
     * 
     */
    public InterruptBatchResponse createInterruptBatchResponse() {
        return new InterruptBatchResponse();
    }

    /**
     * Create an instance of {@link AddInstructionLogResponse }
     * 
     */
    public AddInstructionLogResponse createAddInstructionLogResponse() {
        return new AddInstructionLogResponse();
    }

    /**
     * Create an instance of {@link CBaseVO }
     * 
     */
    public CBaseVO createCBaseVO() {
        return new CBaseVO();
    }

    /**
     * Create an instance of {@link InterruptBatch }
     * 
     */
    public InterruptBatch createInterruptBatch() {
        return new InterruptBatch();
    }

    /**
     * Create an instance of {@link InstructionParameters }
     * 
     */
    public InstructionParameters createInstructionParameters() {
        return new InstructionParameters();
    }

    /**
     * Create an instance of {@link CReqInstruction }
     * 
     */
    public CReqInstruction createCReqInstruction() {
        return new CReqInstruction();
    }

    /**
     * Create an instance of {@link CReqProcessRequestScheduleVO }
     * 
     */
    public CReqProcessRequestScheduleVO createCReqProcessRequestScheduleVO() {
        return new CReqProcessRequestScheduleVO();
    }

    /**
     * Create an instance of {@link CancelCoreSchedule }
     * 
     */
    public CancelCoreSchedule createCancelCoreSchedule() {
        return new CancelCoreSchedule();
    }

    /**
     * Create an instance of {@link CReqCalendarLog }
     * 
     */
    public CReqCalendarLog createCReqCalendarLog() {
        return new CReqCalendarLog();
    }

    /**
     * Create an instance of {@link CResProcessRequestScheduleVO }
     * 
     */
    public CResProcessRequestScheduleVO createCResProcessRequestScheduleVO() {
        return new CResProcessRequestScheduleVO();
    }

    /**
     * Create an instance of {@link ScheduleData }
     * 
     */
    public ScheduleData createScheduleData() {
        return new ScheduleData();
    }

    /**
     * Create an instance of {@link CalendarData }
     * 
     */
    public CalendarData createCalendarData() {
        return new CalendarData();
    }

    /**
     * Create an instance of {@link AddCalendarLogResponse }
     * 
     */
    public AddCalendarLogResponse createAddCalendarLogResponse() {
        return new AddCalendarLogResponse();
    }

    /**
     * Create an instance of {@link AddInstructionLog }
     * 
     */
    public AddInstructionLog createAddInstructionLog() {
        return new AddInstructionLog();
    }

    /**
     * Create an instance of {@link CReqInstructionLog }
     * 
     */
    public CReqInstructionLog createCReqInstructionLog() {
        return new CReqInstructionLog();
    }

    /**
     * Create an instance of {@link GetProcessReqScheduleResponse }
     * 
     */
    public GetProcessReqScheduleResponse createGetProcessReqScheduleResponse() {
        return new GetProcessReqScheduleResponse();
    }

    /**
     * Create an instance of {@link CBaseRequestVO }
     * 
     */
    public CBaseRequestVO createCBaseRequestVO() {
        return new CBaseRequestVO();
    }

    /**
     * Create an instance of {@link GetProcessReqSchedule }
     * 
     */
    public GetProcessReqSchedule createGetProcessReqSchedule() {
        return new GetProcessReqSchedule();
    }

    /**
     * Create an instance of {@link StopCoreCommResponse }
     * 
     */
    public StopCoreCommResponse createStopCoreCommResponse() {
        return new StopCoreCommResponse();
    }

    /**
     * Create an instance of {@link AddCalendarLog }
     * 
     */
    public AddCalendarLog createAddCalendarLog() {
        return new AddCalendarLog();
    }

    /**
     * Create an instance of {@link CBaseResponseVO }
     * 
     */
    public CBaseResponseVO createCBaseResponseVO() {
        return new CBaseResponseVO();
    }

    /**
     * Create an instance of {@link StopCoreComm }
     * 
     */
    public StopCoreComm createStopCoreComm() {
        return new StopCoreComm();
    }

    /**
     * Create an instance of {@link CancelCoreScheduleResponse }
     * 
     */
    public CancelCoreScheduleResponse createCancelCoreScheduleResponse() {
        return new CancelCoreScheduleResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInstructionLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "addInstructionLog")
    public JAXBElement<AddInstructionLog> createAddInstructionLog(AddInstructionLog value) {
        return new JAXBElement<AddInstructionLog>(_AddInstructionLog_QNAME, AddInstructionLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelCoreScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "cancelCoreScheduleResponse")
    public JAXBElement<CancelCoreScheduleResponse> createCancelCoreScheduleResponse(CancelCoreScheduleResponse value) {
        return new JAXBElement<CancelCoreScheduleResponse>(_CancelCoreScheduleResponse_QNAME, CancelCoreScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInstructionLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "addInstructionLogResponse")
    public JAXBElement<AddInstructionLogResponse> createAddInstructionLogResponse(AddInstructionLogResponse value) {
        return new JAXBElement<AddInstructionLogResponse>(_AddInstructionLogResponse_QNAME, AddInstructionLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCalendarLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "addCalendarLogResponse")
    public JAXBElement<AddCalendarLogResponse> createAddCalendarLogResponse(AddCalendarLogResponse value) {
        return new JAXBElement<AddCalendarLogResponse>(_AddCalendarLogResponse_QNAME, AddCalendarLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopCoreComm }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "stopCoreComm")
    public JAXBElement<StopCoreComm> createStopCoreComm(StopCoreComm value) {
        return new JAXBElement<StopCoreComm>(_StopCoreComm_QNAME, StopCoreComm.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCalendarLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "addCalendarLog")
    public JAXBElement<AddCalendarLog> createAddCalendarLog(AddCalendarLog value) {
        return new JAXBElement<AddCalendarLog>(_AddCalendarLog_QNAME, AddCalendarLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessReqScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "getProcessReqScheduleResponse")
    public JAXBElement<GetProcessReqScheduleResponse> createGetProcessReqScheduleResponse(GetProcessReqScheduleResponse value) {
        return new JAXBElement<GetProcessReqScheduleResponse>(_GetProcessReqScheduleResponse_QNAME, GetProcessReqScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InterruptBatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "interruptBatch")
    public JAXBElement<InterruptBatch> createInterruptBatch(InterruptBatch value) {
        return new JAXBElement<InterruptBatch>(_InterruptBatch_QNAME, InterruptBatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InterruptBatchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "interruptBatchResponse")
    public JAXBElement<InterruptBatchResponse> createInterruptBatchResponse(InterruptBatchResponse value) {
        return new JAXBElement<InterruptBatchResponse>(_InterruptBatchResponse_QNAME, InterruptBatchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopCoreCommResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "stopCoreCommResponse")
    public JAXBElement<StopCoreCommResponse> createStopCoreCommResponse(StopCoreCommResponse value) {
        return new JAXBElement<StopCoreCommResponse>(_StopCoreCommResponse_QNAME, StopCoreCommResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessReqSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "getProcessReqSchedule")
    public JAXBElement<GetProcessReqSchedule> createGetProcessReqSchedule(GetProcessReqSchedule value) {
        return new JAXBElement<GetProcessReqSchedule>(_GetProcessReqSchedule_QNAME, GetProcessReqSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelCoreSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "cancelCoreSchedule")
    public JAXBElement<CancelCoreSchedule> createCancelCoreSchedule(CancelCoreSchedule value) {
        return new JAXBElement<CancelCoreSchedule>(_CancelCoreSchedule_QNAME, CancelCoreSchedule.class, null, value);
    }

}
