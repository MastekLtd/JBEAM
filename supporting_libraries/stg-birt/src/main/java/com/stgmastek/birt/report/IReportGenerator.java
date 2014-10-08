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

import java.util.List;

import org.eclipse.birt.core.exception.BirtException;

import com.stgmastek.birt.report.beans.Report;
import com.stgmastek.birt.report.exceptions.ReportServiceException;

/**
 * Report Generator helps generate reports using BIRT.
 * 
 * This class is responsible to generate reports in Online and Off-Line manner.
 * In case of Online manner the exceptions if any are thrown to the caller and in off-line
 * the exceptions if any are logged.
 * 
 * @author Kedar Raybagkar
 */
public interface IReportGenerator {

	/**
	 * Generates the report in an off-line manner.
	 * The report is executed in an asynchronous manner and the errors if any are logged.
	 * Please see {@link #generateOnLine(Report)} to generate the report in the same thread.
	 * 
	 * @param report to be generated.
	 * @throws ReportServiceException in case the service is shutdown and is unable to accept new reports.
	 */
	public void generateOffLine(Report report);
	
	/**
	 * Generates the given list of reports in an off-line manner.
	 * 
	 * The report is executed in an asynchronous manner and the errors if any are logged.
	 * 
	 * @param reports list of reports to be generated
	 * @throws ReportServiceException in case the service is shutdown and is unable to accept new reports.
	 */
	public void generateOffLine(List<Report> reports);
	
	/**
	 * Caller executes the report.
	 * 
	 * @param report to be generated
	 * @return Absolute path of the generated report file.
	 * @throws BirtException in case there is any exception thrown by BIRT
	 * @throws ReportServiceException in case the report service has been destroyed.
	 */
	public List<String> generateOnLine(Report report);
	
//	/**
//	 * The method that generates the report.
//	 * Return a list of generated files (with complete path). 
//	 * 
//	 * @param report Report to be generated.
//	 */
//	public List<String> generateReport(Report report);
	
}
