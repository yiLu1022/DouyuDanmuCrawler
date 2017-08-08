package com.ylu.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ylu.douyuFormat.Logger;


public class Message {
	private final Type type;
	private final String gid;
	private final String rid;
	private final String uid;
	private final String nn;
	private final String bnn;
	private final String txt;
	private final String cid;
	private final String level;
	private final Date date;
	
	

	
	public Message(Type type, String gid, String rid, String uid, String nn,String bnn, String txt, String cid, String level) {
		super();
		this.type = type;
		this.gid = gid;
		this.rid = rid;
		this.uid = uid;
		this.nn = nn;
		this.bnn = bnn;
		this.txt = txt;
		this.cid = cid;
		this.level = level;
		date = new Date();
	}

	public Type getType() {
		return type;
	}

	public String getGid() {
		return gid;
	}

	public String getRid() {
		return rid;
	}

	public String getUid() {
		return uid;
	}

	public String getNn() {
		return nn;
	}
	
	public String getBnn(){
		return bnn;
	}

	public String getTxt() {
		return txt;
	}

	public String getCid() {
		return cid;
	}

	public String getLevel() {
		return level;
	}
	
	public Date getDate(){
		return date;
	}


	public static class Builder{
		
		private Type type;
		private String gid;
		private String rid;
		private String uid;
		private String nn;
		private String bnn;
		private String txt;
		private String cid;
		private String level;
		
		public Builder metaData(Map<String,Object> map){
			nn =(String) map.get("nn");
			String typeStr = (String) map.get("type");
			if("chatmsg".equals(typeStr)){//弹幕消息
				type = Type.Danmu;
			} else if("dgb".equals(typeStr)){//赠送礼物信息
				type = Type.Gift;
			} else {
				type = Type.Other;
			}
			
			gid =(String) map.get("gid");
			rid =(String) map.get("rid");
			uid =(String) map.get("uid");
			bnn =(String) map.get("bnn");
			txt =(String) map.get("txt");
			cid =(String) map.get("cid");
			level =(String) map.get("level");
			
			return this;
		}
		
		public Message build(){
			return new Message(type, gid, rid, uid, nn, bnn, txt, cid, level); 
		}

	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(date.toString());
		sb.append("|");
		sb.append(nn);
		sb.append("|");
		sb.append(txt);
		
		return sb.toString();
		
	}


	public enum Type{
		Gift,
		Danmu,
		Other,
	}
}
