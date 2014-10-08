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

package com.stgmastek.birt.report.engine.config;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportDocumentLockManager;
import org.eclipse.birt.report.engine.api.IStatusHandler;
import org.eclipse.birt.report.model.api.IResourceLocator;

/**
 * Wrapper over Birt's Engine config class {@link org.eclipse.birt.report.engine.api.EngineConfig EngineConfig}.
 * 
 * BIRT Engine's config class is not a true POJO (some attributes are stored in key-value pairs).
 * So, these properties are exposed as bean attribute in order to simplify Spring Injection. 
 *  
 * @author Prasanna Mondkar
 */
public class EngineConfigWrapper {
	private EngineConfig engineConfig;	// The wrapped object
	
	public EngineConfigWrapper() {
		engineConfig = new EngineConfig();
	}
	public EngineConfig getEngineConfig() {
		return engineConfig;
	}
	public void setBIRTHome(String birtHome) {
		engineConfig.setBIRTHome(birtHome);
	}
	
	
	public void setAppContext(HashMap appContext) {		
		engineConfig.setAppContext(appContext);
	}
	public void setProperties(HashMap<String, Object> properties) {
		for(Map.Entry<String, Object> entry : properties.entrySet()) {
			engineConfig.setProperty(entry.getKey(), entry.getValue());
		}
	}
	
	public void setEmitterConfiguration(Map<String, IRenderOption> mapEmitterConfigurations) {
		for(Map.Entry<String, IRenderOption> entry : mapEmitterConfigurations.entrySet() ) {
			engineConfig.setEmitterConfiguration( entry.getKey(), entry.getValue() );
		}
	}
	public void setStatusHandler(IStatusHandler handler) {
		engineConfig.setStatusHandler(handler);
	}
	public void setReportDocumentLockManager(IReportDocumentLockManager manager) {
		engineConfig.setReportDocumentLockManager(manager);
	}	
	public void setResourceLocator(IResourceLocator resourceLocator) {
		engineConfig.setResourceLocator(resourceLocator);
	}
	public void setResourcePath(String resourcePath) {
		engineConfig.setResourcePath(resourcePath);
	}
	public void setTempDir(String tempDirectory) {
		engineConfig.setTempDir(tempDirectory);
	}
	public void setLogFile(String logFilename) {
		engineConfig.setLogFile(logFilename);
	}
	public void setLogger(Logger logger) {
		engineConfig.setLogger(logger);
	}
	
	public void setLogDirectoryName(String logDirectoryName) {		
		engineConfig.setProperty("logDest", logDirectoryName);
	}
	public void setLogLevel(Level logLevel) {
		engineConfig.setProperty("logLevel", logLevel);
	}
}
