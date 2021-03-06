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
 * $Revision: 2958 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ProcessRequestScheduleController.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/beans/ProcessRequestScheduleController.java $
 * 
 * 13    9/01/09 9:05a Kedarr
 * Changes made to catch sepcific exceptions.
 * 
 * 12    2/04/09 3:50p Kedarr
 * Added static keyword to a final variable. Changed method signature from
 * public to protected for finalize.
 * 
 * 11    9/15/08 10:44a Kedarr
 * Removed the column calendar_class_nm
 * 
 * 10    6/15/08 10:52p Kedarr
 * Changes made for a additional column cal_scheduled_time that has been
 * added to the process_request table.
 * 
 * 9     5/13/08 5:29p Kedarr
 * Changes made to throw SQLException and re-formatted.
 * 
 * 8     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 7     3/18/08 3:08p Kedarr
 * Added Email Ids
 * 
 * 6     3/02/07 8:54a Kedarr
 * Changes made for fixed_date field
 * 
 * 5     2/06/06 1:46p Kedarr
 * V13.03
 * 
 * 4     7/13/05 6:41p Kedarr
 * Check for recur to have a value > 0  is removed.
 * 
 * 3     1/19/05 3:10p Kedarr
 * Advanced PRE
 * Revision 1.1  2005/11/03 04:54:42  kedar
 * *** empty log message ***
 *
 * 
 * 1     11/03/03 12:00p Kedarr
 * Revision 1.4  2003/11/01 09:06:41  kedar
 * Organized the imports
 *
 * Revision 1.3  2003/10/29 07:08:09  kedar
 * Changes made for changing the Header Information from all the files.
 * These files now do belong to Systems Task Group International Ltd.
 *
 * Revision 1.2  2003/10/23 09:06:46  kedar
 * Added Future Scheduling Only column
 *
 * Revision 1.1  2003/10/23 06:58:41  kedar
 * Inital Version Same as VSS
 *
 * 
 * *****************  Version 4  *****************
 * User: Kedarr       Date: 9/29/03    Time: 4:47p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
 * Changes made for start time and end time fields which were newly added.
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 9/19/03    Time: 10:13a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
 * Organising Imports
 *
 */

/*
 *  This Class is generated by WBNew Generic Program written by Kedar.
 *  Created on 01/08/2003 09:48:43
 *
 *  This class is the Controller class for the Bean. The Object of this class should be made only once
 *  while having multiple Bean objects.
 *
 *  NOTE: If the table does not have a primary Key then the update() method will have just a
 *  Where clause without any fields.
 *
 */
package stg.pr.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import stg.database.CBeanException;
import stg.database.IController;

public class ProcessRequestScheduleController implements IController {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 2958              $";

    /**
     * Returns the Revision number of the class. Identifies the version number
     * of the source code that generated this class file stored in the
     * configuration management tool.
     * 
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }

    /**
     * SQL Connection. 
     */
    private Connection cn = null;

    /**
     * Default constructor.
     * @param con java.sql.Connection
     * @throws CBeanException
     */
    public ProcessRequestScheduleController(Connection con)
            throws CBeanException {
        if (con == null)
            throw new CBeanException("Invalid Connection Object Parsed");
        cn = con;
    }

    PreparedStatement psmInsert = null;

    /**
     * Creates (Inserts) records in the process_request_schedule table.
     * 
     * The bean's data is used to insert.
     * 
     * @param bean
     * @return boolean True if successfully inserted a record else false.
     * @throws CBeanException If the bean is not initialized then throws exception.
     * @throws SQLException if any.
     */
    public boolean create(ProcessRequestScheduleEntityBean bean)
            throws CBeanException, SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmInsert == null) {
            psmInsert = cn
                    .prepareStatement("Insert Into process_request_schedule ("
                            + "SCH_ID,"
                            + "FREQ_TYPE,"
                            + "RECUR,"
                            + "START_DT,"
                            + "SCH_STAT,"
                            + "USER_ID,"
                            + "ON_WEEK_DAY,"
                            + "END_DT,"
                            + "END_OCCUR,"
                            + "ENTRY_DT,"
                            + "MODIFY_ID,"
                            + "MODIFY_DT,"
                            + "REQ_STAT, "
                            + "OCCUR_COUNTER, "
                            + "PROCESS_CLASS_NM, "
                            + "START_TIME, "
                            + "END_TIME, "
                            + "FUTURE_SCHEDULING_ONLY,"
                            + "FIXED_DATE,"
                            + "EMAIL_IDS," 
                            + "SKIP_FLAG,"
                            + "WEEKDAY_CHECK_FLAG," 
                            + "END_REASON," 
                            + "KEEP_ALIVE )"
                            + "Values ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        if (bean.getSchId() <= 0) {
            psmInsert.setNull(1, java.sql.Types.DECIMAL);
        } else {
            psmInsert.setLong(1, bean.getSchId());
        }
        psmInsert.setString(2, bean.getFreqType());
        psmInsert.setLong(3, bean.getRecur());
        psmInsert.setTimestamp(4, bean.getStartDt());
        psmInsert.setString(5, bean.getSchStat());
        psmInsert.setString(6, bean.getUserId());
        psmInsert.setString(7, bean.getOnWeekDay());
        psmInsert.setTimestamp(8, bean.getEndDt());
        if (bean.getEndOccur() <= 0) {
            psmInsert.setNull(9, java.sql.Types.DECIMAL);
        } else {
            psmInsert.setLong(9, bean.getEndOccur());
        }
        psmInsert.setTimestamp(10, bean.getEntryDt());
        psmInsert.setString(11, bean.getModifyId());
        psmInsert.setTimestamp(12, bean.getModifyDt());
        psmInsert.setString(13, bean.getReqStat());
        psmInsert.setLong(14, bean.getOccurCounter());
        psmInsert.setString(15, bean.getProcessClassNm());
        psmInsert.setTimestamp(16, bean.getStartTime());
        psmInsert.setTimestamp(17, bean.getEndTime());
        psmInsert.setString(18, bean.getFutureSchedulingOnly());
        psmInsert.setString(19, bean.getFixedDate());
        psmInsert.setString(20, bean.getEmailIds());
        psmInsert.setString(21, bean.getSkipFlag());
        psmInsert.setString(22, bean.getWeekdayCheckFlag());
        psmInsert.setString(23, bean.getEndReason());
        psmInsert.setString(24, bean.getKeepAlive());

        int rows = psmInsert.executeUpdate();
        returnValue = (rows == 1);

        bean.setRStatus(true);
        return returnValue;
    }

    /**
     * PreparedStatment for update is created only once and reused again and again.
     */
    private PreparedStatement psmUpdate = null;

    /**
     * Updates the process_request_schedule table with the latest data available in the bean.
     * 
     * @param bean
     * @return boolean True if updates are processed else false.
     * @throws CBeanException If the bean was not initialized.
     * @throws SQLException if any.
     */
    public boolean update(ProcessRequestScheduleEntityBean bean)
            throws CBeanException, SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmUpdate == null) {
            psmUpdate = cn.prepareStatement("Update process_request_schedule "
                    + "Set FREQ_TYPE = ? , " 
                    + "RECUR = ? , "
                    + "START_DT = ? , " 
                    + "SCH_STAT = ? , " 
                    + "USER_ID = ? , "
                    + "ON_WEEK_DAY = ? , " 
                    + "END_DT = ? , "
                    + "END_OCCUR = ? , " 
                    + "ENTRY_DT = ? , "
                    + "MODIFY_ID = ? , " 
                    + "MODIFY_DT = ? , "
                    + "REQ_STAT = ? ,  " 
                    + "OCCUR_COUNTER = ? , "
                    + "PROCESS_CLASS_NM = ?, " 
                    + "START_TIME = ?, "
                    + "END_TIME = ?," 
                    + "FUTURE_SCHEDULING_ONLY = ?, "
                    + "FIXED_DATE = ?, " 
                    + "EMAIL_IDS = ?, " 
                    + "SKIP_FLAG = ?, "
                    + "WEEKDAY_CHECK_FLAG = ?, "
                    + "END_REASON = ?, "
                    + "KEEP_ALIVE = ? "
                    + "Where SCH_ID = ?");
        }
        psmUpdate.setString(1, bean.getFreqType());
        psmUpdate.setLong(2, bean.getRecur());
        psmUpdate.setTimestamp(3, bean.getStartDt());
        psmUpdate.setString(4, bean.getSchStat());
        psmUpdate.setString(5, bean.getUserId());
        psmUpdate.setString(6, bean.getOnWeekDay());
        psmUpdate.setTimestamp(7, bean.getEndDt());
        if (bean.getEndOccur() <= 0) {
            psmUpdate.setNull(8, java.sql.Types.DECIMAL);
        } else {
            psmUpdate.setLong(8, bean.getEndOccur());
        }
        psmUpdate.setTimestamp(9, bean.getEntryDt());
        psmUpdate.setString(10, bean.getModifyId());
        psmUpdate.setTimestamp(11, bean.getModifyDt());
        psmUpdate.setString(12, bean.getReqStat());
        psmUpdate.setLong(13, bean.getOccurCounter());
        psmUpdate.setString(14, bean.getProcessClassNm());
        psmUpdate.setTimestamp(15, bean.getStartTime());
        psmUpdate.setTimestamp(16, bean.getEndTime());
        psmUpdate.setString(17, bean.getFutureSchedulingOnly());
        psmUpdate.setString(18, bean.getFixedDate());
        psmUpdate.setString(19, bean.getEmailIds());
        psmUpdate.setString(20, bean.getSkipFlag());
        psmUpdate.setString(21, bean.getWeekdayCheckFlag());
        psmUpdate.setString(22, bean.getEndReason());
        psmUpdate.setString(23, bean.getKeepAlive());
        psmUpdate.setLong(24, bean.getSchId());

        int rows = psmUpdate.executeUpdate();
        returnValue = (rows == 1);
        bean.setRStatus(true);
        return returnValue;
    }

    /**
     * PreparedStatment for primary key is prepared only once.
     */
    PreparedStatement psmPK = null;

    /**
     * Finds and populates the data in the bean with the help of the primary key.
     *  
     * @param bean
     * @param pschId
     * @return boolean True if updates were successful otherwise false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean findByPrimaryKey(ProcessRequestScheduleEntityBean bean,
            long pschId) throws CBeanException, SQLException {

        ResultSet rs = null;
        boolean returnValue = false;

        try {

            if (psmPK == null) {
                psmPK = cn.prepareStatement(
                        "Select SCH_ID,FREQ_TYPE,RECUR,START_DT,SCH_STAT,USER_ID,ON_WEEK_DAY,END_DT,END_OCCUR,ENTRY_DT,MODIFY_ID,MODIFY_DT,REQ_STAT, OCCUR_COUNTER, PROCESS_CLASS_NM, START_TIME, END_TIME, FUTURE_SCHEDULING_ONLY, FIXED_DATE, EMAIL_IDS, SKIP_FLAG, WEEKDAY_CHECK_FLAG, END_REASON, KEEP_ALIVE "
                                + " From process_request_schedule"
                                + " Where SCH_ID = ?");
            }
            psmPK.setLong(1, pschId);
            rs = psmPK.executeQuery();
            if (rs.next()) {
                returnValue = populateBean(bean, rs);
            }
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
        }
        return returnValue;
    }

    /* (non-Javadoc)
     * @see stg.database.IController#populateBean(java.lang.Object, java.sql.ResultSet)
     */
    public boolean populateBean(Object pobj, ResultSet prs)
            throws CBeanException, SQLException {
        if (pobj instanceof ProcessRequestScheduleEntityBean) {
            ProcessRequestScheduleEntityBean bean = (ProcessRequestScheduleEntityBean) pobj;
            java.math.BigDecimal objBigDecimal = prs.getBigDecimal("SCH_ID");
            if (objBigDecimal == null) {
                bean.setSchId(0);
                bean.setoldSchId(0);
            } else {
                bean.setSchId(objBigDecimal.longValue());
                bean.setoldSchId(objBigDecimal.longValue());
            }
            bean.setFreqType(prs.getString("FREQ_TYPE"));
            bean.setoldFreqType(prs.getString("FREQ_TYPE"));
            objBigDecimal = prs.getBigDecimal("RECUR");
            if (objBigDecimal == null) {
                bean.setRecur(0);
                bean.setoldRecur(0);
            } else {
                bean.setRecur(objBigDecimal.longValue());
                bean.setoldRecur(objBigDecimal.longValue());
            }
            bean.setStartDt(prs.getTimestamp("START_DT"));
            bean.setoldStartDt(prs.getTimestamp("START_DT"));
            bean.setSchStat(prs.getString("SCH_STAT"));
            bean.setoldSchStat(prs.getString("SCH_STAT"));
            bean.setUserId(prs.getString("USER_ID"));
            bean.setoldUserId(prs.getString("USER_ID"));
            bean.setOnWeekDay(prs.getString("ON_WEEK_DAY"));
            bean.setoldOnWeekDay(prs.getString("ON_WEEK_DAY"));
            bean.setEndDt(prs.getTimestamp("END_DT"));
            bean.setoldEndDt(prs.getTimestamp("END_DT"));
            objBigDecimal = prs.getBigDecimal("END_OCCUR");
            if (objBigDecimal == null) {
                bean.setEndOccur(0);
                bean.setoldEndOccur(0);
            } else {
                bean.setEndOccur(objBigDecimal.longValue());
                bean.setoldEndOccur(objBigDecimal.longValue());
            }
            bean.setEntryDt(prs.getTimestamp("ENTRY_DT"));
            bean.setoldEntryDt(prs.getTimestamp("ENTRY_DT"));
            bean.setModifyId(prs.getString("MODIFY_ID"));
            bean.setoldModifyId(prs.getString("MODIFY_ID"));
            bean.setModifyDt(prs.getTimestamp("MODIFY_DT"));
            bean.setoldModifyDt(prs.getTimestamp("MODIFY_DT"));
            bean.setReqStat(prs.getString("REQ_STAT"));
            bean.setoldReqStat(prs.getString("REQ_STAT"));
            objBigDecimal = prs.getBigDecimal("OCCUR_COUNTER");
            if (objBigDecimal == null) {
                bean.setOccurCounter(0);
                bean.setoldOccurCounter(0);
            } else {
                bean.setOccurCounter(objBigDecimal.longValue());
                bean.setoldOccurCounter(objBigDecimal.longValue());
            }
            bean.setProcessClassNm(prs.getString("PROCESS_CLASS_NM"));
            bean.setoldProcessClassNm(prs.getString("PROCESS_CLASS_NM"));
            bean.setStartTime(prs.getTimestamp("START_TIME"));
            bean.setoldStartTime(prs.getTimestamp("START_TIME"));
            bean.setEndTime(prs.getTimestamp("END_TIME"));
            bean.setoldEndTime(prs.getTimestamp("END_TIME"));
            bean.setFutureSchedulingOnly(prs
                    .getString("FUTURE_SCHEDULING_ONLY"));
            bean.setoldFutureSchedulingOnly(prs
                    .getString("FUTURE_SCHEDULING_ONLY"));
            bean.setFixedDate(prs.getString("FIXED_DATE"));
            bean.setoldFixedDate(bean.getFixedDate());
            bean.setEmailIds(prs.getString("EMAIL_IDS"));
            bean.setoldEmailIds(bean.getEmailIds());
            bean.setSkipFlag(prs.getString("SKIP_FLAG"));
            bean.setoldSkipFlag(prs.getString("SKIP_FLAG"));
            bean.setWeekdayCheckFlag(prs.getString("WEEKDAY_CHECK_FLAG"));
            bean.setoldWeekdayCheckFlag(prs.getString("WEEKDAY_CHECK_FLAG"));
            bean.setEndReason(prs.getString("END_REASON")); 
            bean.setoldEndReason(prs.getString("END_REASON"));
            bean.setKeepAlive(prs.getString("KEEP_ALIVE"));
            bean.setoldKeepAlive(prs.getString("KEEP_ALIVE"));
            return true;
        }
        return false;
    }

    /**
     * Closes all the PreparedStatements created by this instance of the class.l
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

    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    protected void finalize() {
        try {
            close();
        } catch (SQLException e) {
            //do nothing.
        }
    }

}
