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
package com.stg.logger;

import org.apache.log4j.Level;

/**
 * Defines the Level of log4 <i>J </i>.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 1 $
 */
public class LogLevel extends Level {

    /**
     * Serial version UID for the LogLevel.
     */
    private static final long serialVersionUID = -8207268947589439413L;

    private static final String REVISION = "$Revision: 1 $";
    
    public static final int NOTICE_INT = 60000;
    public static final int FINEST_INT = Integer.MIN_VALUE + 1000;
    public static final int FINER_INT = FINEST_INT + 1000;
    public static final int FINE_INT = FINER_INT + 1000;

    public static final Level NOTICE = new LogLevel(NOTICE_INT, "NOTICE", NOTICE_INT);
    
    public static final Level FINEST = new LogLevel(FINEST_INT, "FINEST", FINEST_INT);

    public static final Level FINER = new LogLevel(FINER_INT, "FINER", FINER_INT);
    
    public static final Level FINE = new LogLevel(FINE_INT, "FINE", FINE_INT);

    private LogLevel(int arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);
    }
    
    /* (non-Javadoc)
     * @see org.apache.log4j.Level#toLevel(int)
     */
    public static Level toLevel(int arg0) {
        switch (arg0) {
        case NOTICE_INT:
            return NOTICE;
        case FINEST_INT:
            return FINEST;
        case FINER_INT:
            return FINER;
        case FINE_INT:
            return FINE;
        default:
           return Level.toLevel(arg0);
        }
    }
    
    /* (non-Javadoc)
     * @see org.apache.log4j.Level#toLevel(java.lang.String)
     */
    public static Level toLevel(String arg0) {
        if (NOTICE.toString().equals(arg0)) return NOTICE;
        if (FINEST.toString().equals(arg0)) return FINEST;
        if (FINER.toString().equals(arg0)) return FINER;
        if (FINE.toString().equals(arg0)) return FINE;
        return Level.toLevel(arg0);
    }
    
    /* (non-Javadoc)
     * @see org.apache.log4j.Level#toLevel(java.lang.String, org.apache.log4j.Level)
     */
    public static Level toLevel(String arg0, Level arg1) {
        if (NOTICE.toString().equals(arg0)) return NOTICE;
        if (FINEST.toString().equals(arg0)) return FINEST;
        if (FINER.toString().equals(arg0)) return FINER;
        if (FINE.toString().equals(arg0)) return FINE;
        return Level.toLevel(arg0, arg1);
    }
    
    /* (non-Javadoc)
     * @see org.apache.log4j.Level#toLevel(int, org.apache.log4j.Level)
     */
    public static Level toLevel(int arg0, Level arg1) {
        switch (arg0) {
        case NOTICE_INT:
            return NOTICE;
        case FINEST_INT:
            return FINEST;
        case FINER_INT:
            return FINER;
        case FINE_INT:
            return FINE;
        default:
           return Level.toLevel(arg0, arg1);
        }
    }
    
    
    /**
     * Returns the version number of the class.
     * @return String
     */
    public String getVersion() {
        return REVISION;
    }
}
