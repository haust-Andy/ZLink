package com.zzy.dao;

import com.zzy.pojo.entity.MessageContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper

public interface MessageContentDao {
    int deleteByPrimaryKey(Integer messageId);

    int insert(MessageContent record);

    int insertSelective(MessageContent record);

    MessageContent selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(MessageContent record);

    int updateByPrimaryKey(MessageContent record);

    Integer selectIdByOther(MessageContent messageContent);

    ArrayList<MessageContent> selectByCvsnId(Integer cvsnId);
}