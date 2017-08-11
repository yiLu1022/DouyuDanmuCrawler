package com.ylu.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class Message {
	
	//Message Type
	private final Type type;
	
	
	//Group Id
	private final String gid;
	//Room Id
	private final String rid;
	//User Id
	private final String uid;
	//Nickname
	private final String nn;
	//Fan badge
	private final String bnn;
	//Content - ChatMsg
	private final String txt;
	//Unique Id -ChatMsg
	private final String cid;
	//User level
	private final String level;
	
	
	//
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
			type = Type.Danmu;
			try{
				nn =(String) map.get("nn");			
				gid =(String) map.get("gid");
				rid =(String) map.get("rid");
				uid =(String) map.get("uid");
				bnn =(String) map.get("bnn");
				txt =(String) map.get("txt");
				cid =(String) map.get("cid");
				level =(String) map.get("level");
				return this;
			}
			catch (Exception e) {
				return null;
			}
			
		}
		
		public Message build(){
			return new Message(type, gid, rid, uid, nn, bnn, txt, cid, level); 
		}

	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(rid);
		sb.append("|");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		sb.append(dateString);
		sb.append("|");
		sb.append(type.name());
		sb.append("|");
		sb.append(nn);
		sb.append("|");
		sb.append(txt);
		
		return sb.toString();
		
	}



}
