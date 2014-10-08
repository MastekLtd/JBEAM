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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ProcessReqParamsController.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/beans/ProcessReqParamsController.java $
 * 
 * 7     9/01/09 9:04a Kedarr
 * Changes made to catch sepcific exceptions.
 * 
 * 6     2/04/09 3:45p Kedarr
 * Added static keyword to a final variable. Changed method signature of
 * finalize from public to protected.
 * 
 * 5     5/13/08 5:29p Kedarr
 * Changes made to throw SQLException and re-formatted.
 * 
 * 4     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 3     1/19/05 3:10p Kedarr
 * Advanced PRE
 * Revision 1.1  2005/11/03 04:54:42  kedar
 * *** empty log message ***
 *
 * 
 * 1     11/03/03 12:00p Kedarr
 * Revision 1.5  2003/11/01 09:06:41  kedar
 * Organized the imports
 *
 * Revision 1.4  2003/10/29 07:08:09  kedar
 * Changes made for changing the Header Information from all the files.
 * These files now do belong to Systems Task Group International Ltd.
 *
 * Revision 1.3  2003/10/28 09:46:19  kedar
 * Added a STATIC_DYNAMIC_FLAG to identify Static Dynamic nature of the parameter if the parameter is a date.
 * If the parameter is a date then:
 * If the flag is set to Dynamic then the scheduler will be advance the date with the scheduled frequency.
 * If the flag is set to Static then the schedule will not advance the date.
 *
 * Revision 1.2  2003/10/23 09:06:29  kedar
 * Added Static Dynamic Flag indicator
 *
 * Revision 1.1  2003/10/23 06:58:41  kedar
 * Inital Version Same as VSS
 *
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/19/03    Time: 10:09a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
 * Organising Imports
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/pr/beans
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:49p
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

/**
 * Controller for table <code>process_req_params</code>.
 *
 * The controller is responsible to manage all DMLs on the table.
 * 
 * @author Kedar Raybagkar
 * @since
 */
public class ProcessReqParamsController implements IController {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 2382              $";

    /**
     * SQL Connection.
     */
    private Connection cn = null;

    /**
     * Default constructor.
     * 
     * @param con
     * @throws CBeanException
     */
    public ProcessReqParamsController(Connection con) throws CBeanException {
        if (con == null)
            throw new CBeanException("Invalid Connection Object Parsed");
        cn = con;
    }

    /**
     * PreparedStatement for doing inserts.
     */
    PreparedStatement psmInsert = null;

    /**
     * Creates the record by parsing the bean.
     * @param bean
     * @return boolean True to indicate success.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean create(ProcessReqParamsEntityBean bean)
            throws CBeanException, SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmInsert == null) {
            psmInsert = cn
                    .prepareStatement("Insert Into PROCESS_REQ_PARAMS (REQ_ID,PARAM_NO,PARAM_FLD,PARAM_VAL,PARAM_DATA_TYPE, STATIC_DYNAMIC_FLAG )"
                            + "Values ( ? ,? ,? ,? ,?, ? )");
        }
        if (bean.getReqId() == 0) {
            psmInsert.setNull(1, java.sql.Types.NUMERIC);
        } else {
            psmInsert.setLong(1, bean.getReqId());
        }
        psmInsert.setLong(2, bean.getParamNo());
        psmInsert.setString(3, bean.getParamFld());
        psmInsert.setString(4, bean.getParamVal());
        psmInsert.setString(5, bean.getParamDataType());
        psmInsert.setString(6, bean.getStaticDynamicFlag());

        int rows = psmInsert.executeUpdate();
        returnValue = (rows == 1);

        bean.setRStatus(true);
        return returnValue;
    }

    /**
     * PreparedStatement for update.
     */
    private PreparedStatement psmUpdate = null;

    /**
     * Updates the process_req_params table with the data associated with the
     * bean.
     * 
     * @param bean
     * @return boolean true if success else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean update(ProcessReqParamsEntityBean bean)
            throws CBeanException, SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmUpdate == null) {
            psmUpdate = cn.prepareStatement("Update PROCESS_REQ_PARAMS "
                    + "Set PARAM_FLD = ? , " + "PARAM_VAL = ? , "
                    + "PARAM_DATA_TYPE = ?, " + "STATIC_DYNAMIC_FLAG = ?"
                    + " Where REQ_ID = ? " + "  and PARAM_NO = ?");
        }
        psmUpdate.setString(1, bean.getParamFld());
        psmUpdate.setString(2, bean.getParamVal());
        psmUpdate.setString(3, bean.getParamDataType());
        psmUpdate.setString(4, bean.getStaticDynamicFlag());
        psmUpdate.setLong(5, bean.getReqId());
        psmUpdate.setLong(6, bean.getParamNo());
        int rows = psmUpdate.executeUpdate();
        returnValue = (rows == 1);

        bean.setRStatus(true);
        return returnValue;
    }

    /**
     * PreparedStatment for delete.
     */
    private PreparedStatement psmDelete = null;

    /**
     * Deletes the records from the process_req_params table.
     * 
     * @param bean
     * @return boolean true if success else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean delete(ProcessReqParamsEntityBean bean)
            throws CBeanException, SQLException {
        if (bean.getRStatus())
            throw new CBeanException("Bean Not Initialized");
        boolean returnValue = false;
        bean.checkMandatory();
        if (psmDelete == null) {
            psmDelete = cn.prepareStatement("Delete From PROCESS_REQ_PARAMS "
                    + " Where REQ_ID = ? " + "  and PARAM_NO = ?");
        }
        psmDelete.setLong(1, bean.getReqId());
        psmDelete.setLong(2, bean.getParamNo());
        int rows = psmDelete.executeUpdate();
        returnValue = (rows == 1);

        bean.setRStatus(true);
        return returnValue;
    }

    PreparedStatement psmPK = null;

    /**
     * Locates and populates the data into the bean for a given Primary Key.
     * 
     * @param bean
     * @param preqId Primary Key 
     * @param pparamNo Primary Key
     * @return true if success or else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean findByPrimaryKey(ProcessReqParamsEntityBean bean,
            long preqId, long pparamNo) throws CBeanException, SQLException {

        ResultSet rs = null;
        boolean returnValue = false;
        try {
            if (psmPK == null) {
                psmPK = cn
                        .prepareStatement("Select REQ_ID,PARAM_NO,PARAM_FLD,PARAM_VAL,PARAM_DATA_TYPE, STATIC_DYNAMIC_FLAG"
                                + " From PROCESS_REQ_PARAMS"
                                + " Where REQ_ID = ? " + "  and PARAM_NO = ?");
            }
            psmPK.setLong(1, preqId);
            psmPK.setLong(2, pparamNo);
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

    /* (non-Javadoc)
     * @see stg.database.IController#populateBean(java.lang.Object, java.sql.ResultSet)
     */
    public boolean populateBean(Object pobj, ResultSet prs)
            throws CBeanException, SQLException {
        if (pobj instanceof ProcessReqParamsEntityBean) {
            ProcessReqParamsEntityBean bean = (ProcessReqParamsEntityBean) pobj;
            bean.setReqId(prs.getLong("REQ_ID"));
            bean.setoldReqId(prs.getLong("REQ_ID"));
            bean.setParamNo(prs.getLong("PARAM_NO"));
            bean.setoldParamNo(prs.getLong("PARAM_NO"));
            bean.setParamFld(prs.getString("PARAM_FLD"));
            bean.setoldParamFld(prs.getString("PARAM_FLD"));
            bean.setParamVal(prs.getString("PARAM_VAL"));
            bean.setoldParamVal(prs.getString("PARAM_VAL"));
            bean.setParamDataType(prs.getString("PARAM_DATA_TYPE"));
            bean.setoldParamDataType(prs.getString("PARAM_DATA_TYPE"));
            bean.setStaticDynamicFlag(prs.getString("STATIC_DYNAMIC_FLAG"));
            bean.setoldStaticDynamicFlag(prs.getString("STATIC_DYNAMIC_FLAG"));
            return true;
        }
        return false;
    }

    /**
     * Closes all open statements.
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
            // do nothing.
        }
    }

}
