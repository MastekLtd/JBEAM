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

import java.util.Map;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.ws.util.Configurations;

/**
 * An abstract Mailer class.
 * 
 * This class provides all the basic methods to email a message. The actual transportation of the email
 * is left unimplemented. EMail can be transported in two ways namely Synchronous email {@link #sendMail(EMail)}
 * and {@link #sendMail(EMail)}. The Mailer uses thread pools to send asynchronous email. If all the the threads in
 * the thread pool are busy then subsequent emails are queued. In case the PRE is being shutdown these queued email messages 
 * will be applied with {@link EMailPolicy}. This policy defines actions for serializing the queue. When PRE is started based on 
 * the implementation of this policy serialized email messages can be re-loaded and transported to the underlying email server.
 * 
 * An object of {@link EMail} once transported cannot be transported again. Such email messages are identified as SPAM. These SPAM
 * emails are logged as warning. 
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 3 $
 */
public abstract class CMailer {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 3                 $";

    /**
     * The Identifier for the mails with normal priority.
     * 
     * Comment for <code>NORMAL</code>
     */
    public static final String NORMAL = "normal.";

    /**
     * The identifier for the mails with Critical priority.
     * 
     * Comment for <code>CRITICAL</code>
     */
    public static final String CRITICAL = "critical.";

    /**
     * Subject of the mail is stored in this variable.
     * 
     * Comment for <code>strSubject_</code>
     */
    private String strSubject_;

    /**
     * Message Body Header is stored in this variable.
     * 
     * Comment for <code>strMessageBodyHeader_</code>
     */
    private String strMessageBodyHeader_;
    

    /**
     * Message Body Footer is stored in this variable.
     * 
     * Comment for <code>strMessageBodyFooter_</code>
     */
    private String strMessageBodyFooter_;

    /**
     * Logger instance variable.
     * 
     * Comment for <code>logger_</code>
     */
    private transient Logger logger_;

    /**
     * Stores the message header key pair values.
     */
    private Map<String,String> mapMessageHeader_;

    /**
     * Static instance of the CMailer.
     */
    private static CMailer instance_;

//    /**
//     * 
//     */
//    private ThreadPoolExecutor executor_;
//    
//    /**
//     * 
//     */
//    private LinkedBlockingQueue<Runnable> emailQueue;

    /**
     * Constructs a default Mailer.
     * @throws AddressException 
     */
    protected CMailer() {
        super();
        logger_ = Logger.getLogger("Mailer");
    }

    /**
     * Returns the CMailer. 
     * Depending on the mailer type a appropriate mailer is returned. Defaults to SMTP mailer.
     * 
     * @return CMailer
     */
    public synchronized static CMailer getInstance() {
        if (instance_ == null) {
              instance_ = new CSMTPMailer();
        }
        return instance_;
    }


    /**
     * Sends the mail in a synchronous manner.
     * 
     * This method calls the abstract {@link #transportMail(EMail)}method for
     * transporting the mail to mail server. If you use the same EMail message for transporting the second time
     * then PRE will identify this message as a SPAM and will not allow it to be transported. The caller will wait
     * till the email is transported to the email server for delivery.
     * 
     * @param email 
     * @throws MessagingException
     */
    public final void sendMail(final EMail email) throws MessagingException {
        if ("ON".equalsIgnoreCase(Configurations.getConfigurations().getConfigurations("MONITOR", "MAIL", "mailnotification"))) {
            transportMail(email);
        } else {
            if (logger_.isInfoEnabled()) {
                logger_
                        .info("E-Mail notification is off. Email not Transported.");
            }
        }
    }

    /**
     * This is an abstract method that is responsible to send or transport the
     * mail to the mail server. This method should create a new mail session,
     * set the recipients, attach any files supplied and should transport the
     * mail to the mail server.
     * 
     * @param eMail 
     * @throws MessagingException
     */
    protected abstract void transportMail(EMail eMail)
            throws MessagingException;


    /**
     * Returns the default message body footer of the email.
     * 
     * @return String Message Body Footer.
     */
    public String getDefaultMessageBodyFooter() {
        return strMessageBodyFooter_;
    }

    /**
     * Returns the default email's message body header.
     * 
     * @return Returns the Message Body Header.
     */
    public String getDefaultMessageBodyHeader() {
        return strMessageBodyHeader_;
    }

    /**
     * Returns the subject of the email message. If the subject is not set or
     * set to null then the default value for the subject is picked from the
     * mail properties. Property <i>mail.defaultsubject </i>.
     * 
     * @return Returns the Subject.
     */
    public String getDefaultSubject() {
        return strSubject_;
    }

    /**
     * Returns the default e-mail message header.
     * 
     * @return map
     */
    public Map<String, String> getDefaultMessageHeader() {
        return mapMessageHeader_;
    }

    /**
     * Sender address stored in this variable. Comment for
     * <code>addrSender_</code>.
     */
    private Address addrSender_ = null;

    /**
     * Stores the license info in the form of a TEXT.
     */
    private String defaultTEXTLicenseInfo_;
    
    /**
     * Stpres the license info in the form of an HTML.
     */
    private String defaultHTMLLicenseInfo_;

	/**
	 * HTML message header start tags.
	 */
	private String htmlMessageHeaderStartTags;

	/**
	 * HTML message header end tags.
	 */
	private String htmlMessageHeaderEndTags;

	/**
	 * HTML message footer start tags.
	 */
	private String htmlMessageFooterStartTags;

	/**
	 * HTML message footer end tags.
	 */
	private String htmlMessageFooterEndTags;

    /**
     * Returns the FROM address.
     * 
     * @return Sender's address.
     * @throws AddressException
     */
    public final Address getDefaultSender() throws AddressException {
        return addrSender_;
    }

    /**
     * Asynchronous email process.
     *
     * @author STG
     * @since  
     */
    protected class AsynchronousEMailProcess implements Runnable {
        
        private EMail email_;

        /**
         * @param email
         */
        public AsynchronousEMailProcess(EMail email) {
            email_ = email;
        }

        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                if (logger_.isDebugEnabled()) {
                    logger_.debug("Sending Asynchronous Email ...");
                }
                sendMail(email_);
            } catch (MessagingException e) {
                logger_.error("Error caught while sending Async email",
                        e);
            }
        }
        
        /**
         * Returns the underlying email message.
         * @return EMail
         */
        public EMail getUnderlyingEmail() {
            return email_;
        }
    }
    
    /**
     * Returns the license information based on the type of the email {@link EMailType#HTML} or {@link EMailType#TEXT}
     * @param type
     * @return String
     */
    protected final String getLicenseInfo(EMailType type) {
    	if (type.equals(EMailType.HTML)) {
    		return defaultHTMLLicenseInfo_;
    	}
		return defaultTEXTLicenseInfo_;
    }
    
    /**
     * Returns the HTML message header start tags. 
     * @return String
     */
    public String getHtmlMessageHeaderStartTags() {
    	return htmlMessageHeaderStartTags;
    }
    
    /**
     * Returns the HTML message header end tags. 
     * @return String
     */
    public String getHtmlMessageHeaderEndTags() {
    	return htmlMessageHeaderEndTags;
    }

    /**
     * Returns the HTML message footer start tags. 
     * @return String
     */
    public String getHtmlMessageFooterStartTags() {
    	return htmlMessageFooterStartTags;
    }
    
    /**
     * Returns the HTML message footer end tags. 
     * @return String
     */
    public String getHtmlMessageFooterEndTags() {
    	return htmlMessageFooterEndTags;
    }
}