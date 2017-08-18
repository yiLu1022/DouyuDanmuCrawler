package com.ylu.beans;

public enum DyType{
	Gift("dgb"),
	Danmu("chatmsg"),
	Other("other");
	
	private String value;
	DyType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}