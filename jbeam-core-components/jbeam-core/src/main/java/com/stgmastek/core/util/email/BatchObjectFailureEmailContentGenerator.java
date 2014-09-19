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


import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import stg.pr.engine.mailer.EMail;

import com.stgmastek.core.aspects.Email;
import com.stgmastek.core.aspects.Email.EMAIL_TYPE;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Configurations;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * Class to generate an email for batch failure. 
 * 
 * @author Kedar Raybagkar
 *
 */
public class BatchObjectFailureEmailContentGenerator extends DefaultEmailContentGenerator {

	private static Logger logger = Logger.getLogger(BatchObjectFailureEmailContentGenerator.class);
	
	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.email.DefaultEmailContentGenerator#getContent(com.stgmastek.core.util.BatchContext, com.stgmastek.core.aspects.Email.EMAIL_TYPE, com.stgmastek.core.util.BatchObject, com.stgmastek.core.util.ObjectMapDetails, java.lang.Boolean)
	 */
	public EMail getContent(BatchContext context, EMAIL_TYPE TYPE, BatchObject batchObject, ObjectMapDetails objectDetails, Boolean lastEmail) {
		EMail email = new EMail();
		if (TYPE == Email.EMAIL_TYPE.WHEN_OBJECT_FAILS) {
			email.setMessageBody(getEmailContent(context, batchObject, objectDetails, lastEmail));
			email.setSubject("Installation '" + Configurations.getConfigurations().getConfigurations("CORE", "INSTALLATION", "NAME")
					+ "' Batch # " 
					+ context.getBatchInfo().getBatchNo() 
					+ "| Revision # " + context.getBatchInfo().getBatchRevNo()
					+ "| Sequence#" + batchObject.getSequence() + "| Object Name#" + batchObject.getObjectName() + " has failed.");
			if (objectDetails == null) {
				logger.error("Object Map not set.. Could not send an object failure email #" + batchObject.getObjectName() + "[" + batchObject.getSequence() + "]");
				return null;
			}
			String toRecipient = Configurations.getConfigurations().getConfigurations("CORE", objectDetails.getEscalationLevel(), "EMAIL");
			if (toRecipient != null) {
				try {
					email.setTORecipient(toRecipient);
				} catch (AddressException e) {
					logger.error("Unable to set the recipient list as stored against level \"" + objectDetails.getEscalationLevel() + "\".", e );
					if (logger.isInfoEnabled()) {
						logger.info("This email will be routed to the default email recipients as configured");
					}
				}
			}
		} else {
			email.setMessageBody("The Content Handler is not currently set for the type " + TYPE.name() + ". Please set that in the configurations.");
		}
		return email;
	}

	/**
	 * Helper method to create the content when the batch starts  
	 * 
	 * @param context
	 * 		  The batch context 
	 * @param batchObject
	 * 		  The batch object under consideration.
	 * @param objectDetails
	 * @param lastEmail
	 * @return the content of the email as string
	 */
	private String getEmailContent(BatchContext context, BatchObject batchObject, ObjectMapDetails objectDetails, Boolean lastEmail){
        String str = "";
        if (lastEmail) {
        	str = "<br><br><font class=\"trHeader\">" +
        				"<B>Please Note :</B></font><font class=\"trRow1\">This is the last email for FAILED Objects as maximum number " +
        				"of email alerts has been reached. Please contact help desk for further asistance." +
        			"</font>";
        }
		StringBuilder sb = new StringBuilder();
		sb.append("  <HTML> ");
		sb.append("  <HEAD> ");
		sb.append("  <META NAME='GENERATOR' Content='Microsoft Visual Studio 6.0'> ");
		sb.append("  <TITLE></TITLE> ");
		sb.append(getDefaultStyleSheet());
		sb.append("  </HEAD> ");
		sb.append("  <BODY> ");		
		sb.append(getDefaultHeader());
		sb.append(" <HR width='100%' size='1' />");
		sb.append("    <TABLE width='100%' border=0>		");  	 
		sb.append("      <TR> ");
		sb.append("        <TD class='blockHeader'>Batch Object " + batchObject.getObjectName() + " <font color=\"red\">FAILED.</font>" + str +"</TD>");
		sb.append("      </TR> ");
		sb.append("    </TABLE>");
		sb.append("    <BR/> ");
		sb.append("    <TABLE width='50%' border=0>		");  	 
		sb.append("      <TR> ");
		sb.append("        <TD class='blockHeader'>FAILED Batch Object Details</TD> ");
		sb.append("      </TR> ");
		sb.append("      <TR> ");
		sb.append("        <TD> ");
		sb.append("         <TABLE width='100%' border=0 class='dTable'>	");	   
		sb.append("           <TR> ");
		sb.append("             <TD width='48%' class='trHeader'>Name</TD> ");
		sb.append("             <TD width='60%' class='trHeader'>Value</TD> ");
		sb.append("           </TR> ");
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trRow1'>Sequence# :</TD> ");
		sb.append("             <TD width='60%' class='trRow1'>" + batchObject.getSequence() + "</TD> ");
		sb.append("           </TR> ");
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trRow2'>Object Name :</TD> ");
		sb.append("             <TD width='60%' class='trRow2'>" + batchObject.getObjectName() + "</TD> ");
		sb.append("           </TR> ");
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trRow1'>Job Description :</TD> ");
		sb.append("             <TD width='60%' class='trRow1'>" + batchObject.getJobDesc() + "</TD> ");
		sb.append("           </TR> ");
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trRow2'>Job Type :</TD> ");
		sb.append("             <TD width='60%' class='trRow2'>" + batchObject.getJobType() + "</TD> ");
		sb.append("           </TR> ");
		sb.append("           <TR> ");
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trRow2'>Criticality :</TD> ");
		sb.append("             <TD width='60%' class='trRow2'>" + ((objectDetails == null)?"unknown":objectDetails.getEscalationLevel()) + "</TD> ");
		sb.append("           </TR> ");
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trRow1'>Error Type:</TD> ");
		sb.append("             <TD width='60%' class='trRow1'>" + batchObject.getErrorType() + "</TD> ");
		sb.append("           </TR> ");
		sb.append("           <TR> ");
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trRow2'>Error Description:</TD> ");
		sb.append("             <TD width='60%' class='trRow2'>" + batchObject.getErrorDescription() + "</TD> ");
		sb.append("           </TR> ");
		sb.append("         </TABLE> ");
		sb.append("        </TD> ");
		sb.append("      </TR> ");
		sb.append("    </TABLE> ");
		sb.append("    <BR /> ");
		sb.append(getDefaultBatchInfo(context));
		sb.append(getDefaultFooter());
		sb.append("  </BODY> ");
		sb.append("  </HTML> ");

		return sb.toString();
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/email/BatchObjectFailureEmailContentGenerator.java                                                  $
 * 
 * 3     4/23/10 2:21p Kedarr
 * Installation name added to subject line.
 * 
 * 2     4/13/10 2:32p Kedarr
 * Changes made to add the escalation level and for passing object details as changed in the interface.
 * 
 * 1     4/12/10 12:27p Kedarr
 * Moved from project jbeam-impl as this is now the part of the core.
 * 
 * 7     4/07/10 4:49p Kedarr
 * Updates made as per the PRE upgrade.
 * 
 * 6     4/06/10 2:43p Kedarr
 * Changes made to make use of super header and footer
 * 
 * 5     4/06/10 2:38p Kedarr
 * Changes made to make use of super header and footer
 * 
 * 4     4/06/10 2:19p Kedarr
 * Changes made to update the context batch info with the execution end datetime.
 * 
 * 3     3/29/10 1:45p Kedarr
 * Changes made to add more beautification
 * 
 * 2     3/25/10 4:53p Kedarr
 * Changes made to implement the last email
 * 
 * 1     3/24/10 6:05p Kedarr
 * Initial Version
 * 
 * 7     3/24/10 12:47p Kedarr
 * Changed the API to add batch object as a parameter
 * 
 * 6     2/25/10 9:47a Grahesh
 * Changes made for fail over.
 * 
 * 5     2/15/10 11:38a Mandar.vaidya
 * Modified the toMap method
 * 
 * 4     1/07/10 5:37p Grahesh
 * Updated Java Doc comments
 * 
 * 3     12/18/09 12:32p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/