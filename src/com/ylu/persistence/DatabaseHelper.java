package com.ylu.persistence;

import com.ylu.beans.Message;
import com.ylu.douyuDanmu.ThreadDispatcher;
import com.ylu.douyuFormat.Logger;

public class DatabaseHelper {
	
	ThreadDispatcher tDispatcher;
	MessageDAOMapper messageDAOMapper ;
	
	private DatabaseHelper(){
		tDispatcher = new ThreadDispatcher();
		messageDAOMapper = new MessageDaoImpl();
	}
	
	public static DatabaseHelper getInstance(){
		return Holder.instance;
	}
	
	public static class Holder{
		private static DatabaseHelper instance= new DatabaseHelper();
		
	}
	
	public void insertMessage(final Message message){
		tDispatcher.async(new Runnable() {
			
			public void run() {
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
