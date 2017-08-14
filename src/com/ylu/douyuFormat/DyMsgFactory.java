package com.ylu.douyuFormat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.ylu.exceptions.CheckException;



/**
 * @Summary: 斗鱼弹幕协议信息封装类
 * @author: FerroD     
 * @date:   2016-3-12   
 * @version V1.0
 */
public class DyMsgFactory
{

	//弹幕客户端类型设置
    public final static int DY_MESSAGE_TYPE_CLIENT = 689;
    /**
     * 生成登录请求数据包
     * @param roomId
     * @return
     */
    public static byte[] getLoginRequestData(int roomId){
    	//编码器初始化
    	DyEncoder enc = new DyEncoder();
        //添加登录协议type类型
    	enc.addItem("type", "loginreq");
        //添加登录房间ID
    	enc.addItem("roomid", roomId);

    	//返回登录协议数据
        return getByte(enc.getResult());
    }

    /**
     * 解析登录请求返回结果
     * @param respond
     * @return
     */
    public static boolean parseLoginRespond(byte[] respond){
    	boolean rtn = false;
    	
    	//返回数据不正确（仅包含12位信息头，没有信息内容）
    	if(respond.length <= 12){
    		return rtn;
    	}
    	
    	//解析返回信息包中的信息内容
    	String dataStr = new String(respond, 12, respond.length - 12);
    	
    	//针对登录返回信息进行判断
    	if(StringUtils.contains(dataStr, "type@=loginres")){
    		rtn = true;
    	}
    	
    	//返回登录是否成功判断结果
    	return rtn;
    }
    
    private static int parseMsgHead(byte[] head) throws CheckException{
    	byte[] head1 = new byte[4];
    	byte[] head2 = new byte[4];
    	byte[] messageType = new byte[4];
    	System.arraycopy(head, 0, head1, 0, 4);
    	System.arraycopy(head, 4, head2, 0, 4);
    	System.arraycopy(head, 8, messageType, 0, 4);

    	int length1 = Formating.fromLH(head1) ;
    	int length2 = Formating.fromLH(head2) ;
    	if((length1 != length2) || length1 < 12){
    		throw new CheckException("Bad Head!");
    	}

    	if(Formating.fromLH(messageType) != 690){
    		//return Formating.fromLH(head1) +4;
    		throw new CheckException("Bad Head!" + String.valueOf(Formating.fromLH(messageType)));
    	}else{
    		return Formating.fromLH(head1) +4;
    	}
    }
    
    /**
     * 
     * @param recvBytes: the bytes array to parse
     * @param recvLen: the length of valid bytes
     * @param mappers: A collection to collect the parsing result
     * @return: the bytes in the end of the valid bytes, which cannot be parse this time
     */
    public static byte[] parseRecvMsg(byte[] recvBytes,int recvLen, Collection<MsgMapper> mappers){
    	Collection<String> dataStrs = new ArrayList<String>();
    	byte[] leftBytes =carefulParse(recvBytes, recvLen, dataStrs);
    	if(leftBytes == null){
    		for(String dataStr : dataStrs){
    			MsgMapper mapper = new MsgMapper(dataStr); 
    			mappers.add(mapper);
    		}
    		return null;
    	}else{
    		//check the leftBytes again
    		if(leftBytes.length < 12){ 
    			//if it less than 12 bytes, maybe it's a incomplete head, keep it.
    			return leftBytes;
    		}
    		try {
    			//if it pass the check of head, it contains a good head, keep it
    			byte[] head = new byte[12];
            	System.arraycopy(recvBytes, 0, head, 0, 12);
				parseMsgHead(leftBytes);
				return leftBytes;
			} catch (CheckException e) {
				//other wise, it does not contain a good head, try to parse it carelessly
				int index = Formating.indexOf(leftBytes, "type@=".getBytes(),12);
	    		if(index != -1){
		    		byte[] firstCompleteMsg  = new byte[leftBytes.length - index +12];
					System.arraycopy(leftBytes, index - 12, firstCompleteMsg, 0, leftBytes.length - index +12);
					return carefulParse(firstCompleteMsg, firstCompleteMsg.length, dataStrs);
	    		}else{
	    			carelessParse(leftBytes, leftBytes.length, dataStrs);
					return null;
	    		}
			}
    	}
		
    }
    
    /**
     * This method will be called only when "type@=" found in an incomplete bytes array also with 
     * an incomplete head, it means we cannot get the length of the message. carelessParse will only
     * parse one "type@=".
     * Parse the bytes by searching for "type@=", from tail to head, if there are still some bytes
     * before the first "type@=", report it then ignore it. 
     */
    private static void carelessParse(byte[] recvBytes,int recvLen,Collection<String> dataStrs){
		String dataStr = new String(recvBytes, 0, recvBytes.length);
		while(dataStr.lastIndexOf("type@=") > 5){
			String lastString = StringUtils.substring(dataStr, dataStr.lastIndexOf("type@="));
			dataStrs.add(lastString);
			dataStr = StringUtils.substring(dataStr, 0, dataStr.lastIndexOf("type@=") - 12);
		}
		Logger.v("Unhandler message--------------------> %s", dataStr);
    }
    
    private static byte[] carefulParse(byte[] recvBytes,int recvLen,  Collection<String> dataStrs){
    	try {
        	//recvBytes doesn't reach the least length
        	if(recvLen < 12){
        		throw new CheckException(String.format("left Bytes %d",recvLen));
        	}
        	
        	byte[] head = new byte[12];
        	System.arraycopy(recvBytes, 0, head, 0, 12);
			int msgLength = parseMsgHead(head);
			
			//if msgLength < recvLen means the recvBytes is not complete, can not continue to parse the data
			if(msgLength > recvLen){
				throw new CheckException(String.format("should have %d bytes, only got %d bytes",msgLength,recvLen));
			}
			byte[] realBuf = new byte[msgLength];
			System.arraycopy(recvBytes, 0, realBuf, 0, msgLength);
			String dataStr = new String(realBuf, 12, msgLength - 12);
			dataStrs.add(dataStr);
			//After parsing one message ahead, if there are still some bytes left.
			if(recvLen - msgLength  >0){
				byte[] leftBuf = new byte[recvLen - msgLength ];
				System.arraycopy(recvBytes, msgLength , leftBuf, 0, recvLen - msgLength);
				return carefulParse(leftBuf,recvLen - msgLength, dataStrs);
			}
			return null;
		} catch (CheckException e) {
			byte[] leftBytes = new byte[recvLen];
			System.arraycopy(recvBytes, 0, leftBytes, 0, recvLen);
			return leftBytes;
		}
    }
    
    /**
     * 生成加入弹幕分组池数据包
     * @param roomId
     * @param groupId
     * @return
     */
    public static byte[] getJoinGroupRequest(int roomId, int groupId){
    	//编码器初始化
    	DyEncoder enc = new DyEncoder();
    	//添加加入弹幕池协议type类型
    	enc.addItem("type", "joingroup");
    	//添加房间id信息
    	enc.addItem("rid", roomId);
    	//添加弹幕分组池id信息
    	enc.addItem("gid", groupId);
    	
    	//返回加入弹幕池协议数据
    	return getByte(enc.getResult());
    }
    
    /**
     * 生成心跳协议数据包
     * @param timeStamp
     * @return
     */
    public static byte[] getKeepAliveData(int timeStamp){
    	//编码器初始化
    	DyEncoder enc = new DyEncoder();
    	//添加心跳协议type类型
    	enc.addItem("type", "keeplive");
    	//添加心跳时间戳
    	enc.addItem("tick", timeStamp);
    	
    	//返回心跳协议数据
    	return getByte(enc.getResult());
    }
    
    /**
     * 通用方法，将数据转换为小端整数格式
     * @param data
     * @return
     */
    private static byte[] getByte(String data){
    	 ByteArrayOutputStream boutput = new ByteArrayOutputStream();
         DataOutputStream doutput = new DataOutputStream(boutput);

         try
         {
             boutput.reset();
             doutput.write(Formating.toLH(data.length() + 8), 0, 4);        // 4 bytes packet length
             doutput.write(Formating.toLH(data.length() + 8), 0, 4);        // 4 bytes packet length
             doutput.write(Formating.toLH(DY_MESSAGE_TYPE_CLIENT), 0, 2);   // 2 bytes message type
             doutput.writeByte(0);                                               // 1 bytes encrypt
             doutput.writeByte(0);                                               // 1 bytes reserve
             doutput.writeBytes(data);
         }
         catch(Exception e)
         {
         	e.printStackTrace();
         }

         return boutput.toByteArray();
    }

}



