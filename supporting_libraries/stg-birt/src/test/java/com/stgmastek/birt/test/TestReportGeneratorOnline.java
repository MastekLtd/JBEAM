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

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stgmastek.birt.report.IReportGenerator;
import com.stgmastek.birt.report.IReportService;
import com.stgmastek.utilities.LoggerInitializer;

public class TestReportGeneratorOnline {	
	private static Logger logger;
	
	@BeforeClass static public void init() throws Exception {
		LoggerInitializer.initializeLogSystem();
		logger = Logger.getLogger(TestReportGeneratorOnline.class);
		TestDataUtility.deleteAllReportFiles();
	}

	private IReportService service;
	private IReportGenerator reportGenerator;
	public TestReportGeneratorOnline() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans-reporting.xml");
		this.reportGenerator = (IReportGenerator) context.getBean("reportGenerator");
		this.service = (IReportService) context.getBean("reportService");
	}

	
}

