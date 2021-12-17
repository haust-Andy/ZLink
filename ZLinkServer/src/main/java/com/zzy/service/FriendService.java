package com.zzy.service;


import com.zzy.dao.UserDao;
import com.zzy.dao.UserFriendDao;
import com.zzy.pojo.entity.User;
import com.zzy.pojo.entity.UserFriend;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class FriendService {

    @Resource
    UserDao userDao;

    @Resource
    UserFriendDao userFriendDao;


    public ArrayList<User> getFriend(User user) {
        return userFriendDao.selectFriendByUserId(user.getAccountId());
    }

    public boolean addFriend(User thisUser, User friendUser) {
        UserFriend userFriend = new UserFriend();
        thisUser = userDao.selectByAccountNumber(thisUser.getAccountNumber());
        friendUser = userDao.selectByAccountNumber(friendUser.getAccountNumber());
        userFriend.setAccountId(thisUser.getAccountId());
        userFriend.setFriendAccountId(friendUser.getAccountId());
        if (userFriendDao.selectUserFriend(userFriend) != null)
            return false;
        return userFriendDao.insert(userFriend) == 1;
    }
}
