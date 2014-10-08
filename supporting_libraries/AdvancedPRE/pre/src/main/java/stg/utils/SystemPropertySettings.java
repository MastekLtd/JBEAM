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
 * $Revision: 2734 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/SystemPropertySettings.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/utils/SystemPropertySettings.java $
 * 
 * 2     4/08/09 5:13p Kedarr
 * Implemented the changes made in ISettingsSource
 * 
 * 1     3/30/09 11:15a Kedarr
 * Initial Version
 * 
 */
package stg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.lang.text.StrSubstitutor;

/**
 * Settings class that reads from the configured properties file and adds them to the System.properties.
 *
 * All methods to the System.getProperty() should be done using this class.
 * 
 * @author Kedar C. Raybagkar
 * @since  V1.0R26.00.x
 */
public class SystemPropertySettings implements ISettingsSource {

    private String source;

	/* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getBoolean(java.lang.String)
     */
    public boolean getBoolean(String pstrKey) {
        return getBoolean(pstrKey, false);
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getBoolean(java.lang.String, boolean)
     */
    public boolean getBoolean(String pstrKey, boolean defaultValue) {
        return System.getProperty(pstrKey, defaultValue + "").equalsIgnoreCase("true");
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getInteger(java.lang.String)
     */
    public int getInteger(String pstrKey) {
        return getInteger(pstrKey, 0);
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getInteger(java.lang.String, int)
     */
    public int getInteger(String pstrKey, int defaultValue) {
        if (System.getProperty(pstrKey) == null) {
            return defaultValue;
        }
        return Integer.parseInt(System.getProperty(pstrKey));
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getLong(java.lang.String)
     */
    public long getLong(String pstrKey) {
        return getLong(pstrKey, 0L);
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getLong(java.lang.String, long)
     */
    public long getLong(String pstrKey, long defaultValue) {
        if (System.getProperty(pstrKey) == null) {
            return defaultValue;
        }
        return Long.parseLong(System.getProperty(pstrKey));
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getProperties()
     */
    public Properties getProperties() {
        return (Properties) System.getProperties().clone();
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getPropertyNames()
     */
    public Enumeration<?> getPropertyNames() {
        return getProperties().keys();
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getSetting(java.lang.String)
     */
    public String getSetting(String pstrKey) {
        return getSetting(pstrKey, null);
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#getSetting(java.lang.String, java.lang.String)
     */
    public String getSetting(String pstrKey, String pstrDefaultValue) {
        return System.getProperty(pstrKey, pstrDefaultValue);
    }

    /* (non-Javadoc)
     * @see stg.utils.ISettingsSource#load(java.lang.String)
     */
    public void load(String pstrFile) throws IOException {
    	this.source = pstrFile;
    	
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            fis = new FileInputStream(new File(pstrFile));
            props.load(fis);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.putAll(System.getProperties());
        StrSubstitutor sub = new StrSubstitutor(map);
        for (Entry<Object, Object> entry : props.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.setProperty(key, sub.replace(value));
        }
    }

    /**
     * This method is not implemented.
     * @see stg.utils.ISettingsSource#save()
     */
    public void save() throws IOException {
        //do nothing
    }

    /**
     * This method is not implemented.
     * @see stg.utils.ISettingsSource#setSetting(java.lang.String, java.lang.String)
     */
    public void setSetting(String pstrKey, String pstrValue) {
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
        if (System.getProperty(pstrKey) == null) {
            return defaultValue;
        }
        return Double.parseDouble(System.getProperty(pstrKey));
    }

	/* (non-Javadoc)
	 * @see stg.utils.ISettingsSource#getConfigFile()
	 */
	public File getConfigFile() {
		return new File(source);
	}

}
