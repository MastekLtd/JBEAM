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
* $Revision: 2569 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/ISettingsSource.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/ISettingsSource.java $
 * 
 * 8     4/08/09 5:13p Kedarr
 * Implemented the changes made in ISettingsSource
 * 
 * 7     2/04/09 1:13p Kedarr
 * Added static keyword to a final variable.
 * 
 * 6     6/16/08 11:48p Kedarr
 * Added new methods to get Boolean, Integer, Long
 * 
 * 5     3/22/08 12:31a Kedarr
 * Added REVISION variable.
 * 
 * 4     5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 3     1/19/05 3:12p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:01p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:40  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:50p
 * Created in $/DEC18/ProcessRequestEngine/gmac/utils
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:58p
 * Created in $/ProcessRequestEngine/gmac/utils
 * Initial Version
*
*/



package stg.utils;


import java.io.File;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Properties;


/**
* This is description of the class CTest. This class is being
* written for the purpose of describing the JavaDoc standards
* and tags that need to go in into the Java source file.
*
* Please make it a point to follow these standards when
* writing the source code.
**/
public interface ISettingsSource
{
    
    
    //public instance constants and class constants
    
    //public instance variables
    
    //public class(static) variables
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2569              $";
    
    //protected instance constants and class constants
    
    //protected instance variables
    
    //protected class(static) variables
    
    
    //package instance constants and class constants
    
    //package instance variables
    
    //package class(static) variables
    
    
    //private instance constants and class constants
    
    //private instance variables
    
    //private class(static) variables
    
    
    //constructors
    
    
    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order

    /**
     * Returns the config file to which this Settings is associated.
     * 
     * @return File
     */
    public File getConfigFile();

    /**
     * Loads the given file.
     * 
     * @param pstrFile
     * @throws IOException
     */
    public void load(String pstrFile) throws IOException;
    
    /**
     * Saves the in-memory data to the given file.
     * @throws IOException
     */
    public void save() throws IOException;
    
    /**
     * Returns the Setting against the given property.
     * @param pstrKey
     * @return String
     */
    public String getSetting(String pstrKey);
    
    /**
     * Returns the setting against the given property. 
     * If the property is not defined then the default value is passed.
     *  
     * @param pstrKey Key
     * @param pstrDefaultValue Default value to be returned.
     * @return String
     */
    public String getSetting(String pstrKey, String pstrDefaultValue);
    
    /**
     * Sets the key with the given value.
     * 
     * @param pstrKey
     * @param pstrValue
     */
    public void setSetting(String pstrKey, String pstrValue);
    
    /**
     * Returns the enumeration of all properties.
     * @return Keys Enumeration
     */
    public Enumeration<?> getPropertyNames();
    
    /**
     * Returns the Properties.
     * 
     * @return Properties
     */
    public Properties getProperties();

    /**
     * Returns the boolean by converting the character value true or false to boolean. 
     * @param pstrKey 
     * @return boolean
     */
    public boolean getBoolean(String pstrKey);

    /**
     * Returns the boolean value by converting the character value true or false to boolean.
     * @param pstrKey
     * @param defaultValue Default value to be returned if key is undefined.
     * @return boolean
     */
    public boolean getBoolean(String pstrKey, boolean defaultValue) ;

    /**
     * Converts a character numeral to Integer 
     * @param pstrKey
     * @return int
     */
    public int getInteger(String pstrKey);

    /**
     * Converts a character numeral to Integer.
     * 
     * @param pstrKey
     * @param defaultValue Default int value to be returned if key is undefined.
     * @return int
     */
    public int getInteger(String pstrKey, int defaultValue);

    /**
     * Returns the long value by converting a character numeral that represents a long.
     * 
     * @param pstrKey
     * @return long
     */
    public long getLong(String pstrKey);

    /**
     * Returns the long value by converting a character numeral that represents a long.
     * 
     * @param pstrKey
     * @param defaultValue Default long value to be returned if the key is undefined. 
     * @return long
     */
    public long getLong(String pstrKey, long defaultValue);

    /**
     * Returns the double value by converting a character numeral that represents a double.
     * 
     * @param pstrKey
     * @return double
     */
    public double getDouble(String pstrKey);
    
    /**
     * Returns the double value by converting a character numeral that represents a double.
     * 
     * @param pstrKey
     * @param defaultValue Default double value to be returned if the key is undefined. 
     * @return double
     */
    public double getDouble(String pstrKey, double defaultValue);
    
    
    //protected constructors and methods of the class
    
    //package constructors and methods of the class
    
    //private constructors and methods of the class
    
    
} //end of ISettingsSource.java
