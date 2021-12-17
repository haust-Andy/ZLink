package com.zzy.service;


import com.zzy.dao.ConversationDao;
import com.zzy.dao.MessageContentDao;
import com.zzy.pojo.Bean.ChatContent;
import com.zzy.pojo.entity.MessageContent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class MessageService {

    @Resource
    MessageContentDao messageContentDao;

    @Resource
    ConversationDao conversationDao;

    public Integer addMessage(MessageContent messageContent) {
        messageContentDao.insert(messageContent);
        return messageContentDao.selectIdByOther(messageContent);
    }

    public ArrayList<ChatContent> getMessage(String nameA, String nameB) {
        Integer cvsnId = conversationDao.getId(nameA, nameB);
        ArrayList<MessageContent> messageContents=new ArrayList<>();
        ArrayList<ChatContent> chatContents=new ArrayList<>();
        messageContents=messageContentDao.selectByCvsnId(cvsnId);

        for(MessageContent content:messageContents){
            ChatContent chatContent=new ChatContent();
            chatContent.setContent(content.getContent());
            chatContent.setPostTime(content.getPostTime());
            chatContent.setType((Objects.equals(content.getAccountName(), nameA))?1:0);
            chatContents.add(chatContent);
        }
        return chatContents;
    }
}
