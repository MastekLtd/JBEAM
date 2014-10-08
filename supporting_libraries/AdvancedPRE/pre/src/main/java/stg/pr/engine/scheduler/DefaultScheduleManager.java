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
package stg.pr.engine.scheduler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.engine.PREContext;

/**
 * This is an abstract class that implements all basic methods for the scheduler interfaces.
 *
 * By extending this class the implementor will avoid the cluttering of common methods and
 * can leave the basic implementation to this abstract class.
 *
 * @author Kedar Raybagkar
 * @since V1.0R28.2
 */
public abstract class DefaultScheduleManager implements ISchedule, IScheduleEvent,
		IScheduleValidator {

	private PREContext context;
	private PrintWriter printWriter;
	private ProcessRequestEntityBean processRequestEntityBean;
	private HashMap<String, Object> requestParameters;
	private Connection connection;
	private ProcessRequestScheduleEntityBean scheduleBean;

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ISchedule#setPREContext(stg.pr.engine.PREContext)
	 */
	public void setPREContext(PREContext context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ISchedule#setPrintWriter(java.io.PrintWriter)
	 */
	public void setPrintWriter(PrintWriter pwOut) {
		this.printWriter = pwOut;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ISchedule#setProcessRequestEntityBean(stg.pr.beans.ProcessRequestEntityBean)
	 */
	public void setProcessRequestEntityBean(ProcessRequestEntityBean pobjPREB) {
		this.processRequestEntityBean = pobjPREB;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.ISchedule#setRequestParameters(java.util.HashMap)
	 */
	public void setRequestParameters(HashMap<String, Object> phmParameters) {
		this.requestParameters = phmParameters;
	}


	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleValidator#setConnection(java.sql.Connection)
	 */
	public void setConnection(Connection pcon) {
		this.connection = pcon;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.scheduler.IScheduleValidator#setScheduleBean(stg.pr.beans.ProcessRequestScheduleEntityBean)
	 */
	public void setScheduleBean(ProcessRequestScheduleEntityBean bean) {
		this.scheduleBean = bean;
	}

	/**
	 * Returns the {@link PREContext}
	 * @return the context
	 */
	public PREContext getContext() {
		return context;
	}

	/**
	 * Returns the print writer object.
	 * @return the printWriter
	 */
	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	/**
	 * Returns the {@link ProcessRequestEntityBean}
	 * @return the processRequestEntityBean
	 */
	public ProcessRequestEntityBean getProcessRequestEntityBean() {
		return processRequestEntityBean;
	}

	/**
	 * Returns the parameters associated with the request. 
	 * @return the requestParameters
	 */
	public HashMap<String, Object> getRequestParameters() {
		return requestParameters;
	}

	/**
	 * Returns the connection object.
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Returns the {@link ProcessRequestScheduleEntityBean}
	 * @return scheduleBean associated with the request
	 */
	public ProcessRequestScheduleEntityBean getScheduleBean() {
		return scheduleBean;
	}

}
