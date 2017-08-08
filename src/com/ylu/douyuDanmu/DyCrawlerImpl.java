package com.ylu.douyuDanmu;

import java.util.Collection;
import java.util.Hashtable;

import com.ylu.beans.Message;
import com.ylu.beans.Message.Type;
import com.ylu.douyuFormat.Logger;
import com.ylu.persistence.DatabaseHelper;

public class DyCrawlerImpl implements DyCrawler{
	
	private final static int GROUP_ID = -9999;

	private Hashtable<Integer,DyBulletScreenClient> clients;
	private Hashtable<Integer, ThreadDispatcher> threads;
	private DatabaseHelper db;
	
	public DyCrawlerImpl() {
		clients = new Hashtable<Integer, DyBulletScreenClient>();
		db = DatabaseHelper.getInstance();
		threads = new Hashtable<Integer, ThreadDispatcher>();
	}
	
	public void crawlRoom(int roomid) {
		final DyBulletScreenClient client = getClient(roomid);
		getListeningThread(roomid).async(new Runnable() {
			public void run() {
				keepRecvMessage(client);
			}
		});
		
		getListeningThread(roomid).async(new Runnable() {
			public void run() {
				 keepAlive(client);
			}
		});
		
	}

	public void crawlRooms(Collection<Integer> rids) {
		for(int rid: rids){
			crawlRoom(rid);
		}
		
	}
	
	public void keepAlive(DyBulletScreenClient client){
		 while(client.getReadyFlag())
	        {
	        	//发送心跳保持协议给服务器端
	        	client.keepAlive();
	            try
	            {
	            	//设置间隔45秒再发送心跳协议
	                Thread.sleep(45000);        //keep live at least once per minute
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	            }
	        }
	}
	
	public void keepRecvMessage(DyBulletScreenClient client){
		while(client.getReadyFlag())
        {
        	//获取服务器发送的弹幕信息
        	Collection<Message> messages = client.getServerMsg();
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
	
	public synchronized DyBulletScreenClient getClient(int rid){
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
	
	public synchronized ThreadDispatcher getListeningThread(int rid){
		ThreadDispatcher thread = threads.get(rid);
		if(thread != null){
			return thread;
		}else{
			ThreadDispatcher newThread = new ThreadDispatcher();
			threads.put(rid, newThread);
			return newThread;
		}
		
	}

}
