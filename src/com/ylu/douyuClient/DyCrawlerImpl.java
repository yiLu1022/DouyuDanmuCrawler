package com.ylu.douyuClient;

import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ylu.beans.DyMessage;
import com.ylu.beans.RoomInfo;
import com.ylu.douyuFormat.Logger;
import com.ylu.douyuFormat.MsgMapper;

public class DyCrawlerImpl extends DyCrawler {

	private final static int GROUP_ID = -9999;

	private final static int MAX_CRAWLING_THREADS = 20;
	private Hashtable<Integer, DyBulletScreenClient> clients;
	private ExecutorService threadPool;
	private ScheduledExecutorService heartBeatPool;
	

	public DyCrawlerImpl() {
		clients = new Hashtable<Integer, DyBulletScreenClient>();
		threadPool = Executors.newFixedThreadPool(MAX_CRAWLING_THREADS);
		heartBeatPool = Executors.newScheduledThreadPool(MAX_CRAWLING_THREADS);
	}
  
	public void crawlRoom(final int roomid,final DyMessageListener listener) {
		
				
		final DyBulletScreenClient client = getClient(roomid);
		
		if(client.getReadyFlag()){
			Logger.v("already listening");
		}
		
		try {
			client.init(roomid, GROUP_ID);
		} catch (Exception e) {
			listener.onException(e);
		}
		
		threadPool.execute(new Runnable() {
			public void run() {
				keepRecvMessage(client,listener);
			}
		});
		heartBeatPool.scheduleAtFixedRate(new Runnable() {

			public void run() {
				if (client.getReadyFlag()) {
					try {
						client.keepAlive();
						RoomInfo info = DyRoomInfo.getRoomInfo(roomid);
						if(listener != null){
							listener.onReceiveRoomInfo(info);
						}
					} catch (IOException e) {
						if(listener != null){
							listener.onException(e);
						}
					}
				}
			}
		}, 0, 45, TimeUnit.SECONDS);

	}

	private void keepRecvMessage(DyBulletScreenClient client,DyMessageListener listener) {
		while (client.getReadyFlag()) {
			try {
				Collection<MsgMapper> mappers = client.getServerMsg();
				for (MsgMapper mapper : mappers) {
					if(listener != null){
						listener.onReceiveMessage(new DyMessage.Builder().mapper(mapper).build());
					}
				}
			} catch (Exception e) {
				if(listener != null){
					listener.onException(e);
				}
			}

		}
	}

	private synchronized DyBulletScreenClient getClient(int rid) {
		DyBulletScreenClient client = clients.get(rid);
		if (client != null) {
			return client;
		} else {
			DyBulletScreenClient newClient = new DyBulletScreenClient();
			clients.put(rid, newClient);
			return newClient;
		}
	}

	@Override
	public synchronized void stopCrawling(int roomId) {
		getClient(roomId).setReadyFlag(false);
	}

}