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

import stg.utils.Day;

import com.stgmastek.birt.report.beans.OutputFormat;
import com.stgmastek.birt.report.beans.Report;

public class ReportLogger {
	/**
	 * Generate log.
	 * 
	 * @param report Report
	 * @param startTime when the report execution started.
	 * @param endTime when the report execution completed.
	 * @param t Throwable if any.
	 * @return
	 */
	public static String generateLogReport(Report report, long startTime, long endTime, String fileName, OutputFormat format,  Throwable t) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Report ID :- [");
		sb.append(report.getID());
		sb.append("] Name :- [");
		sb.append(report.getFileName());
		sb.append("], Start Time :- [");
		sb.append(startTime);
		sb.append("], End Time :- [");
		sb.append(endTime);
		sb.append("], Time Taken :- [");
		sb.append(Day.verboseTimeDifference(startTime, endTime));
		
		if(format != null) {
			sb.append("], Output Format :- [");
			sb.append(format);
		}
		if (fileName != null) {
			sb.append("], Destination File :- [");
			sb.append(fileName);
		}
		
		sb.append("], Status :- [");
		if (t != null) {
			sb.append("Failure], Error Message :- [");
			sb.append(t.getMessage());
		} else {
			sb.append("Success");
		}
		sb.append("]");
		return sb.toString();
	}
}
