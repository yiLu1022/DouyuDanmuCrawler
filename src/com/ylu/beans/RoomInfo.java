package com.ylu.beans;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class RoomInfo {
	private String error;
	private Data data;
    private Date date;
	




	public RoomInfo(String error, Data data) {
		super();
		this.error = error;
		this.data = data;
		date = new Date();
	}
	
	

	public Data getData() {
		return data;
	}



	public void setData(Data data) {
		this.data = data;
	}



	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}

	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("人气：%s",data.getOnline()));
		sb.append("-");
		sb.append(String.format("体重：%s",data.getOwnerWeight()));
		sb.append("-");
		sb.append(String.format("关注：%s",data.getFansNum()));
		return sb.toString();
		
	}
	
	public class Data{
		@SerializedName("room_id")
		private String roomId;
		private String online;
		@SerializedName("owner_weight")
		private String ownerWeight;
		@SerializedName("room_status")
		private String roomStatus;
		@SerializedName("fans_num")
		private String fansNum;
		
		
		
		public Data(String roomId, String online, String ownerWeight, String roomStatus, String fansNum) {
			super();
			this.roomId = roomId;
			this.online = online;
			this.ownerWeight = ownerWeight;
			this.roomStatus = roomStatus;
			this.fansNum = fansNum;
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

		public void setOwnerWeight(String owner_weight) {
			this.ownerWeight = owner_weight;
		}

		public String getRoomStatus() {
			return roomStatus;
		}

		public void setRoomStatus(String room_status) {
			this.roomStatus = room_status;
		}

		public String getFansNum() {
			return fansNum;
		}

		public void setFansNum(String fans_num) {
			this.fansNum = fans_num;
		}
	}
}
