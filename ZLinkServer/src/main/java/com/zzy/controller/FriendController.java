package com.zzy.controller;

import com.zzy.pojo.Bean.ResultInfo;
import com.zzy.pojo.entity.User;
import com.zzy.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/ZLink")
public class FriendController{

    @Autowired
    FriendService friendService;

    @RequestMapping("/getFriend")
    public ResultInfo getFriend(HttpSession session){
        User user = (User) session.getAttribute("user");
        return new ResultInfo(200,friendService.getFriend(user));
    }

    @RequestMapping("/addFriend")
    public ResultInfo addFriend(HttpSession session,@RequestBody User friendUser){
        User thisUser = (User) session.getAttribute("user");
        if(friendService.addFriend(thisUser,friendUser)){
            System.out.println(200);
            return new ResultInfo(200,true);
        }else{
            System.out.println(202);
            return new ResultInfo(202,false);
        }
    }
}
