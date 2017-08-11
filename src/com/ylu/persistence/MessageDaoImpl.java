package com.ylu.persistence;

import java.util.Collection;

import org.apache.ibatis.session.SqlSession;


public class MessageDaoImpl implements MessageDAOMapper{
	private final String STRING_SELECT_CID = "com.ylu.persistence.MessageDAOMapper.selectByPrimaryKey";
	private final String STRING_INSERT_MESSAGE = "com.ylu.persistence.MessageDAOMapper.insert";
	private final String STRING_CREATE_TABLE = "com.ylu.persistence.MessageDAOMapper.createTable";
	
	private final String STRING_SELECT_UID = "com.ylu.persistence.MessageDAOMapper.selectByUid";
	private final String STRING_SELECT_BNN = "com.ylu.persistence.MessageDAOMapper.selectByBnn";
	private final String STRING_SELECT_LEVEL = "com.ylu.persistence.MessageDAOMapper.selectByLevel";
	
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
		MessageDAO message = session.selectOne(STRING_SELECT_CID,cid);    
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

	public Collection<MessageDAO> selectByUid(String uid) {
		Collection<MessageDAO> messageDAOs = session.selectList(STRING_SELECT_UID, uid);
		session.commit();
		return messageDAOs;
	}

	public Collection<MessageDAO> selectByBnn(String bnn) {
		Collection<MessageDAO> messageDAOs = session.selectList(STRING_SELECT_BNN, bnn);
		session.commit();
		return messageDAOs;
	}

	public Collection<MessageDAO> selectByLevel(String level) {
		Collection<MessageDAO> messageDAOs = session.selectList(STRING_SELECT_LEVEL, level);
		session.commit();
		return messageDAOs;
	}
	

}
