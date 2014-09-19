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
package com.stgmastek.core.comm.util;

import java.sql.Connection;
import java.util.HashMap;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.exception.MessageConsumptionException;
import com.stgmastek.core.comm.server.dao.IConfigDAO;

/**
 * The consumer class for the message. Implements the singleton pattern. 
 * 
 * It calls the appropriate handler class for processing. The sequence is as follows.
 * <li>The {@link #processMessage(CoreMessage)} method is invoked for each CoreMessage. It is expected that the appropriate consumer holds all such messages in a collection.
 * 
 */
public final class MessageConsumer {

	/** Static instance of the class */
	private static MessageConsumer consumer = new MessageConsumer();
	
	private final HashMap<String, IMessageProcessor> consumerClassPool = new HashMap<String, IMessageProcessor>();

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
	 * 		  The message to process (@see {@link CoreMessage}
	 * @return true if the message is processed successfully, 
	 * 		   false otherwise 
	 */
	public boolean processMessage(CoreMessage message){		
		return consume(message);
		
	}		
	
	/**
	 * Public method to consume and process the message by calling 
	 * appropriate handler
	 * 
	 * @param message
	 * 		  The monitor message @see {@link CoreMessage}
	 * @return true if the message was processed successfully, false otherwise
	 */
	final boolean consume(CoreMessage message){
		try{
			if(!execute(message))
				throw new Exception();
			return true;
		}catch(Exception e){
			return onError(message, e);
		}
	}	
	
//	/**
//	 * This method works only on the {@link IOutboundMessageProcessor} types.
//	 * Invokes the transmit messages method.
//	 * First the message is consumed and collated into a list and lastly the message is transmitted.
//	 * 
//	 * @throws MonitorServiceDownException
//	 */
//	final void transmitAllMessages() throws MonitorServiceDownException {
////		for (Entry<String, IMessageProcessor> entry : consumerClassPool.entrySet()) {
////			if (entry.getValue() instanceof IOutboundMessageProcessor) {
////				IOutboundMessageProcessor processor = (IOutboundMessageProcessor) entry.getValue();
////				try {
////					processor.transmitMessages();
////				} catch (Throwable e) {
////					throw new MonitorServiceDownException(
////						"Exception while transmitting message." );
////				}
////			}
////		}
//	}
	
	/**
	 * Helper method that actually executes the instruction depending upon the message
	 *  
	 * @param message
	 * 		  The message to be processed 
	 * @return true, if the execution completes successfully 
	 * 
	 * @throws MessageConsumptionException
	 *   	   Any exception raised during execution of the message wrapped as MessageConsumptionException 
	 */
	private boolean execute(CoreMessage message) throws MessageConsumptionException{
		
		IMessageProcessor mProcessor;
		if (!consumerClassPool.containsKey(message.getMessage())) {
			mProcessor = instantiateMessageProcessor(message);
		} else {
			mProcessor = consumerClassPool.get(message.getMessage());
		}
		
		if("I".equals(message.getMode()) && !(mProcessor instanceof IInboundMessageProcessor))
			throw new MessageConsumptionException(
					"Message Processor should implement IInboundMessageProcessor");
		else if ("O".equals(message.getMode()) && !(mProcessor instanceof IOutboundMessageProcessor))
			throw new MessageConsumptionException(
					"Message Processor should implement IOutboundMessageProcessor");
		
		IMessageProcessor processor = (IMessageProcessor)mProcessor;
		try{
			processor.processMessage(message);
		}catch(Throwable t){
			throw new MessageConsumptionException(t);
		}
		
		return true;
	}
	
	/**
	 * Fail over mechanism where if the handler fails to process the message, 
	 * the message would then be dropped into the dead letter queue 
	 * DEAD_MESSAGE_QUEUE table for further inspection
	 * 
	 * @param message
	 * 		  The message that cannot be processed and has to be dropped 
	 * 		  into the dead letter queue i.e. DEAD_MESSAGE_QUEUE table 
	 * @param throwable
	 * 		  Exception message to be logged
	 * @return true if the message was dropped in the dead letter queue 
	 * 		   false otherwise. In case the dropping of message fails 
	 * 		   the message will be lost permanently
	 */
	final boolean onError(CoreMessage message, Throwable throwable){
		IConfigDAO configdao = DAOFactory.getConfigDAO();
		Connection connection = null;

		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			configdao.setDeadMessage(message, throwable,connection);
		}catch(CommDatabaseException bcde){
			return false;
		}finally {
			configdao.releaseResources(null, null, connection);
		}
		return true;
	}
	
	/**
	 * Returns an instance of {@link IMessageProcessor} after instantiating the class that was configured for a given message.
	 * 
	 * @param message Key
	 * @return IMessageProcessor
	 * @throws MessageConsumptionException if class could not be instantiated or is not of type IMessageProcessor
	 */
	private synchronized IMessageProcessor instantiateMessageProcessor (
			CoreMessage message) throws MessageConsumptionException {
		IMessageProcessor mProcessor = null;
		if (!consumerClassPool.containsKey(message.getMessage())) {
			Class<? extends IMessageProcessor> clazz = MessageConstants
			.getMessageMap().get(message.getMessage());
			
			if (clazz == null)
				throw new MessageConsumptionException("Handler for "
						+ message.getMessage() + " is not registered.");
			
			try {
				mProcessor = clazz.newInstance();
			} catch (Exception e) {
				throw new MessageConsumptionException(e);
			}
			consumerClassPool.put(message.getMessage(), mProcessor);
		} else {
			mProcessor = consumerClassPool.get(message.getMessage()); 
		}
		return mProcessor;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/MessageConsumer.java                                                                       $
 * 
 * 6     6/21/10 11:36a Lakshmanp
 * added the code to get dao from DAOFactory and passed the connection parameter to dao method
 * 
 * 5     1/07/10 5:31p Grahesh
 * Removed unwanted comments
 * 
 * 4     1/07/10 4:56p Grahesh
 * Corrected the code for instrospection
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/