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
 * An interface that can hook up  any custom class in the lifecycle of PRE.
 * PRE will invoke {@link #init(PREContext)} during the startup and will then
 * invoke {@link #destroy(PREContext)} during shutdown process.
 * 
 * @author Kedar Raybagkar
 * @since 30.1.0
 *
 */
public interface Service {
    
    /**
     * Perform initialization routines.
     * This method is invoked when the PRE starts.
     *  
     * @param context PREContext implementation.
     */
    public void init(PREContext context);
    
    /**
     * Perform clean up and destroy actions.
     * This method is invoked when the PRE shuts down.
     * 
     * @param context PREContext implementation.
     */
    public void destroy(PREContext context);

}
