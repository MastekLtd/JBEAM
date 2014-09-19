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
package com.stgmastek.core.logic;

import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.interfaces.IExecutableBatchJob;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add multi-line description of the class indicating the objectives/purpose
 * of the class and the usage with each sentence ending with a period.
 *
 * @author kedar.raybagkar
 * @since
 */
public class TMyExecutionHandler extends BaseExecutionHandler {

	/**
	 * 
	 */
	public TMyExecutionHandler() {
	}
	
	/* (non-Javadoc)
	 * @see com.stgmastek.core.logic.BaseExecutionHandler#execute(com.stgmastek.core.util.BatchObject, com.stgmastek.core.util.ObjectMapDetails, com.stgmastek.core.util.BatchContext)
	 */
	@Override
	public BatchObject execute(BatchObject batchObject,
			ObjectMapDetails objectMapDetails, BatchContext batchContext)
			throws BatchException {
		// TODO Auto-generated method stub
		IExecutableBatchJob javaBatchObject = ExecutableBatchJobPool.getInstance().getJob(batchContext, objectMapDetails.getObjectName()); 
		javaBatchObject.execute(batchContext, batchObject, objectMapDetails);
		return batchObject;
	}
}
