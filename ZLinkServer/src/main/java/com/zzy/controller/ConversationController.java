package com.zzy.controller;


import com.zzy.pojo.Bean.ConversationInfo;
import com.zzy.pojo.Bean.ResultInfo;
import com.zzy.pojo.Bean.test;
import com.zzy.pojo.entity.User;
import com.zzy.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/ZLink")
public class ConversationController {

    @Autowired
    ConversationService conversationService;



    @RequestMapping("/getAllConversation")
    public ResultInfo getAllConversation(HttpSession session) {
        ArrayList<ConversationInfo> conversationInfos;
        User user = (User) session.getAttribute("user");
        conversationInfos=conversationService.getAllConversation(user);
        return new ResultInfo(200,conversationInfos);
    }
}
