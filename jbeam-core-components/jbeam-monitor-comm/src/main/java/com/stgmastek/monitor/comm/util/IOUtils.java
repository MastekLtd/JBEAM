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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A collection of static IO related utilities.
 *
 * @author Kedar Raybagkar
 * @since
 */
public final class IOUtils {

    /**
     * Returns the input stream for the given resource.
     * 
     * @param clazz Class from which the class loader will be used initially to locate the resource.
     * @param resource to be loaded.
     * @return InputStream for the given resource.
     * @throws IOException if unable to load.
     */
    public static InputStream getResourceAsInputStream(Class<?> clazz, String resource) throws IOException {
        InputStream is = null;
        if ( clazz != null ) {
            is = clazz.getResourceAsStream( resource );
        }
        
        if ( is == null ) {
        	is = (new IOUtils()).getClass().getClassLoader().getResourceAsStream( resource );
        }

        if ( is == null ) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream( resource );
        }

        if ( is == null ) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream( resource );
        }

        if ( is == null ) {
            throw new FileNotFoundException( "'" + resource + "' cannot be opened because it does not exist" );
        }
        return is;
    }	

}
