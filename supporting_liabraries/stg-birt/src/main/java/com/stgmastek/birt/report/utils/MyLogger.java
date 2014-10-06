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
package com.stgmastek.birt.report.utils;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MyLogger {
	private static Properties properties;
	static {
		try {
			InputStream propertiesInputStream = MyLogger.class.getResourceAsStream("/Messages.properties");				
			properties = new Properties();
			properties.load(propertiesInputStream);
		} catch (Exception e) {
			System.out.println("ERROR -- Exception while loading Messages.properties in BIRT Reporting Module");
			e.printStackTrace();
		}
	}
	
	public static void info(Logger logger, String msgKey, Object ... values) {
		if(logger.isInfoEnabled() == false)
			return;
		String logMessage = MessageFormat.format(properties.getProperty(msgKey), values);
		logger.info(logMessage);
	}
	
	public static void debug(Logger logger, String msgKey, Object ... values) {
		if(logger.isDebugEnabled() == false)
			return;
		String logMessage = MessageFormat.format(properties.getProperty(msgKey), values);
		logger.debug(logMessage);
	}
	
	public static void error(Logger logger, String msgKey, Object ... values) {
		String logMessage = MessageFormat.format(properties.getProperty(msgKey), values);
		logger.error(logMessage);
	}
}
