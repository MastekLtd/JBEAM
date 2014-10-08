/**
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
 * $Revision:: 3523                                                      $
 * 
 * $Header:: http://192.100.194.241:8080/svn/ProductTools/JavaTools/Adva#$
 * 
 * $Log:: /Utilities/PRE/src/stg/pr/engine/mailer/CSMTPMailer.java       $
 * 
 * 18    11/18/09 11:45a Kedarr
 * Changes made to add HTML email type.
 * 
 * 17    9/01/09 8:30a Kedarr
 * Externalized inner class.
 * 
 * 16    8/23/09 1:22p Kedarr
 * Changes made to add methods to set the sender email id.
 * 
 * 15    3/11/09 5:10p Kedarr
 * Changes made for the implementation of the license file.
 * 
 * 14    2/04/09 1:00p Kedarr
 * Added static keyword to a final variable.
 * 
 * 13    10/09/08 11:13p Kedarr
 * Updated Javadoc
 * 
 * 12    9/30/08 10:11p Kedarr
 * Changes made to implement EMail and CMailer changes.
 * 
 * 11    9/14/08 9:38p Kedarr
 * Depreacted the class.
 * 
 * 10    9/14/08 9:32p Kedarr
 * Depreacted the class.
 * 
 * 9     4/14/08 1:58p Kedarr
 * Added a logging message.
 * 
 * 8     4/10/08 1:53p Kedarr
 * Added functionality to add e-mail Message Headers as well as from or
 * the sender address API has been incorporated.
 * 
 * 7     3/23/08 1:07p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 6     6/14/05 3:22p Kedarr
 * Added port, user id and password for authentication while creating
 * session.
 * 
 * 5     6/06/05 4:09p Kedarr
 * Info and Debug logging is first checked whether it is enabled and then
 * only the messages are logged.
 * 
 * 4     6/02/05 1:58p Kedarr
 * Changes made to check for isDebugEnabled and then log debug message.
 * 
 * 3     6/01/05 12:42p Kedarr
 * Commented a debug message.
 * 
 * 2     5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 1     1/19/05 3:57p Kedarr
 * Advanced PRE
 */

package stg.pr.engine.mailer;

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

import stg.pr.engine.mailer.EMail.TYPE;
import stg.utils.CSettings;

/**
 * The actual SMTP Mailer.
 * The purpose of this class is to transport the mail to the mail server configured
 * in the property file <i>mail.properties<i>.
 * 
 * @version $Revision:: 3523                   $
 * @author Kedar C. Raybagkar
 */
public final class CSMTPMailer extends CMailer {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 3523              $";

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
		String strHost = CSettings.get("mail.SMTPserver");
		String strPort = CSettings.get("mail.SMTPserverPort", "25");
		Address[] addrToRecepient = email.getTORecipient();
		Address[] addrCcRecepient = email.getCCRecipient();
		Address[] addrBccRecepient = email.getBCCRecipient();
		String strSubject = email.getSubject();
		String strMessageBodyHeader = email.getMessageBodyHeader();
		String strMessageBodyFooter = email.getMessageBodyFooter();
		String strMessageBody = email.getMessageBody();
		if (email.isDefaultValuesToBeApplied()) {
		    if (strSubject == null || strSubject.equals("")) {
		        strSubject = super.getDefaultSubject();
		    }
		    
		    if (strMessageBodyHeader == null) {
		        strMessageBodyHeader = super.getDefaultMessageBodyHeader();
		    }

		    if (strMessageBodyFooter == null) {
		        strMessageBodyFooter = super.getDefaultMessageBodyFooter();
		    }
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

		//            No idea if the defaultInstance is thread safe. So it is better to
		// have getInstance()
		//            mailSession = Session.getDefaultInstance(properties_, null);
		if (logger_.isDebugEnabled()) {
		    logger_.debug("Creating Session");
		}
		mailSession = Session.getInstance(properties_, getAuthenticator());
		mailSession.setDebug(CSettings.get("pr.maildebug", "false")
				.equalsIgnoreCase("true"));
		msg = new MimeMessage(mailSession);
		addMessageHeader(msg, email); // adds the message header if any.
		if (email.getSenderEmailId() == null) {
		    msg.setFrom(getDefaultSender());
		} else {
		    msg.setFrom(new InternetAddress(email.getSenderEmailId()));
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
			email.setEMailType(TYPE.valueOf(CSettings.get("mail.defaultemailoftype", "HTML")));
		}
		bodyPart.setDisposition(Part.INLINE);
		if (email.getEMailType() == TYPE.HTML) {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append(getHtmlMessageHeaderStartTags());
			sbuffer.append(strMessageBodyHeader);
			sbuffer.append(getHtmlMessageHeaderEndTags());
			sbuffer.append(strMessageBody);
//			sbuffer.append(getLicenseInfo(TYPE.HTML));
			sbuffer.append(getHtmlMessageFooterStartTags());
			sbuffer.append(strMessageBodyFooter);
			sbuffer.append(getHtmlMessageFooterEndTags());
			bodyPart.setContent(sbuffer.toString(), "text/html");
		} else {
//			if (getLicenseInfo(TYPE.TEXT) != null) {
//				bodyPart.setText(strMessageBodyHeader + "\n\n" + strMessageBody
//						+ "\n\n" + getLicenseInfo(TYPE.TEXT) + "\n\n" + strMessageBodyFooter);
//			} else {
				bodyPart.setText(strMessageBodyHeader + "\n\n" + strMessageBody
						+ "\n\n" + strMessageBodyFooter);
//			}
		}
		multipart.addBodyPart(bodyPart);
		for (EMailAttachment attachment : email.getAttachments()) {
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setDisposition(attachment.getDisposition());
			attachmentPart.setDataHandler(new DataHandler(attachment.getDataSource()));
			attachmentPart.setFileName(attachment.getName());
			multipart.addBodyPart(attachmentPart);
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
       final String strUserId   = CSettings.get("mail.userid", null);
        final String strPassword = CSettings.get("mail.password", null);

	    if (strUserId != null){
	        MailServerAuthenticator authenticator = new MailServerAuthenticator(strUserId, strPassword);
		    logger_.debug("Authenticator #" + authenticator);
		    return authenticator;
	    }
	    logger_.debug("No mail authenticator specified.");
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
