package com.ylu.douyuClient;

import java.util.Collection;

public abstract class DyCrawler {
	
	public abstract void crawlRoom(int roomid,DyMessageListener listener);
	
	public void crawlRooms(Collection<Integer> rids, DyMessageListener listener) {
		for (int rid : rids) {
			crawlRoom(rid,listener);
		}
	}
	
	public abstract void stopCrawling(int roomId);
	
}
