package com.zzy.dao;

import com.zzy.pojo.Bean.CVSN;
import com.zzy.pojo.Bean.ConversationInfo;
import com.zzy.pojo.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface ConversationDao {
    int deleteByPrimaryKey(Integer conversationId);

    int insert(Conversation record);

    int insertSelective(Conversation record);

    Conversation selectByPrimaryKey(Integer conversationId);

    int updateByPrimaryKeySelective(Conversation record);

    int updateByPrimaryKey(Conversation record);


    ArrayList<ConversationInfo> selectByUserNumber1(String accountNumber);
    ArrayList<CVSN> selectByUserNumber2(String accountNumber);

    Conversation selectByAB(Conversation conversation);

    void updateMessageIdByPrimaryKey(Integer messageId, Integer conversationId);

    Integer getId(String name_a, String name_b);

}