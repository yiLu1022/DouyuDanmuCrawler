package com.ylu.persistence;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.ylu.dao.DanmuDAO;


public class DanmuDaoImpl implements DanmuDAOMapper{
	
	private final String STRING_SELECT_CID = "com.ylu.persistence.DanmuDAOMapper.selectByPrimaryKey";
	private final String STRING_INSERT_DANMU = "com.ylu.persistence.DanmuDAOMapper.insert";
	private final String STRING_CREATE_TABLE = "com.ylu.persistence.DanmuDAOMapper.createTable";
	
	private final String STRING_SELECT_UID = "com.ylu.persistence.DanmuDAOMapper.selectByUid";
	private final String STRING_SELECT_BNN = "com.ylu.persistence.DanmuDAOMapper.selectByBnn";
	private final String STRING_SELECT_LEVEL = "com.ylu.persistence.DanmuDAOMapper.selectByLevel";
	
	private final String STRING_SELECT_TOP_BNN = "com.ylu.persistence.DanmuDAOMapper.selectTopByBnn";
	
	private final String STRING_SELECT_TOP_NN = "com.ylu.persistence.DanmuDAOMapper.selectTopByNn";
	
	private final String STRING_SELECT_TIME_INTERVAL = "com.ylu.persistence.DanmuDAOMapper.selectByTimeInterval";
	
	private SqlSession session;
	
	public DanmuDaoImpl(){
		session = MyBatisSession.getInstance();
		session.update(STRING_CREATE_TABLE);
	}

	public int deleteByPrimaryKey(String cid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(DanmuDAO record) {
		int result = session.insert(STRING_INSERT_DANMU, record);
		session.commit();
		return result;
	}

	public DanmuDAO selectByPrimaryKey(String cid) {
		DanmuDAO danmuDAO = session.selectOne(STRING_SELECT_CID,cid);    
        session.commit();  
        return danmuDAO; 
	}

	public Collection<DanmuDAO> selectByUid(String uid) {
		Collection<DanmuDAO> danmuDAOs = session.selectList(STRING_SELECT_UID, uid);
		session.commit();
		return danmuDAOs;
	}

	public Collection<DanmuDAO> selectByBnn(String bnn) {
		Collection<DanmuDAO> danmuDAOs = session.selectList(STRING_SELECT_BNN, bnn);
		session.commit();
		return danmuDAOs;
	}

	public Collection<DanmuDAO> selectByLevel(String level) {
		Collection<DanmuDAO> danmuDAOs = session.selectList(STRING_SELECT_LEVEL, level);
		session.commit();
		return danmuDAOs;
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
