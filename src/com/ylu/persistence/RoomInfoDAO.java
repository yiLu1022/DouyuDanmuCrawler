package com.ylu.persistence;

import java.sql.Timestamp;
import java.util.Date;

import com.ylu.beans.RoomInfo;

public class RoomInfoDAO {
	
	private String roomId;
	
	private String online;

	private String ownerWeight;

	private String fansNum;
	
    private Timestamp time;
    
    public RoomInfoDAO(RoomInfo roomInfo){
    	this.roomId = roomInfo.getData().getRoomId();
    	this.online = roomInfo.getData().getOnline();
    	this.ownerWeight = roomInfo.getData().getOwnerWeight();
    	this.fansNum = roomInfo.getData().getFansNum();
    	if(roomInfo.getDate() == null){
    		this.time = new Timestamp(new Date().getTime());
    	}else{
    		this.time = new Timestamp(roomInfo.getDate().getTime());
    	}
    	
    }

	public RoomInfoDAO(String roomId, String online, String ownerWeight,
			String fansNum, Timestamp time) {
		super();
		this.roomId = roomId;
		this.online = online;
		this.ownerWeight = ownerWeight;
		this.fansNum = fansNum;
		this.time = time;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getOwnerWeight() {
		return ownerWeight;
	}

	public void setOwnerWeight(String ownerWeight) {
		this.ownerWeight = ownerWeight;
	}

	public String getFansNum() {
		return fansNum;
	}

	public void setFansNum(String fansNum) {
		this.fansNum = fansNum;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
    
    
}
