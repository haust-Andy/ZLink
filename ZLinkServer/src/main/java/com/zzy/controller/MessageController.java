package com.zzy.controller;


import com.zzy.pojo.Bean.ChatContent;
import com.zzy.pojo.Bean.ConversationInfo;
import com.zzy.pojo.Bean.ResultInfo;
import com.zzy.pojo.entity.User;
import com.zzy.service.MessageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/ZLink")
public class MessageController {

    @Resource
    MessageService messageService;

    @RequestMapping("/getMessage")
    public ResultInfo getMessage(HttpSession session, @RequestBody ConversationInfo conversationInfo){
        User user = (User) session.getAttribute("user");
        ArrayList<ChatContent> contents=new ArrayList<>();
        contents= messageService.getMessage(user.getAccountName(),conversationInfo.getAccountName2());
        return new ResultInfo(200,contents);
    }



}
