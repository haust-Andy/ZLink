package com.zzy.server;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzy.pojo.Bean.ConversationInfo;
import com.zzy.pojo.entity.Conversation;
import com.zzy.pojo.entity.MessageContent;
import com.zzy.pojo.entity.User;
import com.zzy.service.AccountService;
import com.zzy.service.ConversationService;
import com.zzy.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ZLink/imServer/{userName}")
@Component
public class WebSocketServer {

    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private Conversation theConversation = new Conversation();
    private User theUser = new User();
    ArrayList<ConversationInfo> infos;
    public static ConversationService conversationService;
    public static MessageService messageService;
    public static AccountService accountService;

    @Autowired
    public void setConversationService(ConversationService conversationService) {
        WebSocketServer.conversationService = conversationService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        WebSocketServer.messageService = messageService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        WebSocketServer.accountService = accountService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) throws IOException {

        theUser = accountService.selectUserByName(userName);
        infos = conversationService.getAllConversation(theUser);
        if (sessionMap.get("userName") == null) sessionMap.put(theUser.getAccountName(), session);
        if (!infos.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(infos);
//            session.getAsyncRemote().sendText(json);
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userName") String userName) {
        System.out.println(message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
        String text = obj.getStr("text"); // 发送的消息文本  hello
        Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
        MessageContent messageContent = new MessageContent();
        messageContent.setContent(text);
        messageContent.setPostTime(new Date());
        messageContent.setAccountName(userName);
        Integer cvsnId = conversationService.getId(userName, toUsername);
        messageContent.setConversationId(cvsnId);
        Integer messId = messageService.addMessage(messageContent);
        conversationService.updateLastMessageId(messageContent.getMessageId(), cvsnId);

        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", userName);  // from 是 zhang
            jsonObject.set("text", text);  // text 同上面的text
            toSession.getAsyncRemote().sendText(jsonObject.toString());
//            log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
        } else {
//            log.info("发送失败，未找到用户username={}的session", toUsername);
        }

    }

    @OnClose
    public void OnClose(@PathParam("userName") String userName) {

        sessionMap.remove(userName);

    }


}
