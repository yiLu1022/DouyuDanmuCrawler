package douyuDanmu;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class DouyuConnector {
	private static final String URL = "openbarrage.douyutv.com";
	private static final int PORT = 8601;
	private static final String LOGIN_FORMAT_SHORT = "type@=loginreq/roomid@=%d/";
	private static final String REGROUP = "type@=joingroup/rid@=%d/gid@=-9999/";
	private static final int ROOM_ID = 338281;
	private static final int MAX_BUFFER_LENGTH = 4096;
	private Sink sink;
	private Source source;
	private Socket socket;

	
	DouyuConnector(){
			
	}
	
	public void connect() throws Exception{
		//获取弹幕服务器访问host
    	String host = InetAddress.getByName(URL).getHostAddress();
        //建立socke连接
    	socket = new Socket(host, PORT);
        //设置socket输入及输出
		sink = Okio.buffer(Okio.sink(socket));
		source = Okio.buffer(Okio.source(socket));
		
		
	}
	
	public static byte[] intToBytes( int value )   
	{   
	    byte[] src = new byte[4];  
	    src[3] =  (byte) ((value>>24) & 0xFF);  
	    src[2] =  (byte) ((value>>16) & 0xFF);  
	    src[1] =  (byte) ((value>>8) & 0xFF);    
	    src[0] =  (byte) (value & 0xFF);                  
	    return src;   
	}
	
	private void loginRoom(int roomId)
    {
    	//获取弹幕服务器登陆请求数据包
    	byte[] loginRequestData = DyMessage.getLoginRequestData(roomId);
    	
    	
    	try{
    		BufferedSink bSink = Okio.buffer(sink);
    		//发送登陆请求数据包给弹幕服务器
    		bSink.write(loginRequestData, 0, loginRequestData.length);
    		bSink.flush();
    		
    		//初始化弹幕服务器返回值读取包大小
    		byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
    		//获取弹幕服务器返回值
    		BufferedSource bSource = Okio.buffer(source);
    		bSource.read(recvByte, 0, recvByte.length);
    		
    		//解析服务器返回的登录信息
    		if(DyMessage.parseLoginRespond(recvByte)){
    			Logger.v("Receive login response successfully!");
            } else {
            	Logger.v("Receive login response failed!");
            }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
