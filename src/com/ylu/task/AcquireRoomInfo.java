package com.ylu.task;

public class AcquireRoomInfo implements Runnable{

	private static final String API = "http://open.douyucdn.cn/api/RoomApi/room/%s";
	private final String rid;
	
	public AcquireRoomInfo(String rid){
		this.rid = rid;
	}
	
	public void run() {
		String fullApi = String.format(API, rid);
		
		
	}

}
