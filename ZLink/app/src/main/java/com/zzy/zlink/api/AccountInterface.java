package com.zzy.zlink.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.zzy.zlink.bean.ResultAllConversation;
import com.zzy.zlink.bean.ResultInfo;
import com.zzy.zlink.bean.ResultUserInfo;
import com.zzy.zlink.bean.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccountInterface {

    @POST("/ZLink/verifyLogin")
    Call<ResultUserInfo> verifyLogin(@Body User user);

    @POST("/ZLink/registerAccount")
    Call<ResultInfo> registerAccount(@Body User user);

    @POST("/ZLink/logout")
    Call<ResultInfo> logout(@Header("token") String token);

    @POST("ZLink/updateAccount")
    Call<ResultInfo> updateAccount(@Header("token") String token,@Body User user);

    @POST("/ZLink/searchUser")
    Call<ResultUserInfo> searchUser(@Header("token") String token,@Body User user);
}
