package com.ylu.douyuClient;

import java.io.IOException;

import com.google.gson.Gson;
import com.ylu.beans.RoomInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DyRoomInfo {
	
	private static final String URL = "http://open.douyucdn.cn/api/RoomApi/room/%d";
	
	static RoomInfo getRoomInfo(int roomID) throws IOException{
		OkHttpClient client  = new OkHttpClient();
		String fullUrl  = String.format(URL, roomID);
		Request request = new Request.Builder().url(fullUrl).build();
		Response response = client.newCall(request).execute();
		String content = response.body().string();
		return parse(content);
	}
	
	private static RoomInfo parse(String jsonString){
		Gson gson = new Gson();
		return gson.fromJson(jsonString, RoomInfo.class);
	}
	
	
	
}
