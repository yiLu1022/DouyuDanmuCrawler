package com.ylu.douyuFormat;

import javax.security.auth.kerberos.KerberosKey;

/**
 * @Summary: 弹幕协议格式化类
 * @author: FerroD     
 * @date:   2016-3-12   
 * @version V1.0
 */
public class DyEncoder
{
    private StringBuffer buf = new StringBuffer();

    /**
     * 返回弹幕协议格式化后的结果
     * @return
     */
    public String getResult()
    {
    	//数据包末尾必须以'\0'结尾
    	buf.append('\0');
        return buf.toString();
    }

    /**
     * 添加协议参数项
     * @param key
     * @param value
     */
    public void addItem(String key, Object value)
    {
    	//根据斗鱼弹幕协议进行相应的编码处理
    	buf.append(key.replaceAll("/", "@S").replaceAll("@", "@A"));
    	buf.append("@=");
    	if(value instanceof String){
    		buf.append(((String)value).replaceAll("/", "@S").replaceAll("@", "@A"));
    	}else if(value instanceof Integer){
    		buf.append(value);
    	}
    	buf.append("/");
    }
    
    public static int indexOf(byte[] A, byte[] B,int offset){
    	if(A.length < B.length){
    		return -1;
    	}
    	int j = 0;
    	for(int i= offset; i<= (A.length - B.length) ; i++){
    		for(int k = i; j< B.length && k<A.length; j++){
    			if(A[k] == B[j]){
    				k++; 
    			}else{
    				j = 0;
    				break;
    			}
    		}
    		if(j == B.length){
    			return i;
    		}
    	}
    	return -1;
    }
}
