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

import java.util.List;

import com.stgmastek.core.interfaces.IRequestInterceptor;

/**
 * Uses ThreadLocal that caches list of {@link IRequestInterceptor}.
 * The ICDServiceHandler makes uses of these interceptors to intercept the request going to ICDService.
 * 
 * @author Kedar Raybagkar
 *
 */
public class ChainOfInterceptors {

    private static final ThreadLocal<List<IRequestInterceptor>> thLocal = new ThreadLocal<List<IRequestInterceptor>>();
    
    /**
     * Sets the request interceptors.
     * @param chain of interceptors.
     */
    public static void set(List<IRequestInterceptor> chain) {
        thLocal.set(chain);
    }
    
    /**
     * Returns the registered RequestInterceptors.
     * @return list of {@link IRequestInterceptor}
     */
    public static List<IRequestInterceptor> get() {
        return thLocal.get();
    }
}
