package com.ylu.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ylu.beans.ResultByBnn;


public interface MessageDAOMapper {
    int deleteByPrimaryKey(String cid);

    int insert(MessageDAO record);

    int insertSelective(MessageDAO record);

    MessageDAO selectByPrimaryKey(String cid);

    int updateByPrimaryKeySelective(MessageDAO record);

    int updateByPrimaryKey(MessageDAO record);
    
    Collection<MessageDAO> selectByUid(String uid);
    
    Collection<MessageDAO> selectByBnn(String bnn);
    
    Collection<MessageDAO> selectByLevel(String level);
    
    public List<Map<String, Object>> selectTopByBnn(int limit);
    
    public List<Map<String, Object>> selectTopByNn(int limit);
}