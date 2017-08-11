package com.ylu.persistence;

import java.util.Collection;


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
}