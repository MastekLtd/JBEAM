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
 * $Revision: 3829 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/CConnectionThread.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/CConnectionThread.java $
 * 
 * 21    10/26/09 5:19p Kedarr
 * Removed the javascript brace bracket.
 * 
 * 20    9/08/09 3:24p Kedarr
 * Commented the code to display first 68 characters.
 * 
 * 19    9/01/09 9:21a Kedarr
 * Localized variables and changes made to catch specific exceptions.
 * 
 * 18    8/30/09 10:49p Kedarr
 * Changes made for decoding the encrypted values passed from other
 * clustered PRE.
 * 
 * 17    8/24/09 10:36p Kedarr
 * Stop and Kill now accepts the user id and reason for killing and
 * stopping the execution of a running thread.
 * 
 * 16    8/16/09 1:34p Kedarr
 * Added new methods to invoke stop on the currently running threads.
 * Changes made for fetching query
 * 
 * 15    3/21/09 10:45a Kedarr
 * Changes made for implementing license, company name change and company
 * website instead of individual email ids.
 * 
 * 14    2/04/09 3:53p Kedarr
 * Added static keyword to a final variable. Rectified the code to avoid
 * throwing of null pointer exception.
 * 
 * 13    10/05/08 9:58p Kedarr
 * modified the wordings of the message..
 * 
 * 12    10/05/08 9:57a Kedarr
 * Added 2 more methods for killing a process through the web server.
 * Modified the processStatus method to display links. Also, did eclipse
 * re-formatting.
 * 
 * 11    4/08/08 9:49p Kedarr
 * Added/changed the REVISION variable to public. Added pool statistical
 * attributes to the display and some minor changes to the bundle version
 * numbers.
 * 
 * 10    3/12/07 9:53a Kedarr
 * Changes made to add the HTML header in the methods where it was missed.
 * 
 * 9     3/02/07 9:02a Kedarr
 * Added new methods to stop resume the engine. These can be called from
 * the web server calls. The engine uses these methods to do the handshake
 * between multiple PREs configured in a cluster mode.
 * 
 * 8     6/06/06 11:39a Kedarr
 * Changes made for displaying memory statistics on the web server for
 * Graphical use.
 * 
 * 7     6/28/05 12:15p Kedarr
 * Layout changes made in the about window.
 * 
 * 6     6/20/05 6:32p Kedarr
 * Changed to add "Packaged On" <date> in mail and version printing.
 * 
 * 5     6/15/05 2:19p Kedarr
 * Adjusted the About window's size and height.
 * 
 * 4     6/15/05 1:40p Kedarr
 * Called  getBundleDetails from engine class to show the product version
 * and name.
 * 
 * 3     5/31/05 6:18p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 2     5/09/05 1:19p Kedarr
 * Instead of displaying Process the class is changed to display Process
 * as Job.
 * 
 * 1     1/11/05 10:01a Kedarr
 * Revision 1.1  2005/11/03 04:54:42  kedar
 * *** empty log message ***
 *
 *
 */

package stg.pr.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import stg.customclassloader.CCustomClassLoader;
import stg.customclassloader.IPREClassLoaderClient;
import stg.pr.beans.CProcessStatusBean;
import stg.pr.beans.ProcessRequestEntityBean;
//import stg.pr.engine.license.LicenseParamImpl;
import stg.pr.engine.startstop.CRebootEngine;
import stg.utils.CSettings;
import stg.utils.SpecialString;

/**
 * Handles a request sent from the browser or over the socket to the HttpServer.
 * 
 * @version $Revision: 3829 $
 * 
 * @author Kedar C. Raybagkar
 */
public class CConnectionThread implements Runnable, IPREClassLoaderClient {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision: 3829 $";

    /**
     * Client Socket. Comment for <code>client</code>
     */
    private Socket client_;

    /**
     * The instance of the HttpServer.
     * 
     * Comment for <code>server</code>
     */
    private CHttpServer server_;

    /**
     * Comment for <code>sbufferPropertyLinks_</code>
     */
    private StringBuffer sbufferPropertyLinks_;

    private Logger logger_;

    /**
     * Default Constructor.
     */
    public CConnectionThread() {
        logger_ = Logger.getLogger("WebServer");
    }

    /**
     * Constructs the object for the given socket and for the HttpServer.
     * 
     * @param psocket
     *            Socket Response is written to this socket.
     * @param phttpserver
     *            HttpServer that requested for action.
     */
    public CConnectionThread(Socket psocket, CHttpServer phttpserver) {
        this();
        client_ = psocket;
        server_ = phttpserver;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public synchronized void run()
    {
        BufferedReader in = null;
        PrintStream out = null;
        Vector<String> data = null;

        try
        {
            in = new BufferedReader(new InputStreamReader(client_
                    .getInputStream()));
            data = server_.getInput(in);
            String[] strFileArray = server_.getAction((String) data.elementAt(0),
                    server_.getSep());
            if (strFileArray[1].length() == 0
                    || strFileArray[1].charAt(strFileArray[1].length() - 1) == server_
                            .getSep())
                    strFileArray[1] = strFileArray[1]
                            + server_.getDefaultname();
            out = new PrintStream(client_.getOutputStream());
            if (strFileArray[1].equals("PreInfo.do"))
            {
                writePREInfo(out);
            } 
            else if (strFileArray[1].equals("about"))
            {
                writeAbout(out);
            } 
//            else if (strFileArray[1].equals("licenseinfo"))
//            {
//                writeLicenseInfo(out);
//            } 
            else if( strFileArray[1].equals("processstatus"))
            {
                writeProcessStatus(out);
            } 
            else if (strFileArray[1].indexOf("properties") >= 0)
            {
                writeProperties(out, strFileArray[1].substring(strFileArray[1]
                        .lastIndexOf(server_.getSep()) + 1));
            }
            else if (strFileArray[1].indexOf("CMemoryGraph") >= 0)
            {
                writeClassBytes(out, "stg.applets.CMemoryGraph");
            }
            else if (strFileArray[1].indexOf("status") >= 0)
            {
            	writePREStatus(strFileArray[3], out);
            }
            else if (strFileArray[1].indexOf("stop") >= 0)
            {
            	stopEngine(strFileArray[3], out);
            }
            else if (strFileArray[1].indexOf("reboot") >= 0)
            {
            	rebootEngine(out);
            } else if (strFileArray[1].indexOf("killProcessWarning") >= 0) {
                killProcessWarning(strFileArray[3], out);
            } else if (strFileArray[1].indexOf("finallyKillRequest") >= 0) {
                killProcess(strFileArray[3], out);
            } else if (strFileArray[1].indexOf("logicalTerminateProcessWarning") >= 0) {
                logicalTerminateProcessWarning(strFileArray[3], out);
            } else if (strFileArray[1].indexOf("finallyLogicallyTerminateRequest") >= 0) {
                logicalTerminateProcess(strFileArray[3], out);
            } else {
                writeNotFoundError(out);
            }
        } catch (Throwable e4)
        {
            if (e4 instanceof Error) {
                // do nothing.
            } else {
            	logger_.log(Level.ERROR, "Error Closing", e4);
            }
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException e)
            {
                // Dummy
            }
            try
            {
                if (out != null)
                {
                    out.close();
                }
            } catch (Exception e)
            {
                // Dummy
            }
            try
            {
                client_.close();
            } catch (IOException e1)
            {
                // Dummy
            }
        }
    }

    /**
     * Kills the process.
     * 
     * @param arg
     * @param psOut
     */
    private void killProcess(String arg, PrintStream psOut) {
        Map<String,String> map = server_.getQuery(arg);
        ProcessRequestEntityBean bean = null;
        String reqId = map.get("reqid");
        String userId = map.get("userid");
        String reason = map.get("reason");
        try {
            bean = CProcessRequestEngine.getInstance().killProcess(reqId, userId, reason);
        } catch (Throwable t) {
        }
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>Terminate Process</title>");
        psOut.println("</head>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<br>");
        psOut
                .println("<TABLE  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" width=\"100%\" align=\"center\">");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Request Id</b></font></td>");
        psOut
                .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Job Name</b></font></td>");
        psOut
                .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Status</b></font></td>");
        psOut
                .println("<td cols=3 align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Note</b></font></td>");
        psOut.println("</tr>");
        psOut.println("<tr bgcolor=\"#F2BC4D\">");
        psOut
        .println("<td align=\"left\">"
                + ((bean == null)? -1 : bean.getReqId()) + "</b></td>");
        psOut
        .println("<td align=\"left\">"
                + ((bean != null)?bean.getJobId():"Unable to retrieve job id")
                + "  "
                + ((bean != null)?bean.getJobName():"Unable to retrieve job name")
                + "</b></td>");
        psOut
                .println("<td align=\"left\">Killed</td>");
        psOut
                .println("<td cols=3 align=\"left\">The process that PRE has spawned has been killed. "
                        + "Please note that underlying process if had called any other process or given a call to any other server process such as database procedure/function/package "
                        + "or reports, the corresponding process may still be active and running.</td>");
        psOut.println("</tr>");
        psOut.println("</table>");
        psOut.println("<br>");
        psOut.println("");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
        psOut.println("<center>");
        psOut
                .println("<tr align=\"center\"><td ><a href=\"#\" onClick=\"Javascript:window.close();\">Close</a></td></tr>");
        psOut.println("</center>");
        psOut.println("</body>");
        psOut.println("</html>");
    }

    /**
     * Logically tries to terminate the process.
     * The URL accepts the<code>&reqid=""&userid=""&reason=""</code>.
     * @param arg
     * @param psOut
     */
    private void logicalTerminateProcess(String arg, PrintStream psOut) {
        Map<String,String> map = server_.getQuery(arg);
        String reqId =  map.get("reqid");
        String userId = map.get("userid");
        String reason = map.get("reason");
        
        String strMessage = "Unable to invoke stop. Process may have terminated on its own. If you do see the process running, please invoke kill.";
        boolean bStatus = false;
        try {
            bStatus  = CProcessRequestEngine.getInstance().invokeStopProcess(reqId, userId, reason);
            strMessage = "Stop was sucessfully invoked. This does not garuntee that the JOB will stop itself unless it is able to do so. Wait for some time " +
            		"and let the JOB try to terminate itself. If you feel that it is not terminating, please invoke kill.";
        } catch (Throwable t) {
            strMessage = t.getMessage();
        }
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>Terminate Process</title>");
        psOut.println("</head>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<br>");
        psOut
        .println("<TABLE  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" width=\"100%\" align=\"center\">");
        psOut.println("<tr>");
        psOut
        .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Status</b></font></td>");
        psOut
        .println("<td cols=3 align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Note</b></font></td>");
        psOut.println("</tr>");
        psOut.println("<tr bgcolor=\"#F2BC4D\">");
        psOut
        .println("<td align=\"left\">");
        psOut.println((bStatus==true?"Invoked Stop":"Unable to Invoke Stop"));
        psOut.println("</td>");
        psOut
        .println("<td cols=3 align=\"left\">" + strMessage + "</td>");
        psOut.println("</tr>");
        psOut.println("</table>");
        psOut.println("<br>");
        psOut.println("");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
        psOut.println("<center>");
        psOut
        .println("<tr align=\"center\"><td ><a href=\"#\" onClick=\"Javascript:window.close();\">Close</a></td></tr>");
        psOut.println("</center>");
        psOut.println("</body>");
        psOut.println("</html>");
    }
    
    /**
     * Kills the process.
     * 
     * The URL accepts the<code>&reqid=""&userid=""&reason=""</code>.
     * @param arg
     * @param psOut
     */
    private void killProcessWarning(String arg, PrintStream psOut) {
        Map<String, String> map = server_.getQuery(arg);
        String reqId = map.get("reqid");
        Iterator<CProcessStatusBean> iter = null;
//        try {
            iter = CProcessRequestEngine.getInstance().getProcessStatus();
//        } catch (CProcessRequestEngineException e) {
//            // e.printStackTrace();
//        }
        CProcessStatusBean element = null;
        while (iter.hasNext()) {
            element = iter.next();
            if (reqId.equals(element.getRequestId() + "")) {
                break;
            }
        }
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>Terminate Process Warning</title>");
        psOut.println("</head>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<br>");
        if (element != null) {
            psOut
                    .println("<TABLE  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" width=\"100%\" align=\"center\">");
            psOut.println("<tr>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Request Id</b></font></td>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Job Name</b></font></td>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Action</b></font></td>");
            psOut
                    .println("<td cols=3 align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Note</b></font></td>");
            psOut.println("</tr>");
            psOut.println("<tr bgcolor=\"#F2BC4D\">");
            psOut
                    .println("<td align=\"left\">"
                            + element.getRequestId() + "</b></td>");
            psOut
                    .println("<td align=\"left\">"
                            + element.getProcessName() + "</b></td>");
            psOut
                    .println("<td align=\"left\"><a href=\"/finallyKillRequest?reqid="
                            + element.getRequestId()
                            + "\">Kill</a></td>");
            psOut
                    .println("<td cols=3 align=\"left\">The underlying process if had called any "
                            + "other process or given call to any other server process such as database procedure/function/package "
                            + "or reports, the corresponding process may still be active and running after the kill. This kill will " +
                            		"free up resources from the PRE. Are you sure, you want to kill? If yes, press kill again.</td>");
            psOut.println("</tr>");
            psOut.println("</table>");
        } else {
            psOut
                    .println("The process was not found. Probably got over before it could be killed.");
        }
        psOut.println("<br>");
        psOut.println("");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
        psOut.println("<center>");
        psOut
                .println("<tr align=\"center\"><td ><a href=\"#\" onClick=\"Javascript:window.close();\">Close</a></td></tr>");
        psOut.println("</center>");
        psOut.println("</body>");
        psOut.println("</html>");
    }

    /**
     * Warns the user before the logical stop.
     * 
     * @param arg
     * @param psOut
     */
    private void logicalTerminateProcessWarning(String arg, PrintStream psOut) {
        Map<String,String> map = server_.getQuery(arg);
        String reqId = map.get("reqid");
        Iterator<CProcessStatusBean> iter = null;
//        try {
        iter = CProcessRequestEngine.getInstance().getProcessStatus();
//        } catch (CProcessRequestEngineException e) {
//            // e.printStackTrace();
//        }
        CProcessStatusBean element = null;
        while (iter.hasNext()) {
            element = iter.next();
            if (reqId.equals(element.getRequestId() + "")) {
                break;
            }
        }
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>STOP Process Warning</title>");
        psOut.println("</head>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<br>");
        if (element != null) {
            psOut
            .println("<TABLE  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" width=\"100%\" align=\"center\">");
            psOut.println("<tr>");
            psOut
            .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Request Id</b></font></td>");
            psOut
            .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Job Name</b></font></td>");
            psOut
            .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Action</b></font></td>");
            psOut
            .println("<td cols=3 align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Note</b></font></td>");
            psOut.println("</tr>");
            psOut.println("<tr bgcolor=\"#F2BC4D\">");
            psOut
            .println("<td align=\"left\">"
                    + element.getRequestId() + "</b></td>");
            psOut
            .println("<td align=\"left\">"
                    + element.getProcessName() + "</b></td>");
            psOut
            .println("<td align=\"left\"><a href=\"/finallyLogicallyTerminateRequest?reqid="
                    + element.getRequestId()
                    + "\">Stop</a></td>");
            psOut
            .println("<td cols=3 align=\"left\">The underlying job should implement the interface ITerminateProcess for the "
                    + "PRE to invoke logical stop on the process. Otherwise Stop will not do anything. "
                    + "This is a logical stop and does not garuntee that the process will be able to terminate itself " +
                      "unless, it is capable of stoping itself. Are you sure, you want to invoke STOP? If yes, press Stop again.</td>");
            psOut.println("</tr>");
            psOut.println("</table>");
        } else {
            psOut
            .println("The process was not found. Probably got over before it could be Stopped.");
        }
        psOut.println("<br>");
        psOut.println("");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
        psOut.println("<center>");
        psOut
        .println("<tr align=\"center\"><td ><a href=\"#\" onClick=\"Javascript:window.close();\">Close</a></td></tr>");
        psOut.println("</center>");
        psOut.println("</body>");
        psOut.println("</html>");
    }
    
    /**
     * This method returns the class byte code for a given class name.
     * 
     * @param out
     *            PrintStream.
     * @param pstrFileName
     *            Class Name to be read and sent.
     */
    private void writeClassBytes(PrintStream out, String pstrFileName) {
        CCustomClassLoader classloader = new CCustomClassLoader(this);
        try {
            byte[] bytes = classloader.lookupClassData(pstrFileName);
            out.write(bytes);
        } catch (IOException e) {
            writeNotFoundError(out);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            writeNotFoundError(out);
            e.printStackTrace();
        }
    }

    /**
     * General Message for Page Not Found.
     * 
     * @param psOut
     *            PrintStream
     */
    private void writeNotFoundError(PrintStream psOut) {
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<BR>");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>Page Not Found.</title>");
        psOut.println("</head>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<br>");
        psOut.println("<br>");
        psOut
                .println("<h3><font color=\"#000080\" style=\"font-family: monospace\">"
                        + "Oops !! The page you are looking for is not available with us."
                        + "</font></h3>");
        psOut.println("<br>");
        psOut.println("<br>");
        psOut
                .println("<hr style=\"height: 1px; color: Gray; border: dashed\">");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
    }

    /**
     * Writes about the PRE.
     * 
     * @param psOut
     *            PrintStream to which the response is written.
     */
    private void writeAbout(PrintStream psOut) {
        PREInfo info = CProcessRequestEngine.getInstance().getContext().getPREInfo();
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>About</title>");
        psOut.println("</head>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<br>");
        psOut
                .println("<TABLE  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" width=\"70%\" align=\"center\">");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\" width=\"20%\"><font color=\"#ffffff\"><b>Company</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>Mastek Ltd</b></td>");
        psOut.println("</tr>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\"  width=\"20%\"><font color=\"#ffffff\" ><b>Product</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>"
                        + info.getName() + "</b></td>");
        psOut.println("</tr>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\"  width=\"20%\"><font color=\"#ffffff\" ><b>Version</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>"
                        + info.getVersion() + "</b></td>");
        psOut.println("</tr>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\"  width=\"20%\"><font color=\"#ffffff\" ><b>Packaged On</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>"
                        + info.getBundledOn() + "</b></td>");
        psOut.println("</tr>");
        psOut.println("<tr>");
        psOut
        .println("<td align=\"center\" bgcolor=\"eb922c\"  width=\"20%\"><font color=\"#ffffff\" ><b>Build Number</b></font></td>");
        psOut
        .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>"
        		+ info.getBuildNumber() + "</b></td>");
        psOut.println("</tr>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\"width=\"20%\"><font color=\"#ffffff\" ><b>Concept & Designed</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>Kedar C. Raybagkar</b></td>");
        psOut.println("</tr>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\" width=\"20%\"><font color=\"#ffffff\" ><b>Web Designing</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>Samir Narale</b></td>");
        psOut.println("</tr>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\"width=\"20%\"><font color=\"#ffffff\"><b>Web Site</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>http://www.majescomastek.com</b></td>");
        psOut.println("</tr>");
        psOut.println("</tr>");
        psOut.println("</table>");
        psOut.println("<br>");
        psOut
                .println("<h3><font color=\"eb922c\" style=\"font-family: monospace\"><b>Total Web Requests Serviced So Far :</b><i>"
                        + server_.getRequestServicedCounter()
                        + "</i></font></h3>");
        psOut.println("");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
        psOut.println("<center>");
        psOut
                .println("<tr align=\"center\"><td ><a href=\"#\" onClick=\"Javascript:window.close();\">Close</a></td></tr>");
        psOut.println("</center>");
        psOut.println("</body>");
        psOut.println("</html>");
    }

    /**
     * Writes PRE information regarding the Runtime memory, Processes and JDBC
     * statistics.
     * 
     * @param psOut
     *            PrintStream to which the response is written.
     */
    private void writePREInfo(PrintStream psOut) {
        try {
            Runtime runtime = Runtime.getRuntime();
            // The following line has to be in one single out.println() as this
            // goes as the header.
            psOut.println("HTTP/1.0 200 Document follows\r\n"
                    + "Server: ProcessRequestEngine \r\n"
                    + "Content-type: text/html\r\n");
            psOut.println("<HTML>");
            psOut.println("<head>");
            psOut.println("<META HTTP-EQUIV=\"REFRESH\" CONTENT=\""
                    + CSettings.get("pr.webpagerefresh", "5") + "\">");
            psOut.println("<title>Process Request Engine Information</title>");
            psOut.println("<script>");
            psOut.println("function ProcessStatus(){");
            psOut
                    .println("	var win=window.open(\"/processstatus\",\"ProcessStatus\",\"status=no,resize=yes,toolbar=no,scrollbars=yes,width=900,height=300,maximize=null\");");
            psOut.println("}");
            psOut.println("function ShowProperty(strKey){");
            psOut
                    .println("	var win=window.open(\"/properties/\"+strKey,\"strKey\",\"status=no,resize=yes,toolbar=no,scrollbars=yes,width=800,height=650,maximize=yes\");");
            psOut.println("}");
            psOut.println("function About(){");
            psOut
                    .println("var win=window.open(\"/about\",\"About\",\"status=no,resize=yes,toolbar=no,scrollbars=no,width=570,height=360\");");
            psOut.println("}");
//            psOut.println("function LicenseInfo(){");
//            psOut
//            .println("var win=window.open(\"/licenseinfo\",\"About\",\"status=no,resize=yes,toolbar=no,scrollbars=yes,width=570,height=360,maximize=yes\");");
//            psOut.println("}");
            psOut.println("</script>");
            psOut.println("</head>");
            psOut.println("<body bgcolor=\"#FFF9AF\">");
            psOut.println("<h1>Currently Running Jobs</h1>");
            psOut
                    .println("<TABLE title=\"Running Jobs\" bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" width=\"80%\" align=\"center\">");
            psOut.println("<tr>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Engine</b></font></td>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Running</b></font></td>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Stuck Threads</b></font></td>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Stuck Threads Crossed Max Limit</b></font></td>");
            psOut.println("</tr>");
            psOut.println("<tr  bgcolor=\"#F2BC4D\">");
            int iRunningSAProcess = CProcessRequestEngine.getInstance()
                    .getCurrentRunningStandAloneProcess();
            int iStuckThreads = CProcessRequestEngine.getInstance()
                    .getStuckThreads();
            int iSTML = CProcessRequestEngine.getInstance()
                    .getStuckThreadsThatCrossedMaxLimit();
            if (iRunningSAProcess + iStuckThreads + iSTML > 0) {
                psOut
                        .println("<td align=\"center\"><a href=\"#\" onClick=\"ProcessStatus()\" title=\"Job Status\">StandAlone</a></td>");
            } else {
                psOut.println("<td align=\"center\">StandAlone</td>");
            }
            psOut
                    .println("<td align=\"center\">" + iRunningSAProcess
                            + "</td>");
            psOut.println("<td align=\"center\">" + iStuckThreads + "</td>");
            psOut.println("<td align=\"center\">" + iSTML + "</td>");
            psOut.println("</tr>");
            psOut.println("<tr  bgcolor=\"#F2BC4D\">");
            psOut.println("<td align=\"center\">Grouped</td>");
            psOut.println("<td align=\"center\">"
                    + CProcessRequestEngine.getInstance()
                            .getCurrentRunningGroupedProcess() + "</td>");
            psOut.println("<td align=\"center\">Not Supported</td>");
            psOut.println("<td align=\"center\">Not Supported</td>");
            psOut.println("</tr>");
            psOut.print("</table>");
            psOut.println("<h1>Memory Utilization</h1>");
            psOut
                    .println("<TABLE bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" title=\"Memory Utilization\" width=\"80%\" align=\"center\">");
            psOut.println("<tr>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Maximum</b></font></td>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Total</b></font></td>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Used</b></font></td>");
            psOut
                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Free</b></font></td>");
            psOut.println("</tr>");
            psOut.println("<tr bgcolor=\"#F2BC4D\">");
            long lMaxMemory = -1;
            try {
                lMaxMemory = runtime.maxMemory();
            } catch (NoSuchMethodError e) {
                // Nothing to Do..
            }
            long lTotalMemory = runtime.totalMemory();
            long lFreeMemory = runtime.freeMemory();
            psOut.println("<td align=\"center\">"
                    + ((lMaxMemory == -1) ? "Not Supported" : lMaxMemory + "")
                    + "</td>");
            psOut.println("<td align=\"center\">" + lTotalMemory + "</td>");
            psOut.println("<td align=\"center\">"
                    + (lTotalMemory - lFreeMemory) + "</td>");
            psOut.println("<td align=\"center\">" + lFreeMemory + "</td>");
            psOut.println("</tr>");
            psOut.println("</TABLE>");
//            psOut.println("<h1>JDBC Pool Statistics</h1>");
//            psOut
//                    .println("<table  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" title=\"JDBC Pool\" width=\"80%\" align=\"center\">");
//            psOut.println("<tr>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Pool Name</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Ini Cap</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Max Cap</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Cap Incr</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Con High</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Con Bad</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Con Delay (Avg MS)</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Con In Use</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Con Free</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Con Leaks</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Leak Statement(s)</b></font></td>");
//            psOut
//                    .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Leak ResultSet(s)</b></font></td>");
//            psOut.println("</tr>");
//            for (Iterator iter = CConnectionPoolManager.getInstance()
//                    .getAllPoolStatistics(); iter.hasNext();) {
//                CPoolStatisticsBean cpsb = (CPoolStatisticsBean) iter.next();
//                CPoolAttribute cpa = CConnectionPoolManager.getInstance()
//                        .getPoolAttributes(cpsb.getPoolName());
//                psOut.println("<tr bgcolor=\"#F2BC4D\">");
//                psOut.println("<td align=\"center\">" + cpsb.getPoolName()
//                        + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpa.getInitialPoolSize() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpa.getMaximumCapacity() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpa.getCapacityIncreament() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getConnectionsHighCount() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getBadConnectionCount() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getAverageConnectionDelayTimeInMillis()
//                        + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getCurrentUsedConnectionCount() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getCurrentFreeConnectionCount() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getLeakedConnectionCount() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getLeakedStatementCount() + "</td>");
//                psOut.println("<td align=\"center\">"
//                        + cpsb.getLeakedResultSetCount() + "</td>");
//                psOut.println("</tr>");
//            }
//            psOut.println("</TABLE>");
            printPropertiesLinks(psOut);
            psOut.println("<br>");
            psOut.println("<TABLE width=\"100%\">");
            psOut.println("<tr>");
            psOut.println("<td width=\"80%\">");
            psOut
                    .println("Copyright &copy; MajescoMastek.");
            psOut.println("This site is under construction.");
            psOut.println("</td>");
//            psOut.println("<td width=\"10%\">");
//            psOut
//            .println("<a href=\"#\" onClick=\"LicenseInfo();\"><font color=\"#000080\" style=\"font-family: monospace\">License</font></a>");
//            psOut.println("</td>");
            psOut.println("<td width=\"10%\">");
            psOut
                .println("<a href=\"#\" onClick=\"About();\"><font color=\"#000080\" style=\"font-family: monospace\">about</font></a>");
            psOut.println("</td>");
            psOut.println("</tr>");
            psOut.println("</TABLE>");
            psOut.println("</BODY>");
            psOut.println("</HTML>");
        } catch (RuntimeException re) {
            logger_.log(Level.ERROR, "Runtime Error caught...", re);
        } catch (Exception e) {
            logger_.log(Level.ERROR, "Exception caught...", e);
        } catch (Error e) {
            logger_.log(Level.ERROR, "Error caught...", e);
        }
    }

    /**
     * Prints the links for property files.
     * 
     * @param psOut
     *            Print Stream
     */
    private void printPropertiesLinks(PrintStream psOut) {
        if (sbufferPropertyLinks_ == null) {
            sbufferPropertyLinks_ = new StringBuffer();
            sbufferPropertyLinks_.append("<h1>Properties</h1>\n");
            sbufferPropertyLinks_
                    .append("<table  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" title=\"Engine Property Files\" width=\"80%\" align=\"center\">\n");
            sbufferPropertyLinks_.append("<tr>\n");
            sbufferPropertyLinks_
                    .append("<td align=\"center\" bgcolor=\"eb922c\" colspan=\"1\"><font color=\"#ffffff\"><b>File Identifier</b></font></td>\n");
            sbufferPropertyLinks_
                    .append("<td align=\"center\" bgcolor=\"eb922c\" colspan=\"1\"><font color=\"#ffffff\"><b>Property File</b></font></td>\n");
            sbufferPropertyLinks_.append("</tr>\n");
            Enumeration<Object> enumer = CSettings.getInstance()
                    .getLoadedPropertyIndexes();
            while (enumer.hasMoreElements()) {
                String strIndex = (String) enumer.nextElement();
                if (strIndex.endsWith("sourceType")) {
                    String strKey = strIndex.substring(
                            strIndex.indexOf(".") + 1, strIndex
                                    .lastIndexOf("."));
                    String strValue = CSettings.get("include." + strKey
                            + ".sourceParam");
                    if (strValue.length() > 120) {
                        strValue = "..."
                                + strValue.substring(strValue.length() - 120);
                    }
                    sbufferPropertyLinks_.append("<tr>\n");
                    sbufferPropertyLinks_
                            .append("<td align=\"center\"  bgcolor=\"#F2BC4D\">\n");
                    sbufferPropertyLinks_
                            .append("<a href=\"#\" onClick=\"ShowProperty('"
                                    + strKey + "');\">" + strKey + "</a>\n");
                    sbufferPropertyLinks_
                            .append("<td align=\"left\"  bgcolor=\"#F2BC4D\">\n");
                    sbufferPropertyLinks_
                            .append("<a href=\"#\" onClick=\"ShowProperty('"
                                    + strKey + "');\">" + strValue + "</a>\n");
                    sbufferPropertyLinks_.append("</td>\n");
                    sbufferPropertyLinks_.append("</tr>\n");
                }
            } // end of while
            sbufferPropertyLinks_.append("</tr>\n");
            sbufferPropertyLinks_.append("</TABLE>\n");
        }
        psOut.print(sbufferPropertyLinks_.toString());
    }

    /**
     * Shows the status of each running process.
     * 
     * @param psOut
     *            PrintStream
     * @throws CProcessRequestEngineException
     */
    public void writeProcessStatus(PrintStream psOut)
            throws CProcessRequestEngineException {
        Iterator<CProcessStatusBean> iter = CProcessRequestEngine.getInstance().getProcessStatus();
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>Request Status Information</title>");
        psOut.println("</head>");
        psOut.println("<script>");
//        psOut.println("}");
        psOut.println("</script>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<center>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"right\"><a href=\"#\"onClick=\"window.close()\">Close</a></td>");
        psOut.println("</tr>");
        psOut.println("</center>");
        psOut.println("<h2>Currently Running Job Status</h2>");
        if (iter.hasNext()) {
            psOut
                    .println("<TABLE  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" width=\"100%\" align=\"center\">");
            psOut.println("<tr>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Request Id</b></font></td>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Job Name</b></font></td>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Start Time</b></font></td>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Thread Status</b></font></td>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Status</b></font></td>");
            psOut
                    .println("<td align=\"left\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Action</b></font></td>");
            psOut.println("</tr>");
            for (; iter.hasNext();) {
                CProcessStatusBean element = iter.next();
                psOut.println("<tr  bgcolor=\"#F2BC4D\">");
                psOut.println("<td align=\"left\">" + element.getRequestId()
                        + "</a></td>");
                psOut.println("<td align=\"left\">"
                        + SpecialString.toTitleCase(element.getProcessName())
                        + "</td>");
                psOut.println("<td align=\"left\">"
                        + new Date(element.getStartTime()) + "</td>");
                psOut.println("<td align=\"left\">"
                        + SpecialString.toTitleCase(element.getThreadStatus())
                        + "</td>");
                String str = element.getStatus();
//                if (str.length() > 68) {
//                    str = str.substring(0, 68) + "..";
//                }
                psOut.println("<td align=\"left\">" + str + "</td>");
                // psOut.println("<td align=\"left\"><a href=\"" +
                // element.getRequestId() + "\" onClick=\"killProcess('" +
                // element.getRequestId() + "');\">Kill</a></td>");
                psOut.println("<td align=\"left\">");
                psOut
                        .println("<a href=\"/killProcessWarning?reqid="
                                + element.getRequestId() + "\">Kill</a>");
                psOut
                .println("<a href=\"/logicalTerminateProcessWarning?reqid="
                        + element.getRequestId() + "\">Stop</a>");
                psOut.println("</td>");
                psOut.println("</tr>");
            }
            psOut.println("</TABLE>");
        } else {
            psOut.println("<BR>");
            psOut.println("<BR>");
            psOut
                    .println("<h2>We are sorry. Currently there are no running jobs.</h2>");
        }
        psOut.println("<br>");
        psOut.println("<br>");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
        psOut.println("<center>");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"right\"><a href=\"#\"onClick=\"window.close()\">Close</a></td>");
        psOut.println("</tr>");
        psOut.println("</center>");
        psOut.println("</BODY>");
        psOut.println("</HTML>");
    }

    /**
     * Creates an HTML for the properties loaded by the engine.
     * 
     * @param psOut
     *            PrintStream.
     * @param strPropertyType
     *            Property Type requested for.
     */
    private void writeProperties(PrintStream psOut, String strPropertyType) {
        Enumeration<?> enumer = CSettings.getInstance().getEnumeration(
                strPropertyType);
        if (enumer == null) {
            writeNotFoundError(psOut);
            return;
        }
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/html\r\n");
        psOut.println("<HTML>");
        psOut.println("<head>");
        psOut.println("<title>Process Request Engine Information</title>");
        psOut.println("</head>");
        psOut.println("<body bgcolor=\"#FFF9AF\">");
        psOut.println("<h1>" + strPropertyType + "</h1>");
        psOut
                .println("<table  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" title=\"Properties\" width=\"80%\" align=\"center\">");
        psOut.println("<tr>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Key</b></font></td>");
        psOut
                .println("<td align=\"center\" bgcolor=\"eb922c\"><font color=\"#ffffff\"><b>Value</b></font></td>");
        psOut.println("</tr>");
        List<String> encryptList = new ArrayList<String>();
        StringTokenizer stoken = new StringTokenizer(CSettings.get(
                strPropertyType + ".encryptedproperties", ""), ",");
        while (stoken.hasMoreElements()) {
            String strProperty = (String) stoken.nextElement();
            encryptList.add(strProperty);
        }
        while (enumer.hasMoreElements()) {
            String strElement = (String) enumer.nextElement();
            String strValue = CSettings.get(strPropertyType + "." + strElement,
                    "value not set");
            if (encryptList.contains(strElement)) {
                strValue = "***********";
            }
            psOut.println("<tr bgcolor=\"#F2BC4D\">");
            psOut.println("<td align=\"left\" width=\"30%\">" + strElement
                    + "</td>");
            psOut.println("<td align=\"left\">" + strValue + "</td>");
            psOut.println("</tr>");
        }
        psOut.println("</table>");
        psOut.println("<br>");
        psOut.println("Copyright &copy; MajescoMastek.");
        psOut.println("This page is under construction.");
        psOut.println("</BODY>");
        psOut.println("</HTML>");
    }

//    /**
//     * Writes a text output to the given print stream object with the JDBCPool
//     * Statistics.
//     * 
//     * @param psOut
//     *            PrintStream
//     */
//    private void writeMemoryUtilizationForGraph(PrintStream psOut) {
////        CConnectionPoolManager manager = CConnectionPoolManager.getInstance();
////        CPoolStatisticsBean statsBean = manager.getPoolStatistics(CSettings.get("pr.dsforstandaloneeng"));
////        CPoolAttribute attribBean = manager.getPoolAttributes(CSettings.get("pr.dsforstandaloneeng"));
////        psOut.println(statsBean.toString());
////        psOut.println("Maximum Capacity," + attribBean.getMaximumCapacity());
//    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.customclassloader.IPREClassLoaderClient#getCustomClassLoaderClassPath()
     */
    public String getCustomClassLoaderClassPath() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.customclassloader.IPREClassLoaderClient#getSystemLoadedClasses()
     */
    public String[] getSystemLoadedClasses() {
        return new String[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.customclassloader.IPREClassLoaderClient#isReload()
     */
    public boolean isReload() {
        return false;
    }

    /**
     * PRE status.
     * 
     * @param query
     * @param psOut
     * @throws CProcessRequestEngineException
     */
    public void writePREStatus(String query, PrintStream psOut)
            throws CProcessRequestEngineException {
//        Map<String, String> map = server_.getQuery(query);
        psOut.println("HTTP/1.0 200 Document follows\r\n"
                + "Server: ProcessRequestEngine \r\n"
                + "Content-type: text/ascii\r\n");
//        String strRequestValue = "";
        String strResponseValue = "UNKNOWN";
//        if (map.containsKey("presignature")) {
//            strRequestValue = (String) map.get("presignature");
//            strResponseValue = CProcessRequestEngine.getInstance().getPRESignature();
//        } else if (map.containsKey("queuesignature")) {
//            strRequestValue = (String) map.get("queuesignature");
//            strResponseValue = CProcessRequestEngine.getInstance().getQueueSignature();
//        } else if (map.containsKey("filtersignature")) {
//            strRequestValue = (String) map.get("filtersignature");
//            strResponseValue = CProcessRequestEngine.getInstance().getFilterSignature();
//        } else {
            psOut.println(strResponseValue);
            return;
//        }
//        PBEEncryptionRoutine routine = new PBEEncryptionRoutine();
//        if (routine.decode(strRequestValue).equals(strResponseValue)) {
//            psOut.println("EQUALS");
//        } else {
//            psOut.println("MISMATCH");
//        }
    }

    /**
     * Stops the Engine.
     * 
     * @param query
     *            parameter passed.
     * 
     * @param psOut
     * @throws CProcessRequestEngineException
     */
    public void stopEngine(String query, PrintStream psOut)
            throws CProcessRequestEngineException {
//        PrintWriter pwOut = new PrintWriter(psOut);
//        if (query.equals(CProcessRequestEngine.getInstance().getPRESignature())) {
//            pwOut.println("HTTP/1.0 200 Document follows\r\n"
//                    + "Server: ProcessRequestEngine \r\n"
//                    + "Content-type: text/ascii\r\n");
//            pwOut.println("STOPPED");
//            pwOut.close();
//            logger_.log(LogLevel.NOTICE,
//                    "Stopping the PRE as requested from the clustered PRE");
//            CStopEngine obj = new CStopEngine();
//            obj.setResponseWriter(new PrintWriter(new ByteArrayOutputStream()));
//            CProcessRequestEngine.getInstance().stopEngine(obj);
//        }
    }

    /**
     * Reboots the Engine.
     * 
     * @param psOut
     * @throws CProcessRequestEngineException
     */
    public void rebootEngine(PrintStream psOut)
            throws CProcessRequestEngineException {
        PrintWriter pwOut = new PrintWriter(psOut);
        CRebootEngine obj = new CRebootEngine();
        obj.setResponseWriter(pwOut);
        CProcessRequestEngine.getInstance().stopEngine(obj);
    }

    /**
     * Writes the license information to the given print stream.
     * @param psOut PrintStream
     */
//    public void writeLicenseInfo(PrintStream psOut) {
//        psOut.println("HTTP/1.0 200 Document follows\r\n"
//                + "Server: ProcessRequestEngine \r\n"
//                + "Content-type: text/html\r\n");
//        psOut.println("<HTML>");
//        psOut.println("<head>");
//        psOut.println("<title>License Info</title>");
//        psOut.println("</head>");
//        psOut.println("<body bgcolor=\"#FFF9AF\">");
//        psOut.println("<br>");
//        LicenseContent lc = null;
//        try {
//            final LicenseManager manager = new LicenseManager(new LicenseParamImpl());
//            lc = manager.verify();
//        } catch (LicenseContentException lce) {
//            psOut.println("<h2>" + lce.getLocalizedMessage() + "</h2>");
//            psOut.println("</body>");
//            psOut.println("</html>");
//            return;
//        } catch (Exception e) {
//            psOut.println("<h2>" + e.getLocalizedMessage() + "</h2>");
//            psOut.println("</body>");
//            psOut.println("</html>");
//            return;
//        }
//        
//        PREInfo info = CProcessRequestEngine.getInstance().getBundleDetails();
//        psOut
//                .println("<TABLE  bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" width=\"80%\" align=\"center\">");
//        psOut.println("<tr>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"eb922c\" width=\"30%\"><font color=\"#ffffff\"><b>Company</b></font></td>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>MajescoMastek</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"eb922c\"  width=\"30%\"><font color=\"#ffffff\" ><b>Product</b></font></td>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>"
//                        + info.getName() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"eb922c\"  width=\"30%\"><font color=\"#ffffff\" ><b>Version</b></font></td>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"40%\"><b>"
//                        + info.getVersion() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"eb922c\"  width=\"30%\"><font color=\"#ffffff\" ><b>Packaged On</b></font></td>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>"
//                        + info.getBundledOn() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"eb922c\"  width=\"30%\"><font color=\"#ffffff\" ><b>Build Number</b></font></td>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>"
//        		+ info.getBuildNumber() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"eb922c\"width=\"30%\"><font color=\"#ffffff\" ><b>Issued To</b></font></td>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>" + lc.getHolder().getName() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"eb922c\"width=\"30%\"><font color=\"#ffffff\" ><b>Issued By</b></font></td>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>" + lc.getIssuer().getName() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"eb922c\" width=\"30%\"><font color=\"#ffffff\" ><b>Not Valid Before</b></font></td>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>" + lc.getNotBefore() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"eb922c\" width=\"30%\"><font color=\"#ffffff\" ><b>Not Valid After</b></font></td>");
//        psOut
//                .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>" + lc.getNotAfter() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"eb922c\" width=\"30%\"><font color=\"#ffffff\" ><b>Parellel Process</b></font></td>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>" + lc.getConsumerAmount() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("<tr>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"eb922c\" width=\"30%\"><font color=\"#ffffff\" ><b>Usage</b></font></td>");
//        psOut
//        .println("<td align=\"left\" bgcolor=\"#F2BC4D\" width=\"50%\"><b>" + lc.getInfo() + "</b></td>");
//        psOut.println("</tr>");
//        psOut.println("</tr>");
//        psOut.println("</table>");
//        psOut.println("<br>");
//        psOut
//                .println("<h3><font color=\"eb922c\" style=\"font-family: monospace\"><b>Total Web Requests Serviced So Far :</b><i>"
//                        + server_.getRequestServicedCounter()
//                        + "</i></font></h3>");
//        psOut.println("");
//        psOut.println("Copyright &copy; MajescoMastek.");
//        psOut.println("This page is under construction.");
//        psOut.println("<center>");
//        psOut
//                .println("<tr align=\"center\"><td ><a href=\"#\" onClick=\"Javascript:window.close();\">Close</a></td></tr>");
//        psOut.println("</center>");
//        psOut.println("</body>");
//        psOut.println("</html>");
//    }
    
}