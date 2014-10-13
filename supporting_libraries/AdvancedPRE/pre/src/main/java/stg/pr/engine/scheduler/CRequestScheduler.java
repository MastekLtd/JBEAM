/*
* This file forms part of the STGIL
* Copyright (c) GMac.plc. 2002 - 2002 All rights reserved.
*
* $Revision: 3370 $
*
*/

package stg.pr.engine.scheduler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import stg.customclassloader.CCustomClassLoaderFactory;
import stg.customclassloader.IPREClassLoaderClient;
import stg.database.CBeanException;
import stg.database.CDB;
import stg.database.CDBException;
import stg.database.CDynamicDataContainer;
import stg.database.CDynamicDataContainerException;
import stg.pr.beans.ProcessReqParamsController;
import stg.pr.beans.ProcessReqParamsEntityBean;
import stg.pr.beans.ProcessRequestController;
import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleController;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.beans.ScheduleEventCalendarController;
import stg.pr.beans.ScheduleEventCalendarEntityBean;
import stg.pr.engine.PREContext;
import stg.pr.engine.IProcessRequest.REQUEST_STATUS;
import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.mailer.CMailer;
import stg.pr.engine.mailer.EMail;
//import stg.utils.CDate;
import stg.utils.CSettings;
import stg.utils.Day;
import stg.utils.PREDataType;

import com.stg.logger.LogLevel;

/**
* Scheduler.
* 
* The Scheduler schedules the request depending on the requested schedule associated with a request. 
* The scheduler generates a new request every time when a scheduled request is executed as per the schedule.
* The schedule executes the class that implements IRequestIdGenerator interface which is defined in the 
* pr.properties file. This gives the administrator the flexibility of having their own class to generate a 
* Request ID in a different format. Every schedule is validated before re-scheduling the request. The validator 
* class must implement IScheduleValidator. The methods called in this are in following order
* <ul>
* <li> setPrintWriter(pwRequestLogWriter);
* <li> setConnection(Connection con);
* <li> validateSchedule(long plSchId);
* </ul>
* Every schedule is also associated with a PRE or POST event. A PRE event is called after the schedule is
* validated and just before re-scheduling. A POST event is called just after re-scheduling is done. 
* The class that implements the interface IScheduleEvent will be called in the following order.
* <BR><B>NOTE:</B> IScheduleEvent methods are called on the same object thus the object can maintain its state.
* <BR>
* <ul>
* <li> setPrintWriter(pwRequestLogWriter);
* <li> setConnection(Connection con);
* <li> performPreAction(long plOldRequestId, long plScheduleId);
* <li> performPostAction(long plNewRequestId);
* </ul>
* 
* The request that is scheduled has parameters of type Date or Timestamp and if marked as dynamic then these 
* parameters will be advanced depending on the schedule. 
* <p>
* <ul>
* <li> <b>Current Limitations </b></li>
* <dl>
* <dt> The scheduling is currently done for only Stand Alone requests. For grouped requests the scheduling is not done.
* <dt>
* <dt> No End Date feature not yet provided.
* <dt> For a schedule to work there must be existing request, which is in queued status. So if the user needs to schedule 10 requests then the user must schedule 9 and the request for which the user is putting a schedule will be the 10th. The user must ensure that the schedule time for the request should match with the schedule start date.
* </dl>
* </ul>
* </p>
* 
* @see stg.pr.engine.scheduler.ISchedule
* @see stg.pr.engine.scheduler.IScheduleValidator
* @see stg.pr.engine.scheduler.IScheduleEvent
* @see stg.pr.engine.scheduler.IRequestIdGenerator
* 
* @version $Revision:: 3370                   $
* @author   Kedar C. Raybagkar
* 
**/
public class CRequestScheduler
    extends Object
    implements IScheduleValidator, IRequestIdGenerator, IPREClassLoaderClient
{

    //public instance constants and class constants
    
    /**
     * This indicates the version of the class.
     * Comment for <code>REVISION</code>
     */
    public static final String REVISION = "$Revision: 3370 $";

    //public instance variables

    //public class(static) variables

    //protected instance constants and class constants

    //protected instance variables

    //protected class(static) variables

    //package instance constants and class constants

    //package instance variables

    //package class(static) variables

    //private instance constants and class constants

    //private instance variables

    /**
    * Schedule Id. This variable stores the schedule id of the request's schedule.
    **/
    private long lSchId_;

    /**
     * This variable stores the new Request ID generated after scheduling.
     */
    private long lNewRequestId_;

    /**
     * This variable stores the PrintWriter associated with the Request.
     */
    private PrintWriter pwRequestLogWriter_;

    /**
    * Request Id. This variable stores the Request Id of the request for which the schedule is to be created.
    *
    **/
    private ProcessRequestEntityBean objPREB_;

    /**
    * Connection. This variable stores the connection Object.
    **/
    private Connection con_;

    /**
     * Variable stores the frequency type of the schedule.
     */
    private FREQUENCY frequency_;

    /**
     * Variable stores the date when the request was processed.
     */
    private Day dayReqProcessEndDate_;

    /**
     * Variable stores the recurrance factor of the schedule. 
     */
    private int iRecur_;

    /**
     * Variable stores the request process start time. 
     */
    private Timestamp tsStartTime_;

    /**
     * Variable stores the request process end time.
     */
    private Timestamp tsEndTime_;

    /**
     * Variable stores the week day factor of the schedule.
     */
    private String strOnWeekDays_;

    /**
     * Variable stores the future scheduling factor of the schedule.
     */
    private boolean bFutureSchedulingOnly_;

    /**
     * Variable store the request date parameter format.
     * This will be used to convert the character value to date and vice-versa.
     */
    private SimpleDateFormat paramDateFormat_;

//    /**
//     * Variable stores the request time parameter format.
//     * This will be used to convert the character value to time and vice-versa.
//     */
//    private SimpleDateFormat paramTimeFormat_;
    
    /**
     * Stores the date and time format.
     */
    private SimpleDateFormat dateTimeFormat_;

    /**
     * HashMap representing the parameters associated with the Request.
     * 
     * Comment for <code>hmParameters_</code>
     */
    private HashMap<String, Object> hmParameters_;
    
    /**
     * Instance of logger.
     * Comment for <code>logger_</code>
     */
    private Logger logger_;

    /**
     * This variable stores the delimeter used to separate the DATE and TIMESTAMP arrays used for
     * parameters.
     * Comment for <code>strParamArrayDelimeter_</code>
     */
    private String strParamArrayDelimeter_;

    /**
     * This variable stores the original request schedule date.
     * Comment for <code>tsRequestSchDate_</code>
     */
    private Timestamp tsCalendarScheduleDate_;

    /**
     * This variable stores the original request schedule date.
     * Comment for <code>tsActualRequestSchDate_</code>
     */
    private Timestamp tsActualRequestSchDate_;

    /**
     * This object stores the scheduled date after it is adavanced by the Scheduler.
     * Comment for <code>daySchRequest_</code>
     */
    private Day daySchRequest_;

    /**
     * This variable stores the number of times the day was advanced. This is applicable to
     * frequencies like {@link ISchedule.FREQUENCY#MONTH}, {@link ISchedule.FREQUENCY#YEAR},
     * {@link ISchedule.FREQUENCY#LAST_DATE_OF_MONTH} 
     * Comment for <code>iAdvanceCounter_</code>
     */
    private int iAdvanceCounter_;

    /**
     * Byte array to capture the stack trace.
     */
    private ByteArrayOutputStream bytes_ = null;
    
    /**
     * Print Writer to print the stack trace on <code>bytes_<code>
     */
    private PrintWriter pwException_ = null;
    
    /**
     * This variable stores the object of Pre-Defined Frequency.
     * 
     * Comment for <code>objPreDefinedFrequency_</code>
     */
    IPreDefinedFrequency objPreDefinedFrequency_ = null;
    
    /**
     * Stores the extra message that needs to be mailed. 
     * This value in this variable is by default added in the mail body.
     */
    private String strExtraMessageToBeMailed_ = "";
    
    /**
     * Stores the new line based on the OS.
     * 
     * Comment for <code>NEW_LINE</code>.
     */
    private final String NEW_LINE = CSettings.get("system.line.separator");

    /**
     * Stores the skip flag associated with the schedule.
     */
    private SKIP_FLAG skipFlag_;

    /**
     * Stores the weekday flag asscoiated with the schedule.
     */
    private boolean bWeekdayCheckFlag_;

    /**
     * Stores the skip factor associated with the schedule.
     */
    private int iSkipFactor_ = 0;

    /**
     * Stores the actual scheduled time associated with the request.
     */
    private Day dayActualRequestSchTime_;

    /**
     * True if the business working day is to be called for.
     */
    private boolean bPerformBusinessWorkingDayCheck_;
    
    /**
     * Stores the object of class that implements ICalendar interface.
     */
    private List<ICalendar> listICalendar_ = null;

    private PREContext context_;

    private IDataSourceFactory dataSourceFactory;

    private boolean conToBeClosed;

    /**
     * Default constructor for the class required for Implementation of IRequestIdGenerator & IScheduleValidator.
     *
     */
    public CRequestScheduler()
    {
        bytes_ = new ByteArrayOutputStream();
        pwException_ = new PrintWriter(bytes_, true);
    }

    /**
     * Creates a scheduler for a given schedule id.
     *
     * @param plSchId Schedule id to be scheduled.
     * @param objPREB {@link ProcessRequestEntityBean}
     * @param phmParameters HashMap containing associated parameters for a request.
     * @param pstrDateFormat Date Format as stored in properties file.
     * @param pstrTimeFormat Time Format as stored in properties file.
     * @param context Context
     */
    public CRequestScheduler(
        long plSchId,
        ProcessRequestEntityBean objPREB,
        HashMap<String, Object> phmParameters,
        SimpleDateFormat pstrDateFormat,
        SimpleDateFormat pstrTimeFormat,
        SimpleDateFormat pstrDateTimeFormat,
        PREContext context)
    {
        this(); //instantiates bytes_ and pwException variables.
        lSchId_ = plSchId;
        this.objPREB_ = objPREB;
        paramDateFormat_ = pstrDateFormat;
//        paramTimeFormat_ = new SimpleDateFormat(pstrTimeFormat);
        hmParameters_ = phmParameters;
        logger_ = Logger.getLogger("Scheduler");
        dateTimeFormat_ = pstrDateTimeFormat;
        context_ = context;
    }

    //finalize method, if any

    //main method

    //public methods of the class in the following order

    /**
    * Schedules the request.
    * The method validates the schedule, executes a PRE-Schedule Event if any, advances the schedule 
    * time according to the schedule, executes a POST-Schedule Event if any for the schedule associated
    * with the request.
    * 
    * Generates a new Request for the request scheduled with appropriate parameters.
    * <ul>
    *  <li> Description of attributes
    *     <dl>
    *     <dt> Frequency Type <dd> Frequency Type determines the scheduling frequency. This can be :
    *  <table cols="3">
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#MINUTE}</td> <td>MINUTE wise execution</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#HOUR}</td><td>Hour wise execution</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#DAY}</td><td>Day wise execution</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#WEEK}</td><td>WEEK wise execution</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#MONTH}</td><td>MONTH wise execution</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#YEAR}</td><td>Year wise execution</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#LAST_DATE_OF_MONTH}</td><td>Last date of the month say 31 or 28/29 or 30</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#FIRST_DAY_OF_MONTH}</td><td>Used for First Day(Sun, Mon, etc) in the Month</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#SECOND_DAY_OF_MONTH}</td><td>Used for Second Day(Sun, Mon, etc) in the Month</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#THIRD_DAY_OF_MONTH}</td><td>Used for Third Day(Sun, Mon, etc) in the Month</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#FOURTH_DAY_OF_MONTH}</td><td>Used for Fourth Day(Sun, Mon, etc) in the Month</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#LAST_DATE_OF_MONTH}</td><td>Used for Last Day(Sun, Mon, etc) in the Month</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#FIRST_DAY_OF_YEAR}</td><td>Used for First Day(Sun, Mon, etc) of the week recuring over in a yearly basis</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#SECOND_DAY_OF_YEAR}</td><td>Used for Second Day(Sun, Mon, etc) of the week recuring over in a yearly basis</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#THIRD_DAY_OF_YEAR}</td><td>Used for Third Day(Sun, Mon, etc) of the week recuring over in a yearly basis</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#FOURTH_DAY_OF_YEAR}</td><td>Used for Fourth Day(Sun, Mon, etc) of the week recuring over in a yearly basis</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#LAST_DAY_OF_YEAR}</td><td>Used for Last Day(Sun, Mon, etc) of the week recuring over in a yearly basis</td>
    *  </tr>
    *  <tr>
    *  <td colspan="2">{@link ISchedule.FREQUENCY#PRE_PROGRAMMED}</td>
    *  <td>This is a programmable frequency. The class that implements the interface {@link IPreDefinedFrequency} will be 
    * called by the scheduler to schedule for future dates.
    * </td>
    *  </tr>
    *  <tr>
    *  </tr>
    *  <tr>
    * </table>
    *      </dl>
    *   <dt> Recur <dd> The recurrence factor for the schedule. E.g. If Recur factor is set to 2 
    *        and Frequency Type is set to DAY then it means that recur every 2 Days. Similarly if 
    *        Frequency Type is set to WEEK then it means that recur every 2 Weeks. And so on. 
    *        This is not applicable for frequency type {@link ISchedule.FREQUENCY#PRE_PROGRAMMED}.
    *   <dt> Start Date <dd> The schedule starts on from the start date.
    *   <dt> On Week Day    <dd> Specify the days in the week on which the scheduler should schedule the requests.
    *        E.g. Lets say we have specified a value of 0111110 for "on week day". It means that the Scheduler will schedule from Monday till Friday. 
    *        Sunday and Saturday will not considered for scheduling. If Recur is 2, Frequency Type is WEEK and On Week Day is 1000001 then schedule 
    *        will occur from Sunday --> Saturday and then a week will be skipped and then on second week's Sunday -->... This can also be used in conjuection with frequencies like 
    *        {@link ISchedule.FREQUENCY#FIRST_DAY_OF_MONTH}, {@link ISchedule.FREQUENCY#SECOND_DAY_OF_MONTH}, {@link ISchedule.FREQUENCY#THIRD_DAY_OF_MONTH},
    *        {@link ISchedule.FREQUENCY#FOURTH_DAY_OF_MONTH}, {@link ISchedule.FREQUENCY#LAST_DATE_OF_MONTH}, {@link ISchedule.FREQUENCY#FIRST_DAY_OF_YEAR},
    *        {@link ISchedule.FREQUENCY#SECOND_DAY_OF_YEAR}, {@link ISchedule.FREQUENCY#THIRD_DAY_OF_YEAR}, {@link ISchedule.FREQUENCY#FOURTH_DAY_OF_YEAR} and
    *        {@link ISchedule.FREQUENCY#LAST_DAY_OF_YEAR}
    *        This is not applicable for frequency type {@link ISchedule.FREQUENCY#PRE_PROGRAMMED}.
    *   <dt> End Date   <dd> The End Date when the schedule will stop scheduling further requests. 
    *        Either End Date OR End Occurrences must be specified. 
    *   <dt> End Occurrences <dd> The schedule will execute the task for the specified times. 
    *        Either End Date or End Occurrences must be specified.
    *   <dt> Start Time <dd> The start time is applicable only for frequencies MINUTE and HOUR. The scheduler will schedule only between the start time and end time including start & end time. Note that the Start time can be greater than End time.
    *   <dt> End   Time <dd> The end time is applicable only for frequencies MINUTE and HOUR. The scheduler will schedule only between the start time and end time including start & end time. Note that the End time can be less than Start time.
    *   <dt> Future Scheduling Only <dd> This indicates that if the schedule should not procreate any JOBs that have scheduled time less than today.
    *   <dt> Fixed Date <dd> This indicates that the schedule should occur only on a fixed date in month. Say for example we want to have a schedule to recur every 1 month on 31st. Feb or Apr will not have 31st so the schedule will skip to Mar or May. 
    *   <dt> Email Ids <dd> Not in Use.
    *   <dd> Skip Flag <dt> Skip flag can be NA, D+, D- or SS. 
    *       <li>NA indicates that the schedule should work according to the frequency associated.</li>
    *       <li><b>D+</b> indicates that the date should be advanced by 1 based on weekday check flag and/or calendar class nm.</li>
    *       <li><b>D-</b> indicates that the date should be reduced by 1  based on weekday check flag and/or calendar class nm.</li>
    *       <li><b>SS</b> indicates that the scheduled should be skipped to the next schedule date & time based on the frequency associated.</li>
    *    <dd> Weekday Check Flag <dt>If the scheduled date is on a week end then the schedule will be advanced to a weekday based on skip flag. Weekday defined is Monday through Friday.
    *    <dd> Calendar class name <dt>A class that implements ICalendar interface.
    *  </dl>
    * </ul>
    *
    **/
    public void schedule()
    {
        if (logger_.isInfoEnabled()) {
            logger_.info("Trying to Schedule Id#" + objPREB_.getSchId() + " for Request Id#" + objPREB_.getReqId());
        }
        lNewRequestId_ = 0;
        CDynamicDataContainer objCDC = null;
        ProcessRequestScheduleEntityBean objPRSEB = null;
        ProcessRequestScheduleController objPRSC = null;
        strParamArrayDelimeter_ = CSettings.get("pr.paramarrvaluedelim");
        boolean bprocess = true;
        try
        {
            int iICalendarInfiniteLoopTerminationCounter = CSettings.getInstance().getSource("pr").getInteger("icalendarinfiniteloopterminationcounter", 100);
            if (iICalendarInfiniteLoopTerminationCounter < 1 && iICalendarInfiniteLoopTerminationCounter > 100) {
                bprocess = false;
                strExtraMessageToBeMailed_ = "Invalid value set against the property icalendarinfiniteloopterminationcounter. The valid range is 1 to 100.";
                logger_.fatal(strExtraMessageToBeMailed_);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, strExtraMessageToBeMailed_);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        "Associated Schedule for the Request#" +
                        objPREB_.getReqId() +
                        " could not be executed. Improper PRE configuration.", objPREB_.getUserId(), null);
                return;
            }
            objCDC = new CDynamicDataContainer();
            objPRSEB = new ProcessRequestScheduleEntityBean();
            objPRSC = new ProcessRequestScheduleController(con_);

            objPRSEB.setSchId(lSchId_);
//            objPRSEB.setSchStat("A");
            objCDC.build(con_, objPRSEB);
            objCDC.executeQuery(con_, objPRSC, objPRSEB);
            if (!objCDC.next())
            {
                String error = "Associated Schedule for the Request#" + 
                objPREB_.getReqId() + " is non-existant."; 
                logger_.error(error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), null);
                return;
            }
            SCHEDULE_STATUS currentStatus;
            try {
                currentStatus = SCHEDULE_STATUS.resolve(objPRSEB.getSchStat());
            } catch (IllegalArgumentException e) {
                String error = "Invalid Schedule associated with the request. Schedule Status is MANDATORY." +
                " Request#" + objPREB_.getReqId() + " Schedule#" + objPREB_.getSchId(); 
                logger_.error(error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), null);
                return;
            }
            if (currentStatus != SCHEDULE_STATUS.ACTIVE) {
                //Schedule is not active so simply return.
                return;
            }
            objPRSEB = (ProcessRequestScheduleEntityBean) objCDC.get();

            try {
                frequency_ = FREQUENCY.resolve(objPRSEB.getFreqType());
            } catch (IllegalArgumentException e) {
                String error = "Invalid Schedule associated with the Request#" + objPREB_.getReqId() + ". Un-recognized frequency associated."; 
                logger_.error(error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), null);
                return;
            }
            KEEP_ALIVE keepAlive = KEEP_ALIVE.resolve(objPRSEB.getKeepAlive());
            if (keepAlive == KEEP_ALIVE.NO) {
                REQUEST_STATUS status = REQUEST_STATUS.resolve(objPREB_.getReqStat());
                if (status != REQUEST_STATUS.COMPLETED) {
                    String error ="Associated schedule #" + objPRSEB.getSchId() + " could not be executed as the JOB errored out. Request#" + objPREB_.getReqId() + "."; 
                    logger_.error(error);
                    updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            error, objPREB_.getUserId(), error);
                    return;
                }
            }
            long lRecur = objPRSEB.getRecur();
            strOnWeekDays_ = objPRSEB.getOnWeekDay();
            //            Timestamp   tsStartDate         = objPRSEB.getStartDt();
            Timestamp tsEndDate = objPRSEB.getEndDt();
            long lEndOccur = objPRSEB.getEndOccur();
            tsActualRequestSchDate_ = objPREB_.getScheduledTime();
            Timestamp tsRequestEndDate = objPREB_.getReqEndDt();
            long lOccurCounter = objPRSEB.getOccurCounter();
            tsStartTime_ = objPRSEB.getStartTime();
            tsEndTime_ = objPRSEB.getEndTime();
            String strFutureSchedulingOnly = objPRSEB.getFutureSchedulingOnly();
            if (strFutureSchedulingOnly == null
                || strFutureSchedulingOnly.equals(""))
            {
                strFutureSchedulingOnly = ISchedule.FUTURE_SCHEDULING_ONLY;
            }
            bFutureSchedulingOnly_ =
                strFutureSchedulingOnly.equals(
                    ISchedule.FUTURE_SCHEDULING_ONLY);
            iRecur_ = Integer.parseInt(lRecur + "");

            //            Day dayStart = new Day(tsStartDate);
            Day dayEnd = null;
            END_ON endOn = END_ON.OCCURRENCES;
            if (tsEndDate != null)
            {
                dayEnd = new Day(tsEndDate);
                endOn = END_ON.DATE;
            }
            tsCalendarScheduleDate_ = (objPREB_.getCalScheduledTime()==null?objPREB_.getScheduledTime():objPREB_.getCalScheduledTime());
            daySchRequest_ = new Day(tsCalendarScheduleDate_);
            daySchRequest_.setToStringFormat(dateTimeFormat_);
            dayActualRequestSchTime_ = new Day (tsActualRequestSchDate_);
            dayActualRequestSchTime_.setToStringFormat(dateTimeFormat_);

            dayReqProcessEndDate_ = new Day(tsRequestEndDate);

            if (iRecur_ <= 0){
                String error ="Invalid Schedule associated with the Request#" + objPREB_.getReqId() + ". Recurrence must be equal to or greater than 1."; 
                logger_.error(error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), null);
                return;
            }
            //            String strStatus = "F";
            //            boolean bError = false;
            if (endOn == END_ON.OCCURRENCES
                && (lEndOccur != 0 && lEndOccur <= lOccurCounter))
            {
                String error = "Schedule#" + objPREB_.getSchId() + " associated with the Request#" + objPREB_.getReqId() + " has exceeded the End Occurrance.";
                logger_.log(LogLevel.NOTICE, error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.COMPLETED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), null);
                return;
            }

            if (endOn == END_ON.DATE
                && (bFutureSchedulingOnly_
                    && dayEnd.compareInSeconds(dayReqProcessEndDate_) <= 0))
            {
                StringBuilder sbuilder = new StringBuilder();
                sbuilder.append("Schedule#");
                sbuilder.append(objPREB_.getSchId());
                sbuilder.append(" associated with the Request#");
                sbuilder.append(objPREB_.getReqId());
                sbuilder.append(" has exceeded the End Date.");
                if (objPREB_.getRetryCnt() > 0) {
                    sbuilder.append("This may happen because of Retries.");
                }
                String error = sbuilder.toString();
                logger_.log(LogLevel.NOTICE, error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), error);
                return;
            }
            
            if (strOnWeekDays_ == null || strOnWeekDays_.equals(""))
            {
                strOnWeekDays_ = null;
            }
            else if (strOnWeekDays_.equals("0000000"))
            {
                strOnWeekDays_ = null;
            }
            else if (strOnWeekDays_.length() != 7)
            {
                String error= "Schedule#" + objPREB_.getSchId() + " associated with the Request#" + objPREB_.getReqId() + " has invalid \"On Week Days\" specified.";
                logger_.log(LogLevel.NOTICE, error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_
                        .getJobId(), objPREB_.getJobName(), error, objPREB_.getUserId(), null);
                return;
            }
            else
            {
                try
                {
                    Integer.parseInt(strOnWeekDays_);
                }
                catch (NumberFormatException e)
                {
                    String error = "Schedule#" + objPREB_.getSchId() + " associated with the Request#" + objPREB_.getReqId() + " has invalid \"On Week Days\" specified.";
                    logger_.log(LogLevel.NOTICE, error);
                    updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_
                            .getJobId(), objPREB_.getJobName(), error, objPREB_.getUserId(), null);
                    return;
                }
            }
            
            if (frequency_ == FREQUENCY.FIRST_DAY_OF_MONTH ||
                    frequency_ == FREQUENCY.SECOND_DAY_OF_MONTH ||
                    frequency_ == FREQUENCY.THIRD_DAY_OF_MONTH ||
                    frequency_ == FREQUENCY.FOURTH_DAY_OF_MONTH ||
                    frequency_ == FREQUENCY.LAST_DAY_OF_MONTH ||
                    frequency_ == FREQUENCY.FIRST_DAY_OF_YEAR || 
                    frequency_ == FREQUENCY.SECOND_DAY_OF_YEAR ||
                    frequency_ == FREQUENCY.THIRD_DAY_OF_YEAR ||
                    frequency_ == FREQUENCY.FOURTH_DAY_OF_YEAR ||
                    frequency_ == FREQUENCY.LAST_DAY_OF_YEAR){
                if (strOnWeekDays_ == null)
                {
                    String error = "Invalid Schedule associated with the Request#" + objPREB_.getReqId() + ". A day of the week must be selected for frequency [" + frequency_.getDescription() + "]"; 
                    logger_.error(error);
                    updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            error, objPREB_.getUserId(), null);
                    return;
                }
            }
            try {
                skipFlag_ = SKIP_FLAG.resolve(objPRSEB.getSkipFlag());
            } catch (IllegalArgumentException e) {
                String error ="Invalid Schedule associated with the Request#" + objPREB_.getReqId() + ". SKIP FLAG not set correctly."; 
                logger_.error(error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), null);
                return;
            }
            
            switch (skipFlag_) {
                case NOT_APPLICAIBLE:
                    if (logger_.isEnabledFor(LogLevel.FINE)) {
                        logger_.log(LogLevel.FINE, "Skip Flag is not applicable for this schedule");
                    }
                    break;
                case ADVANCE_BEFORE:
                    iSkipFactor_ = -1;
                    break;
                case ADVANCE_AFTER:
                    iSkipFactor_ = 1;
                    break;
                default:
                    break;
            }
            
            if (logger_.isEnabledFor(LogLevel.FINE)) {
                logger_.log(LogLevel.FINE, "Skip Factor is #" + iSkipFactor_);
            }
            
            if (objPRSEB.getWeekdayCheckFlag() == null || objPRSEB.getWeekdayCheckFlag().equals("")) {
                bWeekdayCheckFlag_ = false;
            } else {
                bWeekdayCheckFlag_ = objPRSEB.getWeekdayCheckFlag().equals("Y"); 
            }
            if (frequency_ == FREQUENCY.MINUTE || frequency_ == FREQUENCY.HOUR) {
                if (iSkipFactor_ < 0) {
                    String error="Invalid Schedule associated with the Request#" + objPREB_.getReqId() + ". SKIP FLAG not set correctly for the associated Frequency #" + frequency_.getDescription();
                    logger_.error(error);
                    updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            error, objPREB_.getUserId(), null);
                    return;
                }
            }
            if (frequency_ == FREQUENCY.DAY) {
                if (iSkipFactor_ < 0 && iRecur_ == 1) {
                    String error = "Invalid Schedule associated with the Request#" + objPREB_.getReqId() + ". SKIP FLAG not set correctly for the associated Frequency Type & Recurrance."; 
                    logger_.error(error);
                    updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            error, objPREB_.getUserId(), null);
                    return;
                }
            }
            bPerformBusinessWorkingDayCheck_ = true; // Default to true
            //Changes being made for fixed date......Kedar...Detroit 2006.08.
            daySchRequest_.setFixedDate(objPRSEB.getFixedDate().equals("Y"));
            //fixed date changes done.

            IScheduleValidator objSV = null;
            String strInstantiationError = "";
            try {
                objSV = instantiateScheduleValidator(objPRSEB
                        .getProcessClassNm());
            } catch (ClassNotFoundException cnfe) {
                logger_.fatal("ClassNotFoundException ", cnfe);
                strInstantiationError = exceptionToString(cnfe);
            }
            if (objSV == null)
            {
                String error = "Could Not instantiate Schedule Validator. Scheduling Cancelled as schedule could not be validated."; 
                logger_.log(LogLevel.NOTICE, error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error + " Technical cause :" + strInstantiationError);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                    "Scheduling Cancelled as schedule could not be validated." + strInstantiationError, objPREB_.getUserId(), null);
                return;
            }
            else
            {
                if (logger_.isDebugEnabled()) {
                    logger_.debug("Validating the Schedule#" + objPREB_.getSchId());
                }
                objSV.setPrintWriter(pwRequestLogWriter_);
                objSV.setScheduleBean((ProcessRequestScheduleEntityBean) objPRSEB.clone());
                objSV.setConnection(con_);
                objSV.setProcessRequestEntityBean((ProcessRequestEntityBean)objPREB_.clone());
                objSV.setRequestParameters(hmParameters_);
                objSV.setPREContext(context_);
                
                if (!objSV.validateSchedule(objPRSEB.getSchId()))
                {
                    String message = "Schedule Validator invalidated the schedule#" + objPREB_.getSchId(); 
                    logger_.log(LogLevel.NOTICE, message);
                    updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, message);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            message, objPREB_.getUserId(), null);
                    return;
                }
            }
            IScheduleEvent objScheduleEvent = null;
            if (objSV instanceof IScheduleEvent)
            {
                objScheduleEvent = (IScheduleEvent) objSV;
                try
                {
                    if (logger_.isDebugEnabled()) {
                        logger_.debug("Calling the PRE-Event associated with this Schedule#" + objPREB_.getSchId());
                    }
                    objScheduleEvent.setConnection(con_);
                    bprocess = objScheduleEvent.performPreAction(
                        objPREB_.getReqId(),
                        lSchId_);
                    if (logger_.isDebugEnabled()) {
                        logger_.debug("Executed the PRE-Event associated with this Schedule#" + objPREB_.getSchId());
                    }
                    if (!bprocess){
                        logger_.fatal("Pre-Event returned false. Terminating the schedule#" + objPREB_.getSchId());
                    }
                }
                catch (RuntimeException re)
                {
                    logger_.fatal("PRE-Event on the Schedule raised a Runtime Exception.", re);
                    bprocess = true;
                }
                catch (Exception e)
                {
                    logger_.fatal("PRE-Event on the Schedule raised an Exception", e);
                    strExtraMessageToBeMailed_ = "PRE-Event on the Schedule raised an Exception. " + NEW_LINE + exceptionToString(e);
                }
            }
            
            if (objSV instanceof IPreDefinedFrequency){
                objPreDefinedFrequency_ = (IPreDefinedFrequency) objSV;
            }

            if (bprocess) {
                bprocess = advanceDay(daySchRequest_, false, null);
                if (bprocess) {
                    int iSkipFlagApplyCounter = 0;
                    if (skipFlag_ == SKIP_FLAG.NOT_APPLICAIBLE) {
                        //skip factor is NA
                        dayActualRequestSchTime_.setDay(daySchRequest_); // Equal to scheduled date and time.
                    } else { // Skip flag is one of either D+ or D- or SS.
                        if (iSkipFactor_ != 0) {
                            //The calendar scheduled date has been advanced now it is time to apply the SKIP Flag.
                            dayActualRequestSchTime_.setDay(daySchRequest_); // This should be used to determine the parameter difference.
                            //Also the above variable is the actual scheduled time when the JOB will be executed.
                            boolean bAtLeastOneCheckIsApplied = true;
                            while (bAtLeastOneCheckIsApplied) {
                                iSkipFlagApplyCounter++;
                                //The schedule has been advanced appropriately so need not loop again unless this day
                                //is a non business working day. So make the variable to false.
                                bAtLeastOneCheckIsApplied = false;
                                
                                if (bWeekdayCheckFlag_) {
                                    applyWeekdayCheck(dayActualRequestSchTime_);
                                }
                                
                                if (bPerformBusinessWorkingDayCheck_) {
                                    boolean bCalendarWorkDayCheck;
                                    try {
                                        bCalendarWorkDayCheck = performCalendarWorkDayCheck(dayActualRequestSchTime_);
                                    } catch (ClassNotFoundException cnfe) {
                                        bprocess = false;
                                        logger_.error("Class could not be found.", cnfe);
                                        strExtraMessageToBeMailed_ = "Class could not be found.\n\n" + exceptionToString(cnfe);
                                        break;
                                    } catch (SQLException sqle) {
                                        bprocess = false;
                                        logger_.error("SQLException .", sqle);
                                        strExtraMessageToBeMailed_ = "SQLException thrown.\n\n" + exceptionToString(sqle);
                                        break;
                                    } catch (CDynamicDataContainerException cde) {
                                        bprocess = false;
                                        logger_.error("CDynamicDataContainerException.", cde);
                                        strExtraMessageToBeMailed_ = "CDynamicDataContainerException thrown.\n\n" + exceptionToString(cde);
                                        break;
                                    } catch (CBeanException cbe) {
                                        bprocess = false;
                                        logger_.error("CBeanException.", cbe);
                                        strExtraMessageToBeMailed_ = "CBeanException thrown.\n\n" + exceptionToString(cbe);
                                        break;
                                    }
                                    if (!bCalendarWorkDayCheck) {
                                        dayActualRequestSchTime_.advance(1 * iSkipFactor_);
                                        bAtLeastOneCheckIsApplied = true;
                                    } else {
                                        //this day is a business working day and therefore need not be looped again.
                                    }
                                } // business working day check
                                
                                // The following condition is to check if the schedule needs to be terminated.
                                if (dayActualRequestSchTime_.compareInSeconds(tsCalendarScheduleDate_) <= 0) {
                                    logger_.error("Weekday Check + Calendar Work Day check + Skip Flag not appropriate to schedule further requests.   Actual Request Sch Time [" + dayActualRequestSchTime_ +"] v/s Calendar [" + new Day(tsCalendarScheduleDate_) + "] + Skip Flag Counter [" + iSkipFlagApplyCounter + "]");
                                    strExtraMessageToBeMailed_ = "Weekday Check + Calendar Work Day check + Skip Flag not appropriate to schedule further requests.";
                                    bprocess = false;
                                    bAtLeastOneCheckIsApplied = false; //to come out of the loop.
                                } else {
                                    if (iSkipFlagApplyCounter > iICalendarInfiniteLoopTerminationCounter) {
                                        bprocess = false;
                                        bAtLeastOneCheckIsApplied = false; //to come out of the loop.
                                        strExtraMessageToBeMailed_ = "Possible infinite loop. Terminating the schedule. " +
                                        "Please check the business logic implemented in the schedule event class(es). "; // + objPRSEB.getCalendarClassNm(); 
                                        logger_.error(strExtraMessageToBeMailed_);
                                    }
                                    // Let us assume that the actual request scheduled time is advanced such that it reaches or crosses the
                                    // next recurrance of schedule. Even then it is okay. The advanceDay() method should take care of such 
                                    // situations and it should advance the schedule till the calendar scheduled date time.
                                }
                            } // while true
                        } else {  // if skipfactor != 0. This indicates that D+ or D- is not specified. Check for SS.
                            if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                                Day dayScheduledSkippedTo = (Day) daySchRequest_.clone();
                                boolean bAtLeastOneCheckIsApplied = true;
                                while (bAtLeastOneCheckIsApplied) {
                                    //The schedule has been advanced appropriately so need not loop again unless this day
                                    //is a non business working day. So make the variable to false.
                                    bAtLeastOneCheckIsApplied = false;
                                    
                                    if (bWeekdayCheckFlag_) {
                                        if (dayScheduledSkippedTo.weekday() == Calendar.SUNDAY || dayScheduledSkippedTo.weekday() == Calendar.SATURDAY) {
                                            bprocess = advanceDay(dayScheduledSkippedTo, false, null);
                                            iSkipFlagApplyCounter++;
                                            bAtLeastOneCheckIsApplied = true;
                                        }
                                    }
                                    
                                    if (bPerformBusinessWorkingDayCheck_) {
                                        boolean bCalendarWorkDayCheck;
                                        try {
                                            bCalendarWorkDayCheck = performCalendarWorkDayCheck(dayScheduledSkippedTo);
                                        } catch (ClassNotFoundException cnfe) {
                                            bprocess = false;
                                            logger_.error("Class could not be found.", cnfe);
                                            strExtraMessageToBeMailed_ = "Class could not be found.\n\n" + exceptionToString(cnfe);
                                            break;
                                        } catch (SQLException sqle) {
                                            bprocess = false;
                                            logger_.error("SQLException .", sqle);
                                            strExtraMessageToBeMailed_ = "SQLException thrown.\n\n" + exceptionToString(sqle);
                                            break;
                                        }
                                        if (!bCalendarWorkDayCheck) {
                                            bprocess = advanceDay(dayScheduledSkippedTo, false, null);
                                            bAtLeastOneCheckIsApplied = true;
                                            iSkipFlagApplyCounter++;
//                                            lOccurCounter++; // as we have advanced the schedule.
                                        } else {
                                            //this day is a business working day and therefore need not be looped again.
                                            // bAtLeastOneCheckIsApplied is false therefore it will not loop.
                                        }
                                    } // business working day check
                                    
                                    // The following condition is to check if the schedule needs to be terminated.
                                    if (bprocess) {
                                        if (iSkipFlagApplyCounter > iICalendarInfiniteLoopTerminationCounter) {
                                            bprocess = false;
                                            bAtLeastOneCheckIsApplied = false; //to come out of the loop.
                                            strExtraMessageToBeMailed_ = "Possible infinite loop. Terminating the schedule. " +
                                            "Please check the business logic implemented in the schedule event class(es) "; // + objPRSEB.getCalendarClassNm(); 
                                            logger_.error(strExtraMessageToBeMailed_);
                                        }
                                    }
                                } // while true
                                dayActualRequestSchTime_.setDay(dayScheduledSkippedTo);
                            } // end of skipFlag = SS
                        } // end of iskipFactor == 0 
                    } // end of strSkipFlag != NA
                } // If after advancing is the bProcess true
            } //bprocess == true
            if (!bprocess)
            {
                doRollBack();
                setAutoCommit(false); //updateStatus(...) method sets the autocommit(true)
                String error = "Schedule could not be advanced. Schedule #" + objPREB_.getSchId() + " Cancelled."; 
                logger_.log(LogLevel.NOTICE, error);
                updateStatus(objPRSC, objPRSEB, SCHEDULE_STATUS.TERMINATED, error);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        error, objPREB_.getUserId(), null);
                return;
            }
            if (endOn == END_ON.OCCURRENCES
                    && lEndOccur < lOccurCounter)
            {
                logger_.log(LogLevel.NOTICE, "The schedule associated with this request has been marked as completed. Schedule Id#" + objPREB_.getSchId());
                if (logger_.isInfoEnabled()){
                    logger_.info("Most likely this is an invalid schedule where recurrence factor is not proper OR is due to the skip flag.");
                    
                }
                bprocess = false;
//                objPRSEB.setOccurCounter(lOccurCounter);
                objPRSEB.setSchStat(SCHEDULE_STATUS.COMPLETED.getID());
                strExtraMessageToBeMailed_ = "Most likely this is an invalid schedule where recurrence is not properly set OR the schedule does not recur." +
                    " The schedule is to be associated only if there is a recurrence factor to the task requested.\n\nOR\n\n" +
                    "This can also happen due to SKIP Flag asscociated with the schedule. After applying the skip flag the schedule crossed the end occurance factor " +
                    "and thus was marked as completed. OR "; 
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        "The schedule associated with this request has " +
                        "been marked as completed. Schedule Id#" + objPREB_.getSchId(),
                        objPREB_.getUserId(), null
                );
            } else if (endOn == END_ON.DATE) {
                if (daySchRequest_.compareInSeconds(dayEnd) > 0d) { // || dayActualRequestSchTime_.compareInSeconds(dayEnd) >= 0) {
                    bprocess = false;
//                    objPRSEB.setOccurCounter(lOccurCounter);
                    strExtraMessageToBeMailed_ = "Most likely this is an invalid schedule where recurrence is not properly set OR the schedule does not recur." +
                    " The schedule is to be associated only if there is a recurrence factor to the task requested.\n\nOR\n\n" +
                    "This can also happen due to SKIP Flag asscociated with the schedule. After applying the skip flag the schedule crossed the end date " +
                    "and thus was marked as completed."; 
                    if (logger_.isInfoEnabled()){
                        logger_.info("Most likely this is an invalid schedule where recurrence factor is not proper OR is due to the skip flag.");
                        
                    }
                    objPRSEB.setSchStat(SCHEDULE_STATUS.COMPLETED.getID());
                    objPRSEB.setEndReason(strExtraMessageToBeMailed_);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            "The schedule associated with this request has " +
                            "been marked as completed. Schedule Id#" + objPREB_.getSchId(),
                            objPREB_.getUserId(), null
                    );
                }
            }
            if (endOn == END_ON.DATE 
                    && dayEnd.compareInSeconds(dayActualRequestSchTime_) < 0d && skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                bprocess=false;
                logger_.log(LogLevel.NOTICE, "The schedule associated with this request has been marked as completed. Schedule Id#" + objPREB_.getSchId());
                strExtraMessageToBeMailed_ = "The scheduled could not be advanced as the schedule has skipped to " + 
                dayActualRequestSchTime_.toString() + " which, is beyond it's end date" + dayEnd.toString() + 
                "(" + dateTimeFormat_.toPattern() + ").";
                logger_.log(LogLevel.NOTICE, strExtraMessageToBeMailed_);
                objPRSEB.setSchStat(SCHEDULE_STATUS.COMPLETED.getID());
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        "The schedule associated with this request has " +
                        "been marked as completed. Schedule Id#" + objPREB_.getSchId(), 
                        objPREB_.getUserId(), null
                );
            }

            if (bprocess)
            {
                if (insertRequest(objPREB_,
                    dayActualRequestSchTime_,
                    daySchRequest_,
                    dayReqProcessEndDate_))
                {
                    if (objScheduleEvent != null)
                    {
                        try
                        {
                            if (logger_.isDebugEnabled()) {
                                logger_.debug("Calling the POST-Event associated with this Schedule#" + objPREB_.getSchId());
                            }
                            bprocess = objScheduleEvent.performPostAction(lNewRequestId_);
                            if (logger_.isDebugEnabled()) {
                                logger_.debug("Executed the POST-Event associated with this Schedule#" + objPREB_.getSchId());
                            }
                        }
                        catch (RuntimeException re)
                        {
                            logger_.fatal("POST-Event on the Schedule raised a Runtime Exception", re);
                            strExtraMessageToBeMailed_ = "POST-Event on the Schedule raised a Runtime Exception." + NEW_LINE + exceptionToString(re);
                        }
                        catch (Exception e)
                        {
                            logger_.fatal("POST-Event on the Schedule raised an Exception", e);
                            strExtraMessageToBeMailed_ = "POST-Event on the Schedule raised an Exception" + NEW_LINE + exceptionToString(e);
                        }
                        if (!bprocess){
                            logger_.fatal("Post-Event returned false. Terminating the schedule");
                            bprocess = false;
                            doRollBack();
                            setAutoCommit(false);
                            objPRSEB.setSchStat(SCHEDULE_STATUS.TERMINATED.getID());
                            objPRSEB.setEndReason(strExtraMessageToBeMailed_);
                        }
                    }
                    if (bprocess) {
                        lOccurCounter++;
                        objPRSEB.setOccurCounter(lOccurCounter);
                    }
                } //pro-created job
                else
                {
                    doRollBack();
                    setAutoCommit(false);
                    if (logger_.isDebugEnabled()) {
                        logger_.debug("Could not Pro-Create JOB. Insert method returned FALSE.");
                    }
                    objPRSEB.setSchStat(SCHEDULE_STATUS.TERMINATED.getID());
                }
            } //to be or not to be processed 

            if (bprocess)
            {
                if (endOn == END_ON.OCCURRENCES
                        && lEndOccur <= lOccurCounter)  // This is because of SKIP Flag SS. The originial condition is lEndOccur == lOccurCounter
                {
                    logger_.log(LogLevel.NOTICE, "The schedule associated with this request has been marked as completed. Schedule Id#" + objPREB_.getSchId());
                    strExtraMessageToBeMailed_ = "Please note a queued request has been placed for its last occurrence as of calendar date " + 
                    daySchRequest_.toString() + " that will be executed on "+ dayActualRequestSchTime_.toString() + 
                    "(" + dateTimeFormat_.toPattern() + "). This notification is sent out upon the penultimate execution of this schedule.";
                    objPRSEB.setSchStat(SCHEDULE_STATUS.COMPLETED.getID());
                    objPRSEB.setEndReason(strExtraMessageToBeMailed_);
                    logger_.log(LogLevel.NOTICE, strExtraMessageToBeMailed_);
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            "The schedule associated with this request has " +
                            "been marked as completed. Schedule Id#" + objPREB_.getSchId(),
                            objPREB_.getUserId(), null
                    );
                }
                else if (
                        endOn == END_ON.DATE
                        && dayEnd.compareInSeconds(daySchRequest_) <= 0d)
                {
                    logger_.log(LogLevel.NOTICE, "The schedule associated with this request has been marked as completed. Schedule Id#" + objPREB_.getSchId());
                    strExtraMessageToBeMailed_ = "Please note a queued request has been placed for its last occurrence as of calendar date " + 
                    daySchRequest_.toString() + " that will be executed on " + dayActualRequestSchTime_.toString() + 
                    "(" + dateTimeFormat_.toPattern() + "). This e-mail is sent out upon the penultimate execution of this schedule.";
                    
                    objPRSEB.setSchStat(SCHEDULE_STATUS.COMPLETED.getID());
                    objPRSEB.setEndReason(strExtraMessageToBeMailed_);
                   
                    mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                            "The schedule associated with this request has " +
                            "been marked as completed. Schedule Id#" + objPREB_.getSchId(), 
                            objPREB_.getUserId(), null
                    );
                }
            } //if (bprocess) validate for occurances# or end date.

            try
            {
                objPRSC.update(objPRSEB);
                con_.commit();
                setAutoCommit(true);
            }
            catch (Exception e)
            {
                doRollBack();
                logger_.error("While Updating the schedule while commit."
                        + objPRSEB.getSchId(), e);
                mail(objPREB_.getReqId(), objPREB_.getSchId(), objPREB_.getJobId(), objPREB_.getJobName(),
                        "While Updating the schedule "
                        + objPRSEB.getSchId() + "\n\n" + exceptionToString(e), objPREB_.getUserId(), null);
            } finally {
            }

        }
        catch (Throwable e)
        {
            logger_.error("Caught Exception while scheduling ", e);
            doRollBack();
        }
        finally
        {
            if (objPRSC != null)
            {
                try {
                    objPRSC.close();
                } catch (SQLException e) {
                    logger_.error("SQLException PRSController", e);
                }
            }
            try {
                if (!con_.getAutoCommit()) {
                    setAutoCommit(true);
                }
            } catch (SQLException e) {
                //do nothgin
            }
            if (conToBeClosed) {
                try {
                    con_.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return;
    }

    /**
     * Returns the the stack trace of an throwable in a String format.
     * 
     * @param throwable
     * @return String 
     */
    private String exceptionToString(Throwable throwable) {
        bytes_.reset();
        throwable.printStackTrace(pwException_);
        return bytes_.toString();
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.IRequestIdGenerator#setConnection(java.sql.Connection)
     */
    public void setConnection(Connection pcon)
    {
        if (pcon != null) {
            try {
                if (!pcon.isClosed()) {
                    con_ = pcon;
                } else {
                    conToBeClosed = true;
                    con_ = dataSourceFactory.getDataSource(CSettings
                            .get("pr.dsforstandaloneeng")).getConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (con_ != null && !con_.isClosed()) {
                setAutoCommit(false);
            }
        } catch (SQLException e) {
            con_ = null; //FIXME Add the datasource implementation.
        }
    }

    /**
     * Method generates a Request Id. Default Implementation.
     *  
     * This method will execute an SQL query to generate the next sequence number. The query is picked from the 
     * properties file based on the property <code>pr.<JDBC ConnectionType>.sequence.sql</code>. For example, if
     * the JDBC connection type is of ORACLE then the property <code>pr.ORACLE.sequency.sql</code> will be used 
     * to get the query.  
     * 
     * This method should return a unique non-used value. If the query does not return any value nor throws an SQLException
     * then the method returns Zero value. if the 
     * 
     * @return Request Id
     * @throws SQLException
     * @see stg.pr.engine.scheduler.IRequestIdGenerator#generateRequestId()
     */
    public long generateRequestId() throws SQLException
    {
        Statement st = null;
        ResultSet rs = null;
        long lReqId = 0L;
        try
        {
            String sql = CSettings.get("pr." + CDB.getDBType(con_) + ".sequence.sql");
            if (sql == null || "".equals(sql)){
                throw new SQLException("Sequency Generation Query not defined in the pr.properties for JDBC Connection type #" + CDB.getDBType(con_));
            }
            st = con_.createStatement();
            rs =
                st.executeQuery(sql);
            if (rs.next())
            {
                lReqId = rs.getLong(1);
            }
        } 
        catch (SQLException e)
        {
            logger_.error("Exception", e);
            strExtraMessageToBeMailed_ = exceptionToString(e);
            throw e;
        } catch (CDBException e) {
            logger_.error("Unknown Database Type.", e);
            strExtraMessageToBeMailed_ = exceptionToString(e);
            e.printStackTrace();
            throw new SQLException ("Unknown Database Type");
        }
        finally
        {
            try
            {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                //dummy catch
            }
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                // dummy catch
            }
        }
        return (lReqId);
    }

    /** 
     * Returns a list of classes that need to be loaded by the system classloader
     *
     *   @return  a String array with the classes that need to be loaded by the System classloader
     */
    public String[] getSystemLoadedClasses()
    {
        StringTokenizer stz =
            new StringTokenizer(CSettings.get("pr.systemloadedclasses"), ";");
        String[] saSystemLoadedClasses = new String[stz.countTokens()];
        int iElement = 0;

        while (stz.hasMoreTokens())
        {
            saSystemLoadedClasses[iElement] = stz.nextToken();
            iElement++;
        }

        return saSystemLoadedClasses;
    }

    /** Returns the path from where the class loader will load the classes
    *
    *   @return  a String with the path where the classes can be found
    */
    public String getCustomClassLoaderClassPath()
    {
        return CSettings.get("pr.objclasspathforclassloader");
    }

    /** Indicates whether the custom classloader has to reload classes or not
    *
    *   @return a boolean indicating whether the custom classloader has to reload classes or not
    */
    public boolean isReload()
    {
        return (
            ((CSettings.get("pr.reloadobjclasses").equals("Y"))
                ? true
                : false));
    }

    /**
    * Default Implimentation of IScheduleValidator.
    * @return boolean Returns TRUE always
    */
    public boolean validateSchedule(long plong)
    {
        return (true);
    }

    //protected constructors and methods of the class

    //package constructors and methods of the class

    //private constructors and methods of the class

    /**
     * Duplicates the Request scheduled for with a new Request Id.
     *
     * Throws RuntimeException, if any. The insert transaction is rolledback.
     * 
     * @param   pobjPREB  ProcessRequestEntityBean object
     * @param   pday  Schedule Time
     * @param   pCalDay  Calendar Schedule Time
     * @param   pdayRequest  Requested Time
     * @return     boolean True on successful insert otherwise False
     */
    private boolean insertRequest(
        ProcessRequestEntityBean pobjPREB,
        Day pday,
        Day pCalDay,
        Day pdayRequest)
    {
        ProcessRequestEntityBean objPREB = (ProcessRequestEntityBean) pobjPREB.clone();
        objPREB.setRStatus(false);
        long lOldReqId = 0L;
        boolean returnValue = false;
        lOldReqId = pobjPREB.getReqId();
        IRequestIdGenerator objRIG = null;
        try {
            objRIG = instantiateReqIdGenerator(
                CSettings.get("pr.schedulerequestidgenerator"));
        } catch (ClassNotFoundException e2) {
            logger_.fatal("Could Not Instantiate Request ID Generator", e2);
        }
        if (objRIG == null)
        {
            return false;
        }
        else
        {
            objRIG.setConnection(con_);
            try {
                lNewRequestId_ = objRIG.generateRequestId();
            } catch (SQLException e1) {
                logger_.fatal("Error Occurred while generating next Request Id", e1);
                logger_.log(LogLevel.NOTICE, "Schedule is being terminated.");
                strExtraMessageToBeMailed_ = "Error Occurred while generating next Request Id" + NEW_LINE + exceptionToString(e1);
                return false;
            }
            if (lNewRequestId_ <= 0)
            {
                logger_.fatal("Request ID Generator Generated Id#" + lNewRequestId_);
                strExtraMessageToBeMailed_ = "Error Occurred while generating next Request Id. Request Id is <= 0";
                return false;
            }
            if (lNewRequestId_ == lOldReqId) {
                logger_.fatal("Old Request ID# " + lOldReqId + " equals New Generated Id#" + lNewRequestId_);
                strExtraMessageToBeMailed_ = "Error Occurred while generating next Request Id. Old Request Id == New Request Id";
                return false;
            }
        }
        objPREB.setReqId(lNewRequestId_);
        objPREB.setReqDt(pdayRequest.getTimestamp());
        objPREB.setReqStat(REQUEST_STATUS.QUEUED.getID());
        objPREB.setScheduledTime(pday.getTimestamp());
        //set null values as the object is cloned.
        objPREB.setReqStartDt(null);
        objPREB.setReqEndDt(null);
        objPREB.setReqLogfileNm(null);
        objPREB.setRetryCnt(0);
        if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
            objPREB.setCalScheduledTime(pday.getTimestamp());
        } else {
            objPREB.setCalScheduledTime(pCalDay.getTimestamp());
        }
        ProcessRequestController objPRC = null;
        ProcessReqParamsController objPRPC = null;
        try
        {
            con_.setAutoCommit(false);
            objPRC = new ProcessRequestController(con_);
            if (objPRC.create(objPREB))
            {
                objPRPC =
                    new ProcessReqParamsController(con_);
                ProcessReqParamsEntityBean objPRPEB =
                    new ProcessReqParamsEntityBean();
                objPRPEB.setReqId(lOldReqId);
                CDynamicDataContainer objCdc = new CDynamicDataContainer();
                objCdc.build(con_, objPRPEB);
                objCdc.executeQuery(con_, objPRPC, objPRPEB);
                objPRPEB = null;
                while (objCdc.next())
                {
                    ProcessReqParamsEntityBean objNewScheduleParamBean = (ProcessReqParamsEntityBean) objCdc.get();
                    if (logger_.isDebugEnabled()) {
                        logger_.debug("Field Name: " + objNewScheduleParamBean
                                .getParamFld() + " Field Datatype: " + objNewScheduleParamBean
                                .getParamDataType() + " Field Value# " + objNewScheduleParamBean
                                .getParamVal());
                    }
                    PREDataType dataType = PREDataType.resolve(objNewScheduleParamBean.getParamDataType()); 
                    if (dataType == PREDataType.DATE
                        || dataType == PREDataType.TIMESTAMP
                        || dataType == PREDataType.DATE_ARRAY
                        || dataType == PREDataType.TIMESTAMP_ARRAY)
                    {
                        if (objNewScheduleParamBean
                            .getStaticDynamicFlag()
                            .equals(ISchedule.DYNAMIC_INDICATOR))
                        {
                            objNewScheduleParamBean.setParamVal(
                                convertParamValue(
                                        objNewScheduleParamBean.getParamVal(),
                                        dataType, objNewScheduleParamBean.getParamFld()));
                            if (logger_.isDebugEnabled()) {
                                logger_.debug("New Value: " + objNewScheduleParamBean.getParamVal());
                            }
                        }
                    }
                    objNewScheduleParamBean.setReqId(lNewRequestId_);
                    objPRPC.create(objNewScheduleParamBean);
                    objNewScheduleParamBean = null;
                }
                returnValue = true;
            }
        }
        catch (Throwable e)
        {
            logger_.error("Exception", e);
            strExtraMessageToBeMailed_ = exceptionToString(e);
            doRollBack();
        }
        finally
        {
            try {
                if (objPRC != null) {
                    objPRC.close();
                }
            } catch (SQLException e) {
                //dummy catch
            }
            try {
                if (objPRPC != null) {
                    objPRPC.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return (returnValue);
    }

    /**
     * Instantiates the IScheduleValidator.
     * This class uses the CustomClassLoader to load the class.
     * 
     * @param   pstrClassName  The name of the class which implements IScheduleValidator
     * @return  IScheduleValidator An object of the class passed in <i>pstrClassName</i> parameter.
     * @throws ClassNotFoundException 
     */
    private IScheduleValidator instantiateScheduleValidator(String pstrClassName) throws ClassNotFoundException
    {

        IScheduleValidator objProcessRequest = null;
        if (pstrClassName == null || pstrClassName.equals(""))
        {
            return (objProcessRequest);
        }
        else if (
                pstrClassName.equals("stg.pr.engine.scheduler.CRequestScheduler"))
        {
            return (this);
        }

        try
        {

            ClassLoader pcl =
                (CCustomClassLoaderFactory.getInstance()).getClassLoader(this);

            Class<?> objClass = pcl.loadClass(pstrClassName);

            if (objClass == null) {
                throw new ClassNotFoundException("Unable to load the class.");
            }
            
            Object obj = objClass.newInstance();

            if (obj instanceof IScheduleValidator)
            {
                objProcessRequest = (IScheduleValidator) obj;
            }
            else
            {
                logger_.error("The business object in the request does not implement IScheduleValidator.");
                strExtraMessageToBeMailed_ = "The business object in the request does not implement IScheduleValidator.";
            }

        }
        catch (ClassNotFoundException cnfe) {
            strExtraMessageToBeMailed_ = "Class not found # " + pstrClassName + NEW_LINE + exceptionToString(cnfe);
            throw cnfe;
        }
        catch (Exception e)
        {
            logger_.error("Exception", e);
            strExtraMessageToBeMailed_ = "Exception Raised while loading class # " + pstrClassName + NEW_LINE + exceptionToString(e);
        } finally
        {

        }

        return objProcessRequest;
    }

    /**
     * Instantiates the ICalendar.
     * 
     * This class uses the CustomClassLoader to load the class.
     * 
     * @param   pstrClassName  The name of the class which implements ICalendar interface
     * @return  ICalendar An object of the class passed in <i>pstrClassName</i> parameter.
     * @throws ClassNotFoundException 
     */
    private ICalendar instantiateCalendar(String pstrClassName) throws ClassNotFoundException
    {
        ICalendar objICalendar = null;
        try
        {
            
            ClassLoader pcl =
                (CCustomClassLoaderFactory.getInstance()).getClassLoader(this);
            
            Class<?> objClass = pcl.loadClass(pstrClassName);
            
            if (objClass == null) {
                throw new ClassNotFoundException("Unable to load the class.");
            }
            
            Object obj = objClass.newInstance();
            
            if (obj instanceof ICalendar)
            {
                objICalendar = (ICalendar) obj;
            }
            else
            {
                strExtraMessageToBeMailed_ = "The business object " + pstrClassName + " does not implement stg.pr.engine.scheduler.ICalendar.";
                logger_.error(strExtraMessageToBeMailed_);
            }
            
        }
        catch (ClassNotFoundException cnfe) {
            strExtraMessageToBeMailed_ = "Class not found # " + pstrClassName + NEW_LINE + exceptionToString(cnfe);
            throw cnfe;
        }
        catch (Exception e)
        {
            logger_.error("Exception", e);
            strExtraMessageToBeMailed_ = "Exception Raised while loading class # " + pstrClassName + NEW_LINE + exceptionToString(e);
        } finally
        {
            
        }
        
        return objICalendar;
    }
    
    /**
     * Instantiates the RequestIdGenerator.
     * This class uses the CustomeClassLoader to load the class.
     * 
     * @param   pstrClassName  The name of the class which implements IRequestIdGenerator
     * @return     IRequestIdGenerator An object of the class passed in <i>pstrClassName</i> parameter.
     * @throws ClassNotFoundException 
     */
    public IRequestIdGenerator instantiateReqIdGenerator(String pstrClassName) throws ClassNotFoundException
    {
        IRequestIdGenerator objProcessRequest = null;

        if (pstrClassName == null || pstrClassName.equals(""))
        {
            return (objProcessRequest);
        }
        else if (
            pstrClassName.equals("stg.pr.engine.scheduler.CRequestScheduler"))
        {
            return (this);
        }

        try
        {

            ClassLoader pcl =
                (CCustomClassLoaderFactory.getInstance()).getClassLoader(this);

            Class<?> objClass = pcl.loadClass(pstrClassName);

            Object obj = objClass.newInstance();

            if (obj instanceof IRequestIdGenerator)
            {
                objProcessRequest = (IRequestIdGenerator) obj;
            }
            else
            {
                strExtraMessageToBeMailed_  = "The business object in the request does not implement stg.pr.engine.scheduler.IRequestIdGenerator";
                logger_.error(strExtraMessageToBeMailed_);
            }

        }
        catch (ClassNotFoundException cnfe){
            strExtraMessageToBeMailed_ = "Class not found #" + pstrClassName + NEW_LINE + exceptionToString(cnfe);
            throw cnfe;
        }
        catch (Exception e)
        {
            logger_.error(e.getMessage(), e);
            strExtraMessageToBeMailed_ = "Exception Raised while loading class # " + pstrClassName + NEW_LINE + exceptionToString(e);
        }
        finally
        {

        }

        return objProcessRequest;
    }

    /**
     * Updates the desired status for the schedule.
     *
     * @param   objPRSC ProcessRequestScheduleController
     * @param   objPRSEB  ProcessRequestScheduleEntityBean
     * @param   status  {@link ISchedule.SCHEDULE_STATUS}
     */
    private void updateStatus(
        ProcessRequestScheduleController objPRSC,
        ProcessRequestScheduleEntityBean objPRSEB,
        SCHEDULE_STATUS status,
        String message)
    {
        try
        {
            objPRSEB.setSchStat(status.getID());
            objPRSEB.setEndReason(message);
            objPRSC.update(objPRSEB);
            con_.commit();
            setAutoCommit(true);
        }
        catch (Exception e)
        {
            String str = "Unable to change the status of the Schedule#"
                + objPRSEB.getSchId()
                + " to " + status.getDescription();
            logger_.fatal(str, e);
            strExtraMessageToBeMailed_ = str + NEW_LINE + exceptionToString(e);
        } finally {
        }
        return;
    }

    /**
     * Checks for the schedule start time and end time and advances the schedule time appropriately.
     * 
     * @param pdaySchedule  Schedule Date
     * @param ptsStartTime  Start Time of the schedule within a given period.
     * @param ptsEndTime    End Time of the schedule within a given period.
     */
    private void checkStartEndTime(
        Day pdaySchedule,
        Timestamp ptsStartTime,
        Timestamp ptsEndTime)
    {
        if (ptsStartTime == null || ptsEndTime == null)
        {
            return;
        }
        int iScheduleTime = 0;
        int iStartTime = 0;
        int iEndTime = 0;
        int iStartHour = 0;
        int iStartMinutes = 0;
        int iStartSeconds = 0;
        iScheduleTime = pdaySchedule.getHour() * 10000 + pdaySchedule.getMinutes() * 100 + pdaySchedule.getSeconds();
        Day dayStartTime = new Day(ptsStartTime);
        Day dayEndTime = new Day(ptsEndTime);
        iStartTime = dayStartTime.getHour() *10000 + dayStartTime.getMinutes() * 100 + dayStartTime.getSeconds(); 
        iEndTime = dayEndTime.getHour() * 10000 + dayEndTime.getMinutes() * 100 + dayEndTime.getSeconds();
        iStartHour = dayStartTime.getHour();
        iStartMinutes = dayStartTime.getMinutes();
        iStartSeconds = dayStartTime.getSeconds();

        if (iStartTime <= iEndTime) // Normal condition where start time is less than end time.
        {
            if (iScheduleTime < iStartTime)
            {
                int iYear = pdaySchedule.getYear();
                int iMonth = pdaySchedule.getMonth();
                int iDay = pdaySchedule.getDay();
                pdaySchedule.setDay(
                    iYear,
                    iMonth,
                    iDay,
                    iStartHour,
                    iStartMinutes,
                    iStartSeconds);
            }
            else if (iScheduleTime > iEndTime)
            {
                pdaySchedule.advance(1);
                int iYear = pdaySchedule.getYear();
                int iMonth = pdaySchedule.getMonth();
                int iDay = pdaySchedule.getDay();
                pdaySchedule.setDay(
                    iYear,
                    iMonth,
                    iDay,
                    iStartHour,
                    iStartMinutes,
                    iStartSeconds);
            }
        }
        else    //Abnormal condition where start time is greater than end time.
        {
            if (iScheduleTime > iEndTime && iScheduleTime < iStartTime)
            {
//              This line was commented as if the scheduled date is greater than the End time and less than 
//              the start time then the day is already advanced. Therefore the day need not be advanced again.
//              Just set the start time accordingly. E.g. Schedule is recur every 30 minutes from jan/01 till
//              jan/31 starting from 23:00 till 01:00 then the jan/01 23:00, jan/01 23:30, jan/02 00:00...where
//              the date is already advanced so once this schedule reaches jan/02 01:00 it should be scheduled
//              at jan/02 23:00 and not jan/03 23:00.
//              pdaySchedule.advance(1);           //Kedar .....Detroit...Jan 30 2007.
                int iYear = pdaySchedule.getYear();
                int iMonth = pdaySchedule.getMonth();
                int iDay = pdaySchedule.getDay();
                pdaySchedule.setDay(
                    iYear,
                    iMonth,
                    iDay,
                    iStartHour,
                    iStartMinutes,
                    iStartSeconds);
            }
        }
    }

    /**
     * Checks for the On Week Day value and advances the schedule date appropriately.
     * 
     * @param pstrOnWeekDays      Week Day scheduled for. 
     * @param pdaySchRequest    Scheduled date
     * @param piRecur   Recurrence factor.
     * @param pbCurrentDayIsValid Is current day valid.
     * @return boolean
     */
    private boolean scheduleOnWeekday(
        String pstrOnWeekDays,
        Day pdaySchRequest,
        int piRecur,
        boolean pbCurrentDayIsValid)
    {
        if (frequency_ != FREQUENCY.WEEK)
        {
//            In case of minute and hour frequency the advanceDay() has already advanced the day.
//            Therefore simply check whether this day is available in the desired days of the week.
//            If yes return true. Otherwise advance it till the desired day is reached.
            int iCharAt = pdaySchRequest.weekday()-1;
            while (true)
            {
                if (pstrOnWeekDays.charAt(iCharAt) == '1')
                {
                    return true;
                }
                else
                {
                    pdaySchRequest.advance(1);
                }
                iCharAt++;
                if (iCharAt > 6 )
                {
                    iCharAt = 0;
                }
            }
        }
        else
        {
//            In case of WEEK fequency the advanceDay has not advanced the week till this method returns
//            false. The pdaySchRequest has already been executed and thus check only for the furture days
//            in this current week. If found the desired day in this week advance the schedule till that
//            day and return true. Otherwise the advance day will advance the frequency for its recurrence.
//            and will call this routine again by initializing the day to the beginging of the week.
            int iDaysToBeAdvanced = 0;
            int iStartFromDay = 0;
            if (pbCurrentDayIsValid)
            {
                iStartFromDay = pdaySchRequest.weekday() -1;
            }
            else
            {
                iStartFromDay = pdaySchRequest.weekday();
                iDaysToBeAdvanced = 1;
            }            
            for (int i = iStartFromDay; i < pstrOnWeekDays.length(); i++)
            {
                if (pstrOnWeekDays.charAt(i) == '1')
                {
                    if (pbCurrentDayIsValid && i == iStartFromDay)
                    {
                        return true;
                    }
                    pdaySchRequest.advance(iDaysToBeAdvanced);
                    return true;
                }
                iDaysToBeAdvanced++;
            }
            return false;
        }
    }

    /**
     * Advances the date with the desired scheduled frequency.
     * 
     * This method if called will advance the day as per the frequency associated.
     * 
     * @param pday  Day that needs to be advanced.
     * @param pbParameterParsing  Boolean True suppress the checks.
     * @param pstrFieldName Field Name associated with the parameter.
     * @return boolean TRUE on successful execution else FALSE
     */
    private boolean advanceDay(Day pday, boolean pbParameterParsing, String pstrFieldName)
    {
        boolean bToBeProcessed = true;
        if (frequency_ == FREQUENCY.MINUTE)
        {
            if (pbParameterParsing)
            {
                Day originalScheduleDay = new Day(tsCalendarScheduleDate_);
                int iDays = originalScheduleDay.compareTo(pday);
                int iHour = originalScheduleDay.getHour() - pday.getHour();
                int iMinutes = originalScheduleDay.getMinutes() - pday.getMinutes();
                int iSeconds = originalScheduleDay.getSeconds() - pday.getSeconds();
                if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                    pday.setDay(dayActualRequestSchTime_.getYear(), dayActualRequestSchTime_.getMonth(), dayActualRequestSchTime_.getDay(), 
                            dayActualRequestSchTime_.getHour(), dayActualRequestSchTime_.getMinutes(), dayActualRequestSchTime_.getSeconds());
                } else {
                    pday.setDay(daySchRequest_.getYear(), daySchRequest_.getMonth(), daySchRequest_.getDay(), 
                            daySchRequest_.getHour(), daySchRequest_.getMinutes(), daySchRequest_.getSeconds());
                    
                }
                pday.advance(-1 * iDays);
                pday.advance(Calendar.HOUR, -1 * iHour);
                pday.advance(Calendar.MINUTE, -1 * iMinutes);
                pday.advance(Calendar.SECOND, -1 * iSeconds);
                return bToBeProcessed;
            }
            if (bFutureSchedulingOnly_)
            {
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        pday.advance(Calendar.MINUTE, iRecur_);
                    }
                } else {
                    pday.advance(Calendar.MINUTE, iRecur_);
                }
                checkStartEndTime(pday, tsStartTime_, tsEndTime_);
                if (strOnWeekDays_ != null)
                {
                    scheduleOnWeekday(
                        strOnWeekDays_,
                        pday,
                        iRecur_,
                        true);
                }
            }
            else
            {
                pday.advance(Calendar.MINUTE, iRecur_);
                checkStartEndTime(pday, tsStartTime_, tsEndTime_);
                if (strOnWeekDays_ != null)
                {
                    scheduleOnWeekday(
                        strOnWeekDays_,
                        pday,
                        iRecur_,
                        true);
                }
                if (pday.compareInSeconds(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else if (frequency_ == FREQUENCY.HOUR)
        {
            if (pbParameterParsing)
            {
                Day originalScheduleDay = new Day(tsCalendarScheduleDate_);
                int iDays = originalScheduleDay.compareTo(pday);
                int iHour = originalScheduleDay.getHour() - pday.getHour();
                int iMinutes = originalScheduleDay.getMinutes() - pday.getMinutes();
                int iSeconds = originalScheduleDay.getSeconds() - pday.getSeconds();
                if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                    pday.setDay(dayActualRequestSchTime_.getYear(), dayActualRequestSchTime_.getMonth(), dayActualRequestSchTime_.getDay(), 
                            dayActualRequestSchTime_.getHour(), dayActualRequestSchTime_.getMinutes(), dayActualRequestSchTime_.getSeconds());
                } else {
                    pday.setDay(daySchRequest_.getYear(), daySchRequest_.getMonth(), daySchRequest_.getDay(), 
                            daySchRequest_.getHour(), daySchRequest_.getMinutes(), daySchRequest_.getSeconds());
                }
                pday.advance(-1 * iDays);
                pday.advance(Calendar.HOUR, -1 * iHour);
                pday.advance(Calendar.MINUTE, -1 * iMinutes);
                pday.advance(Calendar.SECOND, -1 * iSeconds);
                return bToBeProcessed;
            }
            if (bFutureSchedulingOnly_)
            {
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        pday.advance(Calendar.HOUR_OF_DAY, iRecur_);
                    }
                } else {
                    pday.advance(Calendar.HOUR_OF_DAY, iRecur_);
                }
                checkStartEndTime(pday, tsStartTime_, tsEndTime_);
                if (strOnWeekDays_ != null)
                {
                    scheduleOnWeekday(
                        strOnWeekDays_,
                        pday,
                        iRecur_,
                        true);
                }
            }
            else
            {
                pday.advance(Calendar.HOUR_OF_DAY, iRecur_);
                checkStartEndTime(pday, tsStartTime_, tsEndTime_);
                if (strOnWeekDays_ != null)
                {
                    scheduleOnWeekday(
                        strOnWeekDays_,
                        pday,
                        iRecur_,
                        true);
                }
                if (pday.compareInSeconds(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else if (frequency_ == FREQUENCY.DAY)
        {
            if (pbParameterParsing)
            {
                Day originalScheduleDay = new Day(tsCalendarScheduleDate_);
                int iDays = originalScheduleDay.compareTo(pday);
                if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                    pday.setDay(dayActualRequestSchTime_.getYear(), dayActualRequestSchTime_.getMonth(), dayActualRequestSchTime_.getDay(), 
                            pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                } else {
                    pday.setDay(daySchRequest_.getYear(), daySchRequest_.getMonth(), daySchRequest_.getDay(),
                            pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                }
                pday.advance(-1 * iDays);
                return bToBeProcessed;
            }
            if (bFutureSchedulingOnly_) {
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                        pday.advance(Calendar.DATE, iRecur_);
                    }
                } else {
                    pday.advance(Calendar.DATE, iRecur_);
                }
            }
            else
            {
                pday.advance(Calendar.DATE, iRecur_);
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else if (frequency_ == FREQUENCY.WEEK)
        {
            if (pbParameterParsing)
            {
                Day originalScheduleDay = new Day(tsCalendarScheduleDate_);
                int iDays = originalScheduleDay.compareTo(pday);
                if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                    pday.setDay(dayActualRequestSchTime_.getYear(), dayActualRequestSchTime_.getMonth(), dayActualRequestSchTime_.getDay(),
                            pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                } else {
                    pday.setDay(daySchRequest_.getYear(), daySchRequest_.getMonth(), daySchRequest_.getDay(),
                            pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                }
                pday.advance(-1 * iDays);
                return bToBeProcessed;
            }
            boolean bWeekDaySuccessfull = false;
            if (strOnWeekDays_ != null)
            {
                bWeekDaySuccessfull = scheduleOnWeekday(
                        strOnWeekDays_,
                        pday,
                        iRecur_,
                        false);
            }
            if (bFutureSchedulingOnly_)
            {
//First check for the week day. If the week day is satisfied then check whether 
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        if (bWeekDaySuccessfull)
                        {
                            bWeekDaySuccessfull = scheduleOnWeekday(
                                    strOnWeekDays_,
                                    pday,
                                    iRecur_,
                                    false);
                        }
                        else
                        {
                            pday.advance(Calendar.WEEK_OF_YEAR, iRecur_);
                        }
                    }
                } else {
                    if (!bWeekDaySuccessfull) {
                        pday.advance(Calendar.WEEK_OF_YEAR, iRecur_);
                    }
                }
                if (!bWeekDaySuccessfull)
                {
                    if (strOnWeekDays_ != null)
                    {
                        pday.advance(-1 * (pday.weekday()-1));
                        boolean bCurrentDayIsValid = true;
                        while (true)
                        {
                            bWeekDaySuccessfull = scheduleOnWeekday(
                                strOnWeekDays_,
                                pday,
                                iRecur_,
                                bCurrentDayIsValid);
                            if (pday.compareInSeconds(dayReqProcessEndDate_) > 0)
                            {
                                break;
                            }
                            if (!bWeekDaySuccessfull)
                            {
                                pday.advance(Calendar.WEEK_OF_YEAR, iRecur_);
                                pday.advance(-1 * pday.weekday() - 1);
                            }
                            else
                            {
                                bCurrentDayIsValid = false;
                            }
                        }
                    }
                }
            }
            else
            {
                if (!bWeekDaySuccessfull)
                {
                    pday.advance(Calendar.WEEK_OF_YEAR, iRecur_);
                    if (strOnWeekDays_ != null)
                    {
                        pday.advance(-1 * (pday.weekday()-1));
                        scheduleOnWeekday(
                            strOnWeekDays_,
                            pday,
                            iRecur_,
                            true);
                    }
                }
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else if (frequency_ == FREQUENCY.MONTH)
        {
            if (pbParameterParsing)
            {
                for (int i = 0; i < iAdvanceCounter_; i++)
                {
                    pday.advance(Calendar.MONTH, iRecur_);
                }
//                Day originalScheduleDay = new Day(tsRequestSchDate_);
//                int iDays = originalScheduleDay.compareTo(pday);
//                pday.setDay(daySchRequest_.year(), daySchRequest_.month(), daySchRequest_.day());
//                pday.advance(-1 * iDays);
                return bToBeProcessed;
            }
//            iAdvanceCounter_ = 0;
            if (bFutureSchedulingOnly_)
            {
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        pday.advance(Calendar.MONTH, iRecur_);
                        iAdvanceCounter_++;
                    }
                } else {
                    pday.advance(Calendar.MONTH, iRecur_);
                    iAdvanceCounter_++;
                }
            }
            else
            {
                pday.advance(Calendar.MONTH, iRecur_);
                iAdvanceCounter_++;
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else if (frequency_ == FREQUENCY.YEAR)
        {
            if (pbParameterParsing)
            {
                for (int i = 0; i < iAdvanceCounter_; i++)
                {
                    pday.advance(Calendar.YEAR, iRecur_);
                }
//                Day originalScheduleDay = new Day(tsRequestSchDate_);
//                int iDays = originalScheduleDay.compareTo(pday);
//                pday.setDay(daySchRequest_.year(), daySchRequest_.month(), daySchRequest_.day());
//                pday.advance(-1 * iDays);
                return bToBeProcessed;
            }
//            iAdvanceCounter_ = 0;
            if (bFutureSchedulingOnly_)
            {
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        pday.advance(Calendar.YEAR, iRecur_);
                        iAdvanceCounter_++;
                    }
                } else {
                    pday.advance(Calendar.YEAR, iRecur_);
                    iAdvanceCounter_++;
                }
            }
            else
            {
                pday.advance(Calendar.YEAR, iRecur_);
                iAdvanceCounter_++;
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else if (frequency_ == FREQUENCY.LAST_DATE_OF_MONTH) // Last Day of the Month
        {
            if (pbParameterParsing)
            {
                for (int i = 0; i < iAdvanceCounter_; i++)
                {
                    pday.advance(Calendar.MONTH, iRecur_);
                }
//                Day originalScheduleDay = new Day(tsRequestSchDate_);
//                int iDays = originalScheduleDay.compareTo(pday);
//                pday.setDay(daySchRequest_.year(), daySchRequest_.month(), daySchRequest_.day());
//                pday.advance(-1 * iDays);
                return bToBeProcessed;
            }
//            iAdvanceCounter_ = 0;
            if (bFutureSchedulingOnly_)
            {
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        pday.advance(Calendar.MONTH, iRecur_);
                        iAdvanceCounter_++;
                    }
                } else {
                    pday.advance(Calendar.MONTH, iRecur_);
                    iAdvanceCounter_++;
                }
                Calendar calendar = new GregorianCalendar();
                calendar.setLenient(true);
                calendar.clear();
                calendar.setTime(pday.getUtilDate());
                Day newDay = new Day();
                newDay.setDay(
                    pday.getYear(),
                    pday.getMonth(),
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                    pday.getHour(),
                    pday.getMinutes(),
                    pday.getSeconds(),
                    pday.getMilliseconds());
                pday.setDay(newDay);
            }
            else
            {
                pday.advance(Calendar.MONTH, iRecur_);
                iAdvanceCounter_++;
                Calendar calendar = new GregorianCalendar();
                calendar.setLenient(true);
                calendar.clear();
                calendar.setTime(pday.getUtilDate());
                Day newDay = new Day();
                newDay.setDay(
                    pday.getYear(),
                    pday.getMonth(),
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                    pday.getHour(),
                    pday.getMinutes(),
                    pday.getSeconds(),
                    pday.getMilliseconds());
                pday.setDay(newDay);
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else if (frequency_ == FREQUENCY.FIRST_DAY_OF_MONTH ||
                frequency_ == FREQUENCY.SECOND_DAY_OF_MONTH ||
                frequency_ == FREQUENCY.THIRD_DAY_OF_MONTH ||
                frequency_ == FREQUENCY.FOURTH_DAY_OF_MONTH ||
                frequency_ == FREQUENCY.LAST_DAY_OF_MONTH
                )
        {
            if (pbParameterParsing)
            {
                Day originalScheduleDay = new Day(tsCalendarScheduleDate_);
                int iDays = originalScheduleDay.compareTo(pday);
                if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                    pday.setDay(dayActualRequestSchTime_.getYear(), dayActualRequestSchTime_.getMonth(),
                            dayActualRequestSchTime_.getDay(), pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                } else {
                    pday.setDay(daySchRequest_.getYear(), daySchRequest_.getMonth(),
                            daySchRequest_.getDay(), pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                }
                pday.advance(-1 * iDays);
                return bToBeProcessed;
            }
            int iDayOfTheWeek = strOnWeekDays_.indexOf("1");
            if (iDayOfTheWeek < 0)
            {
                return false;
            }
            iDayOfTheWeek += 1;         //As Sunday starts from 1 and indexOf will return 0 for Sunday.
            if (bFutureSchedulingOnly_)
            {
                setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        pday.advance(Calendar.MONTH, iRecur_);
                        setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                    }
                } else {
                    pday.advance(Calendar.MONTH, iRecur_);
                    setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                }
            }
            else
            {
                pday.advance(Calendar.MONTH, iRecur_);
                setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        } else if (frequency_ == FREQUENCY.FIRST_DAY_OF_YEAR ||
                frequency_ == FREQUENCY.SECOND_DAY_OF_YEAR  ||
                frequency_ == FREQUENCY.THIRD_DAY_OF_YEAR  ||
                frequency_ == FREQUENCY.FOURTH_DAY_OF_YEAR  ||
                frequency_ == FREQUENCY.LAST_DAY_OF_YEAR
                )
        { 
            if (pbParameterParsing)
            {
                Day originalScheduleDay = new Day(tsCalendarScheduleDate_);
                int iDays = originalScheduleDay.compareTo(pday);
                if (skipFlag_ == SKIP_FLAG.SKIP_SCHEDULE) {
                    pday.setDay(dayActualRequestSchTime_.getYear(), dayActualRequestSchTime_.getMonth(),
                            dayActualRequestSchTime_.getDay(), pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                } else {
                    pday.setDay(daySchRequest_.getYear(), daySchRequest_.getMonth(),
                            daySchRequest_.getDay(), pday.getHour(), pday.getMinutes(), pday.getSeconds(), pday.getMilliseconds());
                }
                pday.advance(-1 * iDays);
                return bToBeProcessed;
            }
            int iDayOfTheWeek = strOnWeekDays_.indexOf("1");
            if (iDayOfTheWeek < 0)
            {
                return false;
            }
            iDayOfTheWeek += 1;         //As Sunday starts from 1 and indexOf will return 0 for Sunday.
            if (bFutureSchedulingOnly_)
            {
                setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        pday.advance(Calendar.YEAR, iRecur_);
                        setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                    }
                } else {
                    pday.advance(Calendar.YEAR, iRecur_);
                    setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                }
            }
            else
            {
                pday.advance(Calendar.YEAR, iRecur_);
                setDesiredDayOfWeek(pday, frequency_, iDayOfTheWeek);
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        } else if (frequency_ == FREQUENCY.PRE_PROGRAMMED) {
            if (objPreDefinedFrequency_ == null){
                logger_.fatal("The schedule manager class does not implement interface IPreDefinedFrequency");
                return false;
            }
            if (pbParameterParsing)
            {
                if (logger_.isDebugEnabled()){
                    logger_.debug("Invoking advanceDay on PRE-PROGRAMMED frequency for parameter #" + pstrFieldName);
                }
                Date dt = objPreDefinedFrequency_.advanceDay(pday.getUtilDate(), pbParameterParsing, pstrFieldName);
                if (dt == null){
                    logger_.fatal("The schedule manager returned null.");
                    return false;
                }
                Day day  = new Day(dt);
                if (pday.compareInSeconds(day) >= 0){
                    logger_.fatal("The schedule manager returned an invalid date " + day);
                    return false;
                }
                pday.setDay(day);
                return bToBeProcessed;
            }
            if (bFutureSchedulingOnly_)
            {
                if (pday.compareInSeconds(dayReqProcessEndDate_) <= 0) {
                    while (pday.compareInSeconds(dayReqProcessEndDate_) <= 0)
                    {
                        if (logger_.isDebugEnabled()){
                            logger_.debug("Invoking advanceDay on PRE-PROGRAMMED frequency for schedule date on Future Scheduling Only.");
                        }
                        Date dt = objPreDefinedFrequency_.advanceDay(pday.getUtilDate(), pbParameterParsing, pstrFieldName);
                        if (dt == null){
                            logger_.fatal("The schedule manager returned null.");
                            return false;
                        }
                        Day day  = new Day(dt);
                        if (pday.compareInSeconds(day) >= 0){
                            logger_.fatal("The schedule manager returned an invalid date " + day);
                            return false;
                        }
                        pday.setDay(day);
                    }
                } else {
                    if (logger_.isDebugEnabled()){
                        logger_.debug("Invoking advanceDay on PRE-PROGRAMMED frequency for schedule date on Future Scheduling Only.");
                    }
                    Date dt = objPreDefinedFrequency_.advanceDay(pday.getUtilDate(), pbParameterParsing, pstrFieldName);
                    if (dt == null){
                        logger_.fatal("The schedule manager returned null.");
                        return false;
                    }
                    Day day  = new Day(dt);
                    if (pday.compareInSeconds(day) >= 0){
                        logger_.fatal("The schedule manager returned an invalid date " + day);
                        return false;
                    }
                    pday.setDay(day);
                }
            }
            else
            {
                if (logger_.isDebugEnabled()){
                    logger_.debug("Invoking advanceDay on PRE-PROGRAMMED frequency for schedule date.");
                }
                Date dt = objPreDefinedFrequency_.advanceDay(pday.getUtilDate(), pbParameterParsing, pstrFieldName);
                if (dt == null){
                    logger_.fatal("The schedule manager returned null.");
                    return false;
                }
                Day day  = new Day(dt);
                if (pday.compareInSeconds(day) >= 0){
                    logger_.fatal("The schedule manager returned an invalid date " + day);
                    return false;
                }
                pday.setDay(day);
                if (pday.compareTo(dayActualRequestSchTime_) <= 0) {
                    advanceDay(pday, false, null);
                }
            }
        }
        else //Frequency Type not known.
        {
            bToBeProcessed = false;
        }
        return bToBeProcessed;
    }

    /**
     * Date or Timestamp parameters of a request are converted and advanced to the desired
     * scheduled frequency.
     *  
     * @param pstrValue {@link PREDataType} parameter.
     * @param pDataType String Datatype of the parameter value.
     * @param pstrFieldName Field name associated with the request.
     * @return String Converted value of the parameter as per the schedule.
     */
    private String convertParamValue(String pstrValue, PREDataType pDataType, String pstrFieldName)
    {
        if (pstrValue == null || pstrValue.equals(""))
        {
            return null;
        }
        String strReturn = pstrValue;
        try
        {
            Day day;
            switch (pDataType) {
                case DATE:
                    day = new Day(paramDateFormat_.parse(pstrValue));
                    advanceDay(day, true, pstrFieldName);
                    strReturn = paramDateFormat_.format(day.getSQLDate());
                    break;
                case TIMESTAMP:
                    day =
                        new Day(dateTimeFormat_.parse(pstrValue));
                    advanceDay(day, true, pstrFieldName);
                    strReturn = dateTimeFormat_.format(day.getTimestamp());
                    break;
                case DATE_ARRAY:
                    StringTokenizer stz = new StringTokenizer(pstrValue, strParamArrayDelimeter_);
                    StringBuffer sbufferDate = new StringBuffer();
                    while (stz.hasMoreTokens()) 
                    {
                        day = new Day(paramDateFormat_.parse(stz.nextToken()));
                        advanceDay(day, true, pstrFieldName);
                        sbufferDate.append(paramDateFormat_.format(day.getSQLDate()));
                        sbufferDate.append(strParamArrayDelimeter_);
                    }
                    strReturn = sbufferDate.toString();
                    break;
                default:
                    StringTokenizer stz1 = new StringTokenizer(pstrValue, strParamArrayDelimeter_);
                    StringBuffer sbufferDate1 = new StringBuffer();
                    while (stz1.hasMoreTokens()) 
                    {
                        day = new Day(dateTimeFormat_.parse(stz1.nextToken()));
                        advanceDay(day, true, pstrFieldName);
                        sbufferDate1.append(dateTimeFormat_.format(day.getSQLDate()));
                        sbufferDate1.append(strParamArrayDelimeter_);
                    }
                    strReturn = sbufferDate1.toString();
                    break;
            }
        }
        catch (RuntimeException re) {
            logger_.error("RuntimeException ", re);
            strExtraMessageToBeMailed_ = "RuntimeException Raised while converting the associated parameters." + NEW_LINE + exceptionToString(re);
            throw re;
        }
        catch (Exception e)
        {
            logger_.error("Exception ", e);
            strExtraMessageToBeMailed_ = "Exception Raised while converting the associated parameters." + NEW_LINE + exceptionToString(e);
        }
        return strReturn;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ISchedule#setPrintWriter(java.io.PrintWriter)
     */
    public void setPrintWriter(PrintWriter ppwRequestLogWriter)
    {
        pwRequestLogWriter_ = ppwRequestLogWriter;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ISchedule#setProcessRequestEntityBean(stg.pr.beans.ProcessRequestEntityBean)
     */
    public void setProcessRequestEntityBean(ProcessRequestEntityBean pobjPREB)
    {
        //do nothing
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ISchedule#setRequestParameters(java.util.HashMap)
     */
    public void setRequestParameters(HashMap<String, Object> phmParameters)
    {
        //do nothing
    }
    
    /**
     * A helper method for sending mail.
     * 
     * This method catches Throwable as the email is not a necessity but a feature.
     * 
     * @param plRequestId long Request Id is mailed as a part of the Mail Subject.
     * @param plScheduleId long Schedule Id is mailed as a part of the Mail Subject.
     * @param pstrJobId unique job identifier.
     * @param pstrJobName JOB description.
     * @param pstrMessage Message is sent as a part of the mail body.
     * @param pstrByUser User id.
     */
    private void mail(long plRequestId, long plScheduleId, String pstrJobId, String pstrJobName, String pstrMessage, String pstrByUser, String subject)
    {
        if (CSettings.get("pr.mailnotification", "OFF").equals("ON"))
        {
            try
            {
                EMail email = new EMail();
                email.setEMailId("Sch#" + plScheduleId + "->Req#" + plRequestId);
                if (subject != null) {
                    email.setSubject(subject);
                } else {
                    email.setSubject("Schedule#" + plScheduleId
                            + " for job [\"" + pstrJobId + "\" - \"" + pstrJobName +  "\"] Request #" + plRequestId + " By User \"" + pstrByUser + "\"");
                }
                email.setMessageBody(pstrMessage + NEW_LINE + NEW_LINE + "Additional information (if any):" + NEW_LINE + 
                        NEW_LINE + strExtraMessageToBeMailed_ + NEW_LINE + NEW_LINE);
                if (objPREB_.getEmailIds() != null) {
                    String strEmailIds;
                    if (objPREB_.getEmailIds() == null || objPREB_.getEmailIds().equals("")) {
                        strEmailIds = CSettings.get("mail.recepientTO");
                    } else {
                        strEmailIds = objPREB_.getEmailIds();
                    }
                    try {
                        email.setTORecipient(strEmailIds);
                    } catch (AddressException e) {
                        logger_.warn("Invalid Address recepients. " + strEmailIds, e);
                        logger_.info("Setting default recepient address...");
                        email.setTORecipient(CSettings.get("mail.recepientTO"));
                    }
                } else {
                    email.setTORecipient(CSettings.get("mail.recepientTO"));
                }
                email.setCCRecipient(CSettings.get("mail.recepientCC"));
                CMailer.getInstance(CSettings.get("pr.mailtype")).sendAsyncMail(email);
            }
            catch (Throwable me)
            {
                logger_.error("Error While Sending mail..", me);
            }
        }
    }
    
    
    /**
     * Sets the last day (SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY or SATURDAY)
     * of the given day.
     * 
     * @param pday Day
     * @param piDesiredDayOfTheWeek Last Day Of the Week.
     */
    private void setLastDesiredDayOfWeek(Day pday, int piDesiredDayOfTheWeek)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(true);
        calendar.clear();
        calendar.setTimeInMillis(pday.getTimeInMillis());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        while (calendar.get(Calendar.DAY_OF_WEEK) != piDesiredDayOfTheWeek)
        {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        Day newDay = new Day();
        newDay.setDay(
            pday.getYear(),
            pday.getMonth(),
            calendar.get(Calendar.DAY_OF_MONTH),
            pday.getHour(),
            pday.getMinutes(),
            pday.getSeconds(),
            pday.getMilliseconds());
        pday.setDay(newDay);
    }
    
    /**
     * Sets the Day to the desired day of the week based on the following frequencies.
     * 
     * {@link ISchedule.FREQUENCY#FIRST_DAY_OF_MONTH},{@link ISchedule.FREQUENCY#SECOND_DAY_OF_MONTH},  
     * {@link ISchedule.FREQUENCY#THIRD_DAY_OF_MONTH},{@link ISchedule.FREQUENCY#FOURTH_DAY_OF_MONTH},  
     * {@link ISchedule.FREQUENCY#LAST_DAY_OF_MONTH}, {@link ISchedule.FREQUENCY#FIRST_DAY_OF_YEAR},
     * {@link ISchedule.FREQUENCY#SECOND_DAY_OF_YEAR}, {@link ISchedule.FREQUENCY#THIRD_DAY_OF_YEAR},
     * {@link ISchedule.FREQUENCY#FOURTH_DAY_OF_YEAR}, {@link ISchedule.FREQUENCY#LAST_DAY_OF_YEAR}.  
     * 
     * @param pday Day to be set.
     * @param frequency {@link ISchedule.FREQUENCY}
     * @param piDesiredDayOfTheWeek Integer identifying the day of the week.
     */
    private void setDesiredDayOfWeek(Day pday, FREQUENCY frequency, int piDesiredDayOfTheWeek)
    {
        if (frequency == FREQUENCY.LAST_DAY_OF_MONTH || frequency == FREQUENCY.LAST_DAY_OF_YEAR)
        {
            setLastDesiredDayOfWeek(pday, piDesiredDayOfTheWeek);
            return;
        }
        pday.setDay(pday.getYear(), pday.getMonth(), 1, pday.getHour(), pday.getMinutes(), pday.getSeconds());
        while ( pday.weekday() != piDesiredDayOfTheWeek)
        {
            pday.advance(1);
        }
        if (frequency == FREQUENCY.FIRST_DAY_OF_MONTH || frequency == FREQUENCY.FIRST_DAY_OF_YEAR)
        {
            // Simply return as we have already advanced the date to the desired day of the week which is FIRST.
            return;
        } else if (frequency == FREQUENCY.SECOND_DAY_OF_MONTH || frequency == FREQUENCY.SECOND_DAY_OF_YEAR) {
            pday.advance(Calendar.WEEK_OF_YEAR, 1);
        } else if (frequency == FREQUENCY.THIRD_DAY_OF_MONTH || frequency == FREQUENCY.THIRD_DAY_OF_YEAR) {
            pday.advance(Calendar.WEEK_OF_YEAR, 2);
        }
        else if (frequency == FREQUENCY.FOURTH_DAY_OF_MONTH || frequency == FREQUENCY.FOURTH_DAY_OF_YEAR) 
        {
            pday.advance(Calendar.WEEK_OF_YEAR, 3);
        }
    }
    
    /**
     * Rollbacks the transaction.
     */
    private void doRollBack(){
        try
        {
            con_.rollback();
        }
        catch (SQLException e)
        {
            //dummy catch
        }
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.IScheduleValidator#setScheduleBean(stg.pr.beans.ProcessRequestScheduleEntityBean)
     */
    public void setScheduleBean(ProcessRequestScheduleEntityBean bean) {
        //do nothing as this class has the instance already. This method is useful for the class that implement
        //IScheduleValidator interface.
    }
    
    /**
     * Performs the Calendar work day check.
     * Returns true if the given day can be used for scheduling else returns false.
     * 
     * @param pday Day to be validated.
     * @return boolean
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws CDynamicDataContainerException 
     * @throws CBeanException 
     */
    private boolean performCalendarWorkDayCheck(Day pday) throws SQLException, ClassNotFoundException, CDynamicDataContainerException, CBeanException {
        if (listICalendar_ == null) {
            listICalendar_ = new ArrayList<ICalendar>();
            CDynamicDataContainer objDDC = null;
            ScheduleEventCalendarEntityBean objSECEB = new ScheduleEventCalendarEntityBean();
            ScheduleEventCalendarController objSECC = null;
            try {
                objDDC = new CDynamicDataContainer();
                objSECEB.setSchId(lSchId_);
                objSECEB.setCategory("CALENDAR");
                objDDC.build(con_, objSECEB);
                objSECC = new ScheduleEventCalendarController(con_);
                objDDC.executeQuery(con_, objSECC, objSECEB);

                while (objDDC.next()) {
                    objSECEB = (ScheduleEventCalendarEntityBean) objDDC.get();
                    ICalendar objICalendar = instantiateCalendar(objSECEB
                            .getProcessClassNm());
                    if (objICalendar == null) {
                        throw new ClassNotFoundException(
                                strExtraMessageToBeMailed_);
                    }
                    objICalendar.setPREContext(context_);
                    objICalendar.setProcessRequestEntityBean(objPREB_);
                    objICalendar.setRequestParameters(hmParameters_);
                    objICalendar.setConnection(con_);
                    listICalendar_.add(objICalendar);
                }
            } catch (CDynamicDataContainerException e) {
                logger_.error("Table Structure related error ", e);
                throw e;
            } catch (CBeanException e) {
                logger_.error("Cloning Related error ", e);
                throw e;
            } finally {
                try {
                    if (objSECC != null) {
                        objSECC.close();
                    }
                    if (objDDC != null) {
                        objDDC.close();
                    }
                } catch (SQLException e) {
                    //do nothing
                }
            }
        }
        if (logger_.isDebugEnabled()) {
            logger_.debug("There are " + listICalendar_.size() + " calendar(s) associated with the schedule.");
        }
        for (ICalendar objICalendar : listICalendar_) {
            if (!objICalendar.isWorkDay((Day) pday.clone())) {
                if (logger_.isDebugEnabled()) {
                    logger_.debug(objICalendar.getClass().getName() + " calendar returned day " + pday + " as a Non-Working Day");
                }
                return false;
            }
        }
        return true;
    }
    
    /**
     * Advances the date by the skip factor if given day is a SUNDAY or SATURDAY.
     * Returns true if the week day is applied and the date has been advanced else returns false.
     * @param pday
     * @return boolean.
     */
    private boolean applyWeekdayCheck(Day pday) {
        if (iSkipFactor_ == 0) {
            return false;
        }
        boolean bAdvanced = false;
        while (pday.weekday() == Calendar.SUNDAY || pday.weekday() == Calendar.SATURDAY) {
            bAdvanced = true;
            pday.advance(1 * iSkipFactor_);
        }
        return bAdvanced;
    }
    
    private void setAutoCommit(boolean bvalue) {
        try {
            con_.setAutoCommit(bvalue);
        } catch (SQLException e) {
            //do nothing
        }
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.ISchedule#setPREContext(stg.pr.engine.PREContext)
     */
    public void setPREContext(PREContext context) {
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.IScheduleValidator#setDataSourceFactory(stg.pr.engine.datasource.IDataSourceFactory)
     */
    public void setDataSourceFactory(IDataSourceFactory dsFactory) {
        this.dataSourceFactory = dsFactory;
    }

} //end of CRequestScheduler.java
