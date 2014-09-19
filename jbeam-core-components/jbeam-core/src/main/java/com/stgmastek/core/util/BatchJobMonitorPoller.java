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
package com.stgmastek.core.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import stg.pr.engine.mailer.CMailer;
import stg.pr.engine.mailer.EMail;

import com.stg.logger.LogLevel;
import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.Constants.CONTEXT_KEYS;
import com.stgmastek.core.util.email.DefaultEmailContentGenerator;
import com.stgmastek.core.util.email.EmailAgent;
import com.stgmastek.core.util.email.IEmailContentGenerator;

/**
 * A background running class that keeps on checking for objects that have taken
 * more than maximum time for execution as well as objects that have taken less
 * than minimum time as configured against the <code>object_map</code>.
 * 
 * 
 * @author Kedar Raybagkar
 * @since
 */
public class BatchJobMonitorPoller extends BasePoller {

	private Map<String, ObjectMapDetails> objectMapping;
	private static transient Logger logger = Logger.getLogger(BatchJobMonitorPoller.class);
	private final String installatioName;

	/**
	 * The constructor that takes the {@link BatchContext} as an argument.
	 * 
	 * @param context
	 */
	public BatchJobMonitorPoller(BatchContext context) {
		super(context);
		installatioName = Configurations.getConfigurations().getConfigurations("CORE", "INSTALLATION", "NAME");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.stgmastek.core.util.BasePoller#execute()
	 */

	protected void execute() throws BatchException {
		int sleepTime;
		try {
			sleepTime = Integer.parseInt(Configurations.getConfigurations().getConfigurations("CORE", "JOB_MONITOR_POLLER", "WAIT_PERIOD"));
		} catch (NumberFormatException e) {
			sleepTime = 10;
		}
		try {
			while (!STOP_POLLING) {
				checkJobsExecutedLessThanMinTime();
				checkTimeConsumingJobs();
				if (logger.isInfoEnabled()) {
					logger.info("BatchJobMonitor would poll again for monitor instruction in next " + sleepTime + " minute(s)");
				}
				Thread.yield();
				try {
					TimeUnit.MINUTES.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
			}
		} catch (BatchException e) {
			throw e;
		} finally {
			if (logger.isEnabledFor(LogLevel.NOTICE)) {
				logger.log(LogLevel.NOTICE, "BatchJobMonitor has stopped its polling.");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.stgmastek.core.util.BasePoller#init()
	 */

	protected void init() throws BatchException {
	}

	private void sendEmail(BatchContext batchContext, List<BatchObject> list, boolean timeConsuming) throws MessagingException {
		IEmailContentGenerator generator = new DefaultEmailContentGenerator();
		StringBuilder sb = new StringBuilder();
		sb.append("  <HTML> ");
		sb.append("  <HEAD> ");
		sb.append("  <META NAME='GENERATOR' Content='Microsoft Visual Studio 6.0'> ");
		sb.append("  <TITLE></TITLE> ");
		sb.append(generator.getDefaultStyleSheet());
		sb.append("  </HEAD> ");
		sb.append("  <BODY> ");
		sb.append(generator.getDefaultHeader());
		sb.append(" <HR width='100%' size='1' />");
		sb.append("    <TABLE width='100%' border=0>		");
		sb.append("      <TR> ");
		if (timeConsuming) {
			sb.append("        <TD class='blockHeader'>Stuck Batch Objects</TD> ");
		} else {
			sb.append("        <TD class='blockHeader'>Batch Objects That Took Less than Minimum Time</TD> ");
		}
		sb.append("      </TR> ");
		sb.append("      <TR> ");
		sb.append("        <TD> ");
		sb.append("         <TABLE width='100%' border=0 class='dTable'>	");
		sb.append("           <TR> ");
		sb.append("             <TD width='5%' class='trHeader'>Sequence#</TD> ");
		sb.append("             <TD width='10%' class='trHeader'>Object Name</TD> ");
		sb.append("             <TD width='20%' class='trHeader'>Job Description</TD> ");
		sb.append("             <TD width='10%' class='trHeader'>Job Type</TD> ");
		sb.append("             <TD width='10%' class='trHeader'>Escalation Level</TD> ");
		sb.append("             <TD width='10%' class='trHeader'>Start Time</TD> ");
		if (timeConsuming) {
			sb.append("             <TD width='20%' class='trHeader'>Max Time</TD> ");
		} else {
			sb.append("             <TD width='10%' class='trHeader'>End Time</TD> ");
			sb.append("             <TD width='10%' class='trHeader'>Min Time</TD> ");
		}
		sb.append("           </TR> ");
		boolean alternateColor = true;
		for (BatchObject batchObject : list) {
			sb.append("           <TR> ");
			sb.append("             <TD width='5%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getSequence() + "</TD> ");
			sb.append("             <TD width='10%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getObjectName() + "</TD> ");
			sb.append("             <TD width='20%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getJobDesc() + "</TD> ");
			sb.append("             <TD width='10%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getJobType() + "</TD> ");
			sb.append("             <TD width='10%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>"
			        + objectMapping.get(batchObject.getObjectName().toUpperCase()).getEscalationLevel() + "</TD> ");
			sb.append("             <TD width='10%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getObjExecStartTime() + "</TD> ");
			if (timeConsuming) {
				sb.append("             <TD width='20%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getMaxTime() + "</TD> ");
			} else {
				sb.append("             <TD width='10%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getObjExecEndTime() + "</TD> ");
				sb.append("             <TD width='10%' class='" + (alternateColor ? "trRow1" : "trRow2") + "'>" + batchObject.getMinTime() + "</TD> ");
			}
			sb.append("           </TR> ");
			if (alternateColor) {
				alternateColor = false;
			} else {
				alternateColor = true;
			}
		}
		sb.append("         </TABLE> ");
		sb.append("        </TD> ");
		sb.append("      </TR> ");
		sb.append("    </TABLE> ");
		sb.append("    <BR /> ");
		sb.append(generator.getDefaultBatchInfo(batchContext));
		sb.append(generator.getDefaultFooter());
		sb.append("  </BODY> ");
		sb.append("  </HTML> ");

		EMail email = new EMail();
		email.setTORecipient(Configurations.getConfigurations().getConfigurations("CORE", "EMAIL", "NOTIFICATION_GROUP"));
		email.setEMailType(EMail.TYPE.HTML);
		email.setSubject("Installation " + installatioName + " Batch # " + context.getBatchInfo().getBatchNo() + "| Revision # " + context.getBatchInfo().getBatchRevNo() + "| "
		        + (timeConsuming ? "Stuck Objects" : "Attention Required - Minimum Time Alert"));
		email.setMessageBody(sb.toString());
		email.setMessageBodyFooter(""); // as the header and footer are set by
										// default.
		email.setMessageBodyHeader("");
		try {
			email.getAttachments().add(EmailAgent.getAgent().getJbeamLogo());
		} catch (IOException e) {
			logger.error(e);
		}
		CMailer mailer = CMailer.getInstance("SMTP");

		// if (Constants.MODE.equals(Constants.PRE))
		// mailer.sendAsyncMail(email);
		// else
		mailer.sendMail(email);

	}

	/**
	 * Checks for all time consuming jobs. (Currently running jobs who have
	 * taken more time to execute than the max time set in the
	 * <code>object_map</code>)
	 * 
	 * @throws BatchException
	 */
	@SuppressWarnings("unchecked")
	private void checkTimeConsumingJobs() throws BatchException {
		if (objectMapping == null) {
			objectMapping = (Map<String, ObjectMapDetails>) context
					.getPreContextAttribute(
							context.getBatchInfo().getBatchNo(),
							CONTEXT_KEYS.JBEAM_OBJECT_MAP.name());
		}

		Connection dedicatedConnection = null;
		try {
			if (objectMapping != null) {
				dedicatedConnection = context.getBATCHConnection();
				IBatchDao bDao = DaoFactory.getBatchDao();
				List<BatchObject> list = bDao.getCurrentlyRunningJobs(context.getBatchInfo().getBatchNo(), context.getBatchInfo().getBatchRevNo(), dedicatedConnection);
				List<BatchObject> timeConsumingObjectList = new ArrayList<BatchObject>();
				long currentTime = System.currentTimeMillis();
				if (list.size() > 0) {
					for (BatchObject obj : list) {
						ObjectMapDetails detail = objectMapping.get(obj.getObjectName().toUpperCase());
						if (detail != null) {
							if (detail.getMaxTime() == null) {
								detail = new ObjectMapDetails();
								detail.setMaxTime(2000);
							}
							if (TimeUnit.MILLISECONDS.convert(detail.getMaxTime(), TimeUnit.MINUTES) < currentTime - obj.getObjExecStartTime().getTime()) {
								obj.setMaxTime(detail.getMaxTime());
								obj.setEscalationLevel((detail.getEscalationLevel() == null) ? "DEFAULT" : detail.getEscalationLevel());
								timeConsumingObjectList.add(obj);
							}
						} else {
							// simply ignore the object for which the map is not
							// set.
						}
					}
					if (timeConsumingObjectList.size() > 0) {
						sendEmail(context, timeConsumingObjectList, true);
					}
				}
			}
		} catch (Exception sqe) {
			logger.error("Exception caught", sqe);
			throw new BatchException(sqe);
		} finally {
			if (dedicatedConnection != null) {
				try {
					dedicatedConnection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	/**
	 * Checks for jobs whose total time for execution is less than the minimum
	 * time configured in the object map.
	 * 
	 * @throws BatchException
	 */
	private void checkJobsExecutedLessThanMinTime() throws BatchException {
		Connection dedicatedConnection = null;
		try {
			dedicatedConnection = context.getBATCHConnection();
			IBatchDao bDao = DaoFactory.getBatchDao();
			List<BatchObject> list = bDao.getJobsExecutedLessThanMinTime(context.getBatchInfo().getBatchNo(), context.getBatchInfo().getBatchRevNo(), dedicatedConnection);
			for (BatchObject obj : list) {
				ObjectMapDetails detail = objectMapping.get(obj.getObjectName().toUpperCase());
				if (detail == null) {
					detail = new ObjectMapDetails();
				}
				obj.setEscalationLevel((detail.getEscalationLevel() == null) ? "DEFAULT" : detail.getEscalationLevel());
			}
			if (list.size() > 0) {
				sendEmail(context, list, false);
			}
		} catch (Exception sqe) {
			logger.error("Exception caught", sqe);
			throw new BatchException(sqe);
		} finally {
			if (dedicatedConnection != null) {
				try {
					dedicatedConnection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
