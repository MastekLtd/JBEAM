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
package stg.pr.engine;

/**
 * An interface that exposes the instance of T.
 * 
 * @param <T> Class T that will be exposed.
 * 
 * @author Kedar Raybagkar
 * @since 30.1.0
 *
 */
public interface Singleton<T> {

    /**
     * Returns the instance of T.
     * @return instance.
     */
    public T getInstance();
    
}
