package com.ylu.douyuDanmu;

import com.ylu.beans.Message;
import com.ylu.beans.Message.Type;
import com.ylu.douyuFormat.Logger;
import com.ylu.ui.DanmuTable;



public class Main {
private static final int roomId = 292081;
	
	//弹幕池分组号，海量模式使用-9999
	private static final int groupId = -9999;
	
	public static void main(String[] args)
	{
		//初始化弹幕Client
        final DyBulletScreenClient danmuClient = DyBulletScreenClient.getInstance();
        //设置需要连接和访问的房间ID，以及弹幕池分组号
        danmuClient.init(roomId, groupId);
        
        //保持弹幕服务器心跳
        KeepAlive keepAlive = new KeepAlive();
        keepAlive.start();
        
        //获取弹幕服务器发送的所有信息
        KeepGetMsg keepGetMsg = new KeepGetMsg();
        keepGetMsg.setRecvCallback(new RecvCallback(){
  			public void onReceived(Message msg) {
  				if(msg.getType().equals("error")){
	    			Logger.v(msg.toString());
					//结束心跳和获取弹幕线程
					danmuClient.setReadyFlag(false);
				}else {
					try {
						DanmuTable.showDanmu(msg);
					} catch (Exception e) {
						e.printStackTrace();
						danmuClient.setReadyFlag(false);
					}
				}
			}
        });
        keepGetMsg.start();
	}

}
