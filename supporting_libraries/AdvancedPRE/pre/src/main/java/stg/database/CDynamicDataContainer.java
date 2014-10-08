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
 * $Revision: 3375 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/database/CDynamicDataContainer.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/database/CDynamicDataContainer.java $
 * 
 * 13    9/01/09 9:06a Kedarr
 * Changes made to catch sepcific exceptions, removed unwanted exceptions,
 * added Locale to methods that use character case conversion.
 * 
 * 12    8/30/09 9:33p Kedarr
 * Changed string + string to string buffer
 * 
 * 11    2/04/09 3:18p Kedarr
 * Added static keyword to a final variable.
 * 
 * 10    3/22/08 3:45p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 9     3/18/08 2:59p Kedarr
 * Formatting changed. Added Javadoc.
 * 
 * 8     3/20/07 3:29p Kedarr
 * Changes made to trim the white spaces as well as to append a space to
 * Order by clause only if the oder by clause does not start with a space.
 * 
 * 7     3/01/07 4:54p Kedarr
 * Rectified the usage of CDynamicQueryException with
 * CDynamicDataContainerException where ever appropriate and also added
 * the serial uid.
 * 
 * 6     7/26/05 11:24a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 5     1/19/05 3:10p Kedarr
 * Advanced PRE
 * Revision 1.1  2005/11/03 04:54:42  kedar
 * *** empty log message ***
 *
 * Revision 1.4  2003/12/22 06:24:26  kedar
 * Added the fetchsize for the Result Set. This will help in better performance
 * as the data transfer trips between the Application Server and the Database
 * Server will be reduced.
 *
 * 
 * 2     12/09/03 9:35p Kedarr
 * Removed UnUsed variables where ever possible and made
 * the necessary changes.
 * Revision 1.3  2003/12/09 16:02:08  kedar
 * Removed UnUsed variables where ever possible and made
 * the necessary changes.
 *
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
 * *****************  Version 6  *****************
 * User: Kedarr       Date: 9/18/03    Time: 4:17p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Organising Imports
 * 
 * *****************  Version 5  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:30p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changes made for $Revision: 3375 $ in the javadoc.
 * 
 * *****************  Version 4  *****************
 * User: Kedarr       Date: 12/06/03   Time: 6:24p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changes made for Javadoc
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 12/06/03   Time: 2:31p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Changes made in the JavaDoc.
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 10/05/03   Time: 8:35p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * A hashmap containing the list of methods for which dynamicaly a where
 * clause condition is to be added.
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/database
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 13/12/02   Time: 2:31p
 * Updated in $/ProcessRequestEngine/gmac/database
 * Added 3 new methods. getMaximumFetchSize(), setMaximumFetchSize()
 * and setPartialFetch().
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:47p
 * Created in $/ProcessRequestEngine/gmac/database
 * Initial Version
 *
 */

package stg.database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This program will generate the query dynamicaly and get the DataContainer.
 * This program will work only on database beans created by WBNew.
 * 
 * <b>Currently Works on JDBC Type 4 Drivers</b>
 * 
 * @version $Revision: 3375 $
 * @author Kedar C. Raybagkar
 */
public class CDynamicDataContainer {

    // public instance constants and class constants

    // public instance variables

    // public class(static) variables

    // protected instance constants and class constants

    // protected instance variables

    // protected class(static) variables

    // package instance constants and class constants

    // package instance variables

    // package class(static) variables

    // private instance constants and class constants

    // private instance variables

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    private static final String REVISION = "$Revision:: 3375              $";

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

    /**
     * Stores the from clause.
     */
    private StringBuilder strFromClause_ = new StringBuilder("");

    /**
     * Stores the where clause.
     */
    private StringBuilder strWhereClause_ = new StringBuilder("");

    /**
     * Stores the where clause before adding it to <code>strWhereClause_<code>.
     */
    private String strAddWhereClause_ = "";

    /**
     * Stores the order by clause.
     */
    private String strOrderByClause_ = "";

    /**
     * Stores the row numbers that were updated using {@link #update(Object)} method.
     * Row numbers are delimited by hash token.
     */
    private StringBuilder strUpdatedRowNos_ = new StringBuilder();

    /**
     * Stores the row numbers that were added newly using {@link #add(Object)} method.
     * Row numbers are delimited by hash token.
     */
    private StringBuilder strAddedRowNos_ = new StringBuilder();

    /**
     * Stores the values that were added to where clause and is then appended to the main query string.
     * @see #getQuery()
     */
    private StringBuilder strQueryValues_ = new StringBuilder("");

    /**
     * Bean object.
     */
    private Object objBean_;

    /**
     * Class bean for reflection.
     */
    private Class<?> classBean_ = null;

    /**
     * Hashtable for field mappings.
     * Each field in the class that gets mapped to the table.
     */
    private Hashtable<String,String> hashtFieldMap_ = null;

    /**
     * Linkedlist that stores the methods from within the class.
     */
    private LinkedList<Method> llistMethods_ = null;

    /**
     * Stores the methods that needs to be invoked to set the where clause.
     */
    private LinkedList<Method> llistMethodsToBeInvoked_ = null;

    // LinkedList changed to ArrayList as the processing time of an ArrayList is
    // 50% faster than LinkedList
    private ArrayList<Object> alistDataContainer_; // Stores the data objects of entity
                                            // bean

    /**
     * Stores the total number of the rows returned by the query.
     */
    private int iTotalRows_; // Stores the total number of rows.

    /**
     * Stores the current cursor position.
     */
    private int iCursor_; // Stores the current cursor position

    /**
     * Maximum fetch size.
     */
    private int MAX_FETCH_SIZE_ = CDB.MAX_FETCH_SIZE; // By Default it will be
                                                        // same as
                                                        // CDB.MAX_FETCH_SIZE

    // But can be changed programatically

    private boolean bPartialFetch_; // Stores whether data_container should
                                    // retain the records

    // up to CDB.MAX_FETCH_SIZE limit even if
    // CDB.MAX_FETCH_SIZE is crossed.

    // private class(static) variables

    // constructors

    /**
     * Creates an Object of CDynamicDataContainer
     * 
     * @throws CDynamicDataContainerException
     */
    public CDynamicDataContainer() throws CDynamicDataContainerException {
        // initializing variables
        iTotalRows_ = 0;
        iCursor_ = 0;
        alistDataContainer_ = new ArrayList<Object>();
        strUpdatedRowNos_.delete(0, strUpdatedRowNos_.length());
        strAddedRowNos_.delete(0, strAddedRowNos_.length());
        strQueryValues_.delete(0, strQueryValues_.length());
    }

    // finalize method, if any

    protected void finalize() {
        close();
    }

    // main method

    // public methods of the class in the following order

    /**
     * This method builds & excutes the PreparedStatement depnding on the values
     * set in Entity Bean Object.
     * 
     * The methods builds the SELECT, FROM & WHERE clause depending on the
     * entity object passed. If the build method is called again on the same
     * object but having diff. values then the SELECT & FROM clause is not build
     * again and only the WHERE Clause is re-build. If the values set for a
     * String object in an entity bean has a <b>%</b> sign then the query will
     * have a like condition in its where clause; Once the query is generated it
     * is executed with the help of a PreparedStatement and the ResultSet
     * details are then populated in the Data Container.
     * 
     * @param pcon
     *            Connection
     * @param pobjBean
     *            Object Of an Entity Bean
     * @throws CDynamicDataContainerException
     *             if unable to build the Query.
     * 
     * @see #close()
     */
    public void build(Connection pcon, java.io.Serializable pobjBean)
            throws CDynamicDataContainerException {
        build(pcon, pobjBean, null);
    }

    /**
     * This method builds & excutes the PreparedStatement depnding on the values
     * set in Entity Bean Object.
     * 
     * The methods builds the SELECT, FROM & WHERE clause depending on the
     * entity object passed. If the build method is called again on the same
     * object but having diff. values then the SELECT & FROM clause is not build
     * again and only the WHERE Clause is re-build. If the values set for a
     * String object in an entity bean has a <b>%</b> sign then the query will
     * have a like condition in its where clause; Once the query is generated it
     * is executed with the help of a PreparedStatement and the ResultSet
     * details are then populated in the Data Container.
     * 
     * @param pcon
     *            Connection
     * @param pobjBean
     *            Object Of an Entity Bean
     * @param phmWhereCondition
     *            HashMap which maps the where conditons with method names.
     * @throws CDynamicDataContainerException
     *             if unable to build the Query.
     * 
     * @see #close()
     */
    public void build(Connection pcon, java.io.Serializable pobjBean,
            Map<String, String> phmWhereCondition) throws CDynamicDataContainerException {
        iTotalRows_ = 0;
        strUpdatedRowNos_.delete(0, strUpdatedRowNos_.length());
        strAddedRowNos_.delete(0, strAddedRowNos_.length());
        // if (strFromClause_ == null || strFromClause_.equals(""))
        // {
        buildFromClause(pcon, pobjBean);
        // }
        buildWhereClause(pcon, pobjBean, phmWhereCondition);
        objBean_ = pobjBean;
    }

    /**
     * Executes the prepared statement thus generated by
     * {@link stg.database.CDynamicDataContainer#build(java.sql.Connection, java.io.Serializable)}
     * method. The number of rows returned are greater than the
     * {@link stg.database.CDB#MAX_FETCH_SIZE} then an Exception will be thrown.
     * 
     * @param pcon
     *            Connection
     * @param pController
     *            Object who has implemented IController
     * @param pObjBean
     *            Entity Object bean.
     * @return boolean True if records found else returns false.
     * @exception CDynamicDataContainerException
     * @throws SQLException
     */
    public boolean executeQuery(Connection pcon, IController pController,
            java.io.Serializable pObjBean)
            throws CDynamicDataContainerException, SQLException {
        boolean isRecordsFound = false;
        if (objBean_ != null) {
            isRecordsFound = createPreparedStatement(pcon, objBean_,
                    pController, pObjBean);
        } else {
            throw new CDynamicDataContainerException(
                    "Unable to create PreparedStatement. Use Build.");
        }
        return (isRecordsFound);
    }

    /**
     * Goes to Next Record in the DataContainer genrated by
     * {@link stg.database.CDynamicDataContainer#build(Connection, java.io.Serializable)}
     * method
     * 
     * @return boolean false if unable to navigate to next Record in the Result
     *         Set.
     * @throws CDynamicDataContainerException
     *             if called before calling
     *             {@link stg.database.CDynamicDataContainer#executeQuery(Connection, IController, java.io.Serializable)}
     *             method.
     */
    public boolean next() throws CDynamicDataContainerException {
        // if (alistDataContainer_.isEmpty())
        // {
        // throw new CDynamicQueryException("DataContainer not available.");
        // }
        boolean isNextDown = false;
        if (iTotalRows_ > 0) {
            if (iCursor_ < iTotalRows_) {
                iCursor_++;
                isNextDown = true;
            }
        }
        return (isNextDown);
    }

    /**
     * Goes to Previous Record in the DataContainer genrated by
     * {@link stg.database.CDynamicDataContainer#build(Connection, java.io.Serializable)}
     * method
     * 
     * @return boolean false if unable to navigate to previous Record in the
     *         Result Set.
     * @throws CDynamicDataContainerException
     *             if called before calling
     *             {@link stg.database.CDynamicDataContainer#executeQuery(Connection, IController, java.io.Serializable)}
     *             method.
     */
    public boolean previous() throws CDynamicDataContainerException {
        // if (alistDataContainer_.isEmpty())
        // {
        // throw new CDynamicQueryException("DataContainer not available.");
        // }
        boolean isPreviousDone = false;
        if (iTotalRows_ > 0) {
            if (iCursor_ > 1) {
                iCursor_--;
                isPreviousDone = true;
            }
        }
        return (isPreviousDone);
    }

    /**
     * Navigates the cursor to First Record in the DataContainer genrated by
     * {@link stg.database.CDynamicDataContainer#build(Connection, java.io.Serializable) build}
     * method
     * 
     * @return boolean false if unable to navigate to First Record in the Result
     *         Set.
     * @throws CDynamicDataContainerException
     *             if called before calling
     *             {@link stg.database.CDynamicDataContainer#executeQuery(Connection, IController, java.io.Serializable)}
     *             method.
     */
    public boolean first() throws CDynamicDataContainerException {
        boolean isFirst = false;
        // if (alistDataContainer_.isEmpty())
        // {
        // throw new CDynamicQueryException("DataContainer not available.");
        // }
        if (iTotalRows_ > 0) {
            iCursor_ = 1;
            isFirst = true;
        }
        return (isFirst);
    }

    /**
     * Navigates the cursor to Last Record in the DataContainer genrated by
     * {@link stg.database.CDynamicDataContainer#build(Connection, java.io.Serializable)}
     * method
     * 
     * @return boolean false if unable to navigate to Last Record in the Result
     *         Set.
     * @exception CDynamicDataContainerException
     *                if called before calling
     *                {@link stg.database.CDynamicDataContainer#executeQuery(Connection, IController, java.io.Serializable)}
     *                method.
     */
    public boolean last() throws CDynamicDataContainerException {
        boolean isLast = false;
        // if (alistDataContainer_.isEmpty())
        // {
        // throw new CDynamicQueryException("DataContainer not available.");
        // }
        if (iTotalRows_ > 0) {
            iCursor_ = iTotalRows_;
            isLast = true;
        }
        return (isLast);
    }

    /**
     * Moves the cursor to the front of this DataContainer object, just before
     * the first row. This method has no effect if the result set contains no
     * rows.
     * 
     * @exception CDynamicDataContainerException
     *                if called before calling
     *                {@link #executeQuery(Connection, IController, java.io.Serializable)}
     *                method.
     */
    public void beforeFirst() throws CDynamicDataContainerException {
        // if (alistDataContainer_.isEmpty())
        // {
        // throw new CDynamicQueryException("DataContainer not available.");
        // }
        if (iTotalRows_ > 0) {
            iCursor_ = 0;
        }
    }

    /**
     * Moves the cursor to the end of this DataContainer object, just after the
     * last row. This method has no effect if the result set contains no rows.
     * 
     * @exception CDynamicDataContainerException
     */
    public void afterLast() throws CDynamicDataContainerException {
        // if (alistDataContainer_.isEmpty())
        // {
        // throw new CDynamicQueryException("DataContainer not available.");
        // }
        if (iTotalRows_ > 0) {
            iCursor_ = iTotalRows_ + 1;
        }
    }

    /**
     * Retrieves the current row number. The first row is number 1, the second
     * number 2, and so on.
     * 
     * @return the current row number; 0 if beforeFirst(), -1 if DataContainer
     *         equals null
     * @exception CDynamicDataContainerException
     */
    public int getRow() throws CDynamicDataContainerException {
        // if (alistDataContainer_.isEmpty())
        // {
        // throw new CDynamicQueryException("Data Container Not Available");
        // }
        if (iCursor_ > iTotalRows_) {
            return (iCursor_ - 1);
        } else if (iCursor_ < 1) {
            return (iCursor_ + 1);

        } else {
            return (iCursor_);
        }
    }

    /**
     * Returns the SQL Query thus generated by
     * {@link stg.database.CDynamicDataContainer#build(Connection, java.io.Serializable)}
     * method.
     * 
     * @return SQL query
     */
    public String getQuery() {
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(strFromClause_.toString().trim());
        sbuf.append(" ");
        sbuf.append(strWhereClause_.toString().trim());
        sbuf.append(" ");
        sbuf.append(strOrderByClause_.trim());
        return (sbuf.toString());
    }

    /**
     * Overrides the method toString of Object.
     * 
     * @return the Query.
     */
    public String toString() {
        return ("CDynamicDataContainer\n\t Query  ==>" + getQuery()
                + "\n\t Values ==>" + strQueryValues_);
    }

    /**
     * Returns true if this Data Container contains no elements.
     * 
     * @return boolean true if Data Container is not empty.
     */
    public boolean isEmpty() {
        return (alistDataContainer_.isEmpty());
        // if (!alistDataContainer_.isEmpty() && iTotalRows_ > 0)
        // {
        // return(false);
        // }
        // return(true);
    }

    /**
     * Returns the object at the current index position
     * 
     * @return Object
     * @exception CDynamicDataContainerException
     */
    public Object get() throws CDynamicDataContainerException {
        if (alistDataContainer_.isEmpty()) {
            throw new CDynamicDataContainerException(
                    "Data Container Not Available");
        }
        if (iCursor_ <= 0) {
            throw new CDynamicDataContainerException("Cursor Is Before First");
        } else if (iCursor_ > iTotalRows_) {
            throw new CDynamicDataContainerException("Cursor Is After Last");
        }

        return (alistDataContainer_.get(iCursor_ - 1));
    }

    /**
     * Closes any Opened PreparedStatement & ResultSets.
     */
    public void close() {
        strAddWhereClause_ = ""; // Re-Initialising the Variables
        strOrderByClause_ = "";
    }

    /**
     * Updates the DataContainer with the Object passed at the current index
     * position.
     * 
     * @param pobjBean
     *            Object that has been modified.
     * @return boolean if unable to update.
     * @exception CDynamicDataContainerException
     *                If cursor is at {@link #beforeFirst()} or
     *                {@link #afterLast()}
     */
    public boolean update(Object pobjBean)
            throws CDynamicDataContainerException {
        boolean isUpdated = false;
        try {
            if (iCursor_ <= 0) {
                throw new CDynamicDataContainerException(
                        "Cursor Is Before First");
            } else if (iCursor_ > iTotalRows_) {
                throw new CDynamicDataContainerException("Cursor Is After Last");
            }
            alistDataContainer_.set(iCursor_ - 1, pobjBean);
            String strIndex = iCursor_ + "#";
            if (strUpdatedRowNos_.indexOf(strIndex) < 0) {
                strUpdatedRowNos_.append(strIndex);
            }
            isUpdated = true;
        } catch (IndexOutOfBoundsException iobe) {
            throw new CDynamicDataContainerException(
                    "update(): Unable to Update Record.");
        }
        return (isUpdated);

    }

    /**
     * Adds an Entity Bean Object into the DataContainer.
     * 
     * @param pobjBean
     *            Object that needs to be Added.
     * @return boolean false if unable to add.
     * @exception CDynamicDataContainerException
     */
    public boolean add(Object pobjBean) throws CDynamicDataContainerException {
        boolean isAdded = false;
        try {
            // if (alistDataContainer_ == null)
            // {
            // alistDataContainer_ = new ArrayList();
            // }
            alistDataContainer_.add(pobjBean);
            iTotalRows_++;
            iCursor_ = iTotalRows_;
            strAddedRowNos_.append(iCursor_);
            strAddedRowNos_.append("#");
            isAdded = true;
        } catch (Exception e) {
            throw new CDynamicDataContainerException(
                    "add(): Unable to Add Record." + e.getMessage());
        }
        return (isAdded);
    }

    /**
     * Deletes the Record that has been newly added in the Data Container at a
     * given index position.
     * 
     * @param piRowNo
     *            The Record that is to be deleted.
     * @return boolean false if unable to delete the record.
     * @exception CDynamicDataContainerException
     *                if DataContainer is Empty.
     */
    public boolean delete(int piRowNo) throws CDynamicDataContainerException {
        boolean isDeleted = false;
        if (alistDataContainer_.isEmpty()) {
            throw new CDynamicDataContainerException("Data Container Empty.");
        }
        StringBuilder strIndex = new StringBuilder(piRowNo + "#");
        if (strAddedRowNos_.indexOf(strIndex.toString()) > -1) {
            StringBuilder strDummyAddedRows = new StringBuilder();
            StringTokenizer token = new StringTokenizer(strAddedRowNos_.toString(), "#");
            while (token.hasMoreTokens()) {
                int iRowNo = Integer.parseInt(token.nextToken());
                if (iRowNo > piRowNo) {
                    iRowNo--;
                    strDummyAddedRows.append(iRowNo + "#");
                } else if (iRowNo == piRowNo) {
                    alistDataContainer_.remove(piRowNo - 1); // -1 as
                                                                // LinkedList
                                                                // cursor begins
                                                                // from 0 (zero)
                    iTotalRows_--;
                    iCursor_ = iTotalRows_;
                    isDeleted = true;
                } else {
                    strDummyAddedRows.append(iRowNo + "#");
                }
            }
            if (isDeleted) {
                strAddedRowNos_ = new StringBuilder(strDummyAddedRows.toString());
            }
        }
        return (isDeleted);
    }

    /**
     * Adds a clause to the current SQL Query build by CDynamicDataContainer.
     * 
     * The call to this method should be given if an Additional Where Clause has
     * to be parsed to the query. This clause should not start with <b>WHERE</b>
     * or <b>AND</b>.
     * 
     * @param pstr
     *            Additional Where Clause
     * @throws CDynamicDataContainerException
     */
    public void addWhereClause(String pstr)
            throws CDynamicDataContainerException {
        if (pstr == null)
            pstr = "";
        if (!pstr.equals("")) {
            if (pstr.toLowerCase(Locale.US).indexOf("order ") > -1) {
                throw new CDynamicDataContainerException(
                        CDBMessages.ORDER_BY_IN_WHERE_CLAUSE);
            }
            strAddWhereClause_ += pstr;
        }
    }

    /**
     * Adds an external Order By Clause on the query Generated.
     * 
     * @param pstr
     *            Order By Clause query.
     * @exception CDynamicDataContainerException
     */
    public void addOrderByClause(String pstr)
            throws CDynamicDataContainerException {
        if (pstr == null)
            pstr = "";
        if (!pstr.equals("")) {
            if (pstr.toLowerCase(Locale.US).trim().indexOf("order ") != 0) {
                throw new CDynamicDataContainerException(
                        CDBMessages.ORDER_BY_KEYWORD);
            }
            strOrderByClause_ = pstr;
        }
    }

    /**
     * Moves the cursor to the given row number in this DataContainer object.
     * 
     * @param piRowNo
     * @return boolean
     * @exception CDynamicDataContainerException
     */
    public boolean absolute(int piRowNo) throws CDynamicDataContainerException {
        boolean isCursorMovedToAbsoluteRow = false;
        if (iTotalRows_ <= 0) {
            return (isCursorMovedToAbsoluteRow);
        }
        if (piRowNo >= iTotalRows_) {
            isCursorMovedToAbsoluteRow = last();
        } else if (piRowNo <= 0) {
            isCursorMovedToAbsoluteRow = first();
        } else {
            iCursor_ = piRowNo;
            isCursorMovedToAbsoluteRow = true;
        }
        return (isCursorMovedToAbsoluteRow);
    }

    /**
     * Retruns an ArrayList containg Objects of Entity Beans that have been
     * modified.
     * 
     * @return ArrayList Objects that have been modified.
     * @exception CDynamicDataContainerException
     */
    public ArrayList<Object> getUpdatedElements() throws CDynamicDataContainerException {
        if (alistDataContainer_.isEmpty()) {
            throw new CDynamicDataContainerException(
                    "Data Container Not Available");
        }
        ArrayList<Object> list = new ArrayList<Object>();
        if (strUpdatedRowNos_.length() > 0) {
            StringTokenizer token = new StringTokenizer(strUpdatedRowNos_.toString(),
                    "#");
            while (token.hasMoreTokens()) {
                String strNumber = token.nextToken();
                if (absolute(Integer.parseInt(strNumber))) {
                    list.add(get());
                }
            }
        }
        return (list);
    }

    /**
     * Retruns an ArrayList containg Objects of Entity Beans that have been
     * newly Added.
     * 
     * @return ArrayList Objects that have been added.
     * @exception CDynamicDataContainerException
     */
    public ArrayList<Object> getAddedElements() throws CDynamicDataContainerException {
        if (alistDataContainer_.isEmpty()) {
            throw new CDynamicDataContainerException(
                    "Data Container Not Available");
        }
        ArrayList<Object> list = new ArrayList<Object>();
        if (strAddedRowNos_.length() > 0) {
            StringTokenizer token = new StringTokenizer(strAddedRowNos_.toString(),
                    "#");
            while (token.hasMoreTokens()) {
                String strNumber = token.nextToken();
                if (absolute(Integer.parseInt(strNumber))) {
                    list.add(get());
                }
            }
        }
        return (list);
    }

    /**
     * Returns the total number for Rows in a DataContainer.
     * 
     * @return int
     * @exception CDynamicDataContainerException
     */
    public int getTotalRows() throws CDynamicDataContainerException {
        return (iTotalRows_);
    }

    /**
     * This value determines whether the Data Container should retrive the
     * records up to the Maximum Fetch Size or should throw exception.
     * 
     * @param pbPartialFetch
     *            Boolean True or False
     */
    public void setPartialFetch(boolean pbPartialFetch) {
        bPartialFetch_ = pbPartialFetch;
    }

    /**
     * Sets the Maximum Fetch Size that is allowed for the Data Container.
     * 
     * Larger the value that many objects will be created in the Data Container.
     * 
     * @param piFetchSize
     *            int value
     */
    public void setMaximumFetchSize(int piFetchSize) {
        if (piFetchSize <= 0) {
            MAX_FETCH_SIZE_ = CDB.MAX_FETCH_SIZE;
        } else {
            MAX_FETCH_SIZE_ = piFetchSize;
        }
    }

    /**
     * Returns the Maximum Fetch Size for the DataContainer.
     * 
     * @return MAX_FETCH_SIZE_ Maximum Fetch Size currently available
     */
    public int getMaximumFetchSize() {
        return (MAX_FETCH_SIZE_);
    }

    // protected constructors and methods of the class

    // package constructors and methods of the class

    // private constructors and methods of the class

    /**
     * This method builds the SELECT & FROM clause of the Query.
     * 
     * @param pcon
     *            Connection
     * @param pobjBean
     *            Entity Object
     * @throws CDynamicDataContainerException
     */
    private void buildFromClause(Connection pcon, Object pobjBean)
            throws CDynamicDataContainerException {
        if (pobjBean.getClass() != classBean_) {
            strFromClause_.delete(0, strFromClause_.length());
            classBean_ = pobjBean.getClass();
            Method[] methods = classBean_.getDeclaredMethods();
            strFromClause_.append("Select ");
            llistMethods_ = new LinkedList<Method>();
            hashtFieldMap_ = new Hashtable<String, String>();
            for (int i = 0; i < methods.length; i++) {
                String strMethodName = methods[i].getName();
                if ((strMethodName.substring(0, 3).equals("get") && !(strMethodName
                        .substring(0, 6).equals("getold") || strMethodName
                        .equals("getRStatus")))) {
                        String strFieldName = getFieldName(strMethodName
                                .substring(3));
                        strFromClause_.append(strFieldName);
                        strFromClause_.append(", ");
                        llistMethods_.addLast(methods[i]);
                        hashtFieldMap_.put(strMethodName, strFieldName);
                }
            }
            int iLastCommaAt = strFromClause_.toString().lastIndexOf(", ");
            strFromClause_.delete(iLastCommaAt, iLastCommaAt + 1);
            strFromClause_.append(" From ");
            String strTableName = classBean_.getName();
            strTableName = getFieldName(strTableName.substring(strTableName
                    .lastIndexOf(".") + 1, strTableName.length() - 10));
            strFromClause_.append(strTableName);
            // This next line is commented as the From clause is built by
            // the Data Container which does not
            // contain any Oracle / MS SQL releated functions.
            // strFromClause_ = SQLParser.parseSQL(pcon, strFromClause_);
        }
    }

    /**
     * This method builds the WHERE clause of the Query.
     * 
     * @param pcon
     *            Connection
     * @param pobjBean
     *            Entity Object
     * @param phmWhereCondition
     *            HashMap.
     * @throws CDynamicDataContainerException
     */
    private void buildWhereClause(Connection pcon, Object pobjBean,
            Map<String,String> phmWhereCondition) throws CDynamicDataContainerException {
        try {
            StringBuilder strDummy = new StringBuilder("");
            llistMethodsToBeInvoked_ = new LinkedList<Method>();
            for (int i = 0; i < llistMethods_.size(); i++) {
                Method method = llistMethods_.get(i);
                Class<? extends Object> cReturnType = method.getReturnType();
                String strReturnType = cReturnType.getName();
                Object objReturned = method.invoke(pobjBean, new Object[] {});
                boolean isAddInInvokeList = false;
                String strCondition = null;
                if (phmWhereCondition != null) {
                    strCondition = phmWhereCondition.get(method
                            .getName());
                }
                if (strCondition == null) {
                    strCondition = " = ";
                } else {
                    strCondition = " " + strCondition + " ";
                }
                if (strReturnType.indexOf("String") > -1) {
                    String strValue = (String) objReturned;
                    if (strValue != null) {
                        if (!strValue.equals("")) {
                            isAddInInvokeList = true;
                            if (strValue.indexOf("%") >= 0) {
                                strCondition = " LIKE ";
                            } else if (strValue.startsWith(":")) {
                                strCondition = strValue.substring(1);
                            }
                        }
                    }
                }
                if (strReturnType.indexOf("Timestamp") > -1) {
                    if (objReturned != null)
                        isAddInInvokeList = true;
                } else if (strReturnType.indexOf("Date") > -1) {
                    if (objReturned != null)
                        isAddInInvokeList = true;
                } else if (strReturnType.indexOf("Double") > -1) {
                    if (objReturned != null)
                        isAddInInvokeList = true;
                } else if (strReturnType.indexOf("Integer") > -1) {
                    if (objReturned != null)
                        isAddInInvokeList = true;
                } else if (strReturnType.indexOf("Long") > -1) {
                    if (objReturned != null)
                        isAddInInvokeList = true;
                } else if (strReturnType.equals("double")) {
                    double dValue = ((Double) objReturned).doubleValue();
                    // double dValue = Double.parseDouble((String)objReturned);
                    if (dValue != 0.0)
                        isAddInInvokeList = true;
                } else if (strReturnType.equals("int")) {
                    int iValue = ((Integer) objReturned).intValue();
                    // int iValue = Integer.parseInt((String)objReturned);
                    if (iValue != 0)
                        isAddInInvokeList = true;
                    // return new Integer((objData);
                } else if (strReturnType.equals("long")) {
                    long lValue = ((Long) objReturned).longValue();
                    // long lValue = Long.parseLong((String)objReturned);
                    if (lValue != 0)
                        isAddInInvokeList = true;
                }

                if (isAddInInvokeList) {
                    if (strDummy.length() > 0)
                        strDummy.append(" AND ");
                    strDummy.append((String) hashtFieldMap_.get(method
                            .getName())
                            + strCondition + "?");
                    llistMethodsToBeInvoked_.addLast(method);
                }

            }
            boolean isAddExternalClause = checkEmpty(strAddWhereClause_);
            if (strDummy.length() > 0) {
                strDummy.insert(0, " WHERE ");
                if (isAddExternalClause) {
                    strDummy.append(" AND ");
                    // strDummy.append(SQLParser.parseSQL(pcon,
                    // strAddWhereClause_));
                    strDummy.append(strAddWhereClause_);
                }
            } else {
                if (isAddExternalClause) {
                    strDummy.append(" WHERE ");
                    // strDummy.append(SQLParser.parseSQL(pcon,
                    // strAddWhereClause_));
                    strDummy.append(strAddWhereClause_);
                }
            }
            // This next line is commented as its not required.
            // strDummy = SQLParser.parseSQL(pcon, strDummy);

            /*
             * This line will be later on used when the prepared statement
             * should not be created again if the earlier query is same as the
             * current one. Currently the preparedStatement is fired again.
             */
            if (!strWhereClause_.equals(strDummy)) {
                strWhereClause_ = strDummy;
            }

        } catch (NullPointerException e) {
            throw new CDynamicDataContainerException(CMessages.NULL_POINTER
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new CDynamicDataContainerException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new CDynamicDataContainerException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new CDynamicDataContainerException(e.getMessage());
        }
    }

    /**
     * This method converts the methodName to field names.
     * 
     * The method name say getIsinCd will get transformed into isin_cd
     * 
     * @param pstrMethodName
     *            String method Name. (without the set or get)
     * @return String Field Name
     * @throws CDynamicDataContainerException
     */
    private String getFieldName(String pstrMethodName)
            throws CDynamicDataContainerException {
        StringBuilder strFieldName = new StringBuilder();
        try {
            for (int i = 0; i < pstrMethodName.length(); i++) {
                char c = pstrMethodName.charAt(i);
                if ((c >= 65 && c <= 90) && i != 0) {
                    strFieldName.append('_');
                }
                strFieldName.append(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CDynamicDataContainerException(e.getMessage());
        }
        return (strFieldName.toString().toLowerCase(Locale.US));
    }

    /**
     * This method fires the Query.
     * 
     * Creates a PreparedStatement with
     * {@link java.sql.ResultSet#TYPE_SCROLL_INSENSITIVE} and
     * {@link java.sql.ResultSet#CONCUR_READ_ONLY}
     * 
     * @param pcon
     *            Connection
     * @param pobjBean
     *            Object of Entity Bean
     * @param pController
     * @param pObjBean
     * @return boolean
     * @throws CDynamicDataContainerException
     */
    private boolean createPreparedStatement(Connection pcon, Object pobjBean,
            IController pController, Object pObjBean) throws SQLException,
            CDynamicDataContainerException {
        boolean isDataContainerPopulated = false;
        PreparedStatement pss = null;
        try {
            if (!strOrderByClause_.equals("")) {
                // strOrderByClause_ = SQLParser.parseSQL(pcon,
                // strOrderByClause_);
                // strOrderByClause_ = strOrderByClause_;
                // a space is prefixed to the strOrderByClause because all
                // leading and trailing
                // spaces are trimed by SQLParser.parseSQL()
                if (!strOrderByClause_.startsWith(" ")) {
                    strOrderByClause_ = " " + strOrderByClause_;
                }
            }

            iTotalRows_ = 0;
            int iParameterOrder = 0;
            strQueryValues_.delete(0, strQueryValues_.length());
            pss = pcon.prepareStatement(getQuery(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; i < llistMethodsToBeInvoked_.size(); i++) {
                Method method = (Method) llistMethodsToBeInvoked_.get(i);
                Class<? extends Object> cReturnType = method.getReturnType();
                String strReturnType = cReturnType.getName();
                Object objReturned = method.invoke(pobjBean, new Object[] {});

                // Method methodPS = null;
                if (strReturnType.indexOf("String") > -1) {
                    String str = ((String) objReturned);
                    pss.setString(i + 1, str);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(str + " ");
                }
                if (strReturnType.indexOf("Timestamp") > -1) {
                    Timestamp ts = ((java.sql.Timestamp) objReturned);
                    pss.setTimestamp(i + 1, ts);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(ts + " ");
                } else if (strReturnType.indexOf("Date") > -1) {
                    java.sql.Date dt = ((java.sql.Date) objReturned);
                    pss.setDate(i + 1, dt);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(dt + " ");
                } else if (strReturnType.equals("double")) {
                    double d = ((Double) objReturned).doubleValue();
                    pss.setDouble(i + 1, d);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(d + " ");
                } else if (strReturnType.equals("int")) {
                    int integer = ((Integer) objReturned).intValue();
                    pss.setInt(i + 1, integer);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(integer + " ");
                } else if (strReturnType.equals("long")) {
                    long l = ((Long) objReturned).longValue();
                    pss.setLong(i + 1, l);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(l + " ");
                } else if (strReturnType.indexOf("Double") > -1) {
                    double d = ((Double) objReturned).doubleValue();
                    pss.setDouble(i + 1, d);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(d + " ");
                } else if (strReturnType.indexOf("Integer") > -1) {
                    int integer = ((Integer) objReturned).intValue();
                    pss.setInt(i + 1, integer);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(integer + " ");
                } else if (strReturnType.indexOf("Long") > -1) {
                    long l = ((Long) objReturned).longValue();
                    pss.setLong(i + 1, l);
                    iParameterOrder++;
                    strQueryValues_.append("$");
                    strQueryValues_.append(iParameterOrder);
                    strQueryValues_.append("#");
                    strQueryValues_.append(l + " ");
                }

            }
            alistDataContainer_.clear();
            iTotalRows_ = 0;
            iCursor_ = 0;
            strUpdatedRowNos_.delete(0, strUpdatedRowNos_.length());
            strAddedRowNos_.delete(0, strAddedRowNos_.length());
            ResultSet rs = pss.executeQuery();
            rs.setFetchSize(getResultSetFetchSize(rs.getFetchSize()));
            while (rs.next()) {
                if (!pController.populateBean(pObjBean, rs)) {
                    throw new CDynamicDataContainerException(
                            CDBMessages.UNABLE_POPULATE_BEAN);
                }
                if (iTotalRows_ + 1 > MAX_FETCH_SIZE_) {
                    if (!bPartialFetch_) {
                        alistDataContainer_.clear(); // Clear the data
                                                        // fetched so far.
                        throw new CDynamicDataContainerException(
                                "Please Provide more specific selection criteria. Results Fetched are exceeding Maximum Limit of "
                                        + MAX_FETCH_SIZE_ + ".");
                    } else {
                        break; // Come out of the loop. Do not spill over
                                // Max_fetch_size.
                    }
                }
                Class<?> c = pObjBean.getClass();
                Method method = c.getDeclaredMethod("clone");
                Object objReturned = method.invoke(pobjBean, new Object[] {});
                alistDataContainer_.add(objReturned);

                iTotalRows_++;
            }
            rs.close();
            isDataContainerPopulated = (iTotalRows_ > 0);
            if (isDataContainerPopulated) {
                iCursor_ = 0;
            }
            objBean_ = null;
        } catch (SQLException sqle) {
            throw sqle;
        } catch (IllegalArgumentException e) {
            alistDataContainer_.clear();
            iTotalRows_ = 0;
            iCursor_ = 0;
            throw new CDynamicDataContainerException(e.getMessage());
        } catch (IllegalAccessException e) {
            alistDataContainer_.clear();
            iTotalRows_ = 0;
            iCursor_ = 0;
            throw new CDynamicDataContainerException(e.getMessage());
        } catch (InvocationTargetException e) {
            alistDataContainer_.clear();
            iTotalRows_ = 0;
            iCursor_ = 0;
            throw new CDynamicDataContainerException(e.getMessage());
        } catch (CBeanException e) {
            throw new CDynamicDataContainerException("createPreparedStatement() "
                    + e.getMessage());
        } catch (SecurityException e) {
            throw new CDynamicDataContainerException("createPreparedStatement() "
                    + e.getMessage());
        } catch (NoSuchMethodException e) {
        	e.printStackTrace();
            throw new CDynamicDataContainerException("createPreparedStatement() "
                    + e.getMessage());
        } finally {
            try {
                if (pss != null)
                    pss.close();
            } catch (SQLException e) {
                // nothing is to be done
            }
        }
        return (isDataContainerPopulated);
    }

    private boolean checkEmpty(String pstr) {
        if (pstr != null) {
            if (!pstr.equals("")) {
                return (true);

            }
        }
        return (false);

    }

    private int getResultSetFetchSize(int iDefaultFetchSize) {
        int iFetchSize = 100;
        if (iDefaultFetchSize > iFetchSize) {
            iFetchSize = iDefaultFetchSize;
        }
        if (MAX_FETCH_SIZE_ < iFetchSize) {
            return MAX_FETCH_SIZE_;
        } else {
            return iFetchSize;
        }
    }

} // end of CDynamicDataContainer.java
