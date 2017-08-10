package com.ylu.douyuDanmu;

import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ylu.beans.Message;
import com.ylu.beans.Message.Type;
import com.ylu.douyuFormat.Logger;
import com.ylu.persistence.DatabaseHelper;
import com.ylu.util.PerformanceMonitor;

public class DyCrawlerImpl implements DyCrawler{
	
	private final static int GROUP_ID = -9999;

	private final static int MAX_CRAWLING_THREADS = 20;
	private Hashtable<Integer,DyBulletScreenClient> clients;
	private ExecutorService threadPool;
	private ScheduledExecutorService heartBeatPool;
	private DatabaseHelper db;
	private PerformanceMonitor monitor = PerformanceMonitor.getInstance();
	
	
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
					monitor.report();
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
			
        	Collection<Message> messages = client.getServerMsg();
        	monitor.recv();
        	monitor.msg(messages.size());
        	for(Message msg : messages){
	        	if(msg.getType().equals("error")){
	    			Logger.v(msg.toString());
					//结束心跳和获取弹幕线程
					client.setReadyFlag(false);
				}else{
					if(msg.getType() != null){
			    		if(msg.getType() == Type.Danmu){
			    			Logger.v(msg.toString());
			    			db.insertMessage(msg);
			    		}
	
					}
				}
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
