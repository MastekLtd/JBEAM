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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/CSettings.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/utils/CSettings.java $
 * 
 * 17    8/31/09 11:23p Kedarr
 * Code within destroy method commented.
 * 
 * 16    4/15/09 11:17p Kedarr
 * Reverted the destroy method.
 * 
 * 15    4/08/09 5:14p Kedarr
 * Added newer static helper methods to fetch the data in the primitive
 * data types.
 * 
 * 14    3/23/09 5:35p Kedarr
 * Changed the logger to a final variable.
 * 
 * 13    3/21/09 3:57p Kedarr
 * Changes made for synchronizing the instance creation.
 * Changes made to auto load the properties. Added a new property autoload
 * for the base property.
 * 
 * 12    2/04/09 1:11p Kedarr
 * Added static keyword to a final variable. Also, rectified the coding
 * issue of not closing stream.
 * 
 * 11    3/23/08 1:06p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 10    3/18/08 4:06p Kedarr
 * Formatting changes.
 * 
 * 9     3/13/06 3:33p Kedarr
 * Added a Destroy Method for soft reboot of PRE.
 * 
 * 8     7/26/05 11:14a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 7     5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 6     2/11/05 4:23p Kedarr
 * Removed the System.out.printlns()
 * 
 * 5     1/19/05 5:48p Kedarr
 * Version Header change
 * 
 * 3     1/11/05 9:58a Kedarr
 * Revision 1.1  2005/11/03 04:54:42  kedar
 * *** empty log message ***
 *
 * 
 * 2     1/23/04 2:43p Kedarr
 * Changes made to add a default value to the get method.
 * Revision 1.4  2004/01/17 10:49:00  kedar
 * Changes made to add a static method get(String, String) which 
 * accepts a default value for the property that is being requested.
 *
 * 
 * 1     11/03/03 12:01p Kedarr
 * Revision 1.3  2003/10/29 08:52:46  kedar
 * Updated JavaDoc
 *
 * Revision 1.2  2003/10/29 07:08:09  kedar
 * Changes made for changing the Header Information from all the files.
 * These files now do belong to Systems Task Group International Ltd.
 *
 * Revision 1.1  2003/10/23 06:58:40  kedar
 * Inital Version Same as VSS
 *
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 9/25/03    Time: 4:07p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changes made for the enumeration method and java doc.
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:26p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changes made for $Revision: 2980 $ in the javadoc.
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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;

/**
 * This class is a SingleTon Factory class for reading Properties from different
 * Implementors of ISettingsSource.
 * 
 * @version $Revision: 2980 $
 * @author Kedar C. Raybagkar
 */
public class CSettings extends Object {

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

    private Properties propBase_ = new Properties();

    private Hashtable<String,ISettingsSource> hashtSettings_ = new Hashtable<String, ISettingsSource>();

    private String strBaseFile_;

    private final Map<Object, Object> map = new HashMap<Object, Object>();
    private final StrSubstitutor sub = new StrSubstitutor(map);
//	private StrSubstitutor sub;
	

    private final ReadWriteLock latch = new ReentrantReadWriteLock();
    private final Lock rLatch = latch.readLock();
    private final Lock wLatch = latch.writeLock();
    
    /**
     * Static logger.
     */
    public static final Logger logger_ = Logger.getLogger(CSettings.class);

    // private class(static) variables

    private static CSettings instance_ = new CSettings();

    // constructors

    /**
     * Standard constructor for the class
     */
    private CSettings() {
    }

//    /**
//     * Constructs an object by loading the Property Sheet.
//     * 
//     * @param pstrBaseFile
//     * @exception IOException
//     */
//    private CSettings(String pstrBaseFile) throws IOException {
//        load(pstrBaseFile);
//    }

    // finalize method, if any

    // main method
    // public static void main(String[] args) throws Exception
    // {
    // getInstance().load(args[0]);
    // get("font.lov_form_bordercolor"));
    // }

    // public methods of the class in the following order

    /**
     * Factory class Implementation;
     * 
     * @return CSettings object
     */
    public static CSettings getInstance() {
        return instance_;
    }

    /**
     * Loads the base property file.
     * 
     * @param pstrFile base property file.
     * @exception IOException
     */
    public void load(String pstrFile) throws IOException {
        wLatch.lock();
        FileInputStream fis = null;
        try {
        	strBaseFile_ = pstrFile;
            if (logger_.isEnabledFor(LogLevel.FINEST)) {
                logger_.log(LogLevel.FINEST, "Loading the base property file");
            }
            fis = new FileInputStream(new File(strBaseFile_));
            map.putAll(System.getProperties());
            propBase_.load(fis);
            for (Iterator<Object> iterator = propBase_.keySet().iterator(); iterator.hasNext();) {
    			String type = (String) iterator.next();
    			map.put(type, sub.replace(propBase_.get(type)));
    		}
            
            if (logger_.isEnabledFor(LogLevel.FINEST)) {
                logger_.log(LogLevel.FINEST, "Auto-Loading the settings");
            }
            autoLoadSettings();
        } finally {
        	wLatch.unlock();
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * Auto loads all the settings source.
     */
    private void autoLoadSettings() {
        Enumeration<Object> enumeration = getLoadedPropertyIndexes();
        while (enumeration.hasMoreElements()) {
            String str = (String) enumeration.nextElement();
            if (str.endsWith("autoload")) {
                String sindex = str.substring(str.indexOf(".")+1, str.lastIndexOf("."));
                if (getProperty(str, "false").equalsIgnoreCase("true")) {
                    getSource(sindex);
                    if (logger_.isEnabledFor(LogLevel.FINEST)) {
                        logger_.log(LogLevel.FINEST, "Loaded Settings #" + sindex);
                    }
                } else {
                    if (logger_.isEnabledFor(LogLevel.FINEST)) {
                        logger_.log(LogLevel.FINEST, "Skipped Settings #" + sindex);
                    }
                }
            }
        }
    }

    /**
     * Saves the changes in the Property Files those changes done by set method
     * of the property.
     * 
     * @exception IOException
     */
    public void save() throws IOException {
        FileOutputStream fos = null;
        wLatch.lock();
        try {
            if (logger_.isEnabledFor(LogLevel.FINEST)) {
                logger_.log(LogLevel.FINEST, "Saving Base Settings to file #" + strBaseFile_);
            }
            fos = new FileOutputStream(new File(strBaseFile_));
            propBase_.store(fos, "Saved on " + System.currentTimeMillis());
            if (logger_.isEnabledFor(LogLevel.FINEST)) {
                logger_.log(LogLevel.FINEST, "file #" + strBaseFile_ + " saved successfuly.");
            }
        } finally {
        	wLatch.unlock();
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Reloads all the internal property files.
     * 
     */
    public void reLoad() {
    	if (hashtSettings_.size() > 0) {
    		wLatch.lock();
    		try {
    			if (hashtSettings_.size() > 0) {
    				hashtSettings_.clear();
    			}
    		} finally {
    			wLatch.unlock();
    		}
    	}
    }

    /**
     * Reloads the specified internal property file.
     * 
     * @param pstrPropertyIdentifier
     * @return boolean True if reloaded else false.
     */
    public boolean reLoad(String pstrPropertyIdentifier) {
        if (hashtSettings_.containsKey(pstrPropertyIdentifier)) {
        	wLatch.lock();
        	try {
        		if (hashtSettings_.containsKey(pstrPropertyIdentifier)) {
	        		if (logger_.isEnabledFor(LogLevel.FINEST)) {
	        			logger_.log(LogLevel.FINEST, "ReLoading Settings #" + pstrPropertyIdentifier);
	        		}
	        		hashtSettings_.remove(pstrPropertyIdentifier);
	        		loadSettingsSource(pstrPropertyIdentifier);
	        		if (logger_.isEnabledFor(LogLevel.FINEST)) {
	        			logger_.log(LogLevel.FINEST, "Reloaded Settings #" + pstrPropertyIdentifier + " successfully.");
	        		}
        		}
			} finally {
				wLatch.unlock();
			}
        }
        return true;
    }

    /**
     * Returns the value of the property represented by the key
     * 
     * @param pstrKey
     *            Propertyfile.property name combination
     * @param pstrDefaultValue
     *            Default value to be returned.
     * @return String Value of the setting.
     */
    public String getSetting(String pstrKey, String pstrDefaultValue) {
        String strIndex = "";
        int iPos;
        String strValue = null;
        rLatch.lock();
        try {
        	iPos = pstrKey.indexOf(".");
        	if (iPos > -1) {
        		strIndex = pstrKey.substring(0, iPos);
        	}
        	
        	if (strIndex.equals("") || strIndex.equals("include")
        			|| strIndex.equals("directory")) {
        		strValue = getProperty(pstrKey);
        		return (strValue);
        	} else {
        		pstrKey = pstrKey.substring(iPos + 1);
        	}
        	ISettingsSource objTemp = getSource(strIndex);
        	if (objTemp != null) {
        		strValue = objTemp.getSetting(pstrKey, pstrDefaultValue);
        	}
        } finally {
        	rLatch.unlock();
        }

        return (strValue);
    }
    
    
    /**
     * Get the property for the parameter key.
     * 
     * @param pstrKey
     * @return value String. May return null if value is undefined.
     */
    public static String get(String pstrKey) {
        return (getInstance().getSetting(pstrKey, null));
    }

    /**
     * Get the property for the parameter pstrKey. If this property is
     * non-existant then the default value accepted via parameter
     * pstrDefaultValue is returned.
     * 
     * @param pstrKey
     *            String
     * @param pstrDefaultValue
     *            String
     * @return String
     */
    public static String get(String pstrKey, String pstrDefaultValue) {
        return (getInstance().getSetting(pstrKey, pstrDefaultValue));
    }

    /**
     * Get the int representation of the property identified by the key.
     * 
     * @param pstrKey
     * @return value int. May return zero if undefined.
     */
    public static int getInteger(String pstrKey) {
        return (getInstance().getIntegerSetting(pstrKey, 0));
    }

    /**
     * Get the property for the parameter pstrKey. If this property is
     * non-existant then the default value accepted via parameter
     * pstrDefaultValue is returned.
     * 
     * @param pstrKey
     *            String
     * @param pDefaultValue
     *            int
     * @return int
     */
    public static int getInt(String pstrKey, int pDefaultValue) {
        return (getInstance().getIntegerSetting(pstrKey, pDefaultValue));
    }

    /**
     * Get the long representation of the property identified by the key.
     * 
     * @param pstrKey
     * @return value long. May return zero if undefined.
     */
    public static long getLong(String pstrKey) {
        return (getInstance().getLongSetting(pstrKey, 0L));
    }
    
    /**
     * Get the property for the parameter pstrKey. If this property is
     * non-existant then the default value accepted via parameter
     * pstrDefaultValue is returned.
     * 
     * @param pstrKey
     *            String
     * @param pDefaultValue
     *            long
     * @return long
     */
    public static long getLong(String pstrKey, long pDefaultValue) {
        return (getInstance().getLongSetting(pstrKey, pDefaultValue));
    }

    /**
     * Get the double representation of the property identified by the key.
     * 
     * @param pstrKey
     * @return value double. May return zero if undefined.
     */
    public static double getDouble(String pstrKey) {
        return (getInstance().getDoubleSetting(pstrKey, 0D));
    }
    
    /**
     * Get the property for the parameter pstrKey. If this property is
     * non-existant then the default value accepted via parameter
     * pDefaultValue is returned.
     * 
     * @param pstrKey
     *            String
     * @param pDefaultValue
     *            long
     * @return long
     */
    public static double getDouble(String pstrKey, double pDefaultValue) {
        return (getInstance().getDoubleSetting(pstrKey, pDefaultValue));
    }
    
    
    
    /**
     * Translates the directory.
     * 
     * @param pstrPath
     *            Directory path.
     * @return String
     */
    public String translateDirectory(String pstrPath) {
        int iPos;
        int iOldPos;
        StringBuffer strbufOut = new StringBuffer();
        if (pstrPath != null) {
            iPos = pstrPath.indexOf("%");
            iOldPos = 0;
            while (iPos > -1) {
                strbufOut.append(pstrPath.substring(iOldPos, iPos));
                iOldPos = iPos;
                iPos = pstrPath.indexOf("%", iPos + 1);
                if (iPos == -1) {
                    iPos = pstrPath.length();
                }
                String strDir = pstrPath.substring(iOldPos + 1, iPos);
                strbufOut.append(getSetting("directory." + strDir, null));
                iOldPos = iPos + 1;
                iPos = pstrPath.indexOf("%", iPos + 1);
            }
            strbufOut.append(pstrPath.substring(iOldPos, pstrPath.length()));
        }

        return (strbufOut.toString());

    }

    /**
     * Returns an enumeration of all the properties identified by the index.
     * 
     * @param pstrIndex
     *            Property File identifier.
     * @return Enumeration of keys.
     */
    public Enumeration<?> getEnumeration(String pstrIndex) {
        ISettingsSource objTemp = getSource(pstrIndex);
        return objTemp.getPropertyNames();
    }

    /**
     * Returns all the indexes of property files specified in the initial load
     * file passed to create the instance of this class.
     * 
     * @return Enumeration
     */
    public Enumeration<Object> getLoadedPropertyIndexes() {
        return propBase_.keys();
    }

    // protected constructors and methods of the class

    // package constructors and methods of the class

    // private constructors and methods of the class

    /**
     * Returns the ISettingsSource for the given index.
     * 
     * @param pstrIndex
     *            Name identified for the <code>ISettingsSource</code>
     * @return ISettingsSource
     */
    public ISettingsSource getSource(String pstrIndex) {
        ISettingsSource objTemp = (ISettingsSource) hashtSettings_
                .get(pstrIndex);
        if (objTemp == null) {
            objTemp = loadSettingsSource(pstrIndex);
        }
        return (objTemp);
    }

    /**
     * Destroys the instance.
     */
    public synchronized void destroy() {
//        instance_ = null;
    }

    /**
     * Stores the revision number of the source code. This will be available in
     * the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    public static final String REVISION = "$Revision:: 2980      $";
    
    /**
     * Loads the settings source identified by the index.
     * @param pstrIndex
     * @return {@link ISettingsSource}
     */
    private ISettingsSource loadSettingsSource(String pstrIndex) {
        ISettingsSource objTemp = (ISettingsSource) hashtSettings_.get(pstrIndex);
        while (objTemp == null) {
    		if (wLatch.tryLock()) {
    			try {
    				objTemp = (ISettingsSource) hashtSettings_.get(pstrIndex);
    				if (objTemp == null) {
    					String strParam = getProperty("include." + pstrIndex
    							+ ".sourceParam");
    					String strSourceType = getProperty("include." + pstrIndex
    							+ ".sourceType");
    					if (strSourceType == null) {
    						// use this as default
    						strSourceType = "default";
    					}
    					try {
    						objTemp = (ISettingsSource) Class.forName(strSourceType)
    						.newInstance();
    						objTemp.load(strParam);
    						hashtSettings_.put(pstrIndex, objTemp);
    					} catch (IOException e) {
    						throw new RuntimeException("Caught IOException", e);
    					} catch (IllegalAccessException e) {
    						throw new RuntimeException("Caught IllegalAccessException", e);
    					} catch (InstantiationException e) {
    						throw new RuntimeException("Caught InstantiationException", e);
    					} catch (ClassNotFoundException e) {
    						throw new RuntimeException("Caught ClassNotFoundException", e);
    					}
    				}
    			} finally {
    				wLatch.unlock();
    			}
        		objTemp = (ISettingsSource) hashtSettings_.get(pstrIndex);
        	}
        }
        return objTemp;
    }

    /**
     * Returns the setting by converting its value from String to Long.
     * @param key
     * @param defaultValue
     * @return long
     */
    public long getLongSetting(String key, long defaultValue) {
        String strIndex = "";
        int iPos;
        iPos = key.indexOf(".");
        if (iPos > -1) {
            strIndex = key.substring(0, iPos);
        }

        if (strIndex.equals("") || strIndex.equals("include")
                || strIndex.equals("directory")) {
            return Long.parseLong(getProperty(key, defaultValue + ""));
        } else {
            key = key.substring(iPos + 1);
        }
        ISettingsSource objTemp = getSource(strIndex);
        if (objTemp != null) {
            return objTemp.getLong(key, defaultValue);
        }
        throw new IllegalArgumentException("Unable to determine the source.");
    }
    
    /**
     * Returns the value by converting the String value to double.
     * @param key
     * @param defaultValue
     * @return double
     */
    public double getDoubleSetting(String key, double defaultValue) {
        String strIndex = "";
        int iPos;
        iPos = key.indexOf(".");
        if (iPos > -1) {
            strIndex = key.substring(0, iPos);
        }
        
        if (strIndex.equals("") || strIndex.equals("include")
                || strIndex.equals("directory")) {
            return Double.parseDouble(getProperty(key, defaultValue + ""));
        } else {
            key = key.substring(iPos + 1);
        }
        ISettingsSource objTemp = getSource(strIndex);
        if (objTemp != null) {
            return objTemp.getDouble(key, defaultValue);
        }
        throw new IllegalArgumentException("Unable to determine the source.");
    }
    
    /**
     * Returns the integer value by converting the string value to int.
     * @param key
     * @param defaultValue
     * @return integer
     */
    public int getIntegerSetting(String key, int defaultValue) {
        String strIndex = "";
        int iPos;
        iPos = key.indexOf(".");
        if (iPos > -1) {
            strIndex = key.substring(0, iPos);
        }
        
        if (strIndex.equals("") || strIndex.equals("include")
                || strIndex.equals("directory")) {
            return Integer.parseInt(getProperty(key, defaultValue + ""));
        } else {
            key = key.substring(iPos + 1);
        }
        ISettingsSource objTemp = getSource(strIndex);
        if (objTemp != null) {
            return objTemp.getInteger(key, defaultValue);
        }
        throw new IllegalArgumentException("Unable to determine the source.");
    }
    
    /**
     * Returns the property after substituting the system properties if available.
     * 
     * @param key String key
     * @return String value
     */
    private String getProperty(String key) {
    	return getProperty(key, null);
    }
    
    /**
     * Returns the property after substituting the system properties if available.
     * 
     * The default value is passed in case the key could not be located.
     * 
     * @param key String key
     * @param value String default value
     * @return String value
     */
    private String getProperty(String key, String value) {
    	return sub.replace(propBase_.getProperty(key, value));
    }
    
} // end of CSettings.java
