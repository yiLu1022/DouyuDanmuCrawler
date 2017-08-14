package com.ylu.douyuDanmu;

import java.util.ArrayList;
import java.util.Collection;
import com.ylu.douyuFormat.Logger;


/**
 * @Summary: 弹幕客户端类
 * @author: FerroD     
 * @date:   2016-3-12   
 * @version V1.0
 */
class DyBulletScreenClient
{
	
	//第三方弹幕协议服务器地址
	private static final String hostName = "openbarrage.douyutv.com";
	
	//第三方弹幕协议服务器端口
	private static final int port = 8601;
	
	//设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 4096;

    byte[] bufferBytes;

    private DyConnector connector;
    
    //获取弹幕线程及心跳线程运行和停止标记
    private boolean readyFlag = false;
    
    DyBulletScreenClient(){
    	connector = new DyConnector(hostName, port);
    }
    
    /**
     * 单例获取方法，客户端单例模式访问
     * @return
     */
/*    public static DyBulletScreenClient getInstance(){
    	if(null == instance){
    		instance = new DyBulletScreenClient();
    	}
    	return instance;
    }
    */
    /**
     * 客户端初始化，连接弹幕服务器并登陆房间及弹幕池
     * @param roomId 房间ID
     * @param groupId 弹幕池分组ID
     */
    void init(int roomId, int groupId){
    	//连接弹幕服务器
    	connector.connect();
    	//登陆指定房间
    	this.loginRoom(roomId);
    	//加入指定的弹幕池
    	this.joinGroup(roomId, groupId);
    	//设置客户端就绪标记为就绪状态
    	readyFlag = true;
    }
    
    /**
     * 获取弹幕客户端就绪标记
     * @return
     */
    synchronized boolean getReadyFlag(){
    	return readyFlag;
    }
    
    synchronized void setReadyFlag(boolean readyFlag){
    	this.readyFlag = readyFlag;
    }

    /**
     * 登录指定房间
     * @param roomId
     */
    private void loginRoom(int roomId)
    {
    	//获取弹幕服务器登陆请求数据包
    	byte[] loginRequestData = DyMessage.getLoginRequestData(roomId);
    	
    	
    	try{
    		//发送登陆请求数据包给弹幕服务器
    		connector.write(loginRequestData);
    		
    		//初始化弹幕服务器返回值读取包大小
    		byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
    		//获取弹幕服务器返回值
    		connector.read(recvByte);
    		
    		//解析服务器返回的登录信息
    		if(DyMessage.parseLoginRespond(recvByte)){
    			Logger.v("Room %d login!",roomId);
            } else {
            	Logger.v("Receive login response failed!");
            }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    /**
     * 加入弹幕分组池
     * @param roomId
     * @param groupId
     */
    private void joinGroup(int roomId, int groupId)
    {
    	//获取弹幕服务器加弹幕池请求数据包
    	byte[] joinGroupRequest = DyMessage.getJoinGroupRequest(roomId, groupId);
    	
    	try{
    		//想弹幕服务器发送加入弹幕池请求数据
    		connector.write(joinGroupRequest);
            Logger.v("join Room %d group request successfully!",roomId);
            
    	} catch(Exception e){
    		e.printStackTrace();
    		Logger.v("Send join group request failed!");
    	}
    }
   

    /**
     * 服务器心跳连接
     */
    void keepAlive()
    {
    	//获取与弹幕服务器保持心跳的请求数据包
        byte[] keepAliveRequest = DyMessage.getKeepAliveData((int)(System.currentTimeMillis() / 1000));

        try{
        	//向弹幕服务器发送心跳请求数据包
        	connector.write(keepAliveRequest);
            Logger.v("Send keep alive request successfully!");
            
    	} catch(Exception e){
    		e.printStackTrace();
    		Logger.v("Send keep alive request failed!");
    	}
    }
    
    

    /**
     * 获取服务器返回信息
     */
    Collection<MsgMapper> getServerMsg() throws Exception{
    	//初始化获取弹幕服务器返回信息包大小
    	byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
    	//定义服务器返回信息的字符串
    	Collection<MsgMapper> mappers = new ArrayList<MsgMapper>();

		//读取服务器返回信息，并获取返回信息的整体字节长度
		int recvLen = connector.read(recvByte);
		if(recvLen < 0){
			throw new Exception("Connection closed");
		}
		//If there are bytes left since last parse, then try to combine them with the bytes just read.
		if(bufferBytes != null){
			int bufferLen = bufferBytes.length;
			byte[] newRecvBytes = new byte[recvLen +bufferLen];
			System.arraycopy(bufferBytes, 0, newRecvBytes, 0, bufferLen);
			System.arraycopy(recvByte, 0, newRecvBytes, bufferLen, recvLen);
			//Clean buffer.
			bufferBytes = null;
			bufferBytes = DyMessage.parseRecvMsg(newRecvBytes, recvLen + bufferLen, mappers);
		}else{
			bufferBytes = DyMessage.parseRecvMsg(recvByte, recvLen, mappers);
		}
		return mappers;
    } 

    	

    
}
