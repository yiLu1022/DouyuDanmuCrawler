package com.ylu.demoApp;

import com.ylu.persistence.DatabaseHelper;

public class MySqlMain {

	public static void main(String[] args) {
		DatabaseHelper db = DatabaseHelper.getInstance();
		db.selectTopByNn(20);
		
		/*String start = "2017-08-14 16:30:00";
		String end = "2017-08-14 16:55:00";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			db.selectByTimeInterval(sdf.parse(start), sdf.parse(end), 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/

	}

}
