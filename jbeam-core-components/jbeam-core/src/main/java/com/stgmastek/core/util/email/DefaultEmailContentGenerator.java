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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import stg.pr.engine.mailer.EMail;

import com.stgmastek.core.aspects.Email.EMAIL_TYPE;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Configurations;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * Default implementing class for the content of the email generation.
 * 
 * Do not directly implement {@link IEmailContentGenerator}. Always extend this
 * class thereby the default header and footer will be common for all emails.
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public class DefaultEmailContentGenerator implements IEmailContentGenerator {

	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.email.IEmailContentGenerator#getContent(com.stgmastek.core.util.BatchContext, com.stgmastek.core.aspects.Email.EMAIL_TYPE, com.stgmastek.core.util.BatchObject, com.stgmastek.core.util.ObjectMapDetails, java.lang.Boolean)
	 */
	public EMail getContent(BatchContext context, EMAIL_TYPE TYPE, BatchObject batchObject, ObjectMapDetails objectDetails, Boolean lastEmail) {
		EMail email = new EMail();
		if(EMAIL_TYPE.WHEN_BATCH_STARTS == TYPE) {
			email.setMessageBody(getEmailContent(context));
		} else if(EMAIL_TYPE.WHEN_BATCH_ENDS == TYPE) {
			email.setMessageBody(getEmailContent(context));
		} else {
			email.setMessageBody("Handler for the email type " + TYPE.name() + " is not configured.");
		}
		return email;
	}

	/**
	 * Helper method to create the content when the batch starts  
	 * 
	 * @param context
	 * 		  The batch context 
	 * @return the content of the email as string
	 */
	private String getEmailContent(BatchContext context){
		StringBuilder sb = new StringBuilder();
		sb.append("  <HTML> ");
		sb.append("  <HEAD> ");
		sb.append("  <META NAME='GENERATOR' Content='Microsoft Visual Studio 6.0'> ");
		sb.append("  <TITLE></TITLE> ");
		sb.append(getDefaultStyleSheet());
		sb.append("  </HEAD> ");
		sb.append("  <BODY> ");		
		sb.append(getDefaultHeader());
		sb.append(getDefaultBatchInfo(context));
		sb.append(getDefaultFooter());
		sb.append("  </BODY> ");
		sb.append("  </HTML> ");

		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.email.IEmailContentGenerator#getDefaultStyleSheet()
	 */
	public String getDefaultStyleSheet() {
		StringBuilder sb = new StringBuilder();
		sb.append("    <style type='text/css'> ");
		sb.append("    .blockHeader{ ");
		sb.append("        FONT-WEIGHT: bold; ");
		sb.append("        FONT-SIZE: 10pt; ");
		sb.append("        COLOR: #333399; ");
		sb.append("        FONT-FAMILY: Arial,Helvetica,Sans-serif; ");
		sb.append("        TEXT-DECORATION: none; ");
		sb.append("    }  "); 
		sb.append("    .trHeader{ ");
		sb.append("        FONT-WEIGHT: bold; ");
		sb.append("        FONT-SIZE: 8pt; ");
		sb.append("        COLOR: #333399; ");
		sb.append("        FONT-FAMILY: Arial,Helvetica,Sans-serif; ");
		sb.append("        TEXT-DECORATION: none; ");
		sb.append("        border: inset 1pt; ");
		sb.append("        background-color: #b3b3b3; ");
		sb.append("    } ");
		sb.append("    .trRow1{ ");
		sb.append("        FONT-SIZE: 8pt; ");
		sb.append("        COLOR: #333399; ");
		sb.append("        FONT-FAMILY: Arial,Helvetica,Sans-serif; ");
		sb.append("        TEXT-DECORATION: none; ");
		sb.append("        border: inset 1pt; ");
		sb.append("        background-color: #F0F0F0; ");
		sb.append("    }	");
		sb.append("    .trRow2{ ");
		sb.append("        FONT-SIZE: 8pt; ");
		sb.append("        COLOR: #333399; ");
		sb.append("        FONT-FAMILY: Arial,Helvetica,Sans-serif; ");
		sb.append("        TEXT-DECORATION: none; ");
		sb.append("        border: inset 1pt; ");
		sb.append("        background-color: #F7F7F7; ");
		sb.append("    }	");
		sb.append("    .trHeaderFooter{ ");
		sb.append("        FONT-SIZE: 8pt; ");
		sb.append("        COLOR: #333399; ");
		sb.append("        FONT-FAMILY: Arial,Helvetica,Sans-serif; ");
		sb.append("        TEXT-DECORATION: none; ");
		sb.append("        border: outset 2pt; ");
		sb.append("        background-color: #F7F7F7; ");
		sb.append("        border-color:#00a6d6; ");
		sb.append("    }	");
//		sb.append("    .tableHeader ");
//		sb.append("    { ");
//		sb.append("        FONT-WEIGHT: bold; ");
//		sb.append("        FONT-SIZE: 8pt; ");
//		sb.append("        COLOR: #FFFFFF; ");
//		sb.append("        FONT-FAMILY: Arial,Helvetica,Sans-serif; ");
//		sb.append("        TEXT-DECORATION: none; ");
//		sb.append("        border: inset 1pt; ");
//		sb.append("        background-color: #b3b3b3; ");
//		sb.append("    }	");
		sb.append("    .dTable{ ");
		sb.append("      border: 1px solid #aa9; ");
		sb.append("      border-color:#333399; ");
		sb.append("    } ");
		sb.append("    body{ ");
//		sb.append("      background-color: #FFF9AF; ");
		sb.append("      background-color: white; ");
		sb.append("    } ");
		sb.append("    </style> ");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.email.IEmailContentGenerator#getDefaultHeader()
	 */
	public String getDefaultHeader(){
		StringBuilder sb = new StringBuilder();
		sb.append("<TABLE width='100%'>");
		sb.append("<TR><TD class=\"trHeaderFooter\"><img alt=\"jbeam.gif\" src=\"jbeam.gif\">This message is automatically generated by the Java Batch Execution And Monitoring system. Please do not reply. Contact your help desk or on-site support if you need any assistance.</TD></TR></TABLE> ");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.email.IEmailContentGenerator#getDefaultFooter()
	 */
	public String getDefaultFooter(){
		StringBuilder sb = new StringBuilder();
		sb.append("<TABLE width='100%'><TR><TD class=\"trHeaderFooter\"><b>DISCLAIMER</b>: Information contained and transmitted by this E-MAIL is proprietary to the Java Batch Execution And Monitoring system. This is an auto generated mail intended only for certain privileged users of the system.Access to this email by anyone else is unauthorized. Any copying or further distribution beyond the original addressee is not intended.</TD></TR></TABLE> ");
		return sb.toString();
		
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.util.email.IEmailContentGenerator#getDefaultBatchInfo(com.stgmastek.core.util.BatchContext)
	 */
	public String getDefaultBatchInfo(BatchContext context) {
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<String, Serializable>();
        map.put("Installation ", Configurations.getConfigurations().getConfigurations("CORE", "INSTALLATION", "NAME"));
        map.put("Batch #", context.getBatchInfo().getBatchNo());
        map.put("Batch Revision #", context.getBatchInfo().getBatchRevNo());
        map.put("Batch Name", context.getBatchInfo().getBatchName());
        map.put("Batch Type", context.getBatchInfo().getBatchType());
        map.put("Batch Run Date", context.getBatchInfo().getBatchRunDateAsString());
        map.put("Batch Start Time", context.getBatchInfo().getExecutionStartTime());
    	map.put("Batch End Time", context.getBatchInfo().getExecutionEndTime());
        map.put("Instruction Log #", context.getBatchInfo().getInstructionLogSeq());
        map.put("PRE Version", context.getBatchInfo().getPREVersion());
        map.put("JBEAM Core Version", context.getBatchInfo().getVersion());
        map.put("Process Request Id", context.getBatchInfo().getRequestId());
        map.put("Revision Run", context.getBatchInfo().isRevisionRun());
        map.put("Started By", context.getBatchInfo().getStartUser());
        map.put("Stopped By", context.getBatchInfo().getEndUser());
        map.put("Batch End Reason", context.getBatchInfo().getClosureReason());
        if (context.getBatchInfo().isFailedOver()) {
            map.put("<b>Batch Failed Over?</B>", "<b>true</b>");
        }
		
		StringBuilder sb = new StringBuilder();
		sb.append(" <HR width='100%' size='1' />");
		
		sb.append("    <TABLE width='40%' border=0>		");  	 
		sb.append("      <TR> ");
		sb.append("        <TD class='blockHeader'>Batch Details</TD> ");
		sb.append("      </TR> ");
		sb.append("      <TR> ");
		sb.append("        <TD> ");
		sb.append("         <TABLE width='100%' border=0 class='dTable'>	");	   
		sb.append("           <TR> ");
		sb.append("             <TD width='40%' class='trHeader'>Key :</TD> ");
		sb.append("             <TD width='60%' class='trHeader'>Value</TD> ");
		sb.append("           </TR> ");
		boolean alternateColor = true;
		for(Iterator<Entry<String, Serializable>> iter = map.entrySet().iterator(); iter.hasNext(); ){
			Entry<String, Serializable> entry = iter.next();
			sb.append("           <TR> ");
			sb.append("             <TD width='40%' class='" + (alternateColor?"trRow1":"trRow2") + "'>" + entry.getKey() + " :</TD> ");
			sb.append("             <TD width='60%' class='" + (alternateColor?"trRow1":"trRow2") + "'>" + (entry.getValue()==null?"":entry.getValue()) + "</TD> ");
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
		if(!context.getBatchInfo().isDateRun()){
			HashMap<String, Object> paramMap = context.getRequestParams().getProcessRequestParams();
			sb.append("    <TABLE width='50%' border=0>		");  	 
			sb.append("      <TR> ");
			sb.append("        <TD class='blockHeader'>Batch Parameters</TD> ");
			sb.append("      </TR>	"); 
			sb.append("      <TR> ");
			sb.append("        <TD> ");
			sb.append("         <TABLE width='100%' border=0 class='dTable'>		   ");
			sb.append("           <TR> ");
			sb.append("             <TD width='4%' class='trHeader'>#</TD> ");
			sb.append("             <TD width='48%' class='trHeader'>Name</TD> ");
			sb.append("             <TD width='48%' class='trHeader'>Value</TD> ");
			sb.append("           </TR> ");
			int i=1;
			alternateColor = true;
			for(Iterator<Entry<String, Object>> iter = paramMap.entrySet().iterator(); iter.hasNext(); i++){
				Entry<String, Object> entry = iter.next();
				sb.append("           <TR> ");
				sb.append("             <TD class='" + (alternateColor?"trRow1":"trRow2") + "'>" + i + "</TD> ");
				sb.append("             <TD class='" + (alternateColor?"trRow1":"trRow2") + "'>" + entry.getKey() + "</TD> ");
				sb.append("             <TD class='" + (alternateColor?"trRow1":"trRow2") + "'>" + entry.getValue() +"</TD> ");
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
		}
		sb.append(" <HR width='100%' size='1' />");
		return sb.toString();
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/email/DefaultEmailContentGenerator.java                                                             $
 * 
 * 24    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 23    4/23/10 2:21p Kedarr
 * Installation name added to batch info.
 * 
 * 22    4/19/10 3:28p Kedarr
 * Instead of key set used entry set and updated the style sheet.
 * 
 * 21    4/19/10 11:45a Kedarr
 * Instead of key set used entry set and updated the style sheet.
 * 
 * 20    4/13/10 2:32p Kedarr
 * Changes made to add the escalation level and for passing object details as changed in the interface.
 * 
 * 19    4/06/10 3:00p Kedarr
 * Re-adjusted the header and footer
 * 
 * 18    4/06/10 2:16p Kedarr
 * Changes as per the new methods added.
 * 
 * 17    4/06/10 1:32p Mandar.vaidya
 * Style changed for header and footer in email.
 * 
 * 16    4/01/10 3:26p Kedarr
 * Changed the stylesheet.
 * 
 * 15    3/31/10 4:32p Kedarr
 * Changes made to convert a jarred resource into a file.
 * 
 * 14    3/31/10 3:17p Kedarr
 * Changes made for File
 * 
 * 13    3/31/10 12:40p Kedarr
 * Changes made to add image and fixed a table row.
 * 
 * 12    3/25/10 5:09p Kedarr
 * initialized the counter to 1.
 * 
 * 11    3/25/10 4:45p Mandar.vaidya
 * Updated javadoc
 * 
 * 10    3/25/10 3:30p Kedarr
 * Added a new parameter.
 * 
 * 9     3/25/10 9:14a Kedarr
 * Changes made to implement closure email.
 * 
 * 8     3/24/10 3:05p Kedarr
 * Changes made to send EMail as a return object in the interface Email Content generator
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