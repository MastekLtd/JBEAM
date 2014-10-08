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

import com.stgmastek.birt.report.beans.Report;

/**
 * A package protected class that is helpful to generate the reports asynchronously.
 *
 * @author Kedar Raybagkar
 * @version $Revision:  $
 * @since 1.0
 */
class RunnableReportTask implements Runnable {

	private final Report report;
	private IReportGenerator reportGenerator;
	
	
	/**
	 * Constructor that accepts the generator and the report instance.
	 * 
	 * @param generator
	 * @param report
	 */
	public RunnableReportTask(final Report report, final IReportGenerator reportGenerator) {
		this.report = report;
		this.reportGenerator = reportGenerator;
	}
	
	/**
	 * Returns the report that was inconsideration.
	 * @return Report
	 */
	public Report getReport() {
		return report;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			reportGenerator.generateOnLine(report);
		} catch (Exception e) { 
			//as the runnable works only in case of off-line report there will be no caller available to catch the exception.
			e.printStackTrace();
		}
	}
}
