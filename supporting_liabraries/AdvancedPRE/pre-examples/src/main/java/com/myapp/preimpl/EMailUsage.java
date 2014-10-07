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
 */
package com.myapp.preimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.Part;
import javax.mail.internet.AddressException;

import org.apache.log4j.PropertyConfigurator;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;
import stg.pr.engine.mailer.CMailer;
import stg.pr.engine.mailer.EMail;
import stg.pr.engine.mailer.EMailAttachment;
import stg.pr.engine.mailer.SmallEmailAttachment;
import stg.pr.engine.mailer.EMail.TYPE;
import stg.utils.CSettings;

/**
 * Example Class detailing the usage of mailer functionality in PRE.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class EMailUsage extends ProcessRequestServicer {

	public boolean processRequest() throws CProcessRequestEngineException{
		EMail email = new EMail();
		try {
			email.setTORecipient("kedar.raybagkar@mastek.com");
			email.setSubject("Test");
			email.setEMailType(TYPE.HTML);
			email.setMessageBody(getContent());
			EMailAttachment attachment = new SmallEmailAttachment("jbeam.gif", new File("C:/Users/kedar460043/Pictures/IMAG0017.jpg"));
			attachment.setDisposition(Part.INLINE);
			email.getAttachments().add(attachment);
//			FileInputStream fis = new FileInputStream(new File("C:/Users/kedar460043/Pictures/IMAG0017.jpg"));
//			ByteArrayDataSource  ids = new ByteArrayDataSource (fis, "image/gif");
			email.addAttachment(new File("C:/Users/kedar460043/Pictures/IMAG0017.jpg"));
			email.setMessageBodyFooter(""); //This forces the default footer to be not present in the email.
			email.setMessageBodyHeader(""); //This forces the default header to be not present in the email.
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		try {
			CMailer mailer = CMailer.getInstance(CSettings.get("pr.mailtype"));
			mailer.sendMail(email);
			mailer.sendAsyncMail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @return String
	 */
	public String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
		sb.append("   <BODY>");
		sb.append("       <img alt=\"jbeam.gif\" src=\"jbeam.gif\"/>This is a email with an image");
		sb.append("   </BODY>");
		sb.append("</HTML>");
		return sb.toString();
	}
	
	/**
	 * For testing purpose.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty("pre.home", "D:/kedar/Mastek/workspace/AdvancedPRE");
		CSettings.getInstance().load("D:/kedar/Mastek/workspace/AdvancedPRE/src/properties/prinit.properties");
		PropertyConfigurator.configure("D:/kedar/Mastek/workspace/AdvancedPRE/src/properties/log4j.properties");
		EMailUsage usage = new EMailUsage();
		usage.processRequest();
	}

}
