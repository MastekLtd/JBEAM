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
 * $Revision: 2980 $
 *
 * $Header: http://172.16.209.156:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/HeartBeatState.java 1403 2010-05-12 23:57:17Z kedar $
 *
 */
package stg.pr.engine;


/**
 * HeartBeatState ENUM defines various states that will be used to communicate with the cluster.
 * 
 * States are self explanatory.
 * 
 * @author Kedar Raybagkar
 * @version $Revision: 2980 $
 * @since V1.0R28.x
 *
 */
public enum HeartBeatState {
	
	/**
	 * ACTIVE state.
	 */
	RUNNING(0), 
	/**
	 * To be STOPed state.
	 */
	STOP(100),
	
	/**
	 * REBOOT state.
	 */
	REBOOT(200),
	
	/**
	 * PASSIVE state.
	 */
	PASSIVE(300),
	
	/**
	 * BOUNCE state.
	 */
	BOUNCE(400);
	
	private final int id;
	
	private HeartBeatState(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the id.
	 * @return int
	 */
	public int getID() {
		return id;
	}
}
