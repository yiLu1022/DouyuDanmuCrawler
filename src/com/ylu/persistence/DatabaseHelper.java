package com.ylu.persistence;

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

}
