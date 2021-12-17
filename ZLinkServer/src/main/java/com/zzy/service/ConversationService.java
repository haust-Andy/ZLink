package com.zzy.service;


import com.zzy.dao.ConversationDao;
import com.zzy.dao.UserDao;
import com.zzy.pojo.Bean.CVSN;
import com.zzy.pojo.Bean.ConversationInfo;
import com.zzy.pojo.entity.Conversation;
import com.zzy.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConversationService {


    @Autowired
    UserDao userDao;
    @Autowired
    ConversationDao conversationDao;


    public ArrayList<ConversationInfo> getAllConversation(User user) {
        User theUser = userDao.selectByToken(user.getAccountToken());
        ArrayList<ConversationInfo> conversationArrayList = conversationDao.selectByUserNumber1(theUser.getAccountNumber());
        ArrayList<CVSN> cvsns = conversationDao.selectByUserNumber2(theUser.getAccountNumber());
        for (CVSN c1 : cvsns) {
            ConversationInfo info = new ConversationInfo();
            info.setContent(c1.getContent());
            info.setAccountName2(c1.getAccountName1());
            info.setAccountNumber2(c1.getAccountNumber1());
            info.setPostTime(c1.getPostTime());
            conversationArrayList.add(info);
        }
        return conversationArrayList;
    }

    public Integer addConversation(Conversation conversation) {
        Conversation conversation1 = conversationDao.selectByAB(conversation);
        if (conversation1 == null) {
            conversationDao.insert(conversation);
            return conversationDao.selectByAB(conversation).getConversationId();
        } else {
            return conversation1.getConversationId();
        }
    }

    public void updateLastMessageId(Integer messageId, Integer conversationId) {
        conversationDao.updateMessageIdByPrimaryKey(messageId, conversationId);
    }

    public Integer getId(String userName, String toUsername) {
        Integer integer = conversationDao.getId(userName, toUsername);
        if (integer != null) return integer;
        Conversation conversation = new Conversation();
        User user = userDao.selectByName(userName);
        User toUser = userDao.selectByName(toUsername);
        conversation.setAccountName1(userName);
        conversation.setAccountName2(toUsername);
        conversation.setAccountNumber1(user.getAccountNumber());
        conversation.setAccountNumber2(toUser.getAccountNumber());
        conversationDao.insert(conversation);
        return conversation.getConversationId();
    }
}
