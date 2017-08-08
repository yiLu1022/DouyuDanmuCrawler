package com.ylu.douyuFormat;

public class Formating {
	public static String bytes2Hex(byte[] bytes){
		StringBuilder sb = new StringBuilder();
		for(byte b : bytes){
			sb.append("0x");
			byte high =  (byte) ((b & 0xf0)>>4);
			if( high <= 0x09 ){
				sb.append(String.valueOf(high));
			}else{
				int ascii = (high - 0x0a) + 65;
				sb.append((char)ascii);
			}
			
			byte low =  (byte) (b & 0x0f);
			if( low <= 0x09 ){
				sb.append(String.valueOf(low));
			}else{
				int ascii = (low - 0x0a) + 65;
				sb.append((char)ascii);
			}
			sb.append(",");
		}
		return sb.toString();
	}
	
	 public   static   byte [] toLH( int  n)
	    {
	       byte [] b =  new   byte [ 4 ];
	       b[0 ] = ( byte ) (n &  0xff );
	       b[1 ] = ( byte ) (n >>  8  &  0xff );
	       b[2 ] = ( byte ) (n >>  16  &  0xff );
	       b[3 ] = ( byte ) (n >>  24  &  0xff );
	       return  b;
	    }

	    public   static   byte [] toHH( int  n)
	    {
	       byte [] b =  new   byte [ 4 ];
	       b[3 ] = ( byte ) (n &  0xff );
	       b[2 ] = ( byte ) (n >>  8  &  0xff );
	       b[1 ] = ( byte ) (n >>  16  &  0xff );
	       b[0 ] = ( byte ) (n >>  24  &  0xff );
	       return  b;
	    }

	    public   static   byte [] toLH( short  n)
	    {
	       byte [] b =  new   byte [ 2 ];
	       b[0 ] = ( byte ) (n &  0xff );
	       b[1 ] = ( byte ) (n >>  8  &  0xff );
	       return  b;
	    }

	    public   static   byte [] toHH( short  n)
	    {
	       byte [] b =  new   byte [ 2 ];
	       b[1 ] = ( byte ) (n &  0xff );
	       b[0 ] = ( byte ) (n >>  8  &  0xff );
	       return  b;
	    }
}
