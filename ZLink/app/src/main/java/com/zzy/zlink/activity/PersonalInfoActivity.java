package com.zzy.zlink.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzy.zlink.R;
import com.zzy.zlink.api.AccountInterface;
import com.zzy.zlink.api.ConversationInterface;
import com.zzy.zlink.bean.ResultInfo;
import com.zzy.zlink.bean.ResultUserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalInfoActivity extends MyActivity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        gestureDetector = new GestureDetector(PersonalInfoActivity.this, onGestureListener);
        initData();
        setClickEvent();
    }

    void initData() {
        TextView name_tv = findViewById(R.id.name_tv);
        SharedPreferences userData = PersonalInfoActivity.this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        name_tv.setText(userData.getString("accountName", ""));
    }

    void setClickEvent() {
        LinearLayout exit_login_liner = findViewById(R.id.exit_login_liner);
        LinearLayout change_account_liner = findViewById(R.id.change_account_liner);
        ImageView close_btn = findViewById(R.id.close_btn);
        RelativeLayout edit_info_rl = findViewById(R.id.edit_info_rl);

        View.OnClickListener logoutListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                Intent intent = new Intent(PersonalInfoActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        exit_login_liner.setOnClickListener(logoutListener);
        change_account_liner.setOnClickListener(logoutListener);
        close_btn.setOnClickListener(finishClickListener);
        edit_info_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalInfoActivity.this, UserInfoActivity.class));
            }
        });
    }

    private void logout() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        AccountInterface request = retrofit.create(AccountInterface.class);
        //对 发送请求 进行封装
        SharedPreferences userData = PersonalInfoActivity.this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        Call<ResultInfo> userCall = request.logout(userData.getString("accountToken", ""));
        userCall.enqueue(new Callback<ResultInfo>() {
            @Override
            public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                if (response.body().code == 200)
                    Toast.makeText(PersonalInfoActivity.this, "退出账号成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResultInfo> call, Throwable t) {
            }
        });
    }

    private final GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();
                    if (x < 0)
                        finish();
                    return true;
                }
            };

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

}