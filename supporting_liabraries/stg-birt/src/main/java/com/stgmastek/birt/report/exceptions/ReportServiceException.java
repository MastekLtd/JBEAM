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
package com.stgmastek.birt.report.exceptions;

/**
 * A runtime Report Service Exception.
 *
 * The report service class throws this exception.
 * 
 * @author Kedar Raybagkar
 * @version $Revision:  $
 * @since 1.0
 */
public class ReportServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6675362632968494893L;

	/**
	 *  Constructs a new runtime exception with the specified detail message.
	 * @param message
	 */
	public ReportServiceException(String message) {
		super(message);
	}
	
	/**
	 *  Constructs a new runtime exception with the specified detail message and the underlying cause.
	 * @param message
	 * @param cause
	 */
	public ReportServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 *  Constructs a new runtime exception with the specified detail message.
	 * @param cause
	 */
	public ReportServiceException(Throwable cause) {
		super(cause);
	}
}
