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
 * $Revision: 2382 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/CHttpServer.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/CHttpServer.java $
 * 
 * 5     2/04/09 3:53p Kedarr
 * Added static keyword to a final variable.
 * 
 * 4     9/30/08 8:56p Kedarr
 * Implemented ThreadPool for handling the web server requests.
 * 
 * 3     7/15/05 3:03p Kedarr
 * Updated Javadoc
 * 
 * 2     7/15/05 2:29p Kedarr
 * Removed All Unused variables. Added a method to return REVISION number.
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
*
*/

package stg.pr.engine;

import java.net.Socket;
import java.util.Vector;

/**
 * HttpServer.
 * 
 * 
 * @version $Revision: 2382 $
 * @author Kedar C. Raybagkar
 *
 */
public class CHttpServer extends CBaseServer implements Runnable
{
    /**
     * Stores the Version Number of the class.
     * Comment for code <code>REVISION</code>
     */
    public static final String REVISION = "$Revision:: 2382          $";
    
	/**
	 * OS File Separator.
	 * 
	 * Comment for <code>sep</code>
	 */
	private char charOSFileSeparator_;
	
	/**
	 * Current Thread.
	 * 
	 * Comment for <code>t</code>
	 */
	
	private Thread thread_=null;

	/**
	 * Vector for storing the filters.
	 * Comment for <code>alias</code>
	 */
	private Vector<String> vectorFilters_;
	
//	/**
//	 * Currently not used.
//	 * Vector for storing the alias.
//	 * 
//	 * Comment for <code>alias</code>
//	 */
//	private Vector vectorAlias_;
	
	/**
	 * This variable is used to store the counter for the number of requests 
	 * processed by this server.
	 * Comment for <code>iServiceCounter</code>
	 */
	private long lServiceCounter;

	/**
	 * Constructs the HttpServer.
	 */
	public CHttpServer()
	{
		super();
		charOSFileSeparator_=System.getProperty("file.separator").charAt(0);
		vectorFilters_=new Vector<String>();
//		vectorAlias_=new Vector();
		lServiceCounter = 0;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.BaseServer#start()
	 */
	public synchronized void start()
	{
		if(thread_==null)
		{
			thread_=new Thread(this);
			thread_.start();
		}
	}

	/**
	 * Start the server for the specified port.
	 * 
	 * @param p Int port number of the server.
	 */
	public synchronized void start(int p)
	{
	    setPort(p);
		start();
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.BaseServer#stop()
	 */
	public synchronized void stop()
	{
		if(thread_!=null)
		{
			thread_.interrupt();
			thread_=null;
			super.stop();
		}
	}

	/**
	 * Returns the OS File separator.
	 *  
	 * @return char
	 */
	public char getSep()
	{
		return charOSFileSeparator_;
	}

	/**
	 * Returns the default file name if not requested from the URL.
	 * Default value is PreInfo.do.
	 * 
	 * @return String
	 */
	public String getDefaultname()
	{
		return "PreInfo.do";
	}


	/**
	 * Currently not used.
	 * 
	 * Checks the filters for specific extention.
	 * 
	 * @param ext The extention to be filtered.
	 * @return String
	 */
	public String checkFilters(String ext)
	{
		String ret="";
		for(int i=0;i<vectorFilters_.size();i++)
		{
			if(vectorFilters_.elementAt(i).startsWith(ext+";"))
			{
				ret=vectorFilters_.elementAt(i).substring(ext.length()+1);
				break;
			}
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see stg.pr.engine.BaseServer#handleRequest(java.net.Socket)
	 */
	protected void handleRequest(Socket client)
	{
	    if (isStarted())
	    {
	        lServiceCounter++;
	        super.execute(new CConnectionThread(client, this));
//	        (new CConnectionThread(client, this)).start();
	    }
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		super.start();
	}
	
	/**
	 * Returns the serviced request counter.
	 * 
	 * @return long
	 */
	public long getRequestServicedCounter()
	{
	    return lServiceCounter;
	}
	
    /**
     * Returns the version number of the class.
     * @return String
     */
    public String getVersion() {
        return REVISION;
    }
}