package com.ylu.douyuClient;

import com.ylu.beans.DyMessage;
import com.ylu.beans.RoomInfo;

public interface DyMessageListener {
	void onReceiveMessage(DyMessage dyMessage);
	void onReceiveRoomInfo(RoomInfo info);
	void onException(Exception e);
	void onReceiveError(int roomId);
}
