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
import com.stgmastek.birt.report.beans.ReportGenerationMode;
import com.stgmastek.birt.report.beans.ReportParameters;

public class Validater {
	
	public static void validateInput(Report report) {		
		//// Report
		if(report == null) {
			throw new RuntimeException("<Report> element not provided");
		}
		if(report.getDesignFileIdentifier() == null || report.getDesignFileIdentifier().trim().length() == 0) {
			throw new RuntimeException("<Report><DesignFileIdentifier> element missing or empty");
		}

		//// File Name and path
		if(report.getFileName() == null || report.getFileName().trim().length() == 0) {
			throw new RuntimeException("<Report><FileName> element missing or empty");
		}		
		if(report.getFilePath() == null || report.getFilePath().trim().length() == 0) {
			throw new RuntimeException("<Report><FilePath> element missing or empty");
		}		

		//// Output Formats
		if(report.getOutputFormats() == null || report.getOutputFormats().getOutputFormat() == null || report.getOutputFormats().getOutputFormat().isEmpty()) { 
			throw new RuntimeException("<Report><ReportDestination><OutputFormats> element missing or empty");
		}
		
		//// Report Parameters
		ReportParameters rp = report.getReportParameters();
		if((rp != null) && (rp.getReportParameter() == null || rp.getReportParameter().isEmpty())) {
			throw new RuntimeException("<Report><ReportParameters><ReportParameter> element missing or empty");	
		}		
		
		//// Report Manipulators
		if(	report.getReportManipulators() != null && 
			(report.getReportManipulators().getReportManipulator() == null || report.getReportManipulators().getReportManipulator().isEmpty()) ) {
			throw new RuntimeException("<Report><ReportManipulators><ReportManipulator> element missing or empty");
		}
		
		//// Online Mode specific validations
		if( report.getGenerationMode() == ReportGenerationMode.ONLINE ) {
			//// Output Format should be only one.
			if( report.getOutputFormats().getOutputFormat().size() > 1 ) {
				throw new RuntimeException("For Online Report, there should be only one <OutputFormat> tag in <Report><ReportDestination><OutputFormats>");
			}
		}
	}
}
