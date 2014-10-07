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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/prefs/PREPreferencesFactoryImpl.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/utils/prefs/PREPreferencesFactoryImpl.java $
 * 
 * 1     10/27/09 11:29a Kedarr
 * Initial Class
 *
 */
package stg.utils.prefs;

import java.io.File;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;

/**
 * Default implementation of the PreferencesFactory.
 * 
 * @author Kedar Raybagkar
 * @since V1.0R27
 */
public class PREPreferencesFactoryImpl implements PreferencesFactory {

	private static final Logger logger = Logger
			.getLogger(PREPreferencesFactoryImpl.class.getName());

	private PREPreferences rootPreferences;

	/* (non-Javadoc)
	 * @see java.util.prefs.PreferencesFactory#systemRoot()
	 */
	public synchronized Preferences systemRoot() {
		if (rootPreferences == null) {
			if (logger.isEnabledFor(LogLevel.FINER)) {
				logger.log(LogLevel.FINER, "Instantiating root preferences");
			}
			rootPreferences = new PREPreferences(null, "");
		}
		return rootPreferences;
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.PreferencesFactory#userRoot()
	 */
	public synchronized Preferences userRoot() {
		return new PREPreferences(rootPreferences, System.getProperty("user.name", "pre"));
	}

	/**
	 * Backing store file.
	 */
	private static File preferencesFile;

	/**
	 * Returns the File object that will be used as a backing store.
	 * 
	 * @return File
	 */
	public synchronized static File getPreferencesFile() {
		if (preferencesFile == null) {
			String prefsFile = null;
			try {
				prefsFile = System.getProperty("java.util.prefs.PreferencesFactory.file");
			} catch (SecurityException e) {
				//do nothing
			}
			if (prefsFile == null || prefsFile.length() == 0) {
				return null;
//				prefsFile = System.getProperty("user.home") + File.separator
//						+ ".fileprefs";
			}
			preferencesFile = new File(prefsFile).getAbsoluteFile();
			if (logger.isEnabledFor(LogLevel.FINER)) {
				logger.log(LogLevel.FINER, "Preferences file is " + preferencesFile);
			}
		}
		return preferencesFile;
	}
}
