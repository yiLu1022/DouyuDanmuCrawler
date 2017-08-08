package com.ylu.douyuFormat;

public class Logger {
	public static void v(String message){
		System.out.println(message);
	}
	
	public static void v(String format,Object... args){
		System.out.println(String.format(format, args));
	}
}
