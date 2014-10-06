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
package com.stgmastek.birt.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stgmastek.birt.report.IReportGenerator;
import com.stgmastek.birt.report.IReportService;
import com.stgmastek.birt.report.beans.OutputFormat;
import com.stgmastek.birt.report.beans.Report;
import com.stgmastek.birt.report.utils.JAXBUtils;
import com.stgmastek.utilities.LoggerInitializer;

public class TestReportGeneratorBothModes {	
	private static Logger logger;
	
	@BeforeClass static public void init() throws Exception {
		LoggerInitializer.initializeLogSystem();
		logger = Logger.getLogger(TestReportGeneratorBothModes.class);
		TestDataUtility.deleteAllReportFiles();
	}

	private IReportService service;
	private IReportGenerator reportGenerator;
	public TestReportGeneratorBothModes() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans-reporting.xml");
		this.reportGenerator = (IReportGenerator) context.getBean("reportGenerator");
		this.service = (IReportService) context.getBean("reportService");
	}

	/**
	 * Test method for {@link com.stgmastek.birt.report.ReportGenerator#generateOffLine(com.stgmastek.birt.report.Report)}.
	 */
	/*@Test public void testReportWithXMLDataSource() {
		//// XML Data Source
		String reportDesignFile = "com.stgmastek.reports.billing.agency.AgencyStatement_report.rptdesign";
		String xmlDatasource[] = new String[] {
				"/com/stgmastek/reports/billing/agency/datasource/AgencyStatement_876597.xml"
		};
		Report reportOffline = TestDataUtility.getReportObjectForXMLDataSource("AgencyStatement_876597_1", reportDesignFile, xmlDatasource);
		Report reportOnline = TestDataUtility.getReportObjectForXMLDataSource("AgencyStatement_876597_2", reportDesignFile, xmlDatasource);
		
		// Report Generation
		logger.trace("TEST SCNEARIO :- XML Data Source based report, in both modes, simultaneously ...");
		try {
			reportGenerator.generateOffLine(reportOffline);
			reportGenerator.generateOnLine(reportOnline);
		} catch(Exception e) {
			logger.error("Exception during report generation : ["+e.getMessage()+"]", e);
			Assert.fail("Exception : ["+e.getMessage()+"]");
		}
		
		// Forced Wait -- Let the service complete all the Offline Reports
		waitForServiceToProcessQueue();
		
		// Assertions
		assertDestinationFiles(reportOffline);
		assertDestinationFiles(reportOnline);
		logger.trace("Report Generation in both Offline & Online modes, simultaneously, is successful");
	}*/
	
 	private void assertDestinationFiles(Report report) {
 		String outDirectory = report.getFilePath(); 
 		String reportFileName = report.getFileName();
 		
 		for(OutputFormat outputFormat : report.getOutputFormats().getOutputFormat()) {
 			String completeFileName = outDirectory + "/" + reportFileName + "." + outputFormat.value();
 			File file = null;
 			try {
 				file = new File(completeFileName);
 			} catch(Exception e) {
 				Assert.fail("Exception while opening file : ["+completeFileName+"]. Is it corrupted ?");
 			}
 			Assert.assertTrue("File should exist", file.exists());
 			Assert.assertTrue("File should not be blank", (file.length() > 0));
 			logger.trace("File created " + file.getPath());
 		}	
	}

	private void waitForServiceToProcessQueue() {
		// Forced delay - WAIT for the pool to start the job
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Forced Wait - Waiting for Service to finish all the jobs.
		while (service.getCurrentActiveCount() + service.getCurrentPendingQueueSize() > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

