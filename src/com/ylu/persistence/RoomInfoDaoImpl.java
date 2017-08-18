package com.ylu.persistence;

import org.apache.ibatis.session.SqlSession;

import com.ylu.dao.RoomInfoDAO;


public class RoomInfoDaoImpl {

	private final String STRING_CREATE_TABLE = "com.ylu.persistence.RoomInfoDAOMapper.createTable";
	private final String STRING_INSERT_RECORD = "com.ylu.persistence.RoomInfoDAOMapper.insert";
	private SqlSession session;
	
	public RoomInfoDaoImpl(){
		session = MyBatisSession.getInstance();
		session.update(STRING_CREATE_TABLE);
	}
	
	public int insert(RoomInfoDAO record) {
		int result = session.insert(STRING_INSERT_RECORD, record);
		session.commit();
		return result;
	}
	
}
