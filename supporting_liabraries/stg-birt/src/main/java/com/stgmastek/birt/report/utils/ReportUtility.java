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
package com.stgmastek.birt.report.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;

import stg.utils.ResourceUtils;

import com.stgmastek.birt.report.beans.Report;
import com.stgmastek.birt.report.exceptions.ReportServiceException;

public class ReportUtility {
	public static IReportRunnable getReportRunnable(IReportEngine engine, Report report) {
		// Convert the Design File Identifier to relative (from classes) path
		String reportFileFQName = report.getDesignFileIdentifier();
		int extensionDotIndex = reportFileFQName.lastIndexOf('.');
		String reportFileName = reportFileFQName.substring(0, extensionDotIndex);
		String designFileRelativePath = "/" + reportFileName.replace('.', '/') + reportFileFQName.substring(extensionDotIndex);  		
		
		// Read Design File
		InputStream is;
        try {
            is = ResourceUtils.getResourceAsStream(ReportUtility.class, designFileRelativePath);
        } catch (IOException e) {
            throw new ReportServiceException("Could not locate Report Design file #" + designFileRelativePath, e);
        }
		
		// convert to Byte Array Stream
		ByteArrayInputStream bis = null;
		try {
			byte bytes[] = IOUtils.toByteArray( is );
			bis = new ByteArrayInputStream( bytes );	
		} catch(IOException e) {
			String msg = "Exception while converting the read InputStream of design file : ["+designFileRelativePath+"] to ByteArrayStream";
			throw new ReportServiceException(msg, e);
		}
		
		IReportRunnable reportRunnable = null;
		try {
			reportRunnable = engine.openReportDesign( bis );	
		} catch(Exception e) {
			String msg = "Exception while creating Runnable Report : ["+e.getMessage()+"] for Design File : ["+designFileRelativePath+"]";
			throw new ReportServiceException(msg, e);
		}
		
		return reportRunnable;
	}
	
	private static StringBuffer buffer = new StringBuffer(1000);
	public static String getCompleteFileName(Report report) {
		buffer.setLength(0);
		
		buffer.append(report.getFilePath());
		if( report.getFilePath().endsWith("/") == false )
			buffer.append("/");
		
		// strip extension, if provided
		int separaterIndex = report.getFileName().lastIndexOf('.');
		String fileName = report.getFileName();
		if( separaterIndex != -1 ) {
			fileName = fileName.substring(0, separaterIndex);
		}
		buffer.append(fileName);
		return buffer.toString();
	}
}
