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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ProcessRequestController.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/beans/ProcessRequestController.java $
 * 
 * 15    9/01/09 9:04a Kedarr
 * Changes made to catch sepcific exceptions.
 * 
 * 14    2/04/09 3:49p Kedarr
 * Added static keyword to a final variable. Changed method signature from
 * public to protected for finalize.
 * 
 * 13    9/15/08 10:51a Kedarr
 * Added four new columns.
 * 
 * 12    7/09/08 10:38p Kedarr
 * Changed the menu_id field name to job_id and added a new field
 * job_name.
 * 
 * 11    6/15/08 10:49p Kedarr
 * Changes made for a additional column cal_scheduled_time that has been
 * added to the process_request table.
 * 
 * 10    5/13/08 5:29p Kedarr
 * Changes made to throw SQLException and re-formatted.
 * 
 * 9     4/11/08 9:34a Kedarr
 * One change was left out. Corrected it.
 * 
 * 8     4/11/08 9:29a Kedarr
 * Changed the name of the column from elapsed_time to
 * verbose_time_elapsed. The set and get methods of the beans changed.
 * 
 * 7     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 6     3/18/08 3:07p Kedarr
 * Added Email Ids
 * 
 * 5     3/02/07 8:53a Kedarr
 * Added priority and changed request_type field to req_type
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
 * User: Kedarr       Date: 9/19/03    Time: 10:11a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import stg.database.CBeanException;
import stg.database.IController;

public class ProcessRequestController implements IController {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 2382              $";

    /**
     * SQL Connection object.
     */
    private Connection cn = null;

    /**
     * Constructs a controller.
     * 
     * @param con
     * @throws CBeanException
     */
    public ProcessRequestController(Connection con) throws CBeanException {
        if (con == null)
            throw new CBeanException("Invalid Connection Object Parsed");
        cn = con;
    }

    /**
     * Insert PreparedStatement.
     */
    PreparedStatement psmInsert = null;

    /**
     * Creates (insert) record in process_request table using the beans data.
     * 
     * @param bean
     * @return boolean True if able to create a record else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean create(ProcessRequestEntityBean bean) throws CBeanException,
            SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmInsert == null) {
            psmInsert = cn
                    .prepareStatement("Insert Into PROCESS_REQUEST (REQ_ID,USER_ID,REQ_DT,REQ_STAT,PROCESS_CLASS_NM,GRP_ST_IND,REQ_START_DT,REQ_END_DT,GRP_ID,GRP_REQ_SEQ_NO,REQ_LOGFILE_NM,JOB_ID, SCHEDULED_TIME, SCH_ID, STUCK_THREAD_LIMIT, STUCK_THREAD_MAX_LIMIT, REQ_TYPE, PRIORITY, EMAIL_IDS, VERBOSE_TIME_ELAPSED, CAL_SCHEDULED_TIME, JOB_NAME, TEXT1, TEXT2, NUM1, NUM2, RETRY_TIMES, RETRY_TIME_UNIT, RETRY_TIME, RETRY_CNT)"
                            + "Values ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
        }
        psmInsert.setLong(1, bean.getReqId());
        psmInsert.setString(2, bean.getUserId());
        psmInsert.setTimestamp(3, bean.getReqDt());
        psmInsert.setString(4, bean.getReqStat());
        psmInsert.setString(5, bean.getProcessClassNm());
        psmInsert.setString(6, bean.getGrpStInd());
        psmInsert.setTimestamp(7, bean.getReqStartDt());
        psmInsert.setTimestamp(8, bean.getReqEndDt());
        psmInsert.setLong(9, bean.getGrpId());
        psmInsert.setLong(10, bean.getGrpReqSeqNo());
        psmInsert.setString(11, bean.getReqLogfileNm());
        psmInsert.setString(12, bean.getJobId());
        psmInsert.setTimestamp(13, bean.getScheduledTime());
        if (bean.getSchId() == 0) {
            psmInsert.setNull(14, java.sql.Types.DECIMAL);
        } else {
            psmInsert.setLong(14, bean.getSchId());
        }
        psmInsert.setLong(15, bean.getStuckThreadLimit());
        psmInsert.setLong(16, bean.getStuckThreadMaxLimit());
        psmInsert.setString(17, bean.getReqType());
        psmInsert.setInt(18, bean.getPriority());
        psmInsert.setString(19, bean.getEmailIds());
        psmInsert.setString(20, bean.getVerboseTimeElapsed());
        psmInsert.setTimestamp(21, bean.getCalScheduledTime());
        psmInsert.setString(22, bean.getJobName());
        psmInsert.setString(23, bean.getText1());
        psmInsert.setString(24, bean.getText2());
        psmInsert.setDouble(25, bean.getNum1());
        psmInsert.setDouble(26, bean.getNum2());
        psmInsert.setInt(27, bean.getRetryTimes());
        psmInsert.setString(28, bean.getRetryTimeUnit());
        psmInsert.setInt(29, bean.getRetryTime());
        psmInsert.setInt(30, bean.getRetryCnt());

        int rows = psmInsert.executeUpdate();
        returnValue = (rows == 1);

        bean.setRStatus(true);
        return returnValue;
    }

    /**
     * Update PreparedStatement.
     */
    private PreparedStatement psmUpdate = null;

    /**
     * Updates the process_request table with the data from the bean.
     * 
     * @param bean
     * @return boolean true if successfully inserted else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean update(ProcessRequestEntityBean bean) throws CBeanException,
            SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmUpdate == null) {
            psmUpdate = cn.prepareStatement("Update PROCESS_REQUEST "
                    + "Set USER_ID = ? , " 
                    + "REQ_DT = ? , "
                    + "REQ_STAT = ? , " 
                    + "PROCESS_CLASS_NM = ? , "
                    + "GRP_ST_IND = ? , " 
                    + "REQ_START_DT = ? , "
                    + "REQ_END_DT = ? , " 
                    + "GRP_ID = ? , "
                    + "GRP_REQ_SEQ_NO = ? , " 
                    + "REQ_LOGFILE_NM = ? , "
                    + "JOB_ID = ? , " 
                    + "SCHEDULED_TIME = ? , "
                    + "SCH_ID = ? ,  " 
                    + "STUCK_THREAD_LIMIT = ?,  "
                    + "STUCK_THREAD_MAX_LIMIT = ?, " 
                    + "REQ_TYPE = ?, "
                    + "PRIORITY = ?, " 
                    + "EMAIL_IDS = ?, "
                    + "VERBOSE_TIME_ELAPSED = ?, " 
                    + "CAL_SCHEDULED_TIME = ?, " 
                    + "JOB_NAME = ?, " 
                    + "TEXT1 = ?, "
                    + "TEXT2 = ?, "
                    + "NUM1 = ?, "
                    + "NUM2 = ?, "
                    + "RETRY_TIMES = ?, "
                    + "RETRY_TIME_UNIT = ?, "
                    + "RETRY_TIME = ?, "
                    + "RETRY_CNT = ? "
                    + " Where REQ_ID = ?");
        }
        psmUpdate.setString(1, bean.getUserId());
        psmUpdate.setTimestamp(2, bean.getReqDt());
        psmUpdate.setString(3, bean.getReqStat());
        psmUpdate.setString(4, bean.getProcessClassNm());
        psmUpdate.setString(5, bean.getGrpStInd());
        psmUpdate.setTimestamp(6, bean.getReqStartDt());
        psmUpdate.setTimestamp(7, bean.getReqEndDt());
        psmUpdate.setLong(8, bean.getGrpId());
        psmUpdate.setLong(9, bean.getGrpReqSeqNo());
        psmUpdate.setString(10, bean.getReqLogfileNm());
        psmUpdate.setString(11, bean.getJobId());
        psmUpdate.setTimestamp(12, bean.getScheduledTime());
        if (bean.getSchId() == 0) {
            psmUpdate.setNull(13, java.sql.Types.DECIMAL);
        } else {
            psmUpdate.setLong(13, bean.getSchId());
        }
        psmUpdate.setLong(14, bean.getStuckThreadLimit());
        psmUpdate.setLong(15, bean.getStuckThreadMaxLimit());
        psmUpdate.setString(16, bean.getReqType());
        psmUpdate.setInt(17, bean.getPriority());
        psmUpdate.setString(18, bean.getEmailIds());
        psmUpdate.setString(19, bean.getVerboseTimeElapsed());
        psmUpdate.setTimestamp(20, bean.getCalScheduledTime());
        psmUpdate.setString(21, bean.getJobName());
        psmUpdate.setString(22, bean.getText1());
        psmUpdate.setString(23, bean.getText2());
        psmUpdate.setDouble(24, bean.getNum1());
        psmUpdate.setDouble(25, bean.getNum2());
        psmUpdate.setInt(26, bean.getRetryTimes());
        psmUpdate.setString(27, bean.getRetryTimeUnit());
        psmUpdate.setInt(28, bean.getRetryTime());
        psmUpdate.setInt(29, bean.getRetryCnt());
        psmUpdate.setLong(30, bean.getReqId());
        int rows = psmUpdate.executeUpdate();
        returnValue = (rows == 1);

        bean.setRStatus(true);
        return returnValue;
    }

    /**
     * Delete PreparedStatement.
     */
    private PreparedStatement psmDelete = null;

    /**
     * Deletes a record from the process_request table.
     * 
     * @param bean
     * @return boolean True if deleted else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean delete(ProcessRequestEntityBean bean) throws CBeanException,
            SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmDelete == null) {
            psmDelete = cn.prepareStatement("Delete From PROCESS_REQUEST "
                    + " Where REQ_ID = ?");
        }
        psmDelete.setLong(1, bean.getReqId());
        int rows = psmDelete.executeUpdate();
        returnValue = (rows == 1);

        bean.setRStatus(true);
        return returnValue;
    }

    /**
     * Primary Key PreparedStatement.
     */
    PreparedStatement psmPK = null;

    /**
     * Finds the records and populates the bean.
     * 
     * @param bean
     * @param preqId
     * @return boolean true if success else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean findByPrimaryKey(ProcessRequestEntityBean bean, long preqId)
            throws CBeanException, SQLException {

        ResultSet rs = null;
        boolean returnValue = false;

        try {

            if (psmPK == null) {
                psmPK = cn
                        .prepareStatement("Select REQ_ID,USER_ID,REQ_DT,REQ_STAT,PROCESS_CLASS_NM,GRP_ST_IND,REQ_START_DT,REQ_END_DT,GRP_ID,GRP_REQ_SEQ_NO,REQ_LOGFILE_NM,JOB_ID, SCHEDULED_TIME, SCH_ID, STUCK_THREAD_LIMIT, STUCK_THREAD_MAX_LIMIT, REQ_TYPE, PRIORITY, EMAIL_IDS, VERBOSE_TIME_ELAPSED, CAL_SCHEDULED_TIME, JOB_NAME, TEXT1, TEXT2, NUM1, NUM2, RETRY_TIMES, RETRY_TIME_UNIT, RETRY_TIME, RETRY_CNT"
                                + " From PROCESS_REQUEST Where REQ_ID = ?");
            }
            psmPK.setLong(1, preqId);
            rs = psmPK.executeQuery();
            if (rs.next()) {
                returnValue = populateBean(bean, rs);
            }
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                // do nothing.
            }
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.database.IController#populateBean(java.lang.Object,
     *      java.sql.ResultSet)
     */
    public boolean populateBean(Object pobj, ResultSet prs)
            throws CBeanException, SQLException {
        if (pobj instanceof ProcessRequestEntityBean) {
            ProcessRequestEntityBean bean = (ProcessRequestEntityBean) pobj;
            bean.setReqId(prs.getLong("REQ_ID"));
            bean.setoldReqId(prs.getLong("REQ_ID"));
            bean.setUserId(prs.getString("USER_ID"));
            bean.setoldUserId(prs.getString("USER_ID"));
            bean.setReqDt(prs.getTimestamp("REQ_DT"));
            bean.setoldReqDt(prs.getTimestamp("REQ_DT"));
            bean.setReqStat(prs.getString("REQ_STAT"));
            bean.setoldReqStat(prs.getString("REQ_STAT"));
            bean.setProcessClassNm(prs.getString("PROCESS_CLASS_NM"));
            bean.setoldProcessClassNm(prs.getString("PROCESS_CLASS_NM"));
            bean.setGrpStInd(prs.getString("GRP_ST_IND"));
            bean.setoldGrpStInd(prs.getString("GRP_ST_IND"));
            bean.setReqStartDt(prs.getTimestamp("REQ_START_DT"));
            bean.setoldReqStartDt(prs.getTimestamp("REQ_START_DT"));
            bean.setReqEndDt(prs.getTimestamp("REQ_END_DT"));
            bean.setoldReqEndDt(prs.getTimestamp("REQ_END_DT"));
            bean.setGrpId(prs.getLong("GRP_ID"));
            bean.setoldGrpId(prs.getLong("GRP_ID"));
            bean.setGrpReqSeqNo(prs.getLong("GRP_REQ_SEQ_NO"));
            bean.setoldGrpReqSeqNo(prs.getLong("GRP_REQ_SEQ_NO"));
            bean.setReqLogfileNm(prs.getString("REQ_LOGFILE_NM"));
            bean.setoldReqLogfileNm(prs.getString("REQ_LOGFILE_NM"));
            bean.setJobId(prs.getString("JOB_ID"));
            bean.setoldJobId(prs.getString("JOB_ID"));
            bean.setScheduledTime(prs.getTimestamp("SCHEDULED_TIME"));
            bean.setoldScheduledTime(prs.getTimestamp("SCHEDULED_TIME"));
            bean.setSchId(prs.getLong("SCH_ID"));
            bean.setoldSchId(prs.getLong("SCH_ID"));
            bean.setStuckThreadLimit(prs.getLong("STUCK_THREAD_LIMIT"));
            bean.setoldStuckThreadLimit(prs.getLong("STUCK_THREAD_LIMIT"));
            bean.setStuckThreadMaxLimit(prs.getLong("STUCK_THREAD_MAX_LIMIT"));
            bean.setoldStuckThreadMaxLimit(prs
                    .getLong("STUCK_THREAD_MAX_LIMIT"));
            bean.setReqType(prs.getString("REQ_TYPE"));
            bean.setoldReqType(prs.getString("REQ_TYPE"));
            bean.setPriority(prs.getInt("PRIORITY"));
            bean.setoldPriority(prs.getInt("PRIORITY"));
            bean.setEmailIds(prs.getString("EMAIL_IDS"));
            bean.setoldEmailIds(prs.getString("EMAIL_IDS"));
            bean.setVerboseTimeElapsed(prs.getString("VERBOSE_TIME_ELAPSED"));
            bean
                    .setoldVerboseTimeElapsed(prs
                            .getString("VERBOSE_TIME_ELAPSED"));
            bean.setCalScheduledTime(prs.getTimestamp("CAL_SCHEDULED_TIME"));
            bean.setoldCalScheduledTime(prs.getTimestamp("CAL_SCHEDULED_TIME"));
            bean.setJobName(prs.getString("JOB_NAME"));
            bean.setoldJobName(prs.getString("JOB_NAME"));
            bean.setText1(prs.getString("TEXT1"));
            bean.setoldText1(prs.getString("TEXT1"));
            bean.setText2(prs.getString("TEXT2"));
            bean.setoldText2(prs.getString("TEXT2"));
            bean.setNum1(prs.getDouble("NUM1"));
            bean.setoldNum1(prs.getDouble("NUM1"));
            bean.setNum2(prs.getDouble("NUM2"));
            bean.setoldNum2(prs.getDouble("NUM2"));
            bean.setRetryTimes(prs.getInt("RETRY_TIMES"));
            bean.setoldRetryTimes(prs.getInt("RETRY_TIMES"));
            bean.setRetryTimeUnit(prs.getString("RETRY_TIME_UNIT"));
            bean.setoldRetryTimeUnit(prs.getString("RETRY_TIME_UNIT"));
            bean.setRetryTime(prs.getInt("RETRY_TIME"));
            bean.setoldRetryTime(prs.getInt("RETRY_TIME"));
            bean.setRetryCnt(prs.getInt("RETRY_CNT"));
            bean.setoldRetryCnt(prs.getInt("RETRY_CNT"));
            return true;
        }
        return false;
    }

    /**
     * Closes all the open PreparedStatements.
     * 
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (psmInsert != null)
            psmInsert.close();
        if (psmUpdate != null)
            psmUpdate.close();
        if (psmPK != null)
            psmPK.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    protected void finalize() {
        try {
            close();
        } catch (SQLException e) {
            // do nothing.
        }
    }

}
