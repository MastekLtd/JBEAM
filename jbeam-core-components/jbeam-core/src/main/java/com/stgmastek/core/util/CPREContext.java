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

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import stg.pr.engine.PREContext;
import stg.pr.engine.PREInfo;
import stg.pr.engine.Singleton;

/**
 * An adapter class for simulating Process Request Engine's PREContext class for DEVELOPMENT use
 * 
 * @author grahesh.shanbhag
 *
 */
public class CPREContext implements PREContext{

	/** Serial Version UID */
	private static final long serialVersionUID = -1789790386620698810L;
	
	private ConcurrentHashMap<String, Singleton<?>> singletonMap = new ConcurrentHashMap<String, Singleton<?>>();
	
	/** Hashtable for storing information or attributes as key-value pair */
	private Hashtable<String, Serializable> table = null;

	private PREInfo info = new PREInfoImpl();
	
	/**
	 * Default Constructor 
	 */
	CPREContext(){
		table = new Hashtable<String, Serializable>();
	}
	
	/**
	 * Fetches the attribute values for the supplied key 
	 * 
	 * @param arg0
	 * 		  the key 
	 */
	
	public Serializable getAttribute(String arg0) {		
		return table.get(arg0);
	}
	
	/**
	 * Empty implementation 
	 */
	
	public Set<String> getAttributeNames() {		
		return table.keySet();
	}

	/**
	 * Empty implementation 
	 */
	
	public int getPREBuildReleaseNo() {
		return 0;
	}

	/**
	 * Empty implementation 
	 */
	
	public int getPREMajorReleaseNo() {
		return 0;
	}

	/**
	 * Empty implementation 
	 */
	
	public int getPREMinorReleaseNo() {
		return 0;
	}

	/**
	 * Empty implementation 
	 */
	
	public String getPREVersion() {
		return Configurations.getConfigurations().getConfigurations("CORE", "PRE", "VERSION");
	}

	/**
	 * Empty implementation 
	 */
	
	public boolean isPREInProductionDeploymentMode() {
		return false;
	}

	/**
	 * Empty implementation 
	 */
	
	public Serializable removeAttribute(String arg0) {
		return null;
	}

	/**
	 * Empty implementation 
	 */
	
	public boolean removeAttribute(String arg0, Serializable arg1) {
		return false;
	}

	/**
	 * Sets the attribute into the context 
	 * @param arg0
	 * 		  The key 
	 * @param arg1 
	 * 		  The value 
	 */
	
	public Serializable setAttribute(String arg0, Serializable arg1) {
		table.put(arg0, arg1);
		return null;
	}

	/**
	 * Empty implementation 
	 */
	
	public boolean containsKey(String arg0) {
		return false;
	}

	/**
	 * Empty implementation 
	 */
	
	public boolean containsValue(Serializable arg0) {
		return false;
	}

	/**
	 * Empty implementation 
	 */
	
	public Serializable setAttributeIfAbsent(String arg0, Serializable arg1) {
		return null;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#getCurrentlyRunningGJobs()
	 */
	
	public int getCurrentlyRunningGJobs() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#getCurrentlyRunningSJobs()
	 */
	
	public int getCurrentlyRunningSJobs() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#isPREActivelyScanning()
	 */
	
	public boolean isPREActivelyScanning() {
		return true;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#getPREInfo()
	 */
	public PREInfo getPREInfo() {
		return info;
	}
	
	static protected class PREInfoImpl implements PREInfo {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3520315865811801283L;

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getBuildNumber()
		 */
		public int getBuildNumber() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getBundledOn()
		 */
		public long getBundledOn() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getMacroVersion()
		 */
		public int getMacroVersion() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getMajorVersion()
		 */
		public int getMajorVersion() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getMinorVersion()
		 */
		public int getMinorVersion() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getName()
		 */
		public String getName() {
			return null;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getSymbolicName()
		 */
		public String getSymbolicName() {
			return null;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getVersion()
		 */
		public String getVersion() {
			return null;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#isSnapshot()
		 */
		public boolean isSnapshot() {
			return true;
		}

		/* (non-Javadoc)
		 * @see stg.pr.engine.PREInfo#getPatchVersion()
		 */
		public int getPatchVersion() {
			return 0;
		}
		
	}

    public Singleton<?> getSingleton(String serviceName) {
    	 return singletonMap.get(serviceName);
    }
    
    /**
     * Sets the map of singleton classes loaded on startup.
     * 
     * @param key unique identifier that can be used to access the value.
     * @param value class instance.
     */
    protected void addSingletonIfAbsent(String key, Singleton<?> value) {
        this.singletonMap.putIfAbsent(key, value);
    }
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/CPREContext.java                                                                                    $
 * 
 * 4     2/15/10 12:20p Mandar.vaidya
 * Changes made to incorporate Serializable change that was made in PRE V1.0 R 28
 * 
 * 3     1/04/10 4:39p Grahesh
 * Implemented the method for setting the attributes.
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/