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
 * $Revision: 33706 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/PREContextImpl.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/PREContextImpl.java $
 * 
 * 4     10/30/09 4:50p Kedarr
 * Changes made to avoid null pointer if the PRE is not packaged in jar.
 * 
 * 3     8/24/09 5:50p Kedarr
 * Added some easy convenience methods.
 * 
 * 2     3/21/09 3:49p Kedarr
 * api changes implemented.
 * 
 * 1     2/04/09 4:46p Kedarr
 * Initial version.
 * 
 */
package stg.pr.engine;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import stg.pr.engine.vo.RequestStatusVO;
import stg.utils.CSettings;
import stg.utils.ILabelNameSize;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

/**
 * Implements {@link PREContext}.
 *
 * @author Kedar Raybagkar
 * @since  V1.0R26.00
 */
public final class PREContextImpl implements PREContext {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 342590710075235530L;

	/**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 33706             $";
    
    private boolean preStatus;
    
    private PREInfo PREinfo;

    private ConcurrentHashMap<String, Singleton<?>> singletonMap = new ConcurrentHashMap<String, Singleton<?>>();
    

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#getAttribute(java.lang.String)
     */
    public Serializable getAttribute(String name) {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
        return attribMap.get(name);
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#getAttributeNames()
     */
    public Set<String> getAttributeNames() {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
        return attribMap.keySet();
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#isPREInProductionDeploymentMode()
     */
    public boolean isPREInProductionDeploymentMode() {
    	if ("true".equalsIgnoreCase(CSettings.get("pr.useprecustomclassloader"))) {
    		return CSettings.get("pr.reloadobjclasses", "N").equalsIgnoreCase("y");
    	} else {
    		return true;
    	}
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#removeAttribute(java.lang.String)
     */
    public Serializable removeAttribute(String name) {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
    	attribMap.lock(name);
    	try {
    		return attribMap.remove(name);
		} finally {
			attribMap.unlock(name);
		}
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#setAttribute(java.lang.String, java.io.Serializable)
     */
    public Serializable setAttribute(String name, Serializable obj) {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
    	Serializable tmp = attribMap.get(name);
    	if (tmp == null) {
    		return attribMap.put(name, obj);
    	} else {
    		attribMap.lock(name);
    		try {
				return attribMap.replace(name, obj);
			} finally {
				attribMap.unlock(name);
			}
    	}
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#removeAttribute(java.lang.String, java.io.Serializable)
     */
    public boolean removeAttribute(String name, Serializable object) {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
    	attribMap.lock(name);
    	try {
			return attribMap.remove(name, object);
		} finally {
			attribMap.unlock(name);
		}
    }


    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#containsValue(java.io.Serializable)
     */
    public boolean containsValue(Serializable value) {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
    	return attribMap.containsValue(value);
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#containsKey(java.lang.String)
     */
    public boolean containsKey(String key) {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
    	return attribMap.containsKey(key);
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#setAttributeIfAbsent(java.lang.String, java.io.Serializable)
     */
    public Serializable setAttributeIfAbsent(String name, Serializable object) {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.MAP);
		return attribMap.putIfAbsent(name, object);
    }

    /**
     * Returns the cluster map.
     * @return Map
     */
    Map<String, Serializable> getMap() {
    	IMap<String, Serializable> attribMap = Hazelcast.getMap(ILabelNameSize.CLUSTER_MAP);
    	return attribMap;
    }

    /**
     * Helper method that adds or updates the given {@link RequestStatusVO} value object.
     * @param vo {@link RequestStatusVO} value object.
     */
    synchronized void addOrUpdate(RequestStatusVO vo) {
    	Long reqid = Long.valueOf(vo.getReqId());
    	IMap<Long, RequestStatusVO> failOverMap = Hazelcast.getMap(ILabelNameSize.FAILOVER_MAP);    	
    	if (failOverMap.containsKey(reqid)) {
    		failOverMap.remove(reqid);
    	}
    	failOverMap.put(reqid, vo);
    }
    
    /**
     * Removes the {@link RequestStatusVO} object from the map.
     * @param vo
     */
    synchronized void remove(RequestStatusVO vo) {
    	IMap<Long, RequestStatusVO> failOverMap = Hazelcast.getMap(ILabelNameSize.FAILOVER_MAP);
    	failOverMap.remove(Long.valueOf(vo.getReqId()));
    }
    
    /**
     * Returns the Collection of all failed over requests.
     * @return Collection
     */
    synchronized Collection<RequestStatusVO> getFailedOverRequests() {
    	IMap<Long, RequestStatusVO> failOverMap = Hazelcast.getMap(ILabelNameSize.FAILOVER_MAP);
    	return failOverMap.values();
    }

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#isPREActive()
	 */
	public boolean isPREActivelyScanning() {
		return preStatus;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#setPREActive(boolean)
	 */
	void setPREActive(boolean status) {
		preStatus = status;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#getCurrentlyRunningSJobs()
	 */
	public int getCurrentlyRunningSJobs() {
		return CProcessRequestEngine.getInstance().getCurrentRunningStandAloneProcess();
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#getCurrentlyRunningGJobs()
	 */
	public int getCurrentlyRunningGJobs() {
		return CProcessRequestEngine.getInstance().getCurrentRunningGroupedProcess();
	}


	/**
	 * @param pREinfo the pREinfo to set
	 */
	void setPREinfo(PREInfo pREinfo) {
		PREinfo = pREinfo;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.PREContext#getPREInfo()
	 */
	public PREInfo getPREInfo() {
		return PREinfo;
	}

    /* (non-Javadoc)
     * @see stg.pr.engine.PREContext#getSingleton(java.lang.String)
     */
    public Singleton<?> getSingleton(String serviceName) {
        return singletonMap.get(serviceName);
    }
    
    /**
     * Sets the map of singleton classes loaded on startup.
     * 
     * @param key unique identifier that can be used to access the value.
     * @param value class instance.
     */
    void addSingletonIfAbsent(String key, Singleton<?> value) {
        this.singletonMap.putIfAbsent(key, value);
    }
}
