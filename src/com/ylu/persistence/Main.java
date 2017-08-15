package com.ylu.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import com.ylu.beans.Message;
import com.ylu.douyuFormat.Logger;
import com.ylu.util.Utils;

public class Main {

	public static void main(String[] args) {
		DatabaseHelper db = DatabaseHelper.getInstance();
		//db.selectTopByNn(10);
/*		
		String start = "2017-08-15 19:00:00";
		String end = "2017-08-16 03:55:00";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			db.selectByTimeInterval(sdf.parse(start), sdf.parse(end), 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		Collection<Message> messages = db.findMessageByBnn("嗨粉");
		Logger.v(Utils.outputTxt(messages));
	}

}
