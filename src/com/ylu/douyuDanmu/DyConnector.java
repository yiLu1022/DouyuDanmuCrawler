package com.ylu.douyuDanmu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.ylu.douyuFormat.Logger;

public class DyConnector {
	
	
    byte[] bufferBytes;

    //socket相关配置
    private Socket sock;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;
   
  
    private final String url;
    
    private final int port;
    
    public DyConnector(String url, int port){
    	this.url = url;
    	this.port = port;
    }
    
    public void connect(){
    	try
        {
        	//获取弹幕服务器访问host
        	String host = InetAddress.getByName(url).getHostAddress();
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
    
    public void write(byte[] requestBytes) throws IOException{
		bos.write(requestBytes, 0, requestBytes.length);
		bos.flush();
		
    }
    
    public int read(byte[] recvBytes) throws IOException{
    	return bis.read(recvBytes, 0, recvBytes.length);

    }
}
