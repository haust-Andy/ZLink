package com.zzy.dao;

import com.zzy.pojo.entity.User;
import com.zzy.pojo.entity.UserFriend;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserFriendDao {
    int insert(UserFriend record);

    int insertSelective(UserFriend record);

    ArrayList<User> selectFriendByUserId(Integer accountId);

    UserFriend selectUserFriend(UserFriend userFriend);
}