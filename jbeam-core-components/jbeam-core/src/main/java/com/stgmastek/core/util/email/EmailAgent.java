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
package com.stgmastek.core.util.email;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.util.ByteArrayDataSource;

import jdbc.pool.CConnectionPoolManager;
import jdbc.pool.CPoolAttribute;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import stg.pr.engine.Singleton;
import stg.pr.engine.mailer.CMailer;
import stg.pr.engine.mailer.EMail;
import stg.pr.engine.mailer.EMailAttachment;
import stg.pr.engine.mailer.SmallEmailAttachment;

import com.stg.crypto.PBEEncryptionRoutine;
import com.stgmastek.birt.report.ReportGenerator;
import com.stgmastek.birt.report.beans.OutputFormat;
import com.stgmastek.birt.report.beans.Report;
import com.stgmastek.birt.report.beans.ReportBeanFactory;
import com.stgmastek.birt.report.beans.ReportGenerationMode;
import com.stgmastek.birt.report.beans.ReportParameter;
import com.stgmastek.birt.report.beans.ReportParameterType;
import com.stgmastek.birt.report.beans.ReportParameters;
import com.stgmastek.core.aspects.Email.EMAIL_TYPE;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Configurations;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * The emailing agent for the system 
 * 
 * Implements Singleton pattern.
 *   
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public final class EmailAgent {

	private static final Logger logger = Logger.getLogger(EmailAgent.class);
	
	/** The static instance of the agent */	
	private static EmailAgent agent = new EmailAgent();
	
	/** Private constructor to outside instantiation */
	private EmailAgent(){}
	
	private static EMailAttachment image = null;
	
	/**
	 * Public static method to get the instance of the agent 
	 * 
	 * @return the agent as instance of {@link EmailAgent} 
	 */
	public static EmailAgent getAgent(){
		return agent;		
	}
	
	/**
	 * Public instance method to actually send out the email 
	 * 
	 * @param context
	 * 		  The batch context 
	 * @param TYPE 
	 * 		  The type of the email to send 
	 * @param batchObject 
	 * 		  The batch object under consideration. May be null. 
	 * @param objectDetails 
	 * 		  The batch object details as set in the object map. May be null. 
	 * @param lastEmail 
	 * 		  Boolean. True to indicate that this is the last email. 
	 * @return true, if the email has been sent successfully 
	 */
	public Boolean sendEmail(BatchContext context, EMAIL_TYPE TYPE,
			BatchObject batchObject, ObjectMapDetails objectDetails,
			Boolean lastEmail) {

		EMail email = getEmailInstance(context, TYPE, batchObject,
				objectDetails, lastEmail);
		if (email == null) { // As the handler did sent a null object indicating
								// that the email need not be sent.
			return true;
		}

		// Send the email
		try {
			CMailer mailer = CMailer.getInstance("SMTP");
			if (Constants.MODE.equals(Constants.PRE))
				mailer.sendAsyncMail(email);
			else
				mailer.sendMail(email);
		} catch (MessagingException e) {
			if (logger.isEnabledFor(Level.FATAL)) {
				logger.fatal(e);
			}
		}
		return true;
	}
	
	/**
	 * Create {@link EMail} instance with given content handler.
	 * Set the email contents as per the batch status.    
   	 *
	 * @param context
	 * 		  The batch context 
	 * @param TYPE 
	 * 		  The type of the email to send 
	 * @param batchObject 
	 * 		  The batch object under consideration. May be null. 
	 * @param objectDetails 
	 * 		  The batch object details as set in the object map. May be null. 
	 * @param lastEmail 
	 * 		  Boolean. True to indicate that this is the last email.
	 * 
	 * @return email instance with required attachments.
	 */
	private EMail getEmailInstance(BatchContext context, EMAIL_TYPE TYPE,
			BatchObject batchObject, ObjectMapDetails objectDetails,
			Boolean lastEmail) {
		
		//Get the content 
		Object contentHandler = getContentHandler(TYPE);
		EMail email = ((IEmailContentGenerator) contentHandler)
				.getContent(context, TYPE, batchObject, objectDetails,
						lastEmail);
		
		if (email == null) {
			return null;
		}
		
		try {
			if (email.getEMailType() == null) {
				email.setEMailType(EMail.TYPE.HTML);
			}
			String emailId = Configurations.getConfigurations()
					.getConfigurations("CORE", "EMAIL", "NOTIFICATION_GROUP");
			
			if (email.getTORecipient().length == 0) {
				email.setTORecipient(emailId);
			}
			if (email.getMessageBodyHeader() == null) {
				email.setMessageBodyHeader(" ");
			}

			if (email.getMessageBodyFooter() == null) {
				email.setMessageBodyFooter(" ");
			}
			if (email.getSubject() == null) {
				email.setSubject(getJBeamReportSubject(context) + TYPE);
			}
			email.getAttachments().add(getJbeamLogo());

			if (TYPE == EMAIL_TYPE.WHEN_BATCH_ENDS) {
				Report report = generateBatchEndReport(context);
				email.getAttachments().add(getSmallEmailAttachment(context, report));
			}
		} catch (MessagingException e) {
			if (logger.isEnabledFor(Level.FATAL)) {
				logger.fatal(e);
			}
		} catch (IOException e) {
			if (logger.isEnabledFor(Level.FATAL)) {
				logger.fatal(e);
			}
		}
		return email;
	}
	
	
	/**
	 * Retrieve instance of content handler configured in CORE schema. 
	 *  
	 * @param TYPE
	 *        The {@link EMAIL_TYPE} instance
	 * 
	 * @return content handler object 
	 */
	private Object getContentHandler(EMAIL_TYPE TYPE) {
		// Get the registered implementing class
		String registeredclassName = null;
		registeredclassName = Configurations.getConfigurations()
				.getConfigurations("CORE", "EMAIL", TYPE.name());
		if (registeredclassName == null) {
			registeredclassName = Configurations.getConfigurations()
					.getConfigurations("CORE", "EMAIL", "CONTENT_HANDLER");
		}
		Object contentHandler = null;

		// Load the class
		try {
			Class<?> clazz = Class.forName(registeredclassName);
			contentHandler = clazz.newInstance();

			if (!(contentHandler instanceof IEmailContentGenerator))
				throw new Exception();

		} catch (Exception e) {
			if (logger.isEnabledFor(Level.WARN)) {
				logger.warn("Invocation of the registered class "
						+ registeredclassName
						+ " failed. The registered class must implement com.stgmastek.core.util.email.IEmailContentGenerator. "
						+ "Using systems default implementation..", e);
			}
		}

		if (contentHandler == null
				|| !(contentHandler instanceof IEmailContentGenerator)) {
			try {
				contentHandler = DefaultEmailContentGenerator.class
						.newInstance();
			} catch (InstantiationException e) {
				if (logger.isEnabledFor(Level.FATAL)) {
					logger.fatal("Unable to instantiate default system class ",
							e);
				}
			} catch (IllegalAccessException e) {
				if (logger.isEnabledFor(Level.FATAL)) {
					logger.fatal("Unable to instantiate default system class ",
							e);
				}
			}
		}
		return contentHandler;

	}
	
	/**
	 * Generate online report for batch end. 
	 * It also returns {@link Report} instance which would be used 
	 * for email attachment. 
	 * 
	 * @param context
	 * 		  The batch context 
	 * 
	 * @return {@link Report} instance
	 */
	private Report generateBatchEndReport(BatchContext context) {
		Report report = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Initializing Report Generator");
			}
			Singleton<?> singleton = context.getPreContext().getSingleton(
					Constants.PRE_SINGLETON_PLUGINS.ReportGenerator.name());

			if (singleton == null)
				throw new NullPointerException(
						"Could not get the ReportGenerator instance from PRE plugin");

			ReportGenerator generator = (ReportGenerator) singleton
					.getInstance();
			report = getReport(context);
			generator.generateOnLine(report);
		} catch (Throwable e) {
			logger.fatal("Error while generating Report ", e);
		}
		return report;
	}
	/**
	 * Create attachment for email with JBEAM end report.
	 * 
	 * @param context
	 * 			The batch context 
	 * 
	 * @param report
	 * 			The report instance 
	 * 
	 * @return {@link SmallEmailAttachment} instance
	 *  
	 * @throws IOException
	 */
	private SmallEmailAttachment getSmallEmailAttachment(BatchContext context,
			Report report) throws IOException {
		SmallEmailAttachment attachment = new SmallEmailAttachment(
				getJBeamReportFileName(context), 
				(report.getFilePath() + report.getFileName()));
		return attachment;
	}
	
	/**
	 * Create {@link Report} instance from {@link ReportBeanFactory}.
	 * Set the properties for online report generation.
	 *  
	 * @param context
	 * 		  The batch context
	 * 
	 * @return {@link Report} instance
	 */
	private Report getReport(BatchContext context) {
		Report report = ReportBeanFactory.createReport();
		report.setGenerationMode(ReportGenerationMode.ONLINE);
		report.setOutputFormats(ReportBeanFactory
				.createOutputFormats(OutputFormat.PDF));
		report.setDesignFileIdentifier("jbeam.reports.JbeamLogReport.xml");
		report.setFilePath(getJBeamOutputFolder());
		report.setFileName(getJBeamReportFileName(context));
		report.setID(context.getBatchInfo().getBatchNo() + "");
		report.setReportParameters(getReportParameters(context));
		return report;
	}

	/**
	 * Create JBeam report file name.
	 *  
	 * @param context
	 *  	  The batch context instance 
	 *  
	 * @return JBeam report file name
	 */
	private String getJBeamReportFileName(BatchContext context){
		StringBuilder builder = new StringBuilder();
		builder.append("JBeam-");
		builder.append(context.getBatchInfo().getBatchNo());
		builder.append("-");
		builder.append(context.getBatchInfo().getBatchRevNo());
		builder.append(".pdf");
		return builder.toString();
	}
	
	/**
	 * Create JBeam report subject.
	 *  
	 * @param context
	 *  	  The batch context instance 
	 *  
	 * @return JBeam report file name
	 */
	private String getJBeamReportSubject(BatchContext context){
		StringBuilder builder = new StringBuilder();
		builder.append("Installation #");
		builder.append(Configurations.getConfigurations().getConfigurations(
				"CORE", "INSTALLATION", "NAME"));
		builder.append("| Batch # ");
		builder.append(context.getBatchInfo().getBatchNo());
		builder.append("| Revision # ");
		builder.append(context.getBatchInfo().getBatchRevNo());
		builder.append("|");
		return builder.toString();
	}
	
	/**
	 * Retrieve the name of output folder configured in CORE schema.
	 *  
	 * @return name of output folder
	 */
	private String getJBeamOutputFolder() {
		String outputFolder = Configurations.getConfigurations()
				.getConfigurations("CORE", "REPORT_RUNTIME", "OUTPUT_FOLDER");
		if (outputFolder == null) {
			outputFolder = "";
		} else {
			if (!outputFolder.endsWith("/")) {
				outputFolder += "/";
			}
		}
		return outputFolder;
	}

	/**
	 * Create the {@link ReportParameters} instance from {@link ReportBeanFactory}
	 * Add {@link ReportParameter} with 
	 * @param context
	 * @return
	 */
	private ReportParameters getReportParameters(BatchContext context){
		ReportParameters parameters = ReportBeanFactory
				.createReportParameters();
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpInstallationName",
					Configurations.getConfigurations().getConfigurations("CORE", "INSTALLATION", "NAME"),
					ReportParameterType.TEXT));
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpBatchNo", 
					context.getBatchInfo().getBatchNo()	+ "", 
					ReportParameterType.INTEGER));
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpBatchRevNo", 
					context.getBatchInfo().getBatchRevNo()+ "", 
					ReportParameterType.INTEGER));
		// parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpBatchRunDate",
		// new java.sql.Date(context.getBatchInfo().getBatchRunDate().getTime())+"",
		// ReportParameterType.LONG));
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpBatchRunDate", 
					context.getBatchInfo().getBatchRunDate().getTime()	+ "", 
					ReportParameterType.DATE_SQL));
		CPoolAttribute attrib = CConnectionPoolManager.getInstance()
				.getPoolAttributes(Constants.POOL_NAMES.BATCH.name());
		
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpDatabaseURL", 
					attrib.getURL(), ReportParameterType.TEXT));
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpDriver", 
					attrib.getDriver(), ReportParameterType.TEXT));
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpDatabaseUserId",
					attrib.getUser(), ReportParameterType.TEXT));
		PBEEncryptionRoutine encryptor = new PBEEncryptionRoutine();
		parameters.getReportParameter().add(ReportBeanFactory.createReportParameter("rpDatabasePassword",
					encryptor.decode(attrib.getPassword()),	ReportParameterType.TEXT));
		return parameters;		
	}
    /**
     * Returns the input stream for the given resource.
     * 
     * @param clazz
     * @param path
     * @return InputStream
     * @throws IOException
     */
    public InputStream getResourceInputStream(Class<?> clazz, String path) throws IOException {
        InputStream is = null;
        if ( clazz != null ) {
            is = clazz.getResourceAsStream( path );
        }
        
        if ( is == null ) {
        	is = this.getClass().getClassLoader().getResourceAsStream( path );
        }

        if ( is == null ) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream( path );
        }

        if ( is == null ) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream( path );
        }

        if ( is == null ) {
            throw new FileNotFoundException( "'" + path + "' cannot be opened because it does not exist" );
        }
        return is;
    }	
	
    /**
     * Returns the JBeam Logo as an email attachment.
     * 
     * @return EMailAttachment
     * @throws IOException
     */
    public EMailAttachment getJbeamLogo() throws IOException {
    	synchronized (this) {
    		if (EmailAgent.image == null) {
    			InputStream is = null;
    			ByteArrayDataSource byteDataSource = null;
    			try {
    				is = getResourceInputStream(this.getClass(), "jbeam/resources/jbeam.gif");
    				byteDataSource = new ByteArrayDataSource( is, "image/gif");
    			} catch (IOException e) {
    				logger.error("Unable to read jbeam/resources/jbeam.gif", e);
    			} finally {
    				if (is != null) {
    					is.close();
    				}
    			}
    			if (byteDataSource != null) {
    				EmailAgent.image = new SmallEmailAttachment("jbeam.gif",  byteDataSource);
    				EmailAgent.image.setDisposition(Part.INLINE);
    			}
    		}
			
		}
		return EmailAgent.image;
    }
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/email/EmailAgent.java                                                                               $
 * 
 * 31    7/07/10 11:43a Kedarr
 * Removed SOP
 * 
 * 30    6/30/10 10:52a Kedarr
 * Changed the method call from off line to on line report service.
 * 
 * 29    6/15/10 11:02a Kedarr
 * Modified to make use of the BIRT stgmastek jar file.
 * 
 * 28    5/18/10 6:06p Kedarr
 * Corrected the spelling mistake in the configuration key.
 * 
 * 27    5/18/10 3:28p Kedarr
 * Added output folder for Reports
 * 
 * 26    5/18/10 10:19a Kedarr
 * Changed as per the changes in report generator
 * 
 * 25    5/17/10 10:02a Kedarr
 * Changed the name of the outfile.
 * 
 * 24    5/13/10 6:39p Kedarr
 * modified to pass batch context.
 * 
 * 23    5/13/10 6:30p Kedarr
 * modified to pass batch context.
 * 
 * 22    5/13/10 6:11p Kedarr
 * 
 * 21    5/13/10 5:56p Kedarr
 * 
 * 20    5/13/10 4:43p Kedarr
 * Modified to generate report for when batch ends.
 * 
 * 19    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 18    4/23/10 2:19p Kedarr
 * Installation name added to subject line.
 * 
 * 17    4/13/10 2:32p Kedarr
 * Changes made to add the escalation level and for passing object details as changed in the interface.
 * 
 * 16    4/06/10 5:08p Kedarr
 * corrected java doc for missing tags or improper tags due to change in method signatures.
 * 
 * 15    4/06/10 4:28p Kedarr
 * Changes made to have a common method to get the logo
 * 
 * 14    4/06/10 12:57p Kedarr
 * Added a method to get the input stream for the resouce using various methods.
 * 
 * 13    4/06/10 12:48p Kedarr
 * Changed the resource stream to invoke from the system class loader
 * 
 * 12    4/06/10 12:27p Kedarr
 * Changed the resource stream to invoke from the class loader.
 * 
 * 11    4/06/10 10:52a Kedarr
 * Changed image data source to byte array data source from javax activation util pacakge.
 * 
 * 10    4/01/10 3:24p Kedarr
 * Changes made as per the newer demo version of PRE
 * 
 * 9     3/25/10 3:30p Kedarr
 * Added a new parameter.
 * 
 * 8     3/25/10 9:03a Kedarr
 * Email can be null in case the content handler does not want to send any email or has not implemented the type.
 * 
 * 7     3/24/10 3:06p Kedarr
 * Changes made to send EMail as a return object in the interface Email Content generator
 * 
 * 6     3/24/10 12:56p Kedarr
 * Now each email type can have its very own email handler. If not set then it will fall back on the default email handler. Changes made to accept batch object as one of the
 * parameter.
 * 
 * 5     1/07/10 5:37p Grahesh
 * Updated Java Doc comments
 * 
 * 4     12/28/09 11:50a Grahesh
 * Sending asnyc email when in PRE mode.
 * 
 * 3     12/18/09 12:32p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/