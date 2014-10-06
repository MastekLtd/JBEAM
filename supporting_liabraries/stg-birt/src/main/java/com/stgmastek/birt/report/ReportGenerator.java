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

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

import com.stgmastek.birt.report.beans.OutputFormat;
import com.stgmastek.birt.report.beans.Report;
import com.stgmastek.birt.report.beans.ReportGenerationMode;
import com.stgmastek.birt.report.beans.ReportParameter;
import com.stgmastek.birt.report.beans.ReportParameterType;
import com.stgmastek.birt.report.beans.ReportParameters;
import com.stgmastek.birt.report.cache.DefaultReportDesignCacheImpl;
import com.stgmastek.birt.report.cache.IReportDesignCache;
import com.stgmastek.birt.report.exceptions.ReportGenerationException;
import com.stgmastek.birt.report.exceptions.ReportServiceException;
import com.stgmastek.birt.report.parameters.IReportParameterTypeMapper;
import com.stgmastek.birt.report.rendering.types.IReportRenderOptionGenerator;
import com.stgmastek.birt.report.utils.IOUtils;
import com.stgmastek.birt.report.utils.MyLogger;
import com.stgmastek.birt.report.utils.ReportUtility;

/**
 * Implementation of {@link IReportGenerator IReportGenerator}.
 * 
 * @author Kedar Raybagkar
 */
public final class ReportGenerator implements IReportGenerator {	
	private static final Logger logger = Logger.getLogger(ReportGenerator.class);
	
	private static IReportDesignCache cache = null;
	static {
		cache = new DefaultReportDesignCacheImpl();
		cache.create("/cacheConfig.xml");
	}
	
	private IReportService reportService;
	private Map<ReportParameterType, IReportParameterTypeMapper> reportParameterTypeMappers;
	private Map<OutputFormat, IReportRenderOptionGenerator> reportRenderOptionGenerators;
	public void setReportService(IReportService reportService) {		
		this.reportService = reportService;
	}
	public void setReportParameterTypeMappers(Map<ReportParameterType, IReportParameterTypeMapper> reportParameterTypeMappers) {
		this.reportParameterTypeMappers = reportParameterTypeMappers;		
	}
	public void setReportRenderOptionGenerators(Map<OutputFormat, IReportRenderOptionGenerator> reportRenderOptionGenerators) {
		this.reportRenderOptionGenerators = reportRenderOptionGenerators;
	}
	
	/**
	 * Generates the report in an off-line manner.
	 * The report is executed in an asynchronous manner and the errors if any are logged.
	 * Please see {@link #generateOnLine(Report)} to generate the report in the same thread.
	 * 
	 * @param report to be generated.
	 * @throws ReportServiceException in case the service is shutdown and is unable to accept new reports.
	 */
	public void generateOffLine(Report report) {
		ArrayList<Report> list = new ArrayList<Report>();
		list.add(report);
		generateOffLine(list);
	}

	/**
	 * Generates the given list of reports in an off-line manner.
	 * 
	 * The report is executed in an asynchronous manner and the errors if any are logged.
	 * 
	 * @param reports list of reports to be generated
	 * @throws ReportServiceException in case the service is shutdown and is unable to accept new reports.
	 */
	public void generateOffLine(List<Report> reports) {
		if (this.reportService.isShutdown()) {
			throw new ReportServiceException("Service has been shutdown. Unable to accept new Reports.");
		}
		for (Report report : reports) {
			report.setGenerationMode( ReportGenerationMode.OFFLINE );
			RunnableReportTask task = new RunnableReportTask(report, this);
			this.reportService.submit(task);
			MyLogger.info(logger, "offline.report.submit", report.getFileName(), reportService.getCurrentPendingQueueSize());
		}
	}
	
	/**
	 * Caller executes the report.
	 * 
	 * @param report to be generated
	 * @return Absolute path of the generated report file
	 * @throws BirtException in case there is any exception thrown by BIRT
	 * @throws ReportServiceException in case the report service has been destroyed
	 */		
	public List<String> generateOnLine(Report report) {
		MyLogger.info(logger, "online.report.submit", report.getFileName());
		
//		//// Inject the temp file's name and path
//		List<String> tempFile = null;
//		try {
//			tempFile = IOUtils.getTempFileWithFullPath();	
//		} catch(IOException e) {
//			String msg = "Exception while creating temporary file. please check user privileges";
//			throw new ReportGenerationException(msg, e);
//		}		
//		report.setFilePath( tempFile.get(0) );
//		report.setFileName( tempFile.get(1) );
		
		return generateReport(report);
	}
	
	/**
	 * The method that generates the report.
	 * 
	 * @param report Report to be generated.
	 * @throws BirtException
	 */
	@SuppressWarnings("unchecked")
	private List<String> generateReport(Report report) {
		IRunAndRenderTask task = null;
		long time = System.currentTimeMillis();
		IReportEngine engine = null;
		List<String> generatedFiles = null;		
		
		try {
			confirmReportServiceExistence(report);			
			Validater.validateInput( report );
			
			//// Get Engine instance from the Service
			MyLogger.debug(logger, "engine.attempt", report.getFileName());
			engine = reportService.borrowEngine();
			MyLogger.debug(logger, "engine.acquired", report.getFileName());
			
			//// Generate ID for this Report
			report.setID( UUID.randomUUID().toString() );
			
			//// Get the runnable report from cache
			IReportRunnable design = cache.get( report.getDesignFileIdentifier() ); 
			if(design == null) {
				design = ReportUtility.getReportRunnable(engine, report);
				cache.put(report.getDesignFileIdentifier(), design);
			}
			MyLogger.debug(logger, "report.design.acquired", report.getFileName());
			
			//// Apply specified Design Manipulators
			// TODO :- Prasanna - Implementing Design Manipulators
			
			//// Create task to run and render the report, with its context
			task = engine.createRunAndRenderTask(design);
			task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ReportGenerator.class.getClassLoader());			
			
			//// If Report Parameters provided, extract them, stuff in Task Object & Validate the Task Object.
			//// Also, keep aside the XML Data Source temp file names, as these files need to be deleted later.
			ReportParameters parameters = report.getReportParameters();
			String xmlDataSourceTempFileFullPath = null;
			if(parameters != null) {
				for( ReportParameter parameter : parameters.getReportParameter() ) {
					IReportParameterTypeMapper mapper = reportParameterTypeMappers.get( parameter.getReportParameterType() );
					Serializable param = mapper.convert( parameter.getValue() );
					task.setParameterValue(parameter.getName(), param);
					
					if("rpXmlDatasource".equals(parameter.getName())) {
						xmlDataSourceTempFileFullPath = parameter.getValue();
					}
				}
				task.validateParameters();
				MyLogger.debug(logger, "parameters.provided", report.getFileName());
			}
			
			//// Generate Report, once for each Output Format.
			generatedFiles = new ArrayList<String>( report.getOutputFormats().getOutputFormat().size() );
			String reportcompleteFileName = ReportUtility.getCompleteFileName(report);
			for (OutputFormat outputFormat : report.getOutputFormats().getOutputFormat() ) {
				// Generate Render Options
				IReportRenderOptionGenerator generator = reportRenderOptionGenerators.get( outputFormat );
				IRenderOption renderOption = generator.getRenderOption();
				
				//// Generate Complete FileName. If file exists, delete it. If not able to delete, adjust file name.
				reportcompleteFileName = reportcompleteFileName + "." + outputFormat.value();
				MyLogger.info(logger, "report.file", report.getFileName(), reportcompleteFileName);
				File file = new File( reportcompleteFileName );
				if(file.exists()) {
					if( file.delete() == false) {
						MyLogger.error(logger, "file.deletion.failed", report.getFileName(), reportcompleteFileName);
						continue;	
					}
					MyLogger.info(logger, "file.deletion.success", report.getFileName());
				}
				renderOption.setOutputFileName( reportcompleteFileName );
				
				// Run and render report
				task.setRenderOption(renderOption);
				task.run();
				generatedFiles.add( reportcompleteFileName );
				
				long endTime = System.currentTimeMillis();
				if (logger.isInfoEnabled()) {
					String details = ReportLogger.generateLogReport(report, time, endTime, renderOption.getOutputFileName(), outputFormat, null);
					MyLogger.info(logger, "report.generation.success", report.getFileName(), details);
				}
			} // for all destinations.
			
			//// Finally, if XML Data Source, delete the temptorary files
			IOUtils.deleteFile( xmlDataSourceTempFileFullPath );
			
		} catch(Throwable t) {
			long endTime = System.currentTimeMillis();
			// run and render report
			if (logger.isEnabledFor(Level.ERROR)) {
				logger.error( ReportLogger.generateLogReport(report, time, endTime, null, null, t), t);
			}
			String msg = "Exception during Report Generation ["+t.getMessage()+"]";
			throw new ReportGenerationException(msg, t);
		} 
		finally {
			if (task != null) {
				task.close();
			}
			if (engine != null) {
				try {
					this.reportService.releaseEngine(engine);
				} catch (Exception e) {}
			}
		}
		return generatedFiles;
	}

	private void confirmReportServiceExistence(Report report) {
		//// Validate Service existence
		if (this.reportService.isDestroyed()) {
			throw new ReportServiceException("Report Service has been destroyed");
		}			
		MyLogger.info(logger, "report.pickup", report.getFileName(), report.getFilePath());
	}

//	private void extractReportParameters() {
//		
//	}
	
}