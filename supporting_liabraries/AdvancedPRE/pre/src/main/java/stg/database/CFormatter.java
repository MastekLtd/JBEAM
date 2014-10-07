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
 * $Revision: 2717 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/database/CFormatter.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/database/CFormatter.java $
 * 
 * 7     9/01/09 9:05a Kedarr
 * Changes made to catch sepcific exceptions.
 * 
 * 6     2/04/09 3:18p Kedarr
 * Added static keyword to a final variable.
 * 
 * 5     3/22/08 3:45p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 4     7/26/05 11:24a Kedarr
 * Updated for JavaDoc for missing tags
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.3  2004/05/03 06:28:28  kedar
* Un used method/variables commented out.
*
 * 
 * 2     12/09/03 9:35p Kedarr
 * Removed UnUsed variables where ever possible and made
 * the necessary changes.
* Revision 1.2  2003/12/09 16:02:08  kedar
* Removed UnUsed variables where ever possible and made
* the necessary changes.
*
 * 
 * 1     12/01/03 1:28p Kedarr
* Revision 1.1  2003/11/20 11:46:18  kedar
* Initial Versions
*
*
*/


package stg.database;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;

import stg.utils.CDate;
import stg.utils.SimpleFormat;

/**
* CFormatter class formats and sets the values inside Source Object into Target Object.
*
* For CFormatter to work it requires two objects having same method names and the number
* of parameters for the method.
* Please make it a point to follow these standards when
* writing the source code.
**/
public class CFormatter extends Object
{
    
    
    //public instance constants and class constants
    
    //public instance variables
    
    //public class(static) variables
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    private static final String REVISION = "$Revision:: 2717              $";

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

    //protected instance constants and class constants
    
    //protected instance variables
    
    //protected class(static) variables
    
    
    //package instance constants and class constants
    
    //package instance variables
    
    //package class(static) variables
    
    
    //private instance constants and class constants
    
    //private instance variables
    private String strFormatDate_  = "dd/MM/yyyy";

    private String strFormatTime_  = "HH:mm:ss";
    
    private SimpleFormat sfDouble  = null;

    private SimpleFormat sfInteger = null;
    
    //private class(static) variables
    
    
    //constructors
    
    /**
    * Standard constructor for the class
    **/
    public CFormatter()
    {
        super();
    }
    
    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order
    
    /**
    * Sets the Date Format. Default is <b>dd/MM/yyyy</b>.
    *
    * The Date & Time Format is concatenated to format a java.sql.Timestamp
    *
    * @param   pstrFormat   String Format.
    **/
    public void setDateFormat(String pstrFormat) {
        if (pstrFormat == null || pstrFormat.equals("")) return;
        strFormatDate_ = pstrFormat;
    }

    /**
    * Returns the current Date Format used.
    * @return String
    **/
    public String getDateFormat() {
        return strFormatDate_;
    }

    /**
    * Sets the Time Format. Default is <b>HH:mm:ss</b>.
    *
    * The Date & Time Format is concatenated to format a java.sql.Timestamp
    * 
    * @param   pstrFormat   String Format.
    **/
    public void setTimeFormat(String pstrFormat) {
        if (pstrFormat == null || pstrFormat.equals("")) return;
        strFormatTime_ = pstrFormat;
    }

    /**
    * Returns the current Time Format used.
    * @return String
    **/
    public String getTimeFormat() {
        return strFormatTime_;
    }


    /**
    * Sets the Double Format for double numbers.
    *
    * This format must be in the SimpleFormat Specifications
    *
    * @param   pstrFormat   String Format.
    **/
    public void setDoubleFormat(String pstrFormat) {
        if (pstrFormat == null) sfDouble = null;
        else if (pstrFormat.equals("")) sfDouble = null;
        else
        {
            sfDouble = new SimpleFormat(pstrFormat);
        }
    }


    /**
    * Sets the Integer Format for integer or long numbers.
    *
    * This format must be in the SimpleFormat Specifications
    *
    * @param   pstrFormat   String Format.
    **/
    public void setIntegerFormat(String pstrFormat) {
        if (pstrFormat == null) sfInteger = null;
        else if (pstrFormat.equals("")) sfInteger = null;
        else
        {
            sfInteger = new SimpleFormat(pstrFormat);
        }
    }

    /**
    * Clones the contents of the From Object to To Object.
    *
    *
    * @param   pobjFromBean   An Object of Bean from which Values are to be Read.
    * @param   pobjToBean     An Object of Bean from which Values are to be Set.
    * @throws CFormatterException
    **/
    public void clone(Object pobjFromBean, Object pobjToBean) throws CFormatterException{

        try{

            Class<?> objFromClass = pobjFromBean.getClass();
            Class<?> objToClass = pobjToBean.getClass();

            Method[] objAllFromMethods = objFromClass.getMethods();
            Method[] objAllToMethods = objToClass.getMethods();
//            Method   objFromMethod = null;
            Method   objToMethod = null;

//            Object objObject = null;

//            Class[] aryFromClassType = {};
            Class<?>[] aryToClassType   = {};

            Class<?> fromReturnClassType = null;
//            Class toReturnClassType = null;
            objToMethod = objToClass.getMethod("initialize", aryToClassType);
            objToMethod.invoke(pobjToBean, new Object[] {});

            for (int i=0; i<objAllFromMethods.length; i++) {
                String strFromMethodName = objAllFromMethods[i].getName();
                if (strFromMethodName.substring(0,3).equals("get")) {
                    String strSetMethodName = "set" + strFromMethodName.substring(3);
                    int indexOfToMethod = checkMethodInTO(strSetMethodName, objAllToMethods);
                    if ( indexOfToMethod != -1) {
//                        aryFromClassType = objAllFromMethods[i].getParameterTypes();
                        fromReturnClassType = objAllFromMethods[i].getReturnType();
                        aryToClassType = objAllToMethods[indexOfToMethod].getParameterTypes();

                        Object objReturned = objAllFromMethods[i].invoke(pobjFromBean, new Object[] {} );
                        String strReturnType = fromReturnClassType.getName();
                        String strToClassType = aryToClassType[0].getName();

                        if (strToClassType.equals(strReturnType)) {
                            if (objReturned != null ){
                                if (objReturned instanceof String) //If the objReturned is equals "" then it should be treated as null
                                {
                                    if (!((String)objReturned).equals(""))
                                    {
                                        objToMethod = objToClass.getMethod(strSetMethodName, aryToClassType);
                                        objToMethod.invoke(pobjToBean, new Object[] {objReturned});
                                    }
                                    //else do nothing
                                }
                                else
                                {
                                    objToMethod = objToClass.getMethod(strSetMethodName, aryToClassType);
                                    objToMethod.invoke(pobjToBean, new Object[] {objReturned});
                                }
                            }
                        }
                        else {
                            Object objConverted = convert(objReturned, strReturnType, strToClassType );
                            objToMethod = objToClass.getMethod(strSetMethodName, aryToClassType);
                            if (objConverted == null ) {
//                               objToMethod.invoke(pobjToBean, new Object[] {null});
                            }
                            else objToMethod.invoke(pobjToBean, new Object[] {objConverted});
                        }
                    }
                    //else do nothing.
                }
            }
        }
        catch(NullPointerException e)
        {
            throw new CFormatterException(CMessages.NULL_POINTER);
        } catch (IllegalArgumentException e) {
            throw new CFormatterException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new CFormatterException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new CFormatterException(e.getMessage());
        } catch (SecurityException e) {
            throw new CFormatterException(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new CFormatterException(e.getMessage());
        }
    }
    
    //protected constructors and methods of the class
    
    //package constructors and methods of the class
    
    //private constructors and methods of the class

    private int checkMethodInTO(String pstrMethodName, Method[] pmthdArray) {
        int index = -1;
        for (int i=0; i<pmthdArray.length; i++) {
            if (pmthdArray[i].getName().equals(pstrMethodName)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private Object convert(Object pobjData, String pobjDataOfType, String pobjToType) throws CFormatterException{
        try
        {
            if (pobjData == null || pobjData.equals("")) {
                return null;
            }
            if (pobjDataOfType.indexOf("String") > -1) {
                if (pobjToType.indexOf("Timestamp") > -1) {
                    return CDate.getUDFTimestamp((String) pobjData, strFormatDate_ + " " + strFormatTime_);
                }
                else if (pobjToType.indexOf("Date") > -1) {
                    return CDate.getUDFDate((String) pobjData, strFormatDate_ );
                }
                else if (pobjToType.indexOf("Double") > -1 ) {
                    return new Double((String) pobjData);
                }
                else if (pobjToType.indexOf("Integer") > -1) {
                    return new Integer((String) pobjData);
                }
                else if (pobjToType.indexOf("Long") > -1) {
                    return new Long((String) pobjData);
                }
                else if (pobjToType.equals("double") ){
                    return Double.valueOf((String)pobjData);
                }
                else if (pobjToType.equals("int")) {
                    return Integer.valueOf((String)pobjData);
                }
                else if (pobjToType.equals("long")) {
                    return Long.valueOf((String)pobjData);
                }
            }
            else if (pobjDataOfType.indexOf("Timestamp") > -1) {
                return CDate.getUDFDateString((Timestamp) pobjData, strFormatDate_ +  " " + strFormatTime_);
            }
            else if (pobjDataOfType.indexOf("Date") > -1) {
                return CDate.getUDFDateString((Date) pobjData, strFormatDate_ );
            }
            else if (pobjToType.indexOf("Double") > -1 ) {
                return new Double((String) pobjData);
            }
            else if (pobjToType.indexOf("Integer") > -1) {
                return new Integer((String) pobjData);
            }
            else if (pobjToType.indexOf("Long") > -1) {
                return new Long((String) pobjData);
            }
            else if (sfInteger != null)
            {
                if (pobjDataOfType.indexOf("int") > -1) {
                    return sfInteger.format(((Integer)pobjData).intValue());
                }
                else if (pobjDataOfType.indexOf("long") > -1 ) { 
                    return sfInteger.format(((Long)pobjData).longValue());
                }
            }
            else if (sfDouble != null)
            {
                if (pobjDataOfType.indexOf("double") > -1 ) {
                    return sfDouble.format(((Double)pobjData).doubleValue());
                }
            }
            return pobjData.toString();
        }
        catch(NullPointerException e)
        {
            throw new CFormatterException(CMessages.NULL_POINTER);
        }
        catch (Exception e)
        {
            throw new CFormatterException(e.getMessage());
        }
    }
    
    
} //end of CFormatter.java
