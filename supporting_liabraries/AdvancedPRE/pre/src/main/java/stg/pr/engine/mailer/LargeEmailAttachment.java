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

import javax.activation.DataSource;
import javax.activation.FileDataSource;

/**
 * EmailAttachment of no size restrictions. 
 *
 * The email that holds {@link LargeEmailAttachment} cannot be transported using the asynchronous method.
 * The data source defined in this class is defined as transient. 
 *
 * @author Kedar Raybagkar
 * @since V1.0R28.02
 */
public class LargeEmailAttachment extends EMailAttachment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3401774696080206167L;
	
	private transient FileDataSource dataSource = null;

	/**
	 * Constructs the object with the given name and the filename with full path.
	 * @param name that is to be reflected in the email for the given attachment. 
	 * @param filename File that needs to be attached.
	 * @throws IOException
	 */
	public LargeEmailAttachment(String name, String filename)
			throws IOException {
		this(name, new File(filename));
	}

	/**
	 * Constructs the object with the given name and the File.
	 * @param name that is to be reflected in the email for the given attachment.
	 * @param file File that needs to be attached.
	 * 
	 * @throws IOException
	 */
	public LargeEmailAttachment(String name, File file) throws IOException {
		super(name, file);
		this.dataSource = new FileDataSource(file);
	}

	/**
	 * Constructs the object with the given name and the {@link FileDataSource}.
	 * @param name that is to be reflected in the email for the given attachment.
	 * @param datasource that needs to be attached.
	 */
	public LargeEmailAttachment(String name, FileDataSource datasource) {
		super(name, datasource);
		this.dataSource = datasource;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.mailer.EMailAttachment#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
}
