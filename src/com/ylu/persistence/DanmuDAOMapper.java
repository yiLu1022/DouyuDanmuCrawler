package com.ylu.persistence;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ylu.dao.DanmuDAO;


public interface DanmuDAOMapper {
    int deleteByPrimaryKey(String cid);

    int insert(DanmuDAO record);

    DanmuDAO selectByPrimaryKey(String cid);
    
    Collection<DanmuDAO> selectByUid(String uid);
    
    Collection<DanmuDAO> selectByBnn(String bnn);
    
    Collection<DanmuDAO> selectByLevel(String level);
    
    public List<Map<String, Object>> selectTopByBnn(int limit);
    
    public List<Map<String, Object>> selectTopByNn(int limit);
    
    public Collection<DanmuDAO> selectAllByTime(Timestamp start,Timestamp end);
    
    public List<Map<String, Object>> selectByTimeInterval(Timestamp start,Timestamp end,long interval);
}