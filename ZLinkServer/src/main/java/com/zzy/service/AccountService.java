package com.zzy.service;

import com.alibaba.druid.util.StringUtils;
import com.zzy.dao.UserDao;
import com.zzy.pojo.entity.User;
import org.apache.catalina.session.StandardSession;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    UserDao userDao;


    @Autowired
    private HttpSession session;

    public User verifyLogin(User user) {
        User selectUser = userDao.selectByAccountNumber(user.getAccountNumber());
        if (selectUser == null) {
            return null;
        }
        if (Objects.equals(selectUser.getAccountPassword(), user.getAccountPassword())) {
            selectUser.setAccountPassword("");
            return selectUser;
        } else return null;
    }

    public boolean userIsExist(User user) {
        return userDao.selectByAccountNumber(user.getAccountNumber()) != null;
    }

    public boolean createAccount(User user) {
        return userDao.insert(user) == 1;
    }

    public User selectUserById(String id) {
        return userDao.selectByPrimaryKey(Integer.parseInt(id));
    }

    public User verifyToken(String token) {
        if (StringUtils.isEmpty(token)) return null;
        else {
            User user = userDao.selectByToken(token);
            if (user!=null)
                user.setAccountPassword("");
            return user;
        }
    }

    public void setAccountToken(User selectUser) {
        userDao.setToken(selectUser);
    }
    public boolean delAccountToken(){
        User user=(User)session.getAttribute("user");
        return userDao.delTokenByAccountNumber(user.getAccountNumber()) == 1;
    }

    public Boolean updateAccountById(User user) {
       if( userDao.updateByPrimaryKey(user)==1)
           return true;
        else
            return false;
    }

    public User selectUserByToken(String token) {
        return userDao.selectByToken(token);
    }

    public User selectUserByName(String userName) {
        return userDao.selectByName(userName);
    }

    public User selectUserByNumber(String accountNumber) {
        return userDao.selectByAccountNumber(accountNumber);
    }
}
