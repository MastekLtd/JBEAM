/**
 * This file forms part of the Systems Task Group International Limited  
 * Copyright (c) Keystone Solutions plc. 2001 - 2002.  All  rights reserved
 *
 *
 * $Revision: 2959 $
 *
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/AccountingMonthEndScheduleManager.java 4     9/15/09 10:07a Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/AccountingMonthEndScheduleManager.java $
 * 
 * 4     9/15/09 10:07a Kedarr
 * Changed to add Locale.
 * 
 * 3     6/17/09 11:05a Kedarr
 * Changes made to resolve the issue of non-closure of SQL statements.
 * 
 * 2     3/11/09 6:07p Kedarr
 * added revision variable and added null in the return statement if the
 * exception is caught during the execution of the method.
 * 
 * 1     3/14/07 2:14p Kedarr
 * Sample Class that shows how to build classes around PRE
 * 
 * 5     1/05/06 12:59p Kedarr
 * Changes made in the Javadoc.
 * 
 * 4     11/15/05 11:54a Kedarr
 * Changes made to change the parameter id from interfaceId to
 * interfaceID.
 * 
 * 3     7/18/05 7:16p Kedarr
 * Removed Unused variables.
 * 
 * 2     7/14/05 4:00p Kedarr
 * Redundant Query to check for accounting date was removed. Now the only
 * query is the one that checks for the given date between month begin and
 * end date is kept.
 * 
 * 1     7/14/05 3:15p Kedarr
 * Schedule Manager that advances dates as per the Accounting Month wise
 * calendar.
 * 
 * Created on Jun 16, 2005
 *
 */
package com.myapp.preimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import stg.pr.engine.datasource.IDataSourceFactory;
import stg.pr.engine.scheduler.DefaultScheduleManager;
import stg.pr.engine.scheduler.IPreDefinedFrequency;
import stg.utils.CDate;
import stg.utils.Day;

/**
 * Accounting month wise Schedule Manager.
 * 
 * This will work for Interfaces and Reports both. The FROM and TO dates,
 * associated with the request, are not important and they are derived from the
 * scheduled date. It is assumed that for a Accounting Month the scheduled date
 * is always in the next accounting month. And therefore the next scheduled
 * request will have the FROM and TO dates that of the Accounting month in which
 * the previous request's schedule date lies. The schedule date for new request
 * will be advanced by the number of days set in the answer table (LOV master)
 * for the code <code>DAYS_TO_ADVANCE</code>.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 2959 $
 */
public class AccountingMonthEndScheduleManager 
		extends DefaultScheduleManager 
		implements IPreDefinedFrequency {

    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2959              $";


    /**
     * Stores the old request id as passed by the Engine in the method
     * {@link #performPreAction(long, long)}.
     * Comment for <code>lOldRequestId_</code>
     */
    private long lOldRequestId_;

    /**
     * Calculated next accounting month end date as per the frequency defined.
     * Comment for <code>dayAccountingMonthEnd_</code>
     */
    private Day dayAccountingMonthEnd_ = null;

    /**
     * Calculated next accounting month begin date as per the frequency defined.
     * Comment for <code>dayAccountingBeginDay_</code>
     */
    private Day dayAccountingBeginDay_ = null;

    /**
     * True if the request associated is of type Interface else false. Comment
     * for <code>bRequestIsOfInterface_</code>
     */
    private boolean bRequestIsOfInterface_ = false;


    /**
     * Default Constructor.
     */
    public AccountingMonthEndScheduleManager() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.scheduler.IScheduleEvent#performPreAction(long, long)
     */
    public boolean performPreAction(long plOldRequestId, long plScheduleId) {
        lOldRequestId_ = plOldRequestId;
        bRequestIsOfInterface_ = getRequestParameters().containsKey("interfaceID");
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.scheduler.IScheduleEvent#performPostAction(long)
     */
    public boolean performPostAction(long plNewRequestId) {
        if (bRequestIsOfInterface_) {
            return insertSteps(plNewRequestId);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.scheduler.IScheduleValidator#validateSchedule(long)
     */
    public boolean validateSchedule(long plSchId) {
        return true;
    }

    /**
     * This will insert steps from the old request id to the new request id in
     * the table process_interface_steps if the associated request is of type
     * Interface.
     * @param plNewRequestId New pro-created request id.
     * @return boolean True to continue further else false to cancel the schedule.
     */
    private boolean insertSteps(long plNewRequestId) {
        PreparedStatement psmInsert = null;
        boolean bSuccess = false;
        try {
            psmInsert = super.getConnection()
                    .prepareStatement("INSERT INTO PROCESS_INTERFACE_STEPS "
                            + "("
                            + "  SELECT ?, pis_int_id, pis_step_no, pis_step_description, 'Q', pis_performed_by, "
                            + "         pis_method_name, pis_executed_on, pis_user_id "
                            + "  FROM   process_interface_steps "
                            + "  WHERE  pis_req_id = ? " + ")");
            psmInsert.setLong(1, plNewRequestId);
            psmInsert.setLong(2, lOldRequestId_);
            psmInsert.execute();
            bSuccess = true;
            closeSQLStatement(psmInsert);
        } catch (SQLException sqle) {
            closeSQLStatement(psmInsert);
            sqle.printStackTrace(super.getPrintWriter());
            bSuccess = false;
        } finally {
            closeSQLStatement(psmInsert);
        }
        return bSuccess;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.scheduler.IPreDefinedFrequency#advanceDay(java.util.Date,
     *      boolean, java.lang.String)
     */
    public Date advanceDay(Date pGivenDate, boolean bParameter,
            String pstrFieldName) {
        if (!bParameter) {
            Day day = new Day(advanceScheduleDate(pGivenDate));
            day.advance(getDaysToAdvance());
            return day.getUtilDate();
        }
        return advanceParameterDate(pGivenDate, pstrFieldName);
    }

    /**
     * This method will return accounting begin date if the parameter name
     * contains keywords like <code>from, begin, start</code>. Else it will
     * return the accounting end date.
     * 
     * @param pGivenDate
     *            Date to be advanced.
     * @param pstrFieldName
     *            Field associated with the Date.
     * @return Date New advanced date.
     */
    private Date advanceParameterDate(Date pGivenDate, String pstrFieldName) {
        if (pstrFieldName.toLowerCase(Locale.US).indexOf("from") >= 0) {
            return dayAccountingBeginDay_.getUtilDate();
        } else if (pstrFieldName.toLowerCase(Locale.US).indexOf("begin") >= 0) {
            return dayAccountingBeginDay_.getUtilDate();
        } else if (pstrFieldName.toLowerCase(Locale.US).indexOf("start") >= 0) {
            return dayAccountingBeginDay_.getUtilDate();
        } else {
            return dayAccountingMonthEnd_.getUtilDate();
        }
    }

    /**
     * Returns the new future date that is advanced based on the accounting
     * month calendar. This method will fetch the begin and end dates for the
     * accounting month in which the given parameter date lies. It is always
     * assumed that the given parameter date is always in the next month than
     * those of the parameter dates.
     * 
     * @param pGivenDate
     *            Date for which the month is to be derived.
     * @return Date New next month dates.
     */
    private Date advanceScheduleDate(Date pGivenDate) {
        PreparedStatement pstAcBet = null;
        ResultSet rsAcBet = null;
        try {
            pstAcBet = super.getConnection()
                    .prepareStatement("SELECT acc_begin_date, acc_end_date FROM accounting_yymm WHERE TRUNC(?) BETWEEN acc_begin_date AND acc_end_date");
            pstAcBet.setTimestamp(1, CDate.getUDFTimestamp(CDate
                    .getUDFDateString(pGivenDate, "yyyyMMddHHmmss"),
                    "yyyyMMddHHmmss"));
            rsAcBet = pstAcBet.executeQuery();
            if (rsAcBet.next()) {
                dayAccountingBeginDay_ = new Day(rsAcBet.getTimestamp(1));
                dayAccountingMonthEnd_ = new Day(rsAcBet.getTimestamp(2));
            } else {
                throw new IllegalArgumentException(
                        "Accounting Calendar not set.");
            }
            closeSQLStatement(pstAcBet);
        } catch (SQLException e) {
            closeSQLStatement(pstAcBet);
            e.printStackTrace();
        } catch (ParseException e) {
            closeSQLStatement(pstAcBet);
            e.printStackTrace();
        } finally {
            if (rsAcBet != null) {
                try {
                    rsAcBet.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            closeSQLStatement(pstAcBet);
        }

        return dayAccountingMonthEnd_.getUtilDate();
    }

    /**
     * Returns the number of days to be advanced for the scheduled time. The
     * Reports are scheduled for an accounting month after the processing of the
     * accounting month is over. Therefore it is necessary to schedule
     * Accounting Month reports only in the next month so that all necessary
     * month end transactions are closed. This is parameterized and the value is
     * picked from <code>answer_table</code>.
     * 
     * @return int Number of Days to be advanced.
     */
    private int getDaysToAdvance() {
        int iReturn = -1;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = super.getConnection()
                    .prepareStatement("SELECT ans_returned_num FROM answer_table WHERE gtablecode1 = ? and gtablename = ?");
            psmt.setString(1, "DAYS_TO_ADVANCE");
            psmt.setString(2, this.getClass().getName());
            rs = psmt.executeQuery();
            if (rs.next()) {
                iReturn = rs.getInt(1);
            }
            closeSQLStatement(psmt);
        } catch (SQLException e) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                }
            }
            closeSQLStatement(psmt);
        }
        return iReturn;
    }
    
    /**
     * @param st
     */
    private void closeSQLStatement(Statement st) {
        if (st == null) {
            return;
        }
        try {
            st.close();
        } catch (Throwable t) {
        }
    }

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleValidator#setDataSourceFactory(stg.pr.engine.datasource.IDataSourceFactory)
	 */
	public void setDataSourceFactory(IDataSourceFactory dsFactory) {
		//do nothing and not require in this class.
	}

}
