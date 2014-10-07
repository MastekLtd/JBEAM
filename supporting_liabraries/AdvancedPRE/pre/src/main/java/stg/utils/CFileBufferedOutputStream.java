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
*
* $Revision: 2382 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/CFileBufferedOutputStream.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/CFileBufferedOutputStream.java $
 * 
 * 7     2/04/09 1:08p Kedarr
 * Added static keyword to a final variable.
 * 
 * 6     4/07/08 5:05p Kedarr
 * Added/changed the REVISION variable to public.
 * 
 * 5     3/22/08 12:31a Kedarr
 * Added REVISION variable.
 * 
 * 4     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.4  2004/05/03 06:26:48  kedar
* Un used method/variables commented out.
*
 * 
 * 2     12/09/03 9:39p Kedarr
 * Removed UnUsed variables where ever possible and made
 * the necessary changes.
* Revision 1.3  2003/12/09 16:02:08  kedar
* Removed UnUsed variables where ever possible and made
* the necessary changes.
*
 * 
 * 1     11/03/03 12:01p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:40  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 8  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:22p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changes made for $Revision: 2382 $ in the javadoc.
 * 
 * *****************  Version 7  *****************
 * User: Kedarr       Date: 23/01/03   Time: 12:40p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changed the way the file was getting saved. This is reduces the processing
 * time from the earlier logic used. The file will will get generated as
 * FileName + yyyyMMddHHmmss + .extension specified in the properties.
 * 
 * *****************  Version 5  *****************
 * User: Kedarr       Date: 20/01/03   Time: 10:25a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changes made for save method for synchronization
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 23/12/02   Time: 5:07p
 * Updated in $/DEC18/ProcessRequestEngine/gmac/utils
 * A Backup file is created and the log file is again initialized.
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:58p
 * Created in $/ProcessRequestEngine/gmac/utils
 * Initial Version
*
*/


package stg.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

/**
* This class overrides the methods of OutputStream and writes a data to 
* a {@link java.lang.StringBuffer}. This program can also save the log in
* a file.
*
* @version $Revision: 2382 $
* @author   Kedar C. Raybagkar
* @deprecated Will be removed in the next version of PRE.
**/
public class CFileBufferedOutputStream extends OutputStream
{
    
    
    //public instance constants and class constants
    
    //public instance variables
    
    //public class(static) variables
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    
    //protected instance constants and class constants
    
    //protected instance variables
    
    //protected class(static) variables
    
    
    //package instance constants and class constants
    
    //package instance variables
    
    //package class(static) variables
    
    
    //private instance constants and class constants
    
    //private instance variables
    
    private StringBuffer buffer_;
    
    private File file_;
    
    private int iFileSize_;

    private int size_; 
    
    
    private boolean bFileToBeAppended_;
    
    //private class(static) variables
    
    
    //constructors
    

    /**
     * Constructs an Object of CFileBufferedOutputStream.
     *
     * Allows the user to create his/her own buffer size. The file size
     * is defaulted to 500KB and buffer size  is defaulted to 32KB. The
     * file created is always in append mode.
     *  
     * @param   pfile  File to be created.
     */
    public CFileBufferedOutputStream(File pfile)
    {
        this(pfile, 500 * 1024, 32 * 1024, true);
    }
    

    /**
     * Constructs an Object of CFileBufferedOutputStream. If the size of the file
     * specified is less than zero then the file size is defaulted to unlimited and
     * the parameter pbAppend is defaulted to true regardless of what is passed. The
     * buffer size is is defaulted to 32KB. The file created is always in append 
     * mode.
     *
     * Allows the user to specify file size.
     * @param   pfile  File to be created. If the size is set less than zero, then
     * it is defaulted to unlimited.
     * @param   plFileSize  File size.
     */
    public CFileBufferedOutputStream(File pfile, int plFileSize)
    {
        this(pfile, plFileSize, 32 * 1024);
    }


    /**
     * Constructs an Object of CFileBufferedOutputStream.  If the size of the file
     * specified is less than zero then the file size is defaulted to unlimited.
     * By default the file is in append mode.
     * 
     * @param pfile File to be created.
     * @param plFileSize Size of the file.
     * @param plBufferSize Size of the buffer.
     */
    public CFileBufferedOutputStream(File pfile, int plFileSize, int plBufferSize)
    {
        this(pfile, plFileSize, plBufferSize, true);
    }

    /**
     * Constructs an Object of CFileBufferedOutputStream. If the size of the file
     * specified is less than zero then the file size is defaulted to unlimited and
     * the parameter pbAppend is defaulted to true regardless of what is passed.
     * 
     * @param pfile File to be created.
     * @param plFileSize Size of the file.
     * @param plBufferSize Size of the buffer.
     * @param pbAppend To be appended.
     */
    public CFileBufferedOutputStream(File pfile, int plFileSize, int plBufferSize, boolean pbAppend)
    {
        this.size_ = plBufferSize;
        file_ = pfile;
        iFileSize_ = plFileSize;
        buffer_ = new StringBuffer(size_);
        bFileToBeAppended_ = ((iFileSize_ < 1)? true : pbAppend);
    }

    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order
    

	/*
	 * Overrides the toString() method of Object.
	 *
	 * @return String
	 */
    public String toString()
    {
        return(buffer_.toString());
    }
    

	/*
	 * Writes the specified byte to this byte array output stream.
	 *
	 * @param   b byte array to be written  
	 */
    public void write(byte[] b)
    {
        checkBuffer(b);
        buffer_.append(Arrays.toString(b));
    }
    

	/*
	 * Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
	 *
	 * @param   b
	 * @param   off  
	 * @param   len  
	 */
    public void write(byte[] b, int off, int len)
    {
        checkBuffer(b);
        buffer_.append(new String(b, off, len));
    }
    

	/*
	 * Writes the specified byte to this output stream.
	 *
	 * @param   i  
	 */
    public void write(int i)
    {
        checkBuffer(4);
        buffer_.append(i);
    }
    

	/**
	 * Re-Creates the buffer_.
	 *
	 */
    public void reset()
    {
        buffer_ = new StringBuffer(size_);
//        buffer_.append(getDateTime());
//        buffer_.append("| Debuger Re-Set | ");
    }
    
    public void close() throws IOException
    {
        save();
        super.close();
    }

    //protected constructors and methods of the class
    

    
    //package constructors and methods of the class
    
    //private constructors and methods of the class
    
//    private String getDateTime()
//    {
//        return new Date().toString();
//    }
    
    private void checkBuffer(byte[] b)
    {
        checkBuffer(b.length);
    }
    
    private synchronized void checkBuffer(int i)
    {
        if (buffer_.length() + i > size_)
        {
            trunc();
        }
//        if ((buffer_.length() > 1) && (buffer_.charAt(buffer_.length() - 1)) == '\n')
//        {
//            buffer_.append(getDateTime());
//            buffer_.append("| ");
//        }
    }
    
    private void trunc()
    {    
        save();
        buffer_.delete(0, buffer_.length());
//        buffer_.delete(0, buffer_.length() / 2);
    }
    
    /**
     * This method does nothing. Override this method for saving
     * the buffer so far writen by this class.
     *
     */
    private void save()
    {
        if (file_ != null)
        {
            try
            {
                if (file_.exists() && bFileToBeAppended_ && iFileSize_ > 0)
                {
                    long lFileSize = file_.length();
                    if (lFileSize + (buffer_.length()) > iFileSize_)
                    {
                        //delete the file
                        String strFileName = file_.getName();
                        String strFilePath = file_.getPath();
                        String strExtension = ".log"; 
                        if (strFilePath.lastIndexOf(File.separator) >= 0)
                        {
                            strFilePath = strFilePath.substring(0, strFilePath.lastIndexOf(File.separator)+1);
                        }
                        File fileToBeRemamed = null;
                        if (strFileName.lastIndexOf(".") >= 0)
                        {
                            strExtension    = strFileName.substring(strFileName.lastIndexOf("."));
                            strFileName     = strFileName.substring(0, strFileName.lastIndexOf("."));
                        }
                        strFileName = strFileName + CDate.getUDFDateString(new Date(), "yyyyMMddHHmmss");
                        fileToBeRemamed = new File (strFilePath + strFileName + strExtension);
                        if (!file_.renameTo(fileToBeRemamed)) {
                            System.out.println("Unable to rename the file");
                        }
                    }
                }
                PrintWriter out = null;
                try
                {
                    out = new PrintWriter(new BufferedWriter(new FileWriter(file_.getPath(), bFileToBeAppended_)));
                    out.write(buffer_.toString());
//                    out.println();	//This line used to add an un-necessary new line character.
                }
                catch (SecurityException se)
                {
                    se.printStackTrace();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
                finally
                {
                    if (out != null) out.close();
                }
            }
            catch (SecurityException se)
            {
            }
        }
    }
    
} //end of CFileBufferedOutputStream.java