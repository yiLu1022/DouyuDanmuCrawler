package com.ylu.douyuDanmu;

import java.util.Collection;

public interface DyCrawler {
	void crawlRoom(int roomid);
	
	void crawlRooms(Collection<Integer> rids);
	
	
}
