package com.ylu.ui;

import com.ylu.beans.Message;
import com.ylu.beans.Message.Type;
import com.ylu.douyuFormat.Logger;
import com.ylu.persistence.MessageDAO;
import com.ylu.persistence.MessageDaoImpl;


public class DanmuTable {
	
	public static void showDanmu(Message msg) throws Exception{
		if(msg.getType() != null){
	    		MessageDaoImpl daoImpl = new MessageDaoImpl();
	    		//服务器反馈错误信息		
	    		
	    		if(msg.getType() == Type.Danmu){
	    			daoImpl.insert( new MessageDAO(msg));
	    			Logger.v(msg.toString());
	    		}

			}				
	}
}
