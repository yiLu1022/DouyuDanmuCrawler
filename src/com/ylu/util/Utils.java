package com.ylu.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ylu.beans.Message;

public class Utils {
	public static String outputTxt(Collection<Message> messages){
		StringBuilder sb = new StringBuilder();
		for(Message message : messages){
			sb.append(message.getTxt());
			sb.append(",");
		}
		return sb.toString();
	}
	
	public static String outputMap(Map<String,Object> map){
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()){
			Map.Entry<String, Object> entry = it.next();
			sb.append(entry.getKey());
			sb.append(" : ");
			sb.append(entry.getValue());
			sb.append("/n");
		}
		return sb.toString();
	}
}
