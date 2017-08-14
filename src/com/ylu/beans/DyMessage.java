package com.ylu.beans;

import java.util.Map;

import com.ylu.douyuFormat.MsgMapper;

public abstract class DyMessage {
	
	protected DyMessage() {
		
	}
	
	public static class Builder{
		
		private MsgMapper mapper;
		
		public Builder mapper(MsgMapper mapper){
			this.mapper = mapper;
			return this;
		}
		
		
		public DyMessage build(){
			Map<String, Object> map = mapper.getMessageMapper();
			
			if(map.containsKey("type")){
				String type = (String)map.get("type");
				if(DyType.Danmu.getValue().equals(type)){
					Danmu.Builder builder= new Danmu.Builder().metaData(map);
					if(builder != null){
						return builder.build();
					}
				}else if(DyType.Gift.getValue().equals(type)){
					Gift.Builder builder = new Gift.Builder().metaData(map);
					if(builder != null){
						return builder.build();
					}
				}else if("error".equals(type)){
					//TODO
				}else{
					//TODO
				}
			}
			return null;
		}
	}
	
	public abstract DyType getType();
}
