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
 * $Revision: 33730 $
 * 
 * $Header: http://172.16.209.156:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/ClusterListener.java 1404 2010-05-12 23:58:33Z
 * kedar $
 * 
 * $Log: $
 */
package stg.pr.engine;

import java.util.Iterator;

import org.apache.log4j.Logger;

import stg.utils.ILabelNameSize;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Member;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

/**
 * ClusterListener implementation for PRE.
 * 
 * @author Kedar Raybagkar
 * @version $Revision: 33730 $
 * @since V1.0R28
 * 
 */
public class ClusterListener implements MembershipListener {

    /**
     * Engine instance.
     */
    private CProcessRequestEngine engine;

    /**
     * Logger instance.
     */
    private static Logger logger_ = Logger.getLogger("ClusterListener");

    /**
     * Default constructor. Initializes the logger.
     */
    public ClusterListener(CProcessRequestEngine engine) {
        this.engine = engine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hazelcast.core.MembershipListener#memberAdded(com.hazelcast.core.MembershipEvent)
     */
    public void memberAdded(MembershipEvent arg0) {
        String nodeJoined = arg0.getMember().toString();
        if (logger_.isInfoEnabled()) {
            logger_.info("Acknowledged Node Joined " + nodeJoined);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hazelcast.core.MembershipListener#memberRemoved(com.hazelcast.core.MembershipEvent)
     */
    public void memberRemoved(MembershipEvent arg0) {
        String nodeLeft = arg0.getMember().toString();
        if (logger_.isInfoEnabled()) {
            logger_.info("Acknowledged Node Left " + nodeLeft);
        }

        HeartBeatState contextState = (HeartBeatState) Hazelcast.getMap(ILabelNameSize.CLUSTER_MAP).get(ILabelNameSize.STATE);

        Iterator<Member> iterator = Hazelcast.getCluster().getMembers().iterator();
        Member node = iterator.next();
        HeartBeatState myState = engine.getHeartBeatState();
        Member myNode = Hazelcast.getCluster().getLocalMember();
        if (myNode.equals(node)) {
            if (myState != HeartBeatState.RUNNING) {
                if (contextState == HeartBeatState.REBOOT) {
                    if (logger_.isInfoEnabled()) {
                        logger_.info("Active Node is trying to Reboot itself back due to a reboot request. Activating this node.");
                    }
                } else if (contextState == HeartBeatState.BOUNCE) {
                    if (logger_.isInfoEnabled()) {
                        logger_.info("Active Node is trying to Bounce itself back due to a all threads going into STUCK Mode. Activating this node.");
                    }
                }
                engine.activate(contextState);
            } else {
                if (logger_.isInfoEnabled()) {
                    logger_.info("I am already in " + myState.name() + " mode..");
                }
            }
        } else {
            if (logger_.isInfoEnabled()) {
                logger_.info("Not my turn to activate.");
            }
        }
    }

}
