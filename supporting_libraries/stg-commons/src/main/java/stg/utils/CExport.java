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
package stg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * CExport writes line by line to form a text file depending on the format
 * placed in properties file.
 * <UL>
 * <LI><B>Ussage:</B> <TABLE BORDER="0" CELLPADDING="1" CELLSPACING="0"
 * WIDTH="100%">
 * <TD COLSPAN=2> <FONT face="Garmond" size=4 >
 * <TR>
 * <TD WIDTH=40%>CExport e = new CExport("test.export");</TD>
 * <TD WIDTH=40%>//Construct an object of CExport </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.newLine();</TD>
 * <TD WIDTH=40%>//<B><i>Very Important</i></B> Initialize the CExport
 * Object </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.setRecordType("dtlRec");</TD>
 * <TD WIDTH=40%>//Sets the type of record. </TR>
 * <TR>
 * <TD WIDTH=40%>e.newLine(); </TD>
 * <TD WIDTH=40%>//<B><i>Very Important</i></B> Initialize the CExport
 * Object Line to that of Record Type </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.setString(1,"TestData"); </TD>
 * <TD WIDTH=40%>//Sets String </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.setLong(2, 123); </TD>
 * <TD WIDTH=40%>//Sets Long </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.setDate(3, new Date()); </TD>
 * <TD WIDTH=40%>// Sets Date </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.setDouble(4, 999.99);
 * <TD WIDTH=40%>//Sets Double
 * <TR>
 * <TD WIDTH=40%>System.out.println(e.getLine());
 * <TD WIDTH=40%>//|TestData&nbsp&nbsp 000000012320000822000000999990| </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.newLine(); </TD>
 * <TD WIDTH=40%>//Checks for the record length, if matches with the total
 * length then initializes the CExport Object. </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>e.setDate(3, new Date()); </TD>
 * <TD WIDTH=40%>//sets Date </TD>
 * </TR>
 * <TR>
 * <TD WIDTH=40%>System.out.println (e.getLine()); </TD>
 * <TD WIDTH=40%> //|&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
 * 0000000000<B>20000822</B>000000000000| </TD>
 * </TR>
 * </FONT> </TABLE> <BR>
 * <BR>
 * <LI><B>Properties File: </B>&nbsp;&nbsp;&nbsp; <i>test.export</i><BR>
 * <BR>
 * <DT>#Total number of Fields In Detail Record
 * <DD>
 * <DT>dtlRec=4
 * <DD><BR>
 * <DT>#Total length of Detail Record
 * <DD>
 * <DT>dtlRecLength=40
 * <DD><BR>
 * <DT>#Formats In Detail Record
 * <DD>
 * <DT>dtlRec1=%-10s
 * <DD>
 * <DT>dtlRec2=%010d
 * <DD>
 * <DT>dtlRec3=yyyyMMdd
 * <DD>
 * <DT>dtlRec4=%012,3f
 * <DD><BR>
 * <BR>
 * <LI><B>Formats:</B>
 * <DD>Formats allowed are only <B>s</B>-String, <B>f</B>-Floating Point,
 * <B>d</B>-Decimal/Integer<BR>
 * <DT><i>For <b>Date</b>:</i>
 * <DD> <B>Avoid</B> usage of such formats where length varies from the actual
 * format length.<BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>E.g.</B>
 * dd-MMMM-yyyy --> 01-January-2000, 01-March-2000<BR>
 * <BR>
 * <DT><I>For <b>Manadatory Columns</b>:</I>
 * <DD> prefix formats with <B>^</B>. CExport will ensure that the value is set
 * using <B>Set Methods</B> rather than CExport filling it for you.<BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>E.g.</B>
 * dtlRec1=^%-20s means that value must be set using
 * {@link #setString(int, String)} method<BR>
 * <BR>
 * <DD>For more on Formats details <B>See</B>
 * {@link SimpleFormat#SimpleFormat( String )} <BR>
 * <BR>
 * <DT><i><b>Delimiters Allowed</b></i>
 * <DD>If a <b>#</b> delimited file is to be created then specify <b><i>delimiter=#</i></b>
 * as a property in the property file. <BR>
 * <BR>
 * <LI>Please Note:
 * <DD>If delimiter is specified then it is not mandatory to specify the Record
 * Length. <BR>
 * <BR>
 * <LI><B>Exceptions:</B> (at Run Time)
 * <DT><I>IllegalArgumentException</I>
 * <DD>If the format Specified in the Properties File does not match with the
 * value parsed.
 * <DT><I>CExportException</I>
 * <DD>will be followed with a self Explanatory Meaning
 * </DL>
 * </LI>
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 2 $
 */
public class CExport extends Object {

    // public instance constants and class constants

    // public instance variables

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2                 $";
    
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
     * Stores the property file structure of the desired output file. Comment
     * for <code>propExport_</code>.
     */
    private Properties propExport_ = new Properties();

    /**
     * Stores the formatted output string. Comment for
     * <code>strFormatedString_</code>.
     */
    private StringBuffer sbFormatedStringBuffer_;

    /**
     * Stores the type of the record that is being formatted. Comment for
     * <code>strRecordType_</code>.
     */
    private String strRecordType_;

    /**
     * Stores the file delimiter. Comment for <code>strDelimiter_</code>.
     */
    private String strDelimiter_;

    /**
     * Stores the last index set passed during the last call to the set methods.
     * Comment for <code>iLastIndex_</code>.
     */
    private int iLastIndex_;

    /**
     * Stores the total length of the line for a particular Record Type. 
     * Comment for <code>iTotalLength_</code>.
     */
    private int iTotalLength_;

    /**
     * Stores the total number of fields for a particular Record Type.
     * Comment for <code>iTotalFields_</code>.
     */
    private int iTotalFields_;

    /**
     * Stores true if the new line is explicitly called else false.
     * Comment for <code>isNewLineSet_</code>.
     */
    private boolean isNewLineSet_;

    /**
     * Stores true if the property attribute needs to be explicitly set.
     * Comment for <code>isMandatory_</code>.
     */
    private boolean isMandatory_;

    // private class(static) variables

    // constructors
    
    /**
     * Creates an CExport around the properties file.
     * 
     * @param pstrPropFile
     *            Properties File
     * @throws CExportException
     *             if the property file specified does not exists.
     * @throws FileNotFoundException
     *             Property File not found
     * @throws IOException
     *             Unable to read the property file.
     * 
     */
    public CExport(String pstrPropFile) throws CExportException,
            FileNotFoundException, IOException {
        this(new FileInputStream(new File(pstrPropFile)));
    }

    /**
     * Creates an CExport around the properties file.
     * 
     * @param pPropertiesFile Property file
     * @throws CExportException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public CExport(File pPropertiesFile) throws CExportException,
            FileNotFoundException, IOException {
        this(new FileInputStream(pPropertiesFile));
    }

    /**
     * Creates an CExport around the properties file.
     * 
     * @param pPropertiesInputStream Properties input stream.
     * @throws CExportException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public CExport(InputStream pPropertiesInputStream) throws CExportException,
    FileNotFoundException, IOException {
        loadFile(pPropertiesInputStream);
        sbFormatedStringBuffer_ = new StringBuffer();
        strRecordType_ = "";
        strDelimiter_ = "";
        
        iLastIndex_ = 0;
        iTotalLength_ = 0;
        iTotalFields_ = 0;
        
        isNewLineSet_ = false;
        isMandatory_ = false;
    }
    
    /**
     * Constructs an CExport for the given properties.
     * 
     * @param pProperties properties
     * @throws CExportException
     */
    public CExport(Properties pProperties) throws CExportException {
        propExport_.putAll(pProperties);
        try {
            strDelimiter_ = getFormat("delimiter");
        } catch (CExportException cee) {
            strDelimiter_ = "";
        }
        sbFormatedStringBuffer_ = new StringBuffer();
        strRecordType_ = "";
        strDelimiter_ = "";
        
        iLastIndex_ = 0;
        iTotalLength_ = 0;
        iTotalFields_ = 0;
        
        isNewLineSet_ = false;
        isMandatory_ = false;
    	
    }

    /**
     * Constructs CExport with the given properties and delimiter.
     * @param pProperties
     * @param pDelimiter
     * @throws CExportException
     */
    public CExport(Properties pProperties, String pDelimiter) throws CExportException {
        if (pDelimiter == null || pDelimiter.equals("")) {
            throw new CExportException("Invalid delimiter");
        }
        
        this.strDelimiter_ = pDelimiter;
    	propExport_.putAll(pProperties);
    	sbFormatedStringBuffer_ = new StringBuffer();
    	strRecordType_ = "";
    	strDelimiter_ = "";
    	
    	iLastIndex_ = 0;
    	iTotalLength_ = 0;
    	iTotalFields_ = 0;
    	
    	isNewLineSet_ = false;
    	isMandatory_ = false;
    }
    
    /**
     * Constructs an CExport Object.
     * 
     * @param pstrPropFile
     *            Properties File to be loaded.
     * @param pstrdelimiter
     *            delimiter.
     * 
     * @throws CExportException
     *             if the delimiter specified is blank.
     * @throws FileNotFoundException
     *             if the property file specified does not exists.
     * @throws IOException
     *             if unable to read the property file.
     */
    public CExport(String pstrPropFile, String pstrdelimiter)
            throws CExportException, FileNotFoundException, IOException {
        this(new FileInputStream(new File(pstrPropFile)), pstrdelimiter);
    }
    /**
     * Constructs an CExport Object.
     * 
     * @param pstrPropFile
     *            Properties File to be loaded.
     * @param pstrdelimiter
     *            delimiter.
     * 
     * @throws CExportException
     *             if the delimiter specified is blank.
     * @throws FileNotFoundException
     *             if the property file specified does not exists.
     * @throws IOException
     *             if unable to read the property file.
     */
    public CExport(File pstrPropFile, String pstrdelimiter)
    throws CExportException, FileNotFoundException, IOException {
        this(new FileInputStream(pstrPropFile), pstrdelimiter);
    }

    /**
     * Constructs an CExport Object.
     * 
     * @param pInputStream
     *            Properties File input stream.
     * @param pstrdelimiter
     *            delimiter.
     * 
     * @throws CExportException
     *             if the delimiter specified is blank.
     * @throws FileNotFoundException
     *             if the property file specified does not exists.
     * @throws IOException
     *             if unable to read the property file.
     */
    public CExport(InputStream pInputStream, String pstrdelimiter)
    throws CExportException, FileNotFoundException, IOException {
        if (pstrdelimiter == null || pstrdelimiter.equals("")) {
            throw new CExportException("Invalid delimiter");
        }
        
        this.strDelimiter_ = pstrdelimiter;
        
        loadFile(pInputStream);
        
        sbFormatedStringBuffer_ = new StringBuffer();
        strRecordType_ = "";
        strDelimiter_ = "";
        
        iLastIndex_ = 0;
        iTotalLength_ = 0;
        iTotalFields_ = 0;
        
        isNewLineSet_ = false;
        isMandatory_ = false;
    }

    // finalize method, if any

    // main method

    // public methods of the class in the following order

    /**
     * Sets the record type for the formats that needs to be applied to the
     * current record set.
     * 
     * @param pstrType
     *            The Record identifier in the Property File.
     * @throws CExportException
     *             "Record Type not set" if parameter passed is null or "".
     */
    public void setRecordType(String pstrType) throws CExportException {
        if (!isNewLineSet_) {
            throw new CExportException("Object/Line Not Initialized");
        }
        checkRecordType(pstrType);
        strRecordType_ = pstrType;

        iTotalFields_ = Integer.parseInt(propExport_.getProperty(
                strRecordType_, "0"));
        if (iTotalFields_ <= 0) {
            throw new CExportException(
                    "Total Number Of Fields Not Specified. RecordType "
                            + strRecordType_);
        }
        if (strDelimiter_.length() == 0) {
            iTotalLength_ = Integer.parseInt(propExport_.getProperty(
                    strRecordType_ + "Length", "0"));

            if (iTotalLength_ <= 0) {
                throw new CExportException(
                        "Total Record Length Not Specified. RecordType "
                                + strRecordType_);
            }
        }
        isNewLineSet_ = false;
    }

    /**
     * Sets the string value in the specified format at the specified index.
     * 
     * <UL>
     * <LI> If the format specified in the Properties file is "%-20s" & the
     * value supplied to this method exceeds 20 then the first 20 characters
     * will be taken in to account.
     * </UL>
     * 
     * @param piIndex
     *            The record column index identifier in the Property File.
     * @param pstrValue
     *            String value.
     * @throws CExportException
     *             if the format cannot be parsed or any other exception is
     *             raised.
     */
    public void setString(int piIndex, String pstrValue) throws CExportException {
        checkIndex(piIndex);

        buildFiller(piIndex);

        if (pstrValue == null)
            pstrValue = "";

        String strValue = SimpleFormat.format(getFormat(strRecordType_
                + piIndex), pstrValue);

        if (strDelimiter_.length() > 0)
            strValue = strValue.trim() + strDelimiter_;

        setFormatedString(piIndex, strValue);
    }

    /**
     * Sets the integer value in the specified format at the specified index.
     * 
     * @param piIndex
     *            The record column index identifier in the Property File.
     * @param piValue
     *            integer value.
     * 
     * @throws CExportException
     *             if the format cannot be parsed or any other exception is
     *             raised.
     */
    public void setInt(int piIndex, int piValue) throws CExportException {
        checkIndex(piIndex);

        buildFiller(piIndex);

        String strValue = SimpleFormat.format(getFormat(strRecordType_
                + piIndex), piValue);
        if (strDelimiter_.length() > 0) {
            strValue = (piValue == 0) ? strDelimiter_ : strValue.trim()
                    + strDelimiter_;
        }

        setFormatedString(piIndex, strValue);
    }

    /**
     * Sets the long value in the specified format at the specified index.
     * 
     * @param piIndex
     *            The record column index identifier in the Property File.
     * @param plValue
     *            long value.
     * 
     * @throws CExportException
     *             if the format cannot be parsed or any other exception is
     *             raised.
     */
    public void setLong(int piIndex, long plValue) throws CExportException {
        checkIndex(piIndex);

        buildFiller(piIndex);

        String strValue = SimpleFormat.format(getFormat(strRecordType_
                + piIndex), plValue);
        if (strDelimiter_.length() > 0) {
            strValue = (plValue == 0) ? strDelimiter_ : strValue.trim()
                    + strDelimiter_;
        }

        setFormatedString(piIndex, strValue);
    }

    /**
     * Sets the double value in the specified format at the specified index.
     * 
     * @param piIndex
     *            The record column index identifier in the Property File.
     * @param pdValue
     *            double value.
     * 
     * @throws CExportException
     *             if the format cannot be parsed or any other exception is
     *             raised.
     */
    public void setDouble(int piIndex, double pdValue) throws CExportException {
        checkIndex(piIndex);

        buildFiller(piIndex);

        String strValue = SimpleFormat.format(getFormat(strRecordType_
                + piIndex), pdValue);

        if (strDelimiter_.length() > 0) {
            strValue = (pdValue == 0) ? strDelimiter_ : strValue.trim()
                    + strDelimiter_;
        }

        setFormatedString(piIndex, strValue);
    }

    /**
     * Sets the java.sql.date in the specified format at the specified index.
     * 
     * @param piIndex
     *            The record column index identifier in the Property File.
     * @param psdtValue
     *            java.sql.date.
     * 
     * @throws CExportException
     *             if the format cannot be parsed or any other exception is
     *             raised.
     */
    public void setDate(int piIndex, java.sql.Date psdtValue)
            throws CExportException {
        checkIndex(piIndex);

        buildFiller(piIndex);

        String strDateFormat = getFormat(strRecordType_ + piIndex);
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String strValue;
        try {
			strValue = sdf.format(psdtValue);
		} catch (NullPointerException e) {
			strValue="";
		}

        strValue = SimpleFormat.format("%-" + strDateFormat.length() + "s",
                strValue);

        if (strDelimiter_.length() > 0) {
            strValue = strValue.trim() + strDelimiter_;
        }

        setFormatedString(piIndex, strValue);
    }

    /**
     * Sets the java.sql.date in the specified format at the specified index.
     * 
     * @param piIndex
     *            The record column index identifier in the Property File.
     * @param ptsValue
     *            java.sql.date.
     * 
     * @throws CExportException
     *             if the format cannot be parsed or any other exception is
     *             raised.
     */
    public void setTimestamp(int piIndex, java.sql.Timestamp ptsValue)
            throws CExportException {
        checkIndex(piIndex);

        buildFiller(piIndex);

        String strDateFormat = getFormat(strRecordType_ + piIndex);
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String strValue;
        try {
			strValue = sdf.format(ptsValue);
		} catch (NullPointerException e) {
			strValue="";
		}


        strValue = SimpleFormat.format("%-" + strDateFormat.length() + "s",
                strValue);

        if (strDelimiter_.length() > 0) {
            strValue = strValue.trim() + strDelimiter_;
        }

        setFormatedString(piIndex, strValue);
    }

    /**
     * Sets the object at the specified index.
     * 
     * @param piIndex
     *            The record column index identifier in the Property File.
     * @param pobj
     *            java.lang.Object
     * @exception CExportException
     * @exception IllegalArgumentException
     *                thrown when an Object of type other than java.sql.Date,
     *                String, Integer, Long and Double is parsed.
     */
    public void setObject(int piIndex, Object pobj) throws CExportException {
        String strObjectOfType = pobj.getClass().getName();
        if (strObjectOfType.indexOf("java.lang.String") > -1) {
            setString(piIndex, (java.lang.String) pobj);
        } else if (strObjectOfType.indexOf("java.lang.Integer") > -1) {
            setInt(piIndex, ((java.lang.Integer) pobj).intValue());
        } else if (strObjectOfType.indexOf("java.lang.Long") > -1) {
            setLong(piIndex, ((java.lang.Long) pobj).longValue());
        } else if (strObjectOfType.indexOf("java.lang.Double") > -1) {
            setDouble(piIndex, ((java.lang.Double) pobj).doubleValue());
        } else if (strObjectOfType.indexOf("java.sql.Date") > -1) {
            setDate(piIndex, (java.sql.Date) pobj);
        } else if (strObjectOfType.indexOf("java.sql.Timestamp") > -1) {
            setTimestamp(piIndex, (java.sql.Timestamp) pobj);
        } else {
            throw new java.lang.IllegalArgumentException(
                    "Invalid Object parsed at Index " + piIndex + " Class "
                            + strObjectOfType);
        }
    }

    /**
     * Initializes the CExport Object to start a fresh line.
     * 
     * @throws CExportException
     *             If Record Length Does Not Match The Total Length Of The
     *             Record.
     */
    public void newLine() throws CExportException {
        if (strDelimiter_.length() == 0) {
            if (sbFormatedStringBuffer_.length() != 0
                    && sbFormatedStringBuffer_.length() != iTotalLength_) {
                throw new CExportException(
                        "Record Length Does Not Match With Total of Field Lengths");
            }
        }
        sbFormatedStringBuffer_.delete(0, sbFormatedStringBuffer_.length());
        iLastIndex_ = 0;
        isNewLineSet_ = true;
    }

    /**
     * This method returns the Formatted Line. This method ensures that the line
     * returned by this method will be to its fullest length with proper
     * formats. i.e. If the field is a date or character field then spaces will
     * be filled for its total length & if its a numeric field then according to
     * its format specified in the properties file.
     * <UL>
     * <LI>E.g.
     * <DL>
     * <DD>
     * <DT> Consider that there are 15 columns in a record. <BR>
     * Out of which you have set at the moment only first 5 fields/columns.<BR>
     * Then getLine() will ensure that the remaining 10 fields/columns will also
     * be appended to the string and a <i>line</i> will be returned. Of course,
     * there must not be any mandatory field in between 6 & 15 fields/columns.
     * </DL>
     * </UL>
     * 
     * @throws Exception
     *             "Fatal Error: Formatted String length exceeds Total Length of
     *             the Record"
     * @return String
     */
    public String getLine() throws Exception {
        buildFiller(iTotalFields_ + 1);
        if (strDelimiter_.length() == 0) {
            if (sbFormatedStringBuffer_.length() > iTotalLength_)
                throw new Exception(
                        "Fatal Error: Formatted String length exceeds Total Length of the Record");
        }
        isNewLineSet_ = false;
        return sbFormatedStringBuffer_.toString();
    }

    // protected constructors and methods of the class

    // package constructors and methods of the class

    // private constructors and methods of the class

    /**
     * Validates the type of the Record.
     * 
     * @param pstrType
     *            Record Type
     * @throws CExportException
     *             Exception is thrown if blank or null.
     */
    private void checkRecordType(String pstrType) throws CExportException {
        if (pstrType == null || pstrType.equals("")) {
            throw new CExportException("Invalid Record Type Set");
        }
    }

    /**
     * Validates the current column index of the the line based on the record
     * type.
     * 
     * If the current index is greater than the total number of fields then the
     * method throws exception. Also, if the current set index is less than the
     * last index value invoked through any set mehtods then an exception is
     * thrown.
     * 
     * @param piIndex
     *            Current index passed during the set commands.
     * @throws CExportException
     */
    private void checkIndex(int piIndex) throws CExportException {
        if (piIndex <= iLastIndex_) {
            throw new CExportException("Index " + piIndex
                    + " Already Set. Cannot Re-Set the value");
        }
        if (piIndex > iTotalFields_) {
            throw new CExportException("Index " + piIndex
                    + " exceeds the total no of fields " + iTotalFields_);
        }
    }

    /**
     * Adds the formatted string to the line at the appropriate column index position.
     * 
     * @param piIndex Column Index
     * @param pstr Value
     * @throws CExportException
     */
    private void setFormatedString(int piIndex, String pstr)
            throws CExportException {
        if (strDelimiter_.length() == 0) {
            if ((sbFormatedStringBuffer_ + pstr).length() > iTotalLength_) {
                throw new CExportException(
                        "Cannot Not Append. Formatted String length exceeds Total Length of the Record");
            }
        }
        sbFormatedStringBuffer_.append(pstr);
        iLastIndex_ = piIndex;
    }

    /**
     * Returns the format from the properties file.
     * 
     * @param pstrKey
     *            Format key.
     * @return String
     * @throws CExportException
     */
    private String getFormat(String pstrKey) throws CExportException {
        String strProperty = propExport_.getProperty(pstrKey);
        if (strProperty == null) {
            throw new CExportException("Property Not Defined. " + pstrKey);
        }
        if (strProperty.substring(0, 1).equals("^")) {
            isMandatory_ = true;
            strProperty = strProperty.substring(1);
        } else {
            isMandatory_ = false;
        }

        return (strProperty);
    }

    /**
     * Builds the line appropriate to the index set using one of the set
     * methods.
     * 
     * If the index is set to 5 where as the last call to set method had the
     * value as 2 then the build filler fills the columns 3 and 4 based on the
     * formats defined as in the properties file for that particular record
     * type.
     * 
     * @param piIndex
     *            Up to the index number.
     * @throws CExportException
     */
    private void buildFiller(int piIndex) throws CExportException {
        if (!isNewLineSet_) {
            throw new CExportException("Object/Line Not Initialized");
        }

        checkRecordType(strRecordType_);

        if (strDelimiter_.length() == 0) {
            if (sbFormatedStringBuffer_.length() > iTotalLength_) {
                throw new CExportException(
                        "Cannot Not Append. Formatted String length exceeds Total Length of the Record");
            }
        }
        for (int i = iLastIndex_ + 1; i < piIndex; i++) {
            String strFormat = getFormat(strRecordType_ + i);
            if (isMandatory_)
                throw new CExportException("Mandatory Column " + i + " ( "
                        + strFormat + " ) Needs to be Set.");
            if (strFormat.charAt(0) != '%')
                setDate(i, null);
            else {
                char lastChar = strFormat.charAt(strFormat.length() - 1);
                if (lastChar == 's')
                    setString(i, "");
                else if (lastChar == 'i')
                    setInt(i, 0);
                else if (lastChar == 'd')
                    setLong(i, 0);
                else if (lastChar == 'f')
                    setDouble(i, 0);
                else
                    throw new CExportException(
                            "Invalid Format in Properties Sheet for "
                                    + strRecordType_ + i);
            }
        }
    }

    /**
     * Loads the property file.

     * @param inputstream InputStream
     * @throws CExportException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void loadFile(InputStream inputstream) throws CExportException,
            FileNotFoundException, IOException {
        propExport_.load(inputstream);
        inputstream.close();
        try {
            strDelimiter_ = getFormat("delimiter");
        } catch (CExportException cee) {
            strDelimiter_ = "";
        }
    }

} // end of CExport.java
