package com.ylu.beans;

import java.util.Map;

public class Gift {
		
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
		//Gift Id
		private final String gfid;
		//Gift Type -ChatMsg
		private final String gs;
		//User level
		private final String level;
		//Weight
		private final String dw;
		
		private final String gfcnt;

		public Gift(String gid, String rid, String uid, String nn, String bnn, String gfid, String gs, String level,
				String dw, String gfcnt) {
			super();
			type = Type.Gift;
			this.gid = gid;
			this.rid = rid;
			this.uid = uid;
			this.nn = nn;
			this.bnn = bnn;
			this.gfid = gfid;
			this.gs = gs;
			this.level = level;
			this.dw = dw;
			this.gfcnt = gfcnt;
		}
		public Type geType(){
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

		public String getBnn() {
			return bnn;
		}

		public String getGfid() {
			return gfid;
		}

		public String getGs() {
			return gs;
		}

		public String getLevel() {
			return level;
		}

		public String getDw() {
			return dw;
		}

		public String getGfcnt() {
			return gfcnt;
		}

		public static class Builder{

			//Group Id
			private String gid;
			//Room Id
			private String rid;
			//User Id
			private String uid;
			//Nickname
			private String nn;
			//Fan badge
			private String bnn;
			//Gift Id
			private String gfid;
			//Gift Type -ChatMsg
			private String gs;
			//User level
			private String level;
			//Weight
			private String dw;
			
			private String gfcnt;
			
			public Builder metaData(Map<String,Object> map){
				try{
					gid =(String) map.get("gid");
					rid =(String) map.get("rid");
					uid =(String) map.get("uid");
					bnn =(String) map.get("bnn");
					gfid =(String) map.get("gfid");
					gs =(String) map.get("gs");
					dw =(String) map.get("dw");
					gfcnt =(String) map.get("gfcnt");
					level =(String) map.get("level");
					nn =(String) map.get("nn");
					return this;
				}catch (Exception e) {
					return null;
				}
			}
			
			public Gift build(){
				return new Gift(gid, rid, uid, nn, bnn, gfid, gs, level,
						dw, gfcnt); 
			}

		}
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append(rid);
			sb.append("|");
			sb.append(type.name());
			sb.append("|");
			sb.append(nn);
			sb.append("|");
			sb.append(gfid);
			
			return sb.toString();
			
		}

}
