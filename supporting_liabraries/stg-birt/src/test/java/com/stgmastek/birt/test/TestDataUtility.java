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
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.stgmastek.birt.report.beans.OutputFormat;
import com.stgmastek.birt.report.beans.OutputFormats;
import com.stgmastek.birt.report.beans.Report;
import com.stgmastek.birt.report.beans.ReportParameter;
import com.stgmastek.birt.report.beans.ReportParameterType;
import com.stgmastek.birt.report.beans.ReportParameters;

public class TestDataUtility {
	
	public static Report getReportObjectForMultipleXMLDataSource(String reportFileName, String reportDesignFileName, String[] xmlFilePaths) {
		ReportParameters reportParameters = getReportParametersForXMLDataSource( xmlFilePaths );
		OutputFormats outputFormats = getOutputFormats( OutputFormat.PDF );
		Report report = getReportObject(reportFileName, reportDesignFileName, reportParameters, outputFormats);
		return report;
	}
	
	public static Report getReportObjectForScriptedDataSource(String reportDesignFile) {
		OutputFormats outputFormats = TestDataUtility.getOutputFormats( OutputFormat.PDF );
		Report report = getReportObject(reportDesignFile, "ScriptedDataSourceDemo", outputFormats);
		return report;
	}

	private static Report getReportObject(String reportName, String rptDesignFile, ReportParameters reportParameters, OutputFormats outputFormats) {
		Report report = new Report();
		report.setFileName(reportName);
		report.setDesignFileIdentifier( rptDesignFile );
		report.setReportParameters( reportParameters );
		report.setOutputFormats( outputFormats );
		return report;
	}	
	private static Report getReportObject(String reportName, String rptDesignFile, OutputFormats outputFormats) {
		Report report = new Report();
		report.setFileName(reportName);
		report.setDesignFileIdentifier( rptDesignFile );
		report.setOutputFormats( outputFormats );
		return report;
	}
	
	private static ReportParameters getReportParametersForXMLDataSource(String[] xmlFilePaths) {
		int i = 1;
		List<ReportParameter> lstReportParameters = new ArrayList<ReportParameter>( xmlFilePaths.length );
		for(String xmlFilePath : xmlFilePaths) {
			ReportParameter reportParameter = new ReportParameter();
			reportParameter.setName("rpDataSource_"+i);
			reportParameter.setValue(xmlFilePath);
			reportParameter.setReportParameterType( ReportParameterType.TEXT );
			lstReportParameters.add( reportParameter );
			i++;
		}
		ReportParameters reportParameters = new ReportParameters();
		reportParameters.setReportParameter( lstReportParameters );
		return reportParameters;
	}
	
	private static ReportParameter getReportParameter(String name, String value, ReportParameterType type) {
		ReportParameter param = new ReportParameter();
		param.setName(name);
		param.setValue(value);
		param.setReportParameterType(type);	
		return param;
	}
	private static OutputFormats getOutputFormats(OutputFormat ... inOutputFormats) {
		List<OutputFormat> lstOutputFormat = new ArrayList<OutputFormat>( inOutputFormats.length );
		for(OutputFormat outputFormat : inOutputFormats) {
			lstOutputFormat.add( outputFormat );	
		}
		OutputFormats outputFormats = new OutputFormats();
		outputFormats.setOutputFormat(lstOutputFormat);		
		return outputFormats;
	}	
	public static void deleteAllReportFiles() {
		File outDirectory = new File("D:/out");
		for(File file : outDirectory.listFiles()) {
			file.delete();
		}
	}
	private static String xmlFileToString(String classpathXMLResource) {
		InputStream is = TestDataUtility.class.getResourceAsStream(classpathXMLResource);
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
			StringWriter stw = new StringWriter();
	        Transformer serializer = TransformerFactory.newInstance().newTransformer();
	        serializer.transform(new DOMSource(doc), new StreamResult(stw));
	        return stw.toString();
		} catch(Exception e ) {
			String msg = "Exception during read and convert of XML File to String : ["+classpathXMLResource+"]";
			System.out.println(msg);
			e.printStackTrace();
			throw new RuntimeException(msg, e);
		}
	}
	
	
	public static ReportParameters getBatchReportParams(Integer batchNo, Integer batchRevisionNo) {
		List<ReportParameter> params = new ArrayList<ReportParameter>(8);
		
		params.add( getReportParameter("rpInstallationName", 	"Billing Base", 					ReportParameterType.TEXT) );
		params.add( getReportParameter("rpBatchRunDate", 		""+System.currentTimeMillis(), 		ReportParameterType.TEXT) );
		params.add( getReportParameter("rpDatabaseURL", 		"jdbc:oracle:thin:@172.16.209.119:1601:DV_BILL_02", ReportParameterType.TEXT) );
		params.add( getReportParameter("rpDriver", 				"oracle.jdbc.driver.OracleDriver", 	ReportParameterType.TEXT) );
		params.add( getReportParameter("rpDatabaseUserId", 		"bpms_core", 						ReportParameterType.TEXT) );
		params.add( getReportParameter("rpDatabasePassword", 	"bpms_core",						ReportParameterType.TEXT) );
		params.add( getReportParameter("rpBatchNo", 			batchNo.toString(), 				ReportParameterType.INTEGER) );
		params.add( getReportParameter("rpBatchRevNo", 			batchRevisionNo.toString(), 		ReportParameterType.INTEGER) );
		
		ReportParameters reportParameters = new ReportParameters();
		reportParameters.setReportParameter(params);
		return reportParameters;
	}
	
	
}
