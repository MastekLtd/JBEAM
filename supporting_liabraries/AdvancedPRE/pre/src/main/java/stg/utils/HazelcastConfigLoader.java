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
 */
package stg.utils;

import java.io.IOException;
import java.util.ArrayList;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.ConfigLoader;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.TcpIpConfig;

public class HazelcastConfigLoader {

    
    public static ClientConfig loadClientConfiguration(String filePath) throws IOException {
        Config config = ConfigLoader.load(filePath);
        String port = ":" + config.getPort();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setGroupConfig(config.getGroupConfig());
        MulticastConfig mConfig = config.getNetworkConfig().getJoin().getMulticastConfig();
        TcpIpConfig tConfig = config.getNetworkConfig().getJoin().getTcpIpConfig();
        if (!mConfig.isEnabled()) {
            ArrayList<String> list = new ArrayList<String>();
            for (String member : tConfig.getMembers()) {
                if (!member.contains(":")) {
                    member += port;
                }
                list.add(member);
            }
            clientConfig.setAddresses(list);
        }
        return clientConfig;
    }
}
