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
package com.stgmastek.monitor.comm.util;

import java.sql.Connection;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.exception.MessageConsumptionException;
import com.stgmastek.monitor.comm.server.dao.IConfigDAO;


/**
 * The consumer class for the message. Implements the singleton pattern.
 * The message is validated for correctness and whether such instruction exists
 * Once found OK, it calls the appropriate handler class for processing 
 *  
 */
public final class MessageConsumer {
	
	/** Static instance of the class */
	private static MessageConsumer consumer = new MessageConsumer();
	
	/**
	 * Private constructor to avoid outside instantiation 
	 */
	private MessageConsumer(){
	}
	
	/**
	 * Returns the consumer instance
	 * 
	 * @return the consumer instance
	 */
	public static MessageConsumer getConsumer(){
		return consumer;
	}
	
	/**
	 * Processes the message 
	 * 
	 * @param message
	 * 		  The message to process (@see {@link MonitorMessage}
	 * @return true if the message is processed successfully, 
	 * 		   false otherwise 
	 */
	public boolean processMessage(MonitorMessage message){		
		return consume(message);
		
	}

	/**
	 * Public method to consume and process the message calling 
	 * appropriate handler
	 * 
	 * @param message
	 * 		  The monitor message @see {@link MonitorMessage}
	 * @return true if the message was processed successfully, false otherwise
	 */
	final boolean consume(MonitorMessage message){
		try{
			if(!execute(message))
				throw new Exception();
			return true;
		}catch(Exception e){
			return onError(message, e);
		}
	}	
	
	/**
	 * Helper method to execute a message OR instruction of the message to be executed 
	 *  
	 * @param message
	 * 		  The message to be processed or executed 
	 * @return true, if the message has been successfully processed
	 * @throws MessageConsumptionException
	 * 		   Any exception raised during the execution of the message 
	 */
	private boolean execute(MonitorMessage message) throws MessageConsumptionException{
		
		Class<? extends IMessageProcessor> clazz = 
					MessageConstants.MESSAGE_MAP.get(message.getMessage());
		
		if(clazz == null)
			throw new MessageConsumptionException("Handler for " 
							+ message.getMessage() + " is not registered.");
		
		Object obj = null;		
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			throw new MessageConsumptionException(e);
		}
		
		if("I".equals(message.getMode()) && !(obj instanceof IInboundMessageProcessor))
			throw new MessageConsumptionException(
					"Message Processor should implement IInboundMessageProcessor");
		else if ("O".equals(message.getMode()) && !(obj instanceof IOutboundMessageProcessor))
			throw new MessageConsumptionException(
					"Message Processor should implement IOutboundMessageProcessor");
		
		IMessageProcessor processor = (IMessageProcessor)obj;
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
	final boolean onError(MonitorMessage message, Throwable throwable){
		IConfigDAO configdao = DAOFactory.getConfigDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			configdao.setDeadMessage(message, throwable,connection);
		}catch(CommDatabaseException bcde){
			return false;
		}finally{
			configdao.releaseResources(null, null, connection);
		}
		return true;
	}
		
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/MessageConsumer.java                                                                 $
 * 
 * 7     6/18/10 12:46p Lakshmanp
 * added connection parameter for dao methods and modified getting dao object from daofactory
 * 
 * 6     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 5     1/07/10 5:31p Grahesh
 * Removed unwanted comments
 * 
 * 4     12/30/09 2:47p Grahesh
 * Refining invocation
 * 
 * 3     12/21/09 10:20a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/