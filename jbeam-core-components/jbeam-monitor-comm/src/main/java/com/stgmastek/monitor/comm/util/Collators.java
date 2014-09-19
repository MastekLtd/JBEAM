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
package com.stgmastek.monitor.comm.util;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.comm.util.graphs.PerScanExecutionCountCollator;

/**
 * Singleton class to provide easy mechanism of starting / stopping collators for a supplied key
 *   
 * @author grahesh.shanbhag
 * 
 */

@SuppressWarnings("unchecked")
public class Collators {
	
	private static final Logger logger = Logger.getLogger(Collators.class);
	
	/** Static instance of the Collator */
	private static Collators instance = new Collators();
	
	/** The Collator Book to hold the references of all collators that were spawned */
	private static CollatorBook collatorBook = instance.new CollatorBook();
	
	/** Convenient holder for all registered collators in the systen */
	private static Map<String, Class<? extends Collator>> registeredCollators = 
		new HashMap<String, Class<? extends Collator>>();	
	static{
		registeredCollators.put("GraphPlotter", GraphPlotter.class);		
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("Loading FailedObjectsPieChartCollator");
			} 
			registeredCollators.put("FailedObjectsPieChartCollator",
					(Class<? extends Collator>) Class.forName("com.stgmastek.monitor.comm.util.graphs.FailedObjectsPieChartCollator"));
			
		} catch (ClassNotFoundException e) {
			logger.error("Error ["+ e.getMessage() +"] while loading FailedObjectsPieChartCollator", e);
		}		
		registeredCollators.put("PerScanExecutionCountCollator", PerScanExecutionCountCollator.class);		
	}
	
	/**
	 * Private Constructor to avoid outside instantiation 
	 */
	private Collators(){		
	}
	
	/**
	 * Static public method to get the instance of the class 
	 *  
	 * @return the singleton instance of Collators class
	 */
	public static Collators getCollators(){
		return instance;
	}
	
	/**
	 * Public method to start all the collators registered within the system 
	 * 
	 * @param collatorKey
	 * 		  The collator key 
	 * @return the no of Collators started 
	 */
	public Integer startRegisteredCollators(CollatorKey collatorKey){
		int iCollatorCount = 0;
		for(Iterator<String> iter = registeredCollators.keySet().iterator(); iter.hasNext(); ){
			String key = iter.next();
			Class<? extends Collator> clazz = registeredCollators.get(key);
			try{
				add(collatorKey, key, clazz);
				iCollatorCount++;
			}catch(Throwable t){
			}			
		}
		return iCollatorCount;
	}	
	
	/**
	 * Helper method to start a collator 
	 * 
	 * @param key
	 * 		  The collator key 
	 * @param collatorCode
	 * 		  The collator code as registered in the system 
	 * @param collatorClass
	 * 		  The collator class as registered in the system 
	 * @return true if the collator was started successfully
	 * @throws Throwable
	 * 		   Any exception thrown during the instantiation and starting 
	 * 		   of the collator
	 */
	private Boolean add(CollatorKey key, String collatorCode, 
				Class<? extends Collator> collatorClass) throws Throwable {
		
		Constructor<? extends Collator> constructor = 
			collatorClass.getDeclaredConstructor(CollatorKey.class);
		
		Collator runnableCollator = 
			constructor.newInstance(key);

		runnableCollator.start();
		
		collatorBook.get(key).put(collatorCode, runnableCollator);
		
		
		return true;
	}
	
	/**
	 * Public method to stop all registered collators for a collator key 
	 * 
	 * @param key
	 * 		  The collator key 
	 * @return true if the collators were stopped successfully
	 */
	public Boolean stopRegisteredCollators(CollatorKey key){				
		Map<String, BasePoller> collators = collatorBook.get(key);
		for(Iterator<String> iter = collators.keySet().iterator(); iter.hasNext();){
			String keyStr = iter.next();
			BasePoller poller = collators.get(keyStr);
			poller.stopPoller();
			iter.remove();
		}
		
		return true;
	}
	
	/**
	 * Private class, an extension to the HashMap class for private usage. 
	 * 
	 * @author grahesh.shanbhag
	 *
	 */
	class CollatorBook extends HashMap<CollatorKey, Map<String, BasePoller>>{
		private static final long serialVersionUID = 1L;
		
		
		public Map<String, BasePoller> get(Object key) {
			if(super.get(key) == null)
				super.put((CollatorKey)key, new HashMap<String, BasePoller>());
			return super.get(key);
		}
		
	}
	
	/**
	 * Stops all collators. 
	 */
	public void stopAllRegisteredCollators() {
		for (Entry<CollatorKey, Map<String, BasePoller>> entries  : collatorBook.entrySet()) {
			stopRegisteredCollators(entries.getKey());
		}
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/Collators.java                                                                       $
 * 
 * 7     3/15/10 12:27p Mandar.vaidya
 * Used Map instead of List for the static field registeredCollators.
 * Used iterator in startRegisteredCollators method
 * For the method add(), removed synchronized keyword from declaration and added collectorCode in parameters.
 * 
 * 6     3/12/10 4:51p Mandar.vaidya
 * used java util concurrent concurrenthashmap
 * 
 * 5     3/12/10 4:28p Kedarr
 * Added sychronization.
 * 
 * 4     3/12/10 10:50a Kedarr
 * added new collator
 * 
 * 3     3/11/10 4:24p Kedarr
 * Changed the graph data log table and its related changes.
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/