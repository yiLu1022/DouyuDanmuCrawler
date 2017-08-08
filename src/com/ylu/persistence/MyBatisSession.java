package com.ylu.persistence;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisSession {
	private static SqlSession instance;
	private MyBatisSession(){}

	public static final SqlSession getInstance(){
		 if(instance == null){
			 instance = getFactory().openSession();
			 if(instance==null) System.out.println("openSessionFailed!");
		     else System.out.println("openSessionSuccessfully!");
		 }
		 return instance;
	}

	private static class SessionHolder{
		private static final SqlSession session  = getFactory().openSession();
	}
	public static final SqlSession getSessionInstance(){
		return SessionHolder.session;
	}	
	
	public static SqlSessionFactory getFactory(){  
        String resource = "config.xml";  
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);  
	        return factory;  
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
