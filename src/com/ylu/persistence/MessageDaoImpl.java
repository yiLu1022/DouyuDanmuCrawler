package com.ylu.persistence;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;


public class MessageDaoImpl implements MessageDAOMapper{
	private final String STRING_SELECT_CID = "com.ylu.persistence.MessageDAOMapper.selectByPrimaryKey";
	private final String STRING_INSERT_MESSAGE = "com.ylu.persistence.MessageDAOMapper.insert";
	private final String STRING_CREATE_TABLE = "com.ylu.persistence.MessageDAOMapper.createTable";
	
	private final String STRING_SELECT_UID = "com.ylu.persistence.MessageDAOMapper.selectByUid";
	private final String STRING_SELECT_BNN = "com.ylu.persistence.MessageDAOMapper.selectByBnn";
	private final String STRING_SELECT_LEVEL = "com.ylu.persistence.MessageDAOMapper.selectByLevel";
	
	private final String STRING_SELECT_TOP_BNN = "com.ylu.persistence.MessageDAOMapper.selectTopByBnn";
	
	private final String STRING_SELECT_TOP_NN = "com.ylu.persistence.MessageDAOMapper.selectTopByNn";
	
	private final String STRING_SELECT_TIME_INTERVAL = "com.ylu.persistence.MessageDAOMapper.selectByTimeInterval";
	
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
	
	public List<Map<String, Object>> selectTopByBnn(int limit){
		List<Map<String, Object>> resultByBnns = session.selectList(STRING_SELECT_TOP_BNN,limit);
		session.commit();
		return resultByBnns;
		
	}
	
	public List<Map<String, Object>> selectTopByNn(int limit){
		List<Map<String, Object>> resultByNns = session.selectList(STRING_SELECT_TOP_NN,limit);
		session.commit();
		return resultByNns;
		
	}
	
	public List<Map<String, Object>> selectByTimeInterval(Timestamp start,Timestamp end,long interval){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		String startTime = sdf.format(start);
		String endTime = sdf.format(end);
		parameterMap.put("start", startTime);
		parameterMap.put("end", endTime);
		parameterMap.put("interval", interval);
		List<Map<String, Object>> results = session.selectList(STRING_SELECT_TIME_INTERVAL,parameterMap);
		session.commit();
		return results;
		
	}

}
