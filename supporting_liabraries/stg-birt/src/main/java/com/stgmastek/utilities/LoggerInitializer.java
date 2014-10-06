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
package com.stgmastek.utilities;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LoggerInitializer
{	
	public static final void initializeLogSystem() 
	{
		InputStream is = null;
		Properties log4jProperties = new Properties();
		Logger log = null;
		
        try {	 	        	
        	is = LoggerInitializer.class.getResourceAsStream("/log4j.properties");
        	log4jProperties.load(is);        	
        }
        catch(Exception e) {
        	System.out.println("Exception while loading log4j.properties file. It is expected at the root package ...");
        	e.printStackTrace(System.out);
        	return;
        }        

        // Configure the Log4j System
        try {
        	PropertyConfigurator.configure(log4jProperties);
        	log = Logger.getLogger(LoggerInitializer.class);
            log.info("Log4j initialized successfully");
        }
        catch(Exception e) {
        	System.out.println("Exception while configuration of the LOG4j Logging system.");
        	e.printStackTrace(System.out);
        }
	}
}
