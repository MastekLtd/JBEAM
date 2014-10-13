/*
* This file forms part of the Systems Task Group International Ltd $
* Copyright (c) Keystone Solutions plc. 2001 - 2002.  All  rights reserved $
*
*
* $Revision: 3317 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/IProcessRequest.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/IProcessRequest.java $
 * 
 * 12    9/15/09 9:43a Kedarr
 * Updated javadoc.
 * 
 * 11    3/21/09 3:46p Kedarr
 * Added a new method to get the context.
 * 
 * 10    2/04/09 3:54p Kedarr
 * Added static keyword to a final variable.
 * 
 * 9     10/05/08 9:58a Kedarr
 * Added a new status.
 * 
 * 8     4/08/08 9:27p Kedarr
 * Added/changed the REVISION variable to public.
 * 
 * 7     3/01/05 10:26a Kedarr
 * Updated the description for USER_CANCELLED_DESC
 * 
 * 6     2/24/05 2:26p Kedarr
 * Deprecated the method setConnection and added a
 * USER_CANCELLATION_STATUS.
 * 
 * 5     1/20/05 9:47a Kedarr
 * Changed the javadoc for the endProcess() method.
 * 
 * 4     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 2     12/09/03 1:57p Kedarr
 * Java Doc changes
* Revision 1.5  2003/12/09 08:27:41  kedar
* Added Javadoc
*
* Revision 1.4  2003/12/02 10:12:06  kedar
* Changed JavaDoc
*
* Revision 1.3  2003/11/28 11:01:58  kedar
* Added JavaDoc
*
 * 
 * 1     11/03/03 12:00p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:40  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 7/01/03    Time: 6:33p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine
 * Added a new status CANCELLED_PROCESSING & its description
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 20/12/02   Time: 8:19p
 * Updated in $/DEC18/ProcessRequestEngine/gmac/pr/engine
 * Added an Intermediate status. I (eye) as Initialization.
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:56p
 * Created in $/ProcessRequestEngine/gmac/pr/engine
 * Initial Version
*
*/

package stg.pr.engine;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;

import stg.pr.engine.datasource.IDataSourceFactory;

/**
 * The Process Request Engine executes any class that implements this interface.
 * The class that implements this interface must have a public default constructor 
 * without any parameters. Remember not to close the connection. Call commit()
 * or rollback() within the {@link #processRequest()} or {@link #endProcess()} methods.
 *  
 * <DL>
 * <DT>{@link #processRequest()}
 * <DT>{@link #endProcess()}
 * </DL>
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 3317 $
 *
 */
public interface IProcessRequest
{
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final String REVISION = "$Revision: 3317 $";

    
    /**
     * Request source.
     *
     * @author kedar.raybagkar
     * @since 
     */
    public static enum REQUEST_SOURCE {
    	OFFLINE(0, "Request Executed By PRE");
    	
    	private final int id;
    	
		private final String description;

		private REQUEST_SOURCE(int id, String description) {
    		this.id = id;
    		this.description = description;
    	}
		
		/**
		 * Returns the ID of the enum.
		 * @return the ID
		 */
		public int getID() {
			return id;
		}

		/**
		 * Resolves a given type to a REQUEST_SOURCE.
		 * 
		 * @param type to be resolved
		 * @return REQUEST_SOURCE
		 */
		public static REQUEST_SOURCE resolve(int type) {
			for (REQUEST_SOURCE rType : REQUEST_SOURCE.values()) {
				if (rType.getID() == type) {
					return rType;
				}
			}
			throw new IllegalArgumentException("Invalid request source passed");
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
    }

    /**
     * Enum that identifies the type of the request.
     *
     * @author kedar.raybagkar
     * @since
     */
    public static enum REQUEST_TYPE {
    	/**
    	 * Stand Alone Request Type
    	 */
    	STANDALONE("S", "Standalone request"),
    	/**
    	 *Group Request Type. 
    	 */
    	GROUPED("G", "Group request");
    	
    	private final String type;
    	
		private final String description;

		/**
		 * Default constructor.
		 */
		private REQUEST_TYPE(String id, String description) {
			this.type = id;
			this.description = description;
		}
		
		/**
		 * Returns the ID of the REQUEST_TYPE
		 * @return String
		 */
		public String getID() {
			return type;
		}
		
		/**
		 * Resolves a given type to the REQUEST_TYPE
		 * @param type to be resolved.
		 * @return REQUEST_TYPE
		 */
		public static REQUEST_TYPE resolve(String type) {
			for (REQUEST_TYPE rType : REQUEST_TYPE.values()) {
				if (rType.getID().equals(type)) {
					return rType;
				}
			}
			throw new IllegalArgumentException("Invalid request type passed");
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
    }
    
    /**
     * Returns the JDBC connection object for the default schema.
     * Connection should not be closed. The engine will take care of the closing the JDBC Connection.
     * 
     * @deprecated since 14.02
     */
    public Connection getConnection();
    
    /**
     * Returns the data source factory.
     * 
     */
    public IDataSourceFactory getDataSourceFactory();

    /**
     * Returns the associated parameters of the request.
     *  
     */
    public HashMap<String, Object> getParams();

    /**
     * Returns the response writer.
     * 
     * Upon the first invocation of this method the response writer object is created and for all subsequent
     * calls the same response writer is returned. If this method is called then it is necessary to close the
     * response writer by calling the close() method on the returned object.
     *  
     */
    public PrintWriter getResponseWriter() throws IOException;

    /**
     * Returns the id associated with the request.
     * 
     */
    public long getRequestId();

    /**
     * Returns the user id associated with the request.
     * 
     */
    public String getUserId();

    /**
     * Returns the source from where the Job is being executed.
     * If executed from PRE it will always be {@link REQUEST_SOURCE#OFFLINE}
     * 
     * @see REQUEST_SOURCE
     */
    public REQUEST_SOURCE getSource();

    /**
     * The Engine executes this method.
     * The business logic that needs to be executed must be written in this method.
     * 
     * @return boolean true if completed else false
     * @throws CProcessRequestEngineException Exception if any.
     */
    public boolean processRequest() throws CProcessRequestEngineException;

    /**
     * Engine calls this method after calling the {@link #processRequest()}.
     * This can used as a clean up procedure. Note that this method is
     * called even though an exception is raised by the {@link #processRequest()} method.
     * Here one can write the code to perform clean-up activities.
     * 
     * @throws CProcessRequestEngineException
     */
    public void endProcess() throws CProcessRequestEngineException;

    
    /**
     * Returns an instance of PREContext.
     * 
     * PREContext can be used to store objects that can be used/shared across different
     * thread (job) executions. Does not allow null values.
     * This is an application wide context. 
     * 
     * @return PREContext
     */
    public PREContext getContext();
    
    /**
     * Returns true if the request was failed over.
     * 
     * @return True if failed over otherwise false.
     */
    public boolean isFailedOver();
    
    /**
     * Enum that identifies various statuses that a request can have during it's life cycle.
     *
     * @author kedar.raybagkar
     * @since
     */
    public static enum REQUEST_STATUS {
    	/**
    	 * Identifies the status as queued. 
    	 */
    	QUEUED("Q", "Queued"),
    	/**
    	 * Identifies the status as launching or taken up for processing.
    	 */
    	LAUNCHING("I", "Launching the job"), 
    	/**
    	 * Identifies the status as processing. 
    	 * This is when the method {@link IProcessRequest#processRequest()} is called.
    	 */
    	PROCESSING("P", "Processing"),
    	/**
    	 * Identifies the status as completed.
    	 * This is when the method {@link IProcessRequest#processRequest()} returns true.
    	 */
    	COMPLETED("S", "Completed"),
    	/**
    	 * Identifies the status as error'ed out.
    	 * This is when the method {@link IProcessRequest#processRequest()} returns false or
    	 * the method throws an exception.
    	 */
    	ERROR("E", "Errored Out"),
    	/**
    	 * Identifies the status as suspended.
    	 * This is applicable only in case of group request wherein the earlier request fails then
    	 * all subsequent requests are marked as suspended.
    	 * the method throws an exception.
    	 */
    	SUSPENDED("C", "Suspended Job"),
    	/**
    	 * Identifies the status as user canceled.
    	 */
    	USER_CANCELLED("X", "User Cancelled Job"),
    	/**
    	 * Identifies the status as Killed.
    	 * This is possible when the PRE's web service is used.
    	 */
    	KILLED("K", "Job Processing Killed");
    	
    	private final String id;
    	
		private final String description;

		private REQUEST_STATUS(String id, String description) {
    		this.id = id;
    		this.description = description;
    	}
		
		/**
		 * Returns the ID of the status.
		 * @return String
		 */
		public String getID() {
			return id;
		}
		
		/**
		 * Returns the description of the status.
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * Resolves a given status to a REQUEST_STATUS
		 * @param status
		 * @return REQUEST_STATUS
		 */
		public static REQUEST_STATUS resolve(String status) {
			for (REQUEST_STATUS pstatus : REQUEST_STATUS.values()) {
				if (pstatus.getID().equals(status)) {
					return pstatus;
				}
			}
			throw new IllegalArgumentException("Invalid status passed" + status);
		}
    }
}
