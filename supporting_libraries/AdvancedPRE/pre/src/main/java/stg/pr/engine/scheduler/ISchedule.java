/*
* This file forms part of the Systems Task Group International Limited      $
* Copyright (c) Keystone Solutions plc. 2001 - 2002.  All  rights reserved $
*
*
* $Revision: 2958 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/scheduler/ISchedule.java 1402 2010-05-06 11:14:41Z kedar $
*
* $log::                                                              $
* 
*/
package stg.pr.engine.scheduler;

import java.io.PrintWriter;
import java.util.HashMap;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.engine.PREContext;

/**
 * Marker Interface.
 * This interface defines few static variables that used across by the Scheduler. 
 * @author kedarr
 *
 */
public interface ISchedule
{
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2958              $";
    
    /**
     * Identifier for Dynamic Indicator.
     * Comment for <code>DYNAMIC_INDICATOR</code>
     */
    public static final String DYNAMIC_INDICATOR = "D";
    
    /**
     * Identifier for Future Scheduling Only.
     * Comment for <code>FUTURE_SCHEDULING_ONLY</code>
     */
    public static final String FUTURE_SCHEDULING_ONLY = "Y";
    
    /**
     * Enum that defines frequencies.
     *
     * @author Kedar Raybagkar
     * @since V1.0R28.2
     */
    public static enum FREQUENCY {
//    	SECOND("SECOND", "Second"),
    	MINUTE("MINUTE", "Minute"),
    	HOUR("HOUR", "Hour"),
    	DAY("DAY", "Day"),
    	WEEK("WEEK", "Week"),
    	MONTH("MONTH", "Month"),
    	YEAR("YEAR", "Year"),
    	LAST_DATE_OF_MONTH("LDMONTH", "Last date of the month"),
    	FIRST_DAY_OF_MONTH("FIRST_MTH", "First day of the month"),
    	SECOND_DAY_OF_MONTH("SECOND_MTH", "Second day of the month"),
    	THIRD_DAY_OF_MONTH("THIRD_MTH", "Third day of the month"),
    	FOURTH_DAY_OF_MONTH("FOURTH_MTH", "Fourth day of the month"),
    	LAST_DAY_OF_MONTH("LAST_MTH", "Last day of the month"),
    	FIRST_DAY_OF_YEAR("FIRST_YR", "First day of the year"),
    	SECOND_DAY_OF_YEAR("SECOND_YR", "Second day of the year"),
    	THIRD_DAY_OF_YEAR("THIRD_YR", "Third day of the year"),
    	FOURTH_DAY_OF_YEAR("FOURTH_YR", "Fourth day of the year"),
    	LAST_DAY_OF_YEAR("LAST_YR", "Last day of the year"),
    	PRE_PROGRAMMED("PREDEFINED","Pre programed frequency");
    	
    	
    	private final String id;
    	
		private final String description;

		private FREQUENCY(String id, String description) {
    		this.id = id;
    		this.description = description;
    	}

		/**
		 * @return the id
		 */
		public String getID() {
			return id;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * Resolves a given id with the frequency and returns the enum.
		 * @param id
		 * @return {@link FREQUENCY}
		 */
		public static FREQUENCY resolve(String id) {
			for (FREQUENCY frequency : FREQUENCY.values()) {
				if (frequency.getID().equals(id)) {
					return frequency;
				}
			}
			throw new IllegalArgumentException("Unable to resolve #" + id + " against the defiend frequencies.");
		}
    	
    }
    
    /**
     * Enum that defines the schedule status.
     *
     * @author Kedar Raybagkar
     * @since V1.0R28.2
     */
    public static enum SCHEDULE_STATUS {
    	ACTIVE("A", "Active"),
    	COMPLETED("F", "Completed"),
    	TERMINATED("X", "Cancelled by PRE"),
    	USER_CANCELLED("C", "Cancelled by user");
    	
    	private final String id;
    	
    	private final String description;
    	
    	private SCHEDULE_STATUS(String id, String description) {
    		this.id = id;
    		this.description = description;
    	}
    	
    	/**
    	 * @return the id
    	 */
    	public String getID() {
    		return id;
    	}
    	
    	/**
    	 * @return the description
    	 */
    	public String getDescription() {
    		return description;
    	}
    	
    	/**
    	 * Resolves a given id with the frequency and returns the enum.
    	 * @param id
    	 * @return {@link SCHEDULE_STATUS}
    	 */
    	public static SCHEDULE_STATUS resolve(String id) {
    		for (SCHEDULE_STATUS status : SCHEDULE_STATUS.values()) {
    			if (status.getID().equals(id)) {
    				return status;
    			}
    		}
    		throw new IllegalArgumentException("Unable to resolve #" + id + " against the defiend status.");
    	}
    	
    }
    
    /**
     * Defines the end on attributes.
     *
     * @author Kedar Raybagkar
     * @since V1.0R28.02
     */
    public static enum END_ON {
    	/**
    	 * End on date.
    	 */
    	DATE,
    	/**
    	 * End on number of occurrences. 
    	 */
    	OCCURRENCES;
    }

    /**
     * Enum that defines the SKIP flags applicable for a schedule.
     *
     *
     * @author Kedar Raybagkar
     * @since V1.0R28.2
     */
    public static enum SKIP_FLAG {
    	NOT_APPLICAIBLE("NA"), 
    	SKIP_SCHEDULE("SS"), 
    	ADVANCE_BEFORE("D-"), 
    	ADVANCE_AFTER("D+");
    	
    	private final String id;

		private SKIP_FLAG(String identifier) {
    		this.id = identifier;
    	}
		
		public String getID() {
			return id;
		}
		
		public static SKIP_FLAG resolve(String skipFlag) {
			for (SKIP_FLAG flag : SKIP_FLAG.values()) {
				if (flag.getID().equals(skipFlag)) {
					return flag;
				}
			}
			throw new IllegalArgumentException() ;
		}
    };
    
    /**
     * An ENUM that defines the <i>'Keep Alive'</i> nature of a schedule.
     *
     * @author kedar.raybagkar
     * @since
     */
    public static enum KEEP_ALIVE {
    	/**
    	 * YES indicates that the PRE will try to pro-create the next recurrence of the JOB as per the associated schedule even if
    	 * the job fails. 
    	 */
    	YES("Y"), 
    	/**
    	 * NO indicates that the PRE will terminate the schedule if the underlying JOB fails to execute.
    	 */
    	NO("N");
    	
    	private final String ID;

		private KEEP_ALIVE(String identifier) {
    		this.ID = identifier;
    	}
		
		public String getID() {
			return ID;
		}
		
		public static KEEP_ALIVE resolve(String keepAlive) {
			for (KEEP_ALIVE flag : KEEP_ALIVE.values()) {
				if (flag.getID().equals(keepAlive)) {
					return flag;
				}
			}
			return NO;
		}
    }

    /**
     * Set the PrintWriter object for logging purpose.
     * 
     * @param pwOut PrintWriter
     */
    public void setPrintWriter(PrintWriter pwOut);

    /**
     * Sets the Process Request Entity Bean.
     * 
     * @param pobjPREB ProcessRequestEntityBean
     */
    public void setProcessRequestEntityBean(ProcessRequestEntityBean pobjPREB);

    /**
     * Sets the Parameters associated with the Request.
     * 
     * @param phmParameters HashMap
     */
    public void setRequestParameters(HashMap<String, Object> phmParameters);
    
    /**
     * PRE will set the PREContext object.
     * @param context 
     * 
     */
    public void setPREContext(PREContext context);
    
}
