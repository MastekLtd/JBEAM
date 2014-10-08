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
* $Revision: 3309 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/CBaseServer.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/CBaseServer.java $
 * 
 * 16    9/01/09 9:20a Kedarr
 * Changes made to catch specific exceptions.
 * 
 * 15    8/24/09 10:36p Kedarr
 * URL Decoder is used for decoding the query.
 * 
 * 14    8/16/09 12:58p Kedarr
 * Added a new method that reads the HTTP query and returns the parameters
 * and values in a Map.
 * 
 * 13    2/04/09 4:03p Kedarr
 * Added static keyword to a final variable. Rectified the code to handle
 * possible throwing of null pointer exceptions.
 * 
 * 12    10/05/08 9:55a Kedarr
 * Changed the timout from 5 seconds to 10 minutes.
 * 
 * 11    9/30/08 8:56p Kedarr
 * Implemented ThreadPool for handling the web server requests.
 * 
 * 10    3/02/07 9:03a Kedarr
 * Changes made to capture the query string as a 4th parameter.
 * 
 * 9     6/05/06 12:11p Kedarr
 * Added two methods.
 * 
 * 8     7/15/05 3:02p Kedarr
 * Updated Javadoc
 * 
 * 7     7/15/05 2:29p Kedarr
 * Removed All Unused variables. Added a method to return REVISION number.
 * 
 * 6     6/21/05 4:58p Kedarr
 * Refactored name PRELogLevel to LogLevel
 * 
 * 5     6/01/05 12:19p Kedarr
 * Changed the spelling mistake.
 * 
 * 4     6/01/05 11:56a Kedarr
 * Organized imports.
 * 
 * 3     5/31/05 6:18p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 2     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
*
*/

package stg.pr.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This is an abstract class that defines the default base methods for the 
 * web server.
 * 
 * The class is used to create server and client sockets and have by default
 * implementation of the various helper methods.
 * 
 * @version $Revision: 3309 $
 * @author Kedar C. Raybagkar
 * @see stg.pr.engine.CHttpServer
 */
public abstract class CBaseServer
{
	/**
	 * This variable is used to store the port information of the socket.
	 * Default port is set to 8080.
	 * Comment for <code>port</code>
	 */
	protected int port=8080;
	
    /**
     * The version control system will update this revision variable and thus
     * the revision numbers will automatically get updated. 
     * Comment for <code>REVISION</code>
     */
    private static final String REVISION = "$Revision:: 3309              $";
    
	/**
	 * Server side socket.
	 * Comment for <code>server</code>
	 */
	private ServerSocket server;
	
	/**
	 * Client socket to read the request and to write the response.
	 * Comment for <code>client</code>
	 */
	private Socket client;
	
	/**
	 * Boolean variable indicating whether the server is in Running mode or Not.
	 * Comment for <code>bStarted_</code>
	 */
	private volatile AtomicBoolean bStarted_ = new AtomicBoolean(false);
	
	/**
	 * Comment for <code>logger_</code>
	 */
	private Logger logger_;
    
    /**
     * Stores the throwable object generated on error.
     * Comment for <code>objThrowable_</code>.
     */
    private Throwable objThrowable_;

    /**
     * ThreadPoolExecutor.
     */
    private ThreadPoolExecutor executor_;

    /**
	 * Constructs the base server.
	 * 
	 * Logger instance is created here.
	 * 
	 */
	public CBaseServer()
	{
	    logger_ = Logger.getLogger("WebServer");
	}
	
	/**
	 * Starts the server.
	 * Creates the Server Socket and starts listening.
	 */
	public void start()
	{
		try
		{
            executor_ = new ThreadPoolExecutor(5, 5, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(5));
            executor_.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
            executor_.allowCoreThreadTimeOut(true);
		    logger_.info("Acquiring Port #" + port);
		    objThrowable_ = null;
		    boolean serverIsNotBound = true;
		    while (serverIsNotBound) {
		    	try {
		    		server= new ServerSocket(port);
		    		serverIsNotBound = false;
		    	} catch (BindException e) {
		    		if (logger_.isEnabledFor(LogLevel.FINE)) {
		    			logger_.log(LogLevel.FINE, "Port " + port + " already in use.. Increamenting by 1");
		    		}
		    		port++;
		    	}
		    }
            bStarted_.set(true);
            logger_.log(LogLevel.NOTICE, "Web Server Started and is listening on " + port);
			while(bStarted_.get())
			{
				try
                {
                    client = server.accept();
                    handleRequest(client);
                }
				catch (SocketException e)
                {
                    if (!bStarted_.get())
                    {
                        //This exception is raised as the socket was closed by
                        //the stop method.
                        //dummy catch.
                    }
                    else
                    {
                        bStarted_.set(false);
                        objThrowable_ = e;
                        throw e;
                    }
                } //end catch
			} //while (bstarted_)
		} catch (IOException e) {
          if (bStarted_.get())
          {
              logger_.error(e);
                bStarted_.set(false);
                objThrowable_ = e;
          }
        } finally {
		    
		}
	}

	/**
	 * Stops the server.
	 * Closes the server socket and client socket.
	 */
	public void stop()
	{
	    if (bStarted_.get())
	    {
            objThrowable_ = null;
            bStarted_.set(false);
            executor_.shutdownNow();
			try
			{
			    if (client != null)
	            {
					client.close();
					client = null;
	            }
			}
			catch(IOException e)
			{
			    logger_.error("IO Exception Raised during reading the input stream", e);
			}
            try
            {
                if (server != null)
                {
                    server.close();
                    server = null;
                }
            } catch (IOException ioe)
            {
                logger_.error("IO Exception Raised during reading the input stream", ioe);
            }
	    }
	    else
	    {
	        logger_.log(LogLevel.NOTICE, "Web Server Already Stopped.");
        }
	}

	/**
	 * Handles the request requested by the client.
	 * This is an abstract method and the implementation of the same is left to the 
	 * class which extends this.
	 * 
	 * @param client Client Socket.
	 */
	protected abstract void handleRequest(Socket client);
	
	/**
	 * 
	 * @return True if started otherwise returns False.
	 */
	public boolean isStarted()
	{
	    return bStarted_.get();
	}

	/**
	 * The request input stream is read and the data is gathered 
	 * and stored in the vector.
	 * 
	 * @param in DataInputStream for reading the request.
	 * @return Vector representing the data.
	 * @throws IOException
	 */
	public Vector<String> getInput(BufferedReader in) throws IOException
	{
		Vector<String> temp=new Vector<String>();
		String text=null;

		try
		{
			//while((text=in.readLine()).length()!=0)
			while(!"".equals(text=in.readLine()))
            {
			    temp.addElement(text);
            }
		}
		catch(Exception e)
		{
		    logger_.error("Exception Raised during reading the input stream", e);
		}
		return temp;
	}

	/**
	 * Gets the requested action from the request.
	 * 
	 * The request is divided in 3 parts for the request type, path + file and query string.
	 * Request Type indicates the request way GET / POST.
	 * File indicates along with the path and the name of the servlet /PreInfo.do or /pre/jsp/PreInfo.jsp
	 * Query indicates the parameters passed in the request.
	 * 
	 * @param data String data to be divided 
	 * @param sep OS File Separator
	 * @return String[] array consists Path, File and Query.
	 */
	public String[] getAction(String data,char sep)
	{
		String ret[] = new String[4];
		StringTokenizer tok=new StringTokenizer(data," ");
		String temp="";
        String query="";
		
		temp=tok.nextToken();
		ret[0]=temp;

		temp=tok.nextToken();
		
		int j;
		if(ret[0].equals("GET") && (j=temp.indexOf('?'))!=-1)
		{
			query=temp.substring(j+1);
			temp=temp.substring(0,j);
		}

		if(temp.charAt(0)=='/')
			temp=temp.substring(1);
		else
			temp="";
		temp=temp.replace('/',sep);
		ret[1]=temp;

		int l=temp.lastIndexOf('.');

		if(l!=-1)
			ret[2]=temp.substring(l+1);
		else		
			ret[2]="";
		ret[3] = query;
		return ret;
	}
	

	/**
	 * Returns the Port number to which the server is listening.
	 * 
	 * @return int
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Sets the port to which the httpserver should bound itself to.
	 * 
	 * @param p int Port Number.
	 */
	public void setPort(int p)
	{
		port=p;
	}
    
    /**
     * Returns the version number of the class.
     * @return String
     */
    public String getVersion() {
        return REVISION;
    }
    
    /**
     * Returns true if an error is encountered while starting the web server.
     *
     * @return boolean
     */
    public boolean isError() {
        return (objThrowable_ != null);
    }
    
    /**
     * Returns the error object.
     *
     * This method may return null if no error is encountered.
     *
     * @return Throwable
     */
    public Throwable getError() {
        return objThrowable_;
    }
    
    /**
     * Executes the web service requests in a Thread Pool.
     * @param command A runnable command.
     */
    public final void execute(Runnable command) {
        executor_.execute(command);
    }
    
    /**
     * Returns the HTTP query in an map.
     * @param strQuery
     * @return Map
     */
    public final Map<String,String> getQuery(String strQuery) {
        String strQueryDecoded;
        try {
            strQueryDecoded = URLDecoder.decode(strQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            strQueryDecoded = strQuery;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        StringCharacterIterator sci = new StringCharacterIterator(strQueryDecoded);
        boolean bEscapeCharacter = false;
        boolean bQuoted = false;
        StringBuffer cmdBuffer = new StringBuffer();
        ArrayList<String> commandBuilder = new ArrayList<String>();
        for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
            switch (c) {
            case '\\':
                if (bEscapeCharacter) {
                    cmdBuffer.append(c + "" + c);
                    bEscapeCharacter = false;
                } else {
                    bEscapeCharacter = true;
                }
                break;
            case '&':
                if (!bQuoted || !bEscapeCharacter) {
                    commandBuilder.add(cmdBuffer.toString());
                    cmdBuffer.delete(0, cmdBuffer.length());
                } else {
                    cmdBuffer.append(c);
                }
                bEscapeCharacter = false;
                break;
            case '"':
                if (!bEscapeCharacter) {
                    if (!bQuoted) {
                        bQuoted = true;
                    } else {
                        bQuoted = false;
                    }
                }
                bEscapeCharacter = false;
                break;
            default:
                cmdBuffer.append(c);
                break;
            } // end of switch case.
        } // end for string character iterator
        if (cmdBuffer.length() > 0) {
            commandBuilder.add(cmdBuffer.toString());
        }
        for (Iterator<String> iterator = commandBuilder.iterator(); iterator.hasNext();) {
            String str = iterator.next();
            int index = str.indexOf('=');
            if ( index > 0) {
                map.put(str.substring(0, index), str.substring(index+1));
            }
        }
        return map;
    }
}