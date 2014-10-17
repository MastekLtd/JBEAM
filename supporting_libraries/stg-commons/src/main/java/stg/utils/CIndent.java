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

import java.text.StringCharacterIterator;
import java.text.CharacterIterator;

/**
 * This class was written to automate the process of indenting the lines of a code.
 * 
 * The indentation is increased if the last character is an open brace bracket '{' 
 * and decreased if the last character is an closed brace bracket '}'. The iIndent of 
 * the line is kept constant if the last two characters are open & closed brace 
 * brackets '{}'. 
 * 
 * @author kedarr
 * @version $Revision: 1 $
 *
 */
public class CIndent
{
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 1                 $";
    
    /**
     * String Character Iterator to iterate through the line in search of 
     * braces.
     */
    private StringCharacterIterator sci_;

    /**
     * This variable stores the indentation counter.
     */
    private int iIndent_ = 0;

    /**
     * The variable stores the size of the indentation which is desired. 
     */
    private int iFixedIndent_ = 4;

    /**
     * The variable stores the string that will be prefixed to each line depending
     * on the size of the indentation required.
     */
    private StringBuffer sbufIndentWith_;

    /**
     * This variable is set to TRUE if the /* comment has started. 
     */
    private boolean bStarCommentStarted_ = false;

    /**
     * This variable is set to TRUE if a // comment is found on the line.
     */
    private boolean bSlashCommentFound_ = false;

    /**
     * This variable is set to TRUE if a ' is found while iterating the line
     * through charater iterator.
     */
    private boolean bSingleQuotesStarted_ = false;

    /**
     * This variable is set to TRUE if a " is found while iterating the line
     * through character iterator.
     */
    private boolean bDoubleQuotesStarted_ = false;

    /**
     * This variable is set to TRUE if a / is found.
     */
    private boolean bSlashFound_ = false;

    /**
     * This variable is set to TRUE if a * is found.
     */
    private boolean bStarFound_ = true;

    /**
     * This variable is set to TRUE if the line is commented.
     */
    private boolean bLineCommented_ = false;
    
    /**
     * Variable stores the Escape character '\' 
     */
    private int iEscapeChar = 92;
    
    /**
     * Debug comments are added in the file with // comment style 
     * if this variable is set to true.
     */
    private boolean bAddDebugComments = false;
    
    /**
     * Console debug is activated if this variable is set to true. 
     */
    private boolean bConsoleDebug = false;

    /**
     *  Constructs the Object and calls method {@link #init()}
     */
    public CIndent()
    {
        init();
    }

    /** 
     * Initialize the Object.
     * Sets the size of the indentation with default size of 4.
     */
    public void init()
    {
        iIndent_ = 0;
        sbufIndentWith_ = new StringBuffer();
        setIndent(4);
    }

    /** 
     * Sets the indentation value with the given value.
     * 
     * @param value Integer value denoting the indentation length. 
     */
    public void setIndent(int value)
    {
        iFixedIndent_ = Math.abs(value);
    }

    /**
     *  Returns the current indentation value.
     *  @return int
     */
    public int getIndent()
    {
        return iFixedIndent_;
    }
    
    /**
     * Adds debug comments to the code.
     * 
     * @param pboolean True to add the debug comments. Default false.
     */
    public void addDebug(boolean pboolean)
    {
        bAddDebugComments = pboolean;
    }
    
    /**
     * Outputs the debug messages to System.out.
     * 
     * @param pbConsoleDebug True to print the comments. Default false.
     */
    public void startConsoleDebug(boolean pbConsoleDebug)
    {
        bConsoleDebug = pbConsoleDebug;
    }

    /**
     * Returns the current value of the indentaion that is being 
     * implied on the line thats geting formated.
     * @return int
    */
    public int getCurrentIndent()
    {
        return iIndent_;
    }
    
    /**
     * Returns whether the line is commented or not.
     * Before invoking this method please call the method {@link #format(String)}
     * @return boolean TRUE if line is commented else FALSE
     */
    public boolean isLineCommented()
    {
        return bLineCommented_;
    }

    /**
     * Formats the string by indenting the line.
     * 
     * @param st String to be formatted.
     * @return formatted String
     */
    public String format(String st)
    {
        consoleDebug(st);
        boolean bEscapeCharFound = false;
//        int iLineLength = st.length();
        st = st.trim();
        bSlashCommentFound_ = false;
        sci_ = new StringCharacterIterator(st);
        if (!bStarCommentStarted_)
        {
            bLineCommented_ = false;
        }

        consoleDebug("bStartCommentStarted#" + bStarCommentStarted_);
        char c = sci_.first();
        int iPosition = sci_.getIndex();
        String strSpaces = iIndent_();
        consoleDebug("Current Index#" + getCurrentIndent());
        while (c != CharacterIterator.DONE)
        {
            if (c == 39) // Single quote '
            {
                consoleDebug("Character#" + c);
                if (!(bDoubleQuotesStarted_
                    || bSlashCommentFound_
                    || bStarCommentStarted_))
                {
                    if (!bSingleQuotesStarted_)
                    {
                        if (!bEscapeCharFound)
                        {
                            bSingleQuotesStarted_ = true;
                        }
                    }
                    else
                    {
                        if (!bEscapeCharFound)
                        {
                            bSingleQuotesStarted_ = false;
                        }
                    }
                }
                consoleDebug("bEscapeCharFound#" + bEscapeCharFound + " bSingleQuotesStarted_#" + bSingleQuotesStarted_);
            }
            else if (c == 34) // Double quote "
            {
                consoleDebug("Character#" + c);
                if (!(bSlashCommentFound_ || bStarCommentStarted_))
                {
                    if (!bDoubleQuotesStarted_)
                    {
                        if (!bEscapeCharFound)
                        {
                            bDoubleQuotesStarted_ = true;
                        }
                    }
                    else
                    {
                        if (!bEscapeCharFound)
                        {
                            bDoubleQuotesStarted_ = false;
                        }
                    }
                }
                consoleDebug("bEscapeCharFound#" + bEscapeCharFound + " bDoubleQuotesStarted_#" + bDoubleQuotesStarted_);
            }
            else if (c == 47) // forward slash /
            {
                consoleDebug("Character#" + c);
                if (bSlashFound_)
                {
                    if (iPosition == sci_.getIndex() - 1)
                    {
                        bSlashCommentFound_ = true;
                        if (iPosition == sci_.getBeginIndex())
                        {
                            bLineCommented_ = true;
                        }
                    }
                    consoleDebug("bSlashCommentFound_#" + bSlashCommentFound_ + " bLineCommented_#" + bLineCommented_);
                }
                else if (bStarFound_)
                {
                    if (iPosition == sci_.getIndex() - 1)
                    {
                        bStarCommentStarted_ = false;
                        if (!bLineCommented_)
                        {
                            if (iPosition == sci_.getBeginIndex())
                            {
                                bLineCommented_ = true;
                            }
                            else
                            {
                                bLineCommented_ = false;
                            }
                        }
                    }
                }
                bStarFound_ = false;
                bSlashFound_ = true;
                iPosition = sci_.getIndex();
                consoleDebug("bLineCommented_#" + bLineCommented_);
            }
            else if (c == 42) // Star *
            {
                consoleDebug("Character#" + c);
                if (bSlashFound_)
                {
                    if (iPosition + 1 == sci_.getIndex())
                    {
                        bStarCommentStarted_ = true;
                        if (!bLineCommented_)
                        {
                            if (iPosition == sci_.getBeginIndex())
                            {
                                bLineCommented_ = true;
                            }
                            else
                            {
                                bLineCommented_ = false;
                            }
                        }
                    }
                }
                bSlashFound_ = false;
                bStarFound_ = true;
                iPosition = sci_.getIndex();
                consoleDebug("bLineCommented_#" + bLineCommented_);
            }
            else if (c == 123) // Open Brace bracket {
            {
                consoleDebug("Character#" + c);
                if (!(bSlashCommentFound_ || bStarCommentStarted_))
                {
                    if (!(bDoubleQuotesStarted_ || bSingleQuotesStarted_))
                    {
                        increaseIndent();
                    }
                }
                consoleDebug("Current Index#" + getCurrentIndent());
            }
            else if (c == 125) // close brace bracket }
            {
                consoleDebug("Character#" + c);
                if (!(bSlashCommentFound_ || bStarCommentStarted_))
                {
                    if (!(bDoubleQuotesStarted_ || bSingleQuotesStarted_))
                    {
                        decreaseIndent();
                        strSpaces = iIndent_();
                    }
                }
                consoleDebug("Current Index#" + getCurrentIndent());
            }
            else if (c == 40) //open round bracket (
            {
                consoleDebug("Character#" + c);
                if (!(bSlashCommentFound_ || bStarCommentStarted_))
                {
                    if (!(bDoubleQuotesStarted_ || bSingleQuotesStarted_))
                    {
                        increaseIndent();
                    }
                }
                consoleDebug("Current Index#" + getCurrentIndent());
            }
            else if (c == 41) //closed round bracket )
            {
                consoleDebug("Character#" + c);
                if (!(bSlashCommentFound_ || bStarCommentStarted_))
                {
                    if (!(bDoubleQuotesStarted_ || bSingleQuotesStarted_))
                    {
                        decreaseIndent();
                        strSpaces = iIndent_();
                    }
                }
                consoleDebug("Current Index#" + getCurrentIndent());
            }
            else
            {
                bSlashFound_ = false;
                bStarFound_ = false;
            }
            if (c == iEscapeChar)
            {
                consoleDebug("Character#" + c);
                if (bEscapeCharFound)
                {
                    bEscapeCharFound = false;
                }
                else
                {
                    bEscapeCharFound = true;
                }
                consoleDebug("bEscapeCharFound#" + bEscapeCharFound);
            }
            else
            {
                bEscapeCharFound = false;
            }
            c = sci_.next();
        }
//        if (!bLineCommented_)
//        {
            st = strSpaces + st;
//        }
        bConsoleDebug = false;
        if (bAddDebugComments)
        {
            StringBuffer sbuf = new StringBuffer(st);
            sbuf.append(" //INDENT_DEBUG:");
            sbuf.append("#Current Indent:");
            sbuf.append(getCurrentIndent());
            return sbuf.toString();
        }
        return st;
    }

    /**
     * Increase the current size of the indentation.
     * In other words the line is further right-indented.
     */
    public void increaseIndent()
    {
        iIndent_ += iFixedIndent_;
    }

    /**
     * Increases the current size of the indentation by the specified 
     * number of times.
     * 
     * @param no Number of times the lines needs to be Right-Indented. 
     */
    public void increaseIndent(int no)
    {
        for (int i = 1; i <= no; i++)
            increaseIndent();
    }

    /**
     * Decrease the current size of the indentation.
     * In other words line is further left-indented. 
     */
    public void decreaseIndent()
    {
        if (iIndent_ >= iFixedIndent_)
            iIndent_ -= iFixedIndent_;
    }

    /**
     * Decreases the current size of the indentation by the specified 
     * number of times.
     * 
     * @param no Number of times the lines needs to be Left-Indented. 
     */
    public void decreaseIndent(int no)
    {
        for (int i = 1; i <= no; i++)
            decreaseIndent();
    }

    /**
     * This method returns the string with the size of the indentation.
     * @return String Blank Spaces as per the Indent size.
     */
    private String iIndent_()
    {
        if (iIndent_ <= 0)
            return "";
        if (sbufIndentWith_.length() == iIndent_)
            return sbufIndentWith_.toString();
        sbufIndentWith_.delete(0, sbufIndentWith_.length());
        for (int i = 0; i < iIndent_; i++)
            sbufIndentWith_.append(' ');
        return sbufIndentWith_.toString();
    }
    
    /**
     * Prints the output on System.out.
     * 
     * @param pstrMessage String message to be printed.
     */
    private void consoleDebug(String pstrMessage)
    {
        if (bConsoleDebug)
        {
            System.out.println(pstrMessage);
        }
    }

}
