package com.ylu.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class Danmu extends DyMessage{
	
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
	private Date date;
	
	
	private Danmu(DyType type, String gid, String rid, String uid, String nn,String bnn, String txt, String cid, String level) {
		super();
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
	
	public void setDate(Date date){
		this.date = date;
	}


	public static class Builder{
		
		private String gid;
		private String rid;
		private String uid;
		private String nn;
		private String bnn;
		private String txt;
		private String cid;
		private String level;
		
		public Builder metaData(Map<String,Object> map){
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
				e.printStackTrace();
				return null;
			}
			
		}
		
		public Builder gid(String gid){
			this.gid = gid;
			return this;
		}
		
		public Builder rid(String rid){
			this.rid = rid;
			return this;
		}
		
		public Builder uid(String uid){
			this.uid = uid;
			return this;
		}
		
		public Builder nn(String nn){
			this.nn = nn;
			return this;
		}
		
		public Builder bnn(String bnn){
			this.bnn = bnn;
			return this;
		}
		
		public Builder txt(String txt){
			this.txt = txt;
			return this;
		}
		
		public Builder cid(String cid){
			this.cid = cid;
			return this;
		}
		
		public Builder level(String level){
			this.level = level;
			return this;
		}
		
		public Danmu build(){
			return new Danmu(DyType.Danmu, gid, rid, uid, nn, bnn, txt, cid, level); 
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
		sb.append(getType().name());
		sb.append("|");
		sb.append(nn);
		sb.append("|");
		sb.append(txt);
		
		return sb.toString();
		
	}

	@Override
	public DyType getType() {
		return DyType.Danmu;
	}


}
