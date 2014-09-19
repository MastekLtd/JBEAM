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
public class TBatchJob implements IExecutableBatchJob {

	/**
	 * 
	 */
	public TBatchJob() {
	}
	
	/* (non-Javadoc)
	 * @see com.stgmastek.core.interfaces.IExecutableBatchJob#destroy(com.stgmastek.core.util.BatchContext)
	 */
	public void destroy(BatchContext batchContext) {
		System.out.println( Thread.currentThread() +  "\t TBatchJob Destroyed");
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.interfaces.IExecutableBatchJob#init(com.stgmastek.core.util.BatchContext)
	 */
	public void init(BatchContext batchContext) {
		System.out.println(Thread.currentThread() +  "\t TBatchJob Initialized");
	}

	public void execute(BatchContext batchContext, BatchObject batchObject,
			ObjectMapDetails objectMapDetails) throws BatchException {
		System.out.println(Thread.currentThread() +  "\t TBatchJob Executed");
		
	}
}
