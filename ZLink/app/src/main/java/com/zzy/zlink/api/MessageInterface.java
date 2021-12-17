package com.zzy.zlink.api;

import com.zzy.zlink.bean.ChatContent;
import com.zzy.zlink.bean.ConversationInfo;
import com.zzy.zlink.bean.ResultFriends;
import com.zzy.zlink.bean.ResultMessage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MessageInterface {

    @POST("/ZLink/getMessage")
    Call<ResultMessage> getMessage(@Header("token") String token, @Body ConversationInfo conversationInfo);
}
