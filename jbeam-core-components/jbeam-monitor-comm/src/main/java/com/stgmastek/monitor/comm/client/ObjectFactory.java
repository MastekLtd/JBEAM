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

package com.stgmastek.monitor.comm.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.stgmastek.monitor.comm.client package. 
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

    private final static QName _StopCoreComm_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "stopCoreComm");
    private final static QName _AddCalendarLog_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addCalendarLog");
    private final static QName _StopMonitorComm_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "stopMonitorComm");
    private final static QName _UpdateBatch_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateBatch");
    private final static QName _UpdateInstructionLog_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateInstructionLog");
    private final static QName _UpdateBatchProgressResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateBatchProgressResponse");
    private final static QName _RefreshProcessRequestSchedule_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "refreshProcessRequestSchedule");
    private final static QName _RefreshProcessRequestScheduleResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "refreshProcessRequestScheduleResponse");
    private final static QName _AddBatch_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "addBatch");
    private final static QName _GetProcessReqScheduleResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "getProcessReqScheduleResponse");
    private final static QName _UpdateBatchProgress_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateBatchProgress");
    private final static QName _UpdateInstructionLogResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateInstructionLogResponse");
    private final static QName _StopCoreCommResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "stopCoreCommResponse");
    private final static QName _AddInstructionLogResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addInstructionLogResponse");
    private final static QName _UpdateSystemInformation_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateSystemInformation");
    private final static QName _AddCalendarLogResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addCalendarLogResponse");
    private final static QName _InterruptBatch_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "interruptBatch");
    private final static QName _InterruptBatchResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "interruptBatchResponse");
    private final static QName _UpdateBatchLogResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateBatchLogResponse");
    private final static QName _UpdateBatchResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateBatchResponse");
    private final static QName _UpdateBatchLog_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateBatchLog");
    private final static QName _GetProcessReqSchedule_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "getProcessReqSchedule");
    private final static QName _CancelCoreSchedule_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "cancelCoreSchedule");
    private final static QName _AddInstructionLog_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "addInstructionLog");
    private final static QName _CancelCoreScheduleResponse_QNAME = new QName("http://services.server.comm.core.stgmastek.com/", "cancelCoreScheduleResponse");
    private final static QName _UpdateSystemInformationResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "updateSystemInformationResponse");
    private final static QName _StopMonitorCommResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "stopMonitorCommResponse");
    private final static QName _AddBatchResponse_QNAME = new QName("http://services.server.comm.monitor.stgmastek.com/", "addBatchResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.stgmastek.monitor.comm.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MReqInstructionLog }
     * 
     */
    public MReqInstructionLog createMReqInstructionLog() {
        return new MReqInstructionLog();
    }

    /**
     * Create an instance of {@link UpdateInstructionLogResponse }
     * 
     */
    public UpdateInstructionLogResponse createUpdateInstructionLogResponse() {
        return new UpdateInstructionLogResponse();
    }

    /**
     * Create an instance of {@link MReqBatchProgress }
     * 
     */
    public MReqBatchProgress createMReqBatchProgress() {
        return new MReqBatchProgress();
    }

    /**
     * Create an instance of {@link CBaseResponseVO }
     * 
     */
    public CBaseResponseVO createCBaseResponseVO() {
        return new CBaseResponseVO();
    }

    /**
     * Create an instance of {@link UpdateBatchLog }
     * 
     */
    public UpdateBatchLog createUpdateBatchLog() {
        return new UpdateBatchLog();
    }

    /**
     * Create an instance of {@link CReqCalendarLog }
     * 
     */
    public CReqCalendarLog createCReqCalendarLog() {
        return new CReqCalendarLog();
    }

    /**
     * Create an instance of {@link AddInstructionLogResponse }
     * 
     */
    public AddInstructionLogResponse createAddInstructionLogResponse() {
        return new AddInstructionLogResponse();
    }

    /**
     * Create an instance of {@link RefreshProcessRequestScheduleResponse }
     * 
     */
    public RefreshProcessRequestScheduleResponse createRefreshProcessRequestScheduleResponse() {
        return new RefreshProcessRequestScheduleResponse();
    }

    /**
     * Create an instance of {@link MReqBatchLog }
     * 
     */
    public MReqBatchLog createMReqBatchLog() {
        return new MReqBatchLog();
    }

    /**
     * Create an instance of {@link UpdateBatchProgressResponse }
     * 
     */
    public UpdateBatchProgressResponse createUpdateBatchProgressResponse() {
        return new UpdateBatchProgressResponse();
    }

    /**
     * Create an instance of {@link BatchLog }
     * 
     */
    public BatchLog createBatchLog() {
        return new BatchLog();
    }

    /**
     * Create an instance of {@link MBaseRequestVO }
     * 
     */
    public MBaseRequestVO createMBaseRequestVO() {
        return new MBaseRequestVO();
    }

    /**
     * Create an instance of {@link RefreshProcessRequestSchedule }
     * 
     */
    public RefreshProcessRequestSchedule createRefreshProcessRequestSchedule() {
        return new RefreshProcessRequestSchedule();
    }

    /**
     * Create an instance of {@link UpdateSystemInformation }
     * 
     */
    public UpdateSystemInformation createUpdateSystemInformation() {
        return new UpdateSystemInformation();
    }

    /**
     * Create an instance of {@link UpdateInstructionLog }
     * 
     */
    public UpdateInstructionLog createUpdateInstructionLog() {
        return new UpdateInstructionLog();
    }

    /**
     * Create an instance of {@link CReqProcessRequestScheduleVO }
     * 
     */
    public CReqProcessRequestScheduleVO createCReqProcessRequestScheduleVO() {
        return new CReqProcessRequestScheduleVO();
    }

    /**
     * Create an instance of {@link MReqBatch }
     * 
     */
    public MReqBatch createMReqBatch() {
        return new MReqBatch();
    }

    /**
     * Create an instance of {@link CReqInstructionLog }
     * 
     */
    public CReqInstructionLog createCReqInstructionLog() {
        return new CReqInstructionLog();
    }

    /**
     * Create an instance of {@link CancelCoreScheduleResponse }
     * 
     */
    public CancelCoreScheduleResponse createCancelCoreScheduleResponse() {
        return new CancelCoreScheduleResponse();
    }

    /**
     * Create an instance of {@link UpdateBatch }
     * 
     */
    public UpdateBatch createUpdateBatch() {
        return new UpdateBatch();
    }

    /**
     * Create an instance of {@link BatchProgress }
     * 
     */
    public BatchProgress createBatchProgress() {
        return new BatchProgress();
    }

    /**
     * Create an instance of {@link UpdateBatchProgress }
     * 
     */
    public UpdateBatchProgress createUpdateBatchProgress() {
        return new UpdateBatchProgress();
    }

    /**
     * Create an instance of {@link CResProcessRequestScheduleVO }
     * 
     */
    public CResProcessRequestScheduleVO createCResProcessRequestScheduleVO() {
        return new CResProcessRequestScheduleVO();
    }

    /**
     * Create an instance of {@link GetProcessReqSchedule }
     * 
     */
    public GetProcessReqSchedule createGetProcessReqSchedule() {
        return new GetProcessReqSchedule();
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
     * Create an instance of {@link InterruptBatch }
     * 
     */
    public InterruptBatch createInterruptBatch() {
        return new InterruptBatch();
    }

    /**
     * Create an instance of {@link AddBatch }
     * 
     */
    public AddBatch createAddBatch() {
        return new AddBatch();
    }

    /**
     * Create an instance of {@link MBaseResponseVO }
     * 
     */
    public MBaseResponseVO createMBaseResponseVO() {
        return new MBaseResponseVO();
    }

    /**
     * Create an instance of {@link CalendarData }
     * 
     */
    public CalendarData createCalendarData() {
        return new CalendarData();
    }

    /**
     * Create an instance of {@link UpdateBatchLogResponse }
     * 
     */
    public UpdateBatchLogResponse createUpdateBatchLogResponse() {
        return new UpdateBatchLogResponse();
    }

    /**
     * Create an instance of {@link InstructionParameters }
     * 
     */
    public InstructionParameters createInstructionParameters() {
        return new InstructionParameters();
    }

    /**
     * Create an instance of {@link InterruptBatchResponse }
     * 
     */
    public InterruptBatchResponse createInterruptBatchResponse() {
        return new InterruptBatchResponse();
    }

    /**
     * Create an instance of {@link MReqSystemInfo }
     * 
     */
    public MReqSystemInfo createMReqSystemInfo() {
        return new MReqSystemInfo();
    }

    /**
     * Create an instance of {@link StopMonitorComm }
     * 
     */
    public StopMonitorComm createStopMonitorComm() {
        return new StopMonitorComm();
    }

    /**
     * Create an instance of {@link CBaseVO }
     * 
     */
    public CBaseVO createCBaseVO() {
        return new CBaseVO();
    }

    /**
     * Create an instance of {@link UpdateSystemInformationResponse }
     * 
     */
    public UpdateSystemInformationResponse createUpdateSystemInformationResponse() {
        return new UpdateSystemInformationResponse();
    }

    /**
     * Create an instance of {@link StopMonitorCommResponse }
     * 
     */
    public StopMonitorCommResponse createStopMonitorCommResponse() {
        return new StopMonitorCommResponse();
    }

    /**
     * Create an instance of {@link StopCoreComm }
     * 
     */
    public StopCoreComm createStopCoreComm() {
        return new StopCoreComm();
    }

    /**
     * Create an instance of {@link AddInstructionLog }
     * 
     */
    public AddInstructionLog createAddInstructionLog() {
        return new AddInstructionLog();
    }

    /**
     * Create an instance of {@link ScheduleData }
     * 
     */
    public ScheduleData createScheduleData() {
        return new ScheduleData();
    }

    /**
     * Create an instance of {@link CReqInstruction }
     * 
     */
    public CReqInstruction createCReqInstruction() {
        return new CReqInstruction();
    }

    /**
     * Create an instance of {@link UpdateBatchResponse }
     * 
     */
    public UpdateBatchResponse createUpdateBatchResponse() {
        return new UpdateBatchResponse();
    }

    /**
     * Create an instance of {@link CancelCoreSchedule }
     * 
     */
    public CancelCoreSchedule createCancelCoreSchedule() {
        return new CancelCoreSchedule();
    }

    /**
     * Create an instance of {@link AddCalendarLogResponse }
     * 
     */
    public AddCalendarLogResponse createAddCalendarLogResponse() {
        return new AddCalendarLogResponse();
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
     * Create an instance of {@link MBaseVO }
     * 
     */
    public MBaseVO createMBaseVO() {
        return new MBaseVO();
    }

    /**
     * Create an instance of {@link AddBatchResponse }
     * 
     */
    public AddBatchResponse createAddBatchResponse() {
        return new AddBatchResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link StopMonitorComm }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "stopMonitorComm")
    public JAXBElement<StopMonitorComm> createStopMonitorComm(StopMonitorComm value) {
        return new JAXBElement<StopMonitorComm>(_StopMonitorComm_QNAME, StopMonitorComm.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateBatch")
    public JAXBElement<UpdateBatch> createUpdateBatch(UpdateBatch value) {
        return new JAXBElement<UpdateBatch>(_UpdateBatch_QNAME, UpdateBatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInstructionLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateInstructionLog")
    public JAXBElement<UpdateInstructionLog> createUpdateInstructionLog(UpdateInstructionLog value) {
        return new JAXBElement<UpdateInstructionLog>(_UpdateInstructionLog_QNAME, UpdateInstructionLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBatchProgressResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateBatchProgressResponse")
    public JAXBElement<UpdateBatchProgressResponse> createUpdateBatchProgressResponse(UpdateBatchProgressResponse value) {
        return new JAXBElement<UpdateBatchProgressResponse>(_UpdateBatchProgressResponse_QNAME, UpdateBatchProgressResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshProcessRequestSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "refreshProcessRequestSchedule")
    public JAXBElement<RefreshProcessRequestSchedule> createRefreshProcessRequestSchedule(RefreshProcessRequestSchedule value) {
        return new JAXBElement<RefreshProcessRequestSchedule>(_RefreshProcessRequestSchedule_QNAME, RefreshProcessRequestSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshProcessRequestScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "refreshProcessRequestScheduleResponse")
    public JAXBElement<RefreshProcessRequestScheduleResponse> createRefreshProcessRequestScheduleResponse(RefreshProcessRequestScheduleResponse value) {
        return new JAXBElement<RefreshProcessRequestScheduleResponse>(_RefreshProcessRequestScheduleResponse_QNAME, RefreshProcessRequestScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "addBatch")
    public JAXBElement<AddBatch> createAddBatch(AddBatch value) {
        return new JAXBElement<AddBatch>(_AddBatch_QNAME, AddBatch.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBatchProgress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateBatchProgress")
    public JAXBElement<UpdateBatchProgress> createUpdateBatchProgress(UpdateBatchProgress value) {
        return new JAXBElement<UpdateBatchProgress>(_UpdateBatchProgress_QNAME, UpdateBatchProgress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInstructionLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateInstructionLogResponse")
    public JAXBElement<UpdateInstructionLogResponse> createUpdateInstructionLogResponse(UpdateInstructionLogResponse value) {
        return new JAXBElement<UpdateInstructionLogResponse>(_UpdateInstructionLogResponse_QNAME, UpdateInstructionLogResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInstructionLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.core.stgmastek.com/", name = "addInstructionLogResponse")
    public JAXBElement<AddInstructionLogResponse> createAddInstructionLogResponse(AddInstructionLogResponse value) {
        return new JAXBElement<AddInstructionLogResponse>(_AddInstructionLogResponse_QNAME, AddInstructionLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSystemInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateSystemInformation")
    public JAXBElement<UpdateSystemInformation> createUpdateSystemInformation(UpdateSystemInformation value) {
        return new JAXBElement<UpdateSystemInformation>(_UpdateSystemInformation_QNAME, UpdateSystemInformation.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBatchLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateBatchLogResponse")
    public JAXBElement<UpdateBatchLogResponse> createUpdateBatchLogResponse(UpdateBatchLogResponse value) {
        return new JAXBElement<UpdateBatchLogResponse>(_UpdateBatchLogResponse_QNAME, UpdateBatchLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBatchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateBatchResponse")
    public JAXBElement<UpdateBatchResponse> createUpdateBatchResponse(UpdateBatchResponse value) {
        return new JAXBElement<UpdateBatchResponse>(_UpdateBatchResponse_QNAME, UpdateBatchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBatchLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateBatchLog")
    public JAXBElement<UpdateBatchLog> createUpdateBatchLog(UpdateBatchLog value) {
        return new JAXBElement<UpdateBatchLog>(_UpdateBatchLog_QNAME, UpdateBatchLog.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSystemInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "updateSystemInformationResponse")
    public JAXBElement<UpdateSystemInformationResponse> createUpdateSystemInformationResponse(UpdateSystemInformationResponse value) {
        return new JAXBElement<UpdateSystemInformationResponse>(_UpdateSystemInformationResponse_QNAME, UpdateSystemInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopMonitorCommResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "stopMonitorCommResponse")
    public JAXBElement<StopMonitorCommResponse> createStopMonitorCommResponse(StopMonitorCommResponse value) {
        return new JAXBElement<StopMonitorCommResponse>(_StopMonitorCommResponse_QNAME, StopMonitorCommResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBatchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.server.comm.monitor.stgmastek.com/", name = "addBatchResponse")
    public JAXBElement<AddBatchResponse> createAddBatchResponse(AddBatchResponse value) {
        return new JAXBElement<AddBatchResponse>(_AddBatchResponse_QNAME, AddBatchResponse.class, null, value);
    }

}
