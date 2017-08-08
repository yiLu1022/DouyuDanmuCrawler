package com.ylu.persistence;

import org.apache.ibatis.session.SqlSession;


public class MessageDaoImpl implements MessageDAOMapper{
	private final String STRING_SELECT_MESSAGE = "com.ylu.persistence.MessageDAOMapper.selectByPrimaryKey";
	private final String STRING_INSERT_MESSAGE = "com.ylu.persistence.MessageDAOMapper.insert";
	private final String STRING_CREATE_TABLE = "com.ylu.persistence.MessageDAOMapper.createTable";
	
	
	private SqlSession session;
	public MessageDaoImpl(){
		session = MyBatisSession.getInstance();
		session.update(STRING_CREATE_TABLE);
	}

	public int deleteByPrimaryKey(String cid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(MessageDAO record) {
		int result = session.insert(STRING_INSERT_MESSAGE, record);
		session.commit();
		return result;
	}

	public int insertSelective(MessageDAO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public MessageDAO selectByPrimaryKey(String cid) {
		MessageDAO message = session.selectOne(STRING_SELECT_MESSAGE,cid);    
        session.commit();  
        return message; 
	}

	public int updateByPrimaryKeySelective(MessageDAO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByPrimaryKey(MessageDAO record) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
