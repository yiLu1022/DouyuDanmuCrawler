package com.ylu.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
	
	public Map<String, Long> selectTopByBnn(final int limit){
		Collection<Map<String, Object>> results = messageDAOMapper.selectTopByBnn(limit);
		Map<String, Long> bnnRankMap = new HashMap<String, Long>();
		for(Map<String, Object> result : results){
			Logger.v(result.get("bnn") + String.valueOf( (Long)result.get("count")));
			if(result.containsKey("bnn") && result.containsKey("count")){
				bnnRankMap.put((String) result.get("bnn"),(Long) result.get("count"));
			}
		}
		return bnnRankMap;
	}
	
	public Map<String, Long> selectTopByNn(final int limit){
		Collection<Map<String, Object>> results = messageDAOMapper.selectTopByNn(limit);
		Map<String, Long> bnnRankMap = new HashMap<String, Long>();
		for(Map<String, Object> result : results){
			Logger.v(result.get("nn") + String.valueOf( (Long)result.get("count")));
			if(result.containsKey("nn") && result.containsKey("count")){
				bnnRankMap.put((String) result.get("nn"),(Long) result.get("count"));
			}
		}
		return bnnRankMap;
	}
	
	public Map<String, Long> selectByTimeInterval(Date start,Date end,long interval){
		
		Collection<Map<String, Object>> results = messageDAOMapper.selectByTimeInterval(new Timestamp(start.getTime()), new Timestamp(end.getTime()), interval);
		Map<String, Long> resultMap = new HashMap<String, Long>();
		for(Map<String, Object> result : results){
			Logger.v(result.get("time") + " : "+ String.valueOf( (Long)result.get("count")));
			if(result.containsKey("time") && result.containsKey("count")){
				resultMap.put((String) result.get("time"),(Long) result.get("count"));
			}
		}
		return resultMap;
	}


}
