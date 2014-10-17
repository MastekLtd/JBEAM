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
package stg.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Resource loading related utilities.
 *
 * @author kedar460043
 * @since
 */
public class ResourceUtils {

    /**
     * Returns the input stream for the given resource.
     * <ul>
     * <li> class.getResourceAsStream(..)
     * <li> class.getClassLoader().getResourceAsStream(..)
     * <li> Thread.currentThread().getContextClassLoader().getResourceAsStream(..)
     * <li> ClassLoader.getSystemClassLoader().getResourceAsStream(..)
     * </ul>
     * If none of the above is able to return the stream then a {@link FileNotFoundException} is thrown.
     * 
     * @param clazz from where the stream should be loaded if possible.
     * @param resource to be loaded as stream.
     * @return InputStream
     * @throws IOException if unable to load
     */
    public static InputStream getResourceAsStream(Class<?> clazz, String resource) throws IOException {
        InputStream is = null;
        if ( clazz != null ) {
            is = clazz.getResourceAsStream( resource );
        }
        
        if ( is == null ) {
        	is = ResourceUtils.class.getClassLoader().getResourceAsStream( resource );
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
