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
package com.stgmastek.birt.report;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

/**
 * The factory class that creates the BIRT Runtime Engine instances.
 *
 * @author Kedar Raybagkar
 * @since 1.2
 */
public class BIRTEngineFactory extends BasePoolableObjectFactory {

	private EngineConfig engineConfig;

	/**
	 * 
	 */
	public BIRTEngineFactory(EngineConfig config) {
		this.engineConfig = config;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.BasePoolableObjectFactory#makeObject()
	 */
	@Override
	public Object makeObject() throws Exception {
		IReportEngineFactory factory = (IReportEngineFactory) Platform
		.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		return factory.createReportEngine(engineConfig);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.pool.BasePoolableObjectFactory#destroyObject(java.lang.Object)
	 */
	@Override
	public void destroyObject(Object obj) throws Exception {
		if (obj instanceof IReportEngine) {
			IReportEngine engine = (IReportEngine) obj;
			engine.destroy();
		}
	}
}
