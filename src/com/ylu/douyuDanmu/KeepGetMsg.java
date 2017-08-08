package com.ylu.douyuDanmu;

import com.ylu.beans.Message;

/**
 * @Summary: 获取服务器弹幕信息线程
 * @author: FerroD     
 * @date:   2016-3-12   
 * @version V1.0
 */
public class KeepGetMsg extends Thread {

	private RecvCallback listener;

	public void setRecvCallback(RecvCallback listener){
		this.listener = listener;
	}
	
	@Override
    public void run()
    {
		////获取弹幕客户端
    	DyBulletScreenClient danmuClient = DyBulletScreenClient.getInstance();
    	
    	//判断客户端就绪状态
        while(danmuClient.getReadyFlag())
        {
        	//获取服务器发送的弹幕信息
        	Message msg = danmuClient.getServerMsg();
        	if(msg != null){
        		if(listener != null){
        			listener.onReceived(msg);
        		}
        	}
        }
    }
}
