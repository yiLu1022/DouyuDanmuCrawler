package com.ylu.persistence;


public interface MessageDAOMapper {
    int deleteByPrimaryKey(String cid);

    int insert(MessageDAO record);

    int insertSelective(MessageDAO record);

    MessageDAO selectByPrimaryKey(String cid);

    int updateByPrimaryKeySelective(MessageDAO record);

    int updateByPrimaryKey(MessageDAO record);
}