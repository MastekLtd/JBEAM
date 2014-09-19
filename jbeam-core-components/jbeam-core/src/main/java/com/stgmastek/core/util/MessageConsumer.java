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
package com.stgmastek.core.util;

import org.apache.log4j.Logger;

import com.stgmastek.core.exception.MessageConsumptionException;

/**
 * The consumer class for the message. Implements the singleton pattern. The
 * message is analyzed for correctness and then calls the appropriate handler class for processing
 * 
 */
public final class MessageConsumer {

	private static final Logger logger = Logger.getLogger(MessageConsumer.class);
	
	/** Static instance of the class */
	private static MessageConsumer consumer = new MessageConsumer();

	/**
	 * Private constructor 
	 */
	private MessageConsumer() {		
	}

	/**
	 * Returns the consumer instance
	 * 
	 * @return the consumer instance
	 */
	public static MessageConsumer getConsumer() {
		return consumer;
	}

	/**
	 * Processes the message 
	 * 
	 * @param message
	 * 		  The message to process (@see {@link Message}
	 * @param context
	 * 		  The context of the batch 
	 * @return true if the message is processed successfully, 
	 * 		   false otherwise 
	 */
	public boolean processMessage(Message message, BatchContext context){		
		return consume(message, context);
		
	}		
	
	/**
	 * Public method to consume and process the message calling 
	 * appropriate handler
	 * 
	 * @param message
	 * 		  The monitor message @see {@link Message}
	 * @param context
	 * 		  The context of the batch 
	 * @return true if the message was processed successfully, false otherwise
	 */
	final boolean consume(Message message, BatchContext context){
		try{
			if(!execute(message, context))
				throw new Exception();
			return true;
		}catch(Exception e){
			logger.error(e);
			return onError(message, e);
		}
	}	
	
	/**
	 * Helper method that would actually execute the instructions from the message 
	 * 
	 * @param message
	 * 		  The message under consideration 
	 * @param context
	 * 		  The batch context 
	 * @return true, if the message has been executed successfully
	 * @throws MessageConsumptionException
	 */
	private boolean execute(Message message, BatchContext context) throws MessageConsumptionException{
		
		Class<? extends IMessageProcessor> clazz = 
					MessageConstants.getMessageMap().get(message.getMessage());
		
		if(clazz == null)
			throw new MessageConsumptionException("Handler for " 
							+ message.getMessage() + " is not registered.");
		
		Object obj = null;		
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			throw new MessageConsumptionException(e);
		}
		
		IMessageProcessor processor = (IMessageProcessor)obj;		
		try{
			processor.processMessage(message, context);			
		}catch(Throwable t){
			throw new MessageConsumptionException(t);
		}	
		
		return true;
	}

	/**
	 * Fail-over mechanism when the processing of the message fails 
	 * 
	 * @param message
	 * 		  The message under consideration 
	 * @param throwable
	 * 		  The exception that was raised during the processing of the message
	 * @return true, if the error handling was successful
	 */
	final boolean onError(Message message, Throwable throwable){
		//TODO
		//Monitor needs to be informed that the critical 
		//message was not processed, in case of any failures 
		return true;
	}	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/MessageConsumer.java                                                                                $
 * 
 * 6     1/07/10 5:30p Grahesh
 * Updated Java Doc comments
 * 
 * 5     1/07/10 4:56p Grahesh
 * Corrected the code for instrospection
 * 
 * 4     12/18/09 12:58p Grahesh
 * Updated the comments
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/