package com.ylu.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ylu.beans.Message;
import com.ylu.douyuFormat.Logger;

public class DatabaseHelper {

	private MessageDAOMapper messageDAOMapper ;
	private ExecutorService singleThExecutor;
	
	private DatabaseHelper(){
		messageDAOMapper = new MessageDaoImpl();
		singleThExecutor = Executors.newSingleThreadExecutor();
	}
	
	public static DatabaseHelper getInstance(){
		return Holder.instance;
	}
	
	public static class Holder{
		private static DatabaseHelper instance= new DatabaseHelper();
		
	}
	
	public void insertMessage(final Message message){
		
		singleThExecutor.execute(new Runnable() {
			
			public void run() {
				//Logger.v("Database Thread - %s",Thread.currentThread().getName());
				if(message.getCid() != null){
					try{
						messageDAOMapper.insert(new MessageDAO(message));
					}catch(Exception e){
						Logger.v("Cannot insert %s into database, skip.", message.getTxt());
					}
				}
				
			}
		});
		
	}
	
	public Collection<Message> findMessageByCid(final String cid){
		final Collection<Message> messages = new ArrayList<Message>();

		if(cid!=null){
			messages.add(messageDAOMapper.selectByPrimaryKey(cid).toMessage());
		}
				
		return messages;
	}
	
	public Collection<Message> findMessageByUid(final String uid){
		final Collection<Message> messages = new ArrayList<Message>();

		if(uid!=null){
			Collection<MessageDAO> daos = messageDAOMapper.selectByUid(uid);
			for(MessageDAO dao : daos){
				messages.add(dao.toMessage());
			}
		}
				
		return messages;
	}
	
	public Collection<Message> findMessageByBnn(final String bnn){
		final Collection<Message> messages = new ArrayList<Message>();
			
		if(bnn!=null){
			Collection<MessageDAO> daos = messageDAOMapper.selectByBnn(bnn);
			for(MessageDAO dao : daos){
				messages.add(dao.toMessage());
			}
		}
				

		return messages;
	}
	
	public Collection<Message> findMessageByLevel(final String level){
		final Collection<Message> messages = new ArrayList<Message>();

		if(level!=null){
			Collection<MessageDAO> daos = messageDAOMapper.selectByLevel(level);
			for(MessageDAO dao : daos){
				messages.add(dao.toMessage());
			}
		}

		return messages;
	}
	


}
