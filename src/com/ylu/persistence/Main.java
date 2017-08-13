package com.ylu.persistence;

public class Main {

	public static void main(String[] args) {
		DatabaseHelper db = DatabaseHelper.getInstance();
		db.selectTopByBnn(10);
		

	}

}
