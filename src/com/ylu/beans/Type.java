package com.ylu.beans;

public enum Type{
	Gift("dgb"),
	Danmu("chatmsg"),
	Other("other");
	
	private String value;
	Type(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}