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
package com.stgmastek.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * DAO Utility class to map a resultset into objects. It does a mapping from a
 * resultset hence returns the list of objects of the supplied class
 * 
 * It implements Singleton pattern
 * 
 * <B>Note:</B><p /> 
 * If the query is <i>select ABC_BCD, PQR from ZZZ </i> where
 * 'ABC_BCD' and 'PQR' are the columns from table 'ZZZ' then the
 * fields should be named as 'abcBcd' and 'pqr' respectively in
 * the POJO class. The expected methods in the POJO class is 
 * 'setAbcBcd' and 'setPqr' for the utility to invoke
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public final class ResultSetMapper {
	
	private static final Map<String, Class<?>[]> map;
	private static final Logger logger = Logger.getLogger(ResultSetMapper.class);
	
	static {
		map = new HashMap<String, Class<?>[]>();
		map.put("VARCHAR2", new Class<?>[]{String.class});
		map.put("NUMBER", new Class<?>[]{Integer.class, Long.class, Double.class});
		map.put("CHAR", new Class<?>[]{String.class});
		map.put("DATE", new Class<?>[]{java.util.Date.class, Long.class});
		map.put("TIMESTAMP", new Class<?>[]{Long.class});
		
		map.put("text", new Class<?>[]{String.class});
		map.put("varchar2", new Class<?>[]{String.class});
		map.put("number", new Class<?>[]{Integer.class, Long.class, Double.class});
		map.put("char", new Class<?>[]{String.class});
		map.put("date", new Class<?>[]{Long.class});
		map.put("timestamp", new Class<?>[]{Long.class});
		map.put("int8", new Class<?>[]{Long.class, Double.class, Integer.class});
		map.put("bool", new Class<?>[]{Boolean.class});
		map.put("varchar", new Class<?>[]{String.class});
		map.put("float8", new Class<?>[]{Double.class});
		map.put("int", new Class<?>[]{Integer.class});
		map.put("int4", new Class<?>[]{Integer.class});
		map.put("decimal", new Class<?>[]{Integer.class, Long.class, Double.class});
		map.put("float4", new Class<?>[]{Double.class});
		map.put("int2", new Class<?>[]{Integer.class});
		map.put("smallint", new Class<?>[]{Integer.class});
		map.put("integer", new Class<?>[]{Integer.class});
		map.put("bigint", new Class<?>[]{Integer.class});
		map.put("numeric", new Class<?>[]{Integer.class});
		map.put("real", new Class<?>[]{Double.class});
		map.put("double precision", new Class<?>[]{Double.class});
		map.put("bpchar", new Class<?>[]{String.class});
		
		map.put("ntext", new Class<?>[]{String.class});
		map.put("nvarchar", new Class<?>[]{String.class});
		map.put("datetime2", new Class<?>[]{Long.class});
		map.put("float", new Class<?>[]{Double.class,Integer.class});
	}
	
	
	
	/** Private mapper to map the SQL data types with the Java Data types */

	/**
	 * Private constructor to avoid outside instantiation It load the SQL data
	 * type to JAVA data type mapping class
	 */
	private ResultSetMapper() {
	};

	/**
	 * Public static method to get the handle of the class.
	 * 
	 * @return the instance of the ResultSetMapper class
	 * 
	 */
	public static ResultSetMapper getInstance() {
		ResultSetMapper mapper = new ResultSetMapper();
		return mapper;
	}

	/**
	 * The utility method that would map a resultset into instances of the class
	 * supplied for the given number of records. This method internally calls
	 * {@link #mapMultipleRecords(ResultSet, Class, int)} with maximumRecords parameter
	 * as -1.
	 * 
	 * @param <T>
	 *        Any POJO class with fields mapping that of the resultset. Note
	 *        that it follows the JAVA convention for attribute naming Ex.
	 *        If the query is <i>select ABC_BCD, PQR from ZZZ </i> where
	 *        'ABC_BCD' and 'PQR' are the columns from table 'ZZZ' then the
	 *        fields should be named as 'abcBcd' and 'pqr' respectively in
	 *        the POJO class
	 * 
	 * @param rs
	 *        The resultset containing the record set
	 * @param clazz
	 *        The POJO class
	 * @return List of instances of the POJO class supplied
	 * @throws Exception
	 *         Exception thrown during using the reflection API and or the
	 *         SQLException
	 */
	public <T> List<T> mapMultipleRecords(ResultSet rs, Class<T> clazz)
	throws Exception {
		return mapMultipleRecords(rs, clazz, -1);
	}
	
	/**
	 * The utility method that would map a resultset into instances of the class
	 * supplied for the given number of records.
	 * 
	 * @param <T>
	 *        Any POJO class with fields mapping that of the resultset. Note
	 *        that it follows the JAVA convention for attribute naming Ex.
	 *        If the query is <i>select ABC_BCD, PQR from ZZZ </i> where
	 *        'ABC_BCD' and 'PQR' are the columns from table 'ZZZ' then the
	 *        fields should be named as 'abcBcd' and 'pqr' respectively in
	 *        the POJO class
	 * 
	 * @param rs
	 *        The resultset containing the record set
	 * @param clazz
	 *        The POJO class
	 * @param maximumRecords
	 *        Maximum number of records to be retrieved. if <= zero then retrieve all.
	 * @return List of instances of the POJO class supplied
	 * @throws Exception
	 *         Exception thrown during using the reflection API and or the
	 *         SQLException
	 */
	public <T> List<T> mapMultipleRecords(ResultSet rs, Class<T> clazz, int maximumRecords)
			throws Exception {

		ResultSetMetaData rsmd = rs.getMetaData();
		Integer colCount = rsmd.getColumnCount();
		ArrayList<T> returnList = new ArrayList<T>();
		int recordCount = 0;
		rs.setFetchSize(500);
		while (rs.next()) {
			recordCount++;
			T obj = clazz.newInstance();
			for (int i = 1; i <= colCount; i++) {
				String methodName = getMethodName(rsmd.getColumnName(i));
				String sqlColumnType = rsmd.getColumnTypeName(i);
				Class<?>[] paramClasses = map.get(sqlColumnType);
				Method method = null;
				Class<?> paramClass = null;
				
				for(int k=0; k<paramClasses.length;k++){
					paramClass = paramClasses[k];
					try {
						method = clazz.getMethod(methodName, paramClass);
						break;
					} catch (NoSuchMethodException e) {
						logger.error(e);
					}
				}
				
				if(method == null){
					System.err.println("No matching method for " + methodName);
				}
				
				Object rsValue = rs.getObject(i);
				if (method != null && rsValue != null) {				
					if (paramClass == String.class){
						method.invoke(obj, (String)rsValue);
					}else if (paramClass == Integer.class){
						if(rsValue.getClass() == BigDecimal.class)
							method.invoke(obj, ((BigDecimal)rsValue).intValue());
						else if(rsValue.getClass() == Integer.class){
							method.invoke(obj, (Integer)rsValue);
						}
						else if(rsValue.getClass() == Long.class){ 
							//hack for classes that defined attribute as Integer
							method.invoke(obj, Integer.valueOf(((Long)rsValue).intValue()));
						}
					}else if (paramClass == Long.class){
						if(sqlColumnType.equalsIgnoreCase("TIMESTAMP")||sqlColumnType.equalsIgnoreCase("datetime2"))
							method.invoke(obj, (rs.getTimestamp(i)).getTime());
						else if(sqlColumnType.equalsIgnoreCase("DATE"))
							method.invoke(obj, (rs.getDate(i)).getTime());
						else
							if(rsValue.getClass() == BigDecimal.class)
								method.invoke(obj, ((BigDecimal)rsValue).longValue());
							else
								method.invoke(obj, ((Long)rsValue));
								
					}else if (paramClass == Double.class){
						if(rsValue.getClass() == BigDecimal.class)
							method.invoke(obj, ((BigDecimal)rsValue).doubleValue());
						else if (rsValue.getClass() == Long.class) {
							method.invoke(obj, new Double(((Long)rsValue).doubleValue()));
						} else {
							method.invoke(obj, ((Double)rsValue));
						}
					}
				}
			}
			returnList.add(obj);
			if (maximumRecords > 0) {
				if (recordCount >= maximumRecords) {
					break;
				}
			}
		} //while rs.next
		return returnList;
	}

	/**
	 * The utility method that would map a resultset into a scalar object
	 * Specially written for result sets that would return a single record
	 * 
	 * @param <T>
	 *        Any POJO class with fields mapping that of the resultset. Note
	 *        that it follows the JAVA convention for attribute naming Ex.
	 *        If the query is <i>select ABC_BCD, PQR from ZZZ </i> where
	 *        'ABC_BCD' and 'PQR' are the columns from table 'ZZZ' then the
	 *        fields should be named as 'abcBcd' and 'pqr' respectively in
	 *        the POJO class
	 * 
	 * @param rs
	 *        The resultset containing the record set
	 * @param clazz
	 *        The POJO class
	 * @return An instances of the POJO class supplied
	 * @throws Exception
	 *         Exception thrown during using the reflection API and or the
	 *         SQLException
	 */
	public <T> T mapSingleRecord(ResultSet rs, Class<T> clazz)
			throws Exception {

		ResultSetMetaData rsmd = rs.getMetaData();
		Integer colCount = rsmd.getColumnCount();
		T obj = null;
		if(rs.next()){
			obj = clazz.newInstance();
			for (int i = 1; i <= colCount; i++) {
				String methodName = getMethodName(rsmd.getColumnName(i));
				String sqlColumnType = rsmd.getColumnTypeName(i);
				Class<?>[] paramClasses = map.get(sqlColumnType);
				Method method = null;
				Class<?> paramClass = null;
	
				for(int k=0; k<paramClasses.length;k++){
					paramClass = paramClasses[k];
					try {
						method = clazz.getMethod(methodName, paramClass);
						break;
					} catch (NoSuchMethodException e) {
						logger.error(e);
					}
				}
				
				if(method == null){
					System.err.println("No matching method for " + methodName);
				}
	
				Object rsValue = rs.getObject(i);			
				if (method != null && rsValue != null) {				
					if (paramClass == String.class){
						method.invoke(obj, (String)rsValue);
					}else if (paramClass == Integer.class){
						if(rsValue.getClass() == BigDecimal.class)
							method.invoke(obj, ((BigDecimal)rsValue).intValue());
						else if(rsValue.getClass() == Integer.class){
							method.invoke(obj, (Integer)rsValue);
						}
						else if(rsValue.getClass() == Long.class){ 
							//hack for classes that defined attribute as Integer and corresponding Database column length is Long
							method.invoke(obj, Integer.valueOf(((Long)rsValue).intValue()));
						}
					}else if (paramClass == Long.class){
						if(sqlColumnType.equalsIgnoreCase("TIMESTAMP")||sqlColumnType.equalsIgnoreCase("datetime2"))
							method.invoke(obj, (rs.getTimestamp(i)).getTime());
						else if(sqlColumnType.equalsIgnoreCase("DATE"))
							method.invoke(obj, (rs.getDate(i)).getTime());
						else
							if(rsValue.getClass() == BigDecimal.class)
								method.invoke(obj, ((BigDecimal)rsValue).longValue());
							else
								method.invoke(obj, ((Long)rsValue));

					}else if (paramClass == Double.class){
						if(rsValue.getClass() == BigDecimal.class)
							method.invoke(obj, ((BigDecimal)rsValue).doubleValue());
						else if (rsValue.getClass() == Long.class) {
							method.invoke(obj, new Double(((Long)rsValue).doubleValue()));
						} else {
							method.invoke(obj, ((Double)rsValue));
						}
					}
				}
			}
		}
		return obj;
	}	
	
	
	/**
	 * Helper method to get derive the method name from the column name
	 * 
	 * @param colName
	 *        The column name as fetched from the result set meta data
	 * @return the method name i.e. set[Column Name]. Ex: If the column name is
	 *         'ABC_BCD' then the method name it would derive is 'setAbcBcd'.
	 *         For 'PQR' it would be 'setPqr'
	 */
	private String getMethodName(String colName) {
		char[] chars = colName.toLowerCase().toCharArray();
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toUpperCase(chars[0]));
		for (int i = 1; i < chars.length; i++) {
			if (chars[i] != '_')
				sb.append(chars[i]);
			else
				sb.append(Character.toUpperCase(chars[++i]));
		}
		return "set" + sb.toString();
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/ResultSetMapper.java                                                                                $
 * 
 * 4     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 3     3/17/10 11:57a Kedarr
 * Added functionality to retrieve given number of records during the mapMultipleRecords method.
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/