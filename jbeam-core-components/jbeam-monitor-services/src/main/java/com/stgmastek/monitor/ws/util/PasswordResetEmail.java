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
import java.util.LinkedHashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.stgmastek.monitor.mailer.CMailer;
import com.stgmastek.monitor.mailer.EMail;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
/**
 * Class to send an email to user whose password has been reset 
 * 
 * @author Lakshman Pendruam
 * @since $Revision:  $
 */
public class PasswordResetEmail extends BaseEmail {

	private UserProfile userProfile;
	
	public PasswordResetEmail(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * Sends an email to user along with user id and password
	 * 
	 * @throws MessagingException
	 *             Any mail exception occurred
	 * @throws IOException 
	 */
	public void sendEmail() throws IOException, MessagingException {
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
				email.setSubject(Configurations.getConfigurations().getConfigurations("MONITOR", "MAIL",
								 "defaultsubjectpassword"));
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
				
		String monitorServices = Configurations.getConfigurations().getConfigurations("MONITOR_WS",
								 "MONITOR_UI_WS", "SERVICES");
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
		sb.append("             <TD width='95%' class='plainText' > Your password has been reset. The new temporary password is " +
					"" + userProfile.getPassword() + " for the monitor services hosted on [" + monitorServices + " ]." +
				    " You may copy paste this in the password field. " +
				    "You will be forced to change the password upon login.</TD> ");
		sb.append("      </TR> ");
		sb.append("    </TABLE> ");
		sb.append("      <BR> ");
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

