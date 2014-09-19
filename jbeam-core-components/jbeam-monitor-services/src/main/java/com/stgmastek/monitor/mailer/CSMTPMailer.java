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

package com.stgmastek.monitor.mailer;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.ws.util.Configurations;

/**
 * The actual SMTP Mailer.
 * The purpose of this class is to transport the mail to the mail server configured
 * in the property file <i>mail.properties<i>.
 * 
 * @version $Revision:: 2                      $
 * @author Kedar C. Raybagkar
 */
public final class CSMTPMailer extends CMailer {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2                 $";

	/**
	 * Properties for creating the mail session are stored in this
	 * mailer.
	 * Comment for <code>properties_</code>
	 */
	private Properties properties_;
	
    /**
     * Logger. 
     */
    private Logger logger_;

	/**
	 *  Constructs the SMTP Mailer.
	 */
	protected CSMTPMailer() {
		super();
		properties_ = new Properties();
		logger_ = Logger.getLogger("SMTPMailer");
	}

	/**
	 * Transports the mail to an SMTP mail server.
	 * This method creates a mail session, creates a mail message, attaches any
	 * attachments if any and transports the mail to mail server.
	 * 
	 * @see CMailer#transportMail(EMail)
	 */
	protected void transportMail(EMail email) throws MessagingException {
	    if (!email.setEmailTransported()) {
	        throw new MessagingException("SPAM detected." + email.toString() + " email discarded.");
	    }
		Session mailSession;
		Message msg;
		String strHost = Configurations.getConfigurations().getConfigurations("MONITOR", "MAIL", "SMTPServer");
		String strPort = Configurations.getConfigurations().getConfigurations("MONITOR", "MAIL", "SMTPServerPort");
		Address[] addrToRecepient = email.getTORecipient();
		Address[] addrCcRecepient = email.getCCRecipient();
		Address[] addrBccRecepient = email.getBCCRecipient();
		String strSubject = email.getSubject();
		String strMessageBodyHeader = email.getMessageBodyHeader();
//		String strMessageBodyFooter = email.getMessageBodyFooter();
		String strMessageBody = email.getMessageBody();
		if (email.isDefaultValuesToBeApplied()) {
		    if (strSubject == null || strSubject.equals("")) {
		        strSubject = super.getDefaultSubject();
		    }
		    
		    if (strMessageBodyHeader == null) {
		        strMessageBodyHeader = super.getDefaultMessageBodyHeader();
		    }

//		    if (strMessageBodyFooter == null) {
//		        strMessageBodyFooter = super.getDefaultMessageBodyFooter();
//		    }
		}
		if (strHost == null || strHost.equals("")) {
			throw new MessagingException(
					"Mailing Host Is Un-Defined. Mailing Host Is Mandatory");
		}
		if (addrToRecepient == null) {
			throw new MessagingException(
					"To Recepient Is Un-Defined. To Recepient Is Mandatory");
		}
		if (properties_.isEmpty()) {
			properties_.put("mail.smtp.host", strHost);
			properties_.put("mail.smtp.port", strPort);
		}

		if (logger_.isDebugEnabled()) {
		    logger_.debug("Creating Session");
		}
		mailSession = Session.getInstance(properties_, getAuthenticator());
		mailSession.setDebug("true".equalsIgnoreCase(Configurations.getConfigurations().getConfigurations("MONITOR", "MAIL", "debug")));
		msg = new MimeMessage(mailSession);
		addMessageHeader(msg, email); // adds the message header if any.
		msg.setFrom(new InternetAddress(Configurations.getConfigurations().getConfigurations("MONITOR", "MAIL", "senderaddress")));
		if (logger_.isDebugEnabled()) {
			logger_.debug("From Email id: " + msg.getFrom());
		}
		msg.setRecipients(Message.RecipientType.TO, addrToRecepient);
		if (addrCcRecepient != null) {
			msg.setRecipients(Message.RecipientType.CC, addrCcRecepient);
		}
		if (addrBccRecepient != null) {
			msg.setRecipients(Message.RecipientType.BCC, addrBccRecepient);
		}
		msg.setSubject(strSubject);
		msg.saveChanges();
		Multipart multipart = new MimeMultipart();
		MimeBodyPart bodyPart = new MimeBodyPart();
		if (email.getEMailType() == null) {
			email.setEMailType(EMailType.HTML.resolve("HTML"));
		}
		bodyPart.setDisposition(Part.INLINE);
		if (email.getEMailType().equals(EMailType.HTML)) {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append(strMessageBody);
			bodyPart.setContent(sbuffer.toString(), "text/html");
		} else {
			if (getLicenseInfo(EMailType.TEXT) != null) {
				bodyPart.setText(strMessageBodyHeader + "\n\n" + strMessageBody
						+ "\n\n" + getLicenseInfo(EMailType.TEXT));
			} else {
				bodyPart.setText(strMessageBodyHeader + "\n\n" + strMessageBody);
			}
		}
		multipart.addBodyPart(bodyPart);
		for (EMailAttachment attachment : email.getAttachments()) {
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setDisposition(attachment.getDisposition());
			attachmentPart.setDataHandler(new DataHandler(attachment.getDataSource()));
			attachmentPart.setFileName(attachment.getName());
			multipart.addBodyPart(attachmentPart);
		}
		if (email.getMimeBodyPartList().size() > 0) {
			for (MimeBodyPart addBodyPart : email.getMimeBodyPartList()) {
				multipart.addBodyPart(addBodyPart);
			}
		}
		msg.setContent(multipart);
		msg.saveChanges();
		msg.setSentDate(new Date());
		if (logger_.isDebugEnabled()) {
		    logger_.debug("Transporting E-Mail to the E-Mail Server...");
		}
		if (logger_.isDebugEnabled())
		{
		    logger_.debug(email.toString());
		}
		msg.saveChanges();
		Transport.send(msg);
        if (logger_.isDebugEnabled()) {
            logger_.debug("....E-Mail Transported");
        }
	}
	
	/**
	 * Returns the Session Authenticator.
	 * 
	 * @return Authenticator
	 */
	private Authenticator getAuthenticator(){
//       final String strUserId   = null;
//       final String strPassword = null;
//
//	    if (strUserId != null){
//	        MailServerAuthenticator authenticator = new MailServerAuthenticator(strUserId, strPassword);
//		    logger_.debug("Authenticator #" + authenticator);
//		    return authenticator;
//	    }
//	    logger_.debug("No mail authenticator specified.");
        return null;
	}
	
	/**
	 * Adds the message header to the e-mail message.
	 * 
	 * @param message
	 * @param email 
	 * @throws MessagingException
	 * @throws {@link ClassCastException} if the map does not store the String objects.
	 */
	private void addMessageHeader(Message message, EMail email) throws MessagingException {
        if (logger_.isDebugEnabled()) {
            logger_.debug("Adding Message Headers, if any.");
        }
	    Map<String, String> map = email.getMessageHeader();
	    if (map == null) {
	        if (email.isDefaultValuesToBeApplied()) {
	            map = super.getDefaultMessageHeader();
	        }
	    }
	    if (map != null) {
	    	for (Iterator<Entry<String,String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = iterator.next();
				message.addHeader(entry.getKey(), entry.getValue());
            }
	        if (logger_.isDebugEnabled()) {
	            logger_.debug("Message headers added...#" + map.size());
	        }
	    }
	}
}

class MailServerAuthenticator extends Authenticator {
    
    private final String userId;
    
    private final String pwd;
    
    public MailServerAuthenticator(final String userid, final String pwd) {
        this.userId = userid;
        this.pwd = pwd;
    }
    /* (non-Javadoc)
     * @see javax.mail.Authenticator#getPasswordAuthentication()
     */
    protected PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication(userId, pwd));
    }
}
