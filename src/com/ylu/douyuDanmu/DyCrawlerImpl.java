package com.ylu.douyuDanmu;

import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ylu.beans.Gift;
import com.ylu.beans.Message;
import com.ylu.douyuFormat.Logger;
import com.ylu.persistence.DatabaseHelper;

public class DyCrawlerImpl implements DyCrawler{
	
	private final static int GROUP_ID = -9999;

	private final static int MAX_CRAWLING_THREADS = 20;
	private Hashtable<Integer,DyBulletScreenClient> clients;
	private ExecutorService threadPool;
	private ScheduledExecutorService heartBeatPool;
	private DatabaseHelper db;	
	
	public DyCrawlerImpl() {
		clients = new Hashtable<Integer, DyBulletScreenClient>();
		db = DatabaseHelper.getInstance();
		threadPool = Executors.newFixedThreadPool(MAX_CRAWLING_THREADS);
		heartBeatPool = Executors.newScheduledThreadPool(MAX_CRAWLING_THREADS);
	}
	
	public void crawlRoom(int roomid) {
		final DyBulletScreenClient client = getClient(roomid);
		threadPool.execute(new Runnable(){ 
			public void run() {
					keepRecvMessage(client);
				}
			});
		heartBeatPool.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				if(client.getReadyFlag()){
					client.keepAlive();
				}
			}
		}, 0, 45, TimeUnit.SECONDS);
		
		
	}

	public void crawlRooms(Collection<Integer> rids) {
		for(int rid: rids){
			crawlRoom(rid);
		}
		
	}
	
	private void keepRecvMessage(DyBulletScreenClient client){
		while(client.getReadyFlag())
        {
			try {
				Collection<MsgMapper> mappers = client.getServerMsg();
	        	for(MsgMapper mapper : mappers){
	        		String type = (String) mapper.getMessageList().get("type");
	        		if(type == null){
	        			continue;
	        		}
	        		if(type.equals("error")){
		    			Logger.v("error response!");
		    			Logger.v(mapper.printStr());
						//结束心跳和获取弹幕线程
						client.setReadyFlag(false);
					}else if(type.equals(com.ylu.beans.Type.Danmu.getValue())){
						Message msg = mapper.message();
						if(msg != null){
							Logger.v(msg.toString());
			    			db.insertMessage(msg);
						}
			    	}else if(type.equals(com.ylu.beans.Type.Gift.getValue())){
			    		Gift gift = mapper.gift();
			    		if(gift != null){
			    			Logger.v(gift.toString());
			    		}
					}else{
						Logger.v(type);
					}
	        	}
			} catch (Exception e) {
				Logger.v(e.getMessage());
				client.setReadyFlag(false);
			}
        	
        }
	}
	
	private synchronized DyBulletScreenClient getClient(int rid){
		DyBulletScreenClient client = clients.get(rid);
		if(client != null){
			return client;
		}else{
			DyBulletScreenClient newClient = new DyBulletScreenClient(); 
			newClient.init(rid, GROUP_ID);
			//TODO if init good, put and return, if bad, throw exception
			clients.put(rid, newClient);
			return newClient;
		}
	}

}
