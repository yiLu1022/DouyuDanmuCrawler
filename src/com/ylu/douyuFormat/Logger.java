package com.ylu.douyuFormat;

public class Logger {
	private static final String FILE_NAME = "log.txt";
	
/*	static{
		try {
			PrintStream log = new PrintStream(FILE_NAME);
			System.setOut(log);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void v(String message){
		System.out.println(message);
	}
	
	public static void v(String format,Object... args){
		System.out.println(String.format(format, args));
	}
}
