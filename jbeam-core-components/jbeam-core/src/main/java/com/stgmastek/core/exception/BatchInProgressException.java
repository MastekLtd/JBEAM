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
package com.stgmastek.core.exception;

/**
 * BatchInProgress Exception.
 *
 * This exception will be thrown only in case the current batch finds another instance of batch is being executed.
 * This class has a special meaning and should not be used elsewhere but only in Processor.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class BatchInProgressException extends BatchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1245978981318640L;
	
	public BatchInProgressException() {
		super();
	}
	
	public BatchInProgressException(String message) {
		super(message);
	}
	
	public BatchInProgressException(Throwable cause) {
		super(cause);
	}
	
	public BatchInProgressException(String message, Throwable cause) {
		super(message, cause);
	}
}
