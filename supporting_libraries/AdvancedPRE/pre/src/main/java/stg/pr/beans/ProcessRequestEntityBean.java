/**
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
*
* $Revision: 2382 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ProcessRequestEntityBean.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/beans/ProcessRequestEntityBean.java $
 * 
 * 14    2/04/09 3:50p Kedarr
 * Added static keyword to a final variable.
 * 
 * 13    9/15/08 10:51a Kedarr
 * Added four new columns.
 * 
 * 12    7/09/08 10:39p Kedarr
 * Changed the menu_id field name to job_id and added a new field
 * job_name.
 * 
 * 11    6/15/08 10:49p Kedarr
 * Changes made for a additional column cal_scheduled_time that has been
 * added to the process_request table.
 * 
 * 10    4/11/08 2:30p Kedarr
 * Updated javadoc
 * 
 * 9     4/11/08 9:29a Kedarr
 * Changed the name of the column from elapsed_time to
 * verbose_time_elapsed. The set and get methods of the beans changed.
 * 
 * 8     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 7     3/18/08 3:07p Kedarr
 * Added Email Ids
 * 
 * 6     3/02/07 8:54a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 5     6/07/06 1:25p Kedarr
 * Added the two new fields in the initialize method.
 * 
 * 4     6/05/06 12:08p Kedarr
 * Changes made for adding request_type column in the table.
 * 
 * 3     1/19/05 3:10p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.4  2004/02/06 10:38:25  kedar
* Changes made for STUCK Thread Limit and Stuck Thread Max Limit 
* fields that were added to Process_Request table.
*
 * 
 * 1     11/03/03 12:00p Kedarr
* Revision 1.3  2003/11/01 09:06:41  kedar
* Organized the imports
*
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 4  *****************
 * User: Kedarr       Date: 9/19/03    Time: 10:12a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
 * Organising Imports
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 3/01/03    Time: 4:43p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
 * Changes made for Scheduled_Time field that has been added in the
 * Process_Request
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:50p
 * Created in $/DEC18/ProcessRequestEngine/gmac/pr/beans
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:50p
 * Created in $/ProcessRequestEngine/gmac/pr/beans
 * Initial Version
*
*/

package stg.pr.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import stg.database.CBeanException;

public class ProcessRequestEntityBean implements Cloneable, Serializable{

    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 6713669362830488642L;
	
	/**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";

	public ProcessRequestEntityBean(){
        super();//Default Constructor given
    }


    private long reqId = 0;
    private long oldreqId = 0;

    public void setReqId( long reqId  ) {
        this.reqId = reqId ;
    }

    public long getReqId(){
        return reqId;
    }


    public void setoldReqId( long oldreqId  ) {
        this.oldreqId = oldreqId ;
    }

    public long getoldReqId(){
        return oldreqId;
    }

    private String userId = null;
    private String olduserId = null;

    public void setUserId( String userId  ) {
        this.userId = userId ;
    }

    public String getUserId(){
        return userId;
    }


    public void setoldUserId( String olduserId  ) {
        this.olduserId = olduserId ;
    }

    public String getoldUserId(){
        return olduserId;
    }

    private Timestamp reqDt = null;
    private Timestamp oldreqDt = null;

    public void setReqDt( Timestamp reqDt  ) {
        this.reqDt = reqDt ;
    }

    public Timestamp getReqDt(){
        return reqDt;
    }


    public void setoldReqDt( Timestamp oldreqDt  ) {
        this.oldreqDt = oldreqDt ;
    }

    public Timestamp getoldReqDt(){
        return oldreqDt;
    }

    private String reqStat = null;
    private String oldreqStat = null;

    public void setReqStat( String reqStat  ) {
        this.reqStat = reqStat ;
    }

    public String getReqStat(){
        return reqStat;
    }


    public void setoldReqStat( String oldreqStat  ) {
        this.oldreqStat = oldreqStat ;
    }

    public String getoldReqStat(){
        return oldreqStat;
    }

    private String processClassNm = null;
    private String oldprocessClassNm = null;

    public void setProcessClassNm( String processClassNm  ) {
        this.processClassNm = processClassNm ;
    }

    public String getProcessClassNm(){
        return processClassNm;
    }


    public void setoldProcessClassNm( String oldprocessClassNm  ) {
        this.oldprocessClassNm = oldprocessClassNm ;
    }

    public String getoldProcessClassNm(){
        return oldprocessClassNm;
    }

    private String grpStInd = null;
    private String oldgrpStInd = null;

    public void setGrpStInd( String grpStInd  ) {
        this.grpStInd = grpStInd ;
    }

    public String getGrpStInd(){
        return grpStInd;
    }


    public void setoldGrpStInd( String oldgrpStInd  ) {
        this.oldgrpStInd = oldgrpStInd ;
    }

    public String getoldGrpStInd(){
        return oldgrpStInd;
    }

    private Timestamp reqStartDt = null;
    private Timestamp oldreqStartDt = null;

    public void setReqStartDt( Timestamp reqStartDt  ) {
        this.reqStartDt = reqStartDt ;
    }

    public Timestamp getReqStartDt(){
        return reqStartDt;
    }


    public void setoldReqStartDt( Timestamp oldreqStartDt  ) {
        this.oldreqStartDt = oldreqStartDt ;
    }

    public Timestamp getoldReqStartDt(){
        return oldreqStartDt;
    }

    private Timestamp reqEndDt = null;
    private Timestamp oldreqEndDt = null;

    public void setReqEndDt( Timestamp reqEndDt  ) {
        this.reqEndDt = reqEndDt ;
    }

    public Timestamp getReqEndDt(){
        return reqEndDt;
    }


    public void setoldReqEndDt( Timestamp oldreqEndDt  ) {
        this.oldreqEndDt = oldreqEndDt ;
    }

    public Timestamp getoldReqEndDt(){
        return oldreqEndDt;
    }

    private long grpId = 0;
    private long oldgrpId = 0;

    public void setGrpId( long grpId  ) {
        this.grpId = grpId ;
    }

    public long getGrpId(){
        return grpId;
    }


    public void setoldGrpId( long oldgrpId  ) {
        this.oldgrpId = oldgrpId ;
    }

    public long getoldGrpId(){
        return oldgrpId;
    }

    private long grpReqSeqNo = 0;
    private long oldgrpReqSeqNo = 0;

    public void setGrpReqSeqNo( long grpReqSeqNo  ) {
        this.grpReqSeqNo = grpReqSeqNo ;
    }

    public long getGrpReqSeqNo(){
        return grpReqSeqNo;
    }


    public void setoldGrpReqSeqNo( long oldgrpReqSeqNo  ) {
        this.oldgrpReqSeqNo = oldgrpReqSeqNo ;
    }

    public long getoldGrpReqSeqNo(){
        return oldgrpReqSeqNo;
    }

    private String reqLogfileNm = null;
    private String oldreqLogfileNm = null;

    public void setReqLogfileNm( String reqLogfileNm  ) {
        this.reqLogfileNm = reqLogfileNm ;
    }

    public String getReqLogfileNm(){
        return reqLogfileNm;
    }


    public void setoldReqLogfileNm( String oldreqLogfileNm  ) {
        this.oldreqLogfileNm = oldreqLogfileNm ;
    }

    public String getoldReqLogfileNm(){
        return oldreqLogfileNm;
    }

    private String jobId = null;
    private String oldjobId = null;

    public void setJobId( String jobId  ) {
        this.jobId = jobId ;
    }

    public String getJobId(){
        return jobId;
    }


    public void setoldJobId( String oldjobId  ) {
        this.oldjobId = oldjobId ;
    }

    public String getoldJobId(){
        return oldjobId;
    }
    
    private String jobName = null;
    private String oldjobName = null;

    public void setJobName( String jobName  ) {
        this.jobName = jobName ;
    }

    public String getJobName(){
        return jobName;
    }


    public void setoldJobName(String oldjobName) {
        this.oldjobName = oldjobName ;
    }

    public String getoldJobName(){
        return oldjobName;
    }
    

    private Timestamp scheduledTime = null;
    private Timestamp oldscheduledTime = null;
    
    public void setScheduledTime(Timestamp pts)
    {
        scheduledTime = pts;
    }

    public Timestamp getScheduledTime()
    {
        return(scheduledTime);
    }

    public void setoldScheduledTime(Timestamp pts)
    {
        oldscheduledTime = pts;
    }

    public Timestamp getoldScheduledTime()
    {
        return(oldscheduledTime);
    }
    
    private long schId = 0;
    private long oldschId = 0;
    
    public void setSchId(long pl)
    {
        schId = pl;
    }
    
    public void setoldSchId(long pl)
    {
        oldschId = pl;
    }
    
    public long getSchId()
    {
        return(schId);
    }
    
    public long getoldSchId()
    {
        return(oldschId);
    }
    
    private String reqType;
    private String oldreqType;
    
    public void setReqType(String requestType) throws CBeanException {
        checkNull("Request Type", requestType);
        this.reqType = requestType;
    }
    
    public void setoldReqType(String requestType) throws CBeanException {
        checkNull("Request Type", requestType);
        this.oldreqType = requestType;
    }

    public String getReqType(){
        return this.reqType;
    }
    
    public String getoldReqType(){
        return this.oldreqType;
    }

    private void checkNull( String pstrMessage, Object obj ) throws CBeanException{
        if ( obj == null ) throw new CBeanException( pstrMessage + " : Mandatory Field. Cannot Be Left Blank." );
        if ( obj.equals("") ) throw new CBeanException( pstrMessage + " : Mandatory Field. Cannot Be Left Blank." );
    }


    public void checkMandatory() throws CBeanException{
        checkNull( "userId", userId );
        checkNull( "reqDt", reqDt );
        checkNull( "reqStat", reqStat );
        checkNull( "processClassNm", processClassNm );
        checkNull( "grpStInd", grpStInd );
    }


    public void initialize() {
        reqId = 0;
        oldreqId = 0;
        userId = null;
        olduserId = null;
        reqDt = null;
        oldreqDt = null;
        reqStat = null;
        oldreqStat = null;
        processClassNm = null;
        oldprocessClassNm = null;
        grpStInd = null;
        oldgrpStInd = null;
        reqStartDt = null;
        oldreqStartDt = null;
        reqEndDt = null;
        oldreqEndDt = null;
        grpId = 0;
        oldgrpId = 0;
        grpReqSeqNo = 0;
        oldgrpReqSeqNo = 0;
        reqLogfileNm = null;
        oldreqLogfileNm = null;
        jobId = null;
        oldjobId = null;
        scheduledTime = null;
        oldscheduledTime = null;
        schId = 0;
        oldschId = 0;
        rStatus = false;
        stuckThreadLimit = 0;
        oldstuckThreadLimit = 0;
        stuckThreadMaxLimit = 0;
        oldstuckThreadMaxLimit = 0;
        reqType = null;
        oldreqType = null;
        priority = 0;
        oldpriority = 0;
        emailIds = "";
        oldemailIds = "";
        verboseTimeElapsed = "";
        oldverboseTimeElapsed = "";
        calScheduledTime = null;
        oldcalScheduledTime = null;
        jobName = null;
        oldjobName = null;
        text1 = null;
        text2 = null;
        oldtext1 = null;
        oldtext2 = null;
        num1 = 0d;
        oldnum1 = 0d;
        num2 = 0d;
        oldnum2 = 0d;
        retryTimes = 0;
        retryCnt = 0;
        retryTimeUnit = null;
        
        oldretryTimes = 0;
        oldretryCnt = 0;
        oldretryTimeUnit = null;

    	retryTime = 0;
    	oldretryTime = 0;
    }


    public void setRStatus(boolean b){
        this.rStatus = b;
    }

    public boolean getRStatus(){
        return rStatus;
    }


    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch(CloneNotSupportedException cnse)
        {
            return null;
        }
    }


    public String toString()
    {
        return "REQ_ID=" + reqId;
    }

    boolean rStatus = false;
    
    private long stuckThreadLimit = 0;
    private long oldstuckThreadLimit = 0;
    private long stuckThreadMaxLimit = 0;
    private long oldstuckThreadMaxLimit = 0;

    public long getoldStuckThreadLimit()
    {
        return oldstuckThreadLimit;
    }

    public long getoldStuckThreadMaxLimit()
    {
        return oldstuckThreadMaxLimit;
    }

    public long getStuckThreadLimit()
    {
        return stuckThreadLimit;
    }

    public long getStuckThreadMaxLimit()
    {
        return stuckThreadMaxLimit;
    }

    public void setoldStuckThreadLimit(long l)
    {
        oldstuckThreadLimit = l;
    }

    public void setoldStuckThreadMaxLimit(long l)
    {
        oldstuckThreadMaxLimit = l;
    }

    public void setStuckThreadLimit(long l)
    {
        stuckThreadLimit = l;
    }

    public void setStuckThreadMaxLimit(long l)
    {
        stuckThreadMaxLimit = l;
    }

    private int priority = 0;
    private int oldpriority = 0;
    
    public void setPriority(int priority){
    	this.priority = priority;
    }
    
    public void setoldPriority(int priority){
    	this.oldpriority = priority;
    }
    
    public int getPriority() {
    	return priority;
    }
    
    public int getoldPriority() {
    	return oldpriority;
    }

    private String emailIds = "";
    private String oldemailIds = "";
    
    public void setEmailIds(String emailIds) {
        if (emailIds != null) {
            this.emailIds = emailIds;
        } else {
            this.emailIds = "";
        }
    }
    
    public String getEmailIds() {
        return this.emailIds;
    }

    public void setoldEmailIds(String emailIds) {
        if (emailIds != null) {
            this.oldemailIds = emailIds;
        } else {
            this.oldemailIds = "";
        }
    }
    
    public String getoldEmailIds() {
        return this.oldemailIds;
    }
    
    private String verboseTimeElapsed = "";
    private String oldverboseTimeElapsed = "";

    /**
     * @return the verboseTimeElapsed
     */
    public String getVerboseTimeElapsed() {
        return verboseTimeElapsed;
    }

    /**
     * @param verboseTimeElapsed the Elapsed time between request end and request start date.
     */
    public void setVerboseTimeElapsed(String verboseTimeElapsed) {
        this.verboseTimeElapsed = verboseTimeElapsed;
    }

    /**
     * @return the oldverboseTimeElapsed
     */
    public String getoldVerboseTimeElapsed() {
        return oldverboseTimeElapsed;
    }

    /**
     * @param oldverboseTimeElapsed the oldverboseTimeElapsed to set
     */
    public void setoldVerboseTimeElapsed(String oldverboseTimeElapsed) {
        this.oldverboseTimeElapsed = oldverboseTimeElapsed;
    }
    
    private Timestamp calScheduledTime = null;
    private Timestamp oldcalScheduledTime = null;
    
    public void setCalScheduledTime(Timestamp calScheduledTime) {
        this.calScheduledTime = calScheduledTime;
    }

    public void setoldCalScheduledTime(Timestamp oldcalScheduledTime) {
        this.oldcalScheduledTime = oldcalScheduledTime;
    }
    
    public Timestamp getCalScheduledTime() {
        return this.calScheduledTime;
    }
    
    public Timestamp setoldCalScheduledTime() {
        return this.oldcalScheduledTime;
    }

    private String text1 = "";
    private String oldtext1 = "";

    /**
     * @return the text1
     */
    public String getText1() {
        return text1;
    }

    /**
     * @param text1 Any open text for reference
     */
    public void setText1(String text1) {
        this.text1 = text1;
    }

    /**
     * @return the oldtext1
     */
    public String getoldText1() {
        return oldtext1;
    }

    /**
     * @param oldtext1 the oldtext1 to set
     */
    public void setoldText1(String oldtext1) {
        this.oldtext1 = oldtext1;
    }

    private String text2 = "";
    private String oldtext2 = "";

    /**
     * @return the text2
     */
    public String getText2() {
        return text2;
    }

    /**
     * @param text2 Any open text for reference
     */
    public void setText2(String text2) {
        this.text2 = text2;
    }

    /**
     * @return the oldtext2
     */
    public String getoldText2() {
        return oldtext2;
    }

    /**
     * @param oldtext2 the oldtext2 to set
     */
    public void setoldText2(String oldtext2) {
        this.oldtext2 = oldtext2;
    }

    private double num1;
    private double oldnum1;
    
    private double num2;
    private double oldnum2;
    
    public void setNum1(double num1) {
        this.num1 = num1;
    }
    
    public void setoldNum1(double num1) {
        this.oldnum1 = num1;
    }
    
    public double getNum1() {
        return this.num1;
    }

    public double getoldNum1() {
        return this.oldnum1;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }
    
    public void setoldNum2(double num2) {
        this.oldnum2 = num2;
    }
    
    public double getNum2() {
        return this.num2;
    }
    
    public double getoldNum2() {
        return this.oldnum2;
    }
    
    private int retryTimes;
    private int oldretryTimes;
    
    private int retryCnt;
    private int oldretryCnt;
    
    private String retryTimeUnit;
    private String oldretryTimeUnit;

	private int retryTime;
	private int oldretryTime;

	/**
	 * @return the retry
	 */
	public int getRetryTimes() {
		return retryTimes;
	}

	/**
	 * @param retry the retry to set
	 */
	public void setRetryTimes(int retry) {
		this.retryTimes = retry;
	}

	/**
	 * @return the retry
	 */
	public int getoldRetryTimes() {
		return oldretryTimes;
	}
	
	/**
	 * @param retry the retry to set
	 */
	public void setoldRetryTimes(int retry) {
		this.oldretryTimes = retry;
	}
	
	/**
	 * @return the retryCnt
	 */
	public int getRetryCnt() {
		return retryCnt;
	}

	/**
	 * @param retryCnt the retryCnt to set
	 */
	public void setRetryCnt(int retryCnt) {
		this.retryCnt = retryCnt;
	}

	/**
	 * @return the retryCnt
	 */
	public int getoldRetryCnt() {
		return oldretryCnt;
	}
	
	/**
	 * @param retryCnt the retryCnt to set
	 */
	public void setoldRetryCnt(int retryCnt) {
		this.oldretryCnt = retryCnt;
	}
	
	/**
	 * @return the timeUnit
	 */
	public String getRetryTimeUnit() {
		return retryTimeUnit;
	}

	/**
	 * @param timeUnit the timeUnit to set
	 */
	public void setRetryTimeUnit(String timeUnit) {
		this.retryTimeUnit = timeUnit;
	}
	
	/**
	 * @return the timeUnit
	 */
	public String getoldRetryTimeUnit() {
		return oldretryTimeUnit;
	}
	
	/**
	 * @param timeUnit the timeUnit to set
	 */
	public void setoldRetryTimeUnit(String timeUnit) {
		this.oldretryTimeUnit = timeUnit;
	}
	
	/**
	 * @param retryTime the retryTime to set
	 */
	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
	}
	
	/**
	 * @return the retryTime
	 */
	public int getRetryTime() {
		return retryTime;
	}
	
	/**
	 * @param retryTime the oldretryTime to set
	 */
	public void setoldRetryTime(int retryTime) {
		this.oldretryTime = retryTime;
	}
	
	/**
	 * @return the oldretryTime
	 */
	public int getoldRetryTime() {
		return oldretryTime;
	}
	
}
