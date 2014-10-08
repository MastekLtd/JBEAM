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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ProcessRequestIndexedBean.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/beans/ProcessRequestIndexedBean.java $
 * 
 * 7     2/04/09 3:51p Kedarr
 * Added static keyword to a final variable.
 * 
 * 6     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 5     8/22/05 11:33a Kedarr
 * Updated  JavaDoc for missing tags.
 * 
 * 4     1/19/05 3:10p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.3  2004/02/06 10:38:25  kedar
* Changes made for STUCK Thread Limit and Stuck Thread Max Limit 
* fields that were added to Process_Request table.
*
 * 
 * 2     1/16/04 6:17p Yogeshr
 * added schedule id 
 * 
 * 1     11/03/03 12:00p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/19/03    Time: 10:12a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
 * Organising Imports
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:50p
 * Created in $/DEC18/ProcessRequestEngine/gmac/pr/beans
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:51p
 * Created in $/ProcessRequestEngine/gmac/pr/beans
 * Initial Version
*
*/

package stg.pr.beans;

import java.sql.Timestamp;


public class ProcessRequestIndexedBean {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";

    /**
     * Returns the Revision number of the class.
     * Identifies the version number of the source code that generated this class file stored
     * in the configuration management tool.
     * 
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }


    private int         iSize_;
    private long[]      reqId_;
	private long[]      schId_;
    private String[]    userId_;
    private Timestamp[] reqDt_;
    private String[]    reqStat_;
    private Timestamp[] reqStartDt_;
    private Timestamp[] reqEndDt_;
    private Timestamp[] scheduleDt_;
    private String[]    reqLogFileNm_;
    private String[]    processNm_;
    private String[]    processClassNm_;

    public void setSize(int iSize) {
        this.iSize_ = iSize;
    }
    
    public int getSize() {
        return iSize_;
    }

    public void checkSize(String strField) throws Exception {
        if (iSize_ <= 0) {
            throw new Exception("setSize() should be called before setting " + strField);
        }
    }
    
    public void setReqId(int iIndex, long plReqId) throws Exception {
        checkSize("Request Id");
        
        if (reqId_ == null) {
            reqId_ = new long[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setReqId : invalid array index -> " + iIndex);
        }
                    
        this.reqId_[iIndex] = plReqId;
    }

    public long getReqId(int iIndex) throws Exception {
        
        if(reqId_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getReqId : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getReqId : array not initialized");
        }
        
        return reqId_[iIndex];
    }   

    public void setUserId(int iIndex, String pstrUserId) throws Exception {
        checkSize("User Id");
        
        if (userId_ == null) {
            userId_ = new String[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setUserId : invalid array index -> " + iIndex);
        }
                    
        this.userId_[iIndex] = pstrUserId;
    }

    public String getUserId(int iIndex) throws Exception {
        
        if(userId_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getUserId : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getUserId : array not initialized");
        }
        
        return userId_[iIndex];
    }   

    public void setReqDt(int iIndex, Timestamp preqDt) throws Exception {
        checkSize("Request Date");
        
        if (reqDt_ == null) {
            reqDt_ = new Timestamp[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setReqDt : invalid array index -> " + iIndex);
        }

        this.reqDt_[iIndex] = preqDt;
    }

    public Timestamp getReqDt(int iIndex) throws Exception {
        
        if(reqDt_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getReqDt : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getReqDt : array not initialized");
        }
        
        return reqDt_[iIndex];
    }   

    public void setReqStat(int iIndex, String pstrReqStat) throws Exception {
        checkSize("Request Status");
        
        if (reqStat_ == null) {
            reqStat_ = new String[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setReqStat : invalid array index -> " + iIndex);
        }
                    
        this.reqStat_[iIndex] = pstrReqStat;
    }

    public String getReqStat(int iIndex) throws Exception {
        
        if(reqStat_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getReqStat : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getReqStat : array not initialized");
        }
        
        return reqStat_[iIndex];
    }   

    public void setReqStartDt(int iIndex, Timestamp preqStartDt) throws Exception {
        checkSize("Request Start Date");
        
        if (reqStartDt_ == null) {
            reqStartDt_ = new Timestamp[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setReqStartDt : invalid array index -> " + iIndex);
        }

        this.reqStartDt_[iIndex] = preqStartDt;
    }

    public Timestamp getReqStartDt(int iIndex) throws Exception {
        
        if(reqStartDt_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getReqStartDt : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getReqStartDt : array not initialized");
        }
        
        return reqStartDt_[iIndex];
    }   

    public void setReqEndDt(int iIndex, Timestamp preqEndDt) throws Exception {
        checkSize("Request End Date");
        
        if (reqEndDt_ == null) {
            reqEndDt_ = new Timestamp[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setReqEndDt : invalid array index -> " + iIndex);
        }

        this.reqEndDt_[iIndex] = preqEndDt;
    }

    public Timestamp getReqEndDt(int iIndex) throws Exception {
        
        if(reqEndDt_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getReqEndDt : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getReqEndDt : array not initialized");
        }
        
        return reqEndDt_[iIndex];
    }   

    public void setReqLogFileNm(int iIndex, String pstrReqLogFileNm) throws Exception {
        checkSize("Request Log File Name");
        
        if (reqLogFileNm_ == null) {
            reqLogFileNm_ = new String[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setReqLogFileNm : invalid array index -> " + iIndex);
        }

        this.reqLogFileNm_[iIndex] = pstrReqLogFileNm;
    }

    public String getReqLogFileNm(int iIndex) throws Exception {
        
        if(reqLogFileNm_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getReqLogFileNm : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getReqLogFileNm : array not initialized");
        }
        
        return reqLogFileNm_[iIndex];
    }
    
    public void setProcessNm(int iIndex, String pstrProcessNm) throws Exception {
        checkSize("Process Name");
        
        if (processNm_ == null) {
            processNm_ = new String[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setProcessNm : invalid array index -> " + iIndex);
        }

        this.processNm_[iIndex] = pstrProcessNm;
    }

    public String getProcessNm(int iIndex) throws Exception {
        
        if(processNm_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getProcessNm : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getProcessNm : array not initialized");
        }
        
        return processNm_[iIndex];
    }
    
    public void setProcessClassNm(int iIndex, String pstrProcessNm) throws Exception {
        checkSize("Process Name");
        
        if (processClassNm_ == null) {
            processClassNm_ = new String[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setProcessClassNm : invalid array index -> " + iIndex);
        }

        this.processClassNm_[iIndex] = pstrProcessNm;
    }

    public String getProcessClassNm(int iIndex) throws Exception {
        
        if(processClassNm_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getProcessClassNm : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getProcessClassNm : array not initialized");
        }
        
        return processClassNm_[iIndex];
    }
    

    public void reset()
    {
        if (reqId_ != null) reqId_ = null;
        if (userId_ != null) userId_ = null;
        if (reqDt_ != null) reqDt_ = null;
        if (reqStat_ != null) reqStat_ = null;
        if (reqStartDt_ != null) reqStartDt_ = null;
        if (reqEndDt_ != null) reqEndDt_ = null;
        if (reqLogFileNm_ != null) reqLogFileNm_ = null;
        if (processNm_ != null) processNm_ = null;
        if (processClassNm_!= null) processClassNm_ = null;
        
    }
        
    public void initialize(int iVal)
    {
        reset();
        setSize(iVal);
        reqId_ = new long[iVal];               
        userId_ = new String[iVal];        
        reqDt_ = new Timestamp[iVal];                
        reqStat_ = new String[iVal];
        reqStartDt_ = new Timestamp[iVal];
        reqEndDt_ = new Timestamp[iVal];
        reqLogFileNm_ = new String[iVal];
        processNm_ = new String[iVal];
        processClassNm_ = new String[iVal];
		schId_ = new long[iVal];
        
    }
/******************** added by yogesh schedule id part *****************/

    /**
     * Sets the schedule id.
     *
     * @param iIndex
     * @param plSchId
     * @throws Exception
     */
    public void setSchId(int iIndex, long plSchId) throws Exception {
        checkSize("Schedule Id");
        
        if (schId_ == null) {
            schId_ = new long[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setSchId : invalid array index -> " + iIndex);
        }
                    
        this.schId_[iIndex] = plSchId;
    }

    public long getSchId(int iIndex) throws Exception {
        
        if(schId_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getSchId : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getSchId : array not initialized");
        }
        
        return schId_[iIndex];
    }   

    public void setScheduleDt(int iIndex, Timestamp pscheduleDt) throws Exception {
        checkSize("Request End Date");
        
        if (scheduleDt_ == null) {
            scheduleDt_ = new Timestamp[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setscheduleDt : invalid array index -> " + iIndex);
        }
        this.scheduleDt_[iIndex] = pscheduleDt;
    }

    public Timestamp getScheduleDt(int iIndex) throws Exception {
        
        if(scheduleDt_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getscheduleDt : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getscheduleDt : array not initialized");
        }        
        return scheduleDt_[iIndex];
    }   
   
}