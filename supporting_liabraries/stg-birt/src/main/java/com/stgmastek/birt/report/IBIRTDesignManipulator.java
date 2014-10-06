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

import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;

/**
 * An interface that helps to plug-in design manipulators during runtime. 
 *
 *
 * @author kedar.raybagkar
 * @since
 */
public interface IBIRTDesignManipulator {

	/**
	 * The engine and the design instance is passed to the implementor so that the design can be manipulated.
	 * 
	 * @param engine IReportEngine instance.
	 * @param report IReportRunnable instance.
	 */
	public void manupulate(IReportEngine engine, IReportRunnable report);
	
}
