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
 *
 * $Revision: 31105 $
 *
 * $Header: http://172.16.209.156:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/ProcessRequestServicer.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/ProcessRequestServicer.java $
 * 
 * 1     2/04/09 4:46p Kedarr
 * Initial version.
 * 
 */
package stg.pr.engine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;

import stg.pr.engine.datasource.IDataSourceFactory;


/**
 * The base class that any implementer should extend.
 * 
 * This class implements {@link IProcessRequest}.
 * 
 * @author Kedar Raybagkar
 * @since  V1.0R26.00
 */
public abstract class ProcessRequestServicer implements IProcessRequest {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 31105             $";
    
	private boolean failedOver;

	private REQUEST_SOURCE source;

	private long requestId;

	private Map<String, Object> params;

	private String userId;

	private PrintWriter responseWriter;

	private IDataSourceFactory dataSourceFactory;

	private Connection connection;
	
	private final Lock latch = new ReentrantLock();

	private File logFile;

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#getContext()
     */
    public PREContext getContext() {
        return CProcessRequestEngine.getInstance().getContext();
    }

    /**
     * Sets true if failed over from one instance of PRE to another.
     * @param failedOver
     */
    public final void setFailedOver(boolean failedOver) {
    	this.failedOver = failedOver;
    }
    
    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#isFailedOver()
     */
    public final boolean isFailedOver() {
    	return failedOver;
    }
    
    /**
     * Sets the source request type.
     * 
     * @param source
     */
    public final void setSource(REQUEST_SOURCE source) {
    	this.source = source;
    }
    
    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#getSource()
     */
    public REQUEST_SOURCE getSource() {
    	return source;
    }
    
    /**
     * Sets the request id.
     * 
     * @param lRequestId
     */
    public final void setRequestId(long lRequestId) {
    	this.requestId = lRequestId;
    }

    /**
     * Sets the parameters associated with the request.
     * 
     * @param hmParams
     */
    public final void setParams(Map<String, Object> hmParams) {
    	this.params = hmParams;
    }
    
    /**
     * Sets the user id associated with the request
     * @param strUserId
     */
    public final void setUserId(String strUserId) {
    	this.userId = strUserId;
    }
    
    
	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#getRequestId()
	 */
	public long getRequestId() {
		return requestId;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#getParams()
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#getUserId()
	 */
	public String getUserId() {
		return userId;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#getResponseWriter()
	 */
	public PrintWriter getResponseWriter() throws IOException {
		if (responseWriter == null) {
			latch.lock();
			try {
				if (responseWriter == null) {
					responseWriter = new PrintWriter(FileUtils.openOutputStream(logFile),true);
				}
			} finally {
				latch.unlock();
			}
		}
		return responseWriter;
	}
	
	/**
	 * Sets the data source factory class.
	 * 
	 * @param factory
	 */
	public final void setDataSourceFactory(IDataSourceFactory factory) {
		this.dataSourceFactory = factory;
	}
	
	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#getDataSourceFactory()
	 */
	public IDataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}
	
	/**
	 * Sets the JDBC connection object.
	 * @param con
	 */
	public final void setConnection(Connection con) {
		this.connection = con;
	}
	
	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#getConnection()
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Sets the log file that will be created upon {@link #getResponseWriter()}.
	 * @param file
	 */
	public final void setLogFile(File file) {
		this.logFile = file;
	}
	
	/**
	 * Closes the response writer if it was created.
	 */
	public final void close() {
		if (responseWriter != null) {
			responseWriter.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#endProcess()
	 */
	public void endProcess() throws CProcessRequestEngineException {
		close();
	}
}
