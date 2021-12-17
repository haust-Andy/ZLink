package com.zzy.zlink.api;

import com.zzy.zlink.bean.ResultAllConversation;
import com.zzy.zlink.bean.ResultFriends;
import com.zzy.zlink.bean.ResultInfo;
import com.zzy.zlink.bean.ResultUserInfo;
import com.zzy.zlink.bean.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FriendInterface {
    @POST("/ZLink/getFriend")
    Call<ResultFriends> getFriend(@Header("token") String token);


    @POST("/ZLink/searchFriend")
    Call<ResultUserInfo> searchFriend(@Header("token") String tokrn, @Body User user);

    @POST("/ZLink/addFriend")
    Call<ResultInfo> addFriend(@Header("token") String token,@Body User user);

}
