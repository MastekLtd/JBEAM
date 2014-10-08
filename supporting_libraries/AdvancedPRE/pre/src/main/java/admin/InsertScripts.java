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
 */
package admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import stg.utils.CDate;
/**
    Creates Insert Scripts for a given table. It generates files for Oracle & Microsoft SQL Server.
    
*/
public class InsertScripts {

    private Connection cn = null;
    private String dbType = "";
    private LinkedList<String> insertList = null;
    private LinkedList<String> valuesList = null;
    private PrintWriter writer = null;
    private LinkedList<String> deleteList = null;
    private String path = "";                           //Path for crearing Bean File

    private boolean isUnableToCreate = false;
    private boolean isAppendToFile = false;

    private String fileName = "";
    
    private boolean bRollbackStatement = false;
    private String strRollbackFileName_ = "";
    private Integer lastTableRowCount;

    /** 
     * Constructs an object for the supplied connection.
     * 
     * @param con Connection
     * @throws Exception
    */
    public InsertScripts(Connection con) throws Exception {
        if (con == null) {
            throw new Exception("Connection not established");
        }
        cn = con;
        dbType = getDatabase(cn);
    }

    /** Set the database type other than the connection. Valid are ORACLE & MSSQL
        @param type String ORACLE or MSSQL
        @throws Exception
    */
    public void setDatabaseType(String type) throws Exception {
        if ( (!type.equals("ORACLE")) && (!type.equals("MSSQL")) ) {
            throw new Exception ("Invalid Database Type Specified. Valid Types are ORACLE & MSSQL" );
        }
        dbType = type;
    }
    

    /**
     * Set the path in which the files are to be generated.
     * Use this either this method or {@link #setFile(String, boolean)}. Both will not work.
     * In case this method is used then simply calling {@link #onTable(String)} methods will 
     * generate the file name as per the table name and store it in the given path.
     * 
     * @param path String
    */
    public void setPath(String path){
        if( path == null ) path = "";
        this.path = path;
    }

    /**
     * Sets the file in which the insert scripts are to be generated.
     *  
     * @param file output file.
     * @param brollback in case the rollback script is to be created.
     * @throws Exception
     */
    public void setFile(String file, boolean brollback) throws Exception{
        bRollbackStatement = brollback;
        if (bRollbackStatement)
            deleteList = new LinkedList<String>();
        fileName = file;
        writer = createFile(file);
    }

    /**
     * Set true to append to the file.
     *  
     * @param b boolean
     */
    public void setAppendToFile( boolean b ){
        isAppendToFile = b;
    }

    /**
     * Generates the scripts on the given table name.
     * @param tableName
     * @throws Exception
     */
    public void onTable(String tableName) throws Exception{
        onTable(tableName, null);
    }

    /**
     * Generates the insert script on the given table with the given where clause.
     * 
     * @param tableName
     * @param whereClause
     * @throws Exception
     */
    public void onTable(String tableName, String whereClause) throws Exception{
        if (whereClause == null) whereClause = "";
        if (writer == null)
        {
			writer = createFile(tableName);
        }
        System.out.print(tableName + " " + whereClause);
        if (bRollbackStatement) 
        {
            deleteList.addFirst("DELETE FROM " + tableName + " " + whereClause + " ;");
        }
        lastTableRowCount = 0;
        Statement st = null;
        ResultSet rs = null;
        try{
            st = cn.createStatement();
            rs = st.executeQuery("Select * from " + tableName + " " + whereClause);
            ResultSetMetaData rsmd = rs.getMetaData();
            int totalColumns = rsmd.getColumnCount();
            insertList = new LinkedList<String>();
            String insertInto = "Insert Into " + tableName + "(";
            for (int i = 1; i <= totalColumns; i++) {
                insertInto += rsmd.getColumnName(i) + ((i < totalColumns)?", ":"");
                if (insertInto.length() > 80 ){
                    insertList.addLast(insertInto);
                    insertInto = "";
                }
            }
            insertInto += ") values ( ";
            insertList.addLast(insertInto);
            valuesList = new LinkedList<String>();
            String values = "";
            while (rs.next()) {
            	lastTableRowCount++;
               for (int i = 1; i <= totalColumns; i++) {
                   String type = getDataType( rsmd.getColumnType(i) );
                   if (type.equals("String")) {
                       String output = rs.getString(i);
                       if (output != null) values += characterTerminator_ +  rectifyData(output) + characterTerminator_;
                       else values += "null";
                   }
                   else if (type.equals("Date")) {
                       String output = CDate.getUDFDateString(rs.getDate(i), getDateFormat());
                       if (output != null ) values += getDateTypeSQLSyntaxPrefix() + characterTerminator_ + output + characterTerminator_ + getDateTypeSQLSyntaxSuffix();
                       else values += "null";
                   }
                   else if (type.equals("Timestamp")) {
                       String output = CDate.getUDFDateString(rs.getDate(i), getTimestampFormat());
                       if (output != null ) values += getTimestampTypeSQLSyntaxPrefix() + characterTerminator_ + output + characterTerminator_ + getTimestampTypeSQLSyntaxSuffix();
                       else values += "null";
                   } else {
                       values += "" + rs.getString(i);
                   }
                   if (i < totalColumns) values += ", ";
                   if (values.length() > 80) {
                       valuesList.addLast( values );
                       values = "";
                   }
               }
               values += ")" + getSQLTerminator();
               valuesList.addLast( values );
               ListIterator<String> i = insertList.listIterator(0);
               while (i.hasNext()) {
                   print( i.next() );
               }
               i = valuesList.listIterator();
               while (i.hasNext()) {
                   print( i.next() );
               }
               print("");
               values = "";
               valuesList.clear();
            }
            writer.flush();
        }
        catch(Exception e){
            e.printStackTrace();
            isUnableToCreate = true;
        }
        finally{
            insertList.clear();
            insertList = null;
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                // do nothing
            }
            try {
                if (st != null) st.close();
            } catch (Exception e) {
                // Do nothing
            }
            if(!isUnableToCreate) {
                System.out.println("\t.........Done.");
            } else {
                deleteFile(new File(fileName));
                if (bRollbackStatement)
                {
                    deleteFile(new File(strRollbackFileName_));
                }
            }
        }
    }
    
    public Integer lastTableRowCount() {
    	return lastTableRowCount;
    }


    /**
     * Closes the file.
     * 
     * @throws Exception
     */
    public void close() throws Exception
    {
       if (writer != null) writer.close();
       if (bRollbackStatement)
       {
           createRollbackFile();
           if (writer != null) writer.close();
       }
    }


    private String getDataType(int dataType ) throws SQLException{
        String returnValue = "";
        switch (dataType) {
        case java.sql.Types.VARCHAR:
            returnValue="String";
            break;
        case java.sql.Types.TIMESTAMP:
            returnValue="Timestamp";
            break;
        case java.sql.Types.DATE:
            returnValue="Date";
            break;
        case java.sql.Types.NUMERIC:
            returnValue="double";
            break;
        case java.sql.Types.BIGINT:
            returnValue="String";
            break;
        case java.sql.Types.LONGVARCHAR:
            returnValue="long";
            break;
        case java.sql.Types.VARBINARY:
            returnValue="long";
            break;
        case java.sql.Types.LONGVARBINARY:
            returnValue="long";
            break;
        case java.sql.Types.TIME:
            returnValue="Timestamp";
            break;
        case java.sql.Types.CHAR:
            returnValue="String";
            break;
        case java.sql.Types.TINYINT:
            returnValue="int";
            break;
        case java.sql.Types.INTEGER:
            returnValue="int";
            break;
        default:
            System.out.println("Defaulted to String. Data Type #" + dataType + " could not be recognized.");
            returnValue="String";
            break;
        }
        return returnValue;
    }

    private PrintWriter createFile(String tableFileName) throws Exception{
        String fileSufix = dbType.equals("ORACLE")?"_ORA":"_MSQ";
//        if(isWriteBeanClosed) throw new Exception( "Object is Closed" );
        if(fileName.equals("")) fileName = path + tableFileName + fileSufix + ".sql";
        File f = new File(fileName);
        if (f.exists() && isAppendToFile) System.out.println("Appending to File " + fileName);
        else if ( f.exists() && !isAppendToFile) throw new Exception(".....File Already Exists.");
        else System.out.println("Creating File " + fileName);
        isUnableToCreate = false;
        return new PrintWriter(new BufferedWriter(new FileWriter(fileName, isAppendToFile)), true);
    }

    private void print(Object s) throws Exception{
        if (writer != null) writer.println(s);
    }

    /**
     * Prints the comment in the file.
     * 
     * @param s
     * @throws Exception
     */
    public void printComment(String s) throws Exception{
        print("/****************************************************************");
        print("** " + s);
        print("****************************************************************/");
        print(" ");
    }
    
    /**
     * Prints the given string.
     * @param s
     * @throws Exception
     */
    public void println(String s) throws Exception
    {
        print(s);
        print("");
    }
    
    /**
     * Adds prompt in the insert scripts.
     * 
     * @param s
     * @throws Exception
     */
    public void echo(String s) throws Exception
    {
        print("");
        print("prompt " + s);
        print("");
    }

    private  String getDatabase(Connection cn ) throws SQLException{
        DatabaseMetaData dmd = cn.getMetaData();
        String dbProductName = dmd.getDatabaseProductName();

        if( dbProductName.indexOf("Oracle") >= 0 ) return "ORACLE";
        else if( dbProductName.indexOf("Microsoft SQL Server") >= 0 ) return "MSSQL";
        return "";
    }

	/**
	 * Main method for testing.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
    {
	    Connection con = null;
        Context ctx = null;  
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://10.84.133.155:7023");
        try
        {
            ctx = new InitialContext(ht);
            javax.sql.DataSource ds  = (javax.sql.DataSource) ctx.lookup ("RENAISSANCE");
            con = ds.getConnection();

            InsertScripts objIs = new InsertScripts(con);
            if (args.length == 1)
            {
				objIs.onTable(args[0]);
            }
            else if (args.length == 2)
            {
				objIs.onTable(args[0], args[1]);
            }
            else
            {
                throw new IllegalArgumentException("Call InsertScripts <tablename> <wherecondition>");
            }
            objIs.close();
        }
        finally
        {
        	try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (SQLException e)
            {
                //dummy catch
            }
        }
     }

	private String rectifyData(String data)
    {
	    StringBuffer sbuffer = new StringBuffer(data);
	    int iIndex = -2;
	    while ((iIndex = sbuffer.indexOf(characterTerminator_+"", iIndex+2)) > -1)
	    {
	        sbuffer.replace(iIndex,iIndex, characterTerminator_+"");
	    }
	    return sbuffer.toString();
    } // end of rectifyData
	
	private void createRollbackFile() throws Exception
	{
        if (bRollbackStatement)
        {
            System.out.println("Creating Rollback Statements...");
            fileName = fileName + ".ROLLBACK";
            writer = createFile(fileName);
            System.out.println("Rollback");
            for (Iterator<String> iter = deleteList.iterator(); iter.hasNext();)
            {
                String strDeleteStatement = (String) iter.next();
                System.out.println(strDeleteStatement);
                writer.println(strDeleteStatement);
            }
            writer.flush();
        }
	}
	
	/**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    private static final String REVISION = "$Revision:: 2726              $";
    
    private String strSQLTerminator_;
    private String strDateTypeSQLSyntaxPrefix_;
    private String strDateTypeSQLSyntaxSuffix_;
    private String strTimestampTypeSQLSyntaxPrefix_;
    private String strTimestampTypeSQLSyntaxSuffix_;
    private String strDateFormat_;
    private String strTimestampFormat_;
    private char characterTerminator_ = '\'';

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
     * Set the SQL terminator.
     * Most of the cases the SQL terminator is a ;.
     * @param pstrSQLTerminator
     */
    public void setSQLTerminator(String pstrSQLTerminator) {
        this.strSQLTerminator_ = pstrSQLTerminator;
    }
    
    /**
     * Returns the SQL terminator.
     * Default returns ;.
     * @return String
     */
    public String getSQLTerminator() {
        return ((strSQLTerminator_ == null)?";":strSQLTerminator_);
    }

    /**
     * Define the prefix and suffix of the SQL Date syntax.
     * In case of Oracle prefix would be <b<to_date(</b> and suffix would be <b>, 'mm/dd/yyyy')</b>
     * @param prefix
     * @param suffix
     */
    public void setDateTypeSQLSyntax(String prefix, String suffix) {
        this.strDateTypeSQLSyntaxPrefix_ = prefix;
        this.strDateTypeSQLSyntaxSuffix_ = suffix;
    }
    
    /**
     * Return the prefix for SQL date syntax.
     * Defaults to <b>to_date(</b>.
     * @return String
     */
    public String getDateTypeSQLSyntaxPrefix() {
        return ((strDateTypeSQLSyntaxPrefix_ == null)?"to_date(":strDateTypeSQLSyntaxPrefix_);
    }

    /**
     * Return the suffix for SQL date syntax.
     * Defaults to <b>, 'mm/dd/yyyy')</b>.
     * @return String
     */
    public String getDateTypeSQLSyntaxSuffix() {
        return ((strDateTypeSQLSyntaxSuffix_ == null)?", 'mm/dd/yyyy')":strDateTypeSQLSyntaxSuffix_);
    }

    /**
     * Define the prefix and suffix of the SQL Timestamp syntax.
     * In case of Oracle prefix would be <b<to_date(</b> and suffix would be <b>, 'mm/dd/yyyy')</b>
     * @param prefix
     * @param suffix
     */
    public void setTimestampTypeSQLSyntax(String prefix, String suffix) {
        this.strTimestampTypeSQLSyntaxPrefix_ = prefix;
        this.strTimestampTypeSQLSyntaxSuffix_ = suffix;
    }
    
    /**
     * Return the prefix for SQL Timestamp syntax.
     * Defaults to <b>to_date(</b>.
     * @return String
     */
    public String getTimestampTypeSQLSyntaxPrefix() {
        return ((strTimestampTypeSQLSyntaxPrefix_ == null)?"to_date(":strTimestampTypeSQLSyntaxPrefix_);
    }
    
    /**
     * Return the suffix for SQL Timestamp syntax.
     * Defaults to <b>, 'mm/dd/yyyy hh24:mi:ss')</b>.
     * @return String
     */
    public String getTimestampTypeSQLSyntaxSuffix() {
        return ((strTimestampTypeSQLSyntaxSuffix_ == null)?", 'mm/dd/yyyy hh24:mi:ss')":strTimestampTypeSQLSyntaxSuffix_);
    }
    
    /**
     * Set the SimpleDateFormat format for converting SQL Date to a java literal.
     * @param format
     */
    public void setDateFormat(String format) {
        this.strDateFormat_ = format;
    }
    
    /**
     * Returns the Date format.
     * Defaults to <b>MM/dd/yyyy</b>.
     * @return String
     */
    public String getDateFormat() {
        return ((strDateFormat_ == null)?"MM/dd/yyyy":strDateFormat_);
    }
    
    /**
     * Set the SimpleDateFormat format for converting SQL Timestamp to a java literal.
     * @param format
     */
    public void setTimestampFormat(String format) {
        this.strTimestampFormat_ = format;
    }
    
    /**
     * Returns the Timestamp format.
     * Defaults to <b>MM/dd/yyyy HH:mm:ss</b>.
     * @return String
     */
    public String getTimestampFormat() {
        return ((strTimestampFormat_ == null)?"MM/dd/yyyy HH:mm:ss":strTimestampFormat_);
    }
    
    /**
     * Set the character terminator of an SQL.
     * Most of the cases it is a single quote <b>'</b>.
     * @param c
     */
    public void setCharacterTerminator(char c) {
        this.characterTerminator_ = c;
    }
    
    /**
     * Returns the character terminator.
     * Defaults to <b>'</b>.
     * @return char
     */
    public char getCharacterTerminator() {
        return characterTerminator_;
    }
    
    private void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Deleted file " + file.getName());
        } else {
            System.out.println("Unable to delete the file " + file.getName() + ". Please delete it manually.");
        }
    }
}

