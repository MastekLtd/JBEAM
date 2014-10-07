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
* $Revision: 2980 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/CPRSettings.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/CPRSettings.java $
 * 
 * 10    11/18/09 1:47p Kedarr
 * Changes made to initialize the variable to null.
 * 
 * 9     11/11/09 2:08p Kedarr
 * Changes made to make use of commons lang package for resolving
 * variables in the property file.
 * 
 * 8     4/08/09 5:13p Kedarr
 * Implemented the changes made in ISettingsSource
 * 
 * 7     2/04/09 1:10p Kedarr
 * Added static keyword to a final variable. Also, rectified the coding
 * issue of not closing stream.
 * 
 * 6     3/23/08 1:04p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 5     3/18/08 4:05p Kedarr
 * Added new methods as convinent mechanisms for returning Integer, long,
 * etc.
 * 
 * 4     5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 3     1/19/05 3:11p Kedarr
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.text.StrSubstitutor;


/**
* This is description of the class CTest. This class is being
* written for the purpose of describing the JavaDoc standards
* and tags that need to go in into the Java source file.
*
* Please make it a point to follow these standards when
* writing the source code.
**/
public class CPRSettings implements ISettingsSource {
    
    
    //public instance constants and class constants
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2980              $";
    
    //public instance variables
    
    
    //public class(static) variables
    
    
    //protected instance constants and class constants
    
    //protected instance variables
    
    //protected class(static) variables
    
    
    //package instance constants and class constants
    
    //package instance variables
    
    //package class(static) variables
    
    
    //private instance constants and class constants
    
    //private instance variables
    
    private Properties prop_ = new Properties();
    
    private String strFileName_;
    private final ReadWriteLock rwLatch = new ReentrantReadWriteLock();
    private final Lock rLock = rwLatch.readLock();
    private final Lock wLock = rwLatch.writeLock();

	private StrSubstitutor sub = null;
    
    //private class(static) variables
    
    
    //constructors
    
    
    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order
    
    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#load(java.lang.String)
     */
    public void load(String strParam) throws IOException
    {
        strFileName_ = strParam;
        FileInputStream fis = null;
        wLock.lock();
        try {
        	try {
        		fis = new FileInputStream(new File(strFileName_));
        		prop_.load(fis);
        	} finally {
        		if (fis != null) {
        			fis.close();
        		}
        	}
        	Map<Object, Object> map = new HashMap<Object, Object>();
        	map.putAll(System.getProperties());
        	sub = new StrSubstitutor(map);
        	for (Iterator<Object> iterator = prop_.keySet().iterator(); iterator.hasNext();) {
        		String type = (String) iterator.next();
        		map.put(type, sub.replace(prop_.get(type)));
        	}
        } finally {
        	wLock.unlock();
        }
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#save()
     */
    public void save() throws IOException
    {
    	wLock.lock();
    	try {
    		if (strFileName_ != null)
    		{
    			FileOutputStream fos = null;
    			try {
    				fos = new FileOutputStream(new File(strFileName_));
    				prop_.store(fos, "SaveTime @" + System.currentTimeMillis());
    			} finally {
    				if (fos != null) {
    					fos.close();
    				}
    			}
    		}
        } finally {
        	wLock.unlock();
        }
    }
    
    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getSetting(java.lang.String)
     */
    public String getSetting(String pstrKey)
    {
        return(getSetting(pstrKey, null));
    }


    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getSetting(java.lang.String, java.lang.String)
     */
    public String getSetting(String pstrKey, String pstrDefaultValue)
    {
    	rLock.lock();
    	try {
    		return sub.replace((prop_.getProperty(pstrKey, pstrDefaultValue)));
        } finally {
        	rLock.unlock();
        }
    }


    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#setSetting(java.lang.String, java.lang.String)
     */
    public void setSetting(String pstrKey, String pstrValue)
    {
    	wLock.lock();
    	try {
    		prop_.setProperty(pstrKey, pstrValue);
        } finally {
        	wLock.unlock();
        }
    }
    
    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getPropertyNames()
     */
    public Enumeration<?> getPropertyNames()
    {
    	rLock.lock();
    	try {
    		return(prop_.propertyNames());
        } finally {
        	rLock.unlock();
        }
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getProperties()
     */
    public Properties getProperties() {
    	rLock.lock();
    	try {
    		return (Properties) prop_.clone();
        } finally {
        	rLock.unlock();
        }
    }

    /**
     * Returns the boolean by converting the character value true or false to boolean. 
     * @param pstrKey 
     * @return boolean
     */
    public boolean getBoolean(String pstrKey) {
        return getBoolean(pstrKey, false);
    }

    /**
     * Returns the boolean value by converting the character value true or false to boolean.
     * @param pstrKey
     * @param defaultValue Default value to be returned if key is undefined.
     * @return boolean
     */
    public boolean getBoolean(String pstrKey, boolean defaultValue) {
    	rLock.lock();
    	try {
    		return sub.replace(prop_.getProperty(pstrKey, defaultValue+"")).equals("true");
        } finally {
        	rLock.unlock();
        }
    }

    /**
     * Converts a character numeral to Integer 
     * @param pstrKey
     * @return int
     */
    public int getInteger(String pstrKey) {
        return getInteger(pstrKey, -1);
    }

    /**
     * Converts a character numeral to Integer.
     * 
     * @param pstrKey
     * @param defaultValue Default int value to be returned if key is undefined.
     * @return int
     */
    public int getInteger(String pstrKey, int defaultValue) {
    	rLock.lock();
    	try {
    		return Integer.parseInt(sub.replace(prop_.getProperty(pstrKey, defaultValue + "")));
        } finally {
        	rLock.unlock();
        }
    }

    /**
     * Returns the long value by converting a character numeral that represents a long.
     * 
     * @param pstrKey
     * @return long
     */
    public long getLong(String pstrKey) {
        return getLong(pstrKey, -1L);
    }

    /**
     * Returns the long value by converting a character numeral that represents a long.
     * 
     * @param pstrKey
     * @param defaultValue Default long value to be returned if the key is undefined. 
     * @return long
     */
    public long getLong(String pstrKey, long defaultValue) {
    	rLock.lock();
    	try {
    		return Long.parseLong(sub.replace(prop_.getProperty(pstrKey, defaultValue+"")));
        } finally {
        	rLock.unlock();
        }
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getDouble(java.lang.String)
     */
    public double getDouble(String pstrKey) {
        return getDouble(pstrKey, 0d);
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getDouble(java.lang.String, double)
     */
    public double getDouble(String pstrKey, double defaultValue) {
    	rLock.lock();
    	try {
    		return Double.parseDouble(sub.replace(prop_.getProperty(pstrKey, defaultValue+"")));
        } finally {
        	rLock.unlock();
        }
    }

	/* (non-Javadoc)
	 * @see stg.utils.ISettingsSource#getConfigFile()
	 */
	public File getConfigFile() {
		return new File(strFileName_);
	}
    
    
    //package constructors and methods of the class
    
    //private constructors and methods of the class
    
    
} //end of CDBSettings.java
