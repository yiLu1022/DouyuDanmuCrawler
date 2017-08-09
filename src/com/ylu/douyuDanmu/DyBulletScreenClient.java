package com.ylu.douyuDanmu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ylu.beans.Message;
import com.ylu.beans.Message.Type;
import com.ylu.douyuFormat.Formating;
import com.ylu.douyuFormat.Logger;


/**
 * @Summary: 弹幕客户端类
 * @author: FerroD     
 * @date:   2016-3-12   
 * @version V1.0
 */
public class DyBulletScreenClient
{
	private static DyBulletScreenClient instance;
	
	//第三方弹幕协议服务器地址
	private static final String hostName = "openbarrage.douyutv.com";
	
	//第三方弹幕协议服务器端口
	private static final int port = 8601;
	
	//设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 4096;

    //socket相关配置
    private Socket sock;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;
    
    //获取弹幕线程及心跳线程运行和停止标记
    private boolean readyFlag = false;
    
    public DyBulletScreenClient(){}
    
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
    public void init(int roomId, int groupId){
    	//连接弹幕服务器
    	this.connectServer();
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
    public boolean getReadyFlag(){
    	return readyFlag;
    }
    
    public void setReadyFlag(boolean readyFlag){
    	this.readyFlag = readyFlag;
    }
    
    /**
     * 连接弹幕服务器
     */
    private void connectServer()
    {
        try
        {
        	//获取弹幕服务器访问host
        	String host = InetAddress.getByName(hostName).getHostAddress();
            //建立socke连接
        	sock = new Socket(host, port);
            //设置socket输入及输出
            bos = new BufferedOutputStream(sock.getOutputStream());
            bis= new BufferedInputStream(sock.getInputStream());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Logger.v("Server Connect Successfully!");
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
    		bos.write(loginRequestData, 0, loginRequestData.length);
    		bos.flush();
    		
    		//初始化弹幕服务器返回值读取包大小
    		byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
    		//获取弹幕服务器返回值
    		bis.read(recvByte, 0, recvByte.length);
    		
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
    		bos.write(joinGroupRequest, 0, joinGroupRequest.length);
            bos.flush();
            Logger.v("join Room %d group request successfully!",roomId);
            
    	} catch(Exception e){
    		e.printStackTrace();
    		Logger.v("Send join group request failed!");
    	}
    }

    /**
     * 服务器心跳连接
     */
    public void keepAlive()
    {
    	//获取与弹幕服务器保持心跳的请求数据包
        byte[] keepAliveRequest = DyMessage.getKeepAliveData((int)(System.currentTimeMillis() / 1000));

        try{
        	//向弹幕服务器发送心跳请求数据包
    		bos.write(keepAliveRequest, 0, keepAliveRequest.length);
            bos.flush();
            Logger.v("Send keep alive request successfully!");
            
    	} catch(Exception e){
    		e.printStackTrace();
    		Logger.v("Send keep alive request failed!");
    	}
    }

    /**
     * 获取服务器返回信息
     */
    public Collection<Message> getServerMsg(){
    	//初始化获取弹幕服务器返回信息包大小
    	byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
    	//定义服务器返回信息的字符串
    	String dataStr;
    	Collection<Message> messages = new ArrayList<Message>();
		try {
			//读取服务器返回信息，并获取返回信息的整体字节长度
			int recvLen = bis.read(recvByte, 0, recvByte.length);
			
			parseServerMsg(recvByte,recvLen,messages);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
    } 
    
    
    private void parseServerMsg(byte[] recvByte,int recvLen, Collection<Message> messages){
    	byte[] head = new byte[12];
    	System.arraycopy(recvByte, 0, head, 0, 12);
    	
    	try {
			int msgLength = DyMessage.parseMsgHead(head);
			//Logger.v("Head Check OK, msgLength = %d", msgLength);
			byte[] realBuf = new byte[msgLength];
			//按照实际获取的字节长度读取返回信息
			System.arraycopy(recvByte, 0, realBuf, 0, msgLength);
			String dataStr = new String(realBuf, 12, realBuf.length - 12);
			//Logger.v("Catch String : %s", dataStr);
			MsgView msgView = new MsgView(dataStr);
			messages.add(new Message.Builder().metaData(msgView.getMessageList()).build());
			if(recvLen - msgLength >0){
				byte[] leftBuf = new byte[recvLen - msgLength];
				//Logger.v("%d bytes left,keep parsing",leftBuf.length);
				System.arraycopy(recvByte, msgLength, leftBuf, 0, recvLen - msgLength);
				parseServerMsg(leftBuf,recvLen - msgLength, messages);
			}else{
				//Logger.v("Perfect package, parsing finished!");
			}
			
		} catch (Exception e) {
			Logger.v("Unknown type message caught : %s",new String(recvByte));
/*			Logger.v("%d bytes into Optionally parse",recvLen);
			String dataStr = new String(recvByte, 12, recvLen - 12);
			Logger.v("Optionally Catch String1: %s",dataStr);
			while(dataStr.lastIndexOf("type@=") > 0){
				MsgView msgView = new MsgView(StringUtils.substring(dataStr, dataStr.lastIndexOf("type@=")));
				messages.add(new Message.Builder().metaData(msgView.getMessageList()).build());
				dataStr = StringUtils.substring(dataStr, 0, dataStr.lastIndexOf("type@=") - 12);
				Logger.v("Optionally Catch String: %s",dataStr);
			}
			Logger.v("消息尾 : %s", dataStr);*/
			
		}
    	
    }
    	

    
}
