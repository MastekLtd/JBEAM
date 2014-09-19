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
package com.stgmastek.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BatchLockMonitor {
	
	private static final Lock latch = new ReentrantLock();

	private Map<Long, Map<String, Object>> lockedInstrucionsMap = null;
	
	//private AtomicBoolean bConfigurablesReset = new AtomicBoolean(false);
	
	private BatchLockMonitor(){
		lockedInstrucionsMap = new ConcurrentHashMap<Long, Map<String,Object>>();
	}
	
	/**
	 * @return
	 */
	public static BatchLockMonitor getInstance(){
		return SingletonHelper.INSTANCE;
	}

	/**
	 * Singleton Helper class.
	 */
	private static class SingletonHelper{
		private static final BatchLockMonitor INSTANCE = new BatchLockMonitor();
	}

	public static void addLockingEntry(long instructionLogId, Map<String,Object> instructionParamMap){
		latch.lock();
		try {
			getInstance().lockedInstrucionsMap.put(instructionLogId,
					instructionParamMap);
			System.out.println("Adding entry for  : " + instructionLogId);
			printMap(instructionParamMap);
		} finally {
			latch.unlock();
		}
	}
	
	private static <T,S> void printMap(Map<T,S> paramMap){
		for(Entry<T,S> entry: paramMap.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	public static void removeLockingEntry(long instructionLogId){
		latch.lock();
		try {
			getInstance().lockedInstrucionsMap.remove(instructionLogId);
			System.out.println("Removing entry for  : " + instructionLogId);
			System.out.println("Remaining entries  : " + getInstance().lockedInstrucionsMap);
			printMap(getInstance().lockedInstrucionsMap);
		} finally {
			latch.unlock();
		}
	}
	
	public static Map<Long, Map<String, Object>> getLockingEntries(){
		latch.lock();
		try {
			Map<Long, Map<String, Object>> map = new HashMap<Long, Map<String,Object>>();
			map.putAll(getInstance().lockedInstrucionsMap);
			return map;
		} finally {
			latch.unlock();
		}
	}
	
	public static boolean hasMoreLockingEntries() {
		return !(getInstance().lockedInstrucionsMap.isEmpty());
	}
	
/*	public void setConfigurablesAsReset(boolean configurablesReset){
		bConfigurablesReset.set(configurablesReset);
	}
	
	public boolean isConfigurablesReset(){
		return bConfigurablesReset.get();
	}
*/

}
