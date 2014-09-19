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
package com.stgmastek.monitor.ws.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.stgmastek.monitor.mailer.CMailer;
import com.stgmastek.monitor.mailer.EMail;
import com.stgmastek.monitor.ws.server.vo.UserProfile;

/**
 * Class to send an email to newly created user 
 * 
 * @author Lakshman Pendruam
 * @since $Revision:  $
 */
public class NewUserEMail extends BaseEmail {

	private UserProfile userProfile;
	
	/**
	 * @param userProfile
	 *            The user details (@link UserProfile}.
	 */
	public NewUserEMail(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	/**
	 * Sends an email to user along with user id and password
	 * 
	 * @throws MessagingException
	 *             Any mail exception occurred
	 * @throws IOException 
	 */
	public void sendEmail() throws MessagingException, IOException {
		EMail email = new EMail();
		 try
	        {
				 try
				    {
				        email.setTORecipient(userProfile.getEmailId());
				    }
				    catch(AddressException addressexception)
				    {
				        System.out.println("Recepient TO not set "+addressexception);
				}
				email.setEMailId(userProfile.getEmailId());
				email.setSubject(Configurations.getConfigurations().getConfigurations("MONITOR", "MAIL", "defaultsubjectuser"));
				email.getAttachments().add(super.getJbeamLogo());
				email.setMessageBody(super.getEmailContent());
				CMailer.getInstance().sendMail(email);
				return;
	        }
	        catch(MessagingException e)
	        {
	            throw new MessagingException();
	        }
	}

	/**
	 * Generates email content in HTML
	 * 
	 *  @return String which contains email body
	 */
	public String getHTMLEmailContent() {
		StringBuilder sb = new StringBuilder();
				
		String monitorServices = Configurations.getConfigurations().getConfigurations("MONITOR_WS", "MONITOR_UI_WS", "SERVICES");
		System.out.println(monitorServices);
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<String, Serializable>();
        map.put("User Id ", userProfile.getUserId());
        map.put("Password ", userProfile.getPassword());
		sb.append(" <HR width='100%' size='1' />");
		
		sb.append("    <TABLE width='100%' border=0 cols=\"2\">		");  	 
		sb.append("      <TR > ");
		sb.append("             <TD width='100%' colspan=\"2\" class='plainText' > Dear " +userProfile.getUserName() + " , <br><br></TD> ");
		sb.append("      </TR> ");
		sb.append("      <TR> ");
		sb.append("             <TD width='05%' class='plainText'>&nbsp;</TD> ");
		sb.append("             <TD width='95%' class='plainText'>Your account at STG-JBEAM for the monitor services hosted on [" + monitorServices + "] has been created.</TD> ");
		sb.append("      </TR> ");
		sb.append("      <TR> ");
		sb.append("             <TD width='05%' class='plainText'>&nbsp;</TD> ");
		sb.append("             <TD width='95%' class='plainText' >Please use the following user id and password to log-on to the application. " +
				"You will be forced to change the password upon your login. You are requested to set your password recovery options through the user profile.<br><br></TD> ");
		sb.append("      </TR> ");
		sb.append("      <TR>");
		sb.append("         <TD width='05%' class='plainText'>&nbsp;</TD> ");
		sb.append("         <TD width='95%' class='plainText'>");
		sb.append("           <TABLE width='40%' cols='2' border=0>		");  	 
		sb.append("            <TR> ");
		sb.append("               <TD colspan='2' class='blockHeader'>User Details</TD> ");
		sb.append("            </TR> ");
		sb.append("            <TR> ");
		sb.append("               <TD width='40%' class='trHeader'>Key</TD> ");
		sb.append("               <TD width='60%' class='trHeader'>Value</TD> ");
		sb.append("            </TR> ");
		boolean alternateColor = true;
		for(Iterator<Entry<String, Serializable>> iter = map.entrySet().iterator(); iter.hasNext(); ){
			Entry<String, Serializable> entry = iter.next();
			sb.append("        <TR> ");
			sb.append("           <TD width='40%' class='" + (alternateColor?"trRow1":"trRow2") + "'>" + entry.getKey() + "</TD> ");
			sb.append("           <TD width='60%' class='" + (alternateColor?"trRow1":"trRow2") + "'>" + (entry.getValue()==null?"":entry.getValue()) + "</TD> ");
			sb.append("        </TR> ");
			if (alternateColor) {
				alternateColor = false;
			} else {
				alternateColor = true;
			}
		}		
		sb.append("         </TABLE> ");
		sb.append("      </TD>");
		sb.append("      </TR>");
		sb.append("    </TABLE> ");
		sb.append("    <BR> ");
		sb.append("    <BR> ");
		sb.append("    <BR> ");
		sb.append(" <HR width='100%' size='1' />");
		return sb.toString();
	}

}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/mailer/UserAccountEMail.java                                                               $
 * 
*
*/
