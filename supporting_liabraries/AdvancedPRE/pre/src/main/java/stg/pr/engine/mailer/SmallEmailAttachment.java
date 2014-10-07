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
 * $Revision:  $
 *
 * $Header:   $
 *
 * $Log:    $
 *
 */
package stg.pr.engine.mailer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * An email attachment that is of lower size than the defined max file size {@link EMailAttachment#SMALL_SIZE}.
 *
 * An email with Small email attachments can be transported over the cluster Active / Passive implementation where the passive 
 * also becomes as Active to send these types of email asynchronously. The file is completely read and stored as a byte array.
 *
 * @author Kedar Raybagkar
 * @since V1.0R28.02
 */
public class SmallEmailAttachment extends EMailAttachment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1395408239457408411L;
	private String contentType;
	
	private byte[] bytes;
	
	/**
	 * @param name
	 * @param filename
	 * @throws IOException
	 */
	public SmallEmailAttachment(String name, String filename)
			throws IOException {
		this(name, new File(filename));
	}

	/**
	 * Constructs an email attachment with the given name and the file.
	 * @param name of the file that will get associated with the email.
	 * @param file to be attached.
	 * @throws IOException
	 */
	public SmallEmailAttachment(String name, File file) throws IOException {
		super(name, file); //this will throw exception in case the file does not exist 
		if (file.length() > EMailAttachment.SMALL_SIZE.longValue()) {
			throw new IllegalArgumentException("Small email attachments are for file size < " + EMailAttachment.SMALL_SIZE.longValue() + " bytes.");
		}
		FileDataSource fds = new FileDataSource(file);
		contentType = fds.getContentType();
		bytes = FileUtils.readFileToByteArray(file);
	}

	/**
	 * Constructs an attachment with the given name for the given input stream and content type.
	 * @param name of the attachment to be associated in the email
	 * @param is InputStream 
	 * @param contentType
	 * @throws IOException
	 */
	public SmallEmailAttachment(String name, InputStream is, String contentType) throws IOException {
		super(name, is, contentType); //this will throw exception in case the file does not exist
		ByteArrayDataSource datasource = new ByteArrayDataSource(is, contentType);
		if (datasource.getInputStream().available() > EMailAttachment.SMALL_SIZE.longValue()) {
			throw new IllegalArgumentException("Small email attachments are for file size < " + EMailAttachment.SMALL_SIZE.longValue());
		}
		this.contentType = contentType;
		this.bytes = IOUtils.toByteArray(is);
	}
	
	/**
	 * Constructs the SamllEmailAttachment using the given name and the {@link ByteArrayDataSource}
	 * @param name The name of the attachment.
	 * @param datasource The data source is fully read and stored in bytes array provided the length of the bytes is < {@link EMailAttachment#SMALL_SIZE}.
	 */
	public SmallEmailAttachment(String name, ByteArrayDataSource datasource) throws IOException {
		super(name, datasource);
		if (datasource.getInputStream().available() > EMailAttachment.SMALL_SIZE.longValue()) {
			throw new IllegalArgumentException("Small email attachments are for file size < 1MB");
		}
		contentType = datasource.getContentType();
		this.bytes = IOUtils.toByteArray(datasource.getInputStream());
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.mailer.EMailAttachment#getDataSource()
	 */
	public DataSource getDataSource() {
		return new ByteArrayDataSource(bytes, contentType);
	}

}
