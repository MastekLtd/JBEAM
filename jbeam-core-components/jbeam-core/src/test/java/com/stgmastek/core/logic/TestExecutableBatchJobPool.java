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


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stg.utils.CSettings;

import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.BatchStopException;
import com.stgmastek.core.exception.ExecutionException;
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
public class TestExecutableBatchJobPool extends TestCase {

	BatchContext context;
	HashMap<String, String> map; 
	BatchObject obj;
	ObjectMapDetails details;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Logger.getLogger("Test");
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
		CSettings.getInstance().load("src/test/resources/prinit.properties");
		context = new BatchContext();
		map = new HashMap<String, String>();
		map.put("JV", "com.stgmastek.core.logic.TMyExecutionHandler");
		obj = new BatchObject();
		obj.setObjectName("com.stgmastek.core.logic.TBatchJob");
		obj.setTaskname("com.stgmastek.core.logic.TBatchJob");
		details = new ObjectMapDetails();
		details.setId("com.stgmastek.core.logic.TBatchJob");
		details.setObjectName("com.stgmastek.core.logic.TBatchJob");
		details.setObjectType("JV");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		ExecutableBatchJobPool.getInstance().destroy(context);
	}
	
	@Test
	public void testPool() {
		for (int i=0; i<30; i++) {
			ExecutableBatchJobPool.getInstance();
			Thread t = new Thread(i+"") {
				/* (non-Javadoc)
				 * @see java.lang.Thread#run()
				 */
				@Override
				public void run() {
					try {
						ExecutionHandler.execute(context, obj, details, map, false);
					} catch (BatchException e) {
						e.printStackTrace();
					} catch (BatchStopException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
