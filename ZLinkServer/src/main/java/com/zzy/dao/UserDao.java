package com.zzy.dao;

import com.zzy.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    int deleteByPrimaryKey(Integer accountId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer accountId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);
    //自定义
    User selectByAccountNumber(String accountNumber);

    User selectByToken(String token);

    int setToken(User selectUser);

    int delTokenByAccountNumber(String accountNumber);

    User selectByName(String userName);
}