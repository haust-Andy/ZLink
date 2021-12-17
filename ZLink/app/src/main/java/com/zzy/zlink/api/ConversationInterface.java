package com.zzy.zlink.api;

import com.zzy.zlink.bean.ResultAllConversation;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ConversationInterface {

    @POST("/ZLink/getAllConversation")
    Call<ResultAllConversation> getAllConversation(@Header("token") String token);
}
